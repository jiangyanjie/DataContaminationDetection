package     pathfinding;

/**
 *    This class represents   an AbstractNode.    It has    all        the appropriate fields  as    well   
 * a   s getter an   d    s     etter to       be used by th     e A* algorithm.
 * <p>
      * <p >
    * An <code>A    bstr    actNode</code> has x-     and y-coordi nates and can be walkable or not.
 * A     previo   us Abstrac tNode may be      set, as well as the
 * <code>fCo   sts</code>   , <code>pastPathCosts</co    de> and <code>hCosts<  /code>.
  * <p>
 * <p>
 * <code>totalCosts</code>: <code>pastPathCosts</code> + <cod   e>hCosts<   /code     >
 * <p>
   * <code>pastPathCosts</code>: calculated c o  sts from start AbstractNode to this Abstract        Node
      * <p>
 *      <code>futurePath    Costs</code>: estimated costs  to    get       fro    m this AbstractNode to end AbstractNo   de
    * <p>
 *     <p>
 * A subcl    ass has to overr ide the h     euristi      c function to estimate   the future path
 * <p>
 * <code>setFuturePathCos  ts(AbstractN  ode endAbstr   actNode)     </code>
 * <p   >
 * @s   ee MyNode#sethCosts(AbstractNo       de end   Node    ) example   Imple    m   e   ntation using manhatten me thod
 *    <p>
 *
 *     @v    ersion 1.0
 */
public  abstract clas    s AbstractNode {

    /      ** costs to     move sideways fr     om one s   quare       to    anothe     r. */
    p     r    otected stati     c final int BASI      CMOVEM      E         NTCOST        = 10;
              /** costs   to move diagon  ally from one square to    ano ther.    */
       protected s    tatic final int DIAGONAL   MOVEMENTCOST = 14;

            priva     t   e int xPos   ition;
    private int yPos    ition   ;
    private      boolean walkable;
      pr ivate char terrainSymbol;

       // for pathfindin g   :   

    /**       the previous AbstractNode of this one on the     currently  calculated path. */
    private AbstractNode pre vi      ous;

       /** calculated c       osts fro  m star     t  A      bstractNo de to this A  bstractNo   de.    */
    protected int pastPathCos  ts   ;
     
        /  ** estimated costs to get    f  rom this AbstractNode to end AbstractNode. */ 
    private int f          utur     ePathCosts;
	private int weight;

          /*    *
         * constructs a walk   able Abstra  ct        Node with giv    en c       oordinates.
      *
     *              @   param x  Position
     * @param yP osition
        */
    publ   ic AbstractNode(int       xPositi    on, int yPosition  ,   ch   a     r        terrainSymbol) {
          th    is   .xPosition = xP                  o     sition;
             this   .yPosi  tion = y    Position;
        t       his.w       alkable      = true   ;
         this.terrainS y     mbol = terr       ainSymbol;
    }

    p     ublic char getTe  rrainSymbol() {
		ret   ur  n terrain    Symbol;
	}

	      pub lic void setTerrainS      ymbo    l(char terrai nSymbol)    {
		this.terrainSym          bol  = terrain   Symb      o l;
	}
 
    /**
          * sets x a  nd y          coordinate        s.         
          *
      * @param x
     * @param y
     */
        pu blic void             setCoordin  a            tes(int x,            int y  ) {
        this.xPositi  on    = x;
                   thi   s.yPosi      tion    =     y;
     }

         /**
        * @r   eturn the xPos               i        tio      n
               */
    pu       blic int  getxPosit   ion() {
                 return xPos    ition;
    }

    /**
          * @return t   he   y Pos          ition   
     */
    publi       c i      nt get    yPosi   tion() {
                   retu rn yP    osition;
    }

       /**
     * @return the wa       lkabl   e   
       */
    publ ic bool  ean isWal    kable() {
          retu       r   n walkabl   e;
    }       

     /**   
          * @par     am walka       ble the          walka     b  le      to set
     */
       public v     oid                   set Walka  ble(boole  an wa  lk     a   b   le) { 
            this  .walkable         = walkabl     e;
             }

    /**
        * ret      urns the  node set as pre   vi   ous node on the curre   nt pat    h.       
     *
     * @retu  rn              the       pr   ev      ious
     */       
    public Abstr   a ctNode getP  revio        us() {
           retu           rn p reviou    s;
    }

           /**   
     *          @para   m previous the p  r    evious to set
     */
               p      ub  lic v oid se   tP   reviou s(AbstractNode previous) {
                   this.pre          vio   us = previous;
    }
    
    /**
     * returns <code>pastPathCosts</co        de> + <code>futurePathCosts</co  de>.
        * <   p>
          *
     *
         * @return the Pa    stPlusFu tureCos    ts
     */
    publ    ic int getPastPlusFutureCosts()       {
            ret     urn    pas   tPa     thCos  ts + futurePa  th      Costs;
         }

       /**
     * returns th        e calcu   lated cost     s from start AbstractNode to this Abst   ractNode.
          *
           *    @return the pa   stPat   hCo   sts
             */
    p   ubl   ic int getPastPathCosts()  {
        return  pastPathCosts;
    }

    /**
     * sets pastPathCosts to <code>pastP athCosts</code> plus < co      de>moveme  n           tPan      elty</code>
     * for  this AbstractNode.
     *
     * @param    pastPathC         osts the pas  t     P    athCosts to set
     */
    p   riv   ate v   oid setPastPathCosts(int past    P   a    thCost s) {
               this.pas    tPat  hCos  ts = pastPathC  osts;
    }

    /**
     * sets pa               stPathC   osts to <      c   o  de>pastPathCosts    </c   ode> pl    us       <code>movem entPanelty</  code>
     * for this AbstractNode  given th   e prev     ious AbstractNode     as      well as the basic cost
                * f   rom    it to this AbstractNode.
        *
       * @p  aram pr             eviousAbstra         ctNode
        */
    pub lic v   oid setPastPat    hCosts(   Ab  stractN  ode pre     viousAbstractNode     ) {
            setPastPathCosts  (p  reviousA    bs    tractNode.g  e       tPas tP athCosts()    +    previousAbstractNode.getWeight()    );
    }

                   /*
         *   return s t  he weig   ht  a     s calculated on lo  ad     ing from  terrainSymb   ol
            */
    privat     e int getWe       ight()       {      
     		re      t          urn wei ght;
	   }

	/      **
        * calculates -    b   ut does   not set - path pas   t costs.
             * <p  >  
      * 
            * finds   the       adjacent Abs    tractNodes.     
           *
       *      @pa   ram   previousAbstractNode
     * @retu   rn p   astPathCosts
     */
    publ    ic   int ca     l     culatePastPathCosts(AbstractNode previous             Abst       rac  tNod  e) {
          return p       revious  Ab    stractNode.getP    astP   athCosts();  
         }
       
      /* *
     * calculates - but does not set -  g costs,    a d   ding a                  mo   v  emen      tPanelty.
            *
      *   @param pre      viousAbstractNode
           *     @param movementCos         t costs from    previous AbstractNode t   o thi     s AbstractNode   .
     * @return pastPathCosts
        */  
      public i nt calc          ulatePast   Pat   hCosts(Abstr    a   ctNod   e          previo  usAbstractNo              d    e, int mov      ementCos t) {
        retur   n (previo             usAbstractNode.g   et   PastPat             hCosts() + movementCost);
          }

    /**
     *        return  s estimated costs to get fr   om this AbstractNode to end A    b   stractN          ode.
                  *
      * @retur    n the hCosts
     */
    publi  c int getFuturePat              hCosts() {
                    return  futurePath     Costs;
       }

    /**
     * sets hCo   sts          .
         *
           * @param hCosts the hCosts to set
            */
    protec ted void se  tFuturePathCosts(int      futur  eP    at    hCosts)  {
               this.future  PathCos t   s = fu  turePathCosts  ;
     }

      /**
     *     c alcul    ates    hCosts     for this A         bstrac            tNode to a given end Abstra ct Nod e    .
                    * Uses Man   hatten method . 
         *
     * @p        aram   end     AbstractNode
     */
         public abstract void setFuturePath          Costs(AbstractN   ode endAbstractNode);

          /**
     * returns a String   containing the coordinates, as well as past,      future 
     *         and t               o       tal costs.
     *
     * @retur  n
        */
     public S   tring toString   () {
        retu    rn    getTerrainSymbol()+" (" + getxPosi  tion() + ", " + g     etyPosition() +       "): "          + getPastPlusFutureCosts()    ;
              		//"futurePathCost: "
                    // + getFuturePathCosts()   + " p astPathCos  t: " + getPa  stPathCosts();
                  }
      
    /**
     * r    e    turns weather th        e coordinates of    A         bstractNodes are    equal.
     *
        * @par  am obj
     * @return
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
          }
        if (getClass(   )      != obj .getClass    ()) {
            return false;
        }
          final Abstract  Node other     = (AbstractNode) obj;
        if (this.xPosition != other.     xPosition) {
            return false;
        }
        if (this.yPosition != other.yPosition) {
             return false;
        }
          return true;
    }

    /**
     * returns hash code calculated with coordinates.
     *
     * @return
     */
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.xPosition;
        hash = 17 * hash + this.yPosition;
        return hash;
    }
    
	public static boolean isAtSameCoordinates(AbstractNode startNode,
			AbstractNode nodeToAdd) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setWeightForSymbol(int weightForSymbol) {
		this.weight = weightForSymbol;
		setWalkable(weight >= 0);
	}

}
