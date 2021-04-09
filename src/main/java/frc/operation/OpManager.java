// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.operation;

import java.util.ArrayList;
import java.util.Iterator;

/** The operation Manager */
public class OpManager {
    ArrayList<Operation> opList = new ArrayList<Operation>();
    OpMode opMode = OpMode.NONE;
    ReportHandler reportHandler;

    /** Initiallize the manager */
    public void init(ReportHandler handler) {
        this.reportHandler = handler;
    }

    /** Initiallize the manager with the default ReportHandler */
    public void init() {
        this.reportHandler = new ReportSender();
    }

    public void setMode(OpMode mode) {
        this.opMode = mode;
    }

    /** call this periodically to run all operations. */
    public void update() {
        cleanupOperationList();
        Iterator<Operation> iter = opList.iterator();
        Context context = new Context();
        context.mode = this.opMode;
        context.opManager = this;

        while (iter.hasNext()) {
            Operation op = iter.next();
            OpState state = op.execute(context);
            op.state = state;
        }
    }

    /**
     * Start a new operation in parallel
     * 
     * @param operation instance of the operation to start
     */
    public void startOperation(Operation operation) {
        // set manager
        operation.manager = this;
        // create new context obj
        Context context = new Context();
        context.mode = this.opMode;
        context.opManager = this;
        // call invoke
        OpState state = operation.invoke(context);
        operation.state = state;
        // add to list if not ended
        if (operation.isEnded()) {
            return;
        } else {
            opList.add(operation);
        }
    }

    /** interrupt all operations */
    public void interruptAll() {
        ArrayList<Operation> oplist = (ArrayList<Operation>) opList.clone();
        Iterator<Operation> iter = oplist.iterator();
        while (iter.hasNext()) {
            Operation op = iter.next();
            op.interrupt();
        }
    }

    void reportMessage(Operation operation, ReportType type, String message) {
        reportHandler.reportMessage(operation, type, message);
    }

    /** Used internally to remove ended operations */
    void removeOperation(Operation operation) {
        opList.remove(operation);
    }

    /** check if all operation has ended */
    public boolean allOperationEnded() {
        Iterator<Operation> iter = opList.iterator();
        while (iter.hasNext()) {
            Operation op = iter.next();
            if (op.isEnded()) {
                return true;
            }
        }
        return false;
    }

    private boolean cleanupOperationList() {
        Iterator<Operation> iter = opList.iterator();
        boolean hasRunning = false;
        while (iter.hasNext()) {
            Operation op = iter.next();
            if (op.isEnded()) {
                removeOperation(op);
            } else {
                hasRunning = true;
            }
        }
        return hasRunning;
    }

}
