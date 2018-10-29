package plexFolderFormatter;

import java.util.Scanner;

class FormatEngine {
    // --- Constants declaration


    // --- Members
    private Controller ctrl;        // Link to the controller of this engine. Sends the commands to complete.

    private String target;          // Target folder.

    // --- Constructor

    // Default constructor
    public FormatEngine(){}

    // Program constructor
    protected FormatEngine(Controller ctrl) {
        // Save a link to our master.
        this.ctrl = ctrl;
    }

    // --- Protected Methods
    // *** All the methods of the engine are package protected. Commands must come directly from the controller.

    // getUserInput(String) (String):
    //      Prompts the user for an input from the console. Displays the string given as a parameter
    //      and then waits until the <CR> key is received.
    protected String getUserInput(String message) {
        String input;
        Scanner console;

        // Open a stream from the console.
        console = new Scanner(System.in);

        System.out.println(message); // Display the prompt.
        input = console.nextLine(); // Grab the user input.

        // Return the input as a String.
        return input;
    }

    // --- Private Methods
}
