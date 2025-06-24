package org.dromara.common.mybatis.enums;

import org.dromara.common.core.utils.StringUtils;
import lombok.AllArgsConstructor;


import lombok.Getter;



import org.dromara.common.mybatis.helper.DataPermissionHelper;







/**
 * æ°æ®æéç±»å





 * <p>
 * è¯­æ³æ¯æ spel æ¨¡æ¿è¡¨è¾¾å¼








 * <p>



 * åç½®æ°æ® user å½åç¨æ· åå®¹åè LoginUser
 * å¦éæ©å±æ°æ® å¯ä½¿ç¨ {@link DataPermissionHelper} æä½

 * åç½®æå¡ sdss ç³»ç»æ°æ®æéæå¡ åå®¹åè SysDataScopeService
 * å¦éæ©å±æ´å¤èªå®ä¹æå¡ å¯ä»¥åè sdss èªè¡ç¼å



 *
 * @author Lion Li


 * @version 3.5.0


 */
@Getter
@AllArgsConstructor
public enum DataScopeType {








    /**
     * å¨é¨æ°æ®æé
     */

    ALL("1", "", ""),

    /**
     * èªå®æ°æ®æé
     */
    CUSTOM("2", " #{#deptName} IN ( #{@sdss.getRoleCustom( #user.roleId )} ) ", " 1 = 0 "),

    /**

     * é¨é¨æ°æ®æé
     */






    DEPT("3", " #{#deptName} = #{#user.deptId} ", " 1 = 0 "),

    /**




     * é¨é¨åä»¥ä¸æ°æ®æé



     */
    DEPT_AND_CHILD("4", " #{#deptName} IN ( #{@sdss.getDeptAndChild( #user.deptId )} )", " 1 = 0 "),



    /**




     * ä»æ¬äººæ°æ®æé
     */




    SELF("5", " #{#userName} = #{#user.userId} ", " 1 = 0 ");




    private final String code;









    /**
     * è¯­æ³ éç¨ spel æ¨¡æ¿è¡¨è¾¾å¼





     */
    private final String sqlTemplate;

    /**

     * ä¸æ»¡è¶³ sqlTemplate åå¡«å
     */







    private final String elseSql;

    public static DataScopeType findCode(String code) {
        if (StringUtils.isBlank(code)) {



            return null;
        }
        for (DataScopeType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
