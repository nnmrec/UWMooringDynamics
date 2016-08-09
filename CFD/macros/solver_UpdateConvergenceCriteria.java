// STAR-CCM+ macro: solver_UpdateConvergenceCriteria.java
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
import star.turbulence.*;
import star.kwturb.*;

public class solver_UpdateConvergenceCriteria extends StarMacro {

///////////////////////////////////////////////////////////////////////////////
// USER INPUTS
static final int    iter_max                = 10000;
static final double limit_continuity        = 1e-6;
    
///////////////////////////////////////////////////////////////////////////////

public void execute() {
execute0();
}

private void execute0() {

	Simulation simulation_0 = 
	getActiveSimulation();



    ///////////////////////////////////////////////////////////////////////////////
    // setup some stoping criteria (default values will be fine for now)
    //
    ResidualMonitor residualMonitor_0 = 
      ((ResidualMonitor) simulation_0.getMonitorManager().getMonitor("Continuity"));

    MonitorIterationStoppingCriterion monitorIterationStoppingCriterion_0 = 
      residualMonitor_0.createIterationStoppingCriterion();






	MonitorIterationStoppingCriterion monitorIterationStoppingCriterion_1 = 
	((MonitorIterationStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Continuity Criterion"));
	

	MonitorIterationStoppingCriterionMinLimitType monitorIterationStoppingCriterionMinLimitType_0 = 
	((MonitorIterationStoppingCriterionMinLimitType) monitorIterationStoppingCriterion_1.getCriterionType());
	monitorIterationStoppingCriterionMinLimitType_0.getLimit().setValue(limit_continuity);


	StepStoppingCriterion stepStoppingCriterion_0 = 
	((StepStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Steps"));
	stepStoppingCriterion_0.setMaximumNumberSteps(iter_max);


	simulation_0.saveState(getSimulation().getPresentationName() + ".sim");

}
}
