package commands;

import models.Movie;
import services.MovieService;
import utils.StringUtils;

public class AddMovieCommand implements  Command {
    private final MovieService movieService;
    private final String[] parts;

    public AddMovieCommand(MovieService movieService, String[] parts) {
        this.movieService = movieService;
        this.parts = parts;
    }

    @Override
    public void execute() {
        if (parts.length < 6) {
            throw new IllegalArgumentException("Invalid ADD_MOVIE command format");
        }

        int id = Integer.parseInt(parts[1]);
        String title = StringUtils.extractQuotedString(parts, 2);
        int nextIndex = StringUtils.getNextIndexAfterQuoted(parts, 2);
        String genre = StringUtils.extractQuotedString(parts, nextIndex);
        nextIndex = StringUtils.getNextIndexAfterQuoted(parts, nextIndex);
        int year = Integer.parseInt(parts[nextIndex]);
        float rating = Float.parseFloat(parts[nextIndex + 1]);

        Movie movie = new Movie(id, title, genre, year, rating);
        movieService.addMovie(movie);
        System.out.println("Movie '" + title + "' added successfully");
    }

    @Override
    public String getCommandName() {
        return "ADD_MOVIE";
    }
}
