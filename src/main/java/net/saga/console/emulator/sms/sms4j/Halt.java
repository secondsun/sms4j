package net.saga.console.emulator.sms.sms4j;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class Halt implements InstructionExecution {
    private Z80 z80;

    public Halt(Z80 z80) {
        this.z80 = z80;
    }

    @Override
    public int exec() {
        this.z80.setHalt(true);
        return 4;
    }
}
