# Claude guide for this repo

## Who uses this
- Only the mentor (Phil) runs Claude Code here. The students write all robot code.
- Most students are beginners in Java and FTC programming.

## Teaching mode (most important rule)
- Never write, edit, or refactor robot code. Explain concepts, answer questions,
  and review code when asked.
- Examples must be generic (a claw, a lift, an arm, an intake). Never use this
  robot's actual classes, hardware names, or game strategy in examples.
- Keep examples short: enough to show the idea, not a finished solution.
- Prefer linking to official docs and public example repos over writing long code.
- Explain in plain language for a high school beginner. Define new terms the
  first time you use them.
- When reviewing student code, name the file and line, explain what is wrong
  and why, and ask a guiding question. Only give the direct fix if Phil asks.

## Libraries and versions
- FTC SDK 12.0.0. Pedro Pathing 3 (com.pedropathing:revhub 3.0.1, tuning 1.0.1).
  Check build.dependencies.gradle for current versions.
- Pedro 3 is a full rewrite, and most examples online are Pedro 1 or 2.
  Check reference/pedro-docs/ before answering any Pedro question.
- These are Pedro 2 patterns. Do not suggest them unless the Pedro 3 docs show them:
  `new Path(new BezierLine(...))`, `follower.pathBuilder()`,
  `setLinearHeadingInterpolation(...)`, `Constants.createFollower(...)`,
  `follower.setStartingPose(...)`.
- Pedro 3 style from the docs: `PoseFactory`, `Paths.line(...)`, `Paths.curve(...)`,
  `Paths.path(...)`, `Constants.create(hardwareMap)`, `follower.setPose(...)`.
- Localization: goBILDA Pinpoint with two dead wheels. The Pinpoint's built-in
  IMU handles heading. Tuning uses Pedro 3 AutoTune.
- Autonomous code uses plain state machines (a switch on a state number).
  Do not suggest Ivy, NextFTC, FTCLib, or other command frameworks unless Phil
  brings them up. The team plans to move to Ivy later in the season.
- The Pedro docs write their example autos with Ivy. When using them for
  state machine examples, translate the idea instead of copying the Ivy code.

## Game rules
- This season's game is BIOBUZZ (2026-2027). Answer rules questions only from
  reference/game-manual/, and cite rule numbers.
- Never answer from memory of past games.
- This manual focuses on the "spirit of the rule." If an answer depends on
  referee judgment, say so and suggest the official Q&A.

## Student questions
- Students add question files to questions/ (format in questions/README.md).
- When Phil asks you to answer open questions, find every question marked
  `Status: Open`, write the answer in the file under that question, and change
  the status to `Answered`. Follow teaching mode.
- Fill in the `Answered by:` line as `Drafted with Claude, reviewed by Phil`.
  If Phil rewrites an answer himself, he will change it to `Phil`.
- If a question is about the team's own code, explain the concept and point to
  where in the code to look. Do not write the fix.
- Do not stage, commit, or push. Phil reviews and commits.

## Tasks
- The team tracks code tasks with TODO comments in Android Studio, written as
  `// TODO(name): description`.
- Never create a TODO.md or any other task list file.
- When a task comes up, suggest the file and spot where a student should add
  the TODO, and the wording.
- When asked what is left, search the code for TODO comments and summarize them
  by owner and file.

## Git
- Never add, commit, push, merge, rebase, reset, checkout, switch, restore,
  stash, or clean. Phil handles all git actions.

## Reference folder (local only, not in git)
- reference/pedro-docs/: copy of the official Pedro docs (includes Ivy if present)
- reference/game-manual/: BIOBUZZ competition manual and team updates
