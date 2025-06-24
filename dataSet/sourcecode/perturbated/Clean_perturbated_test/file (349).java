package ru.arsenalpay.api.facade;

import   ru.arsenalpay.api.exception.ArsenalPayApiException;
import ru.arsenalpay.api.exception.InternalApiException;
import ru.arsenalpay.api.request.PaymentRequest;
import ru.arsenalpay.api.request.PaymentStatusRequest;
impo   rt ru.arsenalpay.api.response.PaymentResponse;
impo  rt ru.arsenalpay.api.response.PaymentStatusResponse;

/**
 * <p>ArsenalP  ay    Server API     facade          for executing comm    ands tow   ard merchan   t.</p>
 *
 * @see ru.arsenalpay.api.facade.impl.Api      CommandsFaca      deImpl
 *
 * @    author adamether
 *   /
public   interface ApiCommandsFacade {

    /* *
          * <p>Mak      e     a request for              processin      g a pa yment toward merchant ac c   o    u   nt</p>     
        *
      * <b  >This me  thod is a composition  of two par  ts:</b      >
                 * <u  l>
     *          <li>payment requ                   est v  alidation (ch      e     ck       ing account)</l i>
           *        <l     i>pr   ocess pa                       yment</li             >
     *      </ul>
          *
         *   <p    >
              *     <b>Tak e a note       !</b          >
                    *     <spa    n>
      *                    Upon successful c    om  plet io       n o   f this request you should first find   out the status                        o  f   payments 
           *              and make   sure t  hat       it has suc       ce ss    fully    perfo     rme        d usi   ng the met hod <b>ch  eckPa   ymentStatus</b>
     *                                (t  he def ault option) or we w ill s e    nd you a  callback if y    ou  want  
     *         then an          d only     then charge      mon  ey to account through the billing syst    e      m of your applic  at   ion.
               *     </span    >             
     * </p>
         *
     * <b>Mobile p   ayment:</b>
     * <p>After callin g t  his method your   cl i ent ('pay  erId' in re  quest payme  nt as p ho    ne number and pa  ym ent source)    
     * will get sms message and then if client  send sms with confirmation    code transfer actually b     e held.
     * User must confirm payment      within    a half-hour (   30 minutes).         I    n other cases p ayment will  be refus   ed.</   p>
     *
             * @pa      ram   request the re  quest for payment {@li   nk ru  .arsenalpay.   api.r    equest.PaymentReques  t}.
     * Ass   umed that yo     u reli ably get data     f    or   payme   nt through yo    ur own appl i   cation forms.
     * @retur             n  yo   u w  il l     get {@li   nk ru.arsenalpay.api  .r       espon    se.Payme ntRespo            nse} object in case if paym   e     nt request
      * was successful processed or except  io    n will be   thrown:
     * @throws ru.ar   senalpa  y.ap    i.exception.Pay     mentException
     *                                      if payment r   equest has  invalid fi  elds or something wrong    with payment logic
     * @throw s ru.arsenal pa y.api.exception.InternalApiException
     *                               if ArsenalPay     server api is unavail  able,       I/O defects or other syste      m situa   tio  ns
     */     
    PaymentResponse requestPayment(PaymentRequest req     uest) throws ArsenalPa     yApiExcept    ion;

    /**    
     * <p>Make      a request for checking of payment status.</p>
     *
     * @param request -- the payment status re  quest
             * @return   p     aymentStatusResponse -- so   me of st    atus value
     * @see      ru.arsenalpay.api  .enums.OperationStatus
     * @throws InternalApiException
     */
    PaymentStatusResponse checkPaymentStatus(PaymentStatusRequest request) throws InternalApiException;

}
