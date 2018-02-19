package pl.bliw.emulator.cpu;

public class Timers {

    private int delayTimer;
    private int soundTimer;

    public int getDelayTimer() {
        return delayTimer;
    }

    public int getSoundTimer() {
        return soundTimer;
    }

    public void setDelayTimer(int value) {
        this.delayTimer = delayTimer;
    }

    public void setSoundTimer(int value) {
        this.soundTimer = soundTimer;
    }

    public void decrementSoundTimer() {
        if (soundTimer > 0) {
            this.soundTimer--;
        }
    }

    public void decrementDelayTimer() {
        if (delayTimer > 0) {
            this.delayTimer--;
        }
    }

}
