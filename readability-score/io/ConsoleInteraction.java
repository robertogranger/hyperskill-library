package readability.io;

import java.util.Scanner;

public class ConsoleInteraction {
    private final static Scanner scanner = new Scanner(System.in);

    public static void print(String message) {
        System.out.println(message);
    }
    public static void printEmptyLine() { System.out.println(); }
    public static String getLine() { return scanner.nextLine(); }
}
