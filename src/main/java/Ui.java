import java.util.ArrayList;
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

    public static void listTasks(ArrayList<Task> userTasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < userTasks.size(); i++) {
            System.out.println((i + 1) + ". " + userTasks.get(i).toString());
        }
    }

    public static void printMarkTask(Task taskToMark) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(taskToMark.toString());
    }

    public static void printDeleteTask(Task deletedTask, ArrayList<Task> userTasks) {
        System.out.println("Noted. I've removed this task: ");
        System.out.println("  " + deletedTask.toString());
        System.out.println("Now you have " + userTasks.size() + " tasks in the list.");
    }
}

