package chucknorris.cipher;

import java.util.ArrayList;
import java.util.List;

public class EncodeOperation extends CipherOperation {
    @Override
    public String process(String input) throws InvalidCipherException {
        String binaryString = buildBinaryString(input);
        return encodeRunLength(binaryString);
    }

    private String buildBinaryString(String input) {
        StringBuilder sb = new StringBuilder();
        for (char character : input.toCharArray()) {
            sb.append(String.format("%7s", Integer.toBinaryString(character))
                    .replace(' ', '0'));
        }

        return sb.toString();
    }

    private String encodeRunLength(String binaryString) {
        List<String> encodedRuns = new ArrayList<>();

        char currentBit = binaryString.charAt(0);
        int repeatedCount = 1;

        for (int i = 1; i < binaryString.length(); i++) {
            char nextBit = binaryString.charAt(i);

            if (nextBit == currentBit) {
                repeatedCount++;
            } else {
                String valueBlock = (currentBit == '1') ? "0" : "00";
                String countBlock = "0".repeat(repeatedCount);
                encodedRuns.add(valueBlock + " " + countBlock);

                currentBit = nextBit;
                repeatedCount = 1;
            }
        }

        String valueBlock = (currentBit == '1') ? "0" : "00";
        String countBlock = "0".repeat(repeatedCount);
        encodedRuns.add(valueBlock + " " + countBlock);

        return String.join(" ", encodedRuns);
    }
}
