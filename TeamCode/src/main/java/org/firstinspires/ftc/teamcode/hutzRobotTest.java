package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * hi iit is Eliezer in 12
 */
@TeleOp(name="Teleop Test Hutzbots", group = "hutzTele")
public class hutzRobotTest extends LinearOpMode {
    DcMotor currTopLeft, currTopRight, currBotLeft, currBotRight;
    DcMotor intake;
    public void runOpMode(){
        final double BLANK = 180.4;
        final double DEAD_ZONE = 0.05;
        currTopLeft = hardwareMap.dcMotor.get("topLeft");
        currTopRight = hardwareMap.dcMotor.get("topRight");
        currBotLeft = hardwareMap.dcMotor.get("botLeft");
        currBotRight = hardwareMap.dcMotor.get("botRight");
        intake = hardwareMap.dcMotor.get("intake");
        while(!isStarted()){

        }
        while(opModeIsActive()) {
            if(gamepad1.dpad_up) {
                currTopLeft.setPower(1);
            } else {
                currTopLeft.setPower(0);
            }

            if(gamepad1.dpad_right) {
                currTopRight.setPower(1);
            }
            else {
                currTopRight.setPower(0);
            }

            if(gamepad1.dpad_down) {
                currBotLeft.setPower(1);
            } else {
                currBotLeft.setPower(0);
            }

            if(gamepad1.dpad_left) {
                currBotRight.setPower(1);
            } else {
                currBotRight.setPower(0);
            }
            if(gamepad1.a){
                intake.setPower(-0.5);
            } else {
                intake.setPower(0);
            }
        }
    }
}
