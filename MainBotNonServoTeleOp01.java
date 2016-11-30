package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by nmor99 on 11/6/2016.
 */

@TeleOp(name="Mainbot: NonServo Tank", group="AC")
@Disabled
public class MainBotNonServoTeleOp01 extends OpMode {

    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
    // could also use HardwarePushbotMatrix class.


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double left;
        double right;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        // LINES OF ACTUAL CODE ARE COMMENTED OUT IF NOT APPLICABLE TO OUR BASIC ROBOT
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);

        // Use gamepad left & right Bumpers to open and close the claw
        // if (gamepad1.right_bumper)
        //     clawOffset += CLAW_SPEED;
        // else if (gamepad1.left_bumper)
        //     clawOffset -= CLAW_SPEED;

        // Move both servos to new position.  Assume servos are mirror image of each other.
        // clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        // robot.leftClaw.setPosition(robot.MID_SERVO + clawOffset);
        // robot.rightClaw.setPosition(robot.MID_SERVO - clawOffset);

        // Use gamepad buttons to move the arm up (Y) and down (A)
        // if (gamepad1.y)
        //     robot.armMotor.setPower(robot.ARM_UP_POWER);
        // else if (gamepad1.a)
        //     robot.armMotor.setPower(robot.ARM_DOWN_POWER);
        // else
        //     robot.armMotor.setPower(0.0);

        // Send telemetry message to signify robot running;
        // telemetry.addData("claw",  "Offset = %.2f", clawOffset);
        telemetry.addData("left",  "%.2f", left);
        telemetry.addData("right", "%.2f", right);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
