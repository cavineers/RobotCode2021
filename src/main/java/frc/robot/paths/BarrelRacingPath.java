package frc.robot.paths;

import frc.lib.autonomous.Path;

/**
 * Barrel racing path.
 */
public class  BarrelRacingPath extends Path {
    /**
     * Create barrel racing path.
     */
    public BarrelRacingPath() {
        super();

        // TIME: 15.923585999999997
        this.add(86.0, 5.0, 0.0, 2.5, 5.0);
        this.add(130.0, -8.0, 16.0, 2.5, 5.0);
        this.add(144.0, -16.0, 59.0, 2.5, 5.0);
        this.add(146.0, -33.0, 90.0, 2.0, 5.0);
        this.add(145.0, -41.0, 117.0, 2.5, 5.0);
        this.add(132.0, -50.0, 150.0, 3.0, 5.0);
        this.add(119.0, -55.0, 180.0, 2.5, 5.0);
        this.add(107.0, -50.0, 208.0, 2.5, 5.0);
        this.add(92.0, -35.0, 270.0, 2.5, 5.0);
        this.add(94.5, -25.0, 287.0, 2.5, 5.0);
        this.add(108.0, -5.0, 331.0, 2.5, 5.0);
        this.add(129.0, 0.0, 360.0, 2.5, 5.0);
        this.add(202.0, 5.0, 360.0, 2.5, 5.0);
        this.add(231.0, 8.0, 340.0, 2.5, 5.0);
        this.add(232.0, 19.0, 300.0, 2.5, 5.0);
        this.add(237.0, 32.0, 271.0, 2.5, 5.0);
        this.add(231.0, 45.0, 229.0, 2.5, 5.0);
        this.add(218.0, 52.0, 192.0, 2.5, 5.0);
        this.add(202.0, 53.0, 156.0, 2.5, 5.0);
        this.add(191.0, 46.0, 124.0, 2.5, 5.0);
        this.add(184.0, 34.0, 80.0, 2.5, 5.0);
        this.add(196.0, 0.0, 53.0, 2.5, 5.0);
        this.add(236.0, -45.0, 30.0, 2.5, 5.0);
        this.add(265.0, -55.0, 6.0, 2.5, 5.0);
        this.add(286.0, -49.0, -37.0, 2.5, 5.0);
        this.add(297.0, -32.0, -91.0, 2.5, 5.0);
        this.add(287.0, -9.0, -140.0, 2.5, 5.0);
        this.add(267.0, -3.0, -180.0, 2.5, 5.0);
        this.add(43.0, 0.0, -180.0, 2.5, 5.0);
        

        this.finish();
    }
}

/*

86, 5, 0.0
128, -8, 12
138, -16, 59
142, -33, 90
140, -41, 117
130, -49, 150
119, -52, 180
111, -50, 208
95, -30, 270
97, -20, 292
108, -5, 331
129, 0, 360
202, 5, 360
228, 11, 321
232, 19, 297
237, 32, 271
231, 45, 229
218, 52, 192
202, 53, 156
191, 46, 124
184, 34, 80
196, 0, 53
236, -39, 30
260, -52, 6
286, -49, -37
297, -32, -91
287, -9, -140
267, -3, -180
43, 0, 0

*/