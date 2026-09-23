package honestcalculator;

public record EquationParts(String x, String operator, String y) {
    public static EquationParts of(String calc) {
        String[] tokens = calc.split("\\s+");
        return new EquationParts(tokens[0], tokens[1], tokens[2]);
    }
}
