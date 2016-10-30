package org.firstinspires.ftc.teamcode;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by aaronkbutler on 10/21/16.
 */

@Autonomous(name="Aleph Bots Autonomous: Drive To Line", group="Autonomous")
public class AlephBotsAutonomousDriveToLine extends LinearOpMode{
    DcMotor RF = null, LF = null, RB = null, LB = null, Lift = null;
    Servo ButtonPresser = null;
    LightSensor GroundLightSensor =  null, BeaconLightSensor = null;

    private ElapsedTime runtime = new ElapsedTime();

    static final double     FORWARD_SPEED  = 0.6;
    static final double     TURN_SPEED    = 0.5;
    static final double     WHITE_THRESHOLD = 0.2;  // spans between 0.1 - 0.5 from dark to light

    @Override
    public void runOpMode() throws InterruptedException{
        ButtonPresser = hardwareMap.servo.get("ButtonPresser");
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        GroundLightSensor = hardwareMap.lightSensor.get("LightSensor");
        BeaconLightSensor = hardwareMap.lightSensor.get("BeaconLightSensor");
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

        ButtonPresser.setPosition(0.1);
        GroundLightSensor.enableLed(true);
        BeaconLightSensor.enableLed(true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        while (!isStarted()) {

            // Display the light level while we are waiting to start
            telemetry.addData("Light Level:", GroundLightSensor.getLightDetected());
            telemetry.update();
            idle();
        }

        /*
        //TESTING:
        driveStraight(1.0);
        moveServo(0.5);
        Thread.sleep(3000);

        turnLeft(1.0);
        moveServo(1.0);
        Thread.sleep(3000);

        turnRight(1.0);
        moveServo(0.0);
        Thread.sleep(3000);

        stopDrive();

        waitForStart();
        idle();
        */
        driveStraight(FORWARD_SPEED);

        // run until the white line is seen OR the driver presses STOP;
        while (opModeIsActive() && (GroundLightSensor.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level:",  GroundLightSensor.getLightDetected());
            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }

        // Stop all motors
        stopDrive();

        turnRight(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Spin: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
        while (opModeIsActive() && (GroundLightSensor.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level:",  GroundLightSensor.getLightDetected());
            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }

        // Stop all motors
        stopDrive();

        driveStraight(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
        stopDrive();
        if (BeaconLightSensor.getLightDetected() < 0.5) {
            ButtonPresser.setPosition(0.0);
        } else {
            ButtonPresser.setPosition(0.92);
        }
    }
    public void driveStraight(double power) {
        LF.setPower(power);
        LB.setPower(power);
        RF.setPower(power);
        RB.setPower(power);
    }
    public void turnLeft(double power) {
        LF.setPower(-power);
        LB.setPower(-power);
        RF.setPower(power);
        RB.setPower(power);
    }

    public void turnRight(double power) {
        LF.setPower(power);
        LB.setPower(power);
        RF.setPower(-power);
        RB.setPower(-power);
    }

    public void stopDrive() {
        LF.setPower(0);
        LB.setPower(0);
        RF.setPower(0);
        RB.setPower(0);
    }

    public void moveServo(double location) {
        ButtonPresser.setPosition(location);
    }
}
