import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Game game = new Game();
        game.printGameState();

        char currentPlayer = 'X';
        while (game.getGameState().equals("Game not finished")) {
            ConsoleInteraction.askMove(game, currentPlayer);
            game.printGameState();
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        System.out.println(game.getGameState());
    }

    public static class Game {
        private final char[][] gameGrid = new char[3][3];

        private Game() {
            for (char[] row : gameGrid) {
                java.util.Arrays.fill(row, '_');
            }
        }

        private void printGameState() {
            System.out.println("---------");
            for (char[] rows : gameGrid) {
                System.out.print("|");
                for (char item : rows) {
                    System.out.printf(" %s", item);
                }
                System.out.print(" |");
                System.out.println();
            }
            System.out.println("---------");
        }

        private boolean isCellOccupied(int row, int column) {
            return gameGrid[row][column] != '_';
        }

        private void placeMove(int row, int column, char symbol) {
            gameGrid[row][column] = symbol;
        }

        private boolean isWinningLine(char a, char b, char c) {
            return a == b && b == c && a != '_';
        }

        private String getGameState() {
            boolean xWins = false;
            boolean oWins = false;
            int emptyCount = 0;

            for (int i = 0; i < 3; i++) {
                if (isWinningLine(gameGrid[i][0], gameGrid[i][1], gameGrid[i][2])) {
                    if (gameGrid[i][0] == 'X') xWins = true;
                    else oWins = true;
                }
                if (isWinningLine(gameGrid[0][i], gameGrid[1][i], gameGrid[2][i])) {
                    if (gameGrid[0][i] == 'X') xWins = true;
                    else oWins = true;
                }
                for (int j = 0; j < 3; j++) {
                    if (gameGrid[i][j] == '_') emptyCount++;
                }
            }

            if (isWinningLine(gameGrid[0][0], gameGrid[1][1], gameGrid[2][2])) {
                if (gameGrid[0][0] == 'X') xWins = true;
                else oWins = true;
            }
            if (isWinningLine(gameGrid[0][2], gameGrid[1][1], gameGrid[2][0])) {
                if (gameGrid[0][2] == 'X') xWins = true;
                else oWins = true;
            }

            if (xWins) return "X wins";
            if (oWins) return "O wins";
            if (emptyCount == 0) return "Draw";
            return "Game not finished";
        }
    }

    public static class ConsoleInteraction {
        private static final Scanner scanner = new Scanner(System.in);

        private static void askMove(Game game, char currentPlayer) {
            while (true) {
                System.out.print("Enter the coordinates: ");
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.trim().split("\\s+");

                int row;
                int column;
                try {
                    row = Integer.parseInt(parts[0]);
                    column = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("You should enter numbers!");
                    continue;
                }

                if (row < 1 || row > 3 || column < 1 || column > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }

                int gridRow = row - 1;
                int gridColumn = column - 1;

                if (game.isCellOccupied(gridRow, gridColumn)) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                game.placeMove(gridRow, gridColumn, currentPlayer);
                break;
            }
        }
    }
}