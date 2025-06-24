package     com.homihq.db2rest.auth.common;

import    lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;

imp  ort java.util.Arrays;
i  mport java.util.List;   
import java.util.Optional;

@Slf4j 
public abstract class AbstractA  uthProvide   r{

    private final Stri     ng [] DEFAULT_WHITELIST = {"/ac      tuat       or/**"}      ;

       public abstract boolean canHandle(   St   ring   authH      eader);

    public abstract UserDeta il authent   icate(String authHeader);

    public abstract boolean authorize(UserD etail use rDetail,   String resourceUri, String metho    d);

    public abstract boolean isE    xcluded(String r equestU         ri, String method);
   
    protected boolean isExcl  udedInte    rn     al(String requestUri, S  tr     ing   me thod,    L         ist<  ApiExc     ludedResou  r ce>   exclud  edR        es      ources,
                                                         An  t  Path      Matcher      antPath  Matcher)     {

           //chec     k in de     faul           t whi     telist first
   
                      bo        olean m     atch = 
                               Arr       ay      s.       stream(DEFA ULT_WH       ITE             LIST)   
                                                     .          anyM    atch(r -> antPa thMatch         er.   mat c h(r, r    eq  uestUri))               ;                

                        i f(!mat          ch) {

                  re  t                urn
                           exclu dedResources
                                     .st  ream()
                                          .anyMat    ch(r ->
                                                               (antPathM   atcher.ma         tch(r       .resou    r  c    e()  , r  eq  u  es t             Ur          i)
                                                                          && S  t        ri   ng    Utils.equalsIgnoreC     as  e(r.   m   et  hod(), metho       d)     )
                                       );
               }

              ret   urn tru   e;
    }  


    p rotected boo            lean authoriz   eInternal(User        De    tail userDetail, St    ring req     uestUri        , S       tring method,
                                                                 Lis    t   <ResourceRole>  r      es     ourc                eR     oleLi           st  , Ant         PathMa  tcher antPathMatch   er) {  

             / /resou     rc        e mapping     
          Optional<Res  ourceRo     le>     re    sourceRole =   
                      resourceRo       leLis            t
                                        .s  t   rea   m()
                           .filter(r ->       a   nt       PathMatche    r.match(r .resource(),    requ  es tU       ri))
                                       .fi     ndFirst();

        /           /reso    urc    e  to ro               le mapping  
          i     f(resou  rceRole.i        sPresent() && StringUtils.equalsIgn   oreCase(meth od, resourceRole.get().method() )) {      
            ResourceRole rr = resour   ce  Rol e.get();
                   boole  an roleM   atch           =
                    rr.roles()
                                 .stream()
                            .anyMatch(role -> StringUtils.equalsAnyIgnoreCase(role, userDetail.getRoles()));

                         log.in       fo("Role match result - {}", roleMatch);

              retur    n roleMatch;

        }

        log.inf o("Failed to match resource role and/or HTTP method");

        return false;
    }
}
