package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * hi iit is Eliezer in 12
 */
@Autonomous(name="Hutzbots blue with buttons and center platform", group="hutzAuto")

public class HutzOnlyButtonBlue extends HutzFuncMK2 {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeHardware("blue");
        while (!isStarted()) {
            logToTelemetry(); //comment out when no longer needed
        }

        while (opModeIsActive()) {
//            driveForward(0.1, 500);
//            spin(0.2);
//            double desiredAngle = (compass.getDirection() + 180) % 360;
//            double track;
//            do {
//                sleep(50);
//                track=compass.getDirection();
//            } while(! (track > desiredAngle + 5 && track < desiredAngle - 5) );
//            resetWheels();
            currTopLeft = botRight;
            currTopRight = botLeft;
            currBotLeft = topRight;
            currBotRight = topLeft;
            currTopLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            currTopRight.setDirection(DcMotorSimple.Direction.REVERSE);
            currBotLeft.setDirection(DcMotorSimple.Direction.FORWARD);
            currBotRight.setDirection(DcMotorSimple.Direction.FORWARD);

            drive(Math.PI / 4.0, 0.23);
            doTilDistance(25.0);
            resetWheels();
            sleep(500);
            driveForward(0.2);
            doTilLine();
            resetWheels();
            sleep(500);
            pushButton();
            driveForward(0.3);
            sleep(300);
            doTilLine();
            resetWheels();
            sleep(500);
//            if (line.getLightDetected() < 0.12) {
//                drive(3 * Math.PI / 2, 0.13);
//                doTilLine();
//                resetWheels();
//            }
            pushButton();
//            drive(Math.PI, 0.1);
//            doTilDistance(35.0);
//            drive(3*Math.PI/2, 0.2);
//            doTilLine();
//            sleep(500);
//            resetWheels();
            drive(7.5*Math.PI/6, 0.5);
            doTilPlatform();
            resetWheels();
            requestOpModeStop();
            idle();
        }
    }
}
