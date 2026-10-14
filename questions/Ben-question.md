# Questions: State machines (EXAMPLE)
Asked by: Ben
Date: 2026-10-11

## Question 1
Status: Answered

what is the best way to find resources to learn to code for ftc. 

### Answer
Good question. There's a lot online, and much of it is outdated, so it helps to know
where to start. Learn in this order:

**1. Basic Java first (a few hours a week until it clicks)**
FTC code is written in Java. Learn variables, `if` statements, loops, methods, and
classes before diving into robot code.
- [Game Manual 0: Java basics](https://gm0.org/en/latest/docs/software/tutorials/java-basics.html):
  short and written for FTC students.
- [Codecademy: Learn Java](https://www.codecademy.com/learn/learn-java) (free course):
  lets you practice in your browser.

**2. FTC basics: how an OpMode works**
An **OpMode** is a program you choose on the Driver Station, like a TeleOp or an Autonomous.
- [FTC Docs](https://ftc-docs.firstinspires.org/en/latest/): the official docs.
  Start with "Programming Resources" and the Android Studio section.
- [Game Manual 0: Software](https://gm0.org/en/latest/docs/software/index.html):
  explains common topics like motors, encoders, and state machines.

**3. Sample code that is already in our project**
The FTC SDK comes with dozens of example OpModes. In Android Studio, open
`FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/`.
Files starting with `Basic` and `Robot` are good first reads. Pick one, read it line
by line, and try to explain each line out loud.

**4. Pedro Pathing (for autonomous, later)**
- [Pedro Pathing docs](https://pedropathing.com/docs/pathing)
- Warning: we use Pedro **3**. Most videos and GitHub examples online are Pedro 1 or 2
  and will not work if you copy them. Always check the official docs first.

**Tips for searching on your own**
- Check the date. FTC changes every year, so prefer results from the last year or two.
- Prefer official docs over random videos.
- If you get stuck, write a question here with the file name and what you tried.

All of these links are also in `reference/README.md` in our repo.

Guiding question: after reading one sample OpMode, can you point to the part that runs
once at the start and the part that runs over and over?

Answered by: Drafted with Claude, reviewed by Phil
