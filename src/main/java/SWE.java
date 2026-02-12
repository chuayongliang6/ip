import java.util.Scanner;

/**
 * The main class for the SWE task management application.
 * It handles user input and manages a list of tasks.
 */
public class SWE {

    public static final int MAX_TASKS = 100;
    public static final int TODO_PREFIX_LENGTH = 4;
    public static final int DEADLINE_PREFIX_LENGTH = 8;
    public static final int EVENT_PREFIX_LENGTH = 5;
    public static final String DEADLINE_SEPARATOR = "/by";
    public static final String EVENT_SEPARATOR = "\\s*/from\\s*|\\s*/to\\s*";

    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
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
     *
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
            //taksIndex only increments if the handleCommand involves adding tasks
            taskIndex = handleCommand(line, taskIndex, userTasks);
            printBorder();
            line = in.nextLine();
        }
    }

    private static int handleCommand(String line, int taskIndex, Task[] userTasks) {
        try {
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
        } catch (SWEException e) {
            System.out.println(e.getMessage());
            return taskIndex;
        }
    }

    private static int addTasks(Task[] userTasks, int taskIndex, String line) throws SWEException {
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

    private static Task createTask(String input) throws SWEException {
        if (input.startsWith(TODO_COMMAND)) {
            return parseTodo(input);
        } else if (input.startsWith(DEADLINE_COMMAND)) {
            return parseDeadline(input);
        } else if (input.startsWith(EVENT_COMMAND)) {
            return parseEvent(input);
        } else {
            throw new SWEException("I'm sorry, but I don't know what that command means. Please input correct command.");
        }
    }

    private static Todo parseTodo(String input) throws SWEException {
        String description = input.substring(TODO_PREFIX_LENGTH).trim();
        if (description.isEmpty()) {
            throw new SWEException("The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

    private static Deadline parseDeadline(String input) throws SWEException {
        if (!input.contains(DEADLINE_SEPARATOR)) {
            throw new SWEException("A deadline must include '" + DEADLINE_SEPARATOR + "'.");
        }
        String remaining = input.substring(DEADLINE_PREFIX_LENGTH);
        String[] parts = remaining.split(DEADLINE_SEPARATOR);
        //check the description is not empty
        if (parts[0].trim().isEmpty()) {
            throw new SWEException("The description of a deadline cannot be empty.");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new SWEException("The deadline timing cannot be empty.");
        }
        return new Deadline(parts[0], parts[1]);
    }

    private static Event parseEvent(String input) throws SWEException {
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new SWEException("An event must include both /from and /to.");
        }
        String remaining = input.substring(EVENT_PREFIX_LENGTH);
        String[] parts = remaining.split(EVENT_SEPARATOR);
        if (parts.length < 1 || parts[0].trim().isEmpty()) {
            throw new SWEException("The description of an event cannot be empty.");
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new SWEException("The start timing (/from) cannot be empty.");
        }
        if (parts.length < 3 || parts[2].trim().isEmpty()) {
            throw new SWEException("The end timing (/to) cannot be empty.");
        }
        return new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
    }

}
