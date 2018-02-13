package pl.bliw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.bliw.emulator.Chip8;
import pl.bliw.emulator.cpu.Cpu;
import pl.bliw.emulator.cpu.Registers;

@Configuration
public class Chip8MainConfiguration {

    @Bean
    @Scope("singleton")
    public Chip8 chip8() {
        return new Chip8(cpu());
    }

    @Bean
    @Scope("prototype")
    Cpu cpu() {
        return new Cpu(registers());
    }

    @Bean
    @Scope("prototype")
    Registers registers() {
        return new Registers();
    }

}
