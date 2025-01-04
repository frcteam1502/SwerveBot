package frc.robot.subsystems;

import org.team1502.swerve.SwerveDrive;

import java.util.function.Supplier;

import org.team1502.configuration.factory.RobotConfiguration;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
    final Pigeon2 gyro;
    
    /** generic Angle in degrees */
    final Supplier<Angle> gyroYaw;
    
    /** generic Rotation2d in radians */
    final Supplier<Rotation2d> gyroRotation2d;

    final SwerveDrive swerveDrive;
    

    public DriveSubsystem(RobotConfiguration configuration) {
        // initialize IMU and get Yaw supplier
        gyro = configuration.Pigeon2().buildPigeon2();
        var statusCode  = gyro.setYaw(0); // whichever way we are pointing is 0 (+X direction)
        gyroYaw = gyro.getYaw().asSupplier();
        gyroRotation2d = ()->new Rotation2d(gyroYaw.get());

        // initialize swerve drive
        swerveDrive = configuration.SwerveDrive().buildSwerveDrive(gyroRotation2d);
    }


    @Override
    public void periodic() {
        swerveDrive.periodic(); // e.g., update odometry
    }

    /** Chassis "unit" velocities (-1 .. +1 ) for X (forward), Y (left), and Omega (ccw) */
    public void swerveDrive(double forwardUnitVelocity, double leftUnitVelocity, double ccwUnitVelocity) {
        swerveDrive.swerveDrive(forwardUnitVelocity, leftUnitVelocity, ccwUnitVelocity);
    }

    public void drive(double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond, boolean fieldRelative) {
        ChassisSpeeds speedCommands = fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(vxMetersPerSecond, vyMetersPerSecond, omegaRadiansPerSecond,gyroRotation2d.get())
            : new ChassisSpeeds(vxMetersPerSecond, vyMetersPerSecond, omegaRadiansPerSecond);
        
        driveRobotRelative(speedCommands);
    }

    public void driveRobotRelative(ChassisSpeeds speedCommands)
    {
        this.swerveDrive.driveRobotRelative(speedCommands);
    } 

}
