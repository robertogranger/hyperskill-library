package chucknorris;

import chucknorris.cipher.DecodeOperation;
import chucknorris.cipher.EncodeOperation;
import chucknorris.cipher.InvalidCipherException;
import chucknorris.io.ConsoleInteraction;

public class Main {
    public static void main(String[] args) {
        while (true) {
            String operation = ConsoleInteraction.askOperation();

            switch (operation) {
                case "encode" -> {
                    String input = ConsoleInteraction.askInputString();
                    try {
                        String result = new EncodeOperation().process(input);
                        ConsoleInteraction.printEncoded(result);
                    } catch (InvalidCipherException e) {
                        ConsoleInteraction.printError(e.getMessage());
                    }
                }
                case "decode" -> {
                    String input = ConsoleInteraction.askEncodedInput();
                    try {
                        String result = new DecodeOperation().process(input);
                        ConsoleInteraction.printDecoded(result);
                    } catch (InvalidCipherException e) {
                        ConsoleInteraction.printError(e.getMessage());
                    }
                }
                case "exit" -> {
                    ConsoleInteraction.printGoodbye();
                    return;
                }
                default -> ConsoleInteraction.printUnknownOperation(operation);
            }
        }
    }
}