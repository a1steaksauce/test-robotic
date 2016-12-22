package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by poop on 12/16/2016.
 */

public abstract class HutzFunc extends LinearOpMode {
    abstract public void runOpMode() throws InterruptedException;

    String team;
    int andymark_tick = 1120;
    int tetrix_tick = 1440;
    DcMotor topLeft, topRight, botLeft, botRight;
    DcMotor intake, drawback, release;
    UltrasonicSensor ultrason;
    Servo beacon;
    ColorSensor cs;
    LightSensor line;
    double finalFloorVal;

    public void initializeHardware() {
        topLeft = hardwareMap.dcMotor.get("topLeft");
        topRight = hardwareMap.dcMotor.get("topRight");
        botLeft = hardwareMap.dcMotor.get("botLeft");
        botRight = hardwareMap.dcMotor.get("botRight");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        botLeft.setDirection(DcMotor.Direction.REVERSE);

        intake = hardwareMap.dcMotor.get("intake");
        drawback = hardwareMap.dcMotor.get("drawback");
        release = hardwareMap.dcMotor.get("release");

        drawback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        release.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        line = hardwareMap.lightSensor.get("lineDetector");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");

        beacon = hardwareMap.servo.get("beacon");

        cs = hardwareMap.colorSensor.get("cs");

        double floorVal1, floorVal2, floorVal3;
        floorVal1 = line.getLightDetected();
        try {Thread.sleep(15);} catch (InterruptedException e) {}
        floorVal2 = line.getLightDetected();
        try {Thread.sleep(15);} catch (InterruptedException e) {}
        floorVal3 = line.getLightDetected();
        finalFloorVal = (floorVal1 + floorVal2 + floorVal3) / 3;
    } //ready to test
    public void logToTelemetry() {
        telemetry.addData("line: ", line.getLightDetected());
        telemetry.addData("ultrason: ", ultrason.getUltrasonicLevel());
        telemetry.addData("cs: ", cs.argb());

        updateTelemetry(telemetry);
    } //ready to test
    public void initializeTeam(String _team){
        team = _team;
    } //red or blue. works
    public void reset() {
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    } //works
    public void doTilLine() throws InterruptedException{ //waits until white line
        double lightStore;
        do {
            Thread.sleep(50);
            lightStore = line.getLightDetected();
        } while (lightStore < finalFloorVal+0.051); //drives until white line
    } //ready to test
    public void doTilDistance (double distance) throws InterruptedException{ //waits until robot is a certain distance from a thing in cm
        double ultrasonStore;
        do {
            Thread.sleep(50);
            ultrasonStore = ultrason.getUltrasonicLevel();
        } while (ultrasonStore > distance && ultrasonStore != 0);
    } //ready to test
    public void doTilPlatform (double distance) throws InterruptedException{ //waits until robot is on platform
        double lightStore;
        double ultrasonStore;
        do {
            Thread.sleep(50);
            lightStore = line.getLightDetected();
            ultrasonStore = ultrason.getUltrasonicLevel();
        } while (lightStore > finalFloorVal-0.021 || (ultrasonStore > distance && ultrasonStore != 0)); //TODO: TEST!
    } //ready to test
    public void doTilPlatform () throws InterruptedException{
        double lightStore;
        do {
            Thread.sleep(50);
            lightStore = line.getLightDetected();
        } while (lightStore > finalFloorVal-0.021);
    }
    public void strafe180 (double power) {
        topLeft.setPower(power);
        topRight.setPower(-power);
        botLeft.setPower(-power);
        botRight.setPower(power);
        reset();
    } //ready to test
    public void strafe180 (double power, int time) throws InterruptedException {
        strafe180(power);
        Thread.sleep(time * 1000);
        reset();
    } //ready to test
    public void strafeRight45 (double power) {
        topLeft.setPower(power);
        botRight.setPower(power);
        botLeft.setPower(0);
        botRight.setPower(0);
        reset();
    } //ready to test
    public void strafeRight45 (double power, int time) throws InterruptedException {
        strafeRight45(power);
        Thread.sleep(time * 1000);
        reset();
    } //ready to test
    public void strafeLeft45 (double power) {
        topRight.setPower(power);
        botLeft.setPower(power);
        topLeft.setPower(0);
        botRight.setPower(0);
    } //ready to test
    public void strafeLeft45 (double power, int time) throws InterruptedException {
        strafeLeft45(power);
        Thread.sleep(time * 1000);
        reset();
    } //ready to test
    public void driveStraight (double power) {
        topRight.setPower(power);
        topLeft.setPower(power);
        botRight.setPower(power);
        botLeft.setPower(power);
    } //ready to test
    public void driveStraight (double power, int time) throws InterruptedException {
        driveStraight(power);
        Thread.sleep(time * 1000);
        reset();
    } //ready to test
    public void pushLeft() throws InterruptedException {
        beacon.setPosition(1); //TODO: CHANGE THROUGH DEBUGGING (THIS IS ALMOST DEFINITELY WRONG)
        Thread.sleep(250);
        beacon.setPosition(0.5);
    }  //ready to test
    public void pushRight() throws InterruptedException {
        beacon.setPosition(0); //TODO: CHANGE THROUGH DEBUGGING (THIS IS ALMOST DEFINITELY WRONG)
        Thread.sleep(250);
        beacon.setPosition(0.5);
    } //ready to test
    public void pushButton() throws InterruptedException{
        String color = threeDColor();
        if (color.equals(team)) {
            pushRight();
        } else {
            pushLeft();
        }
    } //ready to test
    public String threeDColor(){


        double[] close = colorClose();
        double currSmallest = close[0];
        for(double i : close) {
            if(i < currSmallest)
                currSmallest = i;
        }
        if(currSmallest == close[0]){ //red lowest
            telemetry.addData("red detected",0);
            updateTelemetry(telemetry);
            return "red";
        } else if(currSmallest == close[1]){ //green lowest
            telemetry.addData("green detected",0);
            updateTelemetry(telemetry);
            return "green";
        } else { //blue lowest
            telemetry.addData("blue detected",0);
            updateTelemetry(telemetry);
            return "blue";
        }
    } //ready to test
    public double[] colorClose(){
        int[] currColor = {cs.red(), cs.green(), cs.blue()};

        double closeToRed =   Math.sqrt( Math.pow(currColor[0]-255,2) + Math.pow(currColor[1], 2) + Math.pow(currColor[2], 2) );
        double closeToGreen = Math.sqrt( Math.pow(currColor[0],2) + Math.pow(currColor[1]-255, 2) + Math.pow(currColor[2], 2) );
        double closeToBlue =  Math.sqrt( Math.pow(currColor[0],2) + Math.pow(currColor[1], 2) + Math.pow(currColor[2]-255, 2) );
        double[] ret = {closeToRed, closeToGreen, closeToBlue};
        return ret;
    } //ready to test
    public boolean botOrNot () throws InterruptedException { //returns true if not robot
        double[] closeness = colorClose();
        //if see blue on red team or vice versa, probably a robot.
        if((closeness[0] < 20 && team.equals("red")) || (closeness[2] < 20 && team.equals("blue"))){//Todo: debug here for value
            return true;
        } else {
            return false;
        }
    } //ready to test
    public void shoot () throws InterruptedException{
        shootOnce();
        //eyy. now lets do it again
        intake.setPower(1);
        sleep(700);
        intake.setPower(0);
        //second ball in.
        shootOnce();
        //ez
    } //test me for fucks sake
    public void shootOnce () throws InterruptedException {
        drawback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drawback.setTargetPosition(2 * andymark_tick); //debug. may need to multiply by 60 to account for gearbox
        drawback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drawback.setPower(0.3); //slow for now.
        while (drawback.isBusy()) {
            //wait til motor drawn back
        }
        drawback.setPower(0);
        //we are now pulled back, hopefully!
        //now to release.
        release.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        release.setTargetPosition(tetrix_tick/4); //should work, debug tho
        release.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        release.setPower(0.3);
        while (release.isBusy()) {
            //other motor is releasing!
        }
        release.setPower(0);
        Thread.sleep(100);
        release.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        release.setTargetPosition(-tetrix_tick/4);
        release.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        release.setPower(-0.3);
        while (release.isBusy()) {
            //so this doesn't immediately turn off
        }
        release.setPower(0);
    }
}
