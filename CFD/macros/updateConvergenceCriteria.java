// STAR-CCM+ macro: updateConvergenceCriteria.java
// Written by STAR-CCM+ 10.06.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class updateConvergenceCriteria extends StarMacro {

///////////////////////////////////////////////////////////////////////////////
// USER INPUTS (ACTUALLLY THIS IS EDITED BY A MATLAB SCRIPT "updateConvergenceCriteria.m", change the values in Matlab instead, do not bother editing these values)
//
// boolean             firstTime 	     	    = false;
static final int    iter_max                = 100;
static final double limit_continuity        = 0.001;
// maybe later can include some kinda of criteria for the Adaptive-Mesh-Refinement
///////////////////////////////////////////////////////////////////////////////

public void execute() {
execute0();
}

private void execute0() {

	Simulation simulation_0 = 
	getActiveSimulation();


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
