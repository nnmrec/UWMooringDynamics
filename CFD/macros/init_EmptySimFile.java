// STAR-CCM+ macro: init_EmptySimFile.java
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

public class init_EmptySimFile extends StarMacro {

  public void execute() {
    execute0();
    // runs.case.sim
    // execute1();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.saveState(resolvePath("../empty_case.sim"));
  }

  private void execute1() {
  }
}
