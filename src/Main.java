import caches.L1CacheManager;
import caches.L2CacheManager;
import commands.Command;
import factories.CommandFactory;
import services.MovieService;
import services.SearchService;
import services.UserService;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MovieService movieService = new MovieService();
        UserService userService = new UserService();
        L1CacheManager l1Cache = new L1CacheManager();
        L2CacheManager l2Cache = new L2CacheManager();
        SearchService searchService = new SearchService(movieService, userService, l1Cache, l2Cache);
        CommandFactory commandFactory = new CommandFactory(movieService, userService, searchService);

        System.out.println("=== ZipReel Movie Search System ===\n");

        // Demo workflow
        String[] commands = {
                // Add users
                "ADD_USER 1 \"Alice\" \"Action\"",
                "ADD_USER 2 \"Bob\" \"Sci-Fi\"",
                "ADD_USER 3 \"Charlie\" \"Comedy\"",

                // Add movies
                "ADD_MOVIE 1 \"Inception\" \"Sci-Fi\" 2010 9.5",
                "ADD_MOVIE 2 \"Mad Max\" \"Action\" 2015 8.1",
                "ADD_MOVIE 3 \"Tenet\" \"Sci-Fi\" 2020 7.8",
                "ADD_MOVIE 4 \"John Wick\" \"Action\" 2014 7.4",
                "ADD_MOVIE 5 \"Extraction\" \"Action\" 2020 8.0",
                "ADD_MOVIE 6 \"The Matrix\" \"Sci-Fi\" 1999 8.7",

                // Search operations to demonstrate caching
                "\n=== Search 1: Alice searches for Sci-Fi (Primary Store) ===",
                "SEARCH 1 GENRE \"Sci-Fi\"",

                "\n=== Search 2: Alice searches for Sci-Fi again (L1 Cache) ===",
                "SEARCH 1 GENRE \"Sci-Fi\"",

                "\n=== Search 3: Bob searches for Sci-Fi (L2 Cache) ===",
                "SEARCH 2 GENRE \"Sci-Fi\"",

                "\n=== Search 4: Alice searches for Action (Primary Store) ===",
                "SEARCH 1 GENRE \"Action\"",

                "\n=== Search 5: Multi-filter search ===",
                "SEARCH_MULTI 1 \"Action\" 2020 8.0",

                "\n=== Cache Statistics ===",
                "VIEW_CACHE_STATS",

                "\n=== Clear L1 Cache ===",
                "CLEAR_CACHE L1",

                "\n=== Search 6: Alice searches for Action again (L2 Cache after L1 cleared) ===",
                "SEARCH 1 GENRE \"Action\"",

                "\n=== Final Cache Statistics ===",
                "VIEW_CACHE_STATS"
        };

        // Execute commands using command pattern
        for (String command : commands) {
            if (command.startsWith("\n===")) {
                System.out.println(command);
            } else {
                System.out.println("Command: " + command);
                Command cmd = commandFactory.createCommand(command);
                if (cmd != null) {
                    try {
                        cmd.execute();
                    } catch (Exception e) {
                        System.out.println("Error executing command: " + e.getMessage());
                    }
                }
                System.out.println();
            }
        }

        // Interactive mode (optional)
        System.out.println("\n=== Interactive Mode ===");
        System.out.println("Available commands:");
        System.out.println("- ADD_MOVIE <id> \"<title>\" \"<genre>\" <year> <rating>");
        System.out.println("- ADD_USER <id> \"<name>\" \"<preferred_genre>\"");
        System.out.println("- SEARCH <user_id> <GENRE|YEAR|RATING|TITLE> \"<value>\"");
        System.out.println("- SEARCH_MULTI <user_id> \"<genre>\" <year> <min_rating>");
        System.out.println("- VIEW_CACHE_STATS");
        System.out.println("- CLEAR_CACHE <L1|L2|ALL>");
        System.out.println("- Type 'exit' to quit\n");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            Command cmd = commandFactory.createCommand(input);
            if (cmd != null) {
                try {
                    cmd.execute();
                } catch (Exception e) {
                    System.out.println("Error executing command: " + e.getMessage());
                }
            }
            System.out.println();
        }

        scanner.close();
        System.out.println("Thank you for using ZipReel!");
    }
}