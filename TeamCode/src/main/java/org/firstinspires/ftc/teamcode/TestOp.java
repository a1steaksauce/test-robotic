package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Eliezer Pearl on 9/9/2016.
 */
public class TestOp extends OpMode{
    final int DEAD_ZONE = 8; //Change this value through debugging

    DcMotor topLeft; //All motor names are given based on location from a top down
    DcMotor topRight;//view of the robot
    DcMotor botLeft;
    DcMotor botRight;

    public TestOp(){  //just here, don't touch

    }


    @Override
    public void init() {  //Executed when "arm" button pressed
        topLeft = hardwareMap.dcMotor.get("topL");        //sets code motors to point to
        topRight = hardwareMap.dcMotor.get("topR");       //actual ones. use the names
        botLeft = hardwareMap.dcMotor.get("botL");        //in config as the string vals
        botRight = hardwareMap.dcMotor.get("botR");

        topLeft.setDirection(DcMotor.Direction.REVERSE);  //just for ease of programming since
        topRight.setDirection(DcMotor.Direction.REVERSE); //left motors are backwards
    }
    @Override
    public void start(){  //Executed when "play" button pushed
    }
    @Override
    public void loop() {  //Executed regularly after start
        /*
            Essentially, the following code makes sure that
            a joystick is being moved for real (hence
            the deadzone, since a stationary joystick can
            sometimes give nonzero values), and then sets a
            joystick's y value to the corresponding motors'
            power. So left joystick moves left motors,
            right joystick moves right motors.
         */
        if(Math.abs(gamepad1.left_stick_y) > DEAD_ZONE) {
            topLeft.setPower(gamepad1.left_stick_y);
            botLeft.setPower(gamepad1.left_stick_y);
        }
        if(Math.abs(gamepad1.right_stick_y) > DEAD_ZONE) {
            topRight.setPower(gamepad1.right_stick_y);
            botRight.setPower(gamepad1.right_stick_y);
        }

        /*Telemetry is basically System.out.println() for
          robots. For example, telemetry.addData("Text", "*** Robot Data***");
          will display "Text: *** Robot Data***" on the
          driver's station.
         */
        telemetry.addData("Left joystick Y val", gamepad1.left_stick_y);
        telemetry.addData("Right joystick Y val", gamepad1.right_stick_y);
    }
    @Override
    public void stop(){  //Executed when "stop" button pushed

    }
}
