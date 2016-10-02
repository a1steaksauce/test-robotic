package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by Eliezer Pearl on 9/9/2016.
 */
@TeleOp(name="mainBot: Teleop Tank 2")
//@Disabled //uncomment to disable, comment to enable
public class TeleOpHutz extends LinearOpMode {
    final double DEAD_ZONE = 0.08; //Change this value through debugging

    DcMotor topLeft; //All motor names are given based on location from a top down
    DcMotor topRight;//view of the robot
    DcMotor botLeft;
    DcMotor botRight;
    DcMotor flywheel;//operates the gearbox
    /////////////////////////////////////////
    Servo buttonPush;//pushes buttons on beacon
    /////////////////////////////////////////
    UltrasonicSensor us;
    ColorSensor lineTracker; //pointed at floor
    ColorSensor beaconDetector; //pointed at beacon


    public TeleOpHutz() {  //just here, don't touch

    }


    @Override
    public void runOpMode() {  //Executed in a linear format
        topLeft = hardwareMap.dcMotor.get("topL");        //sets code motors to point to
        topRight = hardwareMap.dcMotor.get("topR");       //actual ones. use the names
        botLeft = hardwareMap.dcMotor.get("botL");        //in config as the string vals
        botRight = hardwareMap.dcMotor.get("botR");

        topLeft.setDirection(DcMotor.Direction.REVERSE);  //just for ease of programming since
        topRight.setDirection(DcMotor.Direction.REVERSE); //left motors are backwards

        while (true) {
            /*
                Essentially, the following code makes sure that
                a joystick is being moved for real (hence
                the deadzone, since a stationary joystick can
                sometimes give nonzero values), and then sets a
                joystick's y value to the corresponding motors'
                power. So left joystick moves left motors,
                right joystick moves right motors.
            */
            if (Math.abs(gamepad1.left_stick_y) > DEAD_ZONE) {
                topLeft.setPower(gamepad1.left_stick_y);
                botLeft.setPower(gamepad1.left_stick_y);
            }
            if (Math.abs(gamepad1.right_stick_y) > DEAD_ZONE) {
                topRight.setPower(gamepad1.right_stick_y);
                botRight.setPower(gamepad1.right_stick_y);
            }

            while (gamepad1.dpad_up){
                flywheel.setPower(1); //note that this is max power also might need to reverse motor if spins inwards
            }
            while (gamepad1.left_bumper) {
                if(buttonPush.getPosition() != 1)
                    buttonPush.setPosition(1); //or -1... debug
            }
            while (gamepad1.right_bumper) {
                if(buttonPush.getPosition() != -1)
                    buttonPush.setPosition(-1); //or 1... debug
            }
            buttonPush.setPosition(0.5);

            /*Telemetry is basically System.out.println() for
            robots. For example, telemetry.addData("Text", "*** Robot Data***");
            will display "Text: *** Robot Data***" on the
            driver's station.
            */
            telemetry.addData("Left joystick Y val", gamepad1.left_stick_y);
            telemetry.addData("Right joystick Y val", gamepad1.right_stick_y);
        }
    }
}

