package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


/**
 * Created by Ben on 12/11/16.
 */
@Autonomous(name="HUTZBOTS RED ALL LIKE LITERALLY ALL", group="hutzAuto")
public class HutzLiterallyAllRed extends HutzFunc {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeTeam("red");
        initializeHardware();
        reset();
        beacon.setPosition(0.5);

        while(!isStarted()) {
            logToTelemetry();
        }
        while(opModeIsActive()){
            //todo: write code here
            shootOnce();
            strafeLeft45(0.75);
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
            strafeRight45(-0.75);
            doTilPlatform(8);
            reset();
            if (botOrNot()) {
                strafeLeft45(-0.5);
                Thread.sleep(2000);
                reset();
            } else {
                strafeRight45(-0.5);
                doTilPlatform();
                reset();
            }
            idle();
        }
    }
}
