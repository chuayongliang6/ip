import java.util.Scanner;

public class SWE {

    public static final int MAX_TASKS = 100;
    public static final int TODO_PREFIX_LENGTH = 5;
    public static final int DEADLINE_PREFIX_LENGTH = 9;
    public static final int EVENT_PREFIX_LENGTH = 6;

    public static void main(String[] args) {
        printWelcome();
        processCommand();
        printGoodbye();
    }

    private static void printGoodbye() {
        System.out.print("____________________________________________________________\n" +
                " Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n");
    }

    private static void processCommand() {
        Task[] userTasks = new Task[MAX_TASKS];
        int taskIndex = 0;
        String line;
        Scanner in = new Scanner(System.in);
        line = in.nextLine();
        while (!line.equals("bye")) {
            System.out.print("____________________________________________________________\n");
            taskIndex = handleCommand(line, taskIndex, userTasks);
            System.out.print("____________________________________________________________\n");
            line = in.nextLine();
        }
    }

    private static int handleCommand(String line, int taskIndex, Task[] userTasks) {
        // Listing tasks
        if (line.equals("list")) {
            listTasks(taskIndex, userTasks);
            //Marking tasks
        } else if (line.startsWith("mark ")) {
            markTasks(line, userTasks);
            //Adding tasks
        } else {
            taskIndex = addTasks(userTasks, taskIndex, line);
        }
        return taskIndex;
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
        String[] markNumber = line.split(" ");
        // Minus 1 because if you mark task 2, it is at index 1
        int markIndex = Integer.parseInt(markNumber[1]) - 1;
        userTasks[markIndex].markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(userTasks[markIndex].toString());
    }

    private static void listTasks(int taskIndex, Task[] userTasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskIndex; i++) {
            System.out.println((i + 1) + ". " + userTasks[i].toString());
        }
    }

    private static void printWelcome() {
        //Gen AI was used to generate the logo
        String logo = " ____  __        __ _____\n"
                + "/ ___| \\ \\      / /| ____|\n"
                + "\\___ \\  \\ \\ /\\ / / |  _|  \n"
                + " ___) |  \\ V  V /  | |___ \n"
                + "|____/    \\_/\\_/   |_____|\n";
        System.out.println("Hello from\n" + logo);
        System.out.print("____________________________________________________________\n" +
                " Hello! I'm SWE\n" +
                " What can I do for you?\uD83D\uDE00\n" +
                "____________________________________________________________\n");
    }

    private static Task createTask(String input) {
        if (input.startsWith("todo ")) {
            return new Todo(input.substring(TODO_PREFIX_LENGTH));
        } else if (input.startsWith("deadline ")) {
            //Splits the remaining string (removed the command deadline) into the task and the deadline
            String[] deadlineParts = input.substring(DEADLINE_PREFIX_LENGTH).split(" /by ");
            return new Deadline(deadlineParts[0], deadlineParts[1]);
        } else {
            String[] eventParts = input.substring(EVENT_PREFIX_LENGTH).split(" /from | /to ");
            return new Event(eventParts[0], eventParts[1], eventParts[2]);
        }
    }

}
