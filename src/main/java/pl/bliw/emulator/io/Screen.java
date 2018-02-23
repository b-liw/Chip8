package pl.bliw.emulator.io;

import pl.bliw.util.Constants;

/**
 * Chip8 screen module, which is by default 64x32.
 */
public class Screen {
    /**
     * The width of screen.
     */
    private static final int WIDTH = Constants.STANDARD_CHIP8_SCREEN_WIDTH;

    /**
     * The height of screen.
     */
    private static final int HEIGHT = Constants.STANDARD_CHIP8_SCREEN_HEIGHT;

    /**
     * An array of state of every pixel. There are two possibilities. Pixel can be on or off (white or black).
     */
    private boolean[] screenState = new boolean[WIDTH * HEIGHT];

    /**
     * Flag indicates if canvas has been updated, and if there is needed redraw.
     */
    private boolean canvasUpdated;

    /**
     * @return screen width.
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * @return screen height.
     */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * Flips state of pixel.
     *
     * @param offset offset of pixel in screen buffer.
     */
    public void flipPixel(int offset) {
        checkBounds(offset);
        screenState[offset] = !screenState[offset];
    }

    /**
     * Gets state of pixel for given offset.
     * @param offset offset of pixel in screen buffer.
     * @return state of pixel.
     */
    public boolean getPixel(int offset) {
        checkBounds(offset);
        return screenState[offset];
    }

    /**
     * @return An array with state of every pixel.
     */
    public boolean[] getScreenState() {
        return screenState;
    }

    /**
     * Clears screen, sets every pixel color to black;
     */
    public void clear() {
        for (int i = 0; i < screenState.length; i++) {
            screenState[i] = false;
        }
    }

    /**
     * @return flag indicates if canvas has been updated recently.
     */
    public boolean isCanvasUpdated() {
        return canvasUpdated;
    }

    /**
     * Sets state of canvas. If true then canvas will be redrawn.
     * @param canvasUpdated state of canvas.
     */
    public void setCanvasUpdated(boolean canvasUpdated) {
        this.canvasUpdated = canvasUpdated;
    }

    /**
     * The method checks if given offset is valid
     * @param offset position in internal screen buffer
     * @throws IllegalArgumentException if offset is incorrect
     */
    private void checkBounds(int offset) throws IllegalArgumentException {
        if (offset < 0 || offset >= WIDTH * HEIGHT) {
            throw new IllegalArgumentException("Invalid offset in screen buffer");
        }
    }
}
