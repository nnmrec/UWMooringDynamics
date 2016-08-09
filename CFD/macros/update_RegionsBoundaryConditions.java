// STAR-CCM+ macro: update_RegionsBoundaryConditions.java
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
import star.flow.*;

public class update_RegionsBoundaryConditions extends StarMacro {

    ///////////////////////////////////////////////////////////////////////////////
    // USER INPUTS
    //
    String path1     = "outputs/environment.csv";

    ///////////////////////////////////////////////////////////////////////////////

    public void execute() {

        Simulation simulation_0 = 
      getActiveSimulation();

    PhysicsContinuum physicsContinuum_0 = 
      ((PhysicsContinuum) simulation_0.getContinuumManager().getContinuum("Physics 1"));

    VelocityProfile velocityProfile_0 = 
      physicsContinuum_0.getInitialConditions().get(VelocityProfile.class);

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Region");

    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Block.Inlet");

    VelocityProfile velocityProfile_1 = 
      boundary_0.getValues().get(VelocityProfile.class);



        // now read the updated coordinate file (written by "mooring model" code) and update probe coordinates
        File f = new File(path1);
        try {

            FileReader  fr      = new FileReader(f);
            Scanner     sc      = new Scanner(fr);
            String      line    = "";


            Integer nLines = new Integer(0);
            while (sc.hasNextLine()) {

                line        = sc.nextLine();

                String name = line.split(",")[0];
                double value    = Double.parseDouble(line.split(",")[1]);

                simulation_0.println("Variable Name: " + name);
                simulation_0.println("Variable Value: " + value);


                velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(value, 0.0, 0.0);
                velocityProfile_1.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(value, 0.0, 0.0);

            }


        // save it
        simulation_0.saveState(getSimulation().getPresentationName()+".sim");


        } catch (FileNotFoundException ex) {
            Logger.getLogger(update_RegionsBoundaryConditions.class.getName()).log(Level.SEVERE, null, ex);
        }



  }
}

