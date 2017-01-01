package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 01
 */
@Autonomous(name="Test for autonomous", group="3c")
public class HutzAutonomousTest extends HutzFuncMK2{


    public void runOpMode() throws InterruptedException{
        initializeHardware("any");
        while(!isStarted()){

        }
        while(opModeIsActive()){
            drive(Math.PI/2, 1);

//            drive(3*Math.PI/4, 0.5);
//            sleep(500);
//            drive(Math.PI/4, 0.5);
//            sleep(500);
//            drive(7*Math.PI/4, 0.5);
//            sleep(500);
//            drive(5*Math.PI/4, 0.5);
//            resetWheels();
        }
    }
}
