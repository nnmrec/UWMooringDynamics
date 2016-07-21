// STAR-CCM+ macro: AMR_Main.java
// tested on STAR-CCM+ v10 and v11
// 
// by Danny Clay Sale (dsale@uw.edu)
// 
// license: ?
// 


// import all the classes we need
package macro;
import java.util.*;
import star.common.*;

///////////////////////////////////////////////////////////////////////////////
// This is the MAIN driver, which calls all the other macros
///////////////////////////////////////////////////////////////////////////////
public class AMR_Main extends StarMacro {


  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS

  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {


    ///////////////////////////////////////////////////////////////////////////////
    // Adaptive Mesh Refinement
    ///////////////////////////////////////////////////////////////////////////////
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("AMR_Initialize.java"))).play(); 

    // now run the AMR, loop through all the refinement levels (this loops meshing and solver)
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("AMR_Run.java"))).play();



    ///////////////////////////////////////////////////////////////////////////////
    // POST-PROCESSING
    ///////////////////////////////////////////////////////////////////////////////
    // export important data here    
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("export_PointProbes.java"))).play();
   
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("export_VirtualDisks.java"))).play();



    ///////////////////////////////////////////////////////////////////////////////
    // POST-POST-PROCESSING
    ///////////////////////////////////////////////////////////////////////////////
    // after the final run, all scenes should be finally updated, and hardcopy saves
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_SaveHardcopies.java"))).play();
     
    

  } // end execute0()
} // end public class
