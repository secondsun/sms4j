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
package net.saga.console.emulator.sms.sms4j.z80;

/**
 *
 * The z80 is the main CPU of the SMS
 *
 * @author summers
 */
public class Z80 {

    private final EightBitDirectRegister registerA = new EightBitDirectRegister();
    private final EightBitDirectRegister registerB = new EightBitDirectRegister();
    private final EightBitDirectRegister registerC = new EightBitDirectRegister();
    private final EightBitDirectRegister registerD = new EightBitDirectRegister();
    private final EightBitDirectRegister registerE = new EightBitDirectRegister();
    private final EightBitDirectRegister registerF = new EightBitDirectRegister();//S Z X H X P/V N C
    private final EightBitDirectRegister registerH = new EightBitDirectRegister();
    private final EightBitDirectRegister registerL = new EightBitDirectRegister();

    private final SixteenBitCombinedRegister registerHL = new SixteenBitCombinedRegister(registerH, registerL);
    private final SixteenBitCombinedRegister registerBC = new SixteenBitCombinedRegister(registerB, registerC);
    private final SixteenBitCombinedRegister registerDE = new SixteenBitCombinedRegister(registerD, registerE);
    private final SixteenBitCombinedRegister registerAF = new SixteenBitCombinedRegister(registerA, registerF);

    private MemoryRegister memoryRegisterHL;

    private EightBitDirectRegister registerA_alt;
    private EightBitDirectRegister registerB_alt;
    private EightBitDirectRegister registerC_alt;
    private EightBitDirectRegister registerD_alt;
    private EightBitDirectRegister registerE_alt;
    private EightBitDirectRegister registerF_alt;
    private EightBitDirectRegister registerH_alt;
    private EightBitDirectRegister registerL_alt;

    private int interruptVector;
    private int memoryRefresh;
    private int indexRegisterIX;
    private int indexRegisterIY;
    private SixteenBitDirectRegister stackPointer = new SixteenBitDirectRegister();
    private int programCounter;
    private byte[] memory;

    private int cycleCountDown = 0; //cycleCountDown is incremented by the number of cycles an instruction requires.

    public boolean isPrefix(byte testPrefix) {
        return (testPrefix == (byte) 0xCB) || (testPrefix == (byte) 0xDD) || (testPrefix == (byte) 0xED) || (testPrefix == (byte) 0xFD);
    }

    public void setPC(int pcIndex) {
        this.programCounter = pcIndex;
    }

    public void setMemory(byte[] memory) {
        memoryRegisterHL = new MemoryRegister(memory, registerHL);
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
                registerC.setValue(memory[programCounter++]);
                registerB.setValue(memory[programCounter++]);
                cycleCountDown += 10;
                break;
            case 0x03: //Incr BC 6 cycles
                inc16(registerB, registerC);
                cycleCountDown += 6;
                break;
            case 0x13: //Incr BC 6 cycles
                inc16(registerD, registerE);
                cycleCountDown += 6;
                break;
            case 0x23: //Incr BC 6 cycles
                inc16(registerH, registerL);
                cycleCountDown += 6;
                break;
            case 0x33: //Incr BC 6 cycles
                stackPointer.postIncrement();
                cycleCountDown += 6;
                break;
            case 0x0C: {
                increment(registerC);
                cycleCountDown += 4;
                break;
            }

            case 0x2C: {
                increment(registerL);
                cycleCountDown += 4;
                break;
            }
            case 0x24: {
                increment(registerH);
                cycleCountDown += 4;
                break;
            }
            case 0x1C: {
                increment(registerE);
                cycleCountDown += 4;
                break;
            }
            case 0x14: {
                increment(registerD);
                cycleCountDown += 4;
                break;
            }
            case 0x04: {//Incr B 4 cycles, affects f register
                increment(registerB);
                cycleCountDown += 4;
                break;
            }
            case 0x06:
                registerB.setValue(memory[programCounter++]);
                cycleCountDown += 7;
                break;
            case 0x16:
                registerD.setValue(memory[programCounter++]);
                cycleCountDown += 7;
                break;
            case 0x26:
                registerH.setValue(memory[programCounter++]);
                cycleCountDown += 7;
                break;
            case 0x0E:
                registerC.setValue(memory[programCounter++]);
                cycleCountDown += 7;
                break;
            case 0x11:
                registerE.setValue(memory[programCounter++]);
                registerD.setValue(memory[programCounter++]);
                cycleCountDown += 10;
                break;
            case 0x1E:
                registerE.setValue(memory[programCounter++]);
                cycleCountDown += 7;
                break;
            case 0x2E:
                registerL.setValue(memory[programCounter++]);
                cycleCountDown += 7;
                break;
            case 0x3E:
                registerA.setValue(memory[programCounter++]);
                cycleCountDown += 7;
                break;
            case 0x21:
                registerL.setValue(memory[programCounter++]);
                registerH.setValue(memory[programCounter++]);
                cycleCountDown += 10;
                break;
            case 0x31:
                stackPointer.setValue(((0xFF & memory[programCounter++]) | ((0xFF00 & (memory[programCounter++]) << 8))) & 0xFFFF);
                cycleCountDown += 10;
                break;
            case 0x40:
                // Decodes to registerB.setValue(registerB.getValue());
                cycleCountDown += 4;
                break;
            case 0x41:
                registerB.setValue(registerC.getValue());
                cycleCountDown += 4;
                break;
            case 0x42:
                registerB.setValue(registerD.getValue());
                cycleCountDown += 4;
                break;
            case 0x43:
                registerB.setValue(registerE.getValue());
                cycleCountDown += 4;
                break;
            case 0x44:
                registerB.setValue(registerH.getValue());
                cycleCountDown += 4;
                break;
            case 0x45:
                registerB.setValue(registerL.getValue());
                cycleCountDown += 4;
                break;
            case 0x47:
                registerB.setValue(registerA.getValue());
                cycleCountDown += 4;
                break;
            case 0x48:
                registerC.setValue(registerB.getValue());
                cycleCountDown += 4;
                break;
            case 0x49:
                //Decodes to registerC.setValue(registerC.getValue());
                cycleCountDown += 4;
                break;
            case 0x4A:
                registerC.setValue(registerD.getValue());
                cycleCountDown += 4;
                break;
            case 0x4B:
                registerC.setValue(registerE.getValue());
                cycleCountDown += 4;
                break;
            case 0x4C:
                registerC.setValue(registerH.getValue());
                cycleCountDown += 4;
                break;
            case 0x4D:
                registerC.setValue(registerL.getValue());
                cycleCountDown += 4;
                break;
            case 0x4F:
                registerC.setValue(registerA.getValue());
                cycleCountDown += 4;
                break;
            case 0x50:
                registerD.setValue(registerB.getValue());
                cycleCountDown += 4;
                break;
            case 0x51:
                registerD.setValue(registerC.getValue());
                cycleCountDown += 4;
                break;
            case 0x52:
                //registerD.setValue(registerD.getValue());
                cycleCountDown += 4;
                break;
            case 0x53:
                registerD.setValue(registerE.getValue());
                cycleCountDown += 4;
                break;
            case 0x54:
                registerD.setValue(registerH.getValue());
                cycleCountDown += 4;
                break;
            case 0x55:
                registerD.setValue(registerL.getValue());
                cycleCountDown += 4;
                break;
            case 0x57:
                registerD.setValue(registerA.getValue());
                cycleCountDown += 4;
                break;
            case 0x58:
                registerE.setValue(registerB.getValue());
                cycleCountDown += 4;
                break;
            case 0x59:
                registerE.setValue(registerC.getValue());
                cycleCountDown += 4;
                break;
            case 0x5A:
                registerE.setValue(registerD.getValue());
                cycleCountDown += 4;
                break;
            case 0x5B:
                cycleCountDown += 4;
                break;
            case 0x5C:
                registerE.setValue(registerH.getValue());
                cycleCountDown += 4;
                break;
            case 0x5D:
                registerE.setValue(registerL.getValue());
                cycleCountDown += 4;
                break;
            case 0x5F:
                registerE.setValue(registerA.getValue());
                cycleCountDown += 4;
                break;
            case 0x60:
                registerH.setValue(registerB.getValue());
                cycleCountDown += 4;
                break;
            case 0x61:
                registerH.setValue(registerC.getValue());
                cycleCountDown += 4;
                break;
            case 0x62:
                registerH.setValue(registerD.getValue());
                cycleCountDown += 4;
                break;
            case 0x63:
                registerH.setValue(registerE.getValue());
                cycleCountDown += 4;
                break;
            case 0x64:
                //registerH.setValue(registerH.getValue());
                cycleCountDown += 4;
                break;
            case 0x65:
                registerH.setValue(registerL.getValue());
                cycleCountDown += 4;
                break;
            case 0x67:
                registerH.setValue(registerA.getValue());
                cycleCountDown += 4;
                break;
            case 0x68:
                registerL.setValue(registerB.getValue());
                cycleCountDown += 4;
                break;
            case 0x69:
                registerL.setValue(registerC.getValue());
                cycleCountDown += 4;
                break;
            case 0x6A:
                registerL.setValue(registerD.getValue());
                cycleCountDown += 4;
                break;
            case 0x6B:
                registerL.setValue(registerE.getValue());
                cycleCountDown += 4;
                break;
            case 0x6C:
                registerL.setValue(registerH.getValue());
                cycleCountDown += 4;
                break;
            case 0x6D:
                //registerL.setValue(registerL.getValue());
                cycleCountDown += 4;
                break;
            case 0x6F:
                registerL.setValue(registerA.getValue());
                cycleCountDown += 4;
                break;
            case 0x78:
                registerA.setValue(registerB.getValue());
                cycleCountDown += 4;
                break;
            case 0x79:
                registerA.setValue(registerC.getValue());
                cycleCountDown += 4;
                break;
            case 0x7A:
                registerA.setValue(registerD.getValue());
                cycleCountDown += 4;
                break;
            case 0x7B:
                registerA.setValue(registerE.getValue());
                cycleCountDown += 4;
                break;
            case 0x7C:
                registerA.setValue(registerH.getValue());
                cycleCountDown += 4;
                break;
            case 0x7D:
                registerA.setValue(registerL.getValue());
                cycleCountDown += 4;
                break;
            case 0x7F:
                //registerA.setValue(registerA.getValue());
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
        return (((registerB.getValue() << 8) | (registerC.getValue())));
    }

    public int getDE() {
        return ((((registerD.getValue()) << 8) | (registerE.getValue())));
    }

    public int getHL() {
        return ((((registerH.getValue()) << 8) | (registerL.getValue())));
    }

    public byte getB() {
        return registerB.getValueAsByte();
    }

    public byte getD() {
        return registerD.getValueAsByte();
    }

    public byte getH() {
        return registerH.getValueAsByte();
    }

    public byte getL() {
        return registerL.getValueAsByte();
    }

    public byte getA() {
        return registerA.getValueAsByte();
    }

    public int getSPValue() {
        return stackPointer.getValue();
    }

    public SixteenBitDirectRegister getSP() {
        return stackPointer;
    }

    public byte getC() {
        return registerC.getValueAsByte();
    }

    public byte getE() {
        return registerE.getValueAsByte();
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
        byte maskedF = (byte) (registerF.getValue() & 0xFE);
        registerF.setValue((maskedF | (b ? 1 : 0)));
    }

    private void setHalfCarry(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & 0b11101111);
        registerF.setValue(maskedF | (b ? 0b10000 : 0));
    }

    private void setZero(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & 0b10111111);
        registerF.setValue(maskedF | (b ? 0b1000000 : 0));
    }

    private void setSign(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & 0b01111111);
        registerF.setValue(maskedF | (b ? 0b10000000 : 0));
    }

    private void setSubtractFlag(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & 0b11111101);
        registerF.setValue(maskedF | (b ? 0b10 : 0b00));
    }

    private void setVOverflow(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & 0b11111011);
        registerF.setValue(maskedF | (b ? 0b100 : 0b000));
    }

    public boolean getFlagS() {
        return (registerF.getValueAsByte() & 0x80) > 0;

    }

    public boolean getFlagZ() {
        return (registerF.getValueAsByte() & 0x40) > 0;
    }

    public boolean getFlagH() {
        return (registerF.getValueAsByte() & 0x10) > 0;
    }

    public boolean getFlagPV() {
        return (registerF.getValueAsByte() & 0x04) > 0;
    }

    public boolean getFlagN() {
        return (registerF.getValueAsByte() & 0x02) > 0;
    }

    public boolean getFlagC() {
        return (registerF.getValueAsByte() & 0x01) > 0;
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
        return registerF.getValue();
    }

    private void increment(EightBitDirectRegister register) {
        byte b = register.getValueAsByte();
        if (b < 0) {//different signs, may never overflow
            setVOverflow(false);
        } else if ((byte) (b + 1) < 0) {//b+1 overflows to negative
            setVOverflow(true);
        }

        b++;

        setHalfCarry(checkHalfCarry(register.getValueAsByte(), b));
        setZero(b == 0);
        setSign(((byte) b) < 0);
        register.setValue(b);
        setSubtractFlag(false);
    }

    private void inc16(EightBitDirectRegister registerB, EightBitDirectRegister registerC) {
        int bc = ((registerB.getValue() << 8) | registerC.getValue());
        bc++;
        registerB.setValue((bc & 0xFF00) >> 8);
        registerC.setValue(bc);
    }

    public EightBitDirectRegister getRegisterA() {
        return registerA;
    }

    public EightBitDirectRegister getRegisterB() {
        return registerB;
    }

    public EightBitDirectRegister getRegisterC() {
        return registerC;
    }

    public EightBitDirectRegister getRegisterD() {
        return registerD;
    }

    public EightBitDirectRegister getRegisterE() {
        return registerE;
    }

    public EightBitDirectRegister getRegisterF() {
        return registerF;
    }

    public EightBitDirectRegister getRegisterH() {
        return registerH;
    }

    public EightBitDirectRegister getRegisterL() {
        return registerL;
    }

    public byte getValueAtRegisterHL() {
        return (byte) (memoryRegisterHL.getValue() & 0xFF);
    }

    public Register getMemoryRegisterHL() {
        return memoryRegisterHL;
    }

    public SixteenBitCombinedRegister getRegisterHL() {
        return registerHL;
    }

    public SixteenBitCombinedRegister getRegisterBC() {
        return registerBC;
    }

    public SixteenBitCombinedRegister getRegisterDE() {
        return registerDE;
    }

    public SixteenBitCombinedRegister getRegisterAF() {
        return registerAF;
    }
    
    

}
