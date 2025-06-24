/*
     * ####################################################     #   ##
 * 
 * Copy  right (c) 2  013, A. Weinb   e  rger. All rights      reserved.
 *      ------------------------------------------------  --------
 */
pack   age ale.view.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.D    imension;
im    port java.awt.EventQueue;
import java.awt.FlowLayou t;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListene   r;

import javax.swing.BorderFa  ctory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swi    ng.JTextPane;
import jav    ax.swing.text.SimpleAttributeSet;
import  javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;   

import ale.controller.Main;
import ale.view.gui.GUIConstants;
import ale.view.gui.util.GUIStr ings   ;

/**
 * -----------------------------------------  -------- <br/>
 * Package: ale.view.gui.dia   logs <br/>
 * C    las     s     : DeleteSkinD  ialog      <          br/>
 * -        -    ---  ------     --        -----             -------    --                            <br/>      
 *                                                                                                                 <   br/>
 *                  The      <co        de>Dele                    teSk   inDi        a log</  co       de> con t    rol s if a             skin   reall    y s  houl  d be    delete    d.
 *                                                                                 <br/>
    *                                                                                 <br/ >
 * Las    t ed              ited: 15.05.2013 <br/>
 * ---------------------  ------    ----------   ----  -  ------- <br/>
        */  
public class DeleteSk    inD     ialog extends Dialo     g {

      p    rivat       e static        fi  nal lo ng serialVer     sionUID              = 1L;   
    privat  e final      JPan e       l basePanel =     n       ew    JPanel();
       pri   vate Stri   ng file;

    /*   *    
     * @par       am  filenam     e the fil ename of t  he skin .
           */                      
    pu  b li   c DeleteSkinDia    log(String  filename) {
        thi    s.file = filen       ame;

           EventQueue.i   n    vokeLater(ne         w Run  nable(     ) {
               @Ove  rride  
               public voi      d run() { 
                Del    et   e        SkinDialog.this.s   etSiz e(new Dim       ension(    450, 140));
                      DeleteSki nDialog.    th      is.setTitle(GU    ICons    tants. PROGRAM_TITLE);    
                                  DeleteSkinDial  og.this.setIconImage(GUIConstants.P  ROGRAM_ICON);
                   DeleteSkinDialog.this.setL           ocationR  elativ   eTo(null);
                  DeleteSkinDia  lo  g.this. g  etContentPane() .setLa   yout(new Bord erLay      out());
                             DeleteSk     inDialog.th     is.basePanel.setBorder(Bord  erFactory.cr ea    teE mptyBorder(  5, 5, 0, 5   ));
                    DeleteSkinDialo    g.this.basePanel.se tBack     gr     ou nd(GUIConstants.DEF AULT_BACK    GROUND);
                                 Delete     SkinDialo       g.   this.basePanel.setLay   out(new BorderLayout()  );
                Del      eteSkinDialo  g.th  is.setModa     lityType(Moda   lityTy  pe .APPL ICATION_MODAL);
                  D     eleteSkinDialog.th     is.setDefaultCl ose    Op        e    ration(D I    SPOSE   _     ON_CLOSE);
                    DeleteSkinDialog.this.  getContentPane().add(DeleteSkinDial    o  g.   this.  b  aseP  anel      , BorderLayout.CENTER);

                            c reate   (   );

                        DeleteSkinDialog.this.se       tVisible(    true);   
            }
        });
       }

    private vo      id create() {
        JPanel textPanel = new JPanel();
                   textPanel.s    etBackgrou  nd(GUICons    tants.DEFAULT_BACKGROU  ND);
        textPa   nel.setBorder(BorderFa     ctory.createTit   ledBo  rde r    (GUI      Str    ings. keyToL      ocated         String(GUIStrings.K        EY_DELETE DIALOG_TI    TLE)));
        textPanel.setL      ay   ou  t(new B     ord    erLayou t());
        this.basePanel.add(textPanel, Bord   er   Layout.CEN      TER);

        {
                      Sim     pleAttributeSet     center = ne w Simpl             eAttributeS   et();
                            StyleConstants.setAlignmen    t(center, StyleCon       stan   ts.ALIGN_CENTER      );

            JTex tPan   e l   b    lMessa   geLabel = new JTextPane(  );
             lblMess ageLa  bel.    setFocusab  le(false);
                lblMessageLabel.setText(GUIStrings.k    eyToLo ca     te    dString(GUIStri     n     g  s.KEY_DELETEDIALOG_MS G));
                  lblMessageL     a    bel.se    tBo    rder(Border Fac          tory.cr         e      ateEmptyBord  er(20,     5, 5, 5))   ;
                     lblMessageLabel.se    tEdi  table(false);
                     lblM     essageLabel.setFont(GUICon    s         tants.DEFAULT_MESSA GE_FONT        )   ;   
            l    blMess   ageLabe  l.setBackgr     ound (GUICon         stant    s.DEFAU LT    _BACKGROUND);
               t  extPan  el.  add(lblMes sageLabel);

                     StyledDocumen   t doc = lblMessageLabel.getStyle    dD ocument();
                         doc.setPara  graphAttributes(     0, doc.getLengt       h() , cent      er, fal              s  e);
           }
 
        JPan     el   buttonPanel   =     new JPanel();
        butt       o  nPanel.     setL  ayou    t(new F   lo    wLayo      u  t(FlowLayout.RIG   HT));
        buttonP  anel.setBackground(GUIConstants        .DEFAULT_BACKGROUND);
              getConte  ntP         ane  ().add(butto  nP     an el, Bord erLayo     ut.  SO   U   TH);

         {
                f  inal JButton okBut      ton = new   JBu tton(G         UIStr   ings.k  eyToLocate dString  (GUIStrings.KEY_DELETEDIALOG_OK));
                 okButton.setB   ackground (GUIConstants.  DEFAULT_BAC  KGROUND);
                  o     kButton.addActio   nList     ener(new A    ctionListener()   {

                        @Override
                  public void actionPerformed(ActionE     vent     arg   0) {
                    okB    ut  ton.setEnabled(  false);
                           dispos    e();
                    M     ain.deleteSkin(DeleteSkinDialog.th  is.file);
                }
                       });
            buttonP anel.add(okButton);
            okBu   tton.grabFocus();

            JButton cancelButton = new JButton(GUIStrings.key   ToLocatedStr ing(GUIStrings.KEY_DELETEDIALOG_CANCEL));
                           cancelButton.setBa  ckgroun d  (GUIConstants.DEFAULT_BACKGROUND);      
            cancelButton.addActionListener(new ActionListener() {
   
                @Override
                public void a   ctionPerformed(ActionEvent a              rg0) {
                    dispose();
                }
            });
            buttonPanel.add(cancelButton);
        }
    }
}
