package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class HardwarePushbot
{
    /* Public OpMode members. */
    public DcMotor  leftMotor   = null;
    public DcMotor  rightMotor  = null;
    public DcMotor  forkliftMotor    = null;
    public DcMotor spinnerMotor = null;
    //public OpticalDistanceSensor lineSensor;   // Alternative MR ODS sensor
    //public OpticalDistanceSensor wallSensor;
    public ColorSensor beaconSensor;
    public Servo    forkliftServo    = null;
    public CRServo  buttonPusher     = null;
    // public DcMotor

    // public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.3 ; // TEST - if too fast, lower
    public static final double ARM_DOWN_POWER  =  0.035;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwarePushbot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor   = hwMap.dcMotor.get("left_drive");
        rightMotor  = hwMap.dcMotor.get("right_drive");
        forkliftMotor    = hwMap.dcMotor.get("forklift_motor");
        spinnerMotor = hwMap.dcMotor.get("spinner");
        forkliftServo = hwMap.servo.get("forklift_servo");
        forkliftServo.setPosition(0.0); //FORKLIFT ALWAYS STARTS UP, VERTICAL
        //IF FAILING, TRY TO SET POSITION TO INVERSE IN CASE SERVO IS WRONG DIRECTION
        buttonPusher = hwMap.crservo.get("button_servo");
        buttonPusher.setPower(0.0);
        // THE FOLLOWING CODE IS USEFUL WHEN BUTTON SERVO IS A SERVO
        // buttonPusher.setPosition(0); //BUTTON PUSHER ALWAYS STARTS BACK
        //IF FAILING, TRY TO SET POSITION TO INVERSE IN CASE SERVO IS WRONG DIRECTION

        //lineSensor = hwMap.opticalDistanceSensor.get("line_sensor");
        // wallSensor = hwMap.opticalDistanceSensor.get("wall_sensor");
        beaconSensor = hwMap.colorSensor.get("beacon_sensor");
        leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        forkliftMotor.setPower(0);
        spinnerMotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        forkliftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        spinnerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        // leftClaw = hwMap.servo.get("left_hand");
        // rightClaw = hwMap.servo.get("right_hand");
        // leftClaw.setPosition(MID_SERVO);
        // rightClaw.setPosition(MID_SERVO);
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

