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
public class HutzForwardsNoEncoder extends LinearOpMode {
    DcMotor topLeft, topRight, botLeft, botRight;
    ColorSensor csL, csR;
    UltrasonicSensor ultrason;
    LightSensor lineDetector;
    DcMotor[] list = {topRight, topLeft, botLeft, botRight};
    public void drive(double power, int time){
        for (DcMotor e : list){
            e.setPower(power);
        }
        try {
            Thread.sleep(time);
        } catch (InterruptedException e){}
        for (DcMotor e : list){
            e.setPower(0);
        }
    }
    public void runOpMode() {

        topRight = hardwareMap.dcMotor.get("topRight");
        topLeft = hardwareMap.dcMotor.get("topLeft");
        botRight = hardwareMap.dcMotor.get("botRight");
        botLeft = hardwareMap.dcMotor.get("botLeft");

        topLeft.setDirection(DcMotor.Direction.REVERSE);
        botLeft.setDirection(DcMotor.Direction.REVERSE);

        csR = hardwareMap.colorSensor.get("csR");
        csL = hardwareMap.colorSensor.get("csL");
        lineDetector = hardwareMap.lightSensor.get("lineDetector");
        ultrason = hardwareMap.ultrasonicSensor.get("ultrason");
        

        for (DcMotor e : list){
            e.setPower(0.5);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e){}
    }
}
