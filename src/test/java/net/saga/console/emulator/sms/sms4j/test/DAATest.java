package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DAATest {
    /**
     * This test is a marker that while a halt instruction exists, the halt implementation doesn't outside of a trivial case
     */
    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x62, 0x06, 0x65, 0x80, 0x27,0x76, 0xC7, 0x00'",//LD a *, LD b *, ADD b, DAA, HALT, $expected, $C
            "'0x3E, 0x00, 0x06, 0x01, 0x80, 0x27,0x76, 0x01, 0x00'",
    })//
    public void testDAA(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80(memory);
        z80.executeUntilHalt();

        byte expected = memory[7];
        boolean c = memory[8]>0;
        assertEquals(expected, z80.getA());
        assertEquals(c, z80.getFlagC());
        assertEquals(z80.getA() == 0, z80.getFlagZ());
        assertEquals(z80.getA() < 0, z80.getFlagS());
        //assertEquals(false, z80.getFlagPV(), "need to implement parity");;

    }
}
