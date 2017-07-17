package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by summers on 7/17/17.
 */
public class SixteenBitMathTest {

    @ParameterizedTest
    @CsvSource({
            "'0x21, 0x10, 0x10, 0x01, 0x01, 0x01, 0x09'", //Load 0x1010 into HL, Load 0x0101 into BC, add BC to HL
            "'0x21, 0x10, 0xFF, 0x01, 0x01, 0x01, 0x09'",  //Load 0xFF10 into HL, Load 0x0101 into BC, add BC to HL.
            "'0x21, 0x10, 0x10, 0x11, 0x01, 0x01, 0x19'", //Load 0x1010 into HL, Load 0x0101 into DE, add DE to HL
            "'0x21, 0x10, 0xFF, 0x11, 0x01, 0x01, 0x19'",  //Load 0xFF10 into HL, Load 0x0101 into DE, add DE to HL.
            "'0x21, 0x01, 0xFF, 0x21, 0x01, 0xFF, 0x29'", //Load 0xFF10 into HL, Load 0xFF01 into HL, add HL to HL
            "'0x21, 0x01, 0x01, 0x21, 0x01, 0x01, 0x29'",  //Load 0x1010 into HL, Load 0x0101 into HL, add HL to HL.
            "'0x21, 0x10, 0x10, 0x31, 0x01, 0x01, 0x39'", //Load 0x1010 into HL, Load 0x0101 into SP, add SP to HL
            "'0x21, 0x10, 0x10, 0x31, 0x01, 0xFF, 0x39'",  //Load 0x1010 into HL, Load 0xFF01 into SP, add SP to HL.
    })
    public void testAddHL(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        int fullAugend = ((memory[2]/*augend high*/ << 8 ) & 0xFF00) | memory[1];
        int fullAddend = ((memory[5]/*addend high*/ << 8 ) & 0xFF00) | memory[4];
        int sum = fullAddend + fullAugend;
        
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        z80.cycle(31);

        assertEquals (7, z80.getPC());
        assertEquals (sum & 0xFFFF, z80.getHL());
        assertEquals(false, z80.getFlagN()); // No Subtraction
        assertEquals(sum > 0xFFFF, z80.getFlagC()); // If overflowed
        assertEquals((fullAugend & 0x0FFF) + (fullAddend& 0x0FFF) > 0x0FFF, z80.getFlagH()); // If carry from bit 11 to 12
    }
}
