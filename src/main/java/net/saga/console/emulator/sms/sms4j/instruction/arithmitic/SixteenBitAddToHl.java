package net.saga.console.emulator.sms.sms4j.instruction.arithmitic;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.z80.Flags;
import net.saga.console.emulator.sms.sms4j.z80.SixteenBitCombinedRegister;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class SixteenBitAddToHl implements InstructionExecution {

    private final Z80 z80;
    private final short toAdd;

    public SixteenBitAddToHl(Z80 z80, short toAdd) {
        this.z80 = z80;
        this.toAdd = toAdd;
    }

    @Override
    public int exec() {

        SixteenBitCombinedRegister hl = z80.getRegisterHL();
        short hlValue = (short) hl.getValue();
        int trueResult = hlValue + toAdd;

        byte flags = z80.getRegisterF().getValueAsByte();

        flags &= (Flags.FLAG_N_SUBTRACT_CLEAR  & Flags.FLAG_C_CARRY_CLEAR & Flags.FLAG_H_HALFCARRY_CLEAR );

        if (trueResult > 0xFFFF) {//Overflow

            flags |= Flags.FLAG_C_CARRY_MASK;
        }
        if ((hlValue & 0x0FFF) + (toAdd & 0x0FFF) > 0x0FFF) {
            flags |= Flags.FLAG_H_HALFCARRY_MASK;
        }


        z80.getRegisterF().setValue(flags);
        hl.setValue((short)(trueResult & 0xFFFF));

        return 11;
    }
}
