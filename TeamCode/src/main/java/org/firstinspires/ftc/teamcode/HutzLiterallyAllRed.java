package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


/**
 * Created by Ben on 12/11/16.
 */
@Autonomous(name="Hutzbots blue everything (ball, buttons, center plat)", group="hutzAuto")
public class HutzLiterallyAllRed extends HutzFuncMK2 {
    @Override
    public void runOpMode() throws InterruptedException {

        initializeHardware("red");
        while (!isStarted()) {
        }

        while(opModeIsActive()) {
            //todo: write code here
            idle();
        }
    }
}
