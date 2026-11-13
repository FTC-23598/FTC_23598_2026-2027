package org.firstinspires.ftc.teamcode;
import com.pedropathing.api.Paths;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;
import com.pedropathing.math.Pose;
import com.pedropathing.api.PoseFactory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous public class RobotAuto extends OpMode{
    // Pedro Pathing
    private Follower follower;
    private final PoseFactory PFDeg = PoseFactory.degrees();

    Launcher Launcher = new Launcher();
    Conveyor Conveyor = new Conveyor();
    DriveTrain DriveTrain = new DriveTrain();
    Intake Intake = new Intake();
    //This variable shows the desired launch RPM when shooting
    int LAUNCHERTARGETVELOCITY = (1250/28) * 60; // I don't know desired launch target (can be changed).
    //This variable shows the least amount of launch RPM for shooting
    int LAUNCHERMINVELOCITY = (1200/28) * 60; // I don't know desired launch target (can be changed).
    // PoseFactory Degrees
    //Field offset is 15.6 inches
    private final Pose startPoseLaunchOne = PFDeg.of(0, 0, 0); // Starting position (Should be changed)
    private final Pose controlPoseLaunchOne = PFDeg.of(0, 0, 0); // Point which you base the vertex of your curve on (Should be changed)
    private final Pose endPoseLaunchOne = PFDeg.of(0, 0, 0); // Ending Position (Should be changed)
    private final Pose startPoseIntakeOne = PFDeg.of(0, 0, 0); // Starting position (Should be changed)
    private final Pose controlPoseIntakeOne = PFDeg.of(0, 0, 0); // Point which you base the vertex of your curve on (Should be changed)
    private final Pose endPoseIntakeOne = PFDeg.of(0, 0, 0); // Ending Position (Should be changed)
    private Path LaunchOne() {
        return Paths.curve(startPoseLaunchOne, controlPoseLaunchOne, endPoseLaunchOne);
    }
    private Path IntakeOne() {
        return Paths.curve(startPoseIntakeOne, controlPoseIntakeOne, endPoseIntakeOne);
    }
    private Command Launching() {
        return sequential(
                instant(() -> Launcher.setVelocityLauncherMotor(1.0)),
                waitMs(3000),
                instant(() -> Conveyor.allow()),
                waitMs(2000),
                instant(() -> Launcher.setVelocityLauncherMotor(0.0)),
                instant(() -> Conveyor.deny())
                );
    }
    private Command Intaking(){
        return sequential(
                instant(() -> DriveTrain.setPowerBLMotor(1)),
                instant(() -> DriveTrain.setPowerFLMotor(1)),
                instant(() -> DriveTrain.setPowerBRMotor(1)),
                instant(() -> DriveTrain.setPowerFRMotor(1)),
                instant(() -> Intake.setPowerIntakeMotor(1)),
                instant(() -> Conveyor.setPowerConveyorMotor(1)),
                waitMs(5000),
                instant(() -> DriveTrain.setPowerBLMotor(0)),
                instant(() -> DriveTrain.setPowerFLMotor(0)),
                instant(() -> DriveTrain.setPowerBRMotor(0)),
                instant(() -> DriveTrain.setPowerFRMotor(0)),
                instant(() -> Intake.setPowerIntakeMotor(0))
                );
    }
    private Command AutoRoutine() {
        return sequential(
                follow(follower, LaunchOne()),
                Launching(),
                follow(follower, IntakeOne()),
                Intaking()
        );
    }

    @Override public void init() {
        follower = Constants.create(hardwareMap); // Make constants
        follower.setPose(startPoseLaunchOne);
        follower.update();
    }
    public void start() {
        schedule(AutoRoutine());
    }
    @Override public void loop() {
        follower.update();
        Launcher.setVelocityLauncherMotor(LAUNCHERTARGETVELOCITY);
        if (Launcher.getVelocityLauncher() > LAUNCHERMINVELOCITY) {
            Conveyor.allow();
        }
    }
}
