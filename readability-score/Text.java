package readability;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Text(String text) {
    private static final Pattern VOWELS_PATTERN = Pattern.compile("[aeiouy]+");


    public String[] words() {
        return text.trim().split("\\s+");
    }

    private static int syllablesIn(String word) {
        int syllableCount = 0;

        String cleanWord = word.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z]", "")
                .replaceAll("e$", "");

        Matcher matcher = VOWELS_PATTERN.matcher(cleanWord);

        while (matcher.find()) {
            syllableCount++;
        }

        return syllableCount == 0 ? 1 : syllableCount;
    }

    public int sentenceCount() {
        int count = 0;

        for (String sentence : text.split("[.!?]+")) {
            if (!sentence.isBlank()) {
                count++;
            }
        }

        return count;
    }

    public int wordCount() {
        return words().length;
    }

    public int characterCount() {
        return text.replaceAll("\\s+", "").length();
    }

    public int syllableCount() {
        int totalSyllables = 0;

        for (String word : words()) {
            totalSyllables += syllablesIn(word);
        }

        return totalSyllables;
    }

    public int polysyllableCount() {
        int totalPolysyllables = 0;

        for (String word : words()) {
            if (syllablesIn(word) > 2) {
                totalPolysyllables++;
            }
        }

        return totalPolysyllables;
    }


    public String report() {

        return String.format("""
                The text is:
                %s
                
                Words: %d
                Sentences: %d
                Characters: %d
                Syllables: %d
                Polysyllables: %d""", text, wordCount(), sentenceCount(), characterCount(), syllableCount(), polysyllableCount());
    }
}
