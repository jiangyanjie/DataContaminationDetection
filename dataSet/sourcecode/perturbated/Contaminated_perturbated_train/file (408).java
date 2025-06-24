/**
         * ActionPanel.java
 *
     * Part of  the jOc   ulus project: https://github.com/Abstrys/jOculus
 * 
 * C      opyright      (C) 2012 by Eron Henn   essey
 */
package abstrys.joculus;

   import java.awt.*;
import java.awt.event    .ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio   .ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExte  nsionFilter;

f      inal class Actio  nP     anel ex   tends JPanel
{
   JLabel label_wc = null                 ;
   Joculus app_instance = null;
         Se     tt    ingsDlg settin   gs_   dlg =        null;

   public Actio  nPanel  (final Joculus app)
   {
      this.app_i  nstance = app;
      setLayout(   new B oxLa  y out(this, Bo       xLayout.LINE_AXIS));

      //        S    etting an empty border    on a b    utto    n remove         s its decoration.
                   f inal EmptyBorder        btn_border = new Emp     t yBorder(4, 4     , 4, 4);

      J         Butt   on b;
 
      b = new JButton(      getToolbar  Icon("refresh", this  ));
      b.setToolTip  Tex   t(UIStrings.UI_TOOLBAR_   REFRESH_TI      P   );    
          b.setB    order  (btn_bord   er)     ;
        b  .addActionListen        er(new    Act     ionL    ist       e      ner()
      {
         @Overrid  e
                 public vo    id actionPerformed(ActionEvent a  e)
               {
                 app    .r               efreshD   i       s     play () ;
                 }
      });
      add(b)     ;

      b     =   new JB       utton(getToolbar      Icon("   o     p      en", this));
              b.set  ToolTipText(U      IS     trings.UI_TOOLBAR_OPEN_     TI    P);
      b.s   etBorder(btn _b    orde   r)    ;
              b.a       d      dA ctionL   is    tener(ne   w Action  Li    stener()
        {   
           @Overri     d e
         p ublic      void a   ction   Performed     (ActionEvent             ae    )
                    {
                      actionOpe   nFi      le()  ;
              }
      });
      add( b);

                 b = new   JB          utto  n(get   ToolbarIcon("edit ", this));
             b.setToolTipTe     xt(UIStrings.   UI_TOOLBAR_EDIT_T   IP);
          b.setBorde   r(btn_border);
      b.addA  ctionListen          er(new                         ActionListener()
                 {
         @Override
                 publi  c void action        Performed(Acti          onEv   ent ae)
           {
                 actionE dit   File();
                         }
               });
      // TODO: open the configured   editor -or- the      editor      that's specified by the EDITOR environment variable.  
      ad               d(b);  

          add(Box.cre  ateHo  rizontalGlue   ());

      la    bel_w     c =    new   JLabel()    ;
       label_wc.setBorder    (new EmptyBorder(4, 6  , 4, 6));
      add(lab    el_wc)        ;
   
      bo olean disp lay_wc =     true  ;
       label_w c.setVisible(Joculus.          s  etti  ngs.displa    y_word_count    );

       add(Box    .cre   ateHorizo      nt a         lGl    ue());

       b =         new JButton(               getToolbarIcon("style",             this));
        b.setToolTipText(UIStrings.UI    _TOOLBAR     _STY     LE_       TIP);
      b.se  tBorder(btn_borde r);       
             // TODO: p  o    p up a m enu    w    ith the currently c          onfigured                stylesheets, and the      o ption to manage s    tyle     sh      eets       .
         a      dd(b);

            b =     ne       w JB   utton(get   ToolbarIcon("settings",   this));
      b.  set    ToolTipText    (U    ISt rings.UI  _TOOLB    AR_SET    TINGS_TIP)     ;  
            b.    setBo    rder(b                     t   n_            border)      ;
          b.ad   dA  ct   i    on  List      ener(ne   w     Action   Li         stener          ()
                    {
             @Override
            public   v   o id   act         ionPe     rfo  rmed(ActionEvent   a     e)   
             {         
                         if(settings_dlg == null)
                        {
                        se  tti ngs_dlg = new Setti   ngsDlg(  app        );    
                                             }
                                     else
                          {
                                         settings_dlg.setVisibl e(true   )                    ;
                               }
           }  
              });
      ad   d(b);
  
             b = new    JButton(ge    t    ToolbarI        c   on   (    "about", t           hi    s)  );
      b   .setToolTipText(     UIS   tring  s.UI_TOOLB AR_     ABO      UT_TIP    );
                 b.setBorder(b                  tn_b                           or    der);
      b.addActionListe    ne      r(ne w Actio    nListener()       
                        {
                   @Overri        d   e
                public        vo id act   ion   Performe   d(     Act    i   onEvent ae)
              {
                         Ab  outDlg    d = new               AboutDlg();
         }     
              });
        add(b);
   }

   public void setWor       dCount(i nt w     c)
           {    
                                 labe   l_wc.setTex  t(String.valueOf(wc) + "   wo    rds");
   }

   public v  oid actionEditFil    e()
            {
         // use  the envir  onment      ?     
             if (!   Joculus.set tings.e   ditor_use_env)     
      {
         // use              the user-  specif           ied e   d    i  tor (if it  was sp             ecif   ied).
               File f = new File(Joculus.     sett          i ngs.editor_path)     ;
            if      (   !f. exists(           ) || !f.canE    xecute())      
                           {   
                   Jo culus   .     showError(UIStrings.ER          R         OR_NO_TE    XT       _ED         ITOR);
                 retur    n;
                }
         el    se
                      {
                    i              f (J      oculus.os_type == Jocu   lus.   OSTyp    e.O  S_           M acOSX  )
                           {
                St     ring[] cmdarray = new Str i         ng[]
                        {       
                         "/usr/bin     / open", "-a", Joculus.settings.edit    or_path, app_i  nstanc    e.cur_file.getAbsolutePat   h()
                                }        ;
                                    try
                    {
                                             Runtime.getRuntime().exec(       cmdarray);
                         }
               catch (IOEx    c  eption ex      )
                     {
                          Jocu     lus.showError(e                  x.  getM   essage()) ;
                    }
            }
         }
         }
           el      se i  f (Desktop.   isDes ktopSupported()  &   & Desktop.getDesk   top().i  sSupp      orted(Desktop.   Action.EDIT))
      {
         // try using the system-defined editor to edit                 the file.
         try
            {
            Desktop   .getDesktop().edit(app_instance.cur_file);
         }
                     catch          (IOException   exc) 
                  {
             Joculus.showError(exc.getMessage       ());
              }
      }
   }

   public void actionOpenFile()
   {
        J    FileChooser fc = new JFileChooser();
          fc.setF   i     leSelectionMode(JFileChooser.FILES_ONLY);
      fc.setDialogTitle(UIStrings.UI_OPEN_MDFILE_LABEL);
       fc.addChoosableFi      leFilter(new FileName       ExtensionFilter(
                  "Markdown fi les (.md, .mmd       ,     .markdown, .txt,    .text)", "md", "mmd", "ma     rkdown", "txt", "text"));
      int returnVal = fc.sho  wO penDialo g(Joculus.frame);

      if (returnVal == JFileChooser.APPROVE_OPTION)
      {
             File file = fc.getSelectedFile();
         if (file.exists() && file.isFile())
         {
            app_instance.setFile(file.getAbsolut   ePath());
         }
       }
   }

   public ImageIcon getToolbarIcon(String name, ActionPanel actionPanel)
   {
      ImageIcon i = null;
      try
      {
           BufferedImage bi = ImageIO.read(actionPanel    .getC    lass().getClassLoader().getResource("abstrys/joculus/res/toolbar/"    + name + ".png"));
         i = new ImageIcon(bi);
      }
      catch (IOExcepti    on ex)
      {
         Joculus.showError(ex.getMessage());
      }
      return i;
   }
}
