package honestcalculator;

public class DivisionByZeroException extends RuntimeException {
    public DivisionByZeroException() {
        super("Division by zero is not valid.");
    }
}
