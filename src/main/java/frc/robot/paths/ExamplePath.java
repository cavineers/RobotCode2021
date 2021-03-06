package frc.robot.paths;

import frc.lib.autonomous.Path;

/**
 * Example path.
 */
public class ExamplePath extends Path {
    /**
     * Create example path.
     */
    public ExamplePath() {
        super();

        this.add(50.0, 0.0, -180.0, 1, 1);
        this.add(95.0, 65.0, 0.0, 1, 1);
        this.add(150.0, 80.0, 0.0, 1, 1);
        this.add(220.0, 50.0, 0.0, 1, 1);
        this.add(275.0, -5.0, 60.0, 1, 1);
        this.add(315.0, 31.0, -90.0, 1, 1);
        this.add(275.0, 70.0, -180.0, 1, 1);
        this.add(220.0, 0.0, 180.0, 1, 1);
        this.add(160.0, -5.0, 180.0, 1, 1);
        this.add(75.0, 0.0, 180.0, 1, 1);
        this.add(52.0, 61.0, 90.0, 1, 1);
        this.add(0.0, 65.0, 0.0, 1, 1);

        this.finish();
    }
}
