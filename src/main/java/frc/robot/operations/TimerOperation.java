// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.operations;

import edu.wpi.first.wpilibj.Timer;
import frc.operation.Context;
import frc.operation.OpState;
import frc.operation.Operation;
import frc.operation.ReportType;


public class TimerOperation extends Operation {
    Timer myTimer = new Timer();
    int messageNum = 0;

    public TimerOperation(){
        this.opPriority = 1;
        this.opDaemon = false;
    }

    @Override
    protected OpState invoke(Context context){
        myTimer.reset();
        myTimer.start();
        this.report(ReportType.MESSAGE, "Oeration has started!");
        return OpState.RUNNING;
    }

    @Override
    protected OpState execute(Context context) {
        if (messageNum > 5) {
            this.report(ReportType.ERROR, "Operation ending!");
            return OpState.FINISHED;
        }
        if (myTimer.get() < 3) {
            return OpState.RUNNING;
        } else{
            myTimer.reset();
            myTimer.start();
            this.report(ReportType.WARNING, String.format("3 Seconds has past! Current: %2f", context.getTimeTotal()));
            messageNum += 1;
            return OpState.RUNNING;
        }
    }

    @Override
    protected void onInterrupt(Context context) {
        this.report(ReportType.WARNING, "I am interrupted!");
    }

    
}
