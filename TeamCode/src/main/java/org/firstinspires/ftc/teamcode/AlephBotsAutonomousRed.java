package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by JacobDavoudgoleh on 1/7/17.
 */
@Autonomous(name="Aleph Bots: Red", group="Autonomous")
public class AlephBotsAutonomousRed extends LinearOpMode {
    DcMotor RF = null, LF = null, RB = null, LB = null, Lift = null, Shooter = null;
    Servo LButtonPresser = null, RButtonPresser = null,/*ButtonPresser = null, LTouchServo = null, RTouchServo = null,*/
            LHolderServo = null, RHolderServo = null, ShooterServo = null;
    OpticalDistanceSensor GroundColorSensor = null;
    ColorSensor BeaconColorSensor = null;
    //TouchSensor LTouchSensor = null;
    //TouchSensor RTouchSensor = null;
    GyroSensor Gyro = null;
    //TouchSensor BeaconTouchSensor = null;
    UltrasonicSensor UltraSensor;

    private ElapsedTime runtime = new ElapsedTime();
    static final long NEXT_BEACON_TURN_TIME = 2000;
    static final double FORWARD_SPEED = 0.75;
    static final double FORWARD2_SPEED = 0.06;
    static final double TURN_SPEED = 0.2;
    static final double END_TURN_SPEED = 0.3;
    static final double WHITE_THRESHOLD = 0.04;  // spans between 0.1 - 0.5 from dark to light
    static final int HIT_MAX = 100;

    int xVal, yVal, zVal = 0;     // Gyro rate Values
    int heading = 0;              // The Gyro integrated heading
    int angleZ = 0;
    boolean lastResetState = false;
    boolean curResetState = false;

    String blueLevelS, redLevelS;
    int blueLevelI, redLevelI;

    @Override
    public void runOpMode() throws InterruptedException {
        LButtonPresser = hardwareMap.servo.get("LButtonPresser");
        RButtonPresser = hardwareMap.servo.get("RButtonPresser");
        /*ButtonPresser = hardwareMap.servo.get("ButtonPresser");
        LTouchServo = hardwareMap.servo.get("LTouchServo");
        RTouchServo = hardwareMap.servo.get("RTouchServo");*/
        LHolderServo = hardwareMap.servo.get("LHolderServo");
        RHolderServo = hardwareMap.servo.get("RHolderServo");
        ShooterServo = hardwareMap.servo.get("ShooterServo");
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        Shooter = hardwareMap.dcMotor.get("Shooter");
        GroundColorSensor = hardwareMap.opticalDistanceSensor.get("GroundColorSensor");
        BeaconColorSensor = hardwareMap.colorSensor.get("BeaconColorSensor");
        //LTouchSensor = hardwareMap.touchSensor.get("LTouchSensor");
        //RTouchSensor = hardwareMap.touchSensor.get("RTouchSensor");
        Gyro = hardwareMap.gyroSensor.get("Gyro");
        UltraSensor = hardwareMap.ultrasonicSensor.get("UltraSensor");
        //BeaconTouchSensor = hardwareMap.touchSensor.get("BeaconTouchSensor");
        //UltraSensor = hardwareMap.ultrasonicSensor.get("UltraSensor");
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

        /*ButtonPresser.setPosition(0.35);
        LTouchServo.setPosition(1.0);
        RTouchServo.setPosition(0.0);*/

        LButtonPresser.setPosition(0.0);
        RButtonPresser.setPosition(1.0);

        LHolderServo.setPosition(0.9);
        RHolderServo.setPosition(0.2);

        ShooterServo.setPosition(0.0);

        GroundColorSensor.enableLed(true);
        BeaconColorSensor.enableLed(false);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        //telemetry.addData(">", "Gyro Calibrating. Do Not move!");
        telemetry.update();
        /*Gyro.calibrate();

        // make sure the gyro is calibrated.
        while (Gyro.isCalibrating())  {
            Thread.sleep(50);
            idle();
        }*/

        Gyro.calibrate();

        // make sure the gyro is calibrated.
        while (Gyro.isCalibrating()) {
            Thread.sleep(50);
            idle();
        }
        heading = Gyro.getHeading();

        while (!isStarted()) {
            telemetry.addData("Gyro: ", Gyro.getHeading());
            telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
            telemetry.addData("RGB Level:", BeaconColorSensor.argb());
            telemetry.addData("Red Value:", BeaconColorSensor.red());
            telemetry.addData("Green Value:", BeaconColorSensor.green());
            telemetry.addData("Blue Value:", BeaconColorSensor.blue());
            telemetry.addData("Distance:", UltraSensor.getUltrasonicLevel());
            telemetry.addData("Battery Level:", hardwareMap.voltageSensor.get("Lift Controller").getVoltage());

            telemetry.update();
            idle();
        }
        LHolderServo.setPosition(0.6);
        RHolderServo.setPosition(0.5);
        Boolean running = true;
        int step = 0;
        while (opModeIsActive() && running) {
            telemetry.addData("Step: ", step);
            telemetry.update();
            if (step == 0) { //Drive to line
                driveStraight(FORWARD_SPEED);

                // run until the white line is seen OR the driver presses STOP;
                while (opModeIsActive() && (GroundColorSensor.getLightDetected() < WHITE_THRESHOLD)) {

                    // Display the light level while we are looking for the line
                    telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                    telemetry.update();
                    idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop

                }

                // Stop all motors
                stopDrive();
            }
            if (step == 1) {  //Go forward
                //LTouchServo.setPosition(0.16);
                //RTouchServo.setPosition(0.72);
                driveStraight(FORWARD_SPEED / 2);

                sleep(150);
                stopDrive();
                sleep(150);
            }
            if (step == 2) { //Line up with the line
                while (opModeIsActive() && GroundColorSensor.getLightDetected() < WHITE_THRESHOLD) {

                    turnLeft(TURN_SPEED);

                    telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                    telemetry.update();
                }
            }
            if (step == 3) {
                int hitAmount = 0;
                runtime.reset();
                while (opModeIsActive() && hitAmount < HIT_MAX /*&& runtime.seconds() < 2.0*/) {
                    if (GroundColorSensor.getLightDetected() >= WHITE_THRESHOLD) {
                        driveStraightLeft(0.2);
                        if (UltraSensor.getUltrasonicLevel() <= 19.0 && UltraSensor.getUltrasonicLevel() != 0) {
                            hitAmount++;
                        } else {
                            hitAmount = 0;
                        }
                        telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                        telemetry.addData("Distance", UltraSensor.getUltrasonicLevel());
                        telemetry.addData("Turning", "Left");
                        telemetry.update();
                    } else {
                        driveStraightRight(0.2);
                        if (UltraSensor.getUltrasonicLevel() <= 19.0 && UltraSensor.getUltrasonicLevel() != 0) {
                            hitAmount++;
                        } else {
                            hitAmount = 0;
                        }
                        telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                        telemetry.addData("Distance", UltraSensor.getUltrasonicLevel());
                        //telemetry.addData("Distances Combined:", num);
                        telemetry.addData("Turning", "Right");
                        telemetry.update();
                    }

                }
                stopDrive();
                telemetry.addData("Hit Amount: ", hitAmount);
                telemetry.update();
            }
            if (step == 4) { //Reading Color and hitting
                runtime.reset();
                LButtonPresser.setPosition(0.1); //Board facing straight up
                RButtonPresser.setPosition(1.0); //Board facing straight up
                while (opModeIsActive() && (runtime.seconds() < 0.5)) {
                    telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
                    telemetry.addData("RGB Level:", BeaconColorSensor.argb());

                    telemetry.update();
                    idle();
                }
                redLevelI = BeaconColorSensor.red();
                blueLevelI = BeaconColorSensor.blue();
                telemetry.addData("Color", "Read");
                telemetry.update();
                runtime.reset();
                while (opModeIsActive() && (runtime.seconds() < 1.0)) {
                    telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
                    telemetry.addData("Red Level Calc:", redLevelI);
                    telemetry.addData("Blue Level Calc:", blueLevelI);
                    if (redLevelI > blueLevelI) {
                        telemetry.addData("Color found:", "Red");
                    } else if (blueLevelI > redLevelI) {
                        telemetry.addData("Color found:", "Blue");
                    } else {
                        telemetry.addData("Color found", "Undecided (Equal values)");
                    }
                    telemetry.addData("RGB Level:", BeaconColorSensor.argb());
                    telemetry.addData("Red Value:", BeaconColorSensor.red());
                    telemetry.addData("Green Value:", BeaconColorSensor.green());
                    telemetry.addData("Blue Value:", BeaconColorSensor.blue());
                    telemetry.update();
                    idle();
                }
                stopDrive();
                if (blueLevelI < redLevelI) {
                    LButtonPresser.setPosition(0.72); //Left Down Value
                    RButtonPresser.setPosition(1.0); //Right Up Value
                } else {
                    LButtonPresser.setPosition(0.0); //Left Up Value
                    RButtonPresser.setPosition(0.2); //Right Down Value
                }
                sleep(500);
                driveStraight(0.25);
                sleep(350);
                stopDrive();
                sleep(200);
                driveStraight(-0.2);
                sleep(800);
                stopDrive();
                //ButtonPresser.setPosition(0.35);
                LButtonPresser.setPosition(0.0);
                RButtonPresser.setPosition(1.0);

                Shooter.setPower(-1.0);
                sleep(1000);
                Shooter.setPower(0);

                ShooterServo.setPosition(0.7);
                sleep(500);
                ShooterServo.setPosition(0.0);
                sleep(150);

                Shooter.setPower(0.75);
                sleep(500);
                Shooter.setPower(0);
                sleep(150);

                Shooter.setPower(-1.0);
                sleep(1000);
                Shooter.setPower(0);

            }
            if (step == 5) { //Turn 90
                telemetry.addData(">", "Gyro Calibrating. Do Not move!");
                telemetry.update();
                Gyro.resetZAxisIntegrator();
                Gyro.calibrate();

                // make sure the gyro is calibrated.
                while (Gyro.isCalibrating()) {
                    Thread.sleep(50);
                    idle();
                }
                heading = Gyro.getHeading();
                turnRight(TURN_SPEED);
                while (opModeIsActive() && ((heading >= 355) ||  (heading <= 85))) {

                    // Display the light level while we are looking for the line
                    heading = Gyro.getHeading();
                    telemetry.addData("Heading:", heading);
                    telemetry.update();
                    idle(); // Always call idle() at the bottom of your while(opMakba kb4odeIsActive()) loop
                }
                stopDrive();
                telemetry.addData("Heading:", heading);
                telemetry.update();
                sleep(1500);
            }
            if (step == 6) { //Go until Line 2
                driveStraight(1);
                sleep(200);
                // run until the white line is seen OR the driver presses STOP;
                while (opModeIsActive() && (GroundColorSensor.getLightDetected() < WHITE_THRESHOLD)) {

                    // Display the light level while we are looking for the line
                    telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                    telemetry.update();
                    idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop

                }
                // Stop all motors//
                stopDrive();
            }
            if (step == 7) {
                driveStraight(FORWARD_SPEED / 2);

                sleep(150);//
                stopDrive();
                sleep(150);
            }
            if (step == 8) { //Line up with the line
                while (opModeIsActive() && GroundColorSensor.getLightDetected() < WHITE_THRESHOLD) {

                    turnLeft(TURN_SPEED);

                    telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                    telemetry.update();
                }
            }
            if (step == 9) {
                int hitAmount = 0;
                runtime.reset();
                while (opModeIsActive() && hitAmount < HIT_MAX /*&& runtime.seconds() < 2.0*/) {
                    if (GroundColorSensor.getLightDetected() >= WHITE_THRESHOLD) {
                        driveStraightLeft(0.2);
                        if (UltraSensor.getUltrasonicLevel() <= 19.0 && UltraSensor.getUltrasonicLevel() != 0) {
                            hitAmount++;
                        } else {
                            hitAmount = 0;
                        }
                        telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                        telemetry.addData("Distance", UltraSensor.getUltrasonicLevel());
                        telemetry.addData("Turning", "Left");
                        telemetry.update();
                    } else {
                        driveStraightRight(0.2);
                        if (UltraSensor.getUltrasonicLevel() <= 19.0 && UltraSensor.getUltrasonicLevel() != 0) {
                            hitAmount++;
                        } else {
                            hitAmount = 0;
                        }
                        telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
                        telemetry.addData("Distance", UltraSensor.getUltrasonicLevel());
                        //telemetry.addData("Distances Combined:", num);
                        telemetry.addData("Turning", "Right");
                        telemetry.update();
                    }

                }
                stopDrive();
                telemetry.addData("Hit Amount: ", hitAmount);
                telemetry.update();
            }
            if (step == 10) { //Reading Color and hitting
                runtime.reset();
                LButtonPresser.setPosition(0.1); //Board facing straight up
                RButtonPresser.setPosition(1.0); //Board facing straight up
                while (opModeIsActive() && (runtime.seconds() < 0.5)) {
                    telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
                    telemetry.addData("RGB Level:", BeaconColorSensor.argb());

                    telemetry.update();
                    idle();
                }
                redLevelI = BeaconColorSensor.red();
                blueLevelI = BeaconColorSensor.blue();
                telemetry.addData("Color", "Read");
                telemetry.update();
                runtime.reset();
                while (opModeIsActive() && (runtime.seconds() < 1.0)) {
                    telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
                    telemetry.addData("Red Level Calc:", redLevelI);
                    telemetry.addData("Blue Level Calc:", blueLevelI);
                    if (redLevelI > blueLevelI) {
                        telemetry.addData("Color found:", "Red");
                    } else if (blueLevelI > redLevelI) {
                        telemetry.addData("Color found:", "Blue");
                    } else {
                        telemetry.addData("Color found", "Undecided (Equal values)");
                    }
                    telemetry.addData("RGB Level:", BeaconColorSensor.argb());
                    telemetry.addData("Red Value:", BeaconColorSensor.red());
                    telemetry.addData("Green Value:", BeaconColorSensor.green());
                    telemetry.addData("Blue Value:", BeaconColorSensor.blue());
                    telemetry.update();
                    idle();
                }
                stopDrive();
                if (blueLevelI < redLevelI) {
                    LButtonPresser.setPosition(0.72); //Left Down Value
                    RButtonPresser.setPosition(1.0); //Right Up Value
                } else {
                    LButtonPresser.setPosition(0.0); //Left Up Value
                    RButtonPresser.setPosition(0.2); //Right Down Value
                }
                sleep(500);
                driveStraight(0.25);
                sleep(350);
                stopDrive();
                sleep(100);
                driveStraight(-0.15);
                sleep(200);
                stopDrive();
                stopDrive();
                running = false;
            }
            step++;
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
}