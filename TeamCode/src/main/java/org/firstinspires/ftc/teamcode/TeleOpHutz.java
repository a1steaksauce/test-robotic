package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Eliezer Pearl on 9/9/2016.
 */
@TeleOp(name="mainBot: Teleop Tank")
//@Disabled //uncomment to disable, comment to enable
public class TeleOpHutz extends LinearOpMode {

    final double DEAD_ZONE = 0.05; //TODO: CHANGE THIS THROUGH DEBUGGING

    DcMotor topLeft; //all motor names are given based on location from a top down
    DcMotor topRight; //view of the robot
    DcMotor botLeft;
    DcMotor botRight;
    DcMotor intake;
//    DcMotor steve; //for raising the balls to the gearbox. Name was provided by Elan Ganz
    public void reset(){
        topLeft.setPower(0);
        topRight.setPower(0);
        botLeft.setPower(0);
        botRight.setPower(0);
    }
    public void logToTelemetry() throws InterruptedException{
        //telemetry.addData("Ultr: ", ultrason.getUltrasonicLevel());
        //telemetry.addData("Line: ", lineDetector.getLightDetected());
        telemetry.addData("csL: argb ", csL.argb());
        telemetry.addData("csR: argb ", csR.argb());

        updateTelemetry(telemetry);
    }
    /////////////////////////////////////////

    /////////////////////////////////////////
    UltrasonicSensor us;
    LightSensor lineDetector; //pointed at floor
    ColorSensor csL; //pointed at beacon
    ColorSensor csR;


    public TeleOpHutz() {  //just here, don't touch

    }


    @Override
    public void runOpMode() {  //Executed in a linear format
        topLeft = hardwareMap.dcMotor.get("topLeft");        //sets code motors to point to
        topRight = hardwareMap.dcMotor.get("topRight");       //actual ones. use the names
        botLeft = hardwareMap.dcMotor.get("botLeft");        //in config as the string vals
        botRight = hardwareMap.dcMotor.get("botRight");
        //us = hardwareMap.ultrasonicSensor.get("us");
        lineDetector = hardwareMap.lightSensor.get("lineDetector");
        csL = hardwareMap.colorSensor.get("csL");
        csR = hardwareMap.colorSensor.get("csR");
        //intake = hardwareMap.dcMotor.get("intake");

        topRight.setDirection(DcMotor.Direction.REVERSE);  //just for ease of programming since
        botRight.setDirection(DcMotor.Direction.REVERSE); //left motors are backward

        reset();
        while (!isStarted()) {
            //logToTelemetry();
        }
        while (opModeIsActive()) {
            /*
                Essentially, the following code makes sure that
                a joystick is being moved for real (hence
                the deadzone, since a stationary joystick can
                sometimes give nonzero values), and then sets a
                joystick's y value to the corresponding motors'
                power. So left joystick moves left motors,
                right joystick moves right motors.
            */
            if (Math.abs(gamepad1.left_stick_y) > DEAD_ZONE) {
                topLeft.setPower(gamepad1.left_stick_y);
                botLeft.setPower(gamepad1.left_stick_y);
            }
            if (Math.abs(gamepad1.right_stick_y) > DEAD_ZONE) {
                topRight.setPower(gamepad1.right_stick_y);
                botRight.setPower(gamepad1.right_stick_y);
            }
            if (Math.abs(gamepad1.right_stick_y) < DEAD_ZONE) {
                topRight.setPower(0);
                botRight.setPower(0);
            }
            if (Math.abs(gamepad1.left_stick_y) < DEAD_ZONE) {
                topLeft.setPower(0);
                botLeft.setPower(0);
            }
            //logToTelemetry();
        }
    }
}

