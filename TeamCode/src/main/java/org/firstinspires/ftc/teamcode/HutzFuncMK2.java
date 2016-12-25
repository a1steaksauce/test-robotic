package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * hi iit is Eliezer in 12
 */

public abstract class HutzFuncMK2 extends LinearOpMode {
    abstract public void runOpMode() throws InterruptedException;

    private String team;
    private final int BALL_INTAKE = 0;
    private final int BLANK = 1;
    private final int BALL_LAUNCH = 2;
    private final int BUTTON_PUSH = 3;
    private int side = BLANK;
    private DcMotor topLeft, topRight, botLeft, botRight;
    private DcMotor currTopLeft, currTopRight, currBotLeft, currBotRight;

    private DcMotor intake, release;
    private UltrasonicSensor ultrason;
    private Servo beaconLeft, beaconRight;
    private ColorSensor cs;
    private LightSensor line;

    public void initializeHardware(String teamName) {
        team = teamName;
        topLeft = hardwareMap.dcMotor.get("topLeft");
        topRight = hardwareMap.dcMotor.get("topRight");
        botLeft = hardwareMap.dcMotor.get("botLeft");
        botRight = hardwareMap.dcMotor.get("botRight");

        currTopLeft = topLeft;
        currTopRight = topRight;
        currBotLeft = botLeft;
        currBotRight = botRight;

        intake = hardwareMap.dcMotor.get("intake");
        release = hardwareMap.dcMotor.get("release");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");
        beaconLeft = hardwareMap.servo.get("beaconLeft");
        beaconRight = hardwareMap.servo.get("beaconRight");
        cs = hardwareMap.colorSensor.get("cs");
        line = hardwareMap.lightSensor.get("line");

        beaconLeft.setPosition(1);
        beaconRight.setPosition(1);
    } //works
    public void logToTelemetry() {
        telemetry.addData("line: ", line.getLightDetected());
        telemetry.addData("ultrason: ", ultrason.getUltrasonicLevel());
        telemetry.addData("cs: ", cs.argb());
        switch(side){
            case BALL_INTAKE:
                telemetry.addData("Ball intake side is front", ".");
                break;
            case BLANK:
                telemetry.addData("Blank side is front",".");
                break;
            case BALL_LAUNCH:
                telemetry.addData("Ball launcher is front",".");
                break;
            case BUTTON_PUSH:
                telemetry.addData("Button push is front",".");
                break;
        }

        updateTelemetry(telemetry);
    } //works
    public void resetWheels() {
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    } //works
    public void resetServos() {
        beaconLeft.setPosition(1);
        beaconRight.setPosition(1);
    } //works
    public void changeSide(int newSide) {
        switch(newSide){
            case BALL_INTAKE:
                currTopLeft = botRight;
                currTopRight = botLeft;
                currBotLeft = topRight;
                currBotRight = botRight;
                side = BALL_INTAKE;
                break;
            case BLANK:
                currTopLeft = topLeft;
                currTopRight = topRight;
                currBotLeft = botLeft;
                currBotRight = botRight;
                side = BLANK;
                break;
            case BALL_LAUNCH:
                currTopLeft = botLeft;
                currTopRight = topLeft;
                currBotLeft = botRight;
                currBotRight = topRight;
                side = BALL_LAUNCH;
                break;
            case BUTTON_PUSH:
                currTopLeft = topRight;
                currTopRight = botRight;
                currBotLeft = topLeft;
                currBotRight =  botRight;
                side = BUTTON_PUSH;
                break;
        }
    } // test
    public void doTilLine() throws InterruptedException { //waits until white line
        double lightStore;
        do {
            Thread.sleep(50);
            lightStore = line.getLightDetected();
        } while (lightStore > 0.12); //drives until white line
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
        } while (lightStore > 0.09 || (ultrasonStore > distance && ultrasonStore != 0)); //TODO: TEST!
    } //ready to test
    public void doTilPlatform () throws InterruptedException{
        double lightStore;
        do {
            Thread.sleep(50);
            lightStore = line.getLightDetected();
        } while (lightStore > 0.09);
    } //test
    public void driveForward(double power){
        currTopLeft.setPower(-power);
        currTopRight.setPower(power);
        currBotLeft.setPower(-power);
        currBotRight.setPower(power);
    } //test
    public void driveForward(double power, int time) throws InterruptedException{
        driveForward(power);
        sleep(time);
        resetWheels();
    } //test
    public void strafe180(double power) { //goes to the right with positive power //test
        currTopLeft.setPower(-power);
        currTopRight.setPower(-power);
        currBotLeft.setPower(power);
        currBotRight.setPower(power);
    } //test
    public void strafe180(double power, int time) throws InterruptedException{
        strafe180(power);
        Thread.sleep(time);
        resetWheels();
    } //test
    public void runIntake(boolean forwards){
        if(forwards) {
            intake.setPower(1);
        }else{
            intake.setPower(-1);
        }
    }
    public void stopIntake(){
        intake.setPower(0);
    }
    public void setServo(boolean left, double location){
        if(left){
            beaconLeft.setPosition(location);
        } else {
            beaconRight.setPosition(location);
        }
    }
}
