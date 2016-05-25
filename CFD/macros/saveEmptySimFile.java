// STAR-CCM+ macro: saveEmptySimFile.java
// Written by STAR-CCM+ 11.02.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;

public class saveEmptySimFile extends StarMacro {

  public void execute() {
    execute0();
    // runs.case.sim
    execute1();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    simulation_0.saveState(resolvePath("../empty_case.sim"));
  }

  private void execute1() {
  }
}
