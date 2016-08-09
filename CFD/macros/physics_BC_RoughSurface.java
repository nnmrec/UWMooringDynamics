// STAR-CCM+ macro: physics_BC_RoughSurface.java
// Written by STAR-CCM+ 11.04.010
package macro;

import java.util.*;

import star.turbulence.*;
import star.common.*;
import star.base.neo.*;
import star.flow.*;

public class physics_BC_RoughSurface extends StarMacro {

  // Usually z0 is larger than the median grain size; 
  // a first approximation might be z0 =~ 2 * d_90, where d_90 grain size at 
  // which 90% of the sample is finer. Hence, the highest value of bottom friction coefficient
  // under consideration would imply d_90 ~ 20 cm = 0.02 m
  // static final double z0          = 0.0001;    // for the Bamfield flume (smooth)
  // static final double z0          = 0.0136;    // seabed surface roughness height [m], UW-NNMREC
  // static final double z0          = 0.0025;    // seabed surface roughness height [m], OpenTidalFarm
  // static final double z0          = 0.043;    // seabed surface roughness height [m], as estimated by Neary in riverine environment http://www.pnnl.gov/main/publications/external/technical_reports/PNNL-20463.pdf, and further discussion here: http://www.homepages.ed.ac.uk/shs/Tidal%20Stream/Baston%20and%20Harris.pdf
  // static final double z0          = 0.04;    // used by Churchfield et al. for SOWFA tidal turbines
  

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    Region region_0 = 
      simulation_0.getRegionManager().getRegion("Region");






    UserFieldFunction userFieldFunction_00 = 
        ((UserFieldFunction) simulation_0.getFieldFunctionManager().getFunction("__z0"));







    Boundary boundary_0 = 
      region_0.getBoundaryManager().getBoundary("Block.Seabed");

    boundary_0.getConditions().get(WallShearStressOption.class).setSelected(WallShearStressOption.Type.NO_SLIP);

    boundary_0.getConditions().get(WallSurfaceOption.class).setSelected(WallSurfaceOption.Type.ROUGH);

    BlendedWallFunctionCondition blendedWallFunctionCondition_0 = 
      boundary_0.getValues().get(BlendedWallFunctionCondition.class);

    blendedWallFunctionCondition_0.setE(9.0);

    blendedWallFunctionCondition_0.setKappa(0.42);

    RoughnessHeightProfile roughnessHeightProfile_0 = 
      boundary_0.getValues().get(RoughnessHeightProfile.class);

    // roughnessHeightProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(z0);
      roughnessHeightProfile_0.getMethod(ConstantScalarProfileMethod.class).getQuantity().setValue(Double.parseDouble(userFieldFunction_00.getDefinition()));

    WallRoughnessCondition wallRoughnessCondition_0 = 
      boundary_0.getValues().get(WallRoughnessCondition.class);

    wallRoughnessCondition_0.setB(0.0);

    wallRoughnessCondition_0.setC(0.253);

    wallRoughnessCondition_0.setRplusSmooth(2.250);

    wallRoughnessCondition_0.setRplusRough(90.0);

    wallRoughnessCondition_0.setRoughnessLimiter(false);

    wallRoughnessCondition_0.setRoughnessLimiter(true);
  }
}
