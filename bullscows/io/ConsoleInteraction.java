package bullscows.io;

import java.util.Scanner;

public class ConsoleInteraction {
    private static final Scanner scanner = new Scanner(System.in);

    public static void print(String message) {
        System.out.println(message);
    }

    public static String getInput() {
        return scanner.nextLine();
    }

    public static int getInt() {
        return Integer.parseInt(scanner.nextLine());
    }
}
