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
@Autonomous(name="Turn Left 90", group="Autonomous")
public class TurnLeftNinety extends LinearOpMode {
    DcMotor RF = null, LF = null, RB = null, LB = null, Lift = null;
    Servo LButtonPresser = null, RButtonPresser = null,/*ButtonPresser = null, LTouchServo = null, RTouchServo = null,*/
            LHolderServo = null, RHolderServo = null;
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
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
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

        telemetry.addData(">", "Gyro Calibrating. Do Not move!");
        telemetry.update();
        Gyro.resetZAxisIntegrator();
        Gyro.calibrate();

        // make sure the gyro is calibrated.
        if(!Gyro.isCalibrating()){
            sleep(200);
        } else {
            while (Gyro.isCalibrating()) {
                Thread.sleep(50);
                idle();
            }
        }


        heading = Gyro.getHeading();
        turnLeft(TURN_SPEED);
        do {
            if(!Gyro.isCalibrating()){
                sleep(200);
            } else {
                while (Gyro.isCalibrating()) {
                    Thread.sleep(50);
                    idle();
                }
            }
        } while(opModeIsActive() && ((heading <= 5) || (heading >= 285)));
        
        stopDrive();
        telemetry.addData("Heading:", heading);
        telemetry.update();
        sleep(1500);

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