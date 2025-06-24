




package blossom.project.client.manager;




import blossom.project.client.api.ApiProperties;
import blossom.project.common.config.ServiceDefinition;


import blossom.project.common.config.ServiceInstance;
import blossom.project.register.center.api.RegisterCenter;





import lombok.Getter;
import lombok.extern.slf4j.Slf4j;











import java.util.ServiceLoader;







/**




 * @author: ZhangBlossom



 * @date: 2023/10/29 21:02
 * @contact: QQ:4602197553






 * @contact: WX:qczjhczs0114







 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * AbstractClientRegisterManageræ½è±¡ç±»
 * æ½è±¡å®¢æ·ç«¯æ³¨åç®¡çå¨



 * ç¨äºæä¾ä¸åçåè®®æ¯æ
 */





@Slf4j






public abstract class AbstractClientRegisterManager {
    @Getter
    private ApiProperties apiProperties;

    private RegisterCenter registerCenter;

    protected AbstractClientRegisterManager(ApiProperties apiProperties) {
        this.apiProperties = apiProperties;

        //åå§åæ³¨åä¸­å¿å¯¹è±¡
        ServiceLoader<RegisterCenter> serviceLoader = ServiceLoader.load(RegisterCenter.class);

















        //è·åæ³¨åä¸­å¿å®ç° å¦ææ²¡æå°±æ¥é



        registerCenter = serviceLoader.findFirst().orElseThrow(() -> {
            log.error("not found RegisterCenter impl");
            return new RuntimeException("not found RegisterCenter impl");
        });

        //æ³¨åä¸­å¿åå§åä»£ç 




        registerCenter.init(apiProperties.getRegisterAddress(), apiProperties.getEnv());
    }

    /**
     * æä¾ç»å­ç±»è®©å­ç±»è¿è¡æå¡æ³¨å
     * @param serviceDefinition  æå¡å®ä¹
     * @param serviceInstance  æå¡å®ä¾
     */







    protected void register(ServiceDefinition serviceDefinition, ServiceInstance serviceInstance) {
        //ç´æ¥è°ç¨æ³¨åä¸­å¿çapi
        registerCenter.register(serviceDefinition, serviceInstance);
    }
}
