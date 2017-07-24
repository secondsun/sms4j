
package net.saga.console.emulator.sms.sms4j.z80;

/**
 *
 * @author summers
 */
public class SixteenBitDirectRegister implements Register<Short> {
    
    private int value = 0;//Registers are 16 bits.  We will use masking for get/set operations.

    /**
     * Returns the value of this register, will be 0-0xFFFF.
     * @return register value
     */
    @Override
    public int getValue() {
        return value;
    }
    
    /**
     * Sets the value of this register, value will be masked with 0xFFFF.
     * @param value the new value
     */
    @Override
    public void setValue(int value) {
        this.value = value & 0xFFFF;
    }

    @Override
    public int getSize() {
        return 16;
    }
}
