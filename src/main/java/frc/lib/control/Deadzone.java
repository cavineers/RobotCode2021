package frc.lib.control;

import edu.wpi.first.wpilibj.util.Units;

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

    /**
     * Get the radial deadzone and curve of the controller drive input.

     * @param input Controller input (fwd, str, rot).
     * @return The modified input in the same format.
     */
    public static ControllerDriveInput advanced(ControllerDriveInput input) {
        // Apply input curve fwd, str
        input.setFwd(input.getFwd() < 0 ? -Math.pow(input.getFwd(), 3) : Math.pow(input.getFwd(), 3));
        input.setStf(input.getStf() < 0 ? -Math.pow(input.getStf(), 3) : Math.pow(input.getStf(), 3));

        // Use a less aggressive input curve for rot
        input.setRot(input.getRot() < 0 ? -Math.pow(input.getRot(), 2) : Math.pow(input.getRot(), 2));

        // Find degrees from 0,0 to the X,Y of str and fwd
        double deg = Units.radiansToDegrees(Math.atan2(input.getStf(), input.getFwd()));

        // Deadzone (in degrees)
        double dz = 5.0;

        // If within dz on top
        if (Math.abs(deg) >= 0 - dz && Math.abs(deg) <= 0 + dz) {
            input.setStf(0.0);
        }

        // If within dz on right
        if (Math.abs(deg) >= 90 - dz && Math.abs(deg) <= 90 + dz) {
            input.setFwd(0.0);
        }

        // If within dz on bottom
        if (Math.abs(deg) >= 180 - dz && Math.abs(deg) <= 180 + dz) {
            input.setStf(0.0);
        }

        // If within dz on left
        if (Math.abs(deg) >= 270 - dz && Math.abs(deg) <= 270 + dz) {
            input.setFwd(0.0);
        }

        return input;
    }
}