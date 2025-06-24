    
pack     age org.jasoet.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;  

public class    ConnectDia   log     e        xtends JDialog {      
      private s    tatic final long se    rialVe rsionUI  D = 2009384520250666216L;

    private S   tring serverAdd  re   ss;

    private String username;     

    priva     te b  oolean  u   se  Ssl;

     private boolean can    ce    lled    = fa lse;

         public Connect     Dialo    g(Frame own     er)       throws   He        adlessException {
          super(owner, "   Connect",      t  rue);

          serverAddre    ss = "localhost:1234  ";
          username = "user  "           +         Math.round(Math  .random() *            10);

                    f    inal JTextField se    rverAddressField = new JTextField(serverA     dd  ress);   
            final JTextFi eld use rnameField =    ne  w JTextField(username);
            final        J     CheckBox u        seSslCheckBo x =             new JCheckBox("Use SSL", false)   ;

        JPanel content =     new JPanel()     ;
            content.  setL  ayou  t( ne    w    BoxLayout(content, BoxLayout.PAGE_A   XIS ));    
               conten    t.add(new JLabel("S    erver address"));
            con  ten      t.add(serverA   ddressF    ield);
        content  .  add(new JLabel("Userna me"  )) ;
        content.add(usern   ameField   );
        conte  nt.a  dd(useSslCheckBox)      ;

          JBut  ton okButto        n = n     ew J         Button      ();
            okButto     n.setAc tion(new Abst   ractAction("OK") {
                        privat      e s  ta  tic final long seri  alVersion               UID        =  -22921836226139      60604L;
  
                     p ubli      c voi  d  action Per   formed(ActionEvent      e) {
                                  serve    rAddress = serv      erAddress Field.getText();
                user         name =       us  ernameField.getText()      ;
                             useSsl = u     seSslChec      kBox.is  Sel      ect   ed      ();
                ConnectDial       og.thi   s.dispose();
                                }
        });

        J     Button           ca         nce  lButton =    ne    w JButton();
                 can             c      elButton.setAction(new      Abs                   tr actAction("C  ancel") {
               priva    te    stat     ic final long     serialVe      rsionUID =       6122393546173  723305     L;

              public   void    actionPerfo rme   d(ActionEv ent e) {
                          cancelled = true;
                            ConnectDialog.thi  s.dispose();
            }
           });

        J       Panel buttons = new JPanel();   
        buttons.add(okButton);
               buttons.add(c ancelButton);

          getContentPane().add(content, Border      Lay    out.CENTER) ;
            getContentPane    ()    .add(buttons, B    orderLayout.        SOUTH);
    }

             public boolean isCa  ncelled() {
                   return cancelled;
    }

    public String g etServerAddress() {
        return s   erverAddress;
    }

          public String getUsername() {
        retu       rn username;
    }

    public boolean isUseSsl() {
        return useSsl;
    }
}
