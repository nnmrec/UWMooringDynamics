// STAR-CCM+ macro: parts_Create_LineProbes.java
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

public class parts_Create_LineProbes extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Block");





    LinePart linePart_0 = 
      simulation_0.getPartManager().createLinePart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), new DoubleVector(new double[] {1.0, 0.0, 0.0}), 20);

    Coordinate coordinate_0 = 
      linePart_0.getPoint1Coordinate();

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    coordinate_0.setCoordinateSystem(labCoordinateSystem_0);

    coordinate_0.setValue(new DoubleVector(new double[] {-100.0, 25.0, 30.0}));

    coordinate_0.setCoordinate(units_2, units_2, units_2, new DoubleVector(new double[] {-100.0, 25.0, 30.0}));


    Coordinate coordinate_1 = 
      linePart_0.getPoint2Coordinate();

    coordinate_1.setCoordinateSystem(labCoordinateSystem_0);

    coordinate_1.setValue(new DoubleVector(new double[] {400.0, 25.0, 30.0}));

    coordinate_1.setCoordinate(units_2, units_2, units_2, new DoubleVector(new double[] {400.0, 25.0, 30.0}));

    linePart_0.setCoordinateSystem(labCoordinateSystem_0);

    linePart_0.getInputParts().setObjects(region_0);

    linePart_0.setResolution(200);

    linePart_0.setPresentationName("line_probe_centerline");







    // setup XYZ internal table for line probes
    XyzInternalTable xyzInternalTable_1 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_1.setPresentationName("line_probe_TI_local");

    UserFieldFunction userFieldFunction_1 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("local_TI"));

    xyzInternalTable_1.setFieldFunctions(new NeoObjectVector(new Object[] {userFieldFunction_1}));

    // LinePart linePart_0 = 
    //   ((LinePart) simulation_0.getPartManager().getObject("line_probe_centerline"));

    xyzInternalTable_1.getParts().setObjects(linePart_0);




    // setup XYZ internal table for line probes
    XyzInternalTable xyzInternalTable_2 = 
      simulation_0.getTableManager().createTable(XyzInternalTable.class);

    xyzInternalTable_2.setPresentationName("line_probe_Ux");

    // UserFieldFunction userFieldFunction_1 = 
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("local_TI"));
    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));
    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(0));

    xyzInternalTable_2.setFieldFunctions(new NeoObjectVector(new Object[] {vectorComponentFieldFunction_0}));

    // LinePart linePart_0 = 
    //   ((LinePart) simulation_0.getPartManager().getObject("line_probe_centerline"));

    xyzInternalTable_2.getParts().setObjects(linePart_0);





  }
}
