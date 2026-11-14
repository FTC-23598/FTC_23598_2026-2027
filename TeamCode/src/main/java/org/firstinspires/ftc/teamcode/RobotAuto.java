package org.firstinspires.ftc.teamcode;
import com.pedropathing.api.Paths;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;
import com.pedropathing.math.Pose;
import com.pedropathing.api.PoseFactory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous public class RobotAuto extends OpMode{
    private Follower follower; // What make the robot follow the path
    private final PoseFactory PFDeg = PoseFactory.degrees(); // Lets us define angles more simply.

    Launcher Launcher = new Launcher();
    Conveyor Conveyor = new Conveyor();
    Intake Intake = new Intake();
    //This variable shows the desired launch RPM when shooting
    double LAUNCHERTARGETVELOCITY = (1250.0/28.0) * 60.0; // I don't know desired launch target (can be changed).
    //This variable shows the least amount of launch RPM for shooting
    // PoseFactory Degrees
    //Field offset is 15.6 inches
    private final Pose startPoseLaunchOne = PFDeg.of(0, 0, 0); // Starting position for LaunchOne (Should be changed)
    private final Pose controlPoseLaunchOne = PFDeg.of(0, 0, 0); // Point which you base the vertex of your curve on for LaunchOne (Should be changed)
    private final Pose endPoseLaunchOne = PFDeg.of(0, 0, 0); // Ending Position for LaunchOne (Should be changed)
    private final Pose startPoseIntakeOne = PFDeg.of(0, 0, 0); // Starting position for IntakeOne (Should be changed)
    private final Pose controlPoseIntakeOne = PFDeg.of(0, 0, 0); // Point which you base the vertex of your curve on IntakeOne (Should be changed)
    private final Pose endPoseIntakeOne = PFDeg.of(0, 0, 0); // Ending Position IntakeOne (Should be changed)
    private final Pose startPoseLaunchTwo = PFDeg.of(0, 0, 0); // Starting position LaunchTwo (Should be changed)
    private final Pose controlPoseLaunchTwo = PFDeg.of(0, 0, 0); // Point which you base the vertex of your curve on LaunchTwo (Should be changed)
    private final Pose endPoseLaunchTwo = PFDeg.of(0, 0, 0); // Starting position LaunchTwo (Should be changed)
    private Path LaunchOne() { // This our first launching path
        return Paths.curve(startPoseLaunchOne, controlPoseLaunchOne, endPoseLaunchOne);
    }
    private Path LaunchTwo() { // This our second launching path
        return Paths.curve(startPoseLaunchTwo, controlPoseLaunchTwo, endPoseLaunchTwo);
    }
    private Path IntakeOne() { // This is our first intake path
        return Paths.curve(startPoseIntakeOne, controlPoseIntakeOne, endPoseIntakeOne);
    }
    private Command AutoRoutine() {
        return sequential(
                parallel(
                        instant(() -> Launcher.setVelocityLauncherMotor(LAUNCHERTARGETVELOCITY)),
                        follow(follower, LaunchOne())
                ),

                instant(() -> Conveyor.allow()),
                instant(() -> Conveyor.setPowerConveyorMotor(1)),

                waitMs(1500),

                instant(() -> Launcher.setVelocityLauncherMotor(0.0)),
                instant(() -> Conveyor.setPowerConveyorMotor(0)),
                instant(() -> Conveyor.deny()),

                instant(() -> Intake.setPowerIntakeMotor(1.0)),
                instant(() -> Conveyor.setPowerConveyorMotor(1.0)),
                follow(follower, IntakeOne()),

                parallel(
                        instant(() -> Intake.setPowerIntakeMotor(0.0)),
                        instant(() -> Conveyor.setPowerConveyorMotor(0.0)),
                        instant(() -> Launcher.setVelocityLauncherMotor(LAUNCHERTARGETVELOCITY)),
                        follow(follower, LaunchTwo())
                ),

                instant(() -> Conveyor.allow()),
                instant(() -> Conveyor.setPowerConveyorMotor(1)),

                waitMs(1500),

                instant(() -> Launcher.setVelocityLauncherMotor(0.0)),
                instant(() -> Conveyor.setPowerConveyorMotor(0)),
                instant(() -> Conveyor.deny())
        );
    }

    @Override public void init() { // Initialization
        Scheduler.reset();
        follower = Constants.create(hardwareMap); // Lets the follower use our motors
        follower.setPose(startPoseLaunchOne);
        follower.update();
        Launcher.init(hardwareMap);
        Conveyor.init(hardwareMap);
        Intake.init(hardwareMap);
    }
    @Override public void start() { // Where we play our auto routine
        schedule(AutoRoutine());
    }
    @Override public void loop() { // Where we constantly update and execute our code
        follower.update();
        Scheduler.execute();
    }
    @Override public void stop() {
        Scheduler.reset();
        Launcher.setVelocityLauncherMotor(0.0);
        Intake.setPowerIntakeMotor(0.0);
        Conveyor.setPowerConveyorMotor(0.0);
        Conveyor.deny();
    }
}