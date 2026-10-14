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
