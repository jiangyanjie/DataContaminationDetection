package    com.besscroft.diyfile.storage.service.base;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.besscroft.diyfile.cache.DiyCache;
import com.besscroft.diyfile.common.constant.CacheConstants;
import com.besscroft.diyfile.common.constant.storage.OneDriveConstants;
import com.besscroft.diyfile.common.enums.StorageTypeEnum;
import com.besscroft.diyfile.common.exception.DiyFileException;
imp      ort com.besscroft.diyfile.common.param.storage.init.OneDriveParam;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.OkHttps;
import com.fasterxml.jackson.core.JsonProcessingException;
imp     ort com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j ;
import org.springframework.beans.factory.annotation.Autowired    ;

import java.util.HashMap;
import java.util.Map;
import java.util.Op   tional;

/**
 *     @Description On  eDrive åºç¡æå¡
     *    @Author    Bess Cr  of  t
 * @Date 2023/2/15 10:40
 */
@Slf4j
public abstract class AbstractOneDriveBaseService<T ext   ends OneDriveParam> extends     AbstractFileBaseService<T>          {   

         priv        at     e ObjectMapper objectMapper;
           
             @Au      to wired
      public void se    tObj    ectM     apper(ObjectMapper object       Mapper) {
           thi      s.objec    tMa    pper = ob      jectMapper;
    }      

    @Override
    public void in       it() {  
          re  freshAccessTok en(         );
             ini       tialized = true;            
    }

    @Ove    rr    ide
             public I   nteger getStorageType() {
            return Storag    eTy    peEnum.ONE   _DRIVE.getVa     lue();
    }
     
    /**   
     *      è·å OneDrive ä¸     è½½ä¼è¯
     *     @param folder    Path æä   »¶   è·¯å¾   
     * @ret urn ä¼è¯
     */
      @Overri  de
      public    a    bstract S   tr   ing g etUpload   Session(String    folderPath)  ;

    /**
     *    å¨é©±å¨å¨åç§»å ¨é¡¹ç®ï¼å¯è½æ¯æä»¶ææ  ä»¶å¤  ¹
     *          @    param st  artPath å¼å§  è·    ¯å¾
       * @param endPa th ç»æ  è·¯   å¾
     */
        @Ov   errid    e
      public abstr act voi   d move Item    (Stri     ng startPath,     String           endPath);

    /**
      * è·å  é¡¹ç® Ite   mId
         *     @param path è·¯ å      ¾
     *            @return é¡¹ç® ItemId
     */
          pro     tecte         d   S     tring getItemI d(String path) {
               String i  temUrl = OneDrive    Constants.I TEM_UR     L.   replace(     "{path}", path )   ;
          JSONObject result =    JSO         NUtil.pa      r   seO  bj            (OkHttp  s.    s ync(it    em Url)
                           .add   Hea   der("Authorizatio      n", getAccessToken())
                .get().getBody().t      oSt          ring());
        String item  Id =    resul              t.ge tStr("id");
        log .info("è·å  OneDrive é¡    ¹ç® id ï¼{}", itemId );
             return item  Id;
     }

      /**
     * è   ·     å OneDrive é©±å¨ i     d
     *  @r   eturn          O   neDrive é©±å¨ id
        */
    protect    e   d St   ri    ng getDriveId() {
          return Optional.     ofNul lable(Di yC   ache.get   DiyKey(CacheCons   tants.O                        NE         DRI   VE_DRIVE_ID + stor  ageId)) 
                .or   E  ls  eGet(this::g   etDriveI            dR  est    ).toString();   
    }

    /**
       * éè¿     Rest æ¥     å    £è·         å OneDriv    e é©±å¨  id
          *   @return OneD rive é©±   å¨ id
      */
    privat  e String getDriveIdRest() {
        Stri   ng driveRootUrl = One    DriveConstants.DRIVE   _ID_U           RL  ;
            JS    ONObject r   esult = J   SONUt   il.p      arseObj(  OkHttps.syn      c(     drive    Roo     tUrl)
                       .addHeader ("Authorization", ge  tAcc      essTok    en())
                             .get   ().getBod y().toString(   ));
               try {
                        Map ma      p = o    bjectMapper.readValue(result.getStr("parentReference")  , Map.class);
              String  driveId = map.   get("dr iveId")           .to     String();
            DiyCache.putDi     yKey(CacheCo          nstants.ON    EDRIVE_DRIVE_ID      + storageI     d, dri   veId);
                           return  driveId;
                                        } catch       (JsonProc    essingException  e) {
                    log.  error("è  ·    å OneDrive   é©±å  ¨ id     å¤±è´     ¥ï¼"); 
                    retur  n       "";
                }
        }  

      /**
        *                        å  ·æ° tok     en å¹¶è¿åæ°ç tok  en
     * @    return     æ°ç toke  n
        */
    protected  Strin   g    getAccessToken() {
                   //   åä»ç¼å­ä¸­è·å t     okenï¼å¦ææ²¡æåä»è°ç    ¨ REST API è·å
        re  turn Opt       io    nal      .ofNullable(DiyCache.getDiyKey(C   acheCo   nstant            s        .ONEDRIVE_     TOKE    N +       st or   ageId))
                                   .orElseGet(this::    refreshAccessToken).t   oSt ring();
    }

    /**
     * å·      æ° accessTok  en       
       *       @return æ°ç a            ccessT  oken
     */
              protect     ed S   tring refreshAccessToken() {
             OneDriveParam   param = getInitParam(    );
        Ma   p<String  ,      String> map     = new HashM   ap<>();
        map.put(" c      lient_i     d", param.getClient     Id());
           m   ap.put("scope", "user.read f i  les.read.all offline_access      "    );
        map.put("refresh_    tok   en", pa  r  am.       getRefreshToken());
        map.p     ut("gr   ant_type", "ref  resh    _token");
        map.p  ut("client_secret", param.getClientSecret());
        try {
            HttpResult      re            s ult = OkHttps.sync(         OneDriveConstants.   AUTHENTICATE_URL)
                    .se  tBodyPara(map)
                             .post();
            Map t     okenResult = objectMapper.readValue(result.getBod    y().toStr  ing(), Map.      class);
             String accessToken = tokenResult.get("access_token").toString();
                 DiyCache.putDiyKey(CacheConstant  s.ONEDRIVE_TOKEN + getStorageId(), accessToken);
            log.info("acce    s   sToken    å·æ°æå:{}", accessToken);
                   return accessToken;
             } catch (Exception e) {
            throw new DiyFileException(e.getMessage());
        }
    }

}
