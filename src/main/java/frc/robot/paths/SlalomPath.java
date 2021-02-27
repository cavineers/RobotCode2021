package frc.robot.paths;

import frc.lib.autonomous.ExactPath;

public class SlalomPath extends ExactPath {
    public SlalomPath() {
        super();

        // TIME: 13.95

        this.addPlot(50.0, 0.0, 0.0, 5.0, 1.0);
        this.addPlot(60.0, 0.0, 0.0, 5.0, 1.0);
        this.addPlot(60.0, 57.0, 0.0, 5.0, 1.0);
        this.addPlot(95.0, 65.0, 0.0, 5.0, 1.0);
        this.addPlot(150.0, 80.0, 0.0, 10.0, 1.0);
        this.addPlot(240.0, 50.0, 0.0, 2.5, 1.0);
        this.addPlot(250.0, 0.0, 0.0, 3.0, 1.0);
        this.addPlot(275.0, -5.0, 0.0, 2.5, 1.0);
        this.addPlot(295.0, 15.0, 0.0, 4.0, 1.0);
        this.addPlot(315.0, 31.0, 0.0, 2.5, 1.0);
        this.addPlot(295.0, 55.0, 0.0, 2.5, 1.0);
        this.addPlot(275.0, 70.0, 0.0, 2.0, 1.0);
        this.addPlot(240.0, 50.0, 0.0, 2.0, 1.0);
        this.addPlot(235.0, 0.0, 0.0, 5.0, 1.0);
        this.addPlot(160.0, -5.0, 0.0, 10.0, 1.0);
        this.addPlot(67.0, 0.0, 0.0, 3.5, 1.0);
        this.addPlot(52.0, 61.0, 0.0, 2.5, 1.0);
        this.addPlot(40.0, 65.0, 0.0, 2.0, 1.0);

        this.finish();
    }
}
