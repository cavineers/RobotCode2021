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

        //         X       Y   ROT  xV   yV    tT   rT
        this.add(60.0,    0.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(60.0,   45.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(60.0,   -5.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(80.0,  -10.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(100.0, -75.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(150.0, -75.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(150.0,  45.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(150.0, -75.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(240.0, -75.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(240.0,  45.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(240.0, -30.0, 0.0, 0.0, 0.0, 10.0, 1.0);
        this.add(270.0, -30.0, 0.0, 0.0, 0.0, 10.0, 1.0);

        this.finish();
    }
}