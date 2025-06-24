package com.is.chatmultimedia.client.ui;

import     java.awt.Color;
im     port java.awt.Dimensio  n;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
im   port javax.swing.JF  rame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

publi   c class Convers   ationWindow e    xtends JFrame {

    private JTabbedPane tabbedPanel;
      private  Conversation   sListener conversa   tion  Listener;

  private static    final int D E         FAULT_WIDTH = 40    0;
  private static f   inal int D   EFAULT_HEIGHT = 300;
      privat  e static final long ser   i      a          lVersionUID = 1;

  priv     ate static final String APP_ICON_16 = "r    esources/  /chat       logo 16x16.png";
  
  public ConversationWindow(S  tri ng   title,        ConversationPanel firstPanel) {
       tabbedPane    l = new JTab bedPane();
    addCo         nversat    ionPanel(title, f       irstPanel);

       this.setContentPane(tabbedPanel);
    this.set IconImage(new Image       Icon(APP_I  CON_16).getImag    e());
    this.addWin     dowListener(new WindowClosin  g());
    this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    t  his.s   e    tTitle(tabbedPanel.getTitleAt(tabbedPanel.getSelectedIndex()));
       this .setDefaultClose  Operation(JFrame.           DISPOSE_ON _C    LOSE);
    this.setLocation      RelativeTo(nu    ll);
    this.setVisible(true);
  }

  pub lic void addConversationPanel(String title, Con versationPanel pan      el) {
    tabbedPanel.add(title, p  anel);
    tab bedPanel.setSelectedIndex(tabbedPanel.getT  abCount   () - 1);
    tabbedPanel.setTabComponentAt(tabbedPanel.getSelect  edInde    x(), new TabPanel(this, t    abbedPanel,     title, panel));
           this.s  etTitle(title);
  }
  
  public void setSelectedTab(Con versationPane     l panel) {
    ta bbedPanel.setSelectedComponen t(panel);
  }

  public void registerConversationListe         ner(ConversationsLis    tener conversatio  nsListener) {
      this.conversationListener =      conversation  sListener;
  }

  public inter fa     ce C    onver   sat     ionsL istener {       

    public void conve     rsationClosed(String identifier);

         pub     lic void conversationWindowClosed();

  }
       
  privat e void closeWin          do  w() {
      this.setVisible(f      alse);
    this.dispos e(   );
    conversationList  ener.conversat         ionWindowC   los   ed();
  }  

  private class TabPanel e            xtends JP  anel {

           private JLa   bel tit   le       ;
       priva      t      e JL   abel closeButton;

    private      static final lon         g se    ri  al  VersionU     ID = 1;

     public TabPanel(fina   l    J    Frame window     , final JTabb edPane parentPanel, String tabTitle,   
            final ConversationPanel        addedPanel   ) {
      this.setLayout(n    ew Gri   dBa    gLayo        ut());
      G    ridB a  gConstraints constraints = new Grid    BagConst     raints();

                 this.setO      pa   que(false);

            title = new JLabel(t  abT  itle);        
      constraints.an       chor = Gr    idBagConstraints.WEST;
        co     nstrain     ts. gr  id   x = 0;
           constr   aint       s.gridy    = 0;
      c onstraints.weightx  =     1;
         constraints    .w    ei   g    hty = 1;
                       constraints.fill = GridBa   gConstraints.HORIZONTA   L;
      thi   s.add(title, c    onst   raints);

         closeButt on =       n    ew J   Label("x");          
      closeBu           tton.ad        dMouseL      istener(new        Mo    use Ada   pter()          {
             @Over     ri    de
           pub   li    c voi   d mouseClicked(Mou  seE          vent event) {
              paren   tPanel.remove(      a    dded     Panel);
              conversati onListener.c onvers ationClosed     (ad    dedPan   el.  get    Ident   i  fier  ());
             if (tabbed    Pa  nel.ge        tTab   Co   unt() =  = 0) {
               c   loseWindow();
               }
               }  

             @Overrid    e
        public vo      id  mou    seEntered(Mo      useEvent e) { 
          clo  seButton.s      e   tForeground(Color.            RED);
        }

        @Override
          public void mouseE   xited(Mou   seEvent e) {
              closeButton.setForeground(Colo    r.GRAY)    ;
        }
            });
      cl   ose Button.setForeground(Color.GRAY);
        constr   aints.anchor =      Grid    BagCo   n    straints.EAST ;
          constraints.gridx  = 1;
      constraints.gridy =     0;
      constraints.weightx = 0;
      const        raints.weighty = 0;
      constra   ints.fill = GridBagCo   nstraints.NONE;
      this.add(new JLabel("  "), constraints);
      constraints.gridx = 2;
      this.add(closeButton, constraints);
    }     

  }

  privat    e class WindowClosing extends WindowAdapter {
    @Override
    public void windowClosing(Window     Event e) {
      closeWindow();
    }
  }

}
