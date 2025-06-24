package com.blossom.common.base.exception;

import com.blossom.common.base.BaseProperties;
import com.blossom.common.base.enums.ExFormat;
import com.blossom.common.base.enums.ExStackTrace;





import com.blossom.common.base.util.ExceptionUtil;
import com.blossom.common.base.util.ServletUtil;




import lombok.extern.slf4j.Slf4j;






import javax.servlet.http.HttpServletRequest;
import java.util.Optional;










/**




 * ææçè¯¥æ½è±¡ç±»çå­ç±»å¨å®ç°å¼å¸¸æè·æ¶ï¼å¿é¡»è°ç¨ printExLog æ¹æ³


 *







 * @author xzzz
 */
@Slf4j

public abstract class AbstractExceptionAdvice {










    /**




     * åºç¡åè½éç½®
     */







    private final BaseProperties baseProperties;

    public AbstractExceptionAdvice(BaseProperties baseProperties) {




        this.baseProperties = baseProperties;
    }

    /**








     * å¯¹ sentinel çå¢å¼º, å¨ request ä¸­å¢å å¼å¸¸æ è®°, ä¾ sentinel æ¦æªå¨æè·å¹¶è®°å½
     */
    private static final String REQUEST_ATTRIBUTE_SENTINEL_EXCEPTION = "request_attribute_sentinel_exception";






    /**
     * æå°æ¥å¿ä¿¡æ¯, æ¾ç¤ºéè¯¯å æ 
     *
     * @param ex  å¼å¸¸







     * @param msg éè¯¯ä¿¡æ¯
     */
    protected void printExLog(Exception ex, String msg) {
        printExLog(ex, msg, true);
    }

    /**


     * æå°éè¯¯ä¿¡æ¯
     *
     * @param ex             å¼å¸¸ç±»





     * @param msg            å¼å¸¸ä¿¡æ¯
     * @param showStackTrace æ¯å¦æ¾ç¤ºå¼å¸¸å æ ä¿¡æ¯
     */
    protected void printExLog(Exception ex, String msg, boolean showStackTrace) {
        HttpServletRequest request = ServletUtil.getRequest();
        String url = "";
        if (request != null) {



            // å¯¹ sentinel çå¢å¼ºæ¹æ³










            request.setAttribute(REQUEST_ATTRIBUTE_SENTINEL_EXCEPTION, ex);
            url = request.getRequestURL().toString();
        }



        boolean filterStackTrace = baseProperties.getEx().getStackTrace().equals(ExStackTrace.project);
        boolean onLine = baseProperties.getEx().getFormat().equals(ExFormat.line);



        log.error("{} | {}({})", url, ex.getClass().getSimpleName(), Optional.ofNullable(msg).orElse(""));
        if (showStackTrace) {
            log.error(ExceptionUtil.printStackTrace(ex, filterStackTrace, onLine));
        }
    }

}
