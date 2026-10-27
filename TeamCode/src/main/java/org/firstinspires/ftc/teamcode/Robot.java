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
        // This variable uses both the triggers to intake, outtake, and move the pollen farther out or in.
        // The special thing about this is that you can control how much power you want to use since we use analog triggers.
        double IntakeConveyorPower = gamepad1.right_trigger - gamepad1.left_trigger; //TODO: Don't forget to add comments that describe what this variable does! (Done)
        Conveyor.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
// TODO: Create a "Conveyor" helper class (similar to intake) that contains a motor that runs with the intake and a servo (Done)
        while (opModeIsActive()) {
            //There is something called setPowerIntakeMotor which specifically powers the Intake motor.
            //Since its power comes form IntakeConveyorPower, whenever there's power in that variable, the Intake motor gets that.
            Intake.setPowerIntakeMotor(IntakeConveyorPower);
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
            //A method called setPowerConveyorMotor powers the Conveyor motor.
            //Since its power value comes form IntakeConveyorPower, whenever the intake runs, the Conveyor motor runs as well.
            Conveyor.setPowerConveyorMotor(IntakeConveyorPower); //TODO: Don't forget to add comments that describe what this method does! This is a clever little control algorithm that simplifies the code significantly, but describe it so you and others can understand what it does. (Done)
            //TODO: Describe what this method does. The name alone isn't clear.
            if (gamepad1.x) {Conveyor.allow();}
            else{Conveyor.deny();}

            //TODO: Make a launcher sub-class that has methods that control the speed of the flywheel
            //TODO: Use the launcher class and the Conveyor class together to only allow the launch when the flywheel is up to speed.


            telemetry.update(); //TODO: Add telemetry that describe what the state of the robot is. For example, are we intaking or outtaking? Is the conveyor allowed to run, or no?
        }
    }
}