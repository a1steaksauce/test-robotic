package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="HUTZBOTS BLUE ALL LIKE LITERALLY ALL", group="hutzAuto")
public class HutzLiterallyAllBlue extends HutzFunc{
    @Override
    public void runOpMode() throws InterruptedException {
        initializeTeam("blue");
        initializeHardware();
        reset();
        beacon.setPosition(0.5);

        while(!isStarted()) {
            logToTelemetry();
        }
        while(opModeIsActive()){
            //todo: write code here
            shootOnce();
            strafeRight45(0.75);
            doTilDistance(8); //cm
            reset();
            for (int i = 0; i < 2; i++) {
                Thread.sleep(500);
                driveStraight(0.5);
                doTilLine();
                reset();
                Thread.sleep(500);
                pushButton();
            }
            Thread.sleep(500);
            strafeLeft45(-0.75);
            doTilPlatform(8);
            reset();
            if (botOrNot()) {
                strafeRight45(-0.5);
                Thread.sleep(2000);
                reset();
            } else {
                strafeLeft45(-0.5);
                doTilPlatform();
                reset();
            }
            idle();
        }
    }
}

