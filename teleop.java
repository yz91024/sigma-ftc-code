package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


@TeleOp(name="TeleOp", group="Linear Opmode")

public class teleop extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private DcMotor intakeMotor;
    private CRServo leftWheel;
    private CRServo rightWheel;

    @Override
    public void runOpMode() {

        ElapsedTime runtime = new ElapsedTime();
        ElapsedTime keepPhoneAlive = new ElapsedTime();
        ElapsedTime LiftRuntime = new ElapsedTime();
        keepPhoneAlive.reset();

        initialize();

        while(!opModeIsActive())
        {
            telemetry.addData("KEEPS THE CONNECTION TO THE PHONE",keepPhoneAlive);
            telemetry.update();
        }
        runtime.reset();
        LiftRuntime.reset();
        double inc = 1;
        double inc2 = 1;
        while (opModeIsActive()) {
            telemetry.addData("What is the incremental at",inc);
            telemetry.addData("THE INTAKE INCREMENTAL IS",inc2);
            telemetry.update();
            if (gamepad1.right_bumper) {
                inc = 1;
            }
            if (gamepad1.left_bumper) {
                inc = .5;
            }
            if(gamepad2.dpad_up)
            {
                inc2 = 1;
            }
            if(gamepad2.dpad_down)
            {
                inc2 = .75;
            }
            double speed = scale(inc * -gamepad1.right_stick_y);
            double turn = scale(inc * gamepad1.right_stick_x);
            double strafe = scale(inc * -gamepad1.left_stick_x);

            double fl = (speed + turn - strafe);
            double fr = (speed - turn + strafe);
            double bl = (speed + turn + strafe);
            double br = (speed - turn - strafe);

            frontLeft.setPower(Range.clip(fl, -1, 1));
            frontRight.setPower(Range.clip(fr, -1, 1));
            backLeft.setPower(Range.clip(bl, -1, 1));
            backRight.setPower(Range.clip(br, -1, 1));

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
            intakeMotor.setPower(.35 * gamepad2.left_stick_y);
            leftMotor.setPower(inc2 * gamepad2.right_stick_y);
            rightMotor.setPower(inc2 * gamepad2.right_stick_y);
            if(gamepad2.a)
            {
                rightWheel.setDirection(CRServo.Direction.REVERSE);
                leftWheel.setDirection(CRServo.Direction.FORWARD);
                leftWheel.setPower(1);
                rightWheel.setPower(1);
            }
            if(gamepad2.x)
            {
                rightWheel.setDirection(CRServo.Direction.FORWARD);
                leftWheel.setDirection(CRServo.Direction.REVERSE);
                leftWheel.setPower(1);
                rightWheel.setPower(1);
            }
            if (gamepad2.y)
            {
                leftWheel.setPower(0);
                rightWheel.setPower(0);
            }
            /*
            if(gamepad2.left_bumper)
            {
                intakeMotor.setPower(.4);
                sleep(500);
                intakeMotor.setPower(0);
                sleep(500);
                intakeMotor.setPower(-.4);
                sleep(1250);
                intakeMotor.setPower(0);
                rightWheel.setDirection(CRServo.Direction.FORWARD);
                leftWheel.setDirection(CRServo.Direction.REVERSE);
                leftWheel.setPower(1);
                rightWheel.setPower(1);
            }
            if(gamepad2.right_bumper)
            {
                intakeMotor.setPower(-.4);
                sleep(1250);
                intakeMotor.setPower(0);
            }
            */
        }

    }

    private double scale(double input) {
        double scaledInput = Math.pow(input, 1.0);
        if(1.0 % 2 == 0) {
            if(input >= 0) {
                return scaledInput;
            } else {
                return -scaledInput;
            }
        }
        return scaledInput;
    }

    private void initialize() {
        frontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        backLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        backRight = hardwareMap.get(DcMotor.class, "BackRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        leftMotor = hardwareMap.get(DcMotor.class, "LeftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "RightMotor");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        intakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);

        leftWheel = hardwareMap.get(CRServo.class, "Left Servo");
        rightWheel = hardwareMap.get(CRServo.class, "Right Servo");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

}