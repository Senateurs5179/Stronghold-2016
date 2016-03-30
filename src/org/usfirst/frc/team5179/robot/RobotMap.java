package org.usfirst.frc.team5179.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RobotMap {
    public static SpeedController driveTrainLeftController;
    public static SpeedController driveTrainRightController;
    public static RobotDrive driveTrainRobotDrive;
    public static Encoder driveTrainEncoderLeft;
    public static Encoder driveTrainEncoderRight;
    public static AnalogGyro driveTrainAnalogGyro;
    public static Servo cameraCameraServo;
    public static SpeedController ballControlBallMotor;
    public static DigitalInput ballControlBallLimitSwitch;
    public static DoubleSolenoid armFirstSolenoid;
    public static DoubleSolenoid armSecondSolenoid;
    public static DoubleSolenoid armThirdSolenoid;
    
    public static DigitalInput lowBarSwitch;
    public static DigitalInput moatSwitch;
    public static DigitalInput rampartsSwitch;
    public static DigitalInput rockWallSwitch;
    public static DigitalInput roughTerrainSwitch;
    
	public static Compressor armCompressor;
	
	
    
    public static void init() {
    	if (Robot.isReal()){
            driveTrainLeftController = new VictorSP(0);
            LiveWindow.addActuator("DriveTrain", "LeftController", (VictorSP) driveTrainLeftController);
            
            driveTrainRightController = new VictorSP(1);
            LiveWindow.addActuator("DriveTrain", "RightController", (VictorSP) driveTrainRightController);
            
            driveTrainRobotDrive = new RobotDrive(driveTrainLeftController, driveTrainRightController);
            
            driveTrainRobotDrive.setSafetyEnabled(true);
            driveTrainRobotDrive.setExpiration(0.1);
            driveTrainRobotDrive.setSensitivity(0.5);
            driveTrainRobotDrive.setMaxOutput(1.0);
            driveTrainLeftController.setInverted(true);
            driveTrainRightController.setInverted(true);
            
            driveTrainEncoderLeft = new Encoder(0, 1, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "EncoderLeft", driveTrainEncoderLeft);
            driveTrainEncoderLeft.setDistancePerPulse(((2*Math.PI*DriveTrain.radius)*(DriveTrain.ratioToCaterpillar))/DriveTrain.PPR);
            driveTrainEncoderLeft.setPIDSourceType(PIDSourceType.kRate);
            driveTrainEncoderRight = new Encoder(2, 3, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "EncoderRight", driveTrainEncoderRight);
            driveTrainEncoderRight.setDistancePerPulse(((2*Math.PI*DriveTrain.radius)*(DriveTrain.ratioToCaterpillar))/DriveTrain.PPR);
            driveTrainEncoderRight.setPIDSourceType(PIDSourceType.kRate);
            cameraCameraServo = new Servo(2);
            LiveWindow.addActuator("Camera", "CameraServo", cameraCameraServo);
            
            ballControlBallMotor = new Talon(3);
            LiveWindow.addActuator("BallControl", "BallMotor", (Talon) ballControlBallMotor);
            
            ballControlBallLimitSwitch = new DigitalInput(4);
            LiveWindow.addSensor("BallControl", "BallLimitSwitch", ballControlBallLimitSwitch);
            
            lowBarSwitch = new DigitalInput(5);
            LiveWindow.addSensor("DriveTrain", "LowBarSwitch", lowBarSwitch);
            
            moatSwitch = new DigitalInput(6);
            LiveWindow.addSensor("DriveTrain", "MoatSwitch", moatSwitch);
            
            rampartsSwitch = new DigitalInput(7);
            LiveWindow.addSensor("DriveTrain", "RampartsSwitch", rampartsSwitch);
            
            rockWallSwitch = new DigitalInput(8);
            LiveWindow.addSensor("DriveTrain", "RockWallSwitch", rockWallSwitch);
            
            roughTerrainSwitch = new DigitalInput(9);
            LiveWindow.addSensor("DriveTrain", "RoughTerrainSwitch", roughTerrainSwitch);
            
            armCompressor = new Compressor(0);
            armCompressor.setClosedLoopControl(true);
            
            armFirstSolenoid = new DoubleSolenoid(0, 0, 1);
            LiveWindow.addActuator("Arm", "FirstSolenoid", armFirstSolenoid);
            
            armSecondSolenoid = new DoubleSolenoid(0, 2, 3);
            LiveWindow.addActuator("Arm", "SecondSolenoid", armSecondSolenoid);
            
            armThirdSolenoid = new DoubleSolenoid(0, 4, 5);
            LiveWindow.addActuator("Arm", "ThirdSolenoid", armThirdSolenoid);	
    	}else{
    		
    		/*
    		 * Different material as a simulator can't support VictorSP
    		 */
    		
            driveTrainLeftController = new Victor(0);
            LiveWindow.addActuator("DriveTrain", "LeftController", (Victor) driveTrainLeftController);
            
            driveTrainRightController = new Victor(1);
            LiveWindow.addActuator("DriveTrain", "RightController", (Victor) driveTrainRightController);
            
            driveTrainRobotDrive = new RobotDrive(driveTrainLeftController, driveTrainRightController);
            
            driveTrainRobotDrive.setSafetyEnabled(true);
            driveTrainRobotDrive.setExpiration(0.1);
            driveTrainRobotDrive.setSensitivity(0.5);
            driveTrainRobotDrive.setMaxOutput(1.0);
            //driveTrainLeftController.setInverted(true); --> inversion unsupported in simulation
            //driveTrainRightController.setInverted(true);
            
            driveTrainEncoderLeft = new Encoder(0, 1, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "EncoderLeft", driveTrainEncoderLeft);
            driveTrainEncoderLeft.setDistancePerPulse(((2*Math.PI*DriveTrain.radius)*(DriveTrain.ratioToCaterpillar))/DriveTrain.PPR);
            driveTrainEncoderLeft.setPIDSourceType(PIDSourceType.kRate);
            driveTrainEncoderRight = new Encoder(2, 3, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "EncoderRight", driveTrainEncoderRight);
            driveTrainEncoderRight.setDistancePerPulse(((2*Math.PI*DriveTrain.radius)*(DriveTrain.ratioToCaterpillar))/DriveTrain.PPR);
            driveTrainEncoderRight.setPIDSourceType(PIDSourceType.kRate);
            
            ballControlBallMotor = new Talon(3);
            LiveWindow.addActuator("BallControl", "BallMotor", (Talon) ballControlBallMotor);
            
            ballControlBallLimitSwitch = new DigitalInput(4);
            LiveWindow.addSensor("BallControl", "BallLimitSwitch", ballControlBallLimitSwitch);
            
            lowBarSwitch = new DigitalInput(5);
            LiveWindow.addSensor("DriveTrain", "LowBarSwitch", lowBarSwitch);
            
            moatSwitch = new DigitalInput(6);
            LiveWindow.addSensor("DriveTrain", "MoatSwitch", moatSwitch);
            
            rampartsSwitch = new DigitalInput(7);
            LiveWindow.addSensor("DriveTrain", "RampartsSwitch", rampartsSwitch);
            
            rockWallSwitch = new DigitalInput(8);
            LiveWindow.addSensor("DriveTrain", "RockWallSwitch", rockWallSwitch);
            
            roughTerrainSwitch = new DigitalInput(9);
            LiveWindow.addSensor("DriveTrain", "RoughTerrainSwitch", roughTerrainSwitch);
            
            armFirstSolenoid = new DoubleSolenoid(0, 0, 1);
            LiveWindow.addActuator("Arm", "FirstSolenoid", armFirstSolenoid);
            
            armSecondSolenoid = new DoubleSolenoid(0, 2, 3);
            LiveWindow.addActuator("Arm", "SecondSolenoid", armSecondSolenoid);
            
            armThirdSolenoid = new DoubleSolenoid(0, 4, 5);
            LiveWindow.addActuator("Arm", "ThirdSolenoid", armThirdSolenoid);	
    	}
    }
}
