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
        Task newTask = Parser.createTask(line);
        userTasks.add(newTask);
        Ui.printAddTask(newTask, userTasks);
    }


}
