// STAR-CCM+ macro: physics_BC_InflowUniform.java
// Written by STAR-CCM+ 11.02.010
package macro;

import java.util.*;

import star.turbulence.*;
import star.kwturb.*;
import star.common.*;
import star.flow.*;

public class physics_BC_InflowUniform extends StarMacro {

  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS
  //
  // Flume Case
  // static final double density             = 997;         // fluid density [kg/m^2]
  // static final double dynamic_viscosity   = 0.00108;     // fluid dynamic viscosity [Pa-s]
  // static final double init_TI             = 0.1;         // turbulence intensity, TI = u' / U [unitless]
  // static final double init_Lturb          = 0.05625;     // turbulent length scale [m]
  // static final double init_Vturb          = 0.1;         // turbulent velocity scale [m/s]
  // static final double init_Vx             = 0.9;         // initial x-velocity [m/s]    NOTE: do not start from 0 because sometime field functions may divide by 0
  // static final double init_Vy             = 0.001;       // initial y-velocity [m/s]
  // static final double init_Vz             = 0.001;       // initial z-velocity [m/s]
  // static final double inlet_Vx            = 0.9;         // inlet x-velocity [m/s]
  // static final double inlet_Vy            = 0.0;         // inlet y-velocity [m/s]
  // static final double inlet_Vz            = 0.0;         // inlet z-velocity [m/s]


  // Tidal Channel Case
  // static final double density             = 1025;         // fluid density [kg/m^2]
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

    PhysicsContinuum physicsContinuum_0 = 
      ((PhysicsContinuum) simulation_0.getContinuumManager().getContinuum("Physics 1"));


    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Region");





      // get the user defined field functions
      UserFieldFunction userFieldFunction_0 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__init_TI"));
      UserFieldFunction userFieldFunction_1 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__init_Lturb"));
      UserFieldFunction userFieldFunction_2 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__init_Vturb"));
      UserFieldFunction userFieldFunction_3 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__init_Vx"));
      UserFieldFunction userFieldFunction_4 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__init_Vy"));
      UserFieldFunction userFieldFunction_5 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__init_Vz"));
      UserFieldFunction userFieldFunction_6 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__inlet_Vx"));
      UserFieldFunction userFieldFunction_7 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__inlet_Vy"));
      UserFieldFunction userFieldFunction_8 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__inlet_Vz"));




      ///////////////////////////////////////////////////////////////////////////////
     // Set Initial Conditions
    //   

    physicsContinuum_0.getInitialConditions().get(KwTurbSpecOption.class).setSelected(KwTurbSpecOption.Type.INTENSITY_LENGTH_SCALE);

    TurbulenceIntensityProfile turbulenceIntensityProfile_0 = 
      physicsContinuum_0.getInitialConditions().get(TurbulenceIntensityProfile.class);

    turbulenceIntensityProfile_0.setMethod(ConstantScalarProfileMethod.class);

    // turbulenceIntensityProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_TI);
    turbulenceIntensityProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(Double.parseDouble(userFieldFunction_0.getDefinition()));
    

    TurbulentLengthScaleProfile turbulentLengthScaleProfile_0 = 
      physicsContinuum_0.getInitialConditions().get(TurbulentLengthScaleProfile.class);

    turbulentLengthScaleProfile_0.setMethod(ConstantScalarProfileMethod.class);

    turbulentLengthScaleProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(Double.parseDouble(userFieldFunction_1.getDefinition()));

    TurbulentVelocityScaleProfile turbulentVelocityScaleProfile_0 = 
      physicsContinuum_0.getInitialConditions().get(TurbulentVelocityScaleProfile.class);

    turbulentVelocityScaleProfile_0.setMethod(FunctionScalarProfileMethod.class);

    turbulentVelocityScaleProfile_0.setMethod(ConstantScalarProfileMethod.class);

    // turbulentVelocityScaleProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_Vturb);
    turbulentVelocityScaleProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(Double.parseDouble(userFieldFunction_2.getDefinition()));

    VelocityProfile velocityProfile_0 = 
      physicsContinuum_0.getInitialConditions().get(VelocityProfile.class);

    velocityProfile_0.setMethod(ConstantVectorProfileMethod.class);

    // velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(init_Vx, init_Vy, init_Vz);
    velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(Double.parseDouble(userFieldFunction_3.getDefinition()), 
                                                                                               Double.parseDouble(userFieldFunction_4.getDefinition()), 
                                                                                               Double.parseDouble(userFieldFunction_5.getDefinition()));







    

    Boundary boundary_1 = 
      // region_0.getBoundaryManager().getBoundary("Inlet");
    region_0.getBoundaryManager().getBoundary("Block.Inlet");

    boundary_1.getConditions().get(KwTurbSpecOption.class).setSelected(KwTurbSpecOption.Type.INTENSITY_LENGTH_SCALE);
    boundary_1.getConditions().get(InletVelocityOption.class).setSelected(InletVelocityOption.Type.COMPONENTS);



    TurbulenceIntensityProfile turbulenceIntensityProfile_1 = 
      boundary_1.getValues().get(TurbulenceIntensityProfile.class);

    turbulenceIntensityProfile_1.setMethod(FunctionScalarProfileMethod.class);

    turbulenceIntensityProfile_1.setMethod(ConstantScalarProfileMethod.class);

    // turbulenceIntensityProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_TI);
    turbulenceIntensityProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(Double.parseDouble(userFieldFunction_0.getDefinition()));
    



    TurbulentLengthScaleProfile turbulentLengthScaleProfile_1 = 
      boundary_1.getValues().get(TurbulentLengthScaleProfile.class);

    turbulentLengthScaleProfile_1.setMethod(FunctionScalarProfileMethod.class);

    turbulentLengthScaleProfile_1.setMethod(ConstantScalarProfileMethod.class);

    // turbulentLengthScaleProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_Lturb);
    turbulentLengthScaleProfile_1.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(Double.parseDouble(userFieldFunction_1.getDefinition()));

    VelocityProfile velocityProfile_1 = 
      boundary_1.getValues().get(VelocityProfile.class);

    // boundary_1.getConditions().get(InletVelocityOption.class).setSelected(InletVelocityOption.Type.COMPONENTS);

    velocityProfile_1.setMethod(ConstantVectorProfileMethod.class);

    // velocityProfile_1.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(inlet_Vx, inlet_Vy, inlet_Vz);
    velocityProfile_1.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(Double.parseDouble(userFieldFunction_6.getDefinition()), 
                                                                                               Double.parseDouble(userFieldFunction_7.getDefinition()), 
                                                                                               Double.parseDouble(userFieldFunction_8.getDefinition()));

// Boundary boundary_3 = 
//       region_2.getBoundaryManager().getBoundary("Block.Inlet");

//     boundary_3.getConditions().get(InletVelocityOption.class).setSelected(InletVelocityOption.Type.COMPONENTS);

//     VelocityProfile velocityProfile_0 = 
//       boundary_3.getValues().get(VelocityProfile.class);

//     velocityProfile_0.getMethod(ConstantVectorProfileMethod.class).getQuantity().setComponents(0.1, 1.0, 2.0);




    Boundary boundary_3 = 
      region_0.getBoundaryManager().getBoundary("Block.Outlet");

    boundary_3.getConditions().get(KwTurbSpecOption.class).setSelected(KwTurbSpecOption.Type.INTENSITY_LENGTH_SCALE);

    TurbulenceIntensityProfile turbulenceIntensityProfile_2 = 
      boundary_3.getValues().get(TurbulenceIntensityProfile.class);

    turbulenceIntensityProfile_2.setMethod(FunctionScalarProfileMethod.class);

    turbulenceIntensityProfile_2.setMethod(ConstantScalarProfileMethod.class);

    // turbulenceIntensityProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_TI);
    turbulenceIntensityProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(Double.parseDouble(userFieldFunction_0.getDefinition()));

    TurbulentLengthScaleProfile turbulentLengthScaleProfile_2 = 
      boundary_3.getValues().get(TurbulentLengthScaleProfile.class);

    turbulentLengthScaleProfile_2.setMethod(FunctionScalarProfileMethod.class);

    turbulentLengthScaleProfile_2.setMethod(ConstantScalarProfileMethod.class);

    // turbulentLengthScaleProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(init_Lturb);
    turbulentLengthScaleProfile_2.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(Double.parseDouble(userFieldFunction_1.getDefinition()));


  }
}
