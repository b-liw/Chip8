package pl.bliw.emulator.io;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class KeyboardTest {
    private Keyboard keyboard;
    private String correctKeyCode = "1";
    private Integer keyIndexInArray = 1;

    @Before
    public void before() {
        keyboard = new Keyboard();
    }

    @Test
    public void pressedKeyShouldHaveStateOfTrue() {
        keyboard.keyPressed(correctKeyCode);
        assertThat(true, is(equalTo(keyboard.isPressed(keyIndexInArray))));
    }

    @Test
    public void testDefaultStateOfKey() {
        assertThat(false, is(equalTo(keyboard.isPressed(keyIndexInArray))));
    }

    @Test
    public void releasedKeyShouldHaveStateOfFalse() {
        keyboard.keyReleased(correctKeyCode);
        assertThat(false, is(equalTo(keyboard.isPressed(keyIndexInArray))));
    }
}
