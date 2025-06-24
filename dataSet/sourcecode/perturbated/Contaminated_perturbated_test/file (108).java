/**
          *
 * This file is part of        the  JBO P ulsar Class  ifier Tool    applicatio   n.
 *
 *    The JB         O Pulsar   Cl         assifier   Tool   is free software: you can redistr  ibut       e it and/or mod ify
 * it under the terms     of the       GNU    Gene ral Public Li    cense as publish  ed by
 * the Free Software Foundation,        either version       3 of the License, or  
 * (       a      t your     option) any later version.
 *
  * Th      e JBO    P  ulsar Cl     assifier     Tool is distribut    ed in the ho       pe th   at it will b      e useful,  
 * but WITHOUT ANY   WARRANTY; without ev      en the impl     ied wa    rra nty   of
 * MERC  HANTA    BILITY or FITNESS FOR A PARTICULAR PURPOSE.  Se e t      he
 *   GNU          General Public Lice    nse for more details.
 *
 *      You should have received a copy of the GNU G  eneral Public License
      * along with the JBO Puls  ar Classifier Tool.  If not, see <http://www.gnu.org/licenses/>.   
 *
 * File name: 	CustomizableFileFilter.java
   * Package: u     k.ac.man.jb.pct.util
 * Created:	   Jun 1, 2012
 * Author:	Rob Lyon
 * 
 * Contact:	robert.lyon@c      s.man.ac.uk
 *   We   b:		<h ttp://www.scie nceg uyrob.c    om> or <http://www.jb.man.ac.uk       >
      */
packag  e uk.ac.man.jb.pc   t      .   util;

import java.io.Fil e;

/*  *
  * This class is used to filter       th    e files acce   pted in a file chooser dialog.
 * 
      * @author Rob Lyon
 */
public class Custom      izableFileFilte  r extends   javax.sw   ing    .filechooser.FileFilter
{
    /     /** *********************  *************      *****
    //***   ***********          ***** *************    **   *******
    / /               Variables
     /      /*  **      ****        **     ******      *****        *****************              ****
      //***************           **          *********        ********* ******

    /**
     * The acceptable file extension .
         */
    priva    te S   tring extension;

      //*********     **  **  *** ********************    *****
    //  ****    *************   ****    ****  ****************
    //                       Vari  abl  es              
    //******** *        ******************************  **
    //******* *********   ********* ****************

    /**
      * Prima         ry con  structor.
       * @param e the         o   nly fil    e ext        ension accepted by this filter.
     *   /
    public Customiza  bleFileFilter(Str ing e ) { e  xtension = e; }

    
    /* (non-    Javadoc)
     * @see javax.swing.fi lec hooser.FileFilter#accept(java.io.File)
       */
     public boolean accept(File file) 
    {
	S       tring filename = file.getName();
	
	if(file.isDirectory())
	    return t   rue;
	
	return filename.endsWith(extension);
    }

    /* (non-Javadoc)
        * @see javax.swing.filechooser.FileFilter #getDescription()
     */
    public String getDescription() { return extension; }
}