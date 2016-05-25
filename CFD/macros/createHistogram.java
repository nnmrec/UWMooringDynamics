// STAR-CCM+ macro: createHistogram.java
// Written by STAR-CCM+ 11.02.009
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class createHistogram extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    HistogramPlot histogramPlot_0 = 
      simulation_0.getPlotManager().createPlot(HistogramPlot.class);

    histogramPlot_0.open();

    HistogramAxisType histogramAxisType_0 = 
      histogramPlot_0.getXAxisType();

    FieldFunctionUnits fieldFunctionUnits_0 = 
      histogramAxisType_0.getBinFunction();

    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Turbulence Intensity Ratio"));

    fieldFunctionUnits_0.setFieldFunction(userFieldFunction_0);

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Block");

    histogramPlot_0.getParts().setObjects(region_0);

    histogramPlot_0.getParts().setObjects(region_0);

    histogramAxisType_0.setNumberOfBin(1000);

    Cartesian2DAxisManager cartesian2DAxisManager_0 = 
      ((Cartesian2DAxisManager) histogramPlot_0.getAxisManager());

    Cartesian2DAxis cartesian2DAxis_0 = 
      ((Cartesian2DAxis) cartesian2DAxisManager_0.getAxis("Bottom Axis"));

    cartesian2DAxis_0.setLockMinimum(true);

    cartesian2DAxis_0.setLockMaximum(true);

    cartesian2DAxis_0.setMaximum(10.0);
  }
}
