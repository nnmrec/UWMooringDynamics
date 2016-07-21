// STAR-CCM+ macro: scene_Mesh_Velocity.java
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
import java.io.*;
import java.util.logging.*;

public class scene_Mesh_Velocity extends StarMacro {


  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS (all these user inputs should be read from a CSV file instead)
  String path0    = "outputs/rotors.csv";
  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {
        

      Simulation simulation_0 = 
        getActiveSimulation();
      Region region_0 = 
      simulation_0.getRegionManager().getRegion("Block");


        //
        int       nVirtualDisks   = 0;
        List<String>  textline    = new ArrayList<String>();


        File f = new File(path0);
        try {

            FileReader  fr   = new FileReader(f);
            Scanner     sc   = new Scanner(fr);
            String      line = "";
            
            Integer nLines = new Integer(0);
            while (sc.hasNextLine()) {
                // this skips the header line
                if(nLines == 0) {
                   nLines = nLines + 1;
                   sc.nextLine();
                   continue;
                }
                nLines = nLines + 1;
                line = sc.nextLine();
                textline.add(line);
            }
            nVirtualDisks = nLines - 1;          

        } catch (FileNotFoundException ex) {
            Logger.getLogger(scene_Mesh_Velocity.class.getName()).log(Level.SEVERE, null, ex);
        } // end try

      simulation_0.println("DEBUG 0: nVirtualDisks = " + nVirtualDisks);

      List<String>  names   = new ArrayList<String>();
      // String[] name = new String[nVirtualDisks];
      for (int i = 0; i < nVirtualDisks; i++) {
        String name = textline.get(i).split(",")[0];
        names.add(name);
        // name[i] = textline.get(i).split(",")[0];
      }
      String[] name_VirtualDiskMarker             = new String[nVirtualDisks];
      String[] name_VirtualDiskInflowPlaneMarker  = new String[nVirtualDisks];
      for (int j = 0; j < nVirtualDisks; j++) {
        // I think these are not connected to the "name" ... they will always be named in order of creation
        name_VirtualDiskMarker[j]            = "VirtualDiskMarker" + (j+1);
        name_VirtualDiskInflowPlaneMarker[j] = "VirtualDiskInflowPlaneMarker" + (j+1);
      }





    PlaneSection planeSection_3 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_3.setPresentationName("plane-xz");

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

      simulation_0.println("DEBUG 00: name = " + names.get(0));
    CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      // ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("turbine-01-CSys 1"));
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject(names.get(1) + "-CSys 1"));

    planeSection_3.setCoordinateSystem(cartesianCoordinateSystem_0);

    Coordinate coordinate_2 = 
      planeSection_3.getOrientationCoordinate();

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    coordinate_2.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));

    planeSection_3.getInputParts().setQuery(null);

    planeSection_3.getInputParts().setObjects(region_0);



    PlaneSection planeSection_4 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_4.setPresentationName("plane-xy");

    planeSection_4.setCoordinateSystem(cartesianCoordinateSystem_0);

    Coordinate coordinate_3 = 
      planeSection_4.getOrientationCoordinate();

    coordinate_3.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.0, 0.0}));

    planeSection_4.getInputParts().setQuery(null);

    planeSection_4.getInputParts().setObjects(region_0);


    PlaneSection planeSection_5 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_5.setPresentationName("plane-yz");

    planeSection_5.setCoordinateSystem(cartesianCoordinateSystem_0);

    planeSection_5.getInputParts().setQuery(null);

    planeSection_5.getInputParts().setObjects(region_0);








    simulation_0.getSceneManager().createGeometryScene("Mesh Scene", "Outline", "Mesh", 3);

    Scene scene_3 = 
      simulation_0.getSceneManager().getScene("Mesh Scene 1");

    scene_3.initializeAndWait();

    PartDisplayer partDisplayer_1 = 
      ((PartDisplayer) scene_3.getDisplayerManager().getDisplayer("Mesh 1"));

    partDisplayer_1.initialize();

    scene_3.open(true);

    CurrentView currentView_2 = 
      scene_3.getCurrentView();

    currentView_2.setInput(new DoubleVector(new double[] {281.25, 250.0, 30.0}), new DoubleVector(new double[] {281.25, 250.0, 1488.5240092750641}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 380.75117038439464, 0);

    scene_3.setPresentationName("mesh");

    partDisplayer_1.getInputParts().setQuery(null);

    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Inlet");

    Boundary boundary_1 = 
      region_0.getBoundaryManager().getBoundary("Left Bank");

    Boundary boundary_2 = 
      region_0.getBoundaryManager().getBoundary("Outlet");

    Boundary boundary_3 = 
      region_0.getBoundaryManager().getBoundary("Seabed");

    partDisplayer_1.getInputParts().setObjects(boundary_0, boundary_1, boundary_2, boundary_3, planeSection_4, planeSection_3, planeSection_5);

    currentView_2.setInput(new DoubleVector(new double[] {276.9176603668437, 235.89398050822038, 100.0162739978332}), new DoubleVector(new double[] {-166.7798200770667, -1208.77699119883, 173.3639924668553}), new DoubleVector(new double[] {-0.2670486087576157, 0.130494559677147, 0.9548068969458117}), 394.98541862403493, 0);

    simulation_0.getSceneManager().createScalarScene("Scalar Scene", "Outline", "Scalar");

    Scene scene_4 = 
      simulation_0.getSceneManager().getScene("Scalar Scene 1");

    scene_4.initializeAndWait();

    PartDisplayer partDisplayer_2 = 
      ((PartDisplayer) scene_4.getDisplayerManager().getDisplayer("Outline 1"));

    partDisplayer_2.initialize();

    ScalarDisplayer scalarDisplayer_2 = 
      ((ScalarDisplayer) scene_4.getDisplayerManager().getDisplayer("Scalar 1"));

    scalarDisplayer_2.initialize();

    scene_4.open(true);

    CurrentView currentView_3 = 
      scene_4.getCurrentView();

    currentView_3.setInput(new DoubleVector(new double[] {281.25, 250.0, 30.0}), new DoubleVector(new double[] {281.25, 250.0, 1488.5240092750641}), new DoubleVector(new double[] {0.0, 1.0, 0.0}), 380.75117038439464, 0);

    scene_4.setPresentationName("velocity");

    scalarDisplayer_2.getInputParts().setQuery(null);

    scalarDisplayer_2.getInputParts().setObjects(planeSection_4, planeSection_3, planeSection_5);

    PrimitiveFieldFunction primitiveFieldFunction_0 = 
      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));

    VectorMagnitudeFieldFunction vectorMagnitudeFieldFunction_0 = 
      ((VectorMagnitudeFieldFunction) primitiveFieldFunction_0.getMagnitudeFunction());

    scalarDisplayer_2.getScalarDisplayQuantity().setFieldFunction(vectorMagnitudeFieldFunction_0);

    currentView_3.setInput(new DoubleVector(new double[] {306.48433251813634, 327.41659659361846, -32.85648549876349}), new DoubleVector(new double[] {-90.60633973670213, -890.82083302522, 838.4555097268384}), new DoubleVector(new double[] {-0.12896336122145058, 0.6043523440200658, 0.7862103381029263}), 404.5021674270079, 0);

    scalarDisplayer_2.setFillMode(5);

    scalarDisplayer_2.getScalarDisplayQuantity().setAutoRange(0);

    scalarDisplayer_2.getScalarDisplayQuantity().setClip(0);

    scalarDisplayer_2.getScalarDisplayQuantity().setRange(new DoubleVector(new double[] {1.6, 2.4}));

    Legend legend_2 = 
      scalarDisplayer_2.getLegend();

    PredefinedLookupTable predefinedLookupTable_0 = 
      ((PredefinedLookupTable) simulation_0.get(LookupTableManager.class).getObject("Kelvin temperature"));

    legend_2.setLookupTable(predefinedLookupTable_0);

    legend_2.setReverse(true);

    legend_2.setLevels(11);

















    // now that scenes are all created and pretty, setup the auto export of hardcopies


    // Scene scene_1 = 
    //   simulation_0.getSceneManager().getScene("mesh");

    // SceneUpdate sceneUpdate_1 = 
    //   scene_1.getSceneUpdate();

    // sceneUpdate_1.setSaveAnimation(true);

    // IterationUpdateFrequency iterationUpdateFrequency_1 = 
    //   sceneUpdate_1.getIterationUpdateFrequency();

    // iterationUpdateFrequency_1.setIterations(10);

    // HardcopyProperties hardcopyProperties_0 = 
    //   sceneUpdate_1.getHardcopyProperties();

    // hardcopyProperties_0.setOutputWidth(2560);

    // hardcopyProperties_0.setOutputHeight(1440);










    // Scene scene_2 = 
    //   simulation_0.getSceneManager().getScene("velocity");

    // SceneUpdate sceneUpdate_2 = 
    //   scene_2.getSceneUpdate();

    // sceneUpdate_2.setSaveAnimation(true);

    // IterationUpdateFrequency iterationUpdateFrequency_2 = 
    //   sceneUpdate_2.getIterationUpdateFrequency();

    // iterationUpdateFrequency_2.setIterations(10);

    // HardcopyProperties hardcopyProperties_1 = 
    //   sceneUpdate_2.getHardcopyProperties();

    // hardcopyProperties_1.setOutputWidth(2560);

    // hardcopyProperties_1.setOutputHeight(1440);












  }
}
