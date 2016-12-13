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
    private int[] memory;

    public boolean isPrefix(int testPrefix) {
        return (testPrefix == 0xCB) || (testPrefix == 0xDD) || (testPrefix == 0xED) || (testPrefix == 0xFD);
    }

    public void setPC(int pcIndex) {
        this.programCounter = pcIndex;
    }

    public void setMemory(int[] memory) {
        this.memory = memory;
    }

    public void executeNextInstruction() {
        int instruction = memory[programCounter++];
        switch (instruction) {
            case 0:
                return;
            case 0x1:
                registerB = memory[programCounter++];
                registerC = memory[programCounter++];
                break;
            case 0x6:
                registerB = memory[programCounter++];
                break;
            case 0x16:
                registerD = memory[programCounter++];
                break;
            case 0x26:
                registerH = memory[programCounter++];
                break;
            case 0x0E:
                registerC = memory[programCounter++];
                break;
            case 0x11:
                registerD = memory[programCounter++];
                registerE = memory[programCounter++];
                break;
            case 0x1E:
                registerE = memory[programCounter++];
                break;
            case 0x2E:
                registerL = memory[programCounter++];
                break;
            case 0x3E:
                registerA = memory[programCounter++];
                break;
            case 0x21:
                registerH = memory[programCounter++];
                registerL = memory[programCounter++];
                break;
            case 0x31:
                stackPointer = memory[programCounter++] << 8 | memory[programCounter++];
                break;
            default:
                return;
        }

    }

    public int getPC() {
        return programCounter;
    }

    public int getBC() {
        return registerB << 8 | registerC;
    }

    public int getDE() {
        return registerD << 8 | registerE;
    }

    public int getHL() {
        return registerH << 8 | registerL;
    }

    public int getB() {
        return registerB;
    }

    public int getD() {
        return registerD;
    }

    public int getH() {
        return registerH;
    }

    public int getL() {
        return registerL;
    }

    public int getA() {
        return registerA;
    }

    public int getSP() {
        return stackPointer;
    }

    public int getC() {
        return registerC;
    }

    public int getE() {
        return registerE;
    }

}
