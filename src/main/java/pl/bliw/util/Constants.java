package pl.bliw.util;

public class Constants {
    public static final int ROM_CODE_OFFSET = 0x200;
    public static final double MILI_SECONDS_FACTOR = Math.pow(10, 3);
    public static final double NANO_SECONDS_FACTOR = Math.pow(10, 9);
    public static final String PONG_CHIP_8_DEFAULT_ROM_PATH = "./pong.chip8";
    public static final double EXPECTED_FPS = 60.0;
    public static final long EXPECTED_DELAY = (long) ((1 / EXPECTED_FPS) * NANO_SECONDS_FACTOR);
}
