package pl.bliw.emulator.memory;

import static pl.bliw.util.Binary.checkIfItIsUnsignedByteOrThrow;

public class Memory {

    private final int DEFAULT_MEMORY_SIZE = 4 * 1024;
    private short[] memory;

    public Memory() {
        memory = new short[DEFAULT_MEMORY_SIZE];
    }

    public void write(int offset, int value) throws IllegalArgumentException {
        checkMemoryBounds(offset);
        checkIfItIsUnsignedByteOrThrow("memory write operation", value);
        memory[offset] = (short) (value & 0xFF);
    }

    public short read(int offset) throws IllegalArgumentException {
        checkMemoryBounds(offset);
        return (short) (memory[offset] & 0xFF);
    }

    public void loadBytesIntoMemory(int offset, byte[] data) throws IllegalArgumentException {
        if (offset + data.length > DEFAULT_MEMORY_SIZE) {
            throw new IllegalArgumentException("There is no enough space for given data starting from given offset " +
                    "in memory");
        }
        for (byte nextByte : data) {
            write(offset++, nextByte);
        }
    }

    public int getMemorySize() {
        return DEFAULT_MEMORY_SIZE;
    }

    private void checkMemoryBounds(int offset) throws IllegalArgumentException {
        if (offset < 0 || offset >= DEFAULT_MEMORY_SIZE) {
            throw new IllegalArgumentException("Invalid offset in memory access operation");
        }
    }

}
