// STAR-CCM+ macro: scene_LineProbes.java
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

public class scene_LineProbes extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();






    XYPlot xYPlot_0 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_0.open();

    xYPlot_0.setPresentationName("line_probe_TI");

    YAxisType yAxisType_0 = 
      ((YAxisType) xYPlot_0.getYAxes().getAxisType("Y Type 1"));

    FieldFunctionUnits fieldFunctionUnits_0 = 
      yAxisType_0.getScalarFunction();

    UserFieldFunction userFieldFunction_1 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("local_TI"));

    fieldFunctionUnits_0.setFieldFunction(userFieldFunction_1);

    LinePart linePart_0 = 
      ((LinePart) simulation_0.getPartManager().getObject("line_probe_centerline"));

    xYPlot_0.getParts().setObjects(linePart_0);

    xYPlot_0.setTitle("Turbulence Intensity");







    

    InternalDataSet internalDataSet_0 = 
      ((InternalDataSet) yAxisType_0.getDataSetManager().getDataSet("line_probe_centerline"));

    LineStyle lineStyle_0 = 
      internalDataSet_0.getLineStyle();

    lineStyle_0.getLinePatternOption().setSelected(LinePatternOption.Type.SOLID);

    SymbolStyle symbolStyle_0 = 
      internalDataSet_0.getSymbolStyle();

    symbolStyle_0.setSize(12);

    symbolStyle_0.setStrokeWidth(3.0);

    symbolStyle_0.setSpacing(1);

    symbolStyle_0.getSymbolShapeOption().setSelected(SymbolShapeOption.Type.FILLED_DIAMOND);

    yAxisType_0.setSmooth(true);

    yAxisType_0.setSmooth(false);

    internalDataSet_0.setNeedsSorting(true);






    XYPlot xYPlot_1 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_1.open();

    xYPlot_1.setPresentationName("Copy of line_probe_TI");

    xYPlot_1.copyProperties(xYPlot_0);

    xYPlot_1.setPresentationName("Copy of line_probe_TI_ratio");

    xYPlot_1.setPresentationName("line_probe_TI_ratio");

    YAxisType yAxisType_1 = 
      ((YAxisType) xYPlot_1.getYAxes().getAxisType("Y Type 1"));

    FieldFunctionUnits fieldFunctionUnits_1 = 
      yAxisType_1.getScalarFunction();

    UserFieldFunction userFieldFunction_2 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Turbulence_Intensity_Ratio"));

    fieldFunctionUnits_1.setFieldFunction(userFieldFunction_2);

    xYPlot_1.setTitle("Turbulence Intensity ratio");







    XYPlot xYPlot_2 = 
      simulation_0.getPlotManager().createPlot(XYPlot.class);

    xYPlot_2.open();

    xYPlot_2.setPresentationName("Copy of line_probe_TI");

    xYPlot_2.copyProperties(xYPlot_0);

    xYPlot_2.setPresentationName("line_probe_Ux");

    YAxisType yAxisType_2 = 
      ((YAxisType) xYPlot_2.getYAxes().getAxisType("Y Type 1"));

    FieldFunctionUnits fieldFunctionUnits_2 = 
      yAxisType_2.getScalarFunction();

      PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));

    VectorComponentFieldFunction vectorComponentFieldFunction_0 = 
      ((VectorComponentFieldFunction) primitiveFieldFunction_0.getComponentFunction(0));

    fieldFunctionUnits_2.setFieldFunction(vectorComponentFieldFunction_0);
    // fieldFunctionUnits_2.setFieldFunction(userFieldFunction_3);

    xYPlot_2.setTitle("Streamwise Velocity, U_x");





  }
}
