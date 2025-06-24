package screens.server;

import     controllers.server.ConnectedPlayersController;
im    port entities.Player;
import screens.controls.IMainFrame  ;
import screens.controls.ImagePanel;
im  port views.server.ConnectedPlayersView;

import javax.swing.*;
import java.a  wt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import     java.util.List;

public class ConnectedPlayersScreen  implements ConnectedP  layersView {

      priv       ate static fin  al String BG_IMAGE  = "ima    ges/ connected.jpg";
    private    final IMainFrame mainFrame;
    private final Con   nectedPlayers     C     ontroller co   ntroller; 
     private Imag   ePa    nel panel;
    private DefaultListModel<String> playersNameL    ist = new Def    aultListModel<String >();
    pri      vate JList playersName      =    new JList(playersNameList);
    private Defaul   tListModel<String>   playersRoleList    = new DefaultListModel<Stri  ng>();
    private JList players             Role = new    JList(  playe   rsRol    eList      );
           private JButton    continu eButton;
    priv ate JButton exit     ;
    private JLabel playerN ames;
         private      JLabel role;


    public       Conn        ec   tedPlayersScreen(IMainFr   ame mainFram   e, Connect         edPlayersControlle        r co              ntroller) {
        this.mainFrame = mainFrame  ;
            this.controller =   controller;
             pan   el = mainFra   me   .c                reateImagePanel(BG   _IMAGE);      
            pl     ayersN a  me           =    c   reateList(players        Nam     e, 5        0  ,  100);
        playersRole = createList(playersRole, 200, 100);
        continueBut   t on = createButt   on(4     00, 100, "Continu      e");
        exit =      creat   e     Bu     tton    (400, 200, "Can  cel");
        playerNa   mes  = createL             abel(   "Pla   y    e  r Name", 50, -125,         150, 400);
        role = createLabel("      Role", 2    50, -     1 20        , 150,    400);
  
        pane  l.add   (conti           n                 ueButton);
         panel  .     add(playersName);
                 panel.add(pla  yersRole  );
        pane      l.add(exit);      
          panel.add(playerNam   es);
                panel   .add(role);

                      addLis tene r();  
                     mainFrame.    se              tSi      ze(600, 600);
              setDefa    ultCloseAction(mainFrame, co  ntr     oller);
        pa    nel.repaint();
    }

        p rivate void   setD       efaul tCloseAct   ion(IMainFram           e m       a     i  nFra      me,  final      C      onnectedPlayers     Co       ntroller co  ntroller) {
        ma  inF  rame.getF   rame().ad     dWindowList   ener (n               ew Window           Ad       apter() {
                       @Ove            rr   ide
                         public   void    windowClosed(Wi   ndowEve nt e) {
                                             c       ontroll  e   r. clo  s  e();
            }
            })    ;
    }

    private void addList   ener() {
                      continue      Button.add    ActionLi   s     tene        r(n         ew   ActionListener() {
                  @Override
                  public     voi d actio     nPerformed(Act       ion         Event e) {
                          controller.Continue();
               }
            });
           exit. addActionLi  sten      er(ne       w Ac     tionListener() {
               @Ov          erride
                               publi c void action  Pe          rformed      (Acti         onEvent     e) {
                controller.close();
              }
        });
         }

       private JList     c    reate    List(JLis         t playersList  , int         x_bound, int y_bound) {

        players     List.s etSiz   e(150,  450);
              playersList.setL oc       a   ti     on(x_b   ound, y_   bound);
                      pl    ayer sList.se    tBa     ckgrou          n  d(Color.ORANGE);
          players    List.setForegr          o    und(Color.B   LACK);
        player    sList.setBorder  (Border  Factor   y.createLineB  order(SystemColor.BLAC   K));
        Font f = new Font("Mono     spaced", Fo    nt.BOLD, 20    );
        playersList.setFont (      f);
           r   eturn pl    aye  rs  List;
       }

    private    JLabel cr        ea  te    Labe     l(String labelName,        int xBound, int yBound, int x       Size, int  ySize)     {
        JLabe     l label = new JLabel(labelName);
        label.setF      ont(new Font("Mo  nospaced", F ont.BOLD, 20));
             label.setForeground(Colo    r.   WHITE);
        label.setSize(xSize, yS          ize);
        labe      l.setLocation    (xBound, yBound);
        return label;
    }

    @Override
     pu       blic void display(List<Player> playe rs) {
        for    (Player player : players)     {
                 playersNameList.addElement(player.getName());
                         playersRole  List.addElement(player.getR ole());
        }
    }

    private JBut  ton crea    t   eButton(  int x_axis, int y_axis, String buttonName)        {
        JButton button = new JButton(buttonName);
        button.setSize(145, 50);
        button.setLocation(x_axis, y_axis);
        button.setFont(new Font("Verdana", Font.BOLD, 16));
        return button;
    }

}
