package pl.bliw.emulator.cpu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.bliw.emulator.cpu.Registers.AvailableRegisters;
import static pl.bliw.emulator.cpu.Registers.AvailableRegisters.V0;
import static pl.bliw.emulator.cpu.Registers.AvailableRegisters.values;

public class RegistersTest {

    private static final int ROM_CODE_OFFSET = 0x200;
    private Registers registers;

    @Before
    public void before() {
        registers = new Registers();
    }

    @Test
    public void allRegistersExcludingPCShouldHoldZeroAfterCreation() {
        int expected = 0;
        for (AvailableRegisters registerID : values()) {
            int actual = registers.get(registerID);
            assertEquals(expected, actual);
        }
        assertEquals(expected, registers.getI());
    }

    @Test
    public void testIfPCstartsAtRomOffset() {
        assertEquals(ROM_CODE_OFFSET, registers.getPC());
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
