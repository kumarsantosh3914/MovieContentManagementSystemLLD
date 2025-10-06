package strategies;

import models.Movie;
import models.SearchCriteria;
import models.SearchType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TitleSearchStrategy implements SearchStrategy {

    @Override
    public List<Movie> search(Collection<Movie> movies, SearchCriteria criteria) {
        if(!supports(criteria)) {
            throw new IllegalArgumentException("Search criteria not supported");
        }

        String targetTitle = criteria.getValue().toLowerCase();
        return movies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(targetTitle))
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(SearchCriteria criteria) {
        return !criteria.isMultiFilter() && criteria.getType() == SearchType.TITLE;
    }
}
