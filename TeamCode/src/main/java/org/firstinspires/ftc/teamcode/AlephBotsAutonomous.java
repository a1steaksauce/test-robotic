package org.firstinspires.ftc.teamcode;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by aaronkbutler on 10/21/16.
 */
@Autonomous(name="Aleph Bots Autonomous", group="Autonomous")
public class AlephBotsAutonomous extends LinearOpMode{
    DcMotor RF, LF, RB, LB, Lift;
    Servo ButtonPresser;
    String running = "Running!";


    @Override
    public void runOpMode() throws InterruptedException{
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);
        ButtonPresser.setPosition(0.01);


        driveStraight(1.0);
        moveServo(0.5);
        Thread.sleep(1000);

        turnLeft(1.0);
        moveServo(1.0);
        Thread.sleep(1000);

        turnRight(1.0);
        moveServo(0.0);
        Thread.sleep(1000);

        stopDrive();

        waitForStart();
        idle();
    }
    public void driveStraight(double power) {
        LF.setPower(power);
        LB.setPower(power);
        RF.setPower(power);
        RB.setPower(power);
    }
    public void turnLeft(double power) {
        LF.setPower(-power);
        LB.setPower(-power);
        RF.setPower(power);
        RB.setPower(power);
    }

    public void turnRight(double power) {
        LF.setPower(power);
        LB.setPower(power);
        RF.setPower(-power);
        RB.setPower(-power);
    }

    public void stopDrive() {
        LF.setPower(0);
        LB.setPower(0);
        RF.setPower(0);
        RB.setPower(0);
    }

    public void moveServo(double location) {
        ButtonPresser.setPosition(location);
    }
}
