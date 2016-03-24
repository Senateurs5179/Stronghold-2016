package org.usfirst.frc.team5179.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;


public class Sequences {
	
	private static double finalLeftDistance = 0;
	private static double finalRightDistance = 0;
	private static int macro = 0;
	private static boolean macroStopButton = false;
	private static boolean activateSequenceButton = false;
	private static boolean crossPlatform = false;
	private static int crossPlatformState = 0;
	private static boolean isReady = false;
	private static double crossPlatformLeftDistance = 0;
	private static double crossPlatformRightDistance = 0;
	private static long crossPlatformStartTime;
	public static int autoSelector = 0;
	
	//Cheval
	private static boolean chevalButton = false;
	private static int chevalState = 0;
	private static double chevalLeftDistance = 0;
	private static double chevalRightDistance = 0;
	private static long chevalStartTime;
	
	//Moat	
	private static boolean moatButton = false;
	private static int moatState = 0;
	private static double moatLeftDistance = 0;
	private static double moatRightDistance = 0;

	//Rampart
	private static boolean rampartsButton = false;
	private static int rampartState = 0;
	private static double rampartLeftDistance = 0;
	private static double rampartRightDistance = 0;
	
	//Drawbridge
	private static boolean drawbridgeButton = false;
	private static int drawbridgeState = 0;
	private static double drawbridgeLeftDistance = 0;
	private static double drawbridgeRightDistance = 0;
	
	//Rock Wall
	private static boolean rockWallButton = false;
	private static int rockWallState = 0;
	private static double rockWallLeftDistance = 0;
	private static double rockWallRightDistance = 0;
	
	//Rough Terrain
	private static boolean roughTerrainButton = false;
	private static int roughTerrainState = 0;
	private static double roughTerrainLeftDistance = 0;
	private static double roughTerrainRightDistance = 0;
	
	//Portcullis
	private static boolean portcullisButton = false;
	private static int portcullisState = 0;
	
	//Low Bar
	private static boolean lowBarButton = false;
	private static int lowBarState = 0;
	private static int lowBarRightDistance = 0;
	private static int lowBarLeftDistance = 0;
	private static long lowBarStartTime = 0;
	
	//Turn around
	private static int turnAroundState = 0;
	private static int turnAroundLeftDistance = 0;
	private static int turnAroundRightDistance = 0;
	private static long turnAroundStartTime = 0;
	
	/*
	 * Solenoids
	 */
	
	//Cross platform
	private static Value crossPlatformFirstSolenoid;
	private static Value crossPlatformSecondSolenoid;
	private static Value crossPlatformThirdSolenoid;
	
	//Turn around
	private static Value turnAroundFirstSolenoid;
	private static Value turnAroundSecondSolenoid;
	private static Value turnAroundThirdSolenoid;
	
	//Low bar
	private static Value lowBarFirstSolenoid;
	private static Value lowBarSecondSolenoid;
	private static Value lowBarThirdSolenoid;
	
	//Cheval
	private static Value chevalFirstSolenoid;
	private static Value chevalSecondSolenoid;
	private static Value chevalThirdSolenoid;
	
	//Drawbridge
	private static Value drawbridgeFirstSolenoid;
	private static Value drawbridgeSecondSolenoid;
	private static Value drawbridgeThirdSolenoid;
	
	//Moat
	private static Value moatFirstSolenoid;
	private static Value moatSecondSolenoid;
	private static Value moatThirdSolenoid;
	
	//Portcullis
	private static Value portcullisFirstSolenoid;
	private static Value portcullisSecondSolenoid;
	private static Value portcullisThirdSolenoid;
	
	//Rampart
	private static Value rampartFirstSolenoid;
	private static Value rampartSecondSolenoid;
	private static Value rampartThirdSolenoid;
	
	//Rock wall
	private static Value rockWallFirstSolenoid;
	private static Value rockWallSecondSolenoid;
	private static Value rockWallThirdSolenoid;
	
	//Rough terrain
	private static Value roughTerrainFirstSolenoid;
	private static Value roughTerrainSecondSolenoid;
	private static Value roughTerrainThirdSolenoid;
	private static boolean abortMacroButton;
	

	public static void drive(){
//		DriveTrain.driveDuringDistance(leftDistance, rightDistance);
	}
	
	public static void updateButtons(){
		int SequencesMode = 0;
		switch(SequencesMode){
		case 0:
			if (abortMacroButton){ // Whenever abort is pressed, cancel macro and return to 0
				autoSelector = 0;
				SequencesMode = 0;
			}else if (Robot.isAutonomous){
				SequencesMode = 1;
			}else{ // If in Teleop / Test
				SequencesMode = 2;
			}
			break;
		case 1:
			if (abortMacroButton){
				autoSelector = 0;
				SequencesMode = 0;
			}else if (!Robot.isAutonomous){
				SequencesMode = 0;
			}else if (RobotMap.lowBarSwitch.get()){
	    		lowBarButton = true;
	    	}else if (RobotMap.moatSwitch.get()){
	    		moatButton = true;
	    	}else if (RobotMap.rampartsSwitch.get()){
	    		rampartsButton = true;
	    	}else if (RobotMap.rockWallSwitch.get()){
	    		rockWallButton = true;
	    	}else if (RobotMap.roughTerrainSwitch.get()){
	    		roughTerrainButton = true;
	    	}else{ // If no mode selected, do nothing...
	    		autoSelector = 0;
	    		SequencesMode = 0;
	    	}
			break;
		case 2:
			if (abortMacroButton){
				autoSelector = 0;
				SequencesMode = 0;
			}else if (Robot.isAutonomous){
				SequencesMode = 0;
			}else {
				abortMacroButton = OI.abortMacroButton.get();
				lowBarButton = OI.lowBarButton.get();
				moatButton = OI.moatButton.get();
				rampartsButton = OI.rampartsButton.get();
				rockWallButton = OI.lowBarButton.get();
				roughTerrainButton = OI.roughTerrainButton.get();
				chevalButton = OI.chevalButton.get();
				drawbridgeButton = OI.drawbridgeButton.get();
				portcullisButton = OI.lowBarButton.get();
			}
			break;
		}		
	}
	
	public static void selectState(){
		
		switch (autoSelector){
		case 0: // Do nothing as no macro is selected
			if (lowBarButton){
				autoSelector = 1;
			}else if (moatButton){
				autoSelector = 2;
			}else if (rampartsButton){
				autoSelector = 3;
			}else if (rockWallButton){
				autoSelector = 4;
			}else if (roughTerrainButton){
				autoSelector = 5;
			}else if (chevalButton){
				autoSelector = 6;
			}else if (drawbridgeButton){
				autoSelector = 7;
			}else if (portcullisButton){
				autoSelector = 8;
			}else{
				autoSelector = 0;
			}
			RobotMap.armFirstSolenoid.set(DoubleSolenoid.Value.kOff);
			RobotMap.armSecondSolenoid.set(DoubleSolenoid.Value.kOff);
			RobotMap.armThirdSolenoid.set(DoubleSolenoid.Value.kOff);
//			DriveTrain.driveDuringDistance(0, 0);
			break;
		case 1: // lowBar
			if (lowBarButton){
				autoSelector = 1;
			}else if (moatButton){
				autoSelector = 2;
			}else if (rampartsButton){
				autoSelector = 3;
			}else if (rockWallButton){
				autoSelector = 4;
			}else if (roughTerrainButton){
				autoSelector = 5;
			}else if (chevalButton){
				autoSelector = 6;
			}else if (drawbridgeButton){
				autoSelector = 7;
			}else if (portcullisButton){
				autoSelector = 8;
			}else{
				autoSelector = 0;
			}
			RobotMap.armFirstSolenoid.set(lowBarFirstSolenoid);
			RobotMap.armSecondSolenoid.set(lowBarSecondSolenoid);
			RobotMap.armThirdSolenoid.set(lowBarThirdSolenoid);
//			DriveTrain.driveDuringDistance(lowBarLeftDistance, lowBarRightDistance);
			break;
		case 2:// Moat
			if (lowBarButton){
				autoSelector = 1;
			}else if (moatButton){
				autoSelector = 2;
			}else if (rampartsButton){
				autoSelector = 3;
			}else if (rockWallButton){
				autoSelector = 4;
			}else if (roughTerrainButton){
				autoSelector = 5;
			}else if (chevalButton){
				autoSelector = 6;
			}else if (drawbridgeButton){
				autoSelector = 7;
			}else if (portcullisButton){
				autoSelector = 8;
			}else{
				autoSelector = 0;
			}
			RobotMap.armFirstSolenoid.set(moatFirstSolenoid);
			RobotMap.armSecondSolenoid.set(moatSecondSolenoid);
			RobotMap.armThirdSolenoid.set(moatThirdSolenoid);
//			DriveTrain.driveDuringDistance(0, 0);
			break;
		case 3:// Rampart
			if (lowBarButton){
				autoSelector = 1;
			}else if (moatButton){
				autoSelector = 2;
			}else if (rampartsButton){
				autoSelector = 3;
			}else if (rockWallButton){
				autoSelector = 4;
			}else if (roughTerrainButton){
				autoSelector = 5;
			}else if (chevalButton){
				autoSelector = 6;
			}else if (drawbridgeButton){
				autoSelector = 7;
			}else if (portcullisButton){
				autoSelector = 8;
			}else{
				autoSelector = 0;
			}
			RobotMap.armFirstSolenoid.set(rampartFirstSolenoid);
			RobotMap.armSecondSolenoid.set(rampartSecondSolenoid);
			RobotMap.armThirdSolenoid.set(rampartThirdSolenoid);
//			DriveTrain.driveDuringDistance(0, 0);
			break;
		case 4:// Rock wall
			if (lowBarButton){
				autoSelector = 1;
			}else if (moatButton){
				autoSelector = 2;
			}else if (rampartsButton){
				autoSelector  = 3;
			}else if (rockWallButton){
				autoSelector = 4;
			}else if (roughTerrainButton){
				autoSelector = 5;
			}else if (chevalButton){
				autoSelector = 6;
			}else if (drawbridgeButton){
				autoSelector = 7;
			}else if (portcullisButton){
				autoSelector = 8;
			}else{
				autoSelector = 0;
			}
			RobotMap.armFirstSolenoid.set(rockWallFirstSolenoid);
			RobotMap.armSecondSolenoid.set(rockWallSecondSolenoid);
			RobotMap.armThirdSolenoid.set(rockWallThirdSolenoid);
//			DriveTrain.driveDuringDistance(rockWallLeftDistance, rockWallRightDistance);
			break;
		case 5:// Rough terrain
			if (lowBarButton){
				autoSelector = 1;
			}else if (moatButton){
				autoSelector = 2;
			}else if (rampartsButton){
				autoSelector = 3;
			}else if (rockWallButton){
				autoSelector = 4;
			}else if (roughTerrainButton){
				autoSelector = 5;
			}else if (chevalButton){
				autoSelector = 6;
			}else if (drawbridgeButton){
				autoSelector = 7;
			}else if (portcullisButton){
				autoSelector = 8;
			}else{
				autoSelector = 0;
			}
			RobotMap.armFirstSolenoid.set(roughTerrainFirstSolenoid);
			RobotMap.armSecondSolenoid.set(roughTerrainSecondSolenoid);
			RobotMap.armThirdSolenoid.set(roughTerrainThirdSolenoid);
//			DriveTrain.driveDuringDistance(0, 0);
			break;
		case 6:// Cheval de frise
			if (lowBarButton){
				autoSelector = 1;
			}else if (moatButton){
				autoSelector = 2;
			}else if (rampartsButton){
				autoSelector = 3;
			}else if (rockWallButton){
				autoSelector = 4;
			}else if (roughTerrainButton){
				autoSelector = 5;
			}else if (chevalButton){
				autoSelector = 6;
			}else if (drawbridgeButton){
				autoSelector = 7;
			}else if (portcullisButton){
				autoSelector = 8;
			}else{
				autoSelector = 0;
			}
			RobotMap.armFirstSolenoid.set(chevalFirstSolenoid);
			RobotMap.armSecondSolenoid.set(chevalSecondSolenoid);
			RobotMap.armThirdSolenoid.set(chevalThirdSolenoid);
//			DriveTrain.driveDuringDistance(chevalLeftDistance, chevalRightDistance);
			break;
		case 7:// DrawBridge
			if (lowBarButton){
				autoSelector = 1;
			}else if (moatButton){
				autoSelector = 2;
			}else if (rampartsButton){
				autoSelector = 3;
			}else if (rockWallButton){
				autoSelector = 4;
			}else if (roughTerrainButton){
				autoSelector = 5;
			}else if (chevalButton){
				autoSelector = 6;
			}else if (drawbridgeButton){
				autoSelector = 7;
			}else if (portcullisButton){
				autoSelector = 8;
			}else{
				autoSelector = 0;
			}
			RobotMap.armFirstSolenoid.set(drawbridgeFirstSolenoid);
			RobotMap.armSecondSolenoid.set(drawbridgeSecondSolenoid);
			RobotMap.armThirdSolenoid.set(drawbridgeThirdSolenoid);
//			DriveTrain.driveDuringDistance(0, 0);
			break;
		case 8:// Portcullis
			if (lowBarButton){
				autoSelector = 1;
			}else if (moatButton){
				autoSelector = 2;
			}else if (rampartsButton){
				autoSelector = 3;
			}else if (rockWallButton){
				autoSelector = 4;
			}else if (roughTerrainButton){
				autoSelector = 5;
			}else if (chevalButton){
				autoSelector = 6;
			}else if (drawbridgeButton){
				autoSelector = 7;
			}else if (portcullisButton){
				autoSelector = 8;
			}else{
				autoSelector = 0;
			}
			RobotMap.armFirstSolenoid.set(portcullisFirstSolenoid);
			RobotMap.armSecondSolenoid.set(portcullisSecondSolenoid);
			RobotMap.armThirdSolenoid.set(portcullisThirdSolenoid);
//			DriveTrain.driveDuringDistance(0, 0);
			break;
		}
	}
	
	public static void skip(){
/*		// Turn left
			leftDistance = -10;
			rightDistance = 10;
		// Go straight for 8'4"3/4 (100.75 inches)
			leftDistance = 100.75;
			rightDistance = 100.75;
		// Turn left
			DriveTrain.LM = -10;
			DriveTrain.RM = 10;
*/	}
	
	public static void moveToNext(){
		// Turn left
/*			leftDistance = -10;
			rightDistance = 10;
		// Go straight for 4'2"3/8 (50.375 inches)
			leftDistance = 50.375;
			rightDistance = 50.375;
		// Turn left
			leftDistance = -10;
			rightDistance = 10;
*/	}
	
	public static void crossPlatform(){ // Cross platform forward
		switch (crossPlatformState ){
		
		case 0:
			crossPlatformLeftDistance = 0;
			crossPlatformRightDistance = 0;
			crossPlatformFirstSolenoid = DoubleSolenoid.Value.kOff;
			crossPlatformSecondSolenoid = DoubleSolenoid.Value.kOff;
			crossPlatformThirdSolenoid = DoubleSolenoid.Value.kOff;
			
			if (macroStopButton){
				crossPlatformState = 0;
				isReady = true;
			}else if(isReady){
				crossPlatformState = 1;
			}else{
				crossPlatformState = 0;
			}
			break;
		case 1:	// Go straight for 5'
			crossPlatformLeftDistance = 60;
			crossPlatformRightDistance = 60;
			crossPlatformFirstSolenoid = DoubleSolenoid.Value.kOff;
			crossPlatformSecondSolenoid = DoubleSolenoid.Value.kOff;
			crossPlatformThirdSolenoid = DoubleSolenoid.Value.kOff;

			if (macroStopButton){
				crossPlatformState = 2;
			}else if(DriveTrain.LEAtDistance && DriveTrain.REAtDistance){
				crossPlatformState = 2;
			}else {
				crossPlatformState = 1;
			}
			break;
		// Stop this sequence
		case 2:
			crossPlatformStartTime = System.currentTimeMillis();
			crossPlatformLeftDistance = 0;
			crossPlatformRightDistance = 0;
			crossPlatformFirstSolenoid = DoubleSolenoid.Value.kOff;
			crossPlatformSecondSolenoid = DoubleSolenoid.Value.kOff;
			crossPlatformThirdSolenoid = DoubleSolenoid.Value.kOff;
			
			if (macroStopButton){
				crossPlatformState = 2;
			}else{
				crossPlatformState = 3;
			}
			break;
		case 3:
			crossPlatformLeftDistance = 0;
			crossPlatformRightDistance = 0;
			crossPlatformFirstSolenoid = DoubleSolenoid.Value.kOff;
			crossPlatformSecondSolenoid = DoubleSolenoid.Value.kOff;
			crossPlatformThirdSolenoid = DoubleSolenoid.Value.kOff;
			
			if (macroStopButton){
				
			}else if(System.currentTimeMillis() - crossPlatformStartTime > 1800){
		    	crossPlatformState = 0;
		    }else{
		    	crossPlatformState = 3;
		    break;
		    }
		}
	}
	public static void turnAround(){ // Turn around (180 degrees)
		switch (turnAroundState){
		case 0:
			turnAroundLeftDistance = 0;
			turnAroundRightDistance = 0;
			turnAroundFirstSolenoid = DoubleSolenoid.Value.kOff;
			turnAroundSecondSolenoid = DoubleSolenoid.Value.kOff;
			turnAroundThirdSolenoid = DoubleSolenoid.Value.kOff;
			
			if (macroStopButton){
				turnAroundState = 0;
			}else if(isReady){
				turnAroundState = 1;
			}else{
				turnAroundState = 0;
			}
			break;
		case 1:	// Go straight for 5'
			turnAroundLeftDistance = -20;
			turnAroundRightDistance = 20;
			turnAroundFirstSolenoid = DoubleSolenoid.Value.kOff;
			turnAroundSecondSolenoid = DoubleSolenoid.Value.kOff;
			turnAroundThirdSolenoid = DoubleSolenoid.Value.kOff;

			if (macroStopButton){
				turnAroundState = 2;
			}else if(DriveTrain.LEAtDistance && DriveTrain.REAtDistance){
				turnAroundState = 2;
			}else {
				turnAroundState = 1;
			}
			break;
		// Stop this sequence
		case 2:
			turnAroundStartTime = System.currentTimeMillis();
			turnAroundLeftDistance = 0;
			turnAroundRightDistance = 0;
			turnAroundFirstSolenoid = DoubleSolenoid.Value.kOff;
			turnAroundSecondSolenoid = DoubleSolenoid.Value.kOff;
			turnAroundThirdSolenoid = DoubleSolenoid.Value.kOff;

			if (macroStopButton){
				turnAroundState = 2;
			}else{
				turnAroundState = 3;
			}
			break;
		case 3:
			turnAroundLeftDistance = 0;
			turnAroundRightDistance = 0;
			turnAroundFirstSolenoid = DoubleSolenoid.Value.kOff;
			turnAroundSecondSolenoid = DoubleSolenoid.Value.kOff;
			turnAroundThirdSolenoid = DoubleSolenoid.Value.kOff;

			if (macroStopButton){
				
			}else if(System.currentTimeMillis() - turnAroundStartTime > 1800){
				turnAroundState = 0;
		    }else{
		    	turnAroundState = 3;
		    break;
		    }
		}
	}

	public static void lowBarSequence(){
		
		switch (lowBarState){
		case 0:
			lowBarLeftDistance = 0;
			lowBarRightDistance = 0;
			lowBarFirstSolenoid = DoubleSolenoid.Value.kOff;
			lowBarSecondSolenoid = DoubleSolenoid.Value.kOff;
			lowBarThirdSolenoid = DoubleSolenoid.Value.kOff;

			if (macroStopButton){
				lowBarState = 0;
			}else if(isReady){
				lowBarState = 1;
			}else{
				lowBarState = 0;
			}
			break;
		case 1:	// Go straight for 5'
			lowBarLeftDistance = 60;
			lowBarRightDistance = 60;
			lowBarFirstSolenoid = DoubleSolenoid.Value.kOff;
			lowBarSecondSolenoid = DoubleSolenoid.Value.kOff;
			lowBarThirdSolenoid = DoubleSolenoid.Value.kOff;

			if (macroStopButton){
				lowBarState = 2;
			}else if(DriveTrain.LEAtDistance && DriveTrain.REAtDistance){
				lowBarState = 2;
			}else {
				lowBarState = 1;
			}
			break;
		// Stop this sequence
		case 2:
			lowBarStartTime = System.currentTimeMillis();
			lowBarLeftDistance = 0;
			lowBarRightDistance = 0;
			lowBarFirstSolenoid = DoubleSolenoid.Value.kOff;
			lowBarSecondSolenoid = DoubleSolenoid.Value.kOff;
			lowBarThirdSolenoid = DoubleSolenoid.Value.kOff;
			
			if (macroStopButton){
				lowBarState = 2;
			}else{
				lowBarState = 3;
			}
			break;
		case 3:
			lowBarLeftDistance = 0;
			lowBarRightDistance = 0;
			lowBarFirstSolenoid = DoubleSolenoid.Value.kOff;
			lowBarSecondSolenoid = DoubleSolenoid.Value.kOff;
			lowBarThirdSolenoid = DoubleSolenoid.Value.kOff;
			
			if (macroStopButton){
				
			}else if(System.currentTimeMillis() - lowBarStartTime > 1800){
				lowBarState = 0;
		    }else{
		    	lowBarState = 3;
		    break;
		    }
		}
	}
	
	public static void portcullisSequence(){
		
	}
	
	public static void chevalSequence(){
		
		switch (chevalState){
		case 0:
			chevalLeftDistance = 0;
			chevalRightDistance = 0;
			chevalFirstSolenoid = DoubleSolenoid.Value.kOff;
			chevalSecondSolenoid = DoubleSolenoid.Value.kOff;
			chevalThirdSolenoid = DoubleSolenoid.Value.kOff;
			
			if (macroStopButton){
				chevalState = 0;
				isReady = true;
			}else if(isReady){
				chevalState = 1;
			}else{
				chevalState = 0;
			}
			break;
		case 1:	// Second solenoid fwd
			
			chevalLeftDistance = 0;
			chevalRightDistance = 0;
			chevalFirstSolenoid = DoubleSolenoid.Value.kOff;
			chevalSecondSolenoid = DoubleSolenoid.Value.kForward;
			chevalThirdSolenoid = DoubleSolenoid.Value.kOff;

			if (macroStopButton){
				chevalState = 2;
			}else {
				chevalState = 1;
			}
			break;
		// Stop this sequence
		case 2:
			chevalStartTime = System.currentTimeMillis();
			chevalLeftDistance = 0;
			chevalRightDistance = 0;
			chevalFirstSolenoid = DoubleSolenoid.Value.kOff;
			chevalSecondSolenoid = DoubleSolenoid.Value.kOff;
			chevalThirdSolenoid = DoubleSolenoid.Value.kOff;
			
			if (macroStopButton){
				chevalState = 2;
			}else{
				chevalState = 3;
			}
			break;
		case 3:
			chevalLeftDistance = 0;
			chevalRightDistance = 0;
			chevalFirstSolenoid = DoubleSolenoid.Value.kOff;
			chevalSecondSolenoid = DoubleSolenoid.Value.kOff;
			chevalThirdSolenoid = DoubleSolenoid.Value.kOff;
			
			if (macroStopButton){
				
			}else if(System.currentTimeMillis() - turnAroundStartTime > 1800){
				chevalState = 0;
		    }else{
		    	chevalState = 3;
		    break;
		    }
		}
		
		RobotMap.armSecondSolenoid.set(DoubleSolenoid.Value.kForward);
		Timer.delay(1);
		RobotMap.armThirdSolenoid.set(DoubleSolenoid.Value.kForward);
		Timer.delay(1);
		RobotMap.armFirstSolenoid.set(DoubleSolenoid.Value.kForward);
		Timer.delay(1);
//		DriveTrain.driveDuringDistance(18,18);
		RobotMap.armThirdSolenoid.set(DoubleSolenoid.Value.kReverse);
//		DriveTrain.driveDuringDistance(7,7);
		RobotMap.armFirstSolenoid.set(DoubleSolenoid.Value.kReverse);
		RobotMap.armSecondSolenoid.set(DoubleSolenoid.Value.kReverse);
//		DriveTrain.driveDuringDistance(74, 74);
	}

	public static void moatSequence(){
		
	}

	public static void rampartsSequence(){
		
	}
	public static void drawbridgeSequence(){
		RobotMap.armSecondSolenoid.set(DoubleSolenoid.Value.kForward);
		Timer.delay(1);
		RobotMap.armThirdSolenoid.set(DoubleSolenoid.Value.kForward);
		Timer.delay(1);
		RobotMap.armFirstSolenoid.set(DoubleSolenoid.Value.kForward);
		Timer.delay(1);
//		DriveTrain.driveDuringDistance(13.5, 13.5);
		RobotMap.armThirdSolenoid.set(DoubleSolenoid.Value.kReverse);
//		DriveTrain.driveDuringDistance(-38, -38);
//		DriveTrain.driveDuringDistance(36, 36);
		RobotMap.armFirstSolenoid.set(DoubleSolenoid.Value.kReverse);
		RobotMap.armSecondSolenoid.set(DoubleSolenoid.Value.kReverse);
//		DriveTrain.driveDuringDistance(104, 104);
	}
	public static void rockWallSequence(){
		
	}
	public static void roughTerrainSequence(){
		
	}
}
