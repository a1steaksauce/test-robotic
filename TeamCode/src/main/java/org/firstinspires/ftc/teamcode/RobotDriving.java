package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by AlexBulanov on 11/11/15.
 */

public class RobotDriving {
    private DcMotor leftDrive; //Defines the four motors
    private DcMotor leftDriveB;
    private DcMotor rightDrive;
    private DcMotor rightDriveB;

    public RobotDriving(DcMotor LF, DcMotor LB, DcMotor RF, DcMotor RB){
    //Creates what an instance of this class should look like

        leftDrive = LF; //Sets the four motors to the parameters. When an instance of RobotDriving is created,
        leftDriveB = LB;  //the parameters are the hardware mappings for the four motors
        rightDrive = RF;
        rightDriveB = RB;

        //Sets the two right motors to revers because they are reverse direction from the left motors, but the
        //joysticks are in the same direction
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDriveB.setDirection(DcMotor.Direction.REVERSE);
    }
    public void tankDrive(double leftPower, double rightPower){ //Function for driving with parameters leftDrive and RightDrive
        leftPower = Range.clip(leftPower, -1, 1); //Clip the values
        rightPower = Range.clip(rightPower,-1,1);

        leftDrive.setPower(leftPower); //Sets the motors to the correct power on their side
        leftDriveB.setPower(leftPower);
        rightDrive.setPower(rightPower);
        rightDriveB.setPower(rightPower);
    }
    public void tankDrive(Gamepad gamepad){
        //Function that calls the other tankDrive with parameters of which gamepad to use
        tankDrive(gamepad.left_stick_y,gamepad.right_stick_y); //gets values from joysticks and sets the motors
    }

}
