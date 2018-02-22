package pl.bliw.util;

/**
 * The performance class counts frames per second and updates per second.
 */
public class PerformanceCounter {
    /**
     * The frames per second counter updated every second.
     */
    private int FPS;

    /**
     * The updates per second counter updated every second.
     */
    private int UPS;

    /**
     * The counter that counts the frames for one second.
     */
    private int framesCounter;

    /**
     * The counter that counts the updates for one second.
     */
    private int updatesCounter;

    /**
     * The counter that counts the time up to one second.
     */
    private long nanoSecondsTime;

    /**
     * Stores previous time in nano seconds.
     */
    private long lastTime;

    /**
     * Constructs a new PerformanceCounter instance.
     */
    public PerformanceCounter() {
        lastTime = System.nanoTime();
    }

    /**
     * The method counts FPS and UPS and updates total counters which are available for a user.
     */
    public void count() {
        long now = System.nanoTime();
        long delta = now - lastTime;
        nanoSecondsTime += delta;
        lastTime = now;
        double seconds = nanoSecondsTime / Constants.NANO_SECONDS_FACTOR;
        if (seconds >= 1) {
            FPS = framesCounter;
            UPS = updatesCounter;
            nanoSecondsTime = 0;
            updatesCounter = 0;
            framesCounter = 0;
        } else {
            updatesCounter++;
            framesCounter++;
        }
    }

    /**
     * Returns FPS.
     *
     * @return frames per second
     */
    public int getFPS() {
        return FPS;
    }

    /**
     * Returns UPS.
     * @return updates per second
     */
    public int getUPS() {
        return UPS;
    }
}
