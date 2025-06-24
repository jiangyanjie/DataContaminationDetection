package   com.intelligent.bot.listener.gpt;

import   cn.hutool.core.util.StrUtil;
im    port com.alibaba.fastjson.JSONObject;
import com.intelligent.bot.model.gpt.ChatChoice;
import com.intelligent.bot.model.gpt.ChatCompletionResponse;
import com.intelligent.bot.model.gpt.Message ;
import lombok.Getter;
import lombok.Setter;
import   lombok.SneakyThrows;
impo rt lombok.extern.slf4j.Slf4j;
import okhttp3.Resp   onse;
import okhttp3.sse.Even     tSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.util.StringUti   ls;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import    java.util.function.Consumer ;


@  Slf4j
  public abstract class AbstractStreamLi     stener extends EventSourceL    istener {  
      

    protected      StringBuffer lastMes     sage = new StringBu ffe     r();

   
         /          **
                     * C   alled  when all new    message  are r    eceived.
             *
     * @param   m         essage the new  message
     */
        @Setter
        @Ge      tter
    protected Cons     ume      r      <S     tring  Buf fer> onComplat      e = s -  > {

    }  ;

             /**
     * Called when a ne      w me    ssage is re   c   eived.
                  *                æ    ¶å°æ¶æ¯   å ä¸      ªå­
        *
            * @pa      ram mes   sage the new message
     */ 
    public abstract vo   id o      nM     sg(O             bje  c   t message) throws IOExcep      tion;

    /**
        * Called when an e   r              ror    occurs   .
     * åºéæ¶è°ç¨    
           *
       * @param throwabl  e the thro       wable that caus ed the err         or
         * @par         am response  the respon     se ass  ocia      ted with the          error,  if any
        */
//    public a bs trac   t void onEr   ror  (Th           ro wable thro wab     le, St    ring respons   e);
    public abst   ra               ct void onE  rror(Throwable thr  owable);

     @Overr       ide
    public  void       onOpen(E  ventSource   e       ventSource,   R    esponse  res   p onse) {
             //  do noth    ing 
            }

    @Override
         public void   on      Closed   (EventSource e            ven        tSource)        {
          // do  nothi    ng
             }

    @Sneaky  T        hrows
        @Override
     publ     ic void onEven   t(EventSource        eventSour      ce, Stri    ng i  d, String typ       e   , S tring data)        {
        log.info("å    ç­      ä¸­ï¼{}", data);
        i  f (data.equa   ls("[DONE]")   ) {
               onComp    late     . accept(l    astMes  sage);
                  onMsg(Message.ofA  ssistant("[   DO NE]"));
                             retur n;  
                          }
           
          ChatCompletionRespon se   res ponse = J  SONObject.       parseObj    ect(data, Chat   Co mpletionResponse.c  lass);
        // è¯»å Json
         L     i     st<Ch atC  hoic   e> choices = r    espon   se.getC   hoices();
             if (    choices ==    n     ull ||    cho        ices.isEmpty()   ) {
                             return  ;      
            }
        Mes  sage delta =   ch  o  i    ces.g   et(0). g       etDelta( );
                  Stri   ng   text =   delta.get   Cont  e       nt(   )        ;
               if (!StringUtils.isE  mpt                    y(text)) {
                 if(delt          a.        g     etContent().eq ual       s    ("<!"))  {
                   d  e    lta.se  tContent("```\\   n")    ;  
                te      xt = de    lta  .g   et    Cont      ent  ()  ;
                                                     l   astMessage        .append  (text);  
                      onMsg(del   ta);
                delta.set   Co    nte nt("<!"     );
                                     text  = d      elta.g       etContent(   );
                    lastMessage.append      (text);
                         onMsg      (delta);
                      }   e   lse {
                             lastMes     sage.append(tex    t)    ;
                    on  Msg(   d elta);
                            }
        }
    }


    @  Sn   ea   ky   Throws 
         @O   v erri de
    public     void onFailure(Eve   nt      So  urce eventSour   ce, Throwabl      e throwa            ble,    Response res   pons             e) {

          try {
              log.error("Stream       connecti  on error: {  }", throwable      );

                   Str      ing responseText =                "                      ";

                         if (Objects.n    onN  ull(respon  se)) {
                responseText = respon   se.body().s  tring  ();
                                }
                      log.e         rror("GPTå¼å¸¸log:{}",responseText );
//                  String[] a    uth   = even tS  ource.request().he          aders().toStri     ng().spl    it(" ");  
//                     String      key = au th[2];
            Stri ng forbidd  enText = "Your acces    s   was ter    m  inated due to violation of our     poli    cies";  
                   i  f (S  trUtil.contains(responseText, forbidde      nText)) {
                         log.error("Ch   at session has bee   n terminat  ed due to policy violation");
                log.error("æ£æµå° å·è¢«å°äº")  ;
                   }
                     String overloadedText = "That model is  currently ove    rloaded with othe  r requests.";
             if (St    rUtil.contains(responseText   , overloadedText))    {
                log.error("æ£æ  µå°å®æ¹è¶   è½½äºï¼èµ¶ç´§ä¼å    ä½ çä»£ç ï¼  åéè¯å§");
                   }
//             this.onError(throwab   le, key.replaceAll("\    \s*|\r|\n|\t",""));
            th    is.onError(throwable);
        } catch (Exception e) {
            log.warn("onFailure error:{}", e);
            // do nothing

        } finally {
            eventSource.cancel();
        }
    }
}
