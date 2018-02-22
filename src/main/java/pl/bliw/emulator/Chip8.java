package pl.bliw.emulator;


import org.apache.log4j.Logger;
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

/**
 * The class represents whole Chip8 emulator.
 */
public class Chip8 {
    /**
     * The logger for this class.
     */
    private static Logger log = Logger.getLogger(Chip8.class.getName());

    /**
     * The central processing unit.
     */
    private Cpu cpu;

    /**
     * The main memory.
     */
    private Memory memory;

    /**
     * The flag indicates if chip8 is currently running.
     */
    private boolean isRunning;

    /**
     * The performance counter for counting FPS and UPS.
     */
    private PerformanceCounter performanceCounter;

    /**
     * The main screen with internal screen buffer memory.
     */
    private Screen screen;

    /**
     * The keyboard.
     */
    private Keyboard keyboard;

    /**
     * The timers.
     */
    private Timers timers;

    /**
     * The sound module.
     */
    private Sound sound;

    /**
     * Constructs new Chip8 instance with dependencies
     *
     * @param cpu                central processing unit
     * @param memory             memory module
     * @param screen             screen module
     * @param keyboard           keyboard module
     * @param timers             timers
     * @param performanceCounter performance counter
     * @param sound              sound manager
     */
    public Chip8(Cpu cpu, Memory memory, Screen screen, Keyboard keyboard, Timers timers, PerformanceCounter performanceCounter, Sound sound) {
        this.cpu = cpu;
        this.memory = memory;
        this.screen = screen;
        this.keyboard = keyboard;
        this.performanceCounter = performanceCounter;
        this.timers = timers;
        this.sound = sound;
    }

    /**
     * The method initializes Chip8, loads ROM and font set.
     * @param romPath file path
     * @throws IOException if file cannot be read
     */
    public void initialize(String romPath) throws IOException {
        loadProgramIntoMemory(romPath);
        loadFontset();
        isRunning = true;
    }

    /**
     * Shutdowns chip8.
     */
    public void shutDown() {
        isRunning = false;
    }

    /**
     * The method performs one tick of emulator.
     */
    public void run() {
        if (isRunning) {
            cpu.run();
            performanceCounter.count();
            timers.decrementSoundTimer();
            timers.decrementDelayTimer();
            if (timers.getSoundTimer() > 0) {
                sound.beep();
            }
            log.info(String.format("\r FPS: %d, UPS: %d ", performanceCounter.getFPS(), performanceCounter.getUPS()));
        }
    }

    /**
     * The method loads ROM as byte array and write it into internal Chip8 memory.
     * @param path file path
     * @throws IOException if file cannot be read
     */
    public void loadProgramIntoMemory(String path) throws IOException {
        byte[] rom = RomReader.readRomAsBytes(new File(path));
        memory.loadSequenceIntoMemory(Constants.ROM_CODE_OFFSET, rom);
    }

    /**
     * Loads font set into internal Chip8 memory.
     */
    public void loadFontset() {
        memory.loadSequenceIntoMemory(Constants.FONT_SET_OFFSET_IN_MEMORY, Constants.CHIP8_FONTSET);
    }

    /**
     * Returns reference to chip8 screen.
     * @return screen
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * Returns reference to chip8 keyboard
     * @return keyboard
     */
    public Keyboard getKeyboard() {
        return keyboard;
    }
}