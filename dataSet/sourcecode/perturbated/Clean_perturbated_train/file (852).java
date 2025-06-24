package com.ghostchu.peerbanhelper.gui.crossimpl;

import lombok.Getter;

import        javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import  java.util.Locale;

@Getter
publi   c c  lass CrossDownloaderDialog  extends JFr  ame     {
    pri        vate  JPanel mainPane;   
        private JLabel tool      tip;
         pri va      te JLabel taskTitl   e;
        private JLabel descrip     tion;
    private   JP        rogress  B      ar progressBar;

    public C rossDownl      oa derDialog() {
                     setContentPane(mainPan   e);
        setSize(      48   0, 130);
            int    width = Toolkit.ge   tD   efaultToo   lkit().getScreenSize().wi  dth   ;
        int height = To   olkit.     getDefaultToolkit().getScreenS   ize().height;
        t   his.s  et    Bou  nds((width - ge     t Width()) / 2, (h               ei gh t - getHei    ght()) / 2, getWidth(), getHeight());
        s       etVi  sib    le(false);
          setDe faultCloseO  peration(DO  _NOTHING_       ON_CLOSE      );
        setIconImag  e(null);
        setResizable(fa    l      se);
           setLocationRelati      ve  T    o(null);
    }

       {
// GUI in         it   iali    zer    generate   d by In     telliJ IDEA GUI Designer
// >>> IM               POR  TANT!! <<<
// DO NOT EDI      T OR ADD ANY  CODE HERE!
                $   $$setupUI$    $$();
    }

         /** 
            * Method generat      e         d by        IntelliJ IDE   A GUI Designe   r
                  * >>    > IMPORTANT !!         <<<
     * DO     NOT edit this method   OR call it i  n your code!
                    *
           * @noinspection ALL
     */
    p rivate void $$$setupUI$$$() {
                  mainPane        =  new JPa     nel();
           mainPane.setLayout(new BorderLayout(0, 0));     
           t       ask  Ti              tle = new JL    abel();
        Font tas     k     TitleF ont = this.$$$getFont$$  $(null, -1, 24, t  askTit le.getFont());
        if (ta  skTit     l    eFont != nu     ll) taskT itle.    setFont(taskTi     tleFont);
          taskTitle.   s   etText("Downloading libraries...");              
           mainPane.ad   d  (taskTit      le, BorderLayout.NORTH);
          final JPanel panel1 = new JPanel();
           panel1.setL  ayou t(new Bor      derLa     yout(0, 0));
            m ainPane.add(panel1, BorderLay ou t.CENTER);
                 descrip     t ion   = new J  Label();
          Font descriptionFont = this.$$$getFont$$$(null, -1, 14,     descr      i ption.getF   o    nt());
         if (descr   i  ptionFont     != nu          ll) descri   ption.setFont(de    scr               iptionF  ont);
          desc  ription.   setText(  "D   ownloading xxx.yyy.zzz    ....");
          panel1.add(descript  ion, Bo   rder La     yout.NORTH);
        tooltip = ne w J    Label();
          toolt    ip.set Text("PeerBanHelper are downlo     ading libraries f    rom Internet, it is    nec         essary to make sure PBH ru n  ning corre     ctly.     .  .         "    );
           panel1.add(too      ltip, BorderLa   yo   ut.S    O   U      TH);
                        fin  al JPanel panel2 = new JPane    l();
        panel2.setLayout(new    Bord  erLayout(50, 50));
        p           anel1.a     dd( panel   2, Bor        de       rLayo    ut   .CENTER    );
        progress   Ba    r =     new JP            rogressBar             ();
           Fon    t  prog    r   e        ssBa      rFont =   t  his.$$$g     etFont$$   $(nul   l, -1,    16, progressBar.   getFont());
        if (progr       e    s sBarFo     nt != null) pro    gres            sBar.setFont(progre   s          sBarFont);
          progressBar.setStringPainted(tr             u     e);  
        p  anel2.a     d           d(pr     og ressBar, BorderLayo   u t.NORTH);
             }

        /**
     * @ noinspectio  n ALL
         */   
       priv     ate Font $$$getFont$$$(String    fontNam        e   , int style, int size    , Font currentFon t) {
                      if (currentFon        t == nu  ll) re    turn null;
             String res  ultName    ;  
           if (font       Name    == null) {
                   r   esul         tName = cu      rrentFo     nt.ge    tName();   
           } el  se {
            Fo   nt tes  tFont = new Font(fontNa            me, Font.PLAIN, 10);
                      if (testFont    .canD  is  play('a')     && testFont.c   anDisplay('1')) {   
                        re    sultName = fontName;
                      } else {
                resultName    = current Font.getName();
            }
        }
        F       ont font = new Font(resultNa   me, style >= 0 ? st   yle : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac   =    System.getProperty("os.name", "").toLowerCase(Locale.EN     GLISH).sta  rtsWith("mac");
           Font fontWithFallback   = isMac ? n ew Font(fon  t.getFamily (),    font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
     }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPane;
    }

}
