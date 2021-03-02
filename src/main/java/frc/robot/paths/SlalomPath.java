package frc.robot.paths;

import frc.lib.autonomous.ExactPath;

/**
 * Slalom path.
 */
public class SlalomPath extends ExactPath {
    /**
     * Create slalom path.
     */
    public SlalomPath() {
        super();

        // TIME: 7.659928999999998

        this.addPlot(50.0, 0.0, 0.0, 5.0, 1.0);
        this.addPlot(60.0, 0.0, 0.0, 5.0, 1.0);
        this.addPlot(60.0, 57.0, 0.0, 5.0, 1.0);
        this.addPlot(95.0, 65.0, 0.0, 5.0, 1.0);
        this.addPlot(150.0, 80.0, 0.0, 10.0, 1.0);
        this.addPlot(240.0, 50.0, 0.0, 2.5, 1.0);
        this.addPlot(250.0, 0.0, 0.0, 3.0, 1.0);
        this.addPlot(275.0, -5.0, 0.0, 2.5, 1.0);
        this.addPlot(295.0, 10.0, 0.0, 4.0, 1.0);
        this.addPlot(310.0, 31.0, 0.0, 2.5, 1.0);
        this.addPlot(295.0, 55.0, 0.0, 2.5, 1.0);
        this.addPlot(275.0, 70.0, 0.0, 2.0, 1.0);
        this.addPlot(240.0, 50.0, 0.0, 2.0, 1.0);
        this.addPlot(235.0, 0.0, 0.0, 5.0, 1.0);
        this.addPlot(160.0, -5.0, 0.0, 10.0, 1.0);
        this.addPlot(67.0, 0.0, 0.0, 3.5, 1.0);
        this.addPlot(52.0, 61.0, 0.0, 4.0, 1.0);
        this.addPlot(42.0, 67.0, 0.0, 2.2, 1.0);

        this.finish();
    }
}
