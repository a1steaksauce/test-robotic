package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Eliezer Pearl on 9/9/2016.
 */
@TeleOp(name="Hutzbots Teleop crab")
//@Disabled //uncomment to disable, comment to enable
public class TeleOpHutzMK2 extends HutzFuncMK2 {

    final double DEAD_ZONE = 0.05; //TODO: CHANGE THIS THROUGH DEBUGGING
    @Override
    public void runOpMode(){
        initializeHardware("lol doesn't matter");
        while(!isStarted()){
            logToTelemetry();
        }
        while(opModeIsActive()){
            //TODO: add crab drive code
        }
    }
}

