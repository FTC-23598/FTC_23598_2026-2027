package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
public class Conveyor {
    public DcMotor conveyorMotor;
    //CRServo is used specifically for servos
    public CRServo conveyorServo;


    public void init(HardwareMap hwMap) {
        conveyorServo = hwMap.get(CRServo.class, "conveyor_servo");
        conveyorMotor = hwMap.get(DcMotor.class, "intake_motor");
    }
    //Specifically powers the Conveyor motor
    public void setPowerConveyorMotor(double power) {conveyorMotor.setPower(power);}

    //Contracts rod attached to allow balls [this is why the method is called allow()]
    public void allow() {conveyorServo.setPower(-1.0);} //TODO: Label what these methods do.
    //Deny pushes the rod attached to servo to a height were the balls can go through
    public void deny() {conveyorServo.setPower(0);}
}
