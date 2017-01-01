package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * hi iit is Eliezer in 12
 */
@TeleOp(name="hutztest", group = "3c")
public class hutzRobotTest extends LinearOpMode {
    DcMotor currTopLeft, currTopRight, currBotLeft, currBotRight;
    public void runOpMode(){
        final double BLANK = 180.4;
        final double DEAD_ZONE = 0.05;
        currTopLeft = hardwareMap.dcMotor.get("topLeft");
        currTopRight = hardwareMap.dcMotor.get("topRight");
        currBotLeft = hardwareMap.dcMotor.get("botLeft");
        currBotRight = hardwareMap.dcMotor.get("botRight");
        while(!isStarted()){

        }
        while(opModeIsActive()) {
            if(Math.abs(gamepad1.left_trigger) > DEAD_ZONE) {
                currTopLeft.setPower(1);
            } else {
                currTopLeft.setPower(0);
            }

            if(Math.abs(gamepad1.right_trigger) > DEAD_ZONE) {
                currTopRight.setPower(1);
            }
            else {
                currTopRight.setPower(0);
            }

            if(Math.abs(gamepad1.left_stick_y) > DEAD_ZONE) {
                currBotLeft.setPower(1);
            } else {
                currBotLeft.setPower(0);
            }

            if(Math.abs(gamepad1.right_stick_y) > DEAD_ZONE) {
                currBotRight.setPower(1);
            } else {
                currBotRight.setPower(0);
            }
        }
    }
}
