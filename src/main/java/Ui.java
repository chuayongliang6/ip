import java.util.Scanner;

public class Ui {
    private static final String BORDER = "____________________________________________________________\n";
    private static final String LOGO = """
             ____  __        __ _____
            / ___| \\ \\      / /| ____|
            \\___ \\  \\ \\ /\\ / / |  _|
             ___) |  \\ V  V /  | |___
            |____/    \\_/\\_/   |_____|
            """;
    private static final String WELCOME_MESSAGE = "Hello from\n" + LOGO + BORDER
            + " Hello! I'm SWE\n"
            + " What can I do for you?\uD83D\uDE00\n"
            + BORDER;
    private static final String GOODBYE_MESSAGE = BORDER
            + " Bye. Hope to see you again soon!\n"
            + BORDER;

    private static Scanner scanner = new Scanner(System.in);

    public static String readCommand() {
        return scanner.nextLine(); // Used here to return the string
    }


    public static void printWelcome() {
        System.out.print(WELCOME_MESSAGE);
    }

    public static void printBorder() {
        System.out.print(BORDER);
    }

    public static void printGoodbye() {
        System.out.print(GOODBYE_MESSAGE);
    }

}

