package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RotateTest {

    @ParameterizedTest
    @CsvSource({"'0x3E, 0x81, 0x0f, 0x76, 0xC0'",//LD A,n RRCA
                "'0x3E, 0x24, 0x0f, 0x76, 0x12'",
                "'0x3E, 0x23, 0x0f, 0x76, 0x91'"})
    public void testRRCA(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.getRegisterF().setValue(0xFF);
        z80.setMemory(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[4], z80.getA());

        assertFalse(z80.getFlagN(), "Flag N should be reset");
        assertFalse(z80.getFlagH(), "Flag H should be reset");
        assertEquals(((memory[1] & 0x1) > 0), z80.getFlagC(), "Flag C should be set correctly");

    }

    @ParameterizedTest
    @CsvSource({"'0x3E, 0x81, 0x07, 0x76, 0x03'",
            "'0x3E, 0x23, 0x07, 0x76, 0x46'"})
    public void testRLCA(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[4], z80.getA());


        assertFalse(z80.getFlagN(), "Flag N should be reset");
        assertFalse(z80.getFlagH(), "Flag H should be reset");
        assertEquals(((memory[1] & 0x80) > 0), z80.getFlagC(), "Flag C should be set correctly");

    }


    @ParameterizedTest
    @CsvSource({"'0x3E, 0x81, 0x1f, 0x76, 0xC0, 0x1'",//LD A,n RRCA, $expected, $carry_init
            "'0x3E, 0x24, 0x1f, 0x76, 0x92, 0x1'",
            "'0x3E, 0x23, 0x1f, 0x76, 0x91, 0x1'",
            "'0x3E, 0x81, 0x1f, 0x76, 0x40, 0x0'",//LD A,n RRCA, $expected, $carry_init
            "'0x3E, 0x24, 0x1f, 0x76, 0x12, 0x0'",
            "'0x3E, 0x23, 0x1f, 0x76, 0x11, 0x0'",})
    public void testRRA(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.getRegisterF().setValue(memory[5]);
        z80.setMemory(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[4], z80.getA());

        assertFalse(z80.getFlagN(), "Flag N should be reset");
        assertFalse(z80.getFlagH(), "Flag H should be reset");
        assertEquals(((memory[1] & 0x1) > 0), z80.getFlagC(), "Flag C should be set correctly");

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x81, 0x17, 0x76, 0x03, 0x1'",//LD A,n RLA, $expected, $carry_init
            "'0x3E, 0x24, 0x17, 0x76, 0x49, 0x1'",
            "'0x3E, 0x23, 0x17, 0x76, 0x47, 0x1'",
            "'0x3E, 0x81, 0x17, 0x76, 0x02, 0x0'",//LD A,n RLA, $expected, $carry_init
            "'0x3E, 0x24, 0x17, 0x76, 0x48, 0x0'",
            "'0x3E, 0x23, 0x17, 0x76, 0x46, 0x0'",})
    public void testRLA(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.getRegisterF().setValue(memory[5]);
        z80.setMemory(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[4], z80.getA());

        assertFalse(z80.getFlagN(), "Flag N should be reset");
        assertFalse(z80.getFlagH(), "Flag H should be reset");
        assertEquals(((memory[1] & 0x80) > 0), z80.getFlagC(), "Flag C should be set correctly");
        
    }

}
