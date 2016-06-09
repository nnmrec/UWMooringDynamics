// STAR-CCM+ macro: AMR_UpdateConvergenceCriteria.java
// Written by STAR-CCM+ 10.06.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class AMR_UpdateConvergenceCriteria extends StarMacro {

///////////////////////////////////////////////////////////////////////////////
// USER INPUTS (ACTUALLLY THIS IS EDITED BY A MATLAB SCRIPT "updateConvergenceCriteria.m", change the values in Matlab instead, do not bother editing these values)
//
// boolean             firstTime 	     	    = false;
// static final int    iter_max                = 33;
static final double    iterFactor              = 2.0;
// static final double    continuityFactor        = 1.333;
// static final double limit_continuity        = 1e-3;
// maybe later can include some kinda of criteria for the Adaptive-Mesh-Refinement
///////////////////////////////////////////////////////////////////////////////

public void execute() {
execute0();
}

private void execute0() {

	Simulation simulation_0 = 
	getActiveSimulation();



	StepStoppingCriterion stepStoppingCriterion_0 = 
	((StepStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Maximum Steps"));
	
	int maxIter = simulation_0.getSimulationIterator().getCurrentIteration();

	double itersMax = (double) maxIter;

	double itersNew = itersMax * iterFactor;

	int newIter = (int) itersNew;

	stepStoppingCriterion_0.setMaximumNumberSteps(newIter);


	//  DEBUG
	simulation_0.println("DEBUG: iter_maxd = " + stepStoppingCriterion_0);
	simulation_0.println("DEBUG: old Max Iteration = " + maxIter);
	simulation_0.println("DEBUG: new Max Iteration = " + newIter);







	// try to get value for the current "continuity limit"


	// MonitorIterationStoppingCriterion monitorIterationStoppingCriterion_1 = 
	// ((MonitorIterationStoppingCriterion) simulation_0.getSolverStoppingCriterionManager().getSolverStoppingCriterion("Continuity Criterion"));
	
	// MonitorIterationStoppingCriterionMinLimitType monitorIterationStoppingCriterionMinLimitType_0 = 
	// ((MonitorIterationStoppingCriterionMinLimitType) monitorIterationStoppingCriterion_1.getCriterionType());
	
	// monitorIterationStoppingCriterionMinLimitType_0.getLimit().setValue(limit_continuity);





	// simulation_0.saveState(getSimulation().getPresentationName() + ".sim");

}
}
