package org.firstinspires.ftc.teamcode;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by aaronkbutler on 3/10/16.
 */
@TeleOp(name="Aleph Bots Tank Drive", group="TeleOp")
//@Disabled //uncomment to disable, comment to enable
public class AlephBotsTankDrive extends OpMode{

    DcMotor RF,LF,RB,LB,Lift;
    Servo ButtonPresser;
    final double ONE_UP = 0.1;
    boolean upMode = false;

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
        ButtonPresser.setPosition(0.01);
    }

    @Override
    public void loop() {
        RF.setPower(gamepad1.right_stick_y);
        LF.setPower(gamepad1.left_stick_y);
        RB.setPower(gamepad1.right_stick_y);
        LB.setPower(gamepad1.left_stick_y);

        if(gamepad1.a && ButtonPresser.getPosition() < 1.0){
            ButtonPresser.setPosition(ButtonPresser.getPosition()+0.01);
        } else if(gamepad1.y && ButtonPresser.getPosition() > 0.0){
            ButtonPresser.setPosition(ButtonPresser.getPosition()-0.01);
        }

        if (gamepad1.right_bumper) {
            Lift.setPower(0.5);
        } else if (gamepad1.left_bumper) {
            Lift.setPower(-0.1);
        }
        else {
            Lift.setPower(0);
        }
        telemetry.addData("ButtonPresser position:", ButtonPresser.getPosition());
    }
}
