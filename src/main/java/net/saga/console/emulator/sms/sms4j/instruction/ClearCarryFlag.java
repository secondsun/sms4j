package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class ClearCarryFlag implements InstructionExecution {
    private final Z80 z80;

    public ClearCarryFlag(Z80 z80) {
        this.z80 = z80;
    }

    @Override
    public int exec() {
        z80.setHalfCarry(z80.getFlagC());
        z80.setCarry(z80.getFlagC());
        z80.setSubtractFlag(false);
        return 4;
    }
}
