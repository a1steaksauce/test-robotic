package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by aaronkbutler on 3/10/16.
 */
@TeleOp(name="testBot: smelly op fank", group="testBot")
//@Disabled //uncomment to disable, comment to enable
public class OutreachTankDrive extends OpMode{

    DcMotor RF,LF,RB,LB;
    Servo silly;



    @Override
    public void init() {
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        silly = hardwareMap.servo.get("silly");
        RF.setDirection(DcMotor.Direction.REVERSE);
        RB.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        RF.setPower(gamepad1.right_stick_y);
        LF.setPower(gamepad1.left_stick_y);
        RB.setPower(gamepad1.right_stick_y);
        LB.setPower(gamepad1.left_stick_y);

        if(gamepad1.a){
            silly.setPosition(silly.getPosition()+0.1);
        } else if(gamepad1.y){
            silly.setPosition(silly.getPosition()-0.1);
        }
    }
}
