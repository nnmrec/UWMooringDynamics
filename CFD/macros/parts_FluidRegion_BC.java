// STAR-CCM+ macro: parts_FluidRegion_BC.java
// Written by STAR-CCM+ 11.04.010
package macro;

import java.util.*;

import star.turbulence.*;
import star.common.*;
import star.flow.*;
import star.base.neo.*;
import star.vis.*;
import star.meshing.*;

public class parts_FluidRegion_BC extends StarMacro {


  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS
  //
  // TODO: some of these variable should be read in from Matlab, easiest way is write/read from a CSV file
  // String path0    = "outputs/mooring-variables.csv";

  // CASE: Bamfield Flume
  // static final double xo          = 0;       // origin x coordinate [m]
  // static final double yo          = 0;       // origin y coordinate [m]
  // static final double zo          = 0;       // origin z coordinate [m]
  // static final double length      = 12.3;    // length in x-dimention (steamwise) [m]
  // static final double width       = 0.98;     // length in y-dimention (crossflow) [m]
  // static final double depth       = 0.76;      // length in z-dimention (vertical) [m]

  // CASE: Tidal Channel (large domain)
  // static final double xo          = 0;       // origin x coordinate [m]
  // static final double yo          = -150;       // origin y coordinate [m]
  // static final double zo          = 0;       // origin z coordinate [m]
  // static final double length       = 3000;    // length in x-dimention (steamwise) [m]
  // static final double width      = 790;     // length in y-dimention (crossflow) [m]
  // static final double depth      = 50;      // length in z-dimention (vertical) [m]

  // CASE: Tidal Channel (small domain)
  // static final double xo          = 0;       // origin x coordinate [m]
  // static final double yo          = 0;       // origin y coordinate [m]
  // static final double zo          = 0;       // origin z coordinate [m]
  // static final double length      = 800;    // length in x-dimention (steamwise) [m]
  // static final double width       = 320;     // length in y-dimention (crossflow) [m]
  // static final double depth       = 50;      // length in z-dimention (vertical) [m]

  // CASE: Tidal Channel (small domain) with 2 DOE-RM1 turbines
  // static final double xo          = 0;       // origin x coordinate [m]
  // static final double yo          = 0;       // origin y coordinate [m]
  // static final double zo          = 0;       // origin z coordinate [m]
  // static final double length      = 1000;    // length in x-dimention (steamwise) [m]
  // static final double width       = 400;     // length in y-dimention (crossflow) [m]
  // static final double depth       = 60;      // length in z-dimention (vertical) [m]

  // CASE: Bamfield Flume
  // static final double xo          = 0;       // origin x coordinate [m]
  // static final double yo          = 0;       // origin y coordinate [m]
  // static final double zo          = 0;       // origin z coordinate [m]
  // static final double length      = 12.3;    // length in x-dimension (steamwise) [m]
  // static final double width       = 1.0;     // length in y-dimension (crossflow) [m]
  // static final double depth       = 0.8;     // length in z-dimension (vertical) [m]





  public void execute() {
    execute0();
  }

  private void execute0() {


    Simulation simulation_0 = 
      getActiveSimulation();

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    MeshPartFactory meshPartFactory_0 = 
      simulation_0.get(MeshPartFactory.class);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();







    // UserFieldFunction userFieldFunction_0 = 
    //   // ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__length"));
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("${__length}"));

    // // turbulenceIntensityProfile_0.getMethod(FunctionScalarProfileMethod.class).setFieldFunction(userFieldFunction_0);

    // // simulation_0.println("DEBUG 0: __length = " + "${__length}");
    //   // simulation_0.println("DEBUG 0: __length = " + userFieldFunction_0);
    //   simulation_0.println("DEBUG 0: __length = " + "${__length}");


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

      // simulation_0.println("DEBUG 0: ${__length} = " + userFieldFunction_33);
      // simulation_0.println("DEBUG 0: ${__length} = " + Double.parseDouble(userFieldFunction_33));
      // simulation_0.println("DEBUG 0: length = " + userFieldFunction_33.getDefinition());
      // String userFieldFunction_33.getDefinition()
      // simulation_0.println("DEBUG 0: length class = " + Object.getClass(userFieldFunction_33));

// SimpleBlockPart simpleBlockPart_0 = 
//       ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

//     Coordinate coordinate_0 = 
//       simpleBlockPart_0.getCorner2();

//     Units units_0 = 
//       ((Units) simulation_0.getUnitsManager().getObject("m"));

//     coordinate_0.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {55.0, 400.0, 60.0}));




    SimpleBlockPart simpleBlockPart_0 = 
      meshPartFactory_0.createNewBlockPart(simulation_0.get(SimulationPartManager.class));

      simpleBlockPart_0.setDoNotRetessellate(true);

      simpleBlockPart_0.setCoordinateSystem(labCoordinateSystem_0);

    Coordinate coordinate_0 = 
      simpleBlockPart_0.getCorner1();

      coordinate_0.setCoordinateSystem(labCoordinateSystem_0);

      coordinate_0.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {Double.parseDouble(userFieldFunction_0.getDefinition()), 
                                                                                           Double.parseDouble(userFieldFunction_1.getDefinition()), 
                                                                                           Double.parseDouble(userFieldFunction_2.getDefinition())}));

    Coordinate coordinate_1 = 
      simpleBlockPart_0.getCorner2();

      coordinate_1.setCoordinateSystem(labCoordinateSystem_0);

      // coordinate_1.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {xo+length, yo+width, zo+depth}));
      coordinate_1.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {Double.parseDouble(userFieldFunction_0.getDefinition()) + Double.parseDouble(userFieldFunction_3.getDefinition()), 
                                                                                           Double.parseDouble(userFieldFunction_1.getDefinition()) + Double.parseDouble(userFieldFunction_4.getDefinition()), 
                                                                                           Double.parseDouble(userFieldFunction_2.getDefinition()) + Double.parseDouble(userFieldFunction_5.getDefinition())}));
           


    simpleBlockPart_0.rebuildSimpleShapePart();

    simpleBlockPart_0.setDoNotRetessellate(false);

    simulation_0.getSceneManager().createGeometryScene("Geometry Scene", "Outline", "Geometry", 1);



    // Scene scene_0 = 
    //   simulation_0.getSceneManager().getScene("Geometry Scene 1");

    // scene_0.initializeAndWait();

    // PartDisplayer partDisplayer_0 = 
    //   ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Outline 1"));

    // partDisplayer_0.initialize();

    // PartDisplayer partDisplayer_1 = 
    //   ((PartDisplayer) scene_0.getDisplayerManager().getDisplayer("Geometry 1"));

    // partDisplayer_1.initialize();

    // LogoAnnotation logoAnnotation_0 = 
    //   ((LogoAnnotation) simulation_0.getAnnotationManager().getObject("Logo"));

    // logoAnnotation_0.setOpacity(0.0);

    // scene_0.open(true);

    // SceneUpdate sceneUpdate_0 = 
    //   scene_0.getSceneUpdate();

    // HardcopyProperties hardcopyProperties_0 = 
    //   sceneUpdate_0.getHardcopyProperties();

    // hardcopyProperties_0.setCurrentResolutionWidth(1333);

    // hardcopyProperties_0.setCurrentResolutionHeight(671);

    // scene_0.resetCamera();



    PartSurface partSurface_0 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface"));

      simpleBlockPart_0.getPartSurfaceManager().splitPartSurfacesByAngle(new NeoObjectVector(new Object[] {partSurface_0}), 89.0);

    // CurrentView currentView_0 = 
    //   scene_0.getCurrentView();

    // currentView_0.setInput(new DoubleVector(new double[] {6.4481669464109395, 0.5180177835506068, -0.09673550054574998}), new DoubleVector(new double[] {-12.423454664627535, -0.6223661440763624, 14.697682882496599}), new DoubleVector(new double[] {-0.023019962152371128, 0.9986089447040001, 0.04743687278548732}), 6.1832434854209, 0);

    // currentView_0.setInput(new DoubleVector(new double[] {7.055373136459419, 0.554710347346071, -0.5727551349737627}), new DoubleVector(new double[] {-12.423454664627535, -0.6223661440763624, 14.697682882496599}), new DoubleVector(new double[] {0.5650482128243559, 0.3488848090921439, 0.7476629636197983}), 6.1832434854209, 0);

    partSurface_0.setPresentationName("Inlet");

    PartSurface partSurface_1 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 6"));

      partSurface_1.setPresentationName("Outlet");

    PartSurface partSurface_2 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 5"));

      partSurface_2.setPresentationName("Left Bank");

    PartSurface partSurface_3 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 2"));

      partSurface_3.setPresentationName("Right Bank");

    PartSurface partSurface_4 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 4"));

      partSurface_4.setPresentationName("Sea Surface");

    PartSurface partSurface_5 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 3"));

      partSurface_5.setPresentationName("Seabed");




    Region region_0 = 
      simulation_0.getRegionManager().createEmptyRegion();

    region_0.setPresentationName("Region");


    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Default");

      region_0.getBoundaryManager().removeBoundaries(new NeoObjectVector(new Object[] {boundary_0}));

    FeatureCurve featureCurve_0 = 
      ((FeatureCurve) region_0.getFeatureCurveManager().getObject("Default"));

      region_0.getFeatureCurveManager().removeObjects(featureCurve_0);

    FeatureCurve featureCurve_1 = 
      region_0.getFeatureCurveManager().createEmptyFeatureCurveWithName("Feature Curve");

      simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {simpleBlockPart_0}), "OneRegion", region_0, "OneBoundaryPerPartSurface", null, "OneFeatureCurve", featureCurve_1, RegionManager.CreateInterfaceMode.BOUNDARY);

      // currentView_0.setInput(new DoubleVector(new double[] {10.19587449057092, 2.041756461417844, -1.774983534741642}), new DoubleVector(new double[] {-12.17623835520402, -11.360810168740647, 4.061348485846002}), new DoubleVector(new double[] {0.13264982910165052, 0.20282032308661566, 0.9701896409374529}), 6.1832434854209, 0);



    Boundary boundary_1 = 
      region_0.getBoundaryManager().getBoundary("Block.Inlet");

      boundary_1.setBoundaryType(InletBoundary.class);

    Boundary boundary_2 = 
      region_0.getBoundaryManager().getBoundary("Block.Outlet");

      boundary_2.setBoundaryType(PressureBoundary.class);



    Boundary boundary_3 = 
      region_0.getBoundaryManager().getBoundary("Block.Left Bank");

    boundary_3.getConditions().get(WallShearStressOption.class).setSelected(WallShearStressOption.Type.SLIP);

    Boundary boundary_4 = 
      region_0.getBoundaryManager().getBoundary("Block.Right Bank");

    boundary_4.getConditions().get(WallShearStressOption.class).setSelected(WallShearStressOption.Type.SLIP);

    Boundary boundary_5 = 
      region_0.getBoundaryManager().getBoundary("Block.Sea Surface");

    boundary_5.getConditions().get(WallShearStressOption.class).setSelected(WallShearStressOption.Type.SLIP);

    Boundary boundary_6 = 
      region_0.getBoundaryManager().getBoundary("Block.Seabed");

    boundary_6.getConditions().get(WallShearStressOption.class).setSelected(WallShearStressOption.Type.SLIP);


    

    

    region_0.getConditions().get(TwoEquationTurbulenceUserSourceOption.class).setSelected(TwoEquationTurbulenceUserSourceOption.Type.AMBIENT);

    AmbientTurbulenceSpecification ambientTurbulenceSpecification_0 = 
      region_0.getValues().get(AmbientTurbulenceSpecification.class);

    ambientTurbulenceSpecification_0.setInflowBoundary(boundary_1);
  }
}
