package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aaronkbutler on 3/10/16.
 */
@TeleOp(name="Aleph Bots TeleOp Linear", group="TeleOp")
//@Disabled //uncomment to disable, comment to enable
public class AlephBotsTeleOpLinear extends LinearOpMode{
    //DECLARING MOTORS, SERVOS, and DRIVE
    AlephBotsRobotDriving drive;
    DcMotor RF, LF, RB, LB, Lift;
    Servo ButtonPresser, LTouchServo, RTouchServo;
    OpticalDistanceSensor GroundColorSensor;
    ColorSensor BeaconColorSensor;
    TouchSensor LTouchSensor;
    TouchSensor RTouchSensor;
    TouchSensor BeaconTouchSensor;

    private ElapsedTime runtime = new ElapsedTime();

    static final double     FORWARD_SPEED  = 0.5;
    static final double     FORWARD2_SPEED = 0.06;
    static final double     TURN_SPEED    = 0.2;
    static final double     END_TURN_SPEED    = 0.3;
    static final double     WHITE_THRESHOLD = 0.04;  // spans between 0.1 - 0.5 from dark to light

    String blueLevelS, redLevelS;
    int blueLevelI, redLevelI;
    Boolean runningAutonomous = false;

    @Override
    public void runOpMode() throws InterruptedException {
        //INITIALIZING EVERYTHING
        drive = new AlephBotsRobotDriving(hardwareMap.dcMotor.get("LF"), hardwareMap.dcMotor.get("LB"),
                hardwareMap.dcMotor.get("RF"), hardwareMap.dcMotor.get("RB"));
        ButtonPresser = hardwareMap.servo.get("ButtonPresser");

        LTouchServo = hardwareMap.servo.get("LTouchServo");
        RTouchServo = hardwareMap.servo.get("RTouchServo");
        GroundColorSensor = hardwareMap.opticalDistanceSensor.get("GroundColorSensor");
        BeaconColorSensor = hardwareMap.colorSensor.get("BeaconColorSensor");
        LTouchSensor = hardwareMap.touchSensor.get("LTouchSensor");
        RTouchSensor = hardwareMap.touchSensor.get("RTouchSensor");
        BeaconTouchSensor = hardwareMap.touchSensor.get("BeaconTouchSensor");

        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        //RF.setDirection(DcMotor.Direction.REVERSE);
        //RB.setDirection(DcMotor.Direction.REVERSE);
        ButtonPresser.setPosition(0.35);

        LTouchServo.setPosition(1.0);
        RTouchServo.setPosition(0.0);

        GroundColorSensor.enableLed(true);
        BeaconColorSensor.enableLed(true);

        waitForStart();

        while(opModeIsActive()){

            drive.tankDrive(gamepad1);
            drive.changeDriveMode(gamepad1.left_stick_button, gamepad1.right_stick_button);
            drive.changeDriveSpeed(gamepad1.dpad_up, gamepad1.dpad_down);

            //BUTTON PUSHER
            if (gamepad1.x && ButtonPresser.getPosition() < 1.0) {
                ButtonPresser.setPosition(ButtonPresser.getPosition() + 0.01);
            } else if (gamepad1.b && ButtonPresser.getPosition() > 0.0) {
                ButtonPresser.setPosition(ButtonPresser.getPosition() - 0.01);
            }
            //LIFT
            if (gamepad1.right_bumper) {
                Lift.setPower(0.7); //SEVEN TENTHS OF THE POWER
            } else if (gamepad1.left_bumper) {
                Lift.setPower(-0.1); //TENTH OF THE POWER
            }
            else {
                Lift.setPower(0);
            }
            //LINE FOLLOW
            if (gamepad2.dpad_left){
                lineUp(false);
            } else if (gamepad2.dpad_right){
                lineUp(true);
            }
            //LIFT TOUCH SENSOR SERVOS
            if (gamepad1.y){
                ButtonPresser.setPosition(0.35);
                RTouchServo.setPosition(0.0);
                LTouchServo.setPosition(1.0);
            }
            //TELEMETRY
            telemetry.addData("Aleph Bots Robot:", "Running!");
            telemetry.addData("Driving Mode:", drive.getDriveMode());
            telemetry.addData("Driving Speed:", drive.getDriveSpeed());
            telemetry.addData("ButtonPresser position:", ButtonPresser.getPosition());
            telemetry.addData("LTouchServo position:", LTouchServo.getPosition());
            telemetry.addData("RTouchServo position:", RTouchServo.getPosition());
            telemetry.update();
        }

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
    public void lineUp(Boolean right) throws InterruptedException {
        runningAutonomous = true;
        int step = 0;
        while (runningAutonomous == true) {
            if(step == 0) {
                driveStraight(FORWARD_SPEED);

                // run until the white line is seen OR the driver presses STOP;
                while (opModeIsActive() && (GroundColorSensor.getLightDetected() < WHITE_THRESHOLD)) {

                    // Display the light level while we are looking for the line
                    telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                    telemetry.update();

                    if(gamepad2.a){
                        runningAutonomous = false;
                        break;
                    }
                    idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop

                }

            // Stop all motors
                stopDrive();
            }
            if (step == 1) {
                LTouchServo.setPosition(0.16);
                RTouchServo.setPosition(0.72);
                driveStraight(FORWARD_SPEED / 2);

                sleep(150);
                stopDrive();
                sleep(150);
            }
            if(step == 2) {
                while (opModeIsActive() && GroundColorSensor.getLightDetected() < WHITE_THRESHOLD) {
                    if (!right) {
                        turnLeft(TURN_SPEED);
                    } else {
                        turnRight(TURN_SPEED);
                    }
                    telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                    telemetry.update();

                    if(gamepad2.a){
                        runningAutonomous = false;
                        break;
                    }
                }
            }
            if (step == 3) {
                runtime.reset();
                while (opModeIsActive() && !LTouchSensor.isPressed() && !RTouchSensor.isPressed() && runtime.seconds() < 2.0) {
                    if (GroundColorSensor.getLightDetected() >= WHITE_THRESHOLD) {
                        driveStraightLeft(FORWARD_SPEED / 2);
                        telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                        telemetry.update();
                    } else {
                        driveStraightRight(FORWARD_SPEED / 2);
                        telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                        telemetry.update();
                    }

                    if(gamepad2.a){
                        runningAutonomous = false;
                        break;
                    }

                }
                runningAutonomous = false;
            }
            step++;
        }
        runningAutonomous = false;
    }
}