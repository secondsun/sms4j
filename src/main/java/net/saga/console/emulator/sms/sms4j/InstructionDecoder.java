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
package net.saga.console.emulator.sms.sms4j;

import net.saga.console.emulator.sms.sms4j.instruction.*;
import net.saga.console.emulator.sms.sms4j.instruction.arithmitic.ALUAction;
import net.saga.console.emulator.sms.sms4j.instruction.contition.Condition;
import net.saga.console.emulator.sms.sms4j.z80.MemoryRegister;
import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of http://www.z80.info/decoding.htm
 *
 * @author summers
 */
public class InstructionDecoder {

    private static final InstructionExecution NOOP = new Noop();
    private static final Set<Integer> PREFIX_BYTES = new HashSet<Integer>(Arrays.asList(new Integer[]{0xCB, 0xDD, 0xED, 0xFD}));
    private final Z80 z80;
    private Register[] tableR = new Register[8];
    private Register[] tableRP = new Register[4];
    private Register[] tableRP2 = new Register[4];
    private Condition[] tableCC = new Condition[8];
    private ALUAction[] tableAlu = new ALUAction[8];

    public InstructionDecoder(Z80 z80) {
        this.z80 = z80;
        buildTables();
    }

    public InstructionExecution decode(Z80 z80) {
        byte firstByte = z80.readProgramByte();
        boolean hasPrefix = PREFIX_BYTES.contains(firstByte);

        if (hasPrefix) {
            return decodePrefixedInstruction(firstByte, z80);
        } else {
            return decodeOpcode(firstByte, z80);
        }

    }

    private InstructionExecution decodePrefixedInstruction(int prefix, Z80 z80) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private InstructionExecution decodeOpcode(int opcode, Z80 z80) {
        int z = opcode & 0b111;
        int y = (opcode & 0b111000) >> 3;
        int x = (opcode & 0b11000000) >> 6;
        int q = y % 2;
        int p = y >> 1;

        switch (x) {
            case 0:
                return decodeUnprefixedOpCodeX0(y, z, q, p, z80);
            case 1:
                return decodeUnprefixedOpCodeX1(y, z, q, p, z80);
            case 2:
                //alu[y] r[z]
                return new AluInstruction(tableAlu[y], tableR[z], z80.getRegisterA(), z80, tableR[z] instanceof MemoryRegister ?7:4, (byte) 0xFF);
            case 3:
                return decodeUnprefixedOpCodeX3(y, z, q, p, z80);
            default:
                return NOOP;
        }

    }

    private void buildTables() {
        buildRTable();
        buildRPTable();
        buildRP2Table();
        buildCCTable();
        buildALUTable();
    }

    private void buildALUTable() {
        tableAlu[0] = ALUAction.ADD_A;
        tableAlu[1] = ALUAction.ADC_A;
        tableAlu[2] = ALUAction.SUB;
        tableAlu[3] = ALUAction.SBC_A;
        tableAlu[4] = ALUAction.AND;
        tableAlu[5] = ALUAction.XOR;
        tableAlu[6] = ALUAction.OR;
        tableAlu[7] = ALUAction.CP;

    }

    private void buildRP2Table() {
        tableRP2[0] = z80.getRegisterBC();
        tableRP2[1] = z80.getRegisterDE();
        tableRP2[2] = z80.getRegisterHL();
        tableRP2[3] = z80.getRegisterAF();
    }

    private void buildRPTable() {
        tableRP[0] = z80.getRegisterBC();
        tableRP[1] = z80.getRegisterDE();
        tableRP[2] = z80.getRegisterHL();
        tableRP[3] = z80.getSP();
    }

    private void buildCCTable() {
        tableCC[0] = Condition.NO_ZERO;
        tableCC[1] = Condition.ZERO;
        tableCC[2] = Condition.NO_CARRY;
        tableCC[3] = Condition.CARRY;
        tableCC[4] = Condition.PO;
        tableCC[5] = Condition.PE;
        tableCC[6] = Condition.NO_SIGN;
        tableCC[7] = Condition.SIGN;

    }

    private void buildRTable() {
        tableR[0] = z80.getRegisterB();
        tableR[1] = z80.getRegisterC();
        tableR[2] = z80.getRegisterD();
        tableR[3] = z80.getRegisterE();
        tableR[4] = z80.getRegisterH();
        tableR[5] = z80.getRegisterL();
        tableR[6] = z80.getMemoryRegisterHL();
        tableR[7] = z80.getRegisterA();

    }

    private InstructionExecution decodeUnprefixedOpCodeX0(int y, int z, int q, int p, Z80 z80) {
        switch (z) {
            case 0:
                switch (y) {
                    case 0:
                        return NOOP;
                    case 1:
                        //EX AF, AF'
                    case 2:
                        //DJNZ d
                    case 3:
                        //JR d
                    default:
                        //JR cc[y-4], d
                        throw new IllegalStateException("Not implemented");
                }
            case 1:
                switch (q) {
                    case 0:
                        return new LoadImmediate(tableRP[p], read16());
                    case 1:
                        return new AluInstruction(tableAlu[0], tableRP[p], z80.getRegisterHL(), z80, 11, (byte) 0b00010011);
                        //return new AddToHL(tableRP[p], z80);
                }
            case 2:
                switch (q) {
                    case 0:
                        switch (p) {
                            case 0:
                                //LD (BC), A
                                return new LoadToMemory.EightBits(z80.getBC(), z80.getRegisterA(), z80, 7);
                            case 1:
                                //LD (DE), A
                                return new LoadToMemory.EightBits(z80.getDE(), z80.getRegisterA(), z80, 7);
                            case 2:
                                //LD (nn), HL
                                return new LoadToMemory.SixteenBits(read16(), z80.getRegisterHL(), z80, 16);
                            case 3:
                                //LD (nn), A
                                return new LoadToMemory.EightBits(read16(), z80.getRegisterA(), z80, 13);
                            default:
                                throw new IllegalStateException("Not implemented");
                        }
                    case 1:
                        switch (p) {
                            case 0:
                                //LD A, (BC)
                                return new LoadFromMemory.EightBits(z80.getRegisterA(), z80.getBC(), z80, 7);
                            case 1:
                                //LD A, (DE)
                                return new LoadFromMemory.EightBits(z80.getRegisterA(), z80.getDE(), z80, 7);
                            case 2:
                                //LD HL, (nn)
                                return new LoadFromMemory.SixteenBits(z80.getRegisterHL(), read16(), z80, 16);
                            case 3:
                                //LD A, (nn)
                                return new LoadFromMemory.EightBits(z80.getRegisterA(), read16(), z80, 13);
                            default:
                                throw new IllegalStateException("Not implemented");
                        }
                }
            case 3:
                switch (q) {
                    case 0:
                        return new IncrementRegisterInstructionSixteenBit(tableRP[p], z80);
                    case 1:
                        return new DecrementRegisterInstructionSixteenBit(tableRP[p], z80);
                }
            case 4:
                return new IncrementRegisterInstructionEightBit(tableR[y], z80);
            case 5:
                return new DecrementRegisterInstructionEightBit(tableR[y], z80);
            case 6:
                return new LoadImmediate(tableR[y], z80.readProgramByte());
            case 7: //Assorted operations on accumulator/flags
                switch (y) {
                    case 0:
                        //RLCA
                    case 1:
                        //RRCA
                    case 2:
                        //RLA
                    case 3:
                        //RRA
                    case 4:
                        //DAA
                    case 5:
                        //CPL
                    case 6:
                        //SCF
                    case 7:
                        //CCF
                }
            default:
                throw new IllegalStateException("Not implemented");
        }
    }

    private InstructionExecution decodeUnprefixedOpCodeX1(int y, int z, int q, int p, Z80 z80) {
        if (z == 6 && y == 6) {
            throw new RuntimeException("HALT");
        } else {
            return new LoadFromRegister(tableR[y], tableR[z]);
        }
    }

    private InstructionExecution decodeUnprefixedOpCodeX3(int y, int z, int q, int p, Z80 z80) {
        switch (z) {
            case 0:
                //RET cc[y]
            case 1:
                switch (q) {
                    case 0:
                        //pop rp2[p]
                    case 1:
                        switch (p) {
                            case 0:
                                //RET
                            case 1:
                                //EXX
                            case 2:
                                //JP HL
                            case 3:
                                //LD SP,HL
                                return new LoadFromRegister(z80.getSP(), z80.getRegisterHL(), 6);

                        }
                }
            case 2:
                //	JP cc[y], nn
            case 3:
                switch (y) {
                    case 0:
                        //JP nn
                    case 1:
                        //(CB prefix)
                    case 2:
                        //OUT (n), A
                    case 3:
                        //IN A, (n)
                    case 4:
                        //EX (SP), HL
                    case 5:
                        //EX DE, HL
                    case 6:
                        //DI
                    case 7:
                        //EI
                }
            case 4:
                //CALL cc[y], nn
            case 5:
                switch (q) {
                    case 0:
                        //PUSH rp2[p]
                    case 1:
                        switch (p) {
                            case 0:
                                //CALL nn
                            case 1:
                                //(DD prefix)
                            case 2:
                                //(ED prefix)
                            case 3:
                                //(FD prefix)
                        }

                }
            case 6:
                //alu[y] n
            case 7:
                //RST y*8 Restart
            default:
                throw new IllegalStateException("Not supported");
        }
    }

    private int read16() {
        byte low = z80.readProgramByte();
        byte high = z80.readProgramByte();
        return ((high << 8) & 0xFF00) | (low & 0xFF);
    }

}
