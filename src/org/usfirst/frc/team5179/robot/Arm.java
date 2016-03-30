package org.usfirst.frc.team5179.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Arm {
	
	public static boolean closeSolenoidButton = false;
	public static boolean firstSolenoidUpButton = false;
	public static boolean firstSolenoidDownButton = false;
	public static boolean secondSolenoidUpButton = false;
	public static boolean secondSolenoidDownButton = false;
	public static boolean thirdSolenoidUpButton = false;
	public static boolean thirdSolenoidDownButton = false;
	
	public static Value armFirstSolenoidValue = DoubleSolenoid.Value.kOff;
	public static Value armSecondSolenoidValue = DoubleSolenoid.Value.kOff;
	public static Value armThirdSolenoidValue = DoubleSolenoid.Value.kOff;

	public static int manualState = 0;
	private static long startTime;

	public static void updateButtons(){
		closeSolenoidButton = OI.closeSolenoidButton.get();
		firstSolenoidUpButton = OI.firstSolenoidUpButton.get();
		firstSolenoidDownButton = OI.firstSolenoidDownButton.get();
		secondSolenoidUpButton = OI.secondSolenoidUpButton.get();
		secondSolenoidDownButton = OI.secondSolenoidDownButton.get();
		thirdSolenoidUpButton = OI.thirdSolenoidUpButton.get();
		thirdSolenoidDownButton = OI.thirdSolenoidDownButton.get();
	}
	public static void updateSolenoids(){
		RobotMap.armFirstSolenoid.set(armFirstSolenoidValue);
		RobotMap.armSecondSolenoid.set(armSecondSolenoidValue);
		RobotMap.armThirdSolenoid.set(armThirdSolenoidValue);
	}	

	public static void manualFSM() {
		switch (manualState) {
		case 0:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kOff;
			armSecondSolenoidValue = DoubleSolenoid.Value.kOff;
			armThirdSolenoidValue = DoubleSolenoid.Value.kOff;
			if (closeSolenoidButton){
				manualState = 0;
			}else if (secondSolenoidUpButton){	
				manualState = 1;
			}else{
				manualState = 0;
			}
    		break;
    		
		case 1:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kOff;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kOff;
    		startTime = System.currentTimeMillis();
    		if (closeSolenoidButton){
    			manualState = 12;
    		}else{
    			manualState = 2;
    		}
    		break;
    		
		case 2:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kOff;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kOff;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (System.currentTimeMillis() - startTime > 500){
				manualState = 3;
			}else{
				manualState = 2;
			}
			break;
    		
		case 3:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kOff;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kOff;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (firstSolenoidUpButton){
				manualState = 4;				
			}else if (firstSolenoidDownButton){
				manualState = 5;
			}else if (thirdSolenoidDownButton){
				manualState = 7;
			}else if (firstSolenoidUpButton && thirdSolenoidUpButton){
				manualState = 8;
			}else if (firstSolenoidUpButton && thirdSolenoidDownButton){
				manualState = 9;
			}/*else if (firstSolenoidDownButton && thirdSolenoidUpButton){
				manualState = 10;
			}*/else if (firstSolenoidDownButton && thirdSolenoidDownButton){
				manualState = 11;
			}else{
				manualState = 3;
			}
    		break;
    		
		case 4:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kForward;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kOff;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (firstSolenoidUpButton){
				manualState = 4;
			}else if (thirdSolenoidUpButton){
				manualState = 6;
			}else if (thirdSolenoidDownButton){
				manualState = 7;
			}else if (firstSolenoidUpButton && thirdSolenoidUpButton){
				manualState = 8;
			}else if (firstSolenoidUpButton && thirdSolenoidDownButton){
				manualState = 9;
			}/*else if (firstSolenoidDownButton && thirdSolenoidUpButton){
				manualState = 10;
			}*/else if (firstSolenoidDownButton && thirdSolenoidDownButton){
				manualState = 11;
			}else{
				manualState = 4;
			}
			break;
    		
		case 5:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kReverse;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kOff;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (firstSolenoidUpButton){
				manualState = 4;				
			}else if (firstSolenoidUpButton){
				manualState = 5;				
			}else if (thirdSolenoidDownButton){
				manualState = 7;
			}else if (firstSolenoidUpButton && thirdSolenoidUpButton){
				manualState = 8;
			}else if (firstSolenoidUpButton && thirdSolenoidDownButton){
				manualState = 9;
			}/*else if (firstSolenoidDownButton && thirdSolenoidUpButton){
				manualState = 10;
			}*/else if (firstSolenoidDownButton && thirdSolenoidDownButton){
				manualState = 11;
			}else{
				manualState = 5;
			}
    		break;
    		
		case 6:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kOff;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kForward;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (firstSolenoidUpButton){
				manualState = 4;				
			}else if (thirdSolenoidUpButton){
				manualState = 6;
			}else if (thirdSolenoidDownButton){
				manualState = 7;
			}else if (firstSolenoidUpButton && thirdSolenoidUpButton){
				manualState = 8;
			}else if (firstSolenoidUpButton && thirdSolenoidDownButton){
				manualState = 9;
			}/*else if (firstSolenoidDownButton && thirdSolenoidUpButton){
				manualState = 10;
			}*/else if (firstSolenoidDownButton && thirdSolenoidDownButton){
				manualState = 11;
			}else{
				manualState = 6;
			}
			break;
    		
		case 7:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kOff;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kReverse;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (firstSolenoidUpButton){
				manualState = 4;				
			}/*else if (firstSolenoidDownButton){
				manualState = 5;
			}*/else if (thirdSolenoidUpButton){
				manualState = 6;
			}else if (thirdSolenoidDownButton){
				manualState = 7;
			}else if (firstSolenoidUpButton && thirdSolenoidUpButton){
				manualState = 8;
			}else if (firstSolenoidUpButton && thirdSolenoidDownButton){
				manualState = 9;
			}/*else if (firstSolenoidDownButton && thirdSolenoidUpButton){
				manualState = 10;
			}*/else if (firstSolenoidDownButton && thirdSolenoidDownButton){
				manualState = 11;
			}else{
				manualState = 7;
			}
    		break;
    		
		case 8:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kForward;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kForward;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (firstSolenoidUpButton){
				manualState = 4;				
			}else if (thirdSolenoidUpButton){
				manualState = 6;
			}else if (thirdSolenoidDownButton){
				manualState = 7;
			}else if (firstSolenoidUpButton && thirdSolenoidUpButton){
				manualState = 8;
			}else if (firstSolenoidUpButton && thirdSolenoidDownButton){
				manualState = 9;
			}/*else if (firstSolenoidDownButton && thirdSolenoidUpButton){
				manualState = 10;
			}*/else if (firstSolenoidDownButton && thirdSolenoidDownButton){
				manualState = 11;
			}else{
				manualState = 8;
			}	
			break;
    		
		case 9:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kForward;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kReverse;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (firstSolenoidUpButton){
				manualState = 4;				
			}else if (firstSolenoidDownButton){
				manualState = 5;
			}else if (thirdSolenoidUpButton){
				manualState = 6;
			}else if (thirdSolenoidDownButton){
				manualState = 7;
			}else if (firstSolenoidUpButton && thirdSolenoidUpButton){
				manualState = 8;
			}else if (firstSolenoidUpButton && thirdSolenoidDownButton){
				manualState = 9;
			}/*else if (firstSolenoidDownButton && thirdSolenoidUpButton){
				manualState = 10;
			}*/else if (firstSolenoidDownButton && thirdSolenoidDownButton){
				manualState = 11;
			}else{
				manualState = 3;
			}
    		break;
    		
/*		case 10:
			
			armFirstSolenoidValue = DoubleSolenoid.Value.kReverse;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kForward;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (firstSolenoidUpButton){
				manualState = 4;				
			}else if (firstSolenoidDownButton){
				manualState = 5;
			}else if (thirdSolenoidUpButton){
				manualState = 6;
			}else if (thirdSolenoidDownButton){
				manualState = 7;
			}else if (firstSolenoidUpButton && thirdSolenoidUpButton){
				manualState = 8;
			}else if (firstSolenoidUpButton && thirdSolenoidDownButton){
				manualState = 9;
			}else if (firstSolenoidDownButton && thirdSolenoidUpButton){
				manualState = 11;
			}else if (firstSolenoidDownButton && thirdSolenoidDownButton){
				manualState = 11;
			}else{
				manualState = 10;
			}
			break;
*/    		
		case 11:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kReverse;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kReverse;
			if (closeSolenoidButton){
				manualState = 12;
			}else if (firstSolenoidUpButton){
				manualState = 4;				
			}else if (firstSolenoidDownButton){
				manualState = 5;
			}else if (thirdSolenoidDownButton){
				manualState = 7;
			}else if (firstSolenoidUpButton && thirdSolenoidUpButton){
				manualState = 8;
			}else if (firstSolenoidUpButton && thirdSolenoidDownButton){
				manualState = 9;
			}/*else if (firstSolenoidDownButton && thirdSolenoidUpButton){
				manualState = 10;
			}*/else if (firstSolenoidDownButton && thirdSolenoidDownButton){
				manualState = 11;
			}else{
				manualState = 11;
			}
    		break;
    		// Close all solenoids
		case 12:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kReverse;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kReverse;
    		startTime = System.currentTimeMillis();
			manualState = 13;
			break;
		case 13:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kReverse;
			armSecondSolenoidValue = DoubleSolenoid.Value.kForward;
			armThirdSolenoidValue = DoubleSolenoid.Value.kReverse;
			
			if(System.currentTimeMillis() - startTime > 2000){
				manualState = 14;
			}else{
				manualState = 13;
			}
			break;
		case 14:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kReverse;
			armSecondSolenoidValue = DoubleSolenoid.Value.kReverse;
			armThirdSolenoidValue = DoubleSolenoid.Value.kReverse;
    		startTime = System.currentTimeMillis();
			manualState = 15;
			break;
		case 15:
						
			armFirstSolenoidValue = DoubleSolenoid.Value.kReverse;
			armSecondSolenoidValue = DoubleSolenoid.Value.kReverse;
			armThirdSolenoidValue = DoubleSolenoid.Value.kReverse;
			if(System.currentTimeMillis() - startTime > 1000){
				manualState = 0;
			}else{
				manualState = 15;
			}
			break;
		}
	}
}