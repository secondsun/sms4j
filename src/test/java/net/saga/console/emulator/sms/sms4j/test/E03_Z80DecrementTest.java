package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * This class tests the instruction decoder for the z80 emulator
 *
 * @author secondsun
 */
public class E03_Z80DecrementTest {

    @Test
    public void testDecrementBC() {
        Z80 z80 = new Z80();
        z80.setMemory(new byte[]{(byte) 0x01, (byte) 0xFF, (byte) 0x01, (byte) 0x0B}); //Load into BC value 0x01FF
        //INCR BC by 1
        z80.cycle(16);
        assertEquals(4, z80.getPC());
        assertEquals(0x01FE, z80.getBC());
    }

    @Test
    public void testDecrementDE() {
        Z80 z80 = new Z80();
        z80.setMemory(new byte[]{(byte) 0x11, (byte) 0x00, (byte) 0x01, (byte) 0x1B});
        //INCR BC by 1
        z80.cycle(16);
        assertEquals(4, z80.getPC());
        assertEquals(0x00FF, z80.getDE());
    }

    @Test
    public void testDecrementHL() {
        Z80 z80 = new Z80();
        z80.setMemory(new byte[]{(byte) 0x21, (byte) 0x00, (byte) 0x00, (byte) 0x2B});
        //INCR BC by 1
        z80.cycle(16);
        assertEquals(4, z80.getPC());
        assertEquals(0xFFFF, z80.getHL());
    }

    @Test
    public void testDecrementSP() {
        Z80 z80 = new Z80();
        z80.setMemory(new byte[]{(byte) 0x31, (byte) 0xFF, (byte) 0x01, (byte) 0x3B});
        z80.cycle(16);
        assertEquals(4, z80.getPC());
        assertEquals(0x01FE, z80.getSPValue());
    }

    @ParameterizedTest
    @CsvSource({"6, 40, 5",
        "6, 128, 5",
        "14, 40, 13",
        "14, 128, 13",
        "22, 40, 21",
        "22, 128, 21",
        "30, 40, 29",
        "30, 128, 29",
        "38, 40, 37",
        "38, 128, 37",
        "46, 40, 45",
        "46, 128, 45",
        "62, 40, 61",
        "62, 128, 61"})

    public void testDecrement(byte loadOpcode, int loadValueInt, byte decOpcode) {
        byte loadValue = (byte) (loadValueInt & 0xFF);
        Z80 z80 = new Z80();
        z80.setMemory(new byte[]{loadOpcode, loadValue, decOpcode}); //Load into BC value 0x01FF
        //DEC B by 1
        z80.cycle(11);

        int trueSum = loadValue - 1;
        int byteSum = trueSum & 0xFF;

        assertEquals(3, z80.getPC());

        Register register = fetchDeccedRegister(decOpcode, z80);

        assertEquals(byteSum, register.getValue());
        
        assertEquals(register.getValue() == 0, z80.getFlagZ());
        assertEquals(byteSum < 0, z80.getFlagS());
        assertEquals((0x0F & loadValue) == 0, z80.getFlagH());
        
        boolean overflow = loadValue == 0x80;
        assertEquals(overflow, z80.getFlagPV());
        assertEquals(trueSum > 0xFF, z80.getFlagC());

    }

    private Register fetchDeccedRegister(byte decOpcode, Z80 z80) {
        switch (decOpcode) {
            case 0x05:
                return z80.getRegisterB();
            case 0x0D:
                return z80.getRegisterC();
            case 0x15:
                return z80.getRegisterD();
            case 0x1d:
                return z80.getRegisterE();
            case 0x25:
                return z80.getRegisterH();
            case 0x2d:
                return z80.getRegisterL();
            case 0x3d:
                return z80.getRegisterA();
            default:
                throw new RuntimeException("Not decode opcode");

        }
    }

}
