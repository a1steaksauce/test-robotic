package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Eliezer Pearl on 10/16/2016.
 */
@TeleOp(name="mainBot: Teleop Tank No Small Ball")

public class TeleopHutzNoSmLift extends LinearOpMode {
    final double DEAD_ZONE = 0.05; //TODO: CHANGE THIS THROUGH DEBUGGING
    final double INCREMENT = 0.05;
    final int CPR = 1440; //Counts per revolution
    final int CPI = CPR / 4;
    DcMotor topLeft; //all motor names are given based on location from a top down
    DcMotor topRight; //view of the robot
    DcMotor botLeft;
    DcMotor botRight;
    DcMotor flywheel; //operates the gearbox
    DcMotor otherFly;
    DcMotor lift;
    Servo buttonPush; //pushes buttons on beacon
    Servo angler;
    UltrasonicSensor us;
    ColorSensor lineTracker; //pointed at floor
    ColorSensor beaconDetector; //pointed at beacon
    @Override
    public void runOpMode(){
        topLeft = hardwareMap.dcMotor.get("topLeft");        //sets code motors to point to
        topRight = hardwareMap.dcMotor.get("topRight");      //actual ones. use the names
        botLeft = hardwareMap.dcMotor.get("botLeft");        //in config as the string vals
        botRight = hardwareMap.dcMotor.get("botRight");
        flywheel = hardwareMap.dcMotor.get("flywheel");
        otherFly = hardwareMap.dcMotor.get("otherFly");
        lift = hardwareMap.dcMotor.get("lift");
        angler = hardwareMap.servo.get("angler");
        buttonPush = hardwareMap.servo.get("buttonPush");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        botLeft.setDirection(DcMotor.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(true){
            if (Math.abs(gamepad1.left_stick_y) > DEAD_ZONE) {
                topLeft.setPower(gamepad1.left_stick_y);
                botLeft.setPower(gamepad1.left_stick_y);
            } else if (Math.abs(gamepad1.left_stick_y) <= DEAD_ZONE) {
                topLeft.setPower(0);
                botLeft.setPower(0);
            }                                                           //Drives robot
            if (Math.abs(gamepad1.right_stick_y) > DEAD_ZONE) {
                topRight.setPower(gamepad1.right_stick_y);
                botRight.setPower(gamepad1.right_stick_y);
            } else if (Math.abs(gamepad1.right_stick_y) <= DEAD_ZONE) {
                topRight.setPower(0);
                botRight.setPower(0);
            }
            if (gamepad1.right_trigger > DEAD_ZONE) {
                flywheel.setPower(gamepad1.right_trigger);
                otherFly.setPower(gamepad1.right_trigger);
            } else if (gamepad1.right_trigger <= DEAD_ZONE) {           //Operates flywheels
                flywheel.setPower(0);
                otherFly.setPower(0);
            }
            if (gamepad1.dpad_up) {
                angler.setPosition(angler.getPosition()+INCREMENT);
            } else if (gamepad1.dpad_down) {                            //Angles launcher
                angler.setPosition(angler.getPosition()-INCREMENT);
            }
            if (gamepad1.dpad_left) {
                buttonPush.setPosition(0.3);
            } else if (gamepad1.dpad_right) {
                buttonPush.setPosition(0.7);
            } else {
                buttonPush.setPosition(0.5);
            }
            if (gamepad1.y) {
                lift.setTargetPosition(14*CPI);
                lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            } else if (gamepad1.a) {
                lift.setTargetPosition(-14*CPI);
                lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
        }
    }
}
