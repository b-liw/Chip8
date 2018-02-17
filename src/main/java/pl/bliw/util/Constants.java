package pl.bliw.util;

public class Constants {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String WINDOW_TITLE = "CHIP8";
    public static final int ROM_CODE_OFFSET = 0x200;
    public static final double MILI_SECONDS_FACTOR = Math.pow(10, 3);
    public static final double NANO_SECONDS_FACTOR = Math.pow(10, 9);
    public static final String PONG_CHIP_8_DEFAULT_ROM_PATH = "./pong.chip8";
    public static final double EXPECTED_FPS = 60.0;
    public static final long EXPECTED_DELAY = (long) ((1 / EXPECTED_FPS) * NANO_SECONDS_FACTOR);
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
    public static final int FONT_SET_OFFSET_IN_MEMORY = 0x50;
    public static final String MAIN_WINDOW_FXML_PATH = "/MainWindow.fxml";

}
