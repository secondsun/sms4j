package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.EightBitDirectRegister;
import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.SixteenBitCombinedRegister;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public abstract class LoadFromMemory implements InstructionExecution {
    protected final Register destination;
    protected final int address;
    protected final Z80 cpu;
    protected final int cycles;

    public LoadFromMemory(Register destination, int address, Z80 cpu, int cycles) {
        this.address = address;
        this.cpu = cpu;
        this.cycles = cycles;
        this.destination = destination;
    }

    public static class EightBits extends LoadFromMemory {

        public EightBits(Register<Byte> destination, int address, Z80 cpu, int cycles) {
            super(destination, address, cpu, cycles);
        }

        @Override
        public int exec() {
            destination.setValue(cpu.readMemory(address));
            return cycles;
        }
    }

    public static class SixteenBits extends LoadFromMemory {

        public SixteenBits(Register<Short> destination, int address, Z80 cpu, int cycles) {
            super(destination, address, cpu, cycles);
        }

        @Override
        public int exec() {
            destination.setValue(cpu.readMemory(address) | (cpu.readMemory(address + 1) << 8));
            return cycles;
        }
    }

}
