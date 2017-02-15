package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by JacobDavoudgoleh on 11/11/15.
 */
public class AlephBotsRobotDriving {
    private DcMotor leftDrive; //Defines the four motors
    private DcMotor leftDriveB;
    private DcMotor rightDrive;
    private DcMotor rightDriveB;
    private Boolean normalDrive = true;
    private Boolean normalSpeed = true;

    double quarterPI = Math.PI / 4;

    public AlephBotsRobotDriving(DcMotor LF, DcMotor LB, DcMotor RF, DcMotor RB){
    //Creates what an instance of this class should look like

        leftDrive = LF; //Sets the four motors to the parameters. When an instance of AlephBotsRobotDriving is created,
        leftDriveB = LB;  //the parameters are the hardware mappings for the four motors
        rightDrive = RF;
        rightDriveB = RB;

        //Sets the two right motors to reverse because they are reverse direction from the left motors, but the
        //joysticks are in the same direction

        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDriveB.setDirection(DcMotor.Direction.REVERSE);
    }

    public double atan2(double x, double y){
        double phi = 0;   //phi=radians;

        if (x>0) {phi=Math.atan(y/x);}
        else
        if ((x<0)&&(y>=0))  {phi=Math.PI+Math.atan(y/x);}
        else
        if ((x<0)&&(y<0))   {phi=-Math.PI+Math.atan(y/x);}
        else
        if ((x==0)&&(y>0))  {phi=Math.PI/2;}
        else
        if ((x==0)&&(y<0))  {phi=-Math.PI/2;}
        else
        if ((x==0)&&(y==0)) {phi=0;}
        return phi;
    }
    public void mechanumDrive(double speed, double theta, double factor, double rotate) {
        leftDrive.setPower(speed * -Math.sin(theta + quarterPI) * factor - (rotate * factor));
        rightDriveB.setPower(speed * -Math.sin(theta + quarterPI) * factor + (rotate * factor));
        rightDrive.setPower(speed * + Math.cos(theta + quarterPI) * factor + (rotate * factor));
        leftDriveB.setPower(speed * + Math.cos(theta + quarterPI) * factor - (rotate * factor));
    }

    public void mechanumDrive(Gamepad gamepad) {
        double speed, theta;
        double rotate;
        int DrivingScale = 1;
        speed = (Math.sqrt(Math.pow(gamepad.left_stick_x, 2) + Math.pow(gamepad.left_stick_y, 2)));
        if (speed > 10){
            theta = atan2(gamepad.left_stick_y,gamepad.left_stick_x);
        }
        else // The joystick values are small enough to fail
        {
            theta = 0; speed = 0;
        }
        if (Math.abs(gamepad.right_stick_x) > 10){
            rotate = gamepad.right_stick_x;
        }
        else // No rotation is detected
        {
            rotate = 0;
        }
        mechanumDrive(speed, theta, DrivingScale, rotate);
    }
    public void tankDrive(double leftPower, double rightPower){ //Function for driving with parameters leftDrive and RightDrive
        leftPower = Range.clip(leftPower, -1, 1); //Clip the values
        rightPower = Range.clip(rightPower,-1,1);

        /*if(!normalDrive && ((leftPower < 0.01 && rightPower > -0.01) || ((leftPower > -0.01 && rightPower < 0.01)))) {
            leftDrive.setPower(-leftPower); //Sets the motors to the correct power on their side
            leftDriveB.setPower(-leftPower);
            rightDrive.setPower(-rightPower);
            rightDriveB.setPower(-rightPower);
        }*/
        leftDrive.setPower(leftPower); //Sets the motors to the correct power on their side
        leftDriveB.setPower(leftPower);
        rightDrive.setPower(rightPower);
        rightDriveB.setPower(rightPower);
    }
    public void tankDrive(Gamepad gamepad){
        //Function that calls the other tankDrive with parameters of which gamepad to use
        if(normalDrive && normalSpeed){
            tankDrive(gamepad.left_stick_y, gamepad.right_stick_y); //gets values from joysticks and sets the motors
        } else if(normalDrive && !normalSpeed){
            tankDrive(0.25 * gamepad.left_stick_y,  0.25 * gamepad.right_stick_y);
        } else if(!normalDrive && normalSpeed){
            tankDrive(-gamepad.right_stick_y, -gamepad.left_stick_y);
        } else{
            tankDrive(0.25 * -gamepad.right_stick_y,  0.25 * -gamepad.left_stick_y);
        }
    }
    public void changeDriveMode(Boolean setNormalDriveTrue, Boolean setNormalDriveFalse){
        if(setNormalDriveTrue){
            normalDrive = true;
        } else if (setNormalDriveFalse){
            normalDrive = false;
        }
    }
    public void changeDriveSpeed(Boolean fullSpeed, Boolean halfSpeed){
        if(fullSpeed){
            normalSpeed = true;
        } else if (halfSpeed){
            normalSpeed = false;
        }
    }
    public String getDriveMode(){
        if(normalDrive){
            return "Normal Drive";
        } else {
            return "Backwards Drive";
        }
    }
    public String getDriveSpeed(){
        if(normalSpeed) {
            return "Full Speed";
        } else {
            return "Half Speed";
        }
    }
    /*public void setServoToggle(Servo servo, boolean firstButton, double firstValue, boolean secondButton, double secondValue){
        if(firstButton){
            servo.setPosition(firstValue);
        } else if (secondButton){
            servo.setPosition(secondValue);
        }
    }*/

}