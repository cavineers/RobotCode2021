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

        this.addPlot(55.0, 7.0, 0.0, 2.5, 1.0);
        this.addPlot(59.0, 42.0, 0.0, 2.5, 1.0);
        this.addPlot(65.0, 6.0, 0.0, 2.5, 1.0);
        this.addPlot(89.0, -5.0, 0.0, 5.0, 1.0);
        this.addPlot(96.0, -56.0, 0.0, 2.5, 1.0);
        this.addPlot(149.0, -55.0, 0.0, 3.0, 1.0);
        this.addPlot(151.0, 42.0, 0.0, 2.5, 1.0);
        this.addPlot(153.0, -56.0, 0.0, 2.5, 1.0);
        this.addPlot(236.0, -55.0, 0.0, 2.5, 1.0);
        this.addPlot(241.0, 42.0, 0.0, 2.5, 1.0);
        this.addPlot(244.0, -2.0, 0.0, 5.0, 1.0);
        this.addPlot(257.0, -1.0, 0.0, 2.8, 1.0);

        this.finish();
    }
}