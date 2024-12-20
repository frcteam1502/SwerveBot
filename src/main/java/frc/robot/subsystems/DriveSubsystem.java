package frc.robot.subsystems;

import org.team1502.configuration.builders.motors.SwerveDrive;

import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    
    public DriveSubsystem(SwerveDrive swerveDrive) {
        SwerveDriveKinematics kinematics = swerveDrive.getKinematics();
        double maxSpeed = swerveDrive.calculateMaxSpeed();

    }
}
