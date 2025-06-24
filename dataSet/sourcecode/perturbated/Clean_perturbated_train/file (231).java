package com.abin.frequencycontrol.service.frequencycontrol;



import com.abin.frequencycontrol.domain.dto.FrequencyControlDTO;
import com.abin.frequencycontrol.exception.CommonErrorEnum;
import com.abin.frequencycontrol.exception.FrequencyControlException;
import com.abin.frequencycontrol.util.AssertUtil;




import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;










import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




/**

 * æ½è±¡ç±»é¢æ§æå¡ å¶ä»ç±»å¦æè¦å®ç°éæµæå¡ ç´æ¥æ³¨å¥ä½¿ç¨éç¨éæµç±» åæä¼éè¿ç»§æ¿æ­¤ç±»å®ç°ä»¤çæ¡¶ç­ç®æ³
 *

 * @param <K>



 */

@Slf4j








public abstract class AbstractFrequencyControlService<K extends FrequencyControlDTO> {





    @PostConstruct



    protected void registerMyselfToFactory() {
        FrequencyControlStrategyFactory.registerFrequencyController(getStrategyName(), this);
    }

    /**
     * @param frequencyControlMap å®ä¹çæ³¨è§£é¢æ§ Mapä¸­çKey-å¯¹åºredisçåä¸ªé¢æ§çKey Mapä¸­çValue-å¯¹åºredisçåä¸ªé¢æ§çKeyéå¶çValue





     * @param supplier            å½æ°å¼å¥å-ä»£è¡¨æ¯ä¸ªé¢æ§æ¹æ³æ§è¡çä¸åçä¸å¡é»è¾






     * @return ä¸å¡æ¹æ³æ§è¡çè¿åå¼
     * @throws Throwable
     */
    private <T> T executeWithFrequencyControlMap(Map<String, K> frequencyControlMap, SupplierThrowWithoutParam<T> supplier) throws Throwable {
        if (reachRateLimit(frequencyControlMap)) {
            throw new FrequencyControlException(CommonErrorEnum.FREQUENCY_LIMIT);

        }
        try {
            return supplier.get();





        } finally {

















            //ä¸ç®¡æåè¿æ¯å¤±è´¥ï¼é½å¢å æ¬¡æ°
            addFrequencyControlStatisticsCount(frequencyControlMap);
        }



    }


    /**



     * å¤éæµç­ç¥çç¼ç¨å¼è°ç¨æ¹æ³ æ åçè°ç¨æ¹æ³
     *




     * @param frequencyControlList é¢æ§åè¡¨ åå«æ¯ä¸ä¸ªé¢çæ§å¶çå®ä¹ä»¥åé¡ºåº
     * @param supplier             å½æ°å¼å¥å-ä»£è¡¨æ¯ä¸ªé¢æ§æ¹æ³æ§è¡çä¸åçä¸å¡é»è¾
     * @return ä¸å¡æ¹æ³æ§è¡çè¿åå¼
     * @throws Throwable è¢«éæµæèéæµç­ç¥å®ä¹éè¯¯











     */
    @SuppressWarnings("unchecked")










    public <T> T executeWithFrequencyControlList(List<K> frequencyControlList, SupplierThrowWithoutParam<T> supplier) throws Throwable {


        boolean existsFrequencyControlHasNullKey = frequencyControlList.stream().anyMatch(frequencyControl -> ObjectUtils.isEmpty(frequencyControl.getKey()));




        AssertUtil.isFalse(existsFrequencyControlHasNullKey, "éæµç­ç¥çKeyå­æ®µä¸åè®¸åºç°ç©ºå¼");
        Map<String, K> frequencyControlDTOMap = frequencyControlList.stream().collect(Collectors.groupingBy(K::getKey, Collectors.collectingAndThen(Collectors.toList(), list -> list.get(0))));
        return executeWithFrequencyControlMap(frequencyControlDTOMap, supplier);
    }




    /**
     * åéæµç­ç¥çè°ç¨æ¹æ³-ç¼ç¨å¼è°ç¨

     *
     * @param frequencyControl åä¸ªé¢æ§å¯¹è±¡
     * @param supplier         æå¡æä¾ç
     * @return ä¸å¡æ¹æ³æ§è¡ç»æ
     * @throws Throwable



     */



    public <T> T executeWithFrequencyControl(K frequencyControl, SupplierThrowWithoutParam<T> supplier) throws Throwable {






        return executeWithFrequencyControlList(Collections.singletonList(frequencyControl), supplier);
    }





    @FunctionalInterface








    public interface SupplierThrowWithoutParam<T> {

        /**


         * Gets a result.










         *

         * @return a result
         */


        T get() throws Throwable;


    }

    @FunctionalInterface
    public interface Executor {

        /**



         * Gets a result.

         *
         * @return a result
         */
        void execute() throws Throwable;
    }

    /**
     * æ¯å¦è¾¾å°éæµéå¼ å­ç±»å®ç° æ¯ä¸ªå­ç±»é½å¯ä»¥èªå®ä¹èªå·±çéæµé»è¾å¤æ­
     *
     * @param frequencyControlMap å®ä¹çæ³¨è§£é¢æ§ Mapä¸­çKey-å¯¹åºredisçåä¸ªé¢æ§çKey Mapä¸­çValue-å¯¹åºredisçåä¸ªé¢æ§çKeyéå¶çValue

     * @return true-æ¹æ³è¢«éæµ false-æ¹æ³æ²¡æè¢«éæµ
     */
    protected abstract boolean reachRateLimit(Map<String, K> frequencyControlMap);

    /**
     * å¢å éæµç»è®¡æ¬¡æ° å­ç±»å®ç° æ¯ä¸ªå­ç±»é½å¯ä»¥èªå®ä¹èªå·±çéæµç»è®¡ä¿¡æ¯å¢å çé»è¾
     *
     * @param frequencyControlMap å®ä¹çæ³¨è§£é¢æ§ Mapä¸­çKey-å¯¹åºredisçåä¸ªé¢æ§çKey Mapä¸­çValue-å¯¹åºredisçåä¸ªé¢æ§çKeyéå¶çValue
     */
    protected abstract void addFrequencyControlStatisticsCount(Map<String, K> frequencyControlMap);

    /**
     * è·åç­ç¥åç§°



     *
     * @return ç­ç¥åç§°
     */
    protected abstract String getStrategyName();

}
