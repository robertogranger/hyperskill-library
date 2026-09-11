package battleship;

import battleship.field.Field;
import battleship.field.Ship;
import battleship.io.ConsoleInteraction;
import battleship.io.CoordinateParser;

import java.util.List;

public class Game {
    private final Player player1 = new Player("Player 1");
    private final Player player2 = new Player("Player 2");
    private final ShipType[] shipTypes = ShipType.values();

    void play() {
        setupPlayer(player1);
        passDevice();
        setupPlayer(player2);
        passDevice();

        Player attacker = player1;
        Player defender = player2;

        while (!defender.getField().allShipsSunk() && !attacker.getField().allShipsSunk()) {
            takeShot(attacker, defender);
            passDevice();

            Player temp = attacker;
            attacker = defender;
            defender = temp;
        }

        ConsoleInteraction.print("You sank the last ship. You won. Congratulations!");
    }

    private void setupPlayer(Player player) {
        ConsoleInteraction.print(player.getName() + ", place your ships on the game field");
        ConsoleInteraction.printEmptyLine();

        for (ShipType type : shipTypes) {
            ConsoleInteraction.print(player.getField().render());
            promptAndPlaceShip(player, type);
            ConsoleInteraction.print(player.getField().render());
        }
    }

    private void promptAndPlaceShip(Player player, ShipType type) {
        Field field = player.getField();

        ConsoleInteraction.print("Enter the coordinates of the " + type.getDisplayName() + " (" + type.getLength() + " cells):");
        ConsoleInteraction.printEmptyLine();

        while (true) {
            String userInput = ConsoleInteraction.getInput();
            ConsoleInteraction.printEmptyLine();

            Coordinate[] coordinates = CoordinateParser.parse(userInput);

            if (coordinates == null) {
                reportErrorAndRetry("Error! Invalid coordinates! Try again:");
                continue;
            }

            if (!field.isValidPlacement(coordinates[0], coordinates[1])) {
                reportErrorAndRetry("Error! Wrong ship location! Try again:");
                continue;
            }

            List<Coordinate> parts = field.resolveCells(coordinates[0], coordinates[1]);

            if (parts.size() != type.getLength()) {
                reportErrorAndRetry("Error! Wrong length of the " + type.getDisplayName() + "! Try again:");
                continue;
            }

            if (field.isAdjacentToExistingShip(parts)) {
                reportErrorAndRetry("Error! You placed it too close to another one. Try again:");
                continue;
            }

            Ship ship = new Ship(parts);
            field.placeShip(ship);
            break;
        }
    }

    private void takeShot(Player attacker, Player defender) {
        ConsoleInteraction.print(defender.getField().renderFog());
        ConsoleInteraction.print("--------------------");
        ConsoleInteraction.print(attacker.getField().render());
        ConsoleInteraction.printEmptyLine();

        ConsoleInteraction.print(attacker.getName() + ", it's your turn:");
        ConsoleInteraction.printEmptyLine();

        while (true) {
            String userInput = ConsoleInteraction.getInput();
            ConsoleInteraction.printEmptyLine();

            Coordinate coordinate = CoordinateParser.parseSingleCoordinate(userInput);

            if (coordinate == null) {
                reportErrorAndRetry("Error! You entered the wrong coordinates! Try again:");
                continue;
            }

            Ship hitShip = defender.getField().processShot(coordinate);

            if (hitShip == null) {
                ConsoleInteraction.print("You missed!");
            } else if (hitShip.isSunk()) {
                ConsoleInteraction.print(defender.getField().allShipsSunk()
                        ? "You sank the last ship. You won. Congratulations!"
                        : "You sank a ship!");
            } else {
                ConsoleInteraction.print("You hit a ship!");
            }

            break;
        }
    }

    private void passDevice() {
        ConsoleInteraction.waitForEnter("Press Enter and pass the move to another player");
        ConsoleInteraction.clearScreen();
    }

    private void reportErrorAndRetry(String message) {
        ConsoleInteraction.print(message);
        ConsoleInteraction.printEmptyLine();
    }
}