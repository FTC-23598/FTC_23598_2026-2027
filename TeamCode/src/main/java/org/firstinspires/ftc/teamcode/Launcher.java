package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {
    //DcMotorEx lets us use setVelocity(), which sets the velocity to the desired amount.
    public DcMotorEx LauncherMotor;

    public void init(HardwareMap hwMap) {LauncherMotor = hwMap.get(DcMotorEx.class, "launcher_motor");}

    //Specifically sets velocity for the Launcher motor
    public void setVelocityLauncherMotor(double power) {LauncherMotor.setVelocity(power);}
    //Gets velocity for the Launcher motor
    public double getVelocityLauncher() {return LauncherMotor.getVelocity();}
}