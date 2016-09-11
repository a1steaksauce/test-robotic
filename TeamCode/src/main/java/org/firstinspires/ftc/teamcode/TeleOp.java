package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.Range;

public class TeleOp extends OpMode {
    RobotDriving drive;
    Servo climberServo;
    Servo buttonServo;
    Servo angler;
    Servo hookServo;
    DcMotor lift;

    @Override
    public void init() {
        drive = new RobotDriving(hardwareMap.dcMotor.get("leftDrive"), hardwareMap.dcMotor.get("leftDriveB"), hardwareMap.dcMotor.get("rightDrive"), hardwareMap.dcMotor.get("rightDriveB"));
        //servos = new RobotServos(hardwareMap.servo.get("buttonServo"), hardwareMap.servo.get("climberServo"));
        climberServo = hardwareMap.servo.get("climberServo");

        buttonServo = hardwareMap.servo.get("buttonServo");
        angler = hardwareMap.servo.get("angler");
        hookServo = hardwareMap.servo.get("hookServo");
        //angler.setPosition(0.5);
        lift = hardwareMap.dcMotor.get("lift");
        hookServo.setPosition(0.99);
        climberServo.setPosition(0.5);

    }


    @Override
    public void loop()

    {
        drive.tankDrive(gamepad1);
        if (gamepad1.dpad_up) { //If up dpad is pressed, change the climberServo
            climberServo.setPosition(0.6);//Value may need to be changed
        } else if (gamepad1.dpad_down) {//If dpad down is pressed, change the climberServo
            climberServo.setPosition(0.4);//Value may need to be changed
        } else {
            climberServo.setPosition(0.5);
        }

        if (gamepad1.b) { //If up dpad is pressed, change the climberServo
            buttonServo.setPosition(0.6);//Value may need to be changed
        } else {//If dpad down is pressed, change the climberServo
            buttonServo.setPosition(0.3);//Value may need to be changed
        }

        if (gamepad1.right_bumper) {
            lift.setPower(0.5);
        } else if (gamepad1.left_bumper) {
            lift.setPower(-0.5);
        } else {
            lift.setPower(0);
        }

        /*if(gamepad1.y){ //Moves up and down 0.1 each time
            if(angler.getPosition() <= 0.9) {
                angler.setPosition(angler.getPosition() + 0.1);
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    System.err.println(e);
                    System.exit(1);
                }
            }
        }
        else if(gamepad1.a){
            if(angler.getPosition() >= 0.1) {
                angler.setPosition(angler.getPosition() - 0.1);
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    System.err.println(e);
                    System.exit(1);
                }
            }
        }*/

        if (gamepad1.y) {
            if (angler.getPosition() <= 0.99) {
                angler.setPosition(angler.getPosition() + 0.01);
            }
        } else if (gamepad1.a) {
            if (angler.getPosition() >= 0.01) {
                angler.setPosition(angler.getPosition() - 0.01);



            }
        }
        if (gamepad2.dpad_up) {
            hookServo.setPosition(0.01);

        if (gamepad2.dpad_down) {
            hookServo.setPosition(0.99);
        }
    }
        telemetry.addData("Hook Position", hookServo.getPosition());

    }
}