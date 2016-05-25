// STAR-CCM+ macro: createScenes_defaultLayout.java
// Written by STAR-CCM+ 10.06.010
package macro;

import java.util.*;

import star.common.*;
import star.base.neo.*;
import star.vis.*;

public class createScenes_defaultLayout extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();

    ResidualPlot residualPlot_0 = 
      ((ResidualPlot) simulation_0.getPlotManager().getPlot("Residuals"));

    residualPlot_0.open();

    Scene scene_1 = 
      simulation_0.getSceneManager().getScene("mesh");

    scene_1.open(true);

    Scene scene_0 = 
      simulation_0.getSceneManager().getScene("scalar");

    scene_0.open(true);

    MonitorPlot monitorPlot_2 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("probes-velocity"));

    monitorPlot_2.open();

    MonitorPlot monitorPlot_0 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("rotors-inflow"));

    monitorPlot_0.open();

    MonitorPlot monitorPlot_3 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("rotors-thrust"));

    monitorPlot_3.open();

    MonitorPlot monitorPlot_1 = 
      ((MonitorPlot) simulation_0.getPlotManager().getPlot("rotors-torque"));

    monitorPlot_1.open();

    CurrentView currentView_1 = 
      scene_1.getCurrentView();

    currentView_1.setInput(new DoubleVector(new double[] {311.88898741216093, 51.79692497404247, 123.74703091927071}), new DoubleVector(new double[] {-663.2297684299394, -715.2021650286008, 678.3560429906779}), new DoubleVector(new double[] {0.34820763887554784, 0.2167730738021511, 0.9120092514350298}), 354.75620095254146, 0);
  }
}
