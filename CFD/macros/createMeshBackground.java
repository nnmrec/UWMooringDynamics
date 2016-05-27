// STAR-CCM+ macro: createMeshBackground.java
// Written by STAR-CCM+ 10.06.010
// 
// license: ?
// 
// 
// issues:
//  * not sure about the syntax to define the growth rates ... maybe try a switch/case statement ... or eval statements
// 
// 

///////////////////////////////////////////////////////////////////////////////
// import all the classes we need
//
package macro;

import java.util.*;

import star.vdm.*;
import star.turbulence.*;
import star.kwturb.*;
import star.material.*;
import star.common.*;
import star.base.neo.*;
import star.vis.*;
import star.base.report.*;
import star.flow.*;
import star.trimmer.*;
import star.prismmesher.*;
import star.segregatedflow.*;
import star.metrics.*;
import star.meshing.*;


public class createMeshBackground extends StarMacro {

///////////////////////////////////////////////////////////////////////////////
// USER INPUTS
//
static final double diameter_rotor          = 25;     // rotor diameter [m]
// static final double depth                   = 60;     // length in z-dimention (vertical) [m]  
static final double core_baseSize           = 25.0;   // base size of core mesh [m]
static final double core_sizeSurface        = 8.00;   // surface size of core mesh [m]
static final double core_maxSize            = 50.0;   // max size of cells in the core mes
// static final double core_growthSurface      = '';     // growth rate from surfaces
// static final double core_growthBase         = '';     // growth rate within core meshh
static final int    prism_numLayers         = 10;     // number prism layers
static final double prism_wallThickness     = 0.02;   // thickness of the first prism cell on the wall [m]
static final double prism_thickness         = 3.33;   // thickness of entire prism layer [m]
static final double prism_thicknessNeighbor = 2.0;    // multiplier of core mesh size to last prism layer [ratio of 2 to Inf]
static final double custom_seabedSize       = 6.66;   // target cell size on seabed
// static final double custom_seabedGrowth     = '';     // growth rate from seabed surface
// static final int    iter_max                = 333;
// static final double limit_continuity        = 1e-05;
///////////////////////////////////////////////////////////////////////////////

public void execute() {
execute0();
}

private void execute0() {

Simulation simulation_0 = 
getActiveSimulation();

// because starccm+ gets confused when the aspect ratio of the box changes,
// need to use some logic to split/name the faces of the box ...
// although upon further inspection, this may have been fixed in latest v11 of starccm


///////////////////////////////////////////////////////////////////////////////
// create the "background" Mesh
//
SimpleBlockPart simpleBlockPart_0 = 
((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

AutoMeshOperation autoMeshOperation_0 = 
simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {}), new NeoObjectVector(new Object[] {simpleBlockPart_0}));

autoMeshOperation_0.getMeshers().setMeshersByNames(new StringVector(new String[] {"star.trimmer.TrimmerAutoMesher", "star.prismmesher.PrismAutoMesher"}));

autoMeshOperation_0.getMesherParallelModeOption().setSelected(MesherParallelModeOption.Type.PARALLEL);

// DEFAULT core mesh settings
autoMeshOperation_0.getDefaultValues().get(BaseSize.class).setValue(core_baseSize);

PartsTargetSurfaceSize partsTargetSurfaceSize_0 = 
autoMeshOperation_0.getDefaultValues().get(PartsTargetSurfaceSize.class);

partsTargetSurfaceSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

GenericAbsoluteSize genericAbsoluteSize_0 = 
((GenericAbsoluteSize) partsTargetSurfaceSize_0.getAbsoluteSize());

genericAbsoluteSize_0.getValue().setValue(core_sizeSurface);


PartsSimpleTemplateGrowthRate partsSimpleTemplateGrowthRate_0 = 
autoMeshOperation_0.getDefaultValues().get(PartsSimpleTemplateGrowthRate.class);

partsSimpleTemplateGrowthRate_0.getGrowthRateOption().setSelected(PartsGrowthRateOption.Type.SLOW);

partsSimpleTemplateGrowthRate_0.getSurfaceGrowthRateOption().setSelected(PartsSurfaceGrowthRateOption.Type.MEDIUM);

MaximumCellSize maximumCellSize_0 = 
autoMeshOperation_0.getDefaultValues().get(MaximumCellSize.class);

maximumCellSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);


GenericAbsoluteSize genericAbsoluteSize_2 = 
((GenericAbsoluteSize) maximumCellSize_0.getAbsoluteSize());

genericAbsoluteSize_2.getValue().setValue(core_maxSize);


// DEFAULT prism layer mesher
PrismAutoMesher prismAutoMesher_0 = 
((PrismAutoMesher) autoMeshOperation_0.getMeshers().getObject("Prism Layer Mesher"));

prismAutoMesher_0.getPrismStretchingOption().setSelected(PrismStretchingOption.Type.WALL_THICKNESS);

NumPrismLayers numPrismLayers_0 = 
autoMeshOperation_0.getDefaultValues().get(NumPrismLayers.class);

numPrismLayers_0.setNumLayers(prism_numLayers);

autoMeshOperation_0.getDefaultValues().get(PrismWallThickness.class).setValue(prism_wallThickness);

PrismThickness prismThickness_0 = 
autoMeshOperation_0.getDefaultValues().get(PrismThickness.class);

prismThickness_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

GenericAbsoluteSize genericAbsoluteSize_1 = 
((GenericAbsoluteSize) prismThickness_0.getAbsoluteSize());

genericAbsoluteSize_1.getValue().setValue(prism_thickness);

MaxTrimmerSizeToPrismThicknessRatio maxTrimmerSizeToPrismThicknessRatio_0 = 
autoMeshOperation_0.getDefaultValues().get(MaxTrimmerSizeToPrismThicknessRatio.class);

maxTrimmerSizeToPrismThicknessRatio_0.setLimitCellSizeByPrismThickness(true);

SizeThicknessRatio sizeThicknessRatio_0 = 
maxTrimmerSizeToPrismThicknessRatio_0.getSizeThicknessRatio();

sizeThicknessRatio_0.setNeighboringThicknessMultiplier(prism_thicknessNeighbor);




///////////////////////////////////////////////////////////////////////////////
// CUSTOM surface controls
// 
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

PartSurface partSurface_5 = 
((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Seabed"));

// for FREE-SLIP surfaces
SurfaceCustomMeshControl surfaceCustomMeshControl_0 = 
autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

surfaceCustomMeshControl_0.setPresentationName("block surfaces");

surfaceCustomMeshControl_0.getGeometryObjects().setQuery(null);

surfaceCustomMeshControl_0.getGeometryObjects().setObjects(partSurface_0, partSurface_1, partSurface_2, partSurface_3, partSurface_4);

PartsCustomizePrismMesh partsCustomizePrismMesh_0 = 
surfaceCustomMeshControl_0.getCustomConditions().get(PartsCustomizePrismMesh.class);

partsCustomizePrismMesh_0.getCustomPrismOptions().setSelected(PartsCustomPrismsOption.Type.DISABLE);


// for NO-SLIP surfaces
SurfaceCustomMeshControl surfaceCustomMeshControl_1 = 
autoMeshOperation_0.getCustomMeshControls().createSurfaceControl();

surfaceCustomMeshControl_1.setPresentationName("seabed surface");  

surfaceCustomMeshControl_1.getGeometryObjects().setQuery(null);



surfaceCustomMeshControl_1.getGeometryObjects().setObjects(partSurface_5);

surfaceCustomMeshControl_1.getCustomConditions().get(PartsTargetSurfaceSizeOption.class).setSelected(PartsTargetSurfaceSizeOption.Type.CUSTOM);

PartsTargetSurfaceSize partsTargetSurfaceSize_1 = 
surfaceCustomMeshControl_1.getCustomValues().get(PartsTargetSurfaceSize.class);

partsTargetSurfaceSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

GenericAbsoluteSize genericAbsoluteSize_3 = 
((GenericAbsoluteSize) partsTargetSurfaceSize_1.getAbsoluteSize());

genericAbsoluteSize_3.getValue().setValue(custom_seabedSize);

surfaceCustomMeshControl_1.getCustomConditions().get(PartsCustomSurfaceGrowthRateOption.class).setSelected(PartsCustomSurfaceGrowthRateOption.Type.CUSTOM);

PartsCustomSimpleSurfaceGrowthRate partsCustomSimpleSurfaceGrowthRate_0 = 
surfaceCustomMeshControl_1.getCustomValues().get(PartsCustomSimpleSurfaceGrowthRate.class);

partsCustomSimpleSurfaceGrowthRate_0.getSurfaceGrowthRateOption().setSelected(PartsSurfaceGrowthRateOption.Type.FAST);


// // ISSUES: here are the commands for growth rates ... figure out tje syntax to use with USER INPUTS
// PartsCustomSimpleSurfaceGrowthRate partsCustomSimpleSurfaceGrowthRate_0 = 
//   surfaceCustomMeshControl_1.getCustomValues().get(PartsCustomSimpleSurfaceGrowthRate.class);

// partsCustomSimpleSurfaceGrowthRate_0.getSurfaceGrowthRateOption().setSelected(PartsSurfaceGrowthRateOption.Type.DISABLE);

// partsCustomSimpleSurfaceGrowthRate_0.getSurfaceGrowthRateOption().setSelected(PartsSurfaceGrowthRateOption.Type.VERYSLOW);

// partsCustomSimpleSurfaceGrowthRate_0.getSurfaceGrowthRateOption().setSelected(PartsSurfaceGrowthRateOption.Type.SLOW);

// partsCustomSimpleSurfaceGrowthRate_0.getSurfaceGrowthRateOption().setSelected(PartsSurfaceGrowthRateOption.Type.MEDIUM);

// partsCustomSimpleSurfaceGrowthRate_0.getSurfaceGrowthRateOption().setSelected(PartsSurfaceGrowthRateOption.Type.FAST);

// partsCustomSimpleSurfaceGrowthRate_0.getSurfaceGrowthRateOption().setSelected(PartsSurfaceGrowthRateOption.Type.CUSTOM);


} // end execute0()
} // end public class
