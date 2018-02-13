package pl.bliw.util;

import org.junit.Test;

import static pl.bliw.util.Binary.checkIfItIsUnsignedByteOrThrow;
import static pl.bliw.util.Binary.checkIfItIsUnsignedWordOrThrow;

public class BinaryTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenArgumentIsGreatherThanUnsignedByteRange() {
        checkIfItIsUnsignedByteOrThrow("test register", 0xFF + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenArgumentIsLessThanUnsignedByteRange() {
        checkIfItIsUnsignedByteOrThrow("test register", -0x1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenArgumentIsGreatherThanUnsignedWordRange() {
        checkIfItIsUnsignedWordOrThrow("test register", 0xFFFF + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenArgumentIsLessThanUnsignedWordRange() {
        checkIfItIsUnsignedWordOrThrow("test register", -0x1);
    }

    @Test
    public void correctUnsignedByteShouldPassWithoutErrors() {
        checkIfItIsUnsignedByteOrThrow("test register", 0x71);
    }

    @Test
    public void correctUnsignedWordShouldPassWithoutErrors() {
        checkIfItIsUnsignedWordOrThrow("test register", 0x7191);
    }

}
