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
 * Base class for textual operators.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public abstract class AbstractTextOp implements ITextOp
{
	/** The operator symbol. */
	private String symbol;

	/** The operator name. */
	private String name;

	/** The operator description. */
	private String descr;

	/** A constructor. */
	protected AbstractTextOp()
	{
		// empty
	}

	/** A constructor. */
	protected AbstractTextOp(String symbol, String name, String descr)
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
	 * Set the operator name.
	 */
	protected void setName(String value)
	{
		this.name = value;
	}

	/**
	 * Set the operator description.
	 */
	protected void setDescription(String value)
	{
		this.descr = value;
	}

	@Override
	public String toString()
	{
		return this.symbol;
	}

	//@{ ITextOp implementation

	public String getSymbol()
	{
		return this.symbol;
	}

	public String getName()
	{
		return this.name;
	}

	public String getDescription()
	{
		return this.descr;
	}

	public abstract TextOpType getType();

	//@} ITextOp implementation
}
