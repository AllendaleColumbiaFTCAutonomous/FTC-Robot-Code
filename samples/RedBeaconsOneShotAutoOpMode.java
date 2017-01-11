package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    OpticalDistanceSensor lineSensor;
    OpticalDistanceSensor wallSensor;
    //Raw value is between 0 and 1
    static double lineSensorReadingRaw;
    // odsReadingRaw to the power of (-0.5)
    static double lineSensorReadingLinear;
    //Raw value is between 0 and 1
    static double wallSensorReadingRaw;
    // odsReadingRaw to the power of (-0.5)
    static double wallSensorReadingLinear;

    static final double     FORWARD_SPEED = 0.5;
    static final double     TURN_SPEED    = 0.5;
    static final double     SECONDS_PER_REVOLUTION = 2.6 * TURN_SPEED; //corrections are blatantly inaccurate but better than nothing
    static final double     VELOCITY = 4.5 * FORWARD_SPEED; // IN FEET PER SECOND
    static final double     LINE_FOLLOW_SPEED = 0.3;
    static final double     LINE_LIGHT = 0.07;  //light spans between 0.1 - 0.5 from dark to light - near edge of line
    static final double     WAll_THRESHOLD = 0.03; //when close enough to gear image
    static final double     BOT_LIGHT_THRESHOLD = 0.1;
    static final double     BUTTON_PUSHING_TIME = 0.5;

    @Override
    public void runOpMode() {



/*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        robot.init(hardwareMap);
        lineSensor = hardwareMap.opticalDistanceSensor.get("line_sensor");
        wallSensor = hardwareMap.opticalDistanceSensor.get("wall_sensor");
        // lightSensor = hardwareMap.opticalDistanceSensor.get("sensor_ods");
        // lightSensor.enableLed(true);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to Run");    //
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        robot.beaconSensor.enableLed(false);
        lineSensor.enableLed(true);
        wallSensor.enableLed(true);


        runtime.reset();
        while(runtime.seconds() < 100 && notAboutToCollide()){
            telemetry.addData("waiting", "waiting for one hundred seconds");
            telemetry.addData("Path", "Wait: %2.5f S Elapsed", runtime.seconds());
          }
        

        goToBeaconLine();
        turnBotRight(90);
        followLine();
        pressRedBeacon();









        // turnBotLeft(60);
        // goForward(12);


        /* try{
            wait(1000);
        }
        catch (InterruptedException IE){
            telemetry.addData("Error","caught in wait 5 seconds");
        }

        // TEST CODE
        turnBotLeft(360);
        goForward(1);
        try{
            wait(5000);
        }
        catch (InterruptedException IE){
            telemetry.addData("Error","caught in wait 5 seconds");
        }


        goToBeaconLine();
        turnBotLeft(90);
        followLine();
        pressRedBeacon();
        //END TEST CODE*/



        /*// PRESS BOTH OF OUR BEACONS FROM NEAR SIDE
        turnBotLeft(45);
        goToBeaconLine();
        turnBotLeft(45);
        followLine();
        pressRedBeacon();
        turnBotRight(90);
        goToBeaconLine();
        turnBotLeft(90);
        followLine();
        pressRedBeacon();
         // This code pushes the other team's two beacons as well as our own
        turnBotRight(90);
        turnBotRight(45);
        goToBeaconLine();
        turnBotLeft(45);
        followLine();
        pressRedBeacon();
        turnBotRight(90);
        goToBeaconLine();
        turnBotLeft(90);
        followLine();
        pressRedBeacon();

        turnBotLeft(135);
        goForward(5.0);
        turnBotLeft(45);
        goForward(4.0);


        // This code parks us in the corner
        turnBotLeft(90);
        goForward(6.0);

        // This code pushes off the cap ball and parks us in the middle
        turnBotLeft(135);
        goForward(5.0);*/

        // Stop all motors
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);



    }

    public void pressRedBeacon(){
        // VERY IMPORTANT CODE GOES HERE
        robot.beaconSensor.enableLed(true);
        if(robot.beaconSensor.red() > robot.beaconSensor.blue()){
            //if red beacon is on the left
            runtime.reset();
            robot.buttonPusher.setPower(1.0);
            while (opModeIsActive() && (runtime.seconds() < BUTTON_PUSHING_TIME)){
                telemetry.addData("Path", "Turn left: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }
            goBackward(1);

        }
        else {
            //if red beacon is on the right
            turnBotRight(90);
            goForward(0.4);
            turnBotLeft(90);
            runtime.reset();
            robot.buttonPusher.setPower(1.0);
            while (opModeIsActive() && (runtime.seconds() < BUTTON_PUSHING_TIME)){
                telemetry.addData("Path", "Push Button: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }
            goBackward(1);

        }
        robot.beaconSensor.enableLed(false);
    }

    public void goToBeaconLine(){
        lineSensor.enableLed(true);
        // Start the robot moving forward, and then begin looking for a white line.
        robot.leftMotor.setPower(FORWARD_SPEED);
        robot.rightMotor.setPower(FORWARD_SPEED);

        // run until the white line is seen OR the driver presses STOP;
        while (opModeIsActive() && (lineSensor.getLightDetected() < LINE_LIGHT) && notAboutToCollide()) {

            // Display the light level while we are looking for the line
            telemetry.addData("Light Level", lineSensor.getLightDetected());
            telemetry.update();
        }

        // Stop all motors
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        lineSensor.enableLed(false);
    }

    public void turnBotLeft(double degrees){
        //remember change turnBotRight too
        runtime.reset();
        robot.rightMotor.setPower(TURN_SPEED);
        robot.leftMotor.setPower(TURN_SPEED * -1);
        while (opModeIsActive() && (runtime.seconds() < ((degrees/360) * SECONDS_PER_REVOLUTION))){
            telemetry.addData("Path", "Turn left: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

    }
    public void turnBotRight(double degrees){
        //ensure to change turnBotLeft too
        runtime.reset();
        robot.rightMotor.setPower(TURN_SPEED * -1);
        robot.leftMotor.setPower(TURN_SPEED );
        while (opModeIsActive() && (runtime.seconds() < ((degrees/360) * SECONDS_PER_REVOLUTION))){
            telemetry.addData("Path", "Turn right : %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

    }

    public void goForward(double distanceInFeet){
        // remember to change velocity data if distance is wrong
        runtime.reset();
        robot.rightMotor.setPower(FORWARD_SPEED);
        robot.leftMotor.setPower(FORWARD_SPEED);
        while (opModeIsActive() && (runtime.seconds() < (distanceInFeet /  VELOCITY))){
            telemetry.addData("Path", "Go forward: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }


    public void goBackward(double distanceInFeet){
        // remember to change velocity data if distance is wrong
        runtime.reset();
        robot.rightMotor.setPower(-1* FORWARD_SPEED);
        robot.leftMotor.setPower(-1 * FORWARD_SPEED);
        while (opModeIsActive() && (runtime.seconds() < (distanceInFeet /  VELOCITY))){
            telemetry.addData("Path", "Go forward: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    public boolean notAboutToCollide(){
        lineSensorReadingRaw = lineSensor.getRawLightDetected();
        lineSensorReadingLinear = lineSensor.getLightDetected();
        wallSensorReadingRaw = wallSensor.getRawLightDetected();
        wallSensorReadingLinear = wallSensor.getLightDetected();
        telemetry.addData("0 lineSensor raw", lineSensorReadingRaw);
        telemetry.addData("1 lineSensor linear", lineSensorReadingLinear);
        telemetry.addData("2 wallSensor raw", wallSensorReadingRaw);
        telemetry.addData("3 wallSensor light", wallSensorReadingLinear);
        telemetry.addData("4 lineSensor corrected", Math.pow(lineSensorReadingRaw / 5, -0.5));
        telemetry.addData("5 wallSensor corrected", Math.pow(wallSensorReadingRaw / 5, -0.5));
        telemetry.addData("6 beaconSensor red", robot.beaconSensor.red());
        telemetry.addData("7 beaconSensor blue", robot.beaconSensor.blue());
        telemetry.update();




        // temporary for telemetry
        return true;





        /*wallSensor.enableLed(true);
        if(wallSensor.getLightDetected() < BOT_LIGHT_THRESHOLD) {
            // wallSensor.enableLed(false);
            return true;
        }
        runtime.reset();
        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);
        while (opModeIsActive() && (runtime.seconds() < 1000) && (wallSensor.getLightDetected() > BOT_LIGHT_THRESHOLD)) {
            telemetry.addData("Path", "Was about to collide : %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        wallSensor.enableLed(false);
        return true;*/
    }

    public void followLine(){
        //9.5 inches from center of robot to ods
        lineSensor.enableLed(true);
        wallSensor.enableLed(true);
        telemetry.addData("Light Level", lineSensor.getLightDetected());
        telemetry.update();
        double lastLight = lineSensor.getLightDetected();
        double currentLight = lineSensor.getLightDetected();
        //while still following line
        //ensure WALL_THRESHOLD is accurate
        while(wallSensor.getLightDetected() < WAll_THRESHOLD && opModeIsActive() && notAboutToCollide()) {
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
            currentLight = lineSensor.getLightDetected();

        }
        lineSensor.enableLed(false);
        wallSensor.enableLed(false);
    }

    /*public void takeShot(){
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
    }*/


}
