package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="hutz blue button only", group="hutzAuto")

public class HutzOnlyButtonBlue extends HutzFuncMK2 {
    @Override
    public void runOpMode() throws InterruptedException {

        initializeHardware("red");
        while (!isStarted()) {
            logToTelemetry();
        }

        while(opModeIsActive()) {
            //todo: write code here

        }
    }
}
