package pl.bliw.emulator.memory;

import static pl.bliw.util.Binary.checkIfItIsUnsignedByteOrThrow;

/**
 * The class represents memory module of Chip8. Chip8 uses unsigned values, but Java doesn't provide them, so there are
 * checks which will throw an exception if values are incorrect unsigned bytes or words.
 */
public class Memory {
    /**
     * The default memory size
     */
    private final int DEFAULT_MEMORY_SIZE = 4 * 1024;

    /**
     * An array which contains unsigned bytes, used as memory representation
     */
    private short[] memory;

    /**
     * Constructs new memory with default size.
     */
    public Memory() {
        memory = new short[DEFAULT_MEMORY_SIZE];
    }

    /**
     * The method allows to write value into memory.
     *
     * @param offset the place of cell where value will be written
     * @param value  value to write
     * @throws IllegalArgumentException if value isn't valid unsigned byte or offset is incorrect
     */
    public void write(int offset, int value) throws IllegalArgumentException {
        checkMemoryBounds(offset);
        checkIfItIsUnsignedByteOrThrow("memory write operation", value);
        memory[offset] = (short) (value & 0xFF);
    }

    /**
     * The method allows read value from memory.
     * @param offset the place of cell from which value will be read
     * @return read value
     * @throws IllegalArgumentException if offset is incorrect
     */
    public short read(int offset) throws IllegalArgumentException {
        checkMemoryBounds(offset);
        return (short) (memory[offset] & 0xFF);
    }

    /**
     * Loads bytes into memory starting from given offset.
     * @param offset starting point in memory
     * @param data   array of bytes which will be written into memory
     * @throws IllegalArgumentException if offset is incorrect
     */
    public void loadSequenceIntoMemory(int offset, byte[] data) throws IllegalArgumentException {
        if (offset + data.length > DEFAULT_MEMORY_SIZE) {
            throw new IllegalArgumentException("There is no enough space for given data starting from given offset " +
                    "in memory");
        }
        for (byte nextByte : data) {
            write(offset++, nextByte & 0xFF);
        }
    }

    /**
     * Loads chars into memory starting from given offset.
     * @param offset starting point in memory
     * @param data   array of chars which will be written into memory
     * @throws IllegalArgumentException if offset is incorrect
     */
    public void loadSequenceIntoMemory(int offset, char[] data) throws IllegalArgumentException {
        if (offset + data.length > DEFAULT_MEMORY_SIZE) {
            throw new IllegalArgumentException("There is no enough space for given data starting from given offset " +
                    "in memory");
        }
        for (char nextChar : data) {
            write(offset++, nextChar & 0xFF);
        }
    }

    /**
     * Returns size of chip8 memory
     * @return
     */
    public int getMemorySize() {
        return DEFAULT_MEMORY_SIZE;
    }

    /**
     * Checks if given offset is in the range of memory.
     * @param offset offset in memory
     * @throws IllegalArgumentException if offset is incorrect
     */
    private void checkMemoryBounds(int offset) throws IllegalArgumentException {
        if (offset < 0 || offset >= DEFAULT_MEMORY_SIZE) {
            throw new IllegalArgumentException("Invalid offset in memory access operation");
        }
    }
}
