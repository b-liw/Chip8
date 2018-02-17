package pl.bliw.emulator.exception;

public class FullStackException extends RuntimeException {
    public FullStackException() {
        super("Cannot add new element when stack is full");
    }
}
