// STAR-CCM+ macro: export_VirtualDisks.java
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

public class export_VirtualDisks extends StarMacro {

    // this should give Fx, Fy, Fz and Mx, My, Mz about the body nodes which is 
    // actually offset from the center of mass of bodies; i.e., turbines and buoyancy pods) and writes into a CSV file

    ///////////////////////////////////////////////////////////////////////////////
    // USER INPUTS
    //
    String path0 = "../outputs/tables/rotors-velocity.csv";
    String path1 = "../outputs/tables/rotors-thrust.csv";
    String path2 = "../outputs/tables/rotors-torque.csv";
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
