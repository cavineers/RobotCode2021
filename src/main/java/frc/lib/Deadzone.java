package frc.lib;

public class Deadzone {
    public static double apply(double input, double removed) {
        if (Math.abs(input) <= removed)
            input = 0;
        else if (input < 0)
            input = -Math.pow(input, 2);
        else
            input = Math.pow(input, 2);
        return input;
    }
}