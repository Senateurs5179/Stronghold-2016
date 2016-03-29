package org.usfirst.frc.team5179.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Indicators extends Thread {
	
	public static boolean notNormal = false; // Something unusual is happening
	public static String unusualDetail; // State where unusual action is taking place 
	public static double batteryVoltage = 0;
	
	private static boolean flash;
	public static Thread indicators;
	private static boolean Stuck;
	private static boolean Slow;
	private static int unusualState;
	private static long unusualBegin;
	private static int i = 0;
	
	
	public void run(){
		try {
			flashUnusual();
			updateIndicators();
			detectStuck();
			Thread.sleep(50);
		} catch (InterruptedException e){
			SmartDashboard.putBoolean("Unusual action", true);
			SmartDashboard.putString("Details", "Indicator thread has crashed!");
		}
	}
	
	public static void detectStuck(){
		if (System.currentTimeMillis() - Robot.lastIterationTime > 1000){
			Stuck = true;
		}else if (System.currentTimeMillis() - Robot.lastIterationTime > 600){
			Slow = true;
		}
	}
	
	public static void flashUnusual(){
    		
		int flashState = 0;
		long startTime = 0;
		
		switch (flashState){
		case 0:
			notNormal = false;
			if (flash){
				flashState = 1;
			}else {
				flashState = 0;
			}
			break;
		case 1:
    		startTime = System.currentTimeMillis();
			notNormal = true;
			if (!flash){
				flashState = 0;
			}else{
				flashState = 2;
			}
			break;
		case 2:
			notNormal = true;
			if (!flash){
				flashState = 0;
			}else if (System.currentTimeMillis() - startTime > 500){
				flashState = 3;
			}else {
				flashState = 2;
			}
			break;
		case 3:
    		startTime = System.currentTimeMillis();
			notNormal = false;
			if (!flash){
				flashState = 0;
			}else{
				flashState = 4;
			}
			break;
		case 4:
			notNormal = false;
			if (!flash){
				flashState = 0;
			}else if (System.currentTimeMillis() - startTime > 500){
				flashState = 0;
			}else {
				flashState = 4;
			}
			break;
		}
	}
	
	public static void updateIndicators(){
		
		//Unusual action
		
		switch (unusualState){
		case 0:
			unusualBegin = System.currentTimeMillis();
			if (OI.cancelBall.get()){
				i++;
				unusualState = 1;
			}else if (OI.abortMacroButton.get()){
				i++;
				unusualState = 2;
			}else if (Stuck){
				i++;
				unusualState = 3;
			}else if (Slow){
				i++;
				unusualState = 4;
			}else{
				unusualState = 0;
			}
			break;
		case 1:
			notNormal = true;
			unusualDetail = "Cancel Ball pressed!";
			unusualState = 5;
			break;
		case 2:
			notNormal = true;
			unusualDetail = "Abort Macro pressed!";
			unusualState = 5;
			break;
		case 3:
			flash = true;
			unusualDetail = "Main thread is stuck!";
			unusualState = 5;
			break;
		case 4:
			notNormal = true;
			unusualDetail = "Execution of main thread is slow!";
			unusualState = 5;
			break;
		case 5:
			if (i >= 2){
				flash = true;
				unusualDetail = "Multiple errrors";
				unusualState = 5;
			}else if (System.currentTimeMillis() - unusualBegin > 7500){
				unusualState = 6;
			}
			break;
		case 6:
			i = 0;
			notNormal = false;
			flash = false;
			unusualDetail = "";
			unusualState = 0;
			break;
		}
		
		SmartDashboard.putBoolean("Unusual action", notNormal); // Indicate if something unusual is happening
		if (unusualDetail != null){
			SmartDashboard.putString("Details", "Something unusual is happening: "+unusualDetail);
		}else{
			SmartDashboard.putString("Details", "");
		}
		
    	batteryVoltage = DriverStation.getInstance().getBatteryVoltage();
    	SmartDashboard.putNumber("Battery Voltage", batteryVoltage);
    	
    	if (batteryVoltage < 10){
    		SmartDashboard.putBoolean("Low battery!", true);
    	}else if (batteryVoltage < 9.5){
    		SmartDashboard.putBoolean("Critical battery!", true);
    		flash = true;
    	}
    	
    	SmartDashboard.putBoolean("lowBarSwitch", RobotMap.lowBarSwitch.get());
    	SmartDashboard.putBoolean("moatSwitch", RobotMap.moatSwitch.get());
    	SmartDashboard.putBoolean("rampartsSwitch", RobotMap.rampartsSwitch.get());
    	SmartDashboard.putBoolean("rockWallSwitch", RobotMap.rockWallSwitch.get());
    	SmartDashboard.putBoolean("roughTerrainSwitch", RobotMap.roughTerrainSwitch.get());
    	 	
		
		if (Robot.isTestMode){ // If we are debugging states
			
			SmartDashboard.putNumber("Last iteration", Robot.lastIterationTime);
			
			/*
			 * Motors speeds and encoders values
			 */
			SmartDashboard.putNumber("Left motor", RobotMap.driveTrainLeftController.get());
			SmartDashboard.putNumber("Right motor", RobotMap.driveTrainRightController.get());
			
			SmartDashboard.putNumber("Left encoder count", RobotMap.driveTrainEncoderLeft.get());
			SmartDashboard.putNumber("Right encoder count", RobotMap.driveTrainEncoderRight.get());
			
			SmartDashboard.putNumber("Left encoder distance", DriveTrain.LE);
			SmartDashboard.putNumber("Right encoder distance", DriveTrain.RE);
			
			
			/*
			 * DriveTrain.driveDuringDistance
			 */
			
			SmartDashboard.putNumber("Left motor state", DriveTrain.leftState);
			SmartDashboard.putNumber("Right motor state", DriveTrain.rightState);
			
			SmartDashboard.putNumber("Current left distance", DriveTrain.currentLeftDistance);
			SmartDashboard.putNumber("Current right distance", DriveTrain.currentRightDistance);
			
			SmartDashboard.putNumber("Target left distance", DriveTrain.desiredLeftDistance);
			SmartDashboard.putNumber("Target right distance", DriveTrain.desiredRightDistance);

			SmartDashboard.putNumber("Delta left distance", DriveTrain.missingLeftDistance);
			SmartDashboard.putNumber("Delta right distance", DriveTrain.missingRightDistance);
			
			SmartDashboard.putBoolean("Left at target", DriveTrain.LEAtDistance);
			SmartDashboard.putBoolean("Right at target", DriveTrain.REAtDistance);
			
			/*
			 * BallControl.FSM
			 */
			SmartDashboard.putNumber("BallControl state", BallControl.state);
			
			/*
			 * Arm
			 */
			SmartDashboard.putNumber("Arm state", Arm.manualState);
			
			/*
			 * Sequences
			 */
			SmartDashboard.putNumber("Sequences state", Sequences.autoSelector);
		}
		
	}
	
}
