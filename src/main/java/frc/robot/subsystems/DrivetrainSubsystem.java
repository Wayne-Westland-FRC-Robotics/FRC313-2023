// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class DrivetrainSubsystem extends SubsystemBase {
  private final CANSparkMax leftMotor1 = new CANSparkMax(DrivetrainConstants.LEFT_MOTOR_ID_1, MotorType.kBrushless);
  private final CANSparkMax leftMotor2 = new CANSparkMax(DrivetrainConstants.LEFT_MOTOR_ID_2, MotorType.kBrushless);
  private final CANSparkMax rightMotor1 = new CANSparkMax(DrivetrainConstants.RIGHT_MOTOR_ID_1, MotorType.kBrushless);
  private final CANSparkMax rightMotor2 = new CANSparkMax(DrivetrainConstants.RIGHT_MOTOR_ID_2, MotorType.kBrushless);

  private final MotorControllerGroup m_leftMotors = new MotorControllerGroup(
    leftMotor1,
    leftMotor2
  );
  private final MotorControllerGroup m_rightMotors = new MotorControllerGroup(
    rightMotor1,
    rightMotor2
  );

  private final RelativeEncoder leftEncoder = leftMotor1.getEncoder();
  private final RelativeEncoder rightEncoder = leftMotor2.getEncoder();

  public DrivetrainSubsystem() {
    startBrake();
  }

  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  public void tankDrive(Double leftSpeed, Double rightSpeed) {
    m_drive.tankDrive(-leftSpeed, -rightSpeed);
  }
  public void tankDrive(Double speed) {
    tankDrive(speed, speed);
  }

  public void arcadeDrive(Double leftSpeed, Double rightSpeed) {
    m_drive.arcadeDrive(leftSpeed, rightSpeed);
  }
  public void arcadeDrive(Double speed) {
    arcadeDrive(speed, speed);
  }

  public void startBrake() {
    leftMotor1.setIdleMode(IdleMode.kBrake);
    leftMotor2.setIdleMode(IdleMode.kBrake);
    rightMotor1.setIdleMode(IdleMode.kBrake);
    rightMotor2.setIdleMode(IdleMode.kBrake);
  }
  public void stopBrake() {
    leftMotor1.setIdleMode(IdleMode.kCoast);
    leftMotor2.setIdleMode(IdleMode.kCoast);
    rightMotor1.setIdleMode(IdleMode.kCoast);
    rightMotor2.setIdleMode(IdleMode.kCoast);
  }

  public Double getEncoderPos() {
    return leftEncoder.getVelocity() + rightEncoder.getVelocity() / 2;
  }
}
