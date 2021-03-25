package frc.robot.paths;

import frc.lib.autonomous.Path;

/**
 * Slalom path.
 */
public class SlalomPath extends Path {
    /**
     * Create slalom path.
     */
    public SlalomPath() {
        super();

        // TIME: 7.659928999999998
        // START POSITION: 30, 30

        this.add(90.0, 0.0, 0.0, 10.0, 10.0);
        this.add(90.0, 85.0, 0.0, 10.0, 10.0);
        this.add(280.0, 85.0, 0.0, 10.0, 10.0);
        this.add(280.0, -10.0, 0.0, 10.0, 10.0);
        this.add(330.0, 0.0, 0.0, 10.0, 10.0);
        this.add(330.0, 40.0, 0.0, 10.0, 10.0);
        this.add(280.0, 31.0, 0.0, 10.0, 10.0);
        this.add(280, -10.0, 0.0, 10, 10.0);

        this.finish();
    }
}
