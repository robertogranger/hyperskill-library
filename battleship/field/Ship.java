package battleship.field;

import battleship.Coordinate;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ship {
    private final List<Coordinate> parts;
    private final Set<Coordinate> hits = new HashSet<>();

    public Ship(List<Coordinate> parts) {
        this.parts = parts;
    }

    public int length() {
        return parts.size();
    }

    public List<Coordinate> parts() {
        return Collections.unmodifiableList(parts);
    }

    public boolean occupies(Coordinate coordinate) {
        return parts.contains(coordinate);
    }

    public void registerHit(Coordinate coordinate) {
        hits.add(coordinate);
    }

    public boolean isSunk() {
        return hits.size() == parts.size();
    }
}
