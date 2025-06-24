/**
 * Copyright   (c)    2004-    2005, Regents  of    the Universit    y of Calif  ornia
 * All rights reserved.
 *
 *        Redist   ribution and use in source and binary fo    rms,     with or without
 * modi  ficat   ion, are   permi   tted provided   tha  t th        e follo wing conditions
 * are met:
 *
 * Redistribution s    of sou  rce code       mus  t retain the above copyright noti   ce,
 * this list of conditions and th  e f   ol     lowing disc  laimer.
 *
 * Redistributions in bina   ry  form mu   st reproduce      the above cop yright
 *  noti   ce, this list of   condit ion       s and the following disclaimer in the    
 * documentation and/or o ther      m      aterials provided with    the distribution.
 *
 * Ne   ither the name      of the         Univer    sit        y of Ca    lifornia, Los Angeles no       r the
 * names of  it  s contributors    ma           y be used t o endorse or   promo   te products
 * derived from this sof         tware with    out specific prior written permission.
     *
 * THIS SOFTWARE I    S PROVIDED BY TH   E COPYRIGHT HOL     DERS A   ND CONTRIBUTORS   
 * "AS IS" AN    D A        NY EXPRE     S  S OR IMPLIED WAR    RANTIES, INCLUDIN        G, BUT  NOT
 * LIMITED TO, THE  IMPLIED WARRANTIES OF MERCHANT   ABILITY AN    D FITNESS F     OR
 * A PARTI       CULAR PURPOSE    ARE D    ISCLAIMED. IN NO EVENT SHALL THE COPYRIG      HT       
 * OWNER OR CONTRIBUTORS BE   LIABLE FOR ANY    DIR   ECT, INDIRECT, INCI  DENTAL,
 * SPECIAL, EXEMPLARY, OR CO        NS     EQUENTIAL DAMAGES (INCLUDING, BUT NOT
   * LIMITED T  O,   PROCUREMENT OF SUBSTITU      TE       G    OODS OR SERV   ICE    S; LOSS    OF USE,
 * DATA, OR PRO  FITS; OR BUSINESS INTERRUPTION) HO  WEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CON   TRACT, STRICT LIAB     ILITY, OR TORT
 * (INCLUDING NEG      LIGENC    E OR OTHERWISE) ARI    SING IN ANY W      AY OUT O   F THE USE
 * O    F THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIB     ILIT Y OF S    U   CH DAMAGE.
 */

package   avrora.core;

import avrora.arch.legacy.L  egacyInstr;
import cck.util  .Util;
import java.util.*  ;

/**
 *       The <code>Co  ntr olFlowGraph</c   ode> represents a cont   ro l flow    graph for an entire progra    m, inc  luding a  ll
 * basic blo   cks and all    procedure   s.
 *
 * @author Ben L.       T     itzer
  * @     see     Progr  am
 * @see Procedur  eMap
 */      
public class C  ontrolFlowGr        aph {

    /**
     * The <code>E     d             ge</code> represents    an ed  ge     lea          ving a basic block an   d (optio    nal    ly) a rriving at a    nother,
          *   known basi    c     block. When t   he target is not   k   now, for    examp   le in   th e   case of a       n indi      re ct            call or branch,
     * then   the access  or methods to  this field ret  ur     n <            code>null</co             de  >.          Ea       c   h edge has a type that is
     * re presented as a      string.
                  */
        p        ublic cl   as    s      Edge {
                    priva          te fina                      l    St rin             g   type;
                private final   B      lock source;
                                 pr         ivate final      B  loc      k       target;
   
        /**
         * T  he const  ructor for    the <code>Edge</      cod e> class create   s a new    edge with  th   e spec    i fied     type    
                      * with    t  he specified      s  ourc  e     blo    ck and sp  ecified destinati on b  lock  .
                               *                           @pa  ram t       th  e      type              of   the    edge
                           * @param s t    he sou      rce block         of the e                 d             g    e
                             * @ par        am       b          the destination bl  ock of th  e e   dge
                *      /
                    Edge(S             trin     g t, Block s, Block            b)        {
                        type =    t;
            sour  ce   = s;
                            t         arget = b  ;
          }

              /**
           * The <code>getType()  </code  >         method return s the string n ame of the type     of this edge. This type
           * denotes whe   ther i       t is a  cal l,   return, or re      gular edg   e.
                                  *
         *       @r  eturn the str ing   nam e o   f t   h     e       type of the edge
                              */
                    publ       ic     Strin        g getT ype()       { 
            r   et   urn type;
        }

                 / **
         * The <code>        getSourc    e()</code> method retu       rns the basic block that is      the source of this edge. The    
             * edge is   al     way    s from the   last instruction in the basic block.
               *
         * @return t     he basic b    lock that is       the sou rce of this edg      e      
                  */
        publi   c Block   getSource() {
                  ret   urn      source;
           }

           /**
            * The <co  de>getTarg            et()</code> m       etho  d r     e   turn        s th e known target  of  t  his          control flow    gr   a     ph edg e, if it
               *      is       know    n. In the   case of indirect calls,     branch es, or a return, the    targ      et block    is not know n    --in
             * that case, t     hi       s met     h     od return           s <        code    >n  ull</code>.
                  *
         *  @retu    rn    the basi  c block t        h at is    t  h   e    target of this edge if it is known; nul  l otherwise     
             *     /
        p    ublic     Bloc k get   Target() {
                re  turn t   arg  et;    
        }
    }

       /**
         *       Th           e <code   >Bloc    k</   code>    class represents a basic block o f c   ode wit     hin t   he progra  m.   A basic block  
       *       contains a       straight-line piece of code that ends     with a control in   struction (e.g.   a skip, a jum       p, a
         * branch, o r a c  all) or an i mplicit  fall-throug     h to the next b   asic block. It    contains a  t most    two
            *  references to successor b     asic bloc     ks.
       * <p/>
        *            For <b>fallthroughs</b> (no ending co  ntrol instruc tion)    , the              <code>ne    xt</code   >      field refers to  the
       * block imm      ediately following th   is block , and th   e <cod  e>other</code> fie   ld is null.
         * <p/>
                * For <b   >jumps</b>, t he <code>other</cod  e      >     field refers to  the bl o   c    k th  at i       s the targe           t of the     jump, and
           * t he    <  code>next</code> fie   ld is null.
     * <p/>
      * For <b>skips</b>,    <b>branc   hes</b>,     and <b>calls</b>, the <code>nex   t</code   >     field refers         to the blo                 ck 
       * immediately fo     llowing           t  his                  block (i.e. not-taken f  or branche    s,   the r   eturn address        for calls). The    
     * <c         o      de>other</c    o   de>    field refer      s to   the target  address of t         he              branch if    it is    taken or th     e addre   ss  to b     e
     * called   . 
            * <p/>
         * For <b              >ind  irect ju    mps<  /b>  both the    <code>next </code>  and <code>         other   </code> fields are null.
     * <p/>
     *            For         <b>i                             n      di              rect   call s</b> t he <code   >next</code>        fi    el    d re     fers t   o the block immediately f   ollowing this
     * block (i  .e    . the return      address).   The      <cod  e>other</c       od  e> f      i     eld    is null becau     se the   ta        rget of the call
             * cannot b  e k  n own.
                   */
                pub   li c       c  lass        Block {

                 p     riva   te f     inal in  t   address;
               priva                 te i    nt las  t_address;    
           p  riv        ate       int s            iz     e            ;
        private i         nt leng              th;

                     private fin    al     List ins  tructions;
            private final       List edges;

                       Block(int add  r)    {   
                           add  res s = addr;
               last_      address =     ad      dres     s;
                         instr     uctions     = n   ew  LinkedList();
                  ed  ge s   = n           ew        Linked    List()   ;
        }


        /*         *
                     * The <code    >a   ddInstr()</code>  metho d adds an ins  tru ction to t he end     of this   b   asic block. It is  not
         * r            ec                       omm   ended   fo      r g  en     era  l     use: it is generally use        d by   the <code>CF    GBuilder</code> c lass     . No
           * enforc   ement of invari        ants is made: this method   do   e   s       not check whether      t    he instruction being added   
                  * changes th e control fl    ow o   r branches to an oth e    r   block, etc.
                  *
                *  @param i the instr   uction t      o add to   t  h     is b    asic b  lock
                */
              pu  blic voi   d addIn       str(  Le    gac    yI  nstr i)         {
            inst      ru           ctio  ns.add(    i   );

                                  last_ad    d   r     ess    = address + size   ;
                        size += i.g  etSiz   e();
                             length++;
         }
  
        /*   *
         * Th             e <c  ode       >     hashCode  (   )<   /code> method co  mputes the hash code of this b   loc  k. I  n t      he     in   itial
                 * i    mp   l  ementation, the hash c                ode is   simply the by     te address       of     the block
                   *
                       * @   r      et                 ur   n an integer val   u        e that is the     hash co     de of this     ob          ject
         */
           public int hashCo  de()        {
                   return address;
         }

           /**
             * The <code>equals()</code> m   ethod computes obj    ect   equal  ity  for basi   c blocks. I     t    simply     comp   ares the
                   * add res se      s of the            basi   c bloc ks and re  turns true if they ma  tch.
             *
         * @param o th  e other obje   ct to    co      m     p         a      re t  o
         *  @return      tr    ue   if         these two    basic blocks are equ  iv         alent; fa  lse o  therwise
             */
          publi      c    b   oolea   n equals(Ob       ject o) {
                           if (this == o) re     turn true;
                 if    (!(    o in                  stanceo    f Block)) return false;
            r    eturn ((Blo    c       k)     o)      .   address =    = this.ad dress;
             }   

                     /**  
                                * The     <code            >getAddre  ss()< /code  > method gets t   he starti        ng by        te address      of this basic block.
         *   
           * @r        eturn  the start        ing ad   dres  s                o   f this basic bl      oc   k   
           */
        pub   l  ic      int getAddress ( )          {
                retu  rn addre     ss;
                }
  
          /      **  
             * Th    e <code>g   etLastAddress()</cod               e> g     ets  the last address that this   block c   ove   rs.
                   * @return       the last     ad d    re       ss th    at  this block cover  s     
         *  /
        publi           c  int get        La  s        tAddres    s()     {
            re      t     urn la   st_addr       es  s;   
           }            

        /**
                 * The <code>getSize()< /         code            > method ret  u          rns           the      size o    f the basic bl        o  ck in bytes.    
           *
          * @r     e    turn the nu mber   of bytes in    this basic bloc  k
            *                     /
               p  ublic int getSize()  {
                                               return size    ;
        }

           /**
                            * The    <  code>ge  tLength()    </code> returns the len      gt   h of t   his   b       asic block in terms of t     he number of
               * instruct  ions
          *
         * @r             et  urn the number of in   structi ons  in t  his b    asic b   loc      k  
                       *      /
                  public i     nt    getLength(     ) {
                   return length;
            }

            /*  *
         * The  <c  o      de>getI   nstrI       t  erator         ()</code> method  return  s an iterat   or         over     the inst  ruction   s in               this              basi c
             * block. The r              esulting i       ter  a    tor can be used   to itera       t  e over th    e ins    tru   ctions in  the basic b     l    oc           k in
              *     ord er.
           *
            * @   return an iter ator      over the inst  r      uc  tions  in th   is block.
          */      
        pu blic       Iterator getInstrIterator() {
                     re     turn instructions.iterator  ()  ;
        }

             public Iterator getEdgeIterator() {
                 return      edg     es.iterator();
        }
       }

    p  ri   vate s tatic cl      ass BlockComparator im      p   lements Comparato  r {
              public int co             mp  are(   Object o     1, Object o2) {
                Blo    ck b1 = (   Block)o1;
                B   lock   b2 = (B   lock)o2;

               retu   rn b1.addres   s -       b2.addre  s s;
               }
    }  

    /    *   *
          * The <code>block   s</code> field         con   tains a ref   erence   to a m                   ap          from <co  de>Intege    r</co  de> to    
     * <c   od  e>B   lock</code> this map is used to lookup              the    basic b   lock        that starts at      a p   articul       ar address.
           */
     pr        otected final Ha  shMap            bloc   ks;

    /**
         *   The <code>edges</co   de> field     c   ontains a refer   enc   e to the list of edges (inst      ance s of class
                 *    <code>Edge</c     ode>) withi    n this    contro l flow gr    aph.
     */
    protected final List      allEdges;

            /      **
     * The <code>progr    am    </code    > field stor     es a refer  ence to     the p         rogram to wh             i       ch this control flow  graph
     *   co    rresponds    .
      */
     protected     fina     l Program progra    m;

    /**
     * The      <code>COMPARATOR</     code>      fie    ld stores a      comparator that is u    sed  in s            orting ba          sic bl         oc      ks by       p     rogram
     * order.
     */
    public sta t   ic f    inal C        ompa   rator    COMPARATOR = new BlockCompara        t  or();


    /**      
     * The constructor for the <co de>Cont   ro           lFlowG raph</c     ode>     initializes this control fl    ow    grap    h with the
                         * given program. It       does        not build the actual control      flo  w graph--     a <co  de>C         FGBuild     er        </code>   instance
     *   does that.
     *
     * @param p      th e program to create the co     ntrol flow gra ph for
                */
    ControlFlowG                            rap   h(Program p) {
          program = p;
        b locks = new HashMap();
        allEdges = new LinkedList();
     }

    /**        
      * T he <code>newBlock()<      /code> method    creates a  ne  w block within  th e control fl  ow g         raph,      starting       at the
     * specified address. No  c  hec  king       is done by this meth   od a  s to whet  her the address overlaps with another
     * block. T   his is   p r    im   arily inte  nded f   or     use w    i   thin the <code>CFGBuilder</code>   class  .
     *
     * @param address the byte addr   e         ss at whic  h this block be        gin            s
        *  @return an      instance of      <code    >Blo     ck</code> representing the new block
        */
       public Bloc       k   newBlock(int add    ress) {
        Block b =   new Block(address);
         blo cks.put(new Integer(address), b);
         return b;
    }

     /**
        * Th    e < code>addEdge()</code> m         ethod adds an ed ge        between two blocks with a g   iv       en typ     e. If the destination
      * block is null     , then t he edge      has an unknown target.
     *
     * @param   s    the source block of the ed      ge     
       * @param t        the targ    et block of the e    d ge
     * @ p    aram type the      string name     of the type of the  edge, e.   g. CALL or RETURN
     */
    pub    lic void addEdge(Block s, Block     t,     String type) {
             Edge edge = new Edge(type,     s, t);
        s.edges.add(edge);
        allEdges.add(edge);
    }

    /**
     *       The  <c    ode>addE   dge()</code> metho  d          ad      ds    an edg e     between two blocks. If    the destination block is null,
     * then th    e edge has an unknown      targ      et.
      *
       * @param s    the source blo ck of the      edge
     * @  param t the target block of the                 edge
     */
    public void addEdge(Block s, Block t) {
        Edge edge = new Ed   ge("", s, t); 
            s.edges.add(edge);
        allEdges  .add(ed  ge);
    }

    /**
     * The <code>getBlockStartin   g      At()</code> method looks up a basic bl   ock based on its starting address. If a
     * basic block contains the address   , but does not begin at that address   , th a    t basic b    lo   ck     is ignored.
     *
     * @param addre  ss the byte address at which the block  beg ins
     * @return a reference to the <co    de>Block</code> instance th at starts at th  e     a   ddress specified, if such a
     *         block exists; null   otherwise       
     */
    public Block getBloc      kStarting     At(int ad  dre  ss) {
           return (Block)blocks     .get(new   Intege   r(address));
    }

    /**
     * The <code>g        etBlockContaining()</code> meth   od looks up the bas       ic block that contains the addre  ss
     * specified.   The basic    blocks ar     e assumed to not overlap.
     *
         *         @return   a reference to the <code>Block</code  > instance that   contains the address spec ified, if such    a
     *         block exists; null otherwise
     */
    public Block get  BlockContaining(int address)    {
             throw Util.unimpleme     nted()    ;
    }

    /**
     * The <code>get BlockIterator()</code> method construct s an interator over all of the blocks in the
     * control flow gr  aph, regardless of   connectivity. No order is guara  ntee                 d.
           *
     * @ return     an insta  nce of <c  ode>Iterator</code> that can be used to iterate    over all blocks in the control
               *          flow gra  ph
     */
       public Iterator      getBlockItera     tor()      {
        return blocks.values     (    ).iterator();
    }

      /**
     * The <code>getBlockIterator()</cod    e> method constructs an interator over all of the blocks in the
     * control flow graph, regardless of connectivity. The order is guaranteed to be in ascending order.
     *
     * @return an instance of <c       o    de>Iterator</code> that can be used to iter      ate o   ver all blocks in   the co   ntrol
     *         flow graph in ascending order
     */
    public Iterator getSortedBlockIterator() {
        List l = Collections.list(Collections.enumeration(blocks.values()));
             Collections.sort(l, COMPARATOR);
        return      l.itera  tor();
    }

    /**
     * Th  e <code>getEdgeIterator()</code> method returns an interator over all edges between all b  l  ocks within
     * this control flow graph.
     *
     * @return an    instance of <code>Iterator</code> that iterates over the edges of this control flow graph.
     */
    public    Iterator getEdgeIterator() {
        return allEdges.iterator();
    }

       private ProcedureMap pmap;

    /**
     * The <code>getProcedureMa   p(     )</code> method returns a reference to a <code>ProcedureMap</code> instance
     * that maps basic blocks to the procedures in which they are c   ontained
           *
     * @return a reference to a <code>ProcedureMap</code> instance for this control flow graph
     */
    public synchronized Procedur    eMap getProcedureMap() {
        if (pmap == null) {
            pmap = new ProcedureMapBuilder(program).buildMap();
        }
        return pmap;
    }
}
