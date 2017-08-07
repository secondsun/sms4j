package net.saga.console.emulator.sms.sms4j.instruction.jump;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class UnconditionalAbsoluteJumpAtHL implements InstructionExecution {
    private final Z80 z80;

    public UnconditionalAbsoluteJumpAtHL(Z80 z80) {
        this.z80 = z80;
    }

    @Override
    public int exec() {
        z80.setPC(z80.getHL());
        return 4;
    }
}
