package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExchangeTest {

    @Test
    public void testExchangeAF() {
        byte[] memory =new byte[]{0x3E, 0x44, 0x37, 0x08, 0x76}; //LD a 0x44, SCF, EX AF, AF', HALT;
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        z80.cycle(8);

        assertTrue(z80.getFlagC());
        z80.cycle(4);

        assertEquals(4, z80.getPC());
        assertFalse(z80.getFlagC());
    }

    @Test
    public void testExchangeDE() {
        byte[] memory =new byte[]{0x11, 0x44, 0x37, 0x21, 0x34, 0x12, (byte) 0xEB, 0x76 }; //LD DE 0x3744, LD HL 0x1234, ex de, hl, HALT
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        z80.executeUntilHalt();
        assertEquals(0x1234, z80.getRegisterDE().getValue());
        assertEquals(0x3744, z80.getRegisterHL().getValue());
    }

    @Test
    public void testExchangeSPmem() {
       throw new RuntimeException("PLease implement");
        /**
         * HL register pair contains 7012h
         * SP register pair contains 8856h
         * memory location 8856h contains byte 11h,
         * memory location 8857h contains byte 22h
         * instruction EX (SP), HL
         * HL register pair containing number 2211h
         * memory location 8856h containing byte 12h
         * memory location 8857h containing byte 70h
         * Stack Pointer containing 8856h
         */


    }

}
