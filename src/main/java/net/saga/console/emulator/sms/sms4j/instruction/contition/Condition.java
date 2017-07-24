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
package net.saga.console.emulator.sms.sms4j.instruction.contition;

import net.saga.console.emulator.sms.sms4j.z80.EightBitDirectRegister;

/**
 * An interface abstraction of the condition tests that run on RegisterF
 * See http://sgate.emt.bme.hu/patai/publications/z80guide/app1c.html
 * @author summers
 */
@FunctionalInterface
public interface Condition {
   
    public boolean evaluate(EightBitDirectRegister flags);
    

    public static final Condition CARRY = (flags) -> {return (flags.getValueAsByte() & 0b00000001) == 1;};
    public static final Condition NO_CARRY = (flags) -> {return (flags.getValueAsByte() & 0b00000001) == 0;};
    public static final Condition ZERO = (flags) -> {return (flags.getValueAsByte() & 0b01000000) > 0;};
    public static final Condition NO_ZERO = (flags) -> {return (flags.getValueAsByte() & 0b01000000) == 0;};
    public static final Condition SIGN = (flags) -> {return (flags.getValueAsByte() & 0b10000000) > 1;};
    public static final Condition NO_SIGN = (flags) -> {return (flags.getValueAsByte() & 0b10000000) == 0;};
    public static final Condition PE = (flags) -> {return (flags.getValueAsByte() & 0b00000100) > 1;};
    public static final Condition PO = (flags) -> {return (flags.getValueAsByte() & 0b00000100) == 0;};
    
    
}


