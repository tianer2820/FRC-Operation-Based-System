// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.operation;

/**
 * The base class of all operations
 */
public abstract class Operation implements ReportHandler {
    public int priority = 0;
    public String priority_ignores[] = {};

    OpState state = OpState.WAITING;
    OpManager manager;

    /**
     * Report messages produced by an operation. Use "report()" instead if possible
     */
    public void reportMessage(Operation operation, ReportType type, String message) {
        this.manager.reportMessage(operation, type, message);
    }

    /**
     * Report a message, warning, or error to be shown to the user.
     * 
     * @param type    The type of the report
     * @param message The message to be sent
     */
    protected void report(ReportType type, String message) {
        manager.reportMessage(this, type, message);
    }

    /**
     * Start a new operation in the current operation. This return after the new
     * operation is done, so the operation should finish instantly. To start a
     * long-running operation, use "OpManager.startOperation()" instead.
     * 
     * @param operation The operation instance to start.
     * @return The end state of the operation
     */
    protected OpState runOperation(Operation operation) {
        // change priority
        operation.priority = Math.max(operation.priority, this.priority);
        // make new opmanager and start operation
        OpManager temp_manager = new OpManager();
        temp_manager.init(this);
        temp_manager.setMode(this.manager.operation_mode);
        temp_manager.startOperation(operation);
        while (!temp_manager.allOperationEnded()) {
            temp_manager.update();
        }
        return operation.getState();
    }

    /** Return the state of this operation */
    public OpState getState() {
        return this.state;
    }

    /** Force stop this operation and change its state to INTERRUPTED */
    public void interrupt() {
        this.onInterrupt();
        this.state = OpState.INTERRUPTED;
        manager.removeOperation(this);
    }

    void onInterrupt() {
    }

    /**
     * Check if this operation can be done in the context. Defaults to true.
     * Override this method to make custom checks.
     * 
     * @return true if can be done
     */
    public static boolean poll(Context context) {
        return true;
    }

    /**
     * Override this method to initiallize the operation.
     * 
     * @return the new state of this operation
     */
    protected OpState invoke(Context context) {
        return this.execute(context);
    }

    /**
     * Overeide this to implement your operation. This will be called repeatly if
     * the returned state is RUNNING.
     * 
     * @return the new state of this operation
     */
    abstract protected OpState execute(Context context);
}
