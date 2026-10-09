package bullscows;

import bullscows.io.ConsoleInteraction;

public class Main {
    public static void main(String[] args) {
        ConsoleInteraction.print("Input the length of the secret code:");
        String lengthInput = ConsoleInteraction.getInput();

        if (!isNumber(lengthInput)) {
            ConsoleInteraction.print(notANumberMessage(lengthInput));
            return;
        }

        ConsoleInteraction.print("Input the number of possible symbols in the code:");
        String symbolsInput = ConsoleInteraction.getInput();

        if (!isNumber(symbolsInput)) {
            ConsoleInteraction.print(notANumberMessage(symbolsInput));
            return;
        }

        int secretLength = Integer.parseInt(lengthInput);
        int symbolCount = Integer.parseInt(symbolsInput);

        String setupError = Game.validate(secretLength, symbolCount);

        if (setupError != null) {
            ConsoleInteraction.print(setupError);
            return;
        }

        Game game = new Game(secretLength, symbolCount);

        ConsoleInteraction.print("The secret is prepared: " + "*".repeat(secretLength)
                + " (" + game.getSymbolRange() + ").");
        ConsoleInteraction.print("Okay, let's start a game!");

        while (true) {
            ConsoleInteraction.print("Turn " + game.getTurn() + ":");
            String guess = ConsoleInteraction.getInput();

            String guessError = game.validateGuess(guess);

            if (guessError != null) {
                ConsoleInteraction.print(guessError);
                return;
            }

            Grade grade = game.play(guess);
            ConsoleInteraction.print(grade.format());

            if (game.isFinished(grade)) {
                ConsoleInteraction.print("Congratulations! You guessed the secret code.");
                break;
            }
        }
    }

    private static boolean isNumber(String text) {
        return text.matches("\\d{1,9}");
    }

    private static String notANumberMessage(String text) {
        return "Error: \"" + text + "\" isn't a valid number.";
    }
}