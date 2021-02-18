package frc.robot;

import frc.lib.Target;

public class Vision {
    private Target m_target = new Target();

    public void parseData(String[][] xyxy) {
        Target shortestDistance = new Target();

        for(int i = 0; i < xyxy.length; i++) {
            Target calculatedData = calculateData(xyxy[i]);
            
            if(!shortestDistance.isSet() || shortestDistance.getDistance() > calculatedData.getDistance()) {
                shortestDistance = calculatedData;
            }
        }

        this.m_target = shortestDistance;
    }

    public Target calculateData(String[] xyxy) {
        // Calculate height / width
        double width = Double.parseDouble(xyxy[2]) - Double.parseDouble(xyxy[0]);
        double height = Double.parseDouble(xyxy[3]) - Double.parseDouble(xyxy[1]);

        // Calculate ty / tx value
        double ty = ((Constants.ObjVision.kCameraResolutionY / 2) - ((Double.parseDouble(xyxy[3])) + (-height / 2))) * (Constants.ObjVision.kCameraFieldOfView / Constants.ObjVision.kCameraResolutionY);
        double tx = ((Constants.ObjVision.kCameraResolutionX / 2) - (Double.parseDouble(xyxy[2]) + (-width / 2))) * (Constants.ObjVision.kCameraFieldOfView / Constants.ObjVision.kCameraResolutionX);

        // Calculate Math using: (ballHeight-cameraHeight) / (math.tan(math.radians(cameraAngle+ty)))
        double distance = (Constants.ObjVision.kBallHeight - Constants.ObjVision.kCameraHeight) / (Math.tan(Math.toRadians(Constants.ObjVision.kCameraAngle + ty)));

        // Target Class
        return new Target(distance, ty, tx);
    }

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
        parseData(matrix);
    }

    public Target getPowerCellTarget() {
        return this.m_target;
    }
}
