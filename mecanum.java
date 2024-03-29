

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="MecDrive", group="Linear Opmode")

public class mecanum extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft  = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double flPow,frPow,blPow,brPow;


            double xDir = gamepad1.left_stick_y;
            double yDir = -gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;

            //left toward each other = right movement
            //right toward each other = left movement

            flPow = xDir+yDir+rotate;
            frPow = xDir-yDir-rotate;
            blPow=xDir-yDir+rotate;
            brPow=xDir+yDir-rotate;





            flPow  = Range.clip(flPow, -1.0, 1.0);
            frPow  = Range.clip(frPow, -1.0, 1.0) ;
            blPow  = Range.clip(blPow, -1.0, 1.0) ;
            brPow  = Range.clip(brPow, -1.0, 1.0) ;


            // Send calculated power to wheels
            frontLeft.setPower(flPow);
            frontRight.setPower(frPow);
            backLeft.setPower(blPow);
            backRight.setPower(brPow);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front Motors", "frontLeft (%.2f) frontRight (%.2f)", flPow, frPow);
            telemetry.addData("Back Motors", "backLeft (%.2f) backRight (%.2f)", blPow, brPow);
            telemetry.update();
        }
    }
}
