package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.*;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.util.Range;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;


/**
 * Created by alexbulanov on 11/1/15.
 */
public class AutonomousREAL extends OpMode {
    private static final int MOTOR_FORWARD = 1; //Variable for moving forward
    private static final int MOTOR_BACKWARD = -1;//Variable for moving backwards
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor leftDriveB;
    DcMotor rightDriveB;
    Servo buttonServo;
    Servo climberServo;
    ColorSensor colorSensor;

    @Override public void init() {
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDriveB = hardwareMap.dcMotor.get("rightDriveB");
        leftDriveB = hardwareMap.dcMotor.get("leftDriveB");
        buttonServo = hardwareMap.servo.get("buttonServo");
        climberServo = hardwareMap.servo.get("climberServo");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");


        //motor power values is between -1 and 1 gud
        buttonServo.setPosition(0);
        climberServo.setPosition(0);
        drive(1.0, 1.0, 4l);
        telemetry.addData("Clear", colorSensor.alpha());
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue ", colorSensor.blue());

        operate(climberServo, 1.0, 1000l);
        if(colorSensor.red() >= 80) //test val
        {
            turn(RIGHT, 3l/2); //90+45 degrees
            drive(MOTOR_FORWARD, MOTOR_FORWARD, 1l/2);
            turn(LEFT, 3l/2); //' '
            drive(MOTOR_FORWARD, MOTOR_FORWARD, 1l/2); //time to drive 10.7906209275 inches
            operate(buttonServo, 1.0, 1000l);


        }
        else if(colorSensor.blue() >= 80)
        {
            operate(climberServo, 1.0, 1000l);
        }
        else
        {
            System.err.println("Bad color!");
            System.exit(1);
            //this will let us know color not found
            //in future, have robot readjust and rerun
        }



        //base thing
        //colorSensor.red should be greater than a certain value
        //or colorSensor.blue should be greater than a certain value
        //after driving
        //
        //and then do stuff to hit button accordingly
    }

    @Override public void loop ()
    {

    }
    @Override public void stop () {
    }
    public void drive(double leftPower, double rightPower, long seconds){ //Method for driving on right side with parameters for power and seconds
        rightDrive.setPower(-rightPower); //Set the right wheel to -power because right side has to be negative
        rightDriveB.setPower(-rightPower);
        leftDrive.setPower(leftPower);
        leftDriveB.setPower(leftPower);
        try {
            // thread to sleep for 1000 milliseconds
            Thread.sleep(seconds*1000l); //Wait for seconds * 1000 to get milliseconds
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        rightDrive.setPower(0); //Set values to 0
        rightDriveB.setPower(0);
        leftDrive.setPower(0); //Set values to 0
        leftDriveB.setPower(0);
    }
    public void turn(int direction, long seconds){
        if(direction==RIGHT){
            drive(-1.0,1.0,seconds);
        }
        if(direction==LEFT){
            drive(1.0,-1.0,seconds);
        }
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

