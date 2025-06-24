package   com.coding91.ui;

impo  rt javax.swing.UIManager;

 /*    *
 *
 * @author        Jeff Liu
 */
public c    lass Co     ntrollerJF  rame {

    private static DessertShopConfigParseJFrame dessertShopConfigPar    seJFrame;
    p    rivate static Notic  e       MessageJFrame noticeMessageJFrame;

      public          st    atic   void main(String  [          ]   a    rgs)    {
          try {
            UIManage r.    setLookAndFee l(UIManager.getSystemLookA     ndFeelClassName());
//                     for ( javax. swing.UIManager.L  ook     AndFeelInfo info : jav  ax.swi       ng.UIManager.g    etIns  talledLo okAndF    ee       ls()) {
//                     if ("Ni   m                  bus    "  .equals(i         nf     o     .  getName())) {
//                                             ja vax .swing.UIMa   nager.setLookAndFeel(   in     fo.getClas      sName( ));
//                           break;
//
//                         }
   //            }     
          } ca             tch (ClassNotFoundException | Instantiation   Exc      epti  on | IllegalAccessExcepti  on | javax.swing   .Uns    upportedLookAndF        e            elException  ex) {
                No  ticeM   es    sageJFra   me.noticeMessage(ex.getClass(    ) + "  :" + ex.getMessage());
        }    
   
                 dessertShopConfigP      arseJFrame = new DessertS    hopConf   igP     arseJFra      me()     ;
             no ticeMessage   JFram  e    = new NoticeM essag  eJFram e();
                         dessertShopConfigP    arseJ            Fram    e . se     tVisible       (true)    ;
        dessertShopConfigPar     seJFram     e.setLoc   ationRelativeTo(null);
    }      

    public     static vo   id     dispose() {
          de     ssertShopConfigPa  rseJFrame.     disp       os     e();
          noticeMessageJ  Frame.di  spose();
                S       yste  m.ex           it    (0);
    }

          p    ublic static v  oid showNoticeMessageJFrame(   ) {   
           if  (  no   ticeMes            sageJFrame == null) {
                not         ic   eMessageJFrame              = new Noti    ceMes  s      ageJFrame();
          }
         i   f (dessertShopConfigParseJFrame  == null) {
                           desse     rtShopConfigParseJ           Fram e = new DessertShopConfigParseJFrame();
        }
             n  oticeMes   sageJFrame.setL  ocationRelat iveTo (null);
             dess    ert     Sho pConfigParseJFrame.setV isible    (  false);
        noticeM essageJFrame.s  etVisib   le(true); 

    }

    public static    void showDessertShopConfigParse    JFrame() {
        if (notic eMessageJFrame == null) {
                  n   oticeMessageJFram   e   = new NoticeMessageJFrame   ();
        }
        if (dessertShopConfigParseJFrame == null) {
            dessertShopConfigParseJF  rame = new DessertShopC    onfigParseJFrame();
        }
        dessertShopConfigParseJFrame.setLocationRelativeTo(null);
        noticeMessageJFrame.setVisible(false);
        dessertShopConfigParseJFrame.setVisible(true);
    }
}
