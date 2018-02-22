package pl.bliw.emulator.cpu;

import pl.bliw.util.Constants;

import static pl.bliw.util.Binary.checkIfItIsUnsignedByteOrThrow;
import static pl.bliw.util.Binary.checkIfItIsUnsignedWordOrThrow;

/**
 * The class Registers represents all of the registers in Chip8.
 */
public class Registers {
    /**
     * The total number of registers available in Chip8.
     */
    private static final int NUM_OF_CPU_REGISTERS = 16;

    /**
     * An array used for storing values of general purpose registers.
     */
    private int[] registers;

    /**
     * The address register
     */
    private int I;

    /**
     * The program counter register, points to the next instruction to execute.
     */
    private int PC;

    /**
     * Constructs registers, sets program counter to offset where rom starts.
     */
    public Registers() {
        registers = new int[NUM_OF_CPU_REGISTERS];
        PC = Constants.ROM_CODE_OFFSET;
    }

    /**
     * Returns value from given register by enum.
     *
     * @param register value from AvailableRegisters enum.
     * @return value from register.
     */
    public int get(AvailableRegisters register) {
        return registers[register.id] & 0xFF;
    }

    /**
     * Returns value from given register by index.
     * @param index register index.
     * @return value from register.
     */
    public int get(int index) {
        return registers[index] & 0xFF;
    }

    /**
     * Sets register to given value.
     * @param register value from AvailableRegisters enum.
     * @param value new value for register.
     */
    public void set(AvailableRegisters register, int value) {
        try {
            checkIfItIsUnsignedByteOrThrow(String.format("V%d", register.id), value);
            registers[register.id] = value;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Incorrect register index");
        }
    }

    /**
     * Sets register to given value.
     * @param index register index.
     * @param value new value for register.
     */
    public void set(int index, int value) {
        try {
            checkIfItIsUnsignedByteOrThrow(String.format("V%d", index), value);
            registers[index] = value;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Incorrect register index");
        }
    }

    /**
     * @return address register.
     */
    public int getI() {
        return I & 0xFFFF;
    }

    /**
     * Sets address registers to given value.
     * @param I new value for address register.
     */
    public void setI(int I) {
        checkIfItIsUnsignedWordOrThrow("I register", I);
        this.I = I;
    }

    /**
     * @return program counter register.
     */
    public int getPC() {
        return PC & 0xFFFF;
    }

    /**
     * Sets program counter register to given value.
     * @param PC new value for program counter register.
     */
    public void setPC(int PC) {
        checkIfItIsUnsignedWordOrThrow("PC register", PC);
        this.PC = PC;
    }

    /**
     * The method increments program counter register by given value.
     * @param value incremental value
     */
    public void incrementPC(int value) {
        this.PC += value;
    }

    /**
     * Maps registers names to indexes in array.
     */
    public enum AvailableRegisters {
        V0(0), V1(1), V2(2), V3(3), V4(4), V5(5), V6(6), V7(7), V8(8), V9(9), VA(10), VB(11), VC(12), VD(13), VE(14), VF(15);

        /**
         * An index in registers array
         */
        private int id;

        /**
         * Constructs new register enum.
         * @param id id
         */
        AvailableRegisters(int id) {
            this.id = id;
        }
    }
}
