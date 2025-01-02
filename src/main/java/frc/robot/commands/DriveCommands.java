package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommands extends Command {
    static class OperatorConstants {
        public static final int kDriverControllerPort = 0;
        public static final int kOperatorControllerPort = 1;
    }
    
    final CommandXboxController driver = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    final SlewRateLimiter jerkLimiter = new SlewRateLimiter(3);
    final SlewRateLimiter turnLimiter = new SlewRateLimiter(5);

    final DriveSubsystem drive;

    public DriveCommands(DriveSubsystem drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        double forwardSpeed = MathUtil.applyDeadband(-driver.getLeftY(), 0.1); // all the way forward Y == -1 (from stick)
        double leftSpeed = MathUtil.applyDeadband(-driver.getLeftX(), 0.1); // chassis left is positive (opposite of stick)
        double ccwSpeed = MathUtil.applyDeadband(-driver.getRightX(), 0.1); // rotation is also opposite

        forwardSpeed = jerkLimiter.calculate(forwardSpeed);
        leftSpeed = jerkLimiter.calculate(leftSpeed);
        ccwSpeed = turnLimiter.calculate(ccwSpeed);

        drive.swerveDrive(forwardSpeed, leftSpeed, ccwSpeed);

    }
}