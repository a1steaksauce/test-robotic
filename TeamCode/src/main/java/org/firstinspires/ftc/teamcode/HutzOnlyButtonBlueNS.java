package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Blue buttons no shoot hutzbots", group="hutzAuto")

public class HutzOnlyButtonBlueNS extends HutzFuncMK2 {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeHardware("blue");
        while (!isStarted()) {
            logToTelemetry(); //comment out when no longer needed
        }

        while(opModeIsActive()) {
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
            idle();
        }
    }
}
