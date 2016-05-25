// STAR-CCM+ macro: refineMesh.java
// Written by STAR-CCM+ 10.04.009
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.trimmer.*;
import star.meshing.*;

public class refineMesh extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();


    XyzInternalTable xyzInternalTable_2 = 
      ((XyzInternalTable) simulation_0.getTableManager().getTable("Turbulence Intensity Ratio Function Table"));

    xyzInternalTable_2.extract();




    AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh"));

    TrimmerAutoMesher trimmerAutoMesher_0 = 
      ((TrimmerAutoMesher) autoMeshOperation_0.getMeshers().getObject("Trimmed Cell Mesher"));

    trimmerAutoMesher_0.setMeshSizeTable(xyzInternalTable_2);


    simulation_0.get(MeshOperationManager.class).executeAll();



    // Solution solution_0 = 
    //   simulation_0.getSolution();

    // solution_0.clearSolution(Solution.Clear.History, Solution.Clear.Fields);

    // simulation_0.getSimulationIterator().run();






    // xyzInternalTable_2.extract();

    // simulation_0.get(MeshOperationManager.class).executeAll();

    // solution_0.clearSolution(Solution.Clear.History, Solution.Clear.Fields);

    // simulation_0.getSimulationIterator().run();
  }
}
