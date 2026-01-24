import java.util.Scanner;

public class SWE {
    public static void main(String[] args) {
        //gen AI was used to generate the logo
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
        String line;
        Scanner in = new Scanner(System.in);
        line = in.nextLine();
        while (!line.equals("bye")) {
            System.out.print("____________________________________________________________\n" +
                    line + "\n"+
                    "____________________________________________________________\n");
            line = in.nextLine();
        }
        System.out.print("____________________________________________________________\n" +
                        " Bye. Hope to see you again soon!\n" +
                "____________________________________________________________\n");
    }
}
