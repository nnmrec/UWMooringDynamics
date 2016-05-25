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
    String path = "../outputs/probes-velocity.csv";
    ///////////////////////////////////////////////////////////////////////////////

    public void execute() {

        Simulation simulation_0 = getActiveSimulation();

        MonitorPlot monitorPlot_0 = 
          ((MonitorPlot) simulation_0.getPlotManager().getPlot("probes-velocity"));

        monitorPlot_0.export(resolvePath(path), ",");
  } 
}
