package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by poop on 11/27/2016.
 */
@Autonomous(name="hutzpls", group="hutzAuto")
public class HutzAll extends LinearOpMode{
    DcMotor topLeft, topRight, botLeft, botRight; //mecanum wheels
   // DcMotor ballIntake, cannon; //Other cool motors
    LightSensor line; //follows line
    UltrasonicSensor ultrason;
    //ColorSensor csL, csR;
    public void runOpMode() throws InterruptedException{
        topLeft = hardwareMap.dcMotor.get("topLeft");
        topRight = hardwareMap.dcMotor.get("topRight");
        botLeft = hardwareMap.dcMotor.get("botLeft");
        botRight = hardwareMap.dcMotor.get("botRight");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        botLeft.setDirection(DcMotor.Direction.REVERSE);

        //ballIntake = hardwareMap.dcMotor.get("ballIntake");
        //cannon = hardwareMap.dcMotor.get("cannon");

        line = hardwareMap.lightSensor.get("line");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");

        //csL = hardwareMap.colorSensor.get("csL");
        //csR = hardwareMap.colorSensor.get("csR");


        while (!isStarted()) {
            telemetry.addData("line: ", line.getLightDetected());
            telemetry.addData("ultrason: ", ultrason.getUltrasonicLevel());
            //telemetry.addData("csL: ", csL.argb());
            //telemetry.addData("csR: ", csR.argb());

            updateTelemetry(telemetry);
        }
        while (opModeIsActive()){
            //TODO: add shooting code
            strafe45(0.5, 135);
            doTilDistance(10.0);
            reset();
            driveStraight(0.5);
            doTilLine();
            reset();
            Thread.sleep(2000); //TODO: replace with line following, pushing, and moving backwards code
            driveStraight(0.5);
            doTilLine();
            reset();
            Thread.sleep(2000);
            //TODO: go diagonally until base
            idle();
        }


    }
    public void doTilLine() throws InterruptedException{ //waits until white line
        double lightStore;
        do {
            Thread.sleep(50);
            lightStore = line.getLightDetected();
        } while(lightStore > 0.12); //drives until white line
    }
    public void doTilDistance(double distance) throws InterruptedException{ //waits until robot is a certain distance from a thing in cm
        double ultrasonStore;
        do {
            Thread.sleep(50);
            ultrasonStore = ultrason.getUltrasonicLevel();
        } while(ultrasonStore != distance);
    }
    public void reset(){
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    }
    public void driveStraight(double power, int time) throws InterruptedException{
        topLeft.setPower(power);
        topRight.setPower(power);
        botLeft.setPower(power);
        botRight.setPower(power);
        Thread.sleep(time);
        reset();
    }
    public void driveStraight(double power){
        topLeft.setPower(power);
        topRight.setPower(power);
        botLeft.setPower(power);
        botRight.setPower(power);
    }
    public void strafeHorizontal (double power, int time) throws InterruptedException{
        topLeft.setPower(power);
        botLeft.setPower(-power);
        topRight.setPower(-power);
        botRight.setPower(power);
        Thread.sleep(time);
        reset();
    }
    public void strafeHorizontal (double power){
        topLeft.setPower(power);
        botLeft.setPower(-power);
        topRight.setPower(-power);
        botRight.setPower(power);
    }
    public void strafe45 (double power, int angle, double time) throws InterruptedException {
        switch(angle){
            case 45:
                topLeft.setPower(power);
                botRight.setPower(power);
                topRight.setPower(0);
                botLeft.setPower(0);
                break;
            case -45:
                topLeft.setPower(0);
                botRight.setPower(0);
                topRight.setPower(0 - power);
                botLeft.setPower(0 - power);
                break;
            case 135:
                topLeft.setPower(0);
                botRight.setPower(0);
                topRight.setPower(power);
                botLeft.setPower(power);
                break;
            case -135:
                topLeft.setPower(0 - power);
                botRight.setPower(0 - power);
                topRight.setPower(0);
                botLeft.setPower(0);
                break;
        }
        Thread.sleep((long) time);
        topLeft.setPower(0);
        botRight.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
    }
    public void strafe45 (double power, int angle) throws InterruptedException {
        switch (angle) {
            case 45:
                topLeft.setPower(power);
                botRight.setPower(power);
                topRight.setPower(0);
                botLeft.setPower(0);
                break;
            case -45:
                topLeft.setPower(0);
                botRight.setPower(0);
                topRight.setPower(0 - power);
                botLeft.setPower(0 - power);
                break;
            case 135:
                topLeft.setPower(0);
                botRight.setPower(0);
                topRight.setPower(power);
                botLeft.setPower(power);
                break;
            case -135:
                topLeft.setPower(0 - power);
                botRight.setPower(0 - power);
                topRight.setPower(0);
                botLeft.setPower(0);
                break;
        }
    }
}