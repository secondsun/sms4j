package net.saga.console.emulator.sms.sms4j.test;

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
        Z80 z80 = new Z80(new byte[]{0x18, 0x05,0x76,0x76,0x76,0x76});

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
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(64 - loops, z80.getC());
        assertEquals(8, z80.getPC());
        assertEquals(0, z80.getB());
    }


    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0xFF, 0xB7, 0x20, 0x04, 0x76, 0x76, 0x76, 0x76, 0x08'", //Load A n, OR A, JR NZ 0x04, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0xB7, 0x20, 0x04, 0x76, 0x76, 0x76, 0x76, 0x06'"
    })
    public void testJR_NZ(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }


        assertEquals(memory[9], z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0xFF, 0xB7, 0x28, 0x04, 0x76, 0x76, 0x76, 0x76, 0x06'", //Load A n, OR A, JR Z 0x04, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0xB7, 0x28, 0x04, 0x76, 0x76, 0x76, 0x76, 0x08'"
    })
    public void testJR_Z(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }


        assertEquals(memory[9], z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0xFF, 0xc6, 0x01, 0x38, 0x05, 0x76, 0x76, 0x76, 0x76, 0x0A'", //Load A n, ADD A 1, JR C 0x04, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0xc6, 0x01, 0x38, 0x05, 0x76, 0x76, 0x76, 0x76, 0x07'"
    })
    public void testJR_C(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }


        assertEquals(memory[10], z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0xFF, 0xc6, 0x01, 0x30, 0x05, 0x76, 0x76, 0x76, 0x76, 0x07'", //Load A n, ADD A 1, JR C 0x04, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0xc6, 0x01, 0x30, 0x05, 0x76, 0x76, 0x76, 0x76, 0x0A'"
    })
    public void testJR_NC(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }


        assertEquals(memory[10], z80.getPC());

    }

    @Test
    public void testJP_nn()
    {
        Z80 z80 = new Z80(new byte[] {(byte) 0xC3, 0x05,0x00, 0x76,0x76,0x76, 0x76});

        while (!z80.isHalt()) {
            z80.cycle();
        }


        assertEquals(6, z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0xFF, 0xB7, 0xC2, 0x08, 0x00, 0x76, 0x76, 0x76, 0x76, 0x09'", //Load A n, OR A , JP NZ 0x84, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0xB7, 0xC2, 0x08, 0x00, 0x76, 0x76, 0x76, 0x76, 0x07'"
    })
    public void testJP_NZ(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[10], z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0xFF, 0xB7, 0xCA, 0x08, 0x00, 0x76, 0x76, 0x76, 0x76, 0x07'", //Load A n, OR A , JP NZ 0x84, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0xB7, 0xCA, 0x08, 0x00, 0x76, 0x76, 0x76, 0x76, 0x09'"
    })
    public void testJP_Z(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[10], z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0xFF, 0xC6, 0x01, 0xD2, 0x09, 0x00, 0x76, 0x76, 0x76, 0x76, 0x08'", //Load A n, ADD A 1 , JP NC 0x0008, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0xC6, 0x01, 0xD2, 0x09, 0x00, 0x76, 0x76, 0x76, 0x76, 0x0A'"
    })
    public void testJP_NC(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[11], z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0xFF, 0xC6, 0x01, 0xDA, 0x09, 0x00, 0x76, 0x76, 0x76, 0x76, 0x0A'", //Load A n, ADD A 1 , JP NC 0x0008, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0xC6, 0x01, 0xDA, 0x09, 0x00, 0x76, 0x76, 0x76, 0x76, 0x08'"
    })
    public void testJP_C(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[11], z80.getPC());

    }


    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x7F, 0x3C, 0xE2, 0x09, 0x00, 0x76, 0x76, 0x76, 0x76, 0x07'", //Load A n, ADD A 1 , JP PO 0x0008, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0x3C, 0xE2, 0x09, 0x00, 0x76, 0x76, 0x76, 0x76, 0x0A'"
    })
    public void testJP_PO(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[10], z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x7F, 0x3C, 0xEA, 0x09, 0x00, 0x76, 0x76, 0x76, 0x76, 0x0A'", //Load A n, ADD A 1 , JP PO 0x0008, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0x3C, 0xEA, 0x09, 0x00, 0x76, 0x76, 0x76, 0x76, 0x07'"
    })
    public void testJP_PE(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[10], z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x01, 0xb7, 0xF2, 0x08, 0x00, 0x76, 0x76, 0x76, 0x76, 0x09'", //Load A n, ADD A 1 , JP PO 0x0008, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x80, 0xb7, 0xF2, 0x08, 0x00, 0x76, 0x76, 0x76, 0x76, 0x07'"
    })
    public void testJP_P(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[10], z80.getPC());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0x01, 0xb7, 0xFA, 0x08, 0x00, 0x76, 0x76, 0x76, 0x76, 0x07'", //Load A n, ADD A 1 , JP PO 0x0008, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x80, 0xb7, 0xFA, 0x08, 0x00, 0x76, 0x76, 0x76, 0x76, 0x09'"
    })
    public void testJP_M(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }

        assertEquals(memory[10], z80.getPC());

    }


}
