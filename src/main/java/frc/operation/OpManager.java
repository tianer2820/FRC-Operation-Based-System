// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.operation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/** The operation Manager */
public class OpManager implements ReportHandler{
    ArrayList<Operation> opList = new ArrayList<Operation>();
    ArrayList<Operation> opbufList = new ArrayList<Operation>(); // to avoid modify during iteration
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
        // append the buffer list
        opList.addAll(opbufList);
        opbufList.clear();

        cleanupOperationList();

        Iterator<Operation> iter = opList.iterator();
        Context context = this.getContext();

        while (iter.hasNext()) {
            Operation op = iter.next();
            OpState state = op.execute(context);
            op.opState = state;
        }
    }

    /**
     * Start a new operation in parallel
     * 
     * @param operation instance of the operation to start
     */
    public void startOperation(Operation operation) {
        operation.opManager = this;
        Context context = this.getContext();
        // call invoke
        OpState state = operation.invoke(context);
        operation.opState = state;
        // add to list if not ended
        if (operation.isEnded()) {
            return;
        } else {
            opbufList.add(operation);
        }
    }

    /** interrupt all operations */
    public void interruptAll() {
        Iterator<Operation> iter = opList.iterator();
        while (iter.hasNext()) {
            Operation op = iter.next();
            op.interrupt();
        }
    }

    @Override
    public void reportMessage(Operation operation, ReportType type, String message) {
        reportHandler.reportMessage(operation, type, message);
    }

    /**generate a context object */
    Context getContext(){
        Context context = new Context();
        context.opMode = this.opMode;
        context.opManager = this;
        return context;
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
        ArrayList<Operation> tempList = (ArrayList<Operation>)opList.clone();
        Iterator<Operation> iter = tempList.iterator();
        boolean hasRunning = false;
        while (iter.hasNext()) {
            Operation op = iter.next();
            if (op.isEnded()) {
                opList.remove(op);
            } else {
                hasRunning = true;
            }
        }
        return hasRunning;
    }
}
