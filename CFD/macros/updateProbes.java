// STAR-CCM+ macro: updateProbes.java
// Written by STAR-CCM+ 10.06.010
// 
// license: ?
// 
package macro;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import star.common.*;
import star.base.neo.*;

import star.vis.*;

public class updateProbes extends StarMacro {


    ///////////////////////////////////////////////////////////////////////////////
    // USER INPUTS
    //
    // path to CSV file with names and coordinates of point probes (this gets updated from the "mooring model" code) This file should NOT have any empty lines at bottom 
    String path1     = "outputs/probes.csv";
    // String path1     = "inputs/update-probes.csv";
    // String path2     = "../outputs/probes-velocity.csv";

    ///////////////////////////////////////////////////////////////////////////////

    public void execute() {

        Simulation simulation_0 = getActiveSimulation();

        Region region_0 =
                simulation_0.getRegionManager().getRegion("Block");

        Units units_0 = 
          ((Units) simulation_0.getUnitsManager().getObject("m"));


        // extract the plot to file
        // MonitorPlot monitorPlot_0 = 
        //   ((MonitorPlot) simulation_0.getPlotManager().getPlot("probes"));

        // monitorPlot_0.export(resolvePath(path2), ",");


        // // extract the table to file
        // // NOTE: the exported file does not put the names in same order as the input files! How can they be sorted???
        // XyzInternalTable xyzInternalTable_0 = 
        //   ((XyzInternalTable) simulation_0.getTableManager().getTable("extract-Point-Probes"));
        
        // xyzInternalTable_0.extract();

        // xyzInternalTable_0.export(resolvePath(path2), ",");


        // now read the updated coordinate file (written by "mooring model" code) and update probe coordinates
        File f = new File(path1);
        try {

            FileReader  fr      = new FileReader(f);
            Scanner     sc      = new Scanner(fr);
            String      line    = "";

        Integer nLines = new Integer(0);
        while (sc.hasNextLine()) {
            if(nLines == 0) {
               nLines = nLines + 1;
               sc.nextLine();
               continue;
            }
            line        = sc.nextLine();
            String name = line.split(",")[0];
            double x    = Double.parseDouble(line.split(",")[1]);
            double y    = Double.parseDouble(line.split(",")[2]);
            double z    = Double.parseDouble(line.split(",")[3]);

            PointPart pointPart_0 = 
              ((PointPart) simulation_0.getPartManager().getObject(name));

            Coordinate coordinate_0 = 
              pointPart_0.getPointCoordinate();

            coordinate_0.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {x, y, z}));

            simulation_0.println("Probe point '" + name + "' updated coordinates (" + x + "," + y + "," + z + ")");
        } // end while


        } catch (FileNotFoundException ex) {
            Logger.getLogger(updateProbes.class.getName()).log(Level.SEVERE, null, ex);
        }



  } // end execute0()
} // end public class

