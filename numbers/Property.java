package numbers;

import java.util.Optional;

public enum Property {
    EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD;

    public String label() {
        return name().toLowerCase();
    }

    // Every case is listed (no default) so adding a property is a compile error until handled.
    public Optional<Property> opposite() {
        return switch (this) {
            case EVEN -> Optional.of(ODD);
            case ODD -> Optional.of(EVEN);
            case DUCK -> Optional.of(SPY);
            case SPY -> Optional.of(DUCK);
            case SQUARE -> Optional.of(SUNNY);
            case SUNNY -> Optional.of(SQUARE);
            case HAPPY -> Optional.of(SAD);
            case SAD -> Optional.of(HAPPY);
            case BUZZ, PALINDROMIC, GAPFUL, JUMPING -> Optional.empty();
        };
    }

    // True when every number has exactly one of this property and its opposite.
    public boolean splitsAllNumbers() {
        return switch (this) {
            case EVEN, ODD, HAPPY, SAD -> true;
            case BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING -> false;
        };
    }

    public static Optional<Property> fromText(String text) {
        for (Property property : values()) {
            if (property.name().equalsIgnoreCase(text)) {
                return Optional.of(property);
            }
        }
        return Optional.empty();
    }
}