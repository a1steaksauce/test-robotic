package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.StrictMath.signum;

/**
 * Created by poop on 11/20/2016.
 */
@Autonomous(name="Hutz BEACON", group = "HutzAuto")
public class HutzBeacon extends LinearOpMode {
    DcMotor topLeft, topRight, botLeft, botRight;
    ColorSensor csL, csR;
    UltrasonicSensor ultrason;
    LightSensor lineDetector;
    final int TURN = 9;
    public void reset(){
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    }
    public void drive(double power, int time) throws InterruptedException{
        topLeft.setPower(power);
        topRight.setPower(power);
        botLeft.setPower(power);
        botRight.setPower(power);
        Thread.sleep(time);
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    }
    public void driveNS(double power){
        topLeft.setPower(power);
        topRight.setPower(power);
        botLeft.setPower(power);
        botRight.setPower(power);
    }
    public void turn(int deg) throws InterruptedException{
        topLeft.setPower(signum(deg)*0.5);
        topRight.setPower(-signum(deg)*0.5);
        botLeft.setPower(signum(deg)*0.5);
        botRight.setPower(-signum(deg)*0.5);
        Thread.sleep(Math.abs(deg)*TURN);
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    }
    public void logToTelemetry() throws InterruptedException{
        telemetry.addData("Ultr: ", ultrason.getUltrasonicLevel());
        telemetry.addData("Line: ", lineDetector.getLightDetected());
        telemetry.addData("csL: argb ", csL.argb());
        telemetry.addData("csR: argb ", csR.argb());

        updateTelemetry(telemetry);
    }
    public void runOpMode() throws InterruptedException {

        topRight = hardwareMap.dcMotor.get("topRight");
        topLeft = hardwareMap.dcMotor.get("topLeft");
        botRight = hardwareMap.dcMotor.get("botRight");
        botLeft = hardwareMap.dcMotor.get("botLeft");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        botLeft.setDirection(DcMotor.Direction.REVERSE);

        csR = hardwareMap.colorSensor.get("csR");
        csL = hardwareMap.colorSensor.get("csL");
        lineDetector = hardwareMap.lightSensor.get("lineDetector");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");

        while(!isStarted()){
            logToTelemetry();
        }

        while (opModeIsActive()){
            double bill; //light recording variable stage 1
            double bill2;//light recording variable stage 2
            double ultra_track;
            do {
                bill = lineDetector.getLightDetected();
                drive(0.2, 100);
            } while (bill < 0.11);
            //drive(0.2, 300); //HI ELIEZ!
            do {
                bill2 = lineDetector.getLightDetected();
                turn(-1);
            } while (bill2 < 0.11);
            //lineFollow(e); //TODO: implement
            if(csL.red() > csR.red()){
                topLeft.setPower(0.5);
                botLeft.setPower(0.5);
           } else {
                topRight.setPower(0.5);
                botRight.setPower(0.5);
           }
            Thread.sleep(750);
            driveNS(0);
            idle();
        }
    }
}
