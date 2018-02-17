package pl.bliw.emulator.cpu;

import pl.bliw.util.Constants;

import static pl.bliw.util.Binary.checkIfItIsUnsignedByteOrThrow;
import static pl.bliw.util.Binary.checkIfItIsUnsignedWordOrThrow;

public class Registers {

    private static final int NUM_OF_CPU_REGISTERS = 16;
    private int[] registers;
    private int I;
    private int PC;

    public Registers() {
        registers = new int[NUM_OF_CPU_REGISTERS];
        PC = Constants.ROM_CODE_OFFSET;
    }

    public int get(AvailableRegisters register) {
        return registers[register.id] & 0xFF;
    }

    public int get(int index) {
        return registers[index] & 0xFF;
    }

    public void set(AvailableRegisters register, int value) {
        checkIfItIsUnsignedByteOrThrow(String.format("V%d", register.id), value);
        registers[register.id] = value;
    }

    public void set(int index, int value) {
        try {
            checkIfItIsUnsignedByteOrThrow(String.format("V%d", index), value);
            registers[index] = value;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Incorrect register index");
        }
    }

    public int getI() {
        return I & 0xFFFF;
    }

    public void setI(int I) {
        checkIfItIsUnsignedWordOrThrow("I register", I);
        this.I = I;
    }

    public int getPC() {
        return PC & 0xFFFF;
    }

    public void setPC(int PC) {
        checkIfItIsUnsignedWordOrThrow("PC register", PC);
        this.PC = PC;
    }

    public void incrementPC(int value) {
        this.PC += value;
    }

    public enum AvailableRegisters {
        V0(0), V1(1), V2(2), V3(3), V4(4), V5(5), V6(6), V7(7), V8(8), V9(9), VA(10), VB(11), VC(12), VD(13), VE(14), VF(15);

        private int id;

        AvailableRegisters(int id) {
            this.id = id;
        }
    }

}
