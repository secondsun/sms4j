package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RotateTest {

    @ParameterizedTest
    @CsvSource({"'0x3E, 0x81, 0x0f, 0x76, 0xC0'",
            "'0x3E, 0x23, 0x0f, 0x76, 0x91'"})
    public void testRRCA(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[4], z80.getA());
        throw new RuntimeException("Add flags checks");
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
        throw new RuntimeException("Add flags checks");
    }


    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x81, 0x17, 0x76, 0x03'",
            "'0x3E, 0x23, 0x17, 0x76, 0x46'",
            "'0x3E, 0x81, 0x17, 0x76, 0x03'",
            "'0x3E, 0x23, 0x17, 0x76, 0x46'",})
    public void testRLA(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[4], z80.getA());
        throw new RuntimeException("Add flags checks");
    }
}
