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

@Autonomous(name="Aleph Bots Autonomous: Blue Right 1", group="Autonomous")
public class AlephBotsAutonomousLineFollowBlueRight1 extends LinearOpMode{
    DcMotor RF = null, LF = null, RB = null, LB = null, Lift = null;
    Servo ButtonPresser = null, LTouchServo = null, RTouchServo = null;
    OpticalDistanceSensor GroundColorSensor =  null;
    ColorSensor BeaconColorSensor = null;
    TouchSensor LTouchSensor = null;
    TouchSensor RTouchSensor = null;
    TouchSensor BeaconTouchSensor = null;

    private ElapsedTime runtime = new ElapsedTime();

    static final double     FORWARD_SPEED  = 0.5;
    static final double     FORWARD2_SPEED = 0.06;
    static final double     TURN_SPEED    = 0.2;
    static final double     END_TURN_SPEED    = 0.3;
    static final double     WHITE_THRESHOLD = 0.04;  // spans between 0.1 - 0.5 from dark to light

    String blueLevelS, redLevelS;
    int blueLevelI, redLevelI;

    @Override
    public void runOpMode() throws InterruptedException{
        ButtonPresser = hardwareMap.servo.get("ButtonPresser");
        LTouchServo = hardwareMap.servo.get("LTouchServo");
        RTouchServo = hardwareMap.servo.get("RTouchServo");
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        GroundColorSensor = hardwareMap.opticalDistanceSensor.get("GroundColorSensor");
        BeaconColorSensor = hardwareMap.colorSensor.get("BeaconColorSensor");
        LTouchSensor = hardwareMap.touchSensor.get("LTouchSensor");
        RTouchSensor = hardwareMap.touchSensor.get("RTouchSensor");
        BeaconTouchSensor = hardwareMap.touchSensor.get("BeaconTouchSensor");
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

        ButtonPresser.setPosition(0.35);
        LTouchServo.setPosition(1.0);
        RTouchServo.setPosition(0.0);

        GroundColorSensor.enableLed(true);
        BeaconColorSensor.enableLed(true);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        while (!isStarted()) {

            // Display the light levels while we are waiting to start
            telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
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
        while (opModeIsActive() && (GroundColorSensor.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level:",  GroundColorSensor.getLightDetected());
            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }

        // Stop all motors
        stopDrive();
        driveStraight(FORWARD_SPEED/2);
        sleep(150);
        stopDrive();
        sleep(150);
        while (opModeIsActive() && GroundColorSensor.getLightDetected() < WHITE_THRESHOLD){
            turnRight(TURN_SPEED);
            telemetry.addData("Light Level:",  GroundColorSensor.getLightDetected());
            telemetry.update();
        }
        while (opModeIsActive() && !BeaconTouchSensor.isPressed()) {
            if(GroundColorSensor.getLightDetected() >= WHITE_THRESHOLD){
                driveStraightLeft(FORWARD_SPEED/2);
                telemetry.addData("Light Level:",  GroundColorSensor.getLightDetected());
                telemetry.update();
            } else {
                driveStraightRight(FORWARD_SPEED/2);
                telemetry.addData("Light Level:",  GroundColorSensor.getLightDetected());
                telemetry.update();
            }
        }

        driveStraight(-FORWARD2_SPEED);
        sleep(400);
        stopDrive();

        ButtonPresser.setPosition(0.55);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData("RGB Level:", BeaconColorSensor.argb());
            telemetry.update();
            idle();
        }

        redLevelS = Integer.toString(BeaconColorSensor.argb());
        redLevelS = redLevelS.substring(2,4);
        redLevelI = Integer.valueOf(redLevelS);

        blueLevelS = Integer.toString(BeaconColorSensor.argb());
        blueLevelS = blueLevelS.substring(6,8);
        blueLevelI = Integer.valueOf(blueLevelS);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData("Red Level:", redLevelI);
            telemetry.addData("Blue Level:", blueLevelI);
            telemetry.update();
            idle();
        }

        driveStraight(-FORWARD2_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Path", "Leg 5: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
        stopDrive();

        LTouchServo.setPosition(0.16);
        RTouchServo.setPosition(0.72);
        ButtonPresser.setPosition(0.35);

        driveStraight(FORWARD2_SPEED);
        while (opModeIsActive() && !LTouchSensor.isPressed() && !RTouchSensor.isPressed()) {
            telemetry.addData("L Pressed?",LTouchSensor.isPressed());
            telemetry.addData("R Pressed?",RTouchSensor.isPressed());
            telemetry.update();
            idle();
        }
        stopDrive();
        runtime.reset();
        while(runtime.seconds() < 2.0) {
            if (LTouchSensor.isPressed()) {
                driveStraightLeft(END_TURN_SPEED);
                while (opModeIsActive() && !RTouchSensor.isPressed()) {
                    telemetry.addData("Turning Left?", "Yes");
                    telemetry.addData("L Pressed?", LTouchSensor.isPressed());
                    telemetry.addData("R Pressed?", RTouchSensor.isPressed());
                    telemetry.update();
                    idle();
                }
                stopDrive();
            } else if (RTouchSensor.isPressed()) {
                driveStraightRight(END_TURN_SPEED);
                while (opModeIsActive() && !LTouchSensor.isPressed()) {
                    telemetry.addData("Turning Right?", "Yes");
                    telemetry.addData("L Pressed?", LTouchSensor.isPressed());
                    telemetry.addData("R Pressed?", RTouchSensor.isPressed());
                    telemetry.update();
                    idle();
                }
                stopDrive();
            }
        }
        if (blueLevelI > redLevelI) {
            ButtonPresser.setPosition(0.68);
        } else {
            ButtonPresser.setPosition(0.00);
        }

        //LTouchServo.setPosition(1.0);
        //RTouchServo.setPosition(0.0);
        sleep(1000);
        idle();
    }
    public void driveStraight(double power) {
        LF.setPower(-power);
        LB.setPower(-power);
        RF.setPower(-power);
        RB.setPower(-power);
    }
    public void driveStraightLeft(double power) {
        //LF.setPower(-(power/3.0));
        //LB.setPower(-(power/3.0));
        LF.setPower(0);
        LB.setPower(0);
        RF.setPower(-power);
        RB.setPower(-power);
    }
    public void driveStraightRight(double power) {
        LF.setPower(-power);
        LB.setPower(-power);
        //RF.setPower(-(power/3.0));
        //RB.setPower(-(power/3.0));
        RF.setPower(0);
        RB.setPower(0);
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