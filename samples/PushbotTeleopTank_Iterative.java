/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Pushbot: Teleop Tank", group="Pushbot")
@Disabled
public class PushbotTeleopTank_Iterative extends OpMode{

    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
                                                         // could also use HardwarePushbotMatrix class.
    double          forkliftOffset  = 0.0;                  // Servo mid position
    // double          buttonPusherOffset = 0.0;
    final double    FORKLIFTSERVO_SPEED  = 0.001;                 // sets rate to move servo
    // final double    BUTTONPUSHER_SPEED = 0.003;
    int             directionMovement = 1;
    boolean         y_released = true;
    double forkliftServoPosition;



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

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.forkliftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.forkliftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d",
                robot.forkliftMotor.getCurrentPosition());
        telemetry.update();
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
        double forkliftPower;
        double buttonpusherPower;
        double spinnerPower;

        if(gamepad1.y){
            if(y_released) {
                directionMovement = directionMovement * -1; //flip direction movement every time y is pressed
                y_released = false; // triggers a flip in directionMovement once per y button press
            }
        }
        else{
            y_released = true; // if y is not pressed, flipping direction is enabled again
        }



        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        // LINES OF ACTUAL CODE ARE COMMENTED OUT IF NOT APPLICABLE TO OUR BASIC ROBOT
        if(directionMovement == 1) {
            left = -gamepad1.left_stick_y;
            right = -gamepad1.right_stick_y;
        }
        else {
            // direction movement is negative 1, driving backwards means inverted left/right and +/-.
            left = gamepad1.right_stick_y;
            right = gamepad1.left_stick_y;
        }
        buttonpusherPower = 0.0;
        left = Range.clip(left, -1.0, 1.0);
        right = Range.clip(right, -1.0, 1.0);
        left = left * Math.abs(left); //squares the motor power, allowing for easier access to small powers, maintaining sign of variable
        right = right * Math.abs(right); //same as above.
        if(gamepad2.right_bumper)
            buttonpusherPower = 1.0;
        else if(gamepad2.left_bumper)
            buttonpusherPower = -1.0;
        robot.buttonPusher.setPower(buttonpusherPower);
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);


        spinnerPower = 0.0;
        if(gamepad1.right_bumper)
            spinnerPower = 1.0;
        else if(gamepad1.left_bumper)
            spinnerPower = -1.0;
        robot.spinnerMotor.setPower(spinnerPower);

        // Use gamepad left & right Bumpers to open and close the claw
        if (gamepad2.right_stick_y < 0)
            forkliftOffset += FORKLIFTSERVO_SPEED;
        else if (gamepad2.right_stick_y > 0 )
            forkliftOffset -= FORKLIFTSERVO_SPEED;

        // Use gamepad a and x buttons to manipulate button pusher
        // if (gamepad2.a)
        //    buttonPusherOffset += BUTTONPUSHER_SPEED;
        // else if (gamepad2.x)
        //   buttonPusherOffset -= BUTTONPUSHER_SPEED;

        // Move both servos to new position.  Assume servos are mirror image of each other.


        forkliftOffset = Range.clip(forkliftOffset, -1.0, 1.0); // IF CHANGE STARTING POSITION, CHANGE OFFSET RANG
        // buttonPusherOffset = Range.clip(buttonPusherOffset,0.0,1.0);
        robot.forkliftServo.setPosition(0 - forkliftOffset); // FLIPPED TO ALLOW FOR // CHANGE IF STARTING POSITION OF SERVO MUST CHANGE
        // robot.buttonPusher.setPosition(0 + buttonPusherOffset);// CHANGE IF STARTING POSITION OF SERVO MUST CHANGE


        // Use gamepad buttons to move the arm up (Y) and down (A)
        forkliftPower = gamepad2.left_stick_y;
        if(forkliftPower == 0){
            robot.forkliftMotor.setPower(0.0);
        }
        if(forkliftPower > 0){
            robot.forkliftMotor.setPower(robot.ARM_DOWN_POWER * forkliftPower);
        }
        if(forkliftPower < 0){
            robot.forkliftMotor.setPower(robot.ARM_UP_POWER * forkliftPower);
        }

        // Send telemetry message to signify robot running;
        telemetry.addData("pureControllerOutputLeft", "%.2f", -gamepad1.left_stick_y);
        telemetry.addData("pureControllerOutputRight", "%.2f", -gamepad1.right_stick_y);
        telemetry.addData("forklift",  "Offset = %.2f", forkliftOffset);
        telemetry.addData("left",  "%.2f", left);
        telemetry.addData("right", "%.2f", right);
        telemetry.addData("buttonPusher", "%.2f", buttonpusherPower);
        double forkliftServoPosition = robot.forkliftServo.getPosition();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        while(true){
            robot.forkliftServo.setPosition(forkliftServoPosition);
        }
    }

}
