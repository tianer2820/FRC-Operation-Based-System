// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.OperationManager;

/**
 * The base class of all operations
 */
public abstract class Operation { 
    public int priority = 0;
    public String priority_ignores[] = {};

    OperationState state = OperationState.WAITING;
    OpManager manager;

    /**
     * Report a message, warning, or error to be shown to the user.
     * @param type    The type of the report
     * @param message The message to be sent
     */
    protected void report(ReportType type, String message) {
        manager.reportMessage(this, type, message);
    }

    /**
     * Start a new operation in the current operation. This return after the new
     * operation is done, so the operation should finish instantly. To start a long-running operation, use
     * OpManager.startOperation() instead.
     * 
     * @param operation The operation instance to start.
     */
    protected OperationState runOperation(Operation operation) {
        OpManager temp_manager = new OpManager();
        temp_manager.init();
        temp_manager.setMode(this.manager.operation_mode);
        temp_manager.startOperation(operation);
        while (!temp_manager.allOperationEnded()) {
            temp_manager.update();
        }
        return operation.getState();
    }

    /**
     * Return the state of this operation
     */
    public OperationState getState() {
        return this.state;
    }

    /**
     * Force stop this operation and change its state to INTERRUPTED
     */
    public void interrupt() {
        this.onInterrupt();
        this.state = OperationState.INTERRUPTED;
        manager.removeOperation(this);
    }

    void onInterrupt(){}

    /**
     * Check if this operation can be done in the context.
     * You should override this method.
     * @return true if can be done
     */
    public static boolean poll(Context context) {
        return true;
    }

    /**
     * Initiallize the operation.
     * You should override this method.
     * @return the new state of this operation
     */
    OperationState init(Context context) {
        return this.execute(context);
    }

    /**
     * Run the operation. This will be called multiple times if the returned state is RUNNING.
     * You must override this method.
     * @return the new state of this operation
     */
    abstract OperationState execute(Context context);
}
