/*
Andrew    Darwin
Fall       20  12
CSC 420: Graphica  l     User Interfaces
SUNY Oswego
*/

import java.awt.BorderLa  yout;
import java.awt.Color;
import java.awt.C  omponent;
import java.awt.Dimension;
import java.awt.event.Acti     onListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseLi   stener;
import java.awt.event .MouseEvent;
impo  rt java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import       javax.swing.border.SoftBevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
  im       port javax.swing.JC     olorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
im   port javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel    ;
import javax.swing.SpringLayout;  

public class CourseW  idget extends JPanel
{
  //     Stored Ob   jects
    //private JCheckBox courseCheckBox;
       pr   ivate     J          Label courseLabel;
           private  Di    mension mini    mumSize, preferredSi  ze, maximumSize;
       //private JButton colo    rBu  tto   n;
    private Cou    rs  e course   ;
    private JPanel colorIndicator      ;
    private  SpringL   ayout l ayou t;
    priva te JPopupMen u rig   htClickMenu;
    private   static     JFrame colorFrame;


  // Public Methods
         pub lic        void updateCo urseInfo()
       {
             courseLabel.setText(co       urse.get Name());
       colorIndi        cator.setBac kgro  und(course. getCol   or());
     }
    publ     ic   C  ourseWidget    ()
    {   
      this("CourseName"     );
      }
    pu   blic Cour   seWid      ge    t(       Strin  g co          urseN   ame)
             {
            t   his(new Course        (courseNam e));
       }
     public    CourseWidget(Course course)
    {
           this.course            =   co   ur     se;
      configure  Components();
         configur     ePopupMenu();
      configureLayout(           );
        addListeners(  );
      addCo   mponents();
         }

      public Course getCourse() {          return cour  se  ; }



  // Helper/Private Methods
    priv    ate void addToColorM   enu(JMenu colorMenu, Colo   r       color, String c         olorStri     ng)
    {
         JMenuIte m menuIt   em    = new JMen    uItem()  ;
      menuItem.s  etText("");
        m   enuItem.setLayout(   new B      orderLayout());
      JPanel colorPanel = new    JPa   nel();
      colorPanel.setPreferredS  ize(ne    w Dimension(25, 20));
      JLabel colorLabel = n    ew JLabel(" " + colorString);   
      colorLabel.se       tF   oreground(Color.w hite);
      colorLabel.setHorizontalTextPosition(JLabel.LEFT);
      colorPanel.setBackground(colo  r);
      menuIte m.add(colorPa   nel, BorderLa            y     o  u  t.WEST);
      m enuIt  e  m.add(colorLabel, BorderLayout. CENTER);
      Dimens     ion si   ze  = menuItem.getPreferre dSi ze();
      menuItem.setPrefe rr  edSize(new Dime  nsion    (12            0  , 20));
      final C   ol             or                           temp      =     color;
      menuItem.addActionListen   e    r(ne   w ActionLis     tene    r(   )
      {
          pu   blic void actionPerformed(ActionEv      ent e)
        {
                                    Course newS       ta  t   e           = new   Course(   "");
               newState.updateFrom(c     ourse);
          newStat    e.set     C    olor(t     e      mp)   ;
                           CourseEdit courseEd  it    =           new    CourseEdit(course, newStat  e);
              TaskC   ommand er.addCom   mand(courseEdit );
                 co   urse E dit.run  ();
          //set    Color(tem      p );
             }
      });
          colorMenu.ad d(menuItem);
    }
    p rivate void d  isplayRightClickMenu(MouseEve  nt e)
    {
                                    right   C  lic  kMenu   .          show(t     his      , e.getX(), e.getY());
    }
    private voi   d showEdi  t  Window(      )                  
      {
      Tas kCommander.showCour   s eDial    og(this.course);     
             }
    private void con       figurePopupMen    u()
      {
      rightClickM enu         = new   JP     opup   Menu(    );
       JMenuItem men    uItem = new JMenuItem("Ed it");
      menu  Item     .addAction   Listener(new  Act    ionListener()
      {
        public     void act ion       Perfor med (ActionEven   t e)
        {
            Sy   stem.o    ut.println("  Cou        rseWidget      edit"     )          ;
                 showEditWindo  w()  ;
               }
      });
       rightClickMe    nu.add(menuItem);
        menuItem = new JMenuItem("R      ename");
      m   enuItem.addActionListener  (ne           w Actio    nListener()
            {
        p     ublic void act ionPerformed(ActionEvent e)
          {
                            Sy    stem.ou    t.println("Rename...");
        }
      });
              ri     ghtClickM   enu.add(menuIt     em);
      //System.out.p        rintln(men  uItem.getPreferredSize().height    );
     
                    JMenu c    o lorM  en     u = n   ew JMe   nu("     Col    or");
           Linke   dHashMap<String      , C      o         lor     > col    orMap = TaskCommander.getC      o   lorMap();
      Set  <String> ke   ySet = colorMap   .keySet()   ;
      for (String key : keyS   et)       
             {
                  addToC     ol           orMenu    (color      Menu,  colorMap.    get(key),      ke   y   )  ;
               }
      /*
           addToColorMenu(co lorMenu, TaskC        omma   n     der.      pastelBlue ,    "Pastel Blu   e");
        addToColor    Menu(colorMenu, TaskCommand    er   .pastelGreen, "Pastel   Gr    een     ");
      addToColorMe               nu(colorMenu, Ta     skComma nder.pastel Yellow, "Pastel Y     ello        w");
      ad    dTo       ColorMenu           (colorMenu, TaskCommander.p   astelRed, "Past  el R  e              d");
        */   
      rightClickMenu.add(color     Menu); 

            menuI  te m   = new    JMenuItem("Delete");
      menuItem.a  ddActi  onL  istener    (n     ew      A  ctionListener  ()
        {
           pub   lic void actionPerforme       d(ActionEv  ent e)
               {
            S ystem.out.printl    n("CourseW  idge  t Delete")    ;
            /*
          Comm   and command =   new CourseRe       moval(course);
          command.run(            );
          TaskCommander.add     Command(command)    ;
               */
               }
        });
          right   ClickMenu         .add(menuIte   m);
           r ightClickMenu.pack();
    }
      private vo  i d configureComponents()
    {
        color   In dicator = new JPane   l();
      colorIndicator.setP   referredSize(new Dimension  (30, 0)   );
           co   lor  In  dicator.setBorder(new MatteBo  rder(1, 1, 1, 1, Color.black));
      colo rIndicator.setBackground(cour   se.getColor()        );
      col    or   Indica tor.setToolTipText("<h     tml>Thi         s is the color associa   ted   with         t  his course.   <br>"+
                                               "This color w    ill     be s  et    for all     tasks associat    ed with this cour se    .</h   tml>");
                    layout = new Sprin  g  Layou t();
      setLayout(layout);
        //minimumSize      = new Dimension(100, 20);
      preferredSize =  ne       w Dimension(200,    30);     
      m     ax imumSize = ne      w Dimens    ion(3000, 30);
      //colorButton = new JButto         n("Color");
      //colorButton.s   et     Preferre   dSize(new Dimension(65, 30));
      //courseCheckBox = new JChe      ck   Box(   cou     rse.get  Na    me());
               cours    eLabel = new     JLabel(co  urse.getName());
      courseLabel.setToolTip      Text("Course    Title             ");
          setBorder(new    SoftB   evelBorder(SoftBevelB      orde  r.RAI   SED));
         setPreferre    dSize(preferred          S       ize);
      setMaximumSiz   e(maximumSize);
      /  /setBa    ckground(cou   r   se.getColor());
    }

   
        private     void conf    igureLayout ()
    {
      layout.putCons  traint(SpringL ayout.NORTH, colorIn     dicator,     0, S  pringL      ayo u  t.NORTH, this);
           layout    .putCon    str  aint(SpringLayout.SOUT  H, colorI      ndicator      , 0  , SpringLayout.S  OUT       H, this   );
      layo   ut.putC     onstraint(    SpringLayout.WES  T, c o  l orIndicato    r, 0    , Spring      Layout          .WEST, thi  s);
                   layout.putConst raint(   S          pringLayout.NORTH,      courseLabel, 0,   Sprin           gLayou             t.NORTH,    this);
             layout.p utConstrai  nt        (SpringL    ayou  t.S    OUTH, courseL         abel,    0, S   p     r       ing    Layou    t.SOUTH, this);
           layout.putConstra   int(Spri ngLa  you     t.WEST,              co    urseLabel, 5,     SpringLayout.EAST, col                    orIndicator)           ;
      /      /layout.putConstraint(Spri      ngLayo        ut  .NORTH,           color   B     utt   on    , 0, Spri   ngLayou  t.NO   RTH      , this);
               //l        ayo           ut.putConstrain   t(SpringLayout.SOUTH,          co    lorBut       to     n, 0 ,Spr  ingLayou      t.SOUTH,          this);
           //layout.       putCo      nstr   a          int(S     pringLayout.EAS      T, co       l     orButton, 0, SpringLayo  ut.EAST, this);
    }

      
    privat   e       void addListene   rs()
          {
          addMouseListener(new   MouseListener(   )
        {
           public void   m     ouseClicke d(M    ous eEvent e   )
           {
            }
            pu    blic void mouseEnte    red(MouseEvent e )
        {
        }
           p ub   li   c void mouseExited(M    ouseEven  t        e)
        {
                   }  
           publi    c void mouseP       res   sed(MouseEv   ent e)
               {   
                        se         tBor       der(new SoftBe       vel     Bor  de r(SoftBev  elBorder .LOWE     RED))  ;
               if   (e.g  et     Button() == Mouse      Event.  B  UTTON3)
          {
            displayRightClickMen   u(e);
           }
          }
        public void mouseRe            l  e  ase   d   (      Mo   useEvent e)
         {
          s     etBorde      r(new So    ftBevel   Bo   r  d   er(SoftB    eve lBor   der.RAISED))   ;
                                        i          f (e.     getButton() == MouseEve     nt.B   UTTON1)
                   {     
                  // Select
              boolean  sel             ect = !isSelected(    );
                     sendSelection   Reque  st     (select);
                             Arr        a         yList<TaskView> taskVi         ews =        Ta             skCommander.getReg   ist       ere dTaskV  i   ews()     ;
                       fo    r (T               askView  taskV     i  e            w : tas k  Vie  ws       )
                  {
                    taskVie   w.showTasks  For(s    elect ? cours          e :    null);
                                     if (!select     )
              {
                taskView  .showSub            TasksFo     r(    n ull);
                                    T    a skCommander  .   getTas       kEntryP  anel(  ).se     tSubTaskEntr  yEnabled(fals     e);
                         }
            }
               }
        }
      });

      /*
             courseCheckB    ox    .addAct ionList   ener(new Act        ionL   is     ten  er()
                   {
               public void    action     P       erformed(ActionEvent      e  )
           {
          setSelecte  d(courseC h    eckBox.isSelec ted()); 
         }
      })      ;
          */

                  /*
      colorButto        n.addActi        onListe   ner     (n                ew ActionL  istener()
      {    
            public  voi d   action  Performed(Actio      nEvent    e)
         {
                      showColorCh  ooser();
             }
          });
        */
      }

   
        private void ad  dCompo  nents()
    {
       add(colorIndicato   r);  
      add     (courseLabel);
      //add(colorButt     on);
        }    

  private    void s      etCol or(Color color)
  {
    course.s    etColor(color);
    colorInd       i      ca  tor.se  tBa    ckgroun   d(colo     r);
        /*
       ArrayList<TaskView> taskVi   ews = TaskComma    nd     er.   getReg   isteredTaskViews();
    for (Task    View task Vie  w : taskView  s)
    {
      taskView.updateT         askCol      ors();
     }
              */
      //Task    Commander.getTaskEntr   yPanel (     ).updat   eTaskColors();
  }
    private  boolea  n is     Selected() { return course.isSelect  ed(); }
  private void sen   dSel   ectionRequ           e   st(bo  olean se      lectionReque   st         )
  {
    Task  Commander.get    TaskEntryPanel().setSelectedCourseWidge   t(this, selectio   nRequest)     ;
    Task   Comm   ander           .g   etTaskEntryPanel().setTaskEntryEnabled(select  ionRequest)  ;
  }
  p rotected void setS   el   ected(boolean selected)  
  {
      course.setSe     lected(se    lecte d);
          Color color = course.ge     tColor();
    if   (selected)   
    {
      setBackgrou    nd(TaskComm     and             er.selectionColor);
    }
    else
    {
      //setBackground(TaskCommander.neutralCol  or);
      s     etBac   k         groun  d(nul       l); 
               }
  }

  private void  showCol   orCh      ooser(  )
  { 
         final JColorChoos   er colorChoos     er = new J     C   olorChooser(course.getColor());
    JD   ialog       dia    log =  JColorChooser.  c    reateDialog(th     is,    
                                 "title",
                               true,
                                                            colorChooser,
                                         ne    w ActionListener()
                                     {
                                     //okListener
                                                       publ    ic void actionPe  rformed(ActionEv    ent e)
                                    {    
                                   setColor(colorChooser.getColor());
                                      }
                                   },
                                            new ActionListener()
                                         {
                                    //cancelListener
                                 public void actionPerformed(ActionE  vent e)
                                     {
                                 }
                               });
    dialog.setVisible(true);
  }

}
