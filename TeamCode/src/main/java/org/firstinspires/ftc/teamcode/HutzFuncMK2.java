package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;


/**
 * hi iit is Eliezer in 12
 */

public abstract class HutzFuncMK2 extends LinearOpMode {
    abstract public void runOpMode() throws InterruptedException;

    String team;
    final int BALL_INTAKE = 0;
    final int BLANK = 1;
    final int BALL_LAUNCH = 2;
    final int BUTTON_PUSH = 3;
    int side = BLANK;
    DcMotor topLeft, topRight, botLeft, botRight;
    DcMotor currTopLeft, currTopRight, currBotLeft, currBotRight;
    //DcMotor release;
    DcMotor intake;
   // private DcMotor intake, release;
    UltrasonicSensor ultrason;
    Servo beaconLeft, beaconRight;
    ColorSensor cs;
    LightSensor line;

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
        //release = hardwareMap.dcMotor.get("release");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");
        beaconLeft = hardwareMap.servo.get("beaconLeft");
        beaconRight = hardwareMap.servo.get("beaconRight");
        cs = hardwareMap.colorSensor.get("cs");
        line = hardwareMap.lightSensor.get("line");

        currTopLeft.setDirection(DcMotor.Direction.REVERSE);
        currTopRight.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        cs.enableLed(false);
        beaconLeft.setPosition(1);
        beaconRight.setPosition(0);
    } //works
    public void logToTelemetry() {
        telemetry.addData("line: ", line.getLightDetected());
        telemetry.addData("ultrason: ", ultrason.getUltrasonicLevel());
        telemetry.addData("cs b: ", cs.blue());
        telemetry.addData("cs r: ", cs.red());
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
        beaconRight.setPosition(0);
    } //works
    public void changeSide(int newSide) {
        currTopLeft.setDirection(DcMotor.Direction.FORWARD);
        currTopRight.setDirection(DcMotor.Direction.FORWARD);
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
        currTopLeft.setDirection(DcMotor.Direction.REVERSE);
        currTopRight.setDirection(DcMotor.Direction.REVERSE);
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
        drive(90, power);
    } //test
    public void driveForward(double power, int time) throws InterruptedException{
        driveForward(power);
        sleep(time);
        resetWheels();
    } //test
    public void strafe180(double power) { //goes to the right with positive power //test
        drive(0, power);
    } //test
    public void drive(double angle, double fullPow) {
        double workAngle = angle + Math.PI/4.0;
        currTopLeft.setPower(Math.sin(workAngle)*fullPow);
        currTopRight.setPower(Math.cos(workAngle)*fullPow);
        currBotLeft.setPower(Math.cos(workAngle)*fullPow);
        currBotRight.setPower(Math.sin(workAngle)*fullPow);
    } //radians
    public void spin(double power) {
        setMotors(power, power, -power, -power); //clockwise
    }
    public void strafe180(double power, int time) throws InterruptedException{
        strafe180(power);
        Thread.sleep(time);
        resetWheels();
    } //test
    public void setServo(boolean left){
        if(left){
            beaconLeft.setPosition(0.5);
        } else {
            beaconRight.setPosition(0.5);
        }
    }
    public void setMotors(double tl, double tr, double bl, double br){
        DcMotor[] list = {currTopLeft, currTopRight, currBotLeft, currBotRight};
        double[] powers = {tl, tr, bl, br};
        for(int i =0; i<4; i++){
            if(powers[i] != 180.4)
                list[i].setPower(powers[i]);
        }
    }
    public void setMotors(double all){
        setMotors(all, all, all, all);
    }
    public void pushButton() throws InterruptedException{
        if((cs.blue() > cs.red() && team.equals("blue")) || (cs.red()>cs.blue() && team.equals("red"))) {
            setServo(team.equals("red"));
        } else {
            setServo(team.equals("blue")); //opposite of above
        }
        drive((team.equals("red") ? Math.PI : 0), 0.2);
        Thread.sleep(500);
        resetWheels();
        Thread.sleep(200);
        drive((team.equals("blue") ? Math.PI : 0), 0.2);
        doTilDistance(8);
        resetWheels();
        resetServos();
    }
    public double[] colorClose(){
        int[] currColor = {cs.red(), cs.green(), cs.blue()};

        double closeToRed =   Math.sqrt( Math.pow(currColor[0]-255,2) + Math.pow(currColor[1], 2) + Math.pow(currColor[2], 2) );
        double closeToGreen = Math.sqrt( Math.pow(currColor[0],2) + Math.pow(currColor[1]-255, 2) + Math.pow(currColor[2], 2) );
        double closeToBlue =  Math.sqrt( Math.pow(currColor[0],2) + Math.pow(currColor[1], 2) + Math.pow(currColor[2]-255, 2) );
        double[] ret = {closeToRed, closeToGreen, closeToBlue};
        return ret;
    } //ready to test
  //  public void launch() throws InterruptedException{
  //      release.setPower(0.2);
  //      Thread.sleep(100);
  //      release.setPower(-0.2);
  //      Thread.sleep(100);
  //      release.setPower(0);
  //  }
    /*

    public double getAngle(){  //
        //let there be a gyro sensor called gyro
        //and a compass sensor called compass



    }
    public double integrateGyro(){
        final float timeStep = 0.01;
        gyro
    }


     */
/*   public void runIntake(boolean forwards){
        if(forwards) {
            intake.setPower(1);
        }else{
            intake.setPower(-1);
        }
}*/
/*public void stopIntake(){
    intake.setPower(0);
}*/

}
