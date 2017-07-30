package net.saga.console.emulator.sms.sms4j;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class DJNZJump implements InstructionExecution {

    private final Z80 z80;
    private final byte jump;

    public DJNZJump(Z80 z80, byte jump) {
        this.z80 = z80;
        this.jump = jump;
    }

    @Override
    public int exec() {

        Register<Byte> b = z80.getRegisterB();

        b.setValue(b.getValue() - 1);

        if (b.getValue() != 0) {
            z80.setPC((z80.getPC() + (jump)) - 2);
        }

        return b.getValue() == 0 ?8:13;
    }
}
