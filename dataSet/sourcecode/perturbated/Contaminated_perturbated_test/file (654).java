
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import      java.io.File;
import java.io.IOException;
import    java.math.BigInteger;
import       java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOpti     onP  ane;

/*
 * To   change this t emp  late, choose Tools | T    em  plates
 * and open th  e template in the editor.
     */

/    *
 * decryptFra me.java
 *
 * Cre  at  ed on 3 Apr, 2009, 12:5   5:4     6 PM
  */

/**
 *
 *      @author abhinav
 */
    public class dec      ryptFrame extends javax    .swing.           JFrame {

    / ** Creates new    form decryptF   rame */
      stat    ic String  pass =           "   ", ms  g ="", fil    ename =          "";
    JFile     C   hooser fc;
    File     file;
     int       isCorrectPass = 1;
       publ ic decryptFra me() {
        ini    tCo mponents  ();   
        fc = new JFileCho    oser();
        //fc.s  etFileSelection   Mode(JFileChoo ser.FILES_ONLY);
           fc.addCh oosa    bleFileFilter( new pngFilte  r()  );
            fc.setAcc  eptAllFileFilterUs      ed(   false);
                   	    //    Add c   ustom icon  s for fil  e  ty  pes.
                   fc.setFileView(new   Im  ageFil  eView      ());

	    //Add   th  e preview pane.
            fc.setAc         c         esso   ry(    new ImagePrev            iew  (fc));
       }

      /** This       method is c       alled from within the     const   ructor to
     * initialize the form.
          * W ARNING:     Do NOT        modify this cod         e. The content of this method i  s
         * always   r             egenerated       by the Fo       rm E ditor.
             */
    @SuppressWarnings      ("unchecked")
    // <editor-fold defaultstate="collapsed" de    sc="  Generated Cod    e">//GEN-     BEGIN:   ini   tComponents
    private      void initComponents() {

                jSc  rollPane    1  = new ja   va    x.s   wing.J    S    crollPane   ();
         jTextAr   ea1 =   new javax.swing.JTextArea();
           O K = new ja   vax.    swing.JButton();
        jLabel1 = ne w javax.sw    ing.JLabel ();
            s      electfile =   ne          w       javax.swing.JTex tFie ld()    ;
            cancel = new jav      a  x.s    wing                .JButton();
          browse = new javax.sw     ing.JButton()  ;   
           jLabel3 =       new javax.swing.JL    a   b el();
                    jTe    xtField2 = new java  x.       swing.JTextF       ield();            
                   jLabel4 = ne     w javax.s    wi   ng  .JLabel()             ;
        jLabel5 = new jav  ax.swin  g.JLabel ();
            jMenuBar1 = n  ew javax     .swing.   J       MenuBar();
        jMen     u1 = new j    avax.swing.JMenu();
               jMenuItem1 = new javax.s    win   g.JMenuItem();
               j      M       enu2 = new ja   vax.swing.JMen  u();
        jM      enuItem2 = new java  x.swing.JMe n    uIte   m(   );

        s et                Defa  ultC     loseOperatio            n(jav     ax.swing.Wi  ndowConsta   nts.EXIT_ON_CLOSE);

           jTextArea1.setCo               lumn     s(20);
             j  Text  Area1   .setEd   itabl   e( false);
        jT   extArea1.s                 etR      ows    (5);
                   jScrollPan  e1.setViewportView(            jTextArea1);

           OK .s  e   tText("OK");
        O K.addAct         ionListener(new ja     v  a.a   wt.e         vent.ActionListener (   ) {
                  pub lic void ac  tio        nPerformed(java.awt.event.Act            ionEvent e vt) {
                      OKA    ct ion    Per formed(ev       t);  
                                           }
          })    ;   

        jLabel1.set   Text("T    ext hidden i     s:");

        s  electfile  .setEdit abl e(fa    lse);
                      s   elect fi      le.addActionL     i   stener(new java.aw     t.event.Action     Liste      ne  r( ) {
             public voi    d actionPerformed(j      ava.a   wt     .even           t.A          cti     onEv               ent ev   t) {
                 selec    tfile Act   ion               Perform   ed  (evt);
                  }
            });   

        c   a               ncel.s  etT     e    xt("Ca       ncel");      
          cancel.addActio  n    Listener(new   ja    va.awt.e       vent.ActionListener()        {
                    pub  lic void actionP     erf  o      r   med(      java   .awt.event.Act  ionEv  e nt evt) {
                  cancelAction  Performed(evt);
                   }
        });

        br          owse.setTe      xt("B       rows   e" );
        browse.a  ddActionListener(new java .awt.event.Act   io                 nLi        sten     er() {
              p         ubl    ic    void act   ionPerformed(java.awt.event.ActionEvent evt)   {
                browseActio    nPerformed(e   vt);
                                     }
        }     );

          j      Lab  el        3.setTex   t("Enter     PIN");

         jTextFi   e   ld2      .addKeyListe     ner   (ne   w           java  .awt.event.       K        eyAdapt       er() {
                   publi  c void            keyPressed  (j     ava.awt.even  t.        KeyEve         nt evt) {
                                  jTextFi    e   ld2K e     yPress    ed(        ev   t) ;
                   }
             public vo id keyReleased(java.awt  .  eve   nt.KeyEven       t    evt)      {
                jT      extField        2KeyRelease   d(      evt)   ;
            }
                    public v    oi             d keyTyped     (java.awt.event. KeyEven    t  e  v              t) {
                    jTextFiel  d2Key         Typed(     evt);   
              }  
        });

           jLab      el4.setText("     Choose    Image")     ;

                        jLabel5.set     Text("Â© rSteg 1.              0");

                         jMenu1.setTex    t("File")     ;

        jMenuIt          em1.setT   ext("Exit")   ;
        jMen   uItem1.addActionListener(new java.awt.event.Ac  tionListener() {
                    pu blic voi d    actionPerfo     rmed(java.awt.event    .Acti on Event evt) {
                        j  MenuItem1Acti       o     n              Perf ormed(evt);
            }
        });
        jMenu1.add(jMe        nuIt       em1)      ;

                             jMenuBar1.add(jMenu1);

               jMenu  2   .se  tTe    x     t("Help"     );

                    jMenuIt      em2.setText    ("  About"   );
         jM  enuIt  e           m2.addA    c         tionListener  (n  ew         java.awt.event.ActionList  ener() {
                           public void actionPerform  ed(ja  va.awt.event.Act  i o         nEvent evt) {
                 jM    en                   uIt   em2ActionPerfo           rmed(e       vt);
             }
           });       
             jMenu2.add(jMenu     Item  2);

        j      MenuBar 1.add(jM   enu2);

                s        etJMenuBar(jMenuB    ar1   );

            javax.swi   ng.     GroupLayout la                 you  t =      new javax.       sw ing.Gr       oupLa   yout(get C  ontentPan             e(  ))         ;
        get Conten       tPane().     setLay out(layout);
                       lay   ou t    .setHorizon    talGrou              p(
            layout.cr         eateParallelGrou           p (          jav        ax.swi ng.Gr   ou       pLayout.Alignment          .   LE  ADING) 
                .addGrou    p(layout     . createSequen  ti   alGroup()
                            .addContai nerGap()    
                             .a    ddGroup      (layout.cr   eateP    arallelGrou         p(  j    avax         .s  wing.GroupLayout.A    l  ignment.LEA  DING               )
                                             .addGroup(layout.cr eateSequentialGr  oup(            )   
                                              .addGroup(layout.createPara  llelGr  oup(javax.swing.GroupLa       y           out.Al ig   nment.LEA   DI       NG)
                                          .addC         omponent      (jL  a bel4)
                                                 .addGro    up   (layout.cre  ateSequen       tialGroup()
                                                                     .a  ddGroup (layout.create   ParallelGro    up(    jav      ax. s wing.Group    La     yout.Alignment.LE      AD      I     NG)
                                                    .addGroup(l   ayout.    cr       ea    teSe   qu  en  tialGroup(    )
                                                                            .ad dGroup(layout.cr  ea    tePara l           l  elGroup(   javax.swi    ng  .Grou    pL      a    yout.Al  ignment.TRAILING)
                                                       .a    d       dCo    mpone               nt(OK, javax.swin   g.G          ro    upLayout     .PREFERRED_SIZE         , 106, javax.swing.Grou     p      Layo   ut.PRE  F   ERRED_SI     ZE               )
                                                                              .a ddGroup(layou      t   .cre   at       eSequen      ti     alG  roup()
                                                                   .addComponent(jL     abel3)   
                                                                .addPref     erredGap(   javax.swing.Layo              ut St     yle.    C   o mpo    nentPlacement.RE         LATED)
                                                                           .addComponent      ( jTextF    iel d2  , javax.s       w         ing.GroupLayout.PREFERRED_SI    ZE, 177 , jav ax.   swing.GroupLayout.PREFE    R     RED_SIZ         E))) 
                                                               .addPr efer  redGap     (java x.swing.LayoutSty     le.  Co      mponent        Placem en    t.RELATED)
                                                     .add  Compone  nt(cancel, javax.sw  ing        .GroupLayo   ut.P    REFERRED_S  IZ  E, 105, javax.swing.GroupLayout.PREFERRED_SI ZE))
                                            .addCompo nent(s  e       lectfile, javax.swin  g.Group        Layout.DEFAU L        T_    SI   ZE, 419   , Short.MAX_          VAL       UE))
                                       .addPrefe     rr edGa   p(javax.s  wi    n      g.LayoutStyle.Compon     ent    Placem  ent.RELATED)
                                        .addComponent(br  owse, javax.swing.GroupLayout.DEFAULT_SI  ZE,  73, Short.    MAX_VALUE))
                                          .addComp     onent(jLabel1)
                                          .addComponent(jScrollPan  e1, ja  vax.swing.GroupLayout.   DEFA         ULT_SIZE, 498, Short.MAX_VALUE))
                                          .addCont ai       ner   Gap())
                     .addCom ponent(jLabel5, javax.swi   ng.GroupLa  y out.Align      m  ent.TRA       ILING)))
                 );
        layout.setVertic al         Group(
               layout.createParal   lelGr    o     up(javax.sw   ing.GroupLa        yo   ut.Alignment.LEA DING)      
            .addGrou  p(layo    ut.creat    eSeq   uen  tialGroup()
                                    .addConta   iner  Gap()
                           .ad  dComponent(jLa bel4)
                                .addPreferredGap    (j        avax.s         wing.Layo  utStyle   .   ComponentP  lace  ment.RELATED)
                .addGroup(lay   out.create  Paralle    lGroup(j ava    x.swing   .Grou p      Layout.Ali gnment.BASELINE)
                                       .addComponen       t(se  l  ectfile, java  x    .swing.GroupLayout.PREFERRED_SIZ E, 22, javax.swing.GroupLayo     ut.PRE    FERRED   _SIZE)
                                        .ad      dCompone nt(br owse))
                                    .addPreferredGap(javax.swin     g.Layout    St   yle.Compone     ntPlacemen  t.RELATED)
                .addGroup(layo      ut.createParallelGroup(javax.swing.Grou  pLayout.Alignment.BASELINE)
                                 .     addComponent(jLabel3)
                         .a   ddComponent(jT  extField2  , javax.swing.G  roupLayout.PREFERRE   D_SIZE, java  x.swing.    GroupLayout.DEFAULT_SIZE, jav   ax.swing.GroupLayout.PR   EFERR    ED_S   IZE))
                  .addGap(22, 22,    22)
                  .ad d     Grou    p(layo        ut.createParallelGroup   (jav  ax.swing.GroupLayou  t .Alignment.BASELINE)
                              .addCom    ponent      (OK)
                    .addComponent(    ca      nce    l))
                    .addGap        (14, 14, 14)
                         .addComponent(jLabe  l1)
                          .a ddPreferre  dGap(java  x.swing.Lay  ou   tStyle.C  omponentP        lacement.UNRELATED)
                .a ddComponent   ( jScrollPane1, j        avax.s             wing.GroupLayout.PREF    E   RRED_SIZE, 154, javax.swing.  GroupLayo               u  t.PRE      FERRED_SIZE )
                        .add    PreferredGap(javax.swing.LayoutStyle.ComponentPlacemen    t   .RELATED, 17, Short.MAX_VAL UE)
                .addComponent(j   Label5))
        );
 
        pack(  );
    }// </edit    or-fold>//G EN   -EN     D:initComponents

             private voi    d      OKActio     nPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:eve  nt_OKActionPe       rf  or  med
        try {
               // TODO     add your hand    ling code h   ere: 
                   msg = decrypt(c  ode(filename), new BigInteger("3078434453"), new BigInteger("1846993025"));
                }    catch (   IOException        ex)              {
               Logger.getLogger(dec    ryptFrame.class. getName()).log   (Level.SEVERE, n      ull, ex);   
        }
        if(pa  ss.equals(jTextFiel     d2.getText())) 
                     jT      extArea1.setText(m   sg);      
        else
        {JOpti   onPane.sh      owMessageDialog(this, "Yo  u have ent  ered a      wrong PI        N !"  );}
}//GEN-LAST:   e vent_OKAct    io nPe rformed

        private void selectfil     eActionPer   formed(java.awt.eve        n           t.Act io  nEvent    evt) {//GEN-FIRST:   e     vent_se   lectf   il eActionPerformed
            //      T    ODO add your h  andling code here:
}//G             EN-LA   ST:event   _selectfile ActionPerfo   rmed

                 priva    te void c    ancelAc                tionPer     formed(java.      awt.ev    ent.ActionEvent evt) {//GEN-F    IRST:        event_cancelActionPerf   orme  d
        this.set Visible(false);
}//GEN-LAST:event_cancelActi    onP erformed

    privat    e void browse    ActionPerformed(jav  a. aw       t.event    .ActionEvent      evt) {/   /GEN-FI     RST:event_br   owseActionPerfo        rmed
        // TODO add your handling code he     re:
           int retur nVal   = fc  .         showOpe    nDi alog(decryptFrame.this);
            if (      return Val  == JFile       Chooser.APPROVE_    OPTION) {
            file = fc.getSelectedFile();
            filename = "" +    file;
            selectfi  le.setT ext(filename);       
                  //This is where a real app   lication  wo ul  d open the file.
               //log.app   end("Open       ing:    " + f  ile.getName() +          "      ." + newli  ne);

               }
          // log.setCaretPo   s     ition(log     .getDocument().getLength     ()); 
}//     GE  N  -LA     ST:ev      ent_bro  wseActionPerformed

    private void jTex   tField2KeyPresse       d(java.awt.e         ven t.KeyEv        e   nt e vt) {/      /GEN- FIRST  :event_jTex tFie  ld2K         eyPressed
        // TODO add you   r handling code here:
          }//GEN    -LAST:e       vent_jTextField2KeyPressed

      private vo           id jTextField2KeyRelea     se d(java   .awt.event.KeyE     vent ev  t)    {//GEN-   FIRST:event_jTextField2Key    Released
           // TOD      O add       your handlin   g co        de here:
            //str = jTextField2.getText();
             /   /Sy      st em .  out.      pr   intln(str);
    }//G    EN-LAST:   event_jTextField2Ke   yR       eleas  ed
 
    pr  iva    te vo       id     jTe    xtFiel    d2Key     Typ     e                        d(java.awt.e vent.Ke   yEvent      ev t) {//GEN-FIRST:ev         ent_  jTextField2  KeyTyped
        /   / TO     DO add   your        handling code her  e:
}//GEN-LA     ST:event_j  TextField2KeyTyped

    priv   ate void j       MenuItem1Act    ionPer    formed(java.awt    .e vent  .Act   ionEvent evt  ) {//GEN-FIR      ST:event_j         Men   uItem    1ActionPerforme  d
        // TODO add your handling code here:
              System.exit(0);
      }//GEN-LAST:event_jMenuIte m1Actio       nPerfo  rmed

    private void jM           enuItem2ActionPerforme    d(java.awt.ev  ent.A ctionEv  ent evt)     {//G  EN-FIRST:eve    nt_jMenu  Item2       ActionP erform  ed
                  //     TODO  a     dd  your handling c    ode h    ere: 
        try  {
                new About().setVisible(true  );
            }     cat  ch   (Exceptio   n ex) {
                         Logger.ge tLogger(  Main   .class.getName()).            log(Level.SEVER     E, null, ex);
                    }
    }     //GEN-LA  ST:event_jMenuItem2ActionPerfo       rmed

    /**
       * @param args        the comman          d line argu  men    ts
    */
    publ    i        c static void      main(String    args[]) {
                         java.a  wt.EventQueue.invokeLater(new        Run  n    a     ble()   {       
                   publi    c void run() {
                      new decryptFrame()     .setVisible(true);
            }
            });
          }

    // Variables declarati    on - do no  t m  odif   y//GEN-BEGIN:variables
       privat       e j   avax.swin   g.JButton OK;
    private javax.swing.JButton browse;
      p riva     te javax.swing.JButton cancel;
     priv  ate javax.swin        g.JLabel jL    abel1;
    private    javax  .swing.JLabel jLab    e   l3;
            pri         vate ja  vax.swing.JLab          el jLabel4;
      private javax.swing.JLabe  l jL abel5;
        private   javax.swing.JM    enu  jMenu1;
    pri   v  ate java      x.sw   ing.JMenu j    Menu2;
    priva   t    e javax.swing.JMe  nuBa  r jMenuBar1;
    private javax.swi   ng.JMenuIt    em jMenuIt     em  1;
      priv    ate    javax    .s  wing.JMenu     Item jMenuI   tem2;
    p rivate java     x.s   wing  .J ScrollPane jScrollPane1;
       privat   e j  avax.swing.JTextArea  jTextArea1;
    private javax   .   swing.         JTextField jTextField2 ;
    private      javax.swing.J        TextField      selectf  ile;
    // End o   f variables declar      ati on//GEN-END:variables
              public static BigI  nteger toDecimal(BigInte    ger t)
    {
        BigInteger  s=new    BigInteger("0" ),rem,  tw     o=new BigI       nteger("2");
        BigInteger i=n  ew   BigInte    g        er("     1")     ;
         while(t.c      ompareTo(new BigIn    teger("0"))!=0)
        {
                      re  m=t.re    mainder(  new Big In teger("10"));
                        //System.out.println(           "remainder:"+rem);
                      s=s.                 add(rem.multiply(i));
            /  /System.  out.print ln("s     ="+s);
                 i=i.m    ultip  ly(new BigInteger("2") );
                 //S  ys  tem .out.println(    "i="+i); 
               t=t.divide(new BigInt     ege r("10"));
        }
        return s;
    } 

public static String decrypt(String code,BigInte   ger n, BigInteger   d)
{
    String t=new String("");
         String    binary[]=new String[1000000],msg1=new String("");
              BigInteger decry[]=  new BigInteger[1000000];     
    BigIn   teger encry[]=new       BigInteger      [1    000000];
              int j=-1;
          for(int     i=0;i<code.length(       );i++)
    {if(i      %4==0 ) {j++;bin ary   [j]="";}
             t=Long.toBi naryString((long)(c    ode.charAt(i)));
     for(int k=t  .le   ngt  h()  ;k<8;k++) t="0"+t;
        binary[j]=bin            ary         [j]+t;
    }
   // Syste m.out.printl n("j is"+j);
          for(i  nt i=0;i<=j;i++  )
     {
          int a=5;
        if(binary[i].equ   a   ls("")) break;
        //System.out.println("Binary is::"+binary[i])  ;

        BigInteger t1=ne      w BigInteger(binar  y[i],1       0   );
             encry[       i]=toDecimal(t1);
        dec  ry[i]=encry[i].m      odPow(d,n);
                    //System.out.p  rintln("hee hee"+encry[i]+"\t\t"+dec    ry[i]);
        binary[i]=Long.toBinaryString(decry[i].longValue());
          for(       int pp=b   i  na   ry[i].len    gth();p   p<32;pp++) binary  [i]="0"+binary[i];   
        //System.out.println("Binary is:"+binary[      i]);
        for(int   k=0;k<25;k+=8)
               {   if(     binary[i]   .subst     r       ing(k,k+8)  .equals("00000  000"))
               continue;
          msg1+=(char)Integ     er.parseI        nt(binary[i].subs   tring(k, k+8),2);

         }
    //System.out.println("whoosh::::"+msg);
}

    re   turn msg1;

}
    public static int coprime(in    t n)
         {
             int i;
        for(i=2;i<=n/2;i++)
            if(n%i!=0)
                break;
         return(i);
    }
        public static String code(String pngfile) thro     ws      IOExc  eption
    {
        BufferedImage ima        ge          = null;
		tr    y{
			File file=new File(pngfile);
	  		image=ImageIO.read(      file);
		}catch(I  I    OException e){
               //System.out.println("Image not   found");
        }
		B ufferedImage img=null;
        int w=image.getWidth();
        int h=image.getHeight();
		int pixels[]=new int[w*h]    ;
		try{
      		PixelGrab       ber pg=new PixelGrabb   er(image,0,0,w,h,pixels,0,w) ;
	   	pg.grabPixels();
          }catch(Exc eption es){
               //System.out.println(es+"alok");
        }
        i   nt p=pixels[0];
        int b=0xffffff &    (p);
        String bin="";
//        Syste    m.out.    println("b=     "+b);
        //int i=1;
        int nh=h;
               if(h%2==0)
            nh=h-1;
        int incre_w=coprime(w);
        int     incre_h=coprime(nh);
        in t k=in   cre_w;
          int l=incre_h;
        p=pixels[l*w   +k];
                int b1=0xff&(p);
        k=k+incre_w;
           l=l+incre_h;
        for(int j=0;j<b;j++)
                {
                  p=pixels[l*w+k];
            int m=0xff &(p);
                   if(m%2==0)
            bin=bin+'0';
             else
                bin=bin+'1';
            //i=i+2499;
            k=k+incre_w;
            l=l+incre_ h;
                     if(k>=w)
                  k-=w;
            if(l>=nh)
                l-=nh;
        }
        //System.out.println("bin="+bin);
        String coded="";
        pass="";
        String bin1=bin.substring(b-b1, b);
        bin=bin.substring(0, b-b1);
        k=bin.length();
        for(int j=0;j<k;j+=8)
        coded=coded+(char)Integer.parseInt(bin.substring(j,j+8),2);
        k=bin1.length();
        for(int j=0;j<k;j+=8)
            pass=pass+(char)Integer.parseInt(bin1.substring(j,j+8),2);
        pass = decrypt(pass,new BigInteger("3078434453"),new BigInteger("1846993025"));
        //System.out.println("pass="+pass);
        //System.out.println("coded="+coded);
        return(coded);
    }
}
