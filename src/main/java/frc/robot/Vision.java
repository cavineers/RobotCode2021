package frc.robot;

import java.util.Arrays;

import frc.robot.Constants;
import frc.robot.DANK;

public class Vision {

    // Init Vars
    private double cameraHeight;
    private double cameraAngle;
    private double ballHeight;
    private double cameraFieldOfView;
    private double cameraResolutionY;
    private double cameraResolutionX;
    private double ty;
    private double distance;
    private double height;
    private double width;
    
    public Vision() {
        // Set variable values from Constants
        this.cameraAngle = Constants.ObjVision.cameraAngle;
        this.cameraHeight = Constants.ObjVision.cameraHeight;
        this.ballHeight = Constants.ObjVision.ballHeight;
        this.cameraFieldOfView = Constants.ObjVision.cameraFieldOfView;
        this.cameraResolutionY = Constants.ObjVision.cameraResolutionY;
        this.cameraResolutionX = Constants.ObjVision.cameraResolutionX;
    }

    public void parseData(String[][] xyxy) {
        for(int i = 0; i < xyxy.length; i++) {
            calculateDistance(xyxy[i]);
        }
    }

    public void calculateDistance(String[] xyxy) {
        // Calculate height / width
        this.width = Integer.parseInt(xyxy[2]) - Integer.parseInt(xyxy[0]);
        this.height = Integer.parseInt(xyxy[3]) - Integer.parseInt(xyxy[1]);

        // Calculate ty value
         this.ty = ((cameraResolutionY / 2) - ((Integer.parseInt(xyxy[3])) + (-height / 2))) * (cameraFieldOfView / cameraResolutionY);

        // Calculate Math using: (ballHeight-cameraHeight) / (math.tan(math.radians(cameraAngle+ty)))
        this.distance = (ballHeight - cameraHeight) / (Math.tan(Math.toRadians(cameraAngle + ty)));
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
}
