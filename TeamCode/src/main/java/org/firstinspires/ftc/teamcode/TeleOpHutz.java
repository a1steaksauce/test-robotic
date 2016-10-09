package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Eliezer Pearl on 9/9/2016.
 */
@TeleOp(name="mainBot: Teleop Tank 2")
//@Disabled //uncomment to disable, comment to enable
public class TeleOpHutz extends LinearOpMode {

    final double DEAD_ZONE = 0.08; //TODO: CHANGE THIS THROUGH DEBUGGING

    DcMotor topLeft; //all motor names are given based on location from a top down
    DcMotor topRight; //view of the robot
    DcMotor botLeft;
    DcMotor botRight;
    DcMotor flywheel; //operates the gearbox
    DcMotor steve; //for raising the balls to the gearbox. Name was provided by Elan Ganz
    public void reset(){
        flywheel.setPower(0);
        steve.setPower(0);
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
        buttonPush.setPosition(0.5);
        handL.setPosition(0.5);
        handR.setPosition(0.5);
        handUp.setPosition(1);
    }
    /////////////////////////////////////////
    Servo buttonPush;//pushes buttons on beacon
    Servo angler;
    Servo handL;
    Servo handR;
    Servo handUp;
    /////////////////////////////////////////
    UltrasonicSensor us;
    ColorSensor lineTracker; //pointed at floor
    ColorSensor beaconDetector; //pointed at beacon

    boolean bumperRunning = false;
    boolean up = false;

    public TeleOpHutz() {  //just here, don't touch

    }


    @Override
    public void runOpMode() {  //Executed in a linear format
        topLeft = hardwareMap.dcMotor.get("topLeft");        //sets code motors to point to
        topRight = hardwareMap.dcMotor.get("topRight");       //actual ones. use the names
        botLeft = hardwareMap.dcMotor.get("botLeft");        //in config as the string vals
        botRight = hardwareMap.dcMotor.get("botRight");
        flywheel = hardwareMap.dcMotor.get("flywheel");
        steve = hardwareMap.dcMotor.get("steve");
        buttonPush = hardwareMap.servo.get("buttonPush");
        angler = hardwareMap.servo.get("angler");
        us = hardwareMap.ultrasonicSensor.get("us");
        lineTracker = hardwareMap.colorSensor.get("lineTracker");
        beaconDetector = hardwareMap.colorSensor.get("beaconDetector");

        topLeft.setDirection(DcMotor.Direction.REVERSE);  //just for ease of programming since
        botLeft.setDirection(DcMotor.Direction.REVERSE); //left motors are backward

        reset();

        while (true) {
            /*
                Essentially, the following code makes sure that
                a joystick is being moved for real (hence
                the deadzone, since a stationary joystick can
                sometimes give nonzero values), and then sets a
                joystick's y value to the corresponding motors'
                power. So left joystick moves left motors,
                right joystick moves right motors.
            */
            if (Math.abs(gamepad1.left_stick_y) > DEAD_ZONE) {
                topLeft.setPower(gamepad1.left_stick_y);
                botLeft.setPower(gamepad1.left_stick_y);
            }
            if (Math.abs(gamepad1.right_stick_y) > DEAD_ZONE) {
                topRight.setPower(gamepad1.right_stick_y);
                botRight.setPower(gamepad1.right_stick_y);
            }
            if (Math.abs(gamepad1.right_stick_y) < DEAD_ZONE) {
                topRight.setPower(0);
                botRight.setPower(0);
            }
            if (Math.abs(gamepad1.left_stick_y) < DEAD_ZONE) {
                topLeft.setPower(0);
                botLeft.setPower(0);
            }
            if (gamepad1.dpad_up) {
                if (gamepad1.left_trigger != 1) {
                    steve.setPower(0.5);
                } else {
                    steve.setPower(0.9);
                }
            } else if (gamepad1.dpad_down) {
                if (gamepad1.left_trigger != 1) {
                    steve.setPower(-0.5);
                } else {
                    steve.setPower(-0.9);
                }
            }
            if (gamepad1.dpad_left) {
                flywheel.setPower(1); //note that this is max power; also TODO: MIGHT NEED TO REVERSE MOTOR IF SPINS INWARDS
            } else {
                flywheel.setPower(0);
            }
            if (gamepad1.left_bumper) {
                buttonPush.setPosition(0.75); //TODO: THIS IS PROBABLY TOO MUCH; SUBJECT TO CHANGE
                bumperRunning = true;
            }
            if (gamepad1.right_bumper) {
                buttonPush.setPosition(0.25); //TODO: THIS IS PROBABLY TOO MUCH; SUBJECT TO CHANGE
                bumperRunning = true;
            }
            if (gamepad1.y) {
                if (angler.getPosition() - 0.05 <= 1.0)
                    angler.setPosition(angler.getPosition() + 0.05); //TODO: MIGHT BE TOO FAST/SLOW; SUBJECT TO CHANGE
            }
            if (gamepad1.a) {
                if (angler.getPosition() - 0.05 >= 0.0)
                    angler.setPosition(angler.getPosition() - 0.05); //TODO: MIGHT BE TOO FAST/SLOW; SUBJECT TO CHANGE
            }
            if (gamepad1.dpad_right) {
                if(up){
                    handUp.setPosition(1);
                    up = true;
                }
                else{
                    handUp.setPosition(0.2);
                    up = false;
                }
            }
            if (gamepad1.left_stick_button) {
                handL.setPosition(1);
                handR.setPosition(1);
            }
            if (gamepad1.right_stick_button){
                handL.setPosition(0);
                handR.setPosition(0);
            }
            if (!bumperRunning)
                buttonPush.setPosition(0.5);

            /*Telemetry is basically System.out.println() for
            robots. For example, telemetry.addData("Text", "*** Robot Data***");
            will display "Text: *** Robot Data***" on the
            driver's station.
            */
            telemetry.addData("Left joystick Y value:", gamepad1.left_stick_y);
            telemetry.addData("Right joystick Y value:", gamepad1.right_stick_y);
        }
    }
}

