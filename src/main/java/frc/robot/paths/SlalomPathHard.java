package frc.robot.paths;

import frc.lib.autonomous.ExactPath;

public class SlalomPathHard extends ExactPath {
    public SlalomPathHard() {
        super();

        // TIME: 14.58

        this.addPlot(50.0, 0.0, 0.0, 2.5, 1.0);
        this.addPlot(60.0, 60.0, 0.0, 2.5, 1.0);
        this.addPlot(240.0, 60.0, 0.0, 2.5, 1.0);
        this.addPlot(250.0, 0.0, 0.0, 2.5, 1.0);
        this.addPlot(295.0, 0.0, 0.0, 2.5, 1.0);
        this.addPlot(295.0, 55.0, 0.0, 2.5, 1.0);
        this.addPlot(240.0, 60.0, 0.0, 2.5, 1.0);
        this.addPlot(250.0, 0.0, 0.0, 2.5, 1.0);
        this.addPlot(67.0, 0.0, 0.0, 2.5, 1.0);
        this.addPlot(67.0, 61.0, 0.0, 2.5, 1.0);
        this.addPlot(40.0, 65.0, 0.0, 2.5, 1.0);

        this.finish();
    }
}
