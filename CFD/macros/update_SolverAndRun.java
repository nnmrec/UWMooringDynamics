// STAR-CCM+ macro: update_SolverAndRun.java
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

///////////////////////////////////////////////////////////////////////////////
// This is the MAIN driver, which calls all the other macros
//
public class update_SolverAndRun extends StarMacro {

  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS

  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {
        
      // read probe data, then update probe coordinates [flag to skip the export step]
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("update_PointProbes.java"))).play();

      // read Virtual Disk data, then update the Virtual Disks [flag to skip the export step]
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("update_VirtualDisks.java"))).play();

      // run the simulation to its stopping criteria (number iterations set inside this macro)
      // this run.java macro fails because it does not conintue running ... need to update the max iterations before running again
      // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("solver_UpdateConvergenceCriteria.java"))).play();
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("solver_Run.java"))).play();

      // extract probe data, then update probe coordinates [flag to skip the update step]
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("export_PointProbes.java"))).play();

      // extract Virtual Disk data, then update the Virtual Disks [flag to skip the update step]
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("export_VirtualDisks.java"))).play();
         
      // update and print Scenes
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_SaveHardcopies.java"))).play();

  } // end execute0()
} // end public class
