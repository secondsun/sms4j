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
 *
 * @author summers
 * @param <T> Size of the Register
 */
public interface Register<T extends Number> {

    /**
     * Returns the value of this register, will be 0-255.
     * @return register value
     */
    int getValue();


    /**
     * Sets the value of this register, value will be masked with 0xFF.
     * @param value the new value
     */
    void setValue(int value);

    /**
     * Registers may be 8 bit or 16 bit.  This is important for future calculations.
     *
     * @return 8 or 16, the number of bits
     */
    int getSize();

}
