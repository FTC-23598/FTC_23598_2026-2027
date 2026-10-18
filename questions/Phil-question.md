# Questions: Phil's Questions
Asked by: Phil
Date: 2026-10-11

## Question 1
Status: Answered

What does "Final" mean during variable declaration? How about "Static"?

### Answer
Both are **keywords** (words with a special meaning in Java) that you put in front of
a variable when you declare it. In Java they are written in lowercase: `final` and `static`.

**`final` means "this can only be set once."**
After a `final` variable gets its value, you can't change it. If you try, Android Studio
shows a red error before you can even build.

```java
final double CLAW_OPEN = 0.8;
CLAW_OPEN = 0.5;   // Error: cannot assign a value to final variable
```

Why use it? It protects values that should never change (like a servo position you
already tuned) from being changed by accident somewhere else in the code.

**`static` means "this belongs to the class, not to one object."**
A **class** is the blueprint (like `Claw`). An **object** is one thing built from that
blueprint (like `new Claw()`). Normally every object gets its own copy of each variable.
A `static` variable has only one copy, shared by everything, and you can use it
through the class name without creating an object first:

```java
public class Lift {
    public static int MAX_HEIGHT = 2000;  // one shared copy
    public int targetHeight = 0;          // each Lift object has its own
}

int limit = Lift.MAX_HEIGHT;  // works without "new Lift()"
```

**Together: `static final` is how Java writes a constant.**
A **constant** is a named value that is shared and never changes. By convention,
constants are named in ALL_CAPS with underscores:

```java
private static final double INTAKE_POWER = 0.7;
```

Where to look in our repo:
- `TeamCode/.../ConceptAprilTag.java` line 74: `private static final boolean USE_WEBCAM`
  is a constant.
- The Pedro tuning files (in `teamcode/pedro/procedures/`) use many `public static`
  variables *without* `final`. Those are meant to be changed while tuning, so they
  can't be `final`.

Guiding question: in a file with a claw, which numbers would you make `static final`,
and which ones need to change while the OpMode is running?

More reading: [Oracle Java tutorial on class variables and constants](https://docs.oracle.com/javase/tutorial/java/javaOO/classvars.html)

Answered by: Drafted with Claude, reviewed by Phil


## Question 2
Status: Answered

If we have a subclass that will be used in a parent class, does that need the @teleop tag? We never want to run just that program (for example, intake or drivetrain)

### Answer
No. Only the class you pick on the Driver Station needs `@TeleOp`.

**What `@TeleOp` does.** It is an **annotation** (a tag that starts with `@`). It tells
the FTC SDK: "add this program to the TeleOp list on the Driver Station." The SDK can
only run a class that is an **OpMode**, meaning it `extends OpMode` or `extends LinearOpMode`.

**A quick word on names.** A **subclass** is a class that `extends` another class
(an OpMode is a subclass of `OpMode`). An intake class that the robot class *uses* is
not a subclass. It is usually called a **helper class** or a **subsystem**. The robot
class *has* an intake; it does not *extend* one.

```java
public class Claw {            // helper class: no @TeleOp, does not extend OpMode
    public void init(HardwareMap hwMap) { ... }
    public void open() { ... }
}

@TeleOp(name = "Main TeleOp")  // only the OpMode gets the tag
public class MainTeleOp extends OpMode {
    Claw claw = new Claw();
    ...
}
```

If you put `@TeleOp` on a class that is not an OpMode, it will not show up in the list
anyway, and the SDK will likely show a warning that the class doesn't inherit from `OpMode`.

Where to look in our repo:
- `Intake.java` line 6: compare it to the example above.
- `Robot.java` line 6: this is the class drivers actually pick.
- `DriveTrain.java` lines 54-56: this is still a full OpMode copied from a sample.
  `@Disabled` hides it from the list. If it becomes a helper class like the intake,
  think about which lines would change.
- `Intake.java` line 9: notice it takes `HardwareMap` as an input. A helper class is
  not an OpMode, so it does not have its own `hardwareMap`. The OpMode passes it in.

Guiding question: if you removed the tag from a helper class, would anything on the
Driver Station change? Why or why not?

Answered by: Drafted with Claude, reviewed by Phil

## Question 3
Status: Answered

Should we define the button inputs at the sub-class level, or at the higher level robot class? 

### Answer
Read the gamepad in the robot class (the OpMode). Keep the subsystems free of
gamepad code. A subsystem should offer actions with clear names, and the OpMode decides
which button triggers which action.

```java
// In the Lift class: actions only, no gamepad
public void goUp()   { liftMotor.setPower(0.8); }
public void goDown() { liftMotor.setPower(-0.5); }
public void stop()   { liftMotor.setPower(0); }

// In the OpMode loop: button mapping lives here
if (gamepad2.dpad_up)        lift.goUp();
else if (gamepad2.dpad_down) lift.goDown();
else                         lift.stop();
```

Why this way:
1. **Autonomous has no driver.** If the lift class reads `gamepad2`, you can't reuse
   it in auto. If it only has `goUp()`, auto can call the same method.
2. **All controls in one place.** Drivers will want buttons changed. It is easier
   to find them in one file than to hunt through every subsystem.
3. **No button conflicts.** If two subsystems both read the same button, you won't
   notice until both move at once. In one file you can see every button that is used.

For driving, the same idea applies. The drive class takes numbers (forward, strafe,
turn), and the OpMode reads the sticks and passes those numbers in.

Where to look in our repo:
- `Robot.java` lines 23-29 and `Intake.java` lines 15-17 already follow this pattern.
- `DriveTrain.java` lines 106-115 read the gamepad. Compare that to the `drive(...)`
  method at line 137. Which part belongs in the robot class, and which part belongs in
  a drivetrain helper class?

Guiding question: if the intake needed to run during autonomous, which version would
let you do that without copying code: one that reads `gamepad1.a`, or one with `takeIn()`?

Answered by: Drafted with Claude, reviewed by Phil
