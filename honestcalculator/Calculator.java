package honestcalculator;

public class Calculator {
    double add(Equation equation) {
        return equation.x() + equation.y();
    }

    double subtract(Equation equation) {
        return equation.x() - equation.y();
    }

    double multiply(Equation equation) {
        return equation.x() * equation.y();
    }

    double divide(Equation equation) throws DivisionByZeroException {
        if (equation.y() == 0) {
            throw new DivisionByZeroException();
        }

        return equation.x() / equation.y();
    }
}