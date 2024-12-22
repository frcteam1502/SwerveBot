package frc.robot.hardware;

import org.team1502.configuration.factory.RobotConfiguration;

import frc.robot.subsystems.DriveSubsystem;

public class SwerveBot {

    public static RobotConfiguration buildRobot() {
        RobotConfiguration robotConfiguration = RobotConfiguration.Create("swerveTest1", fn -> fn
            .Parts(inventory -> {
                Inventory.Motors(inventory);
                Inventory.Mk4iL3(inventory);
                return inventory;
            }
        ));

        return robotConfiguration.Build(builder->builder
            .Subsystem(DriveSubsystem.class, sys->sys
                
                .Pigeon2(g->g.CanNumber(14))

                .SwerveDrive(sd->sd
                    .Chassis(c -> c
                        .Square(19.75)
                        .WheelDiameter(4.0)
                    )
                    .SwerveModule("Module#1", sm->sm
                        .CanNumber(4)
                    )
                    .SwerveModule("Module#2", sm->sm
                        .CanNumber(6)
                    )
                    .SwerveModule("Module#3", sm->sm
                        .CanNumber(8)
                    )
                    .SwerveModule("Module#4", sm->sm
                        .CanNumber(10)
                    )
                )
            )
        );
    }
}
