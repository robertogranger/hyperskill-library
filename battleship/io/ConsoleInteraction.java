package battleship.io;

import java.util.Scanner;

public class ConsoleInteraction {
    private static final Scanner scanner = new Scanner(System.in);

    public static void print(String output) {
        System.out.println(output);
    }

    public static void printEmptyLine() {
        System.out.println();
    }

    public static void waitForEnter(String message) {
        System.out.println(message);
        scanner.nextLine();
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static String getInput() {
        return scanner.nextLine();
    }
}
