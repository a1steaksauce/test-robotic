package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * hi iit is Eliezer in 01
 */
@Autonomous(name="Test for autonomous blue", group="3c")
public class HutzAutonomousTest extends HutzFuncMK2{

    public void runOpMode() throws InterruptedException{
        //currBotRight = hardwareMap.dcMotor.get("botRight");
        initializeHardware("any");
        while(!isStarted()){
            logToTelemetry();
        }
        while(opModeIsActive()) {
            doTilDistance(20);
            telemetry.addData("hi", "");
            updateTelemetry(telemetry);
        }
    }
}
