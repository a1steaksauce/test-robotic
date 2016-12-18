package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Ben on 12/18/16.
 */

@TeleOp(name="New Teleop")
public class TeleOpHutzNew extends HutzFunc {
    final double DEAD_ZONE = 0.08;
    public void runOpMode() throws InterruptedException {
        initializeHardware();
        reset();
        while (!isStarted()) {
            logToTelemetry();
        }
        while (opModeIsActive()) {
            if (Math.abs(gamepad1.left_stick_y) > DEAD_ZONE) {
                botRight.setPower(gamepad1.left_stick_y);
                topRight.setPower(gamepad1.left_stick_y);
                topLeft.setPower(gamepad1.left_stick_y);
                botLeft.setPower(gamepad1.left_stick_y);
            } else {
                reset();
            }
            if (Math.abs(gamepad1.right_stick_x) > DEAD_ZONE) {
                botRight.setPower(0 - gamepad1.right_stick_x);
                topRight.setPower(0 - gamepad1.right_stick_x);
                topLeft.setPower(gamepad1.right_stick_x);
                botLeft.setPower(gamepad1.right_stick_x);
            } else {
                reset();
            }
            if (gamepad1.right_bumper) {
                beacon.setPosition(0);
                Thread.sleep(1000);
                beacon.setPosition(0.5);
            }
            if (gamepad1.left_bumper) {
                beacon.setPosition(1);
                Thread.sleep(1000);
                beacon.setPosition(0.5);
            }
        }
    }
}
