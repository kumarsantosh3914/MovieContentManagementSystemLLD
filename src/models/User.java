package models;

public class User {
    private final Integer userId;
    private final String userName;
    private final GenreType preferredGenre;

    public User(Integer userId, String userName, GenreType preferredGenre) {
        this.userId = userId;
        this.userName = userName;
        this.preferredGenre = preferredGenre;
    }

    // getters
    public Integer getUserId() {
        return userId;
    }

    public GenreType getPreferredGenre() {
        return preferredGenre;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, name='%s', preferredGenre='%s']",
                userId, userName, preferredGenre);
    }
}
