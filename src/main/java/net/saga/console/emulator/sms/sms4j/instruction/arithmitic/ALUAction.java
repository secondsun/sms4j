package net.saga.console.emulator.sms.sms4j.instruction.arithmitic;

import net.saga.console.emulator.sms.sms4j.z80.Flags;
import net.saga.console.emulator.sms.sms4j.z80.Register;

@FunctionalInterface
public interface ALUAction {

    /**
     * This will execute a ALU operation and store the result in destination using the source value and return a byte
     * that is all of the flags that were set in the operation.  Note that actual opcodes may not set all of the flags
     * returned.
     *
     * @param destination the register to store values in
     * @param source      the second operand
     * @return flags which may be set by the operation
     */
    byte execute(Register destination, Register source, int carry);

    ALUAction ADD_A = (destination, source, carry) -> {
        return add(destination, source, 0);
    };

    public static final ALUAction ADC_A = (destination, source, carry) -> {
        return add(destination, source, carry);
    };
    public static final ALUAction SUB = (destination, source, carry) -> {
        throw new IllegalStateException("Not implemented");
    };
    public static final ALUAction SBC_A = (destination, source, carry) -> {
        throw new IllegalStateException("Not implemented");
    };
    public static final ALUAction AND = (destination, source, carry) -> {
        throw new IllegalStateException("Not implemented");
    };
    public static final ALUAction XOR = (destination, source, carry) -> {
        throw new IllegalStateException("Not implemented");
    };
    public static final ALUAction OR = (destination, source, carry) -> {
        throw new IllegalStateException("Not implemented");
    };
    public static final ALUAction CP = (destination, source, carry) -> {
        throw new IllegalStateException("Not implemented");
    };

    static byte add(Register destination, Register source, int carry) {
        byte flags = 0;
        int augend = destination.getValue();
        int addend = source.getValue();
        int byteMask = destination.getSize() == 8 ? 0xFF : 0xFFFF;

        int trueResult = augend + addend + carry;
        int maskedResult = (byte)(trueResult & byteMask);
        if (byteMask == 0xFFFF) {
            maskedResult = (short)(trueResult & byteMask);
        }

        //Flags
        if (trueResult > byteMask) {//Overflow
            flags |= Flags.FLAG_PV_OVERFLOW_MASK;
            flags |= Flags.FLAG_C_CARRY_MASK;
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



}
