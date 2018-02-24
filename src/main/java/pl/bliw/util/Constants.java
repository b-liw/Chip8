package pl.bliw.util;

/**
 * The Constants class provides static variables to be used in application.
 */
public class Constants {
    /**
     * The scale variable which will be used in scaling size of the standard chip-8 window.
     */
    public static final int SCALE = 15;

    /**
     * The width of chip8 window.
     */
    public static final int STANDARD_CHIP8_SCREEN_WIDTH = 64;

    /**
     * The height of chip8 window.
     */
    public static final int STANDARD_CHIP8_SCREEN_HEIGHT = 32;

    /**
     * The width of main window.
     */
    public static final int WIDTH = STANDARD_CHIP8_SCREEN_WIDTH * SCALE;

    /**
     * The height of main window.
     */
    public static final int HEIGHT = STANDARD_CHIP8_SCREEN_HEIGHT * SCALE;

    /**
     * The title of main window.
     */
    public static final String WINDOW_TITLE = "CHIP8";

    /**
     * The standard offset where ROM will be loaded in memory.
     */
    public static final int ROM_CODE_OFFSET = 0x200;

    /**
     * The factor to convert a second to a millisecond.
     */
    public static final double MILI_SECONDS_FACTOR = Math.pow(10, 3);

    /**
     * The factor to convert a second to a nanosecond.
     */
    public static final double NANO_SECONDS_FACTOR = Math.pow(10, 9);

    /**
     * The number of expected fps.
     */
    public static final double EXPECTED_FPS = 60.0;

    /**
     * The counted length of delay between frames for given expected FPS.
     */
    public static final long EXPECTED_DELAY_IN_NANO_SECONDS = (long) ((1.0 / EXPECTED_FPS) * NANO_SECONDS_FACTOR);

    /**
     * The array of standard chip8 font set.
     */
    public static final char CHIP8_FONTSET[] =
            {
                    0xF0, 0x90, 0x90, 0x90, 0xF0, // 0
                    0x20, 0x60, 0x20, 0x20, 0x70, // 1
                    0xF0, 0x10, 0xF0, 0x80, 0xF0, // 2
                    0xF0, 0x10, 0xF0, 0x10, 0xF0, // 3
                    0x90, 0x90, 0xF0, 0x10, 0x10, // 4
                    0xF0, 0x80, 0xF0, 0x10, 0xF0, // 5
                    0xF0, 0x80, 0xF0, 0x90, 0xF0, // 6
                    0xF0, 0x10, 0x20, 0x40, 0x40, // 7
                    0xF0, 0x90, 0xF0, 0x90, 0xF0, // 8
                    0xF0, 0x90, 0xF0, 0x10, 0xF0, // 9
                    0xF0, 0x90, 0xF0, 0x90, 0x90, // A
                    0xE0, 0x90, 0xE0, 0x90, 0xE0, // B
                    0xF0, 0x80, 0x80, 0x80, 0xF0, // C
                    0xE0, 0x90, 0x90, 0x90, 0xE0, // D
                    0xF0, 0x80, 0xF0, 0x80, 0xF0, // E
                    0xF0, 0x80, 0xF0, 0x80, 0x80  // F
            };

    /**
     * The standard offset where font set will be loaded in memory.
     */
    public static final int FONT_SET_OFFSET_IN_MEMORY = 0x50;

    /**
     * The main window title
     */
    public static final String MAIN_WINDOW_FXML_PATH = "/MainWindow.fxml";

    /**
     * The default number of bits in one byte.
     */
    public static final int BITS_IN_BYTE = 8;

    /**
     * The standard length of opcode in chip8.
     */
    public static final int DEFAULT_OPCODE_LENGTH = 2;

    /**
     * The path to the file with beep sound.
     */
    public static final String BEEP_SOUND_PATH = "/beep-08b.wav";

    /**
     * The error dialog title.
     */
    public static final String ERROR_INFO = "ERROR";

    /**
     * A one second in milliseconds.
     */
    public static final long ONE_SECOND = 1000;
}
