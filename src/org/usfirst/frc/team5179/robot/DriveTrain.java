package org.usfirst.frc.team5179.robot;


import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("unused")
public class DriveTrain {
	
	//Variables
	private final SpeedController leftController = RobotMap.driveTrainLeftController;
    private final SpeedController rightController = RobotMap.driveTrainRightController;
    private final static RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;
    private final Encoder encoderLeft = RobotMap.driveTrainEncoderLeft;
    private final Encoder encoderRight = RobotMap.driveTrainEncoderRight;
    private final AnalogGyro analogGyro = RobotMap.driveTrainAnalogGyro;
    
    public static double LE = 0;
    public static double RE = 0;
    public static double currentLeftDistance = 0;
    public static double currentRightDistance = 0;
    public static double PPR = 1440;
    public static double ratio = 12.75;
    public static double ratioToCaterpillar = (21/9);
    public static double radius = 2.375; 
	
	public static double desiredLeftDistance;
	public static double desiredRightDistance;
	private static boolean initiated = false;
	
	private static double initialLeftDistance;
	private static double initialRightDistance;
	public static double missingLeftDistance;
	private static double leftSpeed;
	private static double rightSpeed;
	public static double missingRightDistance;
	
	public static boolean LEAtDistance;
	public static boolean REAtDistance;
	
	public static int leftState = 0;
	public static int rightState = 0;
	
    public static void updateEncoders(){
    	LE = RobotMap.driveTrainEncoderLeft.getDistance();
    	RE = RobotMap.driveTrainEncoderRight.getDistance();
    }
	
    public static void driveDuringDistance(double tempLeftDistance, double tempRightDistance){
    	initiated = false;
    	
    	initialLeftDistance = RobotMap.driveTrainEncoderLeft.getDistance();
    	SmartDashboard.putNumber("Initial left distance", initialLeftDistance);
    	initialRightDistance = RobotMap.driveTrainEncoderRight.getDistance();
    	SmartDashboard.putNumber("Initial right distance", initialRightDistance);
    	
    	desiredLeftDistance = tempLeftDistance;
    	desiredRightDistance = tempRightDistance;
    	
    	initiated = true;
    }
    
    public static void distanceCalculateSpeed(){
    	
    	currentLeftDistance = LE - initialLeftDistance;
    	SmartDashboard.putNumber("Current left distance", currentLeftDistance);
    	missingLeftDistance = desiredLeftDistance - currentLeftDistance;
    	SmartDashboard.putNumber("Missing left distance", missingLeftDistance);
    	
    	currentRightDistance = RE - initialRightDistance;
    	SmartDashboard.putNumber("Current right distance", currentRightDistance);
    	missingRightDistance = desiredRightDistance - currentRightDistance;
    	SmartDashboard.putNumber("Missing right distance", missingRightDistance);
    	
		switch(leftState  ){
    	case 0:
    		leftSpeed = 0;
    		if (LEAtDistance == true){
    			leftState = 0;
    		}else if(initiated){
    			leftState = 1;
    		}else{
    			leftState = 0;
    		}
    	case 1:
    		leftSpeed = 0.75;
    		if(!initiated){
    			leftState = 0;
    		}else if (missingLeftDistance < 2){
        		leftState = 0;
        		LEAtDistance = true;
        	}else{
        		leftState = 1;
        	}
    		break;
    	}
		
		switch(rightState  ){
    	case 0:
    		if (REAtDistance == true){
    			rightState = 0;
    		}else if(initiated){
    			rightState = 1;
    		}else{
    			rightState = 0;
    		}
    	case 1:
    		if(!initiated){
    			rightState = 0;
    		}else if (LEAtDistance == true){
    			rightState = 0;
    		}else{
    			rightState = 1;
    		}
        	if (Math.abs(missingRightDistance) < 2){
        		rightSpeed = 0;
        		REAtDistance = true;
        	}else if(Math.abs(missingRightDistance) <= 10){
            	rightSpeed = missingRightDistance*0.1;
        	}else if (missingRightDistance <= -10){
        		rightSpeed = -1;
        	}else if (missingRightDistance > 10){
        		rightSpeed = 1;
        	}else{
        		rightState = 0;
        	}
    		break;
		}
    	
    }
    
    public static void distanceDrive(){
    	tankDrive(leftSpeed, rightSpeed);
    }
    
    public static void turnDegrees(double degrees){ // Turn by x degrees. (x degrees is right, -x is left)
    	int steeringRadius = 0;
    	double inchesPerDegree = ((2*Math.PI*steeringRadius)/180);
    	double inches = inchesPerDegree*degrees;
//   	driveDuringDistance(inches, -inches);    	
    }

    public static void arcadeDrive(Joystick stick){
    	robotDrive.arcadeDrive(stick);
    }
    public static void tankDrive(double left, double right){
    	robotDrive.tankDrive(left, right);
    }
    
}