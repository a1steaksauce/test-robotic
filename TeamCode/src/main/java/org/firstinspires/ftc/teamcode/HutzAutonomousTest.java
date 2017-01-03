package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * hi iit is Eliezer in 01
 */
@Autonomous(name="Test for autonomous", group="3c")
public class HutzAutonomousTest extends LinearOpMode{

    DcMotor currBotRight;
    public void runOpMode() throws InterruptedException{
        currBotRight = hardwareMap.dcMotor.get("botRight");
        while(!isStarted()){

        }
        while(opModeIsActive()){
         //   currBotLeft.setPower(1);
            currBotRight.setPower(1);

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
