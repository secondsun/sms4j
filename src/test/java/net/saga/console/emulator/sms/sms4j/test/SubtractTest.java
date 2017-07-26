package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtractTest {
    @ParameterizedTest
    @CsvSource({"'0x3E, 0x44, 0x06, 0x11, 0x90'"})//LD a 0x44, LD b 0x11, SUB A B
    public void testSubtract(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        z80.cycle(18);

        int regValue = memory[3] & 0xFF;
        int aValue = memory[1] & 0xFF;
        int trueValue = aValue - regValue;

        byte maskedValue = (byte) (trueValue & 0xFF);
        assertEquals(maskedValue, z80.getA());
        assertEquals(maskedValue < 0, z80.getFlagS(), "Flag S contained the wrong value");
        assertEquals(z80.getA() == 0x00, z80.getFlagZ(), "Flag Z contained the wrong value");
        assertEquals((((0x0F & aValue) + (0x0F & regValue)) > 0x0F), z80.getFlagH(), "Flag H contained the wrong value");
        boolean overflow = trueValue > aValue;
        assertEquals(overflow, z80.getFlagPV(), "Flag P contained the wrong value");
        assertEquals(trueValue > 0xFF, z80.getFlagC(), "Flag C contained the wrong value");

    }
}
