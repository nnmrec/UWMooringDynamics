// STAR-CCM+ macro: physics_SST_KOmega.java
// tested on STAR-CCM+ v10 and v11
// 
// by Danny Clay Sale (dsale@uw.edu)
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


public class physics_SST_KOmega extends StarMacro {

	///////////////////////////////////////////////////////////////////////////////
	// USER INPUTS
	//
	// Flume Case
 //  static final double density 			      = 997;		   // fluid density [kg/m^3]
	// static final double dynamic_viscosity 	= 0.00108; 	 // fluid dynamic viscosity [Pa-s]
	// static final double init_TI 			      = 0.1; 		   // turbulence intensity, TI = u' / U [unitless]
	// static final double init_Lturb 			    = 0.05625; 	   // turbulent length scale [m]
	// static final double init_Vturb 			    = 0.1; 		   // turbulent velocity scale [m/s]
	// static final double init_Vx 			      = 0.001; 		   // initial x-velocity [m/s]    NOTE: do not start from 0 because sometime field functions may divide by 0
	// static final double init_Vy 			      = 0.001; 		   // initial y-velocity [m/s]
	// static final double init_Vz 			      = 0.001; 		   // initial z-velocity [m/s]
 //  static final double inlet_Vx    = 0.9;     // inlet x-velocity [m/s]
 //  static final double inlet_Vy    = 0.0;     // inlet y-velocity [m/s]
 //  static final double inlet_Vz    = 0.0;     // inlet z-velocity [m/s]

  // Tidal Channel Case
  // static final double density             = 1025;         // fluid density [kg/m^3]
  // static final double dynamic_viscosity   = 0.00108;     // fluid dynamic viscosity [Pa-s]
  // static final double init_TI             = 0.1;         // turbulence intensity, TI = u' / U [unitless]
  // static final double init_Lturb          = 2.5;     // turbulent length scale [m]
  // static final double init_Vturb          = 0.1;         // turbulent velocity scale [m/s]
  // static final double init_Vx             = 2.0;         // initial x-velocity [m/s]    NOTE: do not start from 0 because sometime field functions may divide by 0
  // static final double init_Vy             = 0.001;       // initial y-velocity [m/s]
  // static final double init_Vz             = 0.001;       // initial z-velocity [m/s]
  // static final double inlet_Vx            = 2.0;         // inlet x-velocity [m/s]
  // static final double inlet_Vy            = 0.0;         // inlet y-velocity [m/s]
  // static final double inlet_Vz            = 0.0;         // inlet z-velocity [m/s]
	///////////////////////////////////////////////////////////////////////////////

	
  public void execute() {
    execute0();
  }

  private void execute0() {

	Simulation simulation_0 = 
	  getActiveSimulation();

    // get the user inputs field functions
    // UserFieldFunction userFieldFunction_0 = 
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__xo"));
    // UserFieldFunction userFieldFunction_1 = 
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__yo"));
    // UserFieldFunction userFieldFunction_2 = 
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__zo"));
    // UserFieldFunction userFieldFunction_3 = 
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__length"));
    // UserFieldFunction userFieldFunction_4 = 
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__width"));
    // UserFieldFunction userFieldFunction_5 = 
    //   ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__depth"));




	///////////////////////////////////////////////////////////////////////////////
	// create the Physics Continuum and change any default settings 
	//
    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    PhysicsContinuum physicsContinuum_0 = 
      simulation_0.getContinuumManager().createContinuum(PhysicsContinuum.class);

    // Region region_0 = 
    //   simulation_0.getRegionManager().getRegion("Region");

    physicsContinuum_0.enable(ThreeDimensionalModel.class);

    physicsContinuum_0.enable(SteadyModel.class);

    physicsContinuum_0.enable(SingleComponentLiquidModel.class);

    physicsContinuum_0.enable(SegregatedFlowModel.class);

    physicsContinuum_0.enable(ConstantDensityModel.class);

    physicsContinuum_0.enable(TurbulentModel.class);

    physicsContinuum_0.enable(RansTurbulenceModel.class);

    physicsContinuum_0.enable(KOmegaTurbulence.class);

    physicsContinuum_0.enable(SstKwTurbModel.class);

    physicsContinuum_0.enable(KwAllYplusWallTreatment.class);

    physicsContinuum_0.enable(VirtualDiskModel.class);



    ///////////////////////////////////////////////////////////////////////////////
	 // Changes to Continuum settings
	//
    SegregatedFlowModel segregatedFlowModel_0 = 
      physicsContinuum_0.getModelManager().getModel(SegregatedFlowModel.class);

    segregatedFlowModel_0.getDeltaVDissipationOption().setSelected(DeltaVDissipationOption.Type.ON);



    ///////////////////////////////////////////////////////////////////////////////
	 // Set Fluid Properties
	// //
 //    SingleComponentLiquidModel singleComponentLiquidModel_0 = 
 //      physicsContinuum_0.getModelManager().getModel(SingleComponentLiquidModel.class);

 //    Liquid liquid_0 = 
 //      ((Liquid) singleComponentLiquidModel_0.getMaterial());

 //    ConstantMaterialPropertyMethod constantMaterialPropertyMethod_0 = 
 //      ((ConstantMaterialPropertyMethod) liquid_0.getMaterialProperties().getMaterialProperty(ConstantDensityProperty.class).getMethod());

 //    constantMaterialPropertyMethod_0.getQuantity().setValue(density);

	// ConstantMaterialPropertyMethod constantMaterialPropertyMethod_1 = 
 //      ((ConstantMaterialPropertyMethod) liquid_0.getMaterialProperties().getMaterialProperty(DynamicViscosityProperty.class).getMethod());

 //    constantMaterialPropertyMethod_1.getQuantity().setValue(dynamic_viscosity);


    SingleComponentLiquidModel singleComponentLiquidModel_0 = 
      physicsContinuum_0.getModelManager().getModel(SingleComponentLiquidModel.class);

    Liquid liquid_0 = 
      ((Liquid) singleComponentLiquidModel_0.getMaterial());

    ConstantMaterialPropertyMethod constantMaterialPropertyMethod_0 = 
      ((ConstantMaterialPropertyMethod) liquid_0.getMaterialProperties().getMaterialProperty(ConstantDensityProperty.class).getMethod());

    constantMaterialPropertyMethod_0.getQuantity().setDefinition("${__density}");

    ConstantMaterialPropertyMethod constantMaterialPropertyMethod_1 = 
      ((ConstantMaterialPropertyMethod) liquid_0.getMaterialProperties().getMaterialProperty(DynamicViscosityProperty.class).getMethod());

    constantMaterialPropertyMethod_1.getQuantity().setDefinition("${__dynamic_viscosity}");



 //    ///////////////////////////////////////////////////////////////////////////////
	//  // Set Initial Conditions
	// //   

 //    physicsContinuum_0.getInitialConditions().get(KwTurbSpecOption.class).setSelected(KwTurbSpecOption.Type.INTENSITY_LENGTH_SCALE);

 //    TurbulenceIntensityProfile turbulenceIntensityProfile_0 = 
 //      physicsContinuum_0.getInitialConditions().get(TurbulenceIntensityProfile.class);

 //    turbulenceIntensityProfile_0.setMethod(ConstantScalarProfileMethod.class);

 //    turbulenceIntensityProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_TI);

 //    TurbulentLengthScaleProfile turbulentLengthScaleProfile_0 = 
 //      physicsContinuum_0.getInitialConditions().get(TurbulentLengthScaleProfile.class);

 //    turbulentLengthScaleProfile_0.setMethod(ConstantScalarProfileMethod.class);

 //    turbulentLengthScaleProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_Lturb);

 //    TurbulentVelocityScaleProfile turbulentVelocityScaleProfile_0 = 
 //      physicsContinuum_0.getInitialConditions().get(TurbulentVelocityScaleProfile.class);

 //    turbulentVelocityScaleProfile_0.setMethod(FunctionScalarProfileMethod.class);

 //    turbulentVelocityScaleProfile_0.setMethod(ConstantScalarProfileMethod.class);

 //    turbulentVelocityScaleProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_Vturb);

 //    VelocityProfile velocityProfile_0 = 
 //      physicsContinuum_0.getInitialConditions().get(VelocityProfile.class);

 //    velocityProfile_0.setMethod(ConstantVectorProfileMethod.class);

 //    velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(init_Vx, init_Vy, init_Vz);







    

 //    Boundary boundary_1 = 
 //      region_0.getBoundaryManager().getBoundary("Block.Inlet");

 //    boundary_1.getConditions().get(KwTurbSpecOption.class).setSelected(KwTurbSpecOption.Type.INTENSITY_LENGTH_SCALE);



 //    TurbulenceIntensityProfile turbulenceIntensityProfile_1 = 
 //      boundary_1.getValues().get(TurbulenceIntensityProfile.class);

 //    turbulenceIntensityProfile_1.setMethod(FunctionScalarProfileMethod.class);

 //    turbulenceIntensityProfile_1.setMethod(ConstantScalarProfileMethod.class);

 //    turbulenceIntensityProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_TI);



 //    TurbulentLengthScaleProfile turbulentLengthScaleProfile_1 = 
 //      boundary_1.getValues().get(TurbulentLengthScaleProfile.class);

 //    turbulentLengthScaleProfile_1.setMethod(FunctionScalarProfileMethod.class);

 //    turbulentLengthScaleProfile_1.setMethod(ConstantScalarProfileMethod.class);

 //    turbulentLengthScaleProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_Lturb);

 //    VelocityProfile velocityProfile_1 = 
 //      boundary_1.getValues().get(VelocityProfile.class);

 //    velocityProfile_1.setMethod(ConstantVectorProfileMethod.class);

 //    velocityProfile_1.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(inlet_Vx, inlet_Vy, inlet_Vz);






 //    Boundary boundary_3 = 
 //      region_0.getBoundaryManager().getBoundary("Outlet");

 //    boundary_3.getConditions().get(KwTurbSpecOption.class).setSelected(KwTurbSpecOption.Type.INTENSITY_LENGTH_SCALE);

 //    TurbulenceIntensityProfile turbulenceIntensityProfile_2 = 
 //      boundary_3.getValues().get(TurbulenceIntensityProfile.class);

 //    turbulenceIntensityProfile_2.setMethod(FunctionScalarProfileMethod.class);

 //    turbulenceIntensityProfile_2.setMethod(ConstantScalarProfileMethod.class);

 //    turbulenceIntensityProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_TI);

 //    TurbulentLengthScaleProfile turbulentLengthScaleProfile_2 = 
 //      boundary_3.getValues().get(TurbulentLengthScaleProfile.class);

 //    turbulentLengthScaleProfile_2.setMethod(FunctionScalarProfileMethod.class);

 //    turbulentLengthScaleProfile_2.setMethod(ConstantScalarProfileMethod.class);

 //    turbulentLengthScaleProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_Lturb);










 //    physicsContinuum_0.getInitialConditions().get(KwTurbSpecOption.class).setSelected(KwTurbSpecOption.Type.INTENSITY_LENGTH_SCALE);

	// TurbulenceIntensityProfile turbulenceIntensityProfile_0 = 
 //      physicsContinuum_0.getInitialConditions().get(TurbulenceIntensityProfile.class);

 //    turbulenceIntensityProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_TI);

 //    TurbulentLengthScaleProfile turbulentLengthScaleProfile_0 = 
 //      physicsContinuum_0.getInitialConditions().get(TurbulentLengthScaleProfile.class);

 //    turbulentLengthScaleProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_Lturb);

 //    TurbulentVelocityScaleProfile turbulentVelocityScaleProfile_0 = 
 //      physicsContinuum_0.getInitialConditions().get(TurbulentVelocityScaleProfile.class);

 //    turbulentVelocityScaleProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_Vturb);

 //    VelocityProfile velocityProfile_0 = 
 //      physicsContinuum_0.getInitialConditions().get(VelocityProfile.class);

 //    velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(init_Vx, init_Vy, init_Vz);


     // setup the AutoSave
    AutoSave autoSave_0 = 
      simulation_0.getSimulationIterator().getAutoSave();

    StarUpdate starUpdate_0 = 
      autoSave_0.getStarUpdate();

    starUpdate_0.setEnabled(true);

    IterationUpdateFrequency iterationUpdateFrequency_0 = 
      starUpdate_0.getIterationUpdateFrequency();

    iterationUpdateFrequency_0.setIterations(50);


  } // end execute0()
} // end public class
