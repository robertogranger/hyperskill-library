package chucknorris.io;

import java.util.Scanner;

public class ConsoleInteraction {
    private static final Scanner scanner = new Scanner(System.in);

    public static String askOperation() {
        System.out.println("Please input operation (encode/decode/exit):");
        return scanner.nextLine();
    }

    public static String askInputString() {
        System.out.println("Input string:");
        return scanner.nextLine();
    }

    public static String askEncodedInput() {
        System.out.println("Input encoded string:");
        return scanner.nextLine();
    }

    public static void printEncoded(String result) {
        System.out.println("Encoded string:");
        System.out.println(result);
    }

    public static void printDecoded(String result) {
        System.out.println("Decoded string:");
        System.out.println(result);
    }

    public static void printError(String message) {
        System.out.println(message);
    }

    public static void printUnknownOperation(String operation) {
        System.out.printf("There is no '%s' operation%n", operation);
    }

    public static void printGoodbye() {
        System.out.println("Bye!");
    }
}