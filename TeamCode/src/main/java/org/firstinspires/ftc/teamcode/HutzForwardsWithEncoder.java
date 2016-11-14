package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by poop on 11/13/2016.
 */

public class HutzForwardsWithEncoder extends LinearOpMode {
    DcMotor topLeft, topRight, botLeft, botRight;
    ColorSensor csL, csR;
    UltrasonicSensor ultrason;
    LightSensor lineDetector;
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



    }
}
