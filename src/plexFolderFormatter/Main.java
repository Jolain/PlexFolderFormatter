package plexFolderFormatter;

import com.sun.javaws.exceptions.*;

public class Main extends plexFolderFormatter.Controller {
    public static void main(String args[]) {
        FormatEngine inst;

        // Catch programs exceptions.
        try{
            inst = new Controller(args);
        } catch(InvalidArgumentException e) {
            System.out.println("ERROR [1]");
            System.out.println("You provided too many arguments!");
        } catch(IllegalArgumentException e) {
            String prefix;

            if (e.getMessage().length() <= 1) {prefix = "flag \"-";}
            else {prefix = "modifier \"--";}
            System.out.println("ERROR [2]");
            System.out.println("Invalid " + prefix + e.getMessage() + "\" given. Check the spelling of your arguments or type \"plex_format_util --help\"");
            System.out.println("to see the list of available commands.");
        }

        // End of the program.
    }
}
