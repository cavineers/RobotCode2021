package frc.robot.paths;

import frc.lib.autonomous.ExactPath;

/**
 * Example path.
 */
public class ExamplePath extends ExactPath {
    /**
     * Create example path.
     */
    public ExamplePath() {
        super();

        this.addPlot(50.0, 0.0, -180.0, 1, 1);
        this.addPlot(95.0, 65.0, 0.0, 1, 1);
        this.addPlot(150.0, 80.0, 0.0, 1, 1);
        this.addPlot(220.0, 50.0, 0.0, 1, 1);
        this.addPlot(275.0, -5.0, 60.0, 1, 1);
        this.addPlot(315.0, 31.0, -90.0, 1, 1);
        this.addPlot(275.0, 70.0, -180.0, 1, 1);
        this.addPlot(220.0, 0.0, 180.0, 1, 1);
        this.addPlot(160.0, -5.0, 180.0, 1, 1);
        this.addPlot(75.0, 0.0, 180.0, 1, 1);
        this.addPlot(52.0, 61.0, 90.0, 1, 1);
        this.addPlot(0.0, 65.0, 0.0, 1, 1);

        this.finish();
    }
}
