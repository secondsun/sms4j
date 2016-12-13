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
public class E01_Z80ImmediateLoadTest {

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

    @Test
    public void testCopyRegisterToBLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80 z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xEE,
            0x40}); //Load into BC value 0xEEEE, the LD b,b (copy b into b)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xEE, z80.getB());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xFE,
            0x41}); //Load into BC value 0xEEFE, then LD b,c (copy c into b)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getB());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x42}); //Load into DE value 0xFEDE, the LD b,d (copy d into b)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getB());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x43}); //Load into DE value 0xFEDE, the LD b,e (copy E into B)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xDE, z80.getB());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x44}); //Load into HL value 0x1234, the LD b,H (copy H into b)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x12, z80.getB());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x45}); //Load into HL value 0x1234, the LD b,b (copy l into b)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x34, z80.getB());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x3E, 0xAD,
            0x47}); //Load into A value 0xAD, the LD b,a (copy a into b)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xAD, z80.getB());
        Assert.assertEquals(3, z80.getPC());

    }

    @Test
    public void testCopyRegisterToCLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80 z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xEE,
            0x48}); //Load into BC value 0xEEEE, the LD C,b (copy b into C)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xEE, z80.getC());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xFE,
            0x49}); //Load into BC value 0xEEFE, then LD c,c (copy c into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getC());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x4A}); //Load into DE value 0xFEDE, the LD c,d (copy d into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getC());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x4B}); //Load into DE value 0xFEDE, the LD c,e (copy E into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xDE, z80.getC());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x4C}); //Load into HL value 0x1234, the LD c,H (copy H into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x12, z80.getC());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x4D}); //Load into HL value 0x1234, the LD c,l (copy l into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x34, z80.getC());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x3E, 0xAD,
            0x4F}); //Load into A value 0xAD, the LD c,a (copy a into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xAD, z80.getC());
        Assert.assertEquals(3, z80.getPC());

    }

    @Test
    public void testCopyRegisterToDLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80 z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xEE,
            0x50}); //Load into BC value 0xEEEE, the LD C,b (copy b into C)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xEE, z80.getD());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xFE,
            0x51}); //Load into BC value 0xEEFE, then LD c,c (copy c into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getD());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x52}); //Load into DE value 0xFEDE, the LD c,d (copy d into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getD());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x53}); //Load into DE value 0xFEDE, the LD c,e (copy E into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xDE, z80.getD());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x54}); //Load into HL value 0x1234, the LD c,H (copy H into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x12, z80.getD());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x55}); //Load into HL value 0x1234, the LD c,l (copy l into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x34, z80.getD());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x3E, 0xAD,
            0x57}); //Load into A value 0xAD, the LD c,a (copy a into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xAD, z80.getD());
        Assert.assertEquals(3, z80.getPC());

    }
    
    @Test
    public void testCopyRegisterToELoadInstructions() {
        //Test 16 bit loads into various registers
        Z80 z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xEE,
            0x58}); //Load into BC value 0xEEEE, the LD C,b (copy b into C)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xEE, z80.getE());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xFE,
            0x59}); //Load into BC value 0xEEFE, then LD c,c (copy c into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getE());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x5A}); //Load into DE value 0xFEDE, the LD c,d (copy d into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getE());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x5B}); //Load into DE value 0xFEDE, the LD c,e (copy E into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xDE, z80.getE());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x5C}); //Load into HL value 0x1234, the LD c,H (copy H into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x12, z80.getE());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x5D}); //Load into HL value 0x1234, the LD c,l (copy l into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x34, z80.getE());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x3E, 0xAD,
            0x5F}); //Load into A value 0xAD, the LD c,a (copy a into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xAD, z80.getE());
        Assert.assertEquals(3, z80.getPC());

    }
    
    
    @Test
    public void testCopyRegisterToHLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80 z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xEE,
            0x60}); //Load into BC value 0xEEEE, the LD C,b (copy b into C)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xEE, z80.getH());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xFE,
            0x61}); //Load into BC value 0xEEFE, then LD c,c (copy c into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getH());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x62}); //Load into DE value 0xFEDE, the LD c,d (copy d into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getH());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x63}); //Load into DE value 0xFEDE, the LD c,e (copy E into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xDE, z80.getH());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x64}); //Load into HL value 0x1234, the LD c,H (copy H into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x12, z80.getH());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x65}); //Load into HL value 0x1234, the LD c,l (copy l into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x34, z80.getH());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x3E, 0xAD,
            0x67}); //Load into A value 0xAD, the LD c,a (copy a into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xAD, z80.getH());
        Assert.assertEquals(3, z80.getPC());

    }
    
    @Test
    public void testCopyRegisterToLLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80 z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xEE,
            0x68}); //Load into BC value 0xEEEE, the LD C,b (copy b into C)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xEE, z80.getL());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xFE,
            0x69}); //Load into BC value 0xEEFE, then LD c,c (copy c into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getL());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x6A}); //Load into DE value 0xFEDE, the LD c,d (copy d into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getL());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x6B}); //Load into DE value 0xFEDE, the LD c,e (copy E into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xDE, z80.getL());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x6C}); //Load into HL value 0x1234, the LD c,H (copy H into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x12, z80.getL());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x6D}); //Load into HL value 0x1234, the LD c,l (copy l into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x34, z80.getL());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x3E, 0xAD,
            0x6F}); //Load into A value 0xAD, the LD c,a (copy a into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xAD, z80.getL());
        Assert.assertEquals(3, z80.getPC());

    }
    
    @Test
    public void testCopyRegisterToALoadInstructions() {
        //Test 16 bit loads into various registers
        Z80 z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xEE,
            0x78}); //Load into BC value 0xEEEE, the LD C,b (copy b into C)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xEE, z80.getA());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x01, 0xEE, 0xFE,
            0x79}); //Load into BC value 0xEEFE, then LD c,c (copy c into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getA());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x7A}); //Load into DE value 0xFEDE, the LD c,d (copy d into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xFE, z80.getA());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x11, 0xFE, 0xDE,
            0x7B}); //Load into DE value 0xFEDE, the LD c,e (copy E into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xDE, z80.getA());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x7C}); //Load into HL value 0x1234, the LD c,H (copy H into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x12, z80.getA());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x21, 0x12, 0x34,
            0x7D}); //Load into HL value 0x1234, the LD c,l (copy l into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0x34, z80.getA());
        Assert.assertEquals(4, z80.getPC());

        z80 = new Z80();
        z80.setPC(0);
        z80.setMemory(new int[]{0x3E, 0xAD,
            0x7F}); //Load into A value 0xAD, the LD c,a (copy a into c)
        z80.executeNextInstruction();
        z80.executeNextInstruction();
        Assert.assertEquals(0xAD, z80.getA());
        Assert.assertEquals(3, z80.getPC());

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
