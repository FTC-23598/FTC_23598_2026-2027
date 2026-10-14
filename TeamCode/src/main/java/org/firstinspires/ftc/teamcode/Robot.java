package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Robot")
public class Robot extends LinearOpMode {

    Intake Intake = new Intake();

    @Override
    public void runOpMode() {

        Intake.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                Intake.takeIn();  // Intaking
            } else if (gamepad1.b) {
                Intake.eject(); // Outtaking
            } else {
                Intake.stop();  // Stopped
            }

            telemetry.update();
        }
    }
}