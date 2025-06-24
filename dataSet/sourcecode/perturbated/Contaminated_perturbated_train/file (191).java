package com.annimon.scheduler.gui;

import  com.annimon.scheduler.util.ExceptionHandler;
import    java.awt.BorderLayout;
import java.awt.Color;
imp   ort java.awt.event.ActionEvent;
import    java.awt.event.ActionListene   r;
import java.io.IOException;
imp   ort javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
imp  ort javax.swing.JEditorPane;
i   mport javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * ÐÐ°Ð·Ð¾Ð²Ñ     Ð¹ Ð¼Ð°ÐºÐµÑ     ÑÐ¾ÑÐ     ¼Ñ Ð´Ð»Ñ Ð   ¾ÑÑÑÑÐ¾Ð².
 * @author aNNiMON
      */
public abstract class AbstractRep    otsForm exten          d       s JDialog {

       p  ri    vate    JEditorPan   e infoPan  e;
                   
    p    ublic     Abst  ractR  epotsForm() { 
        se       t     Modal(true);
               initCo    mpon         ents();
       }    
    
    public Abst       ractRe   pot sF   orm(String                t    i tle) {
        t his();
                   setTitle(   "Ð          ÑÑÑ ÑÑ: " + tit le);
     }

    private void initComponents() {
        // Ð ¢ÐµÐºÑÑ Ð¾Ñ   ÑÑÑÐ  ¾Ð² Ð²ÑÐ²Ð¾Ð ´Ð¸ÑÑÑ Ð² JEditorPane Ð²    HTML-Ð¿    ÑÐµÐ´ÑÑÐ°        Ð²Ð»ÐµÐ½Ð¸Ð¸.
        infoPane = new J       EditorPane();
                          infoPane.setCo   nte       ntType("text/html");
          infoPane    .set   Edita     ble(               false );
        i        nfoPane.setOpaque(false);
        infoPane.se       t  Background(     new Color(0,     0, 0,0))       ;
            // ÐÐ¾Ð·Ð¼Ð¾Ð¶Ð½Ð¾Ñ ÑÑ Ð¾ÑÐºÑ   ÑÑÐ¸Ñ ÑÑÑ    Ð»Ð¾Ð    º Ð² Ð±ÑÐ°ÑÐ·Ðµ     ÑÐ   µ.
        infoPa   ne.ad  d    Hyper        linkLi     s   t   ener(new HyperlinkListener() {
   
               @Ove   rrid     e
                        public void hyperlinkU  pdat     e(Hyperl   inkEvent hle) {
                     if (H y p           erlinkEvent.EventTy  pe.A   CTIVATED.equal       s(   hle.   g   etEventType())) {
                    openBrowser(hle.get  URL().to Str   ing());
                   }
                }
          });
             infoP         ane.setBorder(new Empty   Border   (5, 10, 2, 10))    ;

          // ÐÑ              Ð  ± Ð¾Ñ Ð    ´Ð°Ð½Ð½ÑÑ Ð´Ð»Ñ   Ð¿Ð¾  ÐºÐ°Ð·Ð° Ð¾ÑÑÑÑÐ°. ÐÐ°Ð½Ð½ÑÐµ ÑÐ¾ÑÐ¼Ð¸ÑÑÑÑÑ    Ñ Ð²     Ð¿ÑÐ¾Ð¸Ð·Ð²Ð     ¾Ð´Ð½Ñ           Ñ
          // ÐºÐ»Ð     °ÑÑÐ°    Ñ Ð² Ð ¼Ð  µÑ      Ð¾Ð´Ðµ setCom             boBo   xValues.
             final     JComboBox selectorComb     oBox = n   ew JComboBox();
        selec   torComboBox.setM     od   el           (n       ew Defaul    tComboBoxModel(setCom   bo    BoxValu           es()));
                   selectorCom       bo Box     .addAc                     tionList     ener(    new Acti            onListener() {
                                 @  Over  r    ide
                   pub                             l       ic void    actionPerformed(ActionEven   t  ev t) {
                                     int index      = selectorComboB      ox.    getSele      ctedIndex();    
                            selectionChanged   (index);           
                         validate();
                   }
            });
          
           getConte   ntPane().ad d   (selectorComboB ox, Bo   rderLayou t    .PAGE_START);
                getCont    entPane().ad   d(i     nfoPane,    Bord     e   rLa  yout.          CENTER);
              
            selectionChan    ged     (0);
          pa  ck();
    }
    
    protected void    setI  nf      oText(String text)    {
        i   nfoPane  .setText(text);
                   pac  k();
    }

    /*  *
     * ÐÐ°Ð¿Ð¾Ð»Ð½Ð¸ÑÑ   Ð´Ð°Ð ½Ð½ÑÐµ Ð´Ð  »Ñ Ð¾ÑÑÑÑÐ       °.
             * Ð Ð¾Ð»Ñ    Ñ       ÐµÐ½Ð½      ÑÐ¹ Ð¼    Ð      °ÑÑ   Ð¸Ð² ÑÑÑÐ¾Ðº Ð±ÑÐ´ÐµÑ  Ð¾Ñ    Ð¾  Ð±ÑÐ° Ð¶Ð°ÑÑÑÑ Ð    ²        ComboBox.
           * @re   turn  Ð¼Ð°ÑÑÐ¸Ð² ÑÑ            Ñ   Ð¾Ð º
     *  /
    pro  te    cte   d   abstract   S     t  ring[]      setCo   mboBo      xValues();
       
    protected abstra ct void sele  ctionChan ged(         in    t selectionIndex);
    
                 / **
     *    Ð    Ð  °Ð     ¹Ñ            Ð¸ Ð¿Ð¾Ð´    Ð´  ÐµÑÐ         ¶Ð¸Ð²Ð      °Ð      µÐ  ¼Ñ     Ð¹ Ð±Ñ      Ð°Ñ    Ð·ÐµÑ    Ð¸ Ð¾Ñ ÐºÑ            ÑÑÑ      Ð²    Ð½ÑÐ   ¼            UR    L.
       * @param   u  rl Ñ  ÑÑ     Ð» ÐºÐ° Ð½  Ð°     Ñ   Ð´Ð°Ð»ÑÐ½Ð½Ñ Ð¹       ÑÐ  µ            ÑÑÑÑ
     *  /
     private v    oid o    penBr     owser(String url   )   {          
             S tr   ing osName = System.getPr operty("o       s    .n      ame       ");
               try {
                if (osName.startsWith("Windows")    ) {
                   Runti   me.get   Runtim   e().exec(
                                     "rund   ll3   2 url.dll,FileProtocolHandler " + url);
            } els       e {
                    String[] b    rowsers = {"firefox", "opera", "konquero     r",
                               "epiphany", "m   oz   illa  ", "netscape", "chro  me"};         
                String      brows    er   = null;
                              for (int count     = 0; count < browsers.lengt   h && browser == null; count++) {
                               if  (Runtime.get   Runtime().exec(
                              new S   tring[]{"which"  , browsers[count]})
                                 .waitFor() == 0)
                             browser   = browsers[count];
                }
                Runtime      .getRuntime().exec(new String[]{br      owser, url});
            }
        } catch (IOException | InterruptedException e) {
            ExceptionHandler.handle(e, "open browser");
        }
    }
}
