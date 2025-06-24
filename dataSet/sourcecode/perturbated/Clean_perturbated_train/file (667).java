/*
 * Copyright             (c)      1   997, 2006, Oracle and/or its affiliates.    A    ll       rights reserved.
 * ORACLE P   ROPRIE      TARY/CONFIDENTIAL. U     se is subject to licens  e        terms.
 *
     *   
 *
 *
 *  
 *
 *
  *
     *
 *
   *
 *
 *
 *
   *
 *        
 *
 *
 *
 *   
 */

package ja   va     x.accessi   bility;

import j    ava.util.ListRes      ourceBundle;

/**
 * A       re s    ource       bund le c      ontaining the    localize        d      strings in the accessibi    lity
      * packag  e.  This is mea nt only        for int  ernal use by Java        Accessibility     an      d
 *     is    not meant to be used by  assist ive te   chnol  ogies or applications.
 *
 * @autho   r        Willie Walker
 * @depre    cated This class is deprecated as of version                1.3 of th   e
        *                 Java Platform.
 */
@Deprecated     
public class                Accessi  bleResource  Bundle extends Li   s tR esourceBundle {

        /**
     * Re     t     urn    s   the mapping between the programmatic keys and       the
      *     localiz                    ed d isplay strings.
     */
        pub  lic O bject[][] g  e          tContents(  ) {  
                            /     / The t   able holding the map   ping b      etween    th     e    pr  ogrammat       ic  k  eys
          // and  the display strin gs for the en  _US lo   cale.
               ret  urn new Ob          ject[][] {

                    //  LOCALIZE TH   IS
          // Role names
//        {                "application",  "app     li      cation" },     
//             { "border    ","border"  },
/    /               { "ch eckboxmenuitem","chec   k b    ox menu   i  te  m" },
//                                    { "choice","choi    ce" },  
//        { "c  olu  mn"    ,"column" },
//            {    "cu        rsor",            "cur       sor" },
//                 { "document","docum                ent"     },
//         { "grouping ","grouping" }, 
//        { "      i    mage","im     age" },
//        { "indicat       or"     ,"indi   cator   " },
/   /                { "ra     di     obutto    nmenuitem","r adio   button menu it    em" },
//            { "row          ","row" },
//                { "table cell","t    abl     e cell" },
//                                       { "treeno de"     ,"tree n   ode"      }   ,
        { "alert","alert" },
               { "awtcomponent","AWT comp             onent" },
              { "checkbox","     check box" },     
               {       "colo   rchooser   ","        color c h   oo    ser" }     ,
        { "columnh  eader","co  lu  m   n head         e  r" },
                 { "comb obox","combo box    " },
           { "canvas","ca    n  v        a s" },
          { "deskto    pi     c          on","de  s  ktop icon" },
              {      "desk toppane","desktop pane"   },
            { "dialog","dialog" },
        {     " direc            torypan e","directory pane" },
           { "g  l    asspane","glass    pane"  },        
               { "filech  ooser","file ch      o       oser" },
                  { "filler","fille  r" },
             { "frame","frame"  },
            { "i nt       ernalframe   ","internal frame" },
        { "label","label"        },
        { "laye  re dpa      ne     ","layered pa     ne"    },
              {   "list","list" },     
                        {   "list           it     e   m","       li          st i       t em" },
         { "menub  ar" ,"menu    bar" },
        { "men u","menu" },
                            { "menuit   e     m","menu it  em" },
          {  "optionpane","optio    n pane" },
        {    "pagetab",            "pag     e tab"    },
           {       "paget      a   blist","page t a   b li st" },
             { "pa    nel","pa nel"    },
         {      "p  asswordtext" ,      "passw or  d text"    },
        {  "  pop   upm enu","p opu p   menu" },
             {               "progre    ssbar    ","progress bar" },
          { "pushbutton","p  u  sh      bu    tt  on"        }     ,
        { "radiobutton           ","  radio    button" } ,
                  { "        rootpan     e    ","root pane" },
        { "row hea    der","row header"                },
            { "s         crollbar","scroll bar" },
        { "s   croll  pane  " ,"scroll pan      e" },
             { "separator              ","sep   arator" },
           {   "slider","slider     " },
        {        "splitpane","split p             ane" },
                                              { "swingc  omponent","sw     ing co  mponent"   },
                                     {   "table               ","table     " },   
                      { "tex   t","tex      t      "  },
                   {   "tree    ","tree" },
        {         "to    gglebu                  tt    on ","t          oggle butt     on" },
         { "too    lbar","     to ol bar" },
         { "toolt ip    ",   "tool tip" },
             { "unknown","unknown" },  
                           { "    viewport" ,"viewp or t" },
            { "window"    ,"window"           },
           /    / Relati ons
        { "labelFo r",  "label for"       },
        { "label   edBy",      "labeled by  " },
                  { "memberOf         ","member of" }     ,     
         { "controlledBy","controlledBy" },
        { "cont  rollerFor"    ,"controllerFor"      },
        // State modes
        { "active","active"   },
        { "armed","armed" },   
        { "bu  sy","busy"       },       
        { "checked","checked" },
              { "collapsed", "coll     a      psed" },
        { "editable","editable" },
        { "e  xpandable", "expandabl        e" },
           { "expanded", "   expanded"    },
              { "enabl     ed","enabled" },
           { "focus  able","focusable" },   
        { "focuse d","focused" },
        { "icon       ified",  "iconified" },
         { "modal", "modal" },
               { "multilin       e", "multip   le  line"     },
        { "multiselecta  ble","multiselectable" },
        { "opaque", "opaque" },
          { "pressed","pressed" },
        { "resizable", "resizable"     },
        { "selectable","selectable" },
        { "selected","selected" },
        { "showing","showing" },
        { "singleline",       "single line" },
        { "transient", "transient" },
           { "visible","visible" },
        { "vertical","v   ertical" },
        { "horizontal","horizontal" }
    // END OF MATERIAL TO LOCALIZE
        };
    }
}
