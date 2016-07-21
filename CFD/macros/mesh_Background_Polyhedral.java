// STAR-CCM+ macro: mesh_Background_Polyhedral.java
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

    SimpleBlockPart simpleBlockPart_0 = 
      ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

    AutoMeshOperation autoMeshOperation_4 = 
      simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {}), new NeoObjectVector(new Object[] {simpleBlockPart_0}));

    autoMeshOperation_4.getMeshers().setMeshersByNames(new StringVector(new String[] {"star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher", "star.resurfacer.ResurfacerAutoMesher"}));

    autoMeshOperation_4.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);

    DualAutoMesher dualAutoMesher_1 = 
      ((DualAutoMesher) autoMeshOperation_4.getMeshers().getObject("Polyhedral Mesher"));

    dualAutoMesher_1.setEnableGrowthRate(true);

    dualAutoMesher_1.setTetOptimizeCycles(3);

    dualAutoMesher_1.setTetQualityThreshold(0.7);

    PrismAutoMesher prismAutoMesher_1 = 
      ((PrismAutoMesher) autoMeshOperation_4.getMeshers().getObject("Prism Layer Mesher"));

    prismAutoMesher_1.getPrismStretchingOption().setSelected(PrismStretchingOption.Type.WALL_THICKNESS);

    autoMeshOperation_4.getDefaultValues().get(BaseSize.class).setValue(50.0);

    PartsTargetSurfaceSize partsTargetSurfaceSize_0 = 
      autoMeshOperation_4.getDefaultValues().get(PartsTargetSurfaceSize.class);

    GenericRelativeSize genericRelativeSize_1 = 
      ((GenericRelativeSize) partsTargetSurfaceSize_0.getRelativeSize());

    // genericRelativeSize_1.setPercentage(1.0);

    // genericRelativeSize_1.setPercentage(2.0);

    partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    GenericAbsoluteSize genericAbsoluteSize_1 = 
      ((GenericAbsoluteSize) partsTargetSurfaceSize_0.getAbsoluteSize());

    genericAbsoluteSize_1.getValue().setValue(16.0);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_0 = 
      autoMeshOperation_4.getDefaultValues().get(PartsMinimumSurfaceSize.class);

    partsMinimumSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    GenericAbsoluteSize genericAbsoluteSize_2 = 
      ((GenericAbsoluteSize) partsMinimumSurfaceSize_0.getAbsoluteSize());

    genericAbsoluteSize_2.getValue().setValue(2.0);

    SurfaceGrowthRate surfaceGrowthRate_0 = 
      autoMeshOperation_4.getDefaultValues().get(SurfaceGrowthRate.class);

    surfaceGrowthRate_0.setGrowthRate(1.2);

    NumPrismLayers numPrismLayers_1 = 
      autoMeshOperation_4.getDefaultValues().get(NumPrismLayers.class);

    numPrismLayers_1.setNumLayers(10);

    autoMeshOperation_4.getDefaultValues().get(PrismWallThickness.class).setValue(0.02);

    PrismThickness prismThickness_1 = 
      autoMeshOperation_4.getDefaultValues().get(PrismThickness.class);

    prismThickness_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    GenericAbsoluteSize genericAbsoluteSize_3 = 
      ((GenericAbsoluteSize) prismThickness_1.getAbsoluteSize());

    genericAbsoluteSize_3.getValue().setValue(3.33);

    PartsTetPolyGrowthRate partsTetPolyGrowthRate_1 = 
      autoMeshOperation_4.getDefaultValues().get(PartsTetPolyGrowthRate.class);

    partsTetPolyGrowthRate_1.setGrowthRate(1.1);

    MaximumCellSize maximumCellSize_1 = 
      autoMeshOperation_4.getDefaultValues().get(MaximumCellSize.class);

    GenericRelativeSize genericRelativeSize_2 = 
      ((GenericRelativeSize) maximumCellSize_1.getRelativeSize());

    genericRelativeSize_2.setPercentage(110.0);

    genericRelativeSize_2.setPercentage(100.0);

    PartsTetPolyDensity partsTetPolyDensity_1 = 
      autoMeshOperation_4.getDefaultValues().get(PartsTetPolyDensity.class);

    partsTetPolyDensity_1.setVolumeMeshDensity(0.9);

    partsTetPolyDensity_1.setGrowthFactor(1.1);











    SurfaceCustomMeshControl surfaceCustomMeshControl_1 = 
      autoMeshOperation_4.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_1.getGeometryObjects().setQuery(null);

    PartSurface partSurface_n = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Seabed"));

    surfaceCustomMeshControl_1.getGeometryObjects().setObjects(partSurface_n);

    surfaceCustomMeshControl_1.getCustomConditions().get(PartsMinimumSurfaceSizeOption.class).setSelected(PartsMinimumSurfaceSizeOption.Type.CUSTOM);

    surfaceCustomMeshControl_1.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

    PartsTargetSurfaceSize partsTargetSurfaceSize_1 = 
      surfaceCustomMeshControl_1.getCustomValues().get(PartsTargetSurfaceSize.class);

    partsTargetSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    GenericAbsoluteSize genericAbsoluteSize_4 = 
      ((GenericAbsoluteSize) partsTargetSurfaceSize_1.getAbsoluteSize());

    genericAbsoluteSize_4.getValue().setValue(6.66);

    PartsMinimumSurfaceSize partsMinimumSurfaceSize_1 = 
      surfaceCustomMeshControl_1.getCustomValues().get(PartsMinimumSurfaceSize.class);

    partsMinimumSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

    GenericAbsoluteSize genericAbsoluteSize_5 = 
      ((GenericAbsoluteSize) partsMinimumSurfaceSize_1.getAbsoluteSize());


    genericAbsoluteSize_2.getValue().setValue(2.0);

    surfaceCustomMeshControl_1.setPresentationName("seabed");








    // SimpleBlockPart simpleBlockPart_0 = 
    //   ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

    PartSurface partSurface_0 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Inlet"));

    PartSurface partSurface_1 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Left Bank"));

    PartSurface partSurface_2 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Outlet"));

    PartSurface partSurface_3 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Right Bank"));

    PartSurface partSurface_4 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Sea Surface"));


    SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
      autoMeshOperation_4.getCustomMeshControls().createSurfaceControl();

    surfaceCustomMeshControl_0.setPresentationName("slip_surfaces");

    surfaceCustomMeshControl_0.getGeometryObjects().setQuery(null);

    surfaceCustomMeshControl_0.getGeometryObjects().setObjects(partSurface_0, partSurface_1, partSurface_2, partSurface_3, partSurface_4);

    PartsCustomizePrismMesh partsCustomizePrismMesh_0 = 
      surfaceCustomMeshControl_0.getCustomConditions().get(PartsCustomizePrismMesh.class);

    partsCustomizePrismMesh_0.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);



  }
}
