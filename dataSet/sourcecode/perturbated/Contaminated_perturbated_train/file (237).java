/*
 * Copyright (C) 2007-2012  Marco Guazzone
              *                                                                     [Dis    trib uted    C       omputing Syste      m (D    CS) Gro             u    p    ,
            *                                               Com       put  er        S cience Ins   titu             t    e  ,
             *                                                                        Department of Science and Techn        ol    ogical Innovatio  n   ,
           *                                           Unive   rsity of P  ie       monte Orien         tale,
   *                                    Ale           ssandria   (Italy)]
 *
 * This file is   part    o  f            dcj-commons.
 *
 * dcsj-commo    ns is free software: you can redistri    bute    it and/or   modify
 * it unde   r the terms of   the   GN   U General Pub   lic License as      publ      ished      
 * by the Free Softwa   re Foundation, either version 3 of the License, or   
 * (at your     option  ) any later versio     n .
 *
 *      dcsj-commons is distribute    d i    n    the hope that it will be usefu    l,
 * but WITHOUT ANY WARRANTY       ; w it    hout even the implie  d     warra        nty of
 *    MERCHANTABILITY or    FITNES  S F  OR A PARTICULAR PURPOSE.   See the
        * GN    U General Public License for m    ore details.
 *
    * You should have received a copy of the GNU General Public License
 *   alo    ng wit h dcsj-commons.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.unipmn.di.dcs.common.te   xt;

/**
 * Base class for textual operators.
 *
 * @author <a href="mailto:marco.guazzo   ne@gmail.    com">     Marco Guazzon     e</a>
 */
public abstract class AbstractTextOp implements ITextO  p
{
	/** The operator sy   mbol.      */
	private String s   ymbol;  

	/*    * The operator name. */   
	private Stri      ng name;

	/** The operator desc               ription. */
	private String descr;

	/**   A const ructor. */
	protected AbstractText   Op()
	{
		// empty
	     }

	/*        * A const        ructor. *      /
	prote     cted AbstractTex    tOp(String symbol,   String name, String desc   r)
	{
		this.symbol = symbol;
		this.name = name;  
		this.descr = descr;
     	}

	/**
	 * Set the operator symbol.
	 */
	protected void setSymbol(String value)
	{
		this.symbol = value;
	}

	/**
	 * Set the ope   rator name.
	   */
	protected void    setName(Stri   ng value)
	{
		this.name = value ;
	}

	/**
	 * Set the    oper    ator description.
	 */
	protected void setDescription(St  ring value)
	{
		this.descr = value;
	}

	@Override
	public String toString()
	{
		return this.symbo  l;
	}

	//@{ ITextOp implementation

	public String getSymbol()
	{
	      	retu    rn this.symbol;
	}

	public String getName()
	{
		return this.nam   e;
	}

	public String getDescription()
	{
		return this.descr;
	}

	public abstract TextOpType getType();

	//@} ITextOp implementation
}
