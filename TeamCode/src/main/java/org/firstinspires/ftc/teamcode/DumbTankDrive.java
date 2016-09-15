package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by aaronkbutler on 3/10/16.
 */
@TeleOp(name="testBot: Telop Tank", group="testBot")
//@Disabled //uncomment to disable, comment to enable
public class DumbTankDrive extends OpMode{

    DcMotor LF,LB;
    Servo silly;



    @Override
    public void init() {

        LF = hardwareMap.dcMotor.get("LF");

        LB = hardwareMap.dcMotor.get("LB");
        silly = hardwareMap.servo.get("silly");

    }

    @Override
    public void loop() {

        LF.setPower(gamepad1.left_stick_y);

        LB.setPower(gamepad1.left_stick_y);

        if(gamepad1.a){
            silly.setPosition(silly.getPosition()+0.1);
        } else if(gamepad1.y){
            silly.setPosition(silly.getPosition()-0.1);
        }
    }
}
