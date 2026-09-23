package honestcalculator.parsing;

public class InvalidOperatorException extends RuntimeException {
    public InvalidOperatorException() {
        super("Operator provided is not valid.");
    }
}
