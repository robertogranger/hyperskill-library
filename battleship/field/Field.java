package battleship.field;

import battleship.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {
    private final char[][] grid = new char[10][10];
    private List<Ship> ships = new ArrayList<>();

    public Field() {
        for (char[] row : grid) {
            Arrays.fill(row, '~');
        }
    }

    public boolean isValidPlacement(Coordinate a, Coordinate b) {
        boolean isSameCell = a.row() == b.row() && a.col() == b.col();
        boolean isDiagonal = a.row() != b.row() && a.col() != b.col();

        return !(isSameCell || isDiagonal);
    }

    public boolean isAdjacentToExistingShip(List<Coordinate> cells) {
        for (Coordinate cell : cells) {
            for (int dRow = -1; dRow <= 1; dRow++) {
                for (int dCol = -1; dCol <= 1; dCol++) {
                    int r = cell.row() + dRow;
                    int c = cell.col() + dCol;
                    if (r >= 0 && r < 10 && c >= 0 && c < 10 && grid[r][c] == 'O') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isShipPart(Coordinate coordinate) {
        return grid[coordinate.row()][coordinate.col()] == 'O';
    }

    public List<Coordinate> resolveCells(Coordinate a, Coordinate b) {
        List<Coordinate> cells = new ArrayList<>();
        boolean horizontal = a.row() == b.row();

        if (horizontal) {
            int fixedRow = a.row();
            int step = a.col() < b.col() ? 1 : -1;
            for (int col = a.col(); col != b.col() + step; col += step) {
                cells.add(new Coordinate(fixedRow, col));
            }
        } else {
            int fixedCol = a.col();
            int step = a.row() < b.row() ? 1 : -1;
            for (int row = a.row(); row != b.row() + step; row += step) {
                cells.add(new Coordinate(row, fixedCol));
            }
        }

        return cells;
    }

    public void placeShip(Ship ship) {
        List<Coordinate> parts = ship.parts();
        ships.add(ship);

       for (Coordinate part : parts) {
           grid[part.row()][part.col()] = 'O';
       }
    }

    private Ship findShipAt(Coordinate coordinate) {
        for (Ship ship : ships) {
            if (ship.occupies(coordinate)) {
                return ship;
            }
        }
        return null;
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }

        return true;
    }

    public Ship processShot(Coordinate coordinate) {
        Ship ship = findShipAt(coordinate);

        if (ship == null) {
            grid[coordinate.row()][coordinate.col()] = 'M';
            return null;
        }

        grid[coordinate.row()][coordinate.col()] = 'X';
        ship.registerHit(coordinate);
        return ship;
    }

    public String render() {
        return buildRender(grid);
    }

    public String renderFog() {
        char[][] grid = new char[10][10];

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                grid[row][col] = this.grid[row][col] == 'O' ? '~' : this.grid[row][col];
            }
        }

        return buildRender(grid);
    }

    private String buildRender(char[][] grid) {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        for (int col = 1; col <= 10; col++) {
            sb.append(col).append(col < 10 ? " " : "");
        }
        sb.append("\n");

        for (int row = 0; row < 10; row++) {
            char rowLetter = (char) ('A' + row);
            sb.append(rowLetter);
            for (int col = 0; col < 10; col++) {
                sb.append(' ').append(grid[row][col]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
