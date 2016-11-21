package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import static java.lang.StrictMath.signum;

//import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by poop on 11/13/2016.
 */
@Autonomous(name="Hutzbots Autonomous: BALL ONLY", group="HutzAuto")
public class HutzBall extends LinearOpMode {
    final int TURN = 9
            ;
    DcMotor topLeft, topRight, botLeft, botRight;
    ColorSensor csL, csR;
    UltrasonicSensor ultrason;
    LightSensor lineDetector;
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
    public void runOpMode() throws InterruptedException{

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
        while (!isStarted()){
            logToTelemetry();
        }
        while (opModeIsActive()){
            waitForStart();
            logToTelemetry();
            while (ultrason.getUltrasonicLevel() == 0 || ultrason.getUltrasonicLevel() > 40) {
                drive(0.5, 250);
            }
            drive(1, 400);
            logToTelemetry();
            sleep(500);
            turn(-60);
            logToTelemetry();
            idle();
        }
    }
}
