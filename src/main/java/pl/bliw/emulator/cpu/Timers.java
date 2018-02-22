package pl.bliw.emulator.cpu;

/**
 * The Timers class encapsulates two timers which are in Chip8.
 */
public class Timers {
    /**
     * The counter for delay timer.
     */
    private int delayTimer;

    /**
     * The counter for sound timer.
     */
    private int soundTimer;

    /**
     * @return value of delay timer.
     */
    public int getDelayTimer() {
        return delayTimer;
    }

    /**
     * Sets delay timer value to given value.
     *
     * @param value new value for delay timer.
     */
    public void setDelayTimer(int value) {
        this.delayTimer = value;
    }

    /**
     * @return value of sound timer.
     */
    public int getSoundTimer() {
        return soundTimer;
    }

    /**
     * Sets sound timer value to given value.
     * @param value new value for sound timer.
     */
    public void setSoundTimer(int value) {
        this.soundTimer = value;
    }

    /**
     * Decrements sound timer by one if current value of sound timer is greater than 0.
     */
    public void decrementSoundTimer() {
        if (soundTimer > 0) {
            this.soundTimer--;
        }
    }

    /**
     * Decrements delay timer by one if current value of delay timer is greater than 0.
     */
    public void decrementDelayTimer() {
        if (delayTimer > 0) {
            this.delayTimer--;
        }
    }
}
