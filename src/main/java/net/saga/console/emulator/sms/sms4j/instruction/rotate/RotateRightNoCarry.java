package net.saga.console.emulator.sms.sms4j.instruction.rotate;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class RotateRightNoCarry implements InstructionExecution {
    private final Z80 z80;

    public RotateRightNoCarry(Z80 z80) {
        this.z80 = z80;
    }


    @Override
    public int exec() {
        byte a = z80.getA();
        a = (byte) (a >>> 1 | ((a << 7)&0xFF));
        z80.getRegisterA().setValue(a);
        return 4;
    }
}
