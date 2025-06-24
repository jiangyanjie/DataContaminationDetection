package org.atdl4j.ui.app.impl;

import  java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
impor  t org.atdl4j.config.Atdl4jConfig;
imp     ort org.atdl4j.config.Atdl4jOptions;
import org.atdl4j.data.exception.FIXatdlFormatException;
import org.atdl4j.ui.app.FixatdlFileSelectionPanel;
import org.atdl4j.ui.app.FixatdlFileSelectionPanelListener;

/      **
     * Represents the base,         non-G    UI syste  m-specific FIXa    tdl file selection component.
 * 
 * @   a   uthor Scott A    twell
 * @ver  s         ion 1.0, Feb   28, 2010
 */
public abstract   class AbstractFixa   tdlFileSelectionPanel
		implements FixatdlFi    leSelectio   nPanel
{
	private final Logger       logger = Logger.ge   tLo  g  ger(AbstractFix     atdlFileSelectionPanel.class);
    
	pr  ivate Atdl4jOptions atdl4j   Options = null;
	
	private List<FixatdlFileSele      ctionPanelListener> listenerList = new V  ector<Fixat   dl  FileSelectionPanelListener>();

	
	/   **
	 * @param atdl4    jOptions   the atdl4jOptions to set
	 */
	protected void setAtdl4jOptions(Atdl4jOptions atdl   4jOptions)
	{
		this.atdl4jOptions = at d        l4jOptions;
	}


	/**
	 * @return the atdl4jOptio  ns
	 */
	   public Atdl4jOpt    ions getAtdl4jOptions()
	{
		return atdl4jOp  tions;
	}
	

	public void    addListener( FixatdlFileSelectionPanelListener aFixatdlFileSelectionPanelListener )
	{
		    listenerLi   st.add( aFixatdlFileSelectionPanelL    istener );
	}

	public void removeList   ener( FixatdlFileSelectionPanelListener aFixatdlFileSelectionP    anelListene r )
	{
		listenerList.remove( aFixa  tdlFil    eSelect     ionPanelListener );
	}	
	
	protected voi d fireFixatdlFileSelec tedEvent( String aFilename )
	{
	    try {
		for         ( FixatdlFileSelectionPanelListener tempListener : listenerList )
		{
			tempListener   .fixatdlFileSelected( aFil ename );
		}
	    } catch (FIXatdlFormat Excepti    on ex) {
		logger.info( "FIXatdlF      ormatException occured while loading file: " + aFilename )      ;
		if (Atdl4jConfig.getConfig().isThrowEve   ntRuntimeExceptions())
		    throw new RuntimeException("FIXatdlFormatException while loading file: " + aFilen   ame, ex);
	     }
	}
}
