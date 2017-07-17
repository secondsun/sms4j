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
public class IncrementRegisterInstructionSixteenBit implements InstructionExecution {

    private final Register<Short> register;
    private final Z80 z80;
    
    public IncrementRegisterInstructionSixteenBit(Register<Short> register, Z80 z80) {
        this.register = register;
        this.z80 = z80;
    }
    
    @Override
    public int exec() {
        int firstValue = register.getValue();
        int newValue = firstValue + 1;
        
        register.setValue(newValue);
        return 6;
    }
    
}
