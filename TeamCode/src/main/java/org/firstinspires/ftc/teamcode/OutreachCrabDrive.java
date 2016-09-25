package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by aaronkbutler on 3/10/16.
 */
@TeleOp(name="Crab Drive", group="TeleOp")
//@Disabled//uncomment to disable, comment to enable
public class OutreachCrabDrive extends OpMode{

    DcMotor RF,LF,RB,LB;

    @Override
    public void init() {
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
    }

    @Override
    public void loop() {

        if (Math.abs(gamepad1.right_stick_x) > 0.03){
            RF.setPower(gamepad1.right_stick_x);
            LF.setPower(gamepad1.right_stick_x);
            RB.setPower(gamepad1.right_stick_x);
            LB.setPower(gamepad1.right_stick_x);
        }
        else{
            if (Math.abs(gamepad1.left_stick_x) < 0.03 && Math.abs(gamepad1.left_stick_y) < 0.03) {
                dontDrive();
            }
            else if (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y)) {
                horizontalDrive();
            }
            else {
                verticalDrive();
            }
        }
    }
    public void dontDrive (){
        RF.setPower(0);
        LF.setPower(0);
        RB.setPower(0);
        LB.setPower(0);
    }
    public void horizontalDrive (){
        RF.setPower(-gamepad1.left_stick_x);
        LF.setPower(gamepad1.left_stick_x);
        RB.setPower(gamepad1.left_stick_x);
        LB.setPower(-gamepad1.left_stick_x);
    }
    public void verticalDrive (){
        RF.setPower(-gamepad1.left_stick_y);
        LF.setPower(gamepad1.left_stick_y);
        RB.setPower(gamepad1.left_stick_y);
        LB.setPower(-gamepad1.left_stick_y);
    }
}
