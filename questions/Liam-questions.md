# Asking programming questions

## How to ask
1. On the repo page on GitHub.com, open the `questions` folder.
2. Click "Add file", then "Create new file".
3. Name it `YYYY-MM-DD-firstname-topic.md`, for example `2026-10-02-sam-state-machines.md`.
4. Copy the template below, fill in your questions, and click "Commit changes".
5. Pull the latest changes before the next meeting to see answers.

One file can hold several questions on the same topic.

## Template

# Questions: (topic)
Asked by: (Liam Patel)
Date: (2026-10-14)

## Question 1
Status: Answered

Should I just use RobotTeleopMecanumFieldRelativeDrive as my drivetrain code?
### Answer
It is a good place to start, and a good sample to learn from. Before it becomes our
real drivetrain code, there are two things to think about.

**First, what "field relative" means.** In **robot relative** driving, pushing the stick
forward moves the robot toward wherever its front is pointing, like an RC car. In
**field relative** driving (also called field centric), pushing forward always moves the
robot away from the driver, no matter which way the robot is facing. To do that, the
code needs the robot's **heading** (the direction it is facing). The sample gets it
from the IMU in the Control Hub.

**Thing 1: it is a whole OpMode, not a helper class.** The sample has `@TeleOp` and
`extends OpMode`, so it runs by itself. Our robot code is set up so `Robot.java` is the
OpMode and things like `Intake.java` are helper classes it uses. To fit that pattern,
the drive code would need to look more like `Intake.java`: an `init(HardwareMap)` method
plus a drive method, with the gamepad reading done in `Robot.java`.
It looks like this has started in `DriveTrain.java`. Compare its lines 54-56 to
`Intake.java`. (Phil's Questions 2 and 3 in `Phil-questions.md` cover this in more detail.)

**Thing 2: where the heading comes from.** Our robot uses Pedro Pathing with a goBILDA
Pinpoint, and Pedro already tracks the robot's position and heading. Pedro 3 has
field-centric TeleOp driving built in (`ManualDrive.fieldCentric(...)` with
`follower.manual(...)`). Using Pedro for TeleOp means auto and TeleOp use the same
heading source, and you can carry the robot's position from auto into TeleOp.
Read: [Pedro Pathing: TeleOp Usage](https://pedropathing.com/docs/pathing/guide/teleop-usage).
Note the tip on that page: localization has to be tuned and working first.

**Where does `ManualDrive` live?** Not in our repo. If you search `TeamCode` for it,
you won't find it. It is part of the Pedro Pathing **library** (code someone else wrote
that our project downloads and uses).
- `build.dependencies.gradle` lists `com.pedropathing:revhub:3.0.1`.
- That library pulls in another one, `com.pedropathing:core:3.0.1`, and `ManualDrive`
  is inside it as `com.pedropathing.follower.ManualDrive`.
- When Android Studio builds the project, Gradle (the build tool) downloads these
  libraries into a cache folder on your computer, under your user folder in
  `.gradle\caches\modules-2\files-2.1\com.pedropathing\core\3.0.1\`.

To use it, add `import com.pedropathing.follower.ManualDrive;` at the top of your
OpMode, the same way you import `DcMotor` from the FTC SDK. To see what's inside,
Ctrl+click `ManualDrive` in Android Studio. Because Android Studio only has the compiled
version, it shows code rebuilt from that version, without the original comments.

Some code we use is in our repo, and some comes from libraries like the FTC SDK and
Pedro. You can read library code to learn how it works, but don't edit it. Our code
should only call it.

**So:** using the sample now is fine for learning and for getting the robot driving.
Once Pedro localization is tuned, talk with the team about switching TeleOp driving to Pedro.

Guiding question: in the sample, find the line that reads the robot's heading. If the
robot's heading reading were wrong by 90 degrees, what would happen when you push the
stick forward?

AI-assisted: Claude, drafted this answer (reviewed by Phil)

## Question 2
Status: Answered

Would you set the power of the conveyor servo to 1.0 to allow the balls to be launched?
### Answer

Yes, that is exactly what we would do. Some servos can be set to a continuous mode where they can 
rotate infinitely rather than only a set number of degrees. In code, a continuous servo is a
`CRServo`, and you set its speed with `setPower()`: 0 stops the servo from running at all,
-1 moves the servo at full speed backwards, and (+)1 moves the servo at full speed forward. 

Here is some reading on programming and choosing servos:

[REV Duo: Programming Servos](https://docs.revrobotics.com/duo-control/hello-robot-java/part-1/programming-servos)

[Game Manual 0: Choosing a Servo](https://gm0.org/en/latest/docs/power-and-electronics/servo-guide/choosing-servo.html)

Here is an example of using a continuous servo and a regular servo in code:

```java
@TeleOp(name = "Servo Example")
public class ServoExample extends OpMode {
    private CRServo roller;  // continuous servo: you set its speed
    private Servo claw;      // regular servo: you set its position

    @Override
    public void init() {
        roller = hardwareMap.get(CRServo.class, "roller");
        claw = hardwareMap.get(Servo.class, "claw");
    }

    @Override
    public void loop() {
        // Hold A to spin the roller forward, or X to spin it backward. Let go and it stops.
        if (gamepad1.a) {
            roller.setPower(1.0);   // full speed forward
        } 
        else if (gamepad1.x) {
            roller.setPower(-1.0);  // full speed backward
        }
        else {
            roller.setPower(0.0);   // stop
        }
        // Hold B to open the claw. Let go and it closes.
        if (gamepad1.b) {
            claw.setPosition(0.8);  // open. The value here is a percentage of the servo's range.
        } else {
            claw.setPosition(0.2);  // closed
        }
    }
}
```

Notice the difference: `CRServo` uses `setPower()` (speed, -1 to 1, where 0 stops),
and `Servo` uses `setPosition()` (where to go, 0 to 1). In the Driver Station
configuration, the roller must be set up as a "Continuous Rotation Servo" and the
claw as a regular "Servo", or `hardwareMap.get()` will fail.

AI-assisted: Claude, wrote the servo code example and edited wording (reviewed by Phil)