/*
   * Copyright (        c) 2005 (Mik  e)  Maurice Kienenberger (mkienenb@gmail.com)   
 *
 * Permiss   ion i   s her eby grante d, free   of charge, to a    n    y perso n obtaining a copy
 * of th   i  s sof    tware and associated documenta tion f  iles (the "      Softwar  e"), to deal
 *    in the   Software without restriction, including with o     ut limi     tation the rights
     *   to use,   copy, modify, merge, publ    ish, distrib  ute, sublicense, and/or sell
 * c         o  pi es of the    S      oftware, and to permit persons to wh  om     the Softwar     e             is
        * furnished to do    so,       subject            to the f  ollowing conditions:
    *
 * The above copy  righ t notice and this permission notice shall be i  nc l   uded       in
 * all            cop   ies or subs ta    ntial p     ortions of    the Software.
 *
 * THE SOFTW  ARE IS           PROVID  ED "AS IS", WITHOUT WARRAN  TY OF ANY KI     ND    , EXPRESS OR  
 * IMPLIED, INCLUDIN G BUT NOT L     IMITED TO THE WARRANTI ES   OF MERCHANTABILITY,
 * FITNE    SS   FOR A PAR    TICULAR PURPOS         E AND    NONINF  RINGEMENT. IN NO EV    ENT SHALL THE
 * AUTHOR    S OR     CO PYRIGHT HOLDERS BE LIABLE    FOR ANY C  LAIM, DAMAGES OR OTHER
     * LIABIL    ITY, WHETHER IN AN ACTION     OF CONTR ACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CON   NECTION WITH THE SOFTWARE OR THE USE OR OTHER DE     ALINGS IN THE
 * SOFTWARE.
 */
package     org.gamenet.application.mm8leveleditor.dataSectionable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
i    mport java.beans.PropertyChang      eEvent;
import java.beans.PropertyC        hang      eListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import   javax.swing.BoxLayout;
i      mport javax.swing.JFormattedTextField;
import     javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.gamenet.application.mm8leveleditor.control.OutdoorFaceControl;
import org.gamenet.application.mm8leveleditor.control.VertexControl;
import       org.gamenet.application.mm8leveleditor.data.mm6.outdoor.D3Object;
im   port org.gamenet.application.mm8leveleditor.data.mm6.outdoor.IntVertex;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.OutdoorFace;
import org.gamenet.swing.controls.ComparativeTableControl;
im  po    rt   org.gamenet.swing.controls.DataSecti   on;
import org.  gamenet.swing.controls.DataSectionable;
import o      rg.gamenet.swing.    controls.Ver tex3DT    extFieldPanel;
im     p       ort org.gamen    et.s  wing.controls.Vertex3DVa lueHolde         r;
   import org.gamenet.util.TaskObserver;

public class D3ObjectDataSection   a   ble ex   tends Ba     seD ataSect       i   onab        le implemen t    s D       at       aS   ectionable
{
     private         D3Object d   3Object         =   nul   l;
                             
      p    ublic D3 O     bjectD       ata      Sectionable(D3Object sr  cD3Objec          t)
       {
             super();
        
                       this.d3        Object = srcD3Object    ;
    }

    public Object get  Data()
     {
           return d3Object;
    }

      public Compon       ent getI    nfoPanel(TaskObserver taskObserver)     throws Int   erruptedException
    {
               taskObserver.taskProgress("   Info", 0.1f);
        if   (Thread.currentThrea      d().isInterrupted())
            throw     new In   terruptedException ("ge             tI    n     f   oPanel() was int  errupted.");

        J  Panel i   nfoPanel = new JPanel();
             info   Panel.setLa  yout(   new BoxLay   out   (infoPanel, BoxLayout.Y   _AXIS));
                           infoPanel.setBorder(new BevelBorder(BevelBord  er.L  OWERED));   

        JForm     at    tedTextField name1TextField = ne     w JFormatt  ed   TextField(d3Object   .g etN     ame    1())          ;
           name1TextField.setColumns(3                  2); 
                     JF    orm attedTextField  nam   e2T    extField = new JFor    ma          tte  dT    e       x     tF   ield   (d3Object.getName  2())     ;
                  name2TextField.s  e     tColumns(36)          ;
      
                                     name1T  extFiel d.a      ddPropertyChangeL      ist  ener("v   alue", ne  w Pro            pertyChang     eListener      ()
                               {
                                   p                  ub        lic void pro          pertyChange(Pro  pertyChange       E    vent e)
                                            {
                                          d3Obje     ct.setNam  e1((String)   ((JFormattedTextField)e           .getSour      c  e()).getValue             ())      ;
                                                      }
                                      });
                     
                   name2TextField.  add    Pr opertyChan   geLi      stener("val    ue", n   ew Pro    pe                      rt  y      ChangeL istener()
                       {
                               public vo     id propertyCh    a   nge   (Prope   rtyChang   eEvent e)
                                                        {
                             d3Object.setName2((Strin         g)((J          FormattedTex tField   )e.getSource()).getValue  ()   );
                                 }
                }    );

              // Lay o  ut the labels in a panel.
                  J   Panel labelPane = new JPanel(new G  ridLayout(0,1));
        labelPane.add(new JLabe            l  ("Name1: "));
                    labelPane.add(ne    w JLabel("Na   me2: ") );  
        labelP   ane.add(new        JLabel("Offset: "));
              labe    lPane.add(new       JLabel("# o   f v  ertexes: ")   );
        l      abel  Pane.ad   d( new JL   abel("V         ertexes offs     et: "));
        label   Pane.add(   new JL    abel( "# of faces: "));
        labelPane.add         (new JLabel("Faces offset: "));

        //L ayout the text     fields in a panel.    
        JPanel fie ldPane   = new JPanel(    new     GridLayout(0,1));
                fieldPan  e.add(name1TextField);
                    field  Pan              e.add(name2TextFie     ld);
         fi   eldPane.add(new JLabel(String .      val    ueOf(d3Object.getOffs      et())))  ;
             fieldPane.a   dd(  new JLabel(String.valueOf(d3Object.g  etV    er    texList  ().s      ize())))     ;
                   fieldPane.add  (new JLabel      (  String.val ueOf(d  3Object.ge    tVer   te xesO  f     f    set())) )  ;
          fieldP ane.add  (new    JLabel(String.valueOf(d3Object  .getF   ace                        tList() .s       i  ze()  ) ));
              fi           eldPane.a       dd(new JLabe  l(String.valueOf(d3     Object. getFa   ce   sOffset()          )))  ;

                        //Put the pane  ls in this p  anel,   labels o    n lef  t,
                           //t ext fields on right. 
               J         P    anel labelAndFieldPanel =      new JPane l(new BorderLayout     ());
             lab    elAndF        i eldPanel.setBorder(B  order                Factory.cre              ateEmpt    yBor   d    er(2   0, 20, 20, 20));
                                labelAnd               Field    Panel.add(   labelPan   e,    Bor           derL a    yout.CE    NTER );
            la   b       elAn dField     Panel.ad     d(     fie    ldPane, BorderLa  yo  ut.LINE_END);

                     JPa nel             wrapping  Lab   elAndFieldP ane      l =        ne           w JPane    l(new FlowLayout(Fl    owL ayo   ut.                         LEF      T));
            wr     appingLabel   An      dFi     el dPanel.add(labelA  n  dFi eldPanel)  ;
               
                    infoPa  ne    l.add(wrappi    n       gLab   el       A          ndFi e  ldPa    nel)  ;

                             JPa    nel v  ertex                    M i      nAttribut     eP     anel = new JP   ane  l       (new FlowLayout(     FlowLayo ut.LEFT));
            v  ertexMinAt   tribut   eP an   e   l.add(new      JLabel("IntV  erte    x M     inimum (   "));
	    
               v             ertexMinA    ttribut   ePanel.a                   dd(n ew Vertex       3DTextField          Panel(        ne    w Vertex3       DVal   u  e  Hold    e  r()
          { 
               p     ublic      int getX()  
                {
                      r etu      rn d     3Ob      ject.g   e     tXMin()     ;
              }

            pub  lic void set    X   (in    t      x)
               {
                                    d3Ob    ject     .setX   Min(x);
                                                     }

                                   pu  b                  lic int   getY()
                                         {
                         return d3Obj  ect.ge       tY  Min();
             }

                                 public void setY(int y )
                   {
                     d3Obj  ect    .    setYMin(y)                        ;           
                         }

                                 public          int getZ()
                {
                   return    d3Obje   ct.getZ   Min();
               }   

               publi         c void setZ(  int z)
            {
                                    d3O   bject.setZMin(      z);
             }          
        })    );

        JFormattedText Fie ld xM inText  Field = new JFormattedTextF     i    e      ld(ne w Integer(d3Objec      t.getXM     in()));
        xMinTe  x  tFiel d.s        etCo lumns(5);
               xMi  nT  extField.      addPropertyChangeListener("va       lue", new Pro    pertyCha   ngeList     ene    r()
                              {
                       public void propertyCh      an g    e(Proper    tyChangeEvent e)
                    {
                             d3Obje  ct.setXMin((      ( Number)((JFormat   tedTextField)e .getSou    rce(    )).getValue()).intV  a   lue()   );
                         }
                            });
            vertexMin Attr   ibute  Panel.add(xMinTextF iel     d)           ;
         vertexM      inAttributePanel. add(new JLabel(","));

        J    Fo     rmatt  edTe      xtFi      eld yMinT         extField        = new JForma    ttedTextField   (   new Integer(d3Objec t.getYMin(   )));
        yMinTextFi   eld.setColumns(5);
        yMinTextField.    addPropertyChan   geListener("va    lu   e", new Pr  opertyChangeListener()   
                {      
                             public void property    C hange(PropertyChangeEvent e)
                         {
                                     d3Object      .set     YMin(((Num    ber)((J   FormattedText      Field)    e.g etSource()).  getValue    ()).intValue());
                           }
                             });
        vert   exM    inAttributePanel.add(yMinTextField);
         vertexMinAttributePanel  .ad        d(new JLab el(","));

        JFormat    tedTextField heightMinTextField = new JF    ormat tedTextField(new Integer(d3Ob      ject.getZMin()));
             heightMinTextField.      se   tC olu  mns(        3);
        heightMinTex   t   Field.addPropertyChangeListener("va  lue", new Pro pertyChange    Listener()
                {
                            public voi d      propertyChange (PropertyChan geEvent e)
                      {
                               d3Obje ct.set  ZMin(((Number)((JFor     m    att  edTextField)e.getSour   ce()   ).    ge          tValue()).intVal    ue( )  );
                                   }
                    });
            vertexMi nAttributePanel.add(heig  htMinTextF ie  ld);  
        v      ertexMinA       t      t     ribut  ePanel.add(new JLabe     l("   )"           ));
	                		    
         infoPanel.  add(ver     te      xMinAttributePa   nel);
         
          return infoPanel;
       }

    prote  cted Comp     onent   get  UnknownsPanel(       TaskObse     r   ver     taskObser    ver ) throws InterruptedException
    {
           J    Panel un  know  nsPanel = new JPanel  ();
        unk  nownsP   anel.se   tLa  yout(new   BoxLayout(unknownsPanel, Box Lay             out.Y    _AXIS) );
        unknowns Panel.setBorder(new BevelBorder(B      ev       elBorder.LOWERE     D));

           fi         nal Compar  ativeTa          bleCon trol     dataComparativeByteData    Tabl       eControl;
        da  taComparativeByteDa     taTableCon   t     rol = n            ew Compa  rativeTableControl(D3Ob ject.getOffset    List(),
                D3  Object.ge tComparativeDataSource(Col     lections.singletonList      (d   3Object)));    

             unknownsPanel.add(makeNonStr       etch  edPan   e   lF  or        (new JLabel(" D3Object Un  known s: ")));
            unknownsPanel.add   (dataCo      mpa   rati   veByte  Da   taTableCo nt   rol);
		
        retur  n unkn     ownsPanel;
    }

        public IntVertex createN  ew     Vert ex()
      {
                    int x = 0;
        int   y = 0;
          in   t z =   0;
          return new     IntVertex( x, y, z );
    }
    
    public OutdoorFace c          re    ateNewFa  ce()   
    {
             short ordering =      0;
        String bit      map   Name   =     "";   
                             ret   urn ne           w    Outd       oorFace(ordering, bit     mapName);
    }
            
    public Component get    Compon    entForDat     aSection(   TaskObserver taskObse   rver, String dataSectionN      a         me) throws Interr      uptedExce  ption
    { 
        if (DATA_SECT     ION_INFO == dataS  ectionName) { r    etu        rn getInf  oPane  l(taskObserver); }
                else if (D  A    TA_SECTION_UNKNOWNS   == dataSectionName) { return getUn     know   nsPanel(t  a     skObse    rve     r); }
           return s      upe  r  .ge tComponent(dataSectionName, taskOb server);
       }

         public     Co  mponent getListC omponent    ForDataSection(TaskObserver taskObser    v        er   , String dataSec   tionName, List list  , Iterator ind      exIterato    r) throws Interrupt edExcepti  o n
    {
          ret   urn super.getListComponent(dataSectionName,    taskObserver,    l   ist,        indexIterator);
             }
    
    pu   blic static final St      ring DATA_     SECTION_IN    FO = "Info";      
    public      static final Strin   g           DATA_S ECT      ION_VERTEXES  = "Ver      texes";
    public static final      String DATA_SEC TI     ON_FACES =  "Faces";
        pu                blic static final String      DATA_SECTION_UNKNOWNS = "Unknowns";

    public static Da  taSectio n[] getDataSection     s()
    {
        return new DataSection[] {
                    new DataSe   ction(DATA_SECTION_INFO),
                new  Dat    aS  ec  tion(DATA_SEC     TION_VE                RTEXES, In   t Vertex.cla   ss, O    utdoorVer   texD   ataSectionable.class),
                              new DataSection(DATA_SECTION_FACES, OutdoorFa ce.class, OutdoorFaceDataSection a ble.class),
                              new DataSection(D   ATA_SECTION_UNKNOWNS)         
         };
       }
    
    public    DataSection[] getStaticDataSections() { return ge        tDat      aSections(); }

    publi   c      Object  getDataF      orDataSection(DataSection dataSe     ction)
    {
        if (DAT   A_SECTION_INFO == data  Sectio n.getDataSectionName(    ))
        {
                 retu  rn null;
        }
        else if (DATA_ SECTION_VERTEXES == dataSect ion.getDataSectionName())
        {
               return d   3Object.getVerte xList();
        }
              else if       (DA  TA_SECTION_FACES ==       dataSection.g  etDataSectionName())
               {
                     return d3O bject.ge   tFacetList();
                    }
        else if (DATA_SEC         TION_UN     KNOWNS == dataSection.getDataS       ectionName())
        {
            return d3Object.getRemainingDa    ta();      
          }     
        else throw n       ew IllegalStateExcept ion    ("No dat  a secti   ons: " + dat    aSecti        on);
    }

    pub   lic DataTypeInfo   getDataTypeInfo(String dataSectionName)
      {
        if      (dataSe ctionName == DATA_SECTION_VERTEXES) { return   vertexDataTypeInfo; }
        if (dataSectionName == DATA_SECT     ION_     FACES) { return facetD   ataTypeInfo; }
        else throw new IllegalStateException("No data sections: " + dataSectionName);
          }
    
    private DataTypeIn    fo vertexDataTypeInfo = new AbstractDataTypeI   nfo() {
        public String getDataTypeNameSingular() { return "Vertex"; }
        public St      ring getDataTypeNamePlural() 	{ return "Vertexes"; }
        public List getDataList() 				{ return d3Objec  t.getVertexList         (); }
        public List getOffse   tList() 			{ return null; }
        p  ublic Comparative   TableControl.Da   taSource getComparat   iveDataSource() {
              						  			return null; }
        public Component getDataControl(int dataIndex) {
            									return ne     w Vert    exControl((IntVert ex)get    DataList().get(dataInde   x)); }
        public void ad     dDataAtIndexF  romComponent(int index   , Component component) {
            									getDataList().ad  d(index, ((VertexControl)component).getVertex()); }
         public Component createNewDataControl() {
             									return new VertexControl(createNewVertex()); }
    };
    
    private DataTypeInfo fac   etDataTypeInfo = new AbstractDataT  ypeIn   fo() {
        pub lic String getDataTypeName     Singular() { return "Facet"; }
        public Strin  g   getDataTypeNamePlural() 	{ return "Facets"; }
        public List getDat  aList() 				{ return d3Object.getFacetList(); }
        public List getOffsetList() 			{ return OutdoorFace.getOffsetList(); }
            public ComparativeTableControl.DataSour ce getComparativeDataSource() {
            									return OutdoorFace.getComparativeDataSource(getDataList()); }
        public Component getDataControl(int   dataIndex) {
            									return new OutdoorFaceControl((OutdoorFace)getDataList().get(dataIndex)); }
        public void addDataAtIndexFromComponent(int index, Component component) {
            									getDataList().add(index, ((OutdoorFaceControl)component).getFace()); }
        public Component createNewDataControl() {
            									return new OutdoorFaceControl(createNewFace()); }
    };
    
}
