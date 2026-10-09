package numbers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record NaturalNumber(long value) {
    public NaturalNumber {
        if (value < 1) {
            throw new IllegalArgumentException("Expected a natural number (>= 1) but got: " + value);
        }
    }

    public boolean has(Property property) {
        return switch (property) {
            case EVEN -> isEven();
            case ODD -> !isEven();
            case BUZZ -> isBuzz();
            case DUCK -> isDuck();
            case PALINDROMIC -> isPalindrome();
            case GAPFUL -> isGapful();
            case SPY -> isSpy();
            case SQUARE -> isSquare();
            case SUNNY -> isSunny();
            case HAPPY -> isHappy();
            case SAD -> !isHappy();
            case JUMPING -> isJumping();
        };
    }

    public boolean matchesAll(List<Criterion> criteria) {
        for (Criterion criterion : criteria) {
            if (!criterion.isSatisfiedBy(this)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasAll(Set<Property> properties) {
        for (Property property : properties) {
            if (!has(property)) {
                return false;
            }
        }
        return true;
    }

    private boolean isHappy() {
        Set<Long> seen = new HashSet<>();
        long number = value;

        // Stops at 1 (happy) or on the first repeated value (an endless cycle, so sad).
        while (number != 1 && seen.add(number)) {
            number = sumOfDigitSquares(number);
        }

        return number == 1;
    }

    private static long sumOfDigitSquares(long n) {
        long sum = 0;

        while (n > 0) {
            long digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }

        return sum;
    }

    private boolean isJumping() {
        long number = value;
        long previous = number % 10;
        number /= 10;

        while (number > 0) {
            long current = number % 10;

            if (Math.abs(previous - current) != 1) {
                return false;
            }

            previous = current;
            number /= 10;
        }

        return true;
    }

    private boolean isSquare() {
        return isPerfectSquare(value);
    }

    private boolean isSunny() {
        // value + 1 would wrap around for Long.MAX_VALUE; 2^63 is not a perfect square anyway.
        return value != Long.MAX_VALUE && isPerfectSquare(value + 1);
    }

    private static boolean isPerfectSquare(long n) {
        BigInteger number = BigInteger.valueOf(n);
        BigInteger root = number.sqrt();
        return root.multiply(root).equals(number);
    }

    private boolean isBuzz() {
        return isDivisibleBy7() || endsWith7();
    }

    private boolean isDivisibleBy7() {
        return value % 7 == 0;
    }

    private boolean endsWith7() {
        return value % 10 == 7;
    }

    private boolean isEven() {
        return value % 2 == 0;
    }

    private boolean isDuck() {
        long number = value;

        while (number != 0) {
            if (number % 10 == 0) {
                return true;
            }

            number /= 10;
        }

        return false;
    }

    private boolean isPalindrome() {
        String number = Long.toString(value);

        // Each digit is compared with its mirror from the other end, so only the
        // first half needs checking. The middle digit of an odd-length number is
        // its own mirror and can be skipped.
        int pairCount = number.length() / 2;

        for (int i = 0; i < pairCount; i++) {
            int mirrorIndex = number.length() - i - 1;

            if (number.charAt(i) != number.charAt(mirrorIndex)) {
                return false;
            }
        }

        return true;
    }

    private boolean isGapful() {
        String number = Long.toString(value);

        if (number.length() < 3) {
            return false;
        }

        return value % Long.parseLong(number.charAt(0) + "" + number.charAt(number.length() - 1)) == 0;
    }

    private boolean isSpy() {
        long sum = 0;
        long product = 1;
        long number = value;

        // A long has at most 19 digits, and 9^19 is about 1.35e18, which is below
        // Long.MAX_VALUE, so the product cannot overflow.
        while (number > 0) {
            long digit = number % 10;
            sum += digit;
            product *= digit;
            number /= 10;
        }

        return sum == product;
    }

    public String describe() {
        StringBuilder card = new StringBuilder(String.format("Properties of %,d", value));

        for (Property property : Property.values()) {
            card.append(System.lineSeparator())
                    .append(String.format("%12s: %b", property.label(), has(property)));
        }

        return card.toString();
    }

    public String describeInline() {
        List<String> labels = new ArrayList<>();

        for (Property property : Property.values()) {
            if (has(property)) {
                labels.add(property.label());
            }
        }

        return String.format("%,16d is %s", value, String.join(", ", labels));
    }
}