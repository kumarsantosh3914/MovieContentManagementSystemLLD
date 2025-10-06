package commands;

import services.SearchService;

public class ViewCacheStatsCommand implements Command {
    private final SearchService searchService;

    public ViewCacheStatsCommand(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public void execute() {
        searchService.printCacheStats();
    }

    @Override
    public String getCommandName() {
        return "VIEW_CACHE_STATS";
    }
}
