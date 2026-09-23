package honestcalculator.parsing;

public class NumberParser {
    private NumberParser() {}

    public static double parse(String token) throws NumberFormatException {
        return Double.parseDouble(token);
    }

    public static boolean isOneDigit(double variable) {
        return variable > -10 && variable < 10 && variable == (int) variable;
    }
}
