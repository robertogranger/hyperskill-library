package numbers.io;

import java.util.Scanner;

public class ConsoleInteraction {
    private final static Scanner scanner = new Scanner(System.in);

    public static void print(String message) {
        System.out.println(message);
    }

    public static void printEmptyLine() {
        System.out.println();
    }

    public static void printPrompt(String message) {
        System.out.print(message);
    }

    public static String getLine() { return scanner.nextLine(); }

    public static long getLong() {
        return scanner.nextLong();
    }

    public static boolean hasLong(String number) {
        return new Scanner(number).hasNextLong();
    }

    public static void skipToken() {
        scanner.next();
    }
}
