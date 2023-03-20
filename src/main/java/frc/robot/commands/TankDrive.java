// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class TankDrive extends CommandBase {
  private final DoubleSupplier m_leftSpeed;
  private final DoubleSupplier m_rightSpeed;
  private final DrivetrainSubsystem m_drivetrain;
  public TankDrive(DrivetrainSubsystem drivetrain, DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {
    m_leftSpeed = leftSpeed;
    m_rightSpeed = rightSpeed;
    m_drivetrain = drivetrain;
    addRequirements(m_drivetrain);
  }

  @Override
  public void initialize() {
    //m_drivetrain.stopBrake();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.tankDrive(0.75*m_leftSpeed.getAsDouble(), -0.75*m_rightSpeed.getAsDouble());
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.tankDrive(0.0);
    //m_drivetrain.startBrake();
  }
}
