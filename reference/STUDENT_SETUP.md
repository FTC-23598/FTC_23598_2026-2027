# Setting up Android Studio for our robot code

_AI-assisted: Claude, drafted this guide (reviewed by Phil). Written for FTC SDK 12.0 (Gradle 9.1.0).
See [Sources](#sources) at the bottom._

Follow these steps once on each laptop. If something goes wrong, check
[Fixing common errors](#fixing-common-errors) before asking for help.

## Words you will see

- **Android Studio**: the program we use to write and build robot code.
- **Gradle**: the tool inside Android Studio that turns our code into the robot app.
  "Building" and "syncing" are both Gradle doing its job.
- **Sync**: Gradle reading the project's setup files and downloading what it needs.
  It runs when you open the project and whenever setup files change.
- **JDK** (Java Development Kit): the Java program Gradle runs on.
  Android Studio comes with its own JDK, called the **JBR** (JetBrains Runtime).

## 1. Install Android Studio

Download Android Studio from <https://developer.android.com/studio>.
You need **Narwhal 3 Feature Drop or newer**. Older versions cannot open this project.
Use the default options in the installer.

## 2. Get the project

Ask Phil which way to download the project (git clone or GitHub Desktop).
Then in Android Studio choose **File > Open**, pick the project folder
(the one with `build.gradle` and the `TeamCode` folder inside), and click **OK**.
If Android Studio asks whether to trust the project, choose **Trust Project**.

## 3. Wait for the first sync

The first sync downloads a lot and can take several minutes.
Watch the progress bar at the bottom right of the window.
Your laptop must be on the internet for the first sync.
When it finishes, open the **Build** tab at the bottom of the screen.
A green check mark means you are done. A red error means go to
[Fixing common errors](#fixing-common-errors).

**Say no to upgrade or downgrade offers.** Android Studio sometimes pops up a
message offering to upgrade Gradle, or to change the "Android Gradle Plugin"
(AGP) version. Close the message without accepting. The FTC SDK picks
these versions on purpose, and changing them breaks the build.

## 4. Check that it builds

Choose **Build > Make Project**. The Build tab should end with `BUILD SUCCESSFUL`.

## 5. Hide false errors in question files

The question files have short Java examples that are not full programs, so
Android Studio marks them with red errors like "class or interface expected".
They are not real problems. To hide them:

1. Open Settings (see the steps in the next section).
2. Go to **Languages & Frameworks > Markdown**.
3. Uncheck **Show problems in code fences** and click **OK**.

## Fixing common errors

### "Incompatible Gradle JVM version"

The full message looks something like this:

```
Incompatible Gradle JVM version
Gradle 9.1.0 requires Java 17 to 25 to run.
```

**What it means:** Our project uses Gradle 9.1.0 (you can see this in
`gradle/wrapper/gradle-wrapper.properties`). Gradle 9.1.0 only runs on
Java 17 through 25. Android Studio is pointed at a different Java on your laptop,
usually an older one you installed for something else.

**How to fix it:** tell Android Studio to use its own built-in Java.

1. Open Settings:
   - Windows: **File > Settings**
   - Mac: **Android Studio > Settings**
2. In the list on the left, go to
   **Build, Execution, Deployment > Build Tools > Gradle**.
3. Find the **Gradle JDK** dropdown.
4. Pick the option that starts with **jbr-** (for example `jbr-21`) or says
   **Embedded JDK** or **JetBrains Runtime**. This is the Java that comes with
   Android Studio.
5. Click **OK**.
6. Choose **File > Sync Project with Gradle Files**.
7. When the sync finishes, do step 4 again (Build > Make Project).

If there is no jbr option in the dropdown, choose **Download JDK...**,
pick version **21**, and use that instead.

Some FTC Docs pages say to install JDK 17 separately. Those pages were written
for an older Android Studio. JDK 17 still works if you already have it, but you
don't need to install it: the built-in jbr is enough.

### Sync fails right away, or Android Studio asks to downgrade AGP

Your Android Studio is too old. Our project needs **Narwhal 3 Feature Drop or
newer** (FTC SDK release notes, version 11.2). Do not accept the downgrade.
Install a newer Android Studio, then open the project again.

### Something else

Copy the first red line from the Build tab and add a question file in
`questions/` so Phil or a teammate can help.

## Why our code says Java 1.8 but Gradle needs Java 17

You might notice `JavaVersion.VERSION_1_8` in the project's build files.
That setting is the version of the Java *language* our robot code is written in.
The FTC SDK keeps it at 1.8 so code also works in OnBot Java, which only
understands Java 1.8.
The Gradle JDK setting above is a different thing: it is the Java that runs
Gradle on your laptop. Leave the 1.8 setting alone.

You may see a yellow warning that Java 1.8 is "deprecated" (old and going away).
That warning is expected and you can ignore it. The FTC SDK release notes say so.

## Sources

- Our project's Gradle version: [`gradle/wrapper/gradle-wrapper.properties`](../gradle/wrapper/gradle-wrapper.properties)
  (Gradle 9.1.0) and [`build.gradle`](../build.gradle) (Android Gradle Plugin 8.13.2).
- FTC SDK release notes in the project [`README.md`](../README.md): version 11.2
  (Gradle 9.1, AGP 8.13.2, needs Narwhal 3 Feature Drop, do not downgrade AGP) and
  the Ladybug notes (why the SDK stays on Java 1.8 for OnBot Java).
- Gradle's Java compatibility table:
  <https://docs.gradle.org/current/userguide/compatibility.html>
  (Gradle 9.1.0 runs on Java 17 to 25).
- JetBrains Markdown settings (code fence problems):
  <https://www.jetbrains.com/help/idea/markdown.html>
- FTC Docs, Installing Android Studio (older JDK 17 advice):
  <https://ftc-docs.firstinspires.org/en/latest/programming_resources/tutorial_specific/android_studio/installing_android_studio/Installing-Android-Studio.html>

If the FTC SDK updates Gradle again, check these sources and update this guide.
