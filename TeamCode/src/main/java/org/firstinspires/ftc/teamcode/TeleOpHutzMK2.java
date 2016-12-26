package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Eliezer Pearl on 9/9/2016.
 */
@TeleOp(name="Hutzbots Teleop crab 2")
//@Disabled //uncomment to disable, comment to enable
public class TeleOpHutzMK2 extends HutzFuncMK2 {
    final double NONE = 180.4;
    final double DEAD_ZONE = 0.05; //TODO: CHANGE THIS THROUGH DEBUGGING
    @Override
    public void runOpMode(){
        initializeHardware("lol doesn't matter");
        while(!isStarted()){
            logToTelemetry();
        }
        while(opModeIsActive()){
            //TODO: add crab drive code
            //left stick drives, right stick turns. add compass or gyro!!!!! for turning. or not

            if(Math.abs(gamepad1.right_stick_x) > DEAD_ZONE){
                setMotors(gamepad1.right_stick_x);
            }
            if(Math.abs(gamepad1.left_stick_y) > DEAD_ZONE || Math.abs(gamepad1.left_stick_x) > DEAD_ZONE){
                double angle = Math.atan2(gamepad1.left_stick_y,gamepad1.left_stick_x);
                if(angle >= 0 && angle < Math.PI/2.0){ //quad 1

                } else if (angle >= Math.PI/2.0 && angle < Math.PI){ //quad 2

                } else if (angle >= Math.PI && angle < 3*Math.PI/2.0){ //quad 3

                } else { //quad 4

                }
            }
            if(gamepad1.y){
                //runIntake(true);
            } else if(gamepad1.x){
                //runIntake(false);
            } else {
              //  stopIntake();
            } //run intake

            if(gamepad1.left_bumper){
                setServo(true);
            } else if(gamepad1.right_bumper){   //could make em independent but no need
                setServo(false);
            } else {
                resetServos();
            } //run pushers

            //TODO: add ball launching code

        }
    }
}

