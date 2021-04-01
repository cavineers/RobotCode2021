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
        this.add(240.0,  70.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(240.0, -10.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(300.0, -10.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(300.0,  65.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(240.0,  65.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(240.0, -30.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(40.0,  -30.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(40.0,   25.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(0.0,   5.0, 0.0, 0.0, 0.0, 10.0, 1.0);

        this.finish();
    }
}
