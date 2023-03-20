// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ContainerConstants;
import frc.robot.commands.ActuateClaw;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ArmNeutralPID;
import frc.robot.commands.Auto_ChargingDirectly;
import frc.robot.commands.Auto_ExitCommunity_Long;
import frc.robot.commands.Auto_ExitCommunity_Short;
import frc.robot.commands.Automatic_End_Highgoal;
import frc.robot.commands.Automatic_Highgoal;
//import frc.robot.commands.BalanceCommand;
import frc.robot.commands.BalanceNoUltra;
//import frc.robot.commands.BalancePID;
import frc.robot.commands.BendArm;
import frc.robot.commands.BrakeCommand;
//import frc.robot.commands.BendArmPID;
import frc.robot.commands.Auto_Bottom;
import frc.robot.commands.TankDrive;
import frc.robot.commands.SlideArm;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.BendArmSubsystem;
import frc.robot.subsystems.SlideArmSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final Robot m_robot;
  // The robot's subsystems and commands are defined here...
  private final DrivetrainSubsystem m_drivetrain = new DrivetrainSubsystem();
  private final BendArmSubsystem m_armBendSubsystem = new BendArmSubsystem();
  private final SlideArmSubsystem m_armSlideSubystem = new SlideArmSubsystem();
  private final ClawSubsystem m_clawSubsystem = new ClawSubsystem();

  private final CommandXboxController m_drController = new CommandXboxController(ContainerConstants.DRIVER_CONTROLLER_PORT);
  private final CommandXboxController m_opController = new CommandXboxController(ContainerConstants.OPERATOR_CONTROLLER_PORT);

  SendableChooser<Command> m_controllerChooser = new SendableChooser<>();
  SendableChooser<Command> m_autonomousChooser = new SendableChooser<>();

  SendableChooser<Boolean> m_controllerCount = new SendableChooser<>();
  SendableChooser<Command> m_autoType = new SendableChooser<>();

  private final Command m_tankDriveCon = new TankDrive(m_drivetrain, m_drController::getLeftY, m_drController::getRightY);
  private final Command m_arcadeDriveCon = new ArcadeDrive(m_drivetrain, m_drController::getLeftY, m_drController::getRightY);
  
  private final Command m_chargeDirectlyCommand;
  private final Command m_bottomAuto = new Auto_Bottom(m_drivetrain);
  private final Command m_autoExitShort = new Auto_ExitCommunity_Long(m_drivetrain);
  private final Command m_autoExitLong = new Auto_ExitCommunity_Short(m_drivetrain);
  public RobotContainer(Robot robot) {
    m_robot = robot;
    m_chargeDirectlyCommand = new Auto_ChargingDirectly(m_drivetrain, m_robot);

    m_armBendSubsystem.setDefaultCommand(new ArmNeutralPID(m_armBendSubsystem));

    m_controllerChooser.setDefaultOption("XboxTank", m_tankDriveCon);
    m_controllerChooser.addOption("XboxArcade", m_arcadeDriveCon);
    Shuffleboard.getTab("Select Controller").add(m_controllerChooser);

    m_autonomousChooser.setDefaultOption("Direct Charge", m_chargeDirectlyCommand);
    m_autonomousChooser.addOption("Bottom Auto", m_bottomAuto);

    m_autoType.setDefaultOption("Exit Long Side Auto", m_autoExitShort);
    m_autoType.addOption("Exit Short Side Auto", m_autoExitLong);

    m_controllerCount.setDefaultOption("Double", true);
    m_controllerCount.addOption("Single", false);

    //configureBindings(); [Configuring bindings in "robot.java" for the controller chooser!]
  }

  public void configureBindings(boolean isDouble) {
    //m_drController.povLeft().whileTrue(new BalanceCommand(m_drivetrain, m_robot::getRobotTilt, m_robot::getUltra));
    m_drController.a().whileTrue(new BrakeCommand(m_drivetrain));
    m_drController.b().whileTrue(new BalanceNoUltra(m_drivetrain, m_robot));
    m_drController.x().toggleOnTrue(new Automatic_Highgoal(m_armBendSubsystem, m_armSlideSubystem, m_clawSubsystem));
    m_drController.x().toggleOnFalse(new Automatic_End_Highgoal(m_armBendSubsystem, m_armSlideSubystem));
    m_drController.povUp().whileTrue(new BendArm(ContainerConstants.ARM_BEND_SPEED, m_armBendSubsystem));
    m_drController.povDown().whileTrue(new BendArm(-ContainerConstants.ARM_BEND_SPEED, m_armBendSubsystem));

    m_drController.leftBumper().whileTrue(new ActuateClaw(0, m_clawSubsystem));
    m_drController.rightBumper().whileTrue(new ActuateClaw(1, m_clawSubsystem));

    m_drController.povLeft().whileTrue(new SlideArm(ContainerConstants.ARM_SLIDER_SPEED, m_armSlideSubystem));
    m_drController.povRight().whileTrue(new SlideArm(-ContainerConstants.ARM_SLIDER_SPEED, m_armSlideSubystem));
    if (isDouble) {
      operatorSetup(m_opController);
    } else {
      operatorSetup(m_drController);
    }
  }

  public void operatorSetup(CommandXboxController controller) {
    controller.povUp().whileTrue(new BendArm(ContainerConstants.ARM_BEND_SPEED, m_armBendSubsystem));
    controller.povDown().whileTrue(new BendArm(-ContainerConstants.ARM_BEND_SPEED, m_armBendSubsystem));

    controller.leftBumper().whileTrue(new ActuateClaw(0, m_clawSubsystem));
    controller.rightBumper().whileTrue(new ActuateClaw(1, m_clawSubsystem));

    controller.povLeft().whileTrue(new SlideArm(ContainerConstants.ARM_SLIDER_SPEED, m_armSlideSubystem));
    controller.povRight().whileTrue(new SlideArm(-ContainerConstants.ARM_SLIDER_SPEED, m_armSlideSubystem));
    /* OLD CONTROLS :
    controller.a().whileTrue(new BendArm(ContainerConstants.ARM_BEND_SPEED, m_armBendSubsystem));
    controller.b().whileTrue(new BendArm(-ContainerConstants.ARM_BEND_SPEED, m_armBendSubsystem));

    controller.x().whileTrue(new ActuateClaw(0, m_clawSubsystem));
    controller.y().whileTrue(new ActuateClaw(1, m_clawSubsystem));

    controller.leftBumper().whileTrue(new SlideArm(ContainerConstants.ARM_SLIDER_SPEED, m_armSlideSubystem));
    controller.rightBumper().whileTrue(new SlideArm(-ContainerConstants.ARM_SLIDER_SPEED, m_armSlideSubystem));
    */
  }

  public Command getAutonomousCommand() {
    return m_autoType.getSelected();
    //return new Auto_ExitCommunity_Long(m_drivetrain);
  }

  public Command getControllerChooser() {
    return m_controllerChooser.getSelected();
  }
  
  public Boolean getControllerCount() {
    return m_controllerCount.getSelected();
  }

  public DrivetrainSubsystem getDrivetrain() {
    return m_drivetrain;
  }
}
