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
package net.saga.console.emulator.sms.sms4j.z80;

/**
 * Some instructions abuse the HL register and let you do a memory read in 
 * a single opcode instruction.
 * @author summers
 */
public class MemoryRegister implements Register{
    
    private final byte[] memory;
    private final SixteenBitCombinedRegister register;

    public MemoryRegister(byte[] memory, SixteenBitCombinedRegister register) {
        this.memory = memory;
        this.register = register;
    }

    @Override
    public int getValue() {
        return memory[register.getValue()];
    }

    @Override
    public void setValue(int value) {
        memory[register.getValue()] = (byte) (0xff & value);
    }
    
    
    
}
