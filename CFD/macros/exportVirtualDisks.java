// STAR-CCM+ macro: exportVirtualDisks.java
// Written by STAR-CCM+ 10.06.010
// 
// license: ?
// 
package macro;

import java.util.*;
import star.common.*;
import star.base.neo.*;

public class exportVirtualDisks extends StarMacro {

    ///////////////////////////////////////////////////////////////////////////////
    // USER INPUTS
    //
    String path0 = "../outputs/rotors-velocity.csv";
    String path1 = "../outputs/rotors-thrust.csv";
    String path2 = "../outputs/rotors-torque.csv";
    ///////////////////////////////////////////////////////////////////////////////
    
    public void execute() {

        Simulation simulation_0 = getActiveSimulation();

        MonitorPlot monitorPlot_0 = 
          ((MonitorPlot) simulation_0.getPlotManager().getPlot("rotors-inflow"));
        monitorPlot_0.export(resolvePath(path0), ",");


        MonitorPlot monitorPlot_1 = 
          ((MonitorPlot) simulation_0.getPlotManager().getPlot("rotors-thrust"));
        monitorPlot_1.export(resolvePath(path1), ",");


        MonitorPlot monitorPlot_2 = 
          ((MonitorPlot) simulation_0.getPlotManager().getPlot("rotors-torque"));
        monitorPlot_2.export(resolvePath(path2), ",");


    } 
}
