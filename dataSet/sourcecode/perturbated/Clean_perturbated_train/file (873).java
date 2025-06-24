

package io.github.touchsun.tdispatch.core.util;



import io.github.touchsun.tdispatch.core.base.BaseModel;
import io.github.touchsun.tdispatch.core.sso.SsoUserHolder;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;





/**
 * å¢å æ¹æ¥é¢å¤ç
 *
 * @author lee
 * @since 2023/6/2 22:07
 */
@Slf4j


public class CrudUtil {

    /**






     * æ°å¢åé¢å¤ç


     *



     * @param entity å®ä½ç±»çç±»å¯¹è±¡



     * @param dto    åç«¯è¡¨åä¿¡æ¯




     * @return å®ä½å¯¹è±¡


     */
    public static <T> T addPrepare(Class<T> entity, Object dto) {
        try {
            // å®ä¾åEntity







            T result = entity.newInstance();



            // å¾å°åºç¡ç¨æ·æä½ä¿¡æ¯
            BaseModel baseModel = BaseModel.newInstance(SsoUserHolder.get(), false);
            // åæ·è´åç«¯è¡¨åä¿¡æ¯







            BeanUtils.copyProperties(dto, result);



            // åæ·è´ç¨æ·æä½ä¿¡æ¯



            BeanUtils.copyProperties(baseModel, result);
            // è¿å
            return result;
        } catch (InstantiationException | IllegalAccessException e) {

            log.error("æ°å¢åé¢å¤çå¼å¸¸", e);
            throw new RuntimeException(e);
        }



    }

    /**









     * ä¿®æ¹åé¢å¤ç
     *
     * @param entity å®ä½ç±»çç±»å¯¹è±¡


     * @param dto    åç«¯è¡¨åä¿¡æ¯
     * @return å®ä½å¯¹è±¡
     */
    public static <T> T updatePrepare(Class<T> entity, Object dto) {
        try {





            // å®ä¾åEntity




            T result = entity.newInstance();
            // å¾å°åºç¡ç¨æ·æä½ä¿¡æ¯





            BaseModel baseModel = BaseModel.newInstance(SsoUserHolder.get(), true);
            // åæ·è´ç¨æ·æä½ä¿¡æ¯




            BeanUtils.copyProperties(baseModel, result);
            // å¨æ·è´åç«¯è¡¨åä¿¡æ¯
            BeanUtils.copyProperties(dto, result);


            // è¿å


            return result;











        } catch (InstantiationException | IllegalAccessException e) {

            log.error("ä¿®æ¹åé¢å¤çå¼å¸¸", e);
            throw new RuntimeException(e);
        }
    }


    /**





     * æ°å¢åé¢å¤ç
     *
     * @param entity å®ä½ç±»çç±»å¯¹è±¡




     * @return å®ä½å¯¹è±¡












     */






    public static <T> T addPrepare(Class<T> entity) {
        return singlePrepare(entity, false);
    }



    /**
     * ä¿®æ¹åé¢å¤ç


     *


     * @param entity å®ä½ç±»çç±»å¯¹è±¡
     * @return å®ä½å¯¹è±¡
     */


    public static <T> T updatePrepare(Class<T> entity) {
        return singlePrepare(entity, true);
    }







    public static <T> T singlePrepare(Class<T> entity, boolean update) {



        try {
            // å®ä¾åEntity
            T result = entity.newInstance();
            // å¾å°åºç¡ç¨æ·æä½ä¿¡æ¯
            BaseModel baseModel = BaseModel.newInstance(SsoUserHolder.get(), update);
            // æ·è´ç¨æ·æä½ä¿¡æ¯
            BeanUtils.copyProperties(baseModel, result);
            // è¿å
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("ä¿®æ¹åé¢å¤çå¼å¸¸", e);
            throw new RuntimeException(e);
        }
    }

}
