package net.saga.console.emulator.sms.sms4j.instruction.jump;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.instruction.contition.Condition;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class ConditionalAbsoluteJump implements InstructionExecution {
    private final Z80 z80;
    private final Condition condition;
    private final int destination;

    public ConditionalAbsoluteJump(Z80 z80, Condition condition, int destination) {
        this.z80 = z80;
        this.condition = condition;
        this.destination = destination;
    }

    @Override
    public int exec() {
        boolean eval = condition.evaluate(z80.getRegisterF());
        if (eval) {
            z80.setPC( destination );
        }
        return 10;
    }
}
