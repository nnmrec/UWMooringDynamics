// STAR-CCM+ macro: exportProbes.java
// Written by STAR-CCM+ 10.06.010
// 
// license: ?
// 
package macro;

import java.util.*;
import star.common.*;
import star.base.neo.*;

public class exportProbes extends StarMacro {

    ///////////////////////////////////////////////////////////////////////////////
    // USER INPUTS
    //
    String pathX = "../outputs/probes-velocity-x.csv";
    String pathY = "../outputs/probes-velocity-y.csv";
    String pathZ = "../outputs/probes-velocity-z.csv";
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
 