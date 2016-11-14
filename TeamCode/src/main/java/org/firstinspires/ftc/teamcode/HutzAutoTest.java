package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.LightSensor;

/**
 * Created by Ben on 11/6/16.
 */

@Autonomous(name="Hutzbots Autonomous: HutzAutoTest", group="HutzAuto")
public class HutzAutoTest extends LinearOpMode {
    DcMotor topLeft, topRight, botLeft, botRight;
    ColorSensor csL, csR;
    UltrasonicSensor ultrason;
    LightSensor lineDetector;
    //TODO: ADD PHONE CAMERA CAPABILITY

    //Amount of ticks encoder gives per inch
    final int inchInTicks = 115;
    int tLTick = 0;
    int tRTick = 0;
    int bLTick = 0;
    int bRTick = 0;
    //BEGIN FUNCTIONS

    //input INCHES!!!
    public void rest (long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e){}
    }
    public void forward (int dist, double pwr) {
        topLeft.setPower(pwr);
        topRight.setPower(pwr);
        botLeft.setPower(pwr);
        botRight.setPower(pwr);
        topRight.setTargetPosition(inchInTicks * dist + 0 /*tRTick*/);
        topLeft.setTargetPosition(inchInTicks * dist + 0/*tLTick*/);
        botLeft.setTargetPosition(inchInTicks * dist + 0/*bLTick*/);
        botRight.setTargetPosition(inchInTicks * dist + 0/*bRTick*/);
        //Add a slowdown in future
        tLTick = topLeft.getCurrentPosition();
        tRTick = topRight.getCurrentPosition();
        bLTick = botLeft.getCurrentPosition();
        bRTick = botRight.getCurrentPosition();
        rest(1000);
    }
    //input INCHES!!!
    public void backward (int dist, double pwr) {
        pwr = 0 - pwr;

        topLeft.setPower(pwr);
        topRight.setPower(pwr);
        botLeft.setPower(pwr);
        botRight.setPower(pwr);
        topRight.setTargetPosition(inchInTicks * dist /*+ tRTick*/);
        topLeft.setTargetPosition(inchInTicks * dist /*+ tLTick*/);
        botLeft.setTargetPosition(inchInTicks * dist /*+ bLTick*/);
        botRight.setTargetPosition(inchInTicks * dist /*+ bRTick*/);
        tLTick = topLeft.getCurrentPosition();
        tRTick = topRight.getCurrentPosition();
        bLTick = botLeft.getCurrentPosition();
        bRTick = botRight.getCurrentPosition();
        rest(1000);
    }

    //Amount of ticks per degree turn
    final int degreeInTicks = (int) Math.round((inchInTicks)*16/90);

    public void turnLeft (int angle, double pwr) {

        topLeft.setPower(pwr);
        botLeft.setPower(pwr);
        topRight.setPower(0 - pwr);
        botRight.setPower(0 - pwr);
        topLeft.setTargetPosition(angle * degreeInTicks);
        botLeft.setTargetPosition(angle * degreeInTicks);
        botRight.setTargetPosition(angle * degreeInTicks);
        topRight.setTargetPosition(angle * degreeInTicks);
        rest(1000);
        //FIX

    }
    public void turnRight (int angle, double pwr) {

        topLeft.setPower(0-pwr);
        botLeft.setPower(0-pwr);
        topRight.setPower(pwr);
        botRight.setPower(pwr);
        topLeft.setTargetPosition(angle * degreeInTicks);
        botLeft.setTargetPosition(angle * degreeInTicks);
        botRight.setTargetPosition(angle * degreeInTicks);
        topRight.setTargetPosition(angle * degreeInTicks);
        rest(1000);
        //FIX
    }
    @Override
    public void runOpMode() {

        topRight = hardwareMap.dcMotor.get("topRight");
        topLeft = hardwareMap.dcMotor.get("topLeft");
        botRight = hardwareMap.dcMotor.get("botRight");
        botLeft = hardwareMap.dcMotor.get("botLeft");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        botLeft.setDirection(DcMotor.Direction.REVERSE);

        //tLTick = topLeft.getCurrentPosition();
        //tRTick = topRight.getCurrentPosition();
        //bLTick = botLeft.getCurrentPosition();
        //bRTick = botRight.getCurrentPosition();
        //ENCODER SETUP:
        topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        botRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        botRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        botLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        botLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);



        csR = hardwareMap.colorSensor.get("csR");
        csL = hardwareMap.colorSensor.get("csL");
        lineDetector = hardwareMap.lightSensor.get("lineDetector");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");
        try{
        waitForStart();}
        catch (InterruptedException e){}

        //START CODE
        //One square on filed = 23"
       //while (opModeIsActive()) {
            forward(24, 0.75);

       //}
    }
}