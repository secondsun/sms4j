package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.Z80;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * This class tests the instruction decoder for the z80 emulator
 *
 * @author secondsun
 */
public class E01_Z80DecodeTest {

    /**
     * The Program Count (PC) is a register which keeps a reference to the
     * memory address that the next instruction starts at.
     *
     * Instructions on the z80 may be up to 4 bytes. For now, let's focus on the
     * NOP instruction and the program counter. NOP means no operation and it
     * serves to make the CPU wait for a cycle and increment the PC.
     */
    @Test
    public void testNOPAndPCIncrement() {
        final Z80 z80 = new Z80();
        z80.setPC(0); //The PC defaults to 0, but being explicit for documentation
        z80.setMemory(new int[]{0, 0, 0}); //Memory is just a collection of bytes.
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(1, z80.getPC());
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(3, z80.getPC());
    }

    /**
     * This test will run through the LD instructions which set a immediate
     * value to a register. These are multi byte instructions.
     *
     * See http://z80.info/decoding.htm#upfx for the details.
     */
    @Test
    public void test8bitRegisterLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80 z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x06, 0xFF, 0xFF}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(2, z80.getPC());
        Assert.assertEquals(0xFF, z80.getB());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x16, 0xEF, 0xFF}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(2, z80.getPC());
        Assert.assertEquals(0xEF, z80.getD());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x26, 0xFA, 0xFF}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(2, z80.getPC());
        Assert.assertEquals(0xFA, z80.getH());
        
         z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x0E, 0x7D, 0xFF}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(2, z80.getPC());
        Assert.assertEquals(0x7d, z80.getC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x1E, 0x01, 0xFF}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(2, z80.getPC());
        Assert.assertEquals(0x01, z80.getE());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x2E, 0xBD, 0xFF}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(2, z80.getPC());
        Assert.assertEquals(0xBD, z80.getL());
        
        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x3E, 0x98, 0xFF}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(2, z80.getPC());
        Assert.assertEquals(0x98, z80.getA());

    }

    /**
     * This test will run through the LD instructions which set a immediate
     * value to a register. These are multi byte instructions.
     *
     * See http://z80.info/decoding.htm#upfx for the details.
     */
    @Test
    public void test16bitRegisterLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80 z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xFF, 0xFF}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(3, z80.getPC());
        Assert.assertEquals(0xFFFF, z80.getBC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xEF, 0xFE}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(3, z80.getPC());
        Assert.assertEquals(0xEFFE, z80.getDE());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x0D, 0xDF}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(3, z80.getPC());
        Assert.assertEquals(0x0DDF, z80.getHL());

        
        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x31, 0x0D, 0xD0}); //Load into BC value 0xFFFF
        z80.executeNextInstruction();//Fetch next instruction, execute, and incriment the program counter
        Assert.assertEquals(3, z80.getPC());
        Assert.assertEquals(0x0DD0, z80.getSP());

    }

    /**
     * From http://z80.info/decoding.htm
     *
     * Z80 instructions may begin with one or two prefixes. These prefixes are
     * always one of the following values : CB, DD, ED, or FD.
     *
     */
    @Test
    public void testIsPrefix() {
        final Z80 z80 = new Z80();
        int isPrefix = 0xCB;
        assertTrue(z80.isPrefix(isPrefix));
        isPrefix = 0xDD;
        assertTrue(z80.isPrefix(isPrefix));
        isPrefix = 0xED;
        assertTrue(z80.isPrefix(isPrefix));
        isPrefix = 0xFD;
        assertTrue(z80.isPrefix(isPrefix));

    }

    /**
     * Instructions have one of two formats :
     *
     * [prefix byte,] opcode [,displacement byte] [,immediate data] - OR - two
     * prefix bytes, displacement byte, opcode
     *
     * Of notes, values in brackets are optional
     *
     * In this step we will create an instance of the "Instruction" object that
     * will encapsulate the values of a valid instruction for processing by Z80.
     *
     * We will also create a program counter and a memory field to serve as the
     * collection of bytes.
     *
     */
    @Test
    public void testCreateInstruction() {

    }
}
