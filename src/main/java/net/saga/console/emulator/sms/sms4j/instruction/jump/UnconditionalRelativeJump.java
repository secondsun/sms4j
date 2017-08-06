package net.saga.console.emulator.sms.sms4j.instruction.jump;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class UnconditionalRelativeJump implements InstructionExecution {
    private final Z80 z80;
    private final byte displacement;

    public UnconditionalRelativeJump(Z80 z80, byte jump) {
        this.z80 = z80;
        this.displacement = jump;
    }

    @Override
    public int exec() {
        z80.setPC(z80.getPC() + displacement - 2);
        return 12;
    }
}
