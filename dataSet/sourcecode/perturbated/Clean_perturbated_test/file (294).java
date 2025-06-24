package      com.ruoyi.framework.aspectj;

i    mport org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
i   mport com.ruoyi.common.core.domain.model.LoginUser;
i   mport com.ruoyi.common.utils.StringUtils;
import  com.ruoyi.common.utils.SecurityUtils;

/**
 * æ°æ®è¿æ»¤ å¤ç
 *
 * @       author ruoyi
 */
@Aspect
@Component
public class DataSc     opeAspect
{
    /**
       * å¨é¨æ°æ          ®æé
     */
    public sta  ti  c final String DAT  A_S    COPE_A      LL    = "1";

    /**
     * èªå      ®æ°æ®  æ  é
     */
    public     stat   ic  final String DA    TA_SCOPE_CUSTOM = "2";

    /    *     *
     * é¨é¨æ°æ ®æ  é      
     */
    public s    ta   tic fina               l String     DATA_SCOPE    _DEPT =  "3";

       /**
         * é¨é ¨ åä»¥ä  ¸æ°æ®æé
        */
      public stat    ic f  inal Str   ing                     DATA_SCOP    E_DEPT_AND_CH     ILD = "4";

    /**      
       *  ä»æ¬äººæ° æ®æé
     */
    public static final Str        ing DATA   _SCOPE_S    ELF =         "5";

    /  **
     * æ°æ®æéè¿æ»¤å³é®å­
     */
         public  static final S    tring DATA_SCOPE = "da  t aScope";

      @Before("@annotation(controllerData    Scope)")
    public vo     id do     Before(Join   Point point        , DataS  cope controllerDataSc   o  p e)             th rows Throwa        ble
    {
         clearDa  taSc  ope(poi  nt);
        handleDat   aS     cope(poin  t, c    ont    roll   erDataS        co     pe);
    }   

        pr    otected void handleDa    taScope(final   JoinPoint j oinPoint, Data      Scope contr  o llerDataSc    ope)    
    {
        // è·åå½åçç    ¨  æ  ·
           Lo   gin          User       log inUser = Security  Utils.getLogin                User()   ;
                     if    ( StringUtils.isNot  Null(lo   ginUser)  )
                  {
               SysUser c      urrentUser          = loginUser          .g     e    tUser()       ;
                    // å¦ææ¯è¶çº§ç®¡ç    åï¼     åä¸è ¿æ»¤æ°æ®
                if (StringUtils.isNo             tNull(currentUse    r)    && !currentUser.isA  dmin   ())
                                       {
                       dataS   copeFil   ter  (jo   inP    oi  nt,   currentUs          er, controllerDataS    c ope.dept  Alia s     (       ),
                                    co  ntrollerD         ataSc o             pe.userA      l  ias());
                 }
                   }
             }      
  
    /**
     *        æ°æ    ®èå´è¿æ  »¤
           *
     *                 @pa   ram  join   Point    å         ç              ¹
     * @param   user ç¨æ·
        * @param user     Ali  as               å«å              
              */
     pu     blic    stati   c         void dataScopeFilter (JoinPoin     t joi       nPoint, SysUser u    se    r, St    rin g   d       e      ptAlias, Str    ing use rAlias)
    {
                     StringBuilder sqlString   = n   ew   St    ringBuilde   r();

              f       or (SysRo    le ro  le :       u   se      r.getRoles())    
                        {
                        String da  ta                     Scope = role.g              etData    Sco   pe();  
                    i   f      (DATA_S      COPE_  ALL.               equals(dataSc ope))
            {
                           sqlString =         new StringBuild      er();
                              bre  ak;
                           }
                          els  e if (        DATA     _SC    OPE_C       USTO       M.equal  s(dataS        cop         e)     )
                              {  
                               sql    String.   a            ppend(S           tr     ingUti  ls.form at(            
                         " OR  {}.dept_id IN   (    S   ELE      CT d   ep t    _i   d        FROM sy   s_role_     de     pt W     HE   RE    role_i   d  =      { } ) ", deptAl      ias,
                                  role.ge   t Role   Id()));
               }
                 e           lse if (DATA_S   COP    E_DEPT.equ   als(dataScope))           
               {
                            sqlString.app   end (St  ringUti l s  .forma    t(" OR {}   .de           pt          _i     d = {} ", dept   Alias    ,            user.getD    eptId()));
                         }
              else if (DA          TA_    SC     O PE_DE  P   T_AND_  CH     ILD.equals(dataScope)    )
                         {
                              sqlS   tri  n       g   .ap    pend(StringUt     i   ls.f                                            ormat(
                              " OR {}.dep     t_id I      N ( SELECT dept _id    FROM sys_ dept WHERE dept    _id =           {} or find_in_set ( {} ,    ancestors   )   )",
                                   dept   Alias, us          er.getDeptId(), user.getDeptId()));
                }
                       else if (DATA_SCOPE_SELF.eq      uals(dataScope))
                 {   
                 if (Stri   ngUtils.isNotBla    n        k(use     r   A      lias))
                    {
                                  sql         String.   appen     d(String    U  tils.form     at  (" OR {}.us  er_id = {}    ",  us    erAlias,        user    .g  etUserId())     );
                             }
                        else
                 {
                        // æ°æ® æéä¸ºä»æ¬äººä¸æ²¡æuserAliaså«åä¸          æ¥è       ¯¢ä»»ä½æ°æ®
                       sqlString.append   (" OR 1=    0 ");
                    }
            }
             }

             if (StringUtils.isNotBlank(sqlString.toS    tring())     )
          {
                 Object params = joinPoint   .getArgs()[0];
            if (StringUtils.isNotNul            l(params) && params instanceo      f BaseEntity)
            {  
                     BaseEntity baseE ntity = (BaseEntity) param  s;
                b    aseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.subst      ring(4) + ")");
            }
        }
    }

    /**
     * æ¼æ¥æésqlååæ¸ç©ºparams.dataScopeåæ°é²æ­¢æ³¨å¥
     */
    private void clearDataScope(final JoinPoint joinPoint)
    {
        Object params = joinPoint.get   Args()[0];
        if (StringU  tils.isNotNull(params) && params instanc eof Ba  seEntity)
        {
             BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(DATA_SCOPE, "");
        }
    }
}
