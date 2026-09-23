package honestcalculator;

import honestcalculator.parsing.InvalidOperatorException;

public enum Operator {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }

    public static Operator fromSymbol(String symbol) throws InvalidOperatorException {
        for (Operator operator : values()) {
            if (operator.symbol.equals(symbol)) {
                return operator;
            }
        }

        throw new InvalidOperatorException();
    }
}
