package pl.bliw.emulator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.bliw.emulator.cpu.Cpu;
import pl.bliw.emulator.cpu.Timers;
import pl.bliw.emulator.io.Keyboard;
import pl.bliw.emulator.io.Screen;
import pl.bliw.emulator.io.Sound;
import pl.bliw.emulator.memory.Memory;
import pl.bliw.util.Constants;
import pl.bliw.util.PerformanceCounter;
import pl.bliw.util.RomReader;

import java.io.File;
import java.io.IOException;


public class Chip8 {

    private static Log log = LogFactory.getLog(Chip8.class);
    private Cpu cpu;
    private Memory memory;
    private boolean isRunning;
    private PerformanceCounter performanceCounter;
    private Screen screen;
    private Keyboard keyboard;
    private Timers timers;
    private Sound sound;

    public Chip8(Cpu cpu, Memory memory, Screen screen, Keyboard keyboard, Timers timers, PerformanceCounter performanceCounter, Sound sound) {
        this.cpu = cpu;
        this.memory = memory;
        this.screen = screen;
        this.keyboard = keyboard;
        this.performanceCounter = performanceCounter;
        this.timers = timers;
        this.sound = sound;
    }

    public void initialize(String romPath) throws IOException {
        loadProgramIntoMemory(romPath);
        loadFontset();
        isRunning = true;
    }

    public void shutDown() {
        isRunning = false;
    }

    public void run() {
        if (isRunning) {
            cpu.run();
            performanceCounter.count();
            timers.decrementSoundTimer();
            timers.decrementDelayTimer();
            if (timers.getSoundTimer() > 0) {
                sound.beep();
            }
//            log.info(String.format("\r FPS: %d, UPS: %d ", performanceCounter.getFPS(), performanceCounter.getUPS()));
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

    public Memory getMemory() {
        return memory;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

}