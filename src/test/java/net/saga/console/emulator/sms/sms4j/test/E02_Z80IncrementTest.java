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
public class E02_Z80IncrementTest {

    
    /**
     * opcode 0s increments bc by 1
     */
    @Test
    public void testIncrementBC() {
        Z80 z80 = new Z80();
        z80.setMemory(new byte[]{(byte)0x01,(byte)0xFF,  (byte)0x01, (byte)0x03}); //Load into BC value 0x01FF
                                                    //INCR BC by 1
        z80.cycle(16);
        Assert.assertEquals(4, z80.getPC());
        Assert.assertEquals(0x0200, z80.getBC());
    }
    
    /**
     * opcode 0x4 increments b by 1
     */
    @Test
    public void testIncrementB() {
        Z80 z80 = new Z80();
        z80.setMemory(new byte[]{(byte)0x01, (byte)0xFF, (byte)0x01, (byte)0x04}); //Load into BC value 0x01FF
                                                    //INCR B by 1
        z80.cycle(14);
        Assert.assertEquals(4, z80.getPC());
        Assert.assertEquals(0x02FF, z80.getBC());
        
        z80 = new Z80();
        z80.setMemory(new byte[]{(byte)0x01, (byte)0xFF, (byte)0xFF, (byte)0x04}); //Load into BC value 0xFFFF
                                                    //INCR B by 1
        z80.cycle(14);
        Assert.assertEquals(4, z80.getPC());
        Assert.assertEquals(0x00FF, z80.getBC());
        Assert.assertEquals(0b01000000, z80.getFlags());
        
        
    }
    
}
