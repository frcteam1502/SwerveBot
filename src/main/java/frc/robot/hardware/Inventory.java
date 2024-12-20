package frc.robot.hardware;

import org.team1502.configuration.CAN.Manufacturer;
import org.team1502.configuration.builders.motors.Motor;
import org.team1502.configuration.builders.motors.MotorController;
import org.team1502.configuration.builders.motors.SwerveDrive;
import org.team1502.hardware.SwerveModule;
import org.team1502.hardware.SwerveModules;
import org.team1502.configuration.builders.motors.SwerveModuleBuilder;

import org.team1502.configuration.factory.PartFactory;
import org.team1502.configuration.factory.RobotConfiguration;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class Inventory {
        public static void Motors(PartFactory inventory) {inventory
        .Motor(Motor.NEO, m -> m
            .MotorType(MotorType.kBrushless)
            .FreeSpeedRPM(5_820.0) // from MK4i docs, see data sheet for empirical values
        )
        .Motor(Motor.VORTEX, m -> m
            .MotorType(MotorType.kBrushless)
            .FreeSpeedRPM(6_784.0) // from REV
        )
        .Motor(Motor.NEO550, m -> m
            .MotorType(MotorType.kBrushless)
            .FreeSpeedRPM(11_000.0) // from REV
        );
    }

    public static void Mk4iL3(PartFactory inventory) {inventory
        .SwerveModule(sm -> sm
            .CANCoder(cc -> cc)
            .TurningMotor(Manufacturer.REVRobotics, mc -> mc
                .Motor(Motor.NEO550)
                .IdleMode(IdleMode.kCoast)
                .Reversed() // all turn motors are reversed
                .GearBox(g-> g
                    .Gear("Stage1", 14, 50)
                    .Gear("Stage2", 10, 60)
                )
                .AngleController(-180, 180)
                .PIDController(p->p
                    .Gain(3.4, 0.0, 0.0)
                    .EnableContinuousInput(-Math.PI, Math.PI)
                )
            )
            .DrivingMotor(Manufacturer.REVRobotics, mc -> mc
                .Motor(Motor.VORTEX)
                .IdleMode(IdleMode.kBrake)
                .Reversed() // all drive motors are reversed
                .GearBox(g-> g
                    .Gear("Stage1", 14, 50)
                    .Gear("Stage2", 28, 16)
                    .Gear("Stage3", 15, 45)
                    .Wheel(4.0)
                )
                .PID(.0005, 0.0, 0.0, 1.0)
                .ClosedLoopRampRate(.5)
                .SmartCurrentLimit(30)
            )
        );
    }

}
