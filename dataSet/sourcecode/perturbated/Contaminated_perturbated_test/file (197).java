/*
    * Copyright   ( c) 2005   (Mike) Maurice Kienenberger (mkienenb@gmail.com  )
 *
 *   Permis     sion is her      eby grant     ed, free of charge, to any person obtain   in  g a copy
 *   of this so ftwar e and associated documentation files (the "Softw     are"), to deal
 * in the Soft  ware without restriction, including    wi          thout limitation the r     ig  hts
 * to us             e   , copy, modify,     merge, publish, distri     bute  , sublicense, and/or sell
   * copies   of the Sof   twa   re          , and    to    permit persons to whom the Software is
 * furnished to do so, subject to the following   conditions:
 *
 * The above copyri  gh        t  notice and       this            permission notice shall be include d           in
 * all copie s o  r s   ubstantial portions of the Sof   tware.
 *
 *     THE SOFTWARE IS PROVID   ED "A S IS", W  IT      HO UT WARRANTY OF ANY KIND, EXPRESS OR
 * IM     PL     IED,   INCLUD  ING BUT NOT LIMITED   TO THE WARRANTIES OF         MERCHANTABILITY,    
 * FITNESS FOR A PARTICULA R PURPOSE AN  D NONINFRINGEMENT.    IN NO     EVENT SHALL THE
 * AUTHORS OR COPYRIGHT    HOLDERS B  E   LIA           BLE         FOR    ANY  CLAIM, DAMAGES OR OTHER
     * LIABILIT   Y, WHETHER IN AN   ACTION OF CO NTRACT, TORT OR OTHERWISE, ARISING FR   OM,
 * OUT OF OR IN CONNECTION WIT    H THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.gamenet.application.mm8leveleditor.control;

import java.awt.   BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;    
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java   .beans.PropertyChangeListener;
im  port java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.annotation.gui.CollapsablePanel;
import or      g.gamenet.application.mm8leveledit    or.data.mm6.outdoor.D3Object;
import org.gamenet.application.mm8leveled   itor.data.mm6.outdoor.OutdoorFace;
import org.gamenet.application.mm8leveleditor.data.mm6.outdoor.IntVertex;
import org.gamenet.swing.contro ls.ComparativeTableControl;
impo  r   t org.gamene  t.swing.controls.       ComponentArr    ayPanel;
    im     port org.gamenet.swing.controls.Vertex      3DV  a lueHol     der;
import org.gamenet          .swing.controls.Vertex3DTextFi     e   ldPanel;
    
public    clas   s D3ObjectContro  l extends       JPan   el   
{
         private D3O  bject d3Object    = nu   ll;
    
    public D3ObjectControl( D3Object src        D3   Objec   t)
    {
        super();
        this.setLayout(ne     w BoxLayout (this, BoxLayo  ut.Y_AXIS));
            this.setBorde     r(ne     w BevelBord    er    (Bev  elBorder.LOW     ERED))   ;

        this.d3Object = srcD3Object;


        JFormattedTextFi    eld nam      e1Te      x tField = new JFormattedText   Field(d3  Object.getN     ame1());   
           nam   e1Tex      tFiel   d.     setCo    l       umns(32);
           JForm  at    t ed    T  e  xtFi  eld      na     me2TextField =              new JFormattedText     Field(d3Obj     ect.getName2(       ));
        name2T       extFi  eld.   setColum   ns( 36);
                           
            nam          e1TextField      .ad dP    rop  ertyC           h    angeListene   r("value             ", new PropertyChangeL   istener()
                                        {
                                                                                      pu      blic        voi      d propertyChange(Pr     opert     yChan  geEv  ent e)
                                {
                                               d3Obj   ect.setNam e1((Strin  g)((JForm   attedText   Field)e.g   etSource()).getValue());
                                                }
                });
                  
          name2T  ext  Field.a    ddProp   ert            yChangeListene r("value", new PropertyChangeL    i                   stener()
                   {
                                public void   proper   tyCha nge(Proper      tyCha   ngeEvent e)  
                          {
                                       d    3Object.setName2((Str   ing)   ((JFormattedTe  xtField)e.getSource()).g    etV            alu    e   ());
                                    }  
                        })    ;

          //Lay out th      e lab    els in a          panel       .
        JPa nel     labelPane = new JPanel(new          GridLayout(0,1));
        labelPa    ne.add(   new JLabel(   "Na me1: "    ));
        lab   elPane.add(new       JLabe  l("Name2: "));
                            labelPane.add(new JLabel("Of       fset: "));
          labelPane.add(new JLabel("# of   vertexes:     "));
        labelPan e.add(n    ew JLabe   l("Vertexe   s off       set: "));
        labelPa    ne.add(new JLabel("#    of faces: "));  
        labelPan  e.ad  d(new       JLabel("Faces offset: "));

         //Layout the text fields in a panel.
          JPanel fie   ldP ane = new JPa     nel(new GridLayou  t(0,1));
                          fiel  dPane.a      dd( name1TextField);
                       fieldP            ane.ad   d(name2TextField);
        fieldPane.add(new            JLabel(Strin       g.v alueOf(       d3Object.g    etOffset())));
        fieldPa  ne.add(new     JLabel(String.v      alueOf(d3    Object.getVertexList().size(  ))));
                    fieldPa  ne.add(n e w J      Label(   Str ing.value Of   (d3Object.getVertex  es  Offs         e   t       ())));
             fi      eldPane.add(n ew JLabel(String.v   alueOf(d3Objec    t.g       e   tFacet     List().size()))) ;
               fieldPane.add(n ew JL    a      bel      (    String.          valueOf(d3 Object.getFacesO    f fset()   )       ) )    ;

             //Put the pane        ls   in              th       is   panel,  l    a        bels on  left,
               //text fields  on  r         ight. 
            JPanel labelAndFieldPanel = new JP        anel(new    Bo     rderLayout());
               labelAndF         ieldPan    el.setBorder(Borde  rF       actory.cre     ate   EmptyBo  rde  r(20,  2            0,     20,   20));
        l  abelAndField Panel.add(labelPane, BorderLayou   t.CENTER)    ;
                  lab     elAndFieldPanel.    add(           fieldPane,    Borde   rLayout.L    INE_END);

              JPanel wra                      ppingLabe  lA   ndF  ie  ldPanel = new J  Panel(new F     lowLay        out(Fl      owLayou   t  .LEFT)   );
               wr    app  ingLabel   AndFi    e        ldP  a      nel.          a dd(        labelAndFieldPane    l);
                   
	   	th          is.   add(    wrappingLabel  AndFieldPanel) ;

                     JPane    l ve  rtexMi        nAttr       i  butePanel = new JPanel(new FlowLayout(FlowL      ayo     ut.L  EFT));   
        vertexMinAt    tributePanel.ad d(new JLa   bel("I  n tVer  tex Mi        nimum ( "))  ;
 	    
          ver             texMinAttri    buteP ane        l.           add(n e     w Ver          tex3  DTex  t  Fie       ldPanel   (new Vertex3DValueH older()    
                    {    
                          p  u       blic    int get   X()
                                 {
                                     ret    u rn d3O    b   ject.getXM      in(); 
                 }  
    
                                     pu b    lic  vo id         se tX(in  t x)
                      {
                          d3    Object.setXM     i   n(x);
               }

                 p  ub    l   ic int getY()
                     {
                   re    tur    n d3Ob     ject.getYMin();
                               }

                       pu  blic   void   setY(int y)
                                     {
                          d3 Object.s         etYM       in(   y   );
                      }

                         public int getZ()
                 {
                           return d3Object.get     ZM  i  n();
            }        

               pub        lic  void setZ    (in      t z)    
                {
                d3Object. s        etZMin(z);
                                }
        }));

           JFo        rm  atted   TextFiel d xMinTextFiel     d   = ne  w JFor   matted    Te             xtField(new Integ                 er(d3     Obj    ec   t.getXM   i  n()));
                        xMinTextFi   eld     .setColumns(5);
                xMinTextField        .      addPropertyChan           geListener("val  ue" , new PropertyCh       ang     eLis   tener()
                              {
                            public      voi             d pro      per   tyC   hange(Pr  opertyChangeEvent   e)
                                                        {
                              d3Object.setXMin ((    (Numb           e  r)((JForm   atte       dTex  tField)e.getSource()).getV a     lue( )).  int Value());
                                  }
                                       });
        ver texMinAttri    butePan  el.       ad  d(xMinTextF   ield);
        v     ertexM   inAttributePanel       .add(new      JLab    el("   ,"));

                        J   Formatted    TextField y M inTextField = new JFormatt  edTextField(new I   nteg er(d3Object. getYM in()   ));   
                    yMin   Tex tFie       ld.set  Co  l     umns(5);
        yM               inT     extFi  eld.a  ddPro    perty       ChangeListener( "v  alue",   new   Prope     rtyChangeLi s    tener()
                                                    {
                                                     public v          oi d pro  pe          rtyChange(P      rope    rtyCha  ngeEvent e   )
                                       {
                                   d3Ob           ject.setYM   in(((       Number)((JFo     rmatte          d    Te  xtField    )e.g  e tSource     ()).g   e  tVa     lu  e(     )).int      V  alue());
                      }
                         });
           vertex MinA ttri  buteP           a     nel.add               (y MinTextField);
        vertex    Min Attribut      ePanel.add(n   e               w        J  Lab                 el(    ","  ));

                       JFormattedTe       xtFiel  d      heightMinTextField = new JForma   tte      dTe xtField(new Integer(d     3O  b       jec                     t.getZMi    n()));
          h   ei     g                htMi   nTextF      ield.  se       tColumns(3);
                              height        MinTextFi   eld.a   d    dPropertyCha  ngeListener("value"  ,           new Proper   t  yChangeList  ener    ()
                                                  {
                              public                    void prop  ert      yChange         (Pr    op     ertyCha    ng  eEven t e)
                                               {
                                d3O  bject.setZMin(((Numbe r            )    ((JFormatt    edTextFi    eld )e.    g        etS  ource  ()).      g             et    Val    ue( )).intValue());
                                                       }
                       });
                       vertexMinAt  t  ribut   e  Panel      .add   (he   igh        t      MinTe xtFie        ld);
                      vert       exM   inAttribu                t       ePan        el.ad             d(new J   Lab   el(")"));
     	                 		    
	    thi              s    .  a dd(    vertex      MinAtt     r           i  butePanel);

	       CollapsablePan   el.Indirect   Dat aSource    v         er   texesI    nd i       rect          DataSource = new    C            ollapsabl       ePanel.Indir   ect DataS     ource(   )
                       {
	             public Compo     ne   nt getC    ompone   nt(     )
                {
                       t   r    y
                                                      {
                                                final   L           ist vertexLis   t = d3Obje ct .getVertexLis         t();

                                          J     Panel verte      x    esPane   l     = n e w JPanel();
                        vertexesPanel.s      et  Layout(new Box L ay  out(     v                 ertexes    P   ane   l, BoxLay    out.         Y_      AXIS));
                             fi nal JLabel   vert    e  xC    ount  L  abel = ne  w    J      La   bel("# o   f V er    texs   : " + St   ring.va  l    ueOf (vert        ex   List.s     ize())               );      
                                verte   xesPanel.ad              d(makeNonStr    et chedPan            el     F    o                        r(vert     exCountLa              bel));
    
                                          List vertexControlList =     new ArrayList( vertexList.si  ze())      ;
                                             for (int  v erte   xIn           d    e     x =   0; v     e     r  te x    Inde   x < vertexList.size    ();   +     +v  ertexIndex   )
                            {
                                         IntVertex ve   rtex = (    IntV er        tex)              vertexLi                     st.  get(   vertexIndex);
                                       v  ertex   C      ontrol    List.add(new   Ver    texCon    t r      ol(ve    rtex));
                                   }
                                vertexesPa       nel     .a             dd(mak        eNonStre  t      chedPane  lF              or(    new Component  Arra  yPa   nel(ve          r    texCont    rolList,     n   ew      Comp    on en     tArray  P   anel   .Comp                onent   Da    taSour  ce()
                                                   {
                                                             public Comp   onent create      Co   mpone   nt       (          int compon      entIndex)
                                                          {
                                                                                                                   IntVer   te         x newV       ertex   = cr     ea     te  NewVe        rtex(   );
                                                              return n  ew      VertexControl(    n     ewV   erte   x);
                                                       }
                                                                     
                                                                 p    ublic        void fireCo                     mponen  tAdded(i     nt c     o         m    po           nentIn     dex, Compo  nent     compon   ent)
                                           {
                                                                                                        Vertex    Con   tro  l verte   xCo  ntrol        = (VertexCon  trol)co     mp one    nt;
                                                                vert     exLi       st.                    a dd(    compon     entIn         dex,        v           e  rtexC    on  trol.getVertex          ());
                                                                                                          ve    rtex    Count  Labe  l.set       Text("# of Vertex   s : "  +    Str   in  g.   valu     eOf(verte   xList.si   ze()));
                                                                   }
                                                                    pu  blic        voi d fire      ComponentDeleted(int c    o   mponentIndex   , Com ponent        co    mpon     ent)
                                                                                { 
                                             VertexCon trol ve r texC    ontrol  = (     VertexControl)  c ompon    ent;
                                                   vert exLis    t.remo                          ve(c omponentInde  x   )        ;
                                                                vertexCo untL ab         el. set          Text(    "#  of       Vertexs: "   + String.va     l  ueOf(vertexList.size())        );
                                                                                        }
                                                                      p  ublic  v oid fir        eCo   mponentMov       edUp(i      nt          c  omp  onentInd       ex,  Com  ponen  t component)   
                                                                    {
                                                         IntVertex   ve r tex = (  In   tVertex)ve  r   texL   i st.rem  ove(c          ompo   nent   I       nd e       x); 
                                                            ve   rtexLis       t.   add(c     o    mpon       entInd    ex - 1, v      er              tex);    
                                    }
                                                                    public void    fi      reCo   mponentMoved   Down(in              t componentInde    x, Co    mpone  nt c    omponent)
                                                         {
                                                   IntV    ertex vertex   = (IntVert             ex)vertexList.remove(compone nt       Ind    e   x);
                                                                              v          erte      xList   .add(comp onentIndex +    1, v  ert    e  x) ;
                                        }
                                       })));
                           
                                 return vertex esP  a   nel;
                       }
                             cat ch   (Interrupted         Exc   eption   ex   c       eption)
                          {
                                              //     IMP         L   EMENT  : replace this wit  h        a pro     gres     s monitor
                             except         ion. print    StackTr  a    ce();
                        
                               r    eturn        new JLabe  l("             Error: U    ser cancelled oper     a   tion.  ");
                     }
                             }
               };
	    this.add(make   No    nStretchedPanelFo   r(n     ew CollapsablePanel("Ve       rtexes  "   ,      vertexe   sInd     ire    ctDataSour    ce, tru   e)));
  	    

	      Collapsab  leP     an   el .Indirec  tDataSource facesIndirect   DataSo          urce = new C            ollapsa     blePa   nel.IndirectDataSource()
        {
                        pu       bl ic   C      om  pon  ent getComp   o     n  ent()
            {       
                               try
                        {
                               final Lis   t faceL            ist =    d3Obje      ct.getF    ac     etLis  t      ();

                                                                   JPanel facesPa   nel = new JPane    l();   
                          facesPanel.setLa   yout(new Box  Layout(facesPan  el,   BoxLayout.Y    _AXIS));
                    final J        Labe       l faceCountL  abel      = new  JLabel("# of Fa     ces: "                + Str   ing.valueOf(face   List    .size()));          
                                 face  sPanel.add    (make NonStretchedPanelFor( faceC    ountLab  el));

                              Lis t OutdoorFaceCont      rol  Lis  t  = new    ArrayList(fac     eLi   st.size());
                           for (int   f   aceInde                x =                              0;    fa              ceInde  x < faceList   .size();        ++faceIn dex)
                      {
                                    Ou   t      door      Face face = (Ou tdoorFace)faceList.   get(faceIn   dex);
                                O           utdoorFaceControlL ist.ad  d(   ne w OutdoorFaceCo      nt rol(fac e));
                              }
                             facesPanel.add(    makeNonS   tre      t   c   h edPanelFo   r(    new Compone         ntArrayPanel(           Outd               oorFaceCo    ntrolList,                     new Compo     nen     tArrayPanel.ComponentDataSource()
                                   {
                                     public Compon    ent createC    omponent(int componentIndex)
                                             {
                                     Ou    tdoorFa   ce    newF  ace    = cre     ateNe wFac     e();
                                              retu       rn new OutdoorFaceCo    ntrol(new      Fa     c  e);
                                                }
                                   
                                                     pu    bl  ic           void fireComp          onentAdded(   int componentInde             x, Co    mponent com  ponent)
                                    {  
                                        OutdoorFa     ceControl OutdoorFaceControl = (OutdoorFaceContr           ol)component;    
                                                            f aceList.add(componentIndex, Outdoor   Fa  ceControl.getFace());
                                                      fa ceC     ountLabel.setText("# of Faces: " +     String.val        ueOf(f  a  ceList.s   ize()));
                                      }
                                       public void f  ireCompo     nentDeleted(int componentIndex, Co      mponent component    )
                                        {
                                    Outd  oorFaceCon     trol OutdoorFaceControl   = (OutdoorFac    eCon trol)compo     nent;
                                                faceList.remove(componentIndex       );
                                            faceCountLab el.setText("# of Faces: " + String.valueOf(f    aceList.size()));
                                               }
                                    pub    lic void fi  r      eComponentMovedUp   (int comp  onentIn  dex, Component compon  e       nt)
                                             {
                                    OutdoorFace f  ace = (OutdoorFace)faceList.r  emove(co   mponentInd ex);
                                       faceLis    t.add(componentIndex - 1, face);
                                          }
                                           public void fireComponentMovedDown(int componentIndex, Compo nent component)
                                         {
                                    OutdoorFace face = (OutdoorFace)faceList.remove(comp         onent Index);
                                       faceList.add(componentIndex + 1, face);
                                       }
                            })));
                    
              		ComparativeTableControl fac   etCBDT C = new ComparativeTableControl(OutdoorFace.getOffs   et        List(), OutdoorFace.getCompara   tiveDataSource(faceList));
             		facesPanel.add(facetCBDTC); 

            		return facesPanel;
                }
                catch (InterruptedException exception)
                {
                    // IMPLEMENT: replace this with a    progress monitor
                    exception.printStackTrace();
                    
                    return new JLabel("Error: User cancelled operation.");
                      }
            }
        };	    
	    this.add(makeNonStretchedPanelFor(new CollapsablePanel("Faces", facesIndirectDataSource, true)));

    }

    public Object getD3Object()
    {
        return d3Object;
    }

    public IntVertex createNewVe rtex()
    {
        int x = 0;
        int y = 0;
        int z = 0;
        return new IntVertex(x, y, z);
    }
    
    public OutdoorFace createNewFace()
    {
        short ordering = 0;
        String bitmapName   = "";
        return new OutdoorFace(ordering, bitmapName);
    }
    
    protected JPanel makeNonStretchedPanelFor(Component component)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        if (null != component)  panel.add(component);
        return panel;
    }
}
