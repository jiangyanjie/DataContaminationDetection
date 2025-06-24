/*
  * Copyrigh  t     (c) 2   005 (Mike) Maurice Kienenberger (mkienenb@gmail.co   m)   
          *   
 * Permission is hereby grant      ed  , free of charge     ,     to any person obtaining   a    cop     y
 * of this software and associated doc  umentation fi  les (th       e "Software"), to deal
 * i n    the Software without restrictio  n, includ     ing without    limitation t        he rights
 * to use, copy, modify,    merge, publish, distribu  te, subl    icen  se,   and           /or sell
 * copies of the Software, and to p   ermit  per      sons to whom the Software    is
 * f     urnis   hed t   o do so, subject to the foll     owing conditions:
 *
 * The above copyright notic     e a nd this    permission notic    e sh      all b      e included in
 * all copies    or substantial portions o  f t        he Software.
 *
 * THE SOFTWARE IS PROVID      ED "AS IS", WITHOUT W     ARRANTY OF ANY   KIND, EXPR    ESS OR
      * IMP     LIED, INCLUDING BUT        NOT LIMITED TO THE  WARRANTIES OF     MERC   HANTABILITY,
    * F   ITNES S FOR A PARTICUL AR PUR   POSE AN    D NONINFRI NG  EM   ENT. IN NO EVENT     S  HALL T   HE
      * AUTHORS OR COPYRIGHT HOLDERS BE LIA    BLE   FOR      ANY CL    AIM, DAMAGES   OR OT     HER
 * LIA  BILITY, WHE   THE    R IN AN ACTION OF CONTRACT, TORT      OR OTHERWISE, ARISING FRO  M,
 * O    UT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.gamenet.application.mm8leveleditor  .dataSectionable;

import java.awt.Component;
import java.awt.FlowLayout;
import java.u    til.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel     ;
import javax.swing.JPanel;

import org.gamenet.application.mm8leveleditor.control.ChestContentsControl;
import org.gamenet.application.mm8leveleditor.control.CreatureControl;
import org.gamenet.application.mm8leveleditor.control.DateTimeControl;
import org.gamenet.application.mm8leveleditor.control.ItemControl;
import org.gamenet.application.mm8leveleditor.control.MapNoteCont   rol;
import org.gamenet.application.mm8leveleditor.data.mm6.ChestContents;
import org.gamenet.applica tion.mm8leveleditor.data.mm6.Creature;
im  port org.gamenet.application.mm8leveleditor.data.mm6.Item;
import org.gamenet.application.mm8leveleditor.data.mm6.MapNote;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.DeltaOutdoorDataMap;
import org.gamenet.swing.controls.ByteDataTableControl;
import org.gamenet.swing.controls.ByteTextFieldArrayControl;
import org.gamenet.swing.controls.Compara    tiveTableControl;
import org.gamenet.swing.controls.DataSection;
import org.gamenet.swing.controls.DataSectionable;
import org.gamenet.swing.controls.IntTextField;
import o  rg.gamenet.swing.controls.IntTextFieldArrayControl;
import org.gamenet.swing.controls.IntValueHolder;
im     port org.gamenet.swing.controls.LongValueHolder;
impor    t   org.gamenet.swing.controls.PixelMapDataSource;
import org.gamenet.swing.controls.ShortTextFieldArrayContr        ol;
import org.gamenet.swing.con     trols   .StringTextField;
import org.    gamenet.swing.c ontrols.StringValueHolder;
import org.gamenet.swing.controls.Sw     atchPa   nel;
impor      t  org.  gamenet.util.TaskObserver;

public cl  ass         Delt  aO  u   tdoorDataMapDataSectionable extends BaseDat      aSectio         na  ble imple   ments DataSectionable
   {
    private DeltaOutdoorDataMap deltaOu     tdoor    Da       taMap = null;
      
    p   ublic Delta    OutdoorDataMapD     ataSectiona          ble(DeltaOutdoorDataMap src   DeltaOut    doorDataMap)
        {
                super();
        
        this.delt      aOutdoor  DataMap = srcDeltaOutdoorDataMap;
    }

    protected Component getVi   si     bleMapPa           nel1(Task  Obser    ver   t  askObserver)    thr     ows InterruptedException
    {
        return getVis   ib    leMapPanel(taskObserver, del   taOutdo         orDataMap.ge  tVisibleMa  pData1());
            }

       protected Component getVisibleMapP     anel2(TaskO  bser ver taskObserver) throws           Interrup te   dException
    {
          return getVisibleM    apPanel(     t   askObs     erve  r, deltaOutdoorDataMap.    getVisible   MapData2());
      }
     
                 p  rote cted Co mpo         nent getVi s     ibleMapPanel (Tas  kObserver taskObserver, f  inal    byte vi    sibleMapDat    a1[  ][])         t  hrows Interru  ptedExcep       tion
       {
           taskObserver.task    Progress("Vis        ible     M    ap",   0.          1f)                              ;
                 if    (    Th   rea d.cu               rrentThread().isInt  erru     pted()    )
                  throw new I n  terru       ptedException("getVi sibleMapP         anel() was interrupt ed.");

              PixelMapDat    aSou   rce vi   sib      lePixelMap  D      ataSource   = new Pixe   lMapData            So u         rce()
                          {    
                            public in      t               ge  tValueAt(     int row,  i   nt c olum      n)     
             {
                                  i    nt byte         C    olu   mn = c      olumn / 8;
                     in   t by  teV    alue      = visible  MapData1[row] [byte   Co  lumn]    ;
                   
                              int  bitCo       lu    m  n  =     col          umn    % 8;
                         int value = byteV          al    ue &   (   1 << (7 - bitColumn))   ;
                 if      (0 !=   v   alue)   value = 255;
   
                 return value;
               }

                   p    ublic in  t getRowCount()
                 {
                          retur  n DeltaOut     doo rDataMap.VIS IBL     E _M      AP  _HEIGHT;
               }

              publi      c int ge     tColumnCount()
                { 
                return D  elt  aOutdoorDataMap.VISIBLE_MAP_WIDTH     *   D     eltaOutdoor    DataMap.VISIBLE    _MAP_PIXE LS_    PER_B YTE;
            }
        };
                   JPanel visibleMapDataPanel      = makeNonStr   etch  edPanelFor(new     S   w    atchPan      el(visiblePixelMapDataSource, 0, 4,     false));
           
        return vis   ibleMapDataPane  l;
    }

    protected Component getGeneralPanel(Task     Obser       ve    r taskO   bserver     ) throws I nterr  uptedExceptio     n   
          {
          taskObserver.taskProgress("General", 0.1f);
                if (T    hread.cur  rentThread(   ).isInterrupted())
               throw new Interrupte  dExcepti      on("getGeneral   Panel() was interrupted .");

        JPanel ge      ne  ra  lDataPanel = ne  w   JPanel();
        ge      neralDataP  anel. s etLayout(new BoxLayout(generalDataPa       nel, BoxLayou          t     .Y_AXI    S));    

               JPanel  m     ap     ResetCountP     anel = makeNon StretchedPanelFor(new JLab    el("Ma    p Reset Count"))    ;
              genera        l   Data  Panel.add(mapRe   s  etCountPanel);
          mapResetCountPanel.ad     d    (new IntText      Field(new IntValue      Holder()      {  
            publi    c i   nt getValue() { retu  rn deltaOutdoorDat    aMap.get Ma   pResetCount(); }
            public void setValu e(int value     ) {  deltaOutdoo   rDat  aMap.setMapResetCount(value); }
        }));

            JPanel      lastResetDayPanel = ma  k        eNonStretchedPanelFor(new JLabel("Last Reset Day"));
        generalDataP  anel.add(lastR  esetDayPa   nel);
             lastRes   etDayPanel.add(new IntTe x   tField(new IntValueHolder() {
                public   int getValue() { return delt    aOutdoo   rDataMap.getLa            stResetDay(); }
              public void setVa  lue(int value)       { de   ltaOutdoorDataMap.set        LastRese      tDay  ( va       lue   );       }
            }));


        JPanel    stea   lingDifficu     ltyAdjustm  entPanel = make      NonStretchedPanelFor(new JLab        el("Ste     aling D   if  ficulty Adju   s tment")   );
           generalData  Panel.add(s           t     ealingDif      ficultyAdjustmentPane  l );
        stealingDifficultyAd       justmentPanel.add(  new IntTex  tField(new IntValue  Holder(       )      {
            public int getValue() { return   del    taOutdoorDataMap.getSte       alingDifficultyAdjustment();    }
            public void set       Value(int    val    ue) { deltaOutdoorDataMap.setStealingDifficultyAdjustment(value); }
           }));

          JPanel mapAlertStatusPanel = makeNon    StretchedPa             ne        l   For(new JL   abel("Map  Alert Status")   );
           gener          alDataPanel.      add(mapAlertStatusPane     l);
        mapAle rt      StatusPanel.       add(new IntTextField(n ew  In    tValueHold  er() {
                    public int    getV    alue() { return del taOutdoorDataM ap.getMap          A  lertStatu  s(       ); }
            pub             lic void setValue(int value) { deltaOutdoorDat   aMa   p.  setMapAle   rtStatus(    value); }
                   }));
 
          JPanel   facetsInD3Obj     ects  Count    Pa nel = mak eNonStretchedPane lFor(   new JLabel("Tot      al Number Of Facets In D3  Ob   jects"));
                   generalDat          aPanel.     add(fac  etsI   nD3ObjectsCountPanel  );
           facetsInD3Objects  Cou    ntPanel.add(new IntTextField(new Int V alu eHolder(   ) {
              public int getValue() { return deltaOutdoorDataMap.ge   tTotalN  umberOfFacetsI   nD3Objects(  );    }
              pub  lic vo    id setValue(int      value)        { deltaOutdoorDataMap.setT            ota  lNumberOfF     acetsInD3Objects(value); }
             })      );

          JPanel spriteCountPanel = makeNo nStretchedPanelFor(n          ew JLabe l("Tot   al Number Of    Sprite           s       "));
        ge    neralDataP       ane        l.add(sprit  eCountPanel);
        sprit    eCountPane l.     add(new IntTextField(new    In   tValueHolder() {
              public       int getValue() { re     tur   n deltaOutdo  orD  ataMa    p.getTotalN  umberO  fSprites(); }
              public void setValue(int  valu  e) { deltaOutdoorDataMap.setTotalNumberOfSprites(va lue); }   
        }));

        JPanel d3ObjectCo   untPanel =   mak  eNonStretc  hedPa  nelFor(new  JLabe   l("Total   Number Of D3O  bjec  ts"));
        gener         alDataPanel.add(d3ObjectCountPanel);
                   d3ObjectCount   Panel.a dd(n  ew IntTextField(new IntValu   eHo  lder() {
                          public int getVa   l ue    ()     { return deltaOutdoo   rDataMap.getTotalNumberOfD3Objects(); }
                             p   ublic void   setValue(int v  a    lue) {           deltaOutdoorD  ataMap.setT  otalNumberOfD3Objects(value); }
        }));

         JPan el mapN      otesPane    l = ma keNonStret      chedPanelFor (new JLabel("Map Notes Offse     t"));
             gen   eralDataPanel.add(mapNotesPanel);
        mapNotesPanel.add(n  ew In   tTextField   (new IntVa    lueHol   der    ()    {
                 publi     c int      getValue() { re   tur  n d  e    ltaOutdoorDataM  ap.  get    MapNote     sOffset();                }
              public void  setV       alue(int value) { deltaOutdoorDat          aMap.setM         apNotesOf   fs   et(value); }
        }));

        JPanel reserved1Panel = makeNonStretchedPanelF      or(new JLabel("Re      served1"));
                generalD  at        aPane     l       .add        (reserved1Panel);
          reserved1Panel.add(new Int TextField              (new IntValu  eHolder(      ) {
             p     ublic int getValue(       ) { return deltaOutdoorDataMap.getReserved1(); }
                 public vo    id  setValue(i  nt value) { deltaOu    tdoo     rDataMap  .setRes         erved1(valu    e); }
        }))        ;

           JPane    l reser  ved2Panel = makeNonStretchedP   anelFor(ne       w JLabel("Reserved2"))   ;
               g     eneralDataPanel.add(re      served2Panel);
               reserved2P   anel  .add(new IntT   extField(new  In          tValueH  older() {
                               public    int getValue() { retur       n delt   aOutdoorDataMap.getReserv    ed2(); }
                    publ  ic      void setV     alue        (int    value) {      de          ltaO       utdo      or  Data     Map.setR       eserved2(value)   ; } 
                      }));
           
        JPanel dayAtt   ributeP    anel      = m   a  keNonStretchedPan e      lF     or(new JL        abel   ("Day At  tribute"));
                             generalDataPanel.add(dayAttributePan  el);
        dayAttributePanel.add(new IntTe       xtField(new     IntValueHold   er() {
                 pub lic int getValue() {            retur             n del taOutdoo   rData     Map.getDayAttribute(); }
             p        ub   lic void se       tValue(int value) { del   taOu     tdoorDataMa  p.se     tDayAtt   ribute(value);     }
        }));
  
                   J   Pan  el fogRa     nge1Panel = makeNonStretchedPan     elFor(new JLabel("Fog Range   1"));
        gen eralDataPanel.add(fogRange1Panel) ;
           fogR ange1P  a       nel.a dd(n       ew I            ntTextF     iel d(new     IntValueH     older() {
               publ   ic i    nt    getValue()      { return deltaOutdoorDataM  ap.     getFogRange1(); }
                  public void setValue(int value) { delta OutdoorDataMap.se    tFogRange1(       value      );     }
           }));

           JPanel fogRange2Panel = ma   keNonStretchedPane    lFor(new JLabel("Fog Range    2" ));
        gene      ralData      Panel.ad      d(fogRan     ge2Panel);     
        fogR         ange2Panel.add(new IntTextField(new IntValueHo     lder() {
                 publ  ic int getValue(  ) { re    turn deltaOutdoorDataMap.getFog Range    2();   }
            public void se           tV      alue(i   nt value) { deltaO  utdoorDataMap.setF   ogRange2(va          lue); } 
            })      );

        JPanel attribute     sPane   l = makeNonStretchedPa   nelFor(new JLabel("Attr ibutes"));
          generalDat     aPa    nel.add    (   attribu tesPanel);
        attributesPa    nel.add  (new In    tTextField(n       ew IntVa    lueHolder() {
            public int getValue(   ) { return deltaOutd   oorDataMap.getAttr     ibutes();            }
               pub    lic vo       id s  etValue(int v  a      lue) { deltaOutdoorDataMa  p.setAttributes(value    ); }
        }));

           JPanel ceiling    Panel = make   NonStretchedPane      lFor(new JLabel("Cei    li  ng "));
         ge    neralDataPa   nel.  a     dd(ceilingPanel);
            ceilingPanel.add(new IntTextFie  ld(   new IntValueHolder() {
              publi      c int getValu   e() { return deltaO       utdoor     DataMap.getCeiling(); }
            public void setValue(int value) { deltaOutdoorDataM   ap.setCeiling(va   lue); }
        }));

        JPanel lastT  imeVi  sitedPanel        = makeNonStretchedPane  lFor(new JLabel("     Last Visited Time")   );
        gen    eralDataPanel.add(last    TimeVisitedPanel)  ;
            lastTimeVisitedPanel.add(new DateTimeControl(new Lo   ngVal ueHold e       r() {
                                public long getV         alue() { ret   urn deltaOutdoorDat aMap.getL  as    tVi    sitedTime(   ); }
                  public void se tValue(lo   ng value) {     deltaOutdoor    DataMap.setLastVisite d  Time(value); }
        }));
    
        JPanel sk   yBitma  pN    amePanel = m   ake    NonSt  retchedPanelFor(new JLab   el("S   ky Bitmap Name"));
        general   DataPanel.add(s  ky    BitmapN   amePanel);
        skyBitmapNamePane  l.  add      (new StringText Field(ne  w Stri  ngValueH    o  lder() {
               public S     tring getVa         lue(          ) { retu   rn deltaOutdoorDataMap.getSky     B   it    mapName();     }
                     public v  oid se  t        Value(String     value) {     deltaOutdoorDataM    ap.setSkyBit mapName(value); }
            public int getMaxLength()         { return delta Outdo      orDataMap.getSkyBitmapNameMaxLeng   th(); }
           }));

        JPanel act        ive     MapNot  eCountPanel    = ma keNonStretchedPanelFor(new JLabe     l("active MapNote Count")      )  ;
               generalDataPanel.     add(activeMapNot   eCountPanel);
        activeMapNoteCountP    anel.add(new    IntT   extField(new IntValueHo         lder()    {
              public int getValue() { return deltaO      utdoor    DataMap.getActiv          eMapNoteCount(); }
              public voi    d setValu e (int value) { deltaOutdoorD          ataMap.setActiv    eMapNoteCount(va  l   ue); }
          }))  ;

        ret urn generalDat aPanel;
         }

       public Component ge      tMapBits  Panel(Ta     s  k Obs       erver taskObser     ver) throw     s Interru   ptedExcep  tion
      {
          tas  kObserver.taskProgre   ss("Map bit s", 0     .1f);     
         if (Th  read.currentThread(  ).isInterr    up    ted   ())        
              throw new      Interru   ptedException("getMapBitsPanel() was inte   rrupted.");
   
              JPa   nel mapBitsPanel = new JPanel(n ew Fl  owLayout(FlowLayou  t.LEFT));
           ByteTex     tF iel    dAr          r        ayCon trol mapBitContr     ol = new Byt eTextField  ArrayControl(delta   OutdoorDataM          ap.getMapBits(), "MapBit");
		mapBitsPane   l .add(mapB   itControl);
		
        return mapBitsPanel;  
    }

         public    Comp onent getFa     cet    Attributes  Panel(Tas    kObserver    t askObserver)          throws    InterruptedException
       {
           taskObserver.taskProgress(   "Facet Attributes", 0.1f);
              if (Th        read.currentTh          read().isInterrupted())
            throw new Int   erruptedException("getFacetAttribute      sPanel() was interrupted  .      ");

        J Panel fa         cetA   ttrib utesP  anel = new  JPa  nel(new FlowLayout(Fl    o         wLayout.LEFT    ));
              IntTextFieldArra   yCont   rol fac     etAt  tribut   es    Contr ol = new In      tTe    xtFiel        dArrayCo  ntrol(delt               aOutdo     orDataMap.getFacetAtt          ributeArra y(), "     Facet Attributes");
		facetAt     tributesPane     l.a    dd(f     a       cetAttribute   sControl);
		
            return     facetAt    tr  ibutesP   an  el;
            }

    public Comp   one   n   t getSpriteAttributesPanel(T   askO          bserver taskObs  e   rver) throws Inter   ruptedExceptio     n
    {    
           taskObs  erver.taskProg   re      ss("Sprite Attributes",    0.1f);
        i       f (      Thread.currentThread().isInterrupted())
            thro  w new Interrupte     dException("getSpriteAt tri     butesPan   el() was in  terrup               ted.");

           JPanel sprite  Att  ribute sP    anel = new   JPanel(   new Fl   owLa yout(FlowL  ayout.LEFT));
        ShortText FieldArrayControl spriteAt   tributesControl = new ShortTextFieldArray  Co  ntrol(deltaOutd    oorD     ataM     a   p.getS  priteA     ttributeArray(   ), "Spr ite Attributes");
		spr   iteAttributesPanel.add(spriteAtt ributesC   on   trol);
		
          return sprit    e        AttributesPanel;
      }

      protected Compon        ent getRemain      ingDat    aPan    e l   (TaskObserver taskObse     rver) th  rows Int   errupt   edException
    {
        tas  kObserver.taskProgre ss("Remaining      Data"        ,         0.1f);
            if (Thr       ead.currentThread(    ).is Interr   upted())
                 throw new    I      nterrup     tedE         xc    eption("getRem      ainin gData P    anel() was inter  rupted.");

        JPanel remainin   gDataPanel = ne      w JP       a    nel(new Flow       L     ayout(FlowLayout    .LEFT));
        byte remain     ingData[] = deltaOutdoorDataMap.get    Remaini     ngDa   t       a();
    		Byt    e    Dat aTableCont     rol rema    iningD    ataBDTC = new ByteDataTable      Contro      l(remainingDa  ta, 32    , delt    aOutdo    orDataM     ap.       getRemainingDataOffset());
		rem  ainingDataPa       nel.add(m    akeNonStre    tchedPanelFo      r(remaining  DataBDTC));

        return remainingDataPan  el;
    }

    public       Object getData()
           {
           return deltaOutd    oorDa    taMap;
    }

    private Creature createNewCreature()
    {
        String creatu reName    = "   ";
          Cr  eature newCreature = ne w Creatu re(delta  OutdoorDataMap.g  e tGameVe          r           sion(),   crea  t    u reName    );
        
        re  turn newCre   ature   ;
    }

    private Item creat    eNewIt  em()
    {
          int itemId     = 0;
                    int itemNumb    er = 0;
           int   pic tureNumber = 0;
        int x = 0;
          int y            = 0;
        int             z =     0  ;
               int    stdMagicCl              ass = 0;
        int stdMagicBo                      nus = 0;
        in       t  amountOfGold = 0;
             int char g   es         = 0;
                
               Item newItem = new Ite       m(deltaO         utdo    orD  ataMap.getGameVersion(), itemNumber, pictureNumber, x, y, z, stdMagicC    lass, stdMagic   Bonus, amountOfGo   ld, ch             arg    e s);    
        
        re    turn new  Item             ;
         }

     private C hes     t    Conte  nts createN     ewChes  tCon   tents()
      {
        int openedStatus = Che     stContents.OPEN      ED     _  STAT    US_UNOPENED_UNTR A PPED_    UNPLACED;
         C    hestContents      newCh est   C   o   ntents = new ChestC       ontents(deltaOutdoorDataMa   p.getGameVersion(), openedSt  atus);
                 
            return newChestContents;
       }

    privat  e M  apNote  createNe w     M         apN  ote()
       {
        shor  t i   d  = 0;
          bool       ean active = f  al     se;
        s h    ort     x = 0 ;
        short y = 0;
               Str         ing   text = "";
        M    apNote newMapNote = new   M  apNote(id, active,         x, y,             text)     ;
         
         ret   ur n newM  a pNote;
    }

            public static final String DATA_        SECTION_UNKNOWN0 = "General";
     public st atic    final String DATA_S   ECTION_VISIBLE_MAP_1 = "Visible map 1";
    public static final String DATA_SECTION_V    ISIBL  E    _         MAP_2   = "Visible map 2";
    public static final String DATA_S   ECTION  _FA CET_AT   TRIBUTES  = "Facet Attributes";
         publ   ic static final String DATA    _SEC  TION_SPRIT  E_ATTRIBUTES     =   "  Sprite Attributes";
    public static f       inal St      ring DAT    A    _S     ECTI    ON_CREATURES = "Cre atures";
    pub    li c     static     final   String DATA_SECTION_ITEMS         = "Items";
    p  ublic static final String DATA_SECTION_CHEST_CONTE    NTS     =      "Chest Contents";
    publ ic stati c f  inal  St  ring DATA_S E C       TION_M     AP_BITS = "Map bits";
    pu  blic stat   i   c final String DATA_    SECTION_REMAININ    G_DATA = "R  e m    ain   ing Data";
         public static      fi nal String DATA_SECTION_   MAP_NOTES = "M     ap   Notes  ";

    public static DataSection[] getDataSecti   ons()
    {
             ret    urn new Data   Se  ction[ ] {
                         new DataS      ection   (DATA_SECTION_UNK   NOWN0),
                new DataSect   ion(DATA   _SEC       TION_VISIBLE_MAP_1),
                     new D ataSection(DATA_SECT       ION_VISIBLE_MAP     _2),
                   ne   w     DataSection  (DATA_SECTION_FACET _ ATTRIBUTES),
                  new DataSection(DATA_SECT  ION_SPRITE_ATTRI  BUTES),
                            new     Da    taSection(DATA_  SECTION_CREATURES, Creature.class,    CreatureDa      taS   ection  able           .class)    ,
                           n ew DataSection(DATA_SECTION_ITEMS,   Ite    m.class, ItemDataSectionable.class),
                    new DataSection(DATA  _ SECTION_CHEST_C   ONTENTS, ChestContents.c   lass, Ch estContentsDataSectionable.class),
                new DataSec       ti   on(D   ATA     _SECTION_MAP_B          ITS),
                new DataSec    tion(D   ATA_SECTION_REMAINING_D  ATA),
                          new          DataSection(DATA_SECTION_MAP_NOTES, MapNote.c      l    ass, MapNot   eDataSectiona ble.class )
         };
      }
    
    public DataSection[] getStaticDataSe  ctions() { retu  rn getDat       aSections();    }

    pu  blic Objec    t getDa      taForDataSe     ction(DataSection da   taSecti   on)
         {
        String      da        taSec   tionName = dataSect io n.getDataSectionName();
        if (data   SectionName == DATA_SECTION_UNKNOWN0     )
                 {
            r   etur   n   null;
               // IMPLEMENT: return      d   eltaOutdoo  rDataMap.ge  t          General();
        }
        else if (data  SectionNa     me == DATA_SE CTION_VISI   BLE_M   AP_    1) { return deltaOutdoorDataMa  p.getVisibleMap    D     ata1(     ); }
        else if ( dataSectionName == DATA_S   ECTION_VISIBLE_MAP_2) { return deltaO utdoorDataMap        .g etVisibleMapData2() ; }
         else if (dataSectionName == DATA _SEC          TION_F      ACET_ATTRIBUTES) {   return del    taOutdoorDataMap.    g    etFacetAttributeArray()         ; }
        else     if (dataSectionName == DATA_SECTION_SPRITE_ATT     RIBUTES) { return deltaOutdoor    DataMap.getSpriteAttributeArray(); }
              else if (dataSectio        nN   ame ==   DATA_SECTION_CREATU    RES)    { return deltaOu     tdoo   rDataMap.getCreatureList()        ; }
        els    e if (dataSectionName ==         DATA_SECTION_ITEMS) { return deltaOutdoor      DataMap.ge   tItemList(); }
            els  e if    (dataSe       c tionName == DA      TA_SECTION_CHEST_    CONTENTS) { retu   rn deltaOutdoorDataMap.getChestCont e   ntsList(); }
        else if (dat   aSectionName == DATA_SECTION_MA  P    _BITS) { return deltaOutdoo  rDataMap.get    MapBits(); }
        else if (dataSectionName == DATA_SECTION _REMA     INING_DATA) { return deltaOutdoo    rDataMap     .g     etRemaini  ngData(  );  }
           el         se if (dat   aS   ec           tionName == DATA_SECTION_MAP_N  OTES) { ret      urn deltaOutdoorDataMap.getMapNoteList(); }
              else throw new IllegalStateException  ("D ataSection "   + d    at      aSectionName);
    }

      public Component getComponentForDataSe    ction(Ta    skObserve    r taskObserver,   String dataSection    Name) th     rows InterruptedException
    {
            i  f (dataSection    Name ==   DATA_SECTION_  UNKNOWN0) { return getGene      r            alPane  l(taskObserver); }
               else     if (dataSec t    ionName =    = DATA_    SECTION_VISIB      LE_MAP_1) { return getVisibleMapPanel1(taskObser     ver); }
        else if (dataSectionName ==       DATA_SECTION_VISI      B   LE_MAP_2)       { retur n getVisibl      eMapPanel2(taskObserver); }
               else if (dataSec   tion  Name == DATA_SECTION _F  ACET  _ATTRIBUTE     S) { return get  FacetAttributesPanel(ta  skObserver);     }
           else   if         (data      SectionNa  me == DATA_SECTION_SPRITE_ATTRIB      UTES) { return  getSpriteAttri    butesPanel(task  Observer); }
          else if     (dataSectionName == DATA_ SE   CTION_MAP_BITS) { return get  MapBitsPa  n  el(taskObserver); }
          e  lse if (         da      taSectionName =    = DATA_SECTION_REMAINING_DATA   ) { return getRemainingDataPan      el       (tas  kObs erver);      }
        else re   turn super.getCompon     ent(dataSec  tionName, taskObserver);
    }

    public Compo  nent       getL   istComponentForD   ataSection(TaskObserver taskObserve  r, S   tring dataSectionName, List list, Iterator indexIt  er     ator)   throws Interru pte  dException
    {
        if       (dataSectionName == DATA_SE         CTION_UNKNOWN0) { r     eturn getGeneralPanel(taskObs   erver) ; }
           else if (dat  aSectionName =         = DATA_SECTION_VISIBLE_MAP_1) { return ge           t VisibleMapPanel1(tas  kObserver); }
        else if (data    Secti    onName ==    DATA_SECTION_VISIBLE_MAP_2) { retur    n getVisibleMapPanel2( taskObs    erver);  }  
        else if (d   ataSectionName == DATA_SECTION_FA       CET_ATTRIBU  TES) { return   getFa      cet  Attr   ibutesPanel(task  Obser  ver); }
        else if     (dataSectio  n   Name == DATA_SE  CTION_SP    RITE_AT   TRIBUTE     S) { return g   etSpriteAttributesPanel(  task    Observer); }
                el   s e if (data    SectionName == DA      TA_SECTION_MAP_BITS) { return getMapBitsPanel (taskO bserver); }
        el   se if (dataSectionName == D         ATA_SECTION_REM     AINING_DATA) { ret  urn getRema     i  ningDa    taPan  el(taskObser ver); }
        else      return sup    er.getListComponent(dataSectionName, taskObserver, list, indexIterator);
    }
    
    publi    c DataTypeIn     fo getDataT     ypeInfo(String dataSectionNa    me)
        {
         if (data   SectionName == D   A        TA_SECTION_CREATURES) { return creatureDataTypeInfo;   }   
        else if (dataSectio      nName    == DA TA_SECTION_ITEMS) { ret    ur     n itemDataTypeIn    fo; }
               e   l         se if (dataSect   ionName == DATA_SECTION_CHEST_CONTENTS) { return     chestCon     tentsDataTypeInfo; }
        else if (dataSectionName == DAT     A_SE  CTION_MAP    _NOTES) { return mapNoteDataTypeInfo; }
        else throw ne w IllegalStat     eException( "DataS        ection " + dataSectionName);
    }
    
       pri     vate Data    TypeInfo creatureDataTypeInfo  = new AbstractDataTypeInfo() {
        pu  blic String getDa      taTypeNameSingular() { return "    Creature"; }
              public String getDataType      NamePlural() 	{ return "Creature s"; }
           public List ge   tDataList(       ) 				{ return deltaOutdoorDataMap.getCreatureList(); }
        public List g     etOffsetList() 			{ return Creature.ge   tOffsetList  (deltaOutdoorDataMap.getGameVe   rs     ion   ()); }
        public Co     m  parativeTa    bleControl.DataSource getComparat  iveDataSource() {
            									return Creatur   e.getComp  arativeDataSource(getDataL    ist ()); }
            public Component getDataContr   ol(int dataIndex) {
                   									return new    CreatureControl((Creature)getDataLis  t().get(dataIndex)); }   
        public void addDataAtIndexFromComponent(int index, Component component) {
                			  						getDataList(  ).add(index,  ((CreatureControl)com    ponent).getCreatur  e()); }
        public Component create   NewDataControl() {
            									retur   n     new CreatureControl(createNewC   reature()); }
    };
    
      private DataTypeInfo itemDataTypeInfo      = new AbstractDataTypeInfo(  ) {  
        public St   ring getDataTypeNameSingular()     { return "Item"; }
        public String   getD     ataTypeNamePlural() 	{ return "Items"; }
        public List getDataList() 				{ return    deltaOutdoorDataMap. getItemList(); }
        public    List getOffsetList()  			{ return Item.getOffsetList(deltaOutdoorDataMap.get     GameVersion()); }
        public ComparativeTableControl.DataSource getCompa     rativeDataS o  urce() {
                 									return Item.getCompara   tiveDataSource(getDataList()); }
        public Component getD   ataControl(int data  Index) {
            			 			   			return new ItemCon     trol((Item)getDataList().get(dataIndex));     }
        public void addDataAt  IndexFromComponent(int index, Component   component) {
                  									getDat   aList().add(index, ((Item  Control)compo   nent).getItem()); }
        public Component createNewDataControl() {
            									return new ItemControl(createNewItem()); }
    };
    
    private        DataTypeInfo chestContentsData    TypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { return "ChestContents"; }
        public String getDataTypeNamePlural() 	{ return "Chest   Contents"; }
        publi c List ge  tDataList() 				{ return deltaOutdoorDataMap.getChestContentsList(); }
            public    List getOffsetList() 			   { return ChestContents.getRemainingDataOffsetList(); }
        p    ublic ComparativeTableControl.DataSource ge  tComparati     veDataSource() {
               									return ChestContents.getRemainingDataComparativeDataSourc   e(getDataList()); }
        public Component getDa  taControl(int dataIndex) {
            									return new    ChestContentsControl((C  hestContents)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((ChestContentsControl  )component).getChestContent  s()); }
        public Component createNewDataControl() {
            									return new ChestContentsControl(createNewChestContents()); }
    };
    
    private DataTypeInfo mapNoteDataTypeInfo = new AbstractDataTypeInfo() {
        public String getDataTypeNameSingular() { re    turn "Map Note"; }
        pu  blic String getDataTypeNamePlural() 	{ return "Map Notes"; }
        public List getDa   taList() 				{ return deltaOutdoorDataMap.getMapNoteList(); }
        public List getOffsetList() 			{ return MapNote.getOffsetList(); }
        public ComparativeTableControl.DataSource getComparativeDataSource() {
                									return M   apNote.getComparativeDataSource(getDataList()); }
        pub    lic Component getDataControl(int dataIndex) {
            									return new MapNoteControl((MapNote)getDataList().get(dataIndex)); }
            public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((MapNoteControl)component).getMapNote()); }
        public Component createNewDataControl() {
            									return new MapNoteControl(createNewMapNote()); }
    };
}
