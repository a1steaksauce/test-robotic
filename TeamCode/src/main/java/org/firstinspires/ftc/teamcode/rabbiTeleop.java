package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

/**
 * Created by poop on 12/14/2016.
 */

public class rabbiTeleop extends LinearOpMode {
    DcMotor topLeft, topRight, botLeft, botRight, capBall;
    Servo bl, br;

    final double DEAD_ZONE = 0.05;
    public void runOpMode(){
        topLeft = hardwareMap.dcMotor.get("l");
        botLeft = hardwareMap.dcMotor.get("lb");
        topRight = hardwareMap.dcMotor.get("r");
        botRight = hardwareMap.dcMotor.get("rb");
        capBall = hardwareMap.dcMotor.get("lift");
        bl = hardwareMap.servo.get("bl");
        br = hardwareMap.servo.get("br");
        while(opModeIsActive()){
            if (Math.abs(gamepad1.left_stick_y) > DEAD_ZONE) {
                topLeft.setPower(gamepad1.left_stick_y);
                botLeft.setPower(gamepad1.left_stick_y);
            }
            if (Math.abs(gamepad1.right_stick_y) > DEAD_ZONE) {
                topRight.setPower(gamepad1.right_stick_y);
                botRight.setPower(gamepad1.right_stick_y);
            }
            if (Math.abs(gamepad1.right_stick_y) < DEAD_ZONE) {
                topRight.setPower(0);
                botRight.setPower(0);
            }
            if (Math.abs(gamepad1.left_stick_y) < DEAD_ZONE) {
                topLeft.setPower(0);
                botLeft.setPower(0);
            }

            if (gamepad1.right_trigger > 0.1) {
                capBall.setPower(gamepad1.right_trigger);
            }
            else if (gamepad1.left_trigger > 0.1) { //debugging only
                capBall.setPower(-gamepad1.left_trigger);
            }
            else {
                //todo: operate servo
                capBall.setPower(0);
            }

            if (gamepad1.right_bumper){
                br.setPosition(1);
            } else {
                br.setPosition(0);
            }
            if (gamepad1.left_bumper){
                bl.setPosition(1);
            } else {
                bl.setPosition(0);
            }
        }
    }
}
