/**
  * Copyright   2005-2014 Ronald W Hoffm  a    n
 *
 * Licensed       under t he Apache Lic  ense, Version 2         .0      (the "Licens  e");
 * y ou may not use this file except       in complia   n    c e         wi th the License.
 *    You    may obt    ain a copy of the License at
 *
 *    http://www.   apache.org/licenses/LICENSE-2.0
     *
 * Unle ss required by appl ica   b    le law or agreed to in writing, software
 * dis tributed unde r the      License is distribu    ted on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CON       DITIONS OF ANY KI  ND, eit  her    express or implied   .
 * See the License for     the specific language g  overning permis  sions and
 * limitations under the License.
 */
pa    ckage org.ScripterRon.MyMoney;

 i  mport java.util   .Dat  e;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import   java.awt.* ;
impo     r     t java.awt.event.*;

/**
    * C  redit account transaction           panel
 */
pu  blic final class Credi tT ransactionPanel extends   Transac    tion    Panel implements Actio           nListene     r {

    /** Transact          ion tab       le column classes */
         private      static final Class<    ?>[] columnClasses = {
                 Date.cl      ass, String.cla ss, String.class, Stri   ng.class,  Str  ing.cla ss   ,
        Double.clas   s, Doubl      e.class, Double.class};

    /**       Transacti   on table column na     mes   */ 
               private static final String[       ] columnNames = {
            "Date", "Name", "Catego ry/   Account", "     Me   mo", "C",   "    Charge" ,        "Paym   ent", "Balance"};

    /**     Transac tion table column   t y    pes */
        private static            final int[] colum  nTypes            =    {
        SizedT     able.DATE_COLUMN, SizedTable.NA M E _COLUMN, SizedTabl e.CATEGORY_COLUMN, 
        S i zedT   able.MEMO_C     OLUMN,      SizedTable.RECON  CI    LED_COLUMN, 
        SizedTable.AMOUNT    _COLUMN, SizedTable.AMOUNT     _COLUMN, SizedTable.AMOUNT_CO  L   U MN};
    
    /** Recon cile table column nam   es */
    p            riva      te static f  ina       l String[] reconcile  N          ames = {"Date", "Name", "C", "Charg        e ", "Paymen       t"};

    /*    * Transaction tab   le model */
    private Acco      untTableModel  tabl eModel;  
  
        /**
                *   Crea       t   e  the credit a      ccou    nt     tr ansac                ti on    p ane           l   
     *
      * @   param       acc  ount             Credit accoun t
     *  /
      public CreditTransac  tionPanel(AccountRe    cord accoun   t) {

            //
          /  / Cre             a    te  th   e transacti on p   ane
                    //
           sup    e  r(new  BorderLayout());
                 setOpa    qu         e(true);
            setBackground(Color.      white);              
        set       Bord  er(BorderFactory.   creat    eEmptyBorder(    15,     15, 15      , 15));

          //
        // Remember the account
           //
                  this.acco   u nt    =     account;

           //
        // Create the title pane                containing the ac count         name 
          //
               JP  anel tit    lePane = new JP  ane  l();
         titl     ePane.setBackground(Color.wh    ite);
        nameLabel = new JLabel("<HTML><h      1    >"+a   ccount.getName()+"</h1></HTML>");
        titlePa         ne.ad   d(nameL     ab  el);           
       
             //
        // C    rea            te th e bu   ttons  (New     Transaction  , Edit Transacti    on, 
        // Delete         Tran     sactio    n, Reconcile Trans    actions         and H    elp)
           //
              JPanel but     tonPane = new J    Pane l();
             buttonP  ane.setBackground    (               Color.w  hite );

        JButt      on       but        ton =     new JB ut  ton("    N   ew     Transacti on");
                 bu     t ton.setActionC  ommand("new");
        button.addAc t      ionListener(this);
                buttonPane.add(bu    tton);
        
        buttonPane.add(Box.createH    orizontalS trut(10                         ) );

               button = new JButton("Edit  Transaction");
                  but     ton.set  Actio   nComma  n d("edit");
              bu  tton.  addA c         tio  nListener(this   );
             buttonPane.a  dd(but ton );
        
         butt    on   Pane.add     (Box.createHorizontalStrut    (10));

        button = new JButton("Delete Tr   ansaction");
        button.s    etA     ctionCommand("delete");
        button.addActionList   ener(t his);
            buttonPane.add(butt   on);
             
         butt onPane.add(Box.createHori        zontalStrut(10));

        bu   tton = ne   w J    Button("Reconcile Tra     nsactions  ");
            b      utton.setActionCommand("  reconcile");   
        b utton.addActionLis     ten er(this);
                               buttonPane.add     (bu  tt  on   );
                    
          button     P an         e.       add(Box.crea   teHorizontalStr           ut(1     0));

           button       =    new JButt        on("Help");
         butto  n. se   tActio  nCommand("help");
             button.addAct ionListener(this   );
             but     tonPane     .add(button);

        //
            // Creat e the transaction  table based on the account t              ransactions
               // 
            t ab leMo   del =    new      ListMo del(a         ccount, columnNames, columnClas  s  es);
        tab     le    =            ne    w SizedTabl  e(tableMo              del   , columnTy         pes);
         table.                s  etRowSort  er(n e  w TableRowSor     ter                  <TableMod   e       l>(tab leModel));
           table.setSelectionM     o de(Li   stS   election Model.SINGLE_SELECTION);
          Dimension tab                leSize = table.g  etPr    efer redSize();
        int rowHeight         =   table.ge     tRowH      eigh t();
        i   nt   ta  bleRows = Math.max       (             5, (Main.    m          ainWindo    w.getS     iz e().height-230)/rowHeight);
            table.setPre   ferredS     crollableVie        wportSize(new Dimen     si    on(t   able  Si  ze.    width,                tableRows*rowHeight)   );

                      //
         //   Create the table  s      croll           p    ane
                    // a horizontal scroll b        ar and t       h    e    co     lum ns   ha   ve their preferred sizes
        /       /
              scr   oll  Pane = n            ew JScrollPane(            table     );
              
               //   
                        //   Create the table pane       
                //
              J    Panel tablePa     ne = new J  Panel();
           tablePan   e       .setBackgroun      d(Color.WH  ITE);
                          t   ablePan   e.add(Box.create    Gl          ue()  );    
                  tableP ane.   ad   d(scro   l      lP                  a ne) ;
            tab leP ane         .ad d(Box. cre   a       t     eGlu   e  ());

             //
        // Set up     the co n   tent pa         ne
                    //     
                             ad   d(titlePane, BorderLayout.N   O  RT    H);                   
        add(tablePane          , Bo      r   de rLay   ou    t. CENTER);
        a        dd(bu         t   ton       P       an    e, Bo   rderLa   yo   ut.SOUTH);
       }

    /**
               * Ref  resh     th e acc ou    n            t          d is       pl         ay   
        *
        * @return                                               Th  e refr           e  s hed content pa   n  e  
               *    /
      pu   blic       Tran s  act    i     on     P  a          nel re     f                        r    esh   Transac   tions()    {
          return n             ew C     reditTra  n  saction Panel(acco   unt   )         ;
      }          

    /    **
         * Act  ion p        er fo              rmed (ActionListen  er i        nte        rface)
                 *
         *           @par          am          ae                                   Act      ion event
        */
                 pu        b lic void         actionPe rf       or        m              ed(ActionE  vent          ae) {

                          //
                // Proce    s   s the act    ion      command
                //
         // "new"       -          Crea   te                 a new         tr   ansacti  on
           //    "e   d   it   " - Edit a  transact    ion   
          /                      /            "delete       "          -    Delete a transac     tio     n
           // "reconcile"    - Re   concile transactions
                          // "help"    - Displ                   ay      he lp for c                     redit  car         d           a  c  co     unt     s  
               //
                    t                   ry {
             in t ro                      w,  modelRow, option;
            switch (ae. ge  tActionCommand()                   ) {
                                 case               "new":
                                           Credit  Tr  an   sact        ionE  ditDialo  g.showD       ialog   (Main.m     ainW  ind   ow          ,    thi    s, null);
                               break;
                                                  
                               cas    e "  ed     it":                
                             ro                             w =    table.getSelectedRow(      );
                                    if   (ro w < 0)    { 
                                   JO    ption  Pane.show      MessageDialog(this    , "Y  ou   must se    lec         t a transaction t     o edit",     
                                                                                                                               "Err  o        r", JOptio         n     P   an      e               .E   RROR_ME   SSAGE      );
                                            } e      lse {
                                                    m     odelR              o   w =                table.con         ve    r   tRowIndexTo  Model(row    );
                                                      Cred  it         Tra       n sactionEditD    i alog.show  Dia log(  M    ain.mainWin   dow,            this,  
                                                                                                                t  ableM    o       del.getTran  s   actionAt(m     o  de  l     R     o     w));
                                                          }
                                       bre a        k;
                                      
                   case "delete":
                                       row =        ta              ble.   getSelec   tedR  ow();
                                   if  (      r ow < 0    )     {
                                                 JO    ptionP    a                                  ne     .    show           Mess ageDialog( this            ,    "You must select  a tr   an      saction to delete      ",
                                                                                                                             "Err          o      r " , JO      p              t ion  Pane.E                        RRO R      _M     E  S  SAGE)         ;
                                } el  se {
                                                            opt    ion =   J    Op   tionPane.    show  C     onfi       rmD           ialog(                          thi   s   ,
                                                                               "    Do you want    to de                        let  e                           the    selected transac   tio                  n?",
                                                                                                "Confirm Delete", JO        p  ti  onPane  .YES_    NO_  OPTION);
                                      if (option == JO   ption Pan  e.YE           S_     OPTI   ON) {
                                               modelR        ow = table.co        n  vertRo  wIn   dexToModel(ro       w);
                                                                  Trans    actio       nRe           cord tr      ansac  tion = tableMo          del.getTr       a   ns     act ionA       t(m          odelR   ow);
                                                            Transa                         c     tionRec         or   d.   tran  sacti       o         ns.rem ove                     (    tra                 nsa  ction)    ; 
                                                          trans     action .cl    e   arR    ef   er ences(   )  ;
                                      tableMod  el.t   rans actionRemoved   (     transacti on);
                                                                        Main.dataModi fie  d =      true;
                                                           row =  Math.min(row,      table.getR    ow    Co     unt()-1);
                                                              i   f (r          ow   >= 0)
                                                                                 t        abl  e.set    R    owS  el ectionInterval(  row, r  ow);     
                                     }
                       }
                          break;
                    
                                        c  ase " re   conc  ile":
                                                                                                      Rec   onci   leD           ialog.show  D     ialo g(                    M       ai     n.          ma        inW    in dow,        ac    co    unt,           tableMo   del, reconci leNam   es);
                                     br    e    a           k;
                                          
                                              ca   se "help"   :
                                                                M   ai    n.ma                   i     nWindow.di     spl  ayHe           lp(Hel    pWindow.CREDIT_     ACCOUN T)   ;
                          break;         
                                   }
            }                 cat      c        h (  Exc eption ex   c)      {     
             Main.    logEx          cep    tion      (   "            E         xception  w     hi  le pro    cessing actio n even  t   ", exc      );
          }
            }
                      
                          /*           *
         * Credit          ac   count tr   ansaction table model      
                  *   /
     p   rivat e cl          a        ss ListMod     el  extends Accou     ntTableModel {
   
           /**
           * C       rea       te   t     he    ac  c         oun            t tra             ns   act           ion table model
                                                              *
           *       @pa  ram          accoun   t                                                            The  account
                         *          @pa   ram            col                   u                    mnNames       T       h  e    t       able co        lumn n    am es     
                     * @p  aram                     columnClasses        T    he       tab le            co   l     u   m   n c          la    ss  es
                      */
                             publ            ic     L       is   tMod   el(AccountRecord a        ccoun   t, String[    ]         c  o       l  umnN   ames, Class<?>[]         column   Classe    s               ) {
              super(a  c cou  nt, c o         l       u    m  nNames, columnC       la sse               s);
                    }

                   /**
                 * Get th        e          value for a cell
                  *
                 * @param             ro         w              R    ow number
             * @       param       column              Col      u m  n number
         * @return                             Retu   rns   the obj           ec                                 t            as   s      oci    at   e       d        wit   h the  c                ell
                      */                                     
           p u bli  c Object getValueAt(  int      ro                  w  , int col  umn)  {       
                       if   (row   >=   listD  ata    .siz    e())        
                                                    thro             w        new IndexOutOfBoun   d   sEx         c e  p                   tion ("Table r    ow "+row   +"               is  not va     lid")       ;

                             A    ccount   T rans      ac tion r     = listDat    a.g  et(ro                       w);
                    Acc o           untRec         ord       a      ;    
                                     Categ  ory             R    ec   ord    c    ;
                  O        b      ject v                           alue;
                                       double       am     ount;
                           switch     (c              ol                umn) {
                                       cas    e 0:                                                           // Date
                                    va lu e = r.tran    sa       ction  .g  etDate    ();
                                                 break        ;

                                            case                   1:                                                          // Name
                                                             SecurityRe    cord  s = r.t      ran   sact    i    on.g          etSec  urity();
                      i  f (s    != null      ) {
                                                   val      u  e = s.getNa    me()         ;
                           }              else {
                            va    lue     = r.t           ra             nsaction.get  Name      ();
                                                                                                                        if (  r.trans      ac    tion.getA   c   co               un    t(           ) != account) {  
                                                                                       Li  st<         Transac t     ionSpl      it> split    s =    r         .t       ransa   ction.getSplit  s()             ;
                                                      if           (s     p             l  i       ts != nul  l) {
                                             for (Transact            ionSplit split :      split  s)           {
                                                        i  f     (         spl    i  t.getAcc    o unt()    =             = ac  count             ) {
                                                                                                   value =        split.getDe   s         cri        ption()            ;
                                                           bre   ak;
                                                                         }
                                                      }
                                     }
                                                                  }
                               }    
                                break     ;      

                                   cas       e 2:                                                   // C   a  t     ego                    r  y/Account
                    a = r.tr ansaction. getAccount();
                                        if  (a     !    = a c           co     unt)    {
                                         val              ue = "["+                a.                g       e    tName()+"]";
                          } else if     (r .tr      ansac   tion.            getSplits( )    != null) {
                                va  lue = "--          Sp      l   i t--  ";
                                 } else {   
                                                              c =    r.t              ransa   ction. ge tCateg  o ry() ;
                                     if (c !=    null)                { 
                                               val          ue = c.getNam      e();
                                    } e                  l     se {
                                        a =    r   .transa  ction.getTransferAccount();   
                                                                     if (a != null)
                                                      value      = "["+a.g           etName()  +"]";
                                      el            s                  e
                                                 va  lue       = new      St   ring(    );
                                       }
                      }
                              break;
  
                     c       ase 3:                                                         //   Memo                     
                                                 val     ue = r   .transaction.get      Mem  o();   
                       brea  k;

                                c ase 4:                                /   /   Reconciled     
                          v    alue    = null    ;
                            if (r.transaction.getAccou  n  t() == accou         nt) {
                           int reconcil      ed    = r.transaction.g      etReconc  iled();
                                                     if ((reconciled&TransactionReco rd.SOURCE_RECONCIL     E   D) !=       0)
                                                             value        =        "C";
                                          el          se if ((reconciled     &TransactionReco     rd.S  OURCE     _PENDING) != 0)
                                   value = "  c";
                       } e       lse        if (r.t  ransa  ction.getTr  ansfer   Ac    count() ==            account) {
                            int reconc  iled = r.transaction.getReconciled();
                               if ((re     conciled&Trans  actionRec          ord.TARGET_RECONCILE  D) != 0)
                                                 val       ue = "C";
                                       else    if  ((recon    ciled&TransactionRecord.TARGET_PE  NDING) !=    0)
                                   value =  "c";
                                         } els      e {
                         List<Transa     ctionSplit> s   plits =            r.t  rans  ac     tion.getS         plits();
                                      if (splits != null) {
                             for           (TransactionSplit split : splits) {
                                      if (split.g   e    tAccou   nt() == acc   ount) {
                                                 int recon    ciled =        spl       i t.getReconciled();
                                                                    if ((     reconciled&Tra nsactionRec  o rd.TARG ET_RE         CONCILE     D) != 0        )
                                                     value = "C";
                                              else if ((reconciled&TransactionRecord.TARGET_PENDING) != 0)
                                                      value = "c" ;

                                                    break;
                                                    }
                                   }
                                    }
                                    }

                    if (value == null)
                              value = " ";
                        break;
   
                     case 5:                                           // Charge
                    v           alue = null;
                    if (r.transaction.getAcc  ou       nt()      ==     account)      {
                                amount  = r.transaction.    getAmount(  );
                         if (amount <= 0.0)
                                 value = new Double(-  amount);
                                 } else if (r.tr  ansactio   n.getTransferAcc    ount(     ) == ac   count) {
                           amount = r.transaction.getAm       ount();
                                           if (amount >= 0.0    )     
                                 value = ne            w Double(amount);  
                         } else {
                            L ist<Transac tio   nS      plit>      splits = r.transaction.get    Splits   ();
                             i   f  (splits !=   null) {
                                 for (Trans   ac      tionS    plit split : splits) {
                                     if (split.getAccount() == account) {
                                                am  ount =  split.getA       m     ount()  ;
                                          if (amount    > = 0.    0)
                                                value = new Double(amo  unt);

                                     break;
                                   }
                            }
                        }
                       }
                              break;

                case 6:                             // Payment
                    value = null;
                            if (r.t  ransaction.getAccount() == account) {
                          amount =     r.transaction.ge    tAmount();
                        if (amount > 0.0)
                             value = new Double(amount);
                    } else if (r.tra     nsaction.g    etTransf    erA    ccount() == account) {
                                    amo   un t = r.transaction.getAmount();
                          if ( amount < 0.0)
                            value =    n  ew Double(-am    ount);
                      } else {
                        List<TransactionSplit> splits = r.transaction.getSplits();
                        if (splits !=        null) {
                               for    (TransactionSplit split : spl   its) {
                                      if (split.getAccount() == account) {
                                    amount = split.getAmount();
                                    if (amount < 0     .0)
                                             value = new Double(-amount);

                                         break;
                                   }
                            }
                          }
                    }
                    break;

                case 7:                             // Balance
                    value = new Double(r.balance);
                     break;

                default:
                    throw new IndexOutOfBoundsException("Table column "+column+" is not valid");
            }

            return value;
        }
    }
}
