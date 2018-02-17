package pl.bliw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pl.bliw.emulator.Chip8;
import pl.bliw.emulator.cpu.Cpu;
import pl.bliw.emulator.cpu.Registers;
import pl.bliw.emulator.memory.Memory;
import pl.bliw.emulator.memory.StackMemory;
import pl.bliw.gui.MainWindowController;

@Configuration
public class Chip8MainConfiguration {

    @Bean
    public Chip8 chip8() {
        return new Chip8(cpu(), memory());
    }

    @Bean
    public Cpu cpu() {
        return new Cpu(registers(), memory(), stackMemory());
    }

    @Bean
    public Registers registers() {
        return new Registers();
    }

    @Bean
    public Memory memory() {
        return new Memory();
    }

    @Bean
    public StackMemory stackMemory() {
        return new StackMemory();
    }

    @Bean
    @Lazy
    MainWindowController mainWindowController() {
        return new MainWindowController(chip8());
    }

}
