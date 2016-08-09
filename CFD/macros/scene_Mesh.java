// STAR-CCM+ macro: scene_Mesh.java
// Written by STAR-CCM+ 11.02.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class scene_Mesh extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.getSceneManager().createGeometryScene("Mesh Scene", "Outline", "Mesh", 3);

    Scene scene_1 = 
      simulation_0.getSceneManager().getScene("Mesh Scene 1");

    scene_1.initializeAndWait();

    PartDisplayer partDisplayer_1 = 
      ((PartDisplayer) scene_1.getDisplayerManager().getDisplayer("Mesh 1"));

    partDisplayer_1.initialize();

    scene_1.open(true);

    scene_1.resetCamera();

    CurrentView currentView_1 = 
      scene_1.getCurrentView();

    currentView_1.setInput(new DoubleVector(new double[] {429.8868756801996, 213.6593648887052, 51.84439785459031}), new DoubleVector(new double[] {-420.9847639323272, -1314.0089090375825, 287.3078180570846}), new DoubleVector(new double[] {-0.36864333838540664, 0.33829625002124153, 0.8658277751872068}), 431.53794734646476, 0);

    scene_1.setPresentationName("mesh");

    partDisplayer_1.getInputParts().setQuery(null);

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Region");

    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Block.Inlet");

    Boundary boundary_1 = 
      region_0.getBoundaryManager().getBoundary("Block.Left Bank");

    Boundary boundary_2 = 
      region_0.getBoundaryManager().getBoundary("Block.Outlet");

    Boundary boundary_3 = 
      region_0.getBoundaryManager().getBoundary("Block.Seabed");

    PlaneSection planeSection_0 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("plane_crossflow"));

    PlaneSection planeSection_2 = 
      ((PlaneSection) simulation_0.getPartManager().getObject("plane_streamwise"));

    // partDisplayer_1.getInputParts().setObjects(boundary_0, boundary_1, boundary_2, boundary_3, planeSection_0, planeSection_2);
      partDisplayer_1.getInputParts().setObjects(boundary_0, boundary_1, boundary_2, planeSection_0, planeSection_2);

    currentView_1.setInput(new DoubleVector(new double[] {553.4529778303305, 189.8165607578913, -72.61363492928587}), new DoubleVector(new double[] {-264.8548074343063, -429.9371091534496, 300.23575644638913}), new DoubleVector(new double[] {0.10216892545106032, 0.41040478437746775, 0.9061619191029051}), 431.53794734646476, 0);

    ScalarDisplayer scalarDisplayer_1 = 
      scene_1.getDisplayerManager().createScalarDisplayer("Scalar");

    scalarDisplayer_1.initialize();

    scalarDisplayer_1.setPresentationName("yPlus");

    scalarDisplayer_1.getInputParts().setQuery(null);

    scalarDisplayer_1.getInputParts().setObjects(boundary_3);

    PrimitiveFieldFunction primitiveFieldFunction_1 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("WallYplus"));

    scalarDisplayer_1.getScalarDisplayQuantity().setFieldFunction(primitiveFieldFunction_1);

    partDisplayer_1.setOpacity(1.0);

    scalarDisplayer_1.setFillMode(5);

    scalarDisplayer_1.getScalarDisplayQuantity().setClip(0);

    partDisplayer_1.getInputParts().setQuery(null);

    partDisplayer_1.getInputParts().setObjects(boundary_0, boundary_1, boundary_2, boundary_3, planeSection_0, planeSection_2);

    Legend legend_1 = 
      scalarDisplayer_1.getLegend();

    legend_1.setLevels(11);

    currentView_1.setInput(new DoubleVector(new double[] {316.0237625446394, 121.16517256655591, -30.440014379817203}), new DoubleVector(new double[] {-145.39053504015115, -343.008928076849, 347.46759973583704}), new DoubleVector(new double[] {0.2013438092286036, 0.4902467389369429, 0.8480087295818414}), 431.53794734646476, 0);
  }
}
