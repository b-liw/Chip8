package pl.bliw.emulator;

import pl.bliw.emulator.cpu.Cpu;
import pl.bliw.emulator.memory.Memory;

public class Chip8 {

    private Cpu cpu;
    private Memory memory;

    public Chip8(Cpu cpu, Memory memory) {
        this.cpu = cpu;
        this.memory = memory;
    }

    public void initialize() {

    }
    
}
