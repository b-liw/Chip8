package pl.bliw.emulator.io;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ScreenTest {
    private Screen screen;
    private int correctOffsetInInternalArray = 10;
    private int incorrectOffsetInInternalArray = -1;

    @Before
    public void before() {
        screen = new Screen();
    }

    @Test
    public void testFlipPixel() {
        screen.flipPixel(correctOffsetInInternalArray);
        assertThat(true, is(equalTo(screen.getPixel(correctOffsetInInternalArray))));
        screen.flipPixel(correctOffsetInInternalArray);
        assertThat(false, is(equalTo(screen.getPixel(correctOffsetInInternalArray))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void gettingValueFromBufferUsingWrongOffsetShouldThrowException() {
        screen.getPixel(incorrectOffsetInInternalArray);
    }

    @Test
    public void everyStateOfPixelShouldBeFalseAfterClear() {
        screen.clear();
        assertThat(Arrays.asList(screen.getScreenState()), everyItem(is(equalTo(false))));
    }
}