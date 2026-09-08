package chucknorris.cipher;

public class InvalidCipherException extends Exception {
    public InvalidCipherException() {
        super("Encoded string is not valid.");
    }
}
