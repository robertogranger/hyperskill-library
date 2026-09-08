package chucknorris.cipher;

public abstract class CipherOperation {
    public abstract String process(String input) throws InvalidCipherException;
}
