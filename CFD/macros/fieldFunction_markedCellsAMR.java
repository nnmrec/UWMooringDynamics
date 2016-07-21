// STAR-CCM+ macro: fieldFunction_markedCellsAMR.java
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

public class fieldFunction_markedCellsAMR extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Block");


    Units units_1 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));


    // these field functions evaluate to true or false, if mesh cell should be refined



    UserFieldFunction userFieldFunction_4 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_4.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_4.setFunctionName("AMR_marked_cells_level_0");

    userFieldFunction_4.setPresentationName("AMR_marked_cells_level_0");

    userFieldFunction_4.setDefinition("(${cell_size_for_refinement_(TI_ratio)_level_0}>0)?1:0");




    UserFieldFunction userFieldFunction_5 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_5.setPresentationName("Copy of AMR_marked_cells_level_0");

    userFieldFunction_5.copyProperties(userFieldFunction_4);

    userFieldFunction_5.setPresentationName("AMR_marked_cells_level_1");

    userFieldFunction_5.setFunctionName("AMR_marked_cells_level_1");

    userFieldFunction_5.setDefinition("(${cell_size_for_refinement_(TI_ratio)_level_1}>0)?1:0");




    UserFieldFunction userFieldFunction_6 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_6.setPresentationName("Copy of AMR_marked_cells_level_0");

    userFieldFunction_6.copyProperties(userFieldFunction_4);

    userFieldFunction_6.setPresentationName("AMR_marked_cells_level_2");

    userFieldFunction_6.setFunctionName("AMR_marked_cells_level_2");

    userFieldFunction_6.setDefinition("(${cell_size_for_refinement_(TI_ratio)_level_2}>0)?1:0");



  }
}

