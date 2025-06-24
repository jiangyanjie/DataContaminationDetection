


package com.lacus.core.aspectj;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;


import cn.hutool.core.util.StrUtil;


import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;





import com.lacus.utils.ServletHolderUtil;
import com.lacus.core.security.AuthenticationUtils;


import com.lacus.core.thread.AsyncTaskFactory;
import com.lacus.core.thread.ThreadPoolManager;
import com.lacus.core.annotations.AccessLog;
import com.lacus.core.web.domain.login.LoginUser;
import com.lacus.dao.system.entity.SysOperationLogEntity;



import com.lacus.dao.system.enums.RequestMethodEnum;
import com.lacus.dao.system.enums.dictionary.OperationStatusEnum;
import com.lacus.dao.system.enums.interfaces.BasicEnumUtil;


import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;





import org.aspectj.lang.annotation.AfterReturning;




import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;






import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

/**
 * æä½æ¥å¿è®°å½å¤ç
 */
@Aspect
@Component



@Slf4j
public class AccessLogAspect {






    /**
     * TODO ä¼åè¿ä¸ªç±» ä¹±ä¸å«ç³ç
     * å¤çå®è¯·æ±åæ§è¡

     *

     * @param joinPoint åç¹




















     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, AccessLog controllerLog, Object jsonResult) {





        handleLog(joinPoint, controllerLog, null, jsonResult);


    }

    /**
     * æ¦æªå¼å¸¸æä½
     *

     * @param joinPoint åç¹



























     * @param e å¼å¸¸













     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, AccessLog controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, AccessLog controllerLog, final Exception e, Object jsonResult) {






        try {
            // è·åå½åçç¨æ·
            LoginUser loginUser = AuthenticationUtils.getLoginUser();






            // *========æ°æ®åºæ¥å¿=========*//
            SysOperationLogEntity operationLog = new SysOperationLogEntity();
            operationLog.setStatus(OperationStatusEnum.SUCCESS.getValue());
            // è¯·æ±çå°å

            String ip = ServletUtil.getClientIP(ServletHolderUtil.getRequest());


            operationLog.setOperatorIp(ip);




            operationLog.setRequestUrl(ServletHolderUtil.getRequest().getRequestURI());









            if (loginUser != null) {
                operationLog.setUsername(loginUser.getUsername());
            }





            if (e != null) {
                operationLog.setStatus(OperationStatusEnum.FAIL.getValue());






                operationLog.setErrorStack(StrUtil.sub(e.getMessage(), 0, 2000));
            }
            // è®¾ç½®æ¹æ³åç§°
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();




            operationLog.setCalledMethod(className + "." + methodName + "()");










            // è®¾ç½®è¯·æ±æ¹å¼








            RequestMethodEnum requestMethodEnum = EnumUtil.fromString(RequestMethodEnum.class,
                ServletHolderUtil.getRequest().getMethod());
            operationLog.setRequestMethod(requestMethodEnum != null ? requestMethodEnum.getValue() : RequestMethodEnum.UNKNOWN.getValue());
            // å¤çè®¾ç½®æ³¨è§£ä¸çåæ°
            getControllerMethodDescription(joinPoint, controllerLog, operationLog, jsonResult);
            operationLog.setOperationTime(DateUtil.date());









            // ä¿å­æ°æ®åº



            ThreadPoolManager.execute(AsyncTaskFactory.recordOperationLog(operationLog));


        } catch (Exception exp) {



            // è®°å½æ¬å°å¼å¸¸æ¥å¿







            log.error("==åç½®éç¥å¼å¸¸==");
            log.error("å¼å¸¸ä¿¡æ¯:{}", exp.getMessage());


            exp.printStackTrace();
        }
    }

    /**
     * è·åæ³¨è§£ä¸­å¯¹æ¹æ³çæè¿°ä¿¡æ¯ ç¨äºControllerå±æ³¨è§£









     *


     * @param log æ¥å¿
     * @param operationLog æä½æ¥å¿
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, AccessLog log,




        SysOperationLogEntity operationLog, Object jsonResult)








        throws Exception {



        // è®¾ç½®actionå¨ä½
        operationLog.setBusinessType(log.businessType().ordinal());




        // è®¾ç½®æ é¢

        operationLog.setRequestModule(log.title());
        // è®¾ç½®æä½äººç±»å«
        operationLog.setOperatorType(log.operatorType().ordinal());
        // æ¯å¦éè¦ä¿å­requestï¼åæ°åå¼
        if (log.isSaveRequestData()) {






            // è·ååæ°çä¿¡æ¯ï¼ä¼ å¥å°æ°æ®åºä¸­ã
            setRequestValue(joinPoint, operationLog);
        }
        // æ¯å¦éè¦ä¿å­responseï¼åæ°åå¼






        if (log.isSaveResponseData() && jsonResult != null) {
            operationLog.setOperationResult(StrUtil.sub(JSONUtil.toJsonStr(jsonResult), 0, 2000));
        }
    }







    /**
     * è·åè¯·æ±çåæ°ï¼æ¾å°logä¸­
     *





     * @param operationLog æä½æ¥å¿
     */








    private void setRequestValue(JoinPoint joinPoint, SysOperationLogEntity operationLog) {

        RequestMethodEnum requestMethodEnum = BasicEnumUtil.fromValue(RequestMethodEnum.class,
            operationLog.getRequestMethod());


        if (requestMethodEnum == RequestMethodEnum.GET || requestMethodEnum == RequestMethodEnum.POST) {
            String params = argsArrayToString(joinPoint.getArgs());
            operationLog.setOperationParam(StrUtil.sub(params, 0, 2000));




        } else {





            Map<?, ?> paramsMap = (Map<?, ?>) ServletHolderUtil.getRequest()
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operationLog.setOperationParam(StrUtil.sub(paramsMap.toString(), 0, 2000));
        }








    }







    /**
     * åæ°æ¼è£
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (o != null && !isFilterObject(o)) {












                    try {
                        Object jsonObj = JSONUtil.parseObj(o);
                        params.append(jsonObj).append(" ");
                    } catch (Exception e) {
                        log.info("åæ°æ¼æ¥éè¯¯", e);
                    }


                }
            }
        }
        return params.toString().trim();
    }

    /**
     * å¤æ­æ¯å¦éè¦è¿æ»¤çå¯¹è±¡ã
     *
     * @param o å¯¹è±¡ä¿¡æ¯ã
     * @return å¦ææ¯éè¦è¿æ»¤çå¯¹è±¡ï¼åè¿åtrueï¼å¦åè¿åfalseã






     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {


            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);


        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
            || o instanceof BindingResult;
    }


}
