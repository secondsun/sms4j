package net.saga.console.emulator.sms.sms4j.instruction.jump;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class UnconditionalAbsoluteJump implements InstructionExecution {
    private final Z80 z80;
    private final int destination;

    public UnconditionalAbsoluteJump(Z80 z80, int jump) {
        this.z80 = z80;
        this.destination = jump;
    }

    @Override
    public int exec() {
        z80.setPC(destination);
        return 10;
    }
}
