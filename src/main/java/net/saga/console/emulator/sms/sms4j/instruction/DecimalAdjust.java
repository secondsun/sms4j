package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.util.Utils;
import net.saga.console.emulator.sms.sms4j.z80.EightBitDirectRegister;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class DecimalAdjust implements InstructionExecution {
    private final Z80 z80;

    public DecimalAdjust(Z80 z80) {
        this.z80 = z80;
    }

    @Override
    public int exec() {
        EightBitDirectRegister regA = z80.getRegisterA();
        byte a = z80.getA();
        boolean flagC = z80.getFlagC();
        boolean flagH = z80.getFlagH();

        byte low = (byte) (a & 0xf);
        byte high = (byte) (((byte) (a & 0xf0)) >> 4);


        if (flagH && flagC) {
            if (high < 4 && low < 4) {
                z80.setCarry(true);
                regA.setValue(a+0x66);
                z80.setHalfCarry(low + 6 > 0xf);
            } else if (high > 5 && low > 5) {
                z80.setCarry(true);
                z80.setHalfCarry(low + 0xA > 0xf);
                regA.setValue(a+0x9A);
            }
        } else if (flagC && !flagH) {
            if (high < 3 && low < 10) {
                z80.setCarry(true);
                regA.setValue(a+0x60);
                z80.setHalfCarry(low + 0x0 > 0xf);
            } else if (high < 3 && low > 9) {
                z80.setCarry(true);
                regA.setValue(a+0x66);
                z80.setHalfCarry(low + 0x6 > 0xf);
            } else if (high > 6 && low < 10) {
                z80.setCarry(true);
                regA.setValue(a+0xA0);
                z80.setHalfCarry(low + 0x0 > 0xf);
            }
        } else if (!flagC && flagH) {
            if (high < 10 && low < 4) {
                z80.setCarry(false);
                regA.setValue(a+0x60);
                z80.setHalfCarry(low + 0x0 > 0xf);
            } else if (high > 9 && low < 4) {
                z80.setCarry(true);
                regA.setValue(a+0x66);
                z80.setHalfCarry(low + 0x6 > 0xf);
            } else if (high < 9 && low > 5) {
                z80.setCarry(false);
                regA.setValue(a+0xFA);
                z80.setHalfCarry(low + 0xA > 0xf);
            }
        } else if (!flagC && !flagH) {
            if (high < 10 && low < 10) {
                z80.setCarry(false);
                regA.setValue(a+0x00);
                z80.setHalfCarry(low + 0x0 > 0xf);
            } else if (high < 9 && low > 9) {
                z80.setCarry(false);
                regA.setValue(a+0x66);
                z80.setHalfCarry(low + 0x6 > 0xf);
            } else if (high > 9 && low < 10) {
                z80.setCarry(true);
                regA.setValue(a+0x60);
                z80.setHalfCarry(low + 0x0> 0xf);
            } else if (high > 9 && low < 10) {
                z80.setCarry(true);
                regA.setValue(a+0x60);
                z80.setHalfCarry(low + 0x0> 0xf);
            } else if (high < 10 && low < 10) {
                z80.setCarry(false);
                regA.setValue(a+0x00);
                z80.setHalfCarry(low + 0x0> 0xf);
            }
        }
        z80.setZero(regA.getValueAsByte() == 0);
        z80.setVOverflow(Utils.countBits(regA.getValueAsByte()) %2 == 0);
        z80.setSign(regA.getValueAsByte() < 0);
        return 4;
    }
}
