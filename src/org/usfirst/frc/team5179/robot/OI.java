package org.usfirst.frc.team5179.robot;



import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	// Main driver's joystick
	
    public static Joystick joystick;
	
    public static JoystickButton cameraFrontMaxButton;
    public static JoystickButton cameraRearMaxButton;
    public static JoystickButton cameraFollowButton;
    public static JoystickButton grabButton;
    public static JoystickButton throwButton;
    public static JoystickButton cancelBall;
    public static JoystickButton cancelCamera;
    public static JoystickButton enableCompressor;

    
    // Codriver's control panel
    
    public static Joystick codriver;
    
    public static JoystickButton closeSolenoidButton;
    public static JoystickButton firstSolenoidUpButton;
    public static JoystickButton firstSolenoidDownButton;
    public static JoystickButton secondSolenoidUpButton;
    public static JoystickButton secondSolenoidDownButton;
    public static JoystickButton thirdSolenoidUpButton;
    public static JoystickButton thirdSolenoidDownButton;
    public static JoystickButton drawbridgeButton;
    public static JoystickButton portcullisButton;
    public static JoystickButton chevalButton;
    public static JoystickButton lowBarButton;
    public static JoystickButton moatButton;
    public static JoystickButton rampartsButton;
    public static JoystickButton rockWallButton;
    public static JoystickButton roughTerrainButton;
	public static JoystickButton abortMacroButton;
    
	public static void updateOI(){
				
		// Driver
		
        joystick = new Joystick(0);
        
        cancelBall = new JoystickButton(joystick, 3);
        throwButton = new JoystickButton(joystick, 2);
        grabButton = new JoystickButton(joystick, 1);
        
        // Codriver
        
        codriver = new Joystick(1);
        
        lowBarButton = new JoystickButton(codriver, 16);
        moatButton = new JoystickButton(codriver, 15);
        rampartsButton = new JoystickButton(codriver, 14);
        rockWallButton = new JoystickButton(codriver, 13);
        roughTerrainButton = new JoystickButton(codriver, 12);
        chevalButton = new JoystickButton(codriver, 10);
        portcullisButton = new JoystickButton(codriver, 7);
        drawbridgeButton = new JoystickButton(codriver, 8);
        abortMacroButton = new JoystickButton(codriver, 9);
        
        thirdSolenoidDownButton = new JoystickButton(codriver, 6);
        thirdSolenoidUpButton = new JoystickButton(codriver, 5);
        secondSolenoidDownButton = new JoystickButton(codriver, 4);
        secondSolenoidUpButton = new JoystickButton(codriver, 3);
        firstSolenoidDownButton = new JoystickButton(codriver, 2);
        firstSolenoidUpButton = new JoystickButton(codriver, 1);
        closeSolenoidButton = new JoystickButton (codriver, 11);
	}
	
    public Joystick getJoystick() {
        return joystick;
    }

    public Joystick getCodriver() {
        return codriver;
    }
}
