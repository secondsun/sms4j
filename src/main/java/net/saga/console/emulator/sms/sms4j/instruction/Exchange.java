package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.Register;

public class Exchange implements InstructionExecution {
    private final Register right;
    private final Register left;

    public Exchange(Register left, Register right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int exec() {
        int leftValue = left.getValue();
        int rightValue = right.getValue();
        left.setValue(rightValue);
        right.setValue(leftValue);
        return 4;
    }
}
