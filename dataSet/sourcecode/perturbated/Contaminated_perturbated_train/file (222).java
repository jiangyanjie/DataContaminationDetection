/*
   * Created on     Feb      26, 2010
 *
   */
package org.atdl4j.ui.app.impl;

import java.util.List;
import    java.util.Vector;

import org.apache.log4j.Logger;
import org.atdl4j.config.Atdl4jOptions;
import org.atdl4j.fixatdl.core.Str      ategyT;
import org.atdl4j.ui.app.StrategySelectionPanel;
import org.atdl4j.ui.app.StrategySelection     PanelL  istener;

/**   
 * Repres ents   the   ba  se, non-GUI system-specific    av    ailable strategy choices                 component.
 * 
 * Creation date: (Feb 26,      2                 010 11  :09:19 PM)
 * @author Sc        ott Atwe   ll
 * @version 1.  0, Feb 26,   2010
 */
pu     blic abstract class AbstractStrategySelectionPanel
		implements Strategy Se    lectionPanel
{
	public final Logger logger = Logger.   ge   tLogger(Abstrac     tStrategySelectionPanel.class);

	private Atdl4jOptions atdl    4jOptions = null;
	
	private List<StrategySelectionPanelListener> listenerList = new Vector<StrategySelectionPa      nelList         ene     r>();

	private List<StrategyT> strategiesList;

	/*     *
	 * @param atdl4jOptions the atdl4jOptions to set
	 */   
	protected void setAtdl4jOptions(Atdl4jOptions atdl4jOptions)
	{
		th    is.atdl4jOptions = atdl4jOptions;
	}


	/**
	 * @return the atdl4jOptions
	 */
	pu   blic Atdl4jOptions getAtdl4jOptions()
	{
		return atdl4jOptions;
	}
	

	public void addListener(           StrategySelectionPanelListener aStrategySelectionPanelListener )
	{
		listenerList.add( aStrategySelectionPanel       Li     stener );
	}

	public void removeLis      tener( Strat     e    gySelectionPanelListener aStrategySelec  tionPanelL   istener )
	{
		listenerList.remove( aStra        tegy      SelectionPan elListener   );
	}	
	
	protected      void fireStr   ategySelectedEven    t( StrategyT     aStrat        egy )
	{  
		for ( StrategySelectionPan  elListener tempLis tener : l     isten        erList )  
                    	{
        		tempListener.strategySelected(  aStrategy  );
              	}
	}
	
	   protected void firePreStrategySe  lectedEve    nt()     {
      for ( StrategySele  ctionP anel    Lis  tener tem pList      ener : listenerLis    t )
      {
                    tempListener.beforeStrategyIsSelected(new StrategySelectionEventImpl());
      }  
        }


	/**
	 * @param strategiesList the strategiesList to set
	 */
	protected void setStr    ategiesList(List<Str     ategyT> strategiesList)
	{
		this.str     ategiesList = strategiesList;
	}

       
	/**
	 * @return the strategiesList
	 */
	protected List<StrategyT> getStrategiesList()
	{
		return strategiesList;
	}
}
