package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Hutzbots blue with shooting and buttons", group="hutzAuto")

public class HutzOnlyButtonBlue extends HutzFuncMK2 {
    @Override
    public void runOpMode() throws InterruptedException {

        initializeHardware("blue");
        while (!isStarted()) {
        }

        while(opModeIsActive()) {
            //todo: write code here
            idle();
        }
    }
}
