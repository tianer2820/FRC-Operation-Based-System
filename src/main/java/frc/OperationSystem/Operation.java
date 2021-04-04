// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.OperationSystem;


public class Operation { // base class of operations
    OperationState state = OperationState.WAITING;

    protected void report(ReportType type, String str) {}
    protected void start_operation(Operation operation) {}

    public OperationState getState(){
        return this.state;
    }

    public void interrupt(){
        
    }
}



