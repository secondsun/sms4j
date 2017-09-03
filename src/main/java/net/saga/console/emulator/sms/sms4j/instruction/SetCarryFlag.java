package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class SetCarryFlag implements InstructionExecution {
    private final Z80 z80;

    public SetCarryFlag(Z80 z80) {
        this.z80 = z80;
    }

    @Override
    public int exec() {
        z80.setCarry(true);
        z80.setHalfCarry(false);
        z80.setSubtractFlag(false);
        return 4;
    }
}
