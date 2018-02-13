package pl.bliw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.bliw.config.Chip8MainConfiguration;
import pl.bliw.emulator.Chip8;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Chip8MainConfiguration.class);
        Chip8 chip = context.getBean(Chip8.class);
        chip.initialize();
    }
}