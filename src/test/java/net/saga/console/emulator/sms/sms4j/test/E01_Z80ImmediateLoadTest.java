package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.z80.Z80;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This class tests the instruction decoder for the z80 emulator
 *
 * @author secondsun
 */
public class E01_Z80ImmediateLoadTest {

    /**
     * The Program Count (PC) is a register which keeps a reference to the
     * memory address that the next instruction starts at.
     * <p>
     * Instructions on the z80 may be up to 4 bytes. For now, let's focus on the
     * NOP instruction and the program counter. NOP means no operation and it
     * serves to make the CPU wait for a cycle and increment the PC.
     */
    @Test
    public void testNOPAndPCIncrement() {
        final Z80
                //The PC defaults to 0, but being explicit for documentation
                z80 = new Z80(new byte[]{0, 0, 0}); //Memory is just a collection of bytes.
        z80.cycle(4);//Fetch next instruction, executeUntilHalt, and incriment the program counter
        assertEquals(1, z80.getPC());
        z80.cycle(4);
        z80.cycle(4);
        assertEquals(3, z80.getPC());
    }


    /**
     * This test will run through the LD instructions which set a immediate
     * value to a register. These are multi byte instructions.
     * <p>
     * See http://z80.info/decoding.htm#upfx for the details.
     */
    @Test
    public void test8bitRegisterLoadInstructions() {
        Z80

                z80 = new Z80(new byte[]{(byte) 0x06, (byte) 0xFF, (byte) 0xFF}); //Load into B value (byte)0xFF
        z80.cycle(7);
        assertEquals(2, z80.getPC());
        assertEquals((byte) 0xFF, z80.getB());


        z80 = new Z80(new byte[]{(byte) 0x16, (byte) 0xEF, (byte) 0xFF}); //Load into D value (byte)0xFF
        z80.cycle(7);
        assertEquals(2, z80.getPC());
        assertEquals((byte) 0xEF, z80.getD());


        z80 = new Z80(new byte[]{(byte) 0x26, (byte) 0xFA, (byte) 0xFF}); //Load into F value (byte)0xFA
        z80.cycle(7);
        assertEquals(2, z80.getPC());
        assertEquals((byte) 0xFA, z80.getH());

        z80 = new Z80(new byte[]{(byte) 0x0E, (byte) 0x7D, (byte) 0xFF}); //Load into C value (byte)0x7D
        z80.cycle(7);
        assertEquals(2, z80.getPC());
        assertEquals((byte) 0x7d, z80.getC());


        z80 = new Z80(new byte[]{(byte) 0x1E, (byte) 0x01, (byte) 0xFF}); //Load into E value (byte)0x01
        z80.cycle(7);
        assertEquals(2, z80.getPC());
        assertEquals((byte) 0x01, z80.getE());


        z80 = new Z80(new byte[]{(byte) 0x2E, (byte) 0xBD, (byte) 0xFF}); //Load into L value (byte)0xBD
        z80.cycle(7);
        assertEquals(2, z80.getPC());
        assertEquals((byte) 0xBD, z80.getL());


        z80 = new Z80(new byte[]{(byte) 0x3E, (byte) 0x98, (byte) 0xFF}); //Load into A value (byte)0x98
        z80.cycle(7);
        assertEquals(2, z80.getPC());
        assertEquals((byte) 0x98, z80.getA());

    }

    /**
     * This test will run through the LD instructions which set a immediate
     * value to a register. These are multi byte instructions.
     * <p>
     * See http://z80.info/decoding.htm#upfx for the details.
     */
    @Test
    public void test16bitRegisterLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80

                z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0x01, (byte) 0x02}); //Load into BC value (byte)0xFFFF
        z80.cycle(10);
        assertEquals(3, z80.getPC());
        assertEquals(0x0201, z80.getBC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xFE, (byte) 0xEF}); //Load into DE value (byte)0xEFFE
        z80.cycle(10);
        assertEquals(3, z80.getPC());
        assertEquals(0xEFFE, z80.getDE());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0xDF, (byte) 0x0D}); //Load into HL value (byte)0x0DDF
        z80.cycle(10);
        assertEquals(3, z80.getPC());
        assertEquals(0x0DDF, z80.getHL());


        z80 = new Z80(new byte[]{(byte) 0x31, (byte) 0xD0, (byte) 0x0D}); //Load into SP value (byte)0x0DD0
        z80.cycle(10);
        assertEquals(3, z80.getPC());
        assertEquals(0x0DD0, z80.getSPValue());

    }

    @Test
    public void testCopyRegisterToBLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80

                z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xEE, (byte) 0xEE,
                (byte) 0x40}); //Load into BC value (byte)0xEEEE, the LD b,b (copy b into b)
        z80.cycle(14);
        assertEquals((byte) 0xEE, z80.getB());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xFE, (byte) 0xEE,
                (byte) 0x41}); //Load into BC value (byte)0xEEFE, then LD b,c (copy c into b)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getB());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x42}); //Load into DE value (byte)0xFEDE, the LD b,d (copy d into b)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getB());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x43}); //Load into DE value (byte)0xFEDE, the LD b,e (copy E into B)
        z80.cycle(14);
        assertEquals((byte) 0xDE, z80.getB());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x44}); //Load into HL value (byte)0x1234, the LD b,H (copy H into b)
        z80.cycle(14);
        assertEquals((byte) 0x12, z80.getB());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x45}); //Load into HL value (byte)0x1234, the LD b,b (copy l into b)
        z80.cycle(14);
        assertEquals((byte) 0x34, z80.getB());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x3E, (byte) 0xAD,
                (byte) 0x47}); //Load into A value (byte)0xAD, the LD b,a (copy a into b)
        z80.cycle(11);
        assertEquals((byte) 0xAD, z80.getB());
        assertEquals(3, z80.getPC());

    }

    @Test
    public void testCopyRegisterToCLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80

                z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xEE, (byte) 0xEE,
                (byte) 0x48}); //Load into BC value (byte)0xEEEE, the LD C,b (copy b into C)
        z80.cycle(14);
        assertEquals((byte) 0xEE, z80.getC());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xFE, (byte) 0xEE,
                (byte) 0x49}); //Load into BC value (byte)0xEEFE, then LD c,c (copy c into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getC());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x4A}); //Load into DE value (byte)0xFEDE, the LD c,d (copy d into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getC());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x4B}); //Load into DE value (byte)0xFEDE, the LD c,e (copy E into c)
        z80.cycle(14);
        assertEquals((byte) 0xDE, z80.getC());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x4C}); //Load into HL value (byte)0x1234, the LD c,H (copy H into c)
        z80.cycle(14);
        assertEquals((byte) 0x12, z80.getC());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x4D}); //Load into HL value (byte)0x1234, the LD c,l (copy l into c)
        z80.cycle(14);
        assertEquals((byte) 0x34, z80.getC());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x3E, (byte) 0xAD,
                (byte) 0x4F}); //Load into A value (byte)0xAD, the LD c,a (copy a into c)
        z80.cycle(11);
        assertEquals((byte) 0xAD, z80.getC());
        assertEquals(3, z80.getPC());

    }

    @Test
    public void testCopyRegisterToDLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80

                z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xEE, (byte) 0xEE,
                (byte) 0x50}); //Load into BC value (byte)0xEEEE, the LD C,b (copy b into C)
        z80.cycle(14);
        assertEquals((byte) 0xEE, z80.getD());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xFE, (byte) 0xEE,
                (byte) 0x51}); //Load into BC value (byte)0xEEFE, then LD c,c (copy c into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getD());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x52}); //Load into DE value (byte)0xFEDE, the LD c,d (copy d into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getD());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x53}); //Load into DE value (byte)0xFEDE, the LD c,e (copy E into c)
        z80.cycle(14);
        assertEquals((byte) 0xDE, z80.getD());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x54}); //Load into HL value (byte)0x1234, the LD c,H (copy H into c)
        z80.cycle(14);
        assertEquals((byte) 0x12, z80.getD());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x55}); //Load into HL value (byte)0x1234, the LD c,l (copy l into c)
        z80.cycle(14);
        assertEquals((byte) 0x34, z80.getD());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x3E, (byte) 0xAD,
                (byte) 0x57}); //Load into A value (byte)0xAD, the LD c,a (copy a into c)
        z80.cycle(11);
        assertEquals((byte) 0xAD, z80.getD());
        assertEquals(3, z80.getPC());

    }

    @Test
    public void testCopyRegisterToELoadInstructions() {
        //Test 16 bit loads into various registers
        Z80

                z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xEE, (byte) 0xEE,
                (byte) 0x58}); //Load into BC value (byte)0xEEEE, the LD C,b (copy b into C)
        z80.cycle(14);
        assertEquals((byte) 0xEE, z80.getE());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xFE, (byte) 0xEE,
                (byte) 0x59}); //Load into BC value (byte)0xEEFE, then LD c,c (copy c into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getE());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x5A}); //Load into DE value (byte)0xFEDE, the LD c,d (copy d into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getE());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x5B}); //Load into DE value (byte)0xFEDE, the LD c,e (copy E into c)
        z80.cycle(14);
        assertEquals((byte) 0xDE, z80.getE());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x5C}); //Load into HL value (byte)0x1234, the LD c,H (copy H into c)
        z80.cycle(14);
        assertEquals((byte) 0x12, z80.getE());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x5D}); //Load into HL value (byte)0x1234, the LD c,l (copy l into c)
        z80.cycle(14);
        assertEquals((byte) 0x34, z80.getE());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x3E, (byte) 0xAD,
                (byte) 0x5F}); //Load into A value (byte)0xAD, the LD c,a (copy a into c)
        z80.cycle(11);
        assertEquals((byte) 0xAD, z80.getE());
        assertEquals(3, z80.getPC());

    }


    @Test
    public void testCopyRegisterToHLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80

                z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xEE, (byte) 0xEE,
                (byte) 0x60}); //Load into BC value (byte)0xEEEE, the LD C,b (copy b into C)
        z80.cycle(14);
        assertEquals((byte) 0xEE, z80.getH());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xFE, (byte) 0xEE,
                (byte) 0x61}); //Load into BC value (byte)0xEEFE, then LD c,c (copy c into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getH());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x62}); //Load into DE value (byte)0xFEDE, the LD c,d (copy d into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getH());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x63}); //Load into DE value (byte)0xFEDE, the LD c,e (copy E into c)
        z80.cycle(14);
        assertEquals((byte) 0xDE, z80.getH());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x64}); //Load into HL value (byte)0x1234, the LD c,H (copy H into c)
        z80.cycle(14);
        assertEquals((byte) 0x12, z80.getH());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x65}); //Load into HL value (byte)0x1234, the LD c,l (copy l into c)
        z80.cycle(14);
        assertEquals((byte) 0x34, z80.getH());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x3E, (byte) 0xAD,
                (byte) 0x67}); //Load into A value (byte)0xAD, the LD c,a (copy a into c)
        z80.cycle(11);
        assertEquals((byte) 0xAD, z80.getH());
        assertEquals(3, z80.getPC());

    }

    @Test
    public void testCopyRegisterToLLoadInstructions() {
        //Test 16 bit loads into various registers
        Z80

                z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xEE, (byte) 0xEE,
                (byte) 0x68}); //Load into BC value (byte)0xEEEE, the LD C,b (copy b into C)
        z80.cycle(14);
        assertEquals((byte) 0xEE, z80.getL());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xFE, (byte) 0xEE,
                (byte) 0x69}); //Load into BC value (byte)0xEEFE, then LD c,c (copy c into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getL());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x6A}); //Load into DE value (byte)0xFEDE, the LD c,d (copy d into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getL());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x6B}); //Load into DE value (byte)0xFEDE, the LD c,e (copy E into c)
        z80.cycle(14);
        assertEquals((byte) 0xDE, z80.getL());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x6C}); //Load into HL value (byte)0x1234, the LD c,H (copy H into c)
        z80.cycle(14);
        assertEquals((byte) 0x12, z80.getL());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x6D}); //Load into HL value (byte)0x1234, the LD c,l (copy l into c)
        z80.cycle(14);
        assertEquals((byte) 0x34, z80.getL());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x3E, (byte) 0xAD,
                (byte) 0x6F}); //Load into A value (byte)0xAD, the LD c,a (copy a into c)
        z80.cycle(11);
        assertEquals((byte) 0xAD, z80.getL());
        assertEquals(3, z80.getPC());

    }

    @Test
    public void testCopyRegisterToALoadInstructions() {
        //Test 16 bit loads into various registers
        Z80

                z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xEE, (byte) 0xEE,
                (byte) 0x78}); //Load into BC value (byte)0xEEEE, the LD C,b (copy b into C)
        z80.cycle(14);
        assertEquals((byte) 0xEE, z80.getA());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xFE, (byte) 0xEE,
                (byte) 0x79}); //Load into BC value (byte)0xEEFE, then LD c,c (copy c into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getA());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x7A}); //Load into DE value (byte)0xFEDE, the LD c,d (copy d into c)
        z80.cycle(14);
        assertEquals((byte) 0xFE, z80.getA());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xDE, (byte) 0xFE,
                (byte) 0x7B}); //Load into DE value (byte)0xFEDE, the LD c,e (copy E into c)
        z80.cycle(14);
        assertEquals((byte) 0xDE, z80.getA());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x7C}); //Load into HL value (byte)0x1234, the LD c,H (copy H into c)
        z80.cycle(14);
        assertEquals((byte) 0x12, z80.getA());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x34, (byte) 0x12,
                (byte) 0x7D}); //Load into HL value (byte)0x1234, the LD c,l (copy l into c)
        z80.cycle(14);
        assertEquals((byte) 0x34, z80.getA());
        assertEquals(4, z80.getPC());


        z80 = new Z80(new byte[]{(byte) 0x3E, (byte) 0xAD,
                (byte) 0x7F}); //Load into A value (byte)0xAD, the LD c,a (copy a into c)
        z80.cycle(11);
        assertEquals((byte) 0xAD, z80.getA());
        assertEquals(3, z80.getPC());

    }

    /**
     * From http://z80.info/decoding.htm
     * <p>
     * Z80 instructions may begin with one or two prefixes. These prefixes are
     * always one of the following values : CB, DD, ED, or FD.
     */
    @Test
    public void testIsPrefix() {
        final Z80 z80 = new Z80(new byte[]{});
        byte isPrefix = (byte) 0xCB;
        assertTrue(z80.isPrefix(isPrefix));
        isPrefix = (byte) 0xDD;
        assertTrue(z80.isPrefix(isPrefix));
        isPrefix = (byte) 0xED;
        assertTrue(z80.isPrefix(isPrefix));
        isPrefix = (byte) 0xFD;
        assertTrue(z80.isPrefix(isPrefix));

    }

    /**
     * Instructions have one of two formats :
     * <p>
     * [prefix byte,] opcode [,displacement byte] [,immediate data] - OR - two
     * prefix bytes, displacement byte, opcode
     * <p>
     * Of notes, values in brackets are optional
     * <p>
     * In this step we will create an instance of the "Instruction" object that
     * will encapsulate the values of a valid instruction for processing by Z80.
     * <p>
     * We will also create a program counter and a memory field to serve as the
     * collection of bytes.
     */
    @Test
    public void testCreateInstruction() {

    }
}
