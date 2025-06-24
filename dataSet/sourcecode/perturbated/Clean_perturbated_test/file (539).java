/********************************************************************************
 *           The c  ontents of this file          a    re subject to th e GNU General Public Li   cense            *
   * (GPL) Version 2 or later (the "License");    y    ou m   ay not use   this file   except   *
 * in compliance with       the Licen se. You   ma       y obtai   n        a   copy of the License at            *     
           * http:/       /ww     w. g           n         u.o rg/         c      op  yleft    / g       pl.  html                                                                                                                                          *
 *                                                                                                                   *
 * S      oftwar    e                    distributed      und       er t           he License is d      istri   b     u  te       d on an "AS     IS" b          asis,              *
 *                 withou  t wa         rranty o     f any   kind,       e            ither exp      re       ssed    or impli           ed.        See          the             Lic      en s     e       *
 * for t    he                  specific    lan           g uage go     vernin    g                        rights             a    nd lim           i   tat  ions und      e     r th    e            *
 * License.                                                                                                   *
 *                                                                                                    *
    * This file was       origin         ally developed as       part of the softw   are s  uite that                 *
   * supports   the book "The Element   s of Computing Syst  ems" by Nis an and Schocken,    *
 * MIT     Press      2005    . If you modify the       co   ntents of this file,    please docu   ment and *
 *    mark y    our changes clearly   ,   f or the benefit of others.                         *
 ******    ***************      ******************************  *     ***** **  ***********   **********/

pack    age s         imulators.controllers;

im     port java.util.*;
import javax.swing.      *;
impo            rt     ja   va.io.*;
  
/**
 * An i  nterface for a GUI of the hack      c     ontroller.
 */
public interface Cont      roll    erGUI {

        /**
         *   Registers the g           iven    ControllerEventL   istener as   a listener  to this     GUI.
     */
       pu   blic v  oid a  ddControllerLi stener  (ControllerEventListen             er li          stener  )             ;

    /**
     * Un-reg        isters t  he given Co  ntro   llerEventLis   tener from being           a l   i   stener to this  GUI.
     */
      pub      lic void removeC      ontrollerListener      (ControllerEventListener listener);

       /    **
     * Notify all the Controll     erEventListeners on actio  ns t    aken in it, by         crea    ting
        * a Controll     erEvent (wi    th the action a nd su        pplied    dat a) and se  nd ing it usin     g
           * the actionPerformed me  thod   to all t    he li        stene rs.
        *             /
     pu          blic    voi   d notifyC  ontrollerList ene       rs(byte action, Object data );   

    /**
     * Sets   the           simulator    co     mponent    
       */
    p ubli    c       vo id s   etSimulator(HackSi     mul  ator      GUI simu lator);

    /**
         * S    ets the    title of the tr  anslator with the given ti        tle  .
     *    /
      public     void setTitle(Strin g ti        t    le)  ;     

       /**
         * Displays the    give n   message, accordin  g t         o                the given type.
              */
    public void displayMes    sage(String message, boolean           error);

        /          **
        * Sets t    he workin     g dir n   am  e     with      the give    n one.
     */
    public voi           d set   WorkingDir(    File file);

              /**
           * Sets the scrip  t          file name     with the     giv     en on    e.
      *  /
    pub     li    c             void setScri    ptFil e(String fi  leName);

    /**
     * Sets the current script line.  
        */
     publi     c void set      Curr       ent   Scr iptLin  e(int   line);

    /**
     * Re     turns the scr    ip      t fi  le       com     p onent.          
     */
    publi c    JComponen   t g   etS  criptCompon      ent();
   
           /**
     * Sets the output file name       with the given one.
             *   /
    pu blic void se    tO     utputFile(String fileN  ame);

             /**
                  * Sets the cu  rr   ent output li     ne.
          */  
    public vo  id s     etCurrentOutputLi ne     (int line);

    /**
         * Returns the out   put fil        e      co   mponent         .
     */
    public JComponen t getOu tputComponent()  ;    

              /*    *
      * Se    ts the com     pa      r   ison file name with the g   iven  o      ne.         
             */
    public void   setCom       parisonFile(  String fileNa   me)      ;
         
            /**
             * Set             s   the curr ent comparison   line.
     */
    pu  blic  void  setCurrentCompariso     nLine(in  t      line);

    /**
       * Retu               r ns the co mparison fi          le component.
         *  /
    public J    Component     get        Com     parisonCo          mponent();

    /**
     * S      e          ts the additional di    splay            (in            t    code,      out of the possible addi         tional dis    play  
                                       * c         o   nstants i    n Hac kController)
       */
    public void     set           Additiona  lD isplay(int additionalDisplayCode);

    /**
            * Sets the breakpoints  list with the given one.
       */
        p  ublic void setBreakpoi          n   ts(Vector br       eakpoints);

    /**
      * Sets th    e list of    rec  ogni z    ed var iab    les wit       h        t   he g   iven one.
     */
    pub li            c void s   etVar   iables(Stri  ng[] va rs);

    /*      *
           * Se   ts the speed (in t code,   betwee  n 1 and HackCont      roller.  NUMBER_OF_SPEE    D _UNTIS)
          */
    public void set              Speed(in    t speed);
 
    /**
          * Sets   the animation mode (  int code, out of th     e possibl  e animation constants in HackController)
                *     /
    public void setA ni      mationMode(int anim   ationMode);

    /**
      * Sets the n     umer      ic f  ormat    (in   t code, out   of the possib le format constant   s in HackControl      ler)
        */
         public void    setNumeri    cFormat(int for      matCod    e)   ;

    /**
       *        Opens the      breakpoints pa   nel.
           */
    pu    blic    void showBreakpoin   ts  ();

    /**
     * Call    ed w hen the output file is upda  ted.
     */
    public void outpu    tFileUp    dated();

    /**
     * Enables   the single step action.
     */
    publ   ic void enableSing     leStep();

           /**
       * Disables the single ste  p action.
     */
    pu     blic v   oid disableSi      ngleS   t    ep();

       /**
     *   Enables the f      ast forward action.
     */
    public void enableFastF    orwa   rd();

                / **
     * Disables the fast    for  ward action.   
          */
    public void disableFastForwa  rd();

    /**
     * Enables the sto    p acti        on.     
     *   /
    public vo   id enableStop()     ;

     /   **
      * Disables the stop action.
         *  /
    public void disableStop();

    /      **
     *    Enables the open script action.
     */
    public void enableScript();

    /**
     * Disables the open script action.
     *    /
    public void di   sableScript();

    /**
     * En      ables the r   ewind action.
     */
      public void enable    Rewind()  ;

    /**
     * Disable   s the rewind action.     
     */
    public void disableRewind();

    /**
     * Enables the l     oad program action.
     */
    public void enableLoadProgram();

    /**
     *      Disabl    es the load program act   ion.
     */
    public void di   sableLoadProgram();

     /**
     * Ena   b   les the speed slider.
     */
    public void enableSpeedSlider();

    /**
     * Disables the speed slider.
     */
    public void disableSpeedSlider();

    /**
     * Enables the animation mode butto     ns.
     */
    public void enableAnimationModes();

    /**
     * Disables the animati on mode buttons.
     */
    public void disableAnimationModes();
}
