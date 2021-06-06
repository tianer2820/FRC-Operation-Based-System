// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.operation.OpManager;
import frc.operation.OpMode;
import frc.robot.subsystems.Chassis;


public class Robot extends TimedRobot {
    OpManager manager = new OpManager();

    // Subsystems
    public static Chassis chassis = new Chassis(0, 1);

    // Joysticks
    public static Joystick stick = new Joystick(1);

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
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        manager.setMode(OpMode.TELEOP);
        manager.startOperation(new TankDrive());
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
