// STAR-CCM+ macro: scene_TurbulenceIntensityLocal.java
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

public class scene_TurbulenceIntensityLocal extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Region");




    simulation_0.getSceneManager().createScalarScene("Scalar Scene", "Outline", "Scalar");

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("Scalar Scene 1");

    scene_0.initializeAndWait();

    scene_0.setPresentationName("TI_local");


    // initialize the scene 
    PartDisplayer partDisplayer_0 = 
      ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Outline 1"));

    partDisplayer_0.initialize();

    ScalarDisplayer scalarDisplayer_0 = 
      ((ScalarDisplayer) scene_0.getDisplayerManager().getDisplayer("Scalar 1"));

    scalarDisplayer_0.initialize();


    // set the view
    scene_0.open(true);

    CurrentView currentView_0 = 
      scene_0.getCurrentView();

    currentView_0.setInput(new DoubleVector(new double[] {1000.0, 250.0, 30.0}), new DoubleVector(new double[] {1000.0, 250.0, 4014.300605510957}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 1040.111173394782, 0);

    

    



    // choose the derived parts to show the scalar
    partDisplayer_0.getInputParts().setQuery(null);

    PlaneSection planeSection_0 = 
      // ((PlaneSection) simulation_0.getPartManager().getObject("plane_middepth"));
      ((PlaneSection) simulation_0.getPartManager().getObject("plane-xy"));

    PlaneSection planeSection_1 = 
      // ((PlaneSection) simulation_0.getPartManager().getObject("plane_streamwise"));
      ((PlaneSection) simulation_0.getPartManager().getObject("plane-xz"));

    PlaneSection planeSection_2 = 
      // ((PlaneSection) simulation_0.getPartManager().getObject("plane_crossflow"));
      ((PlaneSection) simulation_0.getPartManager().getObject("plane-yz"));

    scalarDisplayer_0.getInputParts().setObjects(planeSection_0, planeSection_1, planeSection_2);

    

    // choose the scalar field function
    UserFieldFunction userFieldFunction_0 = 
      ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("local_TI"));

    scalarDisplayer_0.getScalarDisplayQuantity().setFieldFunction(userFieldFunction_0);

    scalarDisplayer_0.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {0.05, 0.15}));

    scalarDisplayer_0.getScalarDisplayQuantity().setClip(0);



  }
}
