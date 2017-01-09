package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Red buttons no shoot hutzbots", group="hutzAuto")
public class HutzOnlyButtonRedNS extends HutzFuncMK2 {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeHardware("red");
        resetServos();
        while (!isStarted()) {
            logToTelemetry(); //remove when no longer needed
        }

        while(opModeIsActive()) {
            //todo: write code here
            drive(3*Math.PI/4.0, 0.2);
            doTilDistance(20.0);
            resetWheels();
            sleep(500);
            driveForward(0.2);
            doTilLine();
            resetWheels();
            sleep(500);
            if(line.getLightDetected() < 0.11){
                drive(3*Math.PI/2, 0.1);
                doTilLine();
            }
            pushButton();
            driveForward(0.2);
            sleep(500);
            doTilLine();
            resetWheels();
            sleep(500);
            if(line.getLightDetected() < 0.11){
                drive(3*Math.PI/2, 0.1);
                doTilLine();
            }
            pushButton();
            while(!gamepad1.x){
                logToTelemetry();
            }
            requestOpModeStop();
            idle();
        }
    }
}
