// STAR-CCM+ macro: _main.java
// tested on STAR-CCM+ v10 and v11
// 
// by Danny Clay Sale (dsale@uw.edu)
// 
// license: ?
// 

// General idea of macros:
// 1) create the Physics Continuum (macro 1)
// 		* settings for Turbulence Models
// 		* settings for Solvers
// 
// 2) create the Geometry Parts for Meshing (macro 2: macroCreateMeshAndVirtualDisks.java)
// 		* assign "Block" Part to Regions
//   	* apply Boundary Conditions to Regions
// 			* note: velocity inlet should load from File Table, or create Field Function
// 		* create the initial "background mesh" within the "Block" (note: 3 options for refining around turbines ... Overset Mesh, Shape Part refinment, or Adaptive Mesh Refinement)
// 
// 3) create the Virtual Disks (macro 2 or 3)
// 		* read Virtual Disk XYZ coordinates from CSV file (at initial/updated conditions from "mooring model" code)
// 		* read Virtual Disk initial/updated settings from CSV file (like: dimensions, positions and operating conditions, file tables, etc.)
// 		* loop to create/update the Virtual Disks
// 		* loop to create Shape Parts for Volumetric Refinement at Virtual Disk locations (can set the cell sizes now in a loop, or use AMR method instead) 
// 
// 4) Alternate: use Adaptive Mesh Refinement (macro 4: macroRunRefineRunRepeat.java)
// 		* create the Field Function for "threshold variable", like TKE, TI, velocity gradient, or other ...
// 		* create the Internal XYZ Table for this Field Function
// 			* run ~100 iterations
// 			* compute the new Field Function values and extract to Internal XYZ Table
// 			* apply the Automated Mesh Operation to provide cell size by "table method"
// 			* run ~100 iterations
// 			* repeat a few times and then run to convergence
// 
// 5) Post Processing: extract solution, create figures and movies (macro 4: macroPostProcessing.java)
// 		* create/update derived parts to post-process/visualize solution (like slice planes, point probes)
// 		* extract the Solution Variables at the Point Probes defined in the "mooring model" code.  Write to updated CSV file from step 3.
//		* update Virtual Disk operating conditions (like change rotor speed to maintain constant tip-speed-ratio)
// 
// 6) repeat from step 3
// 
// 
// Things that should be "User Inputs"
// Physics Continuum options (if any?)
// "Block" dimensions
// Regions Boundary Conditions values (like flow speed, turbulence intensity, length scales, inlet profile)
// Virtual Disk settings (from File Table and "mooring model" table)
// Point Probe locations (from File Table for the "mooring model")
// Mesh Refinement options (like threshold values, cell sizes)
// Run Solver options (like iterations, convergence criteria) 
// 
// 
// OTHER IDEAS:
// 		* if using Overset Mesh ... try running case for single turbine with AMR on the Overset Mesh (not the background mesh) ... and then save this Overset Mesh and load
//        into the simulation with many turbines ... put this "Adaptively Refined" Overset Mesh onto each turbine ... this is alterate method to performing step 4.
//      * define an OPTIONS structure that contions all USER INPUTS, read or define OPTIONS in this file ... then pass OPTIONS as input argument to each macro
// 		* try to separate macros into categories 
// 			1) safe to repeat: only changes/updates options and does not create any parts/interfaces/whatever that could possibly create duplicate entities
// 			2) unsafe to repeat: creates/adds new parts, etc. (probably do not want to repeat running these)
// 			3) 						
// 


// import all the classes we need
package macro;
import java.util.*;
import star.common.*;

///////////////////////////////////////////////////////////////////////////////
// This is the MAIN driver, which calls all the other macros
///////////////////////////////////////////////////////////////////////////////
public class _main extends StarMacro {


  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS

  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {


    ///////////////////////////////////////////////////////////////////////////////
    // Physics and Meshing setup
    ///////////////////////////////////////////////////////////////////////////////
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("physics_SST_KOmega.java"))).play();

    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("parts_RegionsBoundaryConditions.java"))).play();
    
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("mesh_Background_Polyhedral.java"))).play();

    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("mesh_MeshAll.java"))).play();

    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("physics_Create_VirtualDisks.java"))).play();

    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("parts_Create_PointProbes.java"))).play();

    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("mesh_MeshAll.java"))).play();
   
    // create any useful field functions
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("fieldFunction_TurbulenceIntensity.java"))).play();

   // create any other derived parts used for scenes
    new StarScript(getActiveSimulation(),   new java.io.File(resolvePath("parts_Create_LineProbes.java"))).play();

    // create some scenes
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_Mesh_Velocity.java"))).play();
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("scene_LineProbes.java"))).play();
    
    // customize boundary conditions for ABL
    // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("fieldFunction_ABL_inlet.java"))).play();
    
    

    ///////////////////////////////////////////////////////////////////////////////
    // Run the Solver
    ///////////////////////////////////////////////////////////////////////////////
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("solver_UpdateConvergenceCriteria.java"))).play();

    // the initial run (or final if AMR is not enabled)
    new StarScript(getActiveSimulation(), new java.io.File(resolvePath("solver_Run.java"))).play();




    // // ///////////////////////////////////////////////////////////////////////////////
    // // // Adaptive Mesh Refinement
    // // ///////////////////////////////////////////////////////////////////////////////
    // // // create any new field functions, parts, scenes, etc.  used for AMR
    // // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("AMR_Initialize.java"))).play(); 

    // // // now run the AMR, loop through all the refinement levels (this loops meshing and solver)
    // // new StarScript(getActiveSimulation(), new java.io.File(resolvePath("AMR_Run.java"))).play();



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
