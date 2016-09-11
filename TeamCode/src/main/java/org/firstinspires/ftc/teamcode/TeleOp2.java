package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOp2 extends OpMode

{
    RobotDriving drive;
    RobotServos servos;

    @Override public void init() {
        drive = new RobotDriving(hardwareMap.dcMotor.get("leftDrive"), hardwareMap.dcMotor.get("leftDriveB"), hardwareMap.dcMotor.get("rightDrive"), hardwareMap.dcMotor.get("rightDriveB"));
        servos = new RobotServos(hardwareMap.servo.get("buttonServo"), hardwareMap.servo.get("climberServo"));
    }


    @Override public void loop ()

    {
        drive.tankDrive(gamepad1);
        servos.setServos(gamepad1);

    }
    @Override public void stop () {

    }

}