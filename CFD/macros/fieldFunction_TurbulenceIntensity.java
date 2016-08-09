// STAR-CCM+ macro: fieldFunction_TurbulenceIntensity.java
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
import star.base.report.*;

// import star.common.*;
// import star.base.neo.*;
// import star.trimmer.*;
import star.meshing.*;
// import star.base.report.*;

public class fieldFunction_TurbulenceIntensity extends StarMacro {

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
      simulation_0.getRegionManager().getRegion("Region");

    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Block.Inlet");



      


	UserFieldFunction userFieldFunction_10 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_10.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_10.setPresentationName("Turbulence_Intensity_(local)");

    userFieldFunction_10.setDefinition("sqrt(2*$TurbulentKineticEnergy/3)/mag($$Velocity)");

    userFieldFunction_10.setIgnoreBoundaryValues(true);

    userFieldFunction_10.setFunctionName("local_TI");


      
    AreaAverageReport areaAverageReport_0 = 
      simulation_0.getReportManager().createReport(AreaAverageReport.class);

    areaAverageReport_0.setPresentationName("inlet_surface_avg_TI");

    areaAverageReport_0.setScalar(userFieldFunction_10);

    areaAverageReport_0.getParts().setObjects(boundary_0);

    areaAverageReport_0.printReport();




    UserFieldFunction userFieldFunction_99 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_99.getTypeOption().setSelected(FieldFunctionTypeOption.SCALAR);

    userFieldFunction_99.setPresentationName("Turbulence_Intensity_Ratio");

    userFieldFunction_99.setFunctionName("Turbulence_Intensity_Ratio");

    userFieldFunction_99.setDefinition("${local_TI}/${inlet_surface_avg_TIReport}");

    userFieldFunction_99.setIgnoreBoundaryValues(true);



  }
}




