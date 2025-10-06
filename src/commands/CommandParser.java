package commands;

import models.*;
import services.MovieService;
import services.SearchService;
import services.UserService;

public class CommandParser {
    private final MovieService movieService;
    private final UserService userService;
    private final SearchService searchService;

    public CommandParser(MovieService movieService, UserService userService, SearchService searchService) {
        this.movieService = movieService;
        this.userService = userService;
        this.searchService = searchService;
    }

    public void executeCommand(String command) {
        try {
            String [] parts = command.trim().split("\\s+");
            if(parts.length == 0) {
                return;
            }

            String action = parts[0].toUpperCase();

            switch (action) {
                case "ADD_MOVIE":
                    handleAddMovie(parts);
                    break;
                case "ADD_USER":
                    handleAddUser(parts);
                    break;
                case "SEARCH":
                    handleSearch(parts);
                    break;
                case "SEARCH_MULTI":
                    handleSearchMulti(parts);
                    break;
                case "VIEW_CACHE_STATS":
                    searchService.printCacheStats();
                    break;
                case "CLEAR_CACHE":
                    if (parts.length > 1) {
                        searchService.clearCache(parts[1]);
                        System.out.println(parts[1] + " cache cleared successfully");
                    }
                    break;
                default:
                    System.out.println("Unknown command: " + action);
            }
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }

    private void handleAddMovie(String[] parts) {
        if(parts.length < 6) {
            throw new IllegalArgumentException("Invalid ADD_MOVIE command format");
        }

        int id = Integer.parseInt((parts[1]));
        String title = extractQuotedString(parts, 2);
        int nextIndex = getNextIndexAfterQuoted(parts, 2);
        String genre = extractQuotedString(parts, nextIndex);
        nextIndex = getNextIndexAfterQuoted(parts, nextIndex);
        int year = Integer.parseInt(parts[nextIndex]);
        float rating = Float.parseFloat(parts[nextIndex + 1]);

        Movie movie = new Movie(id, title, genre, year, rating);
        movieService.addMovie(movie);
        System.out.println("Movie '" + title + "' added successfully");
    }

    private void handleAddUser(String[] parts) {
        if(parts.length < 4) {
            throw new IllegalArgumentException("Invalid ADD_USER command format");
        }

        int id = Integer.parseInt(parts[1]);
        String name = extractQuotedString(parts, 2);
        int nextIndex = getNextIndexAfterQuoted(parts, 2);
        String preferredGenre = extractQuotedString(parts, nextIndex);

        User user = new User(id, name, parseGenreType(preferredGenre));
        userService.addUser(user);
        System.out.println("User '" + name + "' added successfully");
    }

    private void handleSearch(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid SEARCH command format");
        }

        int userId = Integer.parseInt(parts[1]);
        SearchType type = SearchType.valueOf(parts[2].toUpperCase());
        String value = extractQuotedString(parts, 3);

        SearchCriteria criteria = new SearchCriteria(type, value);
        SearchResult result = searchService.search(userId, criteria);

        if (result.getMovies().isEmpty()) {
            System.out.println("No movies found");
        } else {
            for (Movie movie : result.getMovies()) {
                System.out.println(movie.getTitle() + " (Found in " + result.getFoundIn() + ")");
            }
        }
    }

    private void handleSearchMulti(String[] parts) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid SEARCH_MULTI command format");
        }

        int userId = Integer.parseInt(parts[1]);
        String genre = extractQuotedString(parts, 2);
        int nextIndex = getNextIndexAfterQuoted(parts, 2);
        int year = Integer.parseInt(parts[nextIndex]);
        float minRating = Float.parseFloat(parts[nextIndex + 1]);

        SearchCriteria criteria = new SearchCriteria(parseGenreType(genre), year, minRating);
        SearchResult result = searchService.search(userId, criteria);

        if (result.getMovies().isEmpty()) {
            System.out.println("No movies found");
        } else {
            for (Movie movie : result.getMovies()) {
                System.out.println(movie.getTitle() + " (Found in " + result.getFoundIn() + ")");
            }
        }
    }

    private String extractQuotedString(String[] parts, int startIndex) {
        if (parts[startIndex].startsWith("\"")) {
            StringBuilder sb = new StringBuilder();
            int i = startIndex;
            while (i < parts.length) {
                if (i > startIndex) sb.append(" ");
                sb.append(parts[i]);
                if (parts[i].endsWith("\"")) {
                    break;
                }
                i++;
            }
            String result = sb.toString();
            return result.substring(1, result.length() - 1);
        }
        return parts[startIndex];
    }

    private int getNextIndexAfterQuoted(String[] parts, int startIndex) {
        if (parts[startIndex].startsWith("\"")) {
            int i = startIndex;
            while (i < parts.length && !parts[i].endsWith("\"")) {
                i++;
            }
            return i + 1;
        }
        return startIndex + 1;
    }

    private GenreType parseGenreType(String genreString) {
        // Handle common genre string variations and convert to enum
        switch (genreString.toLowerCase()) {
            case "action":
                return GenreType.ACTION;
            case "comedy":
                return GenreType.COMEDY;
            case "drama":
                return GenreType.DRAMA;
            case "horror":
                return GenreType.HORROR;
            case "romance":
                return GenreType.ROMANCE;
            case "sci-fi":
            case "scifi":
            case "science fiction":
                return GenreType.SCI_FI;
            case "documentary":
                return GenreType.DOCUMENTARY;
            case "thriller":
                return GenreType.THRILLER;
            case "animation":
                return GenreType.ANIMATION;
            case "fantasy":
                return GenreType.FANTASY;
            default:
                // Try direct enum value parsing as fallback
                try {
                    return GenreType.valueOf(genreString.toUpperCase().replace("-", "_"));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid genre: " + genreString);
                }
        }
    }
}
