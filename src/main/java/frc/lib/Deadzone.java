package frc.lib;

/**
 * Deadzone utils.
 */
public class Deadzone {
    /**
     * Apply deadzone.

     * @param input Input data
     * @param removed Deadzone
     * @return Output
     */
    public static double apply(double input, double removed) {
        if (Math.abs(input) <= removed) {
            input = 0;
        } else if (input < 0) {
            input = -Math.pow(input, 2);
        } else {
            input = Math.pow(input, 2);
        }
        return input;
    }
}