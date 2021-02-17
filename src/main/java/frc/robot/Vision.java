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

    public void parseData(double[][] xyxy) {
        for(int i = 0; i < xyxy.length; i++) {
            calculateDistance(xyxy[i]);
        }
    }

    public void calculateDistance(double[] xyxy) {
        // Calculate height / width
        this.width = xyxy[2] - xyxy[0];
        this.height = xyxy[3] - xyxy[1];

        // Calculate ty value
         this.ty = ((cameraResolutionY / 2) - ((xyxy[3]) + (-height / 2))) * (cameraFieldOfView / cameraResolutionY); //! If error in math try Integer.parseInt instead of double...

        // Calculate Math using: (ballHeight-cameraHeight) / (math.tan(math.radians(cameraAngle+ty)))
        this.distance = (ballHeight - cameraHeight) / (Math.tan(Math.toRadians(cameraAngle + ty)));
    }
}
