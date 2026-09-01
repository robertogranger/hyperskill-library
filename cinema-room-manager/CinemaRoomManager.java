import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        CinemaRoom cinemaRoom = CinemaRoom.createCinemaRoom();
        ConsoleInteraction.showMenu(cinemaRoom);
    }

    public static class ConsoleInteraction {
        private static final  Scanner scanner = new Scanner(System.in);

        private static void showMenu(CinemaRoom cinemaRoom) {
            String menuMessage = """
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit""";

            menuLoop:
            while (true) {
                System.out.printf("%n%s%n", menuMessage);

                if (scanner.hasNextInt()) {
                    int userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1: {
                            cinemaRoom.printSeatGrid();
                            continue;
                        }
                        case 2: {
                            boolean isValid = false;
                            while (!isValid) {
                                isValid = cinemaRoom.selectSeat();
                            }
                            continue;
                        }
                        case 3: {
                            cinemaRoom.printStatistics();
                            continue;
                        }
                        case 0: {
                            break menuLoop;
                        }
                    }
                } else {
                    System.out.printf("%nWrong input!%n");
                    scanner.next();
                }
            }
        }

        private static int askTotalRows() {
            System.out.println("Enter the number of rows:");
            return scanner.nextInt();
        }

        private static int askTotalColumns() {
            System.out.println("Enter the number of seats in each row:");
            return scanner.nextInt();
        }

        private static int askRow() {
            System.out.printf("%nEnter a row number:%n");
            return scanner.nextInt();
        }

        private static int askColumn() {
            System.out.println("Enter a seat number in that row:");
            return scanner.nextInt();
        }
    }

    public static class CinemaRoom {
        private final char[][] seatGrid;
        private int profit = 0;

        private final int totalRows;
        private final int totalColumns;
        private final int totalSeats;

        private static final int FLAT_RATE_SEAT_THRESHOLD = 60;
        private static final int FLAT_RATE_PRICE = 10;
        private static final int FRONT_HALF_PRICE = 10;
        private static final int BACK_HALF_PRICE = 8;

        CinemaRoom(int gridRows, int gridColumns) {
            seatGrid = new char[gridRows][gridColumns];

            this.totalRows = seatGrid.length;
            this.totalColumns = seatGrid[0].length;
            this.totalSeats = totalRows * totalColumns;

            for (char[] chars : seatGrid) {
                Arrays.fill(chars, 'S');
            }
        }

        public static CinemaRoom createCinemaRoom() {
            return new CinemaRoom(ConsoleInteraction.askTotalRows(), ConsoleInteraction.askTotalColumns());
        }

        private void printSeatGrid() {
            System.out.printf("%nCinema:%n");

            System.out.print(" ");
            for (int column = 0; column < totalColumns; column++) {
                System.out.printf(" %d", column + 1);
            }
            System.out.println();

            for (int row = 0; row < totalRows; row++) {
                System.out.print(row + 1);
                for (char seat : seatGrid[row]) {
                    System.out.printf(" %s", seat);
                }
                System.out.println();
            }
        }

        private int getFrontRowCount() {
            return totalRows / 2;
        }

        private int getTotalPotentialProfit() {
            if (totalSeats <= FLAT_RATE_SEAT_THRESHOLD) {
                return totalSeats * FLAT_RATE_PRICE;
            } else {
                int frontRows = getFrontRowCount();
                int backRows = totalRows - frontRows;

                return ((frontRows * totalColumns) * FRONT_HALF_PRICE) + ((backRows * totalColumns) * BACK_HALF_PRICE);
            }
        }

        private int getSeatRate(int row) {
            if (totalSeats <= FLAT_RATE_SEAT_THRESHOLD) {
                return FLAT_RATE_PRICE;
            } else if (row < getFrontRowCount()) {
                return FRONT_HALF_PRICE;
            } else {
                return BACK_HALF_PRICE;
            }
        }

        private int getPurchasedTicketCount() {
            int purchasedTicketCount = 0;

            for (char[] chars : seatGrid) {
                for (char seat : chars) {
                    if (seat == 'B') {
                        purchasedTicketCount++;
                    }
                }
            }

            return purchasedTicketCount;
        }

        private boolean selectSeat() {
            try {
                int row = ConsoleInteraction.askRow() - 1;
                int column = ConsoleInteraction.askColumn() - 1;

                if (seatGrid[row][column] == 'B') {
                    System.out.printf("%nThat ticket has already been purchased!%n");
                    return false;
                }

                int ticketPrice = getSeatRate(row);
                System.out.printf("%nTicket price: $%d%n", ticketPrice);

                seatGrid[row][column] = 'B';
                profit += ticketPrice;
                return true;
            } catch (Exception e) {
                System.out.printf("%nWrong input!%n");
                return false;
            }
        }

        private void printStatistics() {
            String statisticsMessage = """
                    %n
                    Number of purchased tickets: %d
                    Percentage: %.2f%%
                    Current income: $%d
                    Total income: $%d
                    """;

            int purchasedTicketCount = getPurchasedTicketCount();
            double purchasedTicketPercentage = (double) purchasedTicketCount / totalSeats * 100;

            System.out.printf(statisticsMessage, purchasedTicketCount, purchasedTicketPercentage, profit, getTotalPotentialProfit());
        }
    }
}