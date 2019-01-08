package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

public class HardwareSigma {

    public static DcMotor frontLeft;
    public static DcMotor frontRight;
    public static DcMotor backLeft;
    public static DcMotor backRight;
    public static DcMotor leftMotor;
    public static DcMotor rightMotor;
    public static DcMotor intakeMotor;
    public static CRServo leftWheel;
    public static CRServo rightWheel;

    public static void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        backLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        backRight = hardwareMap.get(DcMotor.class, "BackRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotor = hardwareMap.get(DcMotor.class, "LeftMotor");
        rightMotor = hardwareMap.get(DcMotor.class, "RightMotor");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);


        intakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);

        leftWheel = hardwareMap.get(CRServo.class, "Left Servo");
        rightWheel = hardwareMap.get(CRServo.class, "Right Servo");
    }

}
