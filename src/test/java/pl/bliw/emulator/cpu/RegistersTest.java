package pl.bliw.emulator.cpu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
    public void allRegistersShouldHoldZeroAfterCreation() {
        int expected = 0;
        for (AvailableRegisters registerID : values()) {
            int actual = registers.get(registerID);
            assertEquals(expected, actual);
        }
        assertEquals(expected, registers.getI());
        assertEquals(expected, registers.getPC());
    }

    @Test
    public void registerShouldHoldValueAfterSet() {
        int expected = 0x50;
        registers.set(V0, expected);
        assertEquals(expected, registers.get(V0));

        registers.setI(expected);
        assertEquals(expected, registers.getI());

        registers.setPC(expected);
        assertEquals(expected, registers.getPC());
    }

    @After
    public void after() {
        registers = null;
    }

}
