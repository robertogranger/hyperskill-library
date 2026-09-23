package honestcalculator.io;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInteraction {
    private static final Scanner scanner = new Scanner(System.in);

    private ConsoleInteraction() {}

    public static String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public static boolean wantsToContinue(String message) throws IllegalArgumentException {
        String userInput = prompt(message);

        if (userInput.equals("y")) {
            return true;
        } else if (userInput.equals("n")){
            return false;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static void print(String message) {
        System.out.println(message);
    }
}
