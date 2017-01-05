package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * hi iit is Eliezer in 01
 */
@Autonomous(name="Test for autonomous blue", group="3c")
public class HutzAutonomousTest extends HutzFuncMK2{

    public void runOpMode() throws InterruptedException{
        //currBotRight = hardwareMap.dcMotor.get("botRight");
        initializeHardware("any");
        while(!isStarted()){
            logToTelemetry();
        }
        while(opModeIsActive()){
         //   currBotLeft.setPower(1);
         //   currBotRight.setPower(1);

            //shoot
            driveForward(0.1, 500);
            spin(0.2);
            double dist=1000;
            double prevDist;
            do{
                wait(50);
                prevDist = dist;
                dist = ultrason.getUltrasonicLevel();
            } while(dist == 0 || prevDist == 0 || dist > 200 || prevDist > 200 || (prevDist < dist));
            resetWheels();
            drive(Math.PI/4.0, 0.2);
            doTilDistance(20.0);
            resetWheels();
            sleep(500);
            driveForward(0.2);
            doTilLine();
            resetWheels();
            sleep(500);
            if(line.getLightDetected() < 0.12){
                drive(3*Math.PI/2, 0.1);
                doTilLine();
                resetWheels();
            }
            resetWheels();
            while(!gamepad1.x){
                logToTelemetry();
            }
            requestOpModeStop();
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
