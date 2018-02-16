package pl.bliw.util;

public class PerformanceCounter {

    private int FPS;
    private int UPS;
    private int framesCounter;
    private int updatesCounter;
    private long nanoSecondsTime;
    private long lastTime;

    public PerformanceCounter() {
        lastTime = System.nanoTime();
    }

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

    public int getFPS() {
        return FPS;
    }

    public int getUPS() {
        return UPS;
    }

}
