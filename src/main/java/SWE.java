import java.util.ArrayList;


/**
 * The main class for the SWE task management application.
 * It handles user input and manages a list of tasks.
 */
public class SWE {
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark ";
    private static final String BYE_COMMAND = "bye";
    private static final String DELETE_COMMAND = "delete";
    private static final String FIND_COMMAND = "find";

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
                TaskList.markTasks(line, userTasks);
                Storage.saveTasks(userTasks);
                // Delete tasks
            } else if (line.startsWith(DELETE_COMMAND)) {
                TaskList.deleteTasks(line, userTasks);
                Storage.saveTasks(userTasks);
            } else if (line.startsWith(FIND_COMMAND)) {
                TaskList.findTasks(line, userTasks);
            } else {
                // Adding tasks
                TaskList.addTasks(userTasks, line);
                Storage.saveTasks(userTasks);
            }
        } catch (SWEException e) {
            System.out.println(e.getMessage());
        }
    }
}