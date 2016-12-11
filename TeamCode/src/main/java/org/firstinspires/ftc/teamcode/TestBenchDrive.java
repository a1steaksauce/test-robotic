package org.firstinspires.ftc.teamcode;
        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.eventloop.opmode.Disabled;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.robot.Robot;
        import com.qualcomm.robotcore.util.Range;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.*;

        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorController;
        import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by alexbulanov on 9/15/16.
 */
@TeleOp(name="TestBenchDrive", group="TeleOp")
public class TestBenchDrive extends OpMode {

    DcMotor mtr;
    Servo srv;


    @Override
    public void init () {
        mtr = hardwareMap.dcMotor.get("mtr");
        srv = hardwareMap.servo.get("srv");
    }

    @Override
    public void loop() {

        float value = gamepad1.left_stick_y;


        float power = Range.clip(value, -1, 1);

       // float leftY = -gamepad1.left_stick_y;
       // float rightY = gamepad1.right_stick_y;

        mtr.setPower(value);

    }

}