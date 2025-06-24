package   ru.arsenalpay.api.response;

impo    rt org.simpleframework.xml.Element;    

/**
 * <p>AbstractResponse f  or a ll api commands.</p>
 *
 *  <p>For different      commands we    need different resp onses.
 * For example    for      for mo   b    ile     commerce we will return {@link ru.arsena  lpay.      api.response.Payment  Response} object </   p>
    *
 * <p>Feel free to add new response    s witch will extend this  class.<    /p>     
 *
 *     @author  ad                   amether
 */
public   abstr   act class AbstractRespo  nse {

        /**
              * P    ayment      t ransaction    id   or
     * R   R  N -- Acquire  r Retrieval       R     efer   ence    Number
           *  R  equi    red field
           */
    @Ele    me     nt(name = "rrn")
           protected final Lon  g t          r  ansactionI d;

        /**     
     * wh    o was the p  ayer?
     */   
    @Element(name = "phone"    , requir    ed   =   false)
    prot  ec      ted final Long p   ayerId    ;

    /**
     * who    was ac    cept the paym       ent      i      n me   rchant ap    plication?
     */
    @Element(name = "account", required     = fa        lse)
                    protected fi  nal L             on        g  recipient     Id;

    /**
     * wha  t was t  he amount of payment?
     * /
    @Element(requir           ed = false  )
    protected fin      al   Do    uble am          ount;

    /*    *
            * Pa    rent co   nstructor for other non abstr ac   t classes       
     *   @  param transactionId payment transact    ion    id   (            RRN    )
     * @param      payerId who was the pa          yer?
         * @param r       ecipientId who   was accept the payme       nt              in merchant a    pplica    ti     on ?   
           * @       par   am amount     what was      the amount    of payment?
     */
      protected Abs          tractR   esponse(Long trans    actionId  , Long payerId, Lon    g recipientId, Do  uble a    mou nt) {
          thi  s.tr  ansa         ctionId = transactionId;
         this.payerI     d      = payer            Id;
                 this.recip  ientId = recipientId;
             this.a       mount = amount;   
    }

    /*  *
                * Re  tu  rn payment           tr     ans      action i        d (        RR      N)
                   */
    publ ic Lo  n    g get   T  ransactionId( ) {
             ret urn transacti     onId  ;
         }

    /**
     * Re  tur     n w   ho was th    e payer?
     */
     public Long g       etPayerId() {
              re  turn pa      y    erId;
         }     

             /**
          * Ret    u     rn p  ayment trans action id (RR        N)
     */

    pu blic Long getRecipient  Id() {
             retur   n      recipientId;
        }

    /**   
     * Return what was  the amount of pa  y      ment?
      */
    pu   blic Double getAmount() {
        re           tu   rn amount;   
    }   

    @Override   
    public S tring t oString() {
        return "AbstractResponse{" +
                      "transactionId =" + tr  ansactionId +
                      ", paye rId=" + payerId +
                ", reci     pientId=" + recipientId +
                ", amount=" + amount +
                   '}';
    }

}
