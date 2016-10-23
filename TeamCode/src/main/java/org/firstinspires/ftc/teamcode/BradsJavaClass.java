package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Brad on 10/23/2016.
 */
@TeleOp(name="Brad Tele OP")

public class BradsJavaClass extends LinearOpMode {
    DcMotor RF, LF, RB, LB, Lift;
    Servo ButtonPresser;
    String running = "Running!";
    Boolean backwards = false;


   @Override
    public void runOpMode(){
        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        ButtonPresser = hardwareMap.servo.get("ButtonPresser");

        while(true){


        }
    }
}
