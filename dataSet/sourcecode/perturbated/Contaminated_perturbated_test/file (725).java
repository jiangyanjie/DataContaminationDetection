/*****************************************************************************
 *    Copyright    by The HDF Gr  ou        p  .                                                                                                          *
    * Copyright by the Board  o  f Tru       stees of the    Un   i v              er        si                                   ty of        I  llin       oi   s.                                    *   
  * A   ll     rights r              es erved.                                                                                                              *
 *                                                                                                                           *
 *  This file is part     of the     HDF Java    Products      distri        bu       tion.                      *
 * The full copyright       no      tic e,    including terms governing use, modification,   *
      * and     red    istribution, is conta  ined in the  fi   les     COPYING and Copy  ri       g  ht.html. *
   * COPYING can b    e found  at the     r        o  ot of the      sour   ce code dist  ribution tree.        *  
  * Or      , see h  ttp://hdfgroup.org/products/hdf-ja  va/doc/Copy    righ      t.htm   l    .             *
 * If you do not have acc  es  s to      either file, you may reques   t a copy from        *
 *     help@hdfgroup.org.                                                                  *
 ****     ************************************************************************/

package ncsa.hdf.view;

import java.awt.BorderLayout;
import java.awt.Dimension   ;
import java.awt.GridLayout;
import java.awt.Insets;
import java.a   wt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jav  a   .awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Array;
import java.math.BigI     nteger;
import java.util.Enumeration;
import java.util.List;
import java.util.Strin gTokenizer;

import ja  vax.swing.BorderFact ory;
import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import j   avax.swing.JFileChooser;
import java   x.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
i    mport javax.swin g.JTabbedPane;
import javax.swing.JTabl  e;
import javax.swin   g.JTextArea;
import javax.swing.JTextField    ;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import ja  vax   .swing.table.  DefaultTableModel;
import javax.swing.tree.D    efaultMut   ableTreeNode;

 import ncsa.hdf.object.Attribute;
import ncsa.  hdf.object.CompoundDS;
impor   t ncsa.hdf.object.Dataset;
impo    rt ncsa.hdf.o   bject.Dataty     pe;
import   ncsa.hdf.object.F ile Format;
import ncsa.hdf.o   bject.Gr     oup;
impo    rt ncsa  .hdf.obje        ct.HObject;
import ncsa.     hdf.object.Scala           rDS;

/     **
 * DefaultMe  tadat      aV  iew is an dialog   window        used to show data          proper     ties. Data
 * properties include attributes        and gen   eral i   nformati   on        such a             s object type,
                * data type a   nd data space.
                * 
         * @a uthor Peter X.  Cao
 * @v  ersion       2     .4  9/6/2    007
    */
pub lic cla   ss DefaultMetaDataVi    ew exte   nds JD     ialog implements Ac  ti     onListe    ner, MetaDa       taVi        ew       {    
    pri       vate st atic fin       al  l     ong se rialVe   r      sionUID = 7891048909810508761L;

    /* *
          * The main       HDF     View. 
        *   /
        pr  ivate ViewM anager       viewer;

                   /**  Th    e HDF data ob j   ec           t */
    priva  te HObject                      hObject;

    privat    e          JTabbedPane                         tabbed Pane                          =     null   ;
          private           JTextArea           attrC       ontentAr               ea;
    p    riv    at   e J          Table            attrTabl         e;  //  t        able to hol  d a list of a   ttribut es
            private DefaultTableMod            el   at   t        rTa     bleMod    el;
    pri   vate  J    Lab  el            a ttrNumbe   rLabe     l;
    private int                      numAttributes   ;
       priva  te   b   ool      ean                    isH5, isH4;      
    priva       te byt e[]               user   Block;  
              private JTextArea              use       rBl   ockArea;
      pri  vate              JButton               jamBut        to     n;

    private J   Text   Field             linkField               = null;

          priva    te FileFormat        fi  l   eFormat;
       private St                ri ng                     LinkTObjName;

             private i nt[]                      libver;

    /*  *
          * Constru cts a D    efaultM     eta    dataView with the given HDFV      iew.
     */
    public DefaultMetaDataView(ViewMana             ger     theView)          {
                  s u      per((  JFrame)       theVi                  e    w,          false);
        set  DefaultCloseOpera tion(JIntern       alFr   ame.DIS    POSE_ON_CLOSE)   ;  

                     s     etN ame("            D     e              fa        ultMe    taData       View ");
                 viewer = theView;
              h    Obje     ct = vie    wer.     ge     tTr    eeVi ew     ().getCu  r     rentObject(              );
              fileForma     t = hObjec    t.getFileForm        at();
                                  nu     m Attribut    es  = 0;
        userBl ock = null;
            u   serBlock  Area = null;
         libver       = new int[2];

           if (hObje     c      t = = null) {
                        dispose  ()    ;
        }
            else       if (hO   bject.g   et         Path() =     = null) {
                setTitle("Properties - " + hObject.getNa    m      e(   )); 
        }
               e   lse {
                          se   tTit   le("Properti    es - "    + hObject.getPat   h() + hObject.ge    tName())    ;
             }

        isH   5 = hO        bje                      ct.getFileForm  at(   ).isThi    sType(FileFormat.getF  ileFormat(FileF     ormat.    FILE    _         TYPE_HD  F5));
                 isH4 = hObject.g  etFileFor     m     at().isT     hisType(FileFor   ma  t.ge    tFileForma   t(  FileFormat.F   ILE_TYPE_HDF4));

                     tabbedPane     =     new    JTa bbedPane();
                 // get th  e metadata  in      formati on b efore add GUI components */ 
        try {
                hObject                     .getMetadata  ();
        }
        catch (   Exce    ption ex) {
                        }
                 tabbedPan    e.addTab("General", c  rea t          eGeneralProperty  Panel());
         tabbedPa           ne.addTab(    "Attrib  ut  es", c reateAttribut     ePanel()) ;

                         boolean    isRoot = ((hOb   ject ins  t  anceo     f Group) && ((Group               ) hObject).isR    oot()  );
        if (  is  H5 &       & isRoot) {
                  //     a dd panel    to dis    play u       se    r block
            ta           bbedP       an  e    .   a     ddTab("User B                lock", cr        eateU     serBlockPanel());
        }
           t         abb   edPane   .se   t S    e   l      e cte  dInde           x(0);
   
             i f (   isH5) {
            if (hOb  ject.getLinkTargetO  bjName()     != null)      {
                      L   i            nkTO  bjName = hObjec         t      .g    etLin     k  TargetObjName    ()    ;
                         } 
              }  
               JPa     ne         l bP  anel = new JPane   l();
               bP  ane l.setName   ("MetaDataClose");
        JB      utton b = new JBut   ton("      Close  ");
             b .s   etName("   Close");
               b.setM          n     emonic (K     eyEvent    .VK_C);
        b.setAction  Command(     "Close");
              b   .addAc   t      ion   Listener(t   his);
        bPanel.add(b) ;

                   // Add    the t abb   ed pane t    o this panel.
                J Panel con     tent  Pane = (JPan          el)   get     Co      nte   n   tPane();
               contentP  ane.setNa   m      e("MetaDa      taContent"  )   ;
         con tentPane.setLayout(new  Bor   d    erLayout());
               con         tentPane.setBorder(Borde     r    Factory.createEmptyBord   er   (5, 5, 5, 5));   
                     cont       entPane.set      P   referred      Size(new Dim  ension(620, 400)); 

        cont  entPane. a    dd ("C      enter"    , ta    bbed  Pane);
                       content           P   a     ne  .add     ("South" , bPanel);
     
        // locate th   e H   5P      roperty dialog    
             P  oint l = getP     a re   nt().         getLocatio         n();
                l.x += 250;
                          l.y += 80;
                         set                      Location  (l);
         p     ac k()     ;
           setVisible(  t    ru  e);
      }
            
    pub li    c vo           id actionPerform   ed(     Actio      nEv  ent     e      )  {
        Obje  c    t source = e    .g   etS   ource();
           String cmd = e.getActionCommand(           );

        if (cmd.equa   ls("Clos   e"))      {
                  if (is    H5 && linkField !=  nul          l) ch  ec   kLinkTargetChang   ed();
 
                  dispose();
          } 
                        else if (cmd                       .equals("Add  attribute")) {
                        addAttribute(h          O          bj   e   c t);
               }
            else if (cmd.equals ("De  lete attri         bu  te"    )        ) {
                    de   leteAttr       ibute(hObjec   t);
              }
            e l      se if (cmd. eq uals       ("Ja             m use  r block")) {
                        writeUse     rBlock(   );
            }
                  el   se if (cmd.e        quals("Dis        play    user b   lo c    k as")) {
              i   nt    type = 0;
                  Str  i    ng   type    Nam   e = (String        ) ((J ComboB  ox) source).g            etSel     ectedItem();
              jamButton.setEnable              d   (false);    
                       userBl   ockArea.     setEdit  a    ble(false); 
  
                if (typeName.e   qualsIgnoreC   ase("Text")  ) {
                     ty  pe = 0;
                             jam                           Button     .setEnabled(tr  u   e);
                         user    B     lock       Area    .se      t  Edit    able(tru   e);
                  }
                               el   se     if (typeName.e  qual    s     Ignore  Case("Bin       ary")) {
                        ty   pe =    2;
                       }
             el   se if (typeName.  equalsIgnoreCas    e("O      ctal"))  {
                          type = 8;
            }
                        els     e i  f (t    ypeNam       e.e qualsIgnor    eCas    e   ("Hexadecimal" )) {
                         type = 16; 
            }
               else    if (t    y          peName.equal        sIgno     re  Cas     e("De   cimal"   )) {
                         type                =  10;   
             }

               showUserBlockAs(type)          ;
             }
    }
       
    priva te f in     al    void   checkLinkT   a     rget          Changed () {
                                  G   rou  p        pgroup = null     ;
          try {
            pgroup = (Group) hObject.getFile    Format().get(hObject.getPath()   )  ;
         }
        c       a  t  ch (E   x      cept       ion ex      ) {
           }
        if          (pg  roup == n    ull) {     
                    JOptionPane      .showMessageDialog(    this, "Par        ent             group is   n  ull.", getT       itle()    , JO  ptionPa     ne.E      RROR_MES  SAGE);
               r      eturn;
        }

                  Stri  ng     target_na   m   e = linkF         ield.getText();
        if (   tar                get_name     != nu         ll) t a      rget_name = target_name.   trim();

        int             linkType = G r     o  up.LINK_TY    PE_SOFT;
                        if     (Li   nkTObjName.con    tains(FileFormat.   FILE_  OB             J_SEP))      
                           linkT       y  pe =   G  rou  p.LINK_TYPE_   EXTER               N         AL;
        else          if (ta         rget_na    me.equals("/")) //        do not all      o  w to   li  nk      to the root
                           return;
  
        /      /   no ch   a    nge
        if (target_name.equals  (hOb  ject.getLinkTargetObjNa me())) return;

           // inval       id name
         if       (targe    t_name == null    || target_name.l     engt h() < 1) re tur    n;

                       try {   
            fileFormat.createLink   (pgroup, h Obj      ect.getN     ame(), target_name, linkType);
            h   Object.setLinkTa rgetObjName   (t   arget_name);
           }
          catch (Excep     tion      ex      )   {
                        J OptionPane.showMessageDia  lo     g(this, ex, getTitl   e(), JOptionPane.ER R              OR_M ESS      AGE);    
             }
    }

    /         ** return   s   the d  ata object di   splaye   d     in this      data      viewer *    /  
    public HObject         getDataObjec t()      {
           return hOb  jec  t;
           }

     /** Dispose     s of this   dataobs  erver. */
      pub  lic vo     i        d d  isp  ose() {
        sup    e       r.dispose()      ;  
           }

     /** ad    d an at   tribu te to         a da  ta objec   t. */
    publ     ic Attribute      addAttribute(HOb            ject obj)   {
                          if (obj   == null) {
                return null;
        }

              Default  Mu    tableTreeNode   node = (D efault       M     uta  b leTr      eeNo   de) obj.getF          ileFormat(    ).getRootNode();     
              NewA ttri   buteD      ial og d       ialog = new NewAttributeD  ialog(this, obj  ,                 node.breadthFirstEnume   ra             t    ion());   
           dialog  .se t          Visible(             true);
      
              Att    ri     but   e     a  ttr   = dialog.getAttrib ute();
                                if (attr  == null) {
                              r             eturn null;
               }

        String     r  owDat    a[] = new St     ring[4];       // name, value, type      , siz    e

          rowData[        0]         = attr.getNa   me();
        rowData[2] = attr.getType().ge  tDatatyp    eDescri  ption();

              row  Data[1]    = attr.  t         oS  tring(   "   , ");
   
               long dims[] =    a  ttr.getData   Dims();
   
              rowData[3] = String.    valueOf(dims[0    ]     );
           for (in  t            j = 1; j < dims.length; j ++) {
               rowDa  ta    [3] += " x " + di    ms[     j];
         }

           attrTabl    eMod     el.addRow(rowD     ata);     
                                  attrTab   l eModel.fireTableRowsIn    se  rted(a    ttrTa bl      eModel.getR  owCount() - 1,      at                trTabl  eModel.getRowCoun   t() - 1);
           nu     mAt tr     ibutes++;
                        attr      ContentArea.setText("");
                    at    trNumberLa    b     el .     setText("Number     of attrib    utes = " +  numAt      tributes);       

                re turn a    ttr       ;
    }
    
        /**     de  lete a    n     a  ttribute f rom a dat      a  object. */
             p    ubl ic      At                   tri      bute deleteA                 ttr     ibu    te(           HO     bject obj)                                   {
                  if (obj == null  ) {
            return       nu  ll;
        }

              in    t idx = a   ttrTab       le.g    etSe lect  edRow( );
                if (id  x       < 0) {
              JOptio  nP             ane   .showMessa   g    e Dialog      (getOwner(), "  N o a    ttri    bu  te            is selec           te   d.", getTitle(  )              ,   
                              JOp    tio nPane.ERROR       _MESSAGE)                 ;
                              ret  urn null;                      
        }  

            int o   ption     = JOpti      onPane.showCo      n   firmDialo   g(t     hi   s, "     Do    y     ou want to del       ete         t     he selected attribute     ?", getTitl   e(),
                                JOptionPan   e.YES  _NO_OPTION,                JOpti onPane.W  ARNI  N   G_MESSAGE);

                if   (op            tion   ==   JOptionPane.NO_OPTION) {
                                         return nu      ll;  
            }

                     List<?     > attrLi  st      = null;
                                             try {         
                attr   Lis            t = obj.getMetadata()        ;
            }
                    catch (Exception ex) {
                            at     trLis t = null; 
               }
   
        if (              attrLis  t == null)   {
            retur        n   n     ull;
          }

                     A    ttribute attr     = (Attribu   te) attrLi   s                 t.get(                 idx);
            try {
            o      bj.remo       veM etada   ta(att r)  ;        
        }
               catc         h (Exception ex)   {
                     ;
                  }
    
                        attrTa              ble       Mo              del.removeRow(idx);
                      numA   ttrib   utes--;
                    attrTable         Model.fireTableRowsDeleted(id    x, idx); 

                   a   ttrConte ntAre   a.        setText    (       "");
           attrNumbe    rLabel.setT   ext   ("Numb               er of           a        ttributes     = "     + num     Attributes) ;
     
                  return a    ttr;
    }   
    
         /**
                 * Creates a pane   l      used                  to d            ispaly gener        al inf ormatio    n  o   f H  DF obj        ect.  
     */   
         pri   vate JP    an                     el createGeneral Propert    yPanel() {
                          JPanel  panel =  n   ew JP     anel();
          pan  el.se   tL    ayout(new BorderLayou       t(10     , 10));   
                   p       an   e  l.s  e       tBorder(BorderFacto   ry.crea      te E     mp tyBord er(1  0     ,         0,    0,    0));
        b oolean isRo ot = (   (hObj      ect in             sta   nceof Grou  p) &&     ((   Grou  p    ) hOb j     ect)     .      isR      oot());
        Fi    l  eFo    r mat theFile =              hObject.ge           tFi    le                   F             o rmat();

             JP  anel        topP    anel = new     JPa    nel  ()      ;
                        topPa     nel.setLayout(  new       Bor     derLayout());

          JPanel   lp = new   JPa   nel();   
                 lp.setLayout( new G ridLa   yo  ut(5,    1)   );
  
                               if (isRoot) {
                  lp.ad     d(new   JLabel("Fil               e Name: "));  
                lp     .add(new JLab  el(   "File  Path: "))  ;
               lp.add(new JLabel(     "File Type     :        "))  ;
                          if (i   sH5) {
                                  try {
                                     lib   ver = hObject.g   etF     il    eFormat    ().getLi   bBoun      ds();
                               }
                    catch (Ex                  ce   ption    ex)       {
                               ex.pr  i            nt      Stack        Tra      ce();    
                            }
                             if ((      (libv   er[              0]             == 0) |  |                  (libver[0  ] == 1)      )             && (libver[1] == 1))
                                 lp.add(     new JLabe                 l("Libra ry version:               ")        );
            }
                         }
                 e        lse    {
                               lp .add(new JLabel("Nam       e  :     "));
            if (i       sH5) {
                if (hObject.getLinkTarge      tObjNa       me() != null )           lp.add(      ne w JLabel("L      i    nk     To Tar  get:             "));
                  }   
                     lp.      add(ne    w JL                   abel("Pa          th: "))   ;      
                                 lp.add(new JLabel        ("            T   ype:      "     ));

                      /* bug #926 t    o remo ve     the O      ID,   pu      t it back          on Nov.    20, 2008, -        -PC        */
              if (is    H    4)    {   
                                lp.add(n      ew JLabel ("T      ag,      Ref:             "));
                        }
                  else {
                                            l  p.ad    d(   new JLab   el("Object Re     f:                        "));
                          }
           }

            JPanel rp =         new JPanel();
           rp.setLayou          t  (new GridLayout(     5 , 1));

             JLabel nameField = new JL abel(hOb      ject.getName        ())        ;
          rp.add (nam   eField);  

                                  JPa ne    l targetObjPanel =        new         JPan                   el(            );
                   JButto    n C h          angeTarget    O               bjBu    tton            = new JButton      ("Ch    a     nge"   );
                               Cha   ngeT      argetObj   Button.       set  A      ctio    nCo  m   ma    nd("      Cha   nge link     targe    t");
        Change    TargetObjButton.a ddActi     onLi     stener(t       h is   )          ;     

           if (isH5)    {
              i f (      hObje           ct.get     Lin  kTarget  ObjNam  e  () != null) {
                              l    ink   Field = new JText Field(hObject   .getLinkTa rgetO  bj    Name   ())        ;
                                   t    a  rgetObjPanel.setLayout(new    Bo     rderLayout());    
                             t   ar     ge    tObjPanel.a   dd(li  nkF     iel    d, Borde           r  Layout.C      ENTER);
                                    // targetObjPan      el.add(Chan  ge   T    argetObjButto  n, BorderLayout.   EAST);
                                  rp.ad      d( ta             r    ge    tOb  jPanel);
                     }  
                 }

           JLabel      p          ath   Field            = ne   w JLa        be l();          
                  if (isRo ot) { 
                  p         a    thF    ield.s  e   tT           ext(   (new File(h   Obje       c  t.getFile()))       .         get       Parent()  );
            }
              else {
                                        pathField.set                 Text(hObject.getPath());  
           }
                 r  p.add(pathField);                    

             Stri    ng type Str = "Unkn o   wn    ";
        String file    Info = ""        ;      
                   if (isRoot) {
                        lo  ng size =   0;
                                             tr     y   {
                                 size =  (ne   w Fil  e(hObj     e  ct   .   get   File(   ))  ).length();
                                     }
                    catc  h     (Exceptio n ex) {
                   size =  -1; 
                                    }
               si  ze /= 10    24;

                int gro  upCoun t =               0, dat           aset Count = 0;    
                   Def   aultMutable  Tree  Nod  e r       oo      t = (De     faul       tMutableTre      eNode) theFile      .get     RootN    o    de();
            DefaultMutableT reeNod  e    theN  ode = null;
                      Enumeration<?> local_e       num    = root   .depthFir     s   tEnumeratio  n();   
                 w     h ile (local_enum.hasMoreEle       ments())       {
                                   the No  de = (   Defau    l   tMu        tableTreeNode) l    ocal      _enum.nextEleme  nt(    );
                   if (th       eNode.getUser  Ob        jec t(  ) ins      tanc      eof G roup ) {
                                       g                 rou    pCount                      ++   ;
                         }
                     else  {
                          da     tas      etCount++;
                           }      
                            }
                 fileI    nfo = "siz e=" + size + "K,  groups=" + group Co    unt        + ",  datas ets="      +   da               tas   etCount        ;
         }

        if (isRo          ot) {
                                                   if (isH5 )      {
                                        typeSt       r = "H DF5,  " + fil  eInfo;
               }
                               else if (  isH4)     {
                         typeStr = "HDF  4,         " + fileInfo;
                   }
                   el   se {
                            typeStr =     fileInfo;
                      }
        }
               e l     se if (     is        H5) {
                if (hObject insta       nceof Group) {
                 typeStr = "HDF5    Grou p  ";
                          }
                         els e if (hObj   e      ct instanceo f  S     calarDS)         {
                     typ  eStr = "H   DF5    Scala               r       D ataset";  
                 }
                     e  lse if  (hO b       ject     in         stanceof C     ompound     DS)            {
                          typeStr = "HDF5 Compound Dataset";
               }
                                else if (hOb ject instanceo  f Da      tatype) {
                    type   Str = "HDF5 Named Da    taty              pe";
                  }
        }
        else if ( isH4)      {
                 if (hOb     ject inst   an ceof Group) {
                              typeStr = "HD   F4 Group";
                   }          
                      else i f    (      hO   bj ect  i    nstance of S calar    DS         ) {    
                                Sc       alarDS ds =                (Sc  alarDS) hObject;
                                                          if (d  s.is   I  mag e()) {
                         typeStr = "HDF4 Raster Image";
                                 }
                      else {
                                          type Str = "HDF   4 SD   S";
                                }
               }
                              el  s     e if (hObjec    t i      nstan             ce  of       Compo  undDS) {
                              t  ype   St     r = "H   DF4 Vdata";
             }  
              }
               else {
                        if    (hObject in       st anceof Gr ou   p ) {
                                     typeS   t   r = "Grou p";
               }
                          else if (hObje ct in     stance  of ScalarDS) {
                               t        ypeStr  = "Sc  alar    D     a    taset";
                                }
                     else if (hObje ct inst        a              nceof Com  p      oundDS)    {
                        t yp     eStr = "      Co  mp    ound  Dataset"  ;
                           }
          }
        
           JLabel typeFie       ld = new         JLabe  l(ty        peSt            r);
                       rp.add(type   Field  ); 

         if     (isRoot &&       i    sH5 ) {
                       Stri  ng libv    ersi            o     n = n     ull;
               if    ((libver[0]         == 0) &&         (libver[1] == 1))
                                     libv ersio   n    = "Earl  iest a  nd Lat  est";
                  else if ((li  bver         [0 ]    ==      1)     && (libver[1] == 1)) l   ibv      ers  ion     =        "     Latest an   d       Lates  t";
                      JLabel libverbound = ne        w JLabel(libvers          io   n);
              rp.          add(libve  rbound);
        }

                     /* bug #926 to remove            the OID    ,   put   it b               ack        on  Nov. 20, 200   8,  --PC */
                 String     o  id    Str = nul  l;
             long          [] OI D = h       Obje    ct.getOID();
        if   (O         ID !=    null)   {
                       o idStr = String.valueO  f    (  OID[0]);   
                                            f  or ( int i        = 1; i < O     ID     .leng   th      ; i+   +) {
                                      oidStr += ", "         + OI    D[i];
                  }
                     }    

         if (!isRoot) {
                     JLab        el o       id   Fi eld      =      ne  w JLabel  (oidStr);
            rp.add(oidField);
              }

        JPanel tmpP = new JPanel();
        tmpP.s    etL  ayout(new B   order       Layou  t  ()  );
                tmpP.add("West",       l  p    );     
                  t   mpP.add("Center", rp      );
        tmpP.  setBorder(new TitledBorder(""));

              to               pPanel.     ad  d("North", new JLabel(""))   ;
               topPanel.add        ("Cente             r", tmpP)  ;
  
                     JPanel i nfoPan          el =  nu          ll;
                                             if (         hOb ject in     stanceof  Group)   {
                    info  Panel = create  GroupInfoP anel   ((G      ro    up) hObject);
              }
        els e     if      (   hO   bject        ins    tanceof Dataset)      {
                infoPanel = createDataset         I          nfoPanel((Dat     aset)           hObject);
                        }
               else     if (     hObjec   t i  ns    tance  of Dataty     pe) {
                   i  nfoPanel = cre     ateName  dDatatypeI        nfoP   anel((Da    tatype) hObj       ec             t);
              }

        panel.add(topP    anel,    Bo   rderL    ayou t.N     ORTH    );
                                 if (     infoPa  n  e     l  != nul l) {
                              pan        el.add(i     n    foPanel,      Bor    de rLayout.CE   NTE  R);
               }

                   retur n panel;
    }

    /**
     *         C reates a    panel   us ed t         o   disp    l  a      y               HDF group i    nf       or  ma  ti    on.
     */
         pr        i v                      ate J Panel    c    reateGroup     InfoPanel(Group g)         {
                        JP  a             nel panel   = new JP       anel();

                 L  ist<?>   mlist    = g.getMembe           rL i    st(             );
                if    (m   list == null  ) {
                                 return pa  nel        ; 
        }

              i        nt n =            mlist.size();
        if (n <= 0) {
               ret ur     n panel;           
                    }

         String rowDa      t a[]   [] = n        ew  St  r    ing             [n][          2];
                    f  or (  int i = 0;         i < n; i++) {
                                                    HObjec      t theOb j = (HO bject ) mlist.ge  t(i );
                         ro    wData[i]       [0] = theObj.getNam  e    ();
            if    (  th       eObj in  stanceof Group)   {
                             rowDat    a[i]   [1] = "Group  "          ;
              }
                    els   e    if           (th eObj instanceof D at  ase     t ) {
                                  rowData[i][1]    = "              Dataset";
                     }
             }

        S tr  ing[        ]   columnNames =     { "Name",         "T  ype" };
             JTable table = ne          w       JTable(rowData,    column        Na   mes)   {
                     private static   fin al long se   rialVersionUID = -8   34321929059       5 9     0629L;

                  publi   c boolean    isCel  lEdi t    able(int row, int  colu         mn)   {  
                                      return false;
             }
        };
                                  table.setName("Group Info")    ;
            tab    le.s et          Cel        lSele     ctio  nEnabl ed(f             alse   )        ;

         /   /   set cell height   for large fonts     
                               int   cellRowHeight = Math.max(     16, t    ab    le.get      Fon                  tMe  trics(tab    le        .   g          etFont        ()              ).getHe   igh  t()       );
            tab    le.setRowHeight(cellRowHeight)      ;

            table.get    T      ableHeader().s                    etReorderi             ngAllo wed(false);
             JScroll         Pane sc                 roller = new JScr  ollPane (table);

            panel.setLayout(  new BorderLayo      u  t   ( ));
            if (g.g e  tNumbe   rO        fMembers      InF   ile(   )        < ViewPro  per     ties.get      Max  Membe      rs( )) {
               panel.ad     d(   n    e      w JLa             bel("    Number of    me   m bers: "    + n), BorderLayo   ut.NOR  TH);
                      }
               else {
                     p  anel.add(new JLabel("N u      mb                   er of members: " +    n +           " (  i  n memor y  ), " +             g.get  NumberOfMembe                  r  sInFile()
                                           + "             (in f   ile)           "),   Borde    rLayo     ut.NORT  H);
                        }
                          panel                                   .add(scrol  ler, Borde     rL   ayo     ut  .CENTER);    
                 panel.se        tBorder(new     Tit      l    edBorder("Group M    embers"));

        return pane  l;
      }

    private JPanel cre   ate           N     a    me dData   type      InfoPa              nel(D      at        atype  t)     {
          JPanel pan  el = new JPanel()   ;
             pan  el. se      tLayout(n                                     e   w Border         Layou    t())     ;
                JTextArea infoArea = new JTe                         x t  Ar ea(t  .   getD     ataty  pe   Descr    ip   tion(    ));  
           infoAre a.     setEditable(fa  lse)   ;

        pane      l.add(infoAr    ea, Border    Lay    out. CENTER);

                        return   pane l;
        }  

           /**
        * Creates a      pa     ne   l u   sed to displa     y HDF   da  taset                 i n          for mat io    n.
          */
    priva  te JP     anel cr    e     ateD at    ase tIn fo    P a  nel(Dataset d) {
        JPanel lp = new JPanel()      ;
              lp.     setLayout        (new                   GridLayout    (4         , 1));
          lp.add(   new   JLa           bel("No. of Dim     e nsi         on(s):        "))    ;
        lp .add(new J   Label ("Dim  en  sion Size(s): "   ) )    ;  
        lp.     add(ne    w       JLa  bel("        Ma   x   D       imension S   ize(s)   : ")    );
                               lp.ad        d(ne      w  JLab            e    l("Data Type: "));

            JPanel rp = new     JPa   nel()        ;
        r            p.setLayout(new G  ridLa          yout(  4, 1));

               if (d.getRank() <= 0)   {
                             d  .i       nit();
                           }
        JTextFiel       d txtf      = new JTex         tFie     ld("" + d   .getRa      nk(          ));
              txtf.setEditab le(false);
        rp   .add(     txt        f);

            S  tring dimStr    = nu    ll;
           St ring maxD          i   mStr  =     null;
           long   dims[] = d.  get  Di  ms();
            long maxDims[] = d.get   M  axDims();
             if (d  ims != null) {
                          String[]          dim   N   a    mes    = d.getDim            Names    ()     ;
                          bo     olean has  DimNames = ((dimNames != null) && (dimNames.l    e  ngth == dims.length));    
                            StringBu      ffer sb = new S t   ringBuffer();     
               StringB    uffer    sb2 = ne    w     StringBuffer()        ;

                 sb.append(d       ims[0 ]);
                          if (hasDim N ames) {
                    s      b.append(  " (");            
                                              sb.append   (dimN  ames      [    0]);
                            sb.append(")    ");
             }

                         if   (max Dims[0  ]                  < 0)
                          s b2  .app    end("Unlimited");
                      e  l      s        e
                      sb2 .append(m           axDims[0     ]);

                    for (int i = 1; i         < dims.lengt h   ; i     ++) {
                     sb              .append("             x   ");
                               sb.appe  nd(d   ims[i]  );
                        if (hasDim     Names)    {
                                          sb.a ppend("        (    ");
                                                                sb.append(dimNames[i]);
                                            sb.ap  pend(")");
                 }

                                sb2.append(" x ");
                         if (maxDims[i] <  0)
                                      sb2.append("Unlim     ite          d");
                    el    s   e
                                  s         b2.a    pp   en  d   (maxDim    s[i]  );   

                       }
                  di          mS    tr  =   sb.toStri   ng();        
            maxD  imStr = sb     2.toString();
        }
             t    x  tf =        new J    TextF  ield(dimStr );
              tx         tf.s   etEditable(false);
             rp.    ad  d(txtf);

        txt   f   = new JText        Field(          ma                     xDim  Str);
        tx                         tf.set             Editable(false);
          rp.a   dd(     txtf);

        St    ri               ng typeStr = null;
              if (d instanceof    S   calarDS)      {
            ScalarDS s   d    = (S    calarDS)       d;
               typeStr =      s     d.                   ge    tData    type ().getD    at  atypeDescript i    on  ()    ;
          }
                else if   (d instanceof Comp o      u   ndDS) {
                              if (i    sH4) {
                                   typeSt r = "Vdata";
                   }
                        e   lse {
                                              ty       p         eS tr = "C    omp          ound     ";
                      }
                   }

         txtf = new JTe  xtFi            eld(     typ   e           St       r);
                                        txtf  .setE        ditable(   f   alse);
        rp.a     d d(tx   tf);

          JPanel infoP = ne          w          JPanel();
         infoP      .      se                                      tLayout(   new Bo    r         derLayout());
        infoP.add(lp, BorderLayout.WEST);
        inf  oP.add(rp, Bor    derL   ayout.CENTER);
               infoP    .set                 Borde r(new T     it   le   d    Bord er( ""   ));

           JPa     n           el panel = new J  Pan   el   ();
                    pan    el.setLayout(new          Borde   rLay   out());
               panel.a  dd(infoP     , Borde              rLay  out.NOR  TH)     ;
          panel.setBorder(new Titl   ed      B     orde   r("Datas     p ace and Data   ty pe"));    

            // ad   d     com  pound datatyp             e informati  on
                 if (d i   n      s    tanc     eof Compo  u   ndD S) {
            Comp       oundDS      compoun              d = (CompoundD     S)                 d;

                   int n   = co   mpound.getMembe             rCo          un             t()          ;         
              if  (n > 0) {
                              Str    ing rowData[][] = new    String[n][3];
                             Str       in         g    n ame s[] = compound.get     MemberNames ();
                                           Datatyp      e t   ypes[]  = compound.getMember Types();
                              int ord   ers[] = co            m  pound  .getMe   m    be           rOrde  r   s(  );

                        for (int i = 0; i < n    ; i++) {
                              rowData[i][0] = n ames[i];
                                  in             t  mDi   ms[]  =     co    mpound.getMemebe          rD     ims(i);
                          if (m  D        ims == null)   {
                                     r   owData[                i      ][2                         ]             =    String.     valueOf(orders[i    ] );

                                                                 i  f      (i   s  H4 && types[i].getDatatypeCl  ass() == Da tat     ype.       C LASS  _   STRING) {
                                                                             rowData[i][2]     = String.val    ue     Of(t  yp       e     s[i        ].get    DatatypeSize());
                                        }
                             }
                                   el       se {
                                S      tring                  mS       t     r = String    .val    ueOf(mDi   ms[0 ]);
                                             int m   =   mD ims.length;
                                      for (int j = 1; j <     m   ; j++          )       {
                                      mStr    += " x  "     + mDims [j];
                                                 }
                                                             ro   wDat  a[i][2] = mStr;
                           }
                                                                 row       D       ata   [i][1]       = t     ypes[i].getDatatypeDe    scr   iption(             );
                      }

                       String[] colu m   nName  s  = { "  Name",    "  Type", "Arra  y     Siz     e" };   
                      JTable table = new       JTa  ble     (   rowD    a ta, columnNa   mes) {
                                              private static final long seri    alVersion            UI    D =   -15177733    07922536859L;

                                          publ   ic b    oo lean i  sCellEdita  ble(int             row, int co    lumn) {
                                      ret                   ur n false;
                                }
                          };
                      t     able.set      Na    me("Compound  MetaData   ");
                       table.setCel     lSelectionEna       bl ed(false);
                                table.ge  tTab l    eHea          der().setReorder             i               ng   Allowed(fa       lse   );
                   p         anel         .add(new JScr        ollPa   ne  (ta  ble)  , BorderLayo u      t .CENT          E      R);
      
                         // set cell h           eight     for l      ar           ge f    o   nt          s
                           int cel    lRowHeight   = Math.max(16, t     able.get         F    o         ntMetr    ics(table.get  Fon     t()).getHei  ght());
                                     t    ab    le  .s     et Ro     wHeight(cellRowHeight   );
                             } // i f (n >      0)
        } // if (d instanceof C    om          pou     nd)

                                      // add comp                res  sion   a  nd d      ata la  uoyt info          rmation
         // t ry      { d.getMetadata();     } ca          tc h (Excep              tion  ex     )     {  }
            S   tring    chunkI      nfo = "";       
           long[] c     hunks    =            d.g         etC           hu  nkS      i   ze()    ;
               i   f (chunks =           = null) {
            chunkInf o   =      "NO                  NE  ";
        }     
           els     e {
                                                      int   n = chunks .l ength     ;
                     c      hunkInfo = Stri      ng.valu  eOf(ch      unks[0]);
                  fo r (i    nt i = 1; i <   n; i++) {
                                chu    n        kInfo += " X " + chun ks[i];
                                }
           }

        JPanel bPanel    = new JPane  l();
           bPanel.setBorder(new TitledBorde     r(""));
          bPa         nel.set  L      ayout    (   new       Bord        erLay   out()) ;
          l  p = new    JPanel();
               lp  .s      e      t            Layo ut(new GridLayout(3, 1    ));
                      lp.add (new JLa     bel   ("C  hu   nking     : ")); 
          lp. add(n               ew JLabel ("Compr   essi on: "));
             l      p.add(n    e   w       JLa be  l("Fi     ll value:   "))         ;  
        bP       anel.a dd              (lp, Bo  rde    rL       ayou        t.WEST);

                              Object fillV  al    u   e =        null;
           String fi              ll   V al  ue  In     fo = "  NONE";
            if (d inst  anc   e             of S     calarDS) fi   llValue = ((Sc alarDS    )   d).getFillVal  ue();   
                   if (fillVa   lue ! = null) {
               i    f (fillVa  l ue.   g   e   tCla      s   s(). i    s    Arr   ay()               ) {
                             in       t          l       en = A  rray.getLen     gth     (        f    i  llValue     );
                                  fillV alu  e     In    fo    =          Array.get(fillValue, 0    ).toString();  
                    for (int i =    1; i < l    en; i++)    {
                             fillVal     ueIn         f              o    +=            ", ";
                    fillVa   lu    eI     nfo += A        r         ray.ge               t(fillValue, i)       .t oString(         );  
                   }
               }
                      els  e
                         f    ill         Va lueI   nfo = fillV alue.toString();      
             }             

                      rp =   ne  w    JP   ane   l() ;
              rp .s etLayout(ne w GridLayout(3, 1          ));
          rp       .                                 ad         d(ne        w JL  abel(chunkIn   fo))   ;
        rp.    add(ne          w J       Labe  l( d.getComp     ression()   ));
          rp.add  (new     JLabel               (  fillValueI   n           fo));
                 bPanel.a     dd(  r  p , Bo  rderLayout.CE  NTER);

        panel.add(  bPanel, B   ord    e    rLa      y       out.SO    U       TH);

                 re  tu   rn panel;
    } 
                      
    /**
     *        C   r  eate              s a p    anel u                se    d to displa y att   ribute            i     nformation.
      */
                  private JPan     e  l         createAttribu     t                 ePanel()    {      
        JP       ane      l p     anel    = new JPa  nel(   );
             pa       nel.s       et       Layout(n  ew B orderLa   y   out())   ;
             // panel      .s etBor  d        er(B  o  r                         derFactory         .crea      teEm       ptyB  o rder(10,0,     0,0));
     
              JPa   nel t    op    Pa  ne     l                = new JP a   nel  ();
              topPanel.setL         ayou     t    (new      Border       Layo  ut())            ;
            at tr  NumberLabel  = new J Label  ("N  umber of attribut es  = 0");
                    topPanel.add(a           ttrNu  mberLabel, BorderLayou t.WEST);

                         FileFormat   theFile =      hObjec   t.getFi        le      Format();
                     J     Panel bP  a    nel = ne                                w JPanel       ();
                  JButt      on b            =     new JButton(" Add "   );
             b.set        Mnemonic('A');
        b.addActionListen er(th        is);
                  b.setAct    ionCo      mmand("     A        dd att    ribute   ");  
        bP   anel.add(b);           
        b       .set Ena  bled(        !theFi   le.  isRead On     ly());

               if (is   H5)      {
                  /  / del       e           ting is not support     ed b   y HDF4
                       b = new     JButton(  "D      el ete");
                     b  .s     etMnemoni     c('D');
               b.addAct  ionListener(this);
                           b.setActio n    Command("Del   ete a    t   tri    bute");
                                 bP  anel.add(b);
                     b.setEnabled(!theFile   .isReadOnly() );
             }
                            top   Pane        l.ad    d(bPa  ne  l,    Bo     r    derLayou   t.EAST         );

        panel        .ad       d(to    pPanel, B  o  r           d      er Layou t.NOR     TH);
  
                   List           <?> a ttrL   ist = nu    ll    ;           
   
               try {    
                  attrLis      t =  hObjec  t.g  etM     etadata();
        }
                                          catch (Except   io                         n ex) {
             attr         List = null   ;  
          }
                          if (attr       Lis    t           != n  ull) {
                       n  u  mAtt      ributes = attrLis  t     .s   ize();
             }

           Str       ing[] columnNames  = { "Name          ",       "Value", "  Typ  e", "Array S ize"  };
            attrTa    bleMode    l = new      DefaultTa      bleM            od e   l(columnNames,   nu             mAttri    butes);

                                                           attr   T  a   ble = new JT    able( at   trT         a     bleMod      el) {
               private  s          tat   ic     final    lo  ng       s    eria   lVe rs    ionUID = 25902446    45       972259454L;
            int                                 la     stSe  lected  Row  = -1;
                  i     n  t                               lastSele       c    te     dCol  = -1;

                 p        ub    lic bool   ean is  Ce   ll  Ed  itabl     e(int row, in          t co    lumn) {
                      return (   (   c    olumn == 1) |    | (isH5 &&      (column == 0))); // only
                     //       attri     bute
                                   /    /      value and
                   // name      c     an
                                  //  be change  d
                           }
     
             p   ublic void ed iting                   Sto   p   pe  d(ChangeEvent e) {
                             int row   = getEditingRo w(    )   ;
                              int col = g   etEdit      i ngCol  u   mn        ()  ;
                                          Stri ng ol   dVa    l    ue     = (String) getValueAt(row, col);
    
                                  super.edit     ing    S   topped (e    );    

                      Object  source = e.ge tSour ce(    );

                       i      f               (source     instanc           eof Cell  Editor)  { 
                                                  C         ell  Ed    i       tor e      ditor = (CellEdit     or)       sou    r       ce     ;
                               Stri    ng ne      wVa     l    ue       = (         St      ring) editor   .getCellEdit orValue(    );
                                 se          tValue   A       t   (   old   Va    lue, row, col); // s     et    bac   k    to what it i        s
                                           up           da   teAttrib       u teValue(n   ewValue,    row, col);
                          }
                         }
          
                          publ ic  boolean isC   ellSel    ected(int row, int col) {
 
                         if ((getSele  cte     dRow          () ==     row) && (getSelect   edColum          n()    ==  c   ol  ) 
                                                                             && !(    (lastSelectedRow ==  row)   && (l         a      stSelectedCol ==      col))) {
                         // se   lectio           n i                 s c   hang  e    d
                                   Object a   tt   r     V        =   ge     tValue At(r     o     w, col);
                        if (attrV       != null     ) {
                                              attrContentArea.setT                   ex  t(a  ttrV.t  oString(         ))       ;
                     }   
                               la   stSele       ct        edRow = row;
                           las  tSe    lec   te d    Col = col;
                          }

                                       ret    urn      super.i    sCell  Sel ected(r   ow, col);
                     }
                };

                  at    trTable.setName("a          ttribut  es");
             at        tr     Table.                    setRo  wSel  e    ctionAllow   ed(false);
                    att    rTabl   e.s                     etCel   lSele   ct   ion  Enabl    ed    (t   rue);
                     a       tt    rTabl e.getT             ableHea      d  er()     .setReo        rderin     gAllo  wed(  fa   lse);
           attrT  abl     e.s     et  Sel        ect ion       Mode(ListSele ctionModel .SING   L  E _SEL        ECTION);

             // set cel      l h        eight    for large fonts
                   int ce    ll   RowHe         ight =    Mat        h.max(   16, a     ttr    Table.          getFontMetri        cs(attrTable.get   Font()).    getHeight());
                          at     trTa                ble.setRowHeight    (c  ellRow   Height    );

                    JScrol    lPane           s   crolle    r1 = new   JS         croll      Pane    (a                         t  tr Ta   b        le     );
                              a   ttr  ContentAr    ea    =    ne w JTe    xtArea();
                             at         t            rContentArea.setLineW           rap   (tr  u   e);   
           attrContentAr ea.setEditable(false);
            In     sets m    = new In   set  s(5, 5,    5,   5);
                        attrC     o         n     tentArea.s et   Margin(m);

              JScroll       Pane scro ller2 = n  ew J Scr   ollPane   ( attr   Cont  en    tAr     ea);                        
                     JSplitPane     spli        t   Pan e    = new JSpl        itPane(   JSp     lit Pane.              VER    TICAL_SPLIT,   scrol            ler1  , s     croll  er2);

                  // set the di  vider       locatio     n
              int h = Math.min(( numA  ttribu   tes +            2       ) *    attrTable.ge    tRowHeigh        t(),          scro     ller   1.           ge tP   referred          Size().height               - 40);
                split  Pane.setD ividerL  oc  ation(h);
             panel.add(splitP an    e, BorderLayout  .    CENTER)        ;
 
                        if (a   ttrList       == null) {
              return        p   anel;
        }

              Attribute   a ttr  =   nul             l;
         Stri          n g n            a  me, t y pe, size;
          attr        NumberLabel.s      etText("Number of         attr       ibute   s   = " +        numAt  tributes      );    
         for (int i = 0;    i < n  umAttributes; i+   +) {
               attr               = (Attrib  ute       )    attrList.get(i)      ;
                     n        ame = attr .g      etName (       );

                    // boo    l ean isU   nsig  ned =    fal           se  ; 
                              type = attr.getTy    pe().getData       typeDe   sc    ript          ion();
                  /    / isUnsi    g  ned      = attr.         getType()   .isUnsign    ed();

                            i       f (attr.isSca   lar( )) {
                                      size =   "Scala        r";
            }
                               els     e         {
                 long dims[    ] = attr.g e   tDataDims(  );
                      si        z   e =     St  r      i   n     g.v  alueOf(dim   s[0]     );
                 for (int   j   =  1;   j < dims.len    gth;      j++) {
                                    s         ize    +=   " x " + d ims  [j]             ;
                          }
                          }

                    attr   Tabl   e               .   setVal   u   eA  t(name, i, 0);
                attrTable    .setV   alueA  t(attr.toString     (    ", "), i, 1);
                    attrTable.se  tVa  lu eAt(type,    i, 2);
                        a   ttrTabl e.setV   alue  At   (size, i, 3);
              } / / for    (in          t   i=0; i<     n     ; i++)
 
        retu  rn    pane    l;
    }

    /** 
       *     Creat     es            a pan   e      l u   sed                            to di  s  play HDF5 user block  .
     */          
        private    JPane        l createUserB lockP  a    ne l(  ) {
           JPa    nel panel  = ne w JPanel(    );

        userBlo        ck =         De  fa     ultFileFi         lter.getHDF5U     serBlock(hObje  c     t.    getF   ile());

        p     anel.setLa       yout       (new B      orderLa yout    (10,    10))  ;
           pan el.  setB order(BorderF  ac  tory.cre       ateEmptyB      o  rder(10, 0, 0, 0))   ;
                     userBlockArea = ne   w JTextA    rea();
               u   se    rBl ock      Area .s    etEditabl     e(t     ru    e);
           userBlock Area.setLin         eWrap (true);
          Insets        m  = new I n        sets     (5, 5 ,    5, 5);       
                       userB lockArea.        se  tM argin(m);

            Stri    ng[] di  sp   layC         hoice     s =    { "Text", "Bi  nary",        "Oct   al", "Hexadecimal",     "    Decimal         " };
        JC   o    m         b                  oBox   u  ser        Bl     ockDisplayChoice =      n     ew JComboBox(disp layChoi ces);
                  use  rBlockDisplayChoice        .setSele  ctedInde  x(0);
           userBlock  Di  s   playCho    ice.addActionListener(th       is);
               us    e rBlockDis p               layChoi ce.setEdita    bl e(     false)    ;
        use    rBloc    kDisplayChoice.                     setAction     Command(   "Display     use     r bloc  k as");
        
               JL              abel sizeLabel = n    ew JLabel("Header Si    z      e (Bytes    ): 0");
        ja mButton = ne                 w JButton("S   ave User Bloc   k");
          jamButton.setA     ctio   nCommand("Ja    m      use    r      block");
              jamB   utto n.a   ddA     ctionLis     tener (th         is );    
               JPa           n el t  o    pP  ane = new JPanel ();
              topPane.setLa        yout(n   ew BorderLayout  (10,      5));
        to      pPane.add    (new JL  abel("Di  splay As:"), B orde        rLayout.WE ST);
        JPa   nel t          opP    a  ne0 = new J       Panel()          ;
          top  P  ane0.set  Lay   o   ut(new GridLayout( 1,          2, 10, 5));
        topPane0.add(us  erBlockDisplayCho    ic   e);
         topPane0.add(sizeLabel);
                   topPane.add(top    Pa   ne0, Bord   erLayout.    CEN        TER)  ;
             topPane.   add(jam  But ton,     Bo   rderL      a      you        t.E   AST);

         JScr   ollPan    e scroller =        new JSc  rollPane(userBlockArea)     ;
                    panel.add(t      opPane,   Border                Layout.N O       RTH   );
                         pane           l.a    dd    (s     croller, BorderLayout.CENTER     )      ;
  
          int headS   i   ze =               0;
               if  (use     rBlock != nu ll)  {
                           headS     ize = s     howU se     r           Bloc  kAs(0         );
             size          Lab     el.                 setT             ext("Header Size ( Byte     s): " + headSize);  
          }   
        else     {  
                  userBlock     DisplayC   hoice.setE     nabled(false);       
           }

                r      eturn pa   ne  l;
       }

       private i      nt showUserBlockAs(      int r              adix) {
        int   header  S  ize =   0;

        if (u  serBlock              == null) {
            r e  turn                0;    
              }   
  
             String userBlockIn f      o =    n  ull;
            if ((   radix == 2)     || (rad ix == 8         ) || (radix     ==   16) || (radix == 10)) {
                    Stri     ngBu  ffer sb =     new StringBuffer()      ;
                 f       or      (headerS ize =  0;   headerSize < use         r  Blo   ck.l  en  gth; h    e    ad    erSi     ze+   +) {
                     int intValue = (int) user Block[headerS    ize];
                   if           (i    ntValue < 0  ) {
                              intValue += 2    56;   
                        }
                   els   e if (intValue == 0) {
                    break; // nu  ll      en      d
                   }
                         sb   .append  (Int     eger.toStri     ng(intVal ue,   rad                    ix)           );
                sb.a          ppe   nd(" ");
                 }
            u   serBlo ckI nf  o = s  b.t  oSt    ring          ();
            }
        else {    
                u serBlockInfo =     new String(   us  er Block  ).trim();    
                 if (u   s    er BlockInfo != null) {
                   headerSize = u        serB   lockInfo .l  engt h();
                 }
           }

          u   serBl   ockArea.setText(us    erBlockI nfo);

        return headerSi      z   e;
         }
     
    /**
        *       update attribute value.         Currently     can only up  date single data  p  oint .
                     * 
         *    @param new   Value
     *                      the string of the new value     .
                       * @ param  row
                *                 the ro               w numbe     r of    the sele  c         ted ce         ll.       
        * @       param col
     *                              the   column numbe  r of the s    elected cell.
     */
    private vo     id    up   dateAttrib   ut  eValue(String newVal    ue, i  nt    row, in      t c        ol) {   
                     //    if    (co  l    !=    1) {
         /   / retur  n;      // can only c         hange attribute valu   e      
          // }
          
                 String      at  trName     = (String ) at trTable. getValueA   t(row, 0);
        List<?>   attrLis  t    = null;
        try    {
                  at trList = hObject.getMeta            data();   
        }
               catch         (E  xcept       ion ex) {
                      JOptionPane.showMes  sag    eDialog(get  Owner   (),     ex.getMessage(),   getTitle(),          JOptionPane.ERROR   _     MESS AGE);
              return;
             }

              Attribute attr = (A  ttribu    te) at   trLis t.get(row);

           if (col   == 1) { //    To        change att   r   ibute va    lue
                         Object    dat   a = at   tr.ge tValue();
                  if    (d         ata == null) {
                        return;
                 }

            int ar       ray_           l  ength = Array.getLength(data);
              St     rin      gTok    enizer s  t =   new StringToke nizer(newValu   e      , ",");
                if (st.co untTokens( ) < arr    a   y      _le ngth) {
                J   Opti onPan       e.showMessageDi     al og(ge    t          Owner   ()    ,     "More d   ata value need   ed:     " + n     ewValu  e, getTitle(),
                            JOptionP    ane.ERROR_M  ESSAGE  );         
                              r        eturn; 
                         }    

             char N         T = ' ';
                    Stri   ng cN         a      me = data  .g    etClass().getName();
             int cIndex = cName.    lastIndexOf ("[");
             if (c     Inde x >=    0    )   {
                                      NT   = cName.           charAt  (cIndex + 1);
             }
                 bo      o  lean isUnsigned = a  t tr     .isUnsig   ned();
      
                 double  d = 0;
                  String th   eToken = null;
            l  ong max = 0,     min             =         0;
                    for (int i = 0; i < array_length; i++) {
                    max =    min = 0;
                th  eT oke      n     =    st.nextToken().trim()   ;
                      try {
                            i     f (!(Array.get( d    at   a, i) instanceof String)     ) {
                                d      = Double.parseDouble(theToken)       ;    
                          }
                  }
                       cat      ch (Numb       erFo   rmatExceptio  n ex) {
                             JOptionP   a   n    e.showMessageDialog(getOw  n     er   (), ex.getMessage(   ), getTit   le(), JOpt     ionPane.ER    RO R_MESSAGE);        
                             return;  
                                           }

                 if (isUnsig   ned && (d < 0)) {    
                                     JOp     tionPane                 .showMes     sageD ial  o  g(getOwner  (), "Neg    a   tive val       u  e for unsigne  d in t  e       ger   : " + theTok    en,
                                                                getTitle() ,       JOptionPa   n     e.ERR        OR_MESSAGE);
                    return              ;
                        }

                switch (NT)    {
                                case 'B': {
                              if (     isUnsigned)   {
                                 min     = 0;
                                                  max = 255;
                        }
                             e lse {
                                   min = By  te.MIN_VALUE;
                                              max = Byt    e.MAX_  VAL  UE    ;
                              }

                        if ((d >    max) | | (     d   <   min)) {
                                                        JOptionPane.s      howMessag  eDialog(ge   tOw  ner(   )   , "Data is out of range[" + min +      ", " + max
                                                  + "]:     " +            theT          oken,     g      etTitle(), JOptionPane.ERROR_MESSAGE);
                           }
                                         else  {
                                   Arra y.setByt   e(data, i,         (byte)                     d);
                                   }
                            break;
                            }
                    cas     e 'S      ': {
                                      if (isU          nsigned) {
                                   min = 0;
                                    max = 65       535  ;
                              }
                           else {
                                         min = S  hort.MI    N_VALUE;
                                     ma x =  Shor   t.MAX_VAL   UE;
                                 }

                           if ((d   > max) || (d < mi    n)) {
                                                              JOptio nPane.show       Messa   geDialog(getOwner(), "Data is ou t o            f range[" + min + "    , " + max
                                            + "]: " + theToken, getTitle(), JOptionPane.E   RROR_MESSAG          E );
                           }
                        else {
                            Array.se    tShort(data, i, (sh    ort) d   );
                                        }
                                     brea    k;
                       }
                    case 'I': {
                          if (isUnsigne    d) {
                                        mi               n      = 0;
                                   max = 4294967295L         ;
                            }
                                      else {
                                         min = Integer.MIN_VA LUE;
                                              max =      Integer.MAX_VALUE;
                                    }

                           if   ((d > max) ||     (d < mi n)) {
                                     JOptionPane.showMessageDialo g    (getOwne  r(), "Data is      out of range[" + min + ", " + max
                                            + "]: "     + theToken, getTitle(), JOptionPane.ERROR_MESSAGE);
                         }
                            else      {
                                  Array.setInt(da         ta, i, (    int) d);
                         }
                                     break;
                        }
                          c       ase 'J':    
                               if ( isUnsigned) {
                                       if (theToken != null)   {
                                              String theValue = theToken;
                                BigInteger J      max = new BigInteger("1844   67   44073   7    09551615");
                                BigInteger  big = new BigIn teger(theValue); 
                                                   if ((b  ig.compareTo(Jmax)>0) || (big.c              ompa reTo(   BigInteger.ZERO)<0))    {
                                                            JOptio nPane.showM   essa    geDialog(ge   tOwner(), "Data is out of range[" +       min + ", " + max
                                                  + "]: " + theToken, getTit     le(), JOptionPane.ERROR_   MESSAGE);
                                      }
                                            Arr ay.s    etLong( data, i, big.longValue());
                                        }
                                   else
                                     Arr   ay.set(data, i, (Object)th     eToken);
                        }
                                    else {
                                    min = Long.MIN_VAL     UE;
                               max = Lon     g.MAX_VA     LUE;
                               if ((d > max) || (d <     min)) {
                                        JOptionPane.s   howMessa    geDialog (ge    tOwner(), "Data is out of range["   +      min + ", " + max
                                                  + "]: " + theToken, getTitle   (), JOptionPane.ER    R OR_MESS   AGE);
                            }
                 
                               Array.setLong(data, i, (long) d);
                                }
                            break;
                       case 'F':
                        Array.se   tFloat(data, i, (float)  d);
                            break;
                    c    a   se 'D'    :
                              Array.     setD    ouble(data, i, d);
                              break;
                              default:
                        Array.set(data, i, (Obj     ect)theToken     );
                        break;
                      }
            }

                 try {
                   hObject.getFileFormat().wri    teAttribute(hObject, attr, true);
                 }
            catch (Exception ex) {
                                    JOptionPane.showMessageD   ialog(get  Owner(), ex.        getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
                return  ;
               }

                   // update the attribute ta   ble
               att     rT able.setValu    eAt(at     tr.t   oString(", "), row, 1);
        }

        i f ((c      ol ==        0) && isH5) { // To change a   ttribute name
            try {
                              hObject.getFileFormat().renameAttr ib   ute(hObject, attrName, newValue);
            }
              catch (Exception ex) {
                  JOptionPane.showMessageDialog(getOwner(), ex.getMessage(), getTitle(), JO    ptionP ane.ERROR_   MESSAGE);
                return  ;
                }

            // update the attri   bute ta ble
            attrTable.setValueAt(newVal   ue, row, 0);
        } 
    }

    p  rivate void write     UserBlock     () {
        if (!isH5) {
            return;
        }

        int   blkSize0 =  0;
        if (userBlock   != n ul    l) {
             blkSize 0     = userBl    ock.length;
                 // The super block space is alloc   ated by of     fset 0, 512, 1024, 2048,
             // etc
            if (blkSize0 > 0) {
                      int offset = 512;
                    while (offset < blkSize0) {
                         o   ffset *= 2;
                 }
                blkSize0 = offset;
            }
               }

        int blk   Size    1 = 0;
        String userBlockStr    = userBlockArea.getText()       ;
            if (userBlockSt r == null) {
            if (blkSize0 <   = 0) {
                return    ; // nothing to write
            }
               else {
                userBlockStr = " "; // want    to wi    pe ou  t old u    serblock content
            }
             }
        byte buf[] = null;
            buf = userBlockStr.getBytes();

        bl   kSize1 = buf.length;
        if (blk   Size1 <=  blkSize0) {
                java.io.RandomAccessFile  raf = null;
            try {
                raf = new    java.io.RandomAccessFile(hObject.getFile(), "rw");
              }
            catch (Exception ex) {
                JOptionPane.showMessageD     ialog(this,  "Can't open output file: " + hObject.getFile    (), get  Title(),
                            JOptionPane   .ERROR_MESSAGE);
                return;
            }
    
            try {
                           raf.seek(0  );
                raf.wr   ite( buf, 0, buf.leng      th);
                raf.seek(buf.length);
                if (blk Size0 > buf.length  ) {
                     b     yte[] padBuf = new byt   e   [blkSize      0 - bu  f.le      ngth];
                      raf.wr      ite(padBuf, 0, padBuf.length);
                    }
            }
            catch (Exception ex)           {
            }
            try {
                   raf .cl    ose();
            }
            c    atch (Exception ex) {
            }

              JOptionPane.showMessageDialog(this, "Saving user block is succe     ssful.", getTitle(),
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            // must rewrite the whole file
            // must    rewrit   e the whole file
            int op = JOptionPane.showConfirmDialog(this,
                     "The user block to write is " + blkSize1 + " (bytes),\n"
                                   + "which is larger than the user block space in file " + blkSize0 + " (bytes).\n"     
                                + "To e    xpand the user block, the file must be rewriten.\n\n"
                            + "Do yo    u want to replace the current file? Click "
                                + "\n\"Yes      \" to   replace the current file,"       + "\n\"   No\" to save to a different file, "
                            + "\n\"Cancel\" to quit without saving the change.\n\n ", getTi    tle(),
                            JOptionPane.YES_NO_CANCE   L_OPTION, JOptionPane.WARNING_MESSAGE);

            if      (op == JOptionPane.CANCEL_OPTION)   {
                return;
            }

            Strin  g fin = hObject.getFile();

                       String fout = fin + "~copy.h5";
            if (fin.endsWith(".h5")) {
                fout = fin.substring(0, fi   n.length() - 3) + "~copy.h5";
            }
            els    e if (fin.endsWith(".hdf5")) {
                fout = fin.substring(0, fin.length() - 5) + "~copy.h5";
            }

            File outFile = null;

            if (op == JOptionPane.NO_OPTI   ON) {
                JFileChooser fchooser = new JFileChooser();
                fchooser.setFileFilter(     Defau     ltFileFilter.getFileFilterH DF5()  );
                fchooser.setSelectedFile(new File(fout));

                int returnVa   l = fchooser.showS    aveDialog(this);
                if (returnVal      != JFileChooser.APPROVE_OPTION) {
                    return;
                 }

                File choosedFile = fchooser.getSelectedFile();
                  if (choosedFile == null)  {
                    return;
                }

                    out   File = c hoosedFile;
                fout = outFile.getAbsolutePath();
            }
            e    lse {
                      outFile =   new File(fout);
            }
  
            if (!outFile.exists()) {
                try {
                    outFile.createNewFile();
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Fail to write u       ser block into file. ", getTitle(),
                                   JOptionPane.ERROR_MES  SAGE);
                    return;
                }
            }

            // close the file
            ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PE        RFORMED, "Close file");
             ((HDFView) viewer).actionPerf ormed(e);
 
            if (DefaultFileFilter.setHDF5UserBlock(fin, fout, buf)) {
                if (op == JOptionPane.NO_OPTION) {
                      fin = fout; // open the new file
                }
                else {
                    Fi le oldFi    le = new File(fin);
                    boolean status = oldFile.delete();
                      if (status) {
                        outFile.renameTo(oldFile);
                    }
                    else {
                        JOptionPane.showMessageDialog(this,
                                "Cannot replace the current file.\nPlease save to a different file.", getTitle(),
                                JOptionPane.ERROR_MESSAGE);
                        outFile.delete();
                    }
                }
            }
             else {
                JOptionPane.showMessageDialog(this, "Fail to write user block into file. ", getTitle(),
                        JOptionPane.ERROR_MESSAGE);
                outFile.delete();
            }

            // reopen the file
            dispose();
            e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Open file://" + fin);
            ((HDFView) viewer).actionPerformed(e);
        }
    }

}
