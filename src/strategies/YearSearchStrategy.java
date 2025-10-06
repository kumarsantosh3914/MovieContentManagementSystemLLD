package strategies;

import models.Movie;
import models.SearchCriteria;
import models.SearchType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class YearSearchStrategy implements  SearchStrategy {

    @Override
    public List<Movie> search(Collection<Movie> movies, SearchCriteria criteria) {
        if(!supports(criteria)){
            throw new IllegalArgumentException("Search criteria supports only movies");
        }

        int targetYear = Integer.parseInt(criteria.getValue());
        return movies.stream()
                .filter(m -> m.getReleaseYear().equals(targetYear))
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(SearchCriteria criteria) {
        return !criteria.isMultiFilter() && criteria.getType() == SearchType.YEAR;
    }
}
