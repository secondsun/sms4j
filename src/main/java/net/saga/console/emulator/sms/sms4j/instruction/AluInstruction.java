package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.instruction.InstructionExecution;
import net.saga.console.emulator.sms.sms4j.instruction.arithmitic.ALUAction;
import net.saga.console.emulator.sms.sms4j.z80.EightBitDirectRegister;
import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public class AluInstruction implements InstructionExecution {
    private final ALUAction aluAction;
    private final Register destination;
    private final Z80 z80;
    private final int cycles;
    private final Register operand;
    private final byte flagsMask;

    /**
     *
     * @param aluAction The action to perform
     * @param operatand the operand of the aluAction
     * @param destination the destinaiton of the ALU action (often the accumulator)
     * @param z80 the z80 system
     * @param cycles the number of cycles the operation will take
     * @param flagsMask the mask to apply to the output of the operation before ORing with the fRegister.
     */
    public AluInstruction(ALUAction aluAction, Register operatand, Register destination, Z80 z80, int cycles, byte flagsMask) {
        this.aluAction = aluAction;
        this.destination = destination;
        this.operand = operatand;
        this.z80 = z80;
        this.cycles = cycles;
        this.flagsMask = flagsMask;
    }

    @Override
    public int exec() {

        EightBitDirectRegister flagsRegister = z80.getRegisterF();
        byte flags = aluAction.execute(destination, operand);
        flags &= flagsMask;

        byte oldFlags = flagsRegister.getValueAsByte();
        oldFlags &= (~flagsMask);//0 out the flags we are setting and save the flags we masked from the new flags

        flagsRegister.setValue(oldFlags | flags);

        return cycles;
    }
}
