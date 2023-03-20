// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Constants.ControlSystemConstants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class BalanceNoUltra extends CommandBase {
  private final DrivetrainSubsystem m_drivetrain;
  private final Robot m_robot;
  private double tilt;
  
  public BalanceNoUltra(DrivetrainSubsystem drivetrain, Robot robot) {
    m_drivetrain = drivetrain;
    m_robot = robot;

    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //m_drivetrain.startBrake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    tilt = m_robot.getRobotTilt();
    if (tilt >= ControlSystemConstants.TILT_BOUND) {
      m_drivetrain.tankDrive(-0.25);
    } else if (tilt <= -ControlSystemConstants.TILT_BOUND) {
      m_drivetrain.tankDrive(0.25);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //m_drivetrain.stopBrake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
