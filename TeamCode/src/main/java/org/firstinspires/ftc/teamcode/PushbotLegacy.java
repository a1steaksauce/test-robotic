package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class PushbotLegacy extends OpMode

{
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor armDrive;

    public PushbotLegacy() {

    }
    @Override public void init(){

        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        armDrive = hardwareMap.dcMotor.get("armDrive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override public void loop () {

        float l_gp1_left_stick_y = -gamepad1.left_stick_y;
        float l_left_drive_power = (l_gp1_left_stick_y);

        float l_gp1_right_stick_y = -gamepad1.right_stick_y;
        float l_right_drive_power = (l_gp1_right_stick_y);

        float l_gp2_right_stick_y = gamepad2.right_stick_y;
        float l_arm_drive_power = (l_gp2_right_stick_y);


        rightDrive.setPower(l_right_drive_power);
        leftDrive.setPower(l_left_drive_power);
        armDrive.setPower(l_arm_drive_power);
        }
}



