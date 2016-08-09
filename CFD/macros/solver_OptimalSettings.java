// STAR-CCM+ macro: solver_OptimalSettings.java
// Written by STAR-CCM+ 11.04.010
package macro;

import java.util.*;

import star.kwturb.*;
import star.common.*;
import star.base.neo.*;
import star.segregatedflow.*;

public class solver_OptimalSettings extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PhysicsContinuum physicsContinuum_0 = 
      ((PhysicsContinuum) simulation_0.getContinuumManager().getContinuum("Physics 1"));

    KwAllYplusWallTreatment kwAllYplusWallTreatment_0 = 
      physicsContinuum_0.getModelManager().getModel(KwAllYplusWallTreatment.class);

    kwAllYplusWallTreatment_0.setIterativeUstarOption(true);

    kwAllYplusWallTreatment_0.setIterativeUstarOption(false);

    SegregatedFlowModel segregatedFlowModel_0 = 
      physicsContinuum_0.getModelManager().getModel(SegregatedFlowModel.class);

    segregatedFlowModel_0.getDeltaVDissipationOption().setSelected(DeltaVDissipationOption.Type.OFF);

    segregatedFlowModel_0.getDeltaVDissipationOption().setSelected(DeltaVDissipationOption.Type.ON);

    SegregatedFlowSolver segregatedFlowSolver_0 = 
      ((SegregatedFlowSolver) simulation_0.getSolverManager().getSolver(SegregatedFlowSolver.class));

    segregatedFlowSolver_0.setContinuityInitialization(true);

    ContinuityInitializer continuityInitializer_0 = 
      segregatedFlowSolver_0.getContinuityInitializer();

    continuityInitializer_0.setTolerance(1.0E-4);

    KwTurbSolver kwTurbSolver_0 = 
      ((KwTurbSolver) simulation_0.getSolverManager().getSolver(KwTurbSolver.class));

    kwTurbSolver_0.setTurbulenceInitialization(true);

    KwTurbViscositySolver kwTurbViscositySolver_0 = 
      ((KwTurbViscositySolver) simulation_0.getSolverManager().getSolver(KwTurbViscositySolver.class));

    kwTurbViscositySolver_0.setMaxTvr(1.0E10); // this is recommended for ABL high Reynolds # flows
  }
}
