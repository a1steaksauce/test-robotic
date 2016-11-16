package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aaronkbutler on 10/21/16.
 */

@Autonomous(name="Aleph Bots Autonomous: Drive To Line Left", group="Autonomous")
public class AlephBotsAutonomousLineFollowTest extends LinearOpMode{
    DcMotor RF = null, LF = null, RB = null, LB = null;
    OpticalDistanceSensor TheGroundColorSensor =  null;
    TouchSensor BeaconTouchSensor = null;

    private ElapsedTime runtime = new ElapsedTime();

    double leftPower, rightPower, correction;
    final double PERFECT_COLOR_VALUE = 0.2;

    @Override
    public void runOpMode() throws InterruptedException{
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        TheGroundColorSensor = hardwareMap.opticalDistanceSensor.get("TheGroundColorSensor");
        BeaconTouchSensor = hardwareMap.touchSensor.get("BeaconTouchSensor");
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

        TheGroundColorSensor.enableLed(true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        while (!isStarted()) {

            // Display the light level while we are waiting to start
            telemetry.addData("Light Level:", TheGroundColorSensor.getLightDetected());
            telemetry.addData("Light Level Raw:", TheGroundColorSensor.getRawLightDetected());
            telemetry.addData("Light Level Raw Max:", TheGroundColorSensor.getRawLightDetectedMax());
            telemetry.update();
            idle();
        }
        while(opModeIsActive() && !BeaconTouchSensor.isPressed()) {
            // Get a correction
            correction = (PERFECT_COLOR_VALUE - TheGroundColorSensor.getLightDetected());

            // Sets the powers so they are no less than .075 and apply to correction
            if (correction <= 0) {
                rightPower = .075d - correction;
                leftPower = .075d;
            } else {
                rightPower = .075d;
                leftPower = .075d + correction;
            }

            // Sets the powers to the motors
            LB.setPower(leftPower);
            LF.setPower(leftPower);
            RB.setPower(rightPower);
            RF.setPower(rightPower);
        }


    }
    public void driveStraight(double power) {
        LF.setPower(-power);
        LB.setPower(-power);
        RF.setPower(-power);
        RB.setPower(-power);
    }
    public void turnLeft(double power) {
        LF.setPower(power);
        LB.setPower(power);
        RF.setPower(-power);
        RB.setPower(-power);
    }

    public void turnRight(double power) {
        LF.setPower(-power);
        LB.setPower(-power);
        RF.setPower(power);
        RB.setPower(power);
    }

    public void stopDrive() {
        LF.setPower(0);
        LB.setPower(0);
        RF.setPower(0);
        RB.setPower(0);
    }
}
