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
    
    private byte[] memory;
    private final Register<Short> register;

    public MemoryRegister(byte[] memory, Register<Short> register) {
        this.memory = memory;
        this.register = register;
    }

    public int getValue16() {
        byte low = memory[register.getValue()];
        byte high = memory[register.getValue() + 1];
        return ((high << 8) & 0xFF00) | (low & 0xFF);
    }

    public void setValue16(int value) {
        byte low = (byte) (value & 0xFF);
        byte high = (byte) ((value & 0xFF00) >>> 8);
        memory[register.getValue()] = low;
        memory[register.getValue() + 1] = high;
    }

    @Override
    public int getValue() {
        return memory[register.getValue()] & 0xFF;
    }

    @Override
    public void setValue(int value) {
        memory[register.getValue()] = (byte) (0xff & value);
    }

    @Override
    public int getSize() {
        return 8;
    }

    public void setMemory(byte[] memory) {
        this.memory = memory;
    }
}
