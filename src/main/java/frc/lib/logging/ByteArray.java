package frc.lib.logging;

/**
 * Byte array utils.
 */
public class ByteArray {
    /**
     * Concatenate two byte arrays.

     * @param a Array 1
     * @param b Array 2
     * @return Combined arrays
     */
    public static byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length]; 
        System.arraycopy(a, 0, result, 0, a.length); 
        System.arraycopy(b, 0, result, a.length, b.length); 
        return result;
    }
}
