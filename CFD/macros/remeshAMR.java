// STAR-CCM+ macro: remeshAMR.java
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
public class remeshAMR extends StarMacro {


  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS

  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {


    
    
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("createScenes_addAMR.java"))).play();  
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("refineMesh_level0.java"))).play();
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("createScenes_addAMR_threshold.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("refineMesh.java"))).play();



    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("run.java"))).play();

    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("exportProbes.java"))).play();
   
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("exportVirtualDisks.java"))).play();

    // new StarScript(getActiveSimulation(),  new java.io.File(resolvePath("exportScenes.java"))).play();

  } // end execute0()
} // end public class



// // STAR-CCM+ macro: refineMesh.java
// // Written by STAR-CCM+ 10.04.009
// package macro;

// import java.util.*;

// import star.common.*;
// import star.base.neo.*;
// import star.trimmer.*;
// import star.meshing.*;

// public class refineMesh extends StarMacro {

//   public void execute() {
//     execute0();
//   }

//   private void execute0() {

//     Simulation simulation_0 = 
//       getActiveSimulation();




//     XyzInternalTable xyzInternalTable_2 = 
//       ((XyzInternalTable) simulation_0.getTableManager().getTable("Turbulence Intensity Ratio Function Table"));

//     xyzInternalTable_2.extract();




//     AutoMeshOperation autoMeshOperation_0 = 
//       ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh"));

//     TrimmerAutoMesher trimmerAutoMesher_0 = 
//       ((TrimmerAutoMesher) autoMeshOperation_0.getMeshers().getObject("Trimmed Cell Mesher"));

//     trimmerAutoMesher_0.setMeshSizeTable(xyzInternalTable_2);

//     simulation_0.get(MeshOperationManager.class).executeAll();

//     simulation_0.getSimulationIterator().run();


//   }
// }
