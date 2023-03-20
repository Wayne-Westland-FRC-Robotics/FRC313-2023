// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ControlSystemConstants;
import frc.robot.subsystems.DrivetrainSubsystem;

public class BalanceCommand extends CommandBase {
  private final DrivetrainSubsystem m_drivetrain;
  private final DoubleSupplier m_tilt;
  private final BooleanSupplier m_isOverhanging;
  
  public BalanceCommand(DrivetrainSubsystem drivetrain, DoubleSupplier tilt, BooleanSupplier isOverhanging) {
    m_drivetrain = drivetrain;
    m_tilt = tilt;
    m_isOverhanging = isOverhanging;
    addRequirements(m_drivetrain);
  }

  @Override
  public void initialize() {
    //m_drivetrain.startBrake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_isOverhanging.getAsBoolean()) {
      m_drivetrain.tankDrive(0.0);
      return;
    }

    if (m_tilt.getAsDouble() >= ControlSystemConstants.BALANCE_TILT_LIMIT) {
      m_drivetrain.tankDrive(ControlSystemConstants.BALANCE_SPEED);
    } else if (m_tilt.getAsDouble() <= -ControlSystemConstants.BALANCE_TILT_LIMIT) {
      m_drivetrain.tankDrive(-ControlSystemConstants.BALANCE_SPEED);
    } else {
      m_drivetrain.tankDrive(0.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.tankDrive(0.0);
    //m_drivetrain.stopBrake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
