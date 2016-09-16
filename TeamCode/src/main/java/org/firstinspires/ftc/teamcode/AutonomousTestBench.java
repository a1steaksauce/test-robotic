package org.firstinspires.ftc.robotcontroller.external.samples;


        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by alexbulanov on 9/15/16.
 */
@Autonomous(name="TestBench Autonomous")

public class AutonomousTestBench extends LinearOpMode {

    DcMotor left = null;
    DcMotor right = null;

public void runOpMode() throws InterruptedException {

    left = hardwareMap.dcMotor.get("left");
    right = hardwareMap.dcMotor.get("right");

    right.setDirection(DcMotor.Direction.REVERSE);

    drive(1);
    Thread.sleep(1000);

    turnLeft(1);
    Thread.sleep(1000);

    turnRight(1);
    Thread.sleep(1000);

    stopDrive();


    waitForStart();
    idle();
}

    public void drive(double power) {
        left.setPower(power);
        right.setPower(power);
}
    public void turnLeft(double power) {
        left.setPower(-power);
        right.setPower(power);
    }

    public void turnRight(double power) {
        left.setPower(power);
        right.setPower(-power);
    }

    public void stopDrive() {
        left.setPower(0);
        right.setPower(0);
    }

/*public class AutonomousTestBench {

    DcMotor left;
    DcMotor right;

    public  AutonomousTestBench() {

        right.setDirection(DcMotor.Direction.REVERSE);
*/

    }


