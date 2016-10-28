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

    DcMotor RF, LF, RB, LB, Lift;
    Servo ButtonPresser;
    Boolean backwards = false;

    @Override
    public void init() {
        ButtonPresser = hardwareMap.servo.get("ButtonPresser");
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);
        ButtonPresser.setPosition(0.4);
    }

    @Override
    public void loop() {
        //DRIVING
        RF.setPower(gamepad1.right_stick_y);
        LF.setPower(gamepad1.left_stick_y);
        RB.setPower(gamepad1.right_stick_y);
        LB.setPower(gamepad1.left_stick_y);

        //BUTTON PUSHER
        if (gamepad1.x && ButtonPresser.getPosition() < 1.0) {
            ButtonPresser.setPosition(ButtonPresser.getPosition() + 0.01);
        } else if (gamepad1.b && ButtonPresser.getPosition() > 0.0) {
            ButtonPresser.setPosition(ButtonPresser.getPosition() - 0.01);
        }

        //LIFT
        if (gamepad1.right_bumper) {
            Lift.setPower(1.0);
        } else if (gamepad1.left_bumper) {
            Lift.setPower(-0.1);
        }
        else {
            Lift.setPower(0);
        }

        //FLIP
        if (gamepad1.a) {
            RF.setDirection(DcMotor.Direction.FORWARD);
            RB.setDirection(DcMotor.Direction.FORWARD);
            LF.setDirection(DcMotor.Direction.REVERSE);
            LB.setDirection(DcMotor.Direction.REVERSE);
            backwards = true;
        }
        if (gamepad1.y) {
            RF.setDirection(DcMotor.Direction.REVERSE);
            RB.setDirection(DcMotor.Direction.REVERSE);
            LF.setDirection(DcMotor.Direction.FORWARD);
            LB.setDirection(DcMotor.Direction.FORWARD);
            backwards = false;
        }


        telemetry.addData("Aleph Bots Robot:", "Running!");
        telemetry.addData("Driving backwards?", backwards);
        telemetry.addData("ButtonPresser position:", ButtonPresser.getPosition());
        telemetry.update();
    }
}
