package battleship;

public enum ShipType {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String displayName;
    private final int length;

    ShipType(String displayName, int length) {
        this.displayName = displayName;
        this.length = length;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLength() {
        return length;
    }
}