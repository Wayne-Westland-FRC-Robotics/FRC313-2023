// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class BendArmSubsystem extends SubsystemBase {
  private final CANSparkMax bendArmMotor = new CANSparkMax(ArmConstants.BEND_ARM_MOTOR_ID, MotorType.kBrushless);

  private final RelativeEncoder encoder = bendArmMotor.getEncoder(); 

  public RelativeEncoder getEncoder() { return encoder; }

  public BendArmSubsystem() {
    encoder.setPositionConversionFactor(ArmConstants.ARM_BEND_RADIUS_ENCODER);
    bendArmMotor.setIdleMode(IdleMode.kBrake);
  }

  public void bend(Double speed) {
    // Limit arm rotation :
    if (
      (encoder.getPosition() < ArmConstants.ARM_BEND_LOWER_LIMIT && speed<0) ||
      (encoder.getPosition() > ArmConstants.ARM_BEND_UPPER_LIMIT && speed>=0)
    ) {
      SmartDashboard.putString("CanBend", "Arm has hit rotation limit!");
      bendArmMotor.set(0);
    } else {
      SmartDashboard.putString("CanBend", "Arm has NOT hit rotation limit.");
      bendArmMotor.set(speed);
    }
  }

  public void startBrake() {
    bendArmMotor.setIdleMode(IdleMode.kBrake);
  }
  public void stopBrake() {
    bendArmMotor.setIdleMode(IdleMode.kCoast);
  }
}