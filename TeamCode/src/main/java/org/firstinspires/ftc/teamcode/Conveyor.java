package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
public class Conveyor {
    public DcMotor conveyorMotor;
    public CRServo conveyorServo;


    public void init(HardwareMap hwMap) {
        conveyorServo = hwMap.get(CRServo.class, "conveyor_servo");
        conveyorMotor = hwMap.get(DcMotor.class, "intake_motor");
    }
    //Specifically powers the Conveyor motor
    public void setPowerConveyorMotor(double power) {conveyorMotor.setPower(power);}
//    public void setPowerServo(double power) {conveyorServo.setPower(power);}
//    public void takeIn() { conveyorMotor.setPower(1.0); }
//    public void eject()  { conveyorMotor.setPower(-1.0); }
//    public void stop()   { conveyorMotor.setPower(0.0); }
    public void allow() {conveyorServo.setPower(-1.0);} //TODO: Label what these methods do.
    public void deny() {conveyorServo.setPower(0);}
}
