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

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by aaronkbutler on 10/21/16.
 */

@Autonomous(name="Aleph Bots: Drive Straight", group="Autonomous")
public class AlephBotsAutonomousDriveStraight extends LinearOpMode{
    DcMotor RF = null, LF = null, RB = null, LB = null, Lift = null;
    Servo LButtonPresser = null, RButtonPresser = null, LHolderServo = null, RHolderServo = null;
    OpticalDistanceSensor GroundColorSensor =  null;
    ColorSensor BeaconColorSensor = null;
    GyroSensor Gyro = null;
    UltrasonicSensor UltraSensor;

    private ElapsedTime runtime = new ElapsedTime();
    static final double     FORWARD_SPEED  = 0.75;

    @Override
    public void runOpMode() throws InterruptedException{
        LButtonPresser = hardwareMap.servo.get("LButtonPresser");
        RButtonPresser = hardwareMap.servo.get("RButtonPresser");
        LHolderServo = hardwareMap.servo.get("LHolderServo");
        RHolderServo = hardwareMap.servo.get("RHolderServo");
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        GroundColorSensor = hardwareMap.opticalDistanceSensor.get("GroundColorSensor");
        BeaconColorSensor = hardwareMap.colorSensor.get("BeaconColorSensor");
        Gyro = hardwareMap.gyroSensor.get("Gyro");
        UltraSensor = hardwareMap.ultrasonicSensor.get("UltraSensor");

        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);

        LButtonPresser.setPosition(0.0);
        RButtonPresser.setPosition(1.0);

        LHolderServo.setPosition(0.9);
        RHolderServo.setPosition(0.2);

        GroundColorSensor.enableLed(true);
        BeaconColorSensor.enableLed(false);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        while (!isStarted()) {

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

        driveStraight(FORWARD_SPEED);
        runtime.reset();
        while(runtime.seconds() <= 5.0) {
            telemetry.addData("Driving", "Straight");

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
        stopDrive();

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
        //ButtonPresser.setPosition(location);
    }
}