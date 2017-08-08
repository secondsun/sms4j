package net.saga.console.emulator.sms.sms4j.instruction.rotate;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class RotateRightWithCarry implements InstructionExecution {
    private final Z80 z80;

    public RotateRightWithCarry(Z80 z80) {
        this.z80 = z80;
    }


    @Override
    public int exec() {
        byte a = z80.getA();
        boolean carry = z80.getFlagC();

        z80.setCarry((a & 0x1) > 0);
        z80.setSubtractFlag(false);
        z80.setHalfCarry(false);
        a = (byte) (a >>> 1 | ((a << 7)&0xFF));
        if (carry) {
            a |= 0x80;
        } else {
            a &= 0x7F;
        }

        z80.getRegisterA().setValue(a);
        return 4;
    }
}
