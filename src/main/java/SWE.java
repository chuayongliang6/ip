import java.util.Scanner;

/**
 * The main class for the SWE task management application.
 * It handles user input and manages a list of tasks.
 */
public class SWE {

    public static final int MAX_TASKS = 100;
    public static final int TODO_PREFIX_LENGTH = 5;
    public static final int DEADLINE_PREFIX_LENGTH = 9;
    public static final int EVENT_PREFIX_LENGTH = 6;
    public static final String DEADLINE_SEPARATOR = " /by ";
    public static final String EVENT_SEPARATOR = " /from | /to ";

    private static final String TODO_COMMAND = "todo ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark ";
    private static final String BYE_COMMAND = "bye";
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

    /**
     * The entry point of the application. Prints a welcome message, processes user commands, and says goodbye.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        printWelcome();
        processCommand();
        printGoodbye();
    }

    private static void printGoodbye() {
        System.out.print(GOODBYE_MESSAGE);
    }

    private static void printBorder() {
        System.out.print(BORDER);
    }

    private static void processCommand() {
        Task[] userTasks = new Task[MAX_TASKS];
        int taskIndex = 0;

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        while (!line.equals(BYE_COMMAND)) {
            printBorder();
            taskIndex = handleCommand(line, taskIndex, userTasks);
            printBorder();
            line = in.nextLine();
        }
    }

    private static int handleCommand(String line, int taskIndex, Task[] userTasks) {
        if (line.equals(LIST_COMMAND)) {
            listTasks(taskIndex, userTasks);
            return taskIndex;
        }

        if (line.startsWith(MARK_COMMAND)) {
            markTasks(line, userTasks);
            return taskIndex;
        }

        // Adding tasks
        return addTasks(userTasks, taskIndex, line);
    }

    private static int addTasks(Task[] userTasks, int taskIndex, String line) {
        userTasks[taskIndex] = createTask(line);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + userTasks[taskIndex].toString());
        taskIndex++;
        System.out.println("Now you have " + taskIndex + " tasks in the list.");
        return taskIndex;
    }

    private static void markTasks(String line, Task[] userTasks) {
        int markIndex = getMarkIndex(line);
        userTasks[markIndex].markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(userTasks[markIndex].toString());
    }

    private static int getMarkIndex(String line) {
        String[] parts = line.split(" ");
        return Integer.parseInt(parts[1]) - 1;
    }

    private static void listTasks(int taskIndex, Task[] userTasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskIndex; i++) {
            System.out.println((i + 1) + ". " + userTasks[i].toString());
        }
    }

    private static void printWelcome() {
        System.out.print(WELCOME_MESSAGE);
    }

    private static Task createTask(String input) {
        if (input.startsWith(TODO_COMMAND)) {
            return parseTodo(input);
        } else if (input.startsWith(DEADLINE_COMMAND)) {
            return parseDeadline(input);
        } else {
            return parseEvent(input);
        }
    }

    private static Todo parseTodo(String input) {
        return new Todo(input.substring(TODO_PREFIX_LENGTH));
    }

    private static Deadline parseDeadline(String input) {
        String remaining = input.substring(DEADLINE_PREFIX_LENGTH);
        String[] parts = remaining.split(DEADLINE_SEPARATOR);
        return new Deadline(parts[0], parts[1]);
    }

    private static Event parseEvent(String input) {
        String remaining = input.substring(EVENT_PREFIX_LENGTH);
        String[] parts = remaining.split(EVENT_SEPARATOR);
        return new Event(parts[0], parts[1], parts[2]);
    }

}
