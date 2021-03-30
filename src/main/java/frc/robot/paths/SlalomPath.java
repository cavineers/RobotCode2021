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

        //         X       Y   ROT  xV   yV    tT   rT
        this.add(60.0,    0.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(60.0,   70.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(260.0,  70.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(260.0, -10.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(320.0, -10.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(320.0,  50.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(260.0,  50.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(260.0, -20.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(50.0,  -20.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(50.0,   30.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(30.0,   30.0, 0.0, 0.0, 0.0, 10.0, 1.0);

        this.finish();
    }
}
