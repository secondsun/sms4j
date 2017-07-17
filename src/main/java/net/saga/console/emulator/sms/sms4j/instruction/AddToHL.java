package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.SixteenBitCombinedRegister;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

/**
 * Created by summers on 7/17/17.
 */
public class AddToHL implements InstructionExecution {

    private final Register<Short> source;


    private final Z80 z80;

    public AddToHL(Register<Short> source, Z80 z80) {
        this.source = source;
        this.z80 = z80;
    }

    @Override
    public int exec() {
        SixteenBitCombinedRegister hl = z80.getRegisterHL();
        int augend = hl.getValue();
        int addend = source.getValue();
        int sum = augend + addend;

        z80.setCarry(sum > 0xFFFF);
        z80.setSubtractFlag(false);
        z80.setHalfCarry((augend & 0x0FFF) + (addend& 0x0FFF) > 0x0FFF);
        hl.setValue(sum);
        return 11;
    }
}
