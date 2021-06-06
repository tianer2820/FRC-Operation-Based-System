// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import frc.operation.SubSystem;

/** 
 * No special things needed!
 */
public class Chassis extends SubSystem {
    private Spark motorL;
    private Spark motorR;

    public Chassis(int channel_left, int channel_right){
        super();
        motorL = new Spark(channel_left);
        motorR = new Spark(channel_right);
    }

    public void drive(double left, double right){
        motorL.set(clampValue(-1, 1, left));
        motorR.set(clampValue(-1, 1, right));
    }

    private double clampValue(double min, double max, double value){
        return Math.min(max, Math.max(min, value));
    }
}
