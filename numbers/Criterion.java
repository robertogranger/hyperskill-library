package numbers;

public record Criterion(Property property, boolean excluded) {

    public boolean isSatisfiedBy(NaturalNumber number) {
        return number.has(property) != excluded;
    }

    @Override
    public String toString() {
        return (excluded ? "-" : "") + property.name();
    }
}