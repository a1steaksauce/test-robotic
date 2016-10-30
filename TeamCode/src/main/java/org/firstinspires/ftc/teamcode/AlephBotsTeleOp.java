package org.firstinspires.ftc.teamcode;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by aaronkbutler on 3/10/16.
 */
@TeleOp(name="Aleph Bots TeleOp", group="TeleOp")
//@Disabled //uncomment to disable, comment to enable
public class AlephBotsTeleOp extends OpMode{
    //DECLARING MOTORS, SERVOS, and DRIVE
    RobotDriving drive;
    DcMotor RF, LF, RB, LB, Lift;
    Servo ButtonPresser;

    @Override
    public void init() {
        //INITIALIZING EVERYTHING
        drive = new RobotDriving(hardwareMap.dcMotor.get("LF"), hardwareMap.dcMotor.get("LB"), hardwareMap.dcMotor.get("RF"), hardwareMap.dcMotor.get("RB"));
        ButtonPresser = hardwareMap.servo.get("ButtonPresser");
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        //RF.setDirection(DcMotor.Direction.REVERSE);
        //RB.setDirection(DcMotor.Direction.REVERSE);
        ButtonPresser.setPosition(0.1);
    }

    @Override
    public void loop() {
        //DRIVE and FLIP using RobotDriving.java Class
        drive.tankDrive(gamepad1);
        drive.changeDriveMode(gamepad1.left_stick_button, gamepad1.right_stick_button);
        drive.changeDriveSpeed(gamepad1.dpad_up, gamepad1.dpad_down);

        //BUTTON PUSHER
        if (gamepad1.x && ButtonPresser.getPosition() < 1.0) {
            ButtonPresser.setPosition(ButtonPresser.getPosition() + 0.01);
        } else if (gamepad1.b && ButtonPresser.getPosition() > 0.0) {
            ButtonPresser.setPosition(ButtonPresser.getPosition() - 0.01);
        }

        //LIFT
        if (gamepad1.right_bumper) {
            Lift.setPower(0.5); //HALF POWER
        } else if (gamepad1.left_bumper) {
            Lift.setPower(-0.1); //TENTH OF THE POWER
        }
        else {
            Lift.setPower(0);
        }

        //TELEMETRY
        telemetry.addData("Aleph Bots Robot:", "Running!");
        telemetry.addData("Driving Mode:", drive.getDriveMode());
        telemetry.addData("Driving Speed:", drive.getDriveSpeed());
        telemetry.addData("ButtonPresser position:", ButtonPresser.getPosition());
        telemetry.update();
    }
}