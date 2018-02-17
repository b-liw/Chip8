package pl.bliw.emulator.memory;

import pl.bliw.emulator.exception.FullStackException;

import java.util.EmptyStackException;

import static pl.bliw.util.Binary.checkIfItIsUnsignedWordOrThrow;

public class StackMemory {

    private static final int DEFAULT_STACK_MAX_SIZE = 16;
    private int[] memory;
    private int stackPointer;
    private int size;

    public StackMemory() {
        this.memory = new int[DEFAULT_STACK_MAX_SIZE];
    }

    public StackMemory(int maxSize) {
        this.memory = new int[maxSize];
    }

    public void push(int value) {
        if (isFull()) {
            throw new FullStackException();
        }
        checkIfItIsUnsignedWordOrThrow("stack write operation", value);
        memory[stackPointer++] = value & 0xFFFF;
        size++;
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        size--;
        return memory[--stackPointer] & 0xFFFF;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == memory.length;
    }

}
