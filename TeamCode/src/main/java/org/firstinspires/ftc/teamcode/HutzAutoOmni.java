package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by Ben on 12/11/16.
 */
@Autonomous(name="HutzAutoOmni", group="hutzAuto")
public class HutzAutoOmni extends LinearOpMode {
    String team = "red";                                //CHANGE TEAM AS NEEDED
    DcMotor topLeft, topRight, botLeft, botRight;
    DcMotor intake, cannon;
    UltrasonicSensor ultrason;  //WRITE FOR THIS
    Servo beacon;
    ColorSensor csR, csL;
    LightSensor line;           //WRITE FOR THIS
    public void runOpMode() throws InterruptedException {
        topLeft = hardwareMap.dcMotor.get("topLeft");
        topRight = hardwareMap.dcMotor.get("topRight");
        botLeft = hardwareMap.dcMotor.get("botLeft");
        botRight = hardwareMap.dcMotor.get("botRight");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        botLeft.setDirection(DcMotor.Direction.REVERSE);

        intake = hardwareMap.dcMotor.get("ballIntake");
        cannon = hardwareMap.dcMotor.get("cannon");

        line = hardwareMap.lightSensor.get("line");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");

        beacon = hardwareMap.servo.get("beacon");

        csL = hardwareMap.colorSensor.get("csL");
        csR = hardwareMap.colorSensor.get("csR");

        reset();
        beacon.setPosition(0.5);
        //TODO: WRITE CODE HERE!!!!!!!!
    }
    public void reset() {
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    }
    public void strafe180 (double power) {
        topLeft.setPower(power);
        topRight.setPower(-power);
        botLeft.setPower(-power);
        botRight.setPower(power);
        reset();
    }
    public void strafe180 (double power, int time) throws InterruptedException {
        topLeft.setPower(power);
        topRight.setPower(-power);
        botLeft.setPower(-power);
        botRight.setPower(power);
        Thread.sleep(time * 1000);
        reset();
    }
    public void strafeRight45 (double power) {
        topLeft.setPower(power);
        botRight.setPower(power);
        reset();
    }
    public void strafeRight45 (double power, int time) throws InterruptedException {
        topLeft.setPower(power);
        botRight.setPower(power);
        Thread.sleep(time * 1000);
        reset();
    }
    public void strafeLeft45 (double power) {
        topRight.setPower(power);
        botLeft.setPower(power);
        reset();
    }
    public void strafeLeft45 (double power, int time) throws InterruptedException {
        topRight.setPower(power);
        botLeft.setPower(power);
        Thread.sleep(time * 1000);
        reset();
    }
    public void driveStraight (double power) {
        topRight.setPower(power);
        topLeft.setPower(power);
        botRight.setPower(power);
        botLeft.setPower(power);
        reset();
    }
    public void driveStraight (double power, int time) throws InterruptedException {
        topRight.setPower(power);
        topLeft.setPower(power);
        botRight.setPower(power);
        botLeft.setPower(power);
        Thread.sleep(time * 1000);
        reset();
    }
    public void pushLeft() {
        beacon.setPosition(1); //TODO: CHANGE THROUGH DEBUGGING (THIS IS ALMOST DEFINITELY WRONG)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        beacon.setPosition(0.5);
    }
    public void pushRight() {
        beacon.setPosition(0); //TODO: CHANGE THROUGH DEBUGGING (THIS IS ALMOST DEFINITELY WRONG)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        beacon.setPosition(0.5);
    }
    public void pushButton() {
        String valueRight = Integer.toString(csR.argb());
        String colorRight = "";
        if (valueRight != "") {
            if (Integer.valueOf(valueRight.substring(2, 4)) > Integer.valueOf(valueRight.substring(6, 8))) {
                colorRight = "red";
            } else {
                colorRight = "blue";
            }
        }
        String valueLeft = Integer.toString(csL.argb());
        String colorLeft = "";
        if (valueLeft != "") {
            if (Integer.valueOf(valueLeft.substring(2, 4)) > Integer.valueOf(valueLeft.substring(6, 8))) {
                colorLeft = "red";
            } else {
                colorLeft = "blue";
            }
        }
        if (colorLeft == colorRight) {
            if (colorLeft != team) {
                pushLeft();
            }
        } else if (colorLeft.equals(team)) {
            pushLeft();
        } else if (colorRight.equals(team)) {
            pushRight();
        }
    }
    public void shoot () {
        cannon.setPower(1);
        try {Thread.sleep(2000);} catch (InterruptedException e) {}
        //TODO: WRITE FOR RELEASE!!!!!!
    }
}
