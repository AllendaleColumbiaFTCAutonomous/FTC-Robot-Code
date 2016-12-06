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
    static final double     LINE_THRESHOLD = 0.2;
    static final double     LINE_FOLLOW_SPEED = 0.3;
    static final double      LINE_LIGHT = 0.3;  //light spans between 0.1 - 0.5 from dark to light - near edge of line
    static final double     WAll_THRESHOLD = 0.2; //when close enough to gear image
    static final double     BOT_LIGHT_THRESHOLD = 0.3;

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
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION / 8.0) && notAboutToCollide()) {
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
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION / 8.0) && notAboutToCollide()) {
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
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION / 4.0) && notAboutToCollide()) {
            telemetry.addData("Path", "Turning from Final Beacon: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        robot.leftMotor.setPower(FORWARD_SPEED);
        robot.rightMotor.setPower(FORWARD_SPEED);
        telemetry.addLine("Motor Power | ")
                .addData("Left", robot.leftMotor.getPower())
                .addData("Right", robot.rightMotor.getPower());
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION / 4.0) && notAboutToCollide()) {
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
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION) && notAboutToCollide()) {
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

        while(opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION) && notAboutToCollide()) {
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
        while (opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION) && notAboutToCollide()) {
            telemetry.addData("Path", "Going to Beacon Line: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    public void turnBotLeft(double degrees){
        //remember change turnBotRight too
        runtime.reset();
        robot.rightMotor.setPower(TURN_SPEED);
        robot.leftMotor.setPower(TURN_SPEED * -1);
        while (opModeIsActive() && (runtime.seconds() < ((degrees/360) * SECONDS_PER_REVOLUTION) && notAboutToCollide()){
            telemetry.addData("Path", "Turn left: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

    }
    public void turnBotRight(double degrees){
        //ensure to change turnBotLeft too
        runtime.reset();
        robot.rightMotor.setPower(TURN_SPEED * -1);
        robot.leftMotor.setPower(TURN_SPEED );
        while (opModeIsActive() && (runtime.seconds() < ((degrees/360) * SECONDS_PER_REVOLUTION) && notAboutToCollide()){
            telemetry.addData("Path", "Turn right : %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

    }
    public boolean notAboutToCollide(){
        if(robot.wallSensor.getLightDetected() < BOT_LIGHT_THRESHOLD) {
            return true;
        }
        runtime.reset();
        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);
        while (opModeIsActive() && (runtime.seconds()< 1000) && (robot.wallSensor.getLightDetected() > BOT_LIGHT_THRESHOLD)){
            telemetry.addData("Path", "Was about to collide : %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        return true;
    }

    public void followLine(){
        //9.5 inches from center of robot to ods
        robot.lineSensor.enableLed(true);
        telemetry.addData("Light Level", robot.lineSensor.getLightDetected());
        telemetry.update();
        double lastLight = robot.lineSensor.getLightDetected();
        double currentLight = robot.lineSensor.getLightDetected();
        //while still following line
        //ensure WALL_THRESHOLD is accurate
        while(robot.wallSensor.getLightDetected() < WAll_THRESHOLD && opModeIsActive() && notAboutToCollide()) {
            if(currentLight < lastLight && currentLight < LINE_LIGHT){
                robot.leftMotor.setPower(LINE_FOLLOW_SPEED);
                robot.rightMotor.setPower(LINE_FOLLOW_SPEED * currentLight/LINE_LIGHT);
            }
            else if(currentLight > lastLight && currentLight > LINE_LIGHT){
                robot.rightMotor.setPower(LINE_FOLLOW_SPEED);
                robot.leftMotor.setPower(LINE_FOLLOW_SPEED * LINE_LIGHT/currentLight);
            }
            else{
                robot.rightMotor.setPower(LINE_FOLLOW_SPEED);
                robot.leftMotor.setPower(LINE_FOLLOW_SPEED);
            }
            lastLight = currentLight;
            currentLight = robot.lineSensor.getLightDetected();

        }
    }

    public void takeShot(){
        // VERY IMPORTANT CODE GOES HERE
        runtime.reset();

        while(opModeIsActive() && (runtime.seconds() < SECONDS_PER_REVOLUTION) && notAboutToCollide()) {
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
