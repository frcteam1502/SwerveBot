package frc.robot.hardware;

import org.team1502.configuration.factory.RobotConfiguration;

public class SwerveBot {

    public SwerveBot() {
             var robotConfiguration = RobotConfiguration.Create("swerveTest1", fn -> fn
            .Parts(inventory -> {
                Inventory.Motors(inventory);
                Inventory.Mk4iL3(inventory);
                return inventory;
            }
        ));

        robotConfiguration.Build(builder->builder
        .SwerveDrive(sd->sd
        .Chassis(c -> c
        .Square(19.75)
        .Frame(25.25)
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
        .TopSpeed(4.6)
        )
        );
    }

}
