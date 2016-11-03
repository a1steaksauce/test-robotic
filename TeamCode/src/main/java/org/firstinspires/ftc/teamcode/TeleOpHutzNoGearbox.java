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
public class TeleOpHutzNoGearbox extends LinearOpMode {

    final double DEAD_ZONE = 0.08; //TODO: CHANGE THIS THROUGH DEBUGGING

    DcMotor topLeft; //all motor names are given based on location from a top down
    DcMotor topRight; //view of the robot
    DcMotor botLeft;
    DcMotor botRight;
    DcMotor spring, tower, ballIntake;
    Servo angler;


    public void reset(){
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
        spring.setPower(0.5);
        tower.setPower(0);
        ballIntake.setPower(0.75);
    }
    UltrasonicSensor us;
    ColorSensor lineTracker; //pointed at floor
    ColorSensor beaconDetector; //pointed at beacon

    public TeleOpHutzNoGearbox() {  //just here, don't touch

    }


    @Override
    public void runOpMode() {  //Executed in a linear format
        topLeft = hardwareMap.dcMotor.get("topLeft");        //sets code motors to point to
        topRight = hardwareMap.dcMotor.get("topRight");      //actual ones. use the names
        botLeft = hardwareMap.dcMotor.get("botLeft");        //in config as the string vals
        botRight = hardwareMap.dcMotor.get("botRight");
        angler = hardwareMap.servo.get("angler");
        spring = hardwareMap.dcMotor.get("spring");
        tower = hardwareMap.dcMotor.get("tower");
        ballIntake = hardwareMap.dcMotor.get("ballIntake");
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
            if (gamepad1.x) {
                angler.setPosition(0);
            }
            if (gamepad1.y) {
                angler.setPosition(0.5);
            }
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