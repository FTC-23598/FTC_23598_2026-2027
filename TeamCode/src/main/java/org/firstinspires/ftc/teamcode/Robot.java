package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Robot")
public class Robot extends LinearOpMode {

    Intake Intake = new Intake();
    Conveyor Conveyor = new Conveyor();
    Launcher Launcher = new Launcher();

    @Override
    public void runOpMode() {

        Intake.init(hardwareMap);
        // This variable uses both the triggers to intake, outtake, and move the pollen farther out or in.
        // The special thing about this is that you can control how much power you want to use since we use analog triggers.
        double IntakeConveyorPower = gamepad1.right_trigger - gamepad1.left_trigger;
        //LAUNCHER_TARGET_VELOCITY shows the desired velocity when shooting
        int LAUNCHER_TARGET_VELOCITY = 1250; // I don't know desired launch target (can be changed)
        //LAUNCHER_MIN_VELOCITY shows the least amount of velocity for shooting
        int LAUNCHER_MIN_VELOCITY = 1200; // I don't know desired launch target (can be changed)
        //TODO: Don't forget to add comments that describe what this variable does! (Done)
        Conveyor.init(hardwareMap);
        Launcher.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
// TODO: Create a "Conveyor" helper class (similar to intake) that contains a motor that runs with the intake and a servo (Done)
        while (opModeIsActive()) {
            /*
            Since our conveyor motor is not defined in this class, we have to make a specific method in the class which has the motor.
            The method called setPowerConveyorMotor uses another method called setPower, a method used to set the power of what you desire (mostly motors and servos).
            We created a parameter that is inputted into the setPower method which will be outputted into the setPowerConveyorMotor.
            We made the parameter value IntakeConveyorPower which is outputted into setPowerConveyorMotor which is outputted into the motor.
             */
            Conveyor.setPowerConveyorMotor(IntakeConveyorPower);
            Intake.setPowerIntakeMotor(IntakeConveyorPower);
            //When the right bumper is held down, you start to spinning the launcher up, if the right bumper isn't pressed, then it stops spinning up
            if (gamepad1.right_bumper) {
                Launcher.setVelocityLauncherMotor(LAUNCHER_TARGET_VELOCITY);
            }
            else {
                Launcher.setVelocityLauncherMotor(0);
            }
            //When the Launcher reaches the desired velocity, it uses the allow() method to let the balls shoot.
            //Because of our drum design, we'll also empty out the whole entire system by intaking the whole entire system
            if (gamepad1.right_bumper && Launcher.GetVelocityLauncher() > LAUNCHER_MIN_VELOCITY){
                Conveyor.allow();
                Conveyor.setPowerConveyorMotor(1);
            }
            else{
                Conveyor.deny();
                Conveyor.setPowerConveyorMotor(0);
            }
            //TODO: Don't forget to add comments that describe what this method does! This is a clever little control algorithm that simplifies the code significantly, but describe it so you and others can understand what it does. (Done)
            //TODO: Describe what this method does. The name alone isn't clear.(Done)
            //TODO: Make a launcher sub-class that has methods that control the speed of the flywheel (Done)
            //TODO: Use the launcher class and the Conveyor class together to only allow the launch when the flywheel is up to speed. (Done)
            //This shows the status of our robot
            telemetry.addData("Intaking", IntakeConveyorPower > 0);
            telemetry.addData("Outtaking", IntakeConveyorPower < 0);
            telemetry.addData("Velocity", Launcher.GetVelocityLauncher());
            telemetry.addData("ConveyorIntaking", IntakeConveyorPower > 0);
            telemetry.addData("ConveyorOuttaking", IntakeConveyorPower < 0);
            telemetry.update();
            //TODO: Add telemetry that describe what the state of the robot is. For example, are we intaking or outtaking? Is the conveyor allowed to run, or no? (Done)
            /*
            unused code
            if (gamepad1.a) {
                Intake.takeIn();  // Intaking
            } else if (gamepad1.b) {
                Intake.eject(); // Outtaking
            } else {
                Intake.stop();  // Stopped
            }
            */
        }
    }
}