// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class DefaultDrive extends CommandBase {
  private final DoubleSupplier m_leftSpeed;
  private final DoubleSupplier m_rightSpeed;
  private final DrivetrainSubsystem m_drivetrain;
  private final BooleanSupplier m_locked;
  private final BooleanSupplier m_canLock;
  public DefaultDrive(DrivetrainSubsystem drivetrain, DoubleSupplier leftSpeed, DoubleSupplier rightSpeed, BooleanSupplier locked, BooleanSupplier canLock) {
    m_leftSpeed = leftSpeed;
    m_rightSpeed = rightSpeed;
    m_drivetrain = drivetrain;
    m_locked = locked;
    m_canLock = canLock;
    addRequirements(m_drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_locked.getAsBoolean() || !m_canLock.getAsBoolean()) m_drivetrain.drive(m_leftSpeed.getAsDouble(), m_rightSpeed.getAsDouble());
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.drive(0.0, 0.0);
  }
}
