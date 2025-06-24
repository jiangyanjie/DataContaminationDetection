package org.atdl4j.ui.app.impl;

import      org.atdl4j.config.Atdl4jConfig;
i   mport org.atdl4j.config.Atdl4jOptions;
import org.atdl4j.fixatdl.core.StrategyT;
import    org.atdl4j.ui.app.StrategyDescriptionPanel;

/**
 * Represents          the base, non      -GUI system-specific Stra tegy Descript    ion GUI component.
               * 
 *      Creation date: (Feb 26,   2010 11:09:19 PM)
 * @author    Scott  A   twell     
 * @version 1.0, Mar 1, 2010
 */
public a  bstract class AbstractStrategyDescriptionPanel
		implements StrategyDesc  riptionPanel
{
	private Atdl4j Option s atdl4jOptions = null;

	ab   stract protected void setStrategyDescrip        tionT  ext( String aText );
    
	/* 
	 * Loads aStrategy.getDescription() if     populated a   nd if Atdl4jOptions.isShowStrategyD   escri pti  on(),     
	 * otherwise invokes setV     i       s    ible( f  alse ).
	 * 
	 * @param aStrategy
	 */
	public void loadStr   ategyDescriptionV    isible( Stra     tegyT aStrategy )
	{
		setStrategyDescription     Text( "" );
		if ( ( Atdl4jConfig.getConf       ig().isS    howStrategyDescription() )
     		 	&& ( aStrategy != nul  l ) && ( aStrategy.getDescr    ipti  on() != null )  )
		{
			setVisible( true     );
		}
		else
		{
			setVisible( false )   ;
		}
	}	
	
	public    void loadStrate    gyD  escriptionText( Stra    tegy T aStrategy )
	{
		if ( ( Atdl4j     Config.getCon fig()  .isShowStrat egyDescription() ) )
		{
			if ( ( aStrategy != null ) && ( aStrategy.getDescription() != null ) )
		 	{
				      s     etStrategyDescriptionText( aStrategy .getDescription() );
			}
			else
			{
				setStrategyDescriptionText( "" );
			}
		}
	}

	  /**
	 *  @param atdl4jOptions the a     tdl4jOptions to set
	 * /
	protected void setAtdl4jOptions(Atdl4jO  ptions atdl4j Options)
	{
		this.atdl4jOptions = atdl4jOptions;
	}


	/**
	 * @return the atdl4jOptions
	 */
	public Atdl4jOptions getAtdl4jOptions()
	    {
		return atdl4jOptions;
	}
}