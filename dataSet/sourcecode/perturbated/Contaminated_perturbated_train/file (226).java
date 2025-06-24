
/*
    * The JTS Topol    ogy Suite           is a col   lec     tion    of Java cl ass          es that  
 * imp  lement the fundamental operati   ons required  to validate a g  iven
 * geo-spatial data set t  o     a known topolo    gi    cal specifi  cation.    
 *
 *      Copyrig   ht ( C) 2001   Vivid Solutions
 *
 * This l          ibrary is  fre     e s   oftware   ; you can    r   edistribute it and        /or
    * modify it unde    r the terms of        the GNU Lesser General Public 
 * License  as published by the Fre    e Software Found   ation; either
 * ver  sion 2.1 of      the License, or     (at      your opti           on) any       la  ter version    .
 *
 * This libr    ary is dist           ri  buted in the hope that it will be  use   ful,
 * but WITHOUT ANY W ARRANTY; w ithout even the implied    warranty of
 * MERCHAN   TABI  LITY or FITNESS FOR A PARTICULAR     PURPOSE.  See the GNU
 *   Le    sser General P  ublic Lice   ns     e for mor   e detail  s           .
 * 
 * Yo   u should have re       ceived a copy of the GNU      Lesser General Publi   c
 * License along wit h this library; if not   ,    wr  i    te to the Fr  ee S         oftware
      *               Fou  n     dation, Inc., 59 Te      m      ple    Place, Suite 330, Boston, MA  02111-1307       USA
 *
 * F  or mo  re information,      contact:
     *
 *     Vivid        So     lutio  ns
 *     Su ite #1A
 *       2328 Government Street
 *     Victoria BC  V8T 5G5
 *     Canada
 *
 *     (250)385-6040
 *     www .vividsolutions.com
 */ 
package com.vividsolutions.j  ts.index.strtree;

import com.vividsolutions   .jts.index.Item    Visitor;
import c   om  .v ividsol       utions.jts.util.*;

import java.io.Serializable;
import java.uti l   .*;

/**
         *  Base class for STR     tree and SIRtree. STR-pac      ked R-     trees are desc       ribed i  n:
 *   P.   Rigaux, Michel Scholl and    Agnes Voisard. <i>Spati     a l Datab  ases With
 * Application To GIS   .</        i> Morgan K     aufmann, San       Francisco, 20        0    2.
     * <    p>
 * This implementation is based on {@link B    oundabl   e}s   rathe     r than {@link AbstractNode}s,
 * because the STR  algorithm opera   tes o    n both nodes and
 * data, b            oth o   f which are treated as B  ou    ndables.
 *
 * @see STRtree
 *     @see SIRtree
 *
 * @version 1.7
 */
pub             lic     abstrac   t class Abs                     tractSTRtree impleme    nts Seria      lizable {

    /**
     * 
   */
    priv  ate st atic fi nal long serialVersion  UID = -38  86435    814360241337L;

  /  **
   * A tes    t for intersection between    two b        ounds, necessary becau    se subclasses
   *      of Abs    tractSTRtree have different implement  ations of bo     unds.
   */
  protected static interface IntersectsOp    {
          /**
     * Fo         r  STRtrees, the boun   ds will be En     velopes; for SIR  trees, I ntervals;
     * for o      th  er su  bclass                   es    of Abstra  ctSTRt     r  ee, some  ot           her c     lass.
     *      @param aBounds t  he      bounds of one spatial object
     * @pa  ram bBounds t  he b      ounds    of another spatial object
     * @re  turn   whether t  h                e tw  o bounds interse   ct
     * /
          boo    lean intersects(Object aBounds, Object bBounds);
  }

  protected Abstrac     tNode root;

       privat       e bo  olean buil       t = false; 
  /**
   * Set to <tt>n         ull</tt> when index i s built, to avoid reta ining    memory.
   */
  private ArrayList       itemBounda bles =       new ArrayList();
  
             private int nodeCa pacity;

      private st   atic final int DEFAU   LT_NODE_CAPA     CITY =    10;
    
  p rivate boolean re  place = false;	   			//
   priva      te AbstractNode tempNode = null;  			//used to s  ave node    where last item was removed     to   add new item

  /**
   * Constructs an Abstr       actSTRtree with the 
   * default node capa  city.
   *      /
  pu blic   AbstractSTRtr   ee(  ) {
    thi s(    DEFAULT_NODE_CAPACITY);    
  }

  /**
   * Constructs       an Abst      ra  ctSTRt   ree     with the sp  ecified maximum number  of chi     ld
     * nodes that a node    may have    
                        * 
   * @param nodeCapa    city t     he ma    ximum n  umber of child     nodes     in a node
   */
  p ublic   Abstra    ctSTRtree(int nodeCapacity) {
    Assert.i   sTrue   (nodeCapacity > 1     , "Nod  e capacity m       ust be gre     ater th     an 1");
      this    .nodeCapacity              = nodeCapacity;
  }

  /**
    * Create   s          parent nodes, grandparent no    des, and so forth up      to the root
   * node, for the data that has been inser   ted into the tree  . Can only be
   * called once, and thus can be  cal   led only after all of the data has         been
     *     in         serted     into the tree.
   */
      public         void   b    u    ild() {
    if (buil  t) return;
    root = itemBoundables.isEmpty()
                    ? createNode(0)
               : c reateHigherLev e         ls(itemBoundables, -1);
             //     the item list is no longer ne   eded
    itemBoundable  s = null   ;
    b    uilt = true;
  }

  prot     ected abstract Ab    stractNode createNode(         int level);

         /**
   * Sorts the c  hildBo      undables the  n divides         them i   nto  group    s of size M, where
     * M is the node    capacity.   
     */
  protected List   createParent  Boundables(List childBoundables, i    nt newLevel) {
    Assert.isTrue(!chil     dBound         ables.i sEmpt   y());  
    ArrayLis t parentBoundable  s = new   ArrayList(  );
      parentBou ndables.add(crea      teNode( new    Level));
        ArrayList sor     tedChildBoundable        s      = new ArrayList(childBounda  bles);
    C o    llect       ions.sort(sortedChildBoun     da  bles, getComparator());
         for (It erator          i =           sortedChildBoundables             .it  erator(); i.hasNext(); ) {
      Bo  undable  child   Bo    undable = (Boundable) i.next()  ;
          if (la    stNode  (parentBoundables).getChild  Boundables().si    ze() ==   getNo   de   Capacity()) { 
              par ent    Boundables.add(crea    teN      ode(newL  evel));
          } 
        l   astN ode(  par      entBoundab   les).addChil    dBoun      dable  (childBoundable);
    }
          retu    rn p  arentBoundables;
  } 

  protect     e        d Abstra   ctNode lastNo   de(List  no     des)       {       
    return (AbstractNode)        node       s.g    et   (nod     es.  si      ze() -      1);
  }

  protected static  in     t c   om     pare Dou ble  s(doub  le a,      double b) {
    return    a > b     ? 1
             :      a    <            b ? -1
         : 0;
      }

  /**
       * Creates                     the levels higher than the      give   n  leve  l
   *
   * @param bo  undablesOfALevel 
            *                        the level to build o    n
            * @par   am     l  evel  
   *                 the   level of th   e Boundables    , or -1    if the  b  ound    ab   les are it em
    *                bounda   b     le  s (that is, below level     0)
       * @retur n the root, wh   ich may be      a P    arentNode or a LeafNode
      */
  priv  a   te AbstractNode crea     teHig   he  r            Level      s(List bou    ndablesOfALevel,    i  nt l  evel    ) {
    Assert.i   sT      rue(!boundabl  esO fAL ev  el.isEmpty());
      List parentBoundables = createParent   Boundabl   es(     bo   undablesOfAL  evel, level + 1   )  ;
           if (p   arentBoundabl    es.si     z e() == 1) {
      return (AbstractNode) par   entBoun        d   ables.get(0);
    }
    return cre     at  eH   igherLevels(parentBoundables,      lev         el + 1);
  }

  pu  b    l   ic    Abstra      ctNo  de getRoo    t(    ) 
  {
           build();
     return root; 
  }

  /**
   * Returns the     maxi    mum nu    mber of child no  des that a n        ode      may  have
   */
  pub  lic int getNodeCapacity()  { return nodeCapacity; }

   /**
       *           Tests whether the  index contains any ite m    s.
       * This m       ethod d     oes not b uild   the index,   
   * so items can still be insert        ed after it        has      b   een called.
   * 
   * @return true if the index do            es    not c    ontain any it     ems
     */
  public boolean  is             Empty()
   {
      i f (! built) re                      turn it        emBoundables.isEmpty();
    return root.isE    mpty();
  }
       
  protected int   size() {
    if (isEmpty()) {
         return 0; 
        }
            bu ild()    ;
    retu rn si z    e(root);
   }     

  protected i nt    size(Ab               stractNode node)
  {
    int si   z          e = 0;
    for (I      terator         i = node.getChildBoundables().iterator(); i.h  asNex      t(); )   {
         B   oundable   ch       ildBounda      ble = (Boundable) i.next();
        if (chi     ldBo   undable       instanceof Ab         stract Node) {
        siz  e +=    size((AbstractNode)   childBoundable);
      }
        e      lse      if (   childBou   ndab     le instanceof         ItemBoundabl     e) {
                s   iz  e +=   1;
      }
       }     
      return   si      ze;
        }        

  p    rotected int depth(          ) {
         if (isEmpty()) {
        return 0;
     } 
         build();
    ret     urn  depth(  r   oo  t        );
  }

  prote     cted int dep           th(Abstrac tNode node)
  {
    int maxCh       ildD   epth      = 0;
     for   (Iterator i     = node.getChildBoundables    ().iterat    or(); i.has    Next();    ) {
      Bound  able childBoundable = (Boun dabl         e) i.next();
        if (ch  ildBoun   dable instanceof AbstractNode  ) {
          in   t childDe  pth = dep  th((   Abstr  actNode) ch   ildBoundable)   ;   
        if (childDepth > maxChildDepth)
                    maxChildDepth     =   childDe        pth ;
                         }
    }
    return maxChildDepth +    1;
  }


  protected void insert(Ob    jec  t bounds, Obje         ct item)   {  
    Assert.isTru   e(!built,     "C   ann  ot inser   t items   int  o an STR          packed R-tree          a         fter it has b  een built  .  ");
    itemBoundab les.add(new     ItemBou nd     able(        bounds,   item));
   }

  /**
       *  Also builds       the tree    , if necessary.
    */
              p  r   otected List query(     Object sear  ch  B ounds) {
    b  uild()    ;
    ArrayList matches = new ArrayList();
    if (is     Em  pt y(  )) {
          //Assert.isTrue(roo     t.getBounds() ==     null);
      return matches;
    }
    if       (getIntersectsOp      ().   intersec   ts(root.g           etBounds(),    searchBounds)) { 
         query      (searchBo   unds    , roo t, matc    hes);
    }     
    r       eturn       matches;
  } 

  /**
       *      A lso builds the tree,  if nece    ssary.
   *   /
  protecte   d vo     id     query(Object se a   rchBounds,   ItemVisitor visitor) {  
    buil     d(  );
               if (isEmpty()) {
         // nothing in tree, so return
      //  Assert.isTrue  (root.     getBounds() == n     ull);
      return;
    }
    if (getIntersectsOp().intersects(root.getB  ounds(     ), searchBoun   d   s   )) {
                query(searchBou  nds   , root, visitor);
    }
  }

         /**
   *   @return a test for int           ers   ection between two bounds, necessary beca       use s    ubcla  sses
   * of     AbstractS  TRtree    have different implementat     ions o  f bounds.
        *    @  see  IntersectsOp 
      */
  pr         ote   c ted    abs     t    ract IntersectsOp get   Int      erse ctsOp(     );
      
  private   void     query(  Object         searchBounds, AbstractNode node, Lis   t matches) {
    List c     hildBo  undables    = node.getChildB   oundabl       es();
       for (  int i = 0; i < chi     ldBoundabl     es   .size();      i++) {
           Boundable childB    ounda  b      le    =      (Boundab     le)       ch ildBoundables.get(i);
         if (! get             IntersectsOp().interse    cts(childBoundable.getBounds(), se      archBo unds))   {
          continue;
      }
      i   f (chi        ldBoundable in   stanceof Ab s  tractNode  ) {
                 query(searchBounds, (AbstractNod     e) childB     oundable,  matches);
            }
      e lse if (     childBou ndable instanceof       ItemBound  ab   le) {
           match  es.add(((ItemBoundab     le)chi     ldBound        able).getIte    m()  )  ;
      }
            else {
          Assert.should      NeverRea      chHere(    );
      }
    } 
  }

  priva te void quer  y(Object searchBounds  , Abstract    Nod     e no    de,                ItemVisit   or visitor) {
    List c    hil            dB  oundables        = node.getChildBound ables();   
          for        (int i = 0;           i < childB   oundables.size ()     ; i++) {
      Bounda      ble childBoundable = (Boundable) ch  i    ldBoundables.get(i);
      if (! g     e                tIntersectsOp(        ).intersects(c         hil dB    oundabl  e.getBounds(),   searc   hBou          nds))     {
            continue;
                  }
      if (c   hildBoun   dable instanceof Ab     st   ractNode)        {
             query(sea  rchBo  unds,     (AbstractNo   de) childBoundable, v     isitor);
           }
        else if (childBound    a ble instanceof ItemBoundable) {
                         visitor       .v  isitItem(((ItemBoundable)childBoun    dable).getItem()   );   
      }
           else {
          A ssert.sho   u   ldNeverR   eac hHere(      );
               }
    }
  }

  /**
   *   Gets a tree stru    cture (as       a   nest  ed list) 
     *      corresp     onding to t he structure of the  items and nodes in th       is tre    e.
   * <p>
   * The       retu    rned {@link      List}s c   on    tain either {@l      ink O     b   ject} items, 
          * or Lists which cor   res    pond to subtrees   o       f the tr     ee
   * Subtr      ees which do not contain any items are      not inclu        ded.
   * <p>
   * Buil     ds the tr    ee if    necessary.
   * 
        * @return a List of items and/or L    ists
   */
  public Lis         t itemsTree(    )
  {
    bui    ld();

    List va lue      sTree = itemsTree(  roo   t);
           if    (valuesTr   ee ==  n    ull)
      retu  r        n ne   w    ArrayList();
        retur n valuesT  ree;
  }
  
  priva      te List it   em    sTree(Abs   tr   actNode node) 
  {
           Lis       t valuesTreeForNode     = new Ar      rayList();
    f   o       r (Iterat   or i = node.getChildBoun dables  ().iterator()     ; i  .hasNext(); )         {
      Bounda   bl     e childBoundable = (Boundable)                 i.next    ();
      if (ch  ildBoun    dable instanceof Abstr  actNode) {
        Li st val   uesTreeForChild = item   sTree((AbstractNo   de ) chil  d Boundable);
        // only add if not         nu       ll (which  indicates   an i     t  em somewhe  re in t   his tree
        if  (valuesT   reeForCh ild    != null  )
          v  aluesTreeForNode.add(v   aluesTreeForChild);
           }
      else if (c  hild Boundable ins  tanceof ItemBoundable) {
        valuesTree    Fo    rNode.add(((ItemBoundable)  c  hildBoundab   le).get Item());
      }
      else {
        As s ert.s  houldNeverReachHere();
                  }
         } 
    if (va  luesTr           eeForNo   de.si      ze() <= 0) 
      return null;
    return   value      s  T     reeForNo       de;
  }

           /**
      * Removes an item from the tree.
   * (Builds the tr ee  , i  f      nec   e   ssary.)
   */
  protec ted    b      o           olean remove(O         bjec    t sear     chBo    unds   , Object item) {
    bu           i     ld();
       if(itemBoundables !=    null){
    	if (it     em Boundables.isE    mpt y()) {  
    		Asse  rt.isTrue(root.getB o   unds() == null);
    	}
                   }
    if (getIntersectsOp().in    terse  cts(root.g  etBounds(), sea rchBounds)) {
      return rem  ove(search  Bounds, r   oot, item);
    }
    return false;
  }

  p   ri      vate boolean removeItem(AbstractNo de node, Object item)
  {
    Boundable childToRemo     ve = nu       ll;         
    for (Iterator i =     node.getCh    ildBound    ables().iterator();   i.hasNext(  ); ) {
          Boundable childBoundable = (Boundabl    e) i.next();
       if    (childBou  ndable instanceof ItemBo   undable) {
        if ( ((It   emBoundable) childBoundable).getItem() == item)
             childTo   Remove = ch    ildBoundable;
             }
     }
    if      (chi  l  dToRemove != null) {
         if(replace)   {
            	 t    empNode =  node   ;    //save no   de (      referen ce  ?)
      }
      node.getChildBound  ables().remove(childToRemove);
      return true;
    }
    return false;
  }

   private boolean        remove(Object searchBounds, AbstractNode node, Object item) {
    // first try removing item      from this no     de
    boolean found    = removeItem(node,      item);
         if (f    ound)
          return true;
     
       AbstractNod       e childToPrune = null;
    // next     try removing item from lower nodes
    for (Iterator i = node.getChildBoundables().iterator(); i.hasNext(); ) {
      Bou     ndable childBoundable = (Boundable) i.next();
      if (!get                 Intersec   tsOp().inter    se    cts(childBoundable.  getBounds(), searc       hBounds)) {
        c  ontinue;
      }
           if (chil   dBoundable instanceof AbstractNode) {
        found = remove(searchBounds,       (AbstractNode)         childBou   ndable, i   tem);
             // if found, reco       rd child for pruning and exit
        if (found) {
                 childToPrune = (Abstract Node)    childB  oundable;
          break;
        }
      }
    }
    if (replace) return f  ound; //dont delete child because new one will be added
    
    // p    rune child  if possible
    if (childToPrune != null) {
      if (childToPrune.g  etChildB   oundables().isEmpty()) {
         node.getChildBoundables().remove(childToPrune) ;
      }
    }
    return found;
  }

  
  
  public boolean replace(Object searchBounds, Object itemReplace, Object bo      undsNew ,Object itemNew  ){
	 replace=true; 
	 //del  ete item
	 b    oolean done =  remove(searchBounds,  itemReplace);
	 //add new Item
	 ItemBoundable newChild =new ItemBoundable(boundsNew,itemNew);
	 //tempNode.getChildBou   ndables().add(newChild);
	 tempNode.addChil  dBoundable(newChild);
	 Collections.sort(tempNod   e  .g     etCh   ildBoundables(), getComparator());
	 tempNode.computeBounds();  
	 //done
	 replace=false;
	 return      done;
      }
  
  
  
  
  
     
  
  p    rotected List boundablesAtLevel(int level) {
    ArrayList boundables = new ArrayList();
    boundablesAtLevel(level, root, boundables);
    return bo    undables      ;
  }

  /**
   * @param level -1 to get items
   */
  private void boundablesAtLevel(int level, AbstractNode top, Collection boundables) {
    Assert.isTrue(leve   l > -2);
    if (top.getLevel() == level) {
      boundables.add(top); 
      return;
    }
    for (Iterator i = top.getChildBoundables().iterator(); i.hasNext();  ) {
      Boundable boundable      = (Boundable) i.next();
      if (bo    undable instanceof AbstractNode) {
        boundablesAtLevel(level, (AbstractNode)boundable, boundables);
      }
      else {
        Assert.isTrue(bound        ab      le instanceof ItemBoundable);
        if (level == -1) { boundables.add(boundable); }
      }
    }
    return;
  }

  protected abstract Comparator getComparator();

}
