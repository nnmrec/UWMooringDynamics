// STAR-CCM+ macro: fieldFunction_ABL_inlet.java
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

public class fieldFunction_ABL_inlet extends StarMacro {

  public void execute() {
    execute0();
  }

  private void execute0() {

    Simulation simulation_0 = 
      getActiveSimulation();




    UserFieldFunction userFieldFunction_1 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_1.getTypeOption().setSelected(FieldFunctionTypeOption.Type.VECTOR);

    userFieldFunction_1.setPresentationName("1.WINDSPEED");

    userFieldFunction_1.setFunctionName("1.WINDSPEED");

    userFieldFunction_1.setDefinition("[-10.3,0,0]");





    UserFieldFunction userFieldFunction_2 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_2.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_2.setPresentationName("2.z0");

    userFieldFunction_2.setFunctionName("2.z0");

    userFieldFunction_2.setDefinition("0.001");





    UserFieldFunction userFieldFunction_3 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_3.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_3.setPresentationName("3.zh");

    userFieldFunction_3.setFunctionName("3.zh");

    userFieldFunction_3.setDefinition("1000");
    




    UserFieldFunction userFieldFunction_4 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_4.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_4.setPresentationName("4.z");

    userFieldFunction_4.setFunctionName("4.z");

    userFieldFunction_4.setDefinition("$WallDistance");





    UserFieldFunction userFieldFunction_5 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_5.getTypeOption().setSelected(FieldFunctionTypeOption.Type.VECTOR);

    userFieldFunction_5.setPresentationName("5.zref");

    userFieldFunction_5.setFunctionName("5.zref");    

    userFieldFunction_5.setDefinition("[0,0,10]");

    userFieldFunction_5.setDimensionsVector(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));





    UserFieldFunction userFieldFunction_6 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_6.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_6.setPresentationName("6.e");

    userFieldFunction_6.setFunctionName("6.e");

    userFieldFunction_6.setDefinition("2.71828183");





    UserFieldFunction userFieldFunction_7 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_7.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_7.setPresentationName("7.kappa");

    userFieldFunction_7.setFunctionName("7.kappa");

    userFieldFunction_7.setDefinition("0.42");





    UserFieldFunction userFieldFunction_8 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_8.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_8.setPresentationName("8.pi");

    userFieldFunction_8.setFunctionName("8.pi");

    userFieldFunction_8.setDefinition("acos(-1)");





    UserFieldFunction userFieldFunction_9 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_9.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_9.setPresentationName("9.uref");

    userFieldFunction_9.setFunctionName("9.uref");

    userFieldFunction_9.setDefinition("$${WINDSPEED}[0]");

    // userFieldFunction_9.setDefinition("$${9.WINDSPEED}[0]");





    UserFieldFunction userFieldFunction_10 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_10.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_10.setPresentationName("10.utau");

    userFieldFunction_10.setFunctionName("10.utau");

    Units units_0 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    Units units_1 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    Units units_2 = 
      simulation_0.getUnitsManager().getPreferredUnits(new IntVector(new int[] {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

    userFieldFunction_10.setDefinition("$kappa*$uref*log($e)/log(($$zref[2]+${z0})/${z0})");





    UserFieldFunction userFieldFunction_11 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_11.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_11.setPresentationName("11.U");

    userFieldFunction_11.setFunctionName("11.U");

    userFieldFunction_11.setDefinition("(${z}>$zh) ? $utau/$kappa*log(($zh+${z0})/${z0})/log($e) : ($utau/$kappa*log((${z}+${z0})/${z0})/log($e))");





    UserFieldFunction userFieldFunction_12 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_12.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_12.setPresentationName("12.theta");

    userFieldFunction_12.setFunctionName("12.theta");

    userFieldFunction_12.setDefinition("180.0*$pi/180");





    UserFieldFunction userFieldFunction_13 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_13.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_13.setPresentationName("13.VelocityProfile");

    userFieldFunction_13.setFunctionName("13.VelocityProfile");

    userFieldFunction_13.setDefinition("[${U}*cos($theta),${U}*sin($theta),0]");





    UserFieldFunction userFieldFunction_14 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_14.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_14.setPresentationName("14.TI");

    userFieldFunction_14.setFunctionName("14.TI");

    userFieldFunction_14.setDefinition("0.13");





    UserFieldFunction userFieldFunction_15 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_15.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_15.setPresentationName("15.tdr");

    userFieldFunction_15.setFunctionName("15.tdr");

    userFieldFunction_15.setDefinition("pow($utau,3)/($kappa*${z})");





    UserFieldFunction userFieldFunction_16 = 
      simulation_0.getFieldFunctionManager().createFieldFunction();

    userFieldFunction_16.getTypeOption().setSelected(FieldFunctionTypeOption.Type.SCALAR);

    userFieldFunction_16.setPresentationName("16.tke");

    userFieldFunction_16.setFunctionName("16.tke");

    userFieldFunction_16.setDefinition("pow(($uref*$TI),2)");




  }
}
