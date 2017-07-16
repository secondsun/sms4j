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

import net.saga.console.emulator.sms.sms4j.z80.Z80;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.saga.console.emulator.sms.sms4j.instruction.Condition;
import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.instruction.Noop;
import net.saga.console.emulator.sms.sms4j.z80.Register;

/**
 *
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

    public InstructionDecoder(Z80 z80) {
        this.z80 = z80;
        buildTables();
    }

    public InstructionExecution decode(int firstByte, int... sequence) {
        boolean hasPrefix = PREFIX_BYTES.contains(firstByte);

        if (hasPrefix) {
            return decodePrefixedInstruction(firstByte, sequence);
        } else {
            return decodeOpcode(firstByte, sequence);
        }

    }

    private InstructionExecution decodePrefixedInstruction(int prefix, int[] sequence) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private InstructionExecution decodeOpcode(int opcode, int[] sequence) {
        int z = opcode & 0b111;
        int y = opcode & 0b111000 >> 3;
        int x = opcode & 0b11000000 >> 6;
        int q = y % 2;
        int p = y >> 1;

        switch (x) {
            case 0:
                return decodeUnprefixedOpCodeX0(y, z, q, p, sequence);
            case 1:
            case 2:
            case 3:
            default:
                return NOOP;
        }

    }

    private void buildTables() {
        buildRTable();
        buildRPTable();
        buildRP2Table();
        buildCCTable();
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

    private InstructionExecution decodeUnprefixedOpCodeX0(int y, int z, int q, int p, int[] sequence) {
        switch (z) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            default:
                return NOOP;

        }
    }

}
