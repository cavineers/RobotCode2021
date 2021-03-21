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

        // Full systems test
        // this.add(50.0, 0.0, -180.0, 1, 1);
        // this.add(95.0, 65.0, 0.0, 1, 1);
        // this.add(150.0, 80.0, 0.0, 1, 1);
        // this.add(220.0, 50.0, 0.0, 1, 1);
        // this.add(275.0, -5.0, 60.0, 1, 1);
        // this.add(315.0, 31.0, -90.0, 1, 1);
        // this.add(275.0, 70.0, -180.0, 1, 1);
        // this.add(220.0, 0.0, 180.0, 1, 1);
        // this.add(160.0, -5.0, 180.0, 1, 1);
        // this.add(75.0, 0.0, 180.0, 1, 1);
        // this.add(52.0, 61.0, 90.0, 1, 1);
        // this.add(0.0, 65.0, 0.0, 1, 1);
        
        // Straight Line
        // this.add(96.0, 0.0, 0.0, 1, 1);

        // Rotate
        // this.add(0.0, 0.0, 90.0, 10.0, 1.0);

        // Simple Test
        // this.add(100.0, 0.0, 90.0, 3.0, 2.0);
        // this.add(0.0, -40.0, 180.0, 3.0, 2.0);
        // this.add(30.0, 0.0, -180.0, 3.0, 2.0);

        // Testing Path
        this.add(65.0, 0.0, 0.0, 3.0, 2.0); // C3
        this.add(95.0, -60.0, 0.0, 3.0, 2.0); // E4
        // this.add(240.0, 60.0, 0.0, 3.0, 2.0); //A9
        // this.add(300.0, -30.0, 0.0, 3.0, 2.0); // D11
        // this.add(30.0, 60.0, 0.0, 3.0, 2.0); // A2


        this.finish();
    }
}
