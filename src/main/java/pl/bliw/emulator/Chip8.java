package pl.bliw.emulator;

import pl.bliw.emulator.cpu.Cpu;
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

    public void initialize() {
        double expectedFps = 60.0;
        long expectedDelay = (long) ((1 / expectedFps) * Constants.NANO_SECONDS_FACTOR);
        try {
            threadExecutor = Executors.newScheduledThreadPool(1);
            threadExecutor.scheduleAtFixedRate(this, 0, expectedDelay, TimeUnit.NANOSECONDS);
            isRunning = true;
            loadProgramIntoMemory("./pong.chip8");
        } catch (Exception e) { // TOP EXCEPTION HANDLER, WHICH WILL SHUTDOWN EMULATOR AND SHOW CRASH LOG
            // TODO log e
            System.out.println("Critical error, shutting down the emulator");
            e.printStackTrace();
            shutDown();
        }
    }

    public void shutDown() {
        if (isRunning) {
            isRunning = false;
            threadExecutor.shutdownNow();
        }
    }

    @Override
    public void run() {
        cpu.run();
        performanceCounter.count();
        System.out.printf("\r FPS: %d, UPS: %d ", performanceCounter.getFPS(), performanceCounter.getUPS());
    }


    public void loadProgramIntoMemory(String path) throws IOException {
        byte[] rom = RomReader.readRomAsBytes(new File(path));
        memory.loadBytesIntoMemory(Constants.ROM_CODE_OFFSET, rom);
    }

}
