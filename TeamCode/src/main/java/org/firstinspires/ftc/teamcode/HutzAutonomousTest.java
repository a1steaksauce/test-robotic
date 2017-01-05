package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * hi iit is Eliezer in 01
 */
@Autonomous(name="Test for autonomous", group="3c")
public class HutzAutonomousTest extends HutzFuncMK2{

    public void runOpMode() throws InterruptedException{
        //currBotRight = hardwareMap.dcMotor.get("botRight");
        initializeHardware("any");
        while(!isStarted()){
            logToTelemetry();
        }
        while(opModeIsActive()){
         //   currBotLeft.setPower(1);
         //   currBotRight.setPower(1);
            drive(3*Math.PI/4.0, 0.2);
            doTilDistance(20.0);
            resetWheels();
            sleep(500);

            driveForward(0.25);
            doTilLineWithCorrection();
            resetWheels();
            while(!gamepad1.x){
                logToTelemetry();
            }
            requestOpModeStop();
//            drive(3*Math.PI/4, 0.5);
//            sleep(500);
//            drive(Math.PI/4, 0.5);
//            sleep(500);
//            drive(7*Math.PI/4, 0.5);
//            sleep(500);
//            drive(5*Math.PI/4, 0.5);
//            resetWheels();
        }
    }
}
