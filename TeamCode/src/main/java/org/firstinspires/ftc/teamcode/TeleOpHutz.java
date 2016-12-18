package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ThreadPool;

/**
 * Created by Eliezer Pearl on 9/9/2016.
 */
@TeleOp(name="Hutzbots Teleop crab")
//@Disabled //uncomment to disable, comment to enable
public class TeleOpHutz extends LinearOpMode {

    final double DEAD_ZONE = 0.05; //TODO: CHANGE THIS THROUGH DEBUGGING

    DcMotor topLeft; //all motor names are given based on location from a top down
    DcMotor topRight; //view of the robot
    DcMotor botLeft;
    DcMotor botRight;
    DcMotor intake;
    DcMotor drawback; //todo: rotate like twice
    DcMotor release; //todo: pull back a bit, 1/4 turn
    Servo beacon;

    int andymark_tick = 1120;
    int tetrix_tick = 1440;

    ColorSensor cs;
    UltrasonicSensor ultrason;
    LightSensor lineDetector;

    Double RFLBPower = 0.0;
    Double RBLFPower = 0.0;
    Double arctanYX = 0.0;
    public void reset(){
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    }
    public void logToTelemetry() throws InterruptedException{
        telemetry.addData("Ultr: ", ultrason.getUltrasonicLevel());
        telemetry.addData("Line: ", lineDetector.getLightDetected());
        telemetry.addData("cs: argb ", cs.argb());
    }



    @Override
    public void runOpMode() throws InterruptedException{  //Executed in a linear format
        topLeft = hardwareMap.dcMotor.get("topLeft");        //sets code motors to point to
        topRight = hardwareMap.dcMotor.get("topRight");       //actual ones. use the names
        botLeft = hardwareMap.dcMotor.get("botLeft");        //in config as the string vals
        botRight = hardwareMap.dcMotor.get("botRight");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");
        lineDetector = hardwareMap.lightSensor.get("lineDetector");
        cs = hardwareMap.colorSensor.get("cs");
        intake = hardwareMap.dcMotor.get("intake");
        beacon = hardwareMap.servo.get("beacon");
        drawback = hardwareMap.dcMotor.get("drawback");
        release = hardwareMap.dcMotor.get("release");
        topLeft.setDirection(DcMotor.Direction.REVERSE);  //just for ease of programming since
        botLeft.setDirection(DcMotor.Direction.REVERSE);  //left motors are backward
        drawback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        release.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        reset();
        while (!isStarted()) {
            logToTelemetry();
        }
        while (opModeIsActive()) {
            if(Math.abs(gamepad1.right_stick_x) > DEAD_ZONE){
                    topRight.setPower(gamepad1.right_stick_x);
                    topLeft.setPower(-gamepad1.right_stick_x);
                    botRight.setPower(gamepad1.right_stick_x);
                    botLeft.setPower(-gamepad1.right_stick_x);
                } else if (Math.abs(gamepad1.left_stick_x) > DEAD_ZONE) {
                    arctanYX = Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x);
                    RFLBPower = Math.sin(arctanYX + Math.PI / 4);
                    RBLFPower = Math.sin(arctanYX - Math.PI / 4);
                    if (gamepad1.left_stick_x < 0) {
                        RFLBPower = -RFLBPower;
                        RBLFPower = -RBLFPower;
                    }
                    topRight.setPower(RFLBPower);
                botLeft.setPower(RFLBPower);
                botRight.setPower(RBLFPower);
                topLeft.setPower(RBLFPower);
            } else {
                topRight.setPower(0);
                topLeft.setPower(0);
                botRight.setPower(0);
                botLeft.setPower(0);
            }

            if (gamepad1.x) {
                if (beacon.getPosition() > 0.6) {
                    beacon.setPosition(0.5);
                } else {
                    beacon.setPosition(1); //TODO: FIND CORRECT VALUE
                }
            } else if (gamepad1.b) {
                if (beacon.getPosition() < 0.4) {
                    beacon.setPosition(0.5);
                } else {
                    beacon.setPosition(0); //TODO: FIND CORRECT VALUE
                }
            }

            if (gamepad1.a){
                intake.setPower(1);
            }
            else if(gamepad1.y){
                intake.setPower(-1);
            }
            else {
                intake.setPower(0);
            }
            if(gamepad1.dpad_right) {
                drawback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                drawback.setPower(0.1); //slow for now.
                drawback.setTargetPosition(andymark_tick/4); //debug. may need to multiply by 60 to account for gearbox
                drawback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while (drawback.isBusy()) {
                    //wait til motor drawn back
                }
                drawback.setPower(0);
                //we are now pulled back, hopefully!
                //now to release.
                release.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                release.setPower(0.5);
                release.setTargetPosition(tetrix_tick / 4); //should work, debug tho
                release.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while (drawback.isBusy()) {
                    //other motor is releasing!
                }
                release.setPower(0);
            }
        }
    }
}

