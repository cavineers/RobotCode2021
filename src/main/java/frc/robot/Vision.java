package frc.robot;

import frc.lib.Target;

/**
 * The vision controller for the robot.
 */
public class Vision {
    private Target m_target = new Target();

    /**
     * Calculates the data as passed from Vision through DANK.

     * @param xyxy The xyxy array passed from vision
     * @return Returns the closest Target to the robot
     */
    public Target createTarget(String[] xyxy) {
        // Calculate height / width | xyxy is in pixels / rep lines around pc | xyxy[0]
        // = left line; xyxy[1] = top line; xyxy[2] = right line; xyxy[3] = bottom line;
        double width = Double.parseDouble(xyxy[2]) - Double.parseDouble(xyxy[0]);
        double height = Double.parseDouble(xyxy[3]) - Double.parseDouble(xyxy[1]);

        // Calculate ty / tx value | tx / ty in degrees (after conversion)
        // double ty = ((Constants.ObjVision.kCameraResolutionY / 2.0) - Double.parseDouble(xyxy[3]) + (-height / 2.0))
        //         * (Constants.ObjVision.kCameraFieldOfView / Constants.ObjVision.kCameraResolutionY);
        double ty = ( (Constants.ObjVision.kCameraResolutionY / 2.0) - Double.parseDouble(xyxy[3]) + (height / 2.0))
                * (Constants.ObjVision.kCameraFieldOfViewY / Constants.ObjVision.kCameraResolutionY);
        double tx = ((Constants.ObjVision.kCameraResolutionX / 2.0) - Double.parseDouble(xyxy[2]) + (width / 2.0))
                * (Constants.ObjVision.kCameraFieldOfView / Constants.ObjVision.kCameraResolutionX);

        // Calculate distance using: (ballHeight-cameraHeight) /
        // (math.tan(math.radians(cameraAngle+ty))) | distance represents line b in law
        // of sines
        // Values shown here: http://share.brycecary.dev/1EF2 with td = distance

        // double distance = (Constants.ObjVision.kBallHeight - Constants.ObjVision.kCameraHeight) * (1 / Math.tan(Math.toRadians(Constants.ObjVision.kCameraAngle + ty))); // TODO find if negative or positive                       

        double distance = (1.87*((Constants.ObjVision.kCameraHeight - Constants.ObjVision.kBallHeight) / (Math.tan(Math.toRadians(ty)))));

        // Robot.logger.addInfo("Vision Data", Double.toString(distance) + "||" + Double.toString(ty));
        // Return new Target Class
        return new Target(distance, ty, tx).setOffset(-Constants.ObjVision.kCameraInset);
    }

    /**
     * Convert a JSON string of data to an array.

     * @param s the string to convert
     */
    public void ingest(String s) {
        // Split on this delimiter
        String[] rows = s.split("], \\[");
        for (int i = 0; i < rows.length; i++) {
            // Remove any beginning and ending braces and any white spaces
            rows[i] = rows[i].replace("[[", "").replace("]]", "").replaceAll(" ", "");
        }

        // Get the number of columns in a row
        int numberOfColumns = rows[0].split(",").length;

        // Setup matrix
        String[][] matrix = new String[rows.length][numberOfColumns];

        // Populate matrix
        for (int i = 0; i < rows.length; i++) {
            matrix[i] = rows[i].split(",");
        }

        Target closestTarget = new Target();

        for (int i = 0; i < matrix.length; i++) {
            Target newTarget = createTarget(matrix[i]).setOffset(-Constants.ObjVision.kCameraInset);
            if (!closestTarget.isSet() || closestTarget.getDistance() > newTarget.getDistance()) {
                closestTarget = newTarget;
            }
        }

        this.m_target = closestTarget;
    }

    /**
     * Get the closest PowerCell to target.

     * @return PowerCell Target class.
     */
    public Target getPowerCellTarget() {
        return this.m_target;
    }
}
