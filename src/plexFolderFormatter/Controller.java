package plexFolderFormatter;

import java.util.Arrays;
import java.util.Scanner;
import com.sun.javaws.exceptions.InvalidArgumentException;
import plexFolderFormatter.pathHandler.*;

public class Controller extends plexFolderFormatter.FormatEngine {

    // --- Constants declaration
    public static int SERIES_MODE = 1;
    public static int MOVIE_MODE = 2;

    // Array of all the valid program modifiers/functions.
    public static String MODIFIERS[] = {
            "help",
            "series",
            "movie",
            "extension",
            "revert"
    };

    // Array of all the valid program flags.
    public static String FLAGS[] = {
            "v",
            "f",
            "r"
    };

    // --- Members
    // *** Declared as protected to allow the engine to see their value.
    protected Path target;            // Target folder structure.
    protected FormatEngine engine;    // Store a link to our slave.
    protected boolean recursiveFlag;  // When set to true, will browse sub-folders as well.
    protected boolean verboseFlag;    // Displays detailed execution information when set to true.
    protected boolean forceFlag;      // Overrides the folder structure detection safe guard when set to true.
    protected int op_mode;            // Can be 0:[NULL], 1:[Series], 2:[Movie]
    protected String extensions[]     // Array of all the extensions to include in the search.
            = {"avi","mp4","mkv","asf","mov","wmv"};

    // --- Constructors

    // Default execution.
    public Controller() {
        // Display information about the program.
        displayHelp();

    }

    // Console arguments constructor
    public Controller(String args[]) throws InvalidArgumentException {
        // Process the arguments given in the console.
        processParameters(args);

        // Create a new instance of the processing engine.
        this.engine = new FormatEngine(this);

    }

    // --- Public Methods

    public void displayHelp() {
        // Output the documentation.
    }

    // help() (void):
    //      Prints to the console a list of available commands, their syntax as well as example uses.
    //      Called when the "--help" modifier is given as parameter.
    public void help() {
        // TODO: Print the documentation.
    }

    // getUserInput(String) (String):
    //      Prompts the user for an input from the console. Displays the string given as a parameter
    //      and then waits until the <CR> key is received.
    public String getUserInput(String message) {
        String input;
        Scanner console;

        console = new Scanner(System.in); // Open a stream from the console.

        System.out.println(message); // Display the prompt.
        input = console.nextLine(); // Grab the user input.

        // Return the input as a String.
        return input;
    }

    // --- Private Methods
    private void processParameters(String args[]) throws IllegalArgumentException {
        int numArgs = args.length;
        String temp;

        // Keep track of which arguments we've processed.
        boolean flag = false;
        boolean target = false;

        // Check if all the given arguments are valid.
        for (int i = 0; i < numArgs; i++) {
            // Check for a modifier "--XXXX"
            if(args[i].toLowerCase().contains("--")) {
                boolean valid = false;
                String mod = "";

                temp = args[i].toLowerCase().substring(2); // Remove '--'.

                // Check if the given modifier is valid.
                for (String validModifier:MODIFIERS) {
                    if(temp.matches( "(" + validModifier + ")(.)*")) {valid = true; mod = validModifier; break;}
                }

                // Throw an exception if we received an invalid argument.
                if(!valid) { throw new IllegalArgumentException(temp); }

                switch(mod) {
                    case "series": // Switch the operation mode to "Series".
                        this.op_mode = SERIES_MODE;
                        break;

                    case "movie": // Switch the operation mode to "Movies".
                        this.op_mode = MOVIE_MODE;
                        break;

                    case "extension": // Override the default extensions.
                        String user_extensions[];

                        // Extract and use the given extensions.
                        user_extensions = temp.split(":")[1].split(",");
                        this.extensions = user_extensions;

                    case "help": // Display the help page.
                        // Help function....................
                        break;

                    case "revert": // Reverts the changes made by this program.
                        // Revert function..........
                        break;

                    default: // Did not match any active modifier; invalid argument given.
                        throw new IllegalArgumentException(temp);
                }
            }
            // Check for a flag "-XXXX"
            else if(args[i].toLowerCase().contains("-") && !flag) {
                flag = true;
                temp = args[i].toLowerCase().substring(1); // Remove '-'.

                // Parse each char.
                int j = 0;
                char current;

                while(j < temp.length()) {
                    current = temp.charAt(j);

                    // Activate the corresponding flag.
                    switch(current) {
                        case 'v': // -v (verbose): Outputs detailed execution information when present.
                            this.verboseFlag = true;
                            break;

                        case 'r': // -r (recursive): Enables the browsing of sub-folders as well.
                            this.recursiveFlag = true;
                            break;

                        case 'f': // -f (force): Disables the folder structure check safeguard that prevents the
                            // execution of the "--movies" modifier on a series library and vice-versa.
                            // WARNING: Executing with this flag can *very* possibly break something and mess up
                            // your existing folder/file naming scheme.
                            this.forceFlag = true;
                            break;

                        default:
                            // The current flag is invalid.
                            throw new IllegalArgumentException(temp.substring(j,j+1));
                    }
                    j++;
                }
            }

            // The param given is the target folder if there's no (- | --) prefix.
            else if(!target){
                target = true;
                this.target = new Path(args[i].toLowerCase());
            }

            // Invalid arguments given.
            else {
                throw new IllegalArgumentException();
            }
        }
    }

    // addElement(String[], String) (String[]):
    //      Adds a given element to a static array. Creates a new array with + 1 size
    //      and returns this new array.
    private String[] addElement(String[] targetArray, String element) {
        String[] resultArray = Arrays.copyOf(targetArray, targetArray.length + 1);
        resultArray[targetArray.length] = element; // Add the element add the end.
        return resultArray;
    }
}
