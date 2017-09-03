package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CPLTest {
    /**
     * This test is a marker that while a halt instruction exists, the halt implementation doesn't outside of a trivial case
     */
    @Test
    public void testCPLIsImplemented() {
        Z80 z80 = new Z80(new byte[]{0x2F});

        z80.getRegisterA().setValue(0b10101010);
        z80.cycle(4);

        assertEquals(0b01010101, z80.getA());
        assertEquals(true, z80.getFlagH());
        assertEquals(true, z80.getFlagN());
        assertEquals(false, z80.getFlagC());
        assertEquals(1, z80.getPC());

    }
}
