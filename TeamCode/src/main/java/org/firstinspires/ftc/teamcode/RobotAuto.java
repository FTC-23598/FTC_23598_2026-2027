package org.firstinspires.ftc.teamcode;
import com.pedropathing.api.Paths;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;
import com.pedropathing.api.Paths.*;
import com.pedropathing.math.Pose;
import com.pedropathing.api.PoseFactory;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous public class RobotAuto extends OpMode {
    // Pedro Pathing
    private Follower follower;
    private Path pathCurve;
    Launcher Launcher = new Launcher();
    Conveyor Conveyor = new Conveyor();
    //This variable shows the desired launch RPM when shooting
    int launcherTargetVelocity = (1250/28) * 60; // I don't know desired launch target (can be changed).
    //This variable shows the least amount of launch RPM for shooting
    int launcherMinVelocity = (1200/28) * 60; // I don't know desired launch target (can be changed).

    @Override public void init()
    {
        follower = Constants.create(hardwareMap);
        PoseFactory PFDeg = PoseFactory.degrees();
        // PoseFactory Degrees
        Pose startPose = PFDeg.of(0, 0, 0); // Starting position (Should be changed)
        Pose controlPose = PFDeg.of(0, 0, 0); // Point which you base the vertex of your curve on (Should be changed)
        Pose endPose = PFDeg.of(0, 0, 0); // Ending Position (Should be changed)
        follower.setPose(startPose);
        pathCurve = Paths.curve(startPose, controlPose, endPose);
    }
    public void start() {
        follower.follow(pathCurve);
    }
    @Override public void loop() {
        follower.update();
        if (!follower.isBusy()) {
            Launcher.setVelocityLauncherMotor(launcherTargetVelocity);
        } else {
            Launcher.setVelocityLauncherMotor(0);
        }
        if (Launcher.getVelocityLauncher() > launcherMinVelocity) {
            Conveyor.allow();
        } else {
            Conveyor.deny();
        }
    }
}
