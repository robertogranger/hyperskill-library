package battleship;

public record Coordinate(int row, int col) {
    @Override
    public String toString() {
        char letter = (char) ('A' + row);
        return letter + "" + (col + 1);
    }
}
