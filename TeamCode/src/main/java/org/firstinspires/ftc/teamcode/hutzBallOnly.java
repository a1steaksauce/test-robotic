package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Ball only without delay", group = "hutzAuto")
public class hutzBallOnly extends HutzFuncMK2 {
    public void runOpMode() throws InterruptedException{
        initializeHardware("any");

        while(!isStarted()) {
        }
        while(opModeIsActive()){
            driveForward(0.5);
            doTilPlatform();
            resetWheels();
            requestOpModeStop();
            idle();
        }
    }
}
