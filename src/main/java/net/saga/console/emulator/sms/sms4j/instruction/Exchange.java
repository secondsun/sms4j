package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.Register;

public class Exchange implements InstructionExecution {
    private final Register right;
    private final Register left;
    private final int durationInCycles;

    public Exchange(Register left, Register right) {
        this.left = left;
        this.right = right;
        this.durationInCycles = 4;
    }

    public Exchange(Register left, Register right, int durationInCycles) {
        this.left = left;
        this.right = right;
        this.durationInCycles = durationInCycles;
    }

    @Override
    public int exec() {
        int leftValue = left.getValue();
        int rightValue = right.getValue();
        left.setValue(rightValue);
        right.setValue(leftValue);
        return durationInCycles;
    }
}
