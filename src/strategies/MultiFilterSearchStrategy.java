package strategies;

import models.Movie;
import models.SearchCriteria;
import models.SearchType;
import utils.GenreUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MultiFilterSearchStrategy implements SearchStrategy {

    @Override
    public List<Movie> search(Collection<Movie> movies, SearchCriteria criteria) {
        if(!supports(criteria)) {
            throw new IllegalArgumentException("Search criteria not supported");
        }

        return movies.stream()
                .filter(m -> criteria.getGenre() == null ||
                        GenreUtils.isGenreMatch(m.getGenre(), criteria.getGenre()))
                .filter(m -> criteria.getType() == null ||
                        m.getReleaseYear().equals(criteria.getYear()))
                .filter(m -> criteria.getMinRating() == null ||
                        m.getRating() >= criteria.getMinRating())
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(SearchCriteria criteria) {
        return !criteria.isMultiFilter();
    }
}
