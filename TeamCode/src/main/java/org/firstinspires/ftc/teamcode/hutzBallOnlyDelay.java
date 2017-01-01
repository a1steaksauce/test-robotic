package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Ball only with delay", group = "hutzAuto")
public class hutzBallOnlyDelay extends HutzFuncMK2 {
    public void runOpMode() throws InterruptedException{
        initializeHardware("any");

        while(!isStarted()) {
        }
        while(opModeIsActive()){
            sleep(20000);
            driveForward(1, 3000);
            idle();
        }
    }
}
