package pl.bliw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pl.bliw.emulator.Chip8;
import pl.bliw.emulator.cpu.Cpu;
import pl.bliw.emulator.cpu.Registers;
import pl.bliw.emulator.cpu.Timers;
import pl.bliw.emulator.io.Keyboard;
import pl.bliw.emulator.io.Screen;
import pl.bliw.emulator.io.Sound;
import pl.bliw.emulator.memory.Memory;
import pl.bliw.emulator.memory.StackMemory;
import pl.bliw.gui.MainWindowController;
import pl.bliw.util.PerformanceCounter;

/**
 * The class contains source of bean definitions for Spring IoC container
 */
@Configuration
public class Chip8MainConfiguration {

    @Bean
    public Chip8 chip8() {
        return new Chip8(cpu(), memory(), screen(), keyboard(), timers(), performanceCounter(), sound());
    }

    @Bean
    public Sound sound() {
        return new Sound();
    }

    @Bean
    public PerformanceCounter performanceCounter() {
        return new PerformanceCounter();
    }

    @Bean
    public Cpu cpu() {
        return new Cpu(registers(), memory(), stackMemory(), screen(), timers(), keyboard());
    }

    @Bean
    public Timers timers() {
        return new Timers();
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
    public Screen screen() {
        return new Screen();
    }

    @Bean
    public Keyboard keyboard() {
        return new Keyboard();
    }

    @Bean
    @Lazy
    MainWindowController mainWindowController() {
        return new MainWindowController(chip8());
    }
}
