package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.test.util.ByteArrayConverter;
import net.saga.console.emulator.sms.sms4j.test.util.ByteUtils;
import net.saga.console.emulator.sms.sms4j.z80.Z80;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class E05_MemoryLoadTest {
    @ParameterizedTest
    @CsvSource({
            "'0x2A,0x03,0x00, 0x42, 0x19'",
            "'0x2A,0x03,0x00, 0xEF, 0xDC'"
    })
    public void testLoadHLAtNN(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        //byte[] memory = {0x2A,0x03,0x00, (byte) 0x42, (byte) 0x19};
        byte lowByte = memory[3];
        byte highByte = memory[4];
        Z80 z80 = new Z80(memory);
        z80.cycle(16);

        assertEquals(highByte, z80.getH());
        assertEquals(lowByte, z80.getL());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x01,0x06,0x00,0x0A,0x03,0x00, 0x42, 0x19'", //LD BC 06, LD A (BC)
            "'0x01,0x06,0x00,0x0A,0x03,0x00, 0xEF, 0xDC'", //LD BC 06, LD A (BC)
            "'0x01,0x06,0x00,0x0A,0x03,0x00, 0x19, 0x19'", //LD BC 06, LD A (BC)
            "'0x01,0x06,0x00,0x0A,0x03,0x00, 0xDC, 0xDC'"  //LD BC 06, LD A (BC)
    })
    public void testLoadAAtBC(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        //byte[] memory = {0x2A,0x03,0x00, (byte) 0x42, (byte) 0x19};
        byte expectedValue = memory[6];

        Z80 z80 = new Z80(memory);
        z80.cycle(17);


        assertEquals(expectedValue, z80.getA());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x11,0x06,0x00,0x1A,0x03,0x00, 0x42, 0x19'", //LD DE 06, LD A (DE)
            "'0x11,0x06,0x00,0x1A,0x03,0x00, 0xEF, 0xDC'", //LD DE 06, LD A (DE)
            "'0x11,0x06,0x00,0x1A,0x03,0x00, 0x19, 0x19'", //LD DE 06, LD A (DE)
            "'0x11,0x06,0x00,0x1A,0x03,0x00, 0xDC, 0xDC'"  //LD DE 06, LD A (DE)
    })
    public void testLoadAAtDE(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        //byte[] memory = {0x2A,0x03,0x00, (byte) 0x42, (byte) 0x19};
        byte expectedValue = memory[6];

        Z80 z80 = new Z80(memory);
        z80.cycle(17);


        assertEquals(expectedValue, z80.getA());

    }


    @ParameterizedTest
    @CsvSource({
            "'0x3A,0x03,0x00, 0x42, 0x19'",//LD A (03)
            "'0x3A,0x03,0x00, 0xEF, 0xDC'" //LD A (03)
    })
    public void testLoadAAtnn(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        //byte[] memory = {0x2A,0x03,0x00, (byte) 0x42, (byte) 0x19};
        byte expectedValue = memory[3];

        Z80 z80 = new Z80(memory);
        z80.cycle(13);


        assertEquals(expectedValue, z80.getA());

    }

    @ParameterizedTest
    @CsvSource({
            "'0x01,0x06,0x00,0x3E,0x03,0x02, 0x05, 0x00'" //LD BC 0x0006, LD A 0x03, LD (BC) A
    })
    public void testLoadAtBC_A(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        //byte[] memory = {0x2A,0x03,0x00, (byte) 0x42, (byte) 0x19};
        byte expectedValue = memory[4];

        Z80 z80 = new Z80(memory);
        z80.cycle(24);

        assertEquals(expectedValue, z80.readMemory(z80.getBC()));

    }

    //LD (DE), A
    @ParameterizedTest
    @CsvSource({
            "'0x11,0x06,0x00,0x3E,0x03,0x12, 0x05, 0x00'" //LD DE 0x0006, LD A 0x03, LD (DE) A
    })
    public void testLoadAtDE_A(@ConvertWith(ByteArrayConverter.class) byte[] memory) {
        //byte[] memory = {0x2A,0x03,0x00, (byte) 0x42, (byte) 0x19};
        byte expectedValue = memory[4];

        Z80 z80 = new Z80(memory);
        z80.cycle(24);

        assertEquals(expectedValue, z80.readMemory(z80.getDE()));

    }

    //LD (nn), HL
    @ParameterizedTest
    @CsvSource({
            "'0x21,0x06,0x00,0x22, 0x06, 0x00,0x03, 0x00'", //LD HL 0x0006, LD (0x0006) HL
            "'0x21,0x06,0x10,0x22, 0x06, 0x00,0x03, 0x00'"  //LD HL 0x1006, LD (0x0006) HL
    })
    public void testLoadAtnn_HL(@ConvertWith(ByteArrayConverter.class) byte[] memory) {

        int expectedValue = ByteUtils.toShort(memory[2], memory[1]);

        Z80 z80 = new Z80(memory);
        z80.cycle(26);

        int actuallyWritten = ByteUtils.toShort(
                (byte) z80.readMemory(ByteUtils.toShort(memory[5], memory[4]) + 1),
                (byte) z80.readMemory(ByteUtils.toShort(memory[5], memory[4]))
        );

        assertEquals(expectedValue, actuallyWritten);

    }

    //LD (nn), A
    @ParameterizedTest
    @CsvSource({
            "'0x3E,0x03,0x32, 0x05, 0x00,0x42'" //LD A 0x03, LD (0x0005) A
    })
    public void testLoadAtnn_A(@ConvertWith(ByteArrayConverter.class) byte[] memory) {

        byte expectedValue = memory[1];

        Z80 z80 = new Z80(memory);
        z80.cycle(18);

        assertEquals(expectedValue, z80.readMemory(0x05));

    }

}
