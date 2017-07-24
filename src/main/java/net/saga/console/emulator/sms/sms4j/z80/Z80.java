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

import net.saga.console.emulator.sms.sms4j.InstructionDecoder;
import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;

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

    
    
    private MemoryRegister memoryRegisterHL = new MemoryRegister(null, registerHL);

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
    private final SixteenBitDirectRegister stackPointer = new SixteenBitDirectRegister();
    private int programCounter;
    private byte[] memory;

    private int cycleCountDown = 0; //cycleCountDown is incremented by the number of cycles an instruction requires.

    private final InstructionDecoder decoder = new InstructionDecoder(this);
    
    public boolean isPrefix(byte testPrefix) {
        return (testPrefix == (byte) 0xCB) || (testPrefix == (byte) 0xDD) || (testPrefix == (byte) 0xED) || (testPrefix == (byte) 0xFD);
    }

    public void setPC(int pcIndex) {
        this.programCounter = pcIndex;
    }

    public void setMemory(byte[] memory) {
        memoryRegisterHL.setMemory(memory);
        this.memory = memory;
    }

    public void cycle() {
        cycle(1);
    }

    public void executeNextInstruction() {
        InstructionExecution instruction = decoder.decode(this);
        cycleCountDown += instruction.exec();
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

    public void setCarry(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & Flags.FLAG_C_CARRY_CLEAR);
        registerF.setValue((maskedF | (b ? Flags.FLAG_C_CARRY_MASK : 0)));
    }

    public void setHalfCarry(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & Flags.FLAG_H_HALFCARRY_CLEAR);
        registerF.setValue(maskedF | (b ? Flags.FLAG_H_HALFCARRY_MASK : 0));
    }

    public void setZero(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & Flags.FLAG_Z_ZERO_CLEAR);
        registerF.setValue(maskedF | (b ? Flags.FLAG_Z_ZERO_MASK: 0));
    }

    public void setSign(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & Flags.FLAG_S_SIGN_CLEAR);
        registerF.setValue(maskedF | (b ? Flags.FLAG_S_SIGN_MASK : 0));
    }

    public void setSubtractFlag(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & Flags.FLAG_N_SUBTRACT_CLEAR);
        registerF.setValue(maskedF | (b ? Flags.FLAG_N_SUBTRACT_MASK : 0));
    }

    public void setVOverflow(boolean b) {
        byte maskedF = (byte) (registerF.getValue() & Flags.FLAG_PV_OVERFLOW_CLEAR);
        registerF.setValue(maskedF | (b ? Flags.FLAG_PV_OVERFLOW_MASK : 0));
    }

    public boolean getFlagS() {
        return (registerF.getValueAsByte() & 0x80) > 0;

    }

    public boolean getFlagZ() {
        return (registerF.getValueAsByte() & 0x40) > 0;
    }

    public boolean getFlagH() {
        return ((registerF.getValueAsByte() & Flags.FLAG_H_HALFCARRY_MASK)) > 0;
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

    /**
     * Reads a byte and increments the program counter
     * @return 
     */
    public byte readProgramByte() {
        return (byte) (memory[programCounter++] & 0x00FF);
    }


    public int readMemory(int address) {
        return memory[address] & 0xFF;
    }

    public void writeMemory(int destAaddress, int value) {
        memory[destAaddress & 0xFFFF] = (byte)(0xFF & value);
    }
}
