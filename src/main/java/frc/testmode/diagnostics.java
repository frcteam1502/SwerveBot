package frc.testmode;

import org.team1502.configuration.factory.RobotConfiguration;
import org.team1502.injection.RobotFactory;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;

public class diagnostics implements Subsystem { //, Sendable {
    static diagnostics singleton;

    public static void enterTestMode() {
        if (singleton == null) {
            singleton = new diagnostics(RobotContainer.robotFactory, RobotContainer.robotConfiguration);
        }
        singleton.beginDiagnostics();
    }
    public static void exitTestMode() {
        if (singleton != null) {
            singleton.endDiagnostics();
        }
    }

    RobotFactory robotFactory;
    RobotConfiguration robotConfiguration;
    public diagnostics(RobotFactory factory, RobotConfiguration configuration) {
        this.robotFactory = factory;
        this.robotConfiguration = configuration;
    }

    /*
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType(this.getClass().getSimpleName());

        builder.
 
    }
    */
    
    private SendableChooser<String> diagnosticsChooser;

    void beginDiagnostics() {
        CommandScheduler.getInstance().registerSubsystem(this);
        diagnosticsChooser = new SendableChooser<>();
        diagnosticsChooser.setDefaultOption("Default Test", "AbsoluteEncoderAlignment");
        diagnosticsChooser.addOption("Magnetic Offsets", "AbsoluteEncoderAlignment");
        diagnosticsChooser.addOption("Motors", "Motors");
        diagnosticsChooser.addOption("Swerve", "Swerve");
        diagnosticsChooser.addOption("Modules", "Modules");
        SmartDashboard.putData("Choose Diagnostics", diagnosticsChooser);
    }

    void endDiagnostics() {
        CommandScheduler.getInstance().unregisterSubsystem(this);
    }
}
