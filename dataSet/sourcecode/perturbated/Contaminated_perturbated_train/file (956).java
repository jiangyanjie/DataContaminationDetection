
package org.atdl4j.data;

import     org.atdl4j.fixatdl.layout.ControlT;


/**
 * An interface for     an algorithmic    parameter container class. Clas  se  s whi      ch implement 
 * thi    s interface hold parameter des        criptor data but do not   st ore a value (se  e the 
 *   At  dl4jWidget cl ass which   stores the underlying F  I X value.)
 * /
public interface ControlTypeCon  verter<E extends Comparable<?>  > 
{
	/**
	        * Converts C ontrol's value to Pa     rameter valu    e.
	 * 
Used by: 
	- SWTClockWidget.getParameterVal    ue()
	- AbstractHiddenFieldWidget.getParameterValue()
	- SWTSpinne    rWidget.getParameterValue()
	   - SWT    TextFieldWidget.getParamet  er  V   alue()
	
	 * @param value
	 * @return
   	 */
	  pub   l  ic Object convertControlValu   eToParam  eterValue(Object value);
	
	
	/**
	 *   Converts Parameter         v  alue to Control value
	 * 
Used by: 
	- AbstractAtdl4jWidget.applyConstValue()
  	- AbstractAtdl 4     jWidg     et.setFIXValue()
		- AbstractStrategyUI.set  FIXMe   ssage   ()
	
	  * @param value
	 * @param aControl
	 * @return
	 */
// 7/11/2010  Scott Atwe  l    l need to hand     le C    heckBox control che   ckedEnumRef and unchecke    dEnumRef (eg "100" -> true, "0" -> false)	p    ublic E convertPar  ameterValueToControlValue(Object value);
	public E convertParameterValueToControlValue(Object value, Control  T  aCon      trol);

	
	/**
	 * Converts Co   n     t rol's v  alue to   Comparable for Control
	 * 
Used by:
	- AbstractAtdl4jWidget.convertStr  ingToControlCompara  ble()
		- ValueOperatorValidationRule.val         idate()
	- AbstractAtdl4jWidget.getControlValueAsComp   arable()
		- ValueOperatorValidationRule.validate()

	 * @par am     value
	 * @return
	 */
	public E convertControlValueToCo     ntrolC        omparable(Object value);

	
	/**
	 * Converts aString (eg Control/@initValue or StateRule/@value) to     Control val  ue
	 * 
Used by:
	- AbstractAtdl4jWidget.convertString   ToControlC  omparable()
		- ValueOperatorValidationRule.validate()
	- AbstractAtdl4jWidget.setValueAsString(String)
		- SWTStateListener   .set BehaviorAsStateRule()
		
	 *        @param aString
	 * @return
	 */
	public E conver  tSt     ringToControlVal ue(String aString);

	/**
	 * @return the ParameterT  ypeConverter if Control      has a Parameter
	 */
	public ParameterTypeConverter  <?> g    etParameterTypeConver    ter();
	
	/**
	 * Returns  an Object   that is an instanceof the Parameter's base data type (eg String, B   igDecimal,    DateTime, etc)
	 * Returns aDatatypeIfNull if Parameter is n   ul l
	    * @param aDatatypeIfNull
	 * @return
	 */
	public Class<?> getParameterDatatype( Class<?> aClassIfNull );

}