package pl.bliw.emulator.memory;

import pl.bliw.emulator.exception.FullStackException;

import java.util.EmptyStackException;

import static pl.bliw.util.Binary.checkIfItIsUnsignedWordOrThrow;

/**
 * Memory area which is used by chip8 as stack for storing i.e return addresses from procedures.
 */
public class StackMemory {
    /**
     * Default size of stack layers.
     */
    private static final int DEFAULT_STACK_MAX_SIZE = 16;

    /**
     * An internal array which contains stack values.
     */
    private int[] memory;
    /**
     * The stack pointer pointing to the top of stack.
     */
    private int stackPointer;

    /**
     * The current number of elements in stack.
     */
    private int size;

    /**
     * Constructs new stack memory with default size.
     */
    public StackMemory() {
        this.memory = new int[DEFAULT_STACK_MAX_SIZE];
    }

    /**
     * Constructs new stack memory with given size.
     *
     * @param maxSize max size of layers.
     */
    public StackMemory(int maxSize) {
        this.memory = new int[maxSize];
    }

    /**
     * Adds new element on the top of the stack.
     * @param value value to store.
     */
    public void push(int value) {
        if (isFull()) {
            throw new FullStackException();
        }
        checkIfItIsUnsignedWordOrThrow("stack write operation", value);
        memory[stackPointer++] = value & 0xFFFF;
        size++;
    }

    /**
     * Returns and deletes value from the top of the stack.
     * @return value from the top of the stack.
     */
    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        size--;
        return memory[--stackPointer] & 0xFFFF;
    }

    /**
     * Checks if stack is empty.
     * @return boolean which indicates if stack is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if stack is full.
     * @return boolean which indicates if stack is full.
     */
    public boolean isFull() {
        return size == memory.length;
    }
}
