// STAR-CCM+ macro: update_PointProbes.java
// tested on STAR-CCM+ v10 and v11
// 
// by Danny Clay Sale (dsale@uw.edu)
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

public class update_PointProbes extends StarMacro {


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
                simulation_0.getRegionManager().getRegion("Region");

        Units units_0 = 
          ((Units) simulation_0.getUnitsManager().getObject("m"));


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
            Logger.getLogger(update_PointProbes.class.getName()).log(Level.SEVERE, null, ex);
        }



  } // end execute0()
} // end public class

