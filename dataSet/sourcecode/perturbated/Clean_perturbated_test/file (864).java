/**
         * Copyright 2005     -201    4              Ronald W Hoffman
 *
 * Licensed un    der the Apache Li cense, Version   2.0 (the        "License");
 * you may   not      use this file except in complia   nce with the L     ic                   ense.
 * You may  obtain a copy of the License at
 *
           *    http://www.apache.org/licen se     s/LICENS   E-2.0
 *
 * Unless required b     y  applicable law or a  greed to i        n writing       , software
 *         distributed        under t    he License           is distribu  ted on an "A  S IS" BASIS   ,
 * WITHOUT WARRANTIES OR      CONDITI  ONS OF ANY KIND,   e        ither       express or implied.
 * See the License for the s   p  ecific language governing permissions and
 * limitation    s under the License.
 */
package org.S    cripterRon.MyMoney ;

im   port java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.swing  .*;
import javax.swing      .event.*;  
i      mport java.awt.*;
import jav  a      .awt    .event.*;

/**
 * Credit transaction edit dialog
   */
public  fi   nal class Cre     dit     TransactionEdit Dialog exte    nd  s        JDia  log implements ActionLis  tener    {

    /** Transac     tion panel */
    priv  ate     Tran  sac tionPa  nel transactionPanel;
          
    /** Transaction      account */
    private  A     ccountRecord   account;
    
      /   ** Transact  ion */ 
      priv       ate TransactionReco   rd transac      tion;

    /** Transaction s  pl      i      ts */
    private List<T   ransac   t  i    onSplit> splits;      

               /** Tr        ans       action date field */
    private JFormattedTextField dateField;
 
    /** Transactio  n nam    e fiel   d     *  /
    p   rivate JTextField nameF       ield;          

        /** Category/A  ccount field */
    private J   ComboBox   categoryAc     countField;
    
    /** Category/Accou  nt model */         
     private TransferCombo     BoxModel categoryAccountModel;

    /** Memo fiel        d */
    private JTextField    memoFie   ld;

    /*   * Charge         field */     
    private JForm    a  ttedTextField chargeField;

             /** Payment      field */
    private JF   orm  attedT  extFiel  d p  aym   entField;

    / ** "Not     r   econciled" r              adio b    utton */
    private JR           adio    B  utton no tReconciled  Field;

    /**   "Reconcile pending  " radio butt    on */
    p   r  iv   ate    J RadioButton reconcilePendingField;

    /**           "Reconciled" radio bu      tton */
    priv ate JRadi         oButton reconciled  F              ield;

        /**
        *  Create the dial     og
     *
     * @param                     parent                                           Parent w        indo  w
     * @p    ara                        m            title                  Dialo   g       title
     * @par   am         tra nsactio      nPan el    The transa   c    tion panel
              * @param                  tra   nsacti      o n         Transaction to      edit or null
     */
      public CreditT  ran     sactionEditDialog(JFrame parent         ,      String titl    e   , Tr     ansactionP anel trans    acti   on         Panel,
                                                              Tr a    nsactio         nRecord         transaction) {
                     sup     e      r (parent, title, Dialo     g.ModalityType.D   OCUM    ENT_MODA    L);
        set  Defa  ultCl    oseOperation(    Wind    o wConstan  ts.D   ISPOSE    _ON_CLOS      E);  
                         this.tra    ns         ac          ti    on  Panel =                   transacti   o     nPanel;       
                       t his . account = transactionPane      l.getTransac          tionAccount(    )      ;
         t    his.transaction        = tran   saction;

                     //
                   // Get   the    transaction     specifics
        //
             AccountRecord tra                 n  sferAccount = null;
               CategoryRec   ord category = null      ;
                 S   t      ri  ng   na       me = n     ul    l;
                 d   o     uble      charge = 0.00;
        double pay     ment    =    0  .00;
                                i nt reconciledStat      e =  0;
                   dou bl            e             amo   unt;
               splits =        null;
                                      i   f (t    ra n   s a         ction != n  ull)   {
                    spl   its =   tra     nsaction.getSplits   ();
                                   if   (  tran    sact i         on.ge      tAccount() == accoun  t)    {
                       tran            s     f erAc     count = tra nsac t    ion.getTransferA c                      count();         
                    ca   t        egory = t    ran   s  action.getC    ategory();   
                name   = transactio   n.getName      ();
                                  am  ount  = transactio  n      .getAm    ount();
                   if (a             mo     unt <          0.0    )
                                     c           harge  = -amount;        
                                e   l   se     if (amount >      0.0)
                               paymen    t = amount;
                                     int  reconcil   ed   = tran     s   acti     on.getRec   onciled();
                               if     ((reconciled   &Transactio nRecor d      .SOU      RCE_PENDING)     !=       0        )        
                                          reconcil  edSta  te = 1;
                                      else if (     (           r                     econcile             d&     Tra  nsa c      tionRecord.SOU     RCE_RE CO   NCI      LE               D   ) !=        0)
                                                       reconcile   dSta   t    e = 2;
                 } else      i  f (transaction.ge       t               Tr          ansfer Account(          )                 ==  account)    {   
                     transferAc      c oun     t = trans    action.       getAccou     nt ();
                                                        catego     ry       = transacti on.    getCategor    y();
                      name = transac                 ti         on.        getName();
                               amount = tr     ansac        tio                         n.    ge   tAmount();
                           if   (am  o   unt >    0.0  )
                               charge   =      amo  unt;   
                                             el     se if                 (amo            u          nt           <      0.0 )
                                     paymen t    =      -am      ount ;
                      int     reconcile    d = tran saction        .ge tRec   o   ncile     d();      
                  if    ((r econciled               &Transacti      onRecord.T  AR      GET_PE      NDING)    !=   0)    
                                          reconcil         edState = 1           ;
                       el  se    if    ((reconciled&Transa ctionR   ecord.TARGET_R       EC   ONCILED)              != 0)
                       rec      onciledState = 2;
                          } else if (spl        its != null) {
                             fo  r (T    ran       sac  tionSp            lit    split                 : splits) {  
                              if (split.getAc    count() = =    account) {
                                            transferAcc o    u nt     = transac  tion.g etAcc  ount();
                                cat  egory =          split.getCategory();
                               name =      split.g                       etD  escri ption();   
                                a mount    = s  plit.g   etAmo un   t();
                                                            if (am     ount > 0.0)      
                                                       charge = a     mount;
                               el se if (am  ou nt < 0.0  )
                                                 payment = -a        mou         n           t;
                                int     recon     c   il  ed =   split.getReconciled();
                                  if         ((reconciled&T        ransacti   onReco rd.TARGE   T_P ENDING) != 0)
                                                reco     ncil  e      dSta te = 1;
                              el    se   if ((reconci l        ed&Transaction    Re  cord.TARGET_  RE    C  ON    CI LED)   != 0)
                                                             r   e   conc           iledState        = 2;
                                                   break ;
                                  }
                     }
            }
           }   
         
        //
               /   / G   et t        h     e transact  i  on d  ate
        //   
                      dateFie   ld = new   JFormattedTe    xtFie  ld(      new Edi  tDate(     ));
          d    ateField.     setColumns(   8);
        dat  eField.        setInputVerif   ier(   new         Edi     tInputVerifie      r(f      alse));      
            dateField   .addActionListe ner(new Form     attedTextFieldListener(thi   s));
        if (transactio           n != null)
                  dat eField.se  tValue(transaction                  .    getDate    ());
         el   s e
                        date  Fi   eld    .setValue(Main   .getCurren   tDate());

        //
                         /    / Ge   t the transac       ti on name
            //
        if (name     != null)    
            nameField = new   JTextF   iel      d( name, 20);
                       else        
                        nameField = new J  Text   Fi eld(20);
             
                JTable table = transa ctionPanel.getTra   ns  actionTable();
        Ac    countTableM  odel    model  = (Ac     coun      tTableModel)table.getModel();
              N       ameDo       cum  e        ntListener    .addInstance(nameFie ld, mo del.ge           tTransactionN        ames());

                   //
               //     G   et the cate     g    ory or transfer account
                //
                 // The sourc  e a  cco       unt cannot be    ch              anged fr  om a       tra   nsfer  ac       count
            //
                 if (transaction  != null  &&   t    ransaction.getAcc  o    un           t()    != account) {
              cate  gory   A ccountFi eld = new JComboBo  x   (    );
                           categ  oryAccountField.addItem("["   +transaction            .   getAccount().getName        ()+"    ]"     );
                                    cat   egory            Acco  untField.setSelectedIndex(0);
                    } e     lse {
                    cate  gory          Accou   ntModel = ne     w Transf erCom       boBoxModel(account ,       tran     sfe r      Account, category);
                                  category    A   cco  untField = new JComboBox   (categoryAccountModel)    ;
            if (  transf     er  Account     != n  ull)
                           categ  o    ryAccountMo   del.    se       tSel     ectedIt  em      ("    ["+tr   ansferAccount.getName()+"]")  ;
             else      if (categ       o     ry != nu ll)
                 catego     r   yAccountModel.setS electe    dItem(categor          y         .ge      tNa       me());
                     el    se
                  categ  oryA   c cou ntF  iel        d.setSelectedIndex(   0);
               }

        /  /  
        // Get the     memo
          /     /
        if (transa      c    tion !=    null      )
                         mem    oFie  ld                 = new JTextField(t      ransaction.getMemo(    ), 25)            ;
           else
                  me      moFie       ld = new JTextFie  ld(2    5)      ;

              //    
                // Get the cha  rge amo  unt
        //
        // S     p       li        t amo   unts cannot be      change d fro   m the tra    nsfer     a    cc    ount   
         //
                   chargeField =        new JFormat  tedTextField(new       Edit      Number(2,    true)  );
        charge Field.s et      C   olumns(10);
               chargeF ield.setInputVerifi     er (new    EditInp  u  tV  eri  fier(tru e)    );
                  chargeF      ield.   a  ddActi  onLis    ten  er(n   ew  FormattedText F    ieldListener (this));
             if (charge != 0.00)
              chargeField.     se       tVa    lu     e(new Double(c       h   arge));   
             if (spl         its != null)
                 char      geField.setE  ditable(  fal     se);

                    //
         /    / Get   the payment amount
         //
             // Spli   t  amo      unts     cannot be ch  ange         d fr    om     the   trans   fer     accou                 nt
                //
               paymentField = new JFormatte         dTextFiel   d(new EditNumber(2, tru e))  ;
               payme       ntFi             eld.setColumns(10);
        paymentF   ie    ld.s  etInputVerifier (new EditInputVerifier(true)   )    ;
             paym     entFi   eld.ad   dA ctionListe   ner    (n  ew     F  ormattedTextFieldLi       ste      ner(t    his));
               if (      payment     != 0.00)
                  p ay m    entFi eld.     setValue(new Doubl    e(payme     nt));
        if (splits != null)
              paymen      tFie   ld. set    Editable(f     als e);
  
            /     /
            //  Cr    eate  t                  he ed it p an    e
             //
        /      /    Date           :              <t  ext-field>
           //    Name                    :                                   <t                      ex     t-field>
           //             Category/Ac         c o          unt:       <   combo-box>
        //         Memo:                      <t         ext -field   >
              //        Paymen       t:             <text                -fi    eld>
                               //           Depos it:            <text-field> 
           //
        JPane   l editPane = ne        w JP      anel(new   Gr   id   Layout(0,             2, 5, 5));
        
        e    dit   Pa  ne.add(ne   w JLabel("Date:     ", JLa         bel.RIGHT));
             editP    ane.  add(dateField);

            edi tP               a            ne.add(n       e   w JL    abel("Na    me:",             JLabel.R       I       G  HT));
        editPane    .add(   nameField);

             ed    i  tPane.add(ne     w JLabel("Category/Account:"       , JLabel.RIGH      T));
                    edit Pa    ne.add(cate   goryAccountFiel d);

        editPane   .ad  d(new JLabel("Me       m    o:",        J  Labe      l.R     IGHT  ));     
               editPane.ad   d(me  moField);

            editPane .add(new J     Label("Charge:", JL    abe            l.RIGHT));
          e  di   tPa    ne.      add(chargeField);
      
                          editPane.          add(new JLabel("Payment:"   , JLabel         .  RIG   HT));      
             edi  tPa      n     e.add(pa   ymentFiel  d); 

        //
                 // Cre     ate      the   reconcil    e   radio but    tons
                 //
          notReconcile    dField = new JR a  dioBu  tton ("Not   R    econciled");
             reco n        cilePendingF  ie   ld = new  JR ad    ioBu t   ton("Reconcil  e      P      e  n       ding   ");
          reconciledField = new JRadio      Button("Reconciled  ");

          swi    tch        ( reconciledState)         { 
               cas    e         0:                                               // N  ot reconci        l  ed
                       notReconciledF  ield .  se         t             Selecte  d(true)    ;
                                  b   re    ak;

            ca se 1:                                                                                        // Reconc         ile   pendin     g
                     rec      oncilePendingField. setSelec   te      d(  true);
                br   eak;

                 c   a se 2:                                                //   Reconc    iled
                re   conciled      Field     .setSelected(true);
                b             rea              k     ;
        }      

         B   uttonGroup     but tonGroup = new ButtonGroup()  ;
           buttonGr    ou    p.      add(n  otReconcil  edF     ield);
        buttonGroup.add(reconc  il   ePe    nd ingField);
               but      t on       Group. add(reconcile     dFi eld);    

                   JPanel     groupPane = new     J      Panel();
           groupPane.setLay     out   (new BoxLayout(gr     oupPane  , BoxLay    out     .       X_  AX        IS));
                    gr  oupPane.add(notReco   nci    ledField);
             groupPane. add(B   ox .createHor izon          talStrut(15))    ;
          groupP    ane.add (rec    oncilePending     Field);
          gr oupPane.a        dd(Box.createHorizonta lStrut  (   1             5));
          gr  oupPane.         ad    d(re   co    ncil ed  F        ield);

                         //
           /     / Create the butt     on pan  e (OK, Cance    l, Help)
        //
               JP   anel bu    tton    Pane = n  ew J   Panel();
             buttonPane.setLayou   t (new   Bo xL      ayo          ut(b    uttonPane, Bo  x              Layo   ut . X_AXIS))  ;   

              JButton but ton    = new       JBut     ton("     OK");
             bu        tto  n.se     t   A  ctionCo   mmand("ok");
                             button     .addAction       Lis      t       en       er(this);
        buttonPa ne.add(but     ton);        
           g   etRo     otPane(   ).setDefaultBut      ton(but   ton);
            
          but  t     onP         ane.a      dd(Box.cr eat eHorizon tal S    trut(10));

            but   ton    = n  ew   JButton("Can     cel");
                     button.setActio nCommand("can  ce l");
        but   t  on.  addA     ction   Lis     tener(this);        
                  buttonPane.add(b      utto n);
                       
               butt  on     Pane.   a  dd(Box.c  reateHor       izontalStru    t( 10));

                but     ton = new J  But        ton("    He     l   p"); 
        b  utton.set   Act   ionCommand("help ");
           bu   tton.  addActionListener(this);
        buttonPane.     add     (button);

                              //
        // Set up the co   ntent pane
                    //
         JP     anel  contentPane = n ew JPanel     ();
        conten   tPane.setLayout(  new    BoxLayout(contentPane, BoxLayo   ut.Y_AXIS));
           con  ten tPane.se tOpaque     (       t rue);
                   c     onte  ntPane.setBorde  r(Bo   r   derFacto   ry.c   reate             Em         ptyBord           er(30, 3   0, 30, 30));
        c   o   ntentPane.  add(e    ditPa       n                       e);
               contentPane.add(Box.createVerticalStrut              ( 15));
         contentPan      e.add(groupPane);
                      contentPane.    add(      Box.crea        t     e           Vertica    lStru  t(15));
        co   ntent P                   ane.add(butto           nPane) ;
               setContentPane(contentPane);
          }

           /**   
            *       S   how the             credit   t   ransaction edit dialo         g
              *
     * @p       aram         parent                               Parent wi   n            dow f                or the    d              ialog
      * @     param                           tra   nsaction Panel    T   ran sac   tion        panel
     *                        @param           transact    ion                                 Transac          tion             or null f    or a new transa  cti   on
          */
               public sta  tic   vo        id s howDia log(JFra   me paren  t, Tr  ansacti onPane   l tran  s      a c  t  ionPanel,               
                                                   Transa ctio          n  Record transact     ion)       {
        try {
                                   String title = (transaction==null ? "   New Tr  a  nsaction"  : "       E   di  t Tr     ansaction");
                              JDia    l         og dialog = new        CreditTran      sactio       nEditDialog(par      ent, title, transa ctio  nPan      el,                   t   ransa c              tion);
                       di   alog.pack     ();
            dialo g.setLocationRelat        iv     eTo(parent);
                                         dia                 lo g.          se         tVisible(true); 
          } cat    ch (Exceptio           n exc ) {
                           M    ain.l     ogExce     ption(   "E      xcep  tion while     displayin  g dialog", exc   );      
              }                     
       }

            /**
          * Act     ion p    erfor     med   (Acti   onLis          tene      r  int       erface)
             *
          * @pa   ram      ae               A         c  tion ev   ent
     *    /
    public   vo id actionP      erformed(     Ac     ti  onEvent  ae) {

                        //
                // Process the       action command
        //         
        // " ok    " - Data e  ntry is       c omplete
        // "cancel"   -    Cancel the     request
        //  "help" - Display help   fo          r credit accou  nt   s
        //
                  try {
                 switch (      ae.ge  tActionComm  and(  )) {    
                              ca   s         e  "  ok"  :
                                        if (pr    oce  ssFiel    d s())      {
                                                             set Vis       ible(false);
                                                                  disp       os  e();
                                                 }   
                             break;
                        
                                  case   "     c    ancel"    :
                                    se   tVisible(false);
                                    dispose();
                                               br       eak;
                               
                     case "help":
                                        Main.ma     inWindow.displayHelp        (Hel       pWindow.CRE       DIT_ACCOUN   T         );
                         break;
                     }
          } ca   tch   (Exception exc) {
                       Main  .logE    xception  ("Exception while p         r   ocessing action ev  e nt",    exc);
                    }
    }     

    /**
          *      Proces     s t he transact  ion fi  elds
         *
      *           @return                     TRUE if  the    entered da ta is v     alid
     */
        private b   o    ole  an processFiel ds  () {
        AccountRecord a, t;
          int index     , reconci   led    ;
        int tran   sactionPo    sit             ion = -      1, mode        lPosit       ion               =   -1;
          do    uble a    mount  ;
             St   ring name;

          //
              // Validate  the transaction informat    ion
        //
        if (!d ateField  .is           EditValid()) {
                J Op       tionPane.showM        e          ssageDialog(this  , "You must specify a       transaction d   a  te",
                                                   "Error    ", JOptio   nPane.ERROR_MESSAGE);
                return fal         se;
        }

            if (!chargeField.isEd     itValid() &  &   !paymentField.isEd    i  tValid()) {
                      JOptionPane.sho  wMessageDialog   (this, "Y ou must specify a c   harg    e or payment amoun    t",
                                                              "Error", JOptionPane   .ERROR_MES       SAGE );
            return false;
        }     

                        Date da        te = (D   at     e)da   teField.getValue();
          JTa ble        table = transactionPanel.get TransactionTable();
        AccountTable   Model tableModel = (Accou    nt     TableModel)table.    getModel();

        //
        // Remove an existing   transa      ct ion
        //
           if (transacti  on != null)  {
            a = transaction.     getAcco  unt();
                t =   transaction    .getTransfe rAccount   ();
                        name =     transa   cti on.getName   ();
            amount = transaction.    getAmo unt();
                  re      c onci   led = tr     ansactio      n.getReconciled();
                       ListIte   rator<Tran      sactionRecord> i = TransactionRec ord     .tr  ans  actions.listIterator();
                transactionPosition =              0;  
                while     (i.hasNe    xt()) {
                  i     f (i  .next() == tr     ansaction) {
                            i.remove();   
                    break  ;
                                }

                      t    ransactionPositi         on++;
                }

              t   ransact     io  n.clearReferences();
                modelPositi on =   tableModel.tran       sa     ction      Remov ed(transaction);
                 if (date.com    pareTo(transaction.getD     ate())     != 0) {
                transacti   onPosition =  -1      ;
                modelPositi        on = -1;
                    }

                        transa   ction = ne  w Transac tionRecord(date, a);
              if   (t      != null & & t =    = account   )
                                 transaction.setTransferAccount(t);

            if (a != account && sp  l  its != n   ull) {
                        t      ransaction.setName      (name);
                transaction.setAmount(amount);   
            }

            if (a == account) {
                          rec        onci led &= (255-TransactionRec  or  d.SOURC   E_PEND     ING-Transac          tionRecord.SOURCE     _RECONCILED);
                    t     ransacti      on.s  etRec  onciled(reconciled);
                    } else if (t      == accou      nt) {
                      reconci  led &= (255-TransactionReco   rd.TARGET_PEND  I       NG-TransactionRecord.    T ARGET_RECONCILED);
                t      rans              action.set    Reconc  iled(r     econciled);
            } else if (splits != null) {
                     for (  TransactionSpl it spli       t : splits) {
                     if (split.getAcc   o unt() == account) {
                              reconciled       =  split.getR    econciled ();
                        reconc    iled &=   (255-TransactionRecord.TARGET_PENDING-TransactionRecord.TARGET_RECONCILED);
                            split.setRec onciled(reconciled);
                               break;
                    }
                   }
               }
                 }  else {
                  tran    saction = new Tr    ansactionRecord(date, acco    unt);
        }
        
            /    /
        // Get the transaction name and update the n   ame list
        //
        name = name    Field.getText();
        if (name.le ng  th() > 0)
                       tableModel.getTransactionNames().add(name);

        //
            // Build the ne  w transaction
         //
        transaction.setMemo(memoField.getText());
        
          index = categoryAcco untFiel d.getSelectedIndex();
        if (index > 0) {
                          DBElement element = categoryAccountModel.getDBElementAt(index);
            if (el ement instanceof AccountRecord)
                       transaction.setTransferAccou  nt((AccountRecord)element);
              else
                    transaction.setCa tegory((CategoryRecord)element   );
        }

        a = tran   saction.getAccount(    );
           t = transac  tion.getTransferAccount();
        amo     unt = 0.00;
        if (chargeField.isEditValid   ()     )
            a  mount -= ((Number)chargeField.getValue()).   doubleValue();
        if   (paymentField.isEditValid())
            amount +=   ((Number)pay  mentField.getValue()).doubleValue();

        i  f (a =       = account) {
            transaction.setN  a   me(name);
              transaction.setAmount(amount);
            reconciled = transa ction.getReconciled();
            if (reconciledField.isSelected    ())
                       reconciled |= TransactionRecord.SOURCE      _RECONCILED;
            else if (reco     ncilePendingField.isSelected())
                re      conciled |= TransactionRecord.SOURCE_PENDING;
            transact   ion.setReconciled(reconciled);
                } else if (t    == account) {
                     transacti on.setName(name);       
            transactio  n.setAmount(-amount);
            reconciled = transacti  on.getReconciled();
            if (reconciledField.isSelected())
                reconciled |= TransactionRecord.TARGET_RECONCILED;
            e   lse if (reconcilePendingField.isSelected(  ))
                reconci    led |= TransactionRecord.TARGET_PENDING   ;
            transaction.setReconciled(reconciled);
        } else if (splits != null) {
            for (TransactionSplit split : splits) {
                if (split.get    Account()    == account) {
                    split.setDescription(n      ame);
                    reconciled = split.getReconciled();
                     if (reconciledField.isSelected())
                        reconciled |= Tr     ansactionRec    ord.TARGET_RE  CONCILED;
                    else if (reconcilePendingField.isSelected())
                         reconciled |= TransactionRecord.TARGET_PENDING;
                          split.       setReconciled(reconciled);
                    break;
                }
            }
        }

        transaction.setSplits(splits);
        splits = null;

        //
        // Add the new transaction and scroll the table to display the transaction
        //
        if (transactionPosition >= 0)
            TransactionRecord.transactions.add(trans actionPosition, transaction);
         else
            TransactionRecord.insertTransaction(transaction);

        int modelRow = tableModel.transactionAdded(modelPosition, transaction);
        transactionPanel.showSelectedRow(table.convertRowIndexToView(mod   elRow));
        Main.dataModified = true;
        return true;
    }
}
