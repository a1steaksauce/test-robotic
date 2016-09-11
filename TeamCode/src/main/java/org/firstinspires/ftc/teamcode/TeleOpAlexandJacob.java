package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOpAlexandJacob extends OpMode {

    RobotDriving drive;
    Servo testServo;

    @Override
    public void init() {
        drive = new RobotDriving(hardwareMap.dcMotor.get("leftDrive"),hardwareMap.dcMotor.get("leftDriveB"),hardwareMap.dcMotor.get("rightDrive"),hardwareMap.dcMotor.get("rightDriveB"));
        testServo = hardwareMap.servo.get("testServo");

    }


    @Override public void loop ()

    {

        drive.tankDrive(gamepad1);
        if(gamepad1.dpad_up){
            testServo.setPosition(0.75);
        }
        if(gamepad1.dpad_down){
            testServo.setPosition(0.25);
        }
        if(gamepad1.a){
            testServo.setPosition(0.5);
        }

    }

}