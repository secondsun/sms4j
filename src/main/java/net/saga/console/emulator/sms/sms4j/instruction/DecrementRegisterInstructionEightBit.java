/*
 * Copyright (C) 2017 summers.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

/**
 *
 * @author summers
 */
public class DecrementRegisterInstructionEightBit implements InstructionExecution {

    private final Register<Byte> register;
    private final Z80 z80;

    public DecrementRegisterInstructionEightBit(Register<Byte> register, Z80 z80) {
        this.register = register;
        this.z80 = z80;
    }
    
    @Override
    public int exec() {
        byte b = (byte) register.getValue();
        if (b > 0) {//different signs, may never overflow
            z80.setVOverflow(false);
        } else if (((b - 1) & 0xFF) > 0) {//b+1 overflows to negative
            z80.setVOverflow(true);
        }

        b--;

        z80.setHalfCarry(((byte) register.getValue() & 0x0F) == 0);
        z80.setZero(b == 0);
        z80.setSign(((byte) b) < 0);
        register.setValue(b);
        z80.setSubtractFlag(false);
        return 4;
    }
    
}
