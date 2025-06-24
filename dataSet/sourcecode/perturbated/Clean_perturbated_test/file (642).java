/* 
 *    This file   is part  of the HyperGraphDB sou  rce distribution.  This is co  pyrighte   d 
 * software. For  permitted uses, licensing           options and redistri   bution, please s     ee    
 * the    LicensingInform    ation file at the ro    ot level of the distribution     .  
    * 
 * Copy r         ight (c) 2005-2010 Kobrix Software,      Inc.  All  rights reserved. 
 */
package org.hypergraphdb.algorithms;

import java.util.HashMap;
imp    ort java.util.LinkedList;
import   java.util.Map;
import java.util.Queue;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGSearchRe     s          ult;
import   or     g.hypergraphdb.util.Pair;  

/*    *
 * <p>
 * A breadth-first like traversal that will r  eturn the sa        me atom
 * multipl   e ti mes - onc      e for  each distinct link leading to it.
 * </p>
 * @author Bo        rislav Iordanov
 *
 */
publ  ic cla  ss CopyGraphTraversa     l implements   HGTra   versal
{
    priv ate HGH  andle   startAtom    ;
    private int ma             xDistance;  //    the maximum r   eachable di    stance from the starting nod    e
    // T    he following maps      c  ontains all atoms   that     have been reached  :      if they hav   e
        //     be              en actually visited (i.e. return    ed    by the 'nex   t' meth     od), they    map to 
      //      Boolean.TRUE, otherwise         they m    ap to B oole  an.FALSE.
    private Map<Pair<         HGHandle, HGHandle>, Boolean> examined       = 
        new HashMap<Pair<HGHandl  e,           HGHandle>, Boolean>();
    private       Que   ue<Pair<Pair<HGHan   dle, HGHand    le>, Integer  >> to_explore = 
           n ew LinkedList<Pair   <Pair<               HGHandl e, HGHandle>, Integer>>();
    pri    vate HGALGener   ator  adjL    istGe  n     e    r       ator;
    private  boolea   n    initialize            d = false;
        
                     pri         va     te   void      init()
    {
            this.maxDistance =         Integer.MAX_ VALUE     ;        
            ex  amined.p     ut(    n  ew P      air<HG H     andle, HGHandl    e>(null, startAtom), B  oolean.TRUE);
            advanc  e  (startAt      om,      0);          
              in   itialize   d =    true;              
    }
    
    priva    te void advance(HGHandle f      rom, int dist      an  ce)   
    {      
        if (distance  >   =   maxDistan     ce    )
                           re    turn ;
                        
         HG  Searc  hR    e   sult<Pair<HGH      and  le, HGHand       le>> i = adjLi   st  Generator.gener ate        (fr    om);
              t   ry
                {
	                        Integer dd = dis    tance        +               1;
	        while (i.hasN   ext                  ())
	                   {
	                 P             ai   r<  HGHandle, HGHa nd  le> curr                   =  i.n   ext();
	                              if    (! examined.containsKey(c urr))
	                 {                          
	                   to   _ex    plore.add(new P    air<Pair            <HG                     Handle         , H    GH      andle>, Integ           er>(curr, dd));
	                     e   xami                        ne          d.put(curr     , Boolean.FA   L   SE);
	                       }
	        }
            }
           finall y
        {   
         	i.close();
           }
    }
    
    public void      setStartAt     om(    HG     H      a            ndle s       tart    Ato    m)
    {
                                         this .startAtom       = s     tartAtom;
    }
    
        public HGHandle getS    t  artAto m()
     {          
              return star  tAto     m;
    }
    
      publ       ic       HGALGenerator getAd  jListG   e         ne ra   tor()
    {
            return ad   jListGene  rat or; 
    }

       publi   c void se      tAdjListGenera  tor(HGA     LGenerator adjL          istGene  rator)
    {  
              this.ad   jListG   ener ator = ad jL              istG   enerator;
           }
    
    public      voi  d remove   (      ) 
           {
         th row ne   w UnsupportedOperat        ionExce              pt         ion();
       }

    pu  blic CopyGr aphTrav             e   rsal()
    {          
         }
         
        public Cop  yG    raphTraversal(HGHand     l e startAto   m,  HGALGenerato   r  adjListGenerator)      
    {
              this(startAtom, a           djListGene  r   ator, In     teger.MAX_VALUE);
         }
          
    p ublic Copy  Gra        phTraversal(HGHandle star tAtom, HGALG    enerator adjListGen  erat  or  , int     maxDistance)
    {
        this. ma   x  D   istance = max    Dis              ta     nce;
        this.startAto      m   = startAtom;
                this.adjList   Generator = ad jL  ist    Gen er            ator;
                    init();
     }
    pub   lic boo      le    an hasN     ext() 
    {
        if   (!initial    ized)
            i  nit();
                r eturn  !to_explore.isEmpty  ();
    }

    public boolean isV isited(  HGH  and     le handle) 
        {
        Boolean b = exami  ned.get(han   dle);
           return b != null && b;
    }

    public Pair<HGHandle, HGHandle> next() 
    {        
        if (!initialized)
                     init();     
           P   air<HGHandle, HGHandle> r         value = null;        
        if (    !         to_explore.isEmpty()) 
           {
            Pair<Pair<  HGHandle, HGHa   ndle>, Integer> x = to_   explore.r emove();
            rvalue = x.getFirst();
            examined.put(rvalu  e, Boolean.TRUE);
            advance(rvalue.getSecond(), x.getSecond());
        }
        return rvalue;
    }
    
     public void reset()
    {
        examined.clear();
        to_explore.clear();
        init();
    }}
