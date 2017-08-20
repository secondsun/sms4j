package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class CPL implements InstructionExecution {
    private final Z80 z80;

    public CPL(Z80 z80) {
        this.z80 = z80;
    }

    @Override
    public int exec() {
        z80.getRegisterA().setValue(~z80.getA());
        z80.setHalfCarry(true);
        z80.setSubtractFlag(true);
        return 4;
    }
}
