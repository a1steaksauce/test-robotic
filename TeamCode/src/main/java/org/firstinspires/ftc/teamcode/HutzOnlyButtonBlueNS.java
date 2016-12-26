package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="hutz blue button only no shoot", group="hutzAuto")

public class HutzOnlyButtonBlueNS extends HutzFuncMK2 {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeHardware("blue");
        resetServos();
        while (!isStarted()) {
            logToTelemetry();
        }

        while(opModeIsActive()) {
            //todo: write code here
            drive(Math.PI/4.0, 0.75);
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
            //Thread.sleep(500);
           // drive(5*Math.PI/4.0, 0.75);
           // doTilPlatform(8);
           // resetWheels();
            idle();
        }
    }
}
