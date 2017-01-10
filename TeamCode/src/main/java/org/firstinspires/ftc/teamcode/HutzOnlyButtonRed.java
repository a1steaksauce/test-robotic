package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Hutzbots red with buttons and ball", group="hutzAuto")
public class HutzOnlyButtonRed extends HutzFuncMK2 {
    @Override
    public void runOpMode() throws InterruptedException {
            initializeHardware("red");
            resetServos();
            while (!isStarted()) {
                logToTelemetry(); //remove when no longer needed
            }

            while(opModeIsActive()) {
                //todo: write code here
                drive(3*Math.PI/4.0, 0.23);
                doTilDistance(25.0);
                resetWheels();
                sleep(500);
                driveForward(0.2);
                sleep(500);
                doTilLine();
                resetWheels();
                sleep(500);
//                if(line.getLightDetected() < 0.11){
//                    drive(3*Math.PI/2, 0.13);
//                    doTilLine();
//                }
                pushButton();
                driveForward(0.3);
                sleep(500);
                doTilLine();
                resetWheels();
                sleep(500);
//                if(line.getLightDetected() < 0.11){
//                    drive(3*Math.PI/2, 0.13);
//                    doTilLine();
//                }
                pushButton();
                drive(10.5*Math.PI/6, 0.5);
                doTilPlatform();
                requestOpModeStop();
                idle();
            }
        }
    }
