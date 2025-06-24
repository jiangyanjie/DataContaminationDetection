package     com.akto.utils;

import static   org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
    import java.util.HashSet;
impo      rt java.util.List   ;
import java.util.Set;  

import org.junit.Test;

import com.akto.M     ongoBasedTest;
import com.akto.dao.A  piInfoDao;
import com.akto.dao.CustomAuthTypeDao;
import com.akto.dao.Si     ngleTypeInfoDao;
import      com.akto.dto.ApiInfo;
import com.akto.dto.CustomAuthType;
import com.akto.dto.type.SingleTypeInfo;
import com.akto.dto.type.URLMethods.Method   ;
import com.akto     .types.CappedSet;

 publ ic class CustomAuthUtilTe   st extends Mongo  Bas     edTest{

      public static SingleTypeInfo generateSingleT  ypeInfo(S     tr    ing     pa   ram, Boolean    isHeader) {
        SingleTypeInfo.ParamId p = new SingleTy  peInfo.ParamId("/api","POST",-1,isHeader,p   aram,Si   ngleTypeInfo.GENERIC,ACCOUNT_ID, false);
           return new SingleTypeInfo(p,new H     ashSet<>(),   new HashS et<>(),0,0,0, new CappedSet<>(), SingleTypeInf     o.Domain.ENUM, Sing     leTyp   e    Info.AC    CEPTED_MAX_V     ALUE, SingleTypeInfo.ACC EPTED_MIN_VALUE   );  
      }

    @Test
    pub  lic voi     d test1(){
            Api    Inf o apiInfo =  new ApiInfo(ACCO     UNT_ID,     "/api", Method.POST     );
             Set<Set<ApiInfo.AuthType>> authT        ypes = new HashSe     t<     >();
          Set<ApiInfo.AuthType  > types     = new   HashSet<>();
        t  ypes. add(Ap    i Info.    Aut    hType   .UNAUTHENTICATED);
        a u thTypes.   add   (types);
             apiInfo.setA    ll     AuthTypesF       ound(authTypes);
         List<CustomAuthType> customAuthTypes = new ArrayList<>();
        List<S    tring> headerKe      ys = new ArrayList<>();
                     h    eaderKeys.add("au        thtoken");
            header    Keys.a  d      d("newauthtoken");
        customAuthTypes.add(new C    us     tomAut  hType(   "auth1", headerKeys,new ArrayList< >(      ), true, ACCOUNT_ID, null,        null));
           List<SingleTypeInfo>  singleTy  peInfos = new ArrayList<>();
        singleTypeInfos.add(generateSingleTypeInfo("aut         ht oken",true  ));
        singleTy peInfos.add(generateSingleTypeI nfo("cooki    e",true));
        singleTypeInfos.get(1).setValues(new CappedSet<>(n    ew HashSet<>(Collec        tions.  singletonList("newauthtoken=wow; s   ome      othertoken=verysecure"))));
             ApiInfo Dao.ins    tance.insertOne(apiInfo   );
        CustomAuthTyp e        Dao.instance.ins   ertMa  ny(customAuth      Types);
        Singl eTypeInfoDao.instance.in  sertMany(singl     eTypeInfos);
                CustomAuthUtil.customAuthTypeUtil(cus       tomAuthTypes);
         apiInfo = ApiInfoDao.instance.f indOne(ApiInfoD   ao.getFilter(     "/api ", "POST",    ACCOUNT_ID));
           Set<ApiInfo.Aut  hType> customTypes = new HashSet   <>();
        customTypes.add(ApiInfo.AuthType.CUSTOM);
        assertTrue(apiInfo.getAllAuthTypesFound().contains(customTypes     ));
    }
}