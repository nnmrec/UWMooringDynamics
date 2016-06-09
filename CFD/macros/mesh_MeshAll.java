// STAR-CCM+ macro: mesh_MeshAll.java
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

public class mesh_MeshAll extends StarMacro {

  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS
  //
  // String path0                = "Mezzanine_v0.sim";
  static final int save_iters = 100;     // number of iterations to trigger the auto-save
  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
    // /mnt/data-RAID-1/danny/marine-star/Mezzanine/meshed.Mezzanine.v0.sim
    execute1();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();


    // setup the auto-save
    AutoSave autoSave_0 = 
      simulation_0.getSimulationIterator().getAutoSave();

    autoSave_0.setAutoSaveMesh(true);

    StarUpdate starUpdate_0 = 
      autoSave_0.getStarUpdate();

    starUpdate_0.setEnabled(true);

    IterationUpdateFrequency iterationUpdateFrequency_0 = 
      starUpdate_0.getIterationUpdateFrequency();

    iterationUpdateFrequency_0.setIterations(save_iters);




    // generate the volume mesh 
    AutoMeshOperation autoMeshOperation_1 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh"));

    autoMeshOperation_1.execute();


    // save the meshed simulation
    // simulation_0.saveState(resolvePath(path0));
    simulation_0.saveState(getSimulation().getPresentationName()+".sim");


  } // end execute0()

  private void execute1() {
  } // end execute1()


} // end public class
