import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private static final String FILE_PATH = "./data/duke.txt";
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

    private static int loadTasks(Task[] userTasks) {
        int count = 0;
        try {
            File f = new File(FILE_PATH);
            //Creates directory if it does not exist
            if (f.getParentFile() != null && !f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            //Create file if it does not exist
            if (!f.exists()) {
                f.createNewFile(); // This physically creates the empty duke.txt file
                return 0; // Return 0 because the new file is obviously empty
            }
            Scanner s = new Scanner(f);
            //while loop means as long as there is more data to be read, ie as long as there are more lines in the text file, keep reading and converting to Task objects
            while (s.hasNext()) {
                //Reads one full line from the text file
                String line = s.nextLine();
                Task t = parseFileString(line); // Convert text back to object
                if (t != null) {
                    userTasks[count] = t;
                    count++;
                }
            }
            //includes errors for CreateNewFile and scanner which both involve IOException
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return count;
    }

    private static Task parseFileString(String line) {
        String[] parts = line.split("\\|");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            //parts[3] is deadline
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            //parts[3] is from, parts[4] is to
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            return null;
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }


    private static void saveTasks(Task[] userTasks, int taskIndex) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            String textToAdd = "";

            // Convert all tasks to string format
            for (int i = 0; i < taskIndex; i++) {
                textToAdd += taskToFileFormat(userTasks[i]) + System.lineSeparator();
            }

            fw.write(textToAdd);
            fw.close();

        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    private static String taskToFileFormat(Task task) {
        String type;
        String additional = "";
        if (task instanceof Todo) {
            type = "T";
        } else if (task instanceof Deadline) {
            type = "D";
            additional = "|" + ((Deadline) task).by;
        } else if (task instanceof Event) {
            type = "E";
            additional = "|" + ((Event) task).from + "|" + ((Event) task).to;
        } else {
            return "";
        }
        return type + "|" + (task.isDone ? "1" : "0") + "|" + task.description + additional;
    }

    private static void processCommand() {
        Task[] userTasks = new Task[MAX_TASKS];
        int taskIndex = loadTasks(userTasks);

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        while (!line.equals(BYE_COMMAND)) {
            printBorder();
            //taskIndex only increments if the handleCommand involves adding tasks
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
                saveTasks(userTasks, taskIndex);
                return taskIndex;
            }
            // Adding tasks
            int newIndex = addTasks(userTasks, taskIndex, line);
            saveTasks(userTasks, newIndex);
            return newIndex;
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
