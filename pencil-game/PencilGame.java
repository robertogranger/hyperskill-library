import java.util.Random;
import java.util.Scanner;

public class Main {
    enum Players {
        JACK("Jack"), JOHN("John");

        private final String displayName;

        Players(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GamePrinter.askPencilAmount(scanner);
        GamePrinter.askPlayerTurn(scanner);

        do {
            PencilGame.printPencilList();
            PencilGame.printCurrentTurn();
            PencilGame.updatePencilCount(scanner);
        } while (PencilGame.pencilCount > 0);
    }

    public static class GamePrinter {
        static void askPencilAmount(Scanner scanner) {
            System.out.print("How many pencils would you like to use: ");
            PencilGame.setPencilCount(scanner);
        }

        static void askPlayerTurn(Scanner scanner) {
            System.out.print("Who will be the first (John, Jack): ");

            while (true) {
                String input = scanner.nextLine().trim();

                try {
                    Players player = Players.valueOf(input.toUpperCase());
                    PencilGame.currentPlayerIndex = player.ordinal();
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Choose between 'John' and 'Jack'");
                }
            }
        }
    }

    public static class PencilGame {
        public static int pencilCount;
        public static int currentPlayerIndex;
        private static final Players[] players = Players.values();
        private static final Random random = new Random();

        static void printPencilList() {
            System.out.println("|".repeat(pencilCount));
        }

        static void printCurrentTurn() {
            String punctuation = isBotTurn() ? ":" : "!";
            System.out.printf("%s's turn%s%n", players[currentPlayerIndex].getDisplayName(), punctuation);
        }

        static void updatePlayerTurn() {
            currentPlayerIndex = currentPlayerIndex == 0 ? 1 : 0;
        }

        static void setPencilCount(Scanner scanner) {
            pencilCount = getValidPencilCount(scanner);
        }

        static void updatePencilCount(Scanner scanner) {
            int userInput = isBotTurn() ? getBotMove() : getValidTurnCount(scanner);

            if (userInput == pencilCount) {
                Players winner = players[(currentPlayerIndex + 1) % players.length];
                System.out.printf("%s won!", winner.getDisplayName());
            }

            pencilCount -= userInput;
            updatePlayerTurn();
        }

        private static boolean isBotTurn() {
            return players[currentPlayerIndex] == Players.JACK;
        }

        private static int getBotMove() {
            int move = calculateBotMove();
            System.out.println(move);
            return move;
        }

        private static int calculateBotMove() {
            int remainder = pencilCount % 4;

            if (remainder == 1) {
                return pencilCount == 1 ? 1 : random.nextInt(3) + 1;
            }

            return remainder == 0 ? 3 : remainder - 1;
        }

        private static int getValidPencilCount(Scanner scanner) {
            while (true) {
                String line = scanner.nextLine().trim();

                try {
                    int userInput = Integer.parseInt(line);

                    if (userInput <= 0) {
                        System.out.println("The number of pencils should be positive");
                        continue;
                    }

                    return userInput;
                } catch (NumberFormatException e) {
                    System.out.println("The number of pencils should be numeric");
                }
            }
        }

        private static int getValidTurnCount(Scanner scanner) {
            while (true) {
                String line = scanner.nextLine().trim();

                try {
                    int userInput = Integer.parseInt(line);

                    if (userInput < 1 || userInput > 3) {
                        System.out.println("Possible values: '1', '2', '3'");
                        continue;
                    }

                    if (userInput > pencilCount) {
                        System.out.println("Too many pencils were taken");
                        continue;
                    }

                    return userInput;
                } catch (NumberFormatException e) {
                    System.out.println("Possible values: '1', '2', '3'");
                }
            }
        }
    }
}