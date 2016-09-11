package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.*;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by alexbulanov on 11/1/15.
 * and edited by eliezerpearl on days afterwards.
 */
public class AtonomousE extends OpMode

{
    int MOTOR_FORWARD = 1; //Variable for moving forward
    int MOTOR_BACKWARD = 1;//Variable for moving backwards
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor leftDriveB;
    DcMotor rightDriveB;

    @Override public void init() {
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDriveB = hardwareMap.dcMotor.get("rightDriveB");
        leftDriveB = hardwareMap.dcMotor.get("leftDriveB");


        //motor power values is between -1 and 1

        drive(true, 5);
    }
    @Override public void loop ()
    {

    }
    @Override public void stop () {
    }
    private int defaultPower = 1;
    public void drive(boolean forwards, int time){
        drive(forwards, defaultPower, time);
    }
    public void set(int p, boolean neg1, boolean neg2, boolean neg3, boolean neg4)   //true is pos, false is neg
    {
        rightDrive.setPower(p);
        rightDriveB.setPower(p);
        leftDrive.setPower(p);
        leftDriveB.setPower(p);
        if(!neg1){
            rightDrive.setPower(-p);
        }
        else if(!neg2){
            rightDriveB.setPower(-p);
        }
        else if(!neg3){
            leftDrive.setPower(-p);
        }
        else if(!neg4){
            leftDriveB.setPower(-p);
        }
    }//Makes it easier to set motor
    public void drive(boolean forwards, int power, int time)
    {
        if(forwards){  //Forwards
            set(power, true, true, true, true);
        }
        else{  //Backwards
            set(power, false, false, false, false);
        }
        try {
            Thread.sleep(time);  //milliseconds is better for more precise time, if needed
        }
        catch (Exception e) {
            System.out.println(e);
        }
        set(0, true, true, true, true);
    }
    public void turn(boolean left, int degrees)
    {
        turn(left, defaultPower, degrees);
    }
    public void turn(boolean lRight, int power, int degrees)
    {
        int p;

        if(lRight){
            p = power;
        }
        else
        {
            p = -power;
        }
        set(p, true, true, false, false);  //left is bool left is true, right if false.
        try {
            Thread.sleep(1000/*placeholder*/*degrees);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        p = 0;
        set(p, false, false, true, true); //stops bot
    }
}
