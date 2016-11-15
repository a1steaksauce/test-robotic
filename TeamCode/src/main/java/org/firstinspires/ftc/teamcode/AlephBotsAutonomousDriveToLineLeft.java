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
public class AlephBotsAutonomousDriveToLineLeft extends LinearOpMode{
    DcMotor RF = null, LF = null, RB = null, LB = null, Lift = null;
    Servo ButtonPresser = null;
    OpticalDistanceSensor TheGroundColorSensor =  null;
    ColorSensor BeaconColorSensor = null;
    TouchSensor BeaconTouchSensor = null;

    private ElapsedTime runtime = new ElapsedTime();

    static final double     FORWARD_SPEED  = 0.6;
    static final double     FORWARD2_SPEED = 0.06;
    static final double     TURN_SPEED    = 0.3;
    static final double     WHITE_THRESHOLD = 0.04;  // spans between 0.1 - 0.5 from dark to light

    String blueLevelS, redLevelS;
    int blueLevelI, redLevelI;

    @Override
    public void runOpMode() throws InterruptedException{
        ButtonPresser = hardwareMap.servo.get("ButtonPresser");
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        TheGroundColorSensor = hardwareMap.opticalDistanceSensor.get("TheGroundColorSensor");
        BeaconColorSensor = hardwareMap.colorSensor.get("BeaconColorSensor");
        BeaconTouchSensor = hardwareMap.touchSensor.get("BeaconTouchSensor");
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

        ButtonPresser.setPosition(0.35);
        TheGroundColorSensor.enableLed(true);
        BeaconColorSensor.enableLed(true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        while (!isStarted()) {

            // Display the light level while we are waiting to start
            telemetry.addData("Light Level:", TheGroundColorSensor.getLightDetected());
            telemetry.addData("RGB Level:", BeaconColorSensor.argb());
            /*
            First two are alpha values
            3rd and 4th Red
            */
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
        while (opModeIsActive() && (TheGroundColorSensor.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level:",  TheGroundColorSensor.getLightDetected());
            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }

        // Stop all motors
        stopDrive();

        turnLeft(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Spin: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
        while (opModeIsActive() && (TheGroundColorSensor.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level:",  TheGroundColorSensor.getLightDetected());
            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }

        // Stop all motors
        stopDrive();

        driveStraight(FORWARD2_SPEED);

        while (opModeIsActive() && !BeaconTouchSensor.isPressed()) {
            telemetry.addData("Pressed?",BeaconTouchSensor.isPressed());
            telemetry.update();
            idle();
        }
        stopDrive();

        driveStraight(-FORWARD2_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.4)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
        stopDrive();

        turnLeft(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
        stopDrive();

        redLevelS = Integer.toString(BeaconColorSensor.argb());
        redLevelS = redLevelS.substring(2,3);
        redLevelI = Integer.valueOf(redLevelS);

        blueLevelS = Integer.toString(BeaconColorSensor.argb());
        blueLevelS = blueLevelS.substring(6,7);
        blueLevelI = Integer.valueOf(blueLevelS);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData("Red Level:", redLevelI);
            telemetry.addData("Blue Level:", blueLevelI);
            telemetry.update();
            idle();
        }

        driveStraight(-FORWARD2_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.4)) {
            telemetry.addData("Path", "Leg 5: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
        stopDrive();

        if (blueLevelI < redLevelI) {
            ButtonPresser.setPosition(0.68);
        } else {
            ButtonPresser.setPosition(0.07);
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

    public void moveServo(double location) {
        ButtonPresser.setPosition(location);
    }
}
