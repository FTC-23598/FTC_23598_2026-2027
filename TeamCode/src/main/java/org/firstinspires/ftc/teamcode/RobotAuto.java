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
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous public class RobotAuto extends OpMode{
    private Follower follower; // What make the robot follow the path
    private final PoseFactory PFDeg = PoseFactory.degrees(); // Lets us define angles more simply.

    Launcher Launcher = new Launcher();
    Conveyor Conveyor = new Conveyor();
    DriveTrain DriveTrain = new DriveTrain();
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
    private Pose StartPose = startPoseLaunchOne;
    private Path LaunchOne() { // This our first launching path
        return Paths.curve(startPoseLaunchOne, controlPoseLaunchOne, endPoseLaunchOne);
    }
    private Command switchPoseToIntakeOne(){
        return instant(() -> StartPose = startPoseIntakeOne);
    }
    private Path LaunchTwo() { // This our second launching path
        return Paths.curve(startPoseLaunchTwo, controlPoseLaunchTwo, endPoseLaunchTwo);
    }
    private Path IntakeOne() { // This is our first intake path
        return Paths.curve(startPoseIntakeOne, controlPoseIntakeOne, endPoseIntakeOne);
    }
    private Command switchPoseToLaunchTwo(){
        return instant(() -> StartPose = startPoseLaunchTwo);
    }
    private Command Launching() { // This is how we launch
        return sequential(
                instant(() -> Launcher.setVelocityLauncherMotor(LAUNCHERTARGETVELOCITY)),
                waitMs(3000),
                instant(() -> Conveyor.allow()),
                waitMs(2000),
                instant(() -> Launcher.setVelocityLauncherMotor(0.0)),
                instant(() -> Conveyor.deny())
        );
    }
    private Command Intaking() { // This is how we intake
        return sequential(
                instant(() -> DriveTrain.setPowerBLMotor(0.5)),
                instant(() -> DriveTrain.setPowerFLMotor(0.5)),
                instant(() -> DriveTrain.setPowerBRMotor(0.5)),
                instant(() -> DriveTrain.setPowerFRMotor(0.5)),
                instant(() -> Intake.setPowerIntakeMotor(0.5)),
                instant(() -> Conveyor.setPowerConveyorMotor(1)),
                waitMs(5000),
                instant(() -> DriveTrain.setPowerBLMotor(0)),
                instant(() -> DriveTrain.setPowerFLMotor(0)),
                instant(() -> DriveTrain.setPowerBRMotor(0)),
                instant(() -> DriveTrain.setPowerFRMotor(0)),
                instant(() -> Intake.setPowerIntakeMotor(0)),
                instant(() -> Conveyor.setPowerConveyorMotor(0))
        );
    }
    private Command AutoRoutine() { //This is our auto routine
        return sequential(
                follow(follower, LaunchOne()),
                switchPoseToIntakeOne(),
                Launching(),
                follow(follower, IntakeOne()),
                switchPoseToLaunchTwo(),
                Intaking(),
                follow(follower, LaunchTwo()),
                Launching()
        );
    }

    @Override public void init() { // Initialization
        Scheduler.reset();
        follower = Constants.create(hardwareMap); // Lets the follower use our motors
        follower.setPose(StartPose);
        follower.update();
        Launcher.init(hardwareMap);
        DriveTrain.init(hardwareMap);
        Conveyor.init(hardwareMap);
        Intake.init(hardwareMap);
    }
    public void start() { // Where we play our auto routine
        schedule(AutoRoutine());
    }
    @Override public void loop() { // Where we constantly update and execute our code
        follower.update();
        Scheduler.execute();
    }
}