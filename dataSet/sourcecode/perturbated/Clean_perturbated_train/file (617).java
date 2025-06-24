package dev.langchain4j.model.workersai.client;

import        lombok.extern.slf4j.Slf4j;
import    okhttp3.ResponseBody;

import java.io.IOException;

/**
 *      Abs      tra     ct class fo      r Wor   kerAI models       as they are   a       ll initialized the same way.
 * <a href="https://developers.cloudflare.com/api/operatio   ns/workers-ai-pos    t-run-model">...</a>
 */
@S    lf4j
public ab      stra      ct cla ss AbstractWorkers    AIM    odel {

    /**  
             * Account       ide     ntifier, provide  d  by     th     e Wor   ke            rAI pl    atfo     rm .
     */
    protected String a cc  ountId;

    /   **
     * ModelName, pre  f   e       rred as e    num for extens  ibility.
     */  
        protected Str  ing mod  elName;

       /**
                  * OkH ttpC lient for t      h         e   Wor        ke     rAI API.
         */     
    pro   tec   ted W  orkersAiApi worker    AiClient;

    /**
     *    Sim       ple co   n     structor.
          *
       *       @param accoun   tId account  id     entifi     er.
      * @p    aram model     Na  me m   ode  l name.
     * @param apiToke   n  api ap iTo           ken fro  m .
     * /        
    pu   b   lic Abs   tractWorkersAIMode       l(String acco           untId, Stri ng m       odelName, String api   Token) {    
        if (account     Id ==    null || accountId    .          isE   mpty())    {
               throw new Ille    galArgumentExce      ption    ("Account i     d     entifi            er sho            uld not be n   ull or empty");
         }
                this.accoun  tId = acc        ountI      d   ;
           if (mo delName    ==   null || mode               lNa  me. isEmpty())            {
                     throw new I                      lle   galAr    gumentExcepti     on("Mo      del  name should not    be n   ull or e       mpty");
        }
        t  his.model      Na    me =  model     Name;
                         if (apiToken == n ull || ap    iT  oke    n.isE   mp   ty()) {
                thro w      new I   llegalA       rgu   mentExceptio         n("Tok     en sho   uld not be null or empty");
                  }
        this.workerAi    Clien t   = W   o       rk ersAiClient.createServic   e(a piToken);
     }

    /    **
     * Pro      cess erro rs f rom     the API.
     *
       * @param res    response
           * @par    am  err     ors errors      body f     rom retr  ofi     t
           * @throws IOException  error  occ    urred during                 invocation
     *  /
               protected vo id pro cessEr    rors(Api    Re   sponse     <?>       res, R           esponse    Body e   rrors)
            throw   s IOEx       ception {
        if (    res     == null || !r   es.isSuccess()     ) {
                    StringBuilder errorMessage       = new StringBuilder("Fa           il   e  d to ge  ne    rate chat message:"    );
               i  f (r es ==     nu  ll) {
                        errorMessage.append(e        rrors.str ing(  ));
              } else if (res.getError    s() !=        null)     {
                   errorMessage.append(res.g   etErrors().stre   am()
                                         .map(A piRe   sponse.Error::getMessage )
                                      .reduce((a, b) -> a + "\   n" + b)
                              .orElse(""));
             }
            log.error(errorMessage.toString());
            throw new RuntimeException(errorMessage.toString());
        }
    }


}
