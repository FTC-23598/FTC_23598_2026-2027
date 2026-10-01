# Questions: State machines (EXAMPLE)
Asked by: Example Student
Date: 2026-09-30

This is a sample file to show the format. Copy the template in README.md
when you ask your own questions.

## Question 1
Status: Answered

What is a state machine, and why do we use one for autonomous? I looked at
the Pedro example code and saw a `switch` statement with numbers but didn't
understand how it works.

### Answer
A state machine breaks a routine into numbered steps called "states." The
robot is always in exactly one state, and each state has a rule for when to
move to the next one.

The important idea: in an iterative OpMode, `loop()` runs over and over, many
times per second. So instead of telling the robot "do step 1, wait, then do
step 2," each time through the loop we ask "which state am I in, and am I
done with it yet?"

Here is a generic example with a claw:

```java
private int state = 0;
private ElapsedTime timer = new ElapsedTime();

@Override
public void loop() {
    switch (state) {
        case 0: // Close the claw
            claw.setPosition(CLAW_CLOSED);
            timer.reset();
            state = 1;
            break;
        case 1: // Wait for the claw to finish closing
            if (timer.seconds() > 0.5) {
                state = 2;
            }
            break;
        case 2: // Done, do nothing
            break;
    }
    telemetry.addData("State", state);
    telemetry.update();
}
```

Notice that nothing in `loop()` pauses or waits with `sleep()`. Case 1 just
checks the timer and moves on. That keeps the loop running fast, so other
things (like drive code or telemetry) keep working while the claw closes.

Read more: [Game Manual 0: Finite State Machines](https://gm0.org/en/latest/docs/software/concepts/finite-state-machines.html)

Something to think about: what would happen if case 0 did not change `state`
to 1?

Answered by: Drafted with Claude, reviewed by Phil

## Question 2
Status: Answered

How does my auto know when the robot has finished driving a path?

### Answer
Pedro's `Follower` can tell you. `follower.isBusy()` is `true` while the robot
is following a path, so `!follower.isBusy()` means the path is finished and
the robot has stopped at the end.

If you only care that the robot reached the end of the path, and not that it
has fully stopped, use `follower.atParametricEnd()` instead. It is faster,
but a little less precise.

Two things to remember:
- Call `follower.update()` every time through `loop()`. If you don't, the
  robot will not follow the path at all.
- Check for "path finished" inside a state, the same way case 1 above
  checks the timer.

Read more: [Pedro Pathing: Follow States](https://pedropathing.com/docs/pathing/guide/follow-state)

Something to think about: in the example from Question 1, which state would
you put this check in if the robot drove somewhere before closing the claw?

Answered by: Drafted with Claude, reviewed by Phil
