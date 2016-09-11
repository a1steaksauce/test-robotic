package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.*;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by alexbulanov on 11/1/15.
 */
public class AutonomousJacobAndAlex extends OpMode {
    int MOTOR_FORWARD = 1; //Variable for moving forward
    int MOTOR_BACKWARD = 1;//Variable for moving backwards
    int LEFT = 0;
    int RIGHT = 1;
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor leftDriveB;
    DcMotor rightDriveB;
    //Servo buttonServo;
    //Servo climberServo;

    @Override public void init() {
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDriveB = hardwareMap.dcMotor.get("rightDriveB");
        leftDriveB = hardwareMap.dcMotor.get("leftDriveB");
        //buttonServo = hardwareMap.servo.get("buttonServo");
        //climberServo = hardwareMap.servo.get("climberServo");

        //motor power values is between -1 and 1
        //buttonServo.setPosition(0);
        //climberServo.setPosition(0);

        drive(1, 1, 5); //LeftPower, RightPower, Seconds
    }
    @Override public void loop ()
    {

    }
    @Override public void stop () {
    }
    public void drive(int leftPower, int rightPower, int seconds){ //Method for driving on right side with parameters for power and seconds
        rightDrive.setPower(-rightPower); //Set the right wheel to -power because right side has to be negative
        rightDriveB.setPower(-rightPower);
        leftDrive.setPower(leftPower);
        leftDriveB.setPower(leftPower);
        try {
            // thread to sleep for 1000 milliseconds
            Thread.sleep(seconds*1000); //Wait for seconds * 1000 to get milliseconds
        } catch (Exception e) {
            System.out.println(e);
        }
        rightDrive.setPower(0); //Set values to 0
        rightDriveB.setPower(0);
        leftDrive.setPower(0); //Set values to 0
        leftDriveB.setPower(0);
    }
    public void turn(int direction, int seconds){
        if(direction==RIGHT){
            drive(-1,1,seconds);
        }
        if(direction==LEFT){
            drive(1,-1,seconds);
        }
    }
}