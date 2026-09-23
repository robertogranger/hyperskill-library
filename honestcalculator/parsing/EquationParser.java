package honestcalculator.parsing;

import honestcalculator.Equation;
import honestcalculator.EquationParts;
import honestcalculator.Memory;
import honestcalculator.Operator;

public class EquationParser {
    private EquationParser() {
    }

    public static Equation parse(EquationParts equationParts, Memory memory)
            throws InvalidOperatorException, NumberFormatException {
        double x = tryParseMemoryOrValue(equationParts.x(), memory);
        double y = tryParseMemoryOrValue(equationParts.y(), memory);
        Operator operator = Operator.fromSymbol(equationParts.operator());

        return new Equation(x, operator, y);
    }

    private static double tryParseMemoryOrValue(String number, Memory memory) throws NumberFormatException {
        return number.equals("M") ? memory.m() : NumberParser.parse(number);
    }
}
