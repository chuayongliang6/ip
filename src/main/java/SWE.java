import java.util.ArrayList;


/**
 * The main class for the SWE task management application.
 * It handles user input and manages a list of tasks.
 */
public class SWE {

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
    private static final String DELETE_COMMAND = "delete";


    /**
     * The entry point of the application. Prints a welcome message, processes user commands, and says goodbye.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Ui.printWelcome();
        processCommand();
        Ui.printGoodbye();
    }


    private static void processCommand() {
        ArrayList<Task> userTasks = new ArrayList<>();
        Storage.loadTasks(userTasks);
        String line = Ui.readCommand();


        while (!line.equals(BYE_COMMAND)) {
            Ui.printBorder();
            handleCommand(line, userTasks);
            Ui.printBorder();
            line = Ui.readCommand();
        }
    }


    private static void handleCommand(String line, ArrayList<Task> userTasks) {
        try {
            if (line.equals(LIST_COMMAND)) {
                Ui.listTasks(userTasks);
            } else if (line.startsWith(MARK_COMMAND)) {
                markTasks(line, userTasks);
                Storage.saveTasks(userTasks);
                // Delete tasks
            } else if (line.startsWith(DELETE_COMMAND)) {
                deleteTasks(line, userTasks);
                Storage.saveTasks(userTasks);
            } else {
                // Adding tasks
                addTasks(userTasks, line);
                Storage.saveTasks(userTasks);
            }
        } catch (SWEException e) {
            System.out.println(e.getMessage());
        }
    }

//    private static void listTasks(ArrayList<Task> userTasks) {
//        System.out.println("Here are the tasks in your list:");
//        for (int i = 0; i < userTasks.size(); i++) {
//            System.out.println((i + 1) + ". " + userTasks.get(i).toString());
//        }
//    }

    private static void markTasks(String line, ArrayList<Task> userTasks) {
        int markIndex = getIndex(line);
        Task taskToMark = userTasks.get(markIndex);
        taskToMark.markAsDone();
        Ui.printMarkTask(taskToMark);
    }

    private static int getIndex(String line) {
        String[] parts = line.split(" ");
        return Integer.parseInt(parts[1]) - 1;
    }

    private static void deleteTasks(String line, ArrayList<Task> userTasks) {
        int deleteIndex = getIndex(line);
        //Deletes the task and assigns deleted task to the deletedTask variable to print the deleted task details
        Task deletedTask = userTasks.remove(deleteIndex);
        Ui.printDeleteTask(deletedTask, userTasks);
    }

    private static void addTasks(ArrayList<Task> userTasks, String line) throws SWEException {
        Task newTask = createTask(line);
        userTasks.add(newTask);
        Ui.printAddTask(newTask, userTasks);
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
