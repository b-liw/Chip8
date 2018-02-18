package pl.bliw.emulator.io;

public class Screen {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 32;
    boolean[] screenState = new boolean[WIDTH * HEIGHT];

    public void flipPixel(int offset) {
        checkBounds(offset);
        screenState[offset] = !screenState[offset];
    }

    public boolean getPixel(int offset) {
        checkBounds(offset);
        return screenState[offset];
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public boolean[] getScreenState() {
        return screenState;
    }

    private void checkBounds(int offset) throws IllegalArgumentException {
        if (offset < 0 || offset >= WIDTH * HEIGHT) {
            throw new IllegalArgumentException("Invalid offset in screen buffer");
        }
    }

}
