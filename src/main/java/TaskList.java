import java.util.ArrayList;

public class TaskList {
    private static int getIndex(String line) {
        String[] parts = line.split(" ");
        return Integer.parseInt(parts[1]) - 1;
    }

    public static void markTasks(String line, ArrayList<Task> userTasks) {
        int markIndex = getIndex(line);
        Task taskToMark = userTasks.get(markIndex);
        taskToMark.markAsDone();
        Ui.printMarkTask(taskToMark);
    }

    public static void addTasks(ArrayList<Task> userTasks, String line) throws SWEException {
        Task newTask = Parser.createTask(line);
        userTasks.add(newTask);
        Ui.printAddTask(newTask, userTasks);
    }

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
