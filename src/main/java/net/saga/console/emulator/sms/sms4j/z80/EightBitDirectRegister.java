
package net.saga.console.emulator.sms.sms4j.z80;

/**
 *
 * @author summers
 */
public class EightBitDirectRegister implements Register {
    
    private int value;//Registers are 8 bits.  We will use masking for get/set operations.

    /**
     * Returns the value of this register, will be 0-255.
     * @return register value
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * Returns the value of this register as a byte, will be -128 - 127.
     * This is because everything  in Java is signed.
     * @return register value masked as a byte.
     */
    public byte getValueAsByte() {
        return (byte)(0xFF & getValue());
    }
    
    /**
     * Sets the value of this register, value will be masked with 0xFF.
     * @param value the new value
     */
    @Override
    public void setValue(int value) {
        this.value = value & 0xFF;
    }
    
    
}
