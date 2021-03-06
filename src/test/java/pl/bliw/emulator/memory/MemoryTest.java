package pl.bliw.emulator.memory;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MemoryTest {
    private Memory memory;
    private int tooLargeOffset;
    private int negativeOffset = -1;
    private int exampleCorrectByte = 0x40;
    private short[] exampleValues = new short[]{10, 200, 50, 90, 100, 21, 33};

    @Before
    public void before() {
        memory = new Memory();
        tooLargeOffset = memory.getMemorySize();
    }

    @Test
    public void testIfWriteWorksCorrectly() {
        for (int i = 0; i < exampleValues.length; i++) {
            memory.write(i, exampleValues[i]);
        }
        for (int i = 0; i < exampleValues.length; i++) {
            assertThat(exampleValues[i], is(equalTo(memory.read(i))));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void readMemoryShouldThrowExceptionWhenOffsetIsTooLarge() {
        memory.read(tooLargeOffset);
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeMemoryShouldThrowExceptionWhenOffsetIsTooLarge() {
        memory.write(tooLargeOffset, exampleCorrectByte);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readMemoryShouldThrowExceptionWhenOffsetIsNegative() {
        memory.read(negativeOffset);
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeMemoryShouldThrowExceptionWhenOffsetIsNegative() {
        memory.write(negativeOffset, exampleCorrectByte);
    }
}
