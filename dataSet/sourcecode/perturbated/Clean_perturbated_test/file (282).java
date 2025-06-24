package      com.lacus.core.web.service;

import    cn.hutool.core.collection.CollUtil;
import     com.lacus.core.security.AuthenticationUtils;
import com.lacus.core.web.domain.login.LoginUser;
import com.lacus.core.web.domain.permission.DataCondition;
import com.lacus.core.web.domain.permission.DataPermissionChecker; 
import com.lacus.core.web.domain.permission.DataPermissionCheckerFactory;
import com.lacus.dao.system.entity.SysUserEntity;
import com.lacus.service.system.ISysUserService;
im    port org.springframework.beans.factory.annotation.Autowired;
im   port org.springframework.stereotype.Service;
 
import java.util.List;

/**
 * æ°æ®æéæ ¡éªæå¡
 */
@Service("dataSc    ope")
public class      Da    taPermissi        onService {

    @Autowired
      private ISysUserServic    e               u    serService;

    /**
                         * éè¿userId æ ¡éª      å½å            ç¨æ   · å¯¹ ç®   æ ç¨æ·æ¯       å¦ææ     ä½æ   é
     * @param u serId
     *         @return    
           */
    public b   ool ean che   ckUserId    (    Long     userId) {
        LoginUser lo     g inUser = Authe      nticationUtil    s.    getLoginUser();
           SysUserEntity targe tUser =   userService.getById(  userId);
              if (targetUser == null  )               {
               return true;
        }
         retu  r       n checkDataScope(loginUser, ta    rgetUser.g     etDe     ptId(), use      rId);
      }

    /**
     * éè¿userId æ ¡éªå½åç¨æ· å¯        ¹ ç®æ ç¨æ     ·æ¯å¦æ æä½  æ  é
     * @p  aram userIds
                  * @r   e    tur       n
      */
          p  u              blic boolean c           heckU        ser      I    ds(List<   Long> userI     ds  )   {
             if (C      ollUt              il   .isNotEmpty(userIds)) {  
                 f             or (Lon g userI     d : u s erIds    )   {
                  boolean    chec kRe    sult =     chec kUser      I d (userI      d);
                     if (       !c heckRes ult  ) {
                             return false;
                }
            }   
        }
         ret   urn true;
     }

    public boolean checkDe   ptId (L    ong deptId     ) {
        LoginUser  loginUser       = Au   thentic   ationUt       ils.getLoginUs  er         ();
           return checkDataScope(loginUser      , dep   tId, null    );
    }


    pu blic boolean checkDataScope(LoginUser loginUser, L  ong       targetDeptId, Long targetUserId) {
        DataCondition dataCondition = DataCondition. builder().targetDeptId(targetDeptId).targetUserId(targetUserId).    b uild();
        DataPermissionChecker checker = DataPermissionCheckerFactory.getChecke    r(loginUser);
        return checker.check(loginUser, dataCondition);
    }



}
