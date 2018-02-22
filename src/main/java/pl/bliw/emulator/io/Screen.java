package pl.bliw.emulator.io;

import pl.bliw.util.Constants;


public class Screen {
    private static final int WIDTH = Constants.WIDTH;
    private static final int HEIGHT = Constants.HEIGHT;
    private boolean[] screenState = new boolean[WIDTH * HEIGHT];
    private boolean canvasUpdated;

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public void flipPixel(int offset) {
        checkBounds(offset);
        screenState[offset] = !screenState[offset];
    }

    public boolean getPixel(int offset) {
        checkBounds(offset);
        return screenState[offset];
    }

    public boolean[] getScreenState() {
        return screenState;
    }

    public void clear() {
        for (int i = 0; i < screenState.length; i++) {
            screenState[i] = false;
        }
    }

    public boolean isCanvasUpdated() {
        return canvasUpdated;
    }

    public void setCanvasUpdated(boolean canvasUpdated) {
        this.canvasUpdated = canvasUpdated;
    }

    private void checkBounds(int offset) throws IllegalArgumentException {
        if (offset < 0 || offset >= WIDTH * HEIGHT) {
            throw new IllegalArgumentException("Invalid offset in screen buffer");
        }
    }
}
