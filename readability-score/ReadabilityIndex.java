package readability;

public enum ReadabilityIndex {
    ARI("Automated Readability Index"),
    FK("Flesch–Kincaid readability tests"),
    SMOG("Simple Measure of Gobbledygook"),
    CL("Coleman–Liau index");

    private final String displayName;

    ReadabilityIndex(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double score(Text text) {
        return switch (this) {
            case CL -> computeCL(text);
            case ARI -> computeARI(text);
            case SMOG -> computeSMOG(text);
            case FK -> computeFK(text);
        };
    }

    private double computeARI(Text text) {
        return 4.71 * ( (double) text.characterCount() / text.wordCount() ) + 0.5 * ( (double) text.wordCount() / text.sentenceCount() ) - 21.43;
    }

    private double computeFK(Text text) {
        return 0.39 * ( (double) text.wordCount() / text.sentenceCount() ) + 11.8 * ( (double) text.syllableCount() / text.wordCount() ) - 15.59;
    }

    private double computeSMOG(Text text) {
        return 1.043 * Math.sqrt( text.polysyllableCount() * ( 30.0 / text.sentenceCount()) ) + 3.1291;
    }

    private double computeCL(Text text) {
        final double charactersPer100Words = (double) text.characterCount() / text.wordCount() * 100;
        final double sentencesPer100Words = (double) text.sentenceCount() / text.wordCount() * 100;

        return 0.0588 * charactersPer100Words - 0.296 * sentencesPer100Words - 15.8;
    }
}
