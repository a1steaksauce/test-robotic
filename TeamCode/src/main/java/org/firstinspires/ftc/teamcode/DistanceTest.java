package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
/**
 * Created by alexbulanov on 5/2/16.
 */
public class DistanceTest extends LinearOpMode {

    OpticalDistanceSensor ods;

    DcMotor LD;
    DcMotor RD;

    @Override
    public void runOpMode() throws InterruptedException {

        ods = hardwareMap.opticalDistanceSensor.get("opticalDistanceSensor");
        RD = hardwareMap.dcMotor.get("motor_2");
        LD = hardwareMap.dcMotor.get("motor_1");
        LD.setDirection(DcMotor.Direction.REVERSE);

        double reflectance = ods.getLightDetected();
        waitForStart();
        while (reflectance < 0.189) {
            LD.setPower(-0.2);
            RD.setPower(-0.2);
        }
    }
}
