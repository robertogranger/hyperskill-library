package battleship;

import battleship.field.Field;

public class Player {
    private final String name;
    private final Field field = new Field();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }
}