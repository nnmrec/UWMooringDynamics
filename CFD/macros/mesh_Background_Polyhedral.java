// // STAR-CCM+ macro: mesh_Background_Polyhedral.java
// // tested on STAR-CCM+ v10 and v11
// // 
// // by Danny Clay Sale (dsale@uw.edu)
// // 
// // license: ?
// // 

// package macro;
// import java.util.*;
// import star.common.*;
// import star.base.neo.*;
// import star.resurfacer.*;
// import star.dualmesher.*;
// import star.prismmesher.*;
// import star.meshing.*;

// public class mesh_Background_Polyhedral extends StarMacro {

//   public void execute() {
//     execute0();
//   }

//   private void execute0() {

//     Simulation simulation_0 = 
//       getActiveSimulation();

//     SimpleBlockPart simpleBlockPart_0 = 
//       ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

//     AutoMeshOperation autoMeshOperation_4 = 
//       simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {}), new NeoObjectVector(new Object[] {simpleBlockPart_0}));

//     autoMeshOperation_4.getMeshers().setMeshersByNames(new StringVector(new String[] {"star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher", "star.resurfacer.ResurfacerAutoMesher"}));

//     autoMeshOperation_4.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);

//     DualAutoMesher dualAutoMesher_1 = 
//       ((DualAutoMesher) autoMeshOperation_4.getMeshers().getObject("Polyhedral Mesher"));

//     dualAutoMesher_1.setEnableGrowthRate(true);

//     dualAutoMesher_1.setTetOptimizeCycles(3);

//     dualAutoMesher_1.setTetQualityThreshold(0.7);

//     PrismAutoMesher prismAutoMesher_1 = 
//       ((PrismAutoMesher) autoMeshOperation_4.getMeshers().getObject("Prism Layer Mesher"));

//     prismAutoMesher_1.getPrismStretchingOption().setSelected(PrismStretchingOption.Type.WALL_THICKNESS);

//     autoMeshOperation_4.getDefaultValues().get(BaseSize.class).setValue(10.0);

//     PartsTargetSurfaceSize partsTargetSurfaceSize_0 = 
//       autoMeshOperation_4.getDefaultValues().get(PartsTargetSurfaceSize.class);

//     GenericRelativeSize genericRelativeSize_1 = 
//       ((GenericRelativeSize) partsTargetSurfaceSize_0.getRelativeSize());

//     // genericRelativeSize_1.setPercentage(1.0);

//     // genericRelativeSize_1.setPercentage(2.0);

//     partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

//     GenericAbsoluteSize genericAbsoluteSize_1 = 
//       ((GenericAbsoluteSize) partsTargetSurfaceSize_0.getAbsoluteSize());

//     genericAbsoluteSize_1.getValue().setValue(2.0);

    // PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
    //   autoMeshOperation_4.getDefaultValues().get(PartsMinimumSurfaceSize.class);

    // partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    // GenericAbsoluteSize genericAbsoluteSize_2 = 
    //   ((GenericAbsoluteSize) partsMinimumSurfaceSize_0.getAbsoluteSize());

    // genericAbsoluteSize_2.getValue().setValue(0.5);

//     SurfaceGrowthRate surfaceGrowthRate_0 = 
//       autoMeshOperation_4.getDefaultValues().get(SurfaceGrowthRate.class);

//     surfaceGrowthRate_0.setGrowthRate(1.1);

//     NumPrismLayers numPrismLayers_1 = 
//       autoMeshOperation_4.getDefaultValues().get(NumPrismLayers.class);

//     numPrismLayers_1.setNumLayers(12);

//     autoMeshOperation_4.getDefaultValues().get(PrismWallThickness.class).setValue(0.014);

//     PrismThickness prismThickness_1 = 
//       autoMeshOperation_4.getDefaultValues().get(PrismThickness.class);

//     prismThickness_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

//     GenericAbsoluteSize genericAbsoluteSize_3 = 
//       ((GenericAbsoluteSize) prismThickness_1.getAbsoluteSize());

//     genericAbsoluteSize_3.getValue().setValue(1.00);

//     PartsTetPolyGrowthRate partsTetPolyGrowthRate_1 = 
//       autoMeshOperation_4.getDefaultValues().get(PartsTetPolyGrowthRate.class);

//     partsTetPolyGrowthRate_1.setGrowthRate(1.1);

//     MaximumCellSize maximumCellSize_1 = 
//       autoMeshOperation_4.getDefaultValues().get(MaximumCellSize.class);

//     GenericRelativeSize genericRelativeSize_2 = 
//       ((GenericRelativeSize) maximumCellSize_1.getRelativeSize());

//     genericRelativeSize_2.setPercentage(110.0);

//     genericRelativeSize_2.setPercentage(100.0);

//     PartsTetPolyDensity partsTetPolyDensity_1 = 
//       autoMeshOperation_4.getDefaultValues().get(PartsTetPolyDensity.class);

//     partsTetPolyDensity_1.setVolumeMeshDensity(1.1);

//     partsTetPolyDensity_1.setGrowthFactor(0.9);











//     SurfaceCustomMeshControl surfaceCustomMeshControl_1 = 
//       autoMeshOperation_4.getCustomMeshControls().createSurfaceControl();

//     surfaceCustomMeshControl_1.getGeometryObjects().setQuery(null);

//     PartSurface partSurface_n = 
//       ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Seabed"));

//     surfaceCustomMeshControl_1.getGeometryObjects().setObjects(partSurface_n);

//     surfaceCustomMeshControl_1.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);

//     surfaceCustomMeshControl_1.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

//     PartsTargetSurfaceSize partsTargetSurfaceSize_1 = 
//       surfaceCustomMeshControl_1.getCustomValues().get(PartsTargetSurfaceSize.class);

//     partsTargetSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

//     GenericAbsoluteSize genericAbsoluteSize_4 = 
//       ((GenericAbsoluteSize) partsTargetSurfaceSize_1.getAbsoluteSize());

//     genericAbsoluteSize_4.getValue().setValue(6.66);

//     PartsMinimumSurfaceSize partsMinimumSurfaceSize_1 = 
//       surfaceCustomMeshControl_1.getCustomValues().get(PartsMinimumSurfaceSize.class);

//     partsMinimumSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

//     GenericAbsoluteSize genericAbsoluteSize_5 = 
//       ((GenericAbsoluteSize) partsMinimumSurfaceSize_1.getAbsoluteSize());


//     genericAbsoluteSize_2.getValue().setValue(2.0);

//     surfaceCustomMeshControl_1.setPresentationName("seabed");








//     // SimpleBlockPart simpleBlockPart_0 = 
//     //   ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

//     PartSurface partSurface_0 = 
//       ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Inlet"));

//     PartSurface partSurface_1 = 
//       ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Left Bank"));

//     PartSurface partSurface_2 = 
//       ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Outlet"));

//     PartSurface partSurface_3 = 
//       ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Right Bank"));

//     PartSurface partSurface_4 = 
//       ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Sea Surface"));


//     SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
//       autoMeshOperation_4.getCustomMeshControls().createSurfaceControl();

//     surfaceCustomMeshControl_0.setPresentationName("slip_surfaces");

//     surfaceCustomMeshControl_0.getGeometryObjects().setQuery(null);

//     surfaceCustomMeshControl_0.getGeometryObjects().setObjects(partSurface_0, partSurface_1, partSurface_2, partSurface_3, partSurface_4);

//     PartsCustomizePrismMesh partsCustomizePrismMesh_0 = 
//       surfaceCustomMeshControl_0.getCustomConditions().get(PartsCustomizePrismMesh.class);

//     partsCustomizePrismMesh_0.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);



//   }
// }










// STAR-CCM+ macro: mesh_Background_Polyhedral.java
// Written by STAR-CCM+ 11.02.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.resurfacer.*;
import star.dualmesher.*;
import star.prismmesher.*;
import star.meshing.*;

public class mesh_Background_Polyhedral extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

        Simulation simulation_0 = 
              getActiveSimulation();









    // get the user inputs field functions
      UserFieldFunction userFieldFunction_0 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_BaseSize"));
      UserFieldFunction userFieldFunction_1 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_SurfaceSizeTarget"));
      UserFieldFunction userFieldFunction_2 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_SurfaceSizeMinimum"));
      UserFieldFunction userFieldFunction_3 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_Seabed_SurfaceSizeTarget"));
      
      UserFieldFunction userFieldFunction_4 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_PrismLayers"));
      UserFieldFunction userFieldFunction_5 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_PrismWallThickness"));
      UserFieldFunction userFieldFunction_6 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_PrismLayerThickness"));

      UserFieldFunction userFieldFunction_7 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_SurfaceGrowthRate"));
      UserFieldFunction userFieldFunction_8 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_VolGrowthRate"));
      UserFieldFunction userFieldFunction_9 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_VolumeDensity"));
      UserFieldFunction userFieldFunction_10 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__mesh_GrowthFactor"));
      





    

    SimpleBlockPart simpleBlockPart_0 = 
      ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

    AutoMeshOperation autoMeshOperation_0 = 
      simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {}), new NeoObjectVector(new Object[] {simpleBlockPart_0}));

    autoMeshOperation_0.getMeshers().setMeshersByNames(new StringVector(new String[] {"star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher", "star.resurfacer.ResurfacerAutoMesher"}));

    autoMeshOperation_0.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);





    // AutoMeshOperation autoMeshOperation_0 = 
    //   ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh 3"));

    ResurfacerAutoMesher resurfacerAutoMesher_0 = 
      ((ResurfacerAutoMesher) autoMeshOperation_0.getMeshers().getObject("Surface Remesher"));


    // resurfacerAutoMesher_0.setDoCurvatureRefinement(false);
    resurfacerAutoMesher_0.setDoCurvatureRefinement(true);

    // resurfacerAutoMesher_0.setDoProximityRefinement(false);
    resurfacerAutoMesher_0.setDoProximityRefinement(true);

    resurfacerAutoMesher_0.setDoCompatibilityRefinement(true);

    // resurfacerAutoMesher_0.setDoAlignedMeshing(false);
    resurfacerAutoMesher_0.setDoAlignedMeshing(true);

    resurfacerAutoMesher_0.setMinimumFaceQuality(0.051);

    DualAutoMesher dualAutoMesher_0 = 
      ((DualAutoMesher) autoMeshOperation_0.getMeshers().getObject("Polyhedral Mesher"));

    dualAutoMesher_0.setPostOptimize(true);

    dualAutoMesher_0.setEnableGrowthRate(true);

    // dualAutoMesher_0.setTetOptimizeCycles(1);
    dualAutoMesher_0.setTetOptimizeCycles(2);

    dualAutoMesher_0.setTetQualityThreshold(0.71);
    // dualAutoMesher_0.setTetQualityThreshold(0.9);

    PrismAutoMesher prismAutoMesher_0 = 
      ((PrismAutoMesher) autoMeshOperation_0.getMeshers().getObject("Prism Layer Mesher"));

    // prismAutoMesher_0.getPrismStretchingFunction().setSelected(PrismStretchingFunction.Type.GEOMETRIC_PROGRESSION);

    // prismAutoMesher_0.getPrismStretchingFunction().setSelected(PrismStretchingFunction.Type.HYPERBOLIC_TANGENT);

    // prismAutoMesher_0.getPrismStretchingOption().setSelected(PrismStretchingOption.Type.STRETCHING);

    prismAutoMesher_0.getPrismStretchingOption().setSelected(PrismStretchingOption.Type.WALL_THICKNESS);

    prismAutoMesher_0.setGapFillPercentage(25.1);

    prismAutoMesher_0.setMinimumThickness(0.011);

    prismAutoMesher_0.setLayerChoppingPercentage(1.0E-6);

    prismAutoMesher_0.setLayerChoppingPercentage(0.0);

    prismAutoMesher_0.setBoundaryMarchAngle(50.1);

    prismAutoMesher_0.setConcaveAngleLimit(1.0E-6);

    prismAutoMesher_0.setLayerChoppingPercentage(1.0E-5);

    prismAutoMesher_0.setConvexAngleLimit(359.9);

    prismAutoMesher_0.setNearCoreLayerAspectRatio(1.1);

















































    // autoMeshOperation_0.getDefaultValues().get(BaseSize.class).setValue(5.1);
    autoMeshOperation_0.getDefaultValues().get(BaseSize.class).setValue(  Double.parseDouble(userFieldFunction_0.getDefinition()) );
    



    ProjectToCadOption projectToCadOption_0 = 
      autoMeshOperation_0.getDefaultValues().get(ProjectToCadOption.class);

    // projectToCadOption_0.setProjectToCad(false);
    projectToCadOption_0.setProjectToCad(true);



    PartsTargetSurfaceSize partsTargetSurfaceSize_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsTargetSurfaceSize.class);

            // partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.RELATIVE);

            // GenericRelativeSize genericRelativeSize_0 = 
            //   ((GenericRelativeSize) partsTargetSurfaceSize_0.getRelativeSize());

            // genericRelativeSize_0.setPercentage(99.9);

        partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

        GenericAbsoluteSize genericAbsoluteSize_00 = 
          ((GenericAbsoluteSize) partsTargetSurfaceSize_0.getAbsoluteSize());

        genericAbsoluteSize_00.getValue().setValue(Double.parseDouble(userFieldFunction_1.getDefinition()));







    // PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
    //   autoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);

    // GenericAbsoluteSize genericAbsoluteSize_0 = 
    //   ((GenericAbsoluteSize) partsMinimumSurfaceSize_0.getAbsoluteSize());

    // // genericAbsoluteSize_0.getValue().setValue(1.1);
    // genericAbsoluteSize_0.getValue().setValue(Double.parseDouble(userFieldFunction_2.getDefinition()));




    PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsMinimumSurfaceSize.class);

    partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    GenericAbsoluteSize genericAbsoluteSize_0 = 
      ((GenericAbsoluteSize) partsMinimumSurfaceSize_0.getAbsoluteSize());

    genericAbsoluteSize_0.getValue().setValue(Double.parseDouble(userFieldFunction_2.getDefinition()));






    SurfaceCurvature surfaceCurvature_0 = 
      autoMeshOperation_0.getDefaultValues().get(SurfaceCurvature.class);

    SurfaceCurvatureNumPts surfaceCurvatureNumPts_0 = 
      surfaceCurvature_0.getSurfaceCurvatureNumPts();

    surfaceCurvatureNumPts_0.setNumPointsAroundCircle(24.0);
    // surfaceCurvatureNumPts_0.setNumPointsAroundCircle(37.0);



    SurfaceProximity surfaceProximity_0 = 
      autoMeshOperation_0.getDefaultValues().get(SurfaceProximity.class);

    surfaceProximity_0.getFloor().setValue(1.0E-5);

    surfaceProximity_0.setNumPointsInGap(2.00001);

    // surfaceProximity_0.setEnableCeiling(true);
    surfaceProximity_0.setEnableCeiling(false);






    SurfaceGrowthRate surfaceGrowthRate_0 = 
      autoMeshOperation_0.getDefaultValues().get(SurfaceGrowthRate.class);

    // surfaceGrowthRate_0.setGrowthRate(1.21);
    surfaceGrowthRate_0.setGrowthRate(Double.parseDouble(userFieldFunction_7.getDefinition()));
    







    NumPrismLayers numPrismLayers_0 = 
      autoMeshOperation_0.getDefaultValues().get(NumPrismLayers.class);

    // numPrismLayers_0.setNumLayers(13);
      numPrismLayers_0.setNumLayers(  Integer.parseInt(userFieldFunction_4.getDefinition()) );






    // autoMeshOperation_0.getDefaultValues().get(PrismWallThickness.class).setValue(0.0138);
    autoMeshOperation_0.getDefaultValues().get(PrismWallThickness.class).setValue(Double.parseDouble(userFieldFunction_5.getDefinition()));


    PrismThickness prismThickness_0 = 
      autoMeshOperation_0.getDefaultValues().get(PrismThickness.class);

    GenericAbsoluteSize genericAbsoluteSize_1 = 
      ((GenericAbsoluteSize) prismThickness_0.getAbsoluteSize());

    // genericAbsoluteSize_1.getValue().setValue(3.01);
      genericAbsoluteSize_1.getValue().setValue(Double.parseDouble(userFieldFunction_6.getDefinition()));




    PartsTetPolyGrowthRate partsTetPolyGrowthRate_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsTetPolyGrowthRate.class);

    // partsTetPolyGrowthRate_0.setGrowthRate(1.11);
    partsTetPolyGrowthRate_0.setGrowthRate(Double.parseDouble(userFieldFunction_8.getDefinition()));





    MaximumCellSize maximumCellSize_0 = 
      autoMeshOperation_0.getDefaultValues().get(MaximumCellSize.class);

    GenericRelativeSize genericRelativeSize_1 = 
      ((GenericRelativeSize) maximumCellSize_0.getRelativeSize());

    genericRelativeSize_1.setPercentage(1000.0);






    PartsTetPolyDensity partsTetPolyDensity_0 = 
      autoMeshOperation_0.getDefaultValues().get(PartsTetPolyDensity.class);

    // partsTetPolyDensity_0.setVolumeMeshDensity(1.01);
    partsTetPolyDensity_0.setVolumeMeshDensity(Double.parseDouble(userFieldFunction_9.getDefinition()));


    // partsTetPolyDensity_0.setGrowthFactor(1.01);
    partsTetPolyDensity_0.setGrowthFactor(Double.parseDouble(userFieldFunction_10.getDefinition()));









    // SurfaceCustomMeshControl surfaceCustomMeshControl_2 = 
    //   autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    // surfaceCustomMeshControl_2.setPresentationName("seabed");

    // surfaceCustomMeshControl_2.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    // surfaceCustomMeshControl_2.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);

    // PartsTargetSurfaceSize partsTargetSurfaceSize_1 = 
    //   surfaceCustomMeshControl_2.getCustomValues().get(PartsTargetSurfaceSize.class);

    // partsTargetSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    // GenericAbsoluteSize genericAbsoluteSize_2 = 
    //   ((GenericAbsoluteSize) partsTargetSurfaceSize_1.getAbsoluteSize());

    // genericAbsoluteSize_2.getValue().setValue(2.0);

    // PartsMinimumSurfaceSize partsMinimumSurfaceSize_1 = 
    //   surfaceCustomMeshControl_2.getCustomValues().get(PartsMinimumSurfaceSize.class);

    // // GenericRelativeSize genericRelativeSize_2 = 
    //   // ((GenericRelativeSize) partsMinimumSurfaceSize_1.getRelativeSize());
    // GenericAbsoluteSize genericAbsoluteSize_22 = 
    //   ((GenericAbsoluteSize) partsMinimumSurfaceSize_1.getAbsoluteSize());

    // // genericRelativeSize_2.setPercentage(0.5);
    //   genericAbsoluteSize_22.getValue().setValue(0.5);

    // surfaceCustomMeshControl_2.getGeometryObjects().setQuery(null);

    // // SimpleBlockPart simpleBlockPart_0 = 
    // //   ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

    // PartSurface partSurface_0 = 
    //   ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Seabed"));

    // surfaceCustomMeshControl_2.getGeometryObjects().setObjects(partSurface_0);









    SurfaceCustomMeshControl surfaceCustomMeshControl_3 = 
      autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_3.setPresentationName("no_prisms");

    surfaceCustomMeshControl_3.getGeometryObjects().setQuery(null);

    PartSurface partSurface_1 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Sea Surface"));

      PartSurface partSurface_2 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Left Bank"));

    PartSurface partSurface_3 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Right Bank"));

      PartSurface partSurface_4 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Inlet"));

    PartSurface partSurface_5 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Outlet"));




    // surfaceCustomMeshControl_3.getGeometryObjects().setObjects(partSurface_1);

    PartsCustomizePrismMesh partsCustomizePrismMesh_0 = 
      surfaceCustomMeshControl_3.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_0.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);



    // surfaceCustomMeshControl_3.getGeometryObjects().setQuery(null);



    surfaceCustomMeshControl_3.getGeometryObjects().setObjects(partSurface_2, partSurface_3, partSurface_1);

    surfaceCustomMeshControl_3.getGeometryObjects().setQuery(null);





    SurfaceCustomMeshControl surfaceCustomMeshControl_5 = 
          autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

        surfaceCustomMeshControl_5.setPresentationName("seabed");

        surfaceCustomMeshControl_5.getGeometryObjects().setQuery(null);

    // SimpleBlockPart simpleBlockPart_0 = 
    //   ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

    PartSurface partSurface_0 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Seabed"));

    surfaceCustomMeshControl_5.getGeometryObjects().setObjects(partSurface_0);

    surfaceCustomMeshControl_5.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsCustomizePrismMesh partsCustomizePrismMesh_2 = 
      surfaceCustomMeshControl_5.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_2.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.CUSTOMIZE);

    PartsCustomizePrismMeshControls partsCustomizePrismMeshControls_1 = 
      partsCustomizePrismMesh_2.getCustomPrismControls();

    partsCustomizePrismMeshControls_1.setCustomizeNumLayers(true);

    partsCustomizePrismMeshControls_1.setCustomizeTotalThickness(true);

    partsCustomizePrismMeshControls_1.setCustomizeStretching(true);

    partsCustomizePrismMeshControls_1.setOverrideBoundaryDefault(true);

    PartsTargetSurfaceSize partsTargetSurfaceSize_2 = 
      surfaceCustomMeshControl_5.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    GenericAbsoluteSize genericAbsoluteSize_4 = 
      ((GenericAbsoluteSize) partsTargetSurfaceSize_2.getAbsoluteSize());

    // genericAbsoluteSize_4.getValue().setValue(2.01);
      genericAbsoluteSize_4.getValue().setValue(Double.parseDouble(userFieldFunction_3.getDefinition()));



    NumPrismLayers numPrismLayers_2 = 
      surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(NumPrismLayers.class);

    // numPrismLayers_2.setNumLayers(15);
      numPrismLayers_2.setNumLayers(Integer.parseInt(userFieldFunction_4.getDefinition()));
    

    PartsOverrideBoundaryDefault partsOverrideBoundaryDefault_1 = 
      surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(PartsOverrideBoundaryDefault.class);

    partsOverrideBoundaryDefault_1.setOverride(true);

    // surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(PrismWallThickness.class).setValue(0.0138);
    surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(PrismWallThickness.class).setValue(Double.parseDouble(userFieldFunction_5.getDefinition()));


    PrismThickness prismThickness_2 = 
      surfaceCustomMeshControl_5.getCustomValues().get(CustomPrismValuesManager.class).get(PrismThickness.class);

    prismThickness_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    GenericAbsoluteSize genericAbsoluteSize_5 = 
      ((GenericAbsoluteSize) prismThickness_2.getAbsoluteSize());

    // genericAbsoluteSize_5.getValue().setValue(1.51);
      genericAbsoluteSize_5.getValue().setValue(Double.parseDouble(userFieldFunction_6.getDefinition()));







  }
}
