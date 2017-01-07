package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Hutzbots blue everything (ball, buttons, center plat)", group="hutzAuto")
public class HutzLiterallyAllBlue extends HutzFuncMK2{
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

