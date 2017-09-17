package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.MemoryRegister;
import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.SixteenBitCombinedRegister;

public class ExchangeHL implements InstructionExecution {
    private final MemoryRegister memoryRegisterSP;
    private final SixteenBitCombinedRegister registerHL;

    public ExchangeHL(MemoryRegister memoryRegisterSP, SixteenBitCombinedRegister registerHL) {
        this.memoryRegisterSP = memoryRegisterSP;
        this.registerHL = registerHL;
    }

    @Override
    public int exec() {
        int leftValue = memoryRegisterSP.getValue16();
        int rightValue = registerHL.getValue();
        memoryRegisterSP.setValue16(rightValue);
        registerHL.setValue(leftValue);
        return 19;
    }
}
