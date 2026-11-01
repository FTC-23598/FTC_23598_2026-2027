package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {
    //DcMotorEx lets us use setVelocity(), which sets the velocity to the desired amount.
    public DcMotorEx LauncherMotor;

    public void init(HardwareMap hwMap) {LauncherMotor = hwMap.get(DcMotorEx.class, "launcher_motor");}
    //TODO: You never tell the motor to run in velocity mode. Take a look at the StarterbotTeleopMecanum code to find the right line.
    public double tpsToRPM(double velocity) {return (velocity / 28) * 60;}
    //Specifically sets velocity for the Launcher motor
    public void setVelocityLauncherMotor(double velocityRPM) {
        double rpmToTps = (velocityRPM / 60) * 28;
        LauncherMotor.setVelocity(rpmToTps);
    }
    //TODO: You have the variable name as "power", but you are setting velocity, not power. These are two very different things, like the gas pedal and the speedometer in a car.
    // TODO: Determine what units velocity is in and see if you want to do a unit conversion.
    //Gets velocity for the Launcher motor
    public double getVelocityLauncher() {return LauncherMotor.getVelocity();}
}