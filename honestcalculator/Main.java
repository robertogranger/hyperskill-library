package honestcalculator;

import honestcalculator.io.ConsoleInteraction;
import honestcalculator.parsing.EquationParser;
import honestcalculator.parsing.InvalidOperatorException;
import honestcalculator.parsing.NumberParser;

public class Main {
    static final String EQUATION_PROMPT = "Enter an equation";
    static final String STORE_RESULT_PROMPT = "Do you want to store the result? (y / n):";
    static final String CONTINUE_CALCULATIONS = "Do you want to continue calculations? (y / n):";
    static final String WRONG_OPERAND = "Do you even know what numbers are? Stay focused!";
    static final String WRONG_OPERATOR = "Yes ... an interesting math operation. You've slept through all classes, haven't you?";
    static final String DIVIDE_BY_ZERO = "Yeah... division by zero. Smart move...";
    static final String INVALID_ANSWER = "Please answer with 'y' or 'n'.";

    static final String[] ONE_DIGIT_CONFIRMATIONS = {
            "Are you sure? It is only one digit! (y / n)",
            "Don't be silly! It's just one number! Add to the memory? (y / n)",
            "Last chance! Do you really want to embarrass yourself? (y / n)"
    };

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Memory memory = new Memory(0);
        double result;

        while (true) {
            String calculation = ConsoleInteraction.prompt(EQUATION_PROMPT);

            try {
                Equation equation = EquationParser.parse(EquationParts.of(calculation), memory);
                String equationEvaluation = equation.checkEquation();

                if (!equationEvaluation.isEmpty()) {
                    ConsoleInteraction.print(equationEvaluation);
                }

                result = switch (equation.operator()) {
                    case PLUS -> calculator.add(equation);
                    case MINUS -> calculator.subtract(equation);
                    case MULTIPLY -> calculator.multiply(equation);
                    case DIVIDE -> calculator.divide(equation);
                };

                ConsoleInteraction.print(String.valueOf(result));

                if (ConsoleInteraction.wantsToContinue(STORE_RESULT_PROMPT)) {
                    if (!NumberParser.isOneDigit(result) || confirmOneDigitStorage()) {
                        memory = new Memory(result);
                    }
                }

                if (!ConsoleInteraction.wantsToContinue(CONTINUE_CALCULATIONS)) {
                    break;
                }

            } catch (NumberFormatException e) {
                ConsoleInteraction.print(WRONG_OPERAND);
            } catch (InvalidOperatorException e) {
                ConsoleInteraction.print(WRONG_OPERATOR);
            } catch (DivisionByZeroException e) {
                ConsoleInteraction.print(DIVIDE_BY_ZERO);
            } catch (IllegalArgumentException e) {
                ConsoleInteraction.print(INVALID_ANSWER);
            }
        }
    }

    private static boolean confirmOneDigitStorage() {
        for (String message : ONE_DIGIT_CONFIRMATIONS) {
            String answer = ConsoleInteraction.prompt(message);
            if (answer.equals("n")) {
                return false;
            }
        }
        return true;
    }
}