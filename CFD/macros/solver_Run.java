// STAR-CCM+ macro: solver_Run.java
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
import star.meshing.*;

public class solver_Run extends StarMacro {

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

    // run the simulation (this will restart from solution of previous known iteration)
    simulation_0.getSimulationIterator().run();

    simulation_0.saveState(getSimulation().getPresentationName() + ".sim");


  } // end execute0()
} // end public class
