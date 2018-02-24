package pl.bliw.emulator.cpu;


import org.junit.Before;
import org.junit.Test;
import pl.bliw.util.Constants;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static pl.bliw.emulator.cpu.Registers.AvailableRegisters;
import static pl.bliw.emulator.cpu.Registers.AvailableRegisters.V0;
import static pl.bliw.emulator.cpu.Registers.AvailableRegisters.values;

public class RegistersTest {
    private Registers registers;

    @Before
    public void before() {
        registers = new Registers();
    }

    @Test
    public void allRegistersExcludingPCShouldHoldZeroAfterCreation() {
        int expectedValueOfRegister = 0;
        for (AvailableRegisters registerID : values()) {
            int actualValueOfRegister = registers.get(registerID);
            assertThat(expectedValueOfRegister, is(equalTo(actualValueOfRegister)));
        }
        assertThat(expectedValueOfRegister, is(equalTo(registers.getI())));
    }

    @Test
    public void testIfPcstartsAtRomOffset() {
        assertThat(Constants.ROM_CODE_OFFSET, is(equalTo(registers.getPC())));
    }

    @Test
    public void registerShouldHoldValueAfterSet() {
        int expected = 0x50;
        registers.set(V0, expected);
        assertThat(expected, is(equalTo(registers.get(V0))));

        registers.setI(expected);
        assertThat(expected, is(equalTo(registers.getI())));

        registers.setPC(expected);
        assertThat(expected, is(equalTo(registers.getPC())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowExceptionWhenRegisterIndexIsIncorrect() {
        int invalidRegisterIndex = 17;
        registers.get(invalidRegisterIndex);
    }

    @Test
    public void checkIfIncrementPcRegisterBYValueWorks() {
        final int startValue = 1000;
        final int step = 5;
        registers.setPC(startValue);
        registers.incrementPC(step);
        assertThat(registers.getPC(), is(equalTo(startValue + step)));
    }
}
