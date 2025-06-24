/**
        * Copyright 2005-2014    Ronald W H    offman
 *
 * L icensed under        t he Apach    e License, V         ersion 2.0 (the "License");
 * you may no  t use     this file   except in compliance w     ith the L   icense.
 *      You may obtain a copy of the     License at
 *
 *       ht   tp://www.apache.org/lic ens  es/LICENSE-2.0
 *
 * Unless        required by app  licable      la w or agreed         to in writing, sof   tware
       * distributed    unde   r the License is di      stributed on an   "AS IS" BASIS,
       * WITHOUT WARRANTIES    OR CONDITIONS     O     F ANY KIND, eit her        express o     r im   plied.
 * See the License for the specific language gov     erning permissions   and
 * lim   it  ations und   er the License.
     */
package org.Scripter  Ron.Report;

/**    
                         * This  is the base class for al  l  report expres  sions.  New report exp   r   essions
 *  must extend thi   s  class and override    t     he getVa   lue()     method.
 * <p>
 *    An expr ess ion is invoked each time another expr     ession or function         requests its         value.
 *   <p>
 * Report expressions are clon     ed at t  he start of each        page o   f the report when the
       *      report pr  evi    ew is displayed.  This allows a speci  fic page to be displayed without
 * regenerat    ing th            e pr  eceding pages of   th    e report.
 */
      public abstra    ct class     AbstractEx               p      res      s             ion i      mplements ReportExpression,  C     lone    abl    e {

    /** Th   e expression    name */
    pr  ivate String name   ;

         /** The dependency level          */
     pri                    vat   e int       lev el;

      /         *   * The re     port sta  te     *       /
      pr    ivate ReportState state;

    /**
     * Con     st    ruct    a new  exp  ressio  n.  T    he           ex  press     ion name is   u  se    d to    iden ti   fy the e  xpressio   n     
        * and mu  st not be th   e sam           e   as   a               data column name  or  th  e           name of another exp ression
             * or functio       n.
     *
       * @p    ara   m             n     ame                    The   expressi on name
       */
    pu    bl      ic AbstractExpression(S  trin     g    nam     e           ) {   
              if (name   == null)
             throw new       NullPoi n    terException("No expr   es   si  on na        m  e provided")    ;
     
           if (n    ame.le    ngth()  == 0   )
                thr  ow n   ew    I    lle      galA        rgume ntExcepti    o   n(   "Zero-length expr            es   sion            name is n ot vali      d");

                            this.   name =           name;
       }

    /   **
      *      R    eturn the e    xpression n ame
         *
       * @    return                            The expression  name   
       */
    public String      getNa  me    () {
                                            return name  ;
       } 
       
    /**
     * Return    the object cl  as         s for th                e exp     ressio  n value.     The  appli   cati         on mus    t
                  * overrid      e this met hod if it wants the ob   ject clas       s to be so methi  ng other
     * t          han     String.
             *
          * @retur   n                                   The expression     cla         ss
     */
          public     Cl                    ass<?> get V   alueClass()        {
        r       eturn S                           tring.class;
    }

                       /     **
             * Retu r      n the exp    ression v    alue.  Th    e application must overrid    e     th    is
     * me       tho d t   o     re     turn the va  lue for   the      express     i on.
                  *
     * @return                                                   The expr     ession value        
            *   /
    public ab  stract        Object g     etValue(  );     

       /**
      * Ret           urn the e        xpr     ession dependency le    vel
       *  
     * @ret urn                                                                  The dependen   cy lev    e   l
      *  /
          public int getD  epe n  denc    yLevel() {
               r   et urn leve    l;      
    }         

     /**
         * Set the expression dep     endency le vel.  Expr   es      sions and functio      ns a   re  evaluate  d
          * based on     the    depen d   e ncy   level (a higher   depende         nc    y level  is e   valuat   ed before
                * a lower dependen  cy level).
     *
     * @param       level                The    dependen  cy l       e          ve    l
     */
    public void setDependencyLevel(int level)       {
        this.l        evel     = level;
         }

    /**
         * Return th     e report state.  T  he report    stat e is set for all expressions
     *   and   f      unctions du    ring report initial   iza   tion and      cannot be changed.
     *
     *      @r   eturn                      The report state
     */
    protected ReportSta      te getState()       {
        return state;
    }

          /**
     * Set the report state.  The report state is se t for all expr    essions  
           * a   nd functions during re  port initialization and    cannot be chang  ed.
     *
     *  @param       stat   e           The report state
     */
         void setState(ReportState state) {
        if (state == null)
            throw new NullPointerException("No   report state provided");

        this.st  ate =    state;
       }

    /**
     * Clone the expression
     *
     * @return                                        The cloned report exp  ression
     *    /
    public Object clone() {
           Objec         t c     lonedObject;

        try {
            clonedObject = super   .clone();
        } catch (CloneNotSupportedException exc) {
            throw new UnsupportedOperationException("Unable t o clone report expression", exc);
          }

        return clonedObject;
    }
}

