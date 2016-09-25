package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;


@TeleOp(name="testBot: sensors", group="testBot")
//@Disabled //uncomment to disable, comment to enable
/*
 * Created by poop on 9/25/2016.
 */
public class Sensors extends OpMode {
    ColorSensor us;
    OpticalDistanceSensor you;
    UltrasonicSensor me;

    @Override
    public void init() {
        us = hardwareMap.colorSensor.get("us");
        you = hardwareMap.opticalDistanceSensor.get("you");
        me = hardwareMap.ultrasonicSensor.get("me");
    }
    @Override
    public void loop() {
        telemetry.addData("Color sensor R: ", us.red());
        telemetry.addData("Color sensor G: ", us.green());
        telemetry.addData("Color sensor B: ", us.blue());
        telemetry.addData("ODS sensor: ", you.getLightDetected());
        telemetry.addData("US sensor: ", me.getUltrasonicLevel());
    }
}
