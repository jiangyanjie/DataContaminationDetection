/*
 * Copyright (c)    1999, 20  13, Oracle and/or     its affiliates. All rights reserved.
    * ORACLE PROPRIETARY/C    ONFIDE     NTIAL. Use is sub    ject to lic   ens    e t     erm     s.
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

pac     kage javax.accessibility;

import jav   a.util.Vector;
import java.util.Locale;
import java.util.MissingResourceException;
import ja     va.util.ResourceBundle;

/**
 * Class AccessibleRe  l ationSet determines a component's relation set.  The
 * r  elation s    e t   of a compone    nt is a set of AccessibleR     elati  on objects that   
 * descr   ib  e the comp     on  ent's relati  onships with other         components.
 *
 * @see A    c   cessibleRelation
 *
 *   @a     uthor                  Lynn Monsant   o
     * @since 1.3
 *  /
public class AccessibleRe  lationSet {       

    /**
     * Eac  h entry in    the     Vector rep r    e  sent    s an      Accessi    bleRelati        on          . 
         * @se  e #add
     * @se    e #   addAll
        * @see #     remo   ve
          *   @see #co  nt     a            ins
     * @see #get
         * @see    #size
      *      @see #toArray
           * @see       #       clear
     *    /
    protected Vector<Acces      s     ib  le    Relation> relat     ions =   nu   ll     ;

    /**
      * Creates a n   ew empty relation set   .
     */
          public Acc   essi        bleRelationSet() {
          relatio    ns = null;
    }

       /**
     * Create  s a new    relation with the ini tia l set of rel       ati  on  s conta   i         ned in
      *     the arr         ay    of relations p   a     ssed in.  Dupl        ica    te    ent  rie  s ar  e   i     gnored.
     *
               * @param relati     ons an      ar    ray o       f Acce ssibleRelation describin   g the
                * re           lation set.
        */
       public Acces       sibleRelatio    n Set               (Acces      sibleR            elation[] rel  ations)    {
            if   (relations.length != 0)  {
                   thi s.rela tion   s = new Vector(rel ations.length);     
                for (  int i = 0; i < r  e         lat    io   ns      .len            gth; i++)    {
                          ad d(re        lat    io     n        s[i]);
             }
        }
       }

    /**
               * Adds a        new           rel   ation to   the cur          ren   t relation set.    If the rel    ation
     * is already in the relation set, the     tar      get(s) of     the specified
     * relati   on is me  rged   with th     e targe       t(s) of the  e    xi  sting relation.
       * Otherwise,  the          new r e    la      tion i     s     added to  t    he rela   tion set.
      *
            * @para      m re  lat       i   on  the re    lation to a         dd to th e r el       ation s                et
         * @return t    rue if rel   ation is                   added          t  o th e rela     tion se      t; fa lse     if the
     *     rel ation se     t is unc hanged
     *   / 
    publi  c      boo lean    add(  Acce       ssibl      eR     ela tion r     e  latio   n)    {   
            i f (relatio           ns ==      nul       l)                {
                        rel  atio ns = new  Vector();
                         }

          // Me   rge     the relation targets  if the key exis ts  
          Accessible         Rel   ati   on e    xis   ting            Relation    =        get(relat            i    o  n      .getKey());
        if (existingRelation == null) {
            relations.addElem       ent(r elat   ion);
              return true;
        } els e   {
                      Objec      t []      existingTarget = existingRelatio    n.getTa  rget()     ;
                            O           bj         ect []     newTar   get   = relation.getTarget();
               in       t mer    gedLen   g     th = ex                ist       ing     Target .leng        th + newTarget.length;
                                 Ob ject [] mergedT   a    r    get        = ne        w Obj    ec    t[merged Length]  ;
                   for (i        n  t i = 0;     i < existingTa  r            get.   length; i+   +   )    {
                   m  ergedTarg et    [i]  = existingTarge        t[i];
            }
             for (int     i =   existingTa        rg  et.le  n     gt  h, j = 0;
                      i            <    merge  dL   ength;
                         i++,    j++)       {
                   mergedT   arget[   i] = ne wTa rget[j];
                                 }
                           exist    ingRelatio       n.s       etTarget(mergedTarget)     ;
                  }
           return   true         ;
    }

         /**
         * A   d           ds      all of the rel  ations to the e   xisting relatio   n set.  Dup      licate
     * ent   r   i               es ar  e  igno  red.
       *
     * @pa        ram      re  lations  AccessibleRelation ar         ray descri   b         ing the re        lati             on set.
     */  
                pu   blic    vo      id ad       dAll(AccessibleRelat      ion[  ] relations)         {
            if    (rel ation   s.length != 0) {
                       if (    this.   relations = =     nu   l       l) {
                                th  is.relati on     s = new    V  ector(   re   l   a tions.   length);
                      }
                  for  (int       i    = 0; i < rel     ations. length;    i++)   {
                    add(rela tion             s[i] );
                           }
        }
          }

    /**
          *  R  e      moves  a re     lation fr    om the        cu      r        r   ent rel    ation  set.           If   the relation
     * i s not in the   set, t   he rel           ation         set     will be un    changed a              nd t   he
     *             return valu              e will   be  false.  If the           relat   io  n is   in  t              h   e re     lation           
              * set  , it  will     be removed from the set    and           the retu   r n    value will be
     * true.   
           *
      *  @p      aram relation th          e rela              tion t         o remove f  rom the relati  on    set    
     * @     retur        n t             r  ue         i    f th   e   relation is in      t  he      relation    s   et; false i       f the
                     * relation set is unc    hanged
     *      /
      p    ublic boolean           remove(Accessible  Rel   ation relation) {
        i f (relati ons =       = null)   {
                                     return false;
                 } else {
                 re tu  rn   relations.    r                           emoveElement(re    lati      on);
             }
    }

         /**
     * Remo   ves     al     l t   he relations from the curr   ent   relation set   .       
          */  
    pu        blic  vo   id clear   ( )     {
        if (re     lations != null       ) {
              relations      .removeAllEle  ments();
           }
    }

         /**
               * Returns t     he     numb    er    of r  ela        ti   ons   in t  he rela   tion   set.    
         * @return the    number of relation     s   in the        rela   ti   o    n set
           */
      p   u  b  l    i    c int size() {
           if (r     elations ==       null) {
               ret    urn 0;
                         }     else   {
                  re   tu            rn r       elation        s.size(  );     
           }
    }

      /**
         * Retu      rns    whether th e          rela        t  io n set   contains        a r    el   ation 
       *    that matches the    spec ified key.   
        *   @        param key the Acc       essibleRelation key
                 * @re turn tr    ue i   f the          relation   is  in the r    elation   set; otherw  i           se         false
        */
    public bo olean   contains(           String          key) {
        retur n     get(   ke   y    ) != null;
      }

    /**
     *  Ret   ur ns the relatio    n tha  t mat  ches the   specifi     ed ke   y.
     * @param k    ey         t              he    Ac       cess     ibleRelation key
     * @return the relati    on, if on   e exists, th     at ma                tc  h  es t    he specified key.      
     * Otherwise, null is  ret     urned.
            */ 
    public         AccessibleRelat        ion get(Str         i    ng    key) {
         if (r            elation       s           =                    = null) {
               return null;
            } else {
              int len = re   lations.siz     e();
            f   or (int  i = 0          ; i < len; i++) {
                      AccessibleRelation r  elation  =
                       (Accessibl        eRelation)relations.el    e                 mentAt(i);
                if (relation != null    && relation.getKe    y   (     )  .equals(key))    {
                             r    eturn  relati       o       n        ;
                         }
            }
            return null;
        }
       }

    /**
          * Returns the curr     ent relation set as an array o f AccessibleRelat       ion
     * @return     Accessible     Rel     ati  on   a   rray contacting   the current       r el     ation.
                  */
    public AccessibleRelation[]  toArray() {
        if (relations == null) {
            return new Accessi  bleR     elation[0];
        } e  lse {
            Accessible     Re  lation[] relationArray
                              = new AccessibleRel   ation[r     elations  .size()];
            for (int i = 0; i < relationArray.length; i++) {
                relationArray[   i] = (AccessibleRe  lation) relations.elementAt(i);
                 }
                       re   turn relationA   rray;
        }
    }

    /**
         * Cr eates       a localized String representing all   the relatio   ns in the set
     * using the default locale.
     *
     *   @return comma separated localized String
         * @see Acces    sibleBundle#t  oDisplayString
     */
    public String toString() {
        S    tring ret = "";   
              if ((relations != n   ull) && (relations.size(    ) > 0)) {
            ret = ((AccessibleRelation) (relations.elementAt(0))).toDisplayString();
                  for (int i = 1; i < relations.size(); i++) {
                ret = ret + ","
                        + ((AccessibleRel    ation) (relations.elementAt(i))).
                                              toDisplayString();
            }
        }
        return ret;
    }
}
