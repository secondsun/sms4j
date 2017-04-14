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

    private byte registerA;
    private byte registerB;
    private byte registerC;
    private byte registerD;
    private byte registerE;
    private byte registerF;//S Z X H X P/V N C
    private byte registerH;
    private byte registerL;

    private byte registerA_alt;
    private byte registerB_alt;
    private byte registerC_alt;
    private byte registerD_alt;
    private byte registerE_alt;
    private byte registerF_alt;
    private byte registerH_alt;
    private byte registerL_alt;

    private int interruptVector;
    private int memoryRefresh;
    private int indexRegisterIX;
    private int indexRegisterIY;
    private int stackPointer;
    private int programCounter;
    private byte[] memory;

    private int cycleCountDown = 0; //cycleCountDown is incremented by the number of cycles an instruction requires.

    public boolean isPrefix(byte testPrefix) {
        return (testPrefix == (byte)0xCB) || (testPrefix == (byte)0xDD) || (testPrefix == (byte)0xED) || (testPrefix == (byte)0xFD);
    }

    public void setPC(int pcIndex) {
        this.programCounter = pcIndex;
    }

    public void setMemory(byte[] memory) {
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
            case 0x01:
                registerC = memory[programCounter++];
                registerB = memory[programCounter++];
                cycleCountDown += 10;
                break;
            case 0x03: { //Incr BC 6 cycles
                int bc = (((int)registerB << 8)) | (0x00FF&(int)registerC);
                bc++;
                registerB = (byte) ((bc & 0xFF00) >> 8);
                registerC = (byte) (bc & 0xFF);
                cycleCountDown += 6;
                break;
            }
            case 0x04: {//Incr B 4 cycles, affects f register
                byte b = registerB;
                if (registerB < 0) {//different signs, may never overflow
                    setVOverflow(false);
                } else if ((byte) (registerB + 1) < 0) {//b+1 overflows to negative
                    setVOverflow(true);
                }

                b++;

                setHalfCarry(checkHalfCarry(registerB, b));
                setZero(b == 0);
                setSign(((byte) b) < 0);
                registerB = (byte)(b & 0xFF);
                setSubtractFlag(false);
                cycleCountDown += 4;
                break;
            }
            case 0x06:
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
                registerE = memory[programCounter++];
                registerD = memory[programCounter++];
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
                registerL = memory[programCounter++];
                registerH = memory[programCounter++];
                cycleCountDown += 10;
                break;
            case 0x31:
                stackPointer = ((0xFF&memory[programCounter++] ) | ((0xFF00&(memory[programCounter++])<< 8)))&0xFFFF;
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
        return (0xFFFF&((((0x0FF)&registerB) << 8) | ((0x0FF)&registerC)));
    }

    public int getDE() {
        return (0xFFFF&((((0x0FF)&registerD) << 8) | ((0x0FF)&registerE)));
    }

    public int getHL() {
        return (0xFFFF&((((0x0FF)&registerH) << 8) | ((0x0FF)&registerL)));
    }

    public byte getB() {
        return registerB;
    }

    public byte getD() {
        return registerD;
    }

    public byte getH() {
        return registerH;
    }

    public byte getL() {
        return registerL;
    }

    public byte getA() {
        return registerA;
    }

    public int getSP() {
        return 0xFFFF&stackPointer;
    }

    public byte getC() {
        return registerC;
    }

    public byte getE() {
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

    private void setCarry(boolean b) {
        byte maskedF = (byte) (registerF & 0xFE);
        registerF = (byte)(maskedF | (b ? 1 : 0));
    }

    private void setHalfCarry(boolean b) {
        byte maskedF = (byte)(registerF & 0b11101111);
        registerF = (byte)(maskedF | (b ? 0b10000 : 0));
    }

    private void setZero(boolean b) {
        byte maskedF = (byte)(registerF & 0b10111111);
        registerF = (byte)(maskedF | (b ? 0b1000000 : 0));
    }

    private void setSign(boolean b) {
        byte maskedF = (byte)(registerF & 0b01111111);
        registerF = (byte)(maskedF | (b ? 0b10000000 : 0));
    }

    private void setSubtractFlag(boolean b) {
        byte maskedF = (byte)(registerF & 0b11111101);
        registerF = (byte)(maskedF | (b ? 0x10 : 0x00));
    }

    private void setVOverflow(boolean b) {
        byte maskedF = (byte)(registerF & 0b11111011);
        registerF = (byte)(maskedF | (b ? 0x100 : 0x000));
    }

    /**
     * Check if there is a carry from bit 3 to bit 4
     *
     * @param original
     * @param updated
     * @return true on a half carry
     */
    private boolean checkHalfCarry(byte original, byte updated) {
        return ((original & 0b00001000) != (updated & 0b00001000));
    }

    public int getFlags() {
        return registerF;
    }

}
