package bullscows;

public record Grade(int bulls, int cows) {
    public String format() {
        StringBuilder grade = new StringBuilder("Grade: ");

        if (bulls == 0 && cows == 0) {
            grade.append("None");
        }

        if (bulls > 0) {
            grade.append(formatGrade(bulls, "bull"));
        }


        if (cows > 0 && bulls > 0) {
            grade.append(" and ");
        }

        if (cows > 0) {
            grade.append(formatGrade(cows, "cow"));
        }

        return grade.toString();
    }

    private String formatGrade(int quantity, String animal) {
        boolean isPlural = quantity > 1;

        return quantity + " " + animal + (isPlural ? "s" : "");
    }
}
