/*
 * Copyright (C) 2016 summers.
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
package net.saga.console.emulator.sms.sms4j;

/**
 *
 * The z80 is the main CPU of the SMS
 * 
 * @author summers
 */
public class Z80 {
    private int registerA;
    private int registerB;
    private int registerC;
    private int registerD;
    private int registerE;
    private int registerF;
    private int registerH;
    private int registerL;
    
    private int registerA_alt;
    private int registerB_alt;
    private int registerC_alt;
    private int registerD_alt;
    private int registerE_alt;
    private int registerF_alt;
    private int registerH_alt;
    private int registerL_alt;
    
    private int interruptVextor;
    private int memoryRefresh;
    private int indexRegisterIX;
    private int indexRegisterIY;
    private int stackPointer;
    private int programCounter;
    
}
