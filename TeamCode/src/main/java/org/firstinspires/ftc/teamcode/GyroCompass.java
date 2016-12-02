package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by poop on 12/1/2016.
 */
@TeleOp(name="gyrotest", group="whatever")
public class GyroCompass extends LinearOpMode {
    CompassSensor cs;
    GyroSensor gs;
    public void runOpMode(){
        gs = hardwareMap.gyroSensor.get("gs");
        cs = hardwareMap.compassSensor.get("cs");
        while(true){
            telemetry.addData("gyro x,y,z: ", String.valueOf(gs.rawX()) + "," + String.valueOf(gs.rawY()) + "," + String.valueOf(gs.rawZ()));
            telemetry.addData("compass: ", cs.getDirection());

        }
    }
}

