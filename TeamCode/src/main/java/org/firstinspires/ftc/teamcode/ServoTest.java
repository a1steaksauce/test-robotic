package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class ServoTest extends OpMode

{
    RobotServos servos;

    @Override public void init() {
        servos = new RobotServos(hardwareMap.servo.get("buttonServo"), hardwareMap.servo.get("climberServo"));
    }


    @Override public void loop ()

    {
        servos.setServos(gamepad1);

    }
    @Override public void stop () {

    }

}