// STAR-CCM+ macro: export_PointProbes.java
// tested on STAR-CCM+ v10 and v11
// 
// by Danny Clay Sale (dsale@uw.edu)
// 
// license: ?
// 

package macro;
import java.util.*;
import star.common.*;
import star.base.neo.*;

public class export_PointProbes extends StarMacro {

    // gives the 3 velocities at each probe, and wrties into a CSV file

    ///////////////////////////////////////////////////////////////////////////////
    // USER INPUTS
    //
    String pathX = "../outputs/tables/probes-velocity-x.csv";
    String pathY = "../outputs/tables/probes-velocity-y.csv";
    String pathZ = "../outputs/tables/probes-velocity-z.csv";
    ///////////////////////////////////////////////////////////////////////////////

    public void execute() {

        Simulation simulation_0 = getActiveSimulation();

        MonitorPlot monitorPlot_0 = 
          ((MonitorPlot) simulation_0.getPlotManager().getPlot("probes-velocity-x"));
        monitorPlot_0.export(resolvePath(pathX), ",");

        MonitorPlot monitorPlot_1 = 
          ((MonitorPlot) simulation_0.getPlotManager().getPlot("probes-velocity-y"));
        monitorPlot_1.export(resolvePath(pathY), ",");

        MonitorPlot monitorPlot_2 = 
          ((MonitorPlot) simulation_0.getPlotManager().getPlot("probes-velocity-z"));
        monitorPlot_2.export(resolvePath(pathZ), ",");
  }
}
 