package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class CrabDrive extends OpMode

{
    DcMotor RB;
    DcMotor LB;
    DcMotor RF;
    DcMotor LF;

    double scale_motor_power (double p_power)
    {
        //
        // Assume no scaling.
        //
        double l_scale; //There is no need to put double l_scale = 0.0f; as you can just leave it as double l_scale;. The = 0.0f is redundant.

        //
        // Ensure the values are legal.
        //
        double l_power = Range.clip (p_power, -1, 1);

        double[] l_array =
                {0.00, 0.05, 0.09, 0.10, 0.12
                        , 0.15, 0.18, 0.24, 0.30, 0.36
                        , 0.43, 0.50, 0.60, 0.72, 0.85
                        , 1.00, 1.00
                };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
        int l_index = (int) (l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    }


    @Override public void init() {
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");


    }


    @Override public void loop ()

    {
        //begin left stick drive/strafe code
        float l_gpl_left_stick_x = gamepad1.left_stick_x;
        float l_gpl_right_stick_y = gamepad1.right_stick_y;
        float l_gpl_right_stick_x = gamepad1.right_stick_x;

        float l_right_y
                = (float) scale_motor_power(l_gpl_right_stick_y);
        float l_right_x
                = (float) scale_motor_power(l_gpl_right_stick_x);

        if(Math.abs(l_gpl_left_stick_x) > 0.1) {
            RB.setPower(-l_gpl_left_stick_x); LB.setPower(-l_gpl_left_stick_x);
            RF.setPower(-l_gpl_left_stick_x); LF.setPower(-l_gpl_left_stick_x);
        }
        else if(Math.abs(l_right_y) > Math.abs(l_right_x) && Math.abs(l_right_y) > 0.1) {
            LF.setPower(l_right_y); LB.setPower(l_right_y);
            RF.setPower(-l_right_y); RB.setPower(-l_right_y);
        }
        else if(Math.abs(l_right_x) > Math.abs(l_right_y) && Math.abs(l_right_x) > 0.1) {
            LF.setPower(-l_right_x); RF.setPower(-l_right_x);
            LB.setPower(l_right_x); RB.setPower(l_right_x);
        }
        else {
            RB.setPower(0); RF.setPower(0);
            LB.setPower(0); LF.setPower(0);
        }





    }
    @Override public void stop () {
    }

}
