package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public DcMotor intakeMotor;

    public void init(HardwareMap hwMap) {intakeMotor = hwMap.get(DcMotor.class, "intake_motor");}
    //Specifically powers the Intake motor
    public void setPowerIntakeMotor(double power) {intakeMotor.setPower(power);} //TODO: Don't forget to add comments that describe what this method does! (Done)
//    public void takeIn() { intakeMotor.setPower(1.0); }
//    public void eject()  { intakeMotor.setPower(-1.0); }
//    public void stop()   { intakeMotor.setPower(0.0); }
}
