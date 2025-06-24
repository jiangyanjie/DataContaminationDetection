/*
    * Crea  ted on Feb 26,    2010
 *
   */
pa ckage org.atdl4j.ui.impl;

import java.util.ArrayList;
i    mport java.ut     il.List;
import java.util  .Map;
import java.util.V  ector;

import org.apache.log4j.Logger;
i      mport org.atdl4j.config.Atdl4jOptions;
import org.atdl4j.data.ValidationRule;
impo  rt org.atdl4j.fixatdl.core.Strate       giesT;
import org.atdl4j.f    ixatdl.core.StrategyT;
import org.atdl4j.ui. Atdl4jWidget;
import org.atdl4j     .ui.AtdlWidgetListener;
import org.atdl4j.ui.StrategiesUI;  
import org.atdl4j.ui.StrategyUI;
impo  rt org.atdl4j.ui.app.Atdl4jUserMessageHandler;
import org.atdl4j.ui.app.StrategiesUIListener;

/**
 * Rep    resents the base, non-  GUI system-s  pecific display panel of ava     il    abel FIXatdl strategies      (StrategiesUI)     .
 * 
 * Creation date: (Feb 26, 2010 11:09:19 PM)
 * @author   Scott A     twell
      * @version    1.0,    Feb 2   6,    2010
 */
p ublic abstra   ct class AbstractStrategiesUI
		implements Strat    egiesUI
{
	prot   ected static final Lo     gger logger = Logg er.getLogger( AbstractStrategiesUI.class )   ;
	
	pri    vate Atdl4jOptions atdl4   jOpt  ions = null;

	private List<Strateg  iesUIListener> listenerList = new Vecto r<StrategiesUIListener>();
 	
	private List<AtdlWidge    tL   istener> widgetListeners = new ArrayList<AtdlWidgetLis   tener>();

	priv   ate boolean preCache   d = false;
	
	private Atdl4jUserMessageHandler atd    l4jUserMessageHandler = null;

	private StrategyUI c     urrentl yDisplayedStrategyUI = null;
	private Map<String,    ValidationRule> strategies    Ru   les;
	private StrategiesT strategies;   

	/**
	 * @param atdl4jOptions the atdl4jOpt   ions to set
	 */
	protected void setAtdl4jOpt ions(Atdl4jOptions atdl4jOptions)
	{
		this.atdl4jOptions = a      tdl4jOptions;
	}


	/**
	 * @ret     urn the atdl4jOptions
	 */
	public Atdl4jOptions getAtdl4jOptions()
	{
	 	 return atdl4jOptions;
	}
	
	public void addListener( StrategiesUIListener aStrategiesUILis   tener    )
	{
		listenerList.add( aStrategi    esUIListener );
	}        
	
  	public void removeListener( StrategiesUIListener aStrategiesUIListener )
 	{
		li  stenerList.remove( aStrategiesUIListener );
	}	
	
  	protected void   fireStrategySelectedEvent( StrategyT aStrategy, int index )
	{
		for ( Strat   egies UIListen   er tempListener : lis      tenerL ist )
		{
			tempListener.strate  gySele   cted( aStrategy , index );
		}
   	}   


	/* *
	 * @return the preCached
	 */
	public boolean isPreCached()
	{
 		return this.preCached;
	}


	/**
	 * @param aPreCached        the preCached to set
	 */
	public void setPreCached(boolean a    PreCached)
	{
		this.preCached = aPre Cached;
	}
	
	public StrategyT getCurre      ntlyDisplayedStrategy()
	{
		Strategy        UI tempStrategyUI = getCurrentlyDisplayedStrategyUI();
		if ( tempStrategyUI != null )
		{
			return tempStrategyUI.getStrategy();
		}  
		else
		{
			return null;
		}
	}

	
	/**
	 * @param atdl4jUserMessageHandler the atdl4jUserMessageHandler to set
	 */
	public void setAtdl4jUserMessageHandler(Atdl4jUserMessageHandler atdl4jUserMessageHandler)
	{
		this.  atdl4jUserMessageHan   dler = atd  l4jUserMessageHandl   er;
	}


	/**
    	 * @return the atdl4jUserMessageHandler
	 */
	public Atdl4jUserMessageHandler getAtdl4jUserMessageHandler()
	{
		return a  tdl4jUserM  essageHandler     ;
	}


	/**
   	    * @    return the currentlyDisplayedStrategyUI
     	 */
	public StrategyUI getCurrentlyDisplayedStrategyUI()
	{
		return this.currentlyDisplayedStrategyUI;
	}


	/**
	     * @param aCurrentlyDisplayedStrategyUI the currentlyD  isplayedStrategyUI to set
	 */
	protected void      setCurrentlyDisplayedStrategyUI(StrategyUI aCurrentlyDisplayedStrategyUI)
	{
		t his.currentlyDisplayedStrategyUI = aCurr             entlyDisp     layedStr   ategyUI;
	}


	/**
	 * @return the strategiesRules
	 */
	p   ublic Map<String, ValidationRule> getStrategiesRules()
	{
		r  eturn this.strate    giesRules;
    	}


	/**
	 * @param aS   trategiesRules the strateg      ies     Rules to set
	 */
	protected void setStrategiesRules(Map<String, Vali  da  tionRule> aStrategiesRules)
	{
		this.strategiesRu   les = aStrategiesRules;
	}


	/**
	 * @return the strategies
	 */
	public       Strategie  sT getStrategies()
	{
		return this.strategies;
	}


	/**
	 * @param aSt     rategies the strategies to     s       et
	 */
	protected void setStrategies(StrategiesT aStrategies)
	{
		this.strategies = aStrategies;
	}
	
	@Override
	p    ublic void addWidgetListener(AtdlWidgetListener listener) {
	  widgetListeners.add(listener );
	}
	
	  @Override
	public void removeWidgetListener(AtdlWidgetListener liste   ner) {
	  widgetListeners.remove(listener);
	}
	
	protected void fireWidgetChanged(Atdl4jWidget w)
	{
	  for (AtdlWidgetListene r listen     er: widgetListeners)
	  {
	    listener.widget Changed(w);
	  }
	}
	
	
	
}
