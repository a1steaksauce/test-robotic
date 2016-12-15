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
    ColorSensor cs;
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

        cs = hardwareMap.colorSensor.get("cs");

        reset();
        beacon.setPosition(0.5);

        while(!isStarted()) {
            telemetry.addData("line: ", line.getLightDetected());
            telemetry.addData("ultrason: ", ultrason.getUltrasonicLevel());
            telemetry.addData("cs: ", cs.argb());

            updateTelemetry(telemetry);
        }
        while(opModeIsActive()){
            //todo: write code here
            //shoot ball
            intake.setPower(1);
            Thread.sleep(1200);
            intake.setPower(0);
            //shoot again
            strafeRight45(1);
            doTilDistance(8);
            reset();
            for (int i = 0; i < 2; i++) {
                Thread.sleep(500);
                driveStraight(0.5);
                doTilLine();
                reset();
                Thread.sleep(500);
                pushButton();
            }
            strafeRight45(-0.75);
            doTilPlatform(15);
            reset();
            if (botOrNot()) {
                strafeRight45(-1);
                Thread.sleep(400);
                reset();
            } else {
                strafeLeft45(-0.5);
                Thread.sleep(600);
                reset();
                driveStraight(-0.3);
                Thread.sleep(200);
                strafeLeft45(-0.5);
                Thread.sleep(600);
                reset();
            }
            idle();
        }
    }
    public void reset() {
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    }
    public void doTilLine() throws InterruptedException{ //waits until white line
        double lightStore;
        do {
            Thread.sleep(50);
            lightStore = line.getLightDetected();
        } while (lightStore > 0.12); //drives until white line
    }
    public void doTilDistance (double distance) throws InterruptedException{ //waits until robot is a certain distance from a thing in cm
        double ultrasonStore;
        do {
            Thread.sleep(50);
            ultrasonStore = ultrason.getUltrasonicLevel();
        } while (ultrasonStore > distance && ultrasonStore != 0);
    }
    public void doTilPlatform (double distance) throws InterruptedException{ //waits until robot is on platform
        double lightStore;
        double ultrasonStore;
        do {
            Thread.sleep(50);
            lightStore = line.getLightDetected();
            ultrasonStore = ultrason.getUltrasonicLevel();
        } while (lightStore > 0.25 || (ultrasonStore > distance && ultrasonStore != 0)); //TODO: TEST!
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
        String valueRight = Integer.toString(cs.argb());
        String colorRight = "";
        if (valueRight != "") {
            if (Integer.valueOf(valueRight.substring(2, 4)) > Integer.valueOf(valueRight.substring(6, 8))) {
                colorRight = "red";
            } else {
                colorRight = "blue";
            }
        }
        if (colorRight.equals(team)) {
            pushRight();
        } else {
            pushLeft();
        }
    }
    public boolean botOrNot () throws InterruptedException { //returns true if not robot
        strafeRight45(-0.4);
        doTilDistance(8);
        reset();
        int red = Integer.valueOf(Integer.toString(cs.argb()).substring(2,4));
        int blue = Integer.valueOf(Integer.toString(cs.argb()).substring(6,8));
        int colorVal = red - blue;
        String color = "";
        if (Math.abs(colorVal) > 30) {
            if (colorVal < 0) {
                color = "blue";
            } else {
                color = "red";
            }
            if (color.equals(team)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public void shoot () {
        cannon.setPower(1);
        try {Thread.sleep(2000);} catch (InterruptedException e) {}
        //TODO: WRITE FOR RELEASE!!!!!!
    }
}
