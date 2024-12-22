package frc.robot.subsystems;

import org.team1502.configuration.builders.motors.SwerveDrive;
import org.team1502.configuration.factory.RobotConfiguration;
import org.team1502.hardware.SwerveModules;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    final Pigeon2 gyro;
    final SwerveDriveKinematics kinematics;
    final double maxFreeSpeed;
    /** adjustable 0 to max achievable speed */
    double maxSpeed;


    public DriveSubsystem(RobotConfiguration configuration) {
        gyro = configuration.Pigeon2().buildPigeon2();
        SwerveDrive swerveDrive = configuration.SwerveDrive();
        SwerveModules swerveModules = new SwerveModules(swerveDrive);
        kinematics = swerveDrive.getKinematics();
        maxFreeSpeed = swerveDrive.calculateMaxSpeed();
        maxSpeed = maxFreeSpeed * 0.6; // TODO: determine empirically
    }


    public void drive(double xSpeed, double ySpeed, double rotationSpeed, boolean fieldRelative) {

        ChassisSpeeds speedCommands = new ChassisSpeeds(xSpeed, ySpeed, rotationSpeed);
        if(fieldRelative){
            speedCommands.toRobotRelativeSpeeds(getGyroRotation2d());
        }
        driveRobotRelative(speedCommands);
    }

    public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds){
        //This method is a consumer of ChassisSpeed and sets the corresponding module states.  This is required for PathPlanner 2024

        //Convert from robot frame of reference (ChassisSpeeds) to swerve module frame of reference (SwerveModuleState)
        var swerveModuleStates = kinematics.toSwerveModuleStates(robotRelativeSpeeds);
        //Normalize wheel speed commands to make sure no speed is greater than the maximum achievable wheel speed.
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, maxSpeed);
        //Set the speed and angle of each module
        setDesiredState(swerveModuleStates);
    }

    public Rotation2d getGyroRotation2d() {
        return new Rotation2d(Units.degreesToRadians(getIMU_Yaw()));
    }

    public Pose2d getPose2d() {
        return odometry.getEstimatedPosition();
    }

    public void resetGyro() {
        gyro.setYaw(0);
    }

    public void reset() {
        resetGyro();
        resetModules();
        resetOdometry(pose);
  }  

}
