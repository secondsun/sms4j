package net.saga.console.emulator.sms.sms4j.instruction;

import net.saga.console.emulator.sms.sms4j.z80.Register;
import net.saga.console.emulator.sms.sms4j.z80.Z80;

public abstract class LoadToMemory implements InstructionExecution {
    protected final Register source;
    protected final int destAaddress;
    protected final Z80 cpu;
    protected final int cycles;

    public LoadToMemory(int destAaddress, Register source, Z80 cpu, int cycles) {
        this.destAaddress = destAaddress;
        this.cpu = cpu;
        this.cycles = cycles;
        this.source = source;
    }

    public static class EightBits extends LoadToMemory {

        public EightBits(int destAaddress, Register<Byte> source, Z80 cpu, int cycles) {
            super(destAaddress, source, cpu, cycles);
        }

        @Override
        public int exec() {
            cpu.writeMemory(destAaddress, source.getValue());
            return cycles;
        }
    }

    public static class SixteenBits extends LoadToMemory {

        public SixteenBits(int destAaddress, Register<Short> source, Z80 cpu, int cycles) {
            super(destAaddress, source, cpu, cycles);
        }

        @Override
        public int exec() {
            cpu.writeMemory(destAaddress, (source.getValue() & 0xFF));
            cpu.writeMemory(destAaddress + 1, ((source.getValue() >> 8) & 0xFF));

            return cycles;
        }
    }

}
