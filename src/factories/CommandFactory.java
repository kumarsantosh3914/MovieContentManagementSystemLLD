package factories;

import commands.*;
import services.MovieService;
import services.SearchService;
import services.UserService;

public class CommandFactory {
    private final MovieService movieService;
    private final UserService userService;
    private final SearchService searchService;

    public CommandFactory(MovieService movieService, UserService userService, SearchService searchService) {
        this.movieService = movieService;
        this.userService = userService;
        this.searchService = searchService;
    }

    public Command createCommand(String commandString) {
        String[] parts = commandString.trim().split("\\s+");
        if (parts.length == 0) return null;

        String action = parts[0].toUpperCase();

        switch (action) {
            case "ADD_MOVIE":
                return new AddMovieCommand(movieService, parts);
            case "ADD_USER":
                return new AddUserCommand(userService, parts);
            case "SEARCH":
                return new SearchCommand(searchService, parts);
            case "SEARCH_MULTI":
                return new SearchMultiCommand(searchService, parts);
            case "VIEW_CACHE_STATS":
                return new ViewCacheStatsCommand(searchService);
            case "CLEAR_CACHE":
                return new ClearCacheCommand(searchService, parts);
            default:
                return new UnknownCommand(action);
        }
    }
}
