package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Robot")
public class Robot extends LinearOpMode {

    // CONSTRUCTORS

    Intake Intake = new Intake();
    Conveyor Conveyor = new Conveyor();
    Launcher Launcher = new Launcher();
    DriveTrain DriveTrain = new DriveTrain();

    @Override
    public void runOpMode() {

        // VARIABLES

        //This variable shows the desired velocity when shooting
        int launcherTargetVelocity = 1250; // I don't know desired launch target (can be changed)
        //This variable shows the least amount of velocity for shooting
        int launcherMinVelocity = 1200; // I don't know desired launch target (can be changed)
        //Last button state either true or false
        boolean lastButtonState = false;
        //Decides if you should set launch or not
        boolean setLaunch = false;
        //Finds value of IntakeConveyorPower
        double intakeConveyorPowerValue = gamepad1.right_trigger - gamepad1.left_trigger;

        //INITIATION

        Intake.init(hardwareMap);
        Conveyor.init(hardwareMap);
        Launcher.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {

            //VARIABLES

            // This variable uses both the triggers to intake, outtake, and move the pollen farther out or in.
            // The special thing about this is that you can control how much power you want to use since we use analog triggers.
            double intakeConveyorPower = gamepad1.right_trigger - gamepad1.left_trigger;
            //Current button state either true or false
            boolean currentButtonState = gamepad1.right_bumper;
            /*
            Since our conveyor motor is not defined in this class, we have to make a specific method in the class which has the motor.
            The method called setPowerConveyorMotor uses another method called setPower, a method used to set the power of what you desire (mostly motors and servos).
            We created a parameter that is inputted into the setPower method which will be outputted into the setPowerConveyorMotor.
            We made the parameter value IntakeConveyorPower which is outputted into setPowerConveyorMotor which is outputted into the motor.
             */
            //Toggle switch mechanisim incase you acidently press shooting button

            // LAUNCHING/CONVEYOR/INTAKE

            if (currentButtonState && !lastButtonState){
                setLaunch = !setLaunch;
            }
            lastButtonState = currentButtonState;
            //When the right bumper is pressed, you start to spinning the launcher up, if the right bumper isn't pressed or pressed again, then it stops spinning up
            if (setLaunch) {
                Launcher.setVelocityLauncherMotor(launcherTargetVelocity);
            } else {
                Launcher.setVelocityLauncherMotor(0);
            }
            //When the Launcher reaches the desired velocity and the designated button is pressed, it uses the allow() method to let the balls shoot.
            //Because of our drum design, we'll also empty out the whole entire system by setting the conveyor motor power to 1 which feeds all balls into the conveyor.
            if (gamepad1.b && Launcher.getVelocityLauncher() > launcherMinVelocity) {
                Conveyor.allow();
                Conveyor.setPowerConveyorMotor(1);
            } else {
                Conveyor.deny();
                Conveyor.setPowerConveyorMotor(intakeConveyorPower);
                Intake.setPowerIntakeMotor(intakeConveyorPower);
            }

            // DRIVETRAIN

            //Press a to reset yaw
            if (gamepad1.a) {
                DriveTrain.resetTheYaw();
            }
            //-gamepad1.left_stick_y is used for forward and backward, gamepad1.left_stick_x is used for left and right, gamepad1.right_stick_x is used for rotating
            DriveTrain.driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }

        // TELEMETRY

        //This shows the status of our robot
        telemetry.addData("Intaking", intakeConveyorPowerValue > 0);
        telemetry.addData("Outtaking", intakeConveyorPowerValue < 0);
        telemetry.addData("Velocity", Launcher.getVelocityLauncher());
        telemetry.addData("Conveyor Intaking", intakeConveyorPowerValue > 0);
        telemetry.addData("Conveyor Outtaking", intakeConveyorPowerValue < 0);
        telemetry.addLine("The left joystick sets the robot direction");
        telemetry.addLine("Moving the right joystick left and right turns the robot");
        telemetry.addLine("Press A to reset Yaw");
        telemetry.addLine("Press right bumper to prepare velocity and again to stop");
        telemetry.addLine("Hold B to launch");
        telemetry.addLine("Use right trigger to intake and feed balls into launcher");
        telemetry.addLine("Use left trigger to outtake and spit balls out of conveyor");
        telemetry.update();

        // TODOS (DONE)

        //TODO: Don't forget to add comments that describe what this method does! This is a clever little control algorithm that simplifies the code significantly, but describe it so you and others can understand what it does. (Done)
        //TODO: Describe what this method does. The name alone isn't clear.(Done)
        //TODO: Make a launcher sub-class that has methods that control the speed of the flywheel (Done)
        //TODO: Use the launcher class and the Conveyor class together to only allow the launch when the flywheel is up to speed. (Done)
        //TODO: Don't forget to add comments that describe what this variable does! (Done)
        //TODO: Create a "Conveyor" helper class (similar to intake) that contains a motor that runs with the intake and a servo (Done)
        //TODO: Add telemetry that describe what the state of the robot is. For example, are we intaking or outtaking? Is the conveyor allowed to run, or no? (Done)

        // TODOS (INCOMPLETE)
    }
}