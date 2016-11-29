package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by poop on 11/13/2016.
 */
@Autonomous(name="Hutzbots Autonomous: No encoder drive forward", group="HutzAuto")
public class HutzAutoNoEncoder extends LinearOpMode {
    DcMotor topLeft, topRight, botLeft, botRight;
    ColorSensor csL, csR;
    UltrasonicSensor ultrason;
    LightSensor lineDetector;
    DcMotor[] list;
    String team = "red"; //this can vary

    public void driveForward(double power, double secs){
        for (DcMotor e : list){
            e.setPower(power);
        }
        try {
            Thread.sleep((int) (secs * 1000));
        } catch (InterruptedException e){}
        for (DcMotor e : list){
            e.setPower(0);
        }
    }
    public void driveBackward(double power, double secs){
        for (DcMotor e : list){
            e.setPower(0 - power);
        }
        try {
            Thread.sleep((int) (secs * 1000));
        } catch (InterruptedException e){}
        for (DcMotor e : list){
            e.setPower(0);
        }
    }
    public void turnLeft(double power, double secs){
        topLeft.setPower(power);
        botLeft.setPower(power);
        topRight.setPower(0 - power);
        botRight.setPower(0 - power);
        try {
            Thread.sleep((int) (secs * 1000));
        } catch (InterruptedException e){}
        for (DcMotor e : list){
            e.setPower(0);
        }
    }
    public void turnRight(double power, double secs){
        power = 0 - power;
        topLeft.setPower(power);
        botLeft.setPower(power);
        topRight.setPower(0 - power);
        botRight.setPower(0 - power);
        try {
            Thread.sleep((int) (secs * 1000));
        } catch (InterruptedException e){}
        for (DcMotor e : list){
            e.setPower(0);
        }
    }
    public boolean isDist(double inches) {
        inches = inches * 20;
        if (ultrason.getUltrasonicLevel() > inches - 7 && ultrason.getUltrasonicLevel() < inches + 7) {
            return true;
        } else {
            return false;
        }
    }
    public void pushButton() {
        String valueRight = Integer.toString(csR.argb());
        String colorRight = "";
        if (valueRight != "") {
            if (Integer.valueOf(valueRight.substring(2, 4)) > Integer.valueOf(valueRight.substring(6, 8))) {
                colorRight = "red";
            } else {
                colorRight = "blue";
            }
        }
        String valueLeft = Integer.toString(csL.argb());
        String colorLeft = "";
        if (valueLeft != "") {
            if (Integer.valueOf(valueLeft.substring(2, 4)) > Integer.valueOf(valueLeft.substring(6, 8))) {
                colorLeft = "red";
            } else {
                colorLeft = "blue";
            }
        }
        if (colorLeft == colorRight) {
            if (colorLeft != team) {
                topLeft.setPower(1);
                botLeft.setPower(1);
                try {
                    Thread.sleep((int) (400));
                } catch (InterruptedException e){}
                topLeft.setPower(-1);
                botLeft.setPower(-1);
                try {
                    Thread.sleep((int) (400));
                } catch (InterruptedException e){}
                topLeft.setPower(0);
                botLeft.setPower(0);
            }
        } else if (colorLeft.equals(team)) {
            topLeft.setPower(1);
            botLeft.setPower(1);
            try {
                Thread.sleep((int) (400));
            } catch (InterruptedException e){}
            topLeft.setPower(-1);
            botLeft.setPower(-1);
            try {
                Thread.sleep((int) (400));
            } catch (InterruptedException e){}
            topLeft.setPower(0);
            botLeft.setPower(0);
        } else if (colorRight.equals(team)) {
            topRight.setPower(1);
            botRight.setPower(1);
            try {
                Thread.sleep((int) (400));
            } catch (InterruptedException e){}
            topRight.setPower(-1);
            botRight.setPower(-1);
            try {
                Thread.sleep((int) (400));
            } catch (InterruptedException e){}
            topRight.setPower(0);
            botRight.setPower(0);
        }
    }
    public void runOpMode() {

        topRight = hardwareMap.dcMotor.get("topRight");
        topLeft = hardwareMap.dcMotor.get("topLeft");
        botRight = hardwareMap.dcMotor.get("botRight");
        botLeft = hardwareMap.dcMotor.get("botLeft");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        botLeft.setDirection(DcMotor.Direction.REVERSE);

        DcMotor[] list = {topRight, topLeft, botLeft, botRight};

        csR = hardwareMap.colorSensor.get("csR");
        csL = hardwareMap.colorSensor.get("csL");
        lineDetector = hardwareMap.lightSensor.get("lineDetector");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");

        driveForward(1, 1.5);       //TODO: TEST AND DEBUG
        turnLeft(1, 0.5);
        while (!isDist(4.5)) {
            driveForward(1, 0.01);
        }
        pushButton();
    }
}
