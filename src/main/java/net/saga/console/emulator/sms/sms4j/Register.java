
package net.saga.console.emulator.sms.sms4j;

/**
 *
 * @author summers
 */
public class Register {
    
    private int value;//Registers are 8 bits.  We will use masking for get/set operations.

    /**
     * Returns the value of this register, will be 0-255.
     * @return register value
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the value of this register as a byte, will be -128 - 127.
     * This is because everything  in Java is signed.
     * @return register value masked as a byte.
     */
    public byte getValueAsByte() {
        return (byte)(value&0xFF);
    }

    /**
     * Sets the value of this rregister, value will be masked with 0xFF.
     * @param value the new value
     */
    public void setValue(int value) {
        this.value = value & 0xFF;
    }
    
    
}
