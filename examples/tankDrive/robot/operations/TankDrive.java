// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.operations;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.operation.Context;
import frc.operation.OpMode;
import frc.operation.OpState;
import frc.operation.Operation;
import frc.robot.Robot;


public class TankDrive extends Operation {

    public TankDrive(){
        this.opPriority = 1;
        this.opDaemon = false;
    }

    @Override
    protected OpState invoke(Context context){
        return OpState.RUNNING;
    }

    @Override
    protected OpState execute(Context context) {
        // check operation mode
        if(context.getOpMode() != OpMode.TELEOP){
            // not in teleop, stop
            return OpState.FINISHED;
        }

        boolean has_control = Robot.chassis.capture(this);
        if(has_control){
            // have control over chassis, read from joystick and drive
            double speedl = Robot.stick.getY(Hand.kLeft);
            double speedr = Robot.stick.getY(Hand.kRight);
            Robot.chassis.drive(speedl, speedr);
        }
        else{
            // chassis is captured by other operations, just wait
        }
        return OpState.RUNNING;
    }

    @Override
    protected void onInterrupt(Context context) {
        if(Robot.chassis.isCurrentOwner(this)){
            // this is the current owner, set speed to zero
            Robot.chassis.drive(0, 0);
        }
    }

    
}
