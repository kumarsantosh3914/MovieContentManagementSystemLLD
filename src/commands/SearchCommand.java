package commands;

import models.Movie;
import models.SearchCriteria;
import models.SearchResult;
import models.SearchType;
import services.SearchService;
import utils.StringUtils;

public class SearchCommand implements Command {
    private final SearchService searchService;
    private final String[] parts;

    public SearchCommand(SearchService searchService, String[] parts) {
        this.searchService = searchService;
        this.parts = parts;
    }

    @Override
    public void execute() {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid SEARCH command format");
        }

        int userId = Integer.parseInt(parts[1]);
        SearchType type = SearchType.valueOf(parts[2].toUpperCase());
        String value = StringUtils.extractQuotedString(parts, 3);

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

    @Override
    public String getCommandName() {
        return "SEARCH";
    }
}
