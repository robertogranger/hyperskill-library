package readability;

public record IndexScore(double score, ReadabilityIndex index) {
    public int maxAge () { return AgeBracket.fromScore(score).getMaxAge(); }

    public String format() {
        return String.format("%s: %.2f (about %d-year-olds).", index.getDisplayName(), score, maxAge());
    }
}
