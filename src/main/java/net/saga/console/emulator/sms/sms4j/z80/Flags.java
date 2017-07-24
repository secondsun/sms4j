package net.saga.console.emulator.sms.sms4j.z80;

public class Flags {
    public static int FLAG_C_CARRY_MASK =  0b00000001;
    public static int FLAG_C_CARRY_CLEAR = 0b11111110;

    public static int FLAG_N_SUBTRACT_MASK =  0b00000010;
    public static int FLAG_N_SUBTRACT_CLEAR = 0b11111101;

    public static int FLAG_PV_PARITY_MASK =  0b00000100;
    public static int FLAG_PV_PARITY_CLEAR = 0b11111011;
    public static int FLAG_PV_OVERFLOW_MASK =  0b00000100;
    public static int FLAG_PV_OVERFLOW_CLEAR = 0b11111011;

    public static int FLAG_H_HALFCARRY_MASK =  0b00010000;
    public static int FLAG_H_HALFCARRY_CLEAR = 0b11101111;

    public static int FLAG_Z_ZERO_MASK =  0b01000000;
    public static int FLAG_Z_ZERO_CLEAR = 0b10111111;

    public static int FLAG_S_SIGN_MASK =  0b10000000;
    public static int FLAG_S_SIGN_CLEAR = 0b01111111;

}
