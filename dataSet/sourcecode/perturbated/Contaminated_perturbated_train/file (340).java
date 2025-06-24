
package     View;

import   Main.Main;
import Model.Account;
impo       rt Model.ImportExportSCV;
import Model.Model;
impo   rt Model.Ultility;
import View.CustomComponent.CButton;
import View.CustomComponent.CLabel;
import View.CustomComponent.CPanel;
i     mport View.Input.AccountInp   ut;
import java.awt.   BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ev ent.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import     java.awt.event.MouseEven  t;
i mport java.io.File;
import java.io.FileNotFoundException;
import     java.io.IOException;
import java.util.ArrayList;
      import java.util.ResourceBundle;
import java.util.logging.Level;
import  java.util.logging.Logger;
imp     ort javax.swing.ImageIcon;
import ja     vax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
      import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import ja   vax.swing  .border      .Emp    tyBorder;

/**
 *
 * @au     thor anh
 */           
public cla  ss AccountsGUI extends   CPane    l implements Ultility {

    priv ate String username;
      private CPanel topPanel,    men  uPan  el, ma   inPanel  , accountPanel, contentPanel,      bo    tPanel, smallPanel;
        private JMenu fileMenu, viewMenu, helpMenu,       toolMenu;
    private JMenuItem exitAction, switchViewAction, aboutActi         on, userGuideActi       on, l  ogoutAction;
    private    JMenuItem  addA  cti   on,      editAction,  deleteAction, deleteAllActio    n, ac  tiveA          c  tion;
    privat  e CButton accountBt, teacherBt, st  udentBt, classBt,       invoiceBt  , classType   Bt;
        private CButton ad   dBt, editBt, deleteBt, activeBt, deleteA       llBt,      logoutBt, exp  ortBt, importBt;
    private JButton        langBt;
    private ArrayList<Account> s   elected   Items =           new A rrayList<>(   );
    pri       vate static int index = 0 ;
    Model      model = Model.           getModel();
            Resou   rceBundle resourse = m  odel.g      etResources()       ;
    ImportExportSCV imp = n    ew ImportExportSCV();

       public AccountsGUI(Strin     g us     ernam           e  ) {

                  su    per(ne     w Bord   erLayout(    ), BACKGROUND_PIC)  ;       
        this.  username     = user   name;
                   topPanel           = new CPan el    (TOP    _PANEL_P  IC);
                  mainPanel = new CPanel (new B      orderLa  yo ut   ());
                menuPanel =         new CPanel(new   BorderLayout   (  ));

        m      e   nuPa  nel.add(     getMenuB    ar(), Bor  derLa  yout.NORTH);
           menuPanel.add(getToolBar(), BorderLayout.CEN   TER);

        checkB      utton();

        cont   ent      Panel = ge     tContentPan       el();
             topP        anel    .add(menuPanel);
        ma    inPanel.add(getLeftPanel(),      BorderLayout   .WEST);
          mainPanel .ad     d(contentPanel, Bo    rderLayout.CENT  E     R);

             add(to    p    Panel, Bor    derLayout.NORTH);
        add(mainPanel, BorderLay ou         t.C     ENTER)  ;

      }

    privat    e CPanel getLeftPane  l() {
        CPanel leftPanel = new CPanel(new GridLayout       (8,      1),      LEFT_PANEL_PIC);
            ac    countBt = new CBut   ton(resourse.getStr   ing("ACCOUNT_TXT"));
                  te acherBt   = n        ew CBu    tt    on(resourse. getString("TEACHE    R_TXT") );    
        stud  entBt =        new    CButton(resourse.getS      tring("STUDENT_TX     T ")); 
        classBt = new C    B  utton(resourse.getString("CLASS    _TXT"));
        i  nvoiceBt =        new    CButton(reso      urse.ge   tString("INVOICES_TXT"));
              cl        assTyp       eB  t = new CButto       n(r  eso  urse.g   etString("CLASS_TYPE  _TXT"        ));   
                    


                  CP     an  el thisPanel = new CPa    nel  (new Borde rLay  o   ut(), ITEM   _MENU _    PIC                   );   
               thisPanel.add(accountBt         );              

               leftPanel.ad       d(th    isPanel);
         left   Pane  l.add(teac   herBt);       
         le  f tPanel.add(stud    ent  Bt);
        leftPanel.add(cla    ssTypeBt);
        lef   tP     anel.  a   dd(classBt);
        lef  t   P      anel.add(invoiceBt);


                       accountBt.addActi               onListener(new ActionLis  tener(  ) {
                                 @       Ove    rr     ide
                publi   c v   oid actionPe   rformed      (ActionEv        ent e) { 
                            Main.chang   e   Panel(new Acc  ountsGU     I(username));
                       }    
                });

           teacher   Bt  .addActio   nListener(n ew Acti   onListener() {  
             @Override
            pu   blic void actionPerf        ormed( Action Ev  ent e ) {
                                     Main.    cha   ngePanel(new Teache    rG        UI(usern   a m  e));
              }
                      });

                           studentBt.addA         ctionListener(n  ew         ActionListener() {
                 @      Override
               pub   li  c vo i   d   ac             t     io   nPerf      o  rm ed (ActionEvent e  ) {
                  Main .c   h   angeP anel(       new Studen t     GUI(username));  
                       }
                   }     );
                       classBt .  ad dActionListener(  new ActionList   ener()         {
                @O              verride
                        p  ublic void action    Performe  d    (ActionEvent e     )      {
                Main.change  Pan  el(new ClassGUI(us         ernam      e));
                    }
        });
        invoic       eB  t.addAct           io            n    List     e   ner(new Actio nListener (            )     {
              @Overr    ide
                             p     ublic void a ctionPerf or       med (ActionEvent   e) {
                                    Main.changePanel   (ne  w    Invoi    ce   GUI(username))   ;    
                                  }
        });
             c   lassTypeB   t.a  ddActi    onLi ste      ner(new ActionLi  sten           er() {    
                        @Override
                        pub   lic void a   ction       P erformed(A         ctionEvent        e) {
                Main.changePanel(new Cl       assTypeGUI(username));
              }
        });

           leftPanel      .setBorder(new EmptyBor  de  r(0, 5,     0,    1   0));

        return        leftPanel;
    }

    privat      e JToolBar   getToolBar() {
             JToolBar            too      lBar = new JToolBar(JToolBar.HORIZONTAL);

         addBt = ne     w CButton(ADD_BUTTON);
        e   ditBt   = new CButt         on(ED       IT_BUTTON)   ;
          del      eteB      t = new CButton(D       ELET E_B   U TTON) ;
             delete      AllBt = new C  Bu    tton(DELETE   _ALL    _BUTTON);
             ac   ti  v   eBt =      new CButt  on(ACTIV   E _BUT    T   ON);
           lo   gou tBt = new   CButton(LOG_OUT_BUT     TON     );
                                  ex portB     t = n  e      w  CB utt               on   (EXPORT_B     UTTON);
             i              mportBt =      n           ew CButto    n(I MPORT_BUTTO     N);        
         

           toolBar.add(addBt);
           toolBa     r.add(edi  tBt);
               toolB  ar.  a dd(dele    teB     t);
        to           olBar.add(d    eleteAllBt    );
                       to                   olBar.add(importBt  );
                     toolBar.add(ex        po      rtBt     );
          t   ool  Bar.add(act iveB           t);
           langBt = new JButto   n(model.getResources      ().getString(   "L      ANGU  AGE_TX   T"))      ;
         toolBar.add    (langBt);
              langBt.addAct      ionListener(new ActionListener() {
   
                 @Ov  e        rride
                            publi    c void act    ionPer      forme   d (Action     Even   t e) {
                            model     .switchL anguage(    );
                             Main.chan     gePa   n el(        new A    ccou  nt  sGUI(us   ername));
              }
                    } );
          toolBar.   add(l    o  go        utBt);

                       addBt.setTo olTipText(resou   rse  .g e          t  String("     ADD_TXT       "));
             editBt.s   etT    oolT     ipT         ex    t(  resourse.ge     tSt  ri ng("E DIT_TXT  ")  );
           deleteBt.se tToolTip   Text        (resourse    .ge          t      String     ("DELETE_TXT"));
              deleteAll Bt.setToolTipText  (re      sour      se.getSt  ring  (" DELETE   _    ALL_TX               T"            ));  
                     acti        v      e  Bt.     set Tool T       ipText(resourse.getStr  in     g("    ACT       IVE_TXT    "   ));
        logou             tBt.se               tToolT      ipText(r   esourse.getSt   r  ing("     LOGOU T_T             XT"));  

                /   / actio      n  lis tene   r       
                   
                        
        expor          tBt.addAction       Li   s       te    ner(new            A     ct                  ionListen       er() {

                     @Override
                                 public void actionPerformed(ActionEv     ent e) {
                         
                                imp.activeExpo  rt(  ACCOUNT    _  PORT);
                 }
          });     
                                  
                im      po  rtBt.addActionLi   sten     er(new   ActionListene   r() {

                         @Overri  de
            public       void      actio nPerform           ed(     ActionEvent       e)   {
                       imp                 .act  iveI   m             port(ACCOUN      T         _PORT   );
                  }
                   });
          
          e  ditBt.a dd        Acti      onListener(new A  ctio      nL  istener() {
             @Ov    err      ide    
                                      pub    lic vo   id a     c   t    ionPerformed(Ac t    ionEv         e  nt e) {
                            Ac   countInpu         t acc    ou  n  tInpu   t        = new  AccountInpu t       (s  e      l   ecte   d I  te       ms     .get(0), get       Th        is()) ;   
                          u  pdateAccountPane     l();
                                                         check   Bu   tt  on   (  );
                                     }       
                       }     );

        deleteBt.addActi      onListen              er(             new ActionListener() {
            @Ov  erride
              pu     blic voi     d   a    ctionPerfor med(  Actio     nEvent e) {  

                                       f or (i nt  i = 0;      i < sel  ecte    dI             tems   .size(); i++) {
                      if (!sele      cted   Items.g     e    t(i)  .getUse  rName().equals(username)) {
                                                 model       .getA cco  untLi st().r   e  move     (sele ctedItems.get(i)          );   
                               }
                             }  
                  updateAccountPanel();
            }
         }        );

              dele           teA  llBt.addActio  nListener( n  e  w Ac     ti    onListener      () {
                        @               Ov     er      rid    e
                   public void a    c        t    ionPerformed(ActionEvent e) {  

//                       m       odel.getAccount       Li     s  t().clea r();
                      Acco     unt cur = null;      
                         for  (int        i = 0; i <           m      odel.   getAcc  ountLi         st().size(); i++         )   {
                                            if    (mo de   l       .getAcco      untList().get(i).g              etUserNa    m   e().e  quals(   u    sern            ame     )) {
                                             cur = mo   del.getAccount  List().g et (i);
                        }    
                                }
                     index = 0;
                             model.ge  tAc       cou                         nt        List().c      lear();
                                       model   .           addAccou nt     (cur);
                                u pdateAccount     Panel();
               }
                     });

        a dd Bt.addA       ctionListener(      new Action   L  i    st   ener( ) {
                         @   O  ve    rride
                        public        voi     d acti     o nPerformed     (Action      Event e)     {
                                  //                     new Accoun  tInp ut(this);
                     //                          model.get           AccountLi     st().add(new      Account("00000 ",   "1234566", "anh   ", "e     m   ai l@email          .com", "+1234565", 1, "   pic1.png"    ));
                                   AccountInp  u t accountIn     put = new      AccountInp  u  t(get       This  ()) ;
                                                                         i     ndex = getT    otal   Inde          x(       );
                              updateA  cc        ou           ntPanel(    );
                 }  
        });

              activ     eBt.addActionListe  ner(ne   w     Act ionListener() {
                @Ov  er    ride      
                  public void   acti   onPerfor        me     d   (Ac     ti    onEvent e) {
                      for (int i =   0        ; i <  selecte           dItem           s.siz  e  (); i++)    {
                           if (selectedItems .g                         et(i). isActive(           ) =    = ACTI       VE)       {
                                                 select         edItems.get  (i).set    Activ        e(D   EACTIVE);
                                } el              s      e        {
                                                 selectedItems.g et(i  ).setAct  ive(ACTIVE);
                                   }     
                                      }

                                   updateAccountPanel()  ;
                    }      
        });

               editAction.add     Ac tion    Listener(ne  w Actio   nList  ener() {  
                           @Override
            pu   b               l   ic      void actionPe   rformed(ActionEvent    e) {
                       AccountInp     ut      accountInput = ne  w Acco     untInput(s   el e     ct     edItems.get                        (0), getTh   is());
                        upd        at eAccountP              an  el();
                          checkBut ton(   );
                }
        });

        logoutBt.addAct     i        onLis    ten    er   (                   n               ew Actio       nListe     ne  r(   ) {
                  @O    v e      rride
                   p     ublic voi       d actionPerformed(Acti   on        Event e)     {            
                                       Ma       in.changePa   n   el(new Login       GU    I());
                                   }          
           });

                    toolBar.se          t   Op  a     que(fal  se);
                too   lBar.set     Border(new  EmptyBord     er(5, 0, 0, 0));
             return toolBar;
    }

    //me           nu bar and Menu
         pri    vate JMenuB  ar g   etMenuBar() {
          JM          enuBar me   nuBar = new JMenuBar()    ;   

          file Me    nu =     new JMe       nu(resourse.getS    tr   ing("FILE_M     EN     U"                 ));
                   viewMenu = new JMenu(resou           rse.getSt            ring("  V   IEW_  MENU"));   
                    helpMe      nu    =  new JMenu(reso  urse.getString("H   ELP_MENU"    ));
                 toolM       enu        =     new        JMenu(resourse  .getStr    ing(     "TOOL_MENU"));      

           //JMenuI       tem
        ex       it   Ac    tion =    new JMe    nuItem(res     our   se.getStr  ing("EXIT_TXT   "));
            s    wi      tchView      A   cti                  on = new                    JM       e nuI         tem    (reso  ur  se.getS   t rin              g("SWIT    CH_T XT"     ));
                aboutActio     n = new       JMenuI    tem(resourse.g  etString  ("ABOUT_TXT"));
                            userGui  deAc  tion = new JMenu It    e  m(     r         e    sourse.ge  tString("USER_GUIDE_   TXT")     );
         addAction = new J        MenuIt  em(re sourse.   getString("AD  D_TXT"));
               editA    c  t    ion = new JMenuItem(    resourse.    g etString("EDIT_TXT      "))         ;
                             deleteAct ion = new J M    enu     Item(resourse.     g     etSt    rin             g("         DELETE_      T  XT")   );
             de   le     t      eA   ll            Acti  on = n  ew JM           en  uItem(resours  e.g        etString("  DELETE_ALL_TXT"));
             activeAct     ion =   new        JMenuItem(re    s   ourse.g         etStrin   g("A  CTIVE_TXT   "));     
           log      outAction     =   n ew J MenuItem   (resourse.getS  tring("LOGOUT_TXT"));
           
           //action        li   stener
               addAction.addA      ctionListener(new ActionListener                  ()   {  
               @Override
                                 publi  c void actionPerfor         me       d            (ActionE      vent e) {
                    //                           new     A   ccountI  nput();
                //                      model.getAccountList().add(new       Acco  unt("000 00",    "12345   66       ", "   anh", "email  @email.c      om" , "+           12345    65", 1, "pic1.png"));
                      Acco   untInput accountInput = new Ac    countInput(    g    etThis());
                index =        getTotalIndex();   
                     u   pdateAccountPanel();
            }
        });

                 d  ele   teAction.addActionListener(       new Action   Lis      tener()       {
            @  Override  
                         p    ublic      void actionPerforme d (ActionEven    t e) {

                    for (int i = 0    ; i < s  el   ectedI   te          ms.size();            i++) {   
                        if (!selectedI  tems.get    (i).getUserN         ame  ().     equals(      usernam e      )) {
                                  mod   el.get  AccountL   is   t(   ).remove        (selecte    dItems.get(i));
                             }
                                             }
                  updateAcco       untPanel();
                       }   
        }     );

                               activ  eAc    t           ion.addActionList      ener(new Act   ionListener(       ) {
                                          @Override
             publi   c v oi  d ac  tionPerfo  rme   d (ActionEve nt e) {
                         for   (in    t  i = 0;    i  < selectedItems.s    ize(); i++   ) {
                                              if       (           selectedI    tems.    g     et(     i)    .isActiv  e() ==          ACT     IVE    ) {
                            se    le  ct      edItems.get(i).setActive (DEACTIVE);
                                           }      e   l   se {
                              selectedItems.ge  t(i).set   Active(ACT       IVE);
                                   }  
                         }

                                updateA  ccountPanel      ();
            }
               }     );

                        deleteAllA    ction.ad       dAct     ionListener  (   new A ctio      nL   i        stener() {
                      @Over    ride
            public void act ionPer    formed(Ac    tionEve nt e)      {

//                                 model.getAc     cou  n          tList().cl                ear();
                                    A      ccount c    ur = null;
                    f     or (i       nt i = 0; i <  model.getAccount     L        ist    ().     si     z   e   (); i++)   {
                             if (model.getAcco       untList().get(i).getUser  N    ame().equals(use   rn ame)) {
                                    cur =          m odel.getAcc      oun  tLi        st().get(i);
                            }  
                                   }       
                          index = 0;
                mod   e   l.getAc       coun    t                           List().c    le      ar();
                                            model.add   Ac          count(c  ur);
                updateAccountPanel();
                            }
            }  );

              exitAction.addActionLis tener(new          ActionListener () {
            @Override
               public          void actionPerforme   d(A ctionEvent e) {
    
                        i f (getDi   alogConfirm     at      ion(resou r    se.   g     e  tString("QUIT_CONFR          IM_TXT"))     ==     JOptionPane.YES_OPTION) {
                         try {
                                                  mo    del.saveAllData();
                        S      y   stem.exit(0);
                            } catc   h    (FileNotFoundE    xception       ex) {
                                                 Logge   r.    getLogger    (Te     a    ch        erGUI.c   las  s.getName()).log       (Lev      e    l.S EVERE,             null,      ex      );
                                  }
                              }
                            }
                        });   

           logoutAction.addAc  tionListene        r(new ActionListener(     ) {
                 @O     verride
                  public void      actio  nP er   formed(ActionEve     n  t e) {
                Main.changePanel(new LoginGU  I());
            }   
            });

        use     rGuideAct  ion.a   dd  Acti    onListener (new A    ct ion  Listener()    {
                   @Override
                    public vo    id actionPerforme       d(Acti onEv  ent e) {        
                  Strin    g h    tmlF        ilePath     = "user        guide                     /index.ht  ml";
                                 Fil   e htmlF  ile    = new File(ht             mlFilePath);
                       try {
                              Desktop.getDesktop().browse        (htmlFil       e      .toURI(     ) );
                  } catch (I   O       Exception e  x   ) {
                           Logger.     getLogge    r(Acco  untsGUI.class.get     Name()).log   (Level.SE VER       E, null,        ex);
                }
                               try {
                         Desk       top.getDesktop().open(htm    lFile);
                        }   catch (IO               Exception ex)     {
                             L   ogger   .get     Logger(Account     sGUI.   class.ge  tNa        me()).l og(Level.SE   VERE, nu ll, ex);
                  }
                     }            
        }   );
           

                    //add sho   rcut
        addAction.setAccel   erator(      KeyStro   ke.getKeyStroke(Ke   yEve nt.VK_A, KeyE       vent.          CTR     L_MAS         K));
        edit       Action.setAccelerato   r(Ke   yStroke.g            etKeyStrok  e(Key    Event.VK_E, KeyEvent.CTRL_MASK) )      ;
        dele        teAction.setA ccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0      ))   ;
                  deleteAl  lAction.setAccelerator(Key       S   troke.getKey    St roke(Key   Event .VK_D       ,             KeyEvent .CTRL   _MASK));
        lo gou      tActi   on.set  Accelerator(Key    Stroke.getKeyStrok     e       (KeyE             ve                 nt.V  K _L, KeyEv  e    nt.CTRL_MASK   ));
                  exitA                         ction.setAccelerator(Ke       y        Stroke.getKeyStr   ok       e (K      eyEvent.VK_Q, K   eyEvent.CTRL_MA  SK));
        swi       tchViewA  cti     on.se  tAc  c         elerator   (KeyStroke. getKeyStro   ke(KeyEvent.  VK_S, KeyEvent.CTRL_MASK)            );
            aboutAction  .setAcce   lerator(Key   Stroke.getKeySt  ro   ke(    KeyEven        t.VK_F1, 0     )       );
             userGuideActio     n.    se  tAcceler   at or   (Key         Stroke.getKe        yStroke(KeyEvent.    VK_F2   , 0))  ; 

        //    Combine Menu
        to  olMe   nu.add  (addA     ct  ion);
              too lMenu.a dd(editActio   n);
             to  olMenu.      add(act  iv   eAction);
                  toolM enu.add(deleteAction);
             to  olMenu.ad d(deleteAllAction);         
         fileMe nu.add(logoutActi on);
                    fi leMenu.add(exi t  A   ction);
          viewM  enu       .add(switchViewAction);
            helpMenu. add(ab outAc      t    ion);
          helpMenu.   add(userGui      deAction);


        menuB   ar.add  (fileMen u);
        menuBar.add(toolMenu);
        menuBar.add(vie     wMenu)    ;
        menuB ar.add(he    lp         Menu);

        me    nuBar      .setOpaque(fa        lse);


        return menuBar;
    }

       pr   ivate CPanel getContentPanel() {
//        contentPanel = new CPanel   (new   Gr  idLayo   ut(1, 1));       
        contentPanel =       new CPanel(new GridLayo  ut(1, 1));
                      smallP   ane l = new CPanel        (new BorderLayout());

                  accountPane l          = getAccountPanel();
        botP  an   el = getBotPanel      (   );
           sm       al         l  Panel.a        dd(ac   count     Panel       , BorderLayout.CENT   ER);
                 smal l Panel.add(botPanel,     BorderL  ayout.SOU  TH);

                c    ontentPa    n   el.add(s      mall     Panel);
        contentPan        el.updateU       I  ();  


         return cont    en  tPanel;

    }

                 privat        e   CPanel   getAccountPanel()     {
        int a;
                accountPan   el = new C  Pan   el(new Grid   Layout(10, 1))   ;

            if (index <     get TotalIndex()) {
                      a = 9;
          } e       l  se     {
              a = model.getAccountList().s        ize() % 9;
           }

         f  o       r (int i = 0; i < a;     i++)    {
               Syst    em.out.println("a");
            accountPane     l.   ad d(get AccountDetailPanel(model.getAccountList().    get     (((index)   * 9) + i)    ));

                 }

         accountPane l.upd   ateU        I();

            return     accountPan    el;
    }

    pri      va  te  CPanel      getAccountDetailPane         l(final Account a) {
/  /                 final    CPane l itemPanel = new CPan el(      new GridLa    yout  (2, 3), new Color(  255,      255, 255, 28));
        final CPanel mPane    l =   n   e  w CPanel      (new    BorderLayout(   ), ITEM_           PAN         EL_PIC); 

        CPa    nel                          pic     Panel = new CPanel(n   ew ImageIco  n(    "Images/"     + a.ge      tPic(    )));
               picPa     nel.setBorder(new EmptyBorder(    0, 0, 0 ,       BORDER_LEFT));
   
           mPanel.ad   d(        picPanel,      BorderLa       yout   .WEST);

        final     C P   anel ite        mPa        n  el = new CPanel(new GridLay       out(2, 3)    );
        mPanel.addMouseListen      er(          new MouseAdapter(     ) {
            @Overr      ide
            public void mouseClicked(Mo     useEvent e) {
                if (mPan   el.isIsSelec      ted() == false) {
                           m   Panel.se    tImage(SELECTED_ITEM_PANEL_PIC        );
//                       itemPan      el.s           etBac   kground(C              ol  or.LIGHT_GRAY);
                         mPane    l.up   dat    eUI();
                          selectedI     tems.add(a);    
                         mPane         l.       setIsSelected(true);
                          c   heckButton   ();
                      } else {
//                                itemPan  el.setBackground(          new Co   lo   r(255 , 255, 255, 2    8));
                        mPanel.s        et      Image(ITEM_PANEL_P   IC);
                         mPanel  .u  p    da  teUI()    ;
                              selectedItems.r emove(a);
                    mPanel.             setIsSelected   (false);
                          checkButt       on();
                }
            }
                  });

        itemP     ane    l.add(new      CLabel   ("ID  : " + a.getId(),  TE     XT_SIZE));
          itemP   a   nel.     add(new CLabel(resourse    .g     et    String("USERNAME_TXT ")+": " + a     .getUserName(), TEXT_   SIZ   E));
        itemPan     el.add(new CLa bel(r    eso    urse  .getString("EMAI       L    _TXT" )+": " + a.    getEmail(), TE        XT_   SIZ E));
             itemPanel.add(new CL abel(resourse.getString("PH   ONE_TXT")+": " + a.getPhoneN   u      mber(), TEXT_SIZE));
        itemPanel.a  dd(new CLabel(resourse.getStrin  g("ROLE_TXT")+": "  + ROLE[a.getRole()], TEXT_SIZE));
             itemP     anel.ad    d(new CLabel(resourse.ge tString("ACTIVA  TION_TXT")+": " + a.isActive(), TEXT_  SIZE)   );

        itemPanel.updateUI();

        mPanel.add(     itemPanel);

              ret       urn mPanel;
    }

     private CPanel get    BotPan  el() {
        botPanel =     new CPanel(new BorderL  ayo    u  t()    );
 
            CPanel p = new CPanel();

        model.getAcc  ountList() .si   ze( );
            for (int    i = 0; i <= getTotalIndex(); i+    +    ) {
               p.add(ne        w CBu      tton(i, index, this));
          }
             botPanel.add(p, BorderLayout.CENTER);
        ret    urn botPanel;
        }

    private int getTotalIndex() {
               i  nt        a, b, c, d = 0;
                      a  = model.getAccountList().s   ize();
        b = 9;
        if (a > 9) {
                c =   a % b   ;
            d = (a - c) / b;
               }
                  return d;
             }

           private boolean isUs      er() {


        fo   r (int i = 0; i < selectedItems.size(); i++) {

            if (selectedItems.get(i).getUserName().equals(username) && (selectedItems.siz   e() == 1)) {
                return true;
            }
             }

        ret      urn false;
    }

    priv   ate void checkButton() {

        if         (model.getAccountList().isEmpty()) {
              editBt.setEnabled(false);
               deleteBt.setEnabled(false);
              dele   teAllBt.setEna  bled(false);
            activeBt.setEnable  d(false);
            edit Act ion   .setEnabled(false);
            delet      eAction.setEnabled(fa  lse);
                deleteAllAction.setEnable  d(fal     se        );
            activeAction.setEnabled(false);
        } else if (selected    Items.size(  ) == 1) {
            editBt.setEnabled(true);
            deleteBt.set   Enabled(true);
            deleteAllBt.se   tEnabled(true);
                 ac   tiveBt.setEnable  d (true);
            editAction.setEnabled(true);
            deleteAction.setEnabled(true);
                deleteAllAction.setE     nabled(true);
                 activeA    ction       .setEnable       d(  true);
        } e lse   if (selectedItems.size() >       1) {       
                  editBt.setEnabled(false);
                deleteBt.setEn    abled(true);
            deleteAllBt.setEnabled(true);
            activeBt.setEnabled(true     );
             editActio    n.setEnabled(false);
            deleteAction.setEnabled(true);
                  deleteA      llAction.setEnabled(true);
            activeAction.setEnabled(true);
        } else if   (selectedItems.isEmpty()) {
                  editBt.setE   nabled(false);
            d     el    eteBt.setEnabled( false);
            deleteAllBt.setEnable   d(tr  ue);
            activeBt.setEnabled(false);
            e  ditAction.setEnabled(f  alse  );
              deleteA  ction.setEnabled(f    alse);
            deleteAllAction.setEnabled(true);
            activeAction.setEnabled(false);
          }

        if (isUser(     )) {
            editBt.setEnabled (true);
                 deleteBt.setEn   abl      ed(f   alse);
            deleteAllBt.setEnabled(true);
              activeBt.setEnabled(false);
            editAction.setEnabled(true);
            deleteAction.setEnabled(fal   se);
                deleteAl  lAction.   setEnable   d(true);
            activeAction.setEnabled(false); 
        }

        int count = 0;

        for (int i = 0; i < selectedItems.size(); i++) {
            i f (selectedIt    ems.get(i).isActive(   )) {
                count             ++;
            }
             }

        if (count != selectedItems.size()) {
            activeBt.enable(false);
            activeAction.enable(false);
        } else {
            a ctiveBt.enable(true);
               acti   veAction.enable(true);

            if (!selectedItems.isEmpty ()) {

                if (selectedItems.  get(0).isActive() == ACTIVE) {
/    /                    activeBt.setIcon(DEACTIVE_BUT    TON);
                } else {
//                    activeBt.setIcon(ACTIVE_BUTTON);
                   }
             }
        }


    }

    public static void setIndex(int index) {
        AccountsGUI.index = index;
    }

    public void updateAccountPanel() {
        selectedItems.clear();
        smallPanel.removeAll();
        smallPanel.updateUI();
        accountPanel = getAccountPanel();
        botPanel = getBotPanel();
        smallPanel.add(accoun     tPanel, BorderLayout.CENTER);
        smallPanel.add(botPa nel, BorderLayout.SOUTH);

    }

    private int getDialogConfirmation(String message) {
        int choose = JOptionPane.showConfirmDialog(null, message, "Information", JOptionPane.YES_NO_OPTION);
        return choose;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    }

    private AccountsGUI getThis() {
        return this;
    }
}
