package pl.bliw.emulator.exception;

/**
 * An runtime exception for stack push operation
 */
public class FullStackException extends RuntimeException {
    /**
     * Constructs new exception
     */
    public FullStackException() {
        super("Cannot add new element if stack is full");
    }
}
