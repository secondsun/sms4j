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
package net.saga.console.emulator.sms.sms4j.test;

import net.saga.console.emulator.sms.sms4j.z80.EightBitDirectRegister;
import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.SixteenBitCombinedRegister;
import net.saga.console.emulator.sms.sms4j.z80.MemoryRegister;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author summers
 */
public class RegisterTest {

    @Test
    public void test8bitSet() {
        Register test = new EightBitDirectRegister();
        test.setValue(8);
        assertEquals(8, test.getValue());
    }

    @Test
    public void test8bitGetValueAsByte() {
        EightBitDirectRegister test = new EightBitDirectRegister();
        test.setValue(255);//Should truncate to -1;
        assertEquals(-1, test.getValueAsByte());
    }

    @Test
    public void test8bitOverflowSet() {
        Register test = new EightBitDirectRegister();
        test.setValue(256);//Should truncate to 0;
        assertEquals(0, test.getValue());
    }

    @Test
    public void test16BitSet() {
        EightBitDirectRegister high = new EightBitDirectRegister();
        EightBitDirectRegister low = new EightBitDirectRegister();

        SixteenBitCombinedRegister test = new SixteenBitCombinedRegister(high, low);
        test.setValue(0x1234);
        assertEquals(0x1234, test.getValue());

    }

    @Test
    public void test16BitOverflows() {
        EightBitDirectRegister high = new EightBitDirectRegister();
        EightBitDirectRegister low = new EightBitDirectRegister();

        SixteenBitCombinedRegister test = new SixteenBitCombinedRegister(high, low);
        test.setValue(0x11234);
        assertEquals(0x1234, test.getValue());

    }

    @Test
    public void testMemoryRegister() {
        
        EightBitDirectRegister high = new EightBitDirectRegister();
        EightBitDirectRegister low = new EightBitDirectRegister();

        SixteenBitCombinedRegister register = new SixteenBitCombinedRegister(high, low);
        byte[] memory = new byte[0xFFFF];
        register.setValue(0x1234);
        
        MemoryRegister test = new MemoryRegister(memory, register);
        
        test.setValue(0x78);
        assertEquals(0x78, test.getValue());
        assertEquals(0x78, memory[0x1234]);        
    }

}

