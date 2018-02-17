package pl.bliw.emulator.io;

public class Screen {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 32;
    boolean[] screenState = new boolean[WIDTH * HEIGHT];

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public boolean[] getScreenState() {
        return screenState;
    }
}
