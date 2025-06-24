package  pointGroups.gui;

import   java.awt.BorderLayout;
imp   ort java.awt.Component;
imp  ort java.awt.Container;
import     java.awt.FlowLayout;
     import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.Actio nListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
im port java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Array  List;
import java.util.List; 
import java.ut  il.Random;

import java x.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinate3DPointEvent;
import pointGroups.gui.event.types.ChangeCoordinate3DPointHandler;
import pointGroups.gui.event.types.ChangeCoordinate4DPointEvent;    
import pointGroups.gui.event.types.ChangeCoor   dinate4DPointHandler;
import pointGroups.gui.event.types.DimensionSwitchE    vent;
import pointGroups.gui.event.types.DimensionSwitchHandler;
import   pointGroups.gui.event.types.ScaleFundamentalDomainEvent;
import pointGroups.gui.event.types.ScaleFundamentalDomainHandler;
import   pointGroups.gui.event.types.Schlegel3DComputeEvent;
import  pointGroups.gui.event.types.Sc  hlegel4DComputeEvent;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseHandler;
import pointGroups.util.jreality.JRealityUtility;
import pointGroups.util.point.PointUtil;


/**
   * @author Oliver, Nadja
 */
   public class Coor      dinateView
  extend s JP        anel
  implements ActionListener    , Symmetry3DChooseHandler, Symmetry4DChooseHandler,
  DimensionSwitchHandler, ChangeCoordinat    e3DPointHand  ler,
  ChangeCoordinate4DPointHandle  r    , ScaleFundam entalDom     ainHandler
{
  /**
	 * 
	 */
  p    rivate stat ic final lo      ng       serialVersionUID = -3113   107578779994265L;
  private final Dimensio                n  in     putFie   ld  inputFie   ld;
  private final JButton run;
  private final JButton randomCoord;    
  protected final EventDi    spatcher di   spatcher;

  private Symmetry3DC     hoos eEven     t l      astSymmetry   3DChoos         eEvent;
  priva    te Symmetry4DChooseEvent    lastSymmetry4DChoos    e    Event;

  private double[] lastCoords =     n   ew double[ ]{0.0, 0.0, 0.0};
  
  public CoordinateView(int dim      ension, int maxD   i     mension, EventD   isp  atc     her di     spatcher) {
    th    is.  setLayout(new Border   Layout());
      in     putFi   eld = new Dimens  ioninputField(dimension, max Dimension);
    run = n  ew  JButton("Run"    );
    randomCoord = new JButton("Crea    te random coordinate"   );
    this.dispat   cher = dispatcher;

    this.add(inputField, BorderLayou  t.PAGE_START);   
    this.add       (run,BorderLayout     .LINE_   END  );
    this.add(randomCoord, Bor derLayout.CEN  TER);

    dispat cher.addHandler (DimensionSwi t  chHandler.class, this);
    dispatcher.addHandler(ChangeCoordinate   3DPointHandler.class, this);
    dispatcher.addHandler(ChangeCoordinate4DPointHandler.class, this);
       dispatcher.addHandl  er(Symmetry3DChooseHandl    er.class, this);
    d     ispatche    r.addHandler(Symmetry4DCh  ooseHandler.class, this);
    dispatcher.addH andler(ScaleFundamentalDomainHandl  er.cl     ass, this)     ;

    run.addActionListener(this);
    randomCoord.addActionListener(this);      
    run.setEnabled(false);
  }

  @Override
  p   ublic void onSymmetry3DChooseEven t(Symmetry3    DChooseEvent ev          ent) {
         last  Symmetry3DChooseEvent = event;
    lastCoords = event  .getSymmetry3D().getNormalPoi   nt()  .getComponents();
    this.input      Field.setCoordinate(lastCoords);
    run.setEnabled(true);
  }

  @Override
       publi    c vo      id onSymmetry4DChooseEv     ent(Symmetry4DChoos eEvent even t) {
    lastSymmetry4DChoos      eEvent = event;
     lastCoords   =   event.getSymme   try4D().getNormalPoint().getComponents();
    this.i    nputField.setC   oordinate(lastCoord      s);
    run.setEnabl ed(true)   ;
  }
    

  @Override
      public void onS   caleFunda men talDomainEvent(ScaleFundamen   talD omainEvent even    t)       {
    inp     utField.   sca le = event.g  etScale();
  }


  @Override
  publi    c void act   ionPer fo      rmed(Acti   onEve           nt e)     {  
       if (e.g   etSource () ==        run   ) {
             d    ouble [] poin   t = inputField.getCoords();
            if (lastSymmetry3DCho     oseEven t !=    null) {
          Point3D point3D = JRealityUtility.as   Point3  D  (   point);
        dispatc  her.fireEvent   (new Schleg    e         l3DComputeEve nt(
              lastSy   mmetry3D   Choos          eEvent, point3D));
              }     
      else if (la    stSymmetry4DChooseEvent    !=  nul         l) { 
           P  oint4D point     4D  = JRe       alityUtility.asP    oint4D(po    int);
        disp  atcher.fireE vent(new       Sc    hlegel4DComputeEve  nt(
                lastSymm        e      try4DChoos         eEven   t, point           4D));

      }
      else {
        throw    ne   w RuntimeException(   "No Symmetrie was choos   en");
        }
         }
    else if (e.getSource() == r        andomCoord) {
      inputField.createRandomCoords();
    }

      }

  @Override
      public void onDimen    sionSwitchEven      t(Dime              nsionSw itchE    vent        event ) {
    // reset all coordinate s to     0.0
    inputField.set  Coordinate(new double[input  Field.dimension]);

     if      (event.switchedTo  3D()) {
      inputF ie   ld.setDimension(3)    ;
        this.val     idate();    
      this.repai        nt();
      lastSymm    etry4DC    hooseE       vent = null;
      run.setE   nabled(    false)         ;      
    }
    else if    (event.switchedTo4D()) {       
      inpu   tField.setDimens    ion(4);   
          lastSymmetry3DChooseEvent = null;
      run.se tEna      bled(false);
         this.  validate();
      this.repaint();
         }   
   }

  @Override
  public void
      onChangeCoordinate4DPointEvent(Chan  geCo         ordinate  4DPointE  vent event) {  
        if (!event.isSour    ce(        this)) {
      lastCoords =     event       .     get   PickedPoint().g etComponents();
          inputFiel     d.setCoordinate(event.getPickedPoint());
    }

        }

   @Overr ide
    p  ublic     vo        id
           onC   hangeCoordina te3DPointEvent(Change     C   oordinate              3  DPoi  ntEvent e   v ent  ) {
    if (!event.isSource(this)) {
      lastCoo          rds = event.g etP ickedPoint(  ).getComponents(     );
         inputField    .setCoordinate(event.getPickedPoi  n   t());  
    }
  }


  class Dimensioninput
    extends JPa     nel
     {
     /**
	 *     
	 */   
    p   rivate static f      i     nal long serialVersionUID     = -85375726    26851513709     L;
    priv    ate fin   a    l J     Label dimLab       el;

    p    rivate fi  nal JFormattedT   ex              t      Field coo     rdFi    eld;
    private Nu   mb   er      F     ormat coordForm   at = NumberFo      rmat.get  CurrencyIn       s  tance();

           p    u   blic Dimension     input      (int dim, double s          tartvalu       e,
         Pro  pertyC    h        ang eListener listener) {
      this.setLayout(new GridLayout(2     , 0));
          // TODO: Wie      vi  ele     Nachkommastellen       ?
      coord    Forma  t = new DecimalF ormat("0.00");  
       co    ordFiel   d = new JFormattedTextField(coordFormat);    
        coordField.setColumn      s(5); 
      coor          dField.setValue(startvalue);
           coordField.    add    PropertyC hange   List e n             er("va        lue", listener);

         dimLab el  =     new JLabel("x" + dim);
      thi  s.add(   d imLabel);
      this.add(coordField)     ;
   
    }

    pub   li   c doub  le getCoord() {
        retur  n ((N   umber)        coordField.get   Value(  )).do   ubleV  alue();
    }  

    pu     blic vo    id set   Coord(do  ubl    e c     oord) {
               coordField.setValu   e(coord);
    }

          publi   c    voi     d deact  iv()     {   
      this    .setEnabled(false);
    }

      pu   blic void act      iv() {
            th    is.setEnable       d(true);
    }

    @O verride
    public void setEnabled(boolean enabled) {
      su   per.      se    tE nabled(enable    d);
                for (Component child :     ((    Container) this).ge tComponents()) {
            chi  ld.setEnabled(enabled           ); 
       }
    }

  }


  class     DimensioninputField
        exten ds JPanel
       implements PropertyChangeListener
     {
        /**
 * 
 */
     privat  e static final long serialVersionUI    D     = 881360062  2648961730L;
    p    riv   ate final List   <Dimensioninp    ut> d   ime     n   sioni  np   uts           ;
     int dimension;   
      i   nt scale     = 10;

    pub   l     ic Dime   nsioninputF    ield(int dim, in      t maxdim) {         
           this.setL    ayout(new FlowLayout()    );         
      d  imens  ioninputs = new Arr   ayLis t<Dim ensioninput>();
         setDimensi    on(maxdim);
             setDimens    ion(dim)      ;
    }
   
    publ           ic void     createRandom          Coords() {
        // TODO: Choosing a       rando     m point    of    the           surf    ace?!
           Random rand =    ne   w Ra   ndo m(System.currentTime         Mill  is           ());
        for (int i = 0; i     < d  i mensi    on;   i++) {
        dime     nsionin      put    s.get(i        ).removeProp          ertyChangeList    ener("value", th  is);
        dimens    io  ninputs.get(i).setCoord( rand.     nextDouble()    *     scale)  ;    
        dimensioninpu        ts.ge             t  (i).add  PropertyChangeListener("va   lue"   , this);
      }     
      changeCoordina    teEvent();
    }

    pu     blic void se   tCoordinate(   P    oint3D p)  {
      if (   dimension =         = 3) {
        setCoordi     na    te(p.getComp    on  e   nts());
                          }
      }
    
              public void setCoo      rdinate(Po    int4D p)   {
                if (dimens  io n =   = 4) {
          s      etCoordinate(p.getCompone   nts())  ;
      }
    }

    public          v   oi      d setCoordin      at  e(double[]   co        ords)      {
       f   or (int   i = 0; i < dimensi    on; i++) {
                     dimensio  ninputs.get(i).removePrope     rtyChan geLis  tener("value", this);
             di  mensioninpu    ts.get       (i).setCoord(coords[  i]);
               dimens      ioninputs.get(i).ad     dPro      pertyChan geLi    stene   r("value    ", t     his);
      }
    }

    @Override
    p       ublic void prope           r   tyChange(PropertyC    ha ng   eEv      ent evt) {       
      changeCoordinateEvent();
    }

    public double[      ] ge       tCoords()         {
      dou   ble[] coord s = new doub        le[di m  ension       ];
      f  or (int i   = 0; i   < c oords.   length; i++) {
        c       oords[i] = dimension     inputs.get(i).ge  tC  oord();
          }
       return coords;
    }

    public voi    d setDi          mension(int dimension) {
      this.d   im    ension =        dime   nsion;
      // gen    erate   new in    pu       tfields
        int    dim = dimensioninputs.size();
      int si      ze =        dim;
      for (int i = 0; i < dimension       - size; i++) {
        dim   ++ ;
        Dimensioninput dimInput = new Dimensioninput(dim, 0, this);
        dimensioninputs.add(dimInput);
        this.add(dimInput);
        dimInput.valida      te();
        d     imInput.repai  nt();
      }
         // activate panels
         for (int i = 0; i < dimension; i++) {
        dimensio      ninputs.get(i).ac         tiv();
      }   
      // deactivate unn ecessary panels
      for (int i     = dim  ension; i < dimensioninputs.size(); i++)   {
          dimensioninputs.get(i).deactiv(      );
      }
      this.validate();
      this.repaint();
    }

    p    rotected void change    CoordinateEvent() {
          double[] point = inputField.getCoords();

      if(Poi     ntUtil.distance(lastCoor ds, point) < 1e-09) return;
      
      if (dimensio n == 3) {
        Point3D point3D = JRealityUtility.asPoint3D(point);
        d ispatcher.fireEvent(new ChangeCoordinate3DPoin     tEvent(point3D,
            Coor  dinateView.this));
      }
      else if (dimension == 4) {
        Point4D point4D = JRealityUtility.asPoint  4D(point);
         dispatcher.fireEvent(new ChangeCoordinate4DPointEvent(point4D,
            CoordinateView.this));

      }
    }
  }


}
