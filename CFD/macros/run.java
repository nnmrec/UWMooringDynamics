// STAR-CCM+ macro: run.java
// Written by STAR-CCM+ 10.06.010
// 
// license: ?
// 
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.meshing.*;

public class run extends StarMacro {

  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS
  //

  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();


    // // set the stopping and convergence criteria
    // StepStoppingCriterion stepStoppingCriterion_0 = 
    //   ((StepStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Steps"));

    // stepStoppingCriterion_0.setIsUsed(false);

    // stepStoppingCriterion_0.setIsUsed(true);

    // stepStoppingCriterion_0.setMaximumNumberSteps(iter_max);

    // ResidualMonitor residualMonitor_0 = 
    //   ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Continuity"));

    // MonitorIterationStoppingCriterion monitorIterationStoppingCriterion_0 = 
    //   residualMonitor_0.createIterationStoppingCriterion();

    // MonitorIterationStoppingCriterionMinLimitType monitorIterationStoppingCriterionMinLimitType_0 = 
    //   ((MonitorIterationStoppingCriterionMinLimitType) monitorIterationStoppingCriterion_0.getCriterionType());

    // monitorIterationStoppingCriterionMinLimitType_0.getLimit().setValue(limit_continuity);


    // run the simulation (this will restart from solution of previous known iteration)
    simulation_0.getSimulationIterator().run();

    simulation_0.saveState(getSimulation().getPresentationName() + ".sim");


  } // end execute0()
} // end public class
