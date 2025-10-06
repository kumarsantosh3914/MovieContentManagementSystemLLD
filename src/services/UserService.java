package services;

import models.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<Integer, User> users;

    public UserService() {
        // this is not a thread-safe implementation for simplicity, but can be improved with ConcurrentHashMap if needed
        this.users = new HashMap<>();
    }

    public void addUser(User user) {
        if(users.containsKey(user.getUserId())) {
            throw new IllegalArgumentException("User already exists");
        }

        users.put(user.getUserId(), user);
    }

    public User getUser(int userId) {
        return users.get(userId);
    }

    public boolean userExists(int userId) {
        return users.containsKey(userId);
    }
}
