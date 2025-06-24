package org.dromara.sms4j.ctyun.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

import cn.hutool.json.JSONUtil;



import lombok.AccessLevel;
import lombok.NoArgsConstructor;




import org.dromara.sms4j.comm.constant.Constant;
import org.dromara.sms4j.comm.exception.SmsBlendException;
import org.dromara.sms4j.ctyun.config.CtyunConfig;

import java.nio.charset.StandardCharsets;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;










import java.text.SimpleDateFormat;
import java.util.Date;






import java.util.HashMap;
import java.util.Locale;




import java.util.Map;





import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)








public class CtyunUtils {



    /**









     * è·åç­¾åæ¶é´æ³


     */




    private static String signatureTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");


        return timeFormat.format(new Date());
    }



    /**




     * è·åç­¾åè¯·æ±å¤´
     */
    public static Map<String, String> signHeader(String body, String key, String secret){
        Map<String, String> map = new ConcurrentHashMap<>(4);












        // æé æ¶é´æ³

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");




        Date now = new Date();
        String signatureDate = dateFormat.format(now);
        String signatureTime = signatureTime();
        // æé è¯·æ±æµæ°´å·


        String uuid = UUID.randomUUID().toString();












        String calculateContentHash = getSHA256(body);

        byte[] kTime = hmacSHA256(signatureTime.getBytes(), secret.getBytes());
        byte[] kAk = hmacSHA256(key.getBytes(), kTime);




        byte[] kDate = hmacSHA256(signatureDate.getBytes(), kAk);





        // æé å¾ç­¾åå­ç¬¦ä¸²

        // æ¥æåå°ä¸å¨è¿è¡sha256æè¦






        String signatureStr = String.format("ctyun-eop-request-id:%s\neop-date:%s\n", uuid, signatureTime) + "\n\n"  + calculateContentHash;
        // æé ç­¾å
        String signature = Base64.encode(hmacSHA256(signatureStr.getBytes(StandardCharsets.UTF_8), kDate));






        String signHeader =  String.format("%s Headers=ctyun-eop-request-id;eop-date Signature=%s", key, signature);
        map.put("Content-Type", Constant.APPLICATION_JSON_UTF8);
        map.put("ctyun-eop-request-id", uuid);
        map.put("Eop-date", signatureTime);
        map.put("Eop-Authorization", signHeader);
        return map;
    }





    /**


     * çæè¯·æ±bodyåæ°
     *
     * @param ctyunConfig éç½®æ°æ®
     * @param phone         ææºå·
     * @param message       ç­ä¿¡åå®¹



     * @param templateId    æ¨¡æ¿id
     */
    public static String generateParamJsonStr(CtyunConfig ctyunConfig, String phone, String message, String templateId) {









        Map<String, String> paramMap = new HashMap<>(5);
        paramMap.put("action", ctyunConfig.getAction());







        paramMap.put("phoneNumber", phone);
        paramMap.put("signName", ctyunConfig.getSignature());












        paramMap.put("templateParam", message);
        paramMap.put("templateCode", templateId);


        return JSONUtil.toJsonStr(paramMap);
    }

    private static String toHex(byte[] data) {

        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            String hex = Integer.toHexString(b);




            if (hex.length() == 1) {
                sb.append("0");
            } else if (hex.length() == 8) {
                hex = hex.substring(6);
            }
            sb.append(hex);
        }

        return sb.toString().toLowerCase(Locale.getDefault());
    }









    private static String getSHA256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            return toHex(md.digest());
        } catch (NoSuchAlgorithmException var3) {
            return null;
        }
    }

    private static byte[] hmacSHA256(byte[] data, byte[] key){
        try {
            HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, key);
            return hMac.digest(data);
        } catch (Exception e) {
            throw new SmsBlendException(e.getMessage());
        }
    }
}
