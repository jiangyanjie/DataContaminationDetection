/*
 *   Copyright (c) 2005 (Mik  e) Mauri   ce Kienenb        erger (mkienen           b@gmail.        com)
 * 
 * Permissi   on is hereby granted, free of cha r      ge, to           any person o     btain    ing a copy
 * of this software and ass     ociated documentation f  iles (the "Softw   are"),    to deal
 * in t    he Software without restriction, including    without limitation the rig             h t    s
 * to u  se, cop y, modify, merge, pu       blish, distrib    ute, s ublicense, and/or sell
 * c   opies of the S     oftware,    a   nd to permit p       ersons to whom the Software is
 * furni  shed to    do so, subjec  t to t    he foll    owing con  ditions:
 *    
 * The    above co  pyright notice and this perm       issio    n not  ice shall be  included in
 * all copies or s           ub   stantial p   ortions of the Softwar   e.
    *
 * THE SOF   TWARE IS PROVIDED "AS IS" , WITHOUT WARRANTY OF ANY KIND, EXPRESS OR   
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO  THE WARRAN     TIES OF MERCHANTABILITY,
 * FITN      ESS      FOR A PARTICULAR       PURPOSE AND NONINFR       IN  GEMENT. IN NO   EVENT SHALL TH    E
      * AUT  HO RS OR C  OPY  RIGHT HOLDERS BE LIA   B  LE FOR ANY CLAIM, DAMA             GES OR OTHER
 *    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OT     HERWISE, A   RIS   ING FROM,
 * OUT OF OR IN CONNECTION W    ITH THE SOFTWARE OR THE USE OR      OTHER DEALINGS IN THE
      * SOFTWARE.
 */

package org.gamenet.application.mm8levele    ditor.data.mm6.indoor;

i   mport java.util.ArrayList;
import java.util.List;

import org.gamenet.application.mm8leveleditor.data.Da taSizeException;
import org.gamenet.application.mm8leveleditor.data.GameVersion;
import org.gamenet.applic  ation.mm8leveleditor.data.mm6.Che  stContents;
import org.gamenet.application.mm8le     veleditor.data.mm6.Cr     eature;  
import org.gamenet.application.mm8leveleditor.da    ta.mm6.Item;
import or g.ga      mene   t.application.mm8leveleditor.data.mm6.MapN     ote;
import org.gamenet.util.  ByteConversions;

p      ublic c   la   ss DeltaIndoorDat  aMap
{ 
    pr  ivat  e static final    int MAP_    RESET_COUN T_OFFSET      = 0; //  4 bytes
    private st        atic final int LAST_ RESET_D   AY_OFFSET = 4; // 4  byt    es

         // MM7 and MM8 values
         pri           vate static final    int  STEALING_DIFFICULTY_ADJUST    MENT_OFFSET = 8; // 4 bytes //   MM7,M  M8
             private static final int MAP_O  N_ALERT_OFFSET = 12;     // 4? 2? 1? bytes //  MM   7,MM8
    //     These values must match values      in    DataHe      ader!  
    p  riv             ate static final int TOTAL_NUMBER_OF_F ACETS_OFFSET  = 16; // 4 bytes // MM7,MM8,       padding ot    herwise
    privat   e static   final int TOTAL_NUM   BER_OF_SPRITES  _OFFSET    = 20; // 4 byt es     // MM7,MM8, padding otherwise
     private stat ic final int TOTA L_NUMBER_OF_D3O  BJECTS_O       FFSET =   24; // 4 bytes // MM7,MM8, pa             dding o    therwise
    priv   ate stat    i   c       final int MAPNOTE    S_OFFSET_OFFSE T = 28; // 4 bytes // M  M8, p  a                 dding otherwise
    p                    rivate    static final  int    PADDING_OFFSET =    28;    // 12  bytes

                pr     ivate static fina l int VISIB       LE_MAP  _OFFSET = 40;
       pr  ivate static fin      al i      nt       VISIB      LE_ MAP_SIZE = 875       ; // map line data?
    
        private int gameVersion = 0;
          
     pri     vate  i   nt mapRes  etCount = 0       ;
    pr ivate int last  ResetDay = 0;
     private i  nt stealingD  ifficultyAdjus    tment = 0   ;
    private in  t mapAlertSt  atus =      0;
      p      r ivat            e in  t to  talNumbe         rOfFacets = 0;
                   p  riva         te   int            totalNumberOfSprit      es = 0;
      priva     te int tot    alNumbe rOfD   3Objec   ts = 0;    
    pr  iv ate int mapNotesOf     fs  et = 0; 
    
    pri      vate int reserv   ed1 = 0;
         pr     ivate int reserved2 = 0      ;
    
              private int rawVisi    bleMapDataOff     set = 0;
    p  rivate    by           te rawV      isibleMapData[] = null;
    
    privat      e i    nt face     tA    tt    ribu   teArray[  ]   = null;
    private short spriteAttri buteArr   ay[ ] = nul      l;
    
           private List   cr eatureList    = null;
    privat  e List itemList = null;
    
       private            /*ChestC   ontent  s*/ List chestCont    entsList = null;

    private  static final int NUMBE   R_OF   _        D   OOR        S = 200;
    private List doorL  is   t    = nul  l;

       p         ri    vate     static final  int NUMB    ER_OF_M  AP_BITS = 200;
        p    riva   te byte mapBits[] = nu  ll;
        
    priv     ate          long lastVisite           dTime;
           
    private static fin  al int S        KY_BIT  M   AP_MAX_LEN     GTH = 12;
         private   String   sky          BitmapName;
         
    priv ate int dayAttr  ibut  e;
              pr  iv   ate int fogRa      nge1;
     private int  fogRange    2;

     priv ate i nt attribu   tes; // MM8
    private int ceiling;//          MM8
               
         priv   ate stat     i       c fi  nal int         REMAINING_D            ATA_LENGTH   = 16;
              private   byte rem   ainingData[] = null; /   /    unused?
    
        priva te  stat       ic    fi    nal int NUMBER_OF_MA      P_NOTES = 100;
    private     List mapNoteList;
    priva   te    int activeMa     pN          oteCount;  

    private   l o  ng             z  eroesDataOffset = 0;
      private               lon  g creature   DataOffse     t = 0;   
          private        long    itemDataOffset = 0;
    private lon g chestContents    Offs     et =      0;
      private     long remainingDataOffset =           0;
     
             
    pu         blic Delta    IndoorData  Map(int   gameVer    sion, by     te data[], int vari    ableDoorDataS    ize)
          {
          th   is(g     ameVers io  n, data, -1, -1,     var    iableD  oo     rDataSize);
    }
    
    publ    ic DeltaIndoor    D ataMap(int gameVers  ion,      by  te     dat   a   [], int to      tal              Sprit eCount, int totalFacetsCount, int varia            bleDoorDa    taSize)
    {
        super    ();
        this.gameVersion = gameV   ersion      ;
            
          int     offset =    0;
         this.mapReset Count =  ByteConversion    s.getIntegerI   nByt eArrayA     t   Posi      tio  n(data, offset);   
              of   fset += 4;

              th    is.lastResetD    ay = ByteConversi       ons .getIntegerI        nByteArrayAtPosit ion(data,          offset);
        offset += 4;

        if     (   G    a   meVers ion.      MM    6 !=  game  Version)
                 { 
                       thi  s.stealin  gDif    ficult  yAdjustm  ent = ByteConversions.getI ntegerIn ByteArrayAtPosit   ion(data,   offs et);
               offset += 4;

                 this.mapAlertStatus = ByteConversions.ge    tIntegerInByteArrayAt  Position(   data, offset);
                offset += 4;

                th               is.totalNumber  OfFace             ts = ByteCon   ver      sions.            getInteg  e   rInByteArrayAtPo    sit  ion(data      , offset);
                             offs   et +=                       4;

                   this.t      ota lNumberOfSprites =        B   yte           Con ve        rsions.getIn tegerInByteA   rrayAtPosition(data, offset)   ;
                          off    set +=         4;

            th     is.totalNumberOfD 3Objects = Byt    eCo               n  versions.get         IntegerInByte       Ar           rayAtPositi   o n     (data, offset);
                  offset     += 4;
  
                            // mapNotesOff  set not used   f     or MM7
              thi     s  .mapNotesOffset = B    yteConversions.getIntegerInByteArr           a   yAt  Position(data   , offset); 
                 off  se     t     += 4;

                  this.r    eserved1 = ByteConv           e    rsi  ons.getInte  gerIn   ByteAr     r   ayAtPositio        n(d   ata, o   ffset);
            offset             += 4;

                 th is.rese     rved2    = ByteConversions.getIntegerI   nByt   eArr    ayAtP   osition(da     ta, offse   t);
                    offset     +=  4;
        }


        this       .rawVisib  le  Ma     pDa   taOffset =   offset;
             this.r    aw   Vis             ibleMapD   ata =  new byte[        VISIBLE_MAP_SIZE];
            Sys  tem.arraycopy     (data, offset, ra    wVis  ibleMapData    , 0, rawVisible       MapData.len       g   th);
                    offset += rawVisibl     e    MapD            ata.lengt    h;
       
        if (Gam     e     Versi     o  n. MM6 != gameVersion)
             {
             f  acetAttributeArray = new in     t[tot  a lFacetsCount];
                        fo    r (int d3Ob   ject  Index = 0; d3Object  Index < totalFacetsCo   unt; d3ObjectInd     ex+   +)
                   {
                          fa  c etAttributeArray[d3ObjectI  nde       x] = B    yteConversio   ns.getIntegerInByteArrayAtPosition(data, offset);
                            offset += 4;
             }
                      
            s prite   Attribute      Arr      ay = n      ew short[t     otal  SpriteCount];
            for (       in  t spriteIndex = 0;     s   priteIndex <          totalSpriteCount; spri   te   Index++)
                  {
                     // read one short for every Sprite.
                   spriteAt        tributeArr  ay[sp      ri      teInde   x]       = Byt  eConve   rsions.getShortI       nByteArrayAt          Po       sition(data, offset);
                    offset += 2;
                            }
        }
        
             // Cr   e  atures
            creatureLi   st = ne       w  ArrayList();
          offset  = C  r   eature.popula te         O  b   j    ects(gameVersion ,             data, o          ffset, creatureList);
                 
                 //        I          t    e    ms
            itemList = new ArrayList       ();
        this.itemDataOffset   =   offset;
        offset = Item.populateObj   e         cts(gameVers  ion, data, o       ffset, itemList)      ;
             
        // Ch est Cont   en ts
              c   hes    tContentsList = new ArrayList()     ;
        of  fset =        ChestConten   t   s.populateObjects(gameVersion, da    t     a, offs            et , chestC     ont   ent     sList);   
               
        doorList =     new Array      List();
          o   ffset = Door.popul   ate Objects(data,      offs   e   t, d oo  r    List, NUMBE  R_OF_    DOORS, variableD    oor                 D    at aSize);     

          this.map  Bits = ne   w byte   [NUMBER_OF_MAP_B  ITS];
             this.m   apResetCount = ByteConve      rsi    ons.g etIn      tegerInByteArra    y AtPositi   on(     dat      a, off set);
                      o   ffse       t += NUMBER_OF_MAP_BIT   S;

                   this .las      tVisit      edTim e = ByteConver   si   ons  .getLong    In      ByteAr  rayA  tP  os        iti    on(d   ata, o  ffs  et);     
                      of fset +   =     8;
         
        this.skyBitm  apName       = ByteC    onversions.getZeroTermi             na    t      edStringInB yt      eA rrayAtPositi  onMaxLen   gth     (data, offset  ,            SKY_BITMAP_MAX_LENGTH);
        offset  += 12;
                   
          th  is.d                     ayAttribute = ByteC  onversion s.getInteg  erInB               yteArray    AtPosition(dat a, off     set);
            off     set += 4;
                        
            this.fogR  ange   1   = By  teConversions  .getIntegerInB  y   teArrayAtPo   si t    ion(data, o      ffset)     ;
        offse     t        += 4;
                             
             this.fogRa nge2 = B      yteCon                       versions    . get         Inte   ger  I        nByteArrayAtPosit       i         on(d      ata  , off   set  );
                    of         fse                   t +        =      4;
        
            t     hi    s.attributes   =    ByteCo nversion        s.get   In  teger      InB  yte   Ar  rayA    tPositi  on(data, off    set);
                         offse  t           + = 4;
              
        this.ceil  ing = ByteConver    sions.getInteg erInByteArr        ayAtPosition(     da   ta      ,                  off  s   et);
                  of     fset         +=      4;
         
        th is.r   emainingDataOffset = offset;
            this.remai  ningD  a t    a =   new by                 t  e[REMAINING_DATA_LENGTH];
                Sys       te    m.arra     ycopy( data, offse   t,       remainingData, 0, remai           ningData.length);    
         offset  +=          remaini   ngDat      a.   length;
         
                      if ((Ga     meVersio   n.MM6   !=    gameVersion  ) && (GameVersion.M   M7       != gam    eVersion))
            {
                //   Map Notes      
                     t     his.mapNo     teList = new             ArrayList(     );       
            o     ffs  et = M    apNote.popu    lat  eObj  e   cts(           data, offset, this.mapN     oteLi           st   ,          NU           MBER _OF_MAP_NOTES);
                   
               /  /     Sometimes n      ot the                 re????
                           if (data.  length   != off set)
                                        {
                       this.a     c     tiv       eMapNoteC   o   un  t     = By  teCon        ve      rsions.getIn   te ge  rInBy  t       e Arra       yAtPosition       (     data, of  fset);        
                                   offset         +=        4     ;   
                              }
        }
          
                           
           //             Insure    we   've read all the    dat a
                   i   f     (of      fset !=          data      .le  ngth)  throw   new Run  timeExcepti on("offset<" + of        fse  t + "      > !      =  file.    length<"       +  data.length + "   >");
    }
                
    p   ubli       c s  t            atic boo    l         ean   ch   eckDat  aIntegrity(i            nt    gameVers    ion, b      yte[        ] data , i        nt     offs   et,         int e   x pe    ctedNewOffset, int           va   ria bleDoorDataS  ize)                       
    {
                    if (GameVer    si  on.MM6 != gameV    ers       io  n)    
               throw new RuntimeExc ep  tion("          Inte     grity che   ck failed for       game version=" + gameVersion + ", m  ust  s    pe   cify to                tal co   unts.");
             
             ret    urn checkDa  t aIntegrity(game V    ersion,   dat   a, offset, expectedNe            wOff    set    , 0, 0,                variableD   oorDataSize);
    }   
    
    pub       lic        sta t ic                boolean check  DataI      ntegrit   y           (int gameV       ers     ion, byt   e[] data, int offset,    int expe     cted N   ewOf  fset,    int totalSpr    iteCo     u nt, int totalFacet      sC ount, in     t vari      ableDoo   rD ataSize)
    {
               tr  y
                            {
                             offset += 4;
               o     ff     set +        = 4;
                       
               if (GameV   ers     ion.             MM    6 != ga      m  e    Ve  rsion)
                            {  
                          offs  et  += 4;
                              offs    et += 4;

                                  of       fset += 4;

                                 o    f  fset += 4;

                                 o      ffset +   =   4;   
                                 offset      +=     4;
                    offset += 4;
                                                   offset += 4;
                                     }

              offse   t += VI   SIBLE_MA    P_SI   ZE;

               i          f (GameVersion.MM   6 !=  ga    meVe  rsion)
            {
                of   fset   + = totalFacet     sCount * 4         ;
                       offset += tot    alSpriteCount * 2;
               }
             
                   int  crea      tureCount = ByteC         o    n    ve     rs ions.getIntegerInByteArrayAtPosition(data, o f fs  et  );
                  of   fset +=   4;
                                offse t += creatureCou    n   t * Creature.ge            tRecor     dSize            (game   Ve rsion);
            
                   int item  Count = Byt  eConvers     io ns.    getIntegerInByt      eArray     AtPositi    on(data              , of  f     set);
                  of   fset   += 4;
                offset += it emCount *     I     tem.g    etRecordSi    z     e(gam eVers         ion);
                     
            int        c    hestContentsCount = ByteCo nversions  .getIntege   rIn   Byt            eArrayAtP  o    sition(d         ata, offset);
                        offset += 4;
                offset += ches    tCo ntentsCount *  ChestCont   en ts.getRe     cordSize   (ga    meVersion);
                   
              offse  t                     = Door     .compute    DataSize(data, offs       et, NUMBER_OF_DO         ORS,   variable    DoorDataSize) ;

                          of   fset +   = NUMBER_O       F_M  AP_BIT    S;

            off  set += 8;
            
                 offset += 12;
                        
                                       offs  et += 4;
                                  
                    offse  t +=  4;
                             
                  o      ffset += 4;    
              
                   offset += 4;
               
                 off  set += 4;
                           
               offset +      = REMAIN        ING_DAT   A_LENG        TH;
             
            if   (   (GameVersion.M M  6 !    =     gameVersi  on)     && (Game Version  .MM7             != g    ameV                  ersion)     )
                  {     
                     offs    et +=       NUMB   ER_OF  _M    AP_   N     OTES    * MapNote.g            etR e   cordSize()   ;
                          
                                           // Sometimes n  ot there???    ?
                      if          ( e      xpecte dNewOffse t != o  ffset)
                            {
                                   offset += 4;
                        }
               }
        }    
            catch (ArrayI nd                exOutOf  Boun       dsEx       ception exce  ption)    
             {    
                new Ru   ntimeE      xcept     ion("In    tegrity check failed for game ve    rsion=  " + gameVer     sion + ".", ex ception).printStac   kT        race();
                           return fa   lse;
                  }
         catch  (DataSizeExcept   ion ex       cept      io      n)
              {
                new RuntimeException("Integri    ty check       failed for game v        e rsio     n=" +  gameVersion     + ".",       e         xce ption).print     StackTr    ace() ;
                 retur      n false;
        }
           
                   i f (offset != ex    pectedNewOff   set)
             {
            new    Runt       imeExcept       i       on("I   ntegr   ity che  ck failed for game v  ersion=" + gameVer        sion +     ": offset   <" + offset +         "> != e   xpecte         dNewOffs     et<" + e            xpe     ctedNewOffs    et + ">") .      prin        tS      ta   ck  Trace();           
              }
             
                   return (  offset     = = e  xpectedNewOff  s        et      )  ;
         }
    
    public byte[] upda te  D ata(by           te oldD   a             t    a[])
    {
            in      t       newDat   aSize = 0;
        
        newDa        taSize +  =       4;
            new  Da  taSize  += 4;
           
                                   if (   Game   Ve   rsion.M    M6                 != gameVersion  )
           {
                     ne    wDataSi    ze += 4;
            newDat  aSize += 4;

                     newDataS                 iz     e += 4;

                   n   ewDat a       Size += 4;

                  newDataS   ize +=   4;
                newDataSize += 4;
                              newDataSize         +=    4; 
                         n    ewDa  t      aSize += 4;
        }
                      
        newData    Size   +=      rawVis        ibleMapData.length       ;
   
               if (GameVersion.MM6 != gam  eVersion)
         {
            newDataSize +=    facetAttribut  eA       r ray.length * 4;
                        new     DataSiz e +=   sp   r    i  teAttri    buteArray.le  ng    t  h * 2;
          }
        
           newDataSize += 4;
           newDataS   ize   += creatureList.size   ()   * C    reature.getR   ecordS        ize(       gameVersion);
           
                        ne  wData  Size +=      4;
           ne  wDataSize += itemList.        size() * Item.getRecordSize(gam   e   Version);  
                
        ne  wData    Si    ze += 4;
                         newDat aSiz e    += chestConte    nts          Li  st.    size() * ChestConte     nts.getRecordSize(gameV er    sion);
           
        newDataS    ize = Door.computeDataSi   ze(gameVersion, doorL      ist);      

        newDataSize += NUMBER   _ OF_MAP   _BITS      ;

                    newDataSize += 8;
               
              newDa    taSize += SKY_B      ITMAP_MAX_LENG     TH;
        
                  new      DataSize    += 4;
                
          n  ewD   ataSize += 4;
        
                ne     wDa  taSi  ze +=     4;           
                      
            new  DataSize += 4;
        
                 newDa    taSize += 4;
        
                   newDat    aSize +  = REMA       INING_DATA_L              ENGTH;

                      if ((Gam eVersion.   MM6 !=     ga  me Version)   && (Gam         eV   e rsion.MM    7 != game  Versio    n))
            {
                     // Map       N       otes   
            newD  ataSize +    = ma           pNoteList.size()      * MapNote.getRecord     Size(  );
            newDataSize += 4;
          }

                 byte newData[] = new b yte[newDataSize];   
         
                  int       of    fset = 0;
               
              Byte  Con      v    ers                ions.set   In tegerInBy   teArrayAtPosition(mapResetCount, new       Data, offset);
        offset += 4;
        
        ByteConversions.setInt  egerInB     yteA rrayAt  Pos itio     n( las     t     ResetDay,  newData, offset);
          offset +=       4    ; 

             if         (  GameVersi on.MM6 != g     ameVersion)
        {
            Byt     eConv     ersions.se      tInt    egerInByteA  r     rayAtPositi   on(this.stealingDif   ficultyAd  ju   stment,     ne      w      Data, o       ffset);
            offset += 4;

                 ByteCo   nversi  ons.se   tIntegerInByteArrayA   tPo            sition(th  i    s.mapAlertSt   atu   s, newD  ata, o       ffs   et)    ;
                         offset += 4;        

              ByteConversions.setIntegerInByteArrayAtP   ositi   on(this.      fa   c    etAttri     buteAr  ray.length, newData,   offset);
                      offset += 4;

                    B          y   teConversions.setIntegerInB    yteArrayAtPosi                                 tion(this.s    priteAttribu teAr    ra     y   .len  gth, new     Data, o           ffs      et);
                                   offs         et         += 4;            

                 B     yteConversions.setI         n   tegerInB  yteArr   a    yAtPosition(th    is.tota   l    Nu    mberOfD3Obje cts, newData, offs  et);
                         offset        +=  4;
 
                // ma  pNo   tesOff       s    et not used fo        r MM       7
            By    t  eConversion       s.setIntegerI nB     yteAr    rayAtPositio          n(thi   s.mapNot   esOffs   et, newData, offse       t);
                 offset +  = 4  ;

                      ByteConversio           ns.setIntegerInByteArrayAtP    ositio  n(t   his.reser  ved   1,   newData, offs et);
                          of fset += 4;

                                 B    yteConversion   s.se   t           Int  egerInByteArrayA    tPosition(this.reserved2       , new      Data , offset);
                    offset   +       =  4;
        }  

               offset += raw      VisibleMapData.l      en    gth;
            
                              if    (G   am  eVersion.MM6 != ga  meVersi  on )
                       {
                      for ( int d3        O     bjectIndex = 0; d3ObjectIndex <     facetAttri  buteArray.length;                 d3ObjectI  ndex   ++)
                        // fo           r (int d3 O b       je    ctInd      e  x = 0; d3ObjectI         ndex < d3  ObjectCount; d3O   bject      Index++)
                        {
                //      f    or (int fa         cet       In  dex =    0; facetIndex < d3Obj   ect[d3O bjectInde x].facetCou      nt;        facetIn    dex++) 
                            {
                    Byte  Conve    rsions.setIntegerInB    yt      e    Array    AtPosition(facetAttributeArray[d3ObjectI         ndex],  newDa       t  a, offset);   
                                offset      +=   4;

                            /   / d3Obj    ect[d3Objec   tIndex       ]   .get      Fac      et(         facetIndex).setAttribute(attribute);
                    }
                }  
                   
                    for (   int sprit eInd  ex = 0;       spr iteIndex          <    spriteAttribut   eArray.le  ngth; spriteIn  dex++)
                             //  for (int spr   i     teIndex = 0; s                    prite   Index <  spr       iteCount; sp       riteI          ndex++)
                      {        
                       // r  e    ad one short        for ev         ery Sp   rite.
                              ByteConvers      ion   s   .setShortInBy  teArrayAtPo   sition(spriteAttributeArray[spri   teIndex]    ,    newDa ta, offset); 
                         offset += 2;

                  // sprite[        sp riteIndex].s    etAttribute(a ttribu  te);
               }
          }
        
        offset =       Creatur     e.updateData (newData,      offset, crea     tu   reLis       t);
            
             offset     =      Item.up   d    at eData(newData, offset, ite    m  List);
              
         offset =    ChestContents.updateD    ata(newData, offset, chestContentsList);
        
        offset = Door.updateData(newData, offset, doorLi st);
          
        Syste  m.arraycopy(map     Bits, 0, newDat     a, offset , mapBits.length);
         offset += map   Bits.length;

        Byte    Conversion  s   .s   etL       ongI     nByte  ArrayAtPosition(th   is.l   astVisitedTim      e,         newData, offset);
         off  s  et += 8;
            
        ByteConversions.setZe  roTer     minatedStringIn ByteArrayAtPositionMaxLength(this.skyBitmapN      ame, newData, offset,      SKY_BITMAP_MAX_LENGTH);
                    o ffset    +=    12;     
         
          ByteConv     er    sions.setIntegerInByteArrayAtPosi     t  ion(th i  s.dayAt      tribute,         n    e  wData, offset) ;
          offset += 4;
         
        B    yte    Conversions.setIntegerInByteArrayAtPosition(this.fogRange1, newData, offset  );
        offset   += 4;
          
        ByteC   onversi     ons.setInte        gerInByteArrayAtPos        ition(this.  fogRa    nge2 , n ewData, offset);
                offset      += 4;

                  ByteConver  sions.setIntegerInByteArrayAtPosition(this.attrib       utes, newData, of     fse t);
          offset += 4;
          
        By    teConversi     ons.se    tI ntegerInByteArrayAtPo si tion(this.ceili         ng,      newDat a, of     fset);
                    offs   et += 4;

               System.arraycopy(remainingData,     0    , newData, offset, remainingData.length       );
         offs et += remainingData.length;
        
           if ((Gam    eVersion.MM6 != gameVersion) && (GameVersi     on.    MM7 != ga      m  eVersion))
        {
                   //  M   ap Notes
            offset = M          ap Note.updateData(ne     wData, offset,     this.mapNoteList);
              
              ByteConversions.setIntegerInByteArrayAtPosition(this.activ  eMapNoteCo       unt, n    ewData, offse  t);
            of      fset += 4;
        }
        
           return newData;
    }

    public int getMap  ResetCount   ()
    {
              return this.    mapResetCount;
    }
    public void setMapRes        etCou  nt(int u nkno     wn1)
    {
          this.mapResetCount   = unk  nown1;
    }
    public int getLastRes    etDay()
    {
        return this      .last ResetDay;
    }
    public voi  d setLas  tResetDay     (int u    n  known2)
    {
        this.la      stResetDay = unknown2;
    }

      pu   bl ic int               getAttributes()
    {
             return t            his.att  ribu    tes;
    }
    public void setAttribute s(int at   tributes)
    {
                this.attributes = attr    ibutes   ;
    }
    public int getCeiling()
    {
        ret    urn thi  s.ceiling;
             }
       public v    oid setCeiling(int ceiling)
    {
        th      is.     ceiling = ceili     ng;
    }
        public int[        ] getFacetAttributeArra    y()
      {
           return this.fa    cetAttributeArray;
    }
    public List getM     apNoteList()
         {
        return this. ma    pNoteList;
     }
    public short[]                 get     SpriteAtt     ributeArray    (    )
       {
        retu       rn this.spriteAttributeArray;
    }
    public int getActiv    eMapNoteCount()
         {
        return this.activeMapNoteCount;
      }
         public  void set    Acti  veM   apN  oteCount(int activeMapNo teCount)
    {
        this .       activeMapNoteCount = activeMap  No           teCount;
    }
      pu blic int ge  tMapAlertStatus()
    {
        return this.mapAlertStatus ;
    }
    public void set  M    apAlertStatus(int mapAlertStatus)
            {
              this.       mapAl   ertStat  us = mapAlertStatus;
    }   
    public      int   getM   apNotesOffset()
    {
        return this.mapNotesOffset;
       }
        public void     setMapNotesOffse       t(int    mapNo   tesO    ffset)
    {
            this.mapNotesOffset = mapNotesOffset;
    }
    public int g         etReserved1()
    {
        r  etu rn this.r eserved1;
    }
    public    void setReserved1(int reserved1)
    {
        this.reserved1 = reserved1;
      }
    public int    getReserved2()
      {
        return this.r       eserved2;
    }
     public void setReserved2(i nt reserved2)
    {
          this.reserved2 = reserved2;
          }
    public int getStealingDifficul tyAdjustment()
    {
               retu  rn      this.stealingDifficultyAdjustment;
    }
    public void setS   tealingDifficultyAdjustment(int stealin  gDifficultyAdju     stment )
    {
        this.stealin    gDifficultyAdjustmen       t = stealingDifficul  tyAd   justment;
    }
    p ublic int getTot alNumberOfD3Objects()
    {
        return        this.totalNumberOfD3Objects;
    }
    public void      setTotalN  umberOfD3Objec ts(int totalNumber OfD3   Ob  jects)
    {
        this.totalNumberOfD3    Objects = tot  alNumberOfD3Ob  j         ects;
    }
    public        int getTotalNu      mberOfFacets()
    {
        retur  n this.totalNumberOfFacets;
      }
    public    void setTotalNumberOfFacets(
            int totalNumberOfFacets)
    {
        this.totalNumberOfF  acets = totalNumberOfFacets;
    }
    public int ge tTotalN       umberOfSprites()
    {
         r  eturn this.       totalNumberOfSprites;
      }
    public void setTotalNumbe  rOfSprites(int totalNumberOfSprites)
    {
          this.totalNumberOfSprites = totalNumbe   rOfSprites;
    }

    publ ic List  getCreatureList()
      {
        return th     is.creatureList;
    }
    public List getItemList()
    {
        return this.itemList;
    }

    public List getChestC    ontentsList     ()
     {
        return this.chestContentsLi    st;
        }

    public byte[] getRemainingData()
    {
        return this.remainingData;
    }

    public long getCreatur  eDataOffset()
    {
        ret     urn this.creatureDataOffset;
    }
    pu   blic long get RemainingDataOf   fset()
    {
        return this     .remainingDat      aOffset;
    }
    public long     getChestContentsOffset()
    {
        retu     rn this.chestContentsOffset;
    }
    public long getZeroesDataOffset()
    {
        return th   is.zeroesDataOffset;
    }
    public byte[] getRawVisibleMapData()
    {
        return this.rawVisibleMapData;
    }
    public int getRawVisibleMa     pDataOffset()
    {
        return this.rawV   isibleMapDataOffset;
    }
    public List getDoorList()
    {
              return this.doorList;
    }

    public int getDayAttribute()
    {
           return this .dayAttribute;
      }
       public void setDayAttribute(int dayAttribute)
        {
          this.dayAttr   ibute = dayAttribute;
    }
    public int getFo      gRange1()
    {
        return      this.fogRange1;
    }
     public void setFogRange1(int fogRange1)
    {
        this.fogRa nge1 = fogRange1;
    }
     public int getFogRange2()
    {
        return this.fogRange2;
    }
    public void setFogRang e2(int fogRange2)
    {
        this.fogRange2 = fogRange2;
    }
      public long getLastVisitedTime()
      {
        return this.las   tVisitedTime;
    }
        public void setLastVisitedTime(long lastVisitedTime)
    {
        this.lastVisitedTime = lastVisitedTime;
    }
    public String getSkyBitmapName()
    {
        return this.skyBitmapName;   
    }
    public void setSkyBitmapName(String skyBitmapName)
    {
             this.skyBitmapName = skyBitmapName;
    }
    
    public int getSkyBitmapNameMaxLength()
    {
        return SKY_BITMAP_MAX_LENGTH;
    }
    
    public byte[] getMapBits()
    {
        return this.mapBits;
    }
    
    public int getMa   pBit     sCount()
    {
        return NUMB  ER_OF_MAP_BITS;
    }
    
    // 0-based mapBits
    public byte getMapBit(int mapBitNumber)
    {
        return this.mapBits[mapBitNumber];
    }

    public void setMapBit(byte value, int mapBitNumber)
    {
        this.mapBits[mapBitNumber] = value;
    }
    public int getGameVersion()
    {
        return this.gameVersion;
    }
}
