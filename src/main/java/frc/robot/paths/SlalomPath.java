package frc.robot.paths;

import frc.lib.autonomous.ExactPath;

public class SlalomPath extends ExactPath {
    public SlalomPath() {
        super();

        this.addPlot(30.0, 30.0, 0.0); // Starting position
        this.addPlot(36.0, 72.0, 0.0);
        this.addPlot(90.0, 120.0, 0.0);
        this.addPlot(108.0, 180.0, 0.0);
        this.addPlot(84.0, 252.0, 0.0);
        this.addPlot(30.0, 300.0, 0.0);
        this.addPlot(60.0, 348.0, 0.0);
        this.addPlot(90.0, 300.0, 0.0);
        this.addPlot(48.0, 252.0, 0.0);
        this.addPlot(30.0, 186.0, 0.0);
        this.addPlot(36.0, 120.0, 0.0);
        this.addPlot(90.0, 30.0, 0.0);

        this.finish();
    }
}
