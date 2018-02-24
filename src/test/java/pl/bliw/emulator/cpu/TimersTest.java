package pl.bliw.emulator.cpu;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TimersTest {
    private Timers timers;

    @Before
    public void before() {
        timers = new Timers();
    }

    @Test
    public void testTimerDecrement() {
        int initialTimerValue = 100;
        timers.setSoundTimer(initialTimerValue);
        timers.setDelayTimer(initialTimerValue);
        timers.decrementSoundTimer();
        timers.decrementDelayTimer();
        assertThat(initialTimerValue - 1, is(equalTo(timers.getDelayTimer())));
        assertThat(initialTimerValue - 1, is(equalTo(timers.getSoundTimer())));
    }

    @Test
    public void timerShouldNotDecrementBelowZero() {
        int initialTimerValue = 1;
        int minimumValueOfTimer = 0;
        timers.setSoundTimer(initialTimerValue);
        timers.setDelayTimer(initialTimerValue);
        timers.decrementSoundTimer();
        timers.decrementSoundTimer();
        timers.decrementDelayTimer();
        timers.decrementDelayTimer();
        assertThat(minimumValueOfTimer, is(equalTo(timers.getDelayTimer())));
        assertThat(minimumValueOfTimer, is(equalTo(timers.getSoundTimer())));
    }
}
