// STAR-CCM+ macro: update_VirtualDisks.java
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
import star.vdm.*;
import star.base.report.*;
import star.vis.*;

public class update_VirtualDisks extends StarMacro {


    ///////////////////////////////////////////////////////////////////////////////
    // USER INPUTS
    //
    // path to CSV file with names and coordinates of point probes (this gets updated from the "mooring model" code) This file should NOT have any empty lines at bottom 
    String path1     = "outputs/rotors.csv";
    ///////////////////////////////////////////////////////////////////////////////

    public void execute() {

        Simulation simulation_0 = getActiveSimulation();

        PhysicsContinuum physicsContinuum_0 = 
          ((PhysicsContinuum) simulation_0.getContinuumManager().getContinuum("Physics 1"));

        VirtualDiskModel virtualDiskModel_0 = 
          physicsContinuum_0.getModelManager().getModel(VirtualDiskModel.class);

        Region region_0 =
                simulation_0.getRegionManager().getRegion("Region");

        Units units_0 = 
          ((Units) simulation_0.getUnitsManager().getObject("m"));

        // now read the updated Virtual Disk coordinate files (written by "mooring model" code)
        // then recompute the tip-speed-ratio and update the rotor speed accordingly
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
            double rpm  = Double.parseDouble(line.split(",")[2]);
            double x    = Double.parseDouble(line.split(",")[3]);
            double y    = Double.parseDouble(line.split(",")[4]);
            double z    = Double.parseDouble(line.split(",")[5]);

            VirtualDisk virtualDisk_0 = 
              ((VirtualDisk) virtualDiskModel_0.getVirtualDiskManager().getObject(name));

            SimpleDiskGeometry simpleDiskGeometry_0 = 
              virtualDisk_0.getComponentsManager().get(SimpleDiskGeometry.class);

            Coordinate coordinate_0 = 
              simpleDiskGeometry_0.getDiskOrigin();

            coordinate_0.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {x, y, z}));

            VdmRotationRateInputValue vdmRotationRateInputValue_0 = 
              virtualDisk_0.getComponentsManager().get(VdmRotationRateInputValue.class);

            vdmRotationRateInputValue_0.getRotationRate().setValue(rpm);

            simulation_0.println("Virtual Disk '" + name + "' updated coordinates (" + x + "," + y + "," + z + ")");

            simulation_0.println("Virtual Disk '" + name + "' updated rotor speed (" + rpm + ")");


// simulation_0.println("\"" + Double.toString(rpm) + "\""); 
            ExpressionReport expressionReport_0 = 
              ((ExpressionReport) simulation_0.getReportManager().getReport("Rotor Speed (" + name + ")"));
            expressionReport_0.setDefinition(Double.toString(rpm));
            // expressionReport_0.setDefinition("\"" + Double.toString(rpm) + "\"");



        } // end while


        } catch (FileNotFoundException ex) {
            Logger.getLogger(update_VirtualDisks.class.getName()).log(Level.SEVERE, null, ex);
        }



  } // end execute0()
} // end public class

