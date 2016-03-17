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
    
    public static double deltaLeft = 0;
    public static double deltaRight = 0;
    
	public static boolean isLeftControlled = false;
	public static boolean isRightControlled = false;
	
	public static boolean LEAtDistance = false;
	public static boolean REAtDistance = false;
	
	public static int leftState;
	public static int rightState;
	private static int leftIteration;
	private static int rightIteration;
	
	public static double leftMotor = 0;
	public static double rightMotor = 0;
    
    public static void updateEncoders(){
    	LE = RobotMap.driveTrainEncoderLeft.get();
    	RE = RobotMap.driveTrainEncoderRight.get();
    	currentLeftDistance = RobotMap.driveTrainEncoderLeft.getDistance();
    	currentRightDistance = RobotMap.driveTrainEncoderRight.getDistance();
    }
	
    public static void driveDuringDistance(double desiredLeftDistance, double desiredRightDistance){
    	double fast = 0.75;
    	double slow = 0.5;
    	
    	/*
    	 * Left motor
    	 */
    	
    	SmartDashboard.putNumber("rightState", rightState);
    	
    	deltaLeft = desiredLeftDistance - currentLeftDistance;
    	
    	switch (leftState){
    	case 0:
    		leftMotor = 0;
    		if (isLeftControlled){
    			leftState = 1;
    		}else {
    			leftState = 0;
    		}
    		break;
    	case 1:
    		leftMotor = 0;
    		if (!isLeftControlled){
        		leftState = 0;
    		}else if (deltaLeft <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft <= 5){ // if missing less than or 5 inches from target
    			leftState = 2;
    		}else if (deltaLeft > 5){ // if missing more than 5 inches from target
    			leftState = 3;
    		}else if (deltaLeft >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft >= -5){ // if less than or 5 inches away from target
    			leftState = 4;
    		}else if (deltaLeft < -5){ // if more than 5 inches away from target
    			leftState = 5;
    		}else {
    			Indicators.notNormal = true;
    			Indicators.unusualDetail = "driveDuringDistance "+" left " + leftState;
    			leftState = 0;
    		}
    		break;
    	/*
    	 * Begin forward cases
    	 */
    	case 2: // Missing less than or 5 inches from target
    		leftMotor = slow;
    		if (!isLeftControlled){
        		leftState = 0;
    		}else if (deltaLeft <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft <= 5){ // if missing less than or 5 inches from target
    			leftState = 2;
    		}else if (deltaLeft > 5){ // if missing more than 5 inches from target
    			leftState = 3;
    		}else if (deltaLeft >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft >= -5){ // if less than or 5 inches away from target
    			leftState = 4;
    		}else if (deltaLeft < -5){ // if more than 5 inches away from target
    			leftState = 5;
    		}else {
    			Indicators.notNormal = true;
    			Indicators.unusualDetail = "driveDuringDistance "+" left " + leftState;
    			leftState = 0;
    		}
    		break;
    	case 3: // Missing more than 5 inches from target
    		leftMotor = fast;
    		if (!isLeftControlled){
        		leftState = 0;
    		}else if (deltaLeft <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft <= 5){ // if missing less than or 5 inches from target
    			leftState = 2;
    		}else if (deltaLeft > 5){ // if missing more than 5 inches from target
    			leftState = 3;
    		}else if (deltaLeft >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft >= -5){ // if less than or 5 inches away from target
    			leftState = 4;
    		}else if (deltaLeft < -5){ // if more than 5 inches away from target
    			leftState = 5;
    		}else {
    			Indicators.notNormal = true;
    			Indicators.unusualDetail = "driveDuringDistance "+" left " + leftState;
    			leftState = 0;
    		}
    		break;
    	/*
         * Begin reverse cases
         */
    	case 4: // Over by 5 inches or less from target
    		leftMotor = -slow;
    		if (!isLeftControlled){
        		leftState = 0;
    		}else if (deltaLeft <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft <= 5){ // if missing less than or 5 inches from target
    			leftState = 2;
    		}else if (deltaLeft > 5){ // if missing more than 5 inches from target
    			leftState = 3;
    		}else if (deltaLeft >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft >= -5){ // if less than or 5 inches away from target
    			leftState = 4;
    		}else if (deltaLeft < -5){ // if more than 5 inches away from target
    			leftState = 5;
    		}else {
    			Indicators.notNormal = true;
    			Indicators.unusualDetail = "driveDuringDistance "+" left " + leftState;
    			leftState = 0;
    		}
    		break;
    	case 5:
    		leftMotor = -fast;
    		if (!isLeftControlled){
        		leftState = 0;
    		}else if (deltaLeft <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft <= 5){ // if missing less than or 5 inches from target
    			leftState = 2;
    		}else if (deltaLeft > 5){ // if missing more than 5 inches from target
    			leftState = 3;
    		}else if (deltaLeft >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			LEAtDistance = true;
    			leftState = 0;
    		}else if (deltaLeft >= -5){ // if less than or 5 inches away from target
    			leftState = 4;
    		}else if (deltaLeft < -5){ // if more than 5 inches away from target
    			leftState = 5;
    		}else {
    			Indicators.notNormal = true;
    			Indicators.unusualDetail = "driveDuringDistance "+" left " + leftState;
    			leftState = 0;
    		}    		
    		break;
    	}

    	/*
    	 * Right motor
    	 */
    	
    	deltaRight = desiredRightDistance - currentRightDistance;
    	
    	switch (rightState){
    	case 0:
    		rightMotor = 0;
    		if (isRightControlled){
    			rightState = 1;
    		}else {
    			rightState = 0;
    		}
    		break;
    	case 1:
    		rightMotor = 0;
			SmartDashboard.putString("At least entered", "good");

    		if (!isRightControlled){
        		rightState = 0;
    		}else if (deltaRight <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight <= 5){ // if missing less than or 5 inches from target
    			rightState = 2;
    		}else if (deltaRight > 5){ // if missing more than 5 inches from target
    			SmartDashboard.putString("Going to state 3", "good");
    			rightState = 3;
    		}else if (deltaRight >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight >= -5){ // if less than or 5 inches away from target
    			rightState = 4;
    		}else if (deltaRight < -5){ // if more than 5 inches away from target
    			rightState = 5;
    		}else {
    			Indicators.notNormal = true;
    			Indicators.unusualDetail = "driveDuringDistance "+" right " + rightState;
    			rightState = 0;
    		}
    		break;
    	/*
    	 * Begin forward cases
    	 */
    	case 2: // Missing less than or 5 inches from target
    		rightMotor = slow;
    		if (!isRightControlled){
        		rightState = 0;
    		}else if (deltaRight <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight <= 5){ // if missing less than or 5 inches from target
    			rightState = 2;
    		}else if (deltaRight > 5){ // if missing more than 5 inches from target
    			rightState = 3;
    		}else if (deltaRight >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight >= -5){ // if less than or 5 inches away from target
    			rightState = 4;
    		}else if (deltaRight < -5){ // if more than 5 inches away from target
    			rightState = 5;
    		}else {
    			Indicators.notNormal = true;
    			Indicators.unusualDetail = "driveDuringDistance "+" right " + rightState;
    			rightState = 0;
    		}
    		break;
    	case 3: // Missing more than 5 inches from target
    		rightMotor = fast;
    		SmartDashboard.putString("Case 3", "Fast");
    		if (!isRightControlled){
        		rightState = 0;
    		}else if (deltaRight <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight <= 5){ // if missing less than or 5 inches from target
    			rightState = 2;
    		}else if (deltaRight >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight >= -5){ // if less than or 5 inches away from target
    			rightState = 4;
    		}else if (deltaRight < -5){ // if more than 5 inches away from target
    			rightState = 5;
    		}else {
    			rightState = 3;
    		}
    		break;
    	/*
         * Begin reverse cases
         */
    	case 4: // Over by 5 inches or less from target
    		rightMotor = -slow;
    		if (!isRightControlled){
        		rightState = 0;
    		}else if (deltaRight <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight <= 5){ // if missing less than or 5 inches from target
    			rightState = 2;
    		}else if (deltaRight > 5){ // if missing more than 5 inches from target
    			rightState = 3;
    		}else if (deltaRight >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight >= -5){ // if less than or 5 inches away from target
    			rightState = 4;
    		}else if (deltaRight < -5){ // if more than 5 inches away from target
    			rightState = 5;
    		}else {
    			Indicators.notNormal = true;
    			Indicators.unusualDetail = "driveDuringDistance "+" right " + rightState;
    			rightState = 0;
    		}
    		break;
    	case 5:
    		rightMotor = -fast;
    		if (!isRightControlled){
        		rightState = 0;
    		}else if (deltaRight <= 2){ // (Begin forward) if missing less than or 2 inches from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight <= 5){ // if missing less than or 5 inches from target
    			rightState = 2;
    		}else if (deltaRight > 5){ // if missing more than 5 inches from target
    			rightState = 3;
    		}else if (deltaRight >= -2){ // (Begin reverse) if less than or 2 inches away from target
    			REAtDistance = true;
    			rightState = 0;
    		}else if (deltaRight >= -5){ // if less than or 5 inches away from target
    			rightState = 4;
    		}else if (deltaRight < -5){ // if more than 5 inches away from target
    			rightState = 5;
    		}else {
    			Indicators.notNormal = true;
    			Indicators.unusualDetail = "driveDuringDistance "+" right " + rightState;
    			rightState = 0;
    		}		
    		break;
    	}
    	
    	if (LEAtDistance){ // If left is at good distance, stop controlling it
    		isLeftControlled = false;
        	leftIteration = 0;
    	}
    	
    	if (REAtDistance){ // If right is at good distance, stop controlling it
    		isRightControlled = false;
        	rightIteration = 0;
    	}    	
    }
    
    public static void updateDistance(){
    	tankDrive(leftMotor, rightMotor);
    }
    
    public static void turnDegrees(double degrees){ // Turn by x degrees. (x degrees is right, -x is left)
    	int steeringRadius = 0;
    	double inchesPerDegree = ((2*Math.PI*steeringRadius)/180);
    	double inches = inchesPerDegree*degrees;
    	driveDuringDistance(inches, -inches);    	
    }

    public static void arcadeDrive(Joystick stick){
    	robotDrive.arcadeDrive(stick);
    }
    public static void tankDrive(double left, double right){
    	robotDrive.tankDrive(left, right);
    }
    
}