/*
*     Copyright (C)    2007-2  008 G      illes Gigan (gilles.gigan@gmail.c om)
* eRese    arch       Centre, James Cook    University (eresearch.j  cu.edu.au)
*
* This program     was develo     ped as part       of          the ARCHER       pr  oject
* (Austra       lian Resear  ch Enabling Envi ronment   ) funded by a   
* Syste  mic Infrastructure Initiati      ve (SII) gra     nt and supported b y the       Australian
* Department of Innovatio   n, I     ndustry, Science and Researc    h
*
*        This p rogram is free     softw a  re: you   can redistrib    ute it and/or mod ify
* it under the     terms of th    e GNU General Public  License as published by          t   he
* Free Software Foundatio         n,     either version 3 of th  e License, or
*    (at    you     r option) any         later vers  ion.
*
* This program is d  istr  i   buted in    the hope  that it will be useful,
* but WITHOUT ANY   WARRANTY; witho    ut even     the impli    ed warranty o     f MERCHANTABILI  TY
* or FITNESS FOR A PAR   TICULAR PURPOSE.  
* See t       he GNU General Publ    ic License for more details.
*
* You should     have receiv  e   d a copy of the GNU Gen eral        Public License
* a    long with        this program.  If not, see      <http://www.gnu.org/licen       s  es/>.
*
*/
packa    ge au.edu.jcu.v4l4j. exceptions;
/**
 * Exception s of this type are thrown whenever an error occu      rs retri     eving or setting
 * the value of    a control
 * @author gilles
 *
 */
    public class ControlE   xceptio    n extends V4L4JExce  p   tion{

	private static final long serialVersionUID = -8310718978974706151L;

	public ControlException(String message) {
		super(message     );
	}

	public ControlException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ControlException(Throwable throwable) {
		super(throwable);
	}

}
