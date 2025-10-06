package commands;

public class UnknownCommand implements Command {
    private final String commandName;

    public UnknownCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public void execute() {
        System.out.println("Unknown command: " + commandName);
    }

    @Override
    public String getCommandName() {
        return "UNKNOWN";
    }
}
