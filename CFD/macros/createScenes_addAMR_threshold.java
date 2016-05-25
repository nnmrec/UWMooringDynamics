// STAR-CCM+ macro: createScenes_addAMR_threshold.java
// Written by STAR-CCM+ 10.06.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class createScenes_addAMR_threshold extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.getSceneManager().createScalarScene("Scalar Scene", "Outline", "Scalar");

    Scene scene_3 = 
      simulation_0.getSceneManager().getScene("Scalar Scene 1");

    scene_3.initializeAndWait();

    PartDisplayer partDisplayer_0 = 
      ((PartDisplayer) scene_3.getDisplayerManager().getDisplayer("Outline 1"));

    partDisplayer_0.initialize();

    ScalarDisplayer scalarDisplayer_2 = 
      ((ScalarDisplayer) scene_3.getDisplayerManager().getDisplayer("Scalar 1"));

    scalarDisplayer_2.initialize();

    scene_3.open(true);

    CurrentView currentView_3 = 
      scene_3.getCurrentView();

    currentView_3.setInput(new DoubleVector(new double[] {1000.0, 250.0, 30.0}), new DoubleVector(new double[] {1000.0, 250.0, 4014.300605510957}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 1040.111173394782, 0);

    scene_3.setPresentationName("refine TI ratio");

    // scalarDisplayer_2.getInputParts().setQuery(null);

    PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("plane-xy"));

    PlaneSection planeSection_1 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("plane-xz"));

    PlaneSection planeSection_2 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("plane-yz"));

    scalarDisplayer_2.getInputParts().setObjects(planeSection_0, planeSection_1, planeSection_2);

    UserFieldFunction userFieldFunction_1 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("cell size for refinement (TI ratio)"));
      // ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("refine Turbulence Intensity Ratio"));

    scalarDisplayer_2.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_1);

    scalarDisplayer_2.getScalarDisplayQuantity().setClip(0);

    currentView_3.setInput(new DoubleVector(new double[] {728.9773730016938, 239.4092729259557, -6.31510354458851}), new DoubleVector(new double[] {728.9773730016938, 239.4092729259557, 1512.7757167679115}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 396.5622808236504, 0);
  }
}
