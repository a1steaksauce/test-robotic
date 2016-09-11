package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by JacobDavoudgoleh on 11/11/15.
 */
public class AutonomousDriving {
    private DcMotor leftFront; //Defines the four motors
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    public AutonomousDriving(DcMotor LF, DcMotor LB, DcMotor RF, DcMotor RB){
        //Creates what an instance of this class should look like

        leftFront = LF; //Sets the four motors to the parameters. When an instance of RobotDriving is created,
        leftBack = LB;  //the parameters are the hardware mappings for the four motors
        rightFront = RF;
        rightBack = RB;

        //Sets the two right motors to revers because they are reverse direction from the left motors
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
    }
    public void drive(double leftPower, double rightPower, double seconds){ //Method for driving on right side with parameters for power and seconds
        leftFront.setPower(leftPower); //Set the right wheel to -power because right side has to be negative
        leftBack.setPower(leftPower);
        rightFront.setPower(rightPower);
        rightBack.setPower(rightPower);
        try {
            // thread to sleep for 1000 milliseconds
            Thread.sleep(Math.round(seconds*1000)); //Wait for seconds * 1000 to get milliseconds
        } catch (Exception e) {
            System.out.println(e);
        }
        leftFront.setPower(0); //Set values to 0
        leftBack.setPower(0);
        rightFront.setPower(0); //Set values to 0
        rightBack.setPower(0);
    }
}
