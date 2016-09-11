package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Supreme Leader Jacob Davoudgoleh on 11/15/15.
 */
public class Autonomous extends LinearOpMode {

    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor leftDriveB;
    DcMotor rightDriveB;
    Servo climberServo;
    private static double degreeMultiplier = 0.144444;
    public static  double tickMultiplier = 114.6496; //1440 revolutions per rotation
    //circumference = diameter * pi -> c = 4 * 3.14 = 12.56 -> 1 full wheel rotation is 12.56 inches -> 1 inch = 0.079618
    //-> 0.079618 * 1440 = 114.6496
    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    public void runOpMode() throws InterruptedException {

        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDriveB = hardwareMap.dcMotor.get("leftDriveB");
        rightDriveB = hardwareMap.dcMotor.get("rightDriveB");
        climberServo = hardwareMap.servo.get("climberServo");


        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDriveB.setDirection(DcMotor.Direction.REVERSE);


        while(rightDrive.getCurrentPosition() != 0 || rightDriveB.getCurrentPosition() != 0 || leftDrive.getCurrentPosition() != 0 || leftDriveB.getCurrentPosition() != 0) {

            leftDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            leftDriveB.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightDriveB.setMode(DcMotorController.RunMode.RESET_ENCODERS);

            telemetry.clearData();
            telemetry.addData("Resetting", "Encoders");
            telemetry.addData("RD", rightDrive.getCurrentPosition() + " / " + rightDrive.getTargetPosition());
            telemetry.addData("RDB", rightDriveB.getCurrentPosition() + " / " + rightDriveB.getTargetPosition());
            telemetry.addData("LD", leftDrive.getCurrentPosition() + " / " + leftDrive.getTargetPosition());
            telemetry.addData("LDB", leftDriveB.getCurrentPosition() + " / " + leftDriveB.getTargetPosition());

            waitOneFullHardwareCycle();
        }
        telemetry.clearData();
        telemetry.addData("On", "Wait for start");

        telemetry.addData("RD", rightDrive.getCurrentPosition() + " / " + rightDrive.getTargetPosition());
        telemetry.addData("RDB", rightDriveB.getCurrentPosition() + " / " + rightDriveB.getTargetPosition());
        telemetry.addData("LD", leftDrive.getCurrentPosition() + " / " + leftDrive.getTargetPosition());
        telemetry.addData("LDB", leftDriveB.getCurrentPosition() + " / " + leftDriveB.getTargetPosition());

        waitForStart();


        turn(RIGHT, 45);
        //dropClimbers();

    }
    public void turn(int direction, double degrees) throws InterruptedException {
        if(direction==RIGHT){
            driveEncoders(-0.5, 0.5, degrees * degreeMultiplier);
        }
        if(direction==LEFT){
            driveEncoders(0.5, -0.5, degrees * degreeMultiplier);
        }

    }
    public void driveEncoders(double leftPower, double rightPower, double distance) throws InterruptedException {
        double leftDistance = distance;
        if(leftPower < 0){
            leftDistance = -leftDistance;
        }
        double rightDistance = distance;
        if(rightPower < 0){
            rightDistance = -rightDistance;
        }


        while(rightDrive.getCurrentPosition() != 0 || rightDriveB.getCurrentPosition() != 0 || leftDrive.getCurrentPosition() != 0 || leftDriveB.getCurrentPosition() != 0) {

            leftDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
            leftDriveB.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightDriveB.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

            telemetry.clearData();
            telemetry.addData("Resetting", "Encoders");
            telemetry.addData("RD", rightDrive.getCurrentPosition() + " / " + rightDrive.getTargetPosition());
            telemetry.addData("RDB", rightDriveB.getCurrentPosition() + " / " + rightDriveB.getTargetPosition());
            telemetry.addData("LD", leftDrive.getCurrentPosition() + " / " + leftDrive.getTargetPosition());
            telemetry.addData("LDB", leftDriveB.getCurrentPosition() + " / " + leftDriveB.getTargetPosition());

            waitOneFullHardwareCycle();
        }

        telemetry.clearData();
        telemetry.addData("Reset", "Completed");
        telemetry.addData("RD", rightDrive.getCurrentPosition() + " / " + rightDrive.getTargetPosition());
        telemetry.addData("RDB", rightDriveB.getCurrentPosition() + " / " + rightDriveB.getTargetPosition());
        telemetry.addData("LD", leftDrive.getCurrentPosition() + " / " + leftDrive.getTargetPosition());
        telemetry.addData("LDB", leftDriveB.getCurrentPosition() + " / " + leftDriveB.getTargetPosition());


        sleep(1000);

        telemetry.clearData();
        telemetry.addData("Power", "0");
        telemetry.addData("Waiting", "1 Second");

        sleep(1000);

        rightDrive.setTargetPosition(((int) ((rightDistance /*+ rightDrive.getCurrentPosition()*/) * tickMultiplier))); //may need to cast inches to int or long idk also may be in other units
        rightDriveB.setTargetPosition(((int) ((rightDistance /*+ rightDriveB.getCurrentPosition()*/) * tickMultiplier)));
        leftDrive.setTargetPosition(((int) ((leftDistance /*+ leftDrive.getCurrentPosition()*/) * tickMultiplier)));
        leftDriveB.setTargetPosition(((int) ((leftDistance /*+ leftDriveB.getCurrentPosition()*/) * tickMultiplier)));

        leftDrive.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightDrive.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        leftDriveB.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        rightDriveB.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        telemetry.clearData();
        telemetry.addData("Mode", "Run To Position");
        telemetry.addData("RD", rightDrive.getCurrentPosition() + " / " + rightDrive.getTargetPosition());
        telemetry.addData("RDB", rightDriveB.getCurrentPosition() + " / " + rightDriveB.getTargetPosition());
        telemetry.addData("LD", leftDrive.getCurrentPosition() + " / " + leftDrive.getTargetPosition());
        telemetry.addData("LDB", leftDriveB.getCurrentPosition() + " / " + leftDriveB.getTargetPosition());

        sleep(1000);

        rightDrive.setPower(rightPower);
        rightDriveB.setPower(rightPower);
        leftDrive.setPower(leftPower);
        leftDriveB.setPower(leftPower);

        while(rightDrive.isBusy() || rightDriveB.isBusy() || leftDrive.isBusy() || leftDriveB.isBusy()) {

            telemetry.clearData();
            telemetry.addData("Mode", "Run To Position");
            telemetry.addData("RP", rightDrive.getPower());
            telemetry.addData("LP", leftDrive.getPower());
            telemetry.addData("RD", rightDrive.getCurrentPosition() + " / " + rightDrive.getTargetPosition());
            telemetry.addData("RDB", rightDriveB.getCurrentPosition() + " / " + rightDriveB.getTargetPosition());
            telemetry.addData("LD", leftDrive.getCurrentPosition() + " / " + leftDrive.getTargetPosition());
            telemetry.addData("LDB", leftDriveB.getCurrentPosition() + " / " + leftDriveB.getTargetPosition());

        }

        telemetry.clearData();
        telemetry.addData("Power", "0");
        telemetry.addData("Mode", "Run To Position");
        telemetry.addData("RP", rightDrive.getPower());
        telemetry.addData("LP", leftDrive.getPower());
        telemetry.addData("RD", rightDrive.getCurrentPosition() + " / " + rightDrive.getTargetPosition());
        telemetry.addData("RDB", rightDriveB.getCurrentPosition() + " / " + rightDriveB.getTargetPosition());
        telemetry.addData("LD", leftDrive.getCurrentPosition() + " / " + leftDrive.getTargetPosition());
        telemetry.addData("LDB", leftDriveB.getCurrentPosition() + " / " + leftDriveB.getTargetPosition());


        sleep(2000);


    }

    public void resetEncoders(){
        while(leftDrive.getCurrentPosition() != 0 || rightDrive.getCurrentPosition() != 0 || leftDriveB.getCurrentPosition() != 0 || rightDriveB.getCurrentPosition() != 0) { //Ensures encoders are zero
            leftDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
            leftDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightDrive.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
    }
    public void useEncoders(){
        leftDrive.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightDrive.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftDriveB.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightDriveB.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
    }
    public void operate(Servo ser, double position, long wait){
        ser.setPosition(position);
        try {
            Thread.sleep(wait);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        ser.setPosition(0.5);
    }
    public void dropClimbers(){
        operate(climberServo,0.4,800);
    }
}