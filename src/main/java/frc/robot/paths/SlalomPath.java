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

        this.add(75.0, 0.0, 0.0, 10.0, 10.0);
        this.add(75.0, 85.0, 0.0, 10.0, 10.0);
        this.add(270.0, 85.0, 0.0, 10.0, 10.0);
        this.add(270.0, -30.0, 0.0, 10.0, 10.0);
        this.add(350.0, 0.0, 0.0, 10.0, 10.0);
        this.add(350.0, 40.0, 0.0, 10.0, 10.0);
        this.add(270.0, 31.0, 0.0, 10.0, 10.0);
        this.add(270, -10.0, 0.0, 10, 10.0);
        this.add(80, 0.0, 0.0, 10, 10.0);
        this.add(80.0, 85.0, 0.0, 10.0, 10.0);
        this.add(0.0, 85.0, 0.0, 10.0, 10.0);

        this.finish();
    }
}
