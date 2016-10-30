package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Ben on 9/18/16.
 */
public class HutzAutonomousRedEchad extends LinearOpMode {
    DcMotor topRight, topLeft, bottomRight, bottomLeft;
    Servo buttonPush;
    ColorSensor beaconCheckRight, beaconCheckLeft;
    String team = "red";

    @Override
    public void runOpMode() throws InterruptedException {
        topRight = hardwareMap.dcMotor.get("topRight");
        topLeft = hardwareMap.dcMotor.get("topLeft");
        bottomRight = hardwareMap.dcMotor.get("bottomRight");
        bottomLeft = hardwareMap.dcMotor.get("bottomLeft");
        topRight.setDirection(DcMotor.Direction.REVERSE);
        bottomRight.setDirection(DcMotor.Direction.REVERSE);
        buttonPush = hardwareMap.servo.get("buttonPush");
        beaconCheckRight = hardwareMap.colorSensor.get("beaconCheckRight");
        beaconCheckLeft = hardwareMap.colorSensor.get("beaconCheckLeft");
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

    public void pushLeft() {
        buttonPush.setPosition(0.7); //TODO: CHANGE THROUGH DEBUGGING
    }

    public void pushRight() {
        buttonPush.setPosition(0.3); //TODO: CHANGE THROUGH DEBUGGING
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
