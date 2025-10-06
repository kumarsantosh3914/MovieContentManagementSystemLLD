package commands;

import models.Movie;
import models.SearchCriteria;
import models.SearchResult;
import services.SearchService;
import utils.StringUtils;
import utils.GenreUtils;

public class SearchMultiCommand implements Command {
    private final SearchService searchService;
    private final String[] parts;

    public SearchMultiCommand(SearchService searchService, String[] parts) {
        this.searchService = searchService;
        this.parts = parts;
    }

    @Override
    public void execute() {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid SEARCH_MULTI command format");
        }

        int userId = Integer.parseInt(parts[1]);
        String genre = StringUtils.extractQuotedString(parts, 2);
        int nextIndex = StringUtils.getNextIndexAfterQuoted(parts, 2);
        int year = Integer.parseInt(parts[nextIndex]);
        float minRating = Float.parseFloat(parts[nextIndex + 1]);

        SearchCriteria criteria = new SearchCriteria(GenreUtils.parseGenreType(genre), year, minRating);
        SearchResult result = searchService.search(userId, criteria);

        if (result.getMovies().isEmpty()) {
            System.out.println("No movies found");
        } else {
            for (Movie movie : result.getMovies()) {
                System.out.println(movie.getTitle() + " (Found in " + result.getFoundIn() + ")");
            }
        }
    }

    @Override
    public String getCommandName() {
        return "SEARCH_MULTI";
    }
}
