// STAR-CCM+ macro: main.java
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
public class main extends StarMacro {


  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS

  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {

    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("updateConvergenceCriteria.java"))).play();

    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("prepareAMR.java"))).play();

    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("createScenes_addAMR.java"))).play();
      
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("createHistogram.java"))).play();

    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("refineMesh_level_0.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("createScenes_addAMR_threshold.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("refineMesh.java"))).play();

    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("run.java"))).play();

  } // end execute0()
} // end public class
