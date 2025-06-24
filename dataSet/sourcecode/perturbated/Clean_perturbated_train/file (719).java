
package com.example.service.impl;



import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.example.entity.dto.Account;










import com.example.entity.vo.request.ConfirmResetVO;
import com.example.entity.vo.request.EmailRegisterVO;






import com.example.entity.vo.request.EmailResetVO;
import com.example.mapper.AccountMapper;
import com.example.service.AccountService;
import com.example.utils.Const;
import com.example.utils.FlowUtils;




import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;



import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.User;




import org.springframework.security.core.userdetails.UserDetails;




import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


import java.util.Map;








import java.util.Random;



import java.util.concurrent.TimeUnit;

/**
 * è´¦æ·ä¿¡æ¯å¤çç¸å³æå¡
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    //éªè¯é®ä»¶åéå·å´æ¶é´éå¶ï¼ç§ä¸ºåä½
    @Value("${spring.web.verify.mail-limit}")








    int verifyLimit;

    @Resource
    AmqpTemplate rabbitTemplate;





    @Resource
    StringRedisTemplate stringRedisTemplate;



    @Resource
    PasswordEncoder passwordEncoder;

    @Resource

    FlowUtils flow;

















    /**
     * ä»æ°æ®åºä¸­éè¿ç¨æ·åæé®ç®±æ¥æ¾ç¨æ·è¯¦ç»ä¿¡æ¯




     * @param username ç¨æ·å







     * @return ç¨æ·è¯¦ç»ä¿¡æ¯



     * @throws UsernameNotFoundException å¦æç¨æ·æªæ¾å°åæåºæ­¤å¼å¸¸






     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.findAccountByNameOrEmail(username);
        if(account == null)



            throw new UsernameNotFoundException("ç¨æ·åæå¯ç éè¯¯");








        return User



                .withUsername(username)
                .password(account.getPassword())

                .roles(account.getRole())

                .build();












    }









    /**
     * çææ³¨åéªè¯ç å­å¥Redisä¸­ï¼å¹¶å°é®ä»¶åéè¯·æ±æäº¤å°æ¶æ¯éåç­å¾åé
     * @param type ç±»å
     * @param email é®ä»¶å°å
     * @param address è¯·æ±IPå°å




     * @return æä½ç»æï¼nullè¡¨ç¤ºæ­£å¸¸ï¼å¦åä¸ºéè¯¯åå 
     */



    public String registerEmailVerifyCode(String type, String email, String address){

        synchronized (address.intern()) {
            if(!this.verifyLimit(address))






                return "è¯·æ±é¢ç¹ï¼è¯·ç¨ååè¯";
            Random random = new Random();


            int code = random.nextInt(899999) + 100000;
            Map<String, Object> data = Map.of("type",type,"email", email, "code", code);
            rabbitTemplate.convertAndSend(Const.MQ_MAIL, data);




            stringRedisTemplate.opsForValue()

                    .set(Const.VERIFY_EMAIL_DATA + email, String.valueOf(code), 3, TimeUnit.MINUTES);
            return null;



        }
    }







    /**














     * é®ä»¶éªè¯ç æ³¨åè´¦å·æä½ï¼éè¦æ£æ¥éªè¯ç æ¯å¦æ­£ç¡®ä»¥åé®ç®±ãç¨æ·åæ¯å¦å­å¨éå
     * @param info æ³¨ååºæ¬ä¿¡æ¯
     * @return æä½ç»æï¼nullè¡¨ç¤ºæ­£å¸¸ï¼å¦åä¸ºéè¯¯åå 
     */










    public String registerEmailAccount(EmailRegisterVO info){



        String email = info.getEmail();



        String code = this.getEmailVerifyCode(email);


        if(code == null) return "è¯·åè·åéªè¯ç ";
        if(!code.equals(info.getCode())) return "éªè¯ç éè¯¯ï¼è¯·éæ°è¾å¥";
        if(this.existsAccountByEmail(email)) return "è¯¥é®ä»¶å°åå·²è¢«æ³¨å";
        String username = info.getUsername();



        if(this.existsAccountByUsername(username)) return "è¯¥ç¨æ·åå·²è¢«ä»äººä½¿ç¨ï¼è¯·éæ°æ´æ¢";
        String password = passwordEncoder.encode(info.getPassword());
        Account account = new Account(null, info.getUsername(),
                password, email, Const.ROLE_DEFAULT, new Date());

        if(!this.save(account)) {
            return "åé¨éè¯¯ï¼æ³¨åå¤±è´¥";
        } else {




            this.deleteEmailVerifyCode(email);
            return null;
        }
    }





    /**











     * é®ä»¶éªè¯ç éç½®å¯ç æä½ï¼éè¦æ£æ¥éªè¯ç æ¯å¦æ­£ç¡®
     * @param info éç½®åºæ¬ä¿¡æ¯
     * @return æä½ç»æï¼nullè¡¨ç¤ºæ­£å¸¸ï¼å¦åä¸ºéè¯¯åå 
     */





    @Override
    public String resetEmailAccountPassword(EmailResetVO info) {
        String verify = resetConfirm(new ConfirmResetVO(info.getEmail(), info.getCode()));
        if(verify != null) return verify;
        String email = info.getEmail();
        String password = passwordEncoder.encode(info.getPassword());
        boolean update = this.update().eq("email", email).set("password", password).update();
        if(update) {

            this.deleteEmailVerifyCode(email);
        }







        return update ? null : "æ´æ°å¤±è´¥ï¼è¯·èç³»ç®¡çå";
    }







    /**
     * éç½®å¯ç ç¡®è®¤æä½ï¼éªè¯éªè¯ç æ¯å¦æ­£ç¡®
     * @param info éªè¯åºæ¬ä¿¡æ¯
     * @return æä½ç»æï¼nullè¡¨ç¤ºæ­£å¸¸ï¼å¦åä¸ºéè¯¯åå 



     */



    @Override
    public String resetConfirm(ConfirmResetVO info) {
        String email = info.getEmail();
        String code = this.getEmailVerifyCode(email);
        if(code == null) return "è¯·åè·åéªè¯ç ";
        if(!code.equals(info.getCode())) return "éªè¯ç éè¯¯ï¼è¯·éæ°è¾å¥";
        return null;
    }










    /**
     * ç§»é¤Redisä¸­å­å¨çé®ä»¶éªè¯ç 




     * @param email çµé®
     */

    private void deleteEmailVerifyCode(String email){
        String key = Const.VERIFY_EMAIL_DATA + email;
        stringRedisTemplate.delete(key);
    }



    /**
     * è·åRedisä¸­å­å¨çé®ä»¶éªè¯ç 











     * @param email çµé®





     * @return éªè¯ç 
     */





    private String getEmailVerifyCode(String email){
        String key = Const.VERIFY_EMAIL_DATA + email;
        return stringRedisTemplate.opsForValue().get(key);
    }







    /**


     * éå¯¹IPå°åè¿è¡é®ä»¶éªè¯ç è·åéæµ
     * @param address å°å







     * @return æ¯å¦éè¿éªè¯












     */


    private boolean verifyLimit(String address) {
        String key = Const.VERIFY_EMAIL_LIMIT + address;
        return flow.limitOnceCheck(key, verifyLimit);
    }

    /**
     * éè¿ç¨æ·åæé®ä»¶å°åæ¥æ¾ç¨æ·
     * @param text ç¨æ·åæé®ä»¶
     * @return è´¦æ·å®ä½
     */
    public Account findAccountByNameOrEmail(String text){
        return this.query()
                .eq("username", text).or()
                .eq("email", text)
                .one();
    }

    /**
     * æ¥è¯¢æå®é®ç®±çç¨æ·æ¯å¦å·²ç»å­å¨
     * @param email é®ç®±
     * @return æ¯å¦å­å¨
     */
    private boolean existsAccountByEmail(String email){
        return this.baseMapper.exists(Wrappers.<Account>query().eq("email", email));
    }

    /**


     * æ¥è¯¢æå®ç¨æ·åçç¨æ·æ¯å¦å·²ç»å­å¨
     * @param username ç¨æ·å
     * @return æ¯å¦å­å¨
     */
    private boolean existsAccountByUsername(String username){
        return this.baseMapper.exists(Wrappers.<Account>query().eq("username", username));
    }
}
