// STAR-CCM+ macro: scene_SaveHardcopies.java
// tested on STAR-CCM+ v10 and v11
// 
// by Danny Clay Sale (dsale@uw.edu)
// 
// license: ?
// 



///////////////////////////////////////////////////////////////////////////////
// import all the classes we need
//
package macro;
import java.util.*;
import star.common.*;
import java.util.*;
import star.vis.*;
import java.io.*;


public class scene_SaveHardcopies extends StarMacro {


  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS

  ///////////////////////////////////////////////////////////////////////////////



  public void execute() {
    execute0();
  }

  private void execute0() {   

    Simulation simulation_0 = getActiveSimulation();

    for (Scene scene : simulation_0.getSceneManager().getScenes()) {
        scene.open(true);
        scene.printAndWait(resolvePath("../outputs/scenes/" + scene.getPresentationName() + ".jpg"), 1, 800, 600);
    };



    for (StarPlot plot : simulation_0.getPlotManager().getObjects()) {
         plot.encode(resolvePath("../outputs/scenes/" + plot.getPresentationName() + ".jpg"), "jpg", 800, 600);

    };

    // now use this bash script to archive the images, and give numbering to filenames
    String cmd = "./utilities/archive_images.sh";
    try {
        Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});
    } catch (IOException ex) {
        simulation_0.println("Error: failed to execute batch command.");
    }
    


  } // end execute0()
} // end public class
