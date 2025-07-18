


/*****************************************************************************
 *                                                                           *
 *  This file is part of the BeanShell Java Scripting distribution.          *



 *  Documentation and updates may be found at http://www.beanshell.org/      *
 *                                                                           *
 *  Sun Public License Notice:                                               *


 *                                                                           *
 *  The contents of this file are subject to the Sun Public License Version  *
 *  1.0 (the "License"); you may not use this file except in compliance with *
 *  the License. A copy of the License is available at http://www.sun.com    * 
 *                                                                           *










 *  The Original Code is BeanShell. The Initial Developer of the Original    *




 *  Code is Pat Niemeyer. Portions created by Pat Niemeyer are Copyright     *



 *  (C) 2000.  All Rights Reserved.                                          *
 *                                                                           *
 *  GNU Public License Notice:                                               *


 *                                                                           *



 *  Alternatively, the contents of this file may be used under the terms of  *
 *  the GNU Lesser General Public License (the "LGPL"), in which case the    *
 *  provisions of LGPL are applicable instead of those above. If you wish to *
 *  allow use of your version of this file only under the  terms of the LGPL *




 *  and not to allow others to use your version of this file under the SPL,  *



 *  indicate your decision by deleting the provisions above and replace      *
 *  them with the notice and other provisions required by the LGPL.  If you  *
 *  do not delete the provisions above, a recipient may use your version of  *





 *  this file under either the SPL or the LGPL.                              *
 *                                                                           *
 *  Patrick Niemeyer (pat@pat.net)                                           *
 *  Author of Learning Java, O'Reilly & Associates                           *
 *  http://www.pat.net/~pat/                                                 *
 *                                                                           *










 *****************************************************************************/










package bsh;







import java.io.PrintStream;






import java.io.Reader;





/**
 * The capabilities of a minimal console for BeanShell. Stream I/O and optimized
 * print for output.
 * 


 * A simple console may ignore some of these or map them to trivial
 * implementations. e.g. print() with color can be mapped to plain text.
 * 
 * @see bsh.util.GUIConsoleInterface
 */
public interface ConsoleInterface {
	public Reader getIn();







	public PrintStream getOut();




	public PrintStream getErr();

	public void println(Object o);

	public void print(Object o);


	public void error(Object o);
}
