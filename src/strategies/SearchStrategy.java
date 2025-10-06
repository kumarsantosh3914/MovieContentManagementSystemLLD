package strategies;

import models.Movie;
import models.SearchCriteria;

import java.util.Collection;
import java.util.List;

public interface SearchStrategy {
    List<Movie> search(Collection<Movie> movies, SearchCriteria criteria);
    boolean supports(SearchCriteria criteria);
}
