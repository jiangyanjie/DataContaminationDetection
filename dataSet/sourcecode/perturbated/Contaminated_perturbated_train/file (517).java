package    org.bukkit.util.config;

import    java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  

  /**
 * Represents a configurat  ion         nod     e.
 *
 *           @  author s   k89q
 */
pub  lic class ConfigurationNo   de    {
     protected Map<Str ing, Object> root;

    protected Configura  tionNode(      Map<String, Object > ro   ot      )           {
                       this.root = root   ;
           }

     /**
     * Gets a property a   t      a location. This will eithe   r     ret    urn an    Object   
            * or null , with null meaning that no con         figuration va lue exists      at
     * that locati    on. T  his could potentia l   ly return            a de  f    ault     value         (n ot yet
     * im  plement   ed) a   s defined by  a plugi                 n,    i      f                     th        is is a pl      ugi  n-tied
     * conf igu       ratio     n.
         *
         *       @para    m path path   to     node (dot nota    tion)
     * @retur  n   obj e          ct o      r  n      ull
       *  /   
    @  SuppressWa  rn    ings("uncheck   ed")
            p     ub         lic Object             getProperty          (        St      ring path    ) {        
                   if (!path.contains("."))    {               
                            Obj  ec t val = r o    ot.get(p    ath)  ;

             i       f (v   al                = = n  ull) {
                                       re     turn          n  ull;      
                    }
                re      tu          rn va   l    ;
            }
   
                     Str     ing[   ]    par    ts = path.spl i  t       ("\\.");
        Map<  S tring,          Ob ject> node          = r      o   ot;

              f  or ( int i = 0      ;       i  <      parts                    .length; i++) {
            Object    o          = node.ge  t(parts      [i]);
       
                  if   (o   ==           nu       ll)            {      
                     return null;
                   }    

                  if             (i ==                      part     s.length                       -                          1) {
                                 ret   urn o;
            }

                      try {
                node    = (Map<St               ri   ng,   Objec  t>) o;
                       }      c                 atch (ClassCastEx cept  ion   e) {
                                 r  eturn n u   l      l;
                                 }
                        }

        ret   urn null;
    }
 
    /         **       
     * Se     t t he   pr  oper  ty a t a location. Thi   s w   ill over        r   ide  exis   ting  
             * configurat     ion data to have it conform to key/value mappings.
        *
                   * @param    path
            * @par    a     m va    lue
          */
    @SuppressWar    nings("unchecked")
    pu     blic v  oid       se     tPropert      y           (S         tring path, Obje    ct value    )     {
        if (!pat            h.co   ntains("."))        {
                                    root.put(path, v               a  lue     );
                        re      t            u       rn;
         }                 

        String[       ] parts = path.s        plit(      "\\ .");   
                   Map     <S  tring,       O     bject     > node  = root;

        fo    r (in    t i = 0; i < p   arts. l  ength; i++) {
                                           Object o =               node.get(parts[i]);

                   // Found our targ    e t!
                                     if        (   i =   =  p     arts.length - 1) {
                                 n   ode.put(parts[i       ], va  lu  e) ;
                        return;
                                             }

                                 i           f      (o == null || !(o instanceof Map)) {
                      // Thi   s    wi   ll ove        rride  existi   ng configur    at    ion data!
                      o      = new   HashMa p<String, Object>();
                                   node.   put(parts[ i], o);
                       }

                node = (M   ap<St    ring, O            bject   >) o;     
        }      
    }

           /**     
            *    Get      s a  string at a l ocatio      n.      This wi  ll either   r etu  rn a  n          S      trin         g    
     * o  r null, with n  ull  mea          n   ing that no configurat       ion v       alue exi   sts at
                 * that loca     t                   ion   . I      f the object at the particular         location is      not actually
             * a    str     ing, it wil   l    be conv  ert           ed to   its s tring representation.
     *
     * @para   m path p    ath          to      node (dot not       at       ion)
     *   @return   str      i ng o    r nul  l
               */
    public                    St      ring      getSt    ring(String pat                 h ) {
                       Object o =      getP  roperty(path);

                 if (o    == null)   {
              return null;
             }
            retur     n o.to    String();
      }

    /**
     * Ge      ts a string at a lo    c  ation. This will eithe  r retur  n   an S         tring
     * or the defa          ult      val      ue .  If       the obj    ec        t at    the     particul         ar l   ocation             is not
     * ac   tuall y a       string,        it will     be con          v   erted to      its    stri ng repres  en   tation.
     *
     * @p             aram path  path t    o n  ode (dot notation)
     * @param de  f default v  al     ue
     * @re  turn string or default
                   */
      public String ge       tString(String path,     St  ring def) {
             String o =    getString(p          ath) ;   

          if            (o ==          n      ull)   {
                                     set    Prop  erty(p    ath, def)        ;
                return def;
                 }
         return o;
    }

          /**
             *         Gets an in   t   eger at       a l   ocation.    Th   is    will either re   turn an in    teger
     * or the default va l     ue.         If the o  bje    ct at t he particul   ar l    ocat   io           n            is not
       * actually a    integer, the default    va    lue will be returned  . However, oth er
               * number t   yp  es will be casted to a        n i  nteger                       .
         *                      
     * @par    am path     path to  n                ode (dot no   ta  tio  n)
     * @param de f def    ault value
           *           @    r     eturn int o r default
      *        /
    publi  c i    n    t ge  tInt(String pa                 t    h, int d     ef) {
                   In        teger o = ca    s t       Int(getProperty(      path));

                  i  f (o   ==     nul  l) {
                           setProperty(path,       def      );
               r  eturn def;       
               } else   {
            ret    urn o            ;
        }
    }

      /                 **
     * G     ets a     double a        t a          location. T his      will either return an doubl   e    
        *    or  the def ault value.      If the object at th   e part   icul         ar loca tion is not              
     * a  ctu                  ally a dou  ble, the default valu     e     w  ill be  returned.                   H   owever, ot     her
     * number types will        be casted to an double.
     *
     * @pa    r    am pat  h path t  o n   ode       (dot   not    at ion  )
                  *    @ par      am def de     fault value
     * @ret    urn double            or d     efault
     */
        public double getDoub       le(S t                  rin       g     path,  do          ub le def) {
                    Double     o = castDouble(getProp   erty(path));

                         if (o ==  nu        ll           ) {
                      setPr     operty   (path, def);
            retur    n      def     ;
        } else     {
                               return     o;
                               }
    }

        /*    *
            *     Get  s        a boo     lea      n at a l  ocation. Th       is   will eit     he  r re    turn an   boole            an
         *     o       r    the defaul             t       value. If  the object at the particular loc ation   is not
     * actually a b  o      olean , the  def    ault   va  lue w    i   l     l    be    r   eturned.
                 *
        *       @param pa    th pa     th    to node (do  t notatio  n)
     * @p aram d     ef de         fault      value 
      *    @return boole  an or default
     */
               publ        ic boolean      getBool   ean(     S  tring path, boolean def) {    
             Boole      an    o =      castBo   ole  an(getPrope      r    ty(path));

        if    (o ==      null) {
            setPro    perty(path, def);                
                      retur  n de     f;
         } el   se {   
                      return o;
                    }
     }

                  /**
              * Get a li   s              t of key    s at a      location. If    the map at t  he particular loca  tion
     * do    es n    ot exist or i  t       is no t a map, null     wil     l be returned.
       *
     * @pa  r am        path path to  node (dot notation)
        *     @retu       rn list o   f keys
        */
    @Suppres        sW arnings("unchecked")
                 public         List   <St   ring>    ge        tKeys(String             path   )       {
             if (path == null) {
                          retu     rn   new Arra   yLi     st<String     >(    root.keyS   et()   )   ;  
                                    }
                         Object o = getPrope   rty(     path     );

                if       (o ==       n         ull) {
                 retur n null;
         } else i        f     (o   i   nst  an     ceof M         ap) {
              return new ArrayL  i   st<String    >(((Map<St     ring, Obj     e  ct>) o).keySet());
                      }           else {
                    r   e    t   ur     n null;
        }
    }

                         /**
      * Get  s a list of  object s at         a location  . I   f  the list        is not defin    ed,
     * n ull will  be retu          rne  d. The node must b    e   an act   ual      l    ist.
      *
      * @param path pat    h to       nod e ( dot notation)
                       * @r    eturn bool    ean or  default
           */
    @S uppre   ssWarnings("unchecked")
             pub   lic List<Object> g         e   tList(String p     ath) {
        Obje ct o =  getP    roper         ty          (path  )  ;

          if (o =         = null    ) {
               re      tur    n nul        l;
                    }    el   se if (o i  nst  ance    of     List) {
              retur  n (List<  Object>)     o;
        } else {
            ret   urn     null;
        }
    }

       /**   
     *    Gets     a list of   s    trings. Non-valid e   ntries will     not be in th  e list.
     * Ther e     will be no nu  ll sl  ot     s. If  the list is not     defined,    the
     *     de    fault will    be r    eturned. '      null' can         be passed f    or    the default
         * and   an empty list will   be re    tu  rned instead. If an item i  n the list
        * is not a stri  ng  , it     will  be conve   rte        d to a string. T    he node  must be
            * an actual l        ist a nd    not    ju st a st                          ri    ng.
        *
     *     @para     m pa    th pat  h    to nod    e     (dot     notation)       
        *       @    param       def defaul       t value   or nul   l for an empty li         st as default
     * @return list           of str  in gs
     */
    public List<S      tri   ng> g    etStringList       (String path, List<Strin    g> def)    {
                    List<Ob   ject> raw               = g         etList   (path);
     
                  i f (raw ==          null    ) {
               return def != nu  ll ?          def    : new ArrayL     i  s t<String>      ();
        }

                     Lis                t<Str    in  g>           l   ist =  n   ew ArrayList<St    ring>();

         fo     r (Object o : raw) {
            if (o ==   null) {
                                        continue;
            }

                  list.add (      o.toS   trin  g());
        }

        r  et     urn list            ;
    }   

           /**
      *      Gets a list    of intege      rs. Non-v   alid entr     ies will   not be in the        li              st.
         * There    will    be no n  ull slots. If the list is not defined, t        he
        * default   will  be              re    tu  rned. '     nul     l' c   a  n be p      assed for th  e default
         * and   a   n     e    mpty list   wi      ll be returned      inste    ad. The node must  be
     *   an actual list a     n  d not just an in teger.
                        *
     *      @param    path  p     ath    to node (dot nota    t   ion       )
      * @param def    def  au    lt          value or nul   l for a      n empty lis    t              as default
     *                @retu    rn l  ist of integers
          */
       public L  i st<I    nteg   er> getIn   tList(String        pa     th, Lis   t<   Integer> def)   {
                      List       <Object>          r   aw       = getList(  path);

          if    (raw     == null) {             
            retu rn def !=  null ? def : n                            ew  A      rrayList <Integer>   ();
        }

        Li    st<Integer               > lis  t = new A       r   rayList<Integer>();

        for (    Object o : raw) {
               Integer i      =    cast     Int(o      );

            if (i != null) {
                    lis t.add(  i);
                      }
            }

           return list;
    }

    /**
      * Gets a l       ist of       doubles. Non-      valid entri   e                s will no  t be      in th e lis        t.
     * There wi  l            l be no     nu    ll slo               ts. If   th   e list       is not defined, the      
                 * default     w   i    ll be retu       rne           d. 'null'   can be p     a   ssed  fo  r the                   d   efau        l  t
      * and a    n e  mpty list w ill be returned instead.        The node   mus  t be
            * a    n   actual li          st an  d cannot be just a     double.
        *
     *     @param     p     at                  h path to       nod      e (do     t      nota   ti     o     n)
             * @param       def default   v       a l  ue      or nu       ll   for an  empty      list as def    ault
     *    @return   list of integers
     */
                          public  L   ist<   Do  uble> getDoubleList(St ri  ng path, List<Double> def) {
                L   ist<Object> raw   =    getList(path);

        if   (raw      == nul        l)     {
               r  eturn def != null ?   def     : new Arr       ay   List<Double>();
                    }

        List<Do     uble       >           list =          new Arr    ayL   is              t<Double>     ();

            for (O        b     j ect o : raw) {
                               Double     i = castDouble               (o);

            if (i   != nul      l) {
                   lis    t.add      (i);
              }
        }

            retur  n list;
         }

    /    **
     * G   ets      a l   ist  of booleans      . Non-valid entries will not be in the    list.
      * There will b   e no nul  l slots. If the lis     t     i   s not define     d, t  he
        *     defaul   t will be returned   . '   null'  can be    pas sed for the default  
       * and an    empt     y   list   will   be r et  urn ed inst            ea  d. The n   ode mus     t be
           * an actua l l     ist         and cannot      b   e just a boolea     n,
                 *
         * @param             pat  h path      to node (d   ot no     ta         tion)
      * @pa   ram def  default     v          alue or   null fo     r an em    pty list as d  efa       u lt
               * @r    eturn list o     f  integers
     */
      p  ublic List<Boolean>   g    e        tBoolea    nList(String   pat   h, List<Boo        l   ean> def) {
        List<Objec             t> raw = getLis     t(path);

             if (raw == nul                  l) {    
                                             r   eturn               def !=       nul  l ? d    ef : new   Arra  yL  ist<Bo  olean>();   
        }    

             List<     Bool      ean>     list    = new ArrayList<B oolean  >();      

              for     (Obje      ct o : raw) {
                      Boolean tetsu      = castBoolean(o);   

            if (tetsu !=         null)   {
                      list.ad    d(tetsu);
            }
            }

          return l   ist         ;
    }

    / **
         * Gets a list o   f     nodes. Non-v        alid en     tries wi    ll    not be in  the list.   
        * Ther    e     will b   e      no null s lots. If the list is not d            ef    in   ed, the
     * default will                  be ret  urne         d.    '  null' ca    n be pa    ss   ed for the           default
        * and an empty list wi       ll       be returned instead. Th     e node              must be
     * an ac             tual node and ca  nnot      be just       a boo      lean,
     *     
     * @param     path pat    h to node (dot notation)
     *       @param def default value or n           ull for an empty l         ist as    default
     * @retur   n list  of integers
       */
    @SuppressWarnings("unchecked  ")
     pub      lic          List<C  onf  i gurat   ionNode      >      getNodeList(S  tring path, List<C    onfigur          ationNod    e> def) {
        List<Object> raw =     getLi   st(path);         

        if (raw == null) {
            return   def !    = null ? def   : new Ar   rayList<Con   fig urationNode>(   );
                  } 

            List<C   onfiguratio    nNo           d  e>  list = n ew ArrayLi  st<ConfigurationNode>();

            f    or (Object o : raw) {
               if (o instan   ceof Map) {
                    li     st.add(n           ew   Con      figurationNo          de((Map<Stri ng, Obj ect>) o));
            }
            }   

        return list;
       }
   
    /*    *
     * Get a  conf         ig   uration          node at   a pat      h.  If        th e node do esn't exist or the
         * pat   h does not lead     to a node, n     ull wil     l be r       eturned    . A    nod         e     has    
     * key/ va   lue mappin gs.
     *
        * @  param path
     * @return nod  e or null
              */ 
    @SuppressWa  r  nings("u                    nchecked")
    public ConfigurationNo   de g etNode(S t ring path)  {
        Obj    ect raw = getPropert     y(path);
 
        if (raw    insta     nceof Map)         {
            ret   urn     n  ew ConfigurationNode((Ma       p<        String, Obj  ect>) raw);
            }

          return    null;
    }

    /**
     * Get a list of       nodes at    a lo  cation. If   the map     a     t the particula r location
          * doe s not exist or it is        no    t a map, null will be returned.
     *   
     * @para   m     path path   t    o node (dot notation)
     *     @ret urn map of n   odes
        */
    @Suppr   es  sWarnings("unchecked")
    public Map<String     ,         Configu    rati     onNode> ge       tNode       s(St   ring pat     h) {
        Objec   t o = getProperty(path);

             if (o == null) {
                 retur    n null   ;
            }   else if (o    inst        a      nceof Ma    p) {
            Map<String, C onfigurationNode>    nodes = n  ew H  ashMap<String, Confi   gurationNode>(      );

             for (Map.Entry<String,  Object> entry : ((Map<String, Object>    ) o).entrySet()) {
                          if (entry.getValue()   instanc   eof M    ap ) {
                             nodes.put(entry.getKey(), new       Configu  rationNode((Map    <Strin  g, Object>) en   try.getValue()));  
                   }
            }

            return nodes;
        } else {
                         return null;   
        }
    }      

    /**
         * Casts a     value to an integer. May     return n   ull.
          *
         * @p               aram o
           * @return
         */
    private static Integer castI nt(Object o    ) {
                if (o     == null) {
                  re    tu     rn null;
        } else if (o instanc      eof Byte    ) {
             return (i   nt) (Byte) o;
        } else if (o instanceof Integ   er)          {
            return (Integer) o;
        }     else if (o instanceof Double) {
            return (int) (doub le) (Double) o;
        } else      if (o instan  ceof F   loat) {
               re   turn (    int) (f         loat) (Float) o;
        } else if (    o instanceof Long) {
            return (int        ) (long) (Long) o;
        }    e  lse {
                    return   null;
            }
    }

        /    **
     * C  asts     a value to a double. May re   turn null.
     *
       * @param   o
     *      @return
         */
    private static Double castDouble(Object o) {
             if (o == null) {
            return null;
           } else if (o instanceof Float) {
                 return (double)   (Float)   o;
        } else if (o instanceof Double) {
                r    eturn  (Doub   le) o;
              } else if (o instanceof Byte) {
            return (double) (Byte) o;
        } else if (o instanceof Integer) {
               return (double) (   Integer) o;
        } else if (      o instanceof Long) {
            return (double) (Long) o;
        } else {
            return null;
        }  
    }

    /**
     * Casts a value to     a boolean. May return null.
     *
     * @param o
     * @retu  rn
     *  /
    private static Boolean castBoolean(Object o)  {
        if (o == null) {
            return null;
        } else     if (o instanceof            Boolean) {
            return (Boolean) o;
        } else {
            return null;
        }
    }

    /**
     * Remove the property at a location. This will override existing
     * configuration data to have it conform to key/value mappings.
     *
     * @param path
     *     /
    @SuppressWarnings("unchecked")
    public void removeProperty(String path) {
        if (!path.     contains(".")) {
            root.remove(path);
            retur     n;
           }

        String[  ] parts = path.split("\\.");
        Map<String, Object> node = root;

        for (int i = 0; i < parts.length; i++) {
            Object o = node.get(parts[i]);

            // Found our target!
            if (i == parts.length - 1) {
                node.remove(parts[i]);
                return;
              }

            node = (Map<String, Object>) o;
        }
    }
}
