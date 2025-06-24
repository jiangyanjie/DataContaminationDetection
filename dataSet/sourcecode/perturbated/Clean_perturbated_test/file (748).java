/*
 *    CPU6502ModelPanel.java
 *
 * Created on December        6, 200   6,  1:06 PM
 *
 * To change   this template  ,      choose Tools | Te      mplate Mana  ger
 * and o  pen the       template in the editor.
 */

package emulator.nes.ui;
   
import java.awt.FlowLayout;
  import java.awt.GridBagConstraints;
import java.awt   .GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing  .border.TitledBo rder;

import utilities.ByteFormatter;
import uti      lities.GUIUtilities;
import emulator.core.CPU6502.CPU6502;
import emulator.core.CPU6502.Instruction6502;
import emulator.core.CPU6502 .mvc.CPU6502ViewInterface;
import e mulator.nes.INESHeader;
      import emulator.nes.NES;
import emulator.nes.PPU;

   /**  
     *
 * @author abaile     y
 */
public class     CPU6502Mode   lPanel exten   ds JPanel implem         ent   s CPU6502V           iewInterfac    e, Runnable{    
    
        /**
	 * 
	 */
	private static final long se   rialVersionUID = -1611709511709  413817  L;
	p   rivate boole      an headerM   ode       =     false;      
    private bo  olean                intMode = false;
    pri    vate b   oolean hexMode =   true;
    
     // register   s
    private    JTe      xtField _accum    ulatorField =   null;
    pri     vate  JTe  xtField _xRegisterField   = null;
    pr  iva       t  e JTextFiel  d _yRegisterField = null;
    

    
    //              fl         ags
       privat       e JC   heckBox      _negFlagCB = null;
    privat e    JCheckBox _zer       oFlagCB      = null;
          p    rivate JCheckBox _overflowF lagC    B = null;
    private JCheckBox _ca  rryFlagCB = null;
      private JCheckBox _interruptFlagCB = null;
    private   J    CheckBo   x _bre    a   kFlag           CB = null;
           private    JCh  eckBox      _dec      imalFlagCB         = null;
    
        /   / state fields
    priv  ate  JTextField _progr  a      mCoun t       erField     = null;
    pr  ivate JTextField   _   nextInst        ructionField = null;
     private     JTextField _operandField      = null;
       private JTextField _stackPointerField = null;
         priv  ate J   Tex   tField _cp      uCyc      leField = n         u    ll; 
    
    private     INESHea  derPanel headerPanel = null;
    private CPUVe   ctors         Panel vectorsPane    l =   null    ;

    priva  te CPU6502 model    = null    ;
	private boolean _r unning = false;
	pr     iva    te Thread _thr      ead =     null ;
          /**
      * Creates a new in  stance of CPU6502Mod   elPanel
         */
        public CPU6502ModelPanel() {
    	model = null;
    	_running =   false  ;   
    	_thread =   null;
              setupU    I();
        }
    public void startRun      n       ing() {
		if(_running){
			return;
		}
		_running = true;
		_thread = new Thread(   this);
		_thr  ead.start();		
	}
    	public void stopRunning() {      
		_running =   false    ;
		model = null;
		if(_  thread  != null) {
			try { _t       hread.join(); } c  atch(Exception e){ e.printStackT         rac       e(); }
			_thr  ead                      =      null;
		}
	    }
       pr     ivat e    voi              d setupUI(){
            set     Border(new TitledBor                der("650     2 CPU") );
        GridBagLayout    gbl     = new Gri               dBagLayou   t();        
         G ridBagConstr      ai    nts  g          bc    = new G    ri   dBagC  on    straints()      ;
        set           Lay   out (gbl);
            GUIUtili ties.initialize         GBC(gbc);
           gbc.anchor = Grid       BagConstraints.NORT    HWES T;
               gbc   .fill =   GridBagCo   nstraints.BOTH;
                         gbc.weightx = 0;
        gbc.weighty = 0;
           gbc.g     ri  d     width       = 1;
           gbc.    g   ridheight = 1  ;
     
           gbc.gri      dx = 0;
           g           bc.gridy     = 0;
           headerPanel = new INES  HeaderP    an        el();
                  gb   l   .setConstraints(headerPanel, gbc);
                     add(head   erPanel)     ;

           JPanel statePanel = setupStat  ePanel();
             gbc   .        g  r    i   dx      = 1;
        gbc.gridy = 0;
          gbl.setCo  nstrain    ts(stat   ePane      l, gbc    );
             add   (stat    ePanel);


        /     / a specia   l   controls mo   de panel
          gbc.gridx = 0;
        gbc.gridy = 1;
            JPanel regis tersPanel = set   upRegistersP    anel();
                gb   l.setC    onstraints(register    sPanel  , gbc);
        add(r    e       gistersPanel);
        
        vectors   Panel         = new CPUVectorsPan  el()     ;
          gbc.gridx =          1  ;
        g   bc.g    ridy = 1;
           gbl.setConstraints(vectorsPanel, gb  c);
        add(v   ecto   rsPanel);

            JPanel  flagsPanel                   = s            etupFlagsPanel() ;
           gb             c.gr  idx = 0;
           gbc.gridy = 2;
             gbl.setConstr aints(           flags  Panel, gbc);
                       add(flagsPa        n e  l    );
           



                     add(GUIUtilities.createFillerWidth(gbc, gbl,           2,    0))     ;
        ad    d(GU   IUtil     itie        s.cre   ateFillerHeight(gb   c, gbl, 0, 3));
                 
        }
    p    ubli   c voi    d   visitReg     istratio   n(NES n      es) {
                           nes.addMemo      r    yModelListener (vecto     rs   Pa nel);
               }

     
      public voi    d s     etHeader(INESHeader header, String filename){
           hea   de          rPa    nel.setHea      der(header, filename);
     }

    privat e JPane  l setu  pRegistersPanel(){
        JPanel regPane    l  = new JPanel();
           regPanel.setBorder(new TitledBorde   r("Registers"));
        G        ridBagLayout gbl = ne  w Grid  BagLayout();      
         GridB    agC      onstrai  n   ts gbc = new      Gr          idBagConstraints();
                            re     gPanel.setLayout(gbl);
                  GUIUtilitie   s.initializeGBC(gbc);
           gbc.an   chor =  GridB    agConstraints.NORT   H    WES  T;
             gbc.weight   y = 0;

           gbc.weightx = 0;
        regP     a   nel.add(GUIUtilities.createLabel("  A   ", "Accumulator",gbc, gbl, 0,0))    ;
        _accumula   t    orField = GU IUtilities.cre  a   teTextFie ld("","Accumulator",1   0, false, gbc, gbl,  1    ,0  )    ;
        reg  Panel.add(_accumul    atorField     );
        regPanel.add(G      UIUti   lities.createFille      rWidth(gbc, gbl,     2,0));

                gbc.  weight       x            =    0;
         regPanel.add(GUIUti lities.createLabel("X", "X Register",g  bc, gbl, 0,1));     
           _xRegist  er      Field     = G UI  Utilities       .createText Field(""        ,"X Register",10, false, gbc, gbl, 1,1);
        regPane    l.add(_xR  egisterField);
        reg     Panel.add(GUIUtilities.    cr   eateFillerWidth(gb        c, g  bl, 2,1)) ;

        gbc.weight    x = 0;
        regPanel.add(GUIU       tilities.c  reateLab  el("Y", "Y  Regist      er",        gbc,   gbl, 0,2));
           _yRegisterField = GUIUtilit      ies.   cre    at     eTextFie   ld("   " ,"Y Register",10,       false,      gbc    , gbl, 1,2);
                reg  Panel.add(_yRe   gist       erFi eld);
        regPanel.add(G  UIUti  lit      ies.createFille    rWidth(gbc, gbl,      2,2));
        
          gbc.weightx = 0.1;
          g   bc.w   eight  y = 1;
        gbc.   gridwid th      = 3;    
        regPan             e  l.add(GUIUtilities.cr      eate          Fille rHeig     ht(gbc, gbl, 0,3));
            return     r       egPane       l;
          }
     
    privat     e JPanel setup           FlagsPanel()   {
          J  Panel flagsPane    l = new JPanel(    );
        flagsPanel.   setBorder(new      Tit   ledBorder("Flags"     ));
        flagsPanel.setLa  yout(new FlowLayou              t(FlowLayout.LEFT));
        _negFlagCB       = GUIU  tilities.createCheck  B  ox("N", "Negative", f   alse, n  ull,    n     u  ll, 0, 0)  ;
              flagsPane       l.a   d        d(_ne         g Fl    agC   B);
        
        _zeroF    lagCB = GUIUtiliti       es.createC   heckBox(    "Z", "Zero"    , false, null, nul   l,   1, 0);
        flagsPanel.add(   _zer oFl         ag      C  B);
        
        _   overflowFlagC       B = GUIU  tilities.createCheckBox("      V", " Ove  rf    l     ow", fals   e, null, null, 2, 0);
            f  la  gsPanel.ad         d(_ overflowFlagCB);
               
            _carryF  lagCB =   GUIU     tilities      .  createChe ckBox("C", "Carry    "              , false, null, null, 3,   0  );
          fl         a        gsPanel .add(_carry      FlagCB);               
         
        _in   terruptFlagCB = GUIUt               ilitie  s.c     re    ateCh    ec        kBox("I", "I   nter  rupt", false, null    , null, 0, 1);   
          flagsPanel.add(_inte   rruptFlagCB);
           
        _breakFlagCB = GUIUtilities.createChe   ckBox("B", "Break", f  alse, null, null, 1           , 1);
                   fla     gsPanel.     add(_breakFlag          CB);
        
        _decimalFlagCB   = GUIUti   lities.cre    a teC    heckBox("D ", "Dec    imal", false, nul  l, null, 2, 1);
             flagsPanel.         add(_decimalFlagCB   );
          
           return flagsPa          nel;
       }
    privat       e J      Pane  l s    etupStatePanel(){
           J    Panel statePanel  = new J  Panel ();
                      statePan  e           l     .setBorder(new Titl                           e   d  Border("St   ate"));               
                
                  GridB      agLayout   gbl     =    n    ew      Gri dBagL    ayout();
             Gr idBa gConstraints gbc = new GridBagConstraints();   
        statePanel.setLayout(gbl    )   ;
           GUIU   tilities.i   nitializeGBC(g  bc);
            gbc.a  nchor      = GridBagCon straints.NORT          HWEST; 
        gbc.w  eightx =    0;
        gbc.weighty = 0;
          statePanel.add(GUIU     ti lities.create Label("P  rogram Counter:     ", "Ad  dress of Pro        gram Counte   r",  gbc    , gbl    , 0,0)   );
                      _programCounter   Fiel   d = GUIUti         liti     es.         creat      eText     Field("","Addre    ss of Program Counter",5   , false, gbc, gbl,    1   ,  0);
        statePa   nel.add(_prog  r   amC          oun     terFiel  d);
            
        statePanel.add(G   UIUt   ilitie  s.crea    teLabel("Instruct    ion:", "      Next  Inst      ructio    n to b    e e     xecuted"   ,                 gbc, gbl, 0,  1))    ;
           _nextInstructionField       =          GUIUtili    ties.createTextField("","N       ex  t Instr uc ti on to be execu t ed    ",14    , f  als   e, gbc,      gbl, 1,1);
          statePanel.add(_ nextIn    struction Field);
        
                s   tatePanel.add(GUI  U        t     il   ities.c    reateL   abe            l("Op  erand:", "Va lu    e (if any)    for   opera    n             d of next   instru       ction to be exe     cut e     d",gbc,    gbl, 0,2));   
        _oper     andField = GUIU  tilit   ies.cr      eateTe     xtField("      ","Valu   e   (if any) f   or operand o    f next instr  uction t      o be exec u    te  d",8, false, gb  c, gbl, 1,2 )   ;
            statePanel.a    dd(_o     perandField  );
         
                statePanel.     add(GUIUtilities     .crea  teLa    bel("Stack Poi  nter:", "Pointer to               current top o     f      stack (empty a  t $FF     )",   gb      c, gbl, 0, 3)         );
                _stackPoin   t  e       rField = GUIUtilities.   createTextFi   eld("","Po       in     ter t   o current top o     f stack",3, fal      se, gbc, gbl      ,     1,3);
           statePanel.add(_stackPointerFie     ld);

            st   atePanel.ad     d(GUIUtilities.cre  ateLabel("CPU Cycl   e:", "The current CPU cycl   e (   gets reset each NMI).",g  bc, gbl, 0,         4))   ;
              _cpuCycle Field = G  UIUtilities.createText    Field("     ","The current CPU    c        ycle   (gets  reset ea     ch NMI).",6, false, gbc, gbl,     1,4);
            statePanel.add(_cpuCycleFi   eld);
                  

                           gbc.weightx = 1;
        gbc.wei        ghty = 1;
         gbc.gridwidth = 2;
              statePa  ne   l.add(GUIU    ti  li    ties.createFillerHeigh   t(gbc,      gbl,0,5));
         
        
        return state     Panel;
    }
    
    private Str ing format          Mul tiV iewableByt  e(byte val){
        St   ring prefix   = "";
        if(intMode     ){
             i  nt i = val &  0xFF;
                    i   f(head  erMo      de)
                prefix = pref   ix + "Int:";
                   if(i < 10){
                prefix =        pre        fix + "0";       
             }
                  pre  fix = prefix   +       i + "     ";
        }
              if(hexMode){
            if(headerMode)
                                             p      refix =    prefix + "Hex      :";
              prefix =          prefix  + "0x" + ByteFo rm   at ter.formatByte(val);
        }
        return prefix;
    }
    
    p  ublic void ref    r    eshFr    omCPU(CPU6502 refresh      Model){
    	model = refreshModel;
    }
    publ      ic void run()   {
    	w     hile  (_running){
      		if(model   != null) {
    	        _ac  cumulatorField.s  etText(formatMultiViewable  Byte(model.get   A  ccumulato   r()));
    	        _xRegi   sterF ield.     setText(form     atMultiViewableByte(model.getXRegister()));
         	        _yRegisterFi    eld        .setText(formatMulti  ViewableByte(model.getYRegister()));
    	    
    	           _negFl   agCB.setSelected(model.getNegativeFlag());
    	        _zeroFlagCB.setSelected(model.getZero     Flag());
    	        _overflowFlagCB.setSelected(model.getO  verflowFlag()    );
        	        _carryFlagCB.s  et  Sel    ected(model.getCarryFlag());
    	        _interruptFlagCB.setSelected(model.   getInterr   uptFlag());
    	               _breakFlagCB.setSelected(model.getBreakFlag());
      	        _      decimalFlagC  B.setSelected(model.g   etDecimalFlag());
    	        
    	          _programCoun  terFiel   d.setText(ByteFo rmatter.form      atInt(model.getProgramCounter() & 0xFFFF));
    	        Instruction6502 i  nst = model.getInst       ructionAtProcessCounter();
         	        if(inst ==  null){
    	            _   nextInstructionField.setText("null");
               	            _operand   Field.setText("null");            
    	        } else {
    	            _nextInstructionF   ield.setText(     inst.toString());
      	            _operandField.setText(inst.getOperand());
    	        }
    	        _stackPointerField.setText(formatMultiV iewableByte(model.getStackPointer()));
    	        _cpuCycleField.setText(""+model.getClock());
    	        mode l = null;
    	        repaint();
    		}
    		try { Thread.sleep(200); } catch(Exception e){ e.printStackTrace(); }
    		
    		
    	}
       
    }
}
