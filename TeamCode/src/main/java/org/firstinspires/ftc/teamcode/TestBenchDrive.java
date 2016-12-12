package org.firstinspires.ftc.teamcode;
        import com.qualcomm.hardware.adafruit.BNO055IMU;
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
    final float DEAD_ZONE = 0.05f;

    DcMotor mtr;
    Servo srv;
    OpticalDistanceSensor ods;
    TouchSensor touch;
    CompassSensor comp;
    @Override
    public void init () {
        mtr = hardwareMap.dcMotor.get("mtr");
        srv = hardwareMap.servo.get("srv");
        ods = hardwareMap.opticalDistanceSensor.get("ods");
        touch = hardwareMap.touchSensor.get("touch");
        comp = hardwareMap.compassSensor.get("comp");
    }

    @Override
    public void loop() {

        float value = gamepad1.left_stick_y;
        float power = Range.clip(value, -1, 1);

        if(value > DEAD_ZONE) {
            mtr.setPower(power);
        } else {
            mtr.setPower(0);
        }

        if(gamepad1.y) {
            srv.setPosition(1);
        } else if(gamepad1.a) {
            srv.setPosition(-1);
        }

        telemetry.addData("ODS light returned: ", ods.getLightDetected());
        telemetry.addData("Touch on? ", touch.isPressed());
        telemetry.addData("Compass direction: ", comp.getDirection());

        updateTelemetry(telemetry);
    }

}