// STAR-CCM+ macro: updateAndRun.java
// Written by STAR-CCM+ 10.06.010
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
public class updateAndRun extends StarMacro {

  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS

  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {
        
      // read probe data, then update probe coordinates [flag to skip the export step]
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("updateProbes.java"))).play();

      // read Virtual Disk data, then update the Virtual Disks [flag to skip the export step]
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("updateVirtualDisks.java"))).play();

      // run the simulation to its stopping criteria (number iterations set inside this macro)
      // this run.java macro fails because it does not conintue running ... need to update the max iterations before running again
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("updateConvergenceCriteria.java"))).play();
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("run.java"))).play();

      // extract probe data, then update probe coordinates [flag to skip the update step]
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("exportProbes.java"))).play();

      // extract Virtual Disk data, then update the Virtual Disks [flag to skip the update step]
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("exportVirtualDisks.java"))).play();
         
      // update and print Scenes
      // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("screensUpdateAndPrint.java"))).play();

  } // end execute0()
} // end public class
