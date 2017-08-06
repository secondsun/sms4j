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


    @ParameterizedTest
    @CsvSource({
            "'0x3E, 0xFF, 0xC6, 0x01, 0x30, 0x04, 0x76, 0x76, 0x76, 0x76, 0x07'", //Load A n, ADD A 1, JR NC 0x04, HALT, HALT, HALT, HALT, $FinalAddress
            "'0x3E, 0x00, 0xC6, 0x01, 0x30, 0x04, 0x76, 0x76, 0x76, 0x76, 0x09'"
    })
    public void testJR_NC(@ConvertWith(ByteArrayConverter.class) byte[] memory)
    {
        Z80 z80 = new Z80();
        z80.setMemory(memory);
        while (!z80.isHalt()) {
            z80.cycle();
        }


        assertEquals(memory[10], z80.getPC());

    }
//
//    @Test
//    public void testUnconditionalJump() {
//        Z80 z80 = new Z80();
//        z80.setMemory(new byte[]{0x18, 0x05,0x76,0x76,0x76,0x76});
//        while (!z80.isHalt()) {
//            z80.cycle();
//        }
//
//        Assertions.assertEquals(0x06, z80.getPC());
//
//    }
//
//    @ParameterizedTest
//    @CsvSource({"'0x06, 0x40, 0x0E, 0x40, 0x0D, 0x10, 0xFF, 0x76'",//LD B loops, LD C 64, DEC C, DJNZ - 1, HALT
//                "'0x06, 0x20, 0x0E, 0x40, 0x0D, 0x10, 0xFF, 0x76'"})
//    public void testDJNZ(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
//
//        byte loops = memory[1];
//        Z80 z80 = new Z80();
//        z80.setMemory(memory);
//        while (!z80.isHalt()) {
//            z80.cycle();
//        }
//
//        assertEquals(64 - loops, z80.getC());
//        assertEquals(8, z80.getPC());
//        assertEquals(0, z80.getB());
//    }
//
//
//    @ParameterizedTest
//    @CsvSource({
//            "'0x3E, 0xFF, 0xB7, 0x20, 0x04, 0x76, 0x76, 0x76, 0x76, 0x08'", //Load A n, OR A, JR NZ 0x04, HALT, HALT, HALT, HALT, $FinalAddress
//            "'0x3E, 0x00, 0xB7, 0x20, 0x04, 0x76, 0x76, 0x76, 0x76, 0x06'"
//    })
//    public void testJR_NZ(@ConvertWith(ByteArrayConverter.class) byte[] memory)
//    {
//        Z80 z80 = new Z80();
//        z80.setMemory(memory);
//        while (!z80.isHalt()) {
//            z80.cycle();
//        }
//
//
//        assertEquals(memory[9], z80.getPC());
//
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "'0x3E, 0xFF, 0xB7, 0x28, 0x04, 0x76, 0x76, 0x76, 0x76, 0x06'", //Load A n, OR A, JR Z 0x04, HALT, HALT, HALT, HALT, $FinalAddress
//            "'0x3E, 0x00, 0xB7, 0x28, 0x04, 0x76, 0x76, 0x76, 0x76, 0x08'"
//    })
//    public void testJR_Z(@ConvertWith(ByteArrayConverter.class) byte[] memory)
//    {
//        Z80 z80 = new Z80();
//        z80.setMemory(memory);
//        while (!z80.isHalt()) {
//            z80.cycle();
//        }
//
//
//        assertEquals(memory[9], z80.getPC());
//
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "'0x3E, 0xFF, 0x3C, 0xC6, 0x01, 0x38, 0x04, 0x76, 0x76, 0x76, 0x76, 0x09'", //Load A n, ADD A 1, JR C 0x04, HALT, HALT, HALT, HALT, $FinalAddress
//            "'0x3E, 0x00, 0x3C, 0xC6, 0x01, 0x38, 0x04, 0x76, 0x76, 0x76, 0x76, 0x08'"
//    })
//    public void testJR_C(@ConvertWith(ByteArrayConverter.class) byte[] memory)
//    {
//        Z80 z80 = new Z80();
//        z80.setMemory(memory);
//        while (!z80.isHalt()) {
//            z80.cycle();
//        }
//
//
//        assertEquals(memory[11], z80.getPC());
//
//    }
//

//        [Test]
//            [TestCase(0xFF, 0x07)]
//            [TestCase(0x00, 0x09)]
//    public void Test_JP_NC_nn(byte val, short addr)
//    {
//        asm.LoadRegVal(7, val);
//        asm.IncReg(7);
//        asm.JpNc(0x0008);
//        asm.Halt();
//        asm.Halt();
//        asm.Halt();
//
//        en.Run();
//
//        Assert.AreEqual(addr, en.PC);
//    }
//        [Test]
//            [TestCase(0xFF, 0x09)]
//            [TestCase(0x00, 0x07)]
//    public void Test_JP_C_nn(byte val, short addr)
//    {
//        asm.LoadRegVal(7, val);
//        asm.IncReg(7);
//        asm.JpC(0x0008);
//        asm.Halt();
//        asm.Halt();
//        asm.Halt();
//
//        en.Run();
//
//        Assert.AreEqual(addr, en.PC);
//    }
    
}
