package commands;

import models.User;
import services.UserService;
import utils.StringUtils;
import utils.GenreUtils;

public class AddUserCommand implements Command {
    private final UserService userService;
    private final String[] parts;

    public AddUserCommand(UserService userService, String[] parts) {
        this.userService = userService;
        this.parts = parts;
    }

    @Override
    public void execute() {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid ADD_USER command format");
        }

        int id = Integer.parseInt(parts[1]);
        String name = StringUtils.extractQuotedString(parts, 2);
        int nextIndex = StringUtils.getNextIndexAfterQuoted(parts, 2);
        String preferredGenre = StringUtils.extractQuotedString(parts, nextIndex);

        User user = new User(id, name, GenreUtils.parseGenreType(preferredGenre));
        userService.addUser(user);
        System.out.println("User '" + name + "' added successfully");
    }

    @Override
    public String getCommandName() {
        return "ADD_USER";
    }
}
