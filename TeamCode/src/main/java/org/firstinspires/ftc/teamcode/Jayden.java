/*
Author: Jerome Yang
Start: 3/27/2025
End: #/##/####
Purpose: FTC 19571 The Robo Brigade Team D robot OpMode code.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Jayden extends LinearOpMode {

    // Motor declarations
    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    double driveTrain_Factor = 0.5;

    //@Override
    public void runOpMode() throws InterruptedException {

        // declare hardware by getting configuration
        frontleft = hardwareMap.get(DcMotor.class, "frontleft");
        frontright = hardwareMap.get(DcMotor.class, "frontright");
        backleft = hardwareMap.get(DcMotor.class, "backleft");
        backright = hardwareMap.get(DcMotor.class, "backright");

        // reverse because it the only one spinning in wrong direction idk why
        //frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        //topright.setDirection(DcMotorSimple.Direction.REVERSE);
        //backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        //backright.setDirection(DcMotorSimple.Direction.REVERSE);

        // When motor has zero power what does it do? BRAKE!!!
        frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Stops and dosent run until you press start
        waitForStart();

        // This check if you stop the code then stops the code
        if (isStopRequested()) return;

        /// ALL CODE RUNNING IN HERE
        while(opModeIsActive()) {

            // Drive normally
            driveTrain(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x);

            updateTelemetry();

        } // while opModeIsActive end

    }// OpMode end
    /// METHODS

    // DriveTrain method
    public void driveTrain(double rightStickX, double rightStickY, double leftStickX) {
        double x = rightStickX * 1.1; // Counteract imperfect strafing
        double y = -rightStickY; // Remember, Y stick value is reversed
        double rx = leftStickX * 0.7; // turning speed

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double leftTop_Power = (y + x + rx) / denominator;
        double leftBot_Power = (y - x + rx) / denominator;
        double rightTop_Power = (y - x - rx) / denominator;
        double rightBot_Power = (y + x - rx) / denominator;

        frontleft.setPower(leftTop_Power * driveTrain_Factor);
        frontright.setPower(rightTop_Power * driveTrain_Factor);
        backleft.setPower(leftBot_Power * driveTrain_Factor);
        backright.setPower(rightBot_Power * driveTrain_Factor);
    }// controller drive end

    // Telemetry method
    public void updateTelemetry() {
        telemetry.addLine();
        telemetry.addData("frontleft Power: ", frontleft.getPower());
        telemetry.addData("frontleft Current Position: ", frontleft.getCurrentPosition());
        telemetry.addLine();
        telemetry.addData("topright Power: ", frontright.getPower());
        telemetry.addData("topright Current Position: ", frontright.getCurrentPosition());
        telemetry.addLine();
        telemetry.addData("backleft Power: ", backleft.getPower());
        telemetry.addData("backleft Current Position: ", backleft.getCurrentPosition());
        telemetry.addLine();
        telemetry.addData("backright Power: ", backright.getPower());
        telemetry.addData("backright Current Position: ", backright.getCurrentPosition());
        telemetry.addLine();
        telemetry.update();
    }//update telemetry end

}// class end
