package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Supreme Leader Jacob Davoudgoleh on 11/15/15.
 */
public class AutonomousAlex extends LinearOpMode {

    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor leftDriveB;
    DcMotor rightDriveB;
    Servo climberServo;
    private static double degreeMultiplier = 0.0077777;

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    public void runOpMode() throws InterruptedException {

        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDriveB = hardwareMap.dcMotor.get("leftDriveB");
        rightDriveB = hardwareMap.dcMotor.get("rightDriveB");
        climberServo = hardwareMap.servo.get("climberServo");


        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDriveB.setDirection(DcMotor.Direction.REVERSE);

        leftDrive.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightDrive.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        leftDriveB.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightDriveB.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        waitForStart();

        drive(-0.5, -0.5, 2.4);
        //drive(0.5, -0.5, 0.7);
        //drive(-0.5, -0.5, 3);
        turn(RIGHT,45);
        //drive(-0.5, -0.5, 1);
        //climberServo.setPosition(0.7);
        //Thread.sleep((long)(0.5));
        //climberServo.setPosition(0.5);


    }
    public void turn(int direction, double degrees){ //As of now, 6 and 2/3 milliseconds = 1 Degree
        if(direction==RIGHT){
            drive(-0.5,0.5,degrees*degreeMultiplier);
            //drive(-0.5,0.5,degrees * de);
        }
        if(direction==LEFT){
            drive(0.5,-0.5,degrees*degreeMultiplier);
            //drive(0.5,-0.5,degrees * );
        }

    }
    public void drive(double leftPower, double rightPower, double seconds) { //Method for driving on right side with parameters for power and seconds
        rightDrive.setPower(rightPower);
        rightDriveB.setPower(rightPower);
        leftDrive.setPower(leftPower);
        leftDriveB.setPower(leftPower);
        try {
            // thread to sleep for 1000 milliseconds
            Thread.sleep((long) ((seconds) * 1000)); //Wait for seconds * 1000 to get milliseconds
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        rightDrive.setPower(0); //Set values to 0
        rightDriveB.setPower(0);
        leftDrive.setPower(0); //Set values to 0
        leftDriveB.setPower(0);
    }

    public void driveEncoder(double leftPower, double rightPower, double ticks)
    {
        rightDrive.setTargetPosition((int) ticks); //may need to cast inches to int or long idk also may be in other units
        rightDriveB.setTargetPosition((int) ticks);
        leftDrive.setTargetPosition((int) ticks);
        leftDriveB.setTargetPosition((int) ticks);

        rightDrive.setPower(rightPower);
        rightDriveB.setPower(rightPower);
        leftDrive.setPower(leftPower);
        leftDriveB.setPower(leftPower);
    }
    public void operate(Servo ser, double position, long wait){
        ser.setPosition(position);
        try {
            Thread.sleep(wait);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        ser.setPosition(0);
    }
}