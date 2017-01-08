package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Eliezer Pearl on 9/9/2016.
 */
@TeleOp(name="Teleop Hutzbots Mark 2", group="hutzTele")
//@Disabled //uncomment to disable, comment to enable
public class TeleOpHutzMK2 extends HutzFuncMK2 {
    final double NONE = 180.4;
    final double DEAD_ZONE = 0.05; //TODO: CHANGE THIS THROUGH DEBUGGING
    //final long TIME_DELAY = 500000000L; //The window of time where a controller CANNOT adjust beacon pushers after hitting it once
    //The goal of the time window is so that a human can reasonably toggle on a button pusher without having to be incredibly precise
    //with timing. Earlier, the code runs through the same boolean determining whether to lower a beacon pusher many times per second.
    //With a delay, toggling is easier.
    //boolean downL = false;
    //boolean downR = false;
    //boolean isBusyL = false;
    //boolean isBusyR = false;
    //long currTimeL = 0;
    //long currTimeR = 0;
    @Override
    public void runOpMode(){
        boolean driving = false;
        boolean turning = false;
        initializeHardware("lol doesn't matter");
        while(!isStarted()){
            logToTelemetry();
        }
        while(opModeIsActive()){
            //left stick drives, right stick turns. add compass or gyro!!!!! for turning. or not

            if(Math.abs(gamepad1.right_stick_y) > DEAD_ZONE || Math.abs(gamepad1.right_stick_x) > DEAD_ZONE){ //crab drive
                drive(Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x), Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y , 2)) );
                driving = true;
            } else {
                driving = false;
            }
            if(Math.abs(gamepad1.left_stick_x) > DEAD_ZONE){
                spin(gamepad1.left_stick_x);
                turning = true;
            } else {
                turning = false;
            }
            if(!driving && !turning){
                resetWheels();
            }

//            if (currTimeL + TIME_DELAY <= System.nanoTime()) { //if the timeframe has elapsed
//                isBusyL = false; //you are no longer busy
//            }
//            if(gamepad1.left_bumper) {
//                if (!isBusyL) { //if the RC hasn't received an "adjust beacon pusher left" input within a 1/2 sec window since last input of the sort
//                    currTimeL = System.nanoTime(); //start a new 1/2 sec period
//                    isBusyL = true; //you are now busy and the time that you are busy is now set.
//                    if (!downL) {
//                        beaconLeft.setPosition(0.5);
//                        downL = true;
//                    } else {
//                        beaconLeft.setPosition(1);
//                        downL = false;
//                    }
//                }
//            }
//
//
//            if (currTimeR + TIME_DELAY <= System.nanoTime()) { //if the timeframe has elapsed
//                isBusyR = false; //you are no longer busy
//            }
//            if(gamepad1.right_bumper){
//                if(!isBusyR) { //see above for explanation
//                    currTimeR = System.nanoTime();
//                    isBusyR = true;
//                    if (!downR) {
//                        beaconRight.setPosition(0.3);
//                        downR = true;
//                    } else {
//                        beaconRight.setPosition(0);
//                        downR = false;
//                    }
//                }
//            }
            if(gamepad1.left_bumper){
                setServo(true);
            } else {
                beaconLeft.setPosition(1);
            }

            if(gamepad1.right_bumper){
                setServo(false);
            } else {
                beaconRight.setPosition(0);
            }

            if(gamepad1.a){
                intake.setPower(-1);
            } else {
                intake.setPower(0);
            }

            if(gamepad1.a){
                release.setPower(-0.2);
            } else {
                release.setPower(0);
            }
            //TODO: maybe add another controller

        }
    }
}

