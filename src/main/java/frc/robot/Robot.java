// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.testmode.diagnostics;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private Command m_teleopCommand;
  private Command m_testCommand;

  private final RobotContainer m_robotContainer;

  public Robot() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    m_teleopCommand = m_robotContainer.getTeleopCommand();
    if (m_teleopCommand != null) {
      m_teleopCommand.schedule();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {
    if (m_teleopCommand != null) {
      m_teleopCommand.cancel();
    }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
    diagnostics.enterTestMode();
    // m_testCommand = m_robotContainer.getTestCommand();
    // if (m_testCommand != null) {
    //   m_testCommand.schedule();
    // }
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {
    if (m_testCommand != null) {
      m_testCommand.cancel();
    }
    diagnostics.exitTestMode();
  }
}
