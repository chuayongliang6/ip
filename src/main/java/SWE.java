import java.util.Scanner;

public class SWE {
    public static void main(String[] args) {
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
        Task[] userTasks = new Task[100];
        int taskIndex = 0;
        String line;
        Scanner in = new Scanner(System.in);
        line = in.nextLine();
        while (!line.equals("bye")) {
            System.out.print("____________________________________________________________\n");
            // Listing tasks
            if (line.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskIndex; i++) {
                    System.out.println((i + 1) + ". " + userTasks[i].toString());
                }
            //Marking tasks
            } else if (line.startsWith("mark ")) {
                String[] markNumber = line.split(" ");
                // Minus 1 because if you mark task 2, it is at index 1
                int markIndex = Integer.parseInt(markNumber[1]) - 1;
                userTasks[markIndex].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(userTasks[markIndex].toString());
            //Adding tasks
            } else {
                userTasks[taskIndex] = new Task(line);
                taskIndex++;
                System.out.println("added: " + line);
            }
            System.out.print("____________________________________________________________\n");
            line = in.nextLine();
        }
        System.out.print("____________________________________________________________\n" +
                " Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n");
    }
}
