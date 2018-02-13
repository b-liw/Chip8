package pl.bliw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.bliw.emulator.Chip8;
import pl.bliw.emulator.cpu.Cpu;
import pl.bliw.emulator.cpu.Registers;
import pl.bliw.emulator.memory.Memory;

@Configuration
public class Chip8MainConfiguration {

    @Bean
    @Scope("singleton")
    public Chip8 chip8() {
        return new Chip8(cpu(), memory());
    }

    @Bean
    @Scope("prototype")
    public Cpu cpu() {
        return new Cpu(registers());
    }

    @Bean
    @Scope("prototype")
    public Registers registers() {
        return new Registers();
    }

    @Bean
    @Scope("prototype")
    public Memory memory() {
        return new Memory();
    }

}
