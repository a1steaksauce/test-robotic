package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Ball only hutz", group = "hutzAuto")
public class hutzBallOnly extends HutzFunc {
    public void runOpMode() throws InterruptedException{
        initializeTeam("lol doesnt matter");
        initializeHardware();
        reset();
        beacon.setPosition(0.5);

        while(!isStarted()) {
            logToTelemetry();
        }
        while(opModeIsActive()){
            driveStraight(1);
            doTilPlatform();
            reset();
            idle();
        }
    }
}
