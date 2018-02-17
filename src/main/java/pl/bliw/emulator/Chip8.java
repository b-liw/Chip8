package pl.bliw.emulator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.bliw.emulator.cpu.Cpu;
import pl.bliw.emulator.memory.Memory;
import pl.bliw.util.Constants;
import pl.bliw.util.PerformanceCounter;
import pl.bliw.util.RomReader;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Chip8 implements Runnable {

    private static Log log = LogFactory.getLog(Chip8.class);
    private Cpu cpu;
    private Memory memory;
    private boolean isRunning;
    private ScheduledExecutorService threadExecutor;
    private PerformanceCounter performanceCounter;

    public Chip8(Cpu cpu, Memory memory) {
        this.cpu = cpu;
        this.memory = memory;
        performanceCounter = new PerformanceCounter();
    }

    public void initialize(String romPath) {
        try {
            loadProgramIntoMemory(romPath);
            threadExecutor = Executors.newScheduledThreadPool(1);
            ScheduledFuture scheduledFuture = threadExecutor.scheduleAtFixedRate(this, 0, Constants.EXPECTED_DELAY, TimeUnit.NANOSECONDS);
            scheduledFuture.get();
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
        cpu.run();
        performanceCounter.count();
//        log.info(String.format("\r FPS: %d, UPS: %d ", performanceCounter.getFPS(), performanceCounter.getUPS()));
    }


    public void loadProgramIntoMemory(String path) throws IOException {
        byte[] rom = RomReader.readRomAsBytes(new File(path));
        memory.loadBytesIntoMemory(Constants.ROM_CODE_OFFSET, rom);
    }

}
