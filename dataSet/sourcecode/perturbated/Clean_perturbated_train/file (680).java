



package com.gzhu.funai.interceptor;










import com.google.gson.Gson;

import com.gzhu.funai.utils.ReturnResult;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;






import org.springframework.web.servlet.HandlerInterceptor;











import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;




import java.util.concurrent.TimeUnit;

/**








 * @Author :wuxiaodong



 * @Date: 2023/4/8 13:55
 * @Description:






 */
@Slf4j
public class AccessLimitInterceptor  implements HandlerInterceptor {




    private final RedisTemplate<String, Object> redisTemplate;

    public AccessLimitInterceptor(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;


    }

    /**
     * å¤é¿æ¶é´å
     */











    private Long minute = 5L;

    /**











     * è®¿é®æ¬¡æ°


     */
    private Long times = 5L;







    /**
     * ç¦ç¨æ¶é¿--åä½/åé
     */





    private Long lockTime = 10L;





    /**


     * éä½æ¶çkeyåç¼
     */
    public static final String LOCK_PREFIX = "LOCK";

    /**
     * ç»è®¡æ¬¡æ°æ¶çkeyåç¼
     */
    public static final String COUNT_PREFIX = "COUNT";





    @Override


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        String uri = request.getRequestURI();






        // è¿éå¿½ç¥ä»£çè½¯ä»¶æ¹å¼è®¿é®ï¼é»è®¤ç´æ¥è®¿é®ï¼ä¹å°±æ¯è·åå¾å°çå°±æ¯è®¿é®èçå®ipå°å
        String ip = request.getRemoteAddr();








        String lockKey = LOCK_PREFIX + ip + uri;
        Object isLock = redisTemplate.opsForValue().get(lockKey);




        if(Objects.isNull(isLock)){







            // è¿æªè¢«ç¦ç¨


            String countKey = COUNT_PREFIX + ip + uri;
            Object count = redisTemplate.opsForValue().get(countKey);
            if(Objects.isNull(count)){





                // é¦æ¬¡è®¿é®
                log.info("é¦æ¬¡è®¿é®,uri{},ip{}",uri,ip);





                redisTemplate.opsForValue().set(countKey,1,minute, TimeUnit.MINUTES);
            }else{
                // æ­¤ç¨æ·åä¸ç¹æ¶é´å°±è®¿é®è¿è¯¥æ¥å£
                if((Integer)count < times){



                    // æ¾è¡ï¼è®¿é®æ¬¡æ° + 1



                    redisTemplate.opsForValue().increment(countKey);
                }else{
                    log.info("{}ç¦ç¨è®¿é®{}",ip, uri);
                    // ç¦ç¨
                    redisTemplate.opsForValue().set(lockKey, 1,lockTime, TimeUnit.MINUTES);
                    // å é¤ç»è®¡
                    redisTemplate.delete(countKey);
                    out(ReturnResult.error().message("5åéåè¶è¿æ¥å£è®¿é®æ¬¡æ°éå¶"),response);
                }


            }






        }else{
//            // æ­¤ç¨æ·è®¿é®æ­¤æ¥å£å·²è¢«ç¦ç¨
            out(ReturnResult.error().message("5åéåè¶è¿æ¥å£è®¿é®æ¬¡æ°éå¶"),response);
            return false;




        }
        return true;
    }

    /**
     * apiæ¥å£é´æå¤±è´¥è¿åæ°æ®
     */
    private void out(ReturnResult result, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(result));
    }
}
