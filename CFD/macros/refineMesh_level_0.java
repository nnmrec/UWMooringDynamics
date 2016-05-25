// STAR-CCM+ macro: refineMesh_level0.java
// Written by STAR-CCM+ 10.04.009
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.trimmer.*;
import star.meshing.*;
import star.base.report.*;

public class refineMesh_level_0 extends StarMacro {

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




    UserFieldFunction userFieldFunction_99 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_99.getTypeOption().setSelected(FieldFunctionTypeOption.SCALAR);

    userFieldFunction_99.setPresentationName("cell size for refinement (TI ratio)");

    userFieldFunction_99.setFunctionName("cell size for refinement (TI ratio)");

    userFieldFunction_99.setDefinition("(${Turbulence Intensity Ratio}>1.3)?4:0");

    userFieldFunction_99.setIgnoreBoundaryValues(true);

    XyzInternalTable xyzInternalTable_99 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_99.setPresentationName("refine Turbulence Intensity Ratio Function Table");

    xyzInternalTable_99.setFieldFunctions(new NeoObjectVector(new Object[] {userFieldFunction_99}));
  
    xyzInternalTable_99.getParts().setObjects(region_0, boundary_0, boundary_1, boundary_2, boundary_3, boundary_4, boundary_5);

    xyzInternalTable_99.extract();

  }
}
