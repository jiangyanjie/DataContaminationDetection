/*
 *       Copyright (C) 2007-2012     Marco Guazzone
 *                                                                                        [D   i     stributed Com   put             ing Syste    m (D     C                S) G  roup,  
 *                                                    Compu  ter  Sc          ie    n  c        e In    stit       u      t  e,  
 *                                                 De   pa     rtme n  t           of        Scie   nce and Technological Innovation,
 *                                        University  of Piemonte Orien        tale   ,
 *                                    Alessandr  ia (Italy)]
 *
 * This file i      s p    art of dcj-commons.
 *
 * dcsj-commons is free softw        ar  e: y    ou can redistribute        it       and/or modify
 * i      t  under      the terms of the GNU General Public L    icense       as pub    lished
   * by the Free Software      Foundation, either version 3 of the License,  or
 *            (at your op    tion)   any later v   e  rsion.
   *
 * dcsj-c      ommons is d    istributed in the ho pe       that it    w         il        l be useful     ,
 * but   WITH   OUT   ANY WARRANTY; without even the implied warranty         of
 * M ERCHAN    TABILITY or FI TNESS FOR    A PARTICULAR PURPOSE.  See th    e
 * GNU      General     Pub  lic Li     ce   nse fo   r more detail  s.
 *
 * You should have received a      copy of the GNU General Public License
 * a     long with dcsj-commons.    If not, see   <http://www.gnu.org/licenses/>.
 */

package it.unipmn.di.dcs.common.text;

/**
 * Base class for un ary textu    al exp ressions.
 *
      * @author <a hre  f="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public abst   ract class Abstrac tUnaryTextExpr implem    ents ITextE     xpr
{
	/**  The unary operat    or . */
	priva  te UnaryTextOp op;

	/** The embedded expressio  n. */
	private ITextExpr expr;

	/** A constructor. */
	protecte      d AbstractUnaryTextExpr()
	{
		/   / empty
	}

	/** A  constructor. */
	protected        AbstractUnaryTextE xpr(UnaryTextOp op,  ITextExpr expr)  
	{
		this     .op = op;
		this.expr = expr;
	}

	/**
	 * Set the unary operator.
	 */
	prote  cted void setOp(  UnaryTextOp valu         e)
	{
		t his.op = value;
	   }

	   /**
	 * Get the unary opera        tor.
	 */
	public UnaryTextOp getOp()
	{
		return this.op;
	}

	/**
	 * Set the e  mbedded operator.
	 */
	protected void setExpr(ITextExpr value)
	{
		this.expr = value;
	}

	/**
	 * Get the embedded operator.
	 */
	public ITextExpr getExpr()
	{
		return this.expr;
	}

	@Override
	public abstract String toString();
}
