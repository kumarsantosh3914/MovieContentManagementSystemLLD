package utils;

import models.GenreType;

public class GenreUtils {
    public static GenreType parseGenreType(String genreString) {
        switch (genreString.toLowerCase()) {
            case "action": return GenreType.ACTION;
            case "comedy": return GenreType.COMEDY;
            case "drama": return GenreType.DRAMA;
            case "horror": return GenreType.HORROR;
            case "romance": return GenreType.ROMANCE;
            case "sci-fi":
            case "scifi":
            case "science fiction": return GenreType.SCI_FI;
            case "documentary": return GenreType.DOCUMENTARY;
            case "thriller": return GenreType.THRILLER;
            case "animation": return GenreType.ANIMATION;
            case "fantasy": return GenreType.FANTASY;
            default:
                try {
                    return GenreType.valueOf(genreString.toUpperCase().replace("-", "_"));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid genre: " + genreString);
                }
        }
    }

    public static boolean isGenreMatch(String movieGenre, GenreType searchGenre) {
        String normalizedMovieGenre = movieGenre.toLowerCase().replace("-", "_").replace(" ", "_");
        String normalizedSearchGenre = searchGenre.name().toLowerCase();
        return normalizedMovieGenre.equals(normalizedSearchGenre);
    }
}
