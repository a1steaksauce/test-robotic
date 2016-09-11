package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class RobotServos {


    private Servo buttonServo; //Defines the servos
    private Servo climberServo;

    
    public RobotServos(Servo BS, Servo CS){ //Creates what an instance of this class looks like
        buttonServo = BS; //Defines the servos to the parameters
        climberServo = CS;
        setButtonServo(0.5); //calls functions to servos to 0 at the start
        setClimberServo(0.5);
    }
    public void setButtonServo(double servoPosition){ //Function for setting the buttonServo

        buttonServo.setPosition(servoPosition);
        servoPosition = Range.clip(servoPosition,0,1); //Clips values
    }
    public void setClimberServo(double servoPosition){ //Function for setting the climberServo

        climberServo.setPosition(servoPosition);
        servoPosition = Range.clip(servoPosition,0,1);//Clips values
    }
    public void setServos(Gamepad gamepad){
        if(gamepad.dpad_left){ //If left dpad is pressed, change the buttonServo
            setButtonServo(1); //Value may need to be changed
        }
        if (gamepad.dpad_right){//If right dpad is pressed, change the buttonServo
            setButtonServo(0); //Value may need to be changed
        }
        while(gamepad.dpad_up){ //If up dpad is pressed, change the climberServo
            if(climberServo.getPosition() < 1){ //Make sure it doesn't go too far
                setClimberServo(0.75);//Value may need to be changed
            }
        }
        while(gamepad.dpad_down){//If dpad down is pressed, change the climberServo
            if(climberServo.getPosition() > 0){ //Make sure it doesn't go too far
                setClimberServo(0.25);//Value may need to be changed
            }
        }
    }
}
