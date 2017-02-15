package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aaronkbutler on 3/10/16.
 */
@TeleOp(name="Aleph Bots: TeleOp Linear New", group="TeleOp")
//@Disabled //uncomment to disable, comment to enable
public class AlephBotsTeleOpLinearNew extends LinearOpMode {
    //DECLARING MOTORS, SERVOS, and DRIVE
    AlephBotsRobotDriving drive;
    DcMotor RF, LF, RB, LB, Shooter, RLift, LLift;

    @Override
    public void runOpMode() throws InterruptedException {
        //INITIALIZING EVERYTHING
        drive = new AlephBotsRobotDriving(hardwareMap.dcMotor.get("LF"), hardwareMap.dcMotor.get("LB"),
                hardwareMap.dcMotor.get("RF"), hardwareMap.dcMotor.get("RB"));

        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");

        Shooter = hardwareMap.dcMotor.get("Shooter");

        waitForStart();

        while (opModeIsActive()) {
            drive.mechanumDrive(gamepad1);
            //SHOOTER
            if (gamepad1.a) {
                Shooter.setPower(1.0);
            } else if (gamepad1.x) {
                Shooter.setPower(-1.0);
            } else {
                Shooter.setPower(0);
            }
            //LIFT
            if (gamepad1.left_bumper) {
                RLift.setPower(1.0);
                LLift.setPower(-1.0);
            } else if (gamepad1.right_bumper) {
                RLift.setPower(-1.0);
                LLift.setPower(1.0);
            } else if (gamepad1.dpad_left) {
                RLift.setPower(0);
                LLift.setPower(-1.0);
            } else if (gamepad1.dpad_right) {
                RLift.setPower(1.0);
                LLift.setPower(0);
            } else {
                RLift.setPower(0);
                LLift.setPower(0);
            }

        }
    }
}