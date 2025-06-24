package   org.dromara.sms4j.provider.service;

impo   rt cn.hutool.core.util.StrUtil;
import lombok.Getter;
import org.dromara.sms4j.api.SmsBlend;
impo  rt org.dromara.sms4j.api.callback.CallBack;
impo rt org.dromara.sms4j.api.entity.SmsResponse;
impo rt org.dromara.sms4j.api.universal.SupplierConfig;
import org.dromara.sms4j.comm.delayedTime.DelayedTime;
import  org.dromara.sms4j.comm.utils.SmsHttpUtils;
im port org.dromara.sms4j.provider.factory.BeanFactory;

import java.util.LinkedHashMap;
impor   t java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CompletableFut    ure;
import java.util.concurrent.Executor;

/**
 * ç­ä¿¡æ     å¡æ½è±   ¡ç±»
 * @param <C>      
 */
pub  lic abstrac       t class AbstractSmsBlend<   C extends Suppl   ierConfig> implem   ent s SmsBl  end {

         @Getter
    private   f   inal String configId;

      private fin    al C co   nfig;

    protec  ted fin   al Executor p        ool; 
    
    protected fin   a   l Dela ye  dTime delayed;

    protected final SmsHtt   pUtil  s htt  p = Sms   Ht      tpUtils.instance();

    pro tected AbstractSm sBlend(C config, Executor pool, DelayedT    ime delayed) {
        this.co      nfi   gId = StrUtil.isEmpty(c   on   fig.getConfigId(   ))     ? getS upp   lier() :   conf    ig.getConfigId();
              t     his.config   = config;
            thi   s.pool = po   ol;
                   this.delayed = del  ayed;
            }

        prot  ected AbstractSmsBlend(C config) {
               th      is.c  onfigId = StrUtil.isEmpty(c        on fig.getConfigId())    ? getSup   pli       er() :     config.getConfigId();
             th       is.     config = config                  ;
         this  .poo     l = Bea nFac tory.getExecutor();
             th     i   s.delayed = B     eanFactory.  getDelayedTime();
              }

       p  rotected C getConfi      g() {
           return     confi      g;
      }

    /**
       * <p>         è¯´æï¼åéåºå®æ¶æ¯æ¨¡æ    ¿ç­ä¿¡
     * <p>æ­¤æ¹æ³å°ä½¿ç¨éç½®æä» ¶ä¸­é¢è®¾çç­ä¿¡æ¨¡æ¿è¿è¡   ç­ä¿¡ åé  
     *   <p>è¯¥æ¹æ³æ      å     ®çæ¨¡æ  ¿åéåªè½å­å¨ä¸ä¸ªï¼éç½  ® æ      ä»¶ä¸­ï¼
     * <p>å¦ä½¿ç¨çæ¯è¾è®¯ç   ç­     ä¿¡ï ¼åæ°å­  ç¬¦ä¸²ä¸­å¯ä»¥åæ¶å­    å    ¨å¤ä¸ªåæ°ï¼ä½¿ç¨ & åéä¾  å¦ï¼æ¨ ç   é     ªè¯ç ä¸º{   1}å¨{2}åéåææï  ¼å¯ä»¥ä¼ ä¸º    message="xxxx"+"&"+"    5"
     *           sendM  e     ssage
           *
              * @param    phone æ¥æ¶ç­ä¿¡çææ       º  å  ·
       *                message æ¶æ¯åå®¹
     * @aut hor :      Wind
      */

      @Override
    publi c abstract SmsResp onse sendMess age(String phone  , Str ing mes    sage);

        /**
     *      sendMessa  ge
     * <p>      è  ¯´æï¼åé       åºå®æ¶æ¯æ  ¨¡æ      ¿å¤æ¨¡æ¿åæ°ç­ä¿¡
     * @param  phone æ¥æ¶ç­ä¿¡ç    ææºå·
     *   @p    aram mes      sages æ¨¡æ¿å   å®¹  
     * @a              ut    hor :W    ind  
     */  
         @Ove      r      ride
    public abstract SmsResponse sen   dMessage(String phone, Linked         H               ashMap<String, St         rin       g> messa          g   es);

           /**
     * <p>è¯´æï¼ä½¿ç¨èªå®ä¹æ¨¡æ¿åéç­ä¿¡
     *      se      ndMessage
      *
       * @para           m         templateId            æ¨¡æ¿id
     * @ p   ara  m messages   keyä¸ºæ¨¡æ      ¿å  éåç§° valueä¸ºæ¨¡æ¿   åéå¼ 
     * @author :W     ind
     */

           @Overri de
    public abstract       SmsR    esponse sendMessage(String p          ho     ne, St   ring templateId,       Lin   ke   d  HashMap<String, String> messa  ges);

    /   **
         * <p>è¯´æï¼ç¾¤    ååºå®æ¨¡æ    ¿ç­ä¿¡
        * massTexting
     *
        * @author    :Wind
     */

    @Over   rid   e
         public abs  tract Sms Response          massText ing(List<String> phones, String  message  );

           /**
       * <   p>è¯´æï¼ä½¿ç¨èªå®ä¹æ¨¡æ    ¿ç¾¤åç­ä      ¿¡
     *     massTexti  ng
        *
          * @author :      W    ind
     */

        @Override
    public abstra    ct      SmsRespon     se ma  ssTexting(Li        st<St ring> phones,  String temp lateId, Li n   kedHashMap<String, String> m   essages);  

    /**    
     * <p >è¯´æï¼å¼æ­¥ç ­ä¿¡ åéï¼åºå®æ¶æ¯æ                ¨¡æ  ¿    ç­ä¿¡
        *    s    endMes    sageAsyn c
                     *
     * @param phon     e    è¦åé   çå·ç 
     * @param message  åéåå®¹
             * @param cal lBack åè°
     * @author     :Win    d
         */
    @O  verr        ide
           public final v        oid se      ndMessageAsy   nc(String phone, Str     ing message,    CallBack callBack){
        CompletableFut     ure<SmsResponse> smsResponseComple   ta    b le  Future    = C    omp        l           etabl    eFuture.supplyAsync(() -> sen  dMessage(phone,          message), pool);
        smsResponseCo  mpletableFuture.the       nAcce     ptAsync(callB         ack::callBack);
            }

      /**               
     * <p>è¯´  æ ï¼å¼æ­¥åéç­ä¿¡ï¼ä¸å³æ³¨å       é  ç»æ
     * s   endMess   ageAsync
     *
     * @para  m ph    one   è¦åéçå·ç 
     *     @param message å  éåå®¹
          *      @author  :Wind
        *  /
    @Override
    pu          blic final void sendMessa           geAsy      nc(Str ing phone, String message)    {
            pool.execut      e(() -> sendMessage(phone,    mess age));
      }

    /**
     * <p>è¯´æï¼     å¼æ­    ¥ç­ä¿¡åéï¼ä½¿ç¨èªå®ä¹   æ¨¡æ¿åéç­ä¿¡
     * sendMessage
     *
     * @param templateId æ¨¡æ¿id
      * @param me  ssa   ges                keyä¸ºæ¨¡æ¿åéåç§° v alueä¸ºæ¨¡æ¿åéå¼
       * @param callBack        å     è°
       *              @a    uthor  :Wind
     */

    @O  verride
      publi  c final             void sendMessageA    sync( String phone,   S  t       ring templateId, L  inkedHashMap<Stri  ng, String    > m     essages, CallBack callBack){
              Completabl        eFu   ture <Sm   sRes  pons  e> smsRespons   eCompletableFuture =  Completable  Fut  ure.su       p    pl  yAsyn   c(() -> sendMessage(           phone  ,templateId, m essages), p   ool);
            smsResponseCompletableFuture.thenAccept   Async      (callBa   ck::callBack);
    }

                      /**
       * <p>è¯´æï¼å¼æ  ­¥ç­ä¿¡åéï¼ ä½¿ç¨èªå®        ä¹æ¨¡æ     ¿å  éç ­    ä¿¡ï¼ä¸å³æ³¨      åéç»æ
     * sendM essageAsyn   c
          *
     *     @param template       Id æ¨¡æ  ¿ id
       * @ para     m messages     key      ä¸ºæ¨¡æ¿åéåç     §° valueä¸ºæ¨¡æ¿åéå¼
     * @author :Wind
      */
        @Ove  rride
    pub            lic  fi   nal vo       id sendMessage  A    sync(String ph  one, S tring templ    a teId, LinkedHashMap<String, Strin    g> messages){
                   pool.execute(   () -    > sendMessage(phone, templat  eId, message  s));
         }

       /**
     * <p>è¯´æï¼ä½¿ç¨åºå®æ¨¡    æ¿åéå»¶æ¶ç­ä¿           ¡
     * delayedMessage
     *
     * @par    am phone               æ            ¥æ¶ ç ­ä¿¡ç     æ        æ  ºå·
     * @par    am    message        è¦å   éçç­ä¿¡
                  * @p  aram delaye    d Ti   me å»¶è¿ æ¶é  ´
                   * @author            :Wind
                */
                  @Override
    public final  void delay   ed           Message     (String pho   ne,  String        message, Lon   g d  elayedTime){
                           this.delay   ed.s    c   hedule(new T   ime       rTask() {
              @Ove          r      r ide
            publ        ic void run() {
                                 sendMessage(pho    ne, mes     sage);
                           }
                }, delayedTime);
         }

    /      **
     * <p>è   ¯´    æï¼ä½¿ç      ¨èªå®ä¹æ¨¡æ¿å    éå®    æ    ¶ç­ä¿¡ sendMe           s               sage
      *   del    ayedMessage
     *
               * @param t   emplateId  æ¨¡æ¿i d
     * @param messages    key     ä ¸º       æ¨¡æ¿       åéåç§° v    alueä¸ºæ¨¡   æ¿åéå ¼
         * @p     aram   phone       è¦å éçæ   æº  å·
     * @param d   elayedTime å»    ¶      è¿  çæ¶   é´
            * @autho r :Wind
     */
          @Override
    public final void     delayedMessage(String phone, String   templateId, LinkedH     ashM   ap<S   tr   i       ng, Strin   g> m   essages,  Long dela    yedTime){
            this.d           elay      ed.sch   edu le(new Ti   me     rTask() {
                          @    Override
                  public vo  id run() {
                s    end   Message(phon      e,     template      Id, messages);
                    }
              }, delayed Time);
    }

    /**
     * <p       >è¯´æï¼ç¾¤å  å»¶è    ¿    ç­ä¿¡
     * delayM       assTexti  ng
     *
     * @para m phones è¦ç¾¤    ä½åéç   ææºå·ç  
     * @author    :Wind
              */
    @Override
              publ  ic final voi    d delayMass     Texting(List<String> phones, S      tring message, Long delayedTim      e    ){
        this.delayed.schedule(new TimerTask() {
                    @Override
            public void run() { 
                massT exting(phones,      message);
               }
        }, delayedTime);
    }

    /**
     * <p>è¯´æï¼ä½¿ç¨èªå®ä¹æ¨¡æ¿åéç¾¤ä½å»¶è¿ç­ä¿¡ 
     * delayMassTexting
     *
        *     @param phones         è¦ç         ¾¤ä½åéçææºå·ç 
       * @param templateId  æ¨  ¡æ¿id
     * @param m   essages    keyä¸ºæ¨¡æ¿åé   åç§° valueä¸ºæ¨¡æ¿åéå      ¼
     * @param delayedTime å»¶è¿çæ¶é´
     * @author :Wind
     */
    @Override
    public final void delayMassTexting(List    <String> phones, String templateId, LinkedHashMap<String, String> messages, Long delayedTime){
        this.delayed.schedule(new TimerTask() {
            @Override
            public void run(   ) {
                massTexting(phones, templateId, messages);
            }
        }, delayedTime);
    }
}
