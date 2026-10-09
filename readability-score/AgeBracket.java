package readability;

public enum AgeBracket {
    KINDERGARTEN(1, 6),
    FIRST_GRADE(2, 7),
    SECOND_GRADE(3, 8),
    THIRD_GRADE(4, 9),
    FOURTH_GRADE(5, 10),
    FIFTH_GRADE(6, 11),
    SIXTH_GRADE(7, 12),
    SEVENTH_GRADE(8, 13),
    EIGHTH_GRADE(9, 14),
    NINTH_GRADE(10, 15),
    TENTH_GRADE(11, 16),
    ELEVENTH_GRADE(12, 17),
    TWELFTH_GRADE(13, 18),
    COLLEGE_STUDENT(14, 22);

    private final int score;
    private final int maxAge;

    AgeBracket(int score, int ages) {
        this.score = score;
        this.maxAge = ages;
    }

    public static AgeBracket fromScore(double rawScore) {
        // ceil rounds number up to the next whole number
        int rounded = (int) Math.ceil(rawScore);

        // Math.clamp(value, min, max) returns value if it's between min and max, min if it's below, max if it's above.
        // Used because the formula can produce scores outside the 1-14 table (e.g. a very short
        // text scores below 1), so we use the nearest bracket instead of finding none.
        int clamped = Math.clamp(rounded, 1, 14);

        for (AgeBracket bracket : values()) {
            if (bracket.score == clamped) {
                return bracket;
            }
        }

        throw new IllegalStateException("No bracket for score " + clamped);
    }

    public int getMaxAge() { return maxAge; }
}
