package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * hi iit is Eliezer in 12
 */
@TeleOp(name="hutztest", group = "3c")
public class hutzRobotTest extends HutzFuncMK2 {
    public void runOpMode(){
        initializeHardware("e");
        final double BLANK = 180.4;
        final double DEAD_ZONE = 0.05;
        while(!isStarted()){
            logToTelemetry();
        }
        while(opModeIsActive()){
            if(gamepad1.left_trigger > DEAD_ZONE) {
                setMotors(1, BLANK, BLANK, BLANK);
            } else {
                setMotors(0, BLANK, BLANK, BLANK);
            }

            if(gamepad1.right_trigger > DEAD_ZONE) {
                setMotors(BLANK, -1, BLANK, BLANK);
            }
            else {
                setMotors(BLANK, 0, BLANK, BLANK);
            }

            if(gamepad1.left_stick_y > DEAD_ZONE) {
                setMotors(BLANK, BLANK, -1, BLANK);
            } else {
                setMotors(BLANK, BLANK, 0, BLANK);
            }

            if(gamepad1.right_stick_y > DEAD_ZONE) {
                setMotors(BLANK, BLANK, BLANK, 1);
            } else {
                setMotors(BLANK, BLANK, BLANK, 0);
            }

           // if(gamepad1.x){
           //     runIntake(true);
           // }
           // else if(gamepad1.a){
          //      runIntake(false);
          //  }
          //  else {
          //      stopIntake();
          //  }

            if(gamepad1.left_bumper) {
                setServo(true);
            } else if (gamepad1.right_bumper) {
                setServo(false);
            } else {
                resetServos();
            }
        }
    }
}
