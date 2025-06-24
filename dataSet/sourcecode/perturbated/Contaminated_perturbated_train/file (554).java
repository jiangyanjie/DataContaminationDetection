import java.awt.*;
import    java.awt.event.*;
imp     ort javax.swing.*;
import javax.swing.event.*       ;
import java.awt.geom.*;
// import java.util.*;

public clas s Connect4 {

         public  static void m     ain  (Stri       ng[] args)          {
                   Ho   st host =   n      ew Host();
                    host.start();
   }
}

/* T   o run the tournament, w  e'll comm ent ou t the strawmen  players.
 */
/*
class Player  1 implements Pl  ayer {

    publ       ic int play      (Board boar        d, i nt opponentsPl    ay       ,   Timer      ignored)           {   
                        java. util.Random rand = new java.util.Random();     

                // Gen  e rate a random l egal mo   ve.     
           //
        int m = rand.nex  tIn  t(U        til    .BOARDSIZE);
        while (!board.isLegalPla         y  (m)) m = rand.ne      xtIn   t(Uti    l.BOARDSIZE);

        i  f(Contro               l.D  E  BUG)
                S  ystem.out.p     rint         ln("player1.play: making   m   o v e       = " + m)   ;

        ret urn m;
       }

    public String team Name()  { re      tur              n "Bulldogs";  }
}

cl  as    s Player       2 implements Player {

           p      u    blic int play(Bo  ard board,     in   t opponentsPlay,    Timer ignored)     {
        j         av   a.util.Random r    a     nd = n ew java.util.Random();
  
        // Generate                  a random    lega       l move. 
        //
        int m      =   ra  n   d.nextInt(Util.B     OARD   SIZE   );
         while    (!board.isLeg  alPlay(   m)) m     =         r  and.nextInt(Util.BOAR  DSIZE);
   
          if(Co  ntrol.DEBUG)      
                      System.out.println("pl ayer     2.play: making mov      e = " + m);

             return m;
    }
      public   String teamNa me() {   return "Terriers"  ; }
}      */


class Connec      t4Frame e       xt    ends     JFrame           {
             priv ate int boardSize      ;
    private Disp    la y mainDisplay;

    pu    b       li c Connect4Frame(int size, Hos      t.     Listeners listeners, 
                         String tea     m1Nam      e, Stri    ng team2Name) {
           this.boardSize       = size;
             setTitle("CS102 Conne    ct4+ + Tournament");
                  setSi        ze(6  00, 5   00);

         m     ai     nDis    pl   ay = new Display(boardSize, listeners, t     eam1Name, team2Name);

        Container cont   en      tPane = getContentPane();
        conte       ntPane  .add(mainDi       splay);
      }

    // Gette         r
    //
    p    u bl ic i    nt    getB       oard  Size()    { ret    urn boa    rdS i  ze; }
        public Display getDis  p  l    a   y          () {     return mainDi          splay; }
}

clas s Display exte    nds JPanel {

    //   Instance variables
        //
      private int   b      oardSize;
    pri   vat          e ControlDisplay          co    ntrolDisplay;
         priva     te BoardDispla  y boardDisplay;
    private Host.Listeners      lis     tener   s;
         
          p         ublic D     isplay(int bs,     Host.  Listeners ls,String team1Name,   String team2N a     me) {
               b   oardSize =     bs;
        listen    ers = ls;
  
           setLayout    (new BorderLa            yo            u       t())    ;

         co  ntrolDisplay = new   ControlDis     play(this,      listeners,team1Name,       team2         Name);  
                  boardDisplay  = ne   w Board    Display(       this);

               add(cont   rolDispla      y,Bo rderLayo      ut.SOUTH  );
        add(boa      rdDisplay         ,B         orderLayo   ut.CENTER);
      }

         // Getters
    //               
     publi    c int getBoardSize() { r       eturn boardSize; }
    publi    c Control         Display getControlDi   spla  y() {   return    controlDi   splay; }
    pub lic BoardDis   play getBoardDi      spla  y() { return boardDisp       lay; }

     //   If you've got your hands on      the main       display    , you can feed
    // this method           a board to display.
    //
    publi c void displayB     oa    rd(Boa rd boa rd   )       {
        // Remo  ve al            l of the colu   mn pane            ls f    rom the   board.
        //   
        boa     rdDisplay.remove      All();
      
                i  nt[][] bo      ard  Ar   ra           y =        board.   ge        t  Array();

        for (int i = 0; i <   boardSize; i++) {

            B   ox co  l umnPanel   =     Box.createVerticalBox();

                  JButton  button =        new JButton      ("D    rop"        + (i    +  1));

                       button.addActionLi    ste   ne  r(            lis  teners.getButtonListener(i));
     
                   columnPanel.add(    but      ton);

              for (int j = 0; j < boardSize; j+  +) {
                 Disk     d    i    sk =   ne   w DiskC(     board          Array[j][i]);
                             JPanel diskPanel  = disk.getDisplayPanel();
                       columnP    anel.add(diskPanel);
                   }
                     boardDispla y.add   (columnPanel);
        }    
  
	// Re  paint the whole display.
	//
	    validate();
        }
}

class BoardDis  play     e    xten ds Box {
    pri  vate Dis     play   m   ainDispla yPanel;

    public Boar  dD    isplay() { super(Box Lay       out.X_AXIS); }    

          public  BoardDisplay(Display      displ      ay) {
                super(BoxLayout.X_AXIS);
                ma   inDi   spl        ayP          anel = display;  
    }
}

cl   ass Contr   olDis  play extends Box       {
    privat  e JButton stepButton;

    private JTextField team   1Fou   rC ount;
    priva   te J    TextField team1GameCount;

          private       JTextField team2FourCount;
    privat   e JTextField      team2GameC  ount;

    p       riv  ate      Display  mainDisplayPanel;  

    public          Contro  lDis        p      lay(Di sp          la  y display, Ho      st.Li        steners listeners   , String team1Na      me    , Stri  ng team2Name)    {
        su    per(BoxLayout.X_AXIS)       ;

        mainDispl   ayPanel = di      s       pl    ay;

        JPa  nel butto   nControlPanel   = n    ew JPanel(   );

           stepBu    tton = n     ew JButton("Step");
        stepButton.addAc    tionListe  ner(listen      ers.get StepL  istener());

                 JButton go   Button = new JButton("Go");
         goButto  n.addAction   Listen      er(lis   teners.getGoLis   ten  er());
                
        J    Butto   n matchPla  yButt on = new     JButto  n("M  atch Pl               ay");
            matchPlay  Button   .addAc  tion Lis   tener(listene    rs.get  MatchPla      yListe    n   e r()); 

         buttonC    ontr    olPanel.add  (matchPlayBu        tton);
            butt   onControlPanel.add(stepButton);
        buttonC     o        ntr     olP    anel.   add(goButto    n  );

        JPa   nel sliderControl  Pan   el  = new JPanel();
             //		JSlider delay = new JSlider  (0,100,0);
        JSl     ider    delay = new JSl   ider(JSlider.HORIZONTAL  , 0, 30, 15);

           delay.   addChangeListener(        li  st     eners.getDelayLi stener   ());
         sliderC    o ntrolPane      l.add   (delay);

        Box leftControlPanel    = Box.c reat    e     Ver ti      calBox()       ;
                      lef  t     ControlPa   nel.add(buttonControlPane    l);
              l    eft   Control      Panel.add(sli  derCon  trolPanel);

                 JPa    n    el rightCont   r ol    Panel =   new JPa       nel();

           rightControlP      anel.setLayout(  new    GridLayout(2,1));    

        tea     m1FourCou nt   = new     JTextFie    ld("0");
          te     am1GameCount = new JTextField("0    "  );     

        team2FourC ount = new JTextF  ield("0");
        team2GameCount      = new JTextFiel                 d("0");

          JPan      el pla    yer1ControlPanel = 
                 m  akePlaye   rCo     nt     rolPanel(team1Nam      e,    te  am1FourCount,team1Ga    meCount);
                   JPanel play er2Co       ntrolPanel = 
            makePlayerControlPanel(team2Name,team2FourCount,t  eam2Ga         meCo      unt);

           ri         ghtCo     ntrolPanel.setLayout(ne     w Grid    Layout(1 ,2));

        rightCo         ntrolPanel.add(player1ControlPanel);
            r    ightCont         rolP     an el.add(player2C     o   ntro      lPanel);

                    add(lef       tContr     olPanel); 
        a  dd(rightControlPa    nel);
    }

     public void setFourScores(Score fs) {
        Integer p1 = new Int  eger(       fs.getPlay  er1());
          Integer p2 = new Integer(fs.getPlay  er2());

        team1FourCo      un     t.set   Text(p1  .toSt        rin  g()   );
               team2FourCount.setText(p2.toSt     ring());
    }  

          public void se    tMatchScores(Score gs) {
             Integer p1      = new Int eger(gs.ge     tPla   yer1());
        Int    eger p2 = new I        nteger(gs.g    etPl         aye    r2());

               team1GameCount.setT   ext(p1.t         oS    t   ring()   );
               team2GameC  ou  nt.setTe xt(p  2.toString());
    }

    public JPanel makePlayerControlPa  nel(String tea    mName, JTextField fc,     JTextField gc) {
        JPanel outer = new JPa   nel();

            outer.setLayout(new GridLay   out(2,1));

          o   uter.add  (new JLabel(teamName));

              JPanel pe1 = make   ControlPanelEntry("Four          s",f c);
            JPanel     pe2 = makeC  ontrolPanelE     ntry("Games",g c);

        JPanel inner = new  JPanel();
        i  nne   r.setLayout(new G     r idLayout(1,2));

              in         ner.add(pe1);
              inner.add(pe2);
        
        outer.add(inner);

        ret urn outer;
    }

    p      ublic JPanel       makeControlPanelEntry(Stri    ng fieldName, JText     Field tf) {
        JPanel jp = new JPanel();

          jp.setLayout(new G    ridLayout(2,1));

            JLabel     l = new JLabel(fieldName);

        jp.add(l);
        jp.add(tf)  ;

        return jp;
    }
}

class FillPanel extends JPanel
{
	pr    ivate Color color;

	public FillPanel(Color c)
	{
		color = c;
       	}

   publi  c void paint         Component(Graphics g)
   {
      sup       er.paint  Compone    nt(g);
      Graphics2D g2 = (Graphics2D )g;

      // draw a rectangle

      double leftX = 0; // 100
      double topY = 0;  // 100
      doubl      e width = 50;
      double height = 50;

      Rectangle2D rect = new Rectangle2D.Double(leftX, topY, wi   dth, height);
      g2.s   etPaint(Util.emptyCellColor    );
      g2.fill(rect);

      // draw the enclosed ellipse

      Ellips     e2D ellipse = new Ellipse2D.Double();
      ellipse.setFrame(rect);
      g2.setPaint(color); // new Color(0    ,  128, 128)); // a dull blue-green
      g2.fill(ellipse);
   }
}


class ColumnOverFlowException extends RuntimeException {}

class ColumnUnderFlowException extends RuntimeException {}

class RanOutOfTimeException extends RuntimeException {}
