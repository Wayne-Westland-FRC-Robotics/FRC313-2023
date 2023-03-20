// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ControlSystemConstants;
import frc.robot.subsystems.BendArmSubsystem;

public class ArmNeutralPID extends CommandBase {
  private final BendArmSubsystem m_armSub;
  private RelativeEncoder m_encoder;
  private double m_goalPosition;

  private final PIDController m_pid = new PIDController(
    ControlSystemConstants.kPArm,
    0,
    ControlSystemConstants.kDArm
  );

  public ArmNeutralPID(BendArmSubsystem armSub) {
    m_armSub = armSub;
    addRequirements(m_armSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_encoder = m_armSub.getEncoder();
    m_goalPosition = m_encoder.getPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_armSub.bend(calc(m_encoder));
  }

  private double calc(RelativeEncoder encoder) {
    return m_pid.calculate(encoder.getPosition(), m_goalPosition);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_armSub.bend(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
