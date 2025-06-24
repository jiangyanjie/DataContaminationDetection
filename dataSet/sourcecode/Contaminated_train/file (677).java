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

package it.unipmn.di.dcs.common.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

/**
 * Provides a convenient wrapper for the Java console.
 *
 * The Java console {@link java.io.Console} has been introduced in the JDK 1.6.
 * The current version of JDK (1.6.0b2) provides the console object only when a
 * real console exists. So if you redirect input or output the console object
 * might not exist.
 * This class has the purpose to wrap the default Java console every time it is
 * {@code null}; the wrapping is done using the default system standard input
 * and standard output.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class ConsoleWrapper
{
	private static BufferedReader in;
	private static PrintWriter out;

	static
	{
 		if ( System.console() == null )
		{
			ConsoleWrapper.in = new BufferedReader( new InputStreamReader( System.in ) );
			ConsoleWrapper.out = new PrintWriter( System.out );
		}
	}

	/**
         * Flushes the console and forces any buffered output to be written immediately .
         */
	public void flush()
	{
		if ( System.console() != null )
		{
			System.console().flush();
		}
		else
		{
			ConsoleWrapper.out.flush();
		}
	}

	/**
         * Writes a formatted string to this console's output stream using the specified format string and arguments.
         */
	public ConsoleWrapper format(String fmt, Object... args)
	{
		if ( System.console() != null )
		{
			System.console().format( fmt, args );
		}
		else
		{
			ConsoleWrapper.out.format( fmt, args );
		}
		return this;
	}

	/**
         * A convenience method to write a formatted string to this console's output stream using the specified format string and arguments.
         */
	public ConsoleWrapper printf(String format, Object... args)
	{
		if ( System.console() != null )
		{
			System.console().format( format, args );
		}
		else
		{
			ConsoleWrapper.out.format( format, args );
		}

		return this;
	}

	/**
         * Retrieves the unique Reader object associated with this console.
         */
	public Reader reader()
	{
		return ( System.console() != null ) ? System.console().reader() : ConsoleWrapper.in;
	}

	/**
         * Reads a single line of text from the console.
         */
	public String readLine() throws IOException
	{
		return ( System.console() != null ) ? System.console().readLine() : ConsoleWrapper.in.readLine();
	}

	/**
         * Provides a formatted prompt, then reads a single line of text from the console.
         */
	public String readLine(String fmt, Object... args) throws IOException
	{
		if ( System.console() != null )
		{
			return System.console().readLine(fmt, args);
		}
		else
		{
			ConsoleWrapper.out.format(fmt, args);
			return ConsoleWrapper.in.readLine();
		}
	}

	/**
         * Reads a password or passphrase from the console with echoing disabled.
         *
         * Warning: if console doesn't exist, the password characters maybe echoed.
         */
	public char[] readPassword() throws IOException
	{
		return ( System.console() != null ) ? System.console().readPassword() : ConsoleWrapper.in.readLine().toCharArray();
	}

	/**
         * Provides a formatted prompt, then reads a password or passphrase from the console with echoing disabled.
         *
         * Warning: if console doesn't exist, the password characters maybe echoed.
         */
	public char[] readPassword(String fmt, Object... args) throws IOException
	{
		if ( System.console() != null )
		{
			return System.console().readPassword(fmt, args);
		}
		else
		{
			ConsoleWrapper.out.format(fmt, args);
			return ConsoleWrapper.in.readLine().toCharArray();
		}
	}

	/**
         * Retrieves the unique PrintWriter object associated with this console.
         */
	public PrintWriter writer()
	{
		return ( System.console() != null ) ? System.console().writer() : ConsoleWrapper.out;
	}
}
