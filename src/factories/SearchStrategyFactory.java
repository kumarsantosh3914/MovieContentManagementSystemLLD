package factories;

import models.SearchCriteria;
import strategies.*;

import java.util.Arrays;
import java.util.List;

public class SearchStrategyFactory {

    private static final List<SearchStrategy> STRATEGIES = Arrays.asList(
            new GenreSearchStrategy(),
            new YearSearchStrategy(),
            new TitleSearchStrategy(),
            new RatingSearchStrategy(),
            new MultiFilterSearchStrategy()
    );

    public static SearchStrategy getSearchStrategy(SearchCriteria criteria) {
        for(SearchStrategy strategy: STRATEGIES) {
            if(strategy.supports(criteria)) {
                return strategy;
            }
        }

        throw new IllegalArgumentException("No strategy found for criteria: " + criteria);
    }
}
