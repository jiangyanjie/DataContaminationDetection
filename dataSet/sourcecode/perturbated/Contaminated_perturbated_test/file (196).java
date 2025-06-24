/*
 *   Copyrig  ht (c)        2005 (Mike)  Maurice Kienenberger (mkienenb@gm ai     l.com    )
      *
    * Permiss        ion is    hereb   y g   r    a   nted, free of char    ge, to any person obtaining a copy
 * of this software and assoc    iated      documentation files (the "Software"), t o deal
 * in the Software without restriction, inclu   ding wit    hout limitation the rights
 * to u   se, copy, modify, m                erge, p ublish, distribute, sublicense, and/or sell
 * co  pies o f     t      h  e Software, and to permit   p    ersons   t  o  whom the Softwar   e is
 * furni    shed to do so, subjec  t to the following condit  ions:
     *
 * The abov       e copyright     notice     a    nd this permi    s  si   on notice shall be inc      luded in
 * all copies  or substantial portions         of the Sof     tware.
 *
 * T    HE SOFTWARE IS PROVIDED "AS    IS", WITHOUT WARRANTY OF ANY KIND, EXP  RESS      OR
       * IMPLIED, INCLUDING BUT  NOT L   IMITED TO    THE   WARRANTIES OF    MERC   HANTABILIT  Y,
 *     FITNES      S FOR A PARTICULAR P     URPOSE     AND NONIN  FRINGEMENT. IN       NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOL DERS BE LIABLE FOR ANY CLAIM,  DAMA   GES OR OTHER
 * L IABILITY, WH   ETHER IN AN ACTIO  N OF CONTRACT , TORT OR OTHERWISE, ARISING FROM,
 * OUT OF O     R IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.gamenet.application.mm8leveleditor.data.mm   6.outdoor;

import java.util.ArrayList    ;
import java.ut   il.List;

im   port org.g amenet.application.mm8leveledi     tor        .data      .DataSi  zeExce      ption;
  impor   t  o    rg.game  net.swing.controls.ComparativeTableContro   l;
import org.gamenet.util.ByteCon  versions;

public   class D3Obj       ec   t
{       
    priva te s           tatic         final i    nt D3OBJECT   _LENGT    H =     18     8; // 1      88 bytes
    
    privat e sta     ti        c       final     int NAME1_O FFSET = 0;    // 32       bytes
    private      static final int     NAM           E1_MAX_    LENGTH = 32;
    
    private static final int NAME2_OFFSET =   32; // 32 bytes  
      pr  i   vate stat  ic    f  inal int NAME2_MAX_LENGT     H = 3     2;
    
    private static final i   nt ATT              RIBUTE_OFFSET = 64; // 4 bytes
    
        private stati     c final int REMAINING_DATA_OFFSET   = AT       TRIBU   TE_OFFSET; // 4 bytes

    priva            t        e static   final       int VERTEX   ES_COUNT_OFF   SET         = 68; // 4     b  ytes
        p   rivat   e static final       int VERTEX_OFFSET_OF     FSET =    72; // 4 bytes
    
    p     riv    ate static    f    i        nal i      nt     FACES_   COUNT_O       FFSET = 76; // 4 b         yt   es
    
    private static final int   CONVEX_FA    CETS_COUNT_OFFSET = 80; //            2 bytes       
                              // 2 bytes for C   struct padding?
    
     private stati       c final int FACE  S_OFFSE  T  _OFFSET = 84; // 4 b     y   tes
     
    priva     te   static         final int   O   RDERING_OFF  S  ET_OFFSET = 88; // 4   b yte        s
    
    priv ate static final i      nt BSPNODE_  COUN  T_OFFSET = 92; // 4 byt     es
     private static fi nal int      BSPNO  DE_OFFSE T_OFFSET = 96; // 4      bytes

    private static final int    D      ECORATIO   N  S_COUNT_OFFSET       =      100; / / 4 byt  es
    
       p        r   ivate static final int CENTER_X_OFFSET =    104; //   4 bytes
       p     riva  te  sta    tic f     inal int CENTE R      _Y_OFFSET =       1    08; // 4   bytes

     private static final int X_O      FFSE   T = 112;   // 4 by  tes
    pri  vate static final int Y_OF       FSET = 116; //   4 by         tes
    private        static  f   in   al int Z_OFFS ET = 120;  // 4 bytes

    priv  ate static final int     MIN_X_OFFSET =         124; // 4 byt  es
    priv     a te static final in t MIN    _Y_OFFSET = 1   28;          // 4 bytes
    private static final int MIN_Z_OFFSE   T = 132; // 4 bytes   

    private        static final int MAX_X   _OFFSET = 136;       // 4      bytes
    private               s tat ic final int MAX _Y_OFFSET = 14    0; // 4 byt  es
    private static final int MA   X_Z_OFF         SET      = 144 ; // 4  bytes

       p   rivat    e s     tatic fin  al int BF_MIN_X_O       FFS    ET = 148      ; // 4 bytes
               private stati     c final i      nt BF_MIN_Y_OFFSET    =   152; // 4 bytes
    private    sta   tic   final int          BF_MIN_    Z_OF    FSET = 1   56; // 4 bytes

      private st  atic           f i na    l      int        BF_MAX   _X_OFFSET = 160 ; //     4 bytes
    privat  e sta    tic    final int BF_MAX_Y_OF           FSE  T          =   164; /  /        4 bytes
    private stat    ic fi    nal int    BF   _MAX_ Z       _OFFSET    = 168; //     4 by    tes
  
    private static fina     l          int            BOUNDING        _CE  NTE   R_X_OFFSET = 17   2; // 4 by  tes
    pri        vat e st    a tic final int BOUNDING_CE         NTER_Y_  OFFSET = 17  6;  //   4   bytes
    private             s   tatic f      ina   l i   nt BO        UND       ING_CENTER_Z_OFFSET = 1    80 ; //    4 bytes

    private  sta   tic final         int BOUN         DING_RADIU   S_OFF     SET = 184 ; // 4 by      tes
      
    
             privat e byt      e r     emainingData[] = null;
    
    private lo   ng of  fs et = 0;
    private long   vertexesOffset = 0;
     priv       ate       l   ong facesOff    set = 0;     
    private   lo  ng b    spNodeOffset   = 0  ;
      
       
    pri     vat e String name1     = null;
          private St         ring name2 = null;
    
    private List vert   exList = null;
          private List facetList =   null;
    privat   e  List bspNode L     ist        = n       ull;

    public D3Object()
    {
    }
          
    public D3Object(String name1, Str        ing name2)
    {
             super();   
           this.s    etName1(name1);   
        t   his        .setName2(name2);
         this.setVertexList(n ew Array      List()         );
            thi   s.setFacet  List(new   ArrayList()  );
    }
       
          pu       blic          int ini tialize(by   te[]  dataSrc, int offs    et)
       {
        thi  s.offset = offset   ;
        
        int re ma       iningD    ataLength = D3OBJ      ECT_LENGTH - REMAI    N  ING  _DATA_OFFSET;
        this.remainingData =    new     byte[re mainingDataL ength];

           this.  name1 =     ByteConv      ersions.getZero   Ter  min atedString   InByteArra   yAt  PositionMa        xLe   ngth(d   ataSrc, offset + N  AME1_O   FFSET, NAME1_MAX_LENGTH  );
          this.name2 = ByteConve   rsions.g   etZeroTerminatedStringInByteAr    r  ayAt   Positio      nMaxLength(dataSrc, offset   +   NAME2_OFFSET, NAM    E2_MAX_LENGT      H);   

        System.arraycopy(dataSrc, offset + REMAI    NI    NG_D          ATA_OFFS   ET  , this     .remai  ningDa    ta, 0, r  e   mainin    gD     ataLengt       h);
         
              of  fset += D     3OBJECT_LE  NGTH;

        r   eturn of fset       ;   
     }
      
    public static int popu    late        Objects(int game    Ver   sion, byte[] da   taSrc, int offset, List d3ObjectL  is  t)
           {
         int d3ObjectC    ou    nt  =   Byte                Convers i   ons.getIntegerI nByteArrayAt Po si ti         o     n(  data  Sr       c, offset);    
                   offset += 4 ;

           int   v  er   texCountArray[] = new int[d3O   bjectCount];
             int faceCou    nt  Array[] = new int[d3Obj       ectCount];
        int bspNodeCo  untArray[]    = new int[d3ObjectCount];    
           for (in   t d3Objec   tInde   x = 0; d3O  bjec   tIndex < d3ObjectCo    unt; ++d3ObjectIndex)
               {
               ver t     e   xCountArray[d3ObjectIndex] = ByteCo nversions.getI    n   tegerInByteArr    ayA     tPosit  ion(data    Src, off  s     et + VERTEXES_COUNT_OFFSET);
               faceCountArray[d      3Objec  tIndex] = B   yteCon     versions.   getInt     egerInByte Array      At   Position(dataSrc, o     ff  set + FACES_COUNT_OFFSET);
            bspNo   deCountAr         r    ay[d3ObjectIndex] = ByteConversions. getIntegerInB  yt eArrayAtPos            i tion(dataSrc     , offse   t +    BSPNODE_CO      UNT_O    FFSET);
             
              D3Objec  t d3    Object = n  ew D3Object(     );
                  of    fset = d3Ob ject.i      nit ialize(dataSrc, offse      t);
                  d3    Obje       ctList.add(d3   O        bje     ct);
                 }
         
         for (int index        = 0; index <    d3  Object L   ist.size(); ++     index )
                 {
            D3Object d3Obje       c  t =    (D3O  bje   ct)d3ObjectList.get(index);
             
              in     t ve    rtexCoun   t = vertexCountArray[  in     de      x];
                          d3Object.   vert  exesOffset         = offset;
                   Lis  t vertexL    ist =   new ArrayList();
            offset = IntVer  tex. popu     lateObjects     (dataSrc, offse  t ,   ver       texList, vertexCount);  
                     
                    int      faceCoun   t = fac  eCountArray[in     dex];
                                d3O              bject.  f  acesOffset = of   fset;
             List     faceList = new   ArrayList();
                 offset = Out   doorFace.populateObjects    (dat aSrc, offset, faceLis t, fac    eCount)   ;
                 
                    // IMPLEMENT      : THIS IS BROKEN -- ne        ed       to      br    ea       k up function above and move    i t     between orderi   ng and   texture     s
 //                if (7 ==      GA  ME_V   E     RS   ION)
//                    {
//	                                     in     t   bspNodeCount = b           spNodeC         ountArray[i  nde  x  ];   
//	                                   d3Objec t.bspNo      d          eOffs   et = of  fset;
//	             List bsp NodeList = new Arra             yList();   
//	            of   fset   = BSP    Node   .populat      e  Object  s(d    at   a           Src, of    fse   t, b spNodeList, bspNodeCount)             ;
//                  }
              
            d   3Object .se  tVertexList(vertexList);
            d3Objec    t.setFa  cetList(faceList);
                      / / IMPLEMENT: d3Object.setBSPNodeList(bsp N       odeList);
        }
                          
         return o    ff   set     ;
    }
    
    /**
                * @param ne w  Data
         * @param   offset
     * @pa              ram d3O     bjectAr     ra      y
         * @return
     */
           pu    blic sta   tic  int updateData(byte[] newData, int offset, List d3ObjectLis  t)
    {
         ByteConversion    s.  setInte        ger     InByteArrayAtPosition(d3ObjectList.size(),           newData,    offs   et);
            o   ffse     t += 4 ;
      
        f  or  (int d3Ob        jectIndex   =        0; d3Ob    jectInde x <   d3ObjectList.siz  e(); ++  d3ObjectIndex)
           {   
                          D   3Object     d3Object = (D3Object)d3Ob   jectLis    t.get(d3        ObjectIn         dex  );
   
                     ByteConversio  ns.setZeroTer minatedSt    r        ing    I    n   Byt    eA rrayAtPositionMaxLength(d3      Object.       getNa me1(), newDat  a        ,    offset, NAME1_MAX_    LENGTH);
                offset += N     A          ME1_MAX    _LENGTH;
            ByteConve  rs      i  ons.set  Zer oT   erminatedStringIn   ByteArrayAtPositionMa   x L  ength(d3Object.ge        tNa    me2      (), ne      wData       , offset, N   AME2_MA   X_LENGTH);
               offset +  = NAME    2_MAX_LENGT    H;

                           // stu ff  we        don't understan  d yet      
                System.a  rr  aycopy(d 3Obje         c    t.getRe   mainingDat  a() ,     0,  newData  , offset, d   3Object.getRemain         ingData().len  gth);
                   offse     t += d    3Objec   t.get    Remaini     ngData().length;

                              // these wil       l be o              verwri   tten by       remainingDa  t        a ab    ove otherw ise
//                     ByteConversions. setIntegerInByteArrayAtPosition(d3Object.getFa  ceAr  ray().length, ne       wDa ta, FACE  S_CO UNT_OFFSET);
      //                Byt     eConversio   ns.setIn   tegerInByteArrayAtPosition(d  3O  bject.getVertex   Array().len g t         h, newData,          VER     TEXE   S_COUNT_OFFSET);
                 }

           for      (int index = 0; index     < d3    ObjectList  .size(   ); ++index)
                 {
                      D3Ob         ject d                3Object   =      (D3Obj   ect)d3Object      List.get(in  dex);
                           
                      of    fs     et = IntVertex.updateD   ata(newD    ata, offset, d3Object.g    etVertexList());  
                  offs      et = O   utdoo  rFace.update    Data(newData, offse   t,          d3     Ob   ject.g       e    tFacetLis   t());
           }
        
        return  offset;
    } 
    
          public Str  in g get    Name1()
     {
          return name1;
            }
    public void     setName1(      St    ring name    1)
         {
                        this.       name1     = name1;
       }

    public Strin g getName2()
    {
            return name2;
    }
          public      void setName2(St  ri   n                           g n                ame2)
    {
        this.name2 = name2;
    }
    
    public byte[] g    etRema     ining   Data ()
        {
            retur  n remain  ingDat  a;
       }
                      
    publ    ic in  t getRema iningDataOffset()
    {    
         return   R   E  MAI   NING_DATA_OFFSET;
      }
        
    public List      get      VertexList()
        {
                r          eturn this.verte      xLis t;
    }
        public voi d set  V  ertexList  (     List vertex       List )
      {
          this.vertex     List =            ve   rtex   Li      st;
    }
      

     public        List getFacet    List()
       {
        r eturn    this.facetList ;
    }
           
    pub     lic void setFacetList   (List faces     List)
        {
        this  .facetList = facesList;  
    }
    
    p          ublic      lo    ng getOffse  t(    )
     {
        return     this.offs et;
         }
    public    long getVertexesOffset()  
    {
                     return this.vertexesOffset;
        }   
       public long getF  acesOffset()
    {
        return   t            hi   s.facesOffset;            
      }

    public    int    getXMin   ()
    {
           int min    = Intege   r.MAX_VALUE;
         f  or       (int vertexInd ex = 0 ; verte   xIndex < vertexL   ist.size();       ++vertexIn dex)
        {
            IntVertex vertex = (In   tVertex)   vert            e       xList.get(vertex  Index);
                            int value = ve   rtex.getX();
    		if (min > v      alue)  min = va   lue;
        }
          
           re     turn      min;
    }
              

    public void             setXMin(      int newM     in)
     {
         int adjustment = newMin - ge tXMin();
        for (int vertexIndex = 0;    vertexIndex < vert      exList.size(); ++ver     t        ex     Index)
        {
                     In      tVertex vertex =     (IntVertex)vertexList.get(    verte     xIndex  );
                 vertex.setX(vertex.getX() +       adjustment);
           }
//                   ByteC  onv  ersio  ns.setInte   ge  rInByteArrayAtPosi        tion(ByteConve           rsions.getInteger            In     ByteA      rrayAtPositi        on(remainingDat a, UNK_X_OFFSET - REMAINING_DATA_OFFSET) + adjustment, remainingDa    ta, UN K_X    _OFFSE T - REMAINING    _DATA_OFFSET);
           ByteConversions.s   etInteger     InByteAr  rayAtPosition(ByteConve  r  sions.getInt       egerInByteArrayAtPosition(r   emainingData, MI   N_X_OFFS        ET - REMAINING_DATA_OFFSET) + ad    justme              nt, rema in    ingD  ata, MIN_X_OFFSET - R     EMAINING_DATA_OFFSET);
        ByteConversions.s   etIntegerInByteArrayAtPosi tion(ByteConversions.getIntegerInB  yteArr     ayAtPosi  tion(re   mainin gData, M  AX_X_OFFSET  - REMAINING_DATA_OFFSET) + ad justm   en  t, r   emainingData, MAX_X_OFFSET -   REMAI  NI     N   G   _DATA_OF         FSET);
                  Byte Conversions     .    setI nteger  I   nB    yteArrayAt  Position(B      yteConversions.getIntegerInByte   ArrayAtPosition(remai  ningData, C EN    TER_   X_O     F  FSE   T - REMAI NIN     G_DATA_OF  FSET )      + adjustm   ent, r emainingData, CENTER_    X_OF     F  SET - REMA    INING_DATA_OFFSET);
    }
    
    pub     lic int ge  tXM  ax()
                {  
               int max = Integer. M IN_VALUE;
           for (int   vertexIndex =  0;   ver  te    xIndex < vertex  List.size(); ++vertex   Index)
           {
            IntVertex ver    tex = (  IntVertex)vertex    List.get(ve       rtexIndex);
                   in       t val     ue    = vertex.getX  (    );
    		if (max < valu   e)  max = value;
              }
              
                return     max;
    }
    
    public int getYMin()
                  {
          int min = In   teger.MAX_VA   LUE;
        for (int verte    x      Ind   ex = 0; vertexIndex < vertexList.s     ize(); ++vertexInde x)
           {
            IntVertex vertex = (IntVertex)vertexList.get(vertexIndex);
            int val  u     e = ver    tex.getY();
    		if (mi      n > value)  min       = value;
          }
        
                           return min;
    }
       
    public void setYMin(int ne    wMin)
           {
            int adjustment = newMin - getYMin();
        for (int vertex   Index =       0; vertexIndex < ver   te   xList.s   ize(); ++v     erte   xIndex)
                  {
             I    ntVe    rtex verte x = (IntVertex)vertexL  ist.get(vertexIndex);  
                    vertex.setY(vertex.g  etY() + adjustment     );
        }
//        B    yteC onversions.          setInt e      gerInByte  ArrayAtPosition   (ByteCon   ve     rs               ions     .g             etIntegerInB yteA rra        yAtPosition(r    emainingData, UNK_Y _OF         FSET    - REMAIN   ING_    D          ATA    _OFFSET) + adju             stment, rema        iningData, UNK_   Y_OFFSET - RE   MAINING_DATA_OF     FSET);
         ByteConve      rsi     ons.set   Inte       gerInByteArrayAtPosi   tion(ByteConv     ersions.getIntegerInB    yt   eArrayAtPosition(remaining Data,    MIN_   Y_OFFSET - REMAI        NING _D        ATA_OFFS    ET) + adjustment,    remain  ingDat      a, MIN_  Y_OFFSET - RE  M  AININ  G _DAT     A_OFFSET);
                   ByteConver  sions.setIntege       rIn ByteArrayA  tPosit     ion(ByteConversions.     get IntegerI   nByteArrayAtPositi    on(    remainingData, MAX_Y_OFFSET - REMAINING_DATA_OFFSE   T) + adjustment, re    main    ingDa   ta, MAX_Y_OFFSE   T - R   EMAINING_DATA_OFFSET);
        Byte     Conve     rsions  .setIntegerInByteAr    r    ayAtPosition(ByteCo    nversions.getIntegerInBy    teArr        ayAtPosition(remainin     gData, CENTER_Y_OFFSE   T - REM   AINING_DATA_OFF   SE    T    ) + adjustment, r emainingDat a, CENTER_Y_OFFSET  -    REMAIN       ING_DATA_OFFSET);
    }
    
    public int      getY Max(  )
    {
                   int max = In         teger.MIN  _VALUE;
                 for (int vertexIndex = 0;   vertexI    ndex < v  ertexList.size(); ++vertexInde    x)
        {
                In   tVert          ex verte   x = (IntVertex)vertexList.get(v    ertexIndex);
            int value =   vertex.getY();
    		if (  max < va     lue)  max = value;     
             }
           
        re    turn max;     
    }
    
    public int getZMin()
    {
          int min = Integer.MAX_VALUE;
        for (int verte   xIn  dex    = 0; vertexIndex < vertexList .size   ();         + +vertexIn   dex)
        {
              I      ntVertex vertex    = (  IntVerte     x)vertex  List.get(vert   exIndex);
               int v   alue =   vertex.getZ(); 
    		if (min > value)     min     = va   lue;
                       }       
            
        r   eturn min;
       }
       
    p   ubl     ic void     setZMin(int newMin)
    {
         int a djust   m ent      = newMin - getZMin  ();
           for    (int vertexIndex = 0;    vertexIndex < verte           xList.size()   ; ++ve   r texIndex)
             {
                  In tVertex ver         tex    = (     IntVertex        )v     ertexList.get(vertexInde   x);
                       vertex.setZ(vertex.g  etZ() + adjustmen  t);
                   }
//         ByteConver   sions.setIntegerIn ByteArrayAtPosition(ByteConversions.getIntegerInByteArrayAtPosi  ti  on(remainingData, UNK_Z_OFFSET - REMAINING_DATA_OFFSET) + adjust   me     nt,    remainingData, UNK_Z_OFFSET    - REMAINING_DATA_OFFSET    );
        Byte        Conversions.setIntegerInByteArrayA  tPosition(ByteCon ver sio   ns   .  getIntegerInByteArrayAtPosition(re  maini   ngData, MIN_Z        _OFFSET - REM        AINING    _DATA_OFFSET) +   adjustment, remainingData, MIN_Z_  OFFSET - REMAININ          G_DA TA_OFFSET);
        By     teConversions.setIntegerInByteArrayAtPosition(ByteConversions.get   IntegerI        n  ByteArrayAtPosition(   remaini   ngData,    MAX_Z_OFFSET - RE       MAINING_DATA_O      FFSET) + adjust  ment, remainingData, MAX_Z      _O         FFSET - REMA  INING_DATA_OFFSET);
//         By       te      Con  versions.s       etIntegerInByteArrayAtPosition(ByteConv   ersions.getIn  tegerInByteArrayAtPositio      n(r   emaining Data, CENTER_Z_OFFSET -     REMAINING_  DATA     _OFFSET) +  adjustment , remainingData, CENTER_Z_OFFSET - REMAINING_DATA_OFFSE T);    
    }
            
    pu   b lic int getZMax()
       {
        int max = Integer.MIN_VALUE;
        for (i  nt vertex   Index         = 0; vertexIndex   <         verte xLi        st.         siz      e()      ; +   +vertexI ndex)
        {
              IntVertex vertex =        (IntVer tex)vertexList.get(vert    exIndex);
                                   int value = vertex.get  Z();
    		if        (max < value  )  max = value;
         }
        
          return ma  x;
    }
    
    public s  t        a    tic    int computeDataSize(in   t gameVersi  on, by  te[] da          ta, in  t o  ffset)
    {   
            int d3ObjectCount  = ByteConversio    ns.getIntegerInBy    teArrayAtPosition(data, offset);
        offset +=  4;

           if (d3Object            Count > data.length)
               throw new DataSi      z      eException("d3   ObjectCo     unt=" + String.valueOf(d3Objec   tCount) + " at offset=" +     String.valueOf(offset));
                
          int vertex Count   Array[] = new  i nt[d     3ObjectCount];
          int faceCountA   rray[]    = new i    nt[d3ObjectCou         nt];
        fo   r (int d3ObjectIndex = 0 ; d3Obje  ctIndex      < d3ObjectCou          nt; ++d3 ObjectIndex)    
          {      
                 vertexCountArray[d3ObjectIndex] = ByteConversions.getIntegerInByteA rrayAtPosition(data, offset + VERTEXES_COUNT_OFFSET);
                 fac     eCountArray[d3ObjectIndex     ] = ByteCon             v    ers  ions.getIntegerInByteArrayAtPosition(data, off        s    et + FA    CES_COUNT_OFFSET);
              
            offset    += D3OBJECT_LENGTH;
          }

                   for (int index = 0; index < d3Object  Cou    nt; ++index)
          {
            of      fse     t += verte xCountArray[index] * I     ntVerte      x.ge    tRecordSize();
        }

          f or (int index = 0; index <    d3ObjectC   ount; ++index)
        {
            offset +=       faceCountArray[index] * Out  doorFace.getR   ecordSize();
           }
        
          return offset;
     }
    
    public int getRe       cor dSi ze(int ga  meVersion)
       {
        return D3O      BJECT_LENGTH + (ver         texList  .size() * IntVertex.getR         ecor   dSize())        +    (facetList.size()      * Out doorFace.getRecordSize());
    }

    // Unknown things to decode

    public static List getOffsetLi       st()
    {
        List offsetLi     st = new ArrayList();
        offsetList.add(new ComparativeTableControl.  OffsetDa   ta(ATT     RIBUTE_OFF   SET, 4, Comparative Table       Co     ntrol     .R  E  PR  ESENTATION_INT_DEC, "Attributes"));
        
         offsetList.add(new C    o  mparativ          eTa bleC           ontrol.OffsetData(VERTEXES_COU       NT_OFFSET, 4, C   omparativeTable     Control.REPRESEN  TATION_INT    _DEC, "# of vertexes"));

        offsetList.add(new Comparat    iveTab  leControl.OffsetData(FACES_CO UNT_OFFSET, 4, ComparativeTab leControl.REPRESENTATION_INT_DEC, "# of Facets"));
              
        offsetList.add(new ComparativeTableCo  nt    rol.OffsetDat   a(CENTER_X     _OFFSET,    4    , ComparativeTableControl.REPRESENTATION_INT_DEC,      "center X"));
        offsetList.a dd  (new Comparat  iveTableControl.OffsetData(CENTER_Y_OF  FSET, 4, ComparativeTableC  ontrol.REPRESENTATION_INT_DEC, "center Y"));

        o       ff    setList.add(new ComparativeTableControl.OffsetData(X_OFFSET, 4, ComparativeTableControl.REPR  E  SENTATION   _INT_DEC,    "X"));
         offsetLi  st.add(new  ComparativeTabl   eC    ontr  ol.OffsetData(Y_OFFSET, 4, Comparat     i     veTableControl.REPRES ENTATION_INT_DEC,             "Y"));
          offsetList.add (new ComparativeTable Control   .Off     s     etDat  a(      Z  _OFFSET, 4 , Compa  rativeTableControl.REPRE    SEN    TATION_INT_DEC, "Z"));

        offsetList.add(new Com    p     ara  tiveTableControl         .OffsetData(M     IN_X_OFFSET, 4, Comparati  veTableControl.REPRESENTAT  ION_INT_DEC, "Boundin   g MIN X"));
        offsetList.add(new ComparativeTableControl.OffsetData(MIN_Y_OFFSET, 4, ComparativeTableControl.RE PRESENTA   TION_INT_DEC, "Bounding MIN Y"));
          offsetList.add(ne  w  Comparativ      eTa   bleControl.OffsetData(MIN_Z_O   FFSET, 4, Co     m   p    arativ    eTableControl.REPRESE     NTATION   _INT_DEC, "Bounding MIN Z"));

             offsetList.add(new Comp arativeTableControl.OffsetData(MAX_X_OFFSET, 4, ComparativeTab    leControl.REPRESENTATION_INT             _DEC, "Bounding MAX X"));
        offsetList.add(new     ComparativeTa   bleControl.OffsetData(MAX_Y_OFFSET, 4, ComparativeTa   bl   eControl.   REPRESENTATION_INT_DEC  ,    "Boun      ding MAX Y"));
        offsetList.add(   new Compa rativeTableCo  ntrol.OffsetData(MAX    _Z_OFFSET, 4, ComparativeTableControl.REPRESENTATI    ON_INT_DEC, "  Bounding MAX Z"));

        offsetL          ist.ad   d(n   ew ComparativeTab     leControl.OffsetData(B    F_MI N_X_O     FFSET, 4, Comparative  TableControl.REPRESENTATION_INT_D   EC, "BF_MI   N_X"));
        offsetList.add(new ComparativeTableContro  l.O   ffsetData( BF_MIN_Y_OFFSET, 4, ComparativeTableC      ontrol.      REPRESENTATION   _INT_DEC, "BF_MIN_Y"));
            offsetLi      st.add(new    ComparativeTableControl.Offset   Data(BF_MIN_Z_OFFSET, 4   , ComparativeT ableControl.REPRES ENTATION_INT_DEC, "BF_MIN_Z"));

        offsetList.add(new    ComparativeTableControl.OffsetData(BF_MAX _X_OFFSET, 4, ComparativeTableContro   l.REPRESENTATION_IN T_DEC, "   BF_MAX_X"));
        offsetList.   add(   new ComparativeTableControl.Offs  etData(BF_MAX_Y_OF FSET, 4,    ComparativeTableControl.REPRESENTATION_INT_DEC, "BF_MAX_Y"));
        offsetList.add(new ComparativeTableCont      rol.Offs etData(BF_MAX_    Z_ O  FFSE    T, 4, Com  parativeTableContr  ol.REPRES  ENTATION_IN      T  _DEC, "BF_MAX_Z"));

         of   fsetList.a      d   d(new    ComparativeTableControl.OffsetData(BOUNDIN     G_CENTER_X_OFFSET, 4, Comparati   veTableC   ontrol.REPRESENTATIO    N_INT_DEC, "Bounding center     X"));
        offsetList.add(new ComparativeTableControl.OffsetData(BOUNDING_CENTER_Y_OFFSET, 4, ComparativeT   ab       leContro l.REPRESENTATION_INT_DE C, "Bounding center Y"));
        offsetList    .add(new   Co      mparativeTableControl.OffsetData(BOUNDING_CENTER_Z_OFFSET, 4, ComparativeTableControl .REPRESEN    TATION _INT_DEC,   "Bounding center Z"));

        offsetList.add(new ComparativeTableControl.Off   setData(BOUNDING_RAD     IUS_OFFSET, 4, ComparativeTableC   ontrol  .REPRESENTATION_INT_D   EC,    "Bounding radius"));
        
        // Suspect  ed as unused
        offsetList.add(new Comparative  TableControl.OffsetData(CONVEX   _FAC    ETS_COUNT_OFFSET+2, 2, ComparativeTableControl.REPRESENTATION   _SHORT_DE   C, "Unused"));
        offsetList.add(new Co mparativeTableControl.OffsetData(VERTEX   _OFFSET_OFFSET, 4     , ComparativeTableControl.REPRE  SENTATION_INT_DEC, "VERTEX_OFFSET"));
              offsetList.add(new Comparat   iveTableControl.OffsetData(FACES_OFFSET_OFFSET, 4, ComparativeTableControl.REPRESEN  TATION_INT_DEC,  "FACES_OFFSET"));
        offsetList.add(new ComparativeTableControl.OffsetData(ORDERING_OFFSET_OFFSET, 4,   ComparativeTableControl.REP  RESENTATION_INT   _DEC, "O   RDERING_OFFSET"));
        offsetList.add(new Compara tiveTableControl.OffsetData(BSPNODE_OFFSET_OFFSET, 4, Comparative TableControl.REPRESENTATION_INT_DEC, "BSPNODE_OFFSET"));
        offsetList.add(new ComparativeTableControl.Offse   tData(BSPNODE_COUNT_OFFSET, 4, Comparative    Table  Contro        l.REPRESENTATION_INT_DE        C, "   # of BSP   Nodes"));
        offsetL ist.add(new Com   parativeTab    leControl.OffsetDat  a(DE   CORATIONS_COUNT  _OFFSET, 4, ComparativeTableControl.REPRESENTATION_INT_DEC, "# of         Decorators"));
        offset List.add(new Com       parativeTableControl.OffsetData(CO  NVEX_FACETS_COUNT_OFFSET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, "# of Convex Facets  "));
        
        return offsetList;
    }
    
    public s      tatic ComparativeTableControl.D ataSource getComparativeDataSource(final Lis    t d3Object     sList)
    {
        return new ComparativeTableControl.DataSource()
        {
              pub    lic int getRowCount()
            {        
                    return d3ObjectsList.si ze();
            }

            public byte[] getData(int dataRow)
            {
                D3Object d3Object = (D3Object)d3ObjectsList.get(dataRow);
                return d3Object.getRemainingData();
            }

            public i   nt getAdjustedDataRowIndex(int dataRo w)
                       {
                return dataRow;
            }
            
            public   String getIdentifier(int dataRow)
            {
                D3Object d3Object = (D3Object)d3ObjectsL  ist.get(dataRow);
                return d3Object.getName1();
            }

            public int getOffset(int dataRow)
            {
                D3Object d3Object = (D3Object)d3ObjectsList.get(dataRow);
                return d3Object.getRemainingDataOffset();
            }
        };
    }
}
