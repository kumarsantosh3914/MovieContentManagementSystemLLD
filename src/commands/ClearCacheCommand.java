package commands;

import services.SearchService;

public class ClearCacheCommand implements Command {
    private final SearchService searchService;
    private final String[] parts;

    public ClearCacheCommand(SearchService searchService, String[] parts) {
        this.searchService = searchService;
        this.parts = parts;
    }

    @Override
    public void execute() {
        if (parts.length > 1) {
            searchService.clearCache(parts[1]);
            System.out.println(parts[1] + " cache cleared successfully");
        } else {
            throw new IllegalArgumentException("Invalid CLEAR_CACHE command format");
        }
    }

    @Override
    public String getCommandName() {
        return "CLEAR_CACHE";
    }
}
