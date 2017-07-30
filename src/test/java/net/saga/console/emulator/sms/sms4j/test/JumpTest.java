package net.saga.console.emulator.sms.sms4j.test;

import com.sun.xml.internal.ws.policy.AssertionSet;
import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JumpTest {

    @Test
    public void testUnconditionalJump() {
        Z80 z80 = new Z80();
        z80.setMemory(new byte[]{0x18, 0x05,0x76,0x76,0x76,0x76});
        while (!z80.isHalt()) {
            z80.cycle();
        }

        Assertions.assertEquals(0x06, z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({"'0x06, 0x40, 0x0E, 0x40, 0x0D, 0x10, 0xFF, 0x76'",//LD B loops, LD C 64, DEC C, DJNZ - 1, HALT
                "'0x06, 0x20, 0x0E, 0x40, 0x0D, 0x10, 0xFF, 0x76'"})
    public void testDJNZ(@ConvertWith(ByteArrayConverter.class) byte[] memory) {

        byte loops = memory[1];
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(64 - loops, z80.getC());
        assertEquals(8, z80.getPC());
        assertEquals(0, z80.getB());
    }
}
