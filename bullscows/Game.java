package bullscows;

import java.util.Random;

public class Game {
    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_SYMBOLS = SYMBOLS.length();

    private final char[] secret;
    private final int symbolCount;
    private int turn = 1;

    public Game(int secretLength, int symbolCount) {
        String error = validate(secretLength, symbolCount);

        if (error != null) {
            throw new IllegalArgumentException(error);
        }

        this.symbolCount = symbolCount;
        this.secret = generateSecret(secretLength, symbolCount);
    }

    private char[] generateSecret(int secretLength, int symbolCount) {
        Random random = new Random();
        StringBuilder candidate = new StringBuilder();

        while (candidate.length() < secretLength) {
            char symbol = SYMBOLS.charAt(random.nextInt(symbolCount));

            if (candidate.indexOf(String.valueOf(symbol)) == -1) {
                candidate.append(symbol);
            }
        }

        return candidate.toString().toCharArray();
    }

    public String validateGuess(String guess) {
        String message = "Error: \"" + guess + "\" isn't a valid guess. Enter "
                + secret.length + " symbols from " + getSymbolRange() + ".";

        if (guess.length() != secret.length) {
            return message;
        }

        String allowed = SYMBOLS.substring(0, symbolCount);

        for (char symbol : guess.toCharArray()) {
            if (allowed.indexOf(symbol) == -1) {
                return message;
            }
        }

        return null;
    }

    private Grade takeGuess(String guess) {
        char[] guessCharacters = guess.toCharArray();

        int bullQuantity = 0;
        int cowQuantity = 0;

        for (int i = 0; i < secret.length; i++) {
            for (int j = 0; j < secret.length; j++) {
                boolean isMatch = secret[i] == guessCharacters[j];

                if (isMatch) {
                    boolean hasSameIndex = i == j;

                    if (hasSameIndex) {
                        bullQuantity++;
                    } else {
                        cowQuantity++;
                    }
                }
            }
        }

        return new Grade(bullQuantity, cowQuantity);
    }

    public Grade play(String guess) {
        validateGuess(guess);
        Grade grade = takeGuess(guess);
        increaseTurn();

        return grade;
    }

    public String getSymbolRange() {
        char last = SYMBOLS.charAt(symbolCount - 1);

        if (symbolCount <= 10) {
            return "0-" + last;
        }

        return symbolCount == 11 ? "0-9, a" : "0-9, a-" + last;
    }

    public static String validate(int secretLength, int symbolCount) {
        if (symbolCount > MAX_SYMBOLS) {
            return "Error: maximum number of possible symbols in the code is "
                    + MAX_SYMBOLS + " (0-9, a-z).";
        }

        if (secretLength <= 0) {
            return "Error: can't generate a secret code with a length of " + secretLength + ".";
        }

        if (symbolCount < secretLength) {
            return "Error: it's not possible to generate a code with a length of " + secretLength
                    + " with " + symbolCount + " unique symbols.";
        }

        return null;
    }

    private void increaseTurn() {
        turn++;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isFinished(Grade grade) {
        return grade.bulls() == secret.length;
    }
}
