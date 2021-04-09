// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.OperationManager;

import java.util.ArrayList;

/**The operation Manager */
public class OpManager {
    ArrayList<Operation> operation_list = new ArrayList<Operation>();
    OperationMode operation_mode = OperationMode.NONE;

    /**
     * Initiallize the manager
     */
    public void init() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setMode(OperationMode mode){
        operation_mode = mode;
    }

    /**
     * Update the manager, running all operations. You should call this periodically
     */
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

    /**
     * interrupt all operations
     */
    public void interruptAll(){
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
