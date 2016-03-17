
package org.usfirst.frc.team5179.robot;


import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
	boolean moreThanOne = false;
	byte i;
	public long startTime = 0;
	public static boolean isAutonomous;
	public static long lastIterationTime;
	
	public static boolean isTestMode;

    public Robot() {
    	OI.updateOI();
    	RobotMap.init();
    }
    
    public void robotInit() {
        		
        CameraServer server;
        server = CameraServer.getInstance();
        server.setQuality(50);
        server.startAutomaticCapture("cam0");

//        DriveTrain.driveDuringDistance((10*12), (10*12));
        
    }

	/**
	 * This selects the right autonomous code to run depending on the switch's position. If the position isn't determined, it runs nothing to avoid problems.
	 */
    public void autonomous() {
    	
    	isAutonomous = true;
    	isTestMode = true;
    	
		Thread t = new Thread(new Indicators());
		t.start();
		
    	lastIterationTime = System.currentTimeMillis();
        
    	if (i < 2){
        	startTime  = System.currentTimeMillis();
        	i++;
    	}
    	
    	
/*     	
		while (System.currentTimeMillis() - startTime < 10000){
			DriveTrain.tankDrive(0.75, 0.75);
		}
*/		
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        while (isOperatorControl() && isEnabled()) {
        	
    		Thread t = new Thread(new Indicators());
    		t.start();
    		
        	lastIterationTime = System.currentTimeMillis();
        	
            Timer.delay(0.005);		// wait for a motor update interval
            
        	DriveTrain.isRightControlled = true;
        	
        	if (i > 2){
            	DriveTrain.driveDuringDistance(4, 4);
            	i++;
        	}
        	DriveTrain.updateDistance();

        	
        	DriveTrain.updateEncoders();
        	DriveTrain.arcadeDrive(OI.joystick);
        	        	
        	
            // Initialize sequences
        	      	
        	
                        
            // Initialize ball FSM
            BallControl.updateButtons();
            BallControl.FSM();
			BallControl.updateMotor();
			
			// Initialize arm (manual) FSMs
			Arm.updateButtons();
			Arm.manualFSM();
			Arm.updateSolenoids();
									
			}
    }

    /**
     * Runs during test mode
     */
    public void test() { // Samething as operator, except that indicators outputs states and motor speed
    	isTestMode = true;
    	operatorControl();
    }
}
