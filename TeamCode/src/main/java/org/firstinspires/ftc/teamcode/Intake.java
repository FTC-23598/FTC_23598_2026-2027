package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public DcMotor intakeMotor;

    public void init(HardwareMap hwMap) {
        intakeMotor = hwMap.get(DcMotor.class, "intake_motor");
    }
    public void setPower(double power) {
        intakeMotor.setPower(power);
    }
    public void takeIn() { intakeMotor.setPower(1.0); }
    public void eject()  { intakeMotor.setPower(-1.0); }
    public void stop()   { intakeMotor.setPower(0.0); }
    // Help with AI
}
