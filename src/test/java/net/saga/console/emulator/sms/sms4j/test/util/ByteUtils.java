package net.saga.console.emulator.sms.sms4j.test.util;

public class ByteUtils {
    public static short toShort(byte high, byte low) {
        return (short) ((((high << 8) & 0xFF00) | low) & 0xFFFF);
    }
}
