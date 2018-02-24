package pl.bliw.emulator.memory;

import org.junit.Before;
import org.junit.Test;
import pl.bliw.emulator.exception.FullStackException;

import java.util.EmptyStackException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StackMemoryTest {
    private static final int TEST_STACK_SIZE = 5;
    private StackMemory stack;

    @Before
    public void before() {
        stack = new StackMemory(TEST_STACK_SIZE);
    }

    @Test
    public void stackWithoutElementsShouldBeEmpty() {
        assertTrue(stack.isEmpty());
    }

    @Test
    public void fullStackShouldBeFull() {
        for (int i = 0; i < TEST_STACK_SIZE; i++) {
            stack.push(i);
        }
        assertTrue(stack.isFull());
    }

    @Test(expected = EmptyStackException.class)
    public void popOnEmptyStackShouldThrowException() {
        stack.pop();
    }

    @Test(expected = FullStackException.class)
    public void pushOnFullStackShouldThrowException() {
        for (int i = 0; i < TEST_STACK_SIZE + 1; i++) {
            stack.push(i);
        }
    }

    @Test
    public void testPush() {
        stack.push(20);
        stack.push(30);
        stack.push(40);
        assertEquals(40, stack.pop());
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
    }
}
