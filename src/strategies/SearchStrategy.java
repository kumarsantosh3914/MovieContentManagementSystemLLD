package strategies;

import models.Movie;

import java.util.Collection;
import java.util.List;

public interface SearchStrategy {
    List<Movie> search(Collection<Movie> movies);
}
