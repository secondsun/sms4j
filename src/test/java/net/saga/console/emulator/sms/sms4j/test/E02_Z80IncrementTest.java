package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.z80.Z80;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


/**
 *
 * This class tests the instruction decoder for the z80 emulator
 *
 * @author secondsun
 */
public class E02_Z80IncrementTest {

    @Test
    public void testIncrementBC() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xFF, (byte) 0x01, (byte) 0x03}); //Load into BC value 0x01FF
        //INCR BC by 1
        z80.cycle(16);
        assertEquals(4, z80.getPC());
        assertEquals(0x0200, z80.getBC());
    }

    @Test
    public void testIncrementDE() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xFF, (byte) 0x01, (byte) 0x13}); //Load into BC value 0x01FF
        //INCR BC by 1
        z80.cycle(16);
        assertEquals(4, z80.getPC());
        assertEquals(0x0200, z80.getDE());
    }

    @Test
    public void testIncrementHL() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0xFF, (byte) 0x01, (byte) 0x23}); //Load into BC value 0x01FF
        //INCR BC by 1
        z80.cycle(16);
        assertEquals(4, z80.getPC());
        assertEquals(0x0200, z80.getHL());
    }

    @Test
    public void testIncrementSP() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x31, (byte) 0xFF, (byte) 0x01, (byte) 0x33}); //Load into BC value 0x01FF
        //INCR BC by 1
        z80.cycle(16);
        assertEquals(4, z80.getPC());
        assertEquals(0x0200, z80.getSPValue());
    }

    @Test
    public void testIncrementB() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0xFF, (byte) 0x01, (byte) 0x04}); //Load into BC value 0x01FF
        //INCR B by 1
        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0x02FF, z80.getBC());

        z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0x7F, (byte) 0x7F, (byte) 0x04}); //Load into BC value 0x7F7F
        //INCR B by 1
        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0x807F, z80.getBC());
        assertTrue(z80.getFlagS());
        assertFalse(z80.getFlagZ());
        assertTrue(z80.getFlagH());
        assertTrue(z80.getFlagPV());
        assertFalse(z80.getFlagC());

    }

    @Test
    public void testIncrementC() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0x01, (byte) 0xFF, (byte) 0x0C}); //Load into BC value 0xFF01
        //INCR B by 1
        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0xFF02, z80.getBC());

        z80 = new Z80(new byte[]{(byte) 0x01, (byte) 0x7F, (byte) 0x7F, (byte) 0x0C}); //Load into BC value 0x7F7F
        //INCR B by 1
        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0x7F80, z80.getBC());
        assertTrue(z80.getFlagS());
        assertFalse(z80.getFlagZ());
        assertTrue(z80.getFlagH());
        assertTrue(z80.getFlagPV());
        assertFalse(z80.getFlagC());

    }

    @Test
    public void testIncrementD() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0xFF, (byte) 0x01, (byte) 0x14}); //Load into DE value 0x01FF

        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0x02FF, z80.getDE());

        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0x7F, (byte) 0x7F, (byte) 0x14}); //Load into DE value 0x7F7F

        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0x807F, z80.getDE());
        assertTrue(z80.getFlagS());
        assertFalse(z80.getFlagZ());
        assertTrue(z80.getFlagH());
        assertTrue(z80.getFlagPV());
        assertFalse(z80.getFlagC());

    }

    @Test
    public void testIncrementE() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0x01, (byte) 0xFF, (byte) 0x1C});

        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0xFF02, z80.getDE());

        z80 = new Z80(new byte[]{(byte) 0x11, (byte) 0x7F, (byte) 0x7F, (byte) 0x1C});

        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0x7F80, z80.getDE());
        assertTrue(z80.getFlagS());
        assertFalse(z80.getFlagZ());
        assertTrue(z80.getFlagH());
        assertTrue(z80.getFlagPV());
        assertFalse(z80.getFlagC());

    }

    @Test
    public void testIncrementH() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0xFF, (byte) 0x01, (byte) 0x24}); //Load into HL value 0x01FF

        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0x02FF, z80.getHL());

        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x7F, (byte) 0x7F, (byte) 0x24}); //Load into HL value 0x7F7F

        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0x807F, z80.getHL());
        assertTrue(z80.getFlagS());
        assertFalse(z80.getFlagZ());
        assertTrue(z80.getFlagH());
        assertTrue(z80.getFlagPV());
        assertFalse(z80.getFlagC());

    }

    @Test
    public void testIncrementL() {
        Z80 z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x01, (byte) 0xFF, (byte) 0x2C});

        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0xFF02, z80.getHL());

        z80 = new Z80(new byte[]{(byte) 0x21, (byte) 0x7F, (byte) 0x7F, (byte) 0x2C});

        z80.cycle(14);
        assertEquals(4, z80.getPC());
        assertEquals(0x7F80, z80.getHL());
        assertTrue(z80.getFlagS());
        assertFalse(z80.getFlagZ());
        assertTrue(z80.getFlagH());
        assertTrue(z80.getFlagPV());
        assertFalse(z80.getFlagC());

    }

}
