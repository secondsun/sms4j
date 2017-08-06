package net.saga.console.emulator.sms.sms4j.instruction.jump;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.instruction.contition.Condition;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class ConditionalRelativeJump implements InstructionExecution {
    private final Z80 z80;
    private final Condition condition;
    private final byte displacement;

    public ConditionalRelativeJump(Z80 z80, Condition condition, byte displacement) {
        this.z80 = z80;
        this.condition = condition;
        this.displacement = displacement;
    }

    @Override
    public int exec() {
        boolean eval = condition.evaluate(z80.getRegisterF());
        if (eval) {
            z80.setPC( z80.getPC() + displacement - 2);
        }
        return eval?12:7;
    }
}
