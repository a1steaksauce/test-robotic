package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aaronkbutler on 10/21/16.
 */

@Autonomous(name="Aleph Bots: Blue Right Short 2", group="Autonomous")
public class AlephBotsAutonomousBlue extends LinearOpMode{
    DcMotor RF = null, LF = null, RB = null, LB = null, Lift = null;
    Servo ButtonPresser = null, LTouchServo = null, RTouchServo = null, LHolderServo = null, RHolderServo = null;
    OpticalDistanceSensor GroundColorSensor =  null;
    ColorSensor BeaconColorSensor = null;
    TouchSensor LTouchSensor = null;
    TouchSensor RTouchSensor = null;
    GyroSensor Gyro = null;
    //TouchSensor BeaconTouchSensor = null;
    //UltrasonicSensor UltraSensor = null;

    private ElapsedTime runtime = new ElapsedTime();
    static final long     NEXT_BEACON_TURN_TIME = 2000;
    static final double     FORWARD_SPEED  = 0.75;
    static final double     FORWARD2_SPEED = 0.06;
    static final double     TURN_SPEED    = 0.2;
    static final double     END_TURN_SPEED    = 0.3;
    static final double     WHITE_THRESHOLD = 0.04;  // spans between 0.1 - 0.5 from dark to light

    int xVal, yVal, zVal = 0;     // Gyro rate Values
    int heading = 0;              // The Gyro integrated heading
    int angleZ = 0;
    boolean lastResetState = false;
    boolean curResetState  = false;

    String blueLevelS, redLevelS;
    int blueLevelI, redLevelI;

    @Override
    public void runOpMode() throws InterruptedException{
        ButtonPresser = hardwareMap.servo.get("ButtonPresser");
        LTouchServo = hardwareMap.servo.get("LTouchServo");
        RTouchServo = hardwareMap.servo.get("RTouchServo");
        LHolderServo = hardwareMap.servo.get("LHolderServo");
        RHolderServo = hardwareMap.servo.get("RHolderServo");
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        GroundColorSensor = hardwareMap.opticalDistanceSensor.get("GroundColorSensor");
        BeaconColorSensor = hardwareMap.colorSensor.get("BeaconColorSensor");
        LTouchSensor = hardwareMap.touchSensor.get("LTouchSensor");
        RTouchSensor = hardwareMap.touchSensor.get("RTouchSensor");
        Gyro = hardwareMap.gyroSensor.get("Gyro");
        //BeaconTouchSensor = hardwareMap.touchSensor.get("BeaconTouchSensor");
        //UltraSensor = hardwareMap.ultrasonicSensor.get("UltraSensor");
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

        ButtonPresser.setPosition(0.35);
        LTouchServo.setPosition(1.0);
        RTouchServo.setPosition(0.0);

        LHolderServo.setPosition(1.0);
        RHolderServo.setPosition(0.0);

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
        while (Gyro.isCalibrating())  {
            Thread.sleep(50);
            idle();
        }
        heading = Gyro.getHeading();

        while (!isStarted()) {


            /*telemetry.addData(">", "Gyro Calibrated.  Press Start.");
            xVal = Gyro.rawX();
            yVal = Gyro.rawY();
            zVal = Gyro.rawZ();

            // get the heading info.
            // the Modern Robotics' gyro sensor keeps
            // track of the current heading for the Z axis only.
            heading = Gyro.getHeading();
            //angleZ  = Gyro.getIntegratedZValue();

            telemetry.addData("0", "Heading %03d", heading);
            //telemetry.addData("1", "Int. Ang. %03d", angleZ);
            telemetry.addData("2", "X av. %03d", xVal);
            telemetry.addData("3", "Y av. %03d", yVal);
            telemetry.addData("4", "Z av. %03d", zVal);
            */
            telemetry.addData("Light Level:", GroundColorSensor.getLightDetected());
            telemetry.addData("RGB Level:", BeaconColorSensor.argb());
            telemetry.addData("Red Value:", BeaconColorSensor.red());
            telemetry.addData("Green Value:", BeaconColorSensor.green());
            telemetry.addData("Blue Value:", BeaconColorSensor.blue());
            //telemetry.addData("Distance:", UltraSensor.getUltrasonicLevel());
            telemetry.addData("Battery Level:", hardwareMap.voltageSensor.get("Lift Controller").getVoltage());
            /*
            First two are alpha values
            3rd and 4th Red
            */
            /*
            if(BeaconColorSensor.argb() != 0) {

                redLevelS = Integer.toString(BeaconColorSensor.argb());
                redLevelS = redLevelS.substring(2, 4);
                redLevelI = Integer.valueOf(redLevelS);

                blueLevelS = Integer.toString(BeaconColorSensor.argb());
                blueLevelS = blueLevelS.substring(6, 8);
                blueLevelI = Integer.valueOf(blueLevelS);
                telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
                telemetry.addData("Red Level:", redLevelI);
                telemetry.addData("Blue Level:", blueLevelI);
            }
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
        LHolderServo.setPosition(0.65);
        RHolderServo.setPosition(0.3);

        driveStraight(1);

        // run until the white line is seen OR the driver presses STOP;
        runtime.reset();
        while (opModeIsActive() && (GroundColorSensor.getLightDetected() < WHITE_THRESHOLD) && runtime.seconds() < 3.0) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level:",  GroundColorSensor.getLightDetected());
            telemetry.addData("Runtime:", runtime.seconds());
            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }

        // Stop all motors
        stopDrive();

        LTouchServo.setPosition(0.16); //Prepare the touch servos for the touch sensors
        RTouchServo.setPosition(0.72);

        driveStraight(FORWARD_SPEED/2);
        sleep(150);
        stopDrive();
        sleep(150);
        while (opModeIsActive() && GroundColorSensor.getLightDetected() < WHITE_THRESHOLD){
            turnRight(TURN_SPEED);
            telemetry.addData("Light Level:",  GroundColorSensor.getLightDetected());
            telemetry.update();
        }
        runtime.reset();
        while (opModeIsActive() && !LTouchSensor.isPressed() && !RTouchSensor.isPressed() && runtime.seconds() < 3.0) {
            telemetry.addData("Runtime:", runtime.seconds());
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
        stopDrive();
        runtime.reset();
        while(runtime.seconds() < 1.0) {
            if (LTouchSensor.isPressed()) {
                driveStraightLeft(END_TURN_SPEED);
                if (opModeIsActive() && !RTouchSensor.isPressed()) {
                    telemetry.addData("Turning Left?", "Yes");
                    telemetry.addData("L Pressed?", LTouchSensor.isPressed());
                    telemetry.addData("R Pressed?", RTouchSensor.isPressed());
                    telemetry.update();
                    idle();
                }
                stopDrive();
            } else if (RTouchSensor.isPressed()) {
                driveStraightRight(END_TURN_SPEED);
                if (opModeIsActive() && !LTouchSensor.isPressed()) {
                    telemetry.addData("Turning Right?", "Yes");
                    telemetry.addData("L Pressed?", LTouchSensor.isPressed());
                    telemetry.addData("R Pressed?", RTouchSensor.isPressed());
                    telemetry.update();
                    idle();
                }
                stopDrive();
            }
            telemetry.addData("Runtime:", runtime.seconds());
        }
        stopDrive();

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData("RGB Level:", BeaconColorSensor.argb());

            telemetry.update();
            idle();
        }
        /*
        redLevelS = Integer.toString(BeaconColorSensor.argb());
        redLevelS = redLevelS.substring(2,4);
        redLevelI = Integer.valueOf(redLevelS);

        blueLevelS = Integer.toString(BeaconColorSensor.argb());
        blueLevelS = blueLevelS.substring(6,8);
        blueLevelI = Integer.valueOf(blueLevelS);
        */

        redLevelI = BeaconColorSensor.red();
        blueLevelI = BeaconColorSensor.blue();

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData("Red Level Calc:", redLevelI);
            telemetry.addData("Blue Level Calc:", blueLevelI);
            if(redLevelI > blueLevelI) {
                telemetry.addData("Color found:", "Red");
            } else if(blueLevelI > redLevelI) {
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
        if (blueLevelI > redLevelI) {
            ButtonPresser.setPosition(0.68);
        } else {
            ButtonPresser.setPosition(0.00);
        }
        sleep(1000);
        ButtonPresser.setPosition(0.35);

        driveStraight(-FORWARD2_SPEED);
        sleep(2350);
        stopDrive();

        LTouchServo.setPosition(1.0);
        RTouchServo.setPosition(0.0);

        telemetry.addData(">", "Gyro Calibrating. Do Not move!");
        telemetry.update();
        Gyro.calibrate();

        // make sure the gyro is calibrated.
        while (Gyro.isCalibrating())  {
            Thread.sleep(50);
            idle();
        }
        heading = Gyro.getHeading();
        turnLeft(TURN_SPEED);
        while (opModeIsActive() && ((heading <= 5) || (heading >= 285))) {

            // Display the light level while we are looking for the line
            heading = Gyro.getHeading();
            telemetry.addData("Heading:",  heading);
            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
        stopDrive();

        driveStraight(1);

        // run until the white line is seen OR the driver presses STOP;
        while (opModeIsActive() && (GroundColorSensor.getLightDetected() < WHITE_THRESHOLD)) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level:",  GroundColorSensor.getLightDetected());
            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }

        // Stop all motors
        stopDrive();

        LTouchServo.setPosition(0.16); //Prepare the touch servos for the touch sensors
        RTouchServo.setPosition(0.72);

        driveStraight(FORWARD_SPEED/2);
        sleep(150);
        stopDrive();
        sleep(150);
        while (opModeIsActive() && GroundColorSensor.getLightDetected() < WHITE_THRESHOLD){
            turnRight(TURN_SPEED);
            telemetry.addData("Light Level:",  GroundColorSensor.getLightDetected());
            telemetry.update();
        }
        while (opModeIsActive() && !LTouchSensor.isPressed() && !RTouchSensor.isPressed()) {
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
        stopDrive();
        runtime.reset();
        while(runtime.seconds() < 1.0) {
            if (LTouchSensor.isPressed()) {
                driveStraightLeft(END_TURN_SPEED);
                if (opModeIsActive() && !RTouchSensor.isPressed()) {
                    telemetry.addData("Turning Left?", "Yes");
                    telemetry.addData("L Pressed?", LTouchSensor.isPressed());
                    telemetry.addData("R Pressed?", RTouchSensor.isPressed());
                    telemetry.update();
                    idle();
                }
                stopDrive();
            } else if (RTouchSensor.isPressed()) {
                driveStraightRight(END_TURN_SPEED);
                if (opModeIsActive() && !LTouchSensor.isPressed()) {
                    telemetry.addData("Turning Right?", "Yes");
                    telemetry.addData("L Pressed?", LTouchSensor.isPressed());
                    telemetry.addData("R Pressed?", RTouchSensor.isPressed());
                    telemetry.update();
                    idle();
                }
                stopDrive();
            }
        }
        stopDrive();

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData("RGB Level:", BeaconColorSensor.argb());
            telemetry.update();
            idle();
        }
        /*
        redLevelS = Integer.toString(BeaconColorSensor.argb());
        redLevelS = redLevelS.substring(2,4);
        redLevelI = Integer.valueOf(redLevelS);

        blueLevelS = Integer.toString(BeaconColorSensor.argb());
        blueLevelS = blueLevelS.substring(6,8);
        blueLevelI = Integer.valueOf(blueLevelS);
        */

        redLevelI = BeaconColorSensor.red();
        blueLevelI = BeaconColorSensor.blue();

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.addData("Red Level Calc:", redLevelI);
            telemetry.addData("Blue Level Calc:", blueLevelI);
            if(redLevelI > blueLevelI) {
                telemetry.addData("Color found:", "Red");
            } else if(blueLevelI > redLevelI) {
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
        if (blueLevelI > redLevelI) {
            ButtonPresser.setPosition(0.68);
        } else {
            ButtonPresser.setPosition(0.00);
        }
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