// STAR-CCM+ macro: physics_Create_VirtualDisks.java
// tested on STAR-CCM+ v10 and v11
// 
// by Danny Clay Sale (dsale@uw.edu)
// 
// license: ?
// 

// 
// ideas: * for help with debgging: split the main or loop into multiple foor loops: make VD, vamke shape parts, refine, reports , monitors, ... 
//        * there might be more inputs to pull from the WT_Perf files into here, like: the combined-case table (U RPM PITCH)
// 
// 
package macro;
import java.util.*;
import star.vdm.*;
import star.common.*;
import star.base.neo.*;
import star.base.report.*;
// import star.trimmer.*;
import star.dualmesher.*;
import star.prismmesher.*;
import star.meshing.*;
import star.vis.*;

// import star.common.*;
// import star.base.neo.*;
// import star.resurfacer.*;
import star.dualmesher.*;
import star.prismmesher.*;
import star.meshing.*;

import java.io.*;
import java.util.logging.*;



public class physics_Create_VirtualDisks extends StarMacro {

	///////////////////////////////////////////////////////////////////////////////
	// USER INPUTS (all these user inputs should be read from a CSV file instead)
	String path0    = "outputs/rotors.csv";

	//TODO  update normal vectors for the virtual disk orientation
  	//      update x-y-z coordinates from the mooring to CFD

	///////////////////////////////////////////////////////////////////////////////

	public void execute() {
		execute0();
	}

	private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    PhysicsContinuum physicsContinuum_0 = 
      ((PhysicsContinuum) simulation_0.getContinuumManager().getContinuum("Physics 1"));

    AutoMeshOperation autoMeshOperation_0 = 
      ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Automated Mesh"));

  	// SimpleBlockPart simpleBlockPart_0 = 
   //    ((SimpleBlockPart) simulation_0.get(SimulationPartManager.class).getPart("Block"));

   //  AutoMeshOperation autoMeshOperation_0 = 
   //    simulation_0.get(MeshOperationManager.class).createAutoMeshOperation(new StringVector(new String[] {}), new NeoObjectVector(new Object[] {simpleBlockPart_0}));

    // autoMeshOperation_1.getMeshers().setMeshersByNames(new StringVector(new String[] {"star.dualmesher.DualAutoMesher", "star.prismmesher.PrismAutoMesher"}));


      //
      int 			nVirtualDisks 	= 0;
      List<String>	textline 		= new ArrayList<String>();


		File f = new File(path0);
        try {

            FileReader	fr   = new FileReader(f);
            Scanner     sc   = new Scanner(fr);
            String      line = "";
            
            Integer nLines = new Integer(0);
            while (sc.hasNextLine()) {
			    // this skips the header line
			    if(nLines == 0) {
			       nLines = nLines + 1;
			       sc.nextLine();
			       continue;
			    }
                nLines = nLines + 1;
                line = sc.nextLine();
                textline.add(line);
            }
            nVirtualDisks = nLines - 1;          

        } catch (FileNotFoundException ex) {
            Logger.getLogger(physics_Create_VirtualDisks.class.getName()).log(Level.SEVERE, null, ex);
        } // end try

// DEBUG
simulation_0.println("nVirtualDisks = " + nVirtualDisks);

      List<String>	names   = new ArrayList<String>();
      // ArrayList<String> names = new ArrayList<String>();
      // String[] name        	= new String[nVirtualDisks];
      String[] table       	= new String[nVirtualDisks];
      double[] rotor_rpm	= new double[nVirtualDisks];
      double[] x 			= new double[nVirtualDisks];
      double[] y 			= new double[nVirtualDisks];
      double[] z 			= new double[nVirtualDisks];
      double[] nx 			= new double[nVirtualDisks];
      double[] ny 			= new double[nVirtualDisks];
      double[] nz 			= new double[nVirtualDisks];
      double[] rotor_radius	= new double[nVirtualDisks];
      double[] hub_radius	= new double[nVirtualDisks];
      double[] rotor_thick	= new double[nVirtualDisks];
      for (int i = 0; i < nVirtualDisks; i++) {
      	
      	String name = textline.get(i).split(",")[0];
        names.add(name);
//DEBUG
simulation_0.println("name = " + name); 
simulation_0.println("names(i) = " + names.get(i));        
      	// name[i]			= textline.get(i).split(",")[0];
      	table[i]		= textline.get(i).split(",")[1];
      	rotor_rpm[i] 	= Double.parseDouble(textline.get(i).split(",")[2]);
      	x[i]			= Double.parseDouble(textline.get(i).split(",")[3]);
      	y[i]			= Double.parseDouble(textline.get(i).split(",")[4]);
      	z[i]			= Double.parseDouble(textline.get(i).split(",")[5]);
      	nx[i]			= Double.parseDouble(textline.get(i).split(",")[6]);
      	ny[i]			= Double.parseDouble(textline.get(i).split(",")[7]);
      	nz[i]           = Double.parseDouble(textline.get(i).split(",")[8]);
      	rotor_radius[i] = Double.parseDouble(textline.get(i).split(",")[9]);
      	hub_radius[i]   = Double.parseDouble(textline.get(i).split(",")[10]);
      	rotor_thick[i]  = Double.parseDouble(textline.get(i).split(",")[11]);
      }

      String[] name_VirtualDiskMarker 				= new String[nVirtualDisks];
      String[] name_VirtualDiskInflowPlaneMarker 	= new String[nVirtualDisks];
      for (int j = 0; j < nVirtualDisks; j++) {
      	// I think these are not connected to the "name" ... they will always be named in order of creation
      	name_VirtualDiskMarker[j] = "VirtualDiskMarker" + (j+1);
		name_VirtualDiskInflowPlaneMarker[j] = "VirtualDiskInflowPlaneMarker" + (j+1);
      }
			
















	// create a new virtual disk
	for (int i = 0; i < nVirtualDisks; i++) {
		
	    VirtualDiskModel virtualDiskModel_0 = 
	      physicsContinuum_0.getModelManager().getModel(VirtualDiskModel.class);

	    

	    ///////////////////////////////
		// Body force propeller method 
			// VirtualDisk virtualDisk_0 = 
		 //      virtualDiskModel_0.getVirtualDiskManager().createDisk(names.get(i));

	  //     	virtualDisk_0.setDisplaySourceTerm(true);

		 //    virtualDisk_0.setActiveMethod(BodyForcePropellerMethod.class);  

		 //    virtualDisk_0.getComponentsManager().get(PropellerCurve.class).setActiveMethod(PropellerCurveTableMethod.class);

	  //   	PropellerCurveTableMethod propellerCurveTableMethod_0 = 
	  //         ((PropellerCurveTableMethod) virtualDisk_0.getComponentsManager().get(PropellerCurve.class).getActiveMethod());
		  
		 //    FileTable fileTable_0 = 
	  //         (FileTable) simulation_0.getTableManager().createFromFile(resolvePath("../inputs/tables/" + table[0] + ".csv"));
	     
			// propellerCurveTableMethod_0.setTable(fileTable_0);

		 //    propellerCurveTableMethod_0.setAdvanceRatio("AdvanceRatio");

		 //    propellerCurveTableMethod_0.setThrustCoeff("Thrust_Kt");

		 //    propellerCurveTableMethod_0.setMomentCoeff("Torque_Kq");

		 //    propellerCurveTableMethod_0.setEfficiency("Efficiency");

		 //    SimpleDiskGeometry simpleDiskGeometry_0 = 
		 //      virtualDisk_0.getComponentsManager().get(SimpleDiskGeometry.class);

		 //    simpleDiskGeometry_0.getDiskOuterRadius().setValue(rotor_radius[i]);

		 //    simpleDiskGeometry_0.getDiskInnerRadius().setValue(hub_radius[i]);

		 //    simpleDiskGeometry_0.getDiskThickness().setValue(rotor_thick[i]);

		 //    Coordinate coordinate_0 = 
		 //      simpleDiskGeometry_0.getDiskOrigin();

		 //    Units units_1 = 
		 //      ((Units) simulation_0.getUnitsManager().getObject("m"));

		 //    simulation_0.println("DEBUG: x = " + x[i]);
		 //    simulation_0.println("DEBUG: y = " + y[i]);
		 //    simulation_0.println("DEBUG: z = " + z[i]);
		 //    coordinate_0.setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {x[i], y[i], z[i]}));

		 //    ((NormalAndCoordinateSystem) simpleDiskGeometry_0.getOrientationSpecification()).getDiskNormal().setComponents(nx[i], ny[i], nz[i]);

		 //    PropellerInflowVelocityPlane propellerInflowVelocityPlane_2 = 
		 //      virtualDisk_0.getComponentsManager().get(PropellerInflowVelocityPlane.class);

		 //    propellerInflowVelocityPlane_2.getRadius().setValue(rotor_radius[i]);

		 //    propellerInflowVelocityPlane_2.getOffset().setValue(-1*4*rotor_radius[i]);

		 //    VdmRotationRateInputValue vdmRotationRateInputValue_2 = 
		 //      virtualDisk_0.getComponentsManager().get(VdmRotationRateInputValue.class);

		 //    Units units_rpm0 = 
		 //      ((Units) simulation_0.getUnitsManager().getObject("rpm"));

		 //    vdmRotationRateInputValue_2.getRotationRate().setUnits(units_rpm0);

		 //    vdmRotationRateInputValue_2.getRotationRate().setValue(rotor_rpm[i]);


		 //    virtualDisk_0.getComponentsManager().get(PropellerHandednessOption.class).setSelected(PropellerHandednessOption.Type.LEFT_HANDED);
		 //    // virtualDisk_0.getComponentsManager().get(PropellerHandednessOption.class).setSelected(PropellerHandednessOption.Type.RIGHT_HANDED);

		 //    // virtualDisk_0.getComponentsManager().get(PropellerOperationPointInputOption.class).setSelected(PropellerOperationPointInputOption.Type.THRUST_SPECIFIED);
		 //    // virtualDisk_0.getComponentsManager().get(PropellerOperationPointInputOption.class).setSelected(PropellerOperationPointInputOption.Type.TORQUE_SPECIFIED);
		 //    virtualDisk_0.getComponentsManager().get(PropellerOperationPointInputOption.class).setSelected(PropellerOperationPointInputOption.Type.ROTATION_RATE_SPECIFIED);




  

	    ///////////////////////////////
		// 1D-Momentum method   
		    VirtualDisk virtualDisk_0 = 
		      virtualDiskModel_0.getVirtualDiskManager().createDisk(names.get(i));

	      	virtualDisk_0.setDisplaySourceTerm(true);

		    virtualDisk_0.setActiveMethod(OneDMomentumMethod.class);

		    virtualDisk_0.getComponentsManager().get(PowerCurve.class).setActiveMethod(PowerCurveTableMethod.class);

		    PowerCurveTableMethod powerCurveTableMethod_0 = 
		      ((PowerCurveTableMethod) virtualDisk_0.getComponentsManager().get(PowerCurve.class).getActiveMethod());

		    FileTable fileTable_0 = 
	          (FileTable) simulation_0.getTableManager().createFromFile(resolvePath("../inputs/tables/" + table[0] + ".csv"));
	   
		    powerCurveTableMethod_0.setTable(fileTable_0);

		    powerCurveTableMethod_0.setWindSpeed("WindSpeed_m/s");

		    powerCurveTableMethod_0.setPower("Power_Watts");

		    powerCurveTableMethod_0.setThrustCoeff("ThrustCoefficient");

		    SimpleDiskGeometry simpleDiskGeometry_0 = 
		      virtualDisk_0.getComponentsManager().get(SimpleDiskGeometry.class);

		    simpleDiskGeometry_0.getDiskOuterRadius().setValue(rotor_radius[i]);

		    simpleDiskGeometry_0.getDiskInnerRadius().setValue(hub_radius[i]);

		    simpleDiskGeometry_0.getDiskThickness().setValue(rotor_thick[i]);

		    Coordinate coordinate_0 = 
		      simpleDiskGeometry_0.getDiskOrigin();

		    Units units_1 = 
		      ((Units) simulation_0.getUnitsManager().getObject("m"));

		    simulation_0.println("DEBUG: x = " + x[i]);
		    simulation_0.println("DEBUG: y = " + y[i]);
		    simulation_0.println("DEBUG: z = " + z[i]);
		    coordinate_0.setCoordinate(units_1, units_1, units_1, new DoubleVector(new double[] {x[i], y[i], z[i]}));

		    ((NormalAndCoordinateSystem) simpleDiskGeometry_0.getOrientationSpecification()).getDiskNormal().setComponents(nx[i], ny[i], nz[i]);

		    VdmInflowSpecification vdmInflowSpecification_0 = 
		      virtualDisk_0.getComponentsManager().get(VdmInflowSpecification.class);

		    ((VdmAverageInflowPlaneMethod) vdmInflowSpecification_0.getActiveInflowMethod()).getRadius().setValue(rotor_radius[i]);

		    // ((VdmAverageInflowPlaneMethod) vdmInflowSpecification_0.getActiveInflowMethod()).getOffset().setValue(-1*4*rotor_radius[i]);
		    ((VdmAverageInflowPlaneMethod) vdmInflowSpecification_0.getActiveInflowMethod()).getOffset().setValue(-1*0.33*rotor_radius[i]);
    
		    // PropellerInflowVelocityPlane propellerInflowVelocityPlane_2 = 
		    //   virtualDisk_0.getComponentsManager().get(PropellerInflowVelocityPlane.class);

		    // propellerInflowVelocityPlane_2.getRadius().setValue(rotor_radius[i]);

		    // propellerInflowVelocityPlane_2.getOffset().setValue(-1*4*rotor_radius[i]);

		    VdmRotationRateInputValue vdmRotationRateInputValue_2 = 
		      virtualDisk_0.getComponentsManager().get(VdmRotationRateInputValue.class);

		    Units units_0 = 
		      ((Units) simulation_0.getUnitsManager().getObject("rpm"));

		    vdmRotationRateInputValue_2.getRotationRate().setUnits(units_0);

		    vdmRotationRateInputValue_2.getRotationRate().setValue(rotor_rpm[i]);


















	    ///////////////////////////////////////////////////////////////////////////////
		// create reports and monitors
		// Torque
		VirtualDiskMomentReport virtualDiskMomentReport_0 = 
      	  simulation_0.getReportManager().createReport(VirtualDiskMomentReport.class);

      	virtualDiskMomentReport_0.setVirtualDisk(virtualDisk_0);

		virtualDiskMomentReport_0.setPresentationName("Torque (" + names.get(i) + ")");

	    VirtualDiskMomentReport virtualDiskMomentReport_1 = 
	      ((VirtualDiskMomentReport) simulation_0.getReportManager().getReport("Torque (" + names.get(i) + ")"));

	    ReportMonitor reportMonitor_5 = 
	      virtualDiskMomentReport_1.createMonitor();


	    // Thrust
		VirtualDiskForceReport virtualDiskForceReport_0 = 
          simulation_0.getReportManager().createReport(VirtualDiskForceReport.class);
		  
		virtualDiskForceReport_0.setVirtualDisk(virtualDisk_0);

		virtualDiskForceReport_0.setPresentationName("Thrust (" + names.get(i) + ")");

	    VirtualDiskForceReport virtualDiskForceReport_1 = 
	      ((VirtualDiskForceReport) simulation_0.getReportManager().getReport("Thrust (" + names.get(i) + ")"));

	    ReportMonitor reportMonitor_6 = 
	      virtualDiskForceReport_1.createMonitor();



	    // Rotor Speed
	  	ExpressionReport expressionReport_0 = 
		  simulation_0.getReportManager().createReport(ExpressionReport.class);
		expressionReport_0.setDefinition("" + rotor_rpm[i] + "");
		expressionReport_0.setPresentationName("Rotor Speed (" + names.get(i) + ")");
		ReportMonitor reportMonitor_00 = 
		  expressionReport_0.createMonitor();


	 //    Units units_rpm = 
  //         ((Units) simulation_0.getUnitsManager().getObject("rpm"));
		// VirtualDiskRotationRateReport virtualDiskRotationRateReport_0 = 
  //     		simulation_0.getReportManager().createReport(VirtualDiskRotationRateReport.class);
		// virtualDiskRotationRateReport_0.setVirtualDisk(virtualDisk_0);
	 //    virtualDiskRotationRateReport_0.setPresentationName("Rotor Speed (" + names.get(i) + ")");
		// virtualDiskRotationRateReport_0.setUnits(units_rpm);


		

	    

	    
    












//                      _         _____       _ _           _            
//                     | |       /  __ \     | (_)         | |           
//   ___ _ __ ___  __ _| |_ ___  | /  \/_   _| |_ _ __   __| | ___ _ __  
//  / __| '__/ _ \/ _` | __/ _ \ | |   | | | | | | '_ \ / _` |/ _ \ '__| 
// | (__| | |  __/ (_| | ||  __/ | \__/\ |_| | | | | | | (_| |  __/ |    
//  \___|_|  \___|\__,_|\__\___|  \____/\__, |_|_|_| |_|\__,_|\___|_|    
//                                       __/ |                           
//                                      |___/                           
// 		// CYLINDER create the Shape Part
	    Units units_2 = 
	      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

	    MeshPartFactory meshPartFactory_0 = 
	      simulation_0.get(MeshPartFactory.class);

	    SimpleCylinderPart simpleCylinderPart_0 = 
	      meshPartFactory_0.createNewCylinderPart(simulation_0.get(SimulationPartManager.class));
	      simpleCylinderPart_0.setPresentationName("refine cylinder (" + names.get(i) + ")");


	    simpleCylinderPart_0.setDoNotRetessellate(true);


	    // set the coordinate system
	    LabCoordinateSystem labCoordinateSystem_0 = 
	      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

	    CartesianCoordinateSystem cartesianCoordinateSystem_1 = 
	      // ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("turbine  1: cw-CSys 1"));
	      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject(names.get(i) + "-CSys 1"));
	      
	    simpleCylinderPart_0.setCoordinateSystem(cartesianCoordinateSystem_1);


	    // start coordinate
	    Coordinate coordinate_5 = 
	      simpleCylinderPart_0.getStartCoordinate();
	    coordinate_5.setCoordinateSystem(cartesianCoordinateSystem_1);

	    coordinate_5.setCoordinate(units_2, units_2, units_2, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

	    coordinate_5.setValue(new DoubleVector(new double[] {0.0, 0.0, -0.5*rotor_radius[i]}));

	    // end coordinate
	    Coordinate coordinate_6 = 
	      simpleCylinderPart_0.getEndCoordinate();

	    coordinate_6.setCoordinateSystem(cartesianCoordinateSystem_1);

	    coordinate_6.setCoordinate(units_2, units_2, units_2, new DoubleVector(new double[] {0.0, 0.0, 0.0}));

	    coordinate_6.setValue(new DoubleVector(new double[] {0.0, 0.0, 0.5*rotor_radius[i]}));

	    // radius
	    simpleCylinderPart_0.getRadius().setUnits(units_2);

	    simpleCylinderPart_0.getRadius().setValue(1.25*rotor_radius[i]);

	    // misc
	    // simpleCylinderPart_0.getTessellationDensityOption().setSelected(TessellationDensityOption.MEDIUM);

	    simpleCylinderPart_0.rebuildSimpleShapePart();

	    // simpleCylinderPart_0.setDoNotRetessellate(false);




//                      _         _____       _                    
//                     | |       /  ___|     | |                   
//   ___ _ __ ___  __ _| |_ ___  \ `--. _ __ | |__   ___ _ __ ___  
//  / __| '__/ _ \/ _` | __/ _ \  `--. \ '_ \| '_ \ / _ \ '__/ _ \ 
// | (__| | |  __/ (_| | ||  __/ /\__/ / |_) | | | |  __/ | |  __/ 
//  \___|_|  \___|\__,_|\__\___| \____/| .__/|_| |_|\___|_|  \___| 
//                                     | |                         
//                                     |_|                         
// 	    ///////////////////////////////////////////////////////////////////////////////
// 	    // SPHERE create the Shape Part
// 	    SimpleSpherePart simpleSpherePart_0 = 
// 	      meshPartFactory_0.createNewSpherePart(simulation_0.get(SimulationPartManager.class));
	      
// 	    simpleSpherePart_0.setPresentationName("refine sphere (" + names.get(i) + ")");

// 	    simpleSpherePart_0.setDoNotRetessellate(true);

// 	    // coordinate system - attach to the Virtual Disk coordinate system
// 	    LabCoordinateSystem labCoordinateSystem_1 = 
// 	      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();


// simulation_0.println(names.get(i) + "-CSys 1");

// 	    CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
// 	      ((CartesianCoordinateSystem) labCoordinateSystem_1.getLocalCoordinateSystemManager().getObject(names.get(i) + "-CSys 1"));
	      
// 	    simpleSpherePart_0.setCoordinateSystem(cartesianCoordinateSystem_0);


// 	    // origin
// 	    Coordinate coordinate_9 = 
// 	      simpleSpherePart_0.getOrigin();

// 	    coordinate_9.setCoordinateSystem(cartesianCoordinateSystem_0);

// 	    coordinate_9.setCoordinate(units_2, units_2, units_2, new DoubleVector(new double[] {0.0, 0.0, 0.0}));

// 	    coordinate_9.setValue(new DoubleVector(new double[] {0.0, 0.0, 0.0}));

	    
// 	    // radius
// 	    simpleSpherePart_0.getRadius().setUnits(units_2);

// 	    simpleSpherePart_0.getRadius().setValue(4.50*rotor_radius[i]);

	    
// 	    // misc
// 	    // simpleSpherePart_0.getTessellationDensityOption().setSelected(TessellationDensityOption.MEDIUM);

// 	    simpleSpherePart_0.rebuildSimpleShapePart();

// 	    // simpleSpherePart_0.setDoNotRetessellate(false);

	    

// //                      _         _____ _____ _   _  _____ 
// //                     | |       /  __ \  _  | \ | ||  ___|
// //   ___ _ __ ___  __ _| |_ ___  | /  \/ | | |  \| || |__  
// //  / __| '__/ _ \/ _` | __/ _ \ | |   | | | | . ` ||  __| 
// // | (__| | |  __/ (_| | ||  __/ | \__/\ \_/ / |\  || |___ 
// //  \___|_|  \___|\__,_|\__\___|  \____/\___/\_| \_/\____/ 
                                                        
                                                        
	   //  ///////////////////////////////////////////////////////////////////////////////
	   //  // CONE create the Shape Part
	   //  SimpleConePart simpleConePart_0 = 
	   //    meshPartFactory_0.createNewConePart(simulation_0.get(SimulationPartManager.class));

	  	// simpleConePart_0.setPresentationName("refine cone (" + names.get(i) + ")");

	   //  simpleConePart_0.setDoNotRetessellate(true);

	   //  simpleConePart_0.setCoordinateSystem(cartesianCoordinateSystem_0);

	   //  Coordinate coordinate_2 = 
	   //    simpleConePart_0.getStartCoordinate();

	   //  coordinate_2.setCoordinateSystem(cartesianCoordinateSystem_0);

	   //  coordinate_2.setCoordinate(units_2, units_2, units_2, new DoubleVector(new double[] {0.0, 0.0, 1.0}));

	   //  coordinate_2.setValue(new DoubleVector(new double[] {0.0, 0.0, 0.0}));

	   //  simpleConePart_0.getStartRadius().setUnits(units_2);

	   //  simpleConePart_0.getStartRadius().setValue(1.50*rotor_radius[i]);

	   //  Coordinate coordinate_3 = 
	   //    simpleConePart_0.getEndCoordinate();

	   //  coordinate_3.setCoordinateSystem(cartesianCoordinateSystem_0);

	   //  coordinate_3.setCoordinate(units_2, units_2, units_2, new DoubleVector(new double[] {0.0, 0.0, 0.0}));

	   //  coordinate_3.setValue(new DoubleVector(new double[] {0.0, 0.0, 20*2*rotor_radius[i]}));

	   //  simpleConePart_0.getEndRadius().setUnits(units_2);

	   //  simpleConePart_0.getEndRadius().setValue(2.0*rotor_radius[i]);

	   //  // simpleConePart_0.getTessellationDensityOption().setSelected(TessellationDensityOption.MEDIUM);

	   //  simpleConePart_0.rebuildSimpleShapePart();

	   //  // simpleConePart_0.setDoNotRetessellate(false);

	    

	    












//            __ _              _____       _ _           _           
//           / _(_)            /  __ \     | (_)         | |          
//  _ __ ___| |_ _ _ __   ___  | /  \/_   _| |_ _ __   __| | ___ _ __ 
// | '__/ _ \  _| | '_ \ / _ \ | |   | | | | | | '_ \ / _` |/ _ \ '__|
// | | |  __/ | | | | | |  __/ | \__/\ |_| | | | | | | (_| |  __/ |   
// |_|  \___|_| |_|_| |_|\___|  \____/\__, |_|_|_| |_|\__,_|\___|_|   
//                                     __/ |                          
//                                    |___/                           
	    ///////////////////////////////////////////////////////////////////////////////
	    // CYLINDER create a volumentric mesh control, add Shape Parts
	    VolumeCustomMeshControl volumeCustomMeshControl_1 = 
	      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();

	    volumeCustomMeshControl_1.setPresentationName("refine cylinder (" + names.get(i) + ")");

	    // SimpleCylinderPart simpleCylinderPart_0 = 
	    //   ((SimpleCylinderPart) simulation_0.get(SimulationPartManager.class).getPart("refine cylinder (Turbine1)"));

	    volumeCustomMeshControl_1.getGeometryObjects().setObjects(simpleCylinderPart_0);

	    VolumeControlDualMesherSizeOption volumeControlDualMesherSizeOption_0 = 
	      volumeCustomMeshControl_1.getCustomConditions().get(VolumeControlDualMesherSizeOption.class);

	    volumeControlDualMesherSizeOption_0.setVolumeControlBaseSizeOption(true);

	    VolumeControlSize volumeControlSize_0 = 
	      volumeCustomMeshControl_1.getCustomValues().get(VolumeControlSize.class);

	    volumeControlSize_0.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.Type.ABSOLUTE);

	    GenericAbsoluteSize genericAbsoluteSize_6 = 
	      ((GenericAbsoluteSize) volumeControlSize_0.getAbsoluteSize());

	    genericAbsoluteSize_6.getValue().setValue(2.0);



	    // VolumeCustomMeshControl volumeCustomMeshControl_2 = 
	    //   autoMeshOperation_0.getCustomMeshControls().createVolumeControl();

	    //   volumeCustomMeshControl_2.setPresentationName("refine cylinder (" + names.get(i) + ")");

	    //   volumeCustomMeshControl_2.getGeometryObjects().setObjects(simpleCylinderPart_0);


	      // customize the size of this mesh refinement
	    // VolumeControlTrimmerSizeOption volumeControlTrimmerSizeOption_2 = 
	    //   volumeCustomMeshControl_2.getCustomConditions().get(VolumeControlTrimmerSizeOption.class);

	    // volumeControlTrimmerSizeOption_2.setVolumeControlBaseSizeOption(true);

	    // VolumeControlSize volumeControlSize_2 = 
	    //   volumeCustomMeshControl_2.getCustomValues().get(VolumeControlSize.class);

	    // volumeControlSize_2.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.ABSOLUTE);

	    // GenericAbsoluteSize genericAbsoluteSize_2 = 
	    //   ((GenericAbsoluteSize) volumeControlSize_2.getAbsoluteSize());

	    // genericAbsoluteSize_2.getValue().setValue(rotor_radius[i]/20);


//            __ _              _____       _                   
//           / _(_)            /  ___|     | |                  
//  _ __ ___| |_ _ _ __   ___  \ `--. _ __ | |__   ___ _ __ ___ 
// | '__/ _ \  _| | '_ \ / _ \  `--. \ '_ \| '_ \ / _ \ '__/ _ \
// | | |  __/ | | | | | |  __/ /\__/ / |_) | | | |  __/ | |  __/
// |_|  \___|_| |_|_| |_|\___| \____/| .__/|_| |_|\___|_|  \___|
//                                   | |                        
//                                   |_|                        
	    ///////////////////////////////////////////////////////////////////////////////
	    // SPHERE create a volumentric mesh control, add Shape Parts
	    // AutoMeshOperation autoMeshOperation_1 = 
	    //   ((AutoMeshOperation) simulation_0.get(MeshOperationManager.class).getObject("Block"));

	    // VolumeCustomMeshControl volumeCustomMeshControl_3 = 
	    //   autoMeshOperation_0.getCustomMeshControls().createVolumeControl();

	    // volumeCustomMeshControl_3.setPresentationName("refine sphere (" + names.get(i) + ")");

	    // volumeCustomMeshControl_3.getGeometryObjects().setObjects(simpleSpherePart_0);

	    // VolumeControlTrimmerSizeOption volumeControlTrimmerSizeOption_3 = 
	    //   volumeCustomMeshControl_3.getCustomConditions().get(VolumeControlTrimmerSizeOption.class);

	    // volumeControlTrimmerSizeOption_3.setVolumeControlBaseSizeOption(true);

	    // VolumeControlSize volumeControlSize_3 = 
	    //   volumeCustomMeshControl_3.getCustomValues().get(VolumeControlSize.class);

	    // volumeControlSize_3.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.ABSOLUTE);

	    // GenericAbsoluteSize genericAbsoluteSize_3 = 
	    //   ((GenericAbsoluteSize) volumeControlSize_3.getAbsoluteSize());

	    // genericAbsoluteSize_3.getValue().setValue(0.40*rotor_radius[i]);

//            __ _              _____ _____ _   _  _____ 
//           / _(_)            /  __ \  _  | \ | ||  ___|
//  _ __ ___| |_ _ _ __   ___  | /  \/ | | |  \| || |__  
// | '__/ _ \  _| | '_ \ / _ \ | |   | | | | . ` ||  __| 
// | | |  __/ | | | | | |  __/ | \__/\ \_/ / |\  || |___ 
// |_|  \___|_| |_|_| |_|\___|  \____/\___/\_| \_/\____/ 
		///////////////////////////////////////////////////////////////////////////
	    // CONE create a volumentric mesh control, add Shape Parts
	 //    VolumeCustomMeshControl volumeCustomMeshControl_0 = 
	 //      autoMeshOperation_0.getCustomMeshControls().createVolumeControl();

		// volumeCustomMeshControl_0.setPresentationName("refine cone (" + names.get(i) + ")");

	 //    volumeCustomMeshControl_0.getGeometryObjects().setObjects(simpleConePart_0);


	 //    VolumeControlTrimmerSizeOption volumeControlTrimmerSizeOption_0 = 
	 //      volumeCustomMeshControl_0.getCustomConditions().get(VolumeControlTrimmerSizeOption.class);

	 //    volumeControlTrimmerSizeOption_0.setVolumeControlBaseSizeOption(true);

	 //    VolumeControlSize volumeControlSize_1 = 
	 //      volumeCustomMeshControl_0.getCustomValues().get(VolumeControlSize.class);

	 //    volumeControlSize_1.getRelativeOrAbsoluteOption().setSelected(RelativeOrAbsoluteOption.ABSOLUTE);

	 //    GenericAbsoluteSize genericAbsoluteSize_1 = 
	 //      ((GenericAbsoluteSize) volumeControlSize_1.getAbsoluteSize());

	 //    genericAbsoluteSize_1.getValue().setValue(0.40*rotor_radius[i]);


	} // end FOR loop





		// dunno why but these things behave better in a separate loop

		Units units_none = 
		  simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

		Region region_0 = 
		  simulation_0.getRegionManager().getRegion("Region");

		for (int i = 0; i < nVirtualDisks; i++) {

			    ///////////////////////////////////////////////////////////////////////////////
				// create threshold parts
			    PrimitiveFieldFunction primitiveFieldFunction_0 = 
			      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction(name_VirtualDiskMarker[i]));

			    ThresholdPart thresholdPart_0 = 
			      simulation_0.getPartManager().createThresholdPart(new NeoObjectVector(new Object[] {region_0}), new DoubleVector(new double[] {1.0, 1.0}), units_none, primitiveFieldFunction_0, 0);

			    thresholdPart_0.setPresentationName(names.get(i));


			    PrimitiveFieldFunction primitiveFieldFunction_1 = 
			      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction(name_VirtualDiskInflowPlaneMarker[i]));

			    ThresholdPart thresholdPart_1 = 
			      simulation_0.getPartManager().createThresholdPart(new NeoObjectVector(new Object[] {region_0}), new DoubleVector(new double[] {1.0, 1.0}), units_none, primitiveFieldFunction_1, 0);

			    thresholdPart_1.setPresentationName("inflow (" + names.get(i) + ")");


			    ///////////////////////////////////////////////////////////////////////////////
				// create volume average reports and monitors on the threshold parts
			    VolumeAverageReport volumeAverageReport_0 = 
			      simulation_0.getReportManager().createReport(VolumeAverageReport.class);

		      	FvRepresentation fvRepresentation_0 = 
      			  ((FvRepresentation) simulation_0.getRepresentationManager().getObject("Volume Mesh"));

    			volumeAverageReport_0.setRepresentation(fvRepresentation_0);

			    PrimitiveFieldFunction primitiveFieldFunction_2 = 
			      ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));

			    VectorMagnitudeFieldFunction vectorMagnitudeFieldFunction_0 = 
			      ((VectorMagnitudeFieldFunction) primitiveFieldFunction_2.getMagnitudeFunction());

			    volumeAverageReport_0.setScalar(vectorMagnitudeFieldFunction_0);

			    volumeAverageReport_0.getParts().setObjects(thresholdPart_1);

			    volumeAverageReport_0.setPresentationName("volume avg. inflow (" + names.get(i) + ")");


			    VolumeAverageReport volumeAverageReport_1 = 
			      ((VolumeAverageReport) simulation_0.getReportManager().getReport("volume avg. inflow (" + names.get(i) + ")"));

			    ReportMonitor reportMonitor_0 = 
				  volumeAverageReport_1.createMonitor();







			    // FIELD FUNCTIONS
				// tip-speed ratio
				UserFieldFunction userFieldFunction_0 = 
			      simulation_0.getFieldFunctionManager().createFieldFunction();
			    userFieldFunction_0.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
			    userFieldFunction_0.setPresentationName("Tip-Speed-Ratio (" + names.get(i) + ")");
			    userFieldFunction_0.setFunctionName("Tip-Speed-Ratio (" + names.get(i) + ")");
			    userFieldFunction_0.setDefinition(rotor_radius[i] + " * ${RotorSpeed(" + names.get(i) + ")Report} * (3.14159/30) / ${volumeavg.inflow(" + names.get(i) + ")Report}");

			    // power
			    UserFieldFunction userFieldFunction_1 = 
			      simulation_0.getFieldFunctionManager().createFieldFunction();
			    userFieldFunction_1.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);
			    userFieldFunction_1.setPresentationName("Power (" + names.get(i) + ")");
			    userFieldFunction_1.setFunctionName("Power (" + names.get(i) + ")");
			    userFieldFunction_1.setDefinition("${Torque(" + names.get(i) + ")Report} *${RotorSpeed(" + names.get(i) + ")Report} * (3.14159/30)");



			    // REPORTS
				ExpressionReport expressionReport_0 = 
      				simulation_0.getReportManager().createReport(ExpressionReport.class);
			    expressionReport_0.setDefinition("${Power (" + names.get(i) + ")}");
			    expressionReport_0.setPresentationName("Power (" + names.get(i) + ")");
				    ReportMonitor reportMonitor_00 = 
				      expressionReport_0.createMonitor();

    

			    ExpressionReport expressionReport_1 = 
      				simulation_0.getReportManager().createReport(ExpressionReport.class);
			    expressionReport_1.setDefinition("${Tip-Speed-Ratio (" + names.get(i) + ")}");
			    expressionReport_1.setPresentationName("Tip-Speed-Ratio (" + names.get(i) + ")");
					ReportMonitor reportMonitor_11 = 
					      expressionReport_1.createMonitor();



				// reports on rotor speeds
				ExpressionReport expressionReport_2 = 
				  ((ExpressionReport) simulation_0.getReportManager().getReport("Rotor Speed (" + names.get(i) + ")"));

				ReportMonitor reportMonitor_1 = 
				  expressionReport_0.createMonitor();


		} // end FOR loop




		ReportMonitor reportMonitor_0 = 
		  ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("volume avg. inflow (" + names.get(0) + ") Monitor"));
	    MonitorPlot monitorPlot_0 = 
	      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_0}), "volume avg. inflow");
	    monitorPlot_0.setPresentationName("rotors-inflow");

	    ReportMonitor reportMonitor_1 = 
		  ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Thrust (" + names.get(0) + ") Monitor"));
	    MonitorPlot monitorPlot_1 = 
	      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_1}), "thrust");
	    monitorPlot_1.setPresentationName("rotors-thrust");

	    ReportMonitor reportMonitor_2 = 
		  ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Torque (" + names.get(0) + ") Monitor"));
	    MonitorPlot monitorPlot_2 = 
	      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_2}), "torque");
	    monitorPlot_2.setPresentationName("rotors-torque");


// simulation_0.println("DEBUG 03:" + "Rotor Speed (" + names.get(0) + ") Monitor");
simulation_0.println("DEBUG 66: testing 1");
	    ReportMonitor reportMonitor_3 = 
		  ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Rotor Speed (" + names.get(0) + ") Monitor"));
simulation_0.println("DEBUG 66: testing 2");
	    MonitorPlot monitorPlot_3 = 
	      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_3}), "rotor speed");
	    monitorPlot_3.setPresentationName("rotor speed");

	    ReportMonitor reportMonitor_4 = 
		  ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Tip-Speed-Ratio (" + names.get(0) + ") Monitor"));
	    MonitorPlot monitorPlot_4 = 
	      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_4}), "Tip-Speed-Ratio");
	    monitorPlot_4.setPresentationName("Tip-Speed-Ratio");

	   ReportMonitor reportMonitor_5 = 
		  ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Power (" + names.get(0) + ") Monitor"));
	    MonitorPlot monitorPlot_5 = 
	      simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_5}), "Power");
	    monitorPlot_5.setPresentationName("Power");





	    for (int i = 1; i < nVirtualDisks; i++) {
            ReportMonitor reportMonitor_0n = 
              ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("volume avg. inflow (" + names.get(i) + ") Monitor"));
            monitorPlot_0.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_0n}));

            ReportMonitor reportMonitor_1n = 
              ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Thrust (" + names.get(i) + ") Monitor"));
            monitorPlot_1.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_1n}));

            ReportMonitor reportMonitor_2n = 
              ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Torque (" + names.get(i) + ") Monitor"));
            monitorPlot_2.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_2n}));



            ReportMonitor reportMonitor_3n = 
              ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Rotor Speed (" + names.get(i) + ") Monitor"));
            monitorPlot_3.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_3n}));


            ReportMonitor reportMonitor_4n = 
              ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Tip-Speed-Ratio (" + names.get(i) + ") Monitor"));
            monitorPlot_4.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_4n}));

            ReportMonitor reportMonitor_5n = 
              ((ReportMonitor) simulation_0.getMonitorManager().getMonitor("Power (" + names.get(i) + ") Monitor"));
            monitorPlot_5.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_5n}));


        }

		
  } // end execute0()
} // end public class
