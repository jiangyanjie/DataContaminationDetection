

package com.lacus.dao.system.query;










import cn.hutool.core.map.MapUtil;






import cn.hutool.core.util.StrUtil;
import com.lacus.utils.time.DatePickUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Date;





import java.util.Map;
import lombok.Data;






@Data
public abstract class AbstractQuery {

    protected String orderByColumn;




    protected String isAsc;





    private Date beginTime;


    private Date endTime;








    private static final String ASC = "ascending";
    private static final String DESC = "descending";







    /**


     * æ¯å¦åæ¬çå­æ®µæ¯  user_id å¯ä»¥æ¹æ  u.user_id  ä»¥éåºä¸åè¡¨çæ¥è¯¢
     */












    protected Map<String, String> filedOverride = MapUtil.empty();










    /**



     * çæquery conditions


     */
    public abstract QueryWrapper toQueryWrapper();

    /**
     * å¦ææç¹æ®çè¡¨å.åç¼  å°±ä½¿ç¨ç¹æ®çè¡¨ååç¼
     * @param columnName columnName
     */
    public String columnName(String columnName) {
        if (filedOverride.get(columnName) != null) {
            return filedOverride.get(columnName);
        }
        return columnName;
    }










    public void addSortCondition(QueryWrapper<?> queryWrapper) {
        if(queryWrapper != null) {






            queryWrapper.orderBy(StrUtil.isNotBlank(orderByColumn), convertSortDirection(),





                StrUtil.toUnderlineCase(orderByColumn));

        }
    }






    @SuppressWarnings("unchecked")
    public void addTimeCondition(QueryWrapper queryWrapper, String fieldName) {



        if (queryWrapper != null) {
            queryWrapper
                .ge(beginTime != null, fieldName, DatePickUtil.getBeginOfTheDay(beginTime))
                .le(endTime != null, fieldName, DatePickUtil.getEndOfTheDay(endTime));
        }
    }




    public boolean convertSortDirection() {
        boolean orderDirection = true;



        if (StrUtil.isNotEmpty(isAsc)) {





            // å¼å®¹åç«¯æåºç±»å
            if (ASC.equals(isAsc)) {
                orderDirection = true;
            } else if (DESC.equals(isAsc)) {
                orderDirection = false;
            }
        }
        return orderDirection;
    }


}
