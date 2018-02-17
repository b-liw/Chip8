package pl.bliw;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.bliw.config.Chip8MainConfiguration;
import pl.bliw.emulator.Chip8;
import pl.bliw.util.Constants;

public class Application {

    private static Log log = LogFactory.getLog(Application.class);

    public static void main(String[] args) {
        log.info("Application is starting");
        ApplicationContext context = new AnnotationConfigApplicationContext(Chip8MainConfiguration.class);
        Chip8 chip = context.getBean(Chip8.class);
        chip.initialize(Constants.PONG_CHIP_8_DEFAULT_ROM_PATH);
    }
}