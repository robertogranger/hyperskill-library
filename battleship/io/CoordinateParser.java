package battleship.io;

import battleship.Coordinate;

public class CoordinateParser {

    private CoordinateParser() {
    }

    public static Coordinate[] parse(String line) {
        String[] tokens = line.trim().split("\\s+");

        if (tokens.length != 2) {
            return null;
        }

        Coordinate a = parseToken(tokens[0]);
        Coordinate b = parseToken(tokens[1]);

        if (a == null || b == null) {
            return null;
        }

        return new Coordinate[] { a, b };
    }

    public static Coordinate parseSingleCoordinate(String line) {
        String[] tokens = line.trim().split("\\s+");

        if (tokens.length != 1) {
            return null;
        }

        return parseToken(tokens[0]);
    }

    private static Coordinate parseToken(String token) {
        if (token.length() < 2 || token.length() > 3) {
            return null;
        }

        char letterChar = token.charAt(0);
        String numberPart = token.substring(1);

        if (letterChar < 'A' || letterChar > 'J') {
            return null;
        }

        int row = letterChar - 'A';
        int col;

        try {
            col = Integer.parseInt(numberPart) - 1;
        } catch (NumberFormatException e) {
            return null;
        }

        if (col < 0 || col > 9) {
            return null;
        }

        return new Coordinate(row, col);
    }
}