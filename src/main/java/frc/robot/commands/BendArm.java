// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BendArmSubsystem;

public class BendArm extends CommandBase {
  private final Double m_speed;
  private final BendArmSubsystem m_armSubsystem;

  public BendArm(Double speed, BendArmSubsystem armSubsystem) {
    m_speed = speed;
    m_armSubsystem = armSubsystem;
    addRequirements(m_armSubsystem);
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_armSubsystem.bend(m_speed);
  }

  @Override
  public void initialize() {
    //m_armSubsystem.startBrake();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_armSubsystem.bend(0.0);
    //m_armSubsystem.stopBrake();
  }
}
