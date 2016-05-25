// STAR-CCM+ macro: createProbes.java
// Written by STAR-CCM+ 10.06.010
// 
// license: ?
// 
//
package macro;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import star.common.*;
import star.base.neo.*;
import star.base.report.*;

import star.vis.*;

public class createProbes extends StarMacro {


    ///////////////////////////////////////////////////////////////////////////////
    // USER INPUTS
    //
    // path to CSV file with names and coordinates of point probes (this gets updated from the "mooring model" code) This file should NOT have any empty lines at bottom 
    String path0    = "outputs/probes.csv";

    ///////////////////////////////////////////////////////////////////////////////

    public void execute() {

        Simulation simulation_0 = getActiveSimulation();

        Region region_0 =
                simulation_0.getRegionManager().getRegion("Block");


        File f = new File(path0);
        try {

            FileReader  fr      = new FileReader(f);
            Scanner     sc      = new Scanner(fr);
            String      line    = "";

            // count the number of lines in the file (naive way, but just loop until end of file)
            ArrayList<String> names = new ArrayList<String>();
            Integer nLines = new Integer(0);
            while (sc.hasNextLine()) {
                if(nLines == 0) {
                   nLines = nLines + 1;
                   sc.nextLine();
                   continue;
                }
                nLines      = nLines + 1;
                line        = sc.nextLine();
                String name = line.split(",")[0];
                names.add(name);

                double x    = Double.parseDouble(line.split(",")[1]);
                double y    = Double.parseDouble(line.split(",")[2]);
                double z    = Double.parseDouble(line.split(",")[3]);

                PointPart pointPart_1 = 
                  simulation_0.getPartManager().createPointPart(new NeoObjectVector(new Object[]{region_0}), new DoubleVector(new double[]{x, y, z}));

                pointPart_1.setPresentationName(name);

                simulation_0.println("Probe point '" + name + "' created at (" + x + "," + y + "," + z + ")");
            } // end while


            // create a report for each probe, then a monitor for each probe, then a single plot with all probes
            int nParts = nLines - 1;
            ArrayList<PointPart>     pointParts = new ArrayList<PointPart>();
            for (int i = 0; i < nParts; i++) {
              
                MaxReport maxReport_0 = 
                  simulation_0.getReportManager().createReport(MaxReport.class);

                maxReport_0.setPresentationName(names.get(i));

                PointPart pointPart_n = 
                    ((PointPart) simulation_0.getPartManager().getObject(names.get(i)));
                pointParts.add(pointPart_n);

                maxReport_0.getParts().setObjects(pointPart_n);

                PrimitiveFieldFunction primitiveFieldFunction_0 = 
                  ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));

                VectorMagnitudeFieldFunction vectorMagnitudeFieldFunction_0 = 
                  ((VectorMagnitudeFieldFunction) primitiveFieldFunction_0.getMagnitudeFunction());

                maxReport_0.setScalar(vectorMagnitudeFieldFunction_0);

                maxReport_0.setSmooth(true);

                ReportMonitor reportMonitor_0 = 
                  maxReport_0.createMonitor();
                reportMonitor_0.setPresentationName(names.get(i));
            }

            // make the plot
            ReportMonitor reportMonitor_0 = 
                  ((ReportMonitor) simulation_0.getMonitorManager().getMonitor(names.get(0)));

            MonitorPlot monitorPlot_0 = 
              simulation_0.getPlotManager().createMonitorPlot(new NeoObjectVector(new Object[] {reportMonitor_0}), "probes-velocity");

            monitorPlot_0.setPresentationName("probes-velocity");

            monitorPlot_0.setTitle("velocity at probes");

            for (int i = 1; i < nParts; i++) {
                ReportMonitor reportMonitor_n = 
                  ((ReportMonitor) simulation_0.getMonitorManager().getMonitor(names.get(i)));

                monitorPlot_0.getDataSetManager().addDataProviders(new NeoObjectVector(new Object[] {reportMonitor_n}));
            }



            // // create an Internal XYZ Table used to export variables (the "export" of tables is not great because it does not include the part name!)
            // XyzInternalTable xyzInternalTable_0 = 
            //   simulation_0.getTableManager().createTable(XyzInternalTable.class);
            
            // xyzInternalTable_0.setPresentationName("extract-Point-Probes");

            // // add parts to the table
            // int nParts = nLines - 1;
            // ArrayList<PointPart> pointParts = new ArrayList<PointPart>();
            // for (int i = 0; i < nParts; i++) {
            //   PointPart pointPart_n = 
            //     ((PointPart) simulation_0.getPartManager().getObject(names.get(i)));
            //   pointParts.add(pointPart_n);
            // }
            // xyzInternalTable_0.getParts().setObjects(pointParts);

            // // choose the variables to extract
            // PrimitiveFieldFunction primitiveFieldFunction_0 = 
            //   ((PrimitiveFieldFunction) simulation_0.getFieldFunctionManager().getFunction("Velocity"));

            // VectorMagnitudeFieldFunction vectorMagnitudeFieldFunction_0 = 
            //   ((VectorMagnitudeFieldFunction) primitiveFieldFunction_0.getMagnitudeFunction());

            // xyzInternalTable_0.setFieldFunctions(new NeoObjectVector(new Object[] {vectorMagnitudeFieldFunction_0}));

            // // set the path for table
            // TableUpdate tableUpdate_0 = 
            //   xyzInternalTable_0.getTableUpdate();

            // tableUpdate_0.setFilePath("outputs");



        } catch (FileNotFoundException ex) {
            Logger.getLogger(createProbes.class.getName()).log(Level.SEVERE, null, ex);
        }

  } // end execute0()
} // end public class
