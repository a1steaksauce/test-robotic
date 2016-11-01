package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.En
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ben on 9/18/16.
 */
public class HutzAutonomousRedEchad extends LinearOpMode {
    DcMotor topRight, topLeft, bottomRight, bottomLeft;
    Servo buttonPush;
    ColorSensor beaconCheckRight, beaconCheckLeft, lineDetector;
    String team = "blue";

    @Override
    public void runOpMode() throws InterruptedException {
        topRight = hardwareMap.dcMotor.get("topRight");
        topLeft = hardwareMap.dcMotor.get("topLeft");
        bottomRight = hardwareMap.dcMotor.get("bottomRight");
        bottomLeft = hardwareMap.dcMotor.get("bottomLeft");
        topRight.setDirection(DcMotor.Direction.REVERSE);
        bottomRight.setDirection(DcMotor.Direction.REVERSE);
        topRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        buttonPush = hardwareMap.servo.get("buttonPush");
        beaconCheckRight = hardwareMap.colorSensor.get("beaconCheckRight");
        beaconCheckLeft = hardwareMap.colorSensor.get("beaconCheckLeft");
        lineDetector = hardwareMap.colorSensor.get("lineDetector");
    }

    public void forward(double power) {
        topRight.setPower(power);
        bottomRight.setPower(power);
        topLeft.setPower(power);
        bottomLeft.setPower(power);
    }

    public void left(double speed) {
        topRight.setPower(0 - speed);
        bottomRight.setPower(0 - speed);
        topLeft.setPower(speed);
        bottomLeft.setPower(speed);
    }

    public void right(double speed) {
        topRight.setPower(speed);
        bottomRight.setPower(speed);
        topLeft.setPower(0 - speed);
        bottomLeft.setPower(0 - speed);
    }

    public void backward(double power) {
        power = 0 - power;
        topRight.setPower(power);
        bottomRight.setPower(power);
        topLeft.setPower(power);
        bottomLeft.setPower(power);
    }

    public boolean lineDetect() { //should have range, not definite equals
        return lineDetector.argb() == 10000000; //TODO: CHANGE THROUGH DEBUGGING
    }

    public void pushLeft() { //TODO: CHANGE THROUGH DEBUGGING
        topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topLeft.setTargetPosition(180);
        bottomLeft.setTargetPosition(180);
        topLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        topLeft.setPower(1);
        bottomLeft.setPower(1);
        topLeft.setTargetPosition(360);
        bottomLeft.setTargetPosition(360);
        topLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        topLeft.setPower(-1);
        bottomLeft.setPower(-1);
        topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void pushRight() { //TODO: CHANGE THROUGH DEBUGGING
        topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topRight.setTargetPosition(180);
        bottomRight.setTargetPosition(180);
        topRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        topRight.setPower(1);
        bottomRight.setPower(1);
        topRight.setTargetPosition(360);
        bottomRight.setTargetPosition(360);
        topRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        topRight.setPower(-1);
        bottomRight.setPower(-1);
        topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void pushButton() {
        String valueRight = Integer.toString(beaconCheckRight.argb());
        String colorRight;
        if (valueRight != "") {
            if (Integer.valueOf(valueRight.substring(2, 4)) > Integer.valueOf(valueRight.substring(6, 8))) {
                colorRight = "red";
            } else {
                colorRight = "blue";
            }
        }
        String valueLeft = Integer.toString(beaconCheckLeft.argb());
        String colorLeft;
        if (valueLeft != "") {
            if (Integer.valueOf(valueLeft.substring(2, 4)) > Integer.valueOf(valueLeft.substring(6, 8))) {
                colorLeft = "red";
            } else {
                colorLeft = "blue";
            }
        }
        if (colorLeft == colorRight) {
            if (colorLeft != team) {
                pushLeft();
            }
        } else if (colorLeft.equals(team)) {
            pushLeft();
        } else if (colorRight.equals(team)) {
            pushRight();
        }
    }
}
