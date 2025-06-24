/**
        * Copyright 2005-2014 R   onald   W Hoffman
 *
  * Lice nsed under    the Apache Lic      en    se, Version   2.0    (   the   "License");
 * y   ou may not     use this fi  le       except in   compliance with th e     Li   cense.
   * Y      ou may obtain a co py of        the License at
 *  
 *    http://w           ww  .apache.org/li  censes/LICENSE-2.0
 *
 * Unles    s required by applic  able law or    a   greed     to in wr iting, software
 *     distr  ibuted        under the   Licens   e is distributed on an "AS    IS" BASIS   ,
 * WITHOUT    WARRANTIES  OR COND    ITI   ONS OF AN Y              KIND,    eit  her exp      ress or i  mplied.
 * See the License for    the specific language  governing permissions and
 * limitations under the License.
 */
package org.ScripterRon.MyMo  ney;
import    org.ScripterRon.A  sn1  .*;
    
import java.util.*;

/ *    *
 * Databas   e account record.
 * <p>
    * T he following account types    are     provid  ed:
     *     <ul>
 * <li   >ASSE         T
 * <li>BANK
 * <li>C RED  IT 
 * <l    i>I  NVESTMEN   T
 *        <li>LOAN
 * <   /ul>    
 * <   p        >
 * All a     cc  ount reco  rds are contained in the <code>accou    n ts</ code> so  rted   set.  Th e
        * entries i       n              the set are so   rted by the ac    count nam  e.  Two ac    c  ount   re  cords         a        r e     
 * equal if th  ey have the same account nam    e.
 * <p>
 *     The account r  ecord is    encoded             as follo  ws:  
 * <pr     e>
   *   Ac      c   o    un     tRe    cord ::=  [APP              LICAT   ION 4   ] SEQUENC           E          {
    *       re  cordID                                                                          INTEGER,
 *     acc       ountT     ype                                       INTEGER  ,
 *       ac  countName                           BM   P   STRI     N      G,
 *     ac     count Number                      B     M    PSTRI               N     G,
     *     accountHi      dden                 BOOLEA   N        ,
          *              linkedAcco      unt              [0] INTE   GER OPTI ONAL,
 *     loan  Rate                          [1] DOUBLE OPTIONAL,
 *       taxDeferred                 [  2] BOO   LEAN O       PTIONA  L }
 * </pre>
 */
public final clas     s        AccountRecord  extends  DBElement {

    /** Ba  nk   account */  
             public static final int BANK = 1;
     
    /**    I  nves tment   account    *    /
    pu  blic        static final int    INVESTMENT =   2;

    /  ** As  set acc        ount   */
    public static f   inal int    AS  SET = 3;

       /** Loan account    */
    public s tat ic final int LOA N =       4;

      /*   * Credit account */
    public static f    inal      int C  REDIT = 5;

           /** Account types */
    private         stati       c final int[] accountTypes        = {BANK,    INVESTMEN    T, ASSET, LOAN, CREDIT};
    
        /** A ccount        t      ype    strings */
      private stat  ic fi   nal St    ring[] accountTypeStrings = {  " Ban  k     ", "   Inve     stmen     t"  , "Asset",  "Loan", "Credit"};

        /**    The set of defi  ned accounts */
    public static SortedS et   <Ac    countRecord> acco unts;
           
    /   ** T   he accoun  t map          */
    p rivate static Map<Integer   , AccountRecord> map;

      /*    *  The next A   ccountRecord ide ntif  ier */
        private stati    c int nextR   ecordID  =        1;

            /**     The enco   ded    AccountR   ecord ASN.1 tag identif     ier */
          p  riva    te     st    ati       c final byte tagID     =    (by   te)(Asn1St     ream   .ASN1_APPLI       CAT    ION+4);

    / **
     *    The       current a  ccount balance.  This is an app   lication w     ork area    and
             * is not          p    reserv    ed acros  s application re    starts.
         */   
         p ubli            c do     u  ble b  ala           nce;
 
     /**      Acco   unt number  (  n    ever   nu    ll)              */
       pr  ivate Str   i    ng accou         ntNumber;

    /** Lin    ked       account (only for an investmen  t    account) */
    private AccountRe            cord linkedA             ccount;

           /** Nu    mber of link   ing  ac   counts       (o n     ly fo       r a bank    account     ) */
          private int linkCount;

                           /** Lo an rat    e (only  for  a       lo      an     account) */
                                  private   double      loa     nR   ate    ;
               
               /** A    c     count       is tax-deferred    */
      p  rivate bo    olea    n  taxDeferred;
  
    /**
            * Cr   eate a n       ew accou nt record
               *      
      * @  p            a ram                    name            Account name
          *    @pa        ram                  type                     A cco     unt             ty         pe
       */
             p  ublic Acc      ountRecord(S             tring        n   a       me, int     ty     pe)     {
                 if (name ==       null)   
                       t    hr  ow ne w       N  ullPointerEx     ception("N         o account name suppl        ied");  

          //
                     // Create t         he        n      e   w a   ccou  n                 t
           //
         recordID = nex tRecordID++;
        ele       mentName = name;
               setType(type);
          acc        ountNu   mbe           r =                  n    ew S      tring()      ;
                    
        //
                /  / Add the acc   oun  t    to     the    map  
           //
        if (map == n    u    l  l    )
                     map      = new HashMap<>();
        
               map.  put(           new Integer(re    cordID), this);
        }

    /**
     * C    r   e ate   an acc           ount                   re      c       ord fr     om an encode   d  byte strea   m
        *
           *  @para       m             d  ata                   Encoded byte    stream fo   r the record
     * @ex   ceptio           n       DB            Exception     Una  ble  to dec    ode ob    ject stream  
     */  
        p u        blic AccountRecor  d            (byte[] data) t hr       ows   DB     Exception {
                                    if (data =    =       nu    ll)
                   throw     ne w      Nu llP   ointerExcept   ion(    "No enco  ded d   ata      supp     l    ie       d");  

         D      ec    odeStre           am s   tre         am = new Decod  eSt            rea        m(data    );

                    tr  y {

            //
                                //  Validate t   he  a    pplication identif                 i      er tag
                                   //
            if (  stream.get Tag() != tagID)
                          t   hr     ow  new DBE           xcepti  on("Not an e  n    coded Acco    un    tRec    or   d          objec   t");

                 //
                      /    /         G   et the A      cc    oun           tRecord sequ   ence        
                                   /       / 
                 DecodeSt     ream seq     = stream.g  etSeque    nce(true    );

                                        //
              //          D   ec     od  e the  r  ec   ord id     enti fier, acc   ount t       ype, account name,
                 //  a   cc  ount               number and     acco unt       hidden   state
            / /
                     recor     dI   D =    s    eq.dec     ode      Intege  r(false)    ;     
                           el   ementType = s   eq.de   codeI   nteg         er(false)      ;
                  elem e n            tName = seq.decode    String(false);
            ac coun   tNum    ber = seq.dec   odeStrin g(false);
                   elementHid  den  = seq.dec    odeBo    o        lean(f       alse);

              //
                       //   The li       n ked account  is    e         n coded as     an   optiona l con          t   ext-specif  i          c
               //    field  with ident   ifier 0    
                   /       /
                         if    ( se       q.getLength(   ) != 0 &&      seq .getTag     () == (     b      yt   e)(A     sn1Stream.ASN1_CONTEXT  _SPECI  FIC+0)         ) {
                          int l  inkID = seq.d   ec ode Inte          ger         (    true);
                              l   inkedAcco  unt = Accou  ntRecord.ge   t    Accou              nt(linkID);
                            i    f (link      edAc  c  ount == nu          ll)
                                  t hrow n       ew DBExcepti   on("Li      nke   d account              "+li   nkID+          "      is           not de    f  i     ned"              );
                    
                     linkedA    ccou   nt.setLinkC ount(link   edAccount.get    L   ink       Co   unt()      +1);
                }

                       //
                       //      The l    oan rate is  enco           ded as an op   tio nal      c       o       ntex            t-spe        cific
              //  field   with ide   ntifier 1
                    //
             if (seq.g   etLe  ngth() ! = 0     && seq.g    etTag   () =   = (  byte)  (Asn1Strea m.  AS N1_C      ONTEXT_SPECIF   IC+1  ))
                                    l   oan    R            ate       =         seq.decode    Dou            bl        e(tr      ue)    ;
                              
                  //
                                 //  The   tax defer  red f  lag i s encoded as an opti    onal c     ont   ext    -spe      cific
                                       //         field wit h    i     dentifier     2   
                 //          
                   if (se       q.getLeng   th() != 0 && seq.get       Tag() == (b y  te      )(Asn1Stre  a  m.ASN        1_     CONTEXT     _SPECIFIC+2           )   )
                taxDeferre d             =  seq.decodeBo    olean  (true);
       
              //
            //     Che    ck             for uncon      s   ummed data
                //
                               if (seq    .getLength    () !  = 0)
                                                    throw new DBE    xcept   ion("      Uncons   ummed data in Acco       unt   Re    cord   sequence");

                             //
                  /   /  Update the next rec  o   rd i   dentifier
                        //        
                if (recordID     >= ne xtR  ecordID)
                      nextRecordID = recordID+1;
                    
                              //
                      // Add   the n   ew account    to t    he map
                                //
                  if (m    ap =    =    null)     
                   map    = n   ew HashMap           <>();
               
            m     ap.put(new Integer(recordID), this);
        } catch (Asn1E       xce  ption exc)     {
            throw new DBException("ASN.  1 decode error          ", exc);
             }
    }    

    /**
         * Test if the supplied  byte str    eam represen   ts a     n en    coded     A  ccou   n  tRe cord      objec      t
     *  
     * @param       d  at    a                 The enc      oded        byte stre     am
      *   @return                                 T RUE if AccountRecord object
       */
    p    ublic static boo    le  an isEnco   dedStre    am                (byte[] dat  a) {
                      if      (da   ta == n    ul      l)
            t         hrow n     ew           NullPo   interException  ("Null byte str   eam refere  nce");

            if (dat                 a.len   gth     ==  0)       
               return fal s     e;   
  
                  re   t       urn (   da   ta[0]==    (byte)(    tagI     D   |Asn1Stream.ASN1_CONST   RUCTED  ) ? tr                ue :          fals        e)    ;
    }
    
    /**     
        * Enco  de     the Account   Record o  bj  ect
            *
      * @retur  n                                      The encode     d byte stre     am for       t    he       obje   ct
                    */
    public byte[] en   code() {
               in  t seqLength = 0;
           EncodeS    tre   am stream = new Encod  eStr    e  am        (128);

                     //
             //  The tax deferre  d   flag is encoded a     s a   n      optional context-spec       ific
        //  fie  ld with identifier 2      
              //
        if (taxDef  e       rr ed)
            seqLength += s  tream.encod    eBoolea       n(taxDeferred,    (by                 te)(A            sn1Stream .ASN1_CONTE      XT_S    PECIFI      C+2) );
            
              //
              //  The loan rate i     s encoded  as an optional cont ext-spe  cific          f        ield
        //      with i  dentifier 1
        //
                         if (loa     nR     ate != 0.0)
               seqLength += st    rea  m        .    encodeDou ble(loanRate, (byt                 e)(  Asn    1     Stream.ASN1_           C       ON            TEXT_  SPECIFIC+1));

            //
                 //  T he link      ed  accoun       t is         e  n  coded as an o     ptional   context-sp   eci    fic field
          //  with       i       denti             fier     0
                        //
                   if (linke   dAcco   u   n  t != null )
              seqLength += strea   m.enc   odeInte   ger(linkedAccount.get        I  D(),       (byte)(Asn1Stream.ASN1   _CON     T   EXT_SPECIFIC   +0));

                        //
                     / /  E  ncode the      account hi     dde  n sta  t  e, accou     nt numb  er,   a    ccount               name,
                //  acc ount       type    and re        cor    d       identif          ie         r
            //
          seqLe          ngth +=        st      rea   m.enco        deB     oole     an(elementHidden  );
        s       eqLeng     t  h += stream.e  ncode String(acc  ountNumber);  
                          se       qLe         ngth += str       eam.encodeString(elem      entNa   me);   
        se  qLength  +=    st    ream. encodeI              nteger(ele    mentType);
              seqLengt  h += stream.encodeI                 nteger(r        e c        or  d    ID)  ;  

              //
           //   Make the Accou  nt     R  ec       ord sequen ce
        //
                st       ream.   ma   k            eSe  quence(seqLength,         tagI   D);
        return str eam. getData();
        }
       
    /**
     *   Get th    e account associated     with      th      e     s  upplied record identi  fier.       
        *     
              *  @param                 reco          rdID        T  he     recor d identifier
     *            @ return                                 Th     e account or NULL
              */
    public stati  c Acc       o  un       tRecord ge   tAcco   unt(int  record   ID    )  {
         AccountR ecord account = null    ;
            if (ma p != null         )
                       ac  coun   t =     map.get(new       Integer    (recordID));
            
          r et    u    rn a       ccoun t;
                  }

    /*  *
              * Get the a    c    co  unt   numbe r.  The return value will never be null.
        *
                  * @return                                    The account number
     */
    public String  getNumber() {
                  retu    r        n accountNumber;
    }     

    /**
          *        Set      the account   number
     *
         * @p   aram       number            The account number
              */
    p  ublic void setNumber(Str    ing num  ber) {
           if (number ==     null)
                    throw new Null   Pointe  r   Exception("No account number suppl  ied  ");

                accountNumber = number;
    }

    /**
           * V     alid   ate and se          t the account type
        *
     *    @param          type             The account       type
                  */
    p    ublic void   se     t Type                 (int type) {
        boolean v  alidTyp         e = false  ;
        for (int i=0; i   <acc     ountTy   p    es.length  ; i++) {
                     if (t   ype ==     accountT           ypes[i]) {
                         validType = tru        e;
                bre   ak;
                }
        }

                 if (!validT   yp e)
              throw      new      IllegalArgumentE     x  ception    ("Account type "+type+" is invalid");

         elementT  ype = ty  pe;
    }
    
    /**       
            * Set t   ax-defer  re      d status      
            *  
                *    @param       deferred             TRUE if account i  s tax deferred
     */
      pu       bli    c vo id setTaxDeferred(bool       ean def      erred) {
             taxDeferred = deferred;
       }
    
      /**
       * Check if the account     is tax deferred
     *     
     * @return                         TRUE if   account is tax deferred  
        */
    public boolean isTaxDef    erred() {
        return taxDeferred;
    }

         /**
     * Get th    e known account types
     *
            * @return                            Array  o f account types
     */     
    public static int[] getTypes() {
            return accountTypes;
    }
    
           /**
     *        Get the ac       co    un              t t    ype    strings
     *
      * @return                             Array of accoun        t type stri  ngs
     */
    public static String[]       getTypeStrings() {
           re  tu  rn   accoun tTypeString     s;
    }

    /**
     * Get the displayable strin     g for an account type
     *
     * @param        type            The  account type
     * @return                            Displayable st ring
     */
    public static String getTyp    eString(int type) {
        String r   etValue = null;
            for (int index=0; index<accountTypes.length; index++) {
                  if (account   Types[index]       ==     type) {
                  ret    Value = a    c countTypeS   trings[index];
                   break;
                  }
        }

        ret    urn (retValue!=null   ? retValue : new St  ring());        
    }

    /**
     * Get the linked account 
     *
     * @return                         AccountRecord reference or null
     */
    public AccountReco  rd   getLinkedAccount() {
                 return linkedAccount;
    }

        /**
     * Set the linke     d account
     *
     * @param       account         Li nked acco unt
     */
    public void setLinkedAccount(AccountRecord account) {
        if (linkedAccount != nul    l)
            linkedAccount.set   LinkCount(    linkedAccount.getLinkCount() -1);

        li      nkedAccount    = account;
  
        if (linkedAccount != null)
            linked    Account.setLinkCount(linkedAccount.getLinkC        ount()+1)     ;
      }

      /**
     * Get the loan rat    e
     *
     * @return                      The loan rate
     */
    public double getLoanRate() {
             return loanRate;
    }

    /**
     * Set the loan rate
     *
       * @param       rate             The loan rate
     */
    public void setLoanRate(double r    ate) {
        loanRate = rate;
    }

    /**
     * Get the link count
     *
     * @return                      Link count
     */
     public i   nt getLinkCount() {
        return linkCount;
    }

    /**
     * Set the link count
     *
           * @param       count           Link count
     */
    public void setLinkCount(int count) {
        if (count < 0)
            throw new IllegalArgumentException("Link count is negative");

        linkCount = count;
    }
}
