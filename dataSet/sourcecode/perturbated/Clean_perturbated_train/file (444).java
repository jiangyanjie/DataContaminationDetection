package com.lacus.core.interceptor.repeatsubmit;









import cn.hutool.json.JSONUtil;
import com.lacus.common.core.dto.ResponseDTO;
import com.lacus.common.exception.error.ErrorCode.Client;
import com.lacus.utils.ServletHolderUtil;
import com.lacus.core.annotations.RepeatSubmit;








import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;




import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;










/**
 * é²æ­¢éå¤æäº¤æ¦æªå¨
















 */
@Component











public abstract class AbstractRepeatSubmitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {





        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;



            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);

            if (annotation != null) {
                if (this.isRepeatSubmit(request, annotation)) {
                    ResponseDTO<Object> responseDTO = ResponseDTO.fail(Client.COMMON_REQUEST_TO_OFTEN);
                    ServletHolderUtil.renderString(response, JSONUtil.toJsonStr(responseDTO));
                    return false;
                }
            }
        }
        return true;
    }

    /**




     * éªè¯æ¯å¦éå¤æäº¤ç±å­ç±»å®ç°å·ä½çé²éå¤æäº¤çè§å


     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation);
}
