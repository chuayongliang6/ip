import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Storage class is responsible for loading tasks from a file and saving tasks to a file.
 * It provides methods to read tasks from a text file, convert them back into Task objects, and write the current list
 * of tasks back to the file in a specific format.
 *
 */
public class Storage {
    private static final String FILE_PATH = "./data/duke.txt";

    /**
     * Loads tasks from the specified file path and adds them to the provided list of user tasks.
     *
     * @param userTasks the list of tasks to which the loaded tasks will be added
     * @throws IOException if there is an error reading the file or creating the file/directory
     */
    public static void loadTasks(ArrayList<Task> userTasks) {
        try {
            File f = new File(FILE_PATH);
            //Creates directory if it does not exist
            if (f.getParentFile() != null && !f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            //Create file if it does not exist
            if (!f.exists()) {
                f.createNewFile(); // This physically creates the empty duke.txt file
            }
            Scanner s = new Scanner(f);
            //read lines from text file
            //while loop means as long as there is more data to be read, ie as long as there are more lines in the text file, keep reading and converting to Task objects
            while (s.hasNext()) {
                //Reads one full line from the text file
                String line = s.nextLine();
                Task t = parseFileString(line); // Convert text back to object
                if (t != null) {
                    userTasks.add(t);
                }
            }
            //includes errors for CreateNewFile and scanner which both involve IOException
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
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


    public static void saveTasks(ArrayList<Task> userTasks) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            String textToAdd = "";

            // Convert all tasks to string format
            for (Task task : userTasks) {
                textToAdd += taskToFileFormat(task) + System.lineSeparator();
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

}
