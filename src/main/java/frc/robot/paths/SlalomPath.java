package frc.robot.paths;

import frc.lib.autonomous.ExactPath;

public class SlalomPath extends ExactPath {
    public SlalomPath() {
        super();

        this.setTolerance(10, 1);

        this.addPlot(50.0, 0.0, 0.0);
        this.addPlot(95.0, 65.0, 0.0);
        this.addPlot(150.0, 80.0, 0.0);
        this.addPlot(220.0, 50.0, 0.0);
        this.addPlot(275.0, -5.0, 0.0);
        this.addPlot(315.0, 31.0, 0.0);
        this.addPlot(275.0, 70.0, 0.0);
        this.addPlot(220.0, 0.0, 0.0);
        this.addPlot(160.0, -5.0, 0.0);
        this.addPlot(75.0, 0.0, 0.0);
        this.addPlot(52.0, 61.0, 0.0);
        this.addPlot(0.0, 65.0, 0.0);

        this.finish();
    }
}
