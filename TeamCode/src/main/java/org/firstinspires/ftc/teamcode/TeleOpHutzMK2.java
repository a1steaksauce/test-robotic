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
@TeleOp(name="Basic driving code")
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
            //left stick drives, right stick turns. add compass or gyro!!!!! for turning. or not

            if(Math.abs(gamepad1.right_stick_y) > DEAD_ZONE || Math.abs(gamepad1.right_stick_x) > DEAD_ZONE){ //crab drive
                drive(Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x), Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y , 2)) );
            }
            if(Math.abs(gamepad1.left_stick_x) > DEAD_ZONE){
                spin(gamepad1.left_stick_x);
            }
            if(Math.abs(gamepad1.right_stick_x) < DEAD_ZONE && Math.abs(gamepad1.right_stick_y) < DEAD_ZONE && Math.abs(gamepad1.left_stick_x) < 0){
                resetWheels();
            }



            boolean downL = false;
            boolean downR = false;
            if(gamepad1.left_bumper){
                if(!downL) {
                    beaconLeft.setPosition(0.6);
                    downL = true;
                } else {
                    beaconLeft.setPosition(1);
                    downL = false;
                }
            }
            if(gamepad1.right_bumper){   //could make em independent but no need
                if(!downR){
                    beaconRight.setPosition(0.6);
                    downR = true;
                } else {
                    beaconRight.setPosition(0);
                    downR = false;
                }
            }

            //TODO: add ball launching code

        }
    }
}

