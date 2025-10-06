package strategies;

import models.Movie;
import models.SearchCriteria;
import models.SearchType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RatingSearchStrategy implements SearchStrategy {

    @Override
    public List<Movie> search(Collection<Movie> movies, SearchCriteria criteria) {
        if(!supports(criteria)) {
            throw new IllegalArgumentException("Search criteria not supported");
        }

        float minRating = Float.parseFloat(criteria.getValue());
        return movies.stream()
                .filter(m -> m.getRating() >= minRating)
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(SearchCriteria criteria) {
        return !criteria.isMultiFilter() && criteria.getType() == SearchType.RATING;
    }
}
