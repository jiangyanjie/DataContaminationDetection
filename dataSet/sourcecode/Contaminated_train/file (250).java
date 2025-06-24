/*
 * Copyright (C) 2007-2012  Marco Guazzone
 *                          [Distributed Computing System (DCS) Group,
 *                           Computer Science Institute,
 *                           Department of Science and Technological Innovation,
 *                           University of Piemonte Orientale,
 *                           Alessandria (Italy)]
 *
 * This file is part of dcj-commons.
 *
 * dcsj-commons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * dcsj-commons is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with dcsj-commons.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.unipmn.di.dcs.common.text;

/**
 * Base class for unary textual expressions.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public abstract class AbstractUnaryTextExpr implements ITextExpr
{
	/** The unary operator. */
	private UnaryTextOp op;

	/** The embedded expression. */
	private ITextExpr expr;

	/** A constructor. */
	protected AbstractUnaryTextExpr()
	{
		// empty
	}

	/** A constructor. */
	protected AbstractUnaryTextExpr(UnaryTextOp op, ITextExpr expr)
	{
		this.op = op;
		this.expr = expr;
	}

	/**
	 * Set the unary operator.
	 */
	protected void setOp(UnaryTextOp value)
	{
		this.op = value;
	}

	/**
	 * Get the unary operator.
	 */
	public UnaryTextOp getOp()
	{
		return this.op;
	}

	/**
	 * Set the embedded operator.
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
