// STAR-CCM+ macro: parts_SectionPlanes.java
// Written by STAR-CCM+ 11.02.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class parts_SectionPlanes extends StarMacro {

  // static final double xo          = 0;       // origin x coordinate [m]
  // static final double yo          = 0;       // origin y coordinate [m]
  // static final double zo          = 0;       // origin z coordinate [m]
  // // static final double length            = 12.3;     // length in x-dimention (steamwise) [m]
  // // static final double width           = 1.0;       // length in y-dimention (crossflow) [m]
  // // static final double depth           = 0.8;          // length in z-dimention (vertical) [m]
  // static final double length      = 1000;     // length in x-dimention (steamwise) [m]
  // static final double width       = 400;     // length in y-dimention (crossflow) [m]
  // static final double depth       = 60;      // length in z-dimention (vertical) [m]


  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Region");






      // get the user inputs field functions
      UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__xo"));
      UserFieldFunction userFieldFunction_1 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__yo"));
      UserFieldFunction userFieldFunction_2 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__zo"));
      UserFieldFunction userFieldFunction_3 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__length"));
      UserFieldFunction userFieldFunction_4 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__width"));
      UserFieldFunction userFieldFunction_5 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__depth"));




      double xx   = Double.parseDouble(userFieldFunction_0.getDefinition())+(Double.parseDouble(userFieldFunction_3.getDefinition())-Double.parseDouble(userFieldFunction_0.getDefinition()))/2;
      double yy   = Double.parseDouble(userFieldFunction_1.getDefinition())+(Double.parseDouble(userFieldFunction_4.getDefinition())-Double.parseDouble(userFieldFunction_1.getDefinition()))/2;
      double zz   = Double.parseDouble(userFieldFunction_2.getDefinition())+(Double.parseDouble(userFieldFunction_5.getDefinition())-Double.parseDouble(userFieldFunction_2.getDefinition()))/2;
      simulation_0.println("DEBUG 0: xx = " + xx);
      simulation_0.println("DEBUG 0: yy = " + yy);
      simulation_0.println("DEBUG 0: zz = " + zz);
      // simulation_0.println("DEBUG 0: xo = " + Double.parseDouble(userFieldFunction_0.getDefinition()));
      // Double.parseDouble(userFieldFunction_0.getDefinition())
      // xo+(length-xo)/2
      // yo+(width-yo)/2
      // zo+(depth-zo)/2







    PlaneSection planeSection_3 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    planeSection_3.setCoordinateSystem(labCoordinateSystem_0);

    planeSection_3.getInputParts().setObjects(region_0);

    Coordinate coordinate_6 = 
      planeSection_3.getOriginCoordinate();

    // Double.parseDouble(userFieldFunction_0.getDefinition())
    // coordinate_6.setValue(new DoubleVector(new double[] {xo+(length-xo)/2, yo+(width-yo)/2, zo+(depth-zo)/2}));
    coordinate_6.setValue(new DoubleVector(new double[] {xx,yy,zz}));

    // coordinate_6.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {xo+(length-xo)/2, yo+(width-yo)/2, zo+(depth-zo)/2}));
    coordinate_6.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {xx,yy,zz}));

    coordinate_6.setCoordinateSystem(labCoordinateSystem_0);

    Coordinate coordinate_7 = 
      planeSection_3.getOrientationCoordinate();

    coordinate_7.setValue(new DoubleVector(new double[] {1.0, 0.0, 0.0}));

    coordinate_7.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.0, 0.0}));

    coordinate_7.setCoordinateSystem(labCoordinateSystem_0);

    SingleValue singleValue_3 = 
      planeSection_3.getSingleValue();

    singleValue_3.getValueQuantity().setValue(0.0);

    singleValue_3.getValueQuantity().setUnits(units_0);

    RangeMultiValue rangeMultiValue_3 = 
      planeSection_3.getRangeMultiValue();

    rangeMultiValue_3.setNValues(2);

    rangeMultiValue_3.getStartQuantity().setValue(0.0);

    rangeMultiValue_3.getStartQuantity().setUnits(units_0);

    rangeMultiValue_3.getEndQuantity().setValue(1.0);

    rangeMultiValue_3.getEndQuantity().setUnits(units_0);

    DeltaMultiValue deltaMultiValue_3 = 
      planeSection_3.getDeltaMultiValue();

    deltaMultiValue_3.setNValues(2);

    deltaMultiValue_3.getStartQuantity().setValue(0.0);

    deltaMultiValue_3.getStartQuantity().setUnits(units_0);

    deltaMultiValue_3.getDeltaQuantity().setValue(1.0);

    deltaMultiValue_3.getDeltaQuantity().setUnits(units_0);

    MultiValue multiValue_3 = 
      planeSection_3.getArbitraryMultiValue();

    multiValue_3.getValueQuantities().setUnits(units_0);

    multiValue_3.getValueQuantities().setArray(new DoubleVector(new double[] {0.0}));

    planeSection_3.setValueMode(0);

    planeSection_3.setPresentationName("plane_crossflow");














    PlaneSection planeSection_4 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_4.setCoordinateSystem(labCoordinateSystem_0);

    planeSection_4.getInputParts().setObjects(region_0);

    Coordinate coordinate_8 = 
      planeSection_4.getOriginCoordinate();

    // coordinate_8.setValue(new DoubleVector(new double[] {xo+(length-xo)/2, yo+(width-yo)/2, zo+(depth-zo)/2}));
    coordinate_8.setValue(new DoubleVector(new double[] {xx,yy,zz}));

    // coordinate_8.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {xo+(length-xo)/2, yo+(width-yo)/2, zo+(depth-zo)/2}));
    coordinate_8.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {xx,yy,zz}));

    coordinate_8.setCoordinateSystem(labCoordinateSystem_0);

    Coordinate coordinate_9 = 
      planeSection_4.getOrientationCoordinate();

    coordinate_9.setValue(new DoubleVector(new double[] {0.0, 1.0, 0.0}));

    coordinate_9.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));

    coordinate_9.setCoordinateSystem(labCoordinateSystem_0);

    SingleValue singleValue_4 = 
      planeSection_4.getSingleValue();

    singleValue_4.getValueQuantity().setValue(0.0);

    singleValue_4.getValueQuantity().setUnits(units_0);

    RangeMultiValue rangeMultiValue_4 = 
      planeSection_4.getRangeMultiValue();

    rangeMultiValue_4.setNValues(2);

    rangeMultiValue_4.getStartQuantity().setValue(0.0);

    rangeMultiValue_4.getStartQuantity().setUnits(units_0);

    rangeMultiValue_4.getEndQuantity().setValue(1.0);

    rangeMultiValue_4.getEndQuantity().setUnits(units_0);

    DeltaMultiValue deltaMultiValue_4 = 
      planeSection_4.getDeltaMultiValue();

    deltaMultiValue_4.setNValues(2);

    deltaMultiValue_4.getStartQuantity().setValue(0.0);

    deltaMultiValue_4.getStartQuantity().setUnits(units_0);

    deltaMultiValue_4.getDeltaQuantity().setValue(1.0);

    deltaMultiValue_4.getDeltaQuantity().setUnits(units_0);

    MultiValue multiValue_4 = 
      planeSection_4.getArbitraryMultiValue();

    multiValue_4.getValueQuantities().setUnits(units_0);

    multiValue_4.getValueQuantities().setArray(new DoubleVector(new double[] {0.0}));

    planeSection_4.setValueMode(0);

    planeSection_4.setPresentationName("plane_streamwise");

















    PlaneSection planeSection_5 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_5.setCoordinateSystem(labCoordinateSystem_0);

    planeSection_5.getInputParts().setObjects(region_0);

    Coordinate coordinate_10 = 
      planeSection_5.getOriginCoordinate();

    // coordinate_10.setValue(new DoubleVector(new double[] {xo+(length-xo)/2, yo+(width-yo)/2, zo+(depth-zo)/2}));
    coordinate_10.setValue(new DoubleVector(new double[] {xx,yy,zz}));

    // coordinate_10.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {xo+(length-xo)/2, yo+(width-yo)/2, zo+(depth-zo)/2}));
    coordinate_10.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {xx,yy,zz}));

    coordinate_10.setCoordinateSystem(labCoordinateSystem_0);

    Coordinate coordinate_11 = 
      planeSection_5.getOrientationCoordinate();

    coordinate_11.setValue(new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    coordinate_11.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

    coordinate_11.setCoordinateSystem(labCoordinateSystem_0);

    SingleValue singleValue_5 = 
      planeSection_5.getSingleValue();

    singleValue_5.getValueQuantity().setValue(0.0);

    singleValue_5.getValueQuantity().setUnits(units_0);

    RangeMultiValue rangeMultiValue_5 = 
      planeSection_5.getRangeMultiValue();

    rangeMultiValue_5.setNValues(2);

    rangeMultiValue_5.getStartQuantity().setValue(0.0);

    rangeMultiValue_5.getStartQuantity().setUnits(units_0);

    rangeMultiValue_5.getEndQuantity().setValue(1.0);

    rangeMultiValue_5.getEndQuantity().setUnits(units_0);

    DeltaMultiValue deltaMultiValue_5 = 
      planeSection_5.getDeltaMultiValue();

    deltaMultiValue_5.setNValues(2);

    deltaMultiValue_5.getStartQuantity().setValue(0.0);

    deltaMultiValue_5.getStartQuantity().setUnits(units_0);

    deltaMultiValue_5.getDeltaQuantity().setValue(1.0);

    deltaMultiValue_5.getDeltaQuantity().setUnits(units_0);

    MultiValue multiValue_5 = 
      planeSection_5.getArbitraryMultiValue();

    multiValue_5.getValueQuantities().setUnits(units_0);

    multiValue_5.getValueQuantities().setArray(new DoubleVector(new double[] {0.0}));

    planeSection_5.setValueMode(0);

    planeSection_5.setPresentationName("plane_middepth");
  }
}
