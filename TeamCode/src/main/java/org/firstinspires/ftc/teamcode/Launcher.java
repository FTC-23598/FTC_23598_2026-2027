package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Launcher {
    //DcMotorEx lets us use setVelocity(), which sets the velocity to the desired amount.
    public DcMotorEx LauncherMotor;

    public void init(HardwareMap hwMap) {
        LauncherMotor = hwMap.get(DcMotorEx.class, "launcher_motor");
        //Sets in velocity mode
        LauncherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LauncherMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5)); //Should be changed
    }
    //TODO: You never tell the motor to run in velocity mode. Take a look at the StarterbotTeleopMecanum code to find the right line. (Done)
    //Specifically sets velocity for the Launcher motor
    public void setVelocityLauncherMotor(double velocityRPM) {
        LauncherMotor.setVelocity(velocityRPM/60 * 28); //Converts RPM to TPS (Ticks Per Second to the motor
    }
    //TODO: You have the variable name as "power", but you are setting velocity, not power. These are two very different things, like the gas pedal and the speedometer in a car. (Done)
    // TODO: Determine what units velocity is in and see if you want to do a unit conversion. (Done)
    //Gets velocity for the Launcher motor
    public double getVelocityLauncher() {return ((LauncherMotor.getVelocity()/28) * 60);} // Returns launcher velocity in RPM
}