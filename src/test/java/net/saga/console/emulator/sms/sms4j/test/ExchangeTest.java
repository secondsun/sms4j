package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExchangeTest {

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x44, 0x37, 0x08, 0x76'" //LD a 0x44, SCF, EX AF, AF', HALT
    })
    public void testExchange(@ConvertWith(ByteArrayConverter.class )byte[] memory) {
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        z80.cycle(8);

        assertTrue(z80.getFlagC());
        z80.cycle(4);

        assertEquals(4, z80.getPC());
        assertFalse(z80.getFlagC());
    }
}
