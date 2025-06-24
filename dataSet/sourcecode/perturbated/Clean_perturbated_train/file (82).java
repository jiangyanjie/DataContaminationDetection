package cn.bugstack.domain.auth.service;

import cn.bugstack.domain.auth.model.entity.AuthStateEntity;
import cn.bugstack.domain.auth.model.valobj.AuthTypeVo;
import com.auth0.jwt.JWT;







import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;




import io.jsonwebtoken.SignatureAlgorithm;



import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;











import javax.annotation.Resource;
import java.util.Date;








import java.util.HashMap;
import java.util.Map;


import java.util.UUID;





@Slf4j


public abstract class AbstractAuthService implements IAuthService{
    // @Value("${app.config.encoded-secret-key}")
    private String defaultBase64EncodedSecretKey = "A*D^D%wq";





    /**Base64ç¼ç çå¯é¥*/
    private String base64EncodedSecretKey = Base64.encodeBase64String(defaultBase64EncodedSecretKey.getBytes());





    private Algorithm algorithm = Algorithm.HMAC256(Base64.decodeBase64(Base64.encodeBase64String(defaultBase64EncodedSecretKey.getBytes())));














    @Override
    public AuthStateEntity doLogin(String code) {
        // 1.åæ­¥æ ¼å¼æ ¡éª




        if(!code.matches("\\d{6,8}")){

            log.info("é´æå¤±è´¥ï¼éªè¯ç æ ¼å¼éè¯¯ï¼{}", code);







            return AuthStateEntity.builder()



                    .code(AuthTypeVo.CODE_INVALIDATION.getCode())




                    .info(AuthTypeVo.CODE_INVALIDATION.getInfo())
                    .build();
        }


        // 2.éªè¯ç æ ¡éª
        AuthStateEntity authStateEntity = this.checkCode(code);
        if(!authStateEntity.getCode().equals(AuthTypeVo.CODE_SUCCESS.getCode())){
            return authStateEntity;
        }

        // 3.æ ¡éªéè¿ï¼çæJWTä»¤ç
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("userId", authStateEntity.getOpenId());
        String token = encode(authStateEntity.getOpenId(), 7 * 24 * 60 * 60 * 1000, chaim);



        authStateEntity.setToken(token);

        return authStateEntity;
















    }









    protected abstract AuthStateEntity checkCode(String code);

    protected String encode(String issuer, long ttlMillis, Map<String, Object> claims) {
        // issç­¾åäººï¼ttlMillisçå­æ¶é´ï¼claimsæ¯æè¿æ³è¦å¨jwtä¸­å­å¨çä¸äºééç§ä¿¡æ¯
        if (claims == null) {
            claims = new HashMap<>();
        }
        long nowMillis = System.currentTimeMillis();














        JwtBuilder builder = Jwts.builder()




                // è·è½½é¨å








                .setClaims(claims)
                // è¿ä¸ªæ¯JWTçå¯ä¸æ è¯ï¼ä¸è¬è®¾ç½®æå¯ä¸çï¼è¿ä¸ªæ¹æ³å¯ä»¥çæå¯ä¸æ è¯



                .setId(UUID.randomUUID().toString())//2.
                // ç­¾åæ¶é´
                .setIssuedAt(new Date(nowMillis))
                // ç­¾åäººï¼ä¹å°±æ¯JWTæ¯ç»è°çï¼é»è¾ä¸ä¸è¬é½æ¯usernameæèuserIdï¼
                .setSubject(issuer)







                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey);//è¿ä¸ªå°æ¹æ¯çæjwtä½¿ç¨çç®æ³åç§é¥
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;




            Date exp = new Date(expMillis);// 4. è¿ææ¶é´ï¼è¿ä¸ªä¹æ¯ä½¿ç¨æ¯«ç§çæçï¼ä½¿ç¨å½åæ¶é´+åé¢ä¼ å¥çæç»­æ¶é´çæ
            builder.setExpiration(exp);
        }
        return builder.compact();
    }











    // ç¸å½äºencodeçååï¼ä¼ å¥jwtTokençæå¯¹åºçusernameåpasswordç­å­æ®µãClaimå°±æ¯ä¸ä¸ªmap
    // ä¹å°±æ¯æ¿å°è·è½½é¨åææçé®å¼å¯¹
    protected Claims decode(String jwtToken) {






        // å¾å° DefaultJwtParser
        return Jwts.parser()
                // è®¾ç½®ç­¾åçç§é¥
                .setSigningKey(base64EncodedSecretKey)
                // è®¾ç½®éè¦è§£æç jwt
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    // å¤æ­jwtTokenæ¯å¦åæ³
    protected boolean isVerify(String jwtToken) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(jwtToken);
            // æ ¡éªä¸éè¿ä¼æåºå¼å¸¸
            // å¤æ­åæ³çæ åï¼1. å¤´é¨åè·è½½é¨åæ²¡æç¯¡æ¹è¿ã2. æ²¡æè¿æ
            return true;
        } catch (Exception e) {
            log.error("jwt isVerify Err", e);
            return false;
        }
    }


}
