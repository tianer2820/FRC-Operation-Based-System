// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.operation;

import java.util.ArrayList;

/**The operation Manager */
public class OpManager {
    ArrayList<Operation> operation_list = new ArrayList<Operation>();
    OpMode operation_mode = OpMode.NONE;
    ReportHandler report_handler;

    /**
     * Initiallize the manager
     */
    public void init(ReportHandler handler) {
        this.report_handler = handler;
    }

    public void setMode(OpMode mode){
        operation_mode = mode;
    }

    /** call this periodically to run all operations. */
    public void update() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Start a new operation in parallel
     * 
     * @param operation instance of the operation to start
     */
    public void startOperation(Operation operation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** interrupt all operations */
    public void interruptAll(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    void reportMessage(Operation operation, ReportType type, String message){
        report_handler.reportMessage(operation, type, message);
    }

    /**Used internally to remove ended operations */
    void removeOperation(Operation operation){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean allOperationEnded(){
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
