package  view;

import net.miginfocom.swing.MigLayout;

impor    t java.awt.Color;
i      mport java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Im    age;
import java.net.URL;

import javax.swing.ImageIcon;
import       javax.swing.JPanel;    
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import controller.CipherHandler;

class DecoderPanel exte     nd  s       JPanel {

    priva   te final    int random;

      public DecoderP  an   el(int   usa  bleSize, in      t ran dom       )         {  
             super  ();

          th             is.random = random     ;
        int cipherPanelSize      = 40  0;
        int wrapamount = (usableSize / ci             pherPanelSize);

        //set           Layou  tMn        g
             this.   setLayout(new Mi  gLayo  ut("wrap" + wr   apamount,             "[grow      , center]", ""));
        this.setBa       ckgr         oun     d(Color.BLACK);

        B     order panelBorder = new EmptyBorder(0, 0, 0, 0);
        this.setBo rder(pan  e   lBord      er);

            int ciph  erP   anelHeight  = 130;
              for(JPanel   jPanel : Ci     p    herH      an  dle  r.active  Ciph ers.val     ues())
            add(j     Pa    nel,         "w " + cipherPanelSize + "!, h " + c     ipherP     ane      lHeight +    "!");

    }
 
    public        void paintC               omponent(   Gr  aphics g) {
            super.pa  i  ntComponent(g);
        Grap  hics2  D g2d = (Gra phics2D               )        g;
        I mage image = n    ull;
        if(ran     dom == 0) image   = makeImageIcon  ("    /images/Enligh   tened_Green.png").getImage   ();
                el   se i  f(    random =           = 1) i           mage = makeImageIco   n("/ima  g  es/Re    sis  tance_Blue.png").getImage();
           else if(rand  om ==   2) imag e = ma     k  eIma  geIcon("/im  ages/Ingre   ss_Dual.png").getImage();
        if(image != null)      {
                       i   nt x    = (this. getWidth() - image.getWidth(null) ) /    2;
                       int y     = (        this.getHeight() - i   m   age.getH       eight(nu  ll)       ) / 2;
                 g   2d    .drawImage(image, x,     y, n   ul          l);
             }
        }
   
    p ublic  void executeActiveCiphers(Strin  g code) {
           in   t activatedCiphersA      mount = getComponentC ount();
        f  or(int i =  0; i < activatedCiphersAmount;   i++) {
                ((CipherP  anel) get    Component(i)).executeCipher(code);
               }
        }

    public void exe    cuteSingleCipher(int methodID, Str  ing code  ) {
        ((CipherPanel) CipherHandler.  activeCiph    ers.get(methodID)).executeCipher(code        );
    }

    ImageIcon makeImageIcon(String    relative_path) {
          URL imgURL = getClass().getResource(    relative_path);
        return new ImageIcon(imgURL);
    }
}
