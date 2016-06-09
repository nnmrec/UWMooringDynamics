// STAR-CCM+ macro: AMR_Initialize.java
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
import star.trimmer.*;
import star.meshing.*;
// import star.base.report.*;

public class AMR_Initialize extends StarMacro {

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




    // here we will make the field functions that can be used for adaptive mesh refinement

    // UserFieldFunction userFieldFunction_10 = 
    //   simulation_0.getFieldFunctionManager().createFieldFunction();

    // userFieldFunction_10.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    // userFieldFunction_10.setPresentationName("Turbulence_Intensity_(local)");

    // userFieldFunction_10.setDefinition("sqrt(2*$TurbulentKineticEnergy/3)/mag($$Velocity)");

    // userFieldFunction_10.setIgnoreBoundaryValues(true);

    // userFieldFunction_10.setFunctionName("local_TI");


      
    // AreaAverageReport areaAverageReport_0 = 
    //   simulation_0.getReportManager().createReport(AreaAverageReport.class);

    // areaAverageReport_0.setPresentationName("inlet_surface_avg_TI");

    // areaAverageReport_0.setScalar(userFieldFunction_10);

    // areaAverageReport_0.getParts().setObjects(boundary_0);

    // areaAverageReport_0.printReport();




    // UserFieldFunction userFieldFunction_99 = 
    //   simulation_0.getFieldFunctionManager().createFieldFunction();

    // userFieldFunction_99.getTypeOption().setSelected(FieldFunctionTypeOption.SCALAR);

    // userFieldFunction_99.setPresentationName("Turbulence_Intensity_Ratio");

    // userFieldFunction_99.setFunctionName("Turbulence_Intensity_Ratio");

    // userFieldFunction_99.setDefinition("${local_TI}/${inlet_surface_avg_TIReport}");

    // userFieldFunction_99.setIgnoreBoundaryValues(true);



    // XyzInternalTable xyzInternalTable_99 = 
    //   simulation_0.getTableManager().createTable(XyzInternalTable.class);

    // xyzInternalTable_99.setPresentationName("Turbulence_Intensity_Ratio_Function_Table");

    // xyzInternalTable_99.setFieldFunctions(new NeoObjectVector(new Object[] {userFieldFunction_99}));
  
    // xyzInternalTable_99.getParts().setObjects(region_0, boundary_0, boundary_1, boundary_2, boundary_3, boundary_4, boundary_5);

    // xyzInternalTable_99.extract();



    // init the scalar field function for new cell sizes
    UserFieldFunction userFieldFunction_100 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_100.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_100.setPresentationName("cell_size_for_refinement_(TI_ratio)");

    userFieldFunction_100.setDefinition("0");

    userFieldFunction_100.setIgnoreBoundaryValues(true);

    userFieldFunction_100.setFunctionName("cell_size_for_refinement_(TI_ratio)");




    // UserFieldFunction userFieldFunction_0 = 
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("cell size for refinement (TI ratio)"));

    // scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

    // // scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {0.2, 2.0}));

    // scalarDisplayer_0.getScalarDisplayQuantity().setClip(0);




    // // setup XYZ internal table for line probes
    // XyzInternalTable xyzInternalTable_1 = 
    //   simulation_0.getTableManager().createTable(XyzInternalTable.class);

    // xyzInternalTable_1.setPresentationName("line_probe_TI_local");

    // UserFieldFunction userFieldFunction_1 = 
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("local_TI"));

    // xyzInternalTable_1.setFieldFunctions(new NeoObjectVector(new Object[] {userFieldFunction_1}));





    


    // setup more useful field functions (should add to the initialize step)
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("fieldFunction_markedCellsAMR.java"))).play(); 


    // now that cells marked for refinement have been identified, make scenes to confirm
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_TurbulenceIntensityLocal.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_TurbulenceIntensityRatio.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_AMR_CellSizes.java"))).play();


    // save it
    simulation_0.saveState(getSimulation().getPresentationName()+".sim");

  }
}
