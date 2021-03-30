package frc.robot;

import edu.wpi.first.wpilibj.util.Units;
import frc.lib.Target;

/**
 * The vision controller for the robot.
 */
public class Vision {
    private Target m_target = new Target();

    /**
     * Finds the shortest distance of vision targets.

     * @param xyxy The xyxy array given from Vision.
     */
    public void shortestDistance(String[][] xyxy) {
        Target shortestDistance = new Target();

        for (int i = 0; i < xyxy.length; i++) {
            Target calculatedData = calculateData(xyxy[i]).setOffset(-Constants.ObjVision.kCameraInset);
            if (!shortestDistance.isSet() || shortestDistance.getDistance() > calculatedData.getDistance() && calculatedData.getDistance() > Units.inchesToMeters(27.0)) {
                shortestDistance = calculatedData;
            }
        }

        this.m_target = shortestDistance;
    }

    /**
     * Calculates the data as passed from Vision through DANK dashboard.

     * @param xyxy The xyxy array passed from vision
     * @return Returns the closest Target to the robot
     */
    public Target calculateData(String[] xyxy) {
        // Calculate height / width | xyxy is in pixels / rep lines around pc | xyxy[0]
        // = left line; xyxy[1] = top line; xyxy[2] = right line; xyxy[3] = bottom line;
        double width = Double.parseDouble(xyxy[2]) - Double.parseDouble(xyxy[0]);
        double height = Double.parseDouble(xyxy[3]) - Double.parseDouble(xyxy[1]);

        // Calculate ty / tx value | tx / ty in degrees (after conversion)
        double ty = ((Constants.ObjVision.kCameraResolutionY / 2.0) - (Double.parseDouble(xyxy[3]) + (-height / 2.0)))
                * (Constants.ObjVision.kCameraFieldOfView / Constants.ObjVision.kCameraResolutionY);
        double tx = ((Constants.ObjVision.kCameraResolutionX / 2.0) - (Double.parseDouble(xyxy[2]) + (-width / 2.0)))
                * (Constants.ObjVision.kCameraFieldOfView / Constants.ObjVision.kCameraResolutionX);

        // Calculate distance using: (ballHeight-cameraHeight) /
        // (math.tan(math.radians(cameraAngle+ty))) | distance represents line b in law
        // of sines
        // Values shown here: http://share.brycecary.dev/1EF2 with td = distance

        double distance = (Constants.ObjVision.kCameraHeight - Constants.ObjVision.kBallHeight) * (1 / Math.tan(Math.toRadians(Constants.ObjVision.kCameraAngle + ty)));

        Robot.logger.addInfo("Vision Data", Double.toString(distance) + " || " + Double.toString(ty) + " || " + Double.toString(Double.parseDouble(xyxy[3])));
        
        // Return new Target Class
        return new Target(distance, ty, tx);
    }

    /**
     * Convert a JSON string of data to an array.

     * @param s the string to convert
     */
    public void convertStringToArr(String s) {
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

        // Run parseData
        shortestDistance(matrix);
    }

    public Target getPowerCellTarget() {
        return this.m_target;
    }
}
