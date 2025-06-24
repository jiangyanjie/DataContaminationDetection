/*
      * This file is  par       t of ViaProxy -   https://github.com/RaphiMC/ViaProxy
 *          Copy  right (C) 2021-2024 RK_01/Raphi  MC       and contri   butors
 *
 * This program is free software:    you     can       re   distri       bute it and/or modify
 * it under the terms of    the GNU Genera    l Public Lice    nse as pu   blished by
 * the F  ree   Soft  ware Foundation, ei         ther              version 3 of the Li    cense, or
 * (at you    r opt  ion)       any later     ver  s         ion.
 *
 * This program is dist     ributed in the hope th          at it will be u    seful,
 * but WITHOU     T ANY    WARRANTY; w ithout     even   the   implied     warranty        of
 * MERCHANTAB  ILITY    or FITNESS FOR A PARTIC   ULAR PU    RPOSE.  See the
 * GN    U General Public Lic     ense for more details.
    *
 * You should       hav   e r  eceived a copy of the      GNU Gen  eral Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package    net.raphimc.viaproxy.ui.impl;

import net.lenni0451.commons.swing.GBC;
import net.raphimc.mi    necraftauth.MinecraftAuth;
import net.raphimc.minecraftau   th.step.msa.StepMsaDeviceCode;
import net.raphimc.viaproxy.ViaProxy;
import net.raphimc.viaproxy.saves    .impl.accounts.Account;
import net.      raphimc.viaproxy.saves.impl.accounts.Be   drockAccount;
import net.raphimc.viaproxy.saves.impl.accounts.MicrosoftAccount;
import net.raphimc.via   proxy.ui.I18n;
i mport net.raphimc.viaproxy.ui.UITab;
import net.raphimc.viaproxy.ui.ViaProxyWindow;
import net.raphimc.viaproxy.ui.p   opups.AddAccountPopup;
import net.raphimc.viaproxy.util.TFunction;

import javax.s  wing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurren     t.TimeoutException;
import java.util.function.Con   sumer;

import static net.raph   i     mc.viapr      oxy.ui.Vi  aProxyWindow.BODY_BLOCK     _PADDING;
import static net.raphimc.v       iaproxy.ui.ViaProxyWindow.BORDER_PADDING;

public cl   ass Accounts      Tab    ex    ten   ds UI   Tab { 

    private JList<Acco  unt> accountsLis  t;
    pri     va        te JB utt    on addMicr     osoftAccoun    t Button;
    pri   vate JButton a    ddBedrockAccount    Button;

    private AddAcc  ountPopup addAccoun       tPopup;
    privat             e          Thread add  Th  read;

     pu blic AccountsTab(f inal      ViaPro       xyWindo          w frame) {
             super         (frame, "acco   unts");
        }

    @Override
    p    rotected  void init(J Pan   el contentPane  ) {      
        JPanel body = new JPanel();     
          body.setLayo  ut(new      Gri   dBagLayout());

                 int gridy = 0;
              {
                       JLab   el  i       nfoLabe    l = new JLabel   ("<htm   l><p  >" + I18n.ge t("tab.accounts.description.line1") + "</    p><br><p>" + I18n.get("tab.acc   ounts.descripti on.line2") +    "<    /p ></html> ");
            GBC.create(body)  .grid(0,         gri     dy        ++)  .weightx(1).insets(BORD   ER_PA   DDING, BORDER_PADDING,       0 , BO     R  DER_PADDING).fi  l      l(GBC.HORI   ZON           TAL).add(infoLab  el) ;
                }
        {
              JScrol lPane scrollPane = new J    ScrollPa    ne(); 
                De    faultL    i   stMode  l<A      c       count> model = new    Default     Li s         tM  odel< >();
              t  h   is  .    accoun   tsList   =    new J  L  is         t<>(model);
                         this.accountsList.addMouse      Lis     tener (new       Mou seA   dapte  r(   ) {
                      p u                 blic void    mousePressed(Mou  s          eEvent e)      {
                     if         (    SwingUtilities.isRig    htMouseButton   (e)) {      
                                           i   n  t    row = AccountsTab.this.accou  ntsL     ist.locationToInd      ex(e.g    etPoint());
                                 AccountsTa      b.  this.a    cc     ountsList.setSelec  tedIndex(        row);
                                                       }      el     se   if (e.getCl ic kCount    (     ) == 2) {       
                                   int               index =      Ac       counts Tab .this.accoun   tsList.get     S e    lectedIndex (   )   ; 
                                                 if (ind   e                    x !=                      -1) Accou nts T    a            b          .this.markSelecte  d(index);
                                       }
                                            }
                         });
                   t  his.ac         countsList.add     Key    Listener (ne    w KeyAd    apte r(        ) {
                             @Override
                public voi    d ke   y      P        r  es          sed  ( KeyEvent e)   {
                                                   int  ind     ex =       A  ccounts   Tab.this.    accountsLis t.getSelecte      dIn   dex()  ;
                        if (i        ndex == -1      ) return;  
                               if (e.getKe   yCode() == Key  Event.VK      _UP) {
                                                       Accou     nts      Tab.   this.move Up(index);
                          e.consum    e()  ;
                            } else i         f (e.g etKeyCode()  == Key           Event.VK_DOWN)        {
                                            Acc  o unt    sTab.this      .   moveD  own(index);
                                         e.con     s  u me(  );
                             }
                     }
            });
                 this.accountsList.se     tCe   llRenderer(new Default      ListCellRende  rer() {
                      @Ove       r   r  id  e
                            publ   i    c C              o   mponent      getListCellRendererCompone  nt(JList <?> lis t, Object valu  e, in        t index, boolean isSele cted, bo    olean c    e    llH         asFocus) {
                                 Defaul  t      ListC        ellRenderer compo     nent              = (De   faultLis               tCellRende    r  e  r) su per.g      etList Ce  llRendererComp            onen     t (list , v    a          lu    e,    index, is  S  e     lected, ce      llHasFocus);      
                                         Acco    un t account = (      Account)                va  lu e;
                                            if    (ViaP   roxy.getCo nf     ig       ().getAccount()   ==         ac  c   o  unt) {  
                                              component.s        etTe    xt("<  html><sp      an style=\"c       o   lor:rgb(   0, 1  80,   0    )\     "      ><b>"     + account.getDis             playSt       ring() +  "        <    /     b></span></html>")     ;
                                      }   else { 
                                       componen   t.s   etT  ex    t(account.g  e      tDispla            ySt   ring    ())        ;
                            }
                                                      retu      rn componen   t;
                         }
                        })    ;
                    scrol   lPane   .setViewpo    r       tView(this.a    ccounts          L       i  st);
             JPop         upMenu contextMenu      = new JPopup   Menu();    
                                {
                            J   M en     uItem  select  Item = n  ew J Men            uItem(I18              n   .g  et("tab.acco      unts.list.context_menu.s        e  lect"))        ;
                         sele     ctItem.    addAct        io   nListener(event -> {
                                     int ind  ex = this.           accoun     t sList.getSel     ectedIn    dex()    ;   
                                           if (index != -1)      thi  s.markS  e  lecte    d  (in d      ex);
                              });
                   contextMenu.add(select              Item);
                         }
                    {
                          JMenuIte  m rem oveIt            em = n   ew  JMenuItem(I1          8n.get("tab.a   ccounts.       list.context_menu.remove"));
                              r  e   moveItem            .addActio   nL  istene        r(event -> {
                            int in   dex =       this   .ac  countsList.    getSelectedI             ndex();
                        if ( in   dex            != -1)      {
                                                         Account removed   =    mo   del.remo  ve(i             n      dex);           
                                                                          ViaPr   oxy.    get         SaveManage r().ac        counts    Save.re move Account(removed   );
                                   ViaPr   oxy.getSaveManager().sa     ve();
                                  if    (Via   Pr   oxy.           getConfig().ge  tA  ccount(   ) == removed         ) { 
                            if (model.isEmpty()) this        .m   arkSelected(   -     1 );
                                          e  lse thi   s.m  arkSele  cted(0);
                                       }
                           }
                       if (index < model  .g    etSize())      this.    accountsLi     st.setSelectedI ndex(i ndex);
                              else i        f (index > 0)  this  .ac     countsList.se     tSe      l ec   tedInd ex(index - 1);
                      });
                     c   o    nt    extMenu    .ad   d(removeItem);  
            }
                            {
                    JMenu      Item m                  oveUp =   new JMen  uItem(  I18n    .get("tab.acc     o   unts.lis          t.context   _menu.      mo             ve_up")      );
                       mo   v  e     Up.addActionL      is tener(e             vent -         > {
                             i    nt    index   = this.a  ccountsL  ist.getS electedIndex();
                                   if          (index       != -1) this.m    oveUp(index);    
                                       });
                        conte  xtMenu.add(m   ove    Up);
                   }
               {
                            JMe    nuItem moveDown = new     JMenuItem(I18  n.get("tab.account     s.list.c   ontext    _me   nu.move_  down"));
                 move      Down.  ad    dActio   nListener(event     -> {
                       int index =          this. a    ccount            sL     i   st.    getSelec   tedIndex();
                    if (index    != -       1  ) this  .mov                             eDown(ind ex);
                            });  
                           contextMenu. add    (move   Down);  
             }
                           this         .account sList.setC    omponentPopupMenu(con          textMenu );
                    GBC.create       (body).g   rid(0,  gridy+  +).weight(1, 1).ins  ets(BODY_BLOCK_PA        DDING,     BORDE    R_P          A DD       ING, 0, BORDER_PADD    IN   G).     fi   ll(GBC.BOTH).ad       d(scrollPane);
          }
        {
                       f          inal JPanel addButtons      = new JPane        l();
                 addButtons.setLay       out(new Gr     i  d    L    ayout(1, 3,  BOR          D  ER_PADDING, 0));
                             contentPane .add(addButtons);
            {
                                        J   Button     addOfflineAccoun    tButton = new      JB    utto  n(I18n.get("ta  b.  accoun     ts.a   dd_offline.label"));
                a   ddOfflineAccountButt   on    .        addActionL   is             tener(   e         vent -> {
                                Strin    g   username = JOptionPane.showInputDi  alog(th               is.viaPr  oxyWindow, I18    n.get("ta   b.accounts.     add_of     fline.enter_      u sername"),    I1  8n.get ("tab.ac   c  ou     nts.add.title")   , JOp  tionPane.PLAIN_MES   SAG E         );
                        if (     user n       a  me           != nul      l &   & !use  rname.trim(        ).isEmpty(    )) {
                                            Account ac  count = ViaProxy.getSaveManager().accou  ntsSave.addAccount(username);
                           ViaProxy.  ge     t   SaveManager().save();
                           this.addAc  coun     t(accoun      t   );
                            }
                            });
                      addBu    ttons.add(addOfflineAccountBut     ton);
                  }
               {
                        this.addMicrosoftAccoun   tButton   = new JBut  ton(I18n.ge  t("tab.acco unts   .add_mic     rosoft.l        a             bel"));
                th   is.addMicrosoftAccoun    tButton.ad     dActionListe   ner(event   -> {
                                 this.ad  dMicrosoftAccou        ntButton.s                   etEnabled(false );     
                              this.handleLogin(m         saDeviceCode  Consumer -> {
                            return new MicrosoftAc  count(M  icrosoftAccount.DEVICE  _CODE_LOGI   N.getFr               omInput(M     ine cra  ft  Auth.c    reateH   ttpClient(), new StepMsaDeviceCo   de.MsaDeviceC      odeCallback(msa  D   eviceCodeConsumer)));
                       });
                          } );
                                addButto  n s.              add(thi          s.addMicro  softAccountButton)  ;
                      }
            {
                   this   .addB   edrockAccountButton = ne     w J  Butt  on(I18               n.g et("tab.accou  n   ts.add_ be   dro    ck.label"));
                             thi  s.addBedroc     kAccountButton.addActio  nList ener(event -> {
                                     t    his.           a     dd BedrockAcc  ountBu    tton.set  Enabled(     f  a ls  e)  ;
                            this.han             dleLo  gin  (msaDeviceCodeConsu   mer ->        {
                                    return new B        edrock  Account(Bedro   ckAc     count.DEVICE_C  ODE_LOGIN.getFromInput(Minecr aftAuth.cre  ateHttpClient(), new S tepMsa  DeviceCode.Msa    D   e         viceCodeCallback      (msaDeviceCodeCons  umer)));
                         });
                                       });   
                        add    Buttons.     add(this.addBedrockA  cco         untButton)   ;
             } 

                        JP      a    nel border = ne     w JPan    el  ()     ;
                 border.setLayout(n  ew      GridBagLay  out());  
                 bord  er.setBorder(Bo  r  derFacto    ry.c      re ateTit ledBorder(I18n.get("tab.accounts.ad  d.title")));
                        GBC.create(b order). grid(  0,   0).weightx(     1).insets(  2, 4, 4, 4).fill(GBC.HORIZONTAL ).add(  addButtons);

            GBC.create(body).gri         d   (0, gridy++).wei          ghtx(1     ).insets(BODY_BLOC  K_PADDI   NG, BORDER_PADDING, B  ORDER   _PADDING, B      ORDER_P     ADDING).fi  ll(GBC.HORIZON   TAL  ).add(border);
        }

           contentPane.setLa    y    out(          new BorderLayout());
           contentPane.ad        d(body, B orderLayout    .CENT   ER);

        ViaProxy.getSav  eManager().accountsSave.getAccounts().forEach(this::addAccount);  
        DefaultL  istMod  el   <Account> model = (Defau  ltLis  tModel<Account   >) thi  s.a         ccountsList.g e    tModel()     ;
              if (!model.isEmpty(       )) this.markSelected(0    );
          }

        private vo id closePopup  () { //   Might        be getting       called multiple times
         if (th    is.addAccountPopup != null)          {
            this.addA   ccou  ntPopup.markExternalClos         e();
            thi     s.addAccountPopup.setVis  ible(false);
              this.addAccou ntPopup.disp        ose();
            this    .    addAccountPopup = null;
        }
         this.addMicrosoftAccountButton.s    etEnabl ed(true);
            this.addBedrockAccountButton.s etEnabled(tru      e);
    }

     private void addAccount(f        i           nal Account a    ccount)          {
          Defau   ltListM  odel<Acco        un  t> model = (Defaul tListModel<Acc    oun  t>)     this.accou     ntsList.getModel();   
        m    odel.a d dElement(account);
    }
   
    publi c vo        id markS   elected(final i  nt     index) {
                if (index < 0 || index >= this.acco untsList.getM     odel().g   etSize()) {
                       ViaProx   y.getConfig().setAc   count(n  ull);
                          return;     
        }

          ViaProxy.getConfig().setAccount  (ViaProxy.getSaveManager().a      ccoun   t    sSave.   getAccounts().get(       index))  ;
           this       .accountsList.repain    t  ();  
    }

         pr   ivate voi   d       moveUp(final int    index) {
         DefaultListM      od     el<Account> model = (DefaultListModel<Ac   count>) this.accountsLi  st.getModel();
        if (mo   del.getSiz   e(    ) == 0) return;
        if (index =  = 0) return;

        Acco unt acco   unt = mo del.rem   ove(index    );
        model.a dd(i         ndex - 1, account);
          th    is.accountsList.setSelectedIndex(index - 1);

         V     iaProxy.getSaveManager().a       ccount   sSave.remo    veAcc   o u nt(account); 
        ViaProxy.getSaveM   anager().account sS    ave.addAccount(i   ndex - 1, account);
        ViaProxy.g etSaveManager().save();
    }

    private void moveDown(final int         ind    ex) {
        DefaultListModel<Ac     count> model = (Def au       ltListM    odel<Account>) this.accou  ntsList.getMode  l();
        if (model.g   etSize     () == 0) re  turn;
          if (  index == model.getSize   () -  1) return ;

             A  ccount account = model.remo   ve(index);
          model. add(index + 1, account);
          this.a  ccountsList.setSe   lectedIndex(index + 1);

        ViaProxy.getSaveManager().accountsSave.removeAccount(ac   count);
           ViaProxy.getSaveManager   ().accountsSave.addAccou nt(index + 1, account);
        V        iaProxy.getSaveManager().sa ve();
    }

    private void handleLog    in(final TFunction<Consumer<StepMsaDeviceCode.   MsaDev       iceCode>, Account      > requestHandler)  {
        this   .addThread = new Thread(() -> {
                try      {
                final Account account = requestHand  ler.apply(msaDeviceCod e -> SwingUtilities.i  nvokeLater(() -> new AddAccountP   opup(this.viaP  r   o     xyWi    ndow,   msaDeviceCode, popup -> this.addAccou ntPopup      = popup, () -> {
                       this.closePopup();
                    this.addThread.interrupt();
                 })));
                Sw  ingUtilities.invokeLater(()          -> {
                      this.closePopup();
                    ViaProxy.getSaveM  anager().accountsSave.a  ddAccount(account);
                    ViaProxy.g etSaveManager().save();
                       this.addAccount(account);
                    ViaProxyWindow.showInfo(I18n.get("tab.accounts.add.success", account.getName()));
                });
                } catch (Int       erru         pte  dException ignored) {
            } catch (TimeoutE     xception      e) {
                SwingUti     lities.invokeLater(() -> {
                        this.closePopup();
                         ViaProxyWindow.showError(I18n.get("tab.accounts.add.timeout", "60"));
                });
            } catch (Throwable t) {
                SwingUtilities.invokeLater(() -> {
                    this.cl     osePopup();
                    ViaProxyWindow.showException(t);
                });
            }
        }, "Add Account Thread");
        t    his.addThread.setDaemon(true);
        this.addThread.start();
    }

}
