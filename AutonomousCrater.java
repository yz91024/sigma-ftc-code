package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


@Autonomous(name="AutonomousCrater", group="Linear Opmode")

public class AutonomousCrater extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    boolean hasGold = false;

    private static final String VUFORIA_KEY = "AZ/y08v/////AAABmW2F+VgsfEqurugLGLrbk3xyH7N9tvzPux4eQg9rPK+SIv5GDmkrIx0vgN" +
            "TWk38gl/twDCosIHE+QKNfrRJ52UEUApnutRqNvEbblyi/uhiqOnJsEBVJnZeiI/Ix+ZZdt2i7g+juzZqYVINYv1p0mOWPDdP" +
            "L77UyWLdwdeHKYe7LJo3SbAbzrH5enUwDRalJ2MmSsXg3xm9rXJlS1RQ2RoDSIVhh101KgF33QlDFnK/8yBRqHbEMfxsb5df8" +
            "gIWnFv/wkQWwUFd1fH/w0VWLjWfX5O5HuvAZJ5fDSq2rVy+i0EbKLsXn/heEQuRJgU409sMEpOKxMYX7em673DL9qP7A3p6dp" +
            "SeoOTH5QDmcv0EJ";

    VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    ElapsedTime pc = new ElapsedTime();

    @Override
    public void runOpMode() {

        //initalize motors, sensors, and servos
        initialize();
        initCam();
        pc.reset();

        //wait until program starts
        while(!opModeIsActive()) {
            telemetry.addData("KEEPING THE PHONE CONNECTION ALIVE ",pc);
            telemetry.update();
        }
        waitForStart();
        runtime.reset();

        String x = ImageSensing();

        if(x.equals("Center")) {
            moveToPos(-.5, .5, -.85, .85);
            pause();
            moveToPos(.5, .5, 38, 38);
            pause();
            moveToPos(-.5, -.5, -8, -8);
            pause();
            moveToPos(-.5, .5, -28, 28);
            pause();
            moveToPos(-.5, -.5, -48, -48);
            pause();
            moveToPos(.5, -.5, 16, -16);
            pause();
            moveToPos(-.5, -.5, -40, -40);
            pause();
            dropMarker();
            pause();
            moveToPos(1, 1, 40, 40);
            pause();
            moveToPos(.5, -.5, 0.7, -0.7);
            pause();
            moveToPos(1, 1, 32, 32);
            pause();
        }
        if(x.equals("Right")) {
            moveToPos(-.5,.5,-7, 7);
            pause();
            moveToPos(.5,.5,34,34);
            pause();
            moveToPos(-.5, -.5, -10, -10);
            pause();
            moveToPos(-.5, .5, -22, 22);
            pause();
            moveToPos(-.5, -.5, -60, -60);
            pause();
            moveToPos(.5, -.5, 15, -15);
            pause();
            moveToPos(-.5, -.5, -40, -40);
            pause();
            dropMarker();
            pause();
            moveToPos(1, 1, 40, 40);
            pause();
            moveToPos(-.5, .5, -60, 60);
            pause();
            moveToPos(-1, -1, -22, -22);
            pause();
            HardwareSigma.intakeMotor.setPower(.25);
            sleep(500);
            HardwareSigma.intakeMotor.setPower(0);
        }
        if(x.equals("Left")) {
            moveToPos(.5, -.5, 1.5, -1.5);
            pause();
            moveToPos(.5, .5, 34, 34);
            pause();
            moveToPos(-.5, -.5, -8, -8);
            pause();
            moveToPos(-.5, .5, -40, 40);
            pause();
            moveToPos(-.5, -.5, -28, -28);
            pause();
            moveToPos(.5, -.5, 11, -11);
            pause();
            moveToPos(-.5, -.5, -54, -54);
            pause();
            dropMarker();
            pause();
            moveToPos(.5, -.5, 0.5, -0.5);
            pause();
            moveToPos(1, 1, 112, 112);
            pause();
        }

        //extendArm();
    }

    private void extendArm() {
        moveToPos(-.5, -.5, -10, -10);
        pause();
        moveToPos(-.5, .5, -60, 60);
        pause();
        moveToPos(-5, .5, 10, 10);
        pause();
        HardwareSigma.intakeMotor.setPower(.25);
        sleep(500);
        HardwareSigma.intakeMotor.setPower(0);
    }

    private void finish() {
        moveToPos(-0.1, -0.1, -2, -2);
        pause();
        moveToPos(-0.1, 0.1, -27, 27);
        pause();
        moveToPos(1, 1, 60, 60);
        pause();
    }

    public void dropMarker() {
        HardwareSigma.intakeMotor.setPower(.25);
        sleep(500);
        HardwareSigma.intakeMotor.setPower(0);
        outtake();
        HardwareSigma.intakeMotor.setPower(-0.5);
        sleep(1000);
        HardwareSigma.intakeMotor.setPower(0);
    }

    public void outtake()
    {
        HardwareSigma.leftWheel.setDirection(CRServo.Direction.REVERSE);
        HardwareSigma.rightWheel.setDirection(CRServo.Direction.FORWARD);
        HardwareSigma.leftWheel.setPower(1);
        HardwareSigma.rightWheel.setPower(1);
        sleep(2000);
        HardwareSigma.leftWheel.setPower(0);
        HardwareSigma.rightWheel.setPower(0);
    }

    private void initialize() {
        HardwareSigma.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    //move robot function. uses time. dont use this
    private void move(double leftPower, double rightPower, long ms) {
        HardwareSigma.frontLeft.setPower(leftPower);
        HardwareSigma.frontRight.setPower(rightPower);
        HardwareSigma.backLeft.setPower(leftPower);
        HardwareSigma.backRight.setPower(rightPower);
        sleep(ms);
        HardwareSigma.frontLeft.setPower(0);
        HardwareSigma.frontRight.setPower(0);
        HardwareSigma.backLeft.setPower(0);
        HardwareSigma.backRight.setPower(0);
    }

    private void pause() {
        sleep(700);
    }

    //move a certain amount in inches. inL and inR are amount of movement in inches for the left and right motors
    private void moveToPos(double leftPower, double rightPower, double inL, double inR) {
        HardwareSigma.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HardwareSigma.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HardwareSigma.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HardwareSigma.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        HardwareSigma.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        HardwareSigma.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        HardwareSigma.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        HardwareSigma.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        HardwareSigma.frontLeft.setPower(-leftPower);
        HardwareSigma.backLeft.setPower(-leftPower);
        HardwareSigma.frontRight.setPower(-rightPower);
        HardwareSigma.backRight.setPower(-rightPower);

        int targetL = convertToTicks(inL);
        int targetR = convertToTicks(inR);

        while (opModeIsActive()) {

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("encoder-frontLeft: ", HardwareSigma.frontLeft.getCurrentPosition());
            telemetry.addData("encoder-backLeft: ", HardwareSigma.backLeft.getCurrentPosition());
            telemetry.addData("encoder-frontRight: ", HardwareSigma.frontRight.getCurrentPosition());
            telemetry.addData("encoder-backRight: ", HardwareSigma.backRight.getCurrentPosition());
            telemetry.update();

            boolean POOOOOWER = false;
            boolean MOREPOOOOWER = false;
            if (targetL > 0) {
                if (HardwareSigma.frontLeft.getCurrentPosition() > targetL || HardwareSigma.backLeft.getCurrentPosition() > targetL) {
                    HardwareSigma.frontLeft.setPower(0);
                    HardwareSigma.backLeft.setPower(0);
                    POOOOOWER = true;
                }
            } else {
                if (HardwareSigma.frontLeft.getCurrentPosition() < targetL || HardwareSigma.backLeft.getCurrentPosition() < targetL) {
                    HardwareSigma.frontLeft.setPower(0);
                    HardwareSigma.backLeft.setPower(0);
                    POOOOOWER = true;
                }
            }
            if (targetR > 0) {
                if (HardwareSigma.frontRight.getCurrentPosition() > targetR || HardwareSigma.backRight.getCurrentPosition() > targetR) {
                    HardwareSigma.frontRight.setPower(0);
                    HardwareSigma.backRight.setPower(0);
                    MOREPOOOOWER = true;
                }
            } else {
                if (HardwareSigma.frontRight.getCurrentPosition() < targetR || HardwareSigma.backRight.getCurrentPosition() < targetR) {
                    HardwareSigma.frontRight.setPower(0);
                    HardwareSigma.backRight.setPower(0);
                    MOREPOOOOWER = true;
                }
            }

            if (POOOOOWER && MOREPOOOOWER) {
                break;
            }
        }

        HardwareSigma.frontLeft.setPower(0);
        HardwareSigma.backLeft.setPower(0);
        HardwareSigma.frontRight.setPower(0);
        HardwareSigma.backRight.setPower(0);
    }

    //convert ticks to inches
    private int convertToTicks(double in) {
        return (int) Math.round(in * (1440.0 / 12.556)); //inches times ratio of ticks to circumference
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = .2;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    public void initCam() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
    }

    public String ImageSensing() {
        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        String minColor = "";
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    telemetry.update();
                                    minColor = "Left";
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    telemetry.update();
                                    minColor = "Right";
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    telemetry.update();
                                    minColor = "Center";
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }
                                }
                            }
                            return minColor;
                        }
                        else if(updatedRecognitions.size() == 2)
                        {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for(Recognition recognition : updatedRecognitions)
                            {
                                if(recognition.getLabel().equals(LABEL_GOLD_MINERAL))
                                {
                                    goldMineralX = (int) recognition.getLeft();
                                }
                                else if(silverMineral1X == -1)
                                {
                                    silverMineral1X = (int) recognition.getLeft();
                                }
                                else
                                {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if((goldMineralX < silverMineral1X && goldMineralX < silverMineral2X))
                            {
                                telemetry.addData("Gold Mineral Position","Right");
                                telemetry.update();
                                minColor = "Right";
                                if (tfod != null) {
                                    tfod.shutdown();
                                }
                            }
                            else if(goldMineralX < silverMineral1X || goldMineralX < silverMineral2X)
                            {
                                telemetry.addData("Gold Mineral Position","Left");
                                telemetry.update();
                                minColor = "Left";
                                if (tfod != null) {
                                    tfod.shutdown();
                                }
                            }
                            else if(goldMineralX > silverMineral1X || goldMineralX > silverMineral2X)
                            {
                                telemetry.addData("Gold Mineral Position","Center");
                                telemetry.update();
                                minColor = "Center";
                                if (tfod != null) {
                                    tfod.shutdown();
                                }
                            }
                            return minColor;
                        }
                        //telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
        return "False";
    }
}