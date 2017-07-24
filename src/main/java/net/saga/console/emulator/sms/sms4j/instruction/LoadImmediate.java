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

/**
 *
 * @author summers
 */
public class LoadImmediate implements InstructionExecution {

    public final Register destinationDegister;
    public final int valueToLoad;
    private final int cycles;

    public LoadImmediate(Register destinationDegister, int valueToLoad, int cycles) {
        this.destinationDegister = destinationDegister;
        this.valueToLoad = valueToLoad;
        this.cycles = cycles;
    }
    
    @Override
    public int exec() {
        destinationDegister.setValue(valueToLoad);
        return cycles;
    }
    
}
