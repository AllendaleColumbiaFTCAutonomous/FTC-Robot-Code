package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
/**
 * Created by nmor99 on 11/20/2016.
 * Autunomous Opmode for red tem when we wanna hit beacons and throw exactly one ball.
 */

public class RedBeaconsOneShotAutoOpMode extends LinearOpMode {

    /* Declare OpMode members. */
    HardwarePushbot         robot   = new HardwarePushbot();   // Use a Pushbot's hardware

    private ElapsedTime     runtime = new ElapsedTime();


    // static final double WHITE_THRESHOLD = 0.2;


    static final double     FORWARD_SPEED = 1.0;
    static final double     TURN_SPEED    = 1.0;
    static final double     SECONDS_PER_REVOLUTION = 2.6;

    @Override
    public void runOpMode() {


        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        // lightSensor = hardwareMap.opticalDistanceSensor.get("sensor_ods");
        // lightSensor.enableLed(true);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        robot.leftMotor.setPower(-1*TURN_SPEED);
        robot.rightMotor.setPower(TURN_SPEED);
        telemetry.addLine("Motor Power | ")
                .addData("Left", robot.leftMotor.getPower())
                .addData("Right", robot.rightMotor.getPower());
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION / 8.0)) {
            telemetry.addData("Path", "First Turn: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        goToBeaconLine();

        pressRedBeaconTurnRight();

        goToBeaconLine();

        pressRedBeaconTurnRight();

        robot.leftMotor.setPower(TURN_SPEED);
        robot.rightMotor.setPower(-1 * TURN_SPEED);
        telemetry.addLine("Motor Power | ")
                .addData("Left", robot.leftMotor.getPower())
                .addData("Right", robot.rightMotor.getPower());
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION / 8.0)) {
            telemetry.addData("Path", "Turning from Second Beacon: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        goToBeaconLine();

        pressRedBeaconTurnRight();

        goToBeaconLine();

        pressRedBeaconTurnRight();


        robot.leftMotor.setPower(TURN_SPEED);
        robot.rightMotor.setPower(-1 * TURN_SPEED);
        telemetry.addLine("Motor Power | ")
                .addData("Left", robot.leftMotor.getPower())
                .addData("Right", robot.rightMotor.getPower());
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION / 4.0)) {
            telemetry.addData("Path", "Turning from Final Beacon: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        robot.leftMotor.setPower(FORWARD_SPEED);
        robot.rightMotor.setPower(FORWARD_SPEED);
        telemetry.addLine("Motor Power | ")
                .addData("Left", robot.leftMotor.getPower())
                .addData("Right", robot.rightMotor.getPower());
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION / 4.0)) {
            telemetry.addData("Path", "Going to Shot Position: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        takeShot();

        robot.leftMotor.setPower(FORWARD_SPEED);
        robot.rightMotor.setPower(FORWARD_SPEED);
        telemetry.addLine("Motor Power | ")
                .addData("Left", robot.leftMotor.getPower())
                .addData("Right", robot.rightMotor.getPower());
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION)) {
            telemetry.addData("Path", "Running into our Cap Ball: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


        // Stop all motors
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);












    }

    public void pressRedBeaconTurnRight(){
        // VERY IMPORTANT CODE GOES HERE
        runtime.reset();

        while(opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION)) {
            robot.leftMotor.setPower(-1*TURN_SPEED);
            robot.rightMotor.setPower(TURN_SPEED);
            telemetry.addLine("Motor Power | ")
                    .addData("Left", robot.leftMotor.getPower())
                    .addData("Right", robot.rightMotor.getPower());
            telemetry.addData("Path", "Hitting the Red Beacon: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    public void goToBeaconLine(){
        robot.leftMotor.setPower(FORWARD_SPEED);
        robot.rightMotor.setPower(FORWARD_SPEED);
        telemetry.addLine("Motor Power | ")
                .addData("Left", robot.leftMotor.getPower())
                .addData("Right", robot.rightMotor.getPower());
        // while (opModeIsActive() && (lightSensor.getLightDetected() < WHITE_THRESHOLD)) {
            // Display the light level while we are looking for the line
            // telemetry.addData("Light Level",  lightSensor.getLightDetected());
            // telemetry.update();
        // }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION)) {
            telemetry.addData("Path", "Going to Beacon Line: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    public void followLine(){
        robot.lineSensor
    }

    public void takeShot(){
        // VERY IMPORTANT CODE GOES HERE
        runtime.reset();

        while(opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION)) {
            robot.leftMotor.setPower(-1*TURN_SPEED);
            robot.rightMotor.setPower(TURN_SPEED);
            telemetry.addLine("Motor Power | ")
                    .addData("Left", robot.leftMotor.getPower())
                    .addData("Right", robot.rightMotor.getPower());
            telemetry.addData("Path", "Taking a Shot: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }


}
