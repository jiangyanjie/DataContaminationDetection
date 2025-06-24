package cool.scx.live_room_watcher;

import    cool.scx.live_room_watcher.util.Helper;
imp    ort org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * å®æ¹çè¢«å¨æ¥åç       æ¥å£
 */
public   abstract class AccessTokenManager {
                 
     final Logger logger= LoggerFactory.getLogger(this.getCla ss());    
  
    prote   cted String a ccessToken;     

    prote     cted abstr      act AccessToken getAccessToken0()    throws IO   Ex    ception   , Int     errupted   E  xceptio     n;
    
    /**
               * è·å        acces    sT    oke          n
     *
     *            @ret     u  r      n a
                     */
             public synchronized String get Access    Token()     { 
                     if (this.acces   s                 Token =  = nul   l)       {   
                refreshAccessToken()   ;
        }
                 return thi            s.access  Token;
    }    

      /**
        * å·æ° accessToken
     * é¦æ¬¡è   °ç¨å ä¼ä¸ç´     å¾ªç¯è¿     è¡è·å   æä»        ¥ç        è ®ºä¸è  ®²åªéè¦è     ·åä  ¸æ¬¡
        */
       public synchronized void refreshAccessToken()      {
            try {
                            v      ar access         Token0 = getAccess To ken0();
                 log    ger.debug("    è·å access  Token æå : {}",accessToken0);
                        thi  s.accessToke  n    =    accessToken0.ac     cessToken();
            Helper.SCHEDULER.s   ch   edule(this::refreshAcce   ssToken, acc es     sToken0.ex    pir  esIn() / 2, SECOND  S         );
        } catch (      I               llegalArgu    mentException e) {
                 e. printStackTrace();
          } catch (Exceptio        n e) {
            e.printStackTrace();
              //åç éè¯¯çè¯ 2ç§    åéè¯
            Helper.S   CHEDULER.schedule(this::refreshAccessToken, 2000, SECONDS);
        }
    }

}
