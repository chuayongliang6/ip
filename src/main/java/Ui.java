import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the user interface that provides methods for reading user input and printing various messages.
 */
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

    /**
     * Reads a line of user input from the console and returns it as a string.
     *
     * @return the user input as a string
     */
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

    /**
     * Prints the list of tasks in the user's task list, with each task numbered and displayed in a readable format.
     *
     * @param userTasks the list of tasks to be printed
     */
    public static void listTasks(ArrayList<Task> userTasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < userTasks.size(); i++) {
            System.out.println((i + 1) + ". " + userTasks.get(i).toString());
        }
    }

    /**
     * Prints a message confirming that a task has been marked as done, along with the details of the marked task.
     *
     * @param taskToMark the task that has been marked as done
     *
     */
    public static void printMarkTask(Task taskToMark) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(taskToMark.toString());
    }


    /**
     * Prints a message confirming that a task has been deleted, along with the details of the deleted task and the updated count of tasks in the user's task list.
     *
     * @param deletedTask the task that has been deleted
     * @param userTasks   the list of tasks remaining after deletion, used to display the
     *
     */
    public static void printDeleteTask(Task deletedTask, ArrayList<Task> userTasks) {
        System.out.println("Noted. I've removed this task: ");
        System.out.println("  " + deletedTask.toString());
        System.out.println("Now you have " + userTasks.size() + " tasks in the list.");
    }

    /**
     * Prints a message confirming that a new task has been added, along with the details of the new task and the updated count of tasks in the user's task list.
     *
     * @param newTask   the task that has been added
     * @param userTasks the list of tasks including the newly added task, used to display the updated count of tasks in the list
     */
    public static void printAddTask(Task newTask, ArrayList<Task> userTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + newTask.toString());
        System.out.println("Now you have " + userTasks.size() + " tasks in the list.");
    }

    public static void printFindTaskHeader() {
        System.out.println("Here are the matching tasks in your list:");
    }

    public static void printFindTaskLoop(Task foundTask, ArrayList<Task> userTasks, int count) {
        System.out.println((count) + ". " + foundTask.toString());
    }
}

