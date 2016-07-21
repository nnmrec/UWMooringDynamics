// STAR-CCM+ macro: AMR_PartsThresholds.java
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
import star.vis.*;

public class AMR_PartsThresholds extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    NullFieldFunction nullFieldFunction_0 = 
      ((NullFieldFunction) simulation_0.getFieldFunctionManager().getFunction("NullFieldFunction"));



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




    ThresholdPart thresholdPart_0 = 
      simulation_0.getPartManager().createThresholdPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 1.0}), units_0, nullFieldFunction_0, 0);

    thresholdPart_0.setPresentationName("AMR_marked_cells_level_0");

    // thresholdPart_0.getInputParts().setQuery(null);

    thresholdPart_0.getInputParts().setObjects(region_0, boundary_0, boundary_1, boundary_2, boundary_3, boundary_4, boundary_5);

    UserFieldFunction userFieldFunction_3 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("AMR_marked_cells_level_0"));

    thresholdPart_0.setFieldFunction(userFieldFunction_3);

    thresholdPart_0.setMode(1);

    thresholdPart_0.getRangeQuantities().setArray(new DoubleVector(new double[] {0.0, 0.0}));





    ThresholdPart thresholdPart_1 = 
      simulation_0.getPartManager().createThresholdPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 1.0}), units_0, nullFieldFunction_0, 0);

    thresholdPart_1.setPresentationName("Copy of AMR_marked_cells_level_0");

    thresholdPart_1.copyProperties(thresholdPart_0);

    thresholdPart_1.setPresentationName("AMR_marked_cells_level_1");

    UserFieldFunction userFieldFunction_4 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("AMR_marked_cells_level_1"));

    thresholdPart_1.setFieldFunction(userFieldFunction_4);





    ThresholdPart thresholdPart_2 = 
      simulation_0.getPartManager().createThresholdPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 1.0}), units_0, nullFieldFunction_0, 0);

    thresholdPart_2.setPresentationName("Copy of AMR_marked_cells_level_0");

    thresholdPart_2.copyProperties(thresholdPart_0);

    thresholdPart_2.setPresentationName("AMR_marked_cells_level_2");

    UserFieldFunction userFieldFunction_5 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("AMR_marked_cells_level_2"));

    thresholdPart_2.setFieldFunction(userFieldFunction_5);




    // simulation_0.getPartManager().getGroupsManager().createGroup("New Group");

    // ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("New Group")).getGroupsManager().groupObjects("New Group", new NeoObjectVector(new Object[] {thresholdPart_0, thresholdPart_1, thresholdPart_2}), true);

    // ((ClientServerObjectGroup) simulation_0.getPartManager().getGroupsManager().getObject("New Group")).setPresentationName("AMR_marked_cells");
  }
}
