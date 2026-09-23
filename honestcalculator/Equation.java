package honestcalculator;

import honestcalculator.parsing.NumberParser;

public record Equation(double x, Operator operator, double y) {
    static final String LAZY_MESSAGE = " ... lazy"; //6
    static final String VERY_LAZY_MESSAGE = " ... very lazy"; //7
    static final String VERY_VERY_LAZY_MESSAGE = " ... very, very lazy"; //8
    static final String YOU_ARE_MESSAGE = "You are"; //9

    public String checkEquation() {
        StringBuilder message = new StringBuilder();

        if (NumberParser.isOneDigit(x) && NumberParser.isOneDigit(y)) {
            message.append(LAZY_MESSAGE);
        }

        if ((x == 1 || y == 1) && operator == Operator.MULTIPLY) {
            message.append(VERY_LAZY_MESSAGE);
        }

        if ((x == 0 || y == 0) && (operator == Operator.MULTIPLY || operator == Operator.PLUS || operator == Operator.MINUS)) {
            message.append(VERY_VERY_LAZY_MESSAGE);
        }

        if (!message.isEmpty()) {
            message.insert(0, YOU_ARE_MESSAGE);
        }

        return message.toString();
    }
}
