package pl.bliw.emulator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.bliw.emulator.cpu.Cpu;
import pl.bliw.emulator.io.Screen;
import pl.bliw.emulator.memory.Memory;
import pl.bliw.util.Constants;
import pl.bliw.util.PerformanceCounter;
import pl.bliw.util.RomReader;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Chip8 implements Runnable {

    private static Log log = LogFactory.getLog(Chip8.class);
    private Cpu cpu;
    private Memory memory;
    private boolean isRunning;
    private ScheduledExecutorService threadExecutor;
    private PerformanceCounter performanceCounter;
    private Screen screen;

    public Chip8(Cpu cpu, Memory memory, Screen screen) {
        this.cpu = cpu;
        this.memory = memory;
        this.screen = screen;
        performanceCounter = new PerformanceCounter();
    }

    public void initialize(String romPath) {
        try {
            loadProgramIntoMemory(romPath);
            loadFontset();
            threadExecutor = Executors.newScheduledThreadPool(1);
            threadExecutor.scheduleAtFixedRate(this, 0, Constants.EXPECTED_DELAY_IN_NANO_SECONDS, TimeUnit.NANOSECONDS);
        } catch (IOException e) {
            log.error(String.format("Specified path: %s doesn't contain valid ROM file", romPath), e);
        } catch (Exception e) { // TOP EXCEPTION HANDLER, WHICH WILL SHUTDOWN EMULATOR AND SHOW CRASH LOG
            log.fatal("Critical error, shutting down the emulator", e);
            shutDown();
        }
    }

    public void shutDown() {
            isRunning = false;
            threadExecutor.shutdownNow();
    }

    @Override
    public void run() {
        try {
            cpu.run();
            performanceCounter.count();
//        log.info(String.format("\r FPS: %d, UPS: %d ", performanceCounter.getFPS(), performanceCounter.getUPS()));
        } catch (Throwable t) {
            throw t; // Because of issues with ScheduledExecutorService exceptions
        }
    }


    public void loadProgramIntoMemory(String path) throws IOException {
        byte[] rom = RomReader.readRomAsBytes(new File(path));
        memory.loadSequenceIntoMemory(Constants.ROM_CODE_OFFSET, rom);
    }

    public void loadFontset() {
        memory.loadSequenceIntoMemory(Constants.FONT_SET_OFFSET_IN_MEMORY, Constants.CHIP8_FONTSET);
    }

    public Screen getScreen() {
        return screen;
    }

}
