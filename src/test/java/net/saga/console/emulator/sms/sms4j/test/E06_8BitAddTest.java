package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class E06_8BitAddTest {

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x44, 0x06, 0x11, 0x80'", //LD a 0x44, LD b 0x11, ADD_A B
            "'0x3E, 0x44, 0x06, 0x0f, 0x80'",
            "'0x3E, 0x44, 0x06, 0xff, 0x80'",
            "'0x3E, 0x44, 0x06, 0x01, 0x80'",
            "'0x3E, 0xF4, 0x06, 0x11, 0x80'",
            "'0x3E, 0xF4, 0x06, 0x0f, 0x80'",
            "'0x3E, 0xF4, 0x06, 0xff, 0x80'",
            "'0x3E, 0xF4, 0x06, 0x01, 0x80'",
            "'0x3E, 0x44, 0x0E, 0x11, 0x81'", //LD a 0x44, LD C 0x11, ADD_A C
            "'0x3E, 0x44, 0x0E, 0x0f, 0x81'",
            "'0x3E, 0x44, 0x0E, 0xff, 0x81'",
            "'0x3E, 0x44, 0x0E, 0x01, 0x81'",
            "'0x3E, 0xF4, 0x0E, 0x11, 0x81'",
            "'0x3E, 0xF4, 0x0E, 0x0f, 0x81'",
            "'0x3E, 0xF4, 0x0E, 0xff, 0x81'",
            "'0x3E, 0xF4, 0x0E, 0x01, 0x81'"
    })
    public void test_Add_A_r(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        z80.cycle(18);

        byte regValue = (byte) (memory[3] & 0xFF);
        byte aValue = (byte) (memory[1] & 0xFF);
        int trueValue = regValue + aValue;
        byte maskedValue = (byte) (trueValue & 0xFF);
        assertEquals(maskedValue, z80.getA());
        assertEquals(maskedValue < 0, z80.getFlagS(), "Flag S contained the wrong value");
        assertEquals(z80.getA() == 0x00, z80.getFlagZ(), "Flag Z contained the wrong value");
        assertEquals((((0x0F & aValue) + (0x0F & regValue)) > 0x0F), z80.getFlagH(), "Flag H contained the wrong value");
        boolean overflow = trueValue > (byte)0x7F || trueValue < (byte)0x80;
        assertEquals(overflow, z80.getFlagPV(), "Flag P contained the wrong value");
        assertEquals(trueValue > 0xFF, z80.getFlagC(), "Flag C contained the wrong value");
    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x44, 0x21, 0x06, 0x00, 0x86, 0x11'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0x44, 0x21, 0x06, 0x00, 0x86, 0x0F'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0x44, 0x21, 0x06, 0x00, 0x86, 0xFF'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0x44, 0x21, 0x06, 0x00, 0x86, 0x01'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0xF4, 0x21, 0x06, 0x00, 0x86, 0x11'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0xF4, 0x21, 0x06, 0x00, 0x86, 0x0F'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0xF4, 0x21, 0x06, 0x00, 0x86, 0xFF'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0xF4, 0x21, 0x06, 0x00, 0x86, 0x01'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
    })
    public void test_Add_A_at_HL(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        z80.cycle(24);

        byte regValue = (byte) (memory[6] & 0xFF);
        byte aValue = (byte) (memory[1] & 0xFF);
        int trueValue = regValue + aValue;
        byte maskedValue = (byte) (trueValue & 0xFF);
        assertEquals(maskedValue, z80.getA());
        assertEquals(maskedValue < 0, z80.getFlagS(), "Flag S contained the wrong value");
        assertEquals(z80.getA() == 0x00, z80.getFlagZ(), "Flag Z contained the wrong value");
        assertEquals((((0x0F & aValue) + (0x0F & regValue)) > 0x0F), z80.getFlagH(), "Flag H contained the wrong value");
        boolean overflow = trueValue >(byte) 0x7F || trueValue < (byte)0x80;
        assertEquals(overflow, z80.getFlagPV(), "Flag P contained the wrong value");
        assertEquals(trueValue > 0xFF, z80.getFlagC(), "Flag C contained the wrong value");
    }


    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x44, 0x06, 0x11, 0x88, 0x00'", //LD a 0x44, LD b 0x11, ADC B, no carry
            "'0x3E, 0x44, 0x06, 0x0f, 0x88, 0x00'",
            "'0x3E, 0x44, 0x06, 0xff, 0x88, 0x00'",
            "'0x3E, 0x44, 0x06, 0x01, 0x88, 0x00'",
            "'0x3E, 0xF4, 0x06, 0x11, 0x88, 0x00'",
            "'0x3E, 0xF4, 0x06, 0x0f, 0x88, 0x00'",
            "'0x3E, 0xF4, 0x06, 0xff, 0x88, 0x00'",
            "'0x3E, 0xF4, 0x06, 0x01, 0x88, 0x00'",
            "'0x3E, 0x44, 0x06, 0x11, 0x88, 0x01'", //LD a 0x44, LD b 0x11, ADC B, carry
            "'0x3E, 0x44, 0x06, 0x0f, 0x88, 0x01'",
            "'0x3E, 0x44, 0x06, 0xff, 0x88, 0x01'",
            "'0x3E, 0x44, 0x06, 0x01, 0x88, 0x01'",
            "'0x3E, 0xF4, 0x06, 0x11, 0x88, 0x01'",
            "'0x3E, 0xF4, 0x06, 0x0f, 0x88, 0x01'",
            "'0x3E, 0xF4, 0x06, 0xff, 0x88, 0x01'",
            "'0x3E, 0xF4, 0x06, 0x01, 0x88, 0x01'"
    })
    public void test_AdC_r(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        boolean carry = memory[5] > 0;
        Z80 z80 = new Z80();
        z80.setCarry(carry);
        z80.setMemory(memory);
        z80.cycle(18);

        byte regValue = (byte) (memory[3] & 0xFF);
        byte aValue = (byte) (memory[1] & 0xFF);
        int trueValue = regValue + aValue;
        if (carry) {trueValue++;}
        byte maskedValue = (byte) (trueValue & 0xFF);
        assertEquals(maskedValue, z80.getA());
        assertEquals(maskedValue < 0, z80.getFlagS(), "Flag S contained the wrong value");
        assertEquals(z80.getA() == 0x00, z80.getFlagZ(), "Flag Z contained the wrong value");
        assertEquals((((0x0F & aValue) + (0x0F & regValue)) > 0x0F), z80.getFlagH(), "Flag H contained the wrong value");
        boolean overflow = trueValue > (byte)0x7F || trueValue < (byte)0x80;
        assertEquals(overflow, z80.getFlagPV(), "Flag P contained the wrong value");
        assertEquals(trueValue > 0xFF, z80.getFlagC(), "Flag C contained the wrong value");
    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x44, 0x21, 0x06, 0x00, 0x86, 0x11'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0x44, 0x21, 0x06, 0x00, 0x86, 0x0F'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0x44, 0x21, 0x06, 0x00, 0x86, 0xFF'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0x44, 0x21, 0x06, 0x00, 0x86, 0x01'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0xF4, 0x21, 0x06, 0x00, 0x86, 0x11'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0xF4, 0x21, 0x06, 0x00, 0x86, 0x0F'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0xF4, 0x21, 0x06, 0x00, 0x86, 0xFF'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
            "'0x3E, 0xF4, 0x21, 0x06, 0x00, 0x86, 0x01'", //LD a 0x44, LD HL 0x06, ADD_A (HL)
    })
    public void test_AdC_at_HL(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        z80.cycle(24);

        byte regValue = (byte) (memory[6] & 0xFF);
        byte aValue = (byte) (memory[1] & 0xFF);
        int trueValue = regValue + aValue;
        byte maskedValue = (byte) (trueValue & 0xFF);
        assertEquals(maskedValue, z80.getA());
        assertEquals(maskedValue < 0, z80.getFlagS(), "Flag S contained the wrong value");
        assertEquals(z80.getA() == 0x00, z80.getFlagZ(), "Flag Z contained the wrong value");
        assertEquals((((0x0F & aValue) + (0x0F & regValue)) > 0x0F), z80.getFlagH(), "Flag H contained the wrong value");
        boolean overflow = trueValue > (byte)0x7F || trueValue < (byte)0x80;
        assertEquals(overflow, z80.getFlagPV(), "Flag P contained the wrong value");
        assertEquals(trueValue > 0xFF, z80.getFlagC(), "Flag C contained the wrong value");
    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x44, 0xC6, 0x11, 0x76'", //Load A n, ADD A 11, HALT
            "'0x3E, 0x44, 0xC6, 0x0F, 0x76'",
            "'0x3E, 0x44, 0xC6, 0xFF, 0x76'",
            "'0x3E, 0x44, 0xC6, 0x01, 0x76'",
            "'0x3E, 0xF4, 0xC6, 0x11, 0x76'",
            "'0x3E, 0xF4, 0xC6, 0x0F, 0x76'",
            "'0x3E, 0xF4, 0xC6, 0xFF, 0x76'",
            "'0x3E, 0xF4, 0xC6, 0x01, 0x76'"
    })
    public void testAddImmediate(@ConvertWith(ByteArrayConverter.class) byte[] memory) {

        byte val = memory[1];
        byte val2 = memory[3];
        int trueSum = val + val2;
        byte byteSum = (byte) (trueSum & 0xFF);

        Z80 z80 = new Z80();
        z80.setMemory(memory);
        z80.cycle(15);



        assertEquals(5, z80.getPC());
        assertEquals(byteSum, z80.getA());

        assertEquals(byteSum < 0, z80.getFlagS(), "Flag S contained the wrong value");
        assertEquals(z80.getA() == 0x00, z80.getFlagZ(), "Flag Z contained the wrong value");
        assertEquals(((0x0F & val2) + (0x0F & val)) > 0x0F, z80.getFlagH(), "Flag H contained the wrong value");
        boolean overflow = trueSum > (byte)0x7F || trueSum < (byte)0x80;
        assertEquals(overflow, z80.getFlagPV(), "Flag P contained the wrong value");
        assertEquals(trueSum > 0xFF, z80.getFlagC(), "Flag C contained the wrong value");


    }
}
