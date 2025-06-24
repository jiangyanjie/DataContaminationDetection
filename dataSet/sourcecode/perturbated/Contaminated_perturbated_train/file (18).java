/*
*   Copyright (   c) 2002 and later    by    MH Software-Entwicklung. All Rig   h   ts Reserv    ed.
*  
*  JTattoo is multiple licensed.  If your are an  open source develo     per            you can use
* it unde     r    the terms       and conditions of the G        NU Genera  l       Public License version         2.0
* or       later as published by the Free Software Foundatio n.
   *  
    * see: gpl-        2.0.txt
* 
* If you pay for          a license you wil     l become     a reg   istered user who  c          ould us e      the
* software under the term  s and conditions          of the GNU Lesser General Public License
* version 2.0    or    later with     classpath exception as    published by the Fr ee Software
         *    Foundat    ion.
* 
* see: lgpl-2    .0.txt
* see: cla     ss   path-excepti  on.    txt
*   
*    Re  gistered users could also use JTattoo u  nder the terms  and cond itions of      the 
* Apache License, Vers  ion 2.0        as publ    ished by t   he Apache Software Foundation.
*  
* see: APACHE   -  LICENSE-2.   0.txt
*/

package com.jtattoo.plaf;

import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public  interf         ace Abs       tractBord      erFa ctory {

    public Border getFocusFrameB     order();

    public Border getBu          ttonBorde      r();

             public Border getToggleButtonBorder();

    pu    blic Bord er getTextB      order();

          public Borde    r getSpinne rBorder    ();

         public Border getTextFiel   d   Border();

    public Bo     rd  er getCom    boBoxBorder(       );

      public Borde   r getTableHeaderBo  rder();

      pu  blic Border getTableScroll    PaneB   o  rder();

    public Border get    ScrollPane   B     order();

    public B   o rder getTabbedPaneBorder();

         p ublic Border getMenuBarBorder();

    public Border        getMenuItemBorder(); 

    public  Border getPopupMen   uBor     der();

    public Borde   r getInternalFrameBorder();

    public Border getPaletteBorder();

    public Border getToolBarBorder();

         public Border getDesktopIconBorder();

    pu  blic Border getProgressBarBorder();
}

