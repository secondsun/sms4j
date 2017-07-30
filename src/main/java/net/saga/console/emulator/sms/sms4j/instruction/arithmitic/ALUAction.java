package net.saga.console.emulator.sms.sms4j.instruction.arithmitic;

import net.saga.console.emulator.sms.sms4j.z80.Flags;
import net.saga.console.emulator.sms.sms4j.z80.Register;

import static net.saga.console.emulator.sms.sms4j.util.Utils.countBits;

@FunctionalInterface
public interface ALUAction {

    /**
     * This will execute a ALU operation and store the result in destination using the source value and return a byte
     * that is all of the flags that were set in the operation.  Note that actual opcodes may not set all of the flags
     * returned.
     *
     * @param destination the register to store values in
     * @param source      the second operand
     * @return flags which may be set by the operation
     */
    byte execute(Register destination, Register source, int carry);

}
