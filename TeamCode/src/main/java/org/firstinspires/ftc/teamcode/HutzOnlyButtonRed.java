package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Hutzbots blue with shooting and buttons", group="hutzAuto")
public class HutzOnlyButtonRed extends HutzFuncMK2 {
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
