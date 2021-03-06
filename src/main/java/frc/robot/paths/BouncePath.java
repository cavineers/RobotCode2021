package frc.robot.paths;

import frc.lib.autonomous.Path;

/**
 * Bounce path.
 */
public class BouncePath extends Path {
    /**
     * Bounce path.
     */
    public BouncePath() {
        super();

        // TIME: 6.471080000000004

        this.add(55.0, 7.0, 0.0, 2.5, 1.0);
        this.add(59.0, 42.0, 0.0, 2.5, 1.0);
        this.add(65.0, 6.0, 0.0, 2.5, 1.0);
        this.add(89.0, -5.0, 0.0, 5.0, 1.0);
        this.add(96.0, -56.0, 0.0, 2.5, 1.0);
        this.add(149.0, -55.0, 0.0, 3.0, 1.0);
        this.add(151.0, 42.0, 0.0, 2.5, 1.0);
        this.add(153.0, -56.0, 0.0, 2.5, 1.0);
        this.add(236.0, -55.0, 0.0, 2.5, 1.0);
        this.add(241.0, 42.0, 0.0, 2.5, 1.0);
        this.add(244.0, -2.0, 0.0, 5.0, 1.0);
        this.add(257.0, -1.0, 0.0, 2.8, 1.0);

        this.finish();
    }
}