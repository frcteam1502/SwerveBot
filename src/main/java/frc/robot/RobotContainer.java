// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.team1502.configuration.factory.RobotConfiguration;
import org.team1502.injection.RobotFactory;

import edu.wpi.first.wpilibj.shuffleboard.SendableCameraWrapper;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.DriveCommands;
import frc.robot.hardware.SwerveBot;
import frc.robot.subsystems.DriveSubsystem;
import frc.testmode.swerve.AbsoluteEncoderAlignment;

public class RobotContainer {
    public static RobotFactory robotFactory;
    public static RobotConfiguration robotConfiguration;

    public RobotContainer() {
        robotConfiguration = SwerveBot.buildRobot();
        robotFactory = RobotFactory.Create(Robot.class, robotConfiguration);

        configureBindings();
    }

    private void configureBindings() {}

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
    
    public Command getTeleopCommand() {
        DriveSubsystem driveSubsystem = robotFactory.getInstance(DriveSubsystem.class);
        return new DriveCommands(driveSubsystem);
    }

    private SendableChooser<String> testChooser;
    public Command getTestCommand() {
        if (testChooser == null) {
            // if (Robot.isSimulation()) {
            //     testChooser = new SendableChooserSim()
            // }
            testChooser = new SendableChooser<>();
            testChooser.setDefaultOption("Default Test", "AbsoluteEncoderAlignment");
            testChooser.addOption("Magnetic Offsets", "AbsoluteEncoderAlignment");
            SmartDashboard.putData("Test Names", testChooser);
        }
        String testName = testChooser.getSelected();
        //if (testName == null) {testName = testChooser}
        if (testName == "AbsoluteEncoderAlignment") return AbsoluteEncoderAlignment.StartAlignmentCommand(robotConfiguration, robotFactory);
        return Commands.print("No autonomous command configured");
    }
}
