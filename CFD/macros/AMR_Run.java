// STAR-CCM+ macro: AMR_Run.java
// tested on STAR-CCM+ v10 and v11
// 
// by Danny Clay Sale (dsale@uw.edu)
// 
// license: ?
// 

package macro;
import java.util.*;
import star.common.*;
import star.base.neo.*;
// import star.trimmer.*;
import star.dualmesher.*;
import star.meshing.*;
import star.base.report.*;


public class AMR_Run extends StarMacro {




  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS
  // the length of these arrays determines how many times to refine the mesh
  // double[] thresholds = {2.2, 1.5, 2.0};
  // double[] tholds = {1.5, 2.0, 3.0};
  double[] tholds = {1.1, 1.3, 1.5};

  // METHOD 1: define cell sizes explicitly
  double[] cellsizes  = {6.0, 4.0, 2.0};

  // METHOD 2: define factor that cell sizes shrink by, and then a minimum cell size
  double factorVolume = 0.5;
  double minCellSize = 1.0;

  ///////////////////////////////////////////////////////////////////////////////

  // how many times to refine the mesh?
  int nRefineLevels =  tholds.length;

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    Units units_1 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 2, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));


    // (${Turbulence_Intensity_Ratio}<1.5) ? 0 : (${Turbulence_Intensity_Ratio}>1.5 && ${Turbulence_Intensity_Ratio}<2.0) ? max(factorVolume*pow(${Volume},1/3),minCellSize) : (${Turbulence_Intensity_Ratio}>2.0 && ${Turbulence_Intensity_Ratio}<3.0) ? max(factorVolume*pow(${Volume},1/3),minCellSize) : max(factorVolume*pow(${Volume},1/3),minCellSize)

    // simulation_0.println("DEBUG: field function definition:" + "(${Turbulence_Intensity_Ratio}<" + tholds[0] + ") ? 0 : (${Turbulence_Intensity_Ratio}>" + tholds[0] + " && ${Turbulence_Intensity_Ratio}<" + tholds[1] + ") ? max(" + factorVolume + "*pow(${Volume},1/3)," + minCellSize + ") : (${Turbulence_Intensity_Ratio}>" + tholds[1] + " && ${Turbulence_Intensity_Ratio}<" + tholds[2] + ") ? max(" + factorVolume + "*pow(${Volume},1/3)," + minCellSize + ") : max(" + factorVolume + "*pow(${Volume},1/3)," + minCellSize + ")");
    // this prints:
    // (${Turbulence_Intensity_Ratio}<1.5) ? 0 : (${Turbulence_Intensity_Ratio}>1.5 && ${Turbulence_Intensity_Ratio}<2.0) ? max(0.5*pow(${Volume},1/3),1.0) : (${Turbulence_Intensity_Ratio}>2.0 && ${Turbulence_Intensity_Ratio}<3.0) ? max(0.5*pow(${Volume},1/3),1.0) : max(0.5*pow(${Volume},1/3),1.0)




    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Block");

    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Inlet");

    Boundary boundary_1 = 
      region_0.getBoundaryManager().getBoundary("Left Bank");

    Boundary boundary_2 = 
      region_0.getBoundaryManager().getBoundary("Outlet");

    Boundary boundary_3 = 
      region_0.getBoundaryManager().getBoundary("Right Bank");

    Boundary boundary_4 = 
      region_0.getBoundaryManager().getBoundary("Sea Surface");

    Boundary boundary_5 = 
      region_0.getBoundaryManager().getBoundary("Seabed");



      AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh"));

      DualAutoMesher dualAutoMesher_0 = 
      ((DualAutoMesher) autoMeshOperation_0.getMeshers().getObject("Polyhedral Mesher"));

    

    // TrimmerAutoMesher trimmerAutoMesher_0 = 
    //   ((TrimmerAutoMesher) autoMeshOperation_0.getMeshers().getObject("Trimmed Cell Mesher"));

    // XyzInternalTable xyzInternalTable_0 = 
    //   ((XyzInternalTable) simulation_0.getTableManager().getTable("refine Turbulence Intensity Ratio Function TableAMR level 0"));

    // trimmerAutoMesher_0.setMeshSizeTable(xyzInternalTable_0);




    UserFieldFunction userFieldFunction_99 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_99.getTypeOption().setSelected(FieldFunctionTypeOption.SCALAR);

    // userFieldFunction_99.setPresentationName("cell size for refinement (TI ratio)");

    // userFieldFunction_99.setFunctionName("cell size for refinement (TI ratio)");

    userFieldFunction_99.setIgnoreBoundaryValues(true);





    // start from larger to smaller cell sizes, refining the highest ratios last
    // userFieldFunction_99.setDefinition("(${Turbulence Intensity Ratio}>1.2)?6:0");
    // userFieldFunction_99.setDefinition("(${Turbulence Intensity Ratio}>1.5)?4:0");
    // userFieldFunction_99.setDefinition("(${Turbulence Intensity Ratio}>2.0)?2:0");


    simulation_0.println("DEBUG: nRefineLevels = " + nRefineLevels);

    // int n = 0;
    // int n = 1;
    // int n = 2;
    for (int n = 0; n < nRefineLevels; n++) {
      
      // update definition of refinment table
      userFieldFunction_99.setPresentationName("cell_size_for_refinement_(TI_ratio)_level_" + n);

      userFieldFunction_99.setFunctionName("cell_size_for_refinement_(TI_ratio)_level_" + n);

      // userFieldFunction_99.setIgnoreBoundaryValues(true);


      // NOTE: I think this needs to be more carefully defined, for example
      
      // shrink the volume of cells by some factor
      // userFieldFunction_99.setDefinition("(${Turbulence_Intensity_Ratio}<" + tholds[0] + ") ? 0 : (${Turbulence_Intensity_Ratio}>" + tholds[0] + " && ${Turbulence_Intensity_Ratio}<" + tholds[1] + ") ? max(" + factorVolume + "*pow(${Volume},1/3)," + minCellSize + ") : (${Turbulence_Intensity_Ratio}>" + tholds[1] + " && ${Turbulence_Intensity_Ratio}<" + tholds[2] + ") ? max(" + factorVolume + "*pow(${Volume},1/3)," + minCellSize + ") : max(" + factorVolume + "*pow(${Volume},1/3)," + minCellSize + ")");

      // user defined cell sizes for each level
      userFieldFunction_99.setDefinition("(${Turbulence_Intensity_Ratio}<" + tholds[0] + ") ? 0 : (${Turbulence_Intensity_Ratio}>" + tholds[0] + " && ${Turbulence_Intensity_Ratio}<" + tholds[1] + ") ? " + cellsizes[0] + " : (${Turbulence_Intensity_Ratio}>" + tholds[1] + " && ${Turbulence_Intensity_Ratio}<" + tholds[2] + ") ? " + cellsizes[1] + " : " + cellsizes[2]);




      // update mesh table
      XyzInternalTable xyzInternalTable_99 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

      xyzInternalTable_99.setPresentationName("refine_Turbulence_Intensity_Ratio_Function_Table_AMR_level_" + n);

      xyzInternalTable_99.setFieldFunctions(new NeoObjectVector(new Object[] {userFieldFunction_99}));
    
      xyzInternalTable_99.getParts().setObjects(region_0, boundary_0, boundary_1, boundary_2, boundary_3, boundary_4, boundary_5);

      xyzInternalTable_99.extract();

      // trimmerAutoMesher_0.setMeshSizeTable(xyzInternalTable_99);

      // FileTable fileTable_0 = 
      // ((FileTable) simulation_0.getTableManager().getTable("Mezzanine"));

      dualAutoMesher_0.setMeshSizeTable(xyzInternalTable_99);


      // DEBUG
      // simulation_0.println("DEBUG: AMR threshold = " + tholds[n]);
      // simulation_0.println("DEBUG: AMR cellsize = " + cellsizes[n]);
      // simulation_0.println("DEBUG: field function definition = " + "(${Turbulence_Intensity_Ratio}>" + tholds[n] + ")?" + cellsizes[n] + ":0");
      // simulation_0.println("DEBUG: field function definition:" + "(${Turbulence_Intensity_Ratio}<" + thold[0]) + " ? 0 : (${Turbulence_Intensity_Ratio}>" + thold[0] + " && ${Turbulence_Intensity_Ratio}<" + thold[1] + ") ? max(factorVolume*pow(${Volume},1/3),1.0) : (${Turbulence_Intensity_Ratio}>" + thold[1] + " && ${Turbulence_Intensity_Ratio}<" + thold[2]) + " ? max(factorVolume*pow(${Volume},1/3),1.0) : max(factorVolume*pow(${Volume},1/3),minCellSize)");



      // update the convergence criteria for the next round of mesh/solver refinement
      // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("updateConvergenceCriteria.java"))).play();
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("AMR_UpdateConvergenceCriteria.java"))).play();




      // update the scene showing which mesh cells will be refined
      UserFieldFunction userFieldFunction_0 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("cell_size_for_refinement_(TI_ratio)"));

      // userFieldFunction_0.setDefinition("${cell size for refinement (TI ratio) level 0}");
        userFieldFunction_0.setDefinition("${cell_size_for_refinement_(TI_ratio)_level_" + n + "}");








      // now re-run the mesher with the updated mesh table
      // simulation_0.println("DEBUG: re-meshing for AMR");
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("mesh_MeshAll.java"))).play();

      // // now re-run the solver with the new mesh size
      // simulation_0.println("DEBUG: re-solving for AMR");
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("solver_Run.java"))).play();







      // Runtime POST_PROCESSING
      // ======================================================================

      // extract tables to CSV file
      XyzInternalTable xyzInternalTable_1 = 
        ((XyzInternalTable) simulation_0.getTableManager().getTable("line_probe_TI_local"));
      xyzInternalTable_1.extract(); 
      xyzInternalTable_1.export("outputs/line_probe_TI_local_level_" + n + ".csv", ",");

      XyzInternalTable xyzInternalTable_2 = 
        ((XyzInternalTable) simulation_0.getTableManager().getTable("line_probe_TI_local"));
      xyzInternalTable_2.extract(); 
      xyzInternalTable_2.export("outputs/line_probe_Ux_level_" + n + ".csv", ",");

      // save hardcopies after each mesh refinement
      new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_SaveHardcopies.java"))).play();
      

    } // end for()


    // create all the scenes for the AMR (note this is hardcoded to 3 refinements ... need to refactor as a loop)  
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("AMR_PartsThresholds.java"))).play();
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_AMRcompareLevels.java"))).play();   


  }
}
