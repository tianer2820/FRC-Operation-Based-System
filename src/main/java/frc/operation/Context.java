// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.operation;

/** Add your docs here. */
public class Context { // a class that provide running context
    public OpManager opManager;
    public OpMode mode;

    public OpManager getOpManager(){
        return opManager;
    }

    public OpMode getOpMode(){
        return mode;
    }
}