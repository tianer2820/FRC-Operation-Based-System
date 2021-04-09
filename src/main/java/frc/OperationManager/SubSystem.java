// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.OperationManager;

import java.util.ArrayList;

/**
 * The base class for subsystems. Provides a ownership/priority system.
 */
public class SubSystem {
    ArrayList<Operation> owners = new ArrayList<Operation>();

    /**
     * Try to gain ownership of the subsystem.
     * @param operation the operation to give ownership to
     * @param force force get the ownership regardless of the priority
     * @return true if success
     */
    public boolean capture(Operation operation, boolean force){
        throw new UnsupportedOperationException("unimplemented yet");
    }

    /**
     * Try to gain ownership of the subsystem. Default to non-force.
     * @param operation the operation to give ownership to
     * @return true if success
     */
    public boolean capture(Operation operation){
        throw new UnsupportedOperationException("unimplemented yet");
    }

    /**
     * Give up the ownership
     */
    public void release(Operation operation){
        throw new UnsupportedOperationException("unimplemented yet");
    }

    /**
     * get the top ownership
     */
    public Operation getCurrentOwner(){
        throw new UnsupportedOperationException("unimplemented yet");
    }

    /**
     * check if the operation has current ownership
     * @param operation
     * @return true if yes
     */
    public boolean isCurrentOwner(Operation operation){
        throw new UnsupportedOperationException("unimplemented yet");
    }

    /**
     * clean up the oweners list, removing any ended operation
     */
    private void refreshOwnership(){
        throw new UnsupportedOperationException("unimplemented yet");
    }
}
