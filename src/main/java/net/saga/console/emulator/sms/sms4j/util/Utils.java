package net.saga.console.emulator.sms.sms4j.util;

public final class Utils {
    private Utils() {}

    public static int countBits(byte out) {
        int bits = 0;
        while (out > 0) {
            if (out % 2 == 1) {//Odd only if leftmost bit is 1
                bits++;
            }
            out = (byte) (out >> 1); // right shift 1;
        }
        return bits ;
    }

}
