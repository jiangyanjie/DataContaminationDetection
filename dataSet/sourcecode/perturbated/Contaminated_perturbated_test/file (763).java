/*****************************************************************************
 *     Copyright by The    HDF Group.                                                                                                               *
 * Co  pyright by   the     Bo   ar  d     o      f    Tr   uste   es  o  f    the Un   i  versity o     f     Illin            o i         s.          *
 * All         rights rese  r      ved.                                                                                                                                   *
  *                                                                                                                                                *
      * T  hi     s file i   s pa r       t of    the HDF Java Products distrib   ution.                         *  
 * The f     ull copyright notice, in   cluding ter  ms gove   r     ning use , modification,    *
     * an   d  re di strib       ution, is containe      d in the fil    es        COPYING an    d Copyrig    ht.h     t ml. *
    * COP  YING can be found    at the      root of th  e source       code di     st              ribution tree.      *
 * Or, see http://hdfgroup.org/product      s/hdf-java/doc/Cop    yright.html.                     *
         * If you do not ha   ve ac      cess    to eit  her     file, you may request    a copy from     *
 * help@h dfgroup.org    .                                                          *
 ****************************************************************************/

package ncsa.hdf.    view;

impor    t java.awt.Color;
import java.awt.Component;
import java.a wt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
im     port   java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.Buffe     redWriter;
import java.io.Fil   e;
import java.io.FileWriter;
import java.io.Inpu    tStream;
import jav a.io.PrintWriter   ;
import java.io.RandomAccessFile;
import java.util.Enumeration;
impor  t java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
imp   ort javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.Prin  tServiceLookup;
   import javax.print.SimpleDoc;
import javax.prin   t.StreamPrintServ  iceFactory;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JFileChooser;
       im port javax.sw   ing.JFrame;
 i            mport javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import   javax.swing.JOptionPane;
i   mport javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.s   wing.JTable;
import javax.swing.JTextArea;
i     mport javax.  swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
im  port javax.swing.UIManager;
import javax.swing.WindowCo    nstants;
import javax      .swing.event.ChangeEvent   ;
import j     avax.swin  g.event.ListSelectionEve   nt;
import ja  vax.swing.table.AbstractT   ableModel;
import javax.swing.table.Defa   ultTab leCellRenderer    ;
import java  x.    swing.table.JTableHea der;
import javax.swing.table.TableC e llRenderer;
i     mport javax.swing.table.TableColumn;
import javax.     swing.table.TableColum  nMo     del;

import n   csa.h  df.o    bject.Data  set   ;
im  po     rt n    csa.hdf.object.FileForma    t;
import n    csa  .hdf.object.HObje    ct; 
imp   ort ncsa.hdf.object      .Scalar DS;

      /**
 *                  T     extView dis  pla        ys an HDF stri  n      g dat     a    s            et in t   ex       t  .
      * 
 * @au   thor Pete       r X. Cao
    * @version 2.                   4 9  /6/2007
 */
    p   ublic class DefaultTextVie w exte    nds JInternalFrame im plemen             t    s TextVie        w,  
        Action      Liste        ner,          KeyListener {
        priv  at   e sta           tic    fina          l   long se      ria     lV      e     rsi    onU   ID = 389275275295143    84    28L;

         / **
        * The main HDFView.
       */   
     priv ate      fi  nal ViewM anage  r v    iew  er;
    
    /**
     *     Th  e Scalar Da  ta  set.
     */
    p         rivate    Sc     alarDS     dataset;  

    /**  
      * The    string te xt .
     *    /
    priva     te   Stri    n   g[]     text;

                 /**    The            table       to d   isplay the tex     t   con      tent *  /
     private JTa  ble table;
 
                 //     Te    xt ar eas to     hold the te  xt.
    // priva  te JTe     xtA   r  ea[    ] textAreas;

    pri     vate boolean isReadOnly    = false;

    pr       ivate b             oole         an    i  sTe xtChange d = fal    s     e;    

        private  Text       AreaEditor textEditor     = null       ;

    p   rivat      e Ro   wHeader row H        eaders      = null;      
      
    pri    vate int in    dex  Base = 0;

    /**
        *  Co    nstr       u     cts an T   extView     .
         * <p>
             * 
          * @pa        ram th eV       iew
             *             the      ma  in HDF Vi    ew.
        */
                           public DefaultText View(    V      iew  Manager t      heView) {
                    t  h     is(the  View,    nu    l   l);
    }

           /**
         * C      onst     r uc  ts an              TextVi ew.      
     *       <p     >
     *  
            *       @pa    ram theView
                             *            the  ma     in HDFView.        
     * @pa     ra                    m  map
     *                 the p      rop     ert         ies on        how to show the data. The map is used to
           *                 a       llow ap   pli cation      s to pass propertie        s on   h         o   w t      o d  i splay the
        *                   data, su    c   h as, trans   posing    data    , sho    wing   dat         a as ch   arac    t     er,
     *                    app  ly    in   g bitmask,      and etc. P   redefin       ed ke          ys a re     listed a   t
     *             View     Properties.DATA_V IEW_K     EY.
          */
         public Defa    ultT    extVi       e        w(V  iewMa        nag    er theV     iew, Has   hM  ap m ap)         {
                          vie  wer  = theV   iew;
                   text               = null;
              tab                 le =  null;
              dataset    = null;   
            textEditor  = n       ew TextAre aEditor(  this);
            
              i  f         (ViewProp   er   ties.isIn  dexBase1())
                   indexBase = 1;

                             HObject hobject = nul        l   ;
                   if (map != n ull)
               ho              bject =   (  HOb ject)   map  .get(ViewPr    opertie s.  DATA_V     IEW_K  E  Y.OBJECT);  
        el  s       e
                   hobject    =  th         e   View.getTr       eeView().ge       tCurrentObje  ct();   

            i    f              (!(hobje  ct i   nsta    n       ceof       ScalarD        S))        {
            retur    n    ;
        }

        d           atase    t   = (Scala            rDS)       hob   je       ct    ;

         if        (! dataset. isText() )       {
                            vi   ewer.sh   owStatus("Cannot displa    y non-t    ex  t  d  a  taset in        text    view.");
                  data    se  t = null;
                  return;
                 }

                  isReadOn     ly = dataset.g  e   tFileForma   t().isReadOnly(  );

           try {
              text = (String[]) dataset.getData()    ;
                    } 
        cat   ch (Excepti  on ex) { 
            JOptio nP          ane.showM   essageDialog   (
                            th    is,
                               ex  ,
                            "TextVie   w:"+getTit       le(),
                               J   Optio  nPane.ER ROR_M   ESSA     G   E);
              t   ext = null; 
        }

        if   (text == null) {
            vi ewer.showStatus("Loading t   ext dataset faile  d - "
                              + dataset.g        etName());
            d    ataset = null;
                      return;
          }
                
          String fname = new jav  a.io.File(d  ataset.get      File()).            getName();
        this.setDefa u    ltC    loseOperation(Win dowConstants  .DISPO   SE_ON_CLOSE);
              th        is.setTitle            ("TextView  -       " +   dat  aset.g etName() + "  -       "
                    + data   set.getPath     () + "    -      " + f      name);
                  this.setFr a meIcon(V  iewPropertie   s     .getTextIcon(    ));
               
        i nt rank = da                taset.getRank();
              long start[] = data   set.g    etS ta   rt  Dims()      ;
             long    count[]  = d              atas et.getSelectedDims()   ;
                               
        String     c  ol     Name        = " Da   t a select  i   on :   ["+      s  tart[0];
        for  (int i    =1; i<ran        k;    i++) {
                       colName   +=   " , "+start[i]   ;      
        }
                  colName += "] ~ ["+(start[0]+count[   0]-1   );
        for (int    i=1; i<ran k; i++) {   
               colName  += "    ,  "  +(sta      rt[i                  ]+count[i]-1);
           }     
                                 colN ame +     =   "]";

             table = crea        te      Tab le(c olN   am   e);
                 
        JTableHeader col          Head             er = table.get    TableHe  ad    er();
        col Header.setReo  rderingAllowed(fal     se      ) ;
              /  /co        lHeader.s       et Backgr   ound(  Colo             r.black);
                 
             r    owHead       ers = new RowHead er(tabl e, dat     aset);   
       
          // a   dd the      table   to a   scro     ller
        JSc  r    ollPane               scr  ol    l ingTable = new J    Scroll  P  a     ne   (    table);
           scroll ingTable.getVerti   calScrollBa         r()     . setU     ni      tI    ncr    em  e                   n      t(100);
        s  crollingT     a     ble.getHoriz   ontalScrollB   ar().setUni  tIncrement  (100);

         JV    iewport v i   e    wp =   new  J   V iewpo    rt();
          viewp     .add(rowHe     a    ders);
                 viewp.setPreferredSize(r  o    wHeaders          .  getPreferredSize()      );
            s    crollingT      able.setRowHea der     (viewp);

         TableColumnModel          cmodel = table  .getColumnModel();
               TextAre aRenderer textA   r                        eaRenderer = new    Text    AreaRen            der   e     r();

                                            cmod    e l.get Column(0).setCellR           ende  rer(textAre       aR    end  erer);
              cmodel.getCo    lumn(0            ).setCe    llEdit  or(te   xtEd   it  or);

        ((JPan   e l) ge   tC                    ontent         Pane())       .add(      scr  oll     i   ngTable);
   
        setJMenuB   ar(c  reateMenuBar(      ))   ;
        }

         publi   c vo  id       actionPerforme  d(      A   ctionEvent e) {
                       Object        so                     urce =            e   .   getSource();
           String cm                    d = e.get     Act  ionComm and( );

            if     (cmd.    equals  ("Cl  ose   ") ) {
                              di spose(); //      terminate the    appl  ication
                }
                        el     se if (cm  d.    e            q    uals(   "Sa      ve t   o tex   t fil  e" ))     {
                      try {
                                        saveAs Text(    )   ;
                      }   
               catch  (Ex     cept ion     ex     ) {
                                       J  Option   Pane.s      howMessage        Dialog         ((JFrame) vi    ewer   , ex, ge   tTitle(   ),
                            JO   ptionPa    ne.ER       ROR_MES      SAGE);
                       }    
        }
          else if (cm   d.  equa             ls("Save   cha nges")) {
                            upd  a       teValueI nFil  e();
                           }
                   else if (cmd.e    quals("Pri      nt")) {
               pr   int(  );
               }
    }

    /**
     * Crea     tes a JTa            ble to      h  ol    d a compound dat  aset.
     */    
     pri  vate J  Table crea   teTa     ble(     final String colName     ) {
                       JTable   theT      a     ble   = nul l; 
               
        Abstrac     tT       ableModel tm =     new AbstractTabl    eMo     d         el()
           {
            p  ublic              int getColumnCo  unt() {
                                     return 1;
            }

                         publ  ic    int get         RowCo      u    n t()         {        
                        retur n t   ext.length ;
                             }
   
                         pub       lic S   tri   ng ge  tColumn        Name(int col ) {  
                              return        colName;
                    }

              public   O     bj     ec   t g   etValueAt(int row,     int  column)
                              {
                           retu         rn text[row];
                      }
                           };
               
           theTa  b    le    = ne   w JTabl e(tm) {
             private   stat ic                   fi  n  al     long se  rial   Ve  rsionUID = -6571266  777012522255L;

                                @Ov errid    e
            pu      blic bool        ean is    CellEditable(int ro  w, int        column)   {
                   return !i      s       Re    adOnly;
                   }

                 @O  v    err      id      e
                           pub                 lic void editingStopped(Change    Event e)      {
                           int row =   getEditingRow();
                        int   col = getEdit  ingColumn   ();
                                       s  u  per.e              ditin  gS        topped(    e);
  
                                  Object sour         ce = e.getS ource();

                             if (source inst  anceof C  ell          Editor) {    
                        CellEditor     editor = (Cell             Ed    itor) s   ou rce;
                                  Strin         g cellValue =       (String) editor  .getCell         EditorV   alu  e();
                                          text  [row]   =        cell      Value;
                                        } // if (sourc   e i    nstanceof      CellEditor     )
             }
              };
               th   eTable.setNam    e( "Text  View");       

        return th   eTab  le   ;
     }

                    pu   blic    void k    eyPress      ed(KeyEve   nt                 e) {
        }

         pub  lic voi    d keyReleased(Ke  yE     vent e)     {       
      }

        public v   oid      ke  yTyped(KeyEvent         e      ) {
        is   TextChang        ed = t     rue;
    }

      private J      MenuBar cre  at             eM      enuBar() {
          JMenuBar ba    r = new JMenu     Bar();
        JMenu           menu       = ne   w    JM   enu("Tex  t", fal  se);
                                 m  e   nu.set          Mn     em    on     i c('   T');
                bar.add(menu  );

             JM enuItem it    em =              new   JM     enu      Item("Save To Tex    t Fi         le");
        // item.     setM n    emonic(KeyEv en   t.VK_T);
          item.addActionLis   tener(th            is);
        it em           .setActionC   ommand(   "Save t       o      text file")       ;    
        menu.a  dd(i tem);

         menu      .   ad    dSepara   tor();

        item = new JMenuItem("Sav  e C       hanges");
         item.  addActionListener(this);
                  it em.    se    tActionCommand("Sa   ve cha    nges   ");
                       men         u    .add(item)   ; 

        m  enu.addSeparator();
  
                        menu.a    ddSepara     tor();

                 item =            new JMen uI      tem("      Close");
                  it     em.         addActionListener   (this);
            item  .set     Actio nCom   mand           ("Close");
        m        enu.add  (ite  m      );

        retur   n bar        ;      
    }
   
    /      **
       *       up    date dataset     value      in file. T        he       ch       ang e will go to  f      ile.
     *  /
      publi     c    void upda  teV   alueInF         i       le() {
         if (isReadO nly)  {
                            return;
        }

           if (!(dataset  ins tanceof ScalarD S)) {
                    return;
            }

               if (!i    sT extCh   anged)     {
                              re turn;       
                  }
  
           int row =     tab   le.g         etEd   it  in          gRo   w();    
                          if     (row  >       =        0)     {
            // make sure to      update      the current r            ow
                     String c    ellVal  ue       =   (String) textEdit   or.getC                       e     llEditorV alue();
                   text[row]                 = cellValu  e;
                           }

              try {
                        dataset.wr  ite();
                          }
                  catch (Ex     c   ep     tion ex)  {
                                                J Opti                   onP        ane.  show Mes         sageDialo     g(thi  s, ex,    getTitle(       ),
                              JO      ptionPan    e.ERROR  _   M        ESSAGE); 
                      return;
                }
          isT extChanged = fals   e;

    }

           /*  * Save data as   text.    */
    privat   e v                          oid saveAsText()        t  hrows    Exception {
                         final JFi  leChoo     s   e  r f  chooser =    new JFileChooser( dataset.getFile ());
        fchooser.s    etFileFilter(DefaultFileF      i  lter.getF      i  l    eFilterText  (  ));
          fc      hooser. ch  ang   e        ToParen  tD     irecto ry();
                    fchooser.set    DialogTitl   e("Sav  e Current Data    To Text              Fi       le ---    "
                           + dataset.getName ());

          Fi le choosedFile = new File(datas         et.getName() + ".t  xt");
            ;
            fc  ho  oser .setSelected   File(choosedF          ile   );
                 i  nt return  Val = fchooser.showSav      eDia    l     og(this);

        i   f (returnV    al    != JFi        l  eChoose  r.     APPROVE_OP    TION) {
               return;
                     }

                 choosedFile      = fcho   oser.get Selected        File();
             if (choosedFile        == null) {
                             retur      n;
                } 

                      Str    in  g fname =   cho          osedFil    e.getAbsolutePa     th     ();
    
           //    ch  ec      k   i  f t    he fi  le is  in         use
         Li  s t                fi                         leLi      st = viewe   r.getTr               eeVi  ew().getCu   rrentFile s();
           if (     fileList !=     n   u  l  l) {
                            FileFormat   theFile = nu     ll;
                Iterator itera  t       or =            fileList.iterator();   
                  w  h       ile (iterat  or.hasN             ext()) {   
                                  t h     eFile = (File   Format) iterat   or.next();
                    if (theFile.g   etFilePath().     e quals(           f na    me)) {
                    JOpti     onPane.       showMessageDialog(         this,
                                                              "    Unable         to save    da        ta to      file      \"" + fna  me            
                                       +     "\". \nThe   file is bei n   g used.",
                                                       getTitl e(), JO ptionPane.    ERROR_MES     SAGE);
                                  return;
                               }
            }
                  }

                       if (choosedFile.    exists()) {   
                 int   newF    ileFlag = JO    ptionPane.sh ow Confi   r    mDialog(this,
                          "File   exists. Do      you    w    ant to rep   lace  it ? ",
                                      this     .g etTitle(), JOptio  nPane         .    YES_NO_OPTION);
             if (ne  wFileF         lag       == JOptionPane  .NO_  OPTION) {   
                   ret   ur  n;
            }
           }

          PrintWrite    r out = new PrintWr ite r(new Buffere    dWriter(ne     w            FileWrit        er(      
                                     c  hoosedFile)));

        int ro ws   = text.len gth;
                     for (   int i =          0;  i < rows; i++) {    
            out.pri   nt(tex t[i].trim(    ));  
              out.pri     ntln();
                        out    .println();
                }

         out.flush ();
                          ou    t.close();

                view   er.showStatus("Data save to:   " + fnam  e);

        try {
                                  RandomAccess   File rf = new Ran    do       mAcces   sF  ile(ch                   oose    dFile,   "r");
                      long size = rf.len  gth  (       );
             rf                .clo    s     e       ()     ;
            viewer.showSta     tus("File        size (bytes):  " + size);
                       }
              catch (Exception         e x      ) {
           }
    }

          @Override
    pu        blic void   dispose          () {    
             i     f    (isTex tCha    nged       && !isRea    dOnl   y     ) {
               in     t op =      JOptionPane.show      ConfirmDialo    g(th  is,  "\""
                                         +     d atase  t .g       e tName() + "\" has changed.\n"
                           + "Do you want to save t  he   changes?", getT itl    e(),
                               JOption   Pane.YES_NO       _        OPTION      );
  
                 if (op        == JOptio   nPane.YE     S_O    PTION) {
                 upd            a     t  eV  alueIn  File()    ;
                }
                                        } 

                view      er.remov   eDataView(this);

        super.dispose();
              }                                    
  
                 // Implementing DataView.
                       public HObject    getDataObject() {
        return          dataset;
    }

             //         Im ple            menting Text  View.
    public S   tring[   ] getText  () {
        ret     urn te  xt;     
      }

        //        print th   e        t  able
        priva  te         void pr     int(   ) {
                     Str eamPrintServiceFactory[ ] sp sf = StreamP   rintSe     rviceFac    tor        y
                     .  loo        kupSt   reamPrintServiceFactorie    s(null, null);
        for     (int i =     0; i <   spsf.length; i++) {
                          System.  out.p         rintln(spsf[i]);
         }
             DocFlavor[         ] docF   lav   ors   = spsf[0   ].getSupporte  dD       ocFlavors()            ;
             fo    r (int i     =    0    ; i < docFla     v     ors.length; i++) {
                      System.out.println(docF  lavors[i]) ;
                     }

             // Get a tex  t DocFlavor
          I    nputStrea   m is    = n u ll;
                 t              ry {
                i  s  = ne     w BufferedIn           put Stream(new  java.io.      FileInputS  tream(
                                     "e:\\temp\\t.ht   ml   "));
         }
               catch (E         xception e     x) {       
              }     
             Doc  Flav         or     flavo r = Doc        Flavor.S TRI     NG    .T      EXT_HTML;

                   // Ge      t all av         ail   able prin   t ser vices
         Print   Serv i        c  e[] services = P      rin    tServic eLoo    kup.look                    upP   r in       tS e         rv ices(null,
                            null        );  

          // Pri  n t this  job on th  e first     pr    int  server
             Do    cPrintJob job = se       rvices[0].createPrint   Job( );
                    D    oc d    oc = new SimpleDo c( i    s,   flavor, null);

                     // Pr          i      n     t i  t
            try   {
                         job.pr  in       t ( doc, null);  
           }
             cat    c        h (E   xc            e ptio    n     ex)     {  
                   Sys te             m.out   .println(ex);
            }
          }

    p    ri    vate c  las    s TextAreaRend       er  e r   extends        JTextArea            implements
                       Ta bl      eCellR  enderer       {                 
        private  static final lo    n    g      se ri        al    Ve  rsionUID = -58    6  9     9751626    785 21978L;

              private   fina   l D      e  fault            Tab     leCellRende  rer ada   ptee = new   Defau  ltTableCell    Renderer()    ; 

           /                   ** map   fro    m t   ab le       to     map     o     f ro               ws to map of column h    eig   hts */
                                           private fin  al   Map  cel lSizes =    new   HashMap();  

                   pu         blic TextAre  aR       e   nderer   () {
                     setLineWr  a    p(tru  e);
                  se tWrapStyleW           ord(true);
                       }   

                      publi   c Co   mp   o nent            g   etTabl        eC  e      llRendererCompon       ent      (
                             //
                                            JTable table, Object obj,   bo           o      le     an i                                  sSelec   ted,  boolean ha s    Focus            ,
                       i      nt row, in t     c olumn) {
                              //   set th  e colo    urs,     et c. usin  g t     he standard f    o      r that       platfor    m      
                      adaptee.ge  tTableCel           lRendererCompon        ent(table   , obj, isSele  cted ,
                                      ha   sFo       cus, row, c ol  umn);
                 se tF   oreground(a    daptee.ge tForeground());
                          s    etBackgr     o  und(adaptee.getB       ack    ground());
                     setB    order(adaptee. getBorder());
                 setFo      nt(ada  p            tee.getFon     t()  );
                          set T ext(adap tee.ge  tText());

                             // Th is li     n  e         was very impo     rtant to         get it working with JDK1.4
                     T    ab       leCol     umnMo   del    col   um nM         ode       l  =    table.get  ColumnModel();
                        setSize(colu      mnModel.ge    tCol  umn(colu   mn).getWidth(    ), 10    0000);  
                            in     t heigh  t   _wa    n    ted =  (int) getPrefe     rredSiz   e(        ).getHeigh  t();
                          addSiz   e(table, ro   w, column,  height_w           anted);     
                 hei    ght_wanted = findTotalMaximum R     owSize    (        table, row)      ;      
            i f          (height_wanted !   = t        able.getRowHeight(ro     w      )) {
                                  table.setRowHeight(       row, hei   ght_wante  d);
                         row Header s     .setRowHe   ight(row,                  height_wa   nte     d);

                          }
                 ret     u   rn    thi  s;
                           }

        priv    ate void addS   ize (    JTa  ble tabl   e, int row    , int       column, i    nt height) {
                  M     ap rows        = (M       ap) cel    lSizes. get(table);
                 if (rows == null)     {    
                                          cell        Size       s.put(tab   le, r   ows =     new HashMap())        ;
                          }
              Map ro  whei  ghts = (Map) ro  ws  .get(new   Integer(ro    w))   ;
               i        f       (rowheight    s      =     = null) {
                  ro             ws.put(new   Integer(row),    rowheigh   ts  = new    Ha s   hMap()      );
                }
              rowheights.put   (new Integer(column), new I    nteger(heig  ht))             ;
        }

        /**
             * Look   through a    l            l columns      and get the r     ende  rer.     If it is also a
         *      TextArea     Renderer,       we look at the maximu   m he             ig    ht in  its h  ash table for         
            * this row.
                   */
        priva te int f   ind  T     otalMaximumRowS   ize(JTable tab    le, int ro         w)      {
                    int maximum_height = 0;
                   Enum     eratio     n        colum  ns        = t     able.getCo    l  umn         Model().getC      olum         n    s();
            w    h ile (colu   mns.hasMoreElements())   {
                        Ta     bleColumn tc = (Ta   bleColumn)    columns.nextElement();
                                Tabl   eCe  l         lRe     nderer cellR    en  der  er = tc   .getCellRenderer();
                                           if (cellRe    nderer instanceof TextAreaRend   erer)      {
                            TextAr   eaR      en        derer tar = (TextAreaRend  erer) ce        llRenderer;
                                           ma     ximum_height = Math.max          (m  aximum_height, tar
                                .findM          aximumRowSize(tab  le,     row));     
                            }
                    }
                            retu        rn maxi   mum_heig   ht;
            }

            priva  te i    nt fin                  dMaxim     umRo  wSize(JTable table, int      row) {
               Map rows = (Map) cellSize    s.get(table);
                       if (rows ==   nu         ll) {
                          return 0;
                 }
                Map rowheigh       ts = (Map) rows.get(  new I  nte  ger    (row))  ;
            if (rowheig    hts == nul    l)    {
                    ret urn 0   ;
               }
                  int maximum_height = 0;
               for     (Iterator it =     rowhe ights.entrySet().itera  t     or(); it.hasNex        t();)    {
                      Ma    p.Entry entry = (Map.  Entry) it.n    ext();
                         int     cellHeight    = (    (Integer) entry.getV    alue()).intV    alu  e();
                     maximum_height = Math   .max(max  imum_heig     ht, cellHe     ight);
                     }  
             r  et urn maximum_hei       gh           t;
             }
    }
            
    priva         te class TextAreaEditor extends     DefaultCellEditor {
           priv   at      e static           final long  serialVersionUID = 17216467798    92184957L;

         publi    c Text   Are     aEdi  to r(K      eyLis    tener keyLi    sten        er)       {
                supe    r(new JTextField());

             f  inal JTextArea    textArea = new JTextArea();

            textArea.     addKeyListener(keyLis     t  ener);
            textA rea.setWrapSty        leWord(true);
                     textArea.setLineWrap(true);
              JScrollPane       scroll    Pane      = new JScrollPane(textAr ea);
              scrol  lPane.setBorder(    null);
            edit  orCo    mponent = scrollPa    ne;
                      de     legate = new Defaul    tCellEditor.EditorDeleg    ate() {
                    private static final   long serialVersionUID    = 7662356    57938537316 0L;

                           @Ove      rride  
                   public    v     oid setValue(       Object va  lue) {
                                                         textArea.setText(   (value !  =     null) ?     value.toString() : ""     );
                               }   

                          @  Overri     de     
                pu   blic Object getCell     EditorValue() {
                               return textArea.getText  ();
                            }
                               };
                    }   
    }

      /**     RowHeader  d         efines the     ro   w header c   ompon ent    of the      Sp      readsheet    .    */
    private cla   s    s RowHead        e       r extends JTable {
         private static final long serialVersionUID = 2572539746584   274419L;   
           priva  te in t currentRowIndex = -1;
              private in  t lastRo  wIndex       = -1;
                    private JTa  ble parentTable;

        public Row                 Header(JTable pTable, Da         t    aset dset) {
                /   / Create a JTable with   the     same n     umbe   r of rows as
            // the  parent table and one column.
                        super(pTabl          e.getRowCount   (), 1);
    
               long[] sta   rt   Array = dset.getStartDims();
            long[] stride   Ar  ray =    dse t .getStride();
                 int[] selectedIn  dex = dset.getSele  ctedIndex();
                           int     start          = (int)  startArray[selectedIndex[0]];
            int stride = (i  nt) st  ride  Array[selectedInde   x[0]];

              // Store the parent table.
                     parentTa   ble = pTabl    e;

             // Set th        e values of the row head          ers star         ting at 0.
                    int    n =     par    entTab           le.getRowCount()    ;
            for (int i     = 0; i < n; i++)   {
                     set Va lueA     t  (ne     w Integer(start + indexBase+ i * stride), i, 0);
            }

                // Ge  t the only table   colu   mn.
                   TableColumn col = getCo   lumnModel().getColu   mn(0);

            // Use the cell rend  erer       in       th     e column.
                       col.setC         e  llRendere   r(new RowHeaderRenderer() );
                        }  

        /**      Ov      erridd      en to return false si                nce the    h   eaders are not editabl   e. */
        @Ov  erride
        pub  lic boolean   isCellEd     itable(int   row  , int col) {
            return false;
           }

           /**            This is called when the     selec tion changes in the row header    s. */
                      @Override
        public  void valueChanged(ListSelectionEvent e) {
            if (parentTabl     e == null) {
                     return;
            }

            int rows[] = getSel   e    ctedRows();
                  if ((rows == null) ||       (ro    ws.length == 0))  {
                   return   ;
                          }

            pa rentTable.clearSelection();
               parentTable.se   tR  owSelectionInterva      l(rows[0], rows[rows.length -   1]);
                              parentTable     .setColumn     SelectionInterv    al(0, parentTa      ble
                        .getColumnCo          unt() - 1);
        }

        @Override
        pro   te    cted void p   rocessMouseMotion      Ev  ent(Mou seEvent e) {
            if (e.getID() ==        Mo     u     seEvent.MOUS      E_DRAGGED) {
                   int colEnd = rowAtPoint(e.getPoi    nt());

                    if (colEnd < 0) {
                      colEnd = 0;
                    }
                                 if (currentRowIndex < 0) {
                    currentRowIn   dex = 0;
                           }

                              parentTable.clearSelection();

                             if (colEnd > currentR         owIndex) {
                               paren   tTa    ble
                            .setRowSelectionInterval(currentRowIndex, col   End);
                }
                  else {
                          p     arentTable
                               .setRowSelectionInterval(colEnd, curren tRowIndex)  ;
                      }

                   parentTable.setColumnSelectionInterval(0, parentTab l e
                                            .getColumnCount() - 1);
            }
        }      

                 @Override
        protected void processMouseEvent(MouseEvent  e) {
            int mouseI   D = e.getID()   ;

             if (mouseID   == MouseEvent.   MOUSE_CLICKED)  {
                  if     (currentRowIndex < 0) {
                      ret   urn;
                            }

                    if (e.isControlDown()) {
                         // select discont           inguous rows
                    parentTable.addRowSele ctionInterval(cur   r    entRowIndex,
                            cur    re  ntRowIndex);
                }
                    else if   (e.isShiftDown()) {
                        // select continguous columns
                       if (lastRowIndex < 0) {
                         parentTable.addRowSe  lectio   nInterval(0, currentRowI  ndex);
                    }
                    else     if (lastRowIndex < currentRowIndex) {
                            parentTable.addRowSelectionInterval(lastRowIndex,
                                    currentRowIndex);
                     }
                    else {
                        parentTable.addRowSelectionInterval(currentRowIndex,
                                     lastRowIndex);
                    }
                }
                else {
                    //     clear old sel ection and set new column selection
                          pare  ntTable.clearS   election();
                    parentTable.setRowS   electionInterval(currentRowIndex,
                            currentRowIndex);
                   }

                lastRowIndex = currentRowIndex;     

                    parentTable.setColumnSelectionI  nterval(0, parentTable
                             .getColumnCount() - 1);
              }
              else if (mouseID == MouseEvent.MOUSE_PRESSED) {
                currentRowInde      x = rowAtPoint      (e.getPoint());
            }
        }
    } // private class RowHeader extends JTable

    /**
     * Row  HeaderRenderer is a custom cell renderer that displays cells as
     * buttons.
     */
    private class RowH    eaderRenderer extends JLabel implements TableCellRen    derer {
        private static final long serialVersionUID = 3081275694689434654L;   

        public RowHeaderRenderer() {
            super      ();
            setHorizontalAlign    ment(SwingConstants.CENTER);

            setOpaque(true);
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setBackground(Color.lightGray);
        }

        /** Configures the button for the current cell, and returns it. */
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            setFont(table.getFo nt());

            if (value != null) {
                setText(value.toString());
            }

            return this;
        }
    } // private class RowHeaderRenderer extends JLabel implements
      // TableCellRenderer

}
