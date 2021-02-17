package frc.robot;

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
    
    public Vision() {
        // Set variable values from Constants
        this.cameraAngle = Constants.ObjVision.cameraAngle;
        this.cameraHeight = Constants.ObjVision.cameraHeight;
        this.ballHeight = Constants.ObjVision.ballHeight;
        this.cameraFieldOfView = Constants.ObjVision.cameraFieldOfView;
        this.cameraResolutionY = Constants.ObjVision.cameraResolutionY;
        this.cameraResolutionX = Constants.ObjVision.cameraResolutionX;
    }

    public double calculateDistance(String[] xyxy, double height, double width) {
        // Calculate ty value
        ty = ((cameraResolutionY / 2) - (Double.parseDouble(xyxy[3]) + (-height / 2))) * (cameraFieldOfView / cameraResolutionY); //! If error in math try Integer.parseInt instead of double...

        // Calculate Math using: (ballHeight-cameraHeight) / (math.tan(math.radians(cameraAngle+ty)))
        distance = (ballHeight - cameraHeight) / (Math.tan(Math.toRadians(cameraAngle + ty)));

        // Return Val
        return distance;
    }
}
