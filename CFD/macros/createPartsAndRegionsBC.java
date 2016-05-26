// STAR-CCM+ macro: createPartsAndRegionsBC.java
// Written by STAR-CCM+ 10.06.010
// 
// license: ?
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


public class createPartsAndRegionsBC extends StarMacro {

	///////////////////////////////////////////////////////////////////////////////
	// USER INPUTS
	//
	static final double xo           = -500;     // origin x coordinate [m]
  static final double yo           = -500;     // origin y coordinate [m]
  static final double zo           = 0;        // origin z coordinate [m]
  static final double length 				= 1000;	// length in x-dimention (steamwise) [m]
	static final double width 				= 500;		// length in y-dimention (crossflow) [m]
	static final double depth 				= 60;		// length in z-dimention (vertical) [m]
	static final double bc_TI 				= 0.1; 		// turbulence intensity for inlet and outlet TI = u' / U [unitless]
	static final double bc_Lturb 			= 3.125; 	// turbulent length scale for inlet and outlet [m]
	static final double inlet_Vx 			= 1.0; 		// inlet x-velocity [m/s]
	static final double inlet_Vy 			= 0.0; 		// inlet y-velocity [m/s]
	static final double inlet_Vz 			= 0.0; 		// inlet z-velocity [m/s]
	static final double z0 					= 0.01;		// seabed surface roughness height [m]
	///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {

	Simulation simulation_0 = 
	  getActiveSimulation();

	///////////////////////////////////////////////////////////////////////////////
	// create the "Block" Shape Part
	//
	Units units_0 = 
	  simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    MeshPartFactory meshPartFactory_0 = 
      simulation_0.get(MeshPartFactory.class);

    SimpleBlockPart simpleBlockPart_0 = 
      meshPartFactory_0.createNewBlockPart(simulation_0.get(SimulationPartManager.class));

    simpleBlockPart_0.setDoNotRetessellate(true);

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

    simpleBlockPart_0.setCoordinateSystem(labCoordinateSystem_0);

    Coordinate coordinate_0 = 
      simpleBlockPart_0.getCorner1();

    coordinate_0.setCoordinateSystem(labCoordinateSystem_0);

    coordinate_0.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {-1.0, -1.0, -1.0}));

    // coordinate_0.setValue(new DoubleVector(new double[] {0.0, 0.0, 0.0}));
    coordinate_0.setValue(new DoubleVector(new double[] {xo, yo, zo}));

    Coordinate coordinate_1 = 
      simpleBlockPart_0.getCorner2();

    coordinate_1.setCoordinateSystem(labCoordinateSystem_0);

    coordinate_1.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 1.0, 1.0}));

    coordinate_1.setValue(new DoubleVector(new double[] {length, width, depth}));

    simpleBlockPart_0.rebuildSimpleShapePart();

    simpleBlockPart_0.setDoNotRetessellate(false);



	///////////////////////////////////////////////////////////////////////////////
	// split the surface of "Block" Shape Part into separate surfaces
    //
    PartSurface partSurface_0 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface"));

    simpleBlockPart_0.getPartSurfaceManager().splitPartSurfacesByAngle(new NeoObjectVector(new Object[] {partSurface_0}), 89.0);

    simulation_0.getRegionManager().newRegionsFromParts(new NeoObjectVector(new Object[] {simpleBlockPart_0}), "OneRegionPerPart", null, "OneBoundaryPerPartSurface", null, "OneFeatureCurve", null, true);


    partSurface_0.setPresentationName("Inlet");


    PartSurface partSurface_1 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 2"));

    partSurface_1.setPresentationName("Left Bank");


    PartSurface partSurface_2 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 3"));

    partSurface_2.setPresentationName("Seabed");


    PartSurface partSurface_3 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 4"));

    partSurface_3.setPresentationName("Right Bank");


    PartSurface partSurface_4 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 5"));

    partSurface_4.setPresentationName("Sea Surface");


    PartSurface partSurface_5 = 
      ((PartSurface) simpleBlockPart_0.getPartSurfaceManager().getPartSurface("Block Surface 6"));

    partSurface_5.setPresentationName("Outlet");


	///////////////////////////////////////////////////////////////////////////////
	// assign types to Regions and Boundary Conditions
    //

    // INLET
    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Block");

    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Block Surface");

    boundary_0.setBoundaryType(InletBoundary.class);

    boundary_0.setPresentationName("Inlet");

    boundary_0.getConditions().get(KwTurbSpecOption.class).setSelected(KwTurbSpecOption.Type.INTENSITY_LENGTH_SCALE);

    boundary_0.getConditions().get(InletVelocityOption.class).setSelected(InletVelocityOption.Type.COMPONENTS);

    TurbulenceIntensityProfile turbulenceIntensityProfile_0 = 
      boundary_0.getValues().get(TurbulenceIntensityProfile.class);

    turbulenceIntensityProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(bc_TI);

    TurbulentLengthScaleProfile turbulentLengthScaleProfile_0 = 
      boundary_0.getValues().get(TurbulentLengthScaleProfile.class);

    turbulentLengthScaleProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(bc_Lturb);

    VelocityProfile velocityProfile_0 = 
      boundary_0.getValues().get(VelocityProfile.class);

    velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(inlet_Vx, inlet_Vy, inlet_Vz);

    region_0.getConditions().get(TwoEquationTurbulenceUserSourceOption.class).setSelected(TwoEquationTurbulenceUserSourceOption.Type.AMBIENT);

    AmbientTurbulenceSpecification ambientTurbulenceSpecification_0 = 
      region_0.getValues().get(AmbientTurbulenceSpecification.class);

    ambientTurbulenceSpecification_0.setInflowBoundary(boundary_0);

    // KwAmbientTurbulenceSpecification kwAmbientTurbulenceSpecification_0 = 
    //   region_0.getValues().get(KwAmbientTurbulenceSpecification.class);

    // kwAmbientTurbulenceSpecification_0.setInflowBoundary(boundary_0);



    // OUTLET
	Boundary boundary_5 = 
      region_0.getBoundaryManager().getBoundary("Block Surface 6");

    boundary_5.setPresentationName("Outlet");

    boundary_5.setBoundaryType(PressureBoundary.class);

    boundary_5.getConditions().get(KwTurbSpecOption.class).setSelected(KwTurbSpecOption.Type.INTENSITY_LENGTH_SCALE);

    TurbulenceIntensityProfile turbulenceIntensityProfile_1 = 
      boundary_5.getValues().get(TurbulenceIntensityProfile.class);

    turbulenceIntensityProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(bc_TI);

    TurbulentLengthScaleProfile turbulentLengthScaleProfile_1 = 
      boundary_5.getValues().get(TurbulentLengthScaleProfile.class);

    turbulentLengthScaleProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(bc_Lturb);



    // SIDE WALLS & SEA SURFACE
    Boundary boundary_1 = 
      region_0.getBoundaryManager().getBoundary("Block Surface 2");

    boundary_1.setPresentationName("Left Bank");

    boundary_1.getConditions().get(WallShearStressOption.class).setSelected(WallShearStressOption.Type.SLIP);

    Boundary boundary_4 = 
      region_0.getBoundaryManager().getBoundary("Block Surface 5");

	boundary_4.setPresentationName("Sea Surface");      

    boundary_4.getConditions().get(WallShearStressOption.class).setSelected(WallShearStressOption.Type.SLIP);

    Boundary boundary_3 = 
      region_0.getBoundaryManager().getBoundary("Block Surface 4");

    boundary_3.setPresentationName("Right Bank");

    boundary_3.getConditions().get(WallShearStressOption.class).setSelected(WallShearStressOption.Type.SLIP);



    // seabed
    Boundary boundary_2 = 
      region_0.getBoundaryManager().getBoundary("Block Surface 3");

    boundary_2.setPresentationName("Seabed");

    boundary_2.getConditions().get(WallShearStressOption.class).setSelected(WallShearStressOption.Type.NO_SLIP);

    boundary_2.getConditions().get(WallSurfaceOption.class).setSelected(WallSurfaceOption.Type.ROUGH);

    RoughnessHeightProfile roughnessHeightProfile_0 = 
      boundary_2.getValues().get(RoughnessHeightProfile.class);

    roughnessHeightProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(z0);



    ///////////////////////////////////////////////////////////////////////////////
	  // after creating the Regions some new Solver options appear
    //
    KwTurbViscositySolver kwTurbViscositySolver_0 = 
      ((KwTurbViscositySolver) simulation_0.getSolverManager().getSolver(KwTurbViscositySolver.class));

    kwTurbViscositySolver_0.setMaxTvr(1.0E10);



    ///////////////////////////////////////////////////////////////////////////////
    // setup some stoping criteria (default values will be fine for now)
    //
    ResidualMonitor residualMonitor_0 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Continuity"));

    MonitorIterationStoppingCriterion monitorIterationStoppingCriterion_0 = 
      residualMonitor_0.createIterationStoppingCriterion();

  } // end execute0()
} // end public class