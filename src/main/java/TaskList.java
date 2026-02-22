import java.util.ArrayList;

/**
 * The TaskList class provides static methods to manage tasks in the task list, including marking tasks as done,
 * adding new tasks, deleting tasks, and finding tasks based on a keyword. It interacts with the user interface
 * to display appropriate messages and updates the task list accordingly.
 */

public class TaskList {
    private static int getIndex(String line) {
        String[] parts = line.split(" ");
        return Integer.parseInt(parts[1]) - 1;
    }

    /**
     * Marks a task as done based on the user input command. It extracts the index of the task to be marked,
     * updates the task's status, and prints a confirmation message to the user.
     *
     * @param line      the user input command containing the index of the task to be marked
     * @param userTasks the list of user tasks from which the task will be marked as
     */
    public static void markTasks(String line, ArrayList<Task> userTasks) {
        int markIndex = getIndex(line);
        Task taskToMark = userTasks.get(markIndex);
        taskToMark.markAsDone();
        Ui.printMarkTask(taskToMark);
    }

    /**
     * Adds a new task to the user's task list based on the user input command.
     *
     * @param userTasks the list of user tasks to which the new task will be added.
     * @param line      the user input command containing the details of the task to be added.
     *
     */
    public static void addTasks(ArrayList<Task> userTasks, String line) throws SWEException {
        Task newTask = Parser.createTask(line);
        userTasks.add(newTask);
        Ui.printAddTask(newTask, userTasks);
    }

    /**
     * Deletes a task from the user's task list based on the user input command.
     *
     * @param line      the user input command containing the index of the task to be deleted.
     * @param userTasks the list of user tasks from which the task will be deleted.
     */
    public static void deleteTasks(String line, ArrayList<Task> userTasks) {
        int deleteIndex = getIndex(line);
        //Deletes the task and assigns deleted task to the deletedTask variable to print the deleted task details
        Task deletedTask = userTasks.remove(deleteIndex);
        Ui.printDeleteTask(deletedTask, userTasks);
    }

    public static void findTasks(String line, ArrayList<Task> userTasks) throws SWEException {
        String keyword = Parser.parseFind(line);
        Ui.printFindTaskHeader();
        int count = 0;
        for (Task task : userTasks) {
            if (task.description.contains(keyword)) {
                count += 1;
                Ui.printFindTaskLoop(task, userTasks, count);
            }
        }
    }
}
