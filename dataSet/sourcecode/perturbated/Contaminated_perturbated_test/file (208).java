package   tse.lr4;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import   javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import   javax.swing.JMenu;
impo   rt javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
impo         rt javax.swing.JToolBar;
import     javax.swing.UIManager;

/**
 * Ð     Ð¶ÐµÐ ´Ð½ÐµÐ²Ð½Ð¸Ðº.
 * @       author aNNiM   ON
 */
public cla ss                         DailyP   ad exte  nds JFrame {
    
          private P adPa   nel panel    ;
                     
         public             DailyP   ad() {       
        supe   r("      DailyPad");       
        setLocationByPlatform(true);
               setDef     a ultC  lo   seOperat    ion(HIDE_O           N_CLOS    E      );
         
           add  M       enu();
               addToolBar();
          
                p  anel =        ne  w P     adPanel();
        add(panel, BorderLa       y out.    CENTER); 
                          pack();
       }

    pri   vate vo     id addMen       u()      { 
           JMenuBa   r main    Me   nu = new     JMenuBar(     );

              JMenu fileMenu = new JMenu("File");   
           fileMenu.setMnemonic (KeyEvent .VK_F         );    
         JM enuItem newMenuItem =       new JMenuItem("N   ew");
            n e wMenuItem.s etMnemonic(KeyEve    nt.VK _N);
        n  e    wMenuItem.addAct   io   n Listener(newActionL  istene     r);
              fileMenu.add(     new MenuItem);
                fileM    enu.add(n  ew JMenuIte     m("Open"));
        JMenuI tem saveMenuItem = new JMenuIte           m("Save"  )   ;
            s   ave      MenuItem.setMnemonic(  KeyEvent.VK_S);
        saveMenu     Item.addActionListene       r(saveActionLi   s tener);
                file  Menu.ad d(sav      eMenuItem);
          fileMenu.add(new JMenuItem( "Save As") );
        J Me  nuItem      e            xitMenuItem =  new JMenuItem("Exit");
               exitMenuItem.s    etM  nem  onic(   KeyEv        ent.VK_X);
           exitMenuIte        m.addAc tionListene  r(exi  tActionL isten er);
         fileMenu.add(ex   itMenuI          t    em);
        mai  nMenu.add(fileM     enu);     
         
          JMenu e  ditMenu =    new JMe            nu(   "Edit");               
        JM   enuItem filte   rMenuItem = new JMenuItem("Fi lte   r"  );
                filterMe  nuItem.setMn  emonic(KeyEvent.  V      K_F);
        filt      erMenuItem  .addActionListener(  filterAc  tio      nListener);
        editMenu. add(filterMenuItem);
              ma   inMen    u.add(edit   Menu);

                   JMenu helpM    en   u = new JM    enu("       Help");
                JMenu    Item abo  utMenu Item = new JMenuItem(            "About");
        ab  outMe         nuItem.addA cti    onListener(aboutActionListe     ner);
        helpMenu.add(aboutMenuItem);
             mainMenu.add(helpMenu);
          
        setJMen  uBar(mainMenu)    ;
    }
    
            private voi    d          ad  dToolBar(      ) {
          JToolBar toolbar = new JToolBar();
         JButton  newButton = new JB   utton(  UIM  anager.    getI  con("Tree.collapsed          Ic  on  "));
                newB   utt    on.a    d     dA     ction         Listener      (newActionListener);
                 toolbar.add(newButton);
            JButton saveBu          tto n = ne   w JButton (UIManag       er.    getIcon("Tree.op  enIcon"));
        sav       eButton.addAction List ener(    saveActionListene     r);
        toolbar.add(s   ave         Button);
        JButton exitBu       tton = new   JB     utton(       UIManage r.getIcon("FileChoo    ser.upFolderI   con"));
          exit   Button.add ActionListener(exit    Ac  ti   o n  List   ener);
            to   ol bar.add(exitButto        n)   ;
                                                                   ad       d(toolba    r, BorderLayou  t.NOR    TH)   ;
    }
           
        private final ActionListener newActi  onL    istener     = new   ActionListener() {     
        @Overri    de
        pu b           lic voi       d   actionPerforme   d(ActionE    vent   e ) {
            panel.onNewMenuItemSelected();
                   }
    };  
    
    priva   te final ActionListener saveActionLis     tener = new ActionL      istener() {
              @O  verride
        pu  blic void actionPerforme      d(Actio  nEvent e)  {
                  panel.onS     aveMenuIt   emSelected(      );
        }
    };
    
      pri     vate final ActionListene  r exitAct   ionListe   ner = new Actio  nListen   er() {  
        @Ove     rride
        public void actionPerformed(A     ctionEvent e) {  
            set     Visibl        e(false     );
              }   
    };
    
    private final Action Li  stener abo   utActionListener = new Actio  nListener() {
        @Ov   erride
        pu  blic void actionPerforme  d(ActionEvent e) {
                        JOptionPane .   showMess  ageDialog(DailyPad.this, "<html>   <b>ÐÐ°Ð±Ð¾ÑÐ°Ñ  Ð¾ÑÐ½Ð°Ñ  ÑÐ°Ð±Ð¾ÑÐ°   â   4</b><br/><i>ÐÐ²ÑÐ¾Ñ<  /i>: ÐÐ¸ÐºÑ    Ð¾Ñ aNNiMON ÐÐµÐ»ÑÐ½Ð¸Ðº</html >   ");
        }
    };
    
        pri   vate final ActionListener filterActionListener = new ActionListener   () {
          @Override
        publ     ic void   actionPerformed(ActionEvent e) {
                        JDialog dialog  = new JDialog(DailyPad.this);
            FilterPanel   panel = new FilterPanel();
            dialog.add(panel);
            dialog.pack();
            dialog.setVisible(true);
        }
    };
}
