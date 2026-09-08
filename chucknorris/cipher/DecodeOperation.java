package chucknorris.cipher;

public class DecodeOperation extends CipherOperation {
    @Override
    public String process(String input) throws InvalidCipherException {
        validate(input);
        String binaryString = decodeRunLength(input);
        return binaryToText(binaryString);
    }

    private void validate(String input) throws InvalidCipherException {
        // Rule 1: only '0' and ' ' allowed
        if (!input.matches("[0 ]+")) {
            throw new InvalidCipherException();
        }

        String[] blocks = input.split(" ");

        // Rule 3: number of blocks must be even (they come in value/count pairs)
        if (blocks.length % 2 != 0) {
            throw new InvalidCipherException();
        }

        int decodedLength = 0;
        for (int i = 0; i < blocks.length; i += 2) {
            String valueBlock = blocks[i];

            // Rule 2: each pair's first block must be "0" or "00"
            if (!valueBlock.equals("0") && !valueBlock.equals("00")) {
                throw new InvalidCipherException();
            }

            String countBlock = blocks[i + 1];
            decodedLength += countBlock.length();
        }

        // Rule 4: total decoded bit length must be a multiple of 7
        if (decodedLength % 7 != 0) {
            throw new InvalidCipherException();
        }
    }

    private String decodeRunLength(String input) {
        String[] blocks = input.split(" ");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < blocks.length; i += 2) {
            String bitValue = (blocks[i].equals("0")) ? "1" : "0";
            String decodedRun = bitValue.repeat(blocks[i + 1].length());
            sb.append(decodedRun);
        }

        return sb.toString();
    }

    private String binaryToText(String binaryString) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binaryString.length(); i += 7) {
            sb.append((char) Integer.parseInt(binaryString.substring(i, i + 7), 2));
        }

        return sb.toString();
    }
}
