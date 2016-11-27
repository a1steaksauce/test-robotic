package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by aaronkbutler on 3/10/16.
 */
@TeleOp(name="Aleph Bots: TeleOp", group="TeleOp")
//@Disabled //uncomment to disable, comment to enable
public class AlephBotsTeleOp extends OpMode{
    //DECLARING MOTORS, SERVOS, and DRIVE
    AlephBotsRobotDriving drive;
    DcMotor RF, LF, RB, LB, Lift;
    Servo ButtonPresser, LTouchServo, RTouchServo;
    OpticalDistanceSensor GroundColorSensor;
    ColorSensor BeaconColorSensor;
    TouchSensor LTouchSensor;
    TouchSensor RTouchSensor;
    TouchSensor BeaconTouchSensor;

    @Override
    public void init() {
        //INITIALIZING EVERYTHING
        drive = new AlephBotsRobotDriving(hardwareMap.dcMotor.get("LF"), hardwareMap.dcMotor.get("LB"),
                                 hardwareMap.dcMotor.get("RF"), hardwareMap.dcMotor.get("RB"));
        ButtonPresser = hardwareMap.servo.get("ButtonPresser");

        LTouchServo = hardwareMap.servo.get("LTouchServo");
        RTouchServo = hardwareMap.servo.get("RTouchServo");
        GroundColorSensor = hardwareMap.opticalDistanceSensor.get("GroundColorSensor");
        BeaconColorSensor = hardwareMap.colorSensor.get("BeaconColorSensor");
        LTouchSensor = hardwareMap.touchSensor.get("LTouchSensor");
        RTouchSensor = hardwareMap.touchSensor.get("RTouchSensor");
        BeaconTouchSensor = hardwareMap.touchSensor.get("BeaconTouchSensor");

        RF = hardwareMap.dcMotor.get("RF");
        LF = hardwareMap.dcMotor.get("LF");
        RB = hardwareMap.dcMotor.get("RB");
        LB = hardwareMap.dcMotor.get("LB");
        Lift = hardwareMap.dcMotor.get("Lift");
        //RF.setDirection(DcMotor.Direction.REVERSE);
        //RB.setDirection(DcMotor.Direction.REVERSE);
        ButtonPresser.setPosition(0.35);

        LTouchServo.setPosition(1.0);
        RTouchServo.setPosition(0.0);

        GroundColorSensor.enableLed(true);
        BeaconColorSensor.enableLed(true);
    }

    @Override
    public void loop() {
        //DRIVE and FLIP using AlephBotsRobotDriving.java Class
        drive.tankDrive(gamepad1);
        drive.changeDriveMode(gamepad1.left_stick_button, gamepad1.right_stick_button);
        drive.changeDriveSpeed(gamepad1.dpad_up, gamepad1.dpad_down);

        //BUTTON PUSHER
        if (gamepad1.x && ButtonPresser.getPosition() < 1.0) {
            ButtonPresser.setPosition(ButtonPresser.getPosition() + 0.01);
        } else if (gamepad1.b && ButtonPresser.getPosition() > 0.0) {
            ButtonPresser.setPosition(ButtonPresser.getPosition() - 0.01);
        }
        /*
        if (gamepad1.dpad_left && LTouchServo.getPosition() < 1.0) {
            LTouchServo.setPosition(LTouchServo.getPosition() + 0.01);
        } else if (gamepad1.dpad_right && LTouchServo.getPosition() > 0.0) {
            LTouchServo.setPosition(LTouchServo.getPosition() - 0.01);
        }

        if (gamepad1.a && RTouchServo.getPosition() < 1.0) {
            RTouchServo.setPosition(RTouchServo.getPosition() + 0.01);
        } else if (gamepad1.y && RTouchServo.getPosition() > 0.0) {
            RTouchServo.setPosition(RTouchServo.getPosition() - 0.01);
        }
        */
        //LIFT
        if (gamepad1.right_bumper) {
            Lift.setPower(0.7); //SEVEN TENTHS OF THE POWER
        } else if (gamepad1.left_bumper) {
            Lift.setPower(-0.1); //TENTH OF THE POWER
        }
        else {
            Lift.setPower(0);
        }

        //TELEMETRY
        telemetry.addData("Aleph Bots Robot:", "Running!");
        telemetry.addData("Driving Mode:", drive.getDriveMode());
        telemetry.addData("Driving Speed:", drive.getDriveSpeed());
        telemetry.addData("ButtonPresser position:", ButtonPresser.getPosition());
        telemetry.addData("LTouchServo position:", LTouchServo.getPosition());
        telemetry.addData("RTouchServo position:", RTouchServo.getPosition());
        telemetry.update();
    }
}