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

    private int cycleCountDown = 0; //cycleCountDown is incremented by the number of cycles an instruction requires.

    public boolean isPrefix(int testPrefix) {
        return (testPrefix == 0xCB) || (testPrefix == 0xDD) || (testPrefix == 0xED) || (testPrefix == 0xFD);
    }

    public void setPC(int pcIndex) {
        this.programCounter = pcIndex;
    }

    public void setMemory(int[] memory) {
        this.memory = memory;
    }

    public void cycle() {
        cycle(1);
    }

    public void executeNextInstruction() {
        int instruction = memory[programCounter++];
        switch (instruction) {
            case 0:
                cycleCountDown += 4;
                return;
            case 0x1:
                registerB = memory[programCounter++];
                registerC = memory[programCounter++];
                cycleCountDown += 10;
                break;
            case 0x6:
                registerB = memory[programCounter++];
                cycleCountDown += 7;
                break;
            case 0x16:
                registerD = memory[programCounter++];
                cycleCountDown += 7;
                break;
            case 0x26:
                registerH = memory[programCounter++];
                cycleCountDown += 7;
                break;
            case 0x0E:
                registerC = memory[programCounter++];
                cycleCountDown += 7;
                break;
            case 0x11:
                registerD = memory[programCounter++];
                registerE = memory[programCounter++];
                cycleCountDown += 10;
                break;
            case 0x1E:
                registerE = memory[programCounter++];
                cycleCountDown += 7;
                break;
            case 0x2E:
                registerL = memory[programCounter++];
                cycleCountDown += 7;
                break;
            case 0x3E:
                registerA = memory[programCounter++];
                cycleCountDown += 7;
                break;
            case 0x21:
                registerH = memory[programCounter++];
                registerL = memory[programCounter++];
                cycleCountDown += 10;
                break;
            case 0x31:
                stackPointer = memory[programCounter++] << 8 | memory[programCounter++];
                cycleCountDown += 10;
                break;
            case 0x40:
                // Decodes to registerB = registerB;
                cycleCountDown += 4;
                break;
            case 0x41:
                registerB = registerC;
                cycleCountDown += 4;
                break;
            case 0x42:
                registerB = registerD;
                cycleCountDown += 4;
                break;
            case 0x43:
                registerB = registerE;
                cycleCountDown += 4;
                break;
            case 0x44:
                registerB = registerH;
                cycleCountDown += 4;
                break;
            case 0x45:
                registerB = registerL;
                cycleCountDown += 4;
                break;
            case 0x47:
                registerB = registerA;
                cycleCountDown += 4;
                break;
            case 0x48:
                registerC = registerB;
                cycleCountDown += 4;
                break;
            case 0x49:
                //Decodes to registerC = registerC;
                cycleCountDown += 4;
                break;
            case 0x4A:
                registerC = registerD;
                cycleCountDown += 4;
                break;
            case 0x4B:
                registerC = registerE;
                cycleCountDown += 4;
                break;
            case 0x4C:
                registerC = registerH;
                cycleCountDown += 4;
                break;
            case 0x4D:
                registerC = registerL;
                cycleCountDown += 4;
                break;
            case 0x4F:
                registerC = registerA;
                cycleCountDown += 4;
                break;
            case 0x50:
                registerD = registerB;
                cycleCountDown += 4;
                break;
            case 0x51:
                registerD = registerC;
                cycleCountDown += 4;
                break;
            case 0x52:
                //registerD = registerD;
                cycleCountDown += 4;
                break;
            case 0x53:
                registerD = registerE;
                cycleCountDown += 4;
                break;
            case 0x54:
                registerD = registerH;
                cycleCountDown += 4;
                break;
            case 0x55:
                registerD = registerL;
                cycleCountDown += 4;
                break;
            case 0x57:
                registerD = registerA;
                cycleCountDown += 4;
                break;
            case 0x58:
                registerE = registerB;
                cycleCountDown += 4;
                break;
            case 0x59:
                registerE = registerC;
                cycleCountDown += 4;
                break;
            case 0x5A:
                registerE = registerD;
                cycleCountDown += 4;
                break;
            case 0x5B:
                cycleCountDown += 4;
                break;
            case 0x5C:
                registerE = registerH;
                cycleCountDown += 4;
                break;
            case 0x5D:
                registerE = registerL;
                cycleCountDown += 4;
                break;
            case 0x5F:
                registerE = registerA;
                cycleCountDown += 4;
                break;
            case 0x60:
                registerH = registerB;
                cycleCountDown += 4;
                break;
            case 0x61:
                registerH = registerC;
                cycleCountDown += 4;
                break;
            case 0x62:
                registerH = registerD;
                cycleCountDown += 4;
                break;
            case 0x63:
                registerH = registerE;
                cycleCountDown += 4;
                break;
            case 0x64:
                //registerH = registerH;
                cycleCountDown += 4;
                break;
            case 0x65:
                registerH = registerL;
                cycleCountDown += 4;
                break;
            case 0x67:
                registerH = registerA;
                cycleCountDown += 4;
                break;
            case 0x68:
                registerL = registerB;
                cycleCountDown += 4;
                break;
            case 0x69:
                registerL = registerC;
                cycleCountDown += 4;
                break;
            case 0x6A:
                registerL = registerD;
                cycleCountDown += 4;
                break;
            case 0x6B:
                registerL = registerE;
                cycleCountDown += 4;
                break;
            case 0x6C:
                registerL = registerH;
                cycleCountDown += 4;
                break;
            case 0x6D:
                //registerL = registerL;
                cycleCountDown += 4;
                break;
            case 0x6F:
                registerL = registerA;
                cycleCountDown += 4;
                break;
            case 0x78:
                registerA = registerB;
                cycleCountDown += 4;
                break;
            case 0x79:
                registerA = registerC;
                cycleCountDown += 4;
                break;
            case 0x7A:
                registerA = registerD;
                cycleCountDown += 4;
                break;
            case 0x7B:
                registerA = registerE;
                cycleCountDown += 4;
                break;
            case 0x7C:
                registerA = registerH;
                cycleCountDown += 4;
                break;
            case 0x7D:
                registerA = registerL;
                cycleCountDown += 4;
                break;
            case 0x7F:
                //registerA = registerA;
                cycleCountDown += 4;
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

    /**
     * This will signal a clock cycle to the z80
     * 
     * @param numberOfCycles the number of cycles to execute
     */ 
    public void cycle(int numberOfCycles) {
        for (int i = 0; i < numberOfCycles; i++) {
            if (cycleCountDown <= 0) {
                executeNextInstruction();
            }
            cycleCountDown--;
        }
    }

}
