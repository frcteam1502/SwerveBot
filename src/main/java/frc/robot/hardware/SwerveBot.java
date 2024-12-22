package frc.robot.hardware;

import org.team1502.configuration.factory.RobotConfiguration;

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
        .SwerveDrive(sd->sd
            .Chassis(c -> c
                .Square(19.75) // for wheel distances
                .Frame(25.25) // base frame
                //.Bumpers(3.0) // PathPlanner needs the full frame
                //.WheelDiameter(4.0) included in SwerveModule
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
            .TopSpeed(4.6) // empirical speed of fully loaded / fully charge robot
        ));
    }
}
