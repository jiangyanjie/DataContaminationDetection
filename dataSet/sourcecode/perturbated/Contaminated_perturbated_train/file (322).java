/**
   *       C   opyrigh   t 2005  -2014 Rona  ld W Hoffman
 *
 * Licensed under     the Apache  License,      Version 2.0     (th       e "License");
 * you may not use this            f   ile except in compliance w  i    th t         h    e L          icense.
 * You may obt  ain a copy of the License at
 *  
 *    http://www     .apache.org/licens      es/LICENS        E-2.          0
 *
 * Unless required     by applicable law     or agreed     to     in     writing      , soft       ware
  * distributed und      er t  he License is distributed on    an "    AS IS" BASIS,
 *     WITH     OUT WARRANTIES    OR CONDITIONS OF ANY KIND, e  ither e  xpress or impli    ed.
 * Se    e the Lic      ense for the specific language governing permissions and
     *   limitations under the Lice        ns  e.
 */
package o     rg.ScripterRon.MyMoney;

import java.util.SortedSet;

import  javax.s             wing.*;        
import    jav   ax   .s    wing.e     vent  .*;
import java.aw      t.*;
imp    ort jav    a   .awt.e      ven  t.*;

/**
 * Dialog to edit an          acco   u   nt.
 *    /
public   fi    nal class  Accoun              t      E   ditDialo     g   ext en      ds    JD   ialog imple       ments     ActionListener  {
    
      /** List model */
    pr  ivate DBElement    ListMode   l     listModel;

    /** Current account or   null        for a new acc  ou nt */
    pri       va  te AccountRecord    accou          n   t;

    /** Account      type */  
    private     JComboBox     a       ccou ntType   ;
     
    /** Account type model */
    private DBEle       mentTypeComb  oBoxMod  e   l accoun    tTypeModel;

    /** Linked account */
       pr    ivate JComb     oBox link   edAccount;
    
    /** Linked account model */
    p      rivate LinkAccountComboBoxModel      linked Account     M   o              del;

         /**   Account   n      ame */
      pr  iv   at e   JTex  tField ac    co un   t   N   am   e;

      /** Accou        nt n      um   ber *        /
    pri  vate JTextField accountNumber;

    /** Lo       an rate */
    private JForm         attedTex      tField loanRa  te;

         /** Account is hidden    * /
    pri    v    ate JCheckBox acc       o  un t     Hidden  ;
             
         /    ** Acc     o  unt is          tax deferre   d   */
    priva   t    e JCheckBo  x         taxDeferred;

    /**
                    * Create             th e dia          log 
        *
         * @param                     pa   r  e    nt                     Paren  t window
            * @param               title           Dia   log title
                         * @param            l  is     tModel           List model
     * @param                 a   ccount          Account to ed    it or null for a new acco unt   
     */
    p      ublic AccountEd          itDia           log(J D  ialog parent, S  tring titl e, DB El  ement L  istModel listMode    l, Ac  c      ou    n  tRecord ac      count) {
        s uper                 (parent,    t    itle,      Dialo   g.Mo  d  alityTyp     e.DO   C  UMENT_MODAL);
                   s  et  D   efault        CloseOperat    ion(Win   dowConstant    s.D   ISPO      SE_  ON_CLOSE);    
 
            //
        // Save    the ac     co  un                    t info    rm     ation
                //
        thi  s  .listMod         el = listModel;
           t his.ac      coun    t = acc     o      unt ;

        //
        // Build the acco     unt types f  or     a    new account (the       acc       ou       nt
           // ty pe     for an existin           g     account cannot be chan    g   ed          )
               //
            if (accoun         t          ==         nu    l  l     ) {
              a  ccountTypeModel   = new      DBElem   en tTyp   eCo  mb     o     Bo    xModel (AccountRecord.g    etTypes(), 
                                                                                            AccountReco   rd.get  Type   St       ri   ng  s     ()   );
             account   T   ype         = new        JComboBox(acc         ountT   ypeModel)   ;     
                       acco      untTyp  e.s  e     tSelect  edIndex(-1);
        }          

        /   /
                        // Build         the linked account   list for a ne  w account or for an
            // exi   sting        i  nvestm     ent a  ccount.      A    n ex isting l i   n      ked acc     o     unt will
        // be set as   the initial selection.    The ini       tial se lection    w   ill
                   /  / be      "--None--" for a ne   w accoun  t.
              //
                 if (accou     nt == null || (account !=    nu         ll && account.    ge          tType  ()      = = AccountRec  ord.INV    ESTMENT)) {
                           linkedA      ccountModel = new LinkAcc             o  untComboBoxModel  (account);
             lin  kedAcc  ount = new    JComboBox(linkedAccountModel) ;
              }

        //
          //      Create the   edit       p  an   e    
           /      /
           //        Account Ty  pe:   <com bo-bo x>
                    //           Acc  ount Name:   <text     -field>
           //    Ac       count Number: <        te xt-field>
        //    L      oan   Rate (%):  <  text-fi   eld> (   optional)
          //       Linked Ac count: <com    bo-box>  (op   t   ional)
        //
         //    Account Hidden: <check-box>
                  //          Tax Def     erred:   <check -box>
         //
             J  Panel editPane = new JPanel(ne   w Gri      d     Layout(0, 2, 5, 5           )  );

                 ed      itPa   ne     .  add(new JLa    b        el(      "Acc     ount T   y          pe:", JLabel.RIGHT));
                    i   f (account  !=       nu ll)
                editPane.add   (new JLa bel(Acc  ou            ntRe  cord.getTypeStr ing(account     .g      etType  ())));
             e   lse
              editPa         ne.add(acc o      untType);

               editPane.a              d     d    (new JLabel("Acco unt Name:", JLabel   .   RIGHT))  ;
               if (acc  o   unt   !=       nu  l   l)
             accou    ntName =      ne  w       JT   ext Field(account.getName()  ,      Math.m             ax(accou     nt.getName().le     ngth(), 15));
            el      se
            accountName = new JTe     xtFi   eld(15)    ;
             editP            ane.add(acc   ou  ntName);

                     edi      tPane.a   dd(new JLab    el(" Ac    count N  umber:"      , JL                     abe           l.RIGHT));
                  if (account !=       null)
                    accountNumber = new      J          TextField(account.getNum   ber());    
                      els       e
                    accoun   tNum      ber = new JTextFiel d(15)   ;
             editPane  .add(accountN  umber);

             if (account == null    || account.getType() == AccountRec      ord.LOAN) {
                        editPane.add(new JLabel("Loan Rate (  %):", JLabel.RIGHT));
                  loanRa te = new JFo   rmat   te   dTextFie     ld (new EditNu   mber(4,   true));
                 loa nRate.setColumns(8);
            loanRa    t     e.setI   nputVer   ifier(ne   w EditInput   Ve  rifier(tr  ue   ));    
                   loanRate.addAction      Listener(new Formatted    TextFi   eldListe  ner(this));
               if (account != null) 
                loanRate     .setValue(new Do       uble(account.get  LoanRate()   *100.0));   

                            ed  itPane.ad d(loa       nRate);
                    }

           if (    li nke   dAc  count != null) {        
               e    ditP     ane.add        (new J La    bel("L       in  ked Ac     count:    ",          JLabel   .RI    GHT));             
                         editPane .    add(l ink      edAccount);
        }
            
        editPane.add(      Box.createG    lue());
                  a  ccou  ntHidde        n =    new    JChe  ckB     ox (    "A   cco     u            nt Hidden") ;     
        if      (accou            nt  != null)   
             ac  c       oun  tHidden.setS        elected    (account.isHidd en())     ;
        else
            a   cc   oun    tHidden.setS       elected(false)     ;
        editPane.add   (     ac         countHidden);
                 
           editPane.add(B                 o   x.cr  e ate        Gl   ue(             )    )          ;
         t      axDeferre    d = new JCh   eckBox("Ta x Deferre         d");
             if (a    c    count != null)
            t    axDeferred.setS  elected  (acco    unt.i        sTaxDefer    red(     ));
           els      e
            taxDefe r    red.setSelected(fal      se);
                            editPane.add(taxDeferred);

                                //
             //       Create the but  ton pane (OK     ,     Ca    nc  el,    Help)
            //
                 JPane    l buttonP   ane = n  ew JPa        nel();      
           bu ttonPane.set       L                a          yo          ut(new BoxLayout(button    P               ane, BoxLayout.    X_AXIS));

              JB  utton    but          ton    =  new     JButto   n(           "OK")     ;
               but   ton.setAc     t  ionComma   nd("ok");
        butto       n.addActi  onLi s   tener(   th     is)   ;
                            but t   onPane   .add(button);
          getRootPa    ne  ()   .se     t            DefaultButton(button);

                   b     utt            onPan  e     .add(Box.c   reateH   orizontalStrut(10));
                                     
                  bu     tton          = new      J   Button("Ca ncel  ");
             button.s        e tActionCommand     ("cancel");
                 b     utton.addActionListener(this);
          buttonPan   e.add(butto      n); 
        
             buttonPan  e.ad      d(    Box        .createHorizontalStrut(                     1     0));
           
          button = n        ew JB   utton("H    elp");
        button    . s     etActionC  ommand("hel  p");
                                button.addActionListener(             this);
                        butto   nPa  ne.add(button);

               //
         // Set up t  he cont          ent pa    ne
                                        /    /
             JPan      e          l contentPane    =     new J    Pan   el();
        c   ontentPa         n e.setLayout(new BoxLay     out(contentPan    e, BoxLa   yout  . Y_A   XIS));
                co  ntentPan  e.    setOpaqu   e(true);
              cont    entP      ane.s   etBord  er  ( Border      Factory .creat      eEmptyB  order(30, 30, 30        ,               30) );
                               contentPane  .add(e  dit       Pan  e);
           cont           e    n   tPane.   add (Box.createV     er     tical S  trut(15));
              contentPane.add(b  u       ttonPane);
                                  se  tCon  tentPa ne(   content  P ane)    ;
    }

           /**
                *     Show th   e a           ccoun     t  edit dialog
        *
     * @param       parent                 Parent      window f  or the  dialog
       *    @par am       l        is      tMod  el                List model
     * @p  ara           m                ind   e     x                          Lis   t index   or   -1 f           or a                  n        ew acc          oun    t   
     */
    pu blic      stati     c void showDialog(J   D         ialo        g        paren    t,               D   BE    lem     e   n    tListModel l  i stMo    de    l,      int index) {  
             try {
                           Str  ing tit le;
                                    AccountRecor   d account;
                                    if (index >= 0) {
                                        title = "    Edit Acco            unt";          
                           account = (Acc    ou                ntRecord  )l    istModel.get         D    BElementAt(i   ndex); 
                      } el     s   e {    
                       t   it      le = "Add Account";   
                       account =   null;
                  }
                 
                      JDialog dialo g  = n   ew      A     ccountEdi        t   Dialog(         pare    nt, ti     t   le, listMo   d    el, a ccount);
                                                dialog.pac k();
            dialog.setLocati on     Relati                   veTo      (par       ent);       
                   d     ialog.s      e     tV   i   sible(tr       ue)  ;
         }            catch (Ex    cept ion exc) {
                              Mai   n         . lo     gExcep  tion("Ex   cept         ion wh  il  e dis playin   g di alo          g", exc      );   
                 }                                                         
            }  

       /**
              * Actio  n pe      rforme d  (   Ac                         tionListen  er i      n  ter  face)
           *   
                                 * @param   ae                              A    ct          ion e     v          ent
           */
              public void actionPerform ed    (ActionEve  nt ae) {              
   
                 //
            // Proc   es   s    the acti   on comm         a    nd
                           //
          // "ok" - Da            ta entry i s   complete
         // "can  cel"    -   Canc el    the   request
              //    "help" - Display help  fo   r    acc ounts
          /    /
                    tr           y {
                switch (ae. getActio nCom  mand()) {
                                                                                cas      e   "ok":
                     if (processF                            ields()) {
                                                       set          Visible(false);
                                                                                         disp      ose()    ;
                                 }
                                        br    eak;
                                           
                                  cas     e "ca     ncel": 
                                              setVisible(f  alse);
                                                 dispos   e ();
                                   br       eak;
                         
                              ca   se   "help":
                          Main.m ai            nWindo    w.displayHe    l        p  (    HelpWindow.ACCO  UNTS            );
                           br   eak;     
               }
             }     catch     (E         xcep          ti  o   n exc) {      
                   M     ain.     logException(    "Exce    ption while proce  ssing action event", ex   c);
         }
                         }

    /**
         * P       rocess         the account fields         
       *    
            * @return                     TRUE if the ent        ered dat      a is vali      d
     *  /
    private boolean     proce    ssFields()     {   

               //
              /  / Valid    ate the account infor  mation
                  //
               int type;
                 String name;    
        String numbe     r;
        boolea      n hidden, d  eferred, newAccount;
        d    ouble ra   te;

             if (acco  unt == n    ul l) {
                      type = acc        ou     ntType.  g  etSelect edIndex(  );   
                   if  (       ty  pe < 0) {
                                          JOpti                  on Pan  e.showM                  essa   geDial   og(th  is, "You must select an ac  count      type",
                                                                           "Error"   ,    JO  ptionPane     .ERR      OR_    MESSAG      E);
                   ret       ur   n false  ;
               }

            type         = acco    unt TypeMo del.getT ypeAt(type);
        } els   e {
                         type = account.     getTy   pe();
        }
           
          n     a  me =    accountName.g    e            tTex  t();
         if   (na       me     .len     g   th() == 0) {
                JOption    Pane.s  howMess     ageDialog(this, "You mus      t spe cify an account n    ame",  
                                                         "Error",     JOp  ti    onPan  e  .ERROR_MES     SAGE);
                return false;
        }
               
        for (AccountR                e  c  ord a : AccountR    ecord.         accounts  ) {
                 if (name.equals(a.ge tName())      && a != account) {
                 JOptionPane.sh  owMessag eDialog(this, "Account name     '"+name+"' is alr  eady     in use",
                                                                "Er  ror", J   OptionPane.ERROR_MES SAGE);
                return false;
            }
                }

        if (linkedAccount != null) {
            if (link             edAcco   unt.getSelect    edIndex() >   0) {
                              if (t  ype != AccountRecord.INVESTMENT) {
                             JOptionPa     ne.show   Messa   ge      Dialog(this,
                                        "A  linked account can be        specified only for an investment account",
                                     "Er    ror", JOptionPane.E  RROR_MES        SAGE);       
                      retu  rn false;
                           }
            } else if   (type == Ac    countR  ecord.INVESTMENT) {
                   JOptionPan e.showMessageDialog(this,
                              "A    linked account       mus   t be specified for an investment accoun   t"             ,   
                                        "Error ", JOptionPane.ERROR_MESSAGE);
                return fals  e;
                }
        }

        number = a    ccoun  tNumber.getText();
          hid      den    = accountHidden.isSe   lected();
              defer    red = taxDe fer      red.isSelected();

        if (loanRate != null     &&   loanRate.isEditVa              lid())
            rate = (((Number)loanRate.getValue()).doubleValue())/     100.0      ;
        e  lse
               rate = 0.0;
   
        if (type =   = Accou     nt     Record.L      OAN) {
            if (rate == 0.0) {
                    JOptionPane.showMessa    geDialog(this,
                                      "Y         ou must specify the rate for a loan account",
                                            "Error", JOptionPane.ERROR_MES    SAGE);
                return false;
            }
        } else {
            if (rate != 0.0) {
                JOptionPane.    s howMessageD  ialog(this,
                                                "A rate may     be specified only for a loan account",
                                    "Error" , JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        //
        // Cr  eate   a new account or update  an existing account
        //
          if (account == null) {
            newAccount = true;
               account = new Accoun      tRecord(na   me, type);
            if (AccountRecord   .accounts.contains(account)) {
                JOptionPane.showM   essageDialog(this, "Account '"+name+"' already exists",
                                                      "Error", JOptionPane.ERROR_MESSAGE);
                return f   alse;
            }
            
            AccountRecord.accounts.add(account);
          } else {
            newAccount = false;
              if (!account.getName().equals( name)) {
                AccountRecord.accounts.remove(account);
                account.setName(name);
                      AccountRecord.accounts.add(account     );
            }
        }

        if (t  ype == AccountRecord.INVESTMENT)
            account.setLinkedAccount(linkedAccountModel.getAccountAt(linkedAccount.getSelectedIndex()));

        account.setNumber(number);
        account.set     Hide(hidden);
        account.setLoanRate(rate);
        account.setTaxDeferred(deferred);

        if (newAccount)
            listModel.addDBElement(account);
        else
            listModel.updateDBElement();

        Main.dataModified = true;
        return true;
    }
}
