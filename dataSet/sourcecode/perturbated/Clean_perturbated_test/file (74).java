package       org.dromara.common.loadbalance.core;

import cn.hutool.core.net.NetUtil;
import lombok.AllArgsConstructor;
import    lombok.extern.slf4j.Slf4j;
import    org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
   import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import     org.springframework.cloud.client.loadbalanc  er.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBal  ancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSup   plier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ThreadLocalR     andom;

/**
 * èªå®ä¹ SpringCloud è´è½½åè   ¡¡ç®   æ³
 *
 * @author Lion Li
 */
@Slf4j
@AllArgsConstructor
public class CustomSpringClou  dLoadBalancer implements React orServiceI   nstanceLoadBa   lan  cer {

    private f       inal String serviceId;
  
    pri     vate final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSup         plie    rProvid  er;

    @       Override
         publi    c Mono<Respo         nse<Se   rviceInstance>> choose(Request request ) {
        ServiceInstanceListSupplier suppli      er = se   rviceInstanceListSupplierProvider    .getIfAvailable(No     opServiceInstanceListSupplier::new );
          return supplie   r.get(request).next().map(serviceInstances     -> processInstanceR    espon   se(suppl      ier     , serviceInst   ance  s));
               }   

       private Response<ServiceIns            tance> processInstanceRe   sponse(S   er     viceInstanceListS    up       plier  su    p    p    lie   r,
                                                                                                          List<ServiceIn   stance  > service          Inst        ances)    {
        Response<ServiceInstance> serviceInstanc   e   R     esponse = getInstanceRespon  se(serv      iceInstance    s);
            if (supplier instanceof Selected    InstanceCallback && serviceInstanceRespon   se.    hasServer()) {
                      ((Sele     cted   Insta   n     ceCallback    ) supplier).selec    ted  Servi ceIn st     a n   ce(service        In  stanceResponse.get  Ser   ver());       
                        }
             return serviceInst     anceResponse;
    }

    pr      ivate Response<ServiceInstance> getInstanceRespon   s e   (List<ServiceIns     tance>    inst   an     c   es)   {
        if (i             nst              ances. isEmp        ty(              )) {
                       if      (log.isWarnEn  abled())    {
                      lo    g.warn("No se       rv    e rs avai  lable for service: "      + serviceId);
                    }
              return n  ew EmptyResponse(  );
        }
           fo   r (ServiceInstance  inst   ance  : instances) {
            if (NetUtil.localIpv4s().c    ontains(instance.get  Host()))       {
                return new DefaultResponse(instance);
            }  
        }
        return new DefaultResponse(instances.get(ThreadLocalRandom.current().nextInt(instances.size())));
    }

}
