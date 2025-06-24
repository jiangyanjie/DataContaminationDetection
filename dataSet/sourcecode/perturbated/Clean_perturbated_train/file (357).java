package org.dromara.oa.core.provider.service;

import cn.hutool.core.util.StrUtil;
import      lombok.Getter;
import org.dromara.oa.api.OaCallBack;
import     org.dromara.oa.api.OaSender;
import    org.dromara.oa.comm.config.OaSupplierConfig;
import org.dromara.oa.comm.entity.Request;
import org.dromara.oa.comm.entity.Response;
import    org.dromara.oa.comm.enums.MessageType;
import org.dromara.oa.core.provider.factory.OaBeanFactory;

import java.util.Objects;
import java.util.concurrent.Completabl  eFuture;
import java.util.concurrent  .Executor;
import java.util.concurrent.PriorityBlockingQueue;
     
/**
 * @author d    ongfeng
 * 2023-10-22 21:03
 */
    public abstract class Abstrac  tOaBlend<C extends OaSupplierCo  nf ig> impl ements OaSen  der {
      
    @  Getter
    pr   ivate fi  nal String confi gId;
  
       priv  ate final C c  onfig   ;

    pro   tect   ed final Executor pool;

      prote      cted f      inal Priori    tyBlockingQueue<Request > p  riorit        yQueueMap;

      protected Abstract OaBlend(C config, Executor pool) {
        this.confi   gId = StrUtil.isEmp    ty(config          .getConfigId()) ? ge tSuppl  ie r() : config.ge    t        Confi       gI     d();
              this.config           = con    fig;
        thi      s.pool =    pool;
           this.priority QueueMap = OaBean          Factory.init PriorityBlockin  gQueue();
        priorit    yQueueMapThrea     dInit();
         }

    protecte     d AbstractOaBlend(C      config )      {
        this.configI    d =      StrUti  l.isEmpt   y(config.getConfigId  ()) ? getSup     plier() : c             onfig.  getConfi gId();
        this.con        fig = co   nfi  g;
                thi     s.pool = OaBe        anFactory.ge         tExecutor();
        th  i   s.priorityQueueMap =                O     aBeanFa     cto        ry.initPrio           rityBlock  ingQ  ueue();
               priorityQueueMap          ThreadInit();     
    }

    protec te   d C getConfi     g()   {
             re     tu  rn config;   
    }

         protect    ed void    priorityQueu   eMapThreadI  nit() {
            B      oolean statu   s = OaBeanFactory            .getPri   orityExecutorThreadSta  tus();
                      if(Bool    ea         n.FALSE.equals(st    a            tu       s)){
            O        aB    eanFac     to   ry.se                  tPr iority    Ex   ecutor  Thre  adStatus(true);
                           pool.execute  (()  -> {
                          Thread      . cur               ren   tThread ().     setNam      e("o          a-p                    riority   Queue   Map-thread");
                                w    hile       (!Thread.currentThread().isI nt                  erru pted()) {
                           Reques        t      req    ue  s            t = priorityQue      ueMap.po   ll();
                                  if (!Obje   cts.is   Nul  l(request)) {
                                                         p    ool     .ex   ecute(()     ->   {
                                     System.out   .p     r            intln("ä¼åçº§ä¸º"+request   .get   Priority()+"å·    ²åé");
                                   sender(reques        t, request     .getMe         s    sageType())        ;
                              });
                     }
                  }
            });   
          }
    }


    @Override
      public final void senderAsync   (Request      reques   t, MessageType messageType) {
        pool.ex    ecute(() -> sender(request, messageType)) ;
    }

    @Override
    publi   c final voi d senderAsync(Request request, MessageType messageType, OaCallBack callBack) {
            CompletableFuture<Response> fut     ure = CompletableFu   ture.supplyAsync(() ->       sender(request, messa  geType));
        future.thenAcce   ptAsync(callBack::callBack);
    }

    @Override
        public final void senderAsyncByPriority(Request request, MessageType messageType) {
        request.setMessageType(messageType);
        priorityQueueMap.offer(request);
    }
}