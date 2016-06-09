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

public class solver_UpdateConvergenceCriteria extends StarMacro {

///////////////////////////////////////////////////////////////////////////////
// USER INPUTS (ACTUALLLY THIS IS EDITED BY A MATLAB SCRIPT "updateConvergenceCriteria.m", change the values in Matlab instead, do not bother editing these values)
//
// boolean             firstTime 	     	    = false;
static final int    iter_max                = 1000;
static final double limit_continuity        = 1e-6;
// maybe later can include some kinda of criteria for the Adaptive-Mesh-Refinement


// dunno if I actuall want to update convergence criteria on the fly ... meaning for each outer loop iteration between mooring and CFD
    // maybe better way is to set an asymptote criteria upon "total power output" within +/- value, or "turbine x-y-z position" within +/- values
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("updateConvergenceCriteria.java"))).play();
    
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
