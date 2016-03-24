package org.usfirst.frc.team5179.robot;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;

public class BallControl {
    private final static SpeedController ballMotor = RobotMap.ballControlBallMotor;
    private final static DigitalInput ballLimitSwitch = RobotMap.ballControlBallLimitSwitch;

	public double grabSpeed = 0.5;
	public static boolean done = false;
	
    public static boolean cancelBallButton = false;
    public static boolean throwButton = false;
    public static boolean grabButton = false;
    public static double throttle = 0;
    public static boolean noButtons = false;
    public static boolean limitSwitch = false;
    public static double motor = 0;
	public static int state = 0;
	public static long startTime = 0;
    
	public static void updateButtons(){
		    cancelBallButton = OI.cancelBall.get();
		    throwButton = OI.throwButton.get();
		    grabButton = OI.grabButton.get();
		    throttle = OI.joystick.getThrottle();
		    noButtons = (!cancelBallButton && !throwButton && !grabButton);
		    limitSwitch = ballLimitSwitch.get();
	}
	public static void updateMotor(){
			ballMotor.set(motor);		
	}
	
    public static void FSM(){
		switch(state){
    	case 0: //waiting
    		motor = 0;
    		if (cancelBallButton){
    	     	state = 0;
    		}else if(grabButton){
    			state = 1;
    		}else if(throwButton){
    			state = 3;
    		}else{
    			state = 0;
    		}
    		break;

    	case 1: //grab
    		motor = 0.75;
    		 if(cancelBallButton){
    			state = 0;
    		}else if (throwButton){
    			state = 3;
    		}else if(!limitSwitch){
    			state = 2;
    		}else if(grabButton){
    			state = 1;
    		}else{
    			state = 1;
    		}
    		break;
    		
    	case 2: //throw
    		motor = -throttle;
    		if(cancelBallButton){
    			state = 0;
    		}else if(grabButton){
    			state = 1;
    		}else if (limitSwitch){
    			state = 1;
			}else if (throwButton){
    			state = 3;
    		}else{
    			state = 2;
    		}
    		break;
    		
    	case 3: //init timer
    		motor = -1;
    		startTime = System.currentTimeMillis(); 
    		state = 4;
    		break;
    		
    	case 4: //wait for timer
    		motor = -1;
    		if (cancelBallButton){
    			state = 0;
    		}else if(grabButton == true){
    			state = 1;
    		}else if(System.currentTimeMillis() - startTime > 10000){ 
    			state = 0;
    		}
    		break;
    	}
    }
}
