// STAR-CCM+ macro: parts_SectionPlanesTurbines.java
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
import star.vis.*;
import java.io.*;
import java.util.logging.*;

public class parts_SectionPlanesTurbines extends StarMacro {


  ///////////////////////////////////////////////////////////////////////////////
  // USER INPUTS (all these user inputs should be read from a CSV file instead)
  // String path0    = "../inputs/turbines.csv";


  ///////////////////////////////////////////////////////////////////////////////

  public void execute() {
    execute0();
  }

  private void execute0() {
        

      Simulation simulation_0 = 
        getActiveSimulation();
      Region region_0 = 
      simulation_0.getRegionManager().getRegion("Region");



      SimpleAnnotation simpleAnnotation_00 = 
        ((SimpleAnnotation) simulation_0.getAnnotationManager().getObject("file_turbines"));
    // File f = new File("../inputs/" + simpleAnnotation_00.getText() + ".csv");  
        

        //
        int       nVirtualDisks   = 0;
        List<String>  textline    = new ArrayList<String>();

        // File f = new File("../inputs/" + simpleAnnotation_00.getText() + ".csv");
        // File f = new File("inputs/tables/" + simpleAnnotation_00.getText() + ".csv");
        File f = new File("outputs/" + "rotors.csv");
        // File f = new File(path0);
        try {

            FileReader  fr   = new FileReader(f);
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
            Logger.getLogger(parts_SectionPlanesTurbines.class.getName()).log(Level.SEVERE, null, ex);
        } // end try

      simulation_0.println("DEBUG 0: nVirtualDisks = " + nVirtualDisks);

      List<String>  names   = new ArrayList<String>();
      // String[] name = new String[nVirtualDisks];
      for (int i = 0; i < nVirtualDisks; i++) {
        String name = textline.get(i).split(",")[0];
        names.add(name);
        // name[i] = textline.get(i).split(",")[0];
      }
      String[] name_VirtualDiskMarker             = new String[nVirtualDisks];
      String[] name_VirtualDiskInflowPlaneMarker  = new String[nVirtualDisks];
      for (int j = 0; j < nVirtualDisks; j++) {
        // I think these are not connected to the "name" ... they will always be named in order of creation
        name_VirtualDiskMarker[j]            = "VirtualDiskMarker" + (j+1);
        name_VirtualDiskInflowPlaneMarker[j] = "VirtualDiskInflowPlaneMarker" + (j+1);
      }





    PlaneSection planeSection_3 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_3.setPresentationName("plane-xz");

    LabCoordinateSystem labCoordinateSystem_0 = 
      simulation_0.getCoordinateSystemManager().getLabCoordinateSystem();

      simulation_0.println("DEBUG 00: name = " + names.get(0));
    CartesianCoordinateSystem cartesianCoordinateSystem_0 = 
      // ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject("turbine-01-CSys 1"));
      ((CartesianCoordinateSystem) labCoordinateSystem_0.getLocalCoordinateSystemManager().getObject(names.get(1) + "-CSys 1"));

    planeSection_3.setCoordinateSystem(cartesianCoordinateSystem_0);

    Coordinate coordinate_2 = 
      planeSection_3.getOrientationCoordinate();

    Units units_0 = 
      ((Units) simulation_0.getUnitsManager().getObject("m"));

    coordinate_2.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {0.0, 1.0, 0.0}));

    planeSection_3.getInputParts().setQuery(null);

    planeSection_3.getInputParts().setObjects(region_0);



    PlaneSection planeSection_4 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_4.setPresentationName("plane-xy");

    planeSection_4.setCoordinateSystem(cartesianCoordinateSystem_0);

    Coordinate coordinate_3 = 
      planeSection_4.getOrientationCoordinate();

    coordinate_3.setCoordinate(units_0, units_0, units_0, new DoubleVector(new double[] {1.0, 0.0, 0.0}));

    planeSection_4.getInputParts().setQuery(null);

    planeSection_4.getInputParts().setObjects(region_0);


    PlaneSection planeSection_5 = 
      (PlaneSection) simulation_0.getPartManager().createImplicitPart(new NeoObjectVector(new Object[] {}), new DoubleVector(new double[] {0.0, 0.0, 1.0}), new DoubleVector(new double[] {0.0, 0.0, 0.0}), 0, 1, new DoubleVector(new double[] {0.0}));

    planeSection_5.setPresentationName("plane-yz");

    planeSection_5.setCoordinateSystem(cartesianCoordinateSystem_0);

    planeSection_5.getInputParts().setQuery(null);

    planeSection_5.getInputParts().setObjects(region_0);




  }
}
