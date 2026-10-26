package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Robot")
public class Robot extends LinearOpMode {

    Intake Intake = new Intake();
    Conveyor Conveyor = new Conveyor();

    @Override
    public void runOpMode() {

        Intake.init(hardwareMap);
        double IntakeConveyorPower = gamepad1.right_trigger - gamepad1.left_trigger; //TODO: Don't forget to add comments that describe what this variable does!
        Conveyor.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
// TODO: Create a "Conveyor" helper class (similar to intake) that contains a motor that runs with the intake and a servo (Done)
        while (opModeIsActive()) {
            Intake.setPowerMotor(IntakeConveyorPower);
            //You can make a grouped comment like this so you don't need to comment every line:
            /*
            if (gamepad1.a) {
                Intake.takeIn();  // Intaking
            } else if (gamepad1.b) {
                Intake.eject(); // Outtaking
            } else {
                Intake.stop();  // Stopped
            }
            */
            Conveyor.setPowerMotor(IntakeConveyorPower); //TODO: Don't forget to add comments that describe what this method does! This is a clever little control algorithm that simplifies the code significantly, but describe it so you and others can understand what it does.
            if (gamepad1.x) {Conveyor.allow();}
            else{Conveyor.deny();}


            telemetry.update(); //TODO: Add telemetry that describe what the state of the robot is. For example, are we intaking or outtaking? Is the conveyor allowed to run, or no?
        }
    }
}