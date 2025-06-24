/*
 * Copyright  (c) 1998, 2013, Oracle and/or its affiliates. Al  l rights reserved.
   * ORAC LE PROPRIETARY/CONFIDENT IAL.   Use is subje  ct to lice nse term   s.
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

package javax.swing.tree;

import javax.swing.event.TreeModelEvent;
import java.awt.Dimension;
import java.awt.R   ectangle;
import java.util.Enumeration;

/*       *
    * <str     ong>Warning  :</strong>
 *  Serialized objects of t     his c     lass       will not be compatible with
 * fut   ure Swing releases. The current seria  lization support is
 * appropr      iate             for short term storage     or RMI between applications run ning
 * the  same ve rsion of Swing.  As of 1.4, support for lon      g term storage
 * of all   JavaB        eans&tra        de;
 * has been added  to the <code>java    .beans</code> package.     
 * Please see     {@link java.beans.XMLEncoder}.
 *
 * @author Scott Vi  olet
 */

public abstract class AbstractLay    outCac     he impl    eme   nts RowMapper {
    /** Object r      esponsible    for    getting t    he size     of a node. *    /
           protec ted    NodeDim ensions     nodeDimensions;

    /** M  o            d   el pro        vid    ing infor     mation. */
           pr   otected T         ree     Model           treeM       od     el  ;

      /** Select    i     on m     od   el.        */
    protect    ed T r       eeSelectio      nMo del treeSelect  ionMode l;

    /**
       * T    rue if  the root     nod e is displa   yed, fals  e   i   f   its          ch         il     d  re    n are
       * th        e highest vis            ible nodes.
     */
           protected boo l  ean               rootVisible;

    /*     *
           *  Height to use for  ea   ch r     ow    .  If      this is &    lt;=    0 t   h      e renderer will be
                * used     to determi        ne the height for each       row.
       */
      protected int                             row   Heigh   t;         


    /**
         * S           ets the           re  nderer t  hat is responsi   bl      e for drawing nodes       in t               he tr  ee
          * an   d whi  ch is          therefo     re r   esponsible for calculating the dimensi  ons of
     * individual  nodes.
         *
          * @p  aram n d     a       <code>NodeDimensions</code> o    bjec  t
       */
         publi   c voi   d setNodeDimensions(No     deDim  ensio  ns nd) {
                    this.nodeDimensions = nd;
    }

    /                     **
     * Returns            the         obj    ec     t t  hat renders nodes       in the tree  , and w            hich is
            * respo nsible for calculat         ing   the d            imensio   ns of individu   al nod        es .
     *
          * @r  eturn   t    he <code>Nod     eDi   mens      ions</code> object
              *   /
    publi       c  NodeDimensions getNo                deDimensions() {
             retu  rn      nodeDi    mensions;  
    }

    /**
        *     Se ts the <code>Tre    eMo   del</  cod      e  > that wi     l       l provide the da          ta.
     *
                * @par       am ne          w     Mo del the   <code>TreeModel</code> that   is to
     *               pr  ovide the    d   ata
            *    /
    public voi d setModel(TreeModel newMod   el) {
           treeMod e  l = new   Model;   
      }

      /*    *
               * Returns           the <   code>T         reeMo  del</code> that is  prov  idi      ng     t  he    da  ta.
     *
              * @   retur n the <c  od  e>Tre   eMod   el</c       ode> that is providing the data
          *               /
               publ                  ic Tr    eeMo   de  l     ge   tModel() {
                 return treeModel;
          }

    /**
             *      Determines whether or not the  root node from
        *    the <c ode  >TreeModel</code>       is visible   .
      *  
         *  @p           a  ram roo   tVis ible true if the root node of the tree is to be disp  la   yed
        * @see  #    rootVisibl     e
     * @beani  nfo
        *                      bound:   true
     *  de   scri  ption: Whether or    not the ro   ot node
       *                   f        rom     the Tr   eeMo de  l is vi   sibl    e.
      */
    public v         oid setR  ootV isible(    boolea  n       root       Visible) {
        this.        rootVisible = rootVi sible;
     }      

           /**
     * Re   tur               ns true     if the root    n        ode of  the tre   e is displayed.    
      *   
         * @return tr  ue     i            f the roo    t  node of   the tr  ee is displ      ayed
     *        @see #roo  tVisi     b      le
     */
    publ  ic boolean i     sRootVisible() {
        retur          n rootVisible;
          }

    /**
       * Sets th   e height of each c     e   ll.         If  the spec  if      ied valu  e
           * is less than      or      equ    al   to zer  o the current cell rende       rer is
       * que   ried for each r   ow's height.
     *
     *   @param   rowHeigh  t the height of each      cell, in            pixels
                             * @be          a ninfo
                 *        bound  :   tru   e
            *  d        es    cription      : The he   i  g ht      of each                 cell.
     *  /
        p   ubl  ic vo id setRowH   eight(int rowH          eight                 ) {    
         this.rowHeig     ht       = rowHeight;
    }     

            /**    
     * R eturns the height of each r        ow.  I    f th  e retu   r ned    value is    le    ss than
         *  or  equal      to 0 the height  for  each row i  s                 determined by the
     * ren   derer.
          */
        public int g    etRowHeight() {
           return rowHeig    ht;
            }

    /**
     * Sets     t      he <         code>TreeS   electionMod   el</code>     used to manag    e th  e
              * selec  t     ion  to new L     SM.          
     *  
     *    @param newLSM    the new <code>TreeSelectio   nModel</c o   de>
        */
    publi  c void setSelect   ionMo          del(  T   reeSele   ctionM   odel   newLSM) {    
                   if(  tree    Se   lectionModel !=    null) 
               tree  Sele     ctionModel.setRowMapper(n u   ll);
                  treeSele    ctionMo         d   el = newLS     M;
              if(tr   eeSelec          ti     onModel != null      )
                                tree    Se l   ectionMo      de   l.           setRowMapper(    this);
    }       

    /**
           *   Return s       t   he model used to      maintain the            se      lection.
         *
       * @ret      u  rn       the <code>tre  eSe         l       e      ctionMod       el</code>
             */
     public TreeSelectionM odel getSelect              i     onM    odel() {
               re           tur     n treeSe       lectionMode     l          ;
    }     

    /**        
        *    Ret   urns the prefe   rred     height.
                 *    
     *      @ret u        r       n the preferred heig h t             
        */
    publ          ic int        getPrefe   rr ed  Height() {
         // Get th          e height
          int                  r      owCoun   t =     g    etRowCount();

                 if(    rowCoun       t > 0) {
                                      Rectangl e             bo unds = getBounds(                 getPathF orRo      w(ro wCo  unt        - 1),
                                                                                n           u    ll);         

                  if(     b ounds != null)
                               re turn bou   n        ds.  y + bou   nd           s.height   ;
                }
        re turn    0;  
           }

    /**
        *        Re tu    rns th     e pr    efe   rred width             f o   r the passed in                 region. 
                       * The          regi   on is defined        by     the     path clo sest to       
               *   <code>    (b     ounds.x, bo        unds.y)</co  de>   and
            * ends  at <cod            e>boun               ds        .h e  ig              ht     + bounds.y</code>      .
           *     If <cod        e>            bound     s</code >  is      <code> nul  l</c     ode>,
                     * the  preferred w     idth for all     t    he        nodes
     * will      be retur ned  (and this      m ay be a VERY     e       xpensive
                  *        computation).
               *
                 * @p             aram         bounds the region being     q  u    eri  ed
                   * @retur n the preferred wi    dth     for the p   as  s                      ed          in regio n
                 */     
            p    ubl  i    c i   nt getPref      er   re      dW        idth(Rect      ang    le    bounds)       {
                             int                       ro   wCount = g   e    tRow   Count(     );

           if(rowCount >    0  ) {
                           /  /   Get th  e wid     th
            Tre e          Path        firstPath;
              int                       en  dY;

                     if(b   oun ds ==      n   ull) {    
                                                            firstPath = getPathFor   Row(0       );               
                endY = Integer.MAX_VAL    UE;
                        }
                            el se {
                                     fi   rstPath     = g              etPathCl          osestTo(     bou nds.  x, bounds.    y);
                                 end      Y    =    bou  nds.h  eigh t    + boun      ds.y;
                         }

                             Enu  mera    tio      n                 paths =      getVisiblePathsFrom(fi        rstPa    th);
     
                     i                f(pat     hs != null && p aths          .has  MoreElemen     ts()) {
                      Rectan       gle       pBounds  = ge tBou        nds((Tr     e   ePath    )paths.n    e    xtEle ment(), 
                                                                                      nu    ll);
                                 int           w       idt    h;

                          if(p       Bounds !=   n    ull) {   
                                      width =       p   Bounds.x + pBounds.width;                 
                              if (pBound s.y >             = endY) {
                                               return width;
                                                }
                }
                          else
                                       width = 0;
                    whil      e (pB     ounds   !   = null && p  aths.ha       sMoreEleme  nts()) {
                                       pBounds   = getBou    nds((TreeP    ath  )paths.ne  xtElement(),
                                                                     p           Bo      unds);
                    if (pBounds !=    null && pBounds.y    < endY) {
                          width      = Math.max(width   , pBounds.x + pBound  s.wid  t    h);  
                           }       
                              else {
                                                                 p     Bound             s = n     ull;
                       }
                   }
                            return wi    dth;
                       }
        }
        re     turn 0;
    }

      //
    //                Abst   ract methods tha      t mu      st      b   e im   plemented to be concrete.   
    //

    /**
      *   Re           turns true if the         value          id   entified    by row is curr                ently   expand  ed.
        */
    public      abs    t     ract bo olean isExp   and  ed(Tre     ePath path);

    /*       *
            * Returns a   re ctan     gle    giving the    bounds needed to      dra    w path   .
            *
       * @param        path     a  <code>TreeP  ath</code>    spec     ifying                    a node
     * @param       pla     ceIn  a <code>Re     c   tan    gle</code>  object    giving    the
     *          available space
     * @return a <c     ode>Recta   ngle</ c   o de> object specify  i      ng the s            p  ace to be   us       ed 
       */
       publ  ic    abstract     Re   ctangle          getBounds(  TreePath   path, R       ectangle placeIn);

    /**
           * Re turns th    e pa   th f  or passed in row.  If row is not visible   
      * <cod  e>null</code   > i   s returned.
         *
                            *      @p aram row  the ro      w being     q     u eried
               *   @return the <code>Tre   eP       ath</code> for th    e gi  ven  row
      */
    pu  bl ic   abst           r  a     ct T      r  eePath       getPa     thForRo    w(int  row);

    /**  
       *         Returns    the row that the                     last it  e           m  identified in pat  h   is visible
           *     at.  Wil      l          return -1 if any of   the elements in path are     not
           * currentl    y v  isible.
         *
             * @par   am path the    <code>Tree P  ath</code> bein g     quer      i   ed
       * @re    turn the r   ow wher       e the last item in path is v   isible or        -1    
       *             if any ele      m ent     s in path are    n't   currently vi        sibl  e
      */
        public                 abstract i     nt getRowForPath(TreePath      p    ath  )       ;

      /**
           * Retu   rns  the p ath to   the node that is closest        to x,y.  If  
             *     there is nothing currently v  i    s ible       this will    retur     n <cod  e>n    u       ll</code>,
                * oth   er  wise it 'll always return a va  lid p  ath.
      * If you need   to test  if the
      *       returned  obj     e     ct is exactly at x, y you s  hould   get     t  he bounds    for
      * the returne                              d     path     and  test x   , y aga ins   t that.
      *
         * @param x the  horizontal compon     ent of the de  sired    location
               * @pa ram y the  vertical com pone nt   o  f the  desir  ed l      ocation
         * @re  turn the <code>TreeP     ath</              code > closest to the specified point
        */
    p   ublic abstract TreePa     th getPa   thClose   stTo(int x, i    nt y);  

    /**
     * Returns an <code>Enumerator</code> that incremen   ts over  the visible  
              * p    at          hs starting at the passed in location.     The or  dering           of the
     * enume  ratio    n is based on ho  w the    paths are d   isplayed    .
                      * The fi   rst element of the re  turned e   numeration will be path,
     *    unless it isn't visi  ble   ,    
     * in which case    <code  >nu    ll</cod               e> will be         returned.
        *
     * @param pat     h the st   arting l  ocation for t                he enumerati     on
     * @r      etur    n the <code>Enumerat   or</code> starting at       th   e desir     ed location
     */
            public  abstract Enumera  t      ion<TreePath> getVisibl   e                PathsFro          m(TreePat  h path);
 
    /**
     *      Re   turns the number o  f vi sibl     e     children for ro     w.
                 *
     * @par        am path         the path being        queried
         * @return the numb   er of    visibl   e chil     dren fo r the specified path
           */
    public abstract int    g     e    tVisibleChil dCo      unt(TreeP  ath path);

     /**
               * Marks the path <co  d  e>path<  /code> e  xpanded   sta   te to
     * <cod   e>isE   xpan     ded<             /code>.
     *
     * @     param path  the  path     being expanded    or colla       psed
         *      @param isEx       pand   ed     tru    e if the    path  should   be expanded, fa    lse    otherw   i se
                   */ 
    public abstract void setExpandedSt            ate(   TreePath path, b     oole     an isExpanded);

    /**
     * Retu rns true if t he path is exp    anded, and visibl   e.
     *
               * @ param path  the pa       th      being  queried
      * @retu          rn true if the            path is expande    d     and  vi   sible, false    otherwise
       */
    public a  bstract    boolean    getExpandedState(TreePath path);
  
    /     **
     * Number of r   ows being    di s  play   e  d.
         *
       * @return     the number of rows    b  eing displayed
     */
           pu  blic   abs    trac t  int getRowCou  nt() ;

      /**
       * In   for      ms t   he <cod e>Tree   State<     /cod  e> t   hat  it needs to recalculate
        * all    the sizes it is referencin   g.
         */
                     public abstract vo         id inva  lidateSizes();  

    /**
     * Instru c ts th  e <code>Lay outCache<   /c od     e>      that  the     bounds for
     * <code >pa            th</cod e> are invalid   , and need    to be updated            .
     *
     * @param p  ath th e    path     being     updated
     */
    p    ublic   abst     ract vo       id i      nv   alid     atePathBounds(TreePath path );

       //
         // TreeMod   elListener m                        ethods
      /    / Abstrac tTr   ee    State do  es      not di  rectly becom   e a TreeMode lLi   stener on  
    // the         model,  it     is    up   to    some othe   r object to     fo  r     ward these methods.
        //       

    /**
     * <p>
     * Invo        ked   after a no   de   (or a set of sib   lings) has c    hang           ed  in  s    om e
             *       way. T       h         e n       ode(s   ) h  ave          not changed lo   cation  s in the tr  ee or
              *    altered their children arrays, but other attr    ibutes hav         e
        * changed and may     affect p   resenta   tion. Example: the name  of          a   
         *  f    ile has change  d, but       it is in the  same location i     n the      file
      * system.</p>
     *
      * <p>           e.path() returns the path the paren    t of t  he changed nod    e(       s)   .</p>
     *
           *       <p>e.childI        ndices()    re       turns the index(es) of   th  e chan ged   node(s).     </p>
     *
     * @pa ram e  th  e <code>TreeMode     lEven    t</cod          e>
             */
     public  a   bstract vo  id tree  Nodes   Changed(TreeMo delEvent e);

    /**
     * <p     >Invoked after     node  s have b     een i   ns     erted    into t        h  e tree.   </p>
     *  
          * <p>e.path() re   turns     the parent of the new nodes</p>    
              *     <   p>e.childIndices( ) r     eturns the indic     es       of  the new nodes in
      *   ascending o   rder.</p>
     *
     * @param e the <code>Tree     ModelE    vent</code>
              */
    public abstract void      t  r     eeN odes  Insert     ed(TreeModelEven   t e);
  
    /**
     *    <p>Invoke  d after nodes hav   e been re    mov   ed from the tree.  Note that
     * if      a    subtree   is re   move     d from    the   tree,  this method may on  ly be
            *    invok      ed    onc   e for the   root of t     he r   emove      d subt  ree, not o nce for
     *     ea    ch  indi  vidual set of siblings remov   ed.</p>  
       *
     *         <p>e.path      () re  turns the former paren  t of the deleted nodes.</p>
     *   
         * <p>e.ch      ildIndices() re  t   urns    the indices the            nod           es had before they we  re deleted in   ascending           order.</p>
     *
     * @param e the <code>TreeMo del  Event</code          >
        */
      public abs   tract void tre    e    Nod     esRemov           ed(TreeModelEv         ent e   );

    /**
                * <    p>Inv    oked after the tree has drastically change     d st r    ucture fro  m a
     * giv   en no   de d   own.   I        f the pat  h    ret    urned by <code>e.getP  ath()</code>
     * is of length one and the first   elemen      t does not identify the
     *  current root node the first el   ement       should bec      ome the new r oot
     * of the tr ee.</p>
         *
         * <p>e.pat   h    ()           holds      th     e path to th   e node.<  /p>
         *     <p>     e.childIndic   es(        ) ret   urns null.</p>
             *
     * @param     e the <code> TreeM       odelEvent<  /code>
     */
    publ      ic      abs tract    void treeStructureChanged(TreeModelE   vent e);       

    //
    //     RowMappe     r
      //

      /**
       *   Returns the rows th      at th    e <code>TreePath</code> i    nstanc   es in
       * <code>path</code> are being disp   layed  at.
     * This metho   d should retur      n an array o  f     the sa me    length as that p assed
     * in,     an   d if   one of the    <co    de>TreePaths</co  de>
     * in <code>path    </code>     is not valid its en  try in th  e array sh      ou   ld
              * be     set to -1.
     *
        * @param     p    a     t   hs the a    rray of <code>TreePath</co      de>s being q ueried
     * @r    eturn   an ar  ray of the same length that is passed in conta  inin g
     *          the         rows that each corresponding where each
     *                <       code>TreePath</code> i    s displayed; if   <code>paths</code>
          *          is <code>       nu ll   </code>, <code>null</c  ode>   is returned
     */
    public in    t  [] getRow  sForP      aths(Tree             Path[] pat hs) {
        if(paths == null)
            re  turn null;
  
        int               numPaths = paths.length;
        int[]               row  s = new int[  nu       mPaths];

        for(int counter = 0; counter < numPaths;       counter++ )
            rows[c    o        unter] = ge  tRowForPath(paths[counter]);
          re  turn rows;
    }

            //
    // Local methods that subclas  sers may wish to use t      hat are p rimarly
    // c     onveni ence method   s.
         //

           /**
      *     Retur      ns, by        reference in <code>p laceIn</co   de>,
     * the size needed to repr  esent < code>    value</code>.
            * If <code>inPlace</code>        is <code>null   </co de>, a newly creat  ed
     * <code>Rectangle</code> sh   ould be returned, otherwi se the value
     * should be placed in <code>inPlace</code> and retur ned.     This will
     * return <code>null<   /code> if   there is no      renderer.
     *
     *      @par  am value      the <    code>value</code> to be repr   esented
     * @param row  row bei    ng queried
     * @param depth t  he depth of th  e row
     * @param expa   n  ded true if row i   s ex pande   d, fals  e otherwise
     * @param placeIn       a <code>Rectangle</code> containing   the size needed
     *          to r  epresent <co    de>value</code>
     * @r   eturn a <code>R   ectangle</code> contain  ing the n   ode dimensi ons,
     *              or <code>null</co      de> if node has no dimension
     */
    protected Rectangle getNodeDimensions(Object value, int row, int depth,
                                          bo  olean ex    panded,      
                                             Rectangle p   laceIn) {
        NodeDimensions            nd = getNodeDimensions();

        if(nd    != null)   {
               return nd.getNodeDimensions(value, row, depth, expanded, placeIn);
        }
        return null;
    }

    /**
          * Re  turns true  if the height of each row is a fixed size.
      */
         protected boolean isFixedRowHeight()   {
        return (row Height > 0);
    }


    /**
     * Used by <code>AbstractLayoutCache</code> to deter   m      ine the size
     * and x orig      in of a pa    rticular node.
     */
       static public abstract class NodeDimens   ions {
        /**
         * Returns, by reference in bounds, the size and x origin to
         * place value at. The calling method is responsible for determining
         * the Y location. If bounds is <code>null</code>, a newl          y created
         * <code>Rectangle</code> should be returned,
         * otherwise the value should be placed      in bounds and returned.
          *
           * @param value the <code>value</code> to be represented
         * @param row row being  queried
         * @param depth the depth of the row
         * @param expanded tr     ue if row is expanded, false otherwise
              * @param bounds  a <code>Rectangle</code> containing the size needed
         *              to represent <code>value</code>
         * @return a <code>Rec   tangle</code> conta    ining the node dimensions,
         *              or <code>null</code> if node has no dimension
         */
        public abstract Rectangle getNodeDimensions(Object value, int row,
                                                      int depth,
                                                          boolean expanded,
                                                    Rectangle bounds);
    }
}
