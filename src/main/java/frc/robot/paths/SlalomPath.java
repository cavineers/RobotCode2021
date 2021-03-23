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

        this.add(100.0, 0.0, 0.0, 3.0, 3.0);
        this.add(100.0, 80.0, 0.0, 3.0, 10.0);
        this.add(275.0, 80.0, 0.0, 3.0, 3.0);
        // this.add(280.0, -10.0, 0.0, 3.0, 3.0);
        // this.add(350.0, 0.0, 0.0, 3.0, 3.0);
        // this.add(375.0, 40.0, 0.0, 4.0, 1.0);
        // this.add(310.0, 31.0, 0.0, 2.5, 1.0);
        // this.add(295.0, 55.0, 0.0, 2.5, 1.0);
        // this.add(275.0, 70.0, 0.0, 2.0, 1.0);
        // this.add(240.0, 50.0, 0.0, 2.0, 1.0);
        // this.add(235.0, 0.0, 0.0, 5.0, 1.0);
        // this.add(160.0, -5.0, 0.0, 10.0, 1.0);
        // this.add(67.0, 0.0, 0.0, 3.5, 1.0);
        // this.add(52.0, 61.0, 0.0, 4.0, 1.0);
        // this.add(42.0, 67.0, 0.0, 2.2, 1.0);

        this.finish();
    }
}
