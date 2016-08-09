// STAR-CCM+ macro: _main.java
// tested on STAR-CCM+ v10 and v11
// 
// by Danny Clay Sale (dsale@uw.edu)
// 
// license: ?
// 


// import all the classes we need
package macro;
import java.util.*;
import star.common.*;

///////////////////////////////////////////////////////////////////////////////
// This is the MAIN driver, which calls all the other macros
///////////////////////////////////////////////////////////////////////////////
public class _main extends StarMacro {


  public void execute() {
    execute0();
  }

  private void execute0() {

    // Read in all the "user inputs"
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("init_UserInputVariables.java"))).play();


    ///////////////////////////////////////////////////////////////////////////////
    // Physics and Meshing setup
    ///////////////////////////////////////////////////////////////////////////////
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("physics_SST_KOmega.java"))).play();

    // new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("parts_RegionsBoundaryConditions.java"))).play();
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("parts_FluidRegion_BC.java"))).play();
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("physics_BC_InflowUniform.java"))).play();
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("physics_BC_RoughSurface.java"))).play();


    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("mesh_Background_Polyhedral.java"))).play();

    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("mesh_MeshAll.java"))).play();

    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("physics_Create_VirtualDisks.java"))).play();

    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("parts_SectionPlanes.java"))).play();
    
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("parts_SectionPlanesTurbines.java"))).play();

    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("parts_Create_PointProbes.java"))).play();

    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("mesh_MeshAll.java"))).play();
   
    

    // // create any other derived parts used for scenes
    // // new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("parts_Create_LineProbes.java"))).play();

    // create some scenes
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_Mesh.java"))).play();
    
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_LineProbes.java"))).play();
    
    // customize boundary conditions for ABL
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("fieldFunction_ABL_inlet.java"))).play();
    
    

    ///////////////////////////////////////////////////////////////////////////////
    // Run the Solver
    ///////////////////////////////////////////////////////////////////////////////
    
    
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("solver_OptimalSettings.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("solver_UpdateConvergenceCriteria.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("solver_InitSolution.java"))).play();

    // create any useful field functions
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("fieldFunction_TurbulenceIntensity.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_Velocity.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_TurbulenceIntensityLocal.java"))).play();
    
    // reads the most recent inflow boundary conditions from the UWMooring code (used for velocity ramping)
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("update_RegionsBoundaryConditions.java"))).play();
    
    // the initial run (or final if AMR is not enabled)
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("solver_Run.java"))).play();


    // ///////////////////////////////////////////////////////////////////////////////
    // // Adaptive Mesh Refinement
    // ///////////////////////////////////////////////////////////////////////////////
    // // create any new field functions, parts, scenes, etc.  used for AMR
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("AMR_Initialize.java"))).play(); 

    // // now run the AMR, loop through all the refinement levels (this loops meshing and solver)
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("AMR_Run.java"))).play();

    ///////////////////////////////////////////////////////////////////////////////
    // POST-PROCESSING
    ///////////////////////////////////////////////////////////////////////////////
    // export important data here    
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("export_PointProbes.java"))).play();
   
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("export_VirtualDisks.java"))).play();


    ///////////////////////////////////////////////////////////////////////////////
    // POST-POST-PROCESSING
    ///////////////////////////////////////////////////////////////////////////////
    // after the final run, all scenes should be finally updated, and hardcopy saves
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_SaveHardcopies.java"))).play();


        

    

  } // end execute0()
} // end public class
