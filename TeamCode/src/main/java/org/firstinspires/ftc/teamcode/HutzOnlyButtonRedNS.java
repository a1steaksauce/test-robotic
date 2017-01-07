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
            drive(3*Math.PI/4.0, 0.75);
            doTilDistance(8); //cm
            resetWheels();
            for (int i = 0; i < 2; i++) {
                Thread.sleep(500);
                driveForward(0.5);
                doTilLine();
                resetWheels();
                Thread.sleep(500);
                pushButton();
            }
           // Thread.sleep(500);
           // drive(7*Math.PI/4.0, 0.75);
            //doTilPlatform(8);
           // resetWheels();
            idle();
        }
    }
}
