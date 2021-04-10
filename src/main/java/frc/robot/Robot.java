// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.operation.OpManager;
import frc.operation.OpMode;
import frc.robot.operations.TimerOperation;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    OpManager manager = new OpManager();

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        manager.init();
    }

    @Override
    public void robotPeriodic() {
        manager.update();
    }

    @Override
    public void autonomousInit() {
        manager.setMode(OpMode.AUTONOMOUS);
        manager.startOperation(new TimerOperation());
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        manager.setMode(OpMode.TELEOP);

    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void disabledInit() {
        manager.setMode(OpMode.DISABLED);

    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
        manager.setMode(OpMode.TEST);

    }

    @Override
    public void testPeriodic() {
    }
}
