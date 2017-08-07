package net.saga.console.emulator.sms.sms4j.instruction.rotate;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class RotateLeftNoCarry implements InstructionExecution {
    private final Z80 z80;

    public RotateLeftNoCarry(Z80 z80) {
        this.z80 = z80;
    }


    @Override
    public int exec() {
        byte a = z80.getA();
        a = (byte) (((a << 1)&0xFF) | (((a&0x80) >>> 7)));
        z80.getRegisterA().setValue(a);
        return 4;
    }
}
