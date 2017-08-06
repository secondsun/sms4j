package net.saga.console.emulator.sms.sms4j.instruction.arithmitic;

import net.saga.console.emulator.sms.sms4j.z80.Flags;
import net.saga.console.emulator.sms.sms4j.z80.Register;

import static net.saga.console.emulator.sms.sms4j.util.Utils.countBits;

public abstract class ALUActions implements ALUAction {


    public static final ALUAction ADD_A = (destination, source, carry) -> add(destination, source, 0);


    public static final ALUAction SUB = (destination, source, carry) -> sub(destination, source, 0);

    public static final ALUAction SBC_A = ALUActions::sub;
    public static final ALUAction ADC_A = ALUActions::add;

    public static final ALUAction AND = (destination, source, carry) -> {
        byte flags = 0;
        flags |= Flags.FLAG_H_HALFCARRY_MASK;

        byte aReg = (byte) destination.getValue();
        byte other = (byte) source.getValue();
        byte out = (byte) (aReg & other);

        if (out == 0) {
            flags |= Flags.FLAG_Z_ZERO_MASK;
        }

        if (out < 0) {
            flags |= Flags.FLAG_S_SIGN_MASK;
        }

        if ((countBits(out) % 2) == 0) {
            flags |= Flags.FLAG_PV_PARITY_MASK;
        }

        destination.setValue(out);

        return flags;
    };


    public static final ALUAction XOR = (destination, source, carry) -> {
        byte flags = 0;

        byte aReg = (byte) destination.getValue();
        byte other = (byte) source.getValue();
        byte out = (byte) (aReg ^ other);

        if (out == 0) {
            flags |= Flags.FLAG_Z_ZERO_MASK;
        }

        if (out < 0) {
            flags |= Flags.FLAG_S_SIGN_MASK;
        }

        if ((countBits(out) % 2) == 0) {
            flags |= Flags.FLAG_PV_PARITY_MASK;
        }

        destination.setValue(out);

        return flags;
    };
    public static final ALUAction OR = (destination, source, carry) -> {
        byte flags = 0;

        byte aReg = (byte) destination.getValue();
        byte other = (byte) source.getValue();
        byte out = (byte) (aReg | other);

        if (out == 0) {
            flags |= Flags.FLAG_Z_ZERO_MASK;
        }

        if (out < 0) {
            flags |= Flags.FLAG_S_SIGN_MASK;
        }

        if ((countBits(out) % 2) == 0) {
            flags |= Flags.FLAG_PV_PARITY_MASK;
        }

        destination.setValue(out);

        return flags;
    };

    public static final ALUAction CP = (destination, source, carry) -> {
        byte flags = 0;
        int minuend = destination.getValue();
        int subtrahend = source.getValue();
        int byteMask = destination.getSize() == 8 ? 0xFF : 0xFFFF;

        int trueDifference = minuend - subtrahend;
        int maskedDifference = (byte)(trueDifference & byteMask);
        if (byteMask == 0xFFFF) {
            maskedDifference = (short)(trueDifference & byteMask);
        }

        //Flags
        if (trueDifference > minuend) {//Overflow
            flags |= Flags.FLAG_PV_OVERFLOW_MASK;
            flags |= Flags.FLAG_C_CARRY_MASK;
        }

        if (maskedDifference == 0) {
            flags |= Flags.FLAG_Z_ZERO_MASK;
        }

        if (maskedDifference < 0) {
            flags |= Flags.FLAG_S_SIGN_MASK;
        }

        flags |= Flags.FLAG_N_SUBTRACT_MASK;

        switch (destination.getSize()) {
            case 8:
                if ((minuend & 0x0F) < (subtrahend & 0x0F) ) {
                    flags |= Flags.FLAG_H_HALFCARRY_MASK;
                }
                break;
            case 16:
                if ((minuend & 0x0FFF) < (subtrahend & 0x0FFF) ) {
                    flags |= Flags.FLAG_H_HALFCARRY_MASK;
                }
                break;
            default:
                throw new IllegalArgumentException(destination.getSize() + " is not a valid register size");
        }
        return flags;    };

    private static byte add(Register destination, Register source, int carry) {
        byte flags = 0;

        int byteMask = destination.getSize() == 8 ? 0xFF : 0xFFFF;

        byte augend =(byte) destination.getValue();
        byte addend =(byte) source.getValue();

        int trueResult = augend + addend + carry;
        int unsignedResult = (int)(augend & byteMask)+ (int)(addend & byteMask)+ carry;
        int maskedResult = (byte)(trueResult & byteMask);
        if (byteMask == 0xFFFF) {
            maskedResult = (short)(trueResult & byteMask);
        }

        //Flags
        if (unsignedResult > byteMask) {//Overflow
            flags |= Flags.FLAG_C_CARRY_MASK;
        }

        if (augend > 0 && addend > 0 && maskedResult < 0) {
            flags |= Flags.FLAG_PV_OVERFLOW_MASK;
        } else if (augend < 0 && addend < 0 && maskedResult > 0) {
            flags |= Flags.FLAG_PV_OVERFLOW_MASK;
        }

        if (maskedResult == 0) {
            flags |= Flags.FLAG_Z_ZERO_MASK;
        }

        if (maskedResult < 0) {
            flags |= Flags.FLAG_S_SIGN_MASK;
        }

        switch (destination.getSize()) {
            case 8:
                if ((augend & 0x0F) + (addend & 0x0F) > 0x0F) {
                    flags |= Flags.FLAG_H_HALFCARRY_MASK;
                }
                break;
            case 16:
                if ((augend & 0x0FFF) + (addend & 0x0FFF) > 0x0FFF) {
                    flags |= Flags.FLAG_H_HALFCARRY_MASK;
                }
                break;
            default:
                throw new IllegalArgumentException(destination.getSize() + " is not a valid register size");
        }

        destination.setValue(maskedResult);
        return flags;
    }

    private static byte sub(Register destination, Register source, int borrow) {
        byte flags = 0;
        int minuend = destination.getValue();
        int subtrahend = source.getValue();
        int byteMask = destination.getSize() == 8 ? 0xFF : 0xFFFF;
        int unsignedDifference = (int)(minuend & byteMask) - (int)(subtrahend & byteMask) - borrow;

        int trueDifference = minuend - subtrahend - borrow;
        int maskedDifference = (byte)(trueDifference & byteMask);
        if (byteMask == 0xFFFF) {
            maskedDifference = (short)(trueDifference & byteMask);
        }

        //Flags
        if (unsignedDifference < minuend && unsignedDifference < 0 ) {//Borrow
            flags |= Flags.FLAG_C_CARRY_MASK;
        }


        if (minuend > 0 && subtrahend < 0 && maskedDifference < 0) {
            flags |= Flags.FLAG_PV_OVERFLOW_MASK;
        } else if (minuend  < 0 && subtrahend > 0 && maskedDifference > 0) {
            flags |= Flags.FLAG_PV_OVERFLOW_MASK;
        }


        if (maskedDifference == 0) {
            flags |= Flags.FLAG_Z_ZERO_MASK;
        }

        if (maskedDifference < 0) {
            flags |= Flags.FLAG_S_SIGN_MASK;
        }

        flags |= Flags.FLAG_N_SUBTRACT_MASK;

        switch (destination.getSize()) {
            case 8:
                if ((minuend & 0x0F) < (subtrahend & 0x0F) ) {
                    flags |= Flags.FLAG_H_HALFCARRY_MASK;
                }
                break;
            case 16:
                if ((minuend & 0x0FFF) < (subtrahend & 0x0FFF) ) {
                    flags |= Flags.FLAG_H_HALFCARRY_MASK;
                }
                break;
            default:
                throw new IllegalArgumentException(destination.getSize() + " is not a valid register size");
        }

        destination.setValue(maskedDifference);
        return flags;
    }


}
