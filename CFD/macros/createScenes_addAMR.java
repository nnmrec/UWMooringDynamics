// STAR-CCM+ macro: createScenes_addAMR.java
// Written by STAR-CCM+ 10.06.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class createScenes_addAMR extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Block");




    simulation_0.getSceneManager().createScalarScene("Scalar Scene", "Outline", "Scalar");

    Scene scene_2 = 
      simulation_0.getSceneManager().getScene("Scalar Scene 1");

    scene_2.initializeAndWait();

    PartDisplayer partDisplayer_0 = 
      ((PartDisplayer) scene_2.getDisplayerManager().getDisplayer("Outline 1"));

    partDisplayer_0.initialize();

    ScalarDisplayer scalarDisplayer_0 = 
      ((ScalarDisplayer) scene_2.getDisplayerManager().getDisplayer("Scalar 1"));

    scalarDisplayer_0.initialize();

    scene_2.open(true);

    CurrentView currentView_0 = 
      scene_2.getCurrentView();

    currentView_0.setInput(new DoubleVector(new double[] {1000.0, 250.0, 30.0}), new DoubleVector(new double[] {1000.0, 250.0, 4014.300605510957}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 1040.111173394782, 0);

    scene_2.setPresentationName("TI ratio");

    partDisplayer_0.getInputParts().setQuery(null);

    // Boundary boundary_0 = 
    //   region_0.getBoundaryManager().getBoundary("Inlet");

    // Boundary boundary_1 = 
    //   region_0.getBoundaryManager().getBoundary("Left Bank");

    // Boundary boundary_2 = 
    //   region_0.getBoundaryManager().getBoundary("Outlet");

    // Boundary boundary_3 = 
    //   region_0.getBoundaryManager().getBoundary("Right Bank");

    // Boundary boundary_4 = 
    //   region_0.getBoundaryManager().getBoundary("Sea Surface");

    // Boundary boundary_5 = 
    //   region_0.getBoundaryManager().getBoundary("Seabed");

    // PlaneSection planeSection_0 = 
    //   ((PlaneSection) simulation_0.getPartManager().getObject("plane-xy"));

    // PlaneSection planeSection_1 = 
    //   ((PlaneSection) simulation_0.getPartManager().getObject("plane-xz"));

    // PlaneSection planeSection_2 = 
    //   ((PlaneSection) simulation_0.getPartManager().getObject("plane-yz"));

    // partDisplayer_0.getInputParts().setObjects(boundary_0, boundary_1, boundary_2, boundary_3, boundary_4, boundary_5, planeSection_0, planeSection_1, planeSection_2);


    PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("plane-xy"));

    PlaneSection planeSection_1 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("plane-xz"));

    PlaneSection planeSection_2 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("plane-yz"));

    scalarDisplayer_0.getInputParts().setObjects(planeSection_0, planeSection_1, planeSection_2);

    


    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Turbulence Intensity Ratio"));

    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {0.5, 1.5}));

    scalarDisplayer_0.getScalarDisplayQuantity().setClip(0);
    // scalarDisplayer_0.getScalarDisplayQuantity().setClip(1);
    // scalarDisplayer_0.getScalarDisplayQuantity().setClip(2);
    // scalarDisplayer_0.getScalarDisplayQuantity().setClip(3);


  }
}
